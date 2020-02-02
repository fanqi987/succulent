package com.fanqi.succulent.activity.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fanqi.succulent.application.BaseApplication;
import com.fanqi.succulent.presenter.SavedApplicationDataPresenter;

import me.samlss.broccoli.Broccoli;

public class BaseFragment extends Fragment {

    protected Broccoli mBroccoli;
    protected SavedApplicationDataPresenter mApplicationDataPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBroccoli = new Broccoli();
        mApplicationDataPresenter = ((BaseApplication) getActivity().getApplication())
                .getDataPresenter();
    }
}
