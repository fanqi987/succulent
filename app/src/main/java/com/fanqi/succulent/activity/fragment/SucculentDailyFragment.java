package com.fanqi.succulent.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.fanqi.succulent.R;
import com.fanqi.succulent.databinding.SucculentDailyFragmentBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.viewmodel.SucculentDailyViewModel;

public class SucculentDailyFragment extends BaseFragment {

    private SucculentDailyViewModel model;
    private SucculentDailyFragmentBinding mBinding;
    private int mDailyItemNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        LayoutInflater.from(this, R.layout.)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.succulent_daily_fragment,
                container, false);
        model = new SucculentDailyViewModel(mBinding, this);
        mBinding.setModel(model);
        View v = mBinding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model.initView(mBroccoli);
        //切换到每日推荐的fragment
    }
}
