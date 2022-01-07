package com.yjn.anonymousgroup.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.hjq.toast.ToastUtils;
import com.yjn.anonymousgroup.R;
import com.yjn.anonymousgroup.base.BaseActivity;
import com.yjn.anonymousgroup.databinding.ActivitySettingBinding;
import com.yjn.anonymousgroup.model.Message;
import com.yjn.anonymousgroup.repository.MessageRepository;
import com.yjn.anonymousgroup.udp.CanChatUdpSend;
import com.yjn.anonymousgroup.udp.Udp;

public class SettingActivity extends BaseActivity {

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        initView();
    }

    private void initView() {
        initBar();
        binding.tvVersionName.setText("version:"+ AppUtils.getAppVersionName());
        binding.btnClearChatRecord.setOnClickListener(v -> {

            ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
                @Override
                public Void doInBackground() throws Throwable {
                    MessageRepository.getInstance().deleteAll();
                    return null;
                }

                @Override
                public void onSuccess(Void result) {
                    ToastUtils.show("success");
                }
            });

        });
        binding.btnInsertSomeChatRecord.setOnClickListener(v -> {
            ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
                @Override
                public Void doInBackground() throws Throwable {
                    Message tmp = new Message();
                    for (int i = 0; i < 20; i++) {
                        tmp.setMessage("少量测试数据:"+ i);
                        MessageRepository.getInstance().insertMessage(tmp);
                    }
                    return null;
                }

                @Override
                public void onSuccess(Void result) {
                    ToastUtils.show("success");
                }
            });

        });
        binding.btnInsertChatRecord.setOnClickListener(v -> {

            ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
                @Override
                public Void doInBackground() throws Throwable {
                    Message tmp = new Message();
                    for (int i = 0; i < 2000; i++) {
                        tmp.setMessage("生成聊天记录:"+ i);
                        MessageRepository.getInstance().insertMessage(tmp);
                    }
                    return null;
                }

                @Override
                public void onSuccess(Void result) {
                    ToastUtils.show("success");
                }
            });

        });
        binding.btnSendMessage.setOnClickListener(v -> {
            ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Void>() {
                @Override
                public Void doInBackground() throws Throwable {
                    for (int i = 0; i < 200; i++) {
                        new CanChatUdpSend("发送聊天记录:"+ i, Udp.getIpToAll(),Udp.PORT_ALL).run();
                    }
                    return null;
                }

                @Override
                public void onSuccess(Void result) {
                    ToastUtils.show("success");
                }
            });
        });
        binding.btnInsertCoins.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alipays://platformapi/startapp?saId=10000007&qrcode=https%3A%2F%2Fqr.alipay.com%2Ffkx112228skh6472vtimg11"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                ToastUtils.show("打开支付宝失败，你可能还没有安装支付宝客户端");
            }
        });
    }
    private void initBar() {
        binding.includeBar.ivBack.setVisibility(View.VISIBLE);
        binding.includeBar.tvTitle.setVisibility(View.VISIBLE);
        binding.includeBar.ivBack.setOnClickListener(v -> onBackPressed());
        binding.includeBar.tvTitle.setText("设置");

    }
}