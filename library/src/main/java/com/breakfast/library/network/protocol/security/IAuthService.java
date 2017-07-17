package com.breakfast.library.network.protocol.security;

import android.support.annotation.NonNull;

import com.breakfast.library.data.entity.base.ApiResponse;
import com.breakfast.library.data.entity.user.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface IAuthService {

  @POST("/auth/login") Observable<ApiResponse<String>> login(@NonNull @Body User detail);

  @POST("/auth/logout") Observable<ApiResponse<Void>> logout();

  
  @GET("/auth/refresh") Observable<ApiResponse<String>> refreshToken();



}
