package com.giftgenius.app.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.giftgenius.app.api.ApiClient;
import com.giftgenius.app.database.AppDatabase;
import com.giftgenius.app.database.GiftDao;
import com.giftgenius.app.models.Gift;
import com.giftgenius.app.models.GiftRequest;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiftRepository {
    private static GiftRepository instance;
    private GiftDao giftDao;
    private LiveData<List<Gift>> allGifts;
    private LiveData<List<Gift>> favorites;

    public interface GiftCallback {
        void onSuccess(List<Gift> gifts);
        void onError(String error);
    }

    private GiftRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        giftDao = database.giftDao();
        allGifts = giftDao.getAllGifts();
        favorites = giftDao.getFavorites();
    }

    public static synchronized GiftRepository getInstance(Application application) {
        if (instance == null) {
            instance = new GiftRepository(application);
        }
        return instance;
    }

    public void generateGifts(String recipient, String occasion,
                              String interests, String budget,
                              GiftCallback callback) {
        GiftRequest request = new GiftRequest(recipient, occasion, interests, budget);

        ApiClient.getClient().generateGifts(request).enqueue(new Callback<List<Gift>>() {
            @Override
            public void onResponse(Call<List<Gift>> call, Response<List<Gift>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Gift> gifts = response.body();
                    new Thread(() -> {
                        giftDao.insertAll(gifts);
                    }).start();
                    callback.onSuccess(gifts);
                } else {
                    callback.onError("Ошибка сервера: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Gift>> call, Throwable t) {
                callback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Gift>> getAllGifts() {
        return allGifts;
    }

    public LiveData<List<Gift>> getFavorites() {
        return favorites;
    }

    public void toggleFavorite(Gift gift) {
        new Thread(() -> {
            gift.setFavorite(!gift.isFavorite());
            giftDao.update(gift);
        }).start();
    }

    public void insertGift(Gift gift) {
        new Thread(() -> giftDao.insert(gift)).start();
    }

    public void deleteGift(Gift gift) {
        new Thread(() -> giftDao.delete(gift)).start();
    }
}
