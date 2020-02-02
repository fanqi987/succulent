package com.fanqi.succulent.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.fanqi.succulent.R;
import com.fanqi.succulent.activity.MainActivity;
import com.fanqi.succulent.databinding.SucculentItemDetailLayoutBinding;
import com.fanqi.succulent.presenter.NavigationPresenter;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.SucculentViewModel;

public class SucculentFragment extends BaseFragment {

    private SucculentItemDetailLayoutBinding mBinding;
    private SucculentViewModel model;
    private NavigationPresenter mNavigationPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.succulent_item_detail_layout, container, false);
        model = new SucculentViewModel();
        model.setData(getArguments().getSerializable(Constant.ViewModel.SUCCULENT));
        model.setBroccoli(mBroccoli);
        model.setFragment(this);
        model.setBinding(mBinding);
        mBinding.setModel(model);
        View v = mBinding.getRoot();

        mNavigationPresenter = ((MainActivity) this.getActivity()).getNavigationPresenter();
        //todo 隐藏底部栏
        mNavigationPresenter.hiddenBottomNavigation();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo 显示占拉符页面,再初始化页面
        model.initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //todo 显示底部栏
        mNavigationPresenter.showBottomNavigation();
    }
}
