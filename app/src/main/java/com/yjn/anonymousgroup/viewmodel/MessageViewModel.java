package com.yjn.anonymousgroup.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yjn.anonymousgroup.App;
import com.yjn.anonymousgroup.db.MessageDao;
import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.repository.MessageRepository;
import com.yjn.anonymousgroup.udp.CanChatUdpReceiver;
import com.yjn.anonymousgroup.udp.Udp;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private LiveData<List<Message>> messageLiveData;
    private MessageRepository messageRepository;
    Application application;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        MessageDao messageDao = App.getDatabase().messageDao();
        messageRepository =  new MessageRepository(messageDao);
        messageLiveData = messageRepository.getChattingRecords();
    }

    public LiveData<List<Message>> getChattingRecords(){
        return messageLiveData;
    }

    public void udpReceiver(){
        new CanChatUdpReceiver(application, Udp.PORT_ALL,messageRepository).start();
    }
}
