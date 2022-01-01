package com.yjn.anonymousgroup.activity;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.yjn.anonymousgroup.R;
import com.yjn.anonymousgroup.base.BaseActivity;
import com.yjn.anonymousgroup.databinding.ActivitySettingBinding;

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

        });
        binding.btnInsertChatRecord.setOnClickListener(v -> {

        });
        binding.btnSendMessage.setOnClickListener(v -> {

        });
        binding.btnInsertCoins.setOnClickListener(v -> {

        });
    }
    private void initBar() {
        binding.includeBar.ivBack.setVisibility(View.VISIBLE);
        binding.includeBar.tvTitle.setVisibility(View.VISIBLE);

        binding.includeBar.ivBack.setOnClickListener(v -> onBackPressed());

        binding.includeBar.tvTitle.setText("设置");

    }
}