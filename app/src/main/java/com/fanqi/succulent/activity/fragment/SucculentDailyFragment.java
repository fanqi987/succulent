package com.fanqi.succulent.activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.fanqi.succulent.R;
import com.fanqi.succulent.databinding.SucculentDailyFragmentBinding;
import com.fanqi.succulent.viewmodel.SucculentDailyViewModel;

import java.io.Serializable;

public class SucculentDailyFragment extends BaseFragment {

    private SucculentDailyViewModel model;
    private SucculentDailyFragmentBinding mBinding;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.succulent_daily_fragment,
                container, false);
        if (mView == null) {
            mView = mBinding.getRoot();
        }
        Log.e("daily fragment", "onCreateView");
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new SucculentDailyViewModel();
        mBinding.setModel(model);
        model.setBroccoli(mBroccoli);
        model.setFragment(this);
        model.setBinding(mBinding);
        Serializable savedInstance = mApplicationDataPresenter.getDailyFragmentData();
        if (savedInstance == null) {
            model.initView();
        } else {
            model.initViewByCache(savedInstance);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.e("daily fragment", "onDestroyView");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.e("daily fragment", "onViewCreated");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        Log.e("daily fragment", "onSaveInstanceState");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
//        Log.e("daily fragment", "onViewStateRestored");
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.e("daily fragment", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.e("daily fragment", "onDestroy");
    }




}
