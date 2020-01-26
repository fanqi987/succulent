package com.fanqi.succulent.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fanqi.succulent.R;

public class SucculentDailyFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        LayoutInflater.from(this, R.layout.)
        View v = inflater.inflate(R.layout.succulent_daily_fragment, container, false);
        return v;


    }
}
