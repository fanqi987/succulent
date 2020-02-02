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
import com.fanqi.succulent.databinding.MainAcBinding;
import com.fanqi.succulent.databinding.SucculentListFragmentBinding;
import com.fanqi.succulent.viewmodel.SucculentListViewModel;

public class SucculentListFragment extends BaseFragment {

    private SucculentListFragmentBinding mBinding;
    private SucculentListViewModel model;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.succulent_list_fragment,
                container, false);
        if(mView==null){
            mView = mBinding.getRoot();
        }
        Log.e("list fragment", "onCreateView");

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("list fragment", "onViewCreated");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new SucculentListViewModel();
        mBinding.setModel(model);
        model.setBroccoli(mBroccoli);
        model.setFragment(this);
        model.setBinding(mBinding);
        model.initView();
        Log.e("list fragment", "onActivityCreated");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("list fragment", "onDestroyView");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.e("list fragment", "onSaveInstanceState");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.e("list fragment", "onViewStateRestored");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("list fragment", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("list fragment", "onDestroy");
    }
}
