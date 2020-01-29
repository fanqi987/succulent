package com.fanqi.succulent.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.fanqi.succulent.activity.MainActivity;
import com.fanqi.succulent.activity.fragment.SucculentDailyFragment;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.databinding.MainAcBinding;
import com.fanqi.succulent.databinding.SucculentDailyFragmentBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.viewmodel.bean.SucculentDailyViewBean;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.samlss.broccoli.Broccoli;

public class SucculentDailyViewModel implements ViewModelCallback, SwipeRefreshLayout.OnRefreshListener {

    public static final String IMAGE = "image";
    public static final String SUMMARY = "summary";
    public static final String INFO_TITLE = "info_title";
    public static final String INFO_CONTENT = "info_content";

    private Fragment mFragment;

    private int mDailyItemNumber;

    private MainAcBinding mMainBinding;
    private SucculentDailyFragmentBinding mBinding;

    private NetworkUtil mNetworkUtil;

    public SucculentDailyViewBean mBean;

    private List<String> mBitmapUrls;
    private boolean mShowedBitmap;
    private Broccoli mBroccoli;


    public SucculentDailyViewModel(SucculentDailyFragmentBinding fragmentBinding,
                                   SucculentDailyFragment succulentDailyFragment) {
        mBean = new SucculentDailyViewBean();
        mBinding = fragmentBinding;
        mFragment = succulentDailyFragment;
        mNetworkUtil = new NetworkUtil();
        mBitmapUrls = new ArrayList<>();
        mMainBinding = ((MainActivity) mFragment.getActivity()).getBinding();
        mMainBinding.swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void initView(Broccoli broccoli) {
        mShowedBitmap = false;
        mBitmapUrls = new ArrayList<>();

        mMainBinding.swipeRefreshLayout.setEnabled(false);
        mBroccoli = broccoli;
        // 显示占拉符页面,再初始化页面
        mBroccoli.addPlaceholders(
                mBinding.dailyItemImageCardview,
                mBinding.dailyItemFamilyCardview,
                mBinding.dailyItemGenusCardview,
                mBinding.dailyItemNameCardview,
                mBinding.dailyItemIntroCardview,
                mBinding.dailyItemLightCardview,
                mBinding.dailyItemWaterCardview);
        broccoli.show();

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

    @Override
    public void onSuccessed(Bundle bundle) {
        if (bundle.getString(SUMMARY) != null) {
            mBean.setSummary(bundle.getString(SUMMARY));
            mBroccoli.removePlaceholder(mBinding.dailyItemIntroCardview);
        }
        if (bundle.get(IMAGE) != null) {
            mBitmapUrls.add(bundle.getString(IMAGE));
            if (!mShowedBitmap && mBitmapUrls.size() > 0) {
                Glide.with(mFragment).load(mBitmapUrls.get(0)).into(mBinding.dailyItemImage);
                mBroccoli.removePlaceholder(mBinding.dailyItemImageCardview);
                mShowedBitmap = true;
                if (mMainBinding.swipeRefreshLayout.isRefreshing()) {
                    mMainBinding.swipeRefreshLayout.setRefreshing(false);
                }
                mMainBinding.swipeRefreshLayout.setEnabled(true);
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
        initView(mBroccoli);
    }
}
