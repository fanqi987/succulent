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
        if (mView == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.succulent_list_fragment,
                    container, false);
            mView = mBinding.getRoot();
            model.setBinding(mBinding);
            mBinding.setModel(model);
        }
        Log.e("list fragment", "onCreateView");
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new SucculentListViewModel();
        model.setBroccoli(mBroccoli);
        model.setFragment(this);
        model.initViewPagerAdapter();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.initView();

        Log.e("list fragment", "onActivityCreated");
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getArguments();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("list fragment", "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        model.notifyDataChanged();
        Log.e("list fragment", "onDestroyView");
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
