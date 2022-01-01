package com.yjn.anonymousgroup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.repository.MessageRepository;

import kotlinx.coroutines.CoroutineScope;

public class MessageViewModel extends ViewModel {
    private MessageRepository messageRepository;
    CoroutineScope viewModelScope;
    Pager<Integer, Message> pager;

    public MessageViewModel() {
        messageRepository =  MessageRepository.getInstance();
//        PagingSource<Integer, Message> messageLiveData;
//        messageLiveData = messageRepository.getChattingRecords();
        viewModelScope = ViewModelKt.getViewModelScope(this);
        pager = new Pager<>(new PagingConfig(50),() -> messageRepository.getChattingRecords());
    }

    public LiveData<PagingData<Message>> getChattingRecords(){
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }
}
