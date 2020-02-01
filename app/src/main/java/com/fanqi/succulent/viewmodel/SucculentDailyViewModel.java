package com.fanqi.succulent.viewmodel;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.SucculentFull;
import com.fanqi.succulent.databinding.SucculentDailyFragmentBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.bean.SucculentDailyViewBean;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class SucculentDailyViewModel extends BaseViewModel
        implements ViewModelCallback, SwipeRefreshLayout.OnRefreshListener {

    private SucculentDailyFragmentBinding mBinding;

    private NetworkUtil mNetworkUtil;

    public SucculentDailyViewBean mBean;

    private SucculentFull mSucculentFull;
    private boolean mShowedBitmap;

    private int mDailyItemNumber;


    public SucculentDailyViewModel() {
        mBean = new SucculentDailyViewBean();
        mNetworkUtil = new NetworkUtil();
        mSucculentFull = new SucculentFull();
    }

    public void setBinding(SucculentDailyFragmentBinding binding) {
        mBinding = binding;
    }

    public void initView() {
        mShowedBitmap = false;
        // 显示占拉符页面,再初始化页面
        addPlaceHolders();


        //设置好本地数据
        //获取今日植物序号
        mDailyItemNumber = LocalDataUtil.getDailyItem();
        //获取今日植物
        Succulent succulent = LocalDataUtil.getSucculents().get(mDailyItemNumber);

        mBean.setName(succulent.getName());

        mMainAcBinding.appbarLayout.setExpanded(false);
        mMainAcBinding.collapsingToolbarLayout.setTitle(succulent.getName());

        mBroccoli.removePlaceholder(mBinding.dailyItemNameCardview);

        mBean.setLight(String.valueOf(succulent.getLight()));
        mBroccoli.removePlaceholder(mBinding.dailyItemLightCardview);

        mBean.setWater(String.valueOf(succulent.getWater()));
        mBroccoli.removePlaceholder(mBinding.dailyItemWaterCardview);

//        //todo 即将放入网络数据
//        int familyId = succulent.getFamily_id();
//        mBean.setFamilyName(LocalDataUtil.findFamily(familyId).getName());
//        mBroccoli.removePlaceholder(mBinding.dailyItemFamilyCardview);
//
//        //todo 即将放入网络数据
//        int generaId = succulent.getGenera_id();
//        mBean.setGeneraName(LocalDataUtil.findGenera(generaId).getName());
//        mBroccoli.removePlaceholder(mBinding.dailyItemGenusCardview);

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
        if (bundle.getSerializable(Constant.ViewModel.TEXTS) != null) {
            mSucculentFull = (SucculentFull) bundle.getSerializable(Constant.ViewModel.TEXTS);
            mBean.setSummary(mSucculentFull.getInfos().get(0)[1]);
            mBroccoli.removePlaceholder(mBinding.dailyItemIntroCardview);
            mBean.setFamilyName(mSucculentFull.getFamilyName());
            mBroccoli.removePlaceholder(mBinding.dailyItemFamilyCardview);
            mBean.setGeneraName(mSucculentFull.getGeneraName());
            mBroccoli.removePlaceholder(mBinding.dailyItemGenusCardview);
        }
        if (bundle.get(Constant.ViewModel.IMAGE) != null) {
            if (mSucculentFull.getImageUrls() == null) {
                mSucculentFull.setImageUrl(new ArrayList<>());
            }
            List<String> bitmapUrls = mSucculentFull.getImageUrls();
            bitmapUrls.add(bundle.getString(Constant.ViewModel.IMAGE));
            if (!mShowedBitmap && bitmapUrls.size() > 0) {
                mFragment.getActivity().runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(mFragment.getActivity()).load(bitmapUrls.get(0))
                                        .into(mBinding.dailyItemImage);
                            }
                        }
                );
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
        mFragment.getActivity().runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mFragment.getContext(), "加载失败，请再次刷新~",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    @Override
    public void onRefresh() {
        initView();
    }

}
