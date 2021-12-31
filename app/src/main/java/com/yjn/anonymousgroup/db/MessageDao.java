package com.yjn.anonymousgroup.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yjn.anonymousgroup.model.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(Message message);

    @Query("DELETE FROM message")
    void deleteAll();

    @Query("SELECT * FROM message limit 50")
    LiveData<List<Message>> getChattingRecords();
}
