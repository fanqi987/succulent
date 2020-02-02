package com.fanqi.succulent.viewmodel;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
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

public abstract class BaseViewModel implements SwipeRefreshLayout.OnRefreshListener, Serializable {
    protected MainAcBinding mMainAcBinding;
    protected Fragment mFragment;
    protected Broccoli mBroccoli;
    protected SavedApplicationDataPresenter mApplicationDataPresenter;
    protected NavigationPresenter mNavigationPresenter;

    public void setFragment(Fragment succulentListFragment) {
        mFragment = succulentListFragment;
        mNavigationPresenter=((MainActivity) mFragment.getActivity()).getNavigationPresenter();
        mMainAcBinding = ((MainActivity) mFragment.getActivity()).getBinding();
        mMainAcBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mMainAcBinding.swipeRefreshLayout.setEnabled(false);
        mApplicationDataPresenter=((BaseApplication)mFragment.getActivity().getApplication())
                .getDataPresenter();
    }

    public void setBroccoli(Broccoli broccoli) {
        mBroccoli = broccoli;
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
