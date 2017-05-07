package com.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.base.util.StringUtils;
import com.ui.gank.R;

/***********************************************************************
 **                            _ooOoo_  
 **                           o8888888o  
 **                           88" . "88  
 **                           (| -_- |)  
 **                            O\ = /O  
 **                        ____/`---'\____  
 **                      .   ' \\| |// `.  
 **                       / \\||| : |||// \  
 **                     / _||||| -:- |||||- \  
 **                       | | \\\ - /// | |  
 **                     | \_| ''\---/'' | |  
 **                      \ .-\__ `-` ___/-. /  
 **                   ___`. .' /--.--\ `. . __  
 **                ."" '< `.___\_<|>_/___.' >'"".  
 **               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
 **                 \ \ `-. \_ __\ /__ _/ .-` / /  
 **         ======`-.____`-.___\_____/___.-`____.-'======  
 **                            `=---='  
 **         .............................................  
 **                  佛祖镇楼                  BUG辟易  
 **    佛曰:  
 **    写字楼里写字间，写字间里程序员；  程序人员写程序，又拿程序换酒钱。  
 **    酒醒只在网上坐，酒醉还来网下眠；  酒醉酒醒日复日，网上网下年复年。  
 **    酒醉酒醒日复日，网上网下年复年。  酒醉酒醒日复日，网上网下年复年。  
 **    酒醉酒醒日复日，网上网下年复年。  但愿老死电脑间，不愿鞠躬老板前；  
 **    奔驰宝马贵者趣，公交自行程序员。  别人笑我忒疯癫，我笑自己命太贱；  
 **                  不见满街漂亮妹，哪个归得程序员？
 ***********************************************************************
 * Created by ChenBaoLin on 2017/5/1.
 *
 */

public class BaseWebActivity extends BaseActivity {

    private static final String TAG = "BaseWebActivity";

    protected WebView wv_web_view;


    @Override
    protected int setLayoutResouceId() {
        return R.layout.layout_web;
    }


    @Override
    protected void initView() {
        wv_web_view = (WebView) findViewById(R.id.wv_webview);
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    protected WebView initWebView(final String url,
                                  Activity activity) {
        return initWebView(url, activity, null, null);
    }

    protected WebView initWebView(final String url,
                                  Activity activity,
                                  final onWebProgressListener listener) {
        return initWebView(url, activity, null, listener);
    }

    /**
     * 初始化webView
     *
     * @param activity webView窗体
     * @param listener web加载进度监听
     * @param classObj 调用javascript类
     * @return
     */
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    protected WebView initWebView(final String url,
                                  Activity activity,
                                  Object classObj,
                                  final onWebProgressListener listener
    ) {
        if (null == activity) {
            Log.e(TAG, "activity is null!");
            return null;
        }

        WebSettings wv_settings = wv_web_view.getSettings();
        wv_settings.setJavaScriptEnabled(true);
        wv_settings.setDefaultTextEncodingName("UTF-8");
        wv_settings.setDisplayZoomControls(false);
        wv_settings.setBuiltInZoomControls(false);
        wv_settings.setSupportZoom(false);
        wv_settings.setDomStorageEnabled(true);

        // 加载的页面自适应手机屏幕
        wv_settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        wv_web_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //                LoadingDialogUtils.showLoadingDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //                LoadingDialogUtils.cancelLoadingDialog();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //去WebView打开
                view.loadUrl(url);
                return true;
            }

            //https:验证
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
            }

        });

        if (null != listener) {
            wv_web_view.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (100 == newProgress) {
                        if (null != listener) {
                            listener.onProgressComplete();
                        }
                        // 关闭进度条
                    } else {
                        if (null != listener) {
                            listener.onProgressStart();
                        }
                        // 加载中显示进度条
                    }
                }
            });
        }

        if (null != classObj) {
            wv_web_view.addJavascriptInterface(classObj, "JsInteface");
        }

        if (!StringUtils.isEmpty(url)) {
            loadWebPage(wv_web_view, url);
        }

        return wv_web_view;
    }

    /**
     * 加载网页
     */
    private void loadWebPage(WebView webView, final String params) {
        if (this.isUrl(params)) { // url 地址 形式 加载
            //WebView加载web资源
            //          final String urlString = WebPage.IP_PATH
            //                  + this.webPageIdentify
            //                  + this.webPageParams;
            Log.e(TAG, "url：" + params);
            webView.loadUrl(params);
        } else {// 网页 源码 形式 加载
            webView.loadDataWithBaseURL(null, params, "text/html", "utf-8", null);
        }
    }

    private boolean isUrl(final String url) {
        boolean urlFlag = false;

        final String strTmp = url.substring(0, 4);
        if (!StringUtils.isEmpty(url) && !StringUtils.isEmpty(strTmp)) {
            if ("http".equals(strTmp) || "https".equals(strTmp)) {
                urlFlag = true;
            }
        }

        return urlFlag;
    }


    //	protected abstract void initView();
    //	protected abstract void initLogic();

    /**
     * @author zhaojy
     * @ClassName IWebProgress
     * @date 2015-12-13
     * @Description: web页加载状态
     */
    public interface onWebProgressListener {
        public void onProgressComplete();

        public void onProgressStart();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}

