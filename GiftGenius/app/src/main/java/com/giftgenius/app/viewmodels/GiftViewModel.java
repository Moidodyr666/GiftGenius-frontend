package com.giftgenius.app.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.giftgenius.app.models.Gift;
import com.giftgenius.app.repository.GiftRepository;
import java.util.List;

public class GiftViewModel extends AndroidViewModel {
    private GiftRepository repository;
    private LiveData<List<Gift>> gifts;
    private LiveData<List<Gift>> favorites;
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<String> error;
    private MutableLiveData<Boolean> offlineMode;

    public GiftViewModel(GiftRepository repository) {
        this.repository = repository;
        this.loading = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.offlineMode = new MutableLiveData<>();
    }

    public void generateGifts(String recipient, String occasion,
                              String interests, String budget) {
        loading.setValue(true);
        error.setValue(null);

        repository.generateGifts(recipient, occasion, interests, budget,
                new GiftRepository.GiftCallback() {
                    @Override
                    public void onSuccess(List<Gift> gifts) {
                        loading.setValue(false);
                        offlineMode.setValue(false);
                    }

                    @Override
                    public void onError(String error) {
                        loading.setValue(false);
                        GiftViewModel.this.error.setValue(error);
                        offlineMode.setValue(true);
                    }
                });
    }

    public LiveData<List<Gift>> getGifts() {
        return repository.getAllGifts();
    }

    public LiveData<List<Gift>> getFavorites() {
        return repository.getFavorites();
    }

    public void loadFavorites() {
        // Просто триггерим загрузку
    }

    public void toggleFavorite(Gift gift) {
        repository.toggleFavorite(gift);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getOfflineMode() {
        return offlineMode;
    }
}
