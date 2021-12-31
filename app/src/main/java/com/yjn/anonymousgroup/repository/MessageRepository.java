package com.yjn.anonymousgroup.repository;

import androidx.lifecycle.LiveData;

import com.yjn.anonymousgroup.db.MessageDao;
import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.viewmodel.MessageViewModel;

import java.util.List;

public class MessageRepository {
    private MessageDao messageDao;
    private MessageViewModel messageViewModel;

    public MessageRepository(MessageDao messageDao,MessageViewModel messageViewModel){
        this.messageDao = messageDao;
        this.messageViewModel = messageViewModel;
    }

    public LiveData<List<Message>> getChattingRecords(){
        return messageDao.getChattingRecords();
    }

    public void insertMessage(Message message){
        messageDao.insertMessage(message);
    }
}
