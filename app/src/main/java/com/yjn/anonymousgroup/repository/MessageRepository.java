package com.yjn.anonymousgroup.repository;

import androidx.paging.PagingSource;

import com.yjn.anonymousgroup.App;
import com.yjn.anonymousgroup.db.MessageDao;
import com.yjn.anonymousgroup.model.Message;

public class MessageRepository {
    private MessageDao messageDao;
    private static final MessageRepository messageRepository = new MessageRepository();

    private MessageRepository(){
        messageDao = App.getDatabase().messageDao();
    }

    public static MessageRepository getInstance(){
        return messageRepository;
    }

    public PagingSource<Integer, Message> getChattingRecords(){
        return messageDao.getChattingRecords();
    }

    public void insertMessage(Message message){
        messageDao.insertMessage(message);
    }

    public void deleteAll(){
        messageDao.deleteAll();
    }
}
