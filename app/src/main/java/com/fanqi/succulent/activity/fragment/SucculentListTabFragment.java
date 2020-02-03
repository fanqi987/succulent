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
import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.databinding.SucculentListTabFragmentBinding;
import com.fanqi.succulent.viewmodel.SucculentListTabViewModel;

public class SucculentListTabFragment extends BaseFragment {
    private SucculentListTabFragmentBinding mBinding;
    private SucculentListTabViewModel model;
    public Family mFamily;
    private View mView;

    public SucculentListTabFragment(Family family) {
        super();
        this.mFamily = family;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.succulent_list_tab_fragment,
                container, false);
        if (mView == null) {
            mView = mBinding.getRoot();
        }
        Log.e("tab fragment", "onCreateView" + mFamily.getName());
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("tab fragment", "onViewCreated" + mFamily.getName());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new SucculentListTabViewModel(mFamily);
        mBinding.setModel(model);
        model.setBroccoli(mBroccoli);
        model.setFragment(this);
        model.setBinding(mBinding);
        //todo 显示占拉符页面,再初始化页面
        model.initView();

        Log.e("tab fragment", "onActivityCreated" + mFamily.getName());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("tab fragment", "onStart" + mFamily.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("tab fragment", "onResume" + mFamily.getName());

    }

    @Override
    public void onPause() {
        Log.e("tab fragment", "onPause" + mFamily.getName());
        super.onPause();
        getArguments();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("tab fragment", "onDestroyView" + mFamily.getName());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("tab fragment", "onStop" + mFamily.getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("tab fragment", "onDestroy" + mFamily.getName());
    }
}
