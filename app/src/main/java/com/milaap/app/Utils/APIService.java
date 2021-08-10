package com.milaap.app.Utils;

import com.milaap.app.Notifications.MyResponse;
import com.milaap.app.Notifications.Sender;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA0cao058:APA91bHzt5DgjWFrDjb9dq8UNjQBtrn8qgyN5QHYIUVOzj8GlFEh1Pkab5KP4k4D42rAXvq1lKqhhcWqzGi81ZFWeratUUsPUuuM6KJhBL74bgQSgZNn6dCXhmVUbHWd4EsGhfDaKX3h"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

    @POST("fcm/send")
    Call<MyResponse> sendPostNotification(@Body Sender body);
}
