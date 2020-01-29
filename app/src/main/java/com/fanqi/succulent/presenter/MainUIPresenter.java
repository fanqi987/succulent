package com.fanqi.succulent.presenter;

import com.fanqi.succulent.databinding.MainAcBinding;

public class MainUIPresenter {

    private MainAcBinding mBinding;
    private NavigationPresenter mNavigationPresenter;

    public MainUIPresenter(MainAcBinding binding) {
        this.mBinding = binding;
    }

    public void initUI() {


    }

    public void setNavigationPresenter(NavigationPresenter navigationPresenter) {
        this.mNavigationPresenter = navigationPresenter;
    }
}
