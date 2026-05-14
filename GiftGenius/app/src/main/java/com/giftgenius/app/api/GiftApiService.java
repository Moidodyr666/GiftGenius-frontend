package com.giftgenius.app.api;

import com.giftgenius.app.models.Gift;
import com.giftgenius.app.models.GiftRequest;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GiftApiService {
    @POST("api/gifts/generate")
    Call<List<Gift>> generateGifts(@Body GiftRequest request);

    @GET("api/gifts")
    Call<List<Gift>> getAllGifts();
}
