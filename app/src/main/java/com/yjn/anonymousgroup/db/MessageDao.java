package com.yjn.anonymousgroup.db;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yjn.anonymousgroup.model.Message;


@Dao
public interface MessageDao {
    @Insert()
    void insertMessage(Message message);

    @Query("DELETE FROM message")
    void deleteAll();

    @Query("SELECT * FROM message limit 50")
    PagingSource<Integer, Message> getChattingRecords();
}
