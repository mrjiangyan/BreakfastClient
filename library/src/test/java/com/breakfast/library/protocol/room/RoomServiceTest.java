package com.breakfast.library.protocol.room;


import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;


@RunWith(RobolectricGradleTestRunner.class)
public class RoomServiceTest {

//    @Test
//    public void testChangeRoomStatus() throws Exception {
//
//        Application application = RuntimeEnvironment.application;
//
//        Call<ApiResponse<String>> baseResponseCall=ServiceFactory.generateService(application,IRoomService.class).changeRoomStatus("2001","VD", "2016-08-12");
//        final CountDownLatch timer= new CountDownLatch(1);
//        baseResponseCall.enqueue(new Callback<ApiResponse<String>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
//                if(!response.isSuccessful())
//                {
//                    try {
//                        LogUtils.d(response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                timer.countDown();
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
//                LogUtils.d(t);
//                timer.countDown();
//            }
//        });
//        timer.await();
//    }
}