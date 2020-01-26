package com.fanqi.succulent.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.fanqi.succulent.R;
import com.fanqi.succulent.databinding.GuideAcBinding;
import com.fanqi.succulent.viewmodel.GuideViewModel;
import com.fanqi.succulent.viewmodel.listener.GuideListener;

public class GuideActivity extends BaseActivity implements GuideListener {

    private GuideViewModel mGuideViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GuideAcBinding binding = DataBindingUtil.setContentView(this, R.layout.guide_ac);
        mGuideViewModel = new GuideViewModel(this);
        binding.setGuideModel(mGuideViewModel);
    }

    @Override
    public void onEnterHome() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
