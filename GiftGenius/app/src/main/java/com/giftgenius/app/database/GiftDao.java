package com.giftgenius.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.giftgenius.app.models.Gift;
import java.util.List;

@Dao
public interface GiftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Gift gift);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Gift> gifts);

    @Update
    void update(Gift gift);

    @Delete
    void delete(Gift gift);

    @Query("SELECT * FROM gifts")
    LiveData<List<Gift>> getAllGifts();

    @Query("SELECT * FROM gifts WHERE isFavorite = 1")
    LiveData<List<Gift>> getFavorites();

    @Query("SELECT * FROM gifts WHERE id = :id")
    Gift getGiftById(int id);

    @Query("UPDATE gifts SET isFavorite = :isFavorite WHERE id = :id")
    void updateFavoriteStatus(int id, boolean isFavorite);

    @Query("DELETE FROM gifts")
    void deleteAll();
}
