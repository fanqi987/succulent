package com.fanqi.succulent.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fanqi.succulent.R;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.bean.SucculentFull;
import com.fanqi.succulent.databinding.SucculentItemDetailLayoutBinding;
import com.fanqi.succulent.util.NetworkUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.bean.SucculentViewBean;
import com.fanqi.succulent.viewmodel.listener.ViewModelCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 具体信息页面的model
 */
public class SucculentViewModel extends BaseViewModel
        implements ViewModelCallback, MenuItem.OnMenuItemClickListener, View.OnClickListener {

    private SucculentItemDetailLayoutBinding mBinding;

    private NetworkUtil mNetworkUtil;

    public SucculentViewBean mBean;

    private boolean mShowedBitmap;
    private int mShowedBitmapNumber;


    private SucculentFull mSucculentFull;

    public SucculentViewModel() {
        mBean = new SucculentViewBean();
        mNetworkUtil = new NetworkUtil();
    }

    public void setBinding(SucculentItemDetailLayoutBinding binding) {
        mBinding = binding;
    }

    public void setData(Serializable serializable) {
        mSucculentFull = new SucculentFull((Succulent) serializable);
        mSucculentFull.setUrl(Constant.baseUrlBaidu
                + mSucculentFull.getPage_name()
        );
    }

    public void initView() {
        mShowedBitmap = false;

        // 显示占拉符页面,再初始化页面
        addPlaceHolders();
        //导航控制
        mNavigationPresenter.itemViewNav(mSucculentFull.getName());

        //设置好本地数据
        mBean.setName(mSucculentFull.getName());
        //本地光水
        textToImage();
        //设置好网络数据
        mNetworkUtil.setViewModelCallback(this);
        String pageName = mSucculentFull.getPage_name();
        mNetworkUtil.requestGetMediaInfo(pageName);
        //设置好刷新图片按钮
        mMainAcBinding.toolbar.getMenu().findItem(R.id.toolbar_refresh)
                .setOnMenuItemClickListener(this);
        //设置好按钮监听
        mBinding.itemDetailVisitWeb.setOnClickListener(this);
    }

    private void textToImage() {
        if (!mTextToImageFlag) {
            mBean.setLight("");
            mBean.setWater("");
            textInfoToImage(mFragment.getContext(),
                    mBinding.itemDetailLightCardviewLinearlayout,
                    mSucculentFull.getLight(),
                    R.drawable.sunny);
            textInfoToImage(mFragment.getContext(),
                    mBinding.itemDetailWaterCardviewLinearlayout,
                    mSucculentFull.getWater(),
                    R.drawable.drop);
            mTextToImageFlag = true;
        }
    }

    private void addPlaceHolders() {
        //改变的是toolbar的image
        mBroccoli.addPlaceholders(
                mMainAcBinding.toolbarImage,
                mBinding.itemDetailFamilyCardview,
                mBinding.itemDetailGenusCardview,
                mBinding.itemDetailIntroCardview
        );
        mBroccoli.show();
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
                mShowedBitmapNumber = 0;
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_refresh:
                Random random = new Random();
                int dice;
                List<String> urls = mSucculentFull.getImageUrls();
                if ((urls == null) || (urls.size() == 0)) {
                    return false;
                }
                int i = 0;
                do {
                    dice = random.nextInt(urls.size());
                    i++;
                } while (dice == mShowedBitmapNumber && i < 10);
                if (dice == mShowedBitmapNumber) {
                    Toast.makeText(mFragment.getContext(), Constant.Common.SAME_IMAGE,
                            Toast.LENGTH_SHORT).show();
                }
                mShowedBitmapNumber = dice;
                Glide.with(mFragment.getContext()).load(urls.get(mShowedBitmapNumber))
                        .into(mMainAcBinding.toolbarImage);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        String url = mSucculentFull.getUrl();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url.trim()));
//        Log.e("aaaaa", intent.getDataString());
//        intent.setData(Uri.parse("https://baike.baidu.com/item/紫珍珠/4527869"));
//        Log.e("aaaaa", intent.getDataString());
        mFragment.getActivity().startActivity(intent);
    }

    class UIRunnable extends BaseViewModel.UIRunnable implements Runnable {

        public UIRunnable(int flag) {
            super(flag);
        }

        @Override
        public void run() {
            switch (mFlag) {
                case SUCCESS_TEXT:
                    mBroccoli.removePlaceholder(mBinding.itemDetailIntroCardview);
                    mBroccoli.removePlaceholder(mBinding.itemDetailFamilyCardview);
                    mBroccoli.removePlaceholder(mBinding.itemDetailGenusCardview);
                    break;
                case SUCCESS_IMAGE:
                    Glide.with(mFragment.getActivity()).load(mSucculentFull.getImageUrls().get(0))
                            .into(mMainAcBinding.toolbarImage);
                    mBroccoli.removePlaceholder(mMainAcBinding.toolbarImage);
                    if (mMainAcBinding.swipeRefreshLayout.isRefreshing()) {
                        mMainAcBinding.swipeRefreshLayout.setRefreshing(false);
                    }
                    mMainAcBinding.swipeRefreshLayout.setEnabled(true);
                    break;
                case FAILED:
                    Toast.makeText(mFragment.getContext(), Constant.Common.FAILED,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
