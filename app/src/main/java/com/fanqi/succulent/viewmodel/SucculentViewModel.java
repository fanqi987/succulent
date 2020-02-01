package com.fanqi.succulent.viewmodel;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.databinding.SucculentItemDetailLayoutBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.bean.SucculentViewBean;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 具体信息页面的model
 */
public class SucculentViewModel extends BaseViewModel implements ViewModelCallback {

    private SucculentItemDetailLayoutBinding mBinding;

    private NetworkUtil mNetworkUtil;

    public SucculentViewBean mBean;

    private List<String> mBitmapUrls;
    private boolean mShowedBitmap;

    private Succulent mSucculent;

    public SucculentViewModel() {
        mBean = new SucculentViewBean();
        mNetworkUtil = new NetworkUtil();
        mBitmapUrls = new ArrayList<>();
    }

    public void setBinding(SucculentItemDetailLayoutBinding binding) {
        mBinding = binding;
    }

    public void setData(Serializable serializable) {
        mSucculent = (Succulent) serializable;
    }

    public void initView() {
        mShowedBitmap = false;
        mBitmapUrls = new ArrayList<>();

        // 显示占拉符页面,再初始化页面
        addPlaceHolders();

        //设置好本地数据

        int familyId = mSucculent.getFamily_id();
        mBean.setFamilyName(LocalDataUtil.findFamily(familyId).getName());
        mBroccoli.removePlaceholder(mBinding.itemDetailFamilyCardview);

        int generaId = mSucculent.getGenera_id();
        mBean.setGeneraName(LocalDataUtil.findGenera(generaId).getName());
        mBroccoli.removePlaceholder(mBinding.itemDetailGenusCardview);

        mBean.setName(mSucculent.getName());
        mBroccoli.removePlaceholder(mBinding.itemDetailNameCardview);

        mBean.setLight(String.valueOf(mSucculent.getLight()));
        mBroccoli.removePlaceholder(mBinding.itemDetailLightCardview);

        mBean.setWater(String.valueOf(mSucculent.getWater()));
        mBroccoli.removePlaceholder(mBinding.itemDetailWaterCardview);

        //设置好网络数据
        mNetworkUtil.setViewModelCallback(this);
        String pageName = mSucculent.getPage_name();
        mNetworkUtil.requestGetMediaInfo(pageName);
    }

    private void addPlaceHolders() {
        //改变的是toolbar的image
        mBroccoli.addPlaceholders(
                mMainAcBinding.toolbarImage,
                mBinding.itemDetailFamilyCardview,
                mBinding.itemDetailGenusCardview,
                mBinding.itemDetailNameCardview,
                mBinding.itemDetailIntroCardview,
                mBinding.itemDetailLightCardview,
                mBinding.itemDetailWaterCardview);
        mBroccoli.show();
    }


    @Override
    public void onSuccessed(Bundle bundle) {
        if (bundle.getString(Constant.ViewModel.TEXTS) != null) {
            mBean.setSummary(bundle.getString(Constant.ViewModel.TEXTS));
            mBroccoli.removePlaceholder(mBinding.itemDetailIntroCardview);
        }
        if (bundle.get(Constant.ViewModel.IMAGE) != null) {
            mBitmapUrls.add(bundle.getString(Constant.ViewModel.IMAGE));
            if (!mShowedBitmap && mBitmapUrls.size() > 0) {
                Glide.with(mFragment).load(mBitmapUrls.get(0)).into(mMainAcBinding.toolbarImage);
                mBroccoli.removePlaceholder(mMainAcBinding.toolbarImage);
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
