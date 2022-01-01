package com.yjn.anonymousgroup.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;
import androidx.paging.PagingSource;

import com.yjn.anonymousgroup.App;
import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.repository.MessageRepository;
import com.yjn.anonymousgroup.udp.CanChatUdpReceiver;
import com.yjn.anonymousgroup.udp.Udp;

import kotlinx.coroutines.CoroutineScope;

public class MessageViewModel extends ViewModel {
    private MessageRepository messageRepository;
    CoroutineScope viewModelScope;
    Pager<Integer, Message> pager;

    public MessageViewModel() {
        messageRepository =  MessageRepository.getMessageRepository();
//        PagingSource<Integer, Message> messageLiveData;
//        messageLiveData = messageRepository.getChattingRecords();
        viewModelScope = ViewModelKt.getViewModelScope(this);
        pager = new Pager<>(new PagingConfig(50),() -> messageRepository.getChattingRecords());
    }

    public LiveData<PagingData<Message>> getChattingRecords(){
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }
}
