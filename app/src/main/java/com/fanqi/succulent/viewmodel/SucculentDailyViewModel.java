package com.fanqi.succulent.viewmodel;

import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.databinding.SucculentDailyFragmentBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.bean.SucculentDailyViewBean;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.util.ArrayList;
import java.util.List;

public class SucculentDailyViewModel extends BaseViewModel
        implements ViewModelCallback, SwipeRefreshLayout.OnRefreshListener {

    private SucculentDailyFragmentBinding mBinding;

    private NetworkUtil mNetworkUtil;

    public SucculentDailyViewBean mBean;

    private List<String> mBitmapUrls;
    private boolean mShowedBitmap;

    private int mDailyItemNumber;


    public SucculentDailyViewModel() {
        mBean = new SucculentDailyViewBean();
        mNetworkUtil = new NetworkUtil();
        mBitmapUrls = new ArrayList<>();
    }

    public void setBinding(SucculentDailyFragmentBinding binding) {
        mBinding = binding;
    }

    public void initView() {
        mShowedBitmap = false;
        mBitmapUrls = new ArrayList<>();

        // 显示占拉符页面,再初始化页面
        addPlaceHolders();


        //设置好本地数据
        mDailyItemNumber = LocalDataUtil.getDailyItem();
        Succulent succulent = LocalDataUtil.getSucculents().get(mDailyItemNumber);

        int familyId = succulent.getFamily_id();
        mBean.setFamilyName(LocalDataUtil.findFamily(familyId).getName());
        mBroccoli.removePlaceholder(mBinding.dailyItemFamilyCardview);

        int generaId = succulent.getGenera_id();
        mBean.setGeneraName(LocalDataUtil.findGenera(generaId).getName());
        mBroccoli.removePlaceholder(mBinding.dailyItemGenusCardview);

        mBean.setName(succulent.getName());
        mBroccoli.removePlaceholder(mBinding.dailyItemNameCardview);

        mBean.setLight(String.valueOf(succulent.getLight()));
        mBroccoli.removePlaceholder(mBinding.dailyItemLightCardview);

        mBean.setWater(String.valueOf(succulent.getWater()));
        mBroccoli.removePlaceholder(mBinding.dailyItemWaterCardview);

        //设置好网络数据
        mNetworkUtil.setViewModelCallback(this);
        String pageName = succulent.getPage_name();
        mNetworkUtil.requestGetMediaInfo(pageName);
    }

    private void addPlaceHolders() {
        mBroccoli.addPlaceholders(
                mBinding.dailyItemImageCardview,
                mBinding.dailyItemFamilyCardview,
                mBinding.dailyItemGenusCardview,
                mBinding.dailyItemNameCardview,
                mBinding.dailyItemIntroCardview,
                mBinding.dailyItemLightCardview,
                mBinding.dailyItemWaterCardview);
        mBroccoli.show();
    }

    @Override
    public void onSuccessed(Bundle bundle) {
        if (bundle.getString(Constant.ViewModel.SUMMARY) != null) {
            mBean.setSummary(bundle.getString(Constant.ViewModel.SUMMARY));
            mBroccoli.removePlaceholder(mBinding.dailyItemIntroCardview);
        }
        if (bundle.get(Constant.ViewModel.IMAGE) != null) {
            mBitmapUrls.add(bundle.getString(Constant.ViewModel.IMAGE));
            if (!mShowedBitmap && mBitmapUrls.size() > 0) {
                Glide.with(mFragment).load(mBitmapUrls.get(0)).into(mBinding.dailyItemImage);
                mBroccoli.removePlaceholder(mBinding.dailyItemImageCardview);
                mShowedBitmap = true;
                if (mMainAcBinding.swipeRefreshLayout.isRefreshing()) {
                    mMainAcBinding.swipeRefreshLayout.setRefreshing(false);
                }
                mMainAcBinding.swipeRefreshLayout.setEnabled(true);
            }
        }
    }

    @Override
    public void onFailed(Throwable e) {
        //todo 提示获取数据失败，提示刷新
        //todo 再增加一个主动刷新按钮接口，点击后刷新，可以提示在下面点击图片刷新
    }

    @Override
    public void onRefresh() {
        initView();
    }

}
