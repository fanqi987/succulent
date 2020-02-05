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

    private static final int START_TIME = 5000;

    private boolean mStartFlag = false;

    public GuideViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GuideAcBinding binding = DataBindingUtil.setContentView(this, R.layout.guide_ac);
        model = new GuideViewModel(this);
        binding.setModel(model);
        start();
    }

    private void start() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int time = START_TIME; time > 0; time = time - 1000) {
                    model.setGuideEnterText("点击进入  " + "(" + time / 1000 + ")");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                onEnterHome();
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public synchronized void onEnterHome() {
        if (!mStartFlag) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            mStartFlag = true;
        }
    }
}
