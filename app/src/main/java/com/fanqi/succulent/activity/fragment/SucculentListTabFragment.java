package com.fanqi.succulent.activity.fragment;

import android.os.Bundle;
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
    private Family mFamily;

    public SucculentListTabFragment(Family family) {
        super();
        this.mFamily=family;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.succulent_list_tab_fragment,
                container, false);
        model = new SucculentListTabViewModel(mFamily);
        mBinding.setModel(model);
        model.setBroccoli(mBroccoli);
        model.setFragment(this);
        model.setBinding(mBinding);
        View v = mBinding.getRoot();
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo 显示占拉符页面,再初始化页面
        model.initView();
    }
}
