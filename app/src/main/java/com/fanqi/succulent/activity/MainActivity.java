package com.fanqi.succulent.activity;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.fanqi.succulent.R;
import com.fanqi.succulent.databinding.MainAcBinding;
import com.fanqi.succulent.presenter.InitializeDataPresenter;
import com.fanqi.succulent.presenter.ProgressUIPresenter;
import com.fanqi.succulent.presenter.listener.CallbackPresenter;
import com.fanqi.succulent.presenter.MainActivityCallbackPresenter;
import com.fanqi.succulent.presenter.NavigationPresenter;
import com.fanqi.succulent.util.PreferenceUtil;
import com.fanqi.succulent.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {

    private MainViewModel model;
    private NavigationPresenter mNavigationPresenter;
    private ProgressUIPresenter mProgressUIPresenter;
    private CallbackPresenter mCallbackPresenter;
    private InitializeDataPresenter mInitializeDataPresenter;
    private MainAcBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定view model
        mBinding = DataBindingUtil.setContentView(this, R.layout.main_ac);
        mBinding.setModel(model);
        //初始化本地存储工具
        PreferenceUtil.init(this);
        // 主活动回调代理者
        mCallbackPresenter = new MainActivityCallbackPresenter(this, mBinding);
        // 导航组件代理者
        mNavigationPresenter = new NavigationPresenter(this, mBinding, mCallbackPresenter);
        // 为导航组件切换事件添加回调
        mCallbackPresenter.setPresenterCallback(mNavigationPresenter);
        // 获取初始化数据代理者
        mInitializeDataPresenter = InitializeDataPresenter.getInstance();
        //判断是否第1次进入,标志在sharedPreference工具获取
        if (PreferenceUtil.getFirstEnterFlag()) {
            //创建后台进度提示进度框相关UI的代理者
            mProgressUIPresenter = new ProgressUIPresenter(this, mNavigationPresenter);
            mProgressUIPresenter.showProgressDialog("首次加载中，稍等下哦~");
            //调用初始化数据代理者,从网络获取
            mInitializeDataPresenter.setProgressBarListener(mProgressUIPresenter);
            mInitializeDataPresenter.initFirstData(this);
        } else {
            //初始化页面
            mNavigationPresenter.firstNavigate();
            //生成今日植物
            //爬虫今日植物的所有数据
            //显示今日植物的数据
        }
    }

    public MainAcBinding getBinding() {
        return mBinding;
    }

    public NavigationPresenter getNavigationPresenter() {
        return mNavigationPresenter;
    }


}
