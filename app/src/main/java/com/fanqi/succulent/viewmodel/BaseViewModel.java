package com.fanqi.succulent.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fanqi.succulent.activity.MainActivity;
import com.fanqi.succulent.activity.fragment.SucculentFragment;
import com.fanqi.succulent.application.BaseApplication;
import com.fanqi.succulent.databinding.MainAcBinding;
import com.fanqi.succulent.databinding.SucculentListFragmentBinding;
import com.fanqi.succulent.presenter.NavigationPresenter;
import com.fanqi.succulent.presenter.SavedApplicationDataPresenter;

import java.io.Serializable;

import me.samlss.broccoli.Broccoli;

public abstract class BaseViewModel extends ViewModel
        implements SwipeRefreshLayout.OnRefreshListener, Serializable {

    public static final int TEXT_TO_IMAGE_SIZE = 80;
    public static final int TEXT_TO_IMAGE_MARGIN = 20;
    public boolean mTextToImageFlag = false;

    protected MainAcBinding mMainAcBinding;
    protected Fragment mFragment;
    protected Broccoli mBroccoli;
    protected SavedApplicationDataPresenter mApplicationDataPresenter;
    protected NavigationPresenter mNavigationPresenter;

    public void setFragment(Fragment succulentListFragment) {
        mFragment = succulentListFragment;
        mNavigationPresenter = ((MainActivity) mFragment.getActivity()).getNavigationPresenter();
        mMainAcBinding = ((MainActivity) mFragment.getActivity()).getBinding();
        mMainAcBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mMainAcBinding.swipeRefreshLayout.setEnabled(false);
        mApplicationDataPresenter = ((BaseApplication) mFragment.getActivity().getApplication())
                .getDataPresenter();
    }

    public void setBroccoli(Broccoli broccoli) {
        mBroccoli = broccoli;
    }

    protected void textInfoToImage(Context c, ViewGroup v, int count, int resource) {

        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(c);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    TEXT_TO_IMAGE_SIZE, TEXT_TO_IMAGE_SIZE);
            layoutParams.setMargins(TEXT_TO_IMAGE_MARGIN, TEXT_TO_IMAGE_MARGIN
                    , TEXT_TO_IMAGE_MARGIN, TEXT_TO_IMAGE_MARGIN);
            imageView.setImageResource(resource);
            imageView.setLayoutParams(layoutParams);
            v.addView(imageView);
        }

    }


    class UIRunnable {
        public static final int FAILED = -1;
        public static final int SUCCESS_TEXT = 0;
        public static final int SUCCESS_IMAGE = 1;

        protected int mFlag;

        public UIRunnable(int flag) {
            this.mFlag = flag;
        }
    }
}
