package com.breakfast.library.network.protocol.app;

import com.breakfast.library.data.entity.app.AppCheck;
import com.breakfast.library.data.entity.base.ApiResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Steven on 2017/6/1.
 */

public interface IAppService {

    @GET("/app/check")
    Observable<ApiResponse<AppCheck>> check();
}
