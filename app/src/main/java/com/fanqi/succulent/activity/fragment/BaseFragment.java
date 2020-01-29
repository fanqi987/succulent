package com.fanqi.succulent.activity.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.samlss.broccoli.Broccoli;

public class BaseFragment extends Fragment {

    protected Broccoli mBroccoli;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBroccoli = new Broccoli();
    }
}
