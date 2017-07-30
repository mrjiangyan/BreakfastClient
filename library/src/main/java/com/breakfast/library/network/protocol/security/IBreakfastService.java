package com.breakfast.library.network.protocol.security;

import com.breakfast.library.data.entity.base.ApiResponse;
import com.breakfast.library.data.entity.breakfast.ConsumeBreakfast;
import com.breakfast.library.data.entity.breakfast.HotelBreakfastSummary;

import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jliang on 7/24/2017.
 */

public interface IBreakfastService {

    /**
     * 获取酒店早餐详情
     * @return
     */
    @GET("/breakfast/get/summary") Observable<ApiResponse<HotelBreakfastSummary>> getHotelBreakfastSummary();

    /**
     * 模糊查询房间号
     * @return
     */
    @GET("xxxx") Observable<ApiResponse<String[]>> getRoomNumbers(String roomNumber);

    /**
     * 消费早餐
     * @return
     */
    @POST("xxxx") Observable<ApiResponse<Boolean>> postConsumeBreakfast(ConsumeBreakfast model);


}
