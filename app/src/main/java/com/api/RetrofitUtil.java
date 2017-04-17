package com.api;

import com.App;
import com.C;
import com.base.util.LogUtils;
import com.base.util.NetWorkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by chenbaolin on 2017/4/5.
 */

public class RetrofitUtil {
    public static RetrofitUtil retrofitUtil;
    public Retrofit retrofit;
    public static final long CONNECT_TIME_OUT = 30L;
    public static final long READ_TIME_OUT = 30L;
    public static final long WRITE_TIME_OUT = 30L;

    //获取单例
    public static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null) {
                    retrofitUtil = new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;

    }

    /**
     * 统一的请求头
     */
    Interceptor headInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
            .addHeader("appkeyId", "1234")
            .addHeader("Content-Type", "application/json")
            .build());
    /**
     * 让所有网络请求都附上token
     */
//    Interceptor mTokenInterceptor = chain -> {
    //        Request originalRequest = chain.request();
    //        if (Your.sToken == null || alreadyHasAuthorizationHeader(originalRequest)) {
    //            return chain.proceed(originalRequest);
    //        }
    //        Request authorised = originalRequest.newBuilder()
    //                .header("Authorization", Your.sToken)
    //                .build();
    //        return chain.proceed(authorised);
    //    };
    //静态内部类,保证单例并在调用getRetrofit方法的时候才去创建.
    private  RetrofitUtil() {
        // 可以通过实现 Logger 接口更改日志保存位置
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //配置OkHttpClient（设置缓存路径和缓存文件大小）
        File httpCacheDirectory = new File(App.getAppContext().getCacheDir(), "HttpCache");
        Cache cache = new Cache(httpCacheDirectory, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(headInterceptor)
                .addInterceptor(new ChuckInterceptor(App.getAppContext()))
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())//添加自定义缓存拦截器
                .cache(cache)//把缓存添加进来
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)//读取超时
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)//写入超时
                .retryOnConnectionFailure(true)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        //配置Retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)//把OkHttpClient添加进来
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(C.BASE_URL)
                .build();
        //        apiService = retrofit.create(retrofitUtilService.class);
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

    class HttpCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //对在request请求前对其进行无网的判断，其实无需判断，无网自动访问缓存
            if (!NetWorkUtil.isNetConnected(App.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)//只访问缓存
                        .build();
                LogUtils.d("Okhttp", "no network");
            }

            Response response = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(App.getAppContext())) {
                int maxAge = 60;//缓存失效时间，单位为秒
                LogUtils.d("Okhttp", "请求网络数据");
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return response.newBuilder()
                        .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .header("Cache-Control", cacheControl)
                        .build();
            } else {
                LogUtils.d("Okhttp", "请求数据");
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }

        }
    }

    /**
     * 初始化通用的观察者
     * 订阅
     *
     * @param observable 观察者
     */
    public ResourceSubscriber startObservable(Flowable observable, ResourceSubscriber subscriber) {
        return (ResourceSubscriber) observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnLifecycle(subscription -> LogUtils.d("doOnLifecycle", "OnSubscribe"),
                        t -> LogUtils.d("doOnLifecycle", "OnRequest"),
                        () -> LogUtils.d("doOnLifecycle", "OnCancel"))
                .subscribeWith(subscriber);
    }
}