package com.yjn.anonymousgroup.activity;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.hjq.toast.ToastUtils;
import com.tcl.common.util.L;
import com.yjn.anonymousgroup.adapter.MessageAdapter;
import com.yjn.anonymousgroup.adapter.comparator.MessageComparator;
import com.yjn.anonymousgroup.base.BaseActivity;
import com.yjn.anonymousgroup.databinding.ActivityMainBinding;
import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.service.ReceiverService;
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


        startService();
        initViewModel();
        initView();
        initData();
        initOther();
    }


    private void initBar() {
        binding.includeBar.ivSubMenu.setVisibility(View.VISIBLE);
        binding.includeBar.tvTitle.setVisibility(View.VISIBLE);
        binding.includeBar.tvTitle2.setVisibility(View.VISIBLE);
        binding.includeBar.ivSubMenu.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SettingActivity.class)));

        if (NetworkUtils.isWifiConnected()){
            binding.includeBar.tvTitle.setText("深海群聊");
            binding.includeBar.tvTitle2.setText("在线人数：");
        }

    }


    private void startService() {
        if (!ServiceUtils.isServiceRunning(ReceiverService.class)){
            startForegroundService(new Intent(this, ReceiverService.class));
        }
    }


    private void initViewModel() {
        messageViewModel = getApplicationScopeViewModel(MessageViewModel.class);
    }

    private void initView() {
        initBar();
        binding.btnSend.setOnClickListener(v -> {
            String inputText = binding.etInputBox.getText().toString();
            if (!TextUtils.isEmpty(inputText)){
                binding.etInputBox.getText().clear();
                checkWifi();
                new Thread(new CanChatUdpSend(inputText, Udp.getIpToAll(),Udp.PORT_ALL)).start();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvChatFrame.setLayoutManager(layoutManager);
        messageAdapter = new MessageAdapter(new MessageComparator());
        binding.rvChatFrame.setAdapter(messageAdapter);
    }

    private void initData() {
        messageViewModel.getChattingRecords().observe(this,messagePagingData -> {
            messageAdapter.submitData(getLifecycle(),messagePagingData);
        });
    }

    private void initOther() {
        checkWifi();
    }

    private void checkWifi() {
        if (!NetworkUtils.isWifiConnected()){
            ToastUtils.show("未连接WIFI");
        }
    }

}