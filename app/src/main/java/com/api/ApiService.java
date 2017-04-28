package com.api;

import com.Constants;
import com.entity.BaseRespnseData;
import com.entity.HttpResult;
import com.entity.UserInfo;
import com.model.Gank;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by chenbaolin on 2017/4/5.
 */

public interface ApiService {

    //    @GET("data/{gank}/10/{page}")
    //    Observable<Gank> getGankData(@Path("gank") String gank,
    //                                                   @Path("page") int page);·
    @GET("data/Android/10/{page}")
    Flowable<Gank> getGankData(@Path("page") String page);

    @POST(Constants.ApiInterface.LOGIN_REGISTER)
    Observable<BaseRespnseData<UserInfo>> login(@Query("userPhone") String userPhone,
                                               @Query("time") String timestamp,
                                               @Query("appkeyId") String appkeyId,
                                               @Query("deviceToken") String deviceToken,
                                               @Query("ip") String ip,
                                               @Query("source") String source,
                                               @Query("verificationResponseCode") String code,
                                               @Query("additional") String additional);

    @POST(Constants.ApiInterface.SEND_CODE)
    Observable<HttpResult> sendCode(@Query("phone") String userPhone,
                                      @Query("codeType") String codeType);
    @POST(Constants.ApiInterface.FIND_RENTAL_CARS)
    Flowable<RentalCar> getRentalCars();


}
