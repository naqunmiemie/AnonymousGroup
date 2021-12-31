package com.yjn.anonymousgroup.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tcl.common.util.L;
import com.yjn.anonymousgroup.adapter.MessageAdapter;
import com.yjn.anonymousgroup.base.BaseActivity;
import com.yjn.anonymousgroup.databinding.ActivityMainBinding;
import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.udp.CanChatUdpSend;
import com.yjn.anonymousgroup.udp.Udp;
import com.yjn.anonymousgroup.viewmodel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private MessageViewModel messageViewModel;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        
        initViewModel();
        initView();
        initData();


//        new Thread(new CanChatUdpSend("huawei",Udp.getIpToAll(),Udp.PORT_ALL)).start();
    }


    private void initViewModel() {
        messageViewModel = getApplicationScopeViewModel(MessageViewModel.class);
        messageViewModel.udpReceiver();
    }

    private void initView() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = binding.etInputBox.getText().toString();
                if (!TextUtils.isEmpty(inputText)){
                    binding.etInputBox.getText().clear();
                    new Thread(new CanChatUdpSend(inputText, Udp.getIpToAll(),Udp.PORT_ALL)).start();
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvChatFrame.setLayoutManager(layoutManager);
        messageAdapter = new MessageAdapter(new ArrayList<Message>());
        binding.rvChatFrame.setAdapter(messageAdapter);
    }

    private void initData() {
        messageViewModel.getChattingRecords().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                if (messages != null){
                    L.d("messageViewModel has changed,messages size:"+messages.size());
                    messageAdapter.messages = messages;
                    messageAdapter.refresh();
                }
            }
        });
    }
}