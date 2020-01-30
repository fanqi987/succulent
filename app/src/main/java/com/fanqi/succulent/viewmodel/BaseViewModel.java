package com.fanqi.succulent.viewmodel;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fanqi.succulent.activity.MainActivity;
import com.fanqi.succulent.activity.fragment.SucculentFragment;
import com.fanqi.succulent.databinding.MainAcBinding;
import com.fanqi.succulent.databinding.SucculentListFragmentBinding;

import me.samlss.broccoli.Broccoli;

public abstract class BaseViewModel implements SwipeRefreshLayout.OnRefreshListener {
    protected MainAcBinding mMainAcBinding;
    protected Fragment mFragment;
    protected Broccoli mBroccoli;

    public void setFragment(Fragment succulentListFragment) {
        mFragment = succulentListFragment;
        mMainAcBinding = ((MainActivity) mFragment.getActivity()).getBinding();
        mMainAcBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mMainAcBinding.swipeRefreshLayout.setEnabled(false);
    }

    public void setBroccoli(Broccoli broccoli) {
        mBroccoli = broccoli;
    }
}
