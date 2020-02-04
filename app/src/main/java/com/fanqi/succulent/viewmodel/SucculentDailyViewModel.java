package com.fanqi.succulent.viewmodel;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.fanqi.succulent.R;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.SucculentFull;
import com.fanqi.succulent.databinding.SucculentDailyFragmentBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.bean.SucculentDailyViewBean;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SucculentDailyViewModel extends BaseViewModel
        implements ViewModelCallback, SwipeRefreshLayout.OnRefreshListener {

    private static final int DICE_FREQUENCE = 200;
    private static final int DICE_REDIRECT_TIME = 5000;

    private SucculentDailyFragmentBinding mBinding;

    private NetworkUtil mNetworkUtil;

    public SucculentDailyViewBean mBean;

    private SucculentFull mSucculentFull;
    private boolean mShowedBitmap;

    private int mDailyItemNumber;

    private boolean mDiceStopFlag;


    public SucculentDailyViewModel() {
        mBean = new SucculentDailyViewBean();
        mNetworkUtil = new NetworkUtil();
        mSucculentFull = new SucculentFull();
        mDiceStopFlag = true;
    }

    public void setBinding(SucculentDailyFragmentBinding binding) {
        mBinding = binding;
    }

    public void initView() {
        //骰子导航控制
        mNavigationPresenter.dailyViewNav(mFragment.getResources().getString(R.string.drawer_daily));
        //设置骰子页面
        if (!LocalDataUtil.getDailyIsShowed()) {
            mBinding.luckyPlantView.setVisibility(View.VISIBLE);
            mBean.setLuckySucculentBtnShowing(mFragment.getResources().getString(R.string.dice_btn_ready));
            return;
        } else {
            mBinding.luckyPlantView.setVisibility(View.GONE);
        }
        mShowedBitmap = false;
        // 显示占拉符页面,再初始化页面
        addPlaceHolders();
        //设置好本地数据
        //获取今日植物序号
        mDailyItemNumber = LocalDataUtil.getDailyItem();
        //获取今日植物
        Succulent succulent = LocalDataUtil.getSucculents().get(mDailyItemNumber);
        mSucculentFull = new SucculentFull(succulent);
        mBean.setName(mSucculentFull.getName());
        //骰子结束后的导航控制
        mNavigationPresenter.dailyViewNav(mSucculentFull.getName());
        mBroccoli.removePlaceholder(mBinding.dailyItemNameCardview);

        mBean.setLight(String.valueOf(mSucculentFull.getLight()));
        mBroccoli.removePlaceholder(mBinding.dailyItemLightCardview);

        mBean.setWater(String.valueOf(mSucculentFull.getWater()));
        mBroccoli.removePlaceholder(mBinding.dailyItemWaterCardview);

        //设置好网络数据
        mNetworkUtil.setViewModelCallback(this);
        String pageName = mSucculentFull.getPage_name();
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

    public void initViewByCache(Serializable savedInstanceState) {
        mBinding.luckyPlantView.setVisibility(View.GONE);
        mSucculentFull = (SucculentFull) savedInstanceState;
        mShowedBitmap = true;
        mBean.setName(mSucculentFull.getName());
        //导航控制
        mNavigationPresenter.dailyViewNav(mSucculentFull.getName());
        mBean.setLight(String.valueOf(mSucculentFull.getLight()));
        mBean.setWater(String.valueOf(mSucculentFull.getWater()));
        mBean.setSummary(mSucculentFull.getInfos().get(0)[1]);
        mBean.setFamilyName(mSucculentFull.getFamilyName());
        mBean.setGeneraName(mSucculentFull.getGeneraName());
        mFragment.getActivity().runOnUiThread(new UIRunnable(UIRunnable.SUCCESS_TEXT));
        mFragment.getActivity().runOnUiThread(new UIRunnable(UIRunnable.SUCCESS_IMAGE));
        mMainAcBinding.swipeRefreshLayout.setEnabled(true);
    }

    public void onDiceClick(View v) {
        if (mDiceStopFlag) {
            mDiceStopFlag = false;
            mBinding.luckyPlantBtnCardview.setEnabled(true);
            mBean.setLuckySucculentBtnShowing(mFragment.getResources()
                    .getString(R.string.dice_btn_stop));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    List<Succulent> succulents = LocalDataUtil.getSucculents();
                    Random random = new Random();
                    while (!mDiceStopFlag) {
                        i = random.nextInt(Constant.SUCCULENT_COUNT);
                        mBean.setLuckySucculentName(succulents.get(i).getName());
                        try {
                            Thread.sleep(DICE_FREQUENCE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    LocalDataUtil.saveDailyItem(i);
                    for (int time = DICE_REDIRECT_TIME; time > 0; time = time - 1000) {
                        mBean.setLuckySucculentWish(
                                "今日多肉是 ”" + succulents.get(i).getName() +
                                        "” ，希望您也像美丽的" + succulents.get(i).getName()
                                        + "一样，天天开心！\n" +
                                        "（" + time / 1000 + "秒后跳转到展示页面)");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mFragment.getActivity().runOnUiThread(new UIRunnable(UIRunnable.DICE_FINISH));
                }
            }).start();
        } else {
            mDiceStopFlag = true;
            mBinding.luckyPlantBtnCardview.setEnabled(false);
        }
    }

    @Override
    public void onSuccessed(Bundle bundle) {
        if (bundle.getSerializable(Constant.ViewModel.SUCCULENT_FULL) != null) {
            SucculentFull succulentFull = (SucculentFull) bundle
                    .getSerializable(Constant.ViewModel.SUCCULENT_FULL);
            mSucculentFull.setInfos(succulentFull.getInfos());
            mSucculentFull.setFamilyName(succulentFull.getFamilyName());
            mSucculentFull.setGeneraName(succulentFull.getGeneraName());
            mBean.setSummary(mSucculentFull.getInfos().get(0)[1]);
            mBean.setFamilyName(mSucculentFull.getFamilyName());
            mBean.setGeneraName(mSucculentFull.getGeneraName());
            mFragment.getActivity().runOnUiThread(new UIRunnable(UIRunnable.SUCCESS_TEXT));
        }
        if (bundle.get(Constant.ViewModel.IMAGE) != null) {
            if (mSucculentFull.getImageUrls() == null) {
                mSucculentFull.setImageUrl(new ArrayList<>());
            }
            List<String> bitmapUrls = mSucculentFull.getImageUrls();
            bitmapUrls.add(bundle.getString(Constant.ViewModel.IMAGE));
            if (!mShowedBitmap && bitmapUrls.size() > 0) {
                mFragment.getActivity().runOnUiThread(new UIRunnable(UIRunnable.SUCCESS_IMAGE));
                mShowedBitmap = true;
            }
        }
    }

    @Override
    public void onFailed(Throwable e) {
        //todo 提示获取数据失败，提示刷新
        //todo 再增加一个主动刷新按钮接口，点击后刷新，可以提示在下面点击图片刷新
        mFragment.getActivity().runOnUiThread(new UIRunnable(UIRunnable.FAILED));
    }

    @Override
    public void onRefresh() {
        initView();
    }


    class UIRunnable extends BaseViewModel.UIRunnable implements Runnable {

        private static final int DICE_FINISH = 2;

        public UIRunnable(int flag) {
            super(flag);
        }

        @Override
        public void run() {
            switch (mFlag) {
                case SUCCESS_TEXT:
                    mBroccoli.removePlaceholder(mBinding.dailyItemIntroCardview);
                    mBroccoli.removePlaceholder(mBinding.dailyItemFamilyCardview);
                    mBroccoli.removePlaceholder(mBinding.dailyItemGenusCardview);
                    break;
                case SUCCESS_IMAGE:
                    Glide.with(mFragment.getActivity()).load(mSucculentFull.getImageUrls().get(0))
                            .preload();
                    Glide.with(mFragment.getActivity()).load(mSucculentFull.getImageUrls().get(0))
                            .into(mBinding.dailyItemImage);
                    Glide.with(mFragment.getActivity()).load(mSucculentFull.getImageUrls().get(0))
                            .into(mMainAcBinding.toolbarImage);
                    mBroccoli.removePlaceholder(mBinding.dailyItemImageCardview);
                    if (mMainAcBinding.swipeRefreshLayout.isRefreshing()) {
                        mMainAcBinding.swipeRefreshLayout.setRefreshing(false);
                    }
                    mMainAcBinding.swipeRefreshLayout.setEnabled(true);
                    mApplicationDataPresenter.setDailyFragmentData(mSucculentFull);
                    break;
                case DICE_FINISH:
                    initView();
                    break;
                case FAILED:
                    Toast.makeText(mFragment.getContext(), Constant.Common.FAILED,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
