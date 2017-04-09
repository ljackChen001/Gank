package com.api;

import com.model.Gank;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chenbaolin on 2017/4/5.
 */

public interface ApiService {

//    @GET("data/{gank}/10/{page}")
//    Observable<Gank> getGankData(@Path("gank") String gank,
//                                                   @Path("page") int page);
    @GET("data/Android/10/{page}")
    Flowable<Gank> getGankData(@Path("page") String page);

}
