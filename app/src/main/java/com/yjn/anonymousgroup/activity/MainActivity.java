package com.yjn.anonymousgroup.activity;

import static com.yjn.anonymousgroup.util.Net.checkWifi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.tcl.common.util.L;
import com.yjn.anonymousgroup.adapter.MessageAdapter;
import com.yjn.anonymousgroup.adapter.comparator.MessageComparator;
import com.yjn.anonymousgroup.base.BaseActivity;
import com.yjn.anonymousgroup.databinding.ActivityMainBinding;
import com.yjn.anonymousgroup.service.ReceiverService;
import com.yjn.anonymousgroup.udp.CanChatUdpSend;
import com.yjn.anonymousgroup.udp.Udp;
import com.yjn.anonymousgroup.viewmodel.MessageViewModel;

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
            ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
                @Override
                public Void doInBackground() throws Throwable {
                    while (true){
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.includeBar.tvTitle2.setText("在线人数："+Udp.peopleSet.size());
                            }
                        });
                        Thread.sleep(500);
                        Udp.peopleSet.clear();
                        Thread.sleep(500);
                    }
                }

                @Override
                public void onSuccess(Void result) {

                }
            });
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
        binding.srlChatFrame.setOnRefreshListener(() -> {
            L.d("下拉刷新");
            binding.srlChatFrame.setRefreshing(false);
        });
        binding.btnSend.setOnClickListener(v -> {
            String inputText = binding.etInputBox.getText().toString();
            if (!TextUtils.isEmpty(inputText)){
                binding.etInputBox.getText().clear();
                if (checkWifi()){
                    ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
                        @Override
                        public Void doInBackground() throws Throwable {
                            new CanChatUdpSend(inputText, Udp.getIpToAll(),Udp.PORT_ALL).run();
                            return null;
                        }

                        @Override
                        public void onSuccess(Void result) {

                        }
                    });
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        layoutManager.setStackFromEnd(true);
        binding.rvChatFrame.setLayoutManager(layoutManager);
        messageAdapter = new MessageAdapter(new MessageComparator());
        binding.rvChatFrame.setAdapter(messageAdapter);

    }

    private void initData() {
        messageViewModel.getChattingRecords().observe(this,messagePagingData -> {
            messageAdapter.submitData(getLifecycle(),messagePagingData);

//            if (isVisBottom(binding.rvChatFrame)){
//                L.d("messageAdapter.getItemCount()"+messageAdapter.getItemCount());
//                messageAdapter.submitData(getLifecycle(),messagePagingData);
//                binding.rvChatFrame.scrollToPosition(messageAdapter.getItemCount());
//                L.d("messageAdapter.getItemCount()"+messageAdapter.getItemCount());
//                L.d("isVisBottom");
//            }else {
//                messageAdapter.submitData(getLifecycle(),messagePagingData);
//
//                L.d("isNotVisBottom");
//            }


        });
    }

    public static boolean isVisBottom(RecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }

    private void initOther() {
        checkWifi();
    }



}