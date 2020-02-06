package com.fanqi.succulent.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fanqi.succulent.R;
import com.fanqi.succulent.activity.MainActivity;

public class SucculentFavoriteFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getBinding().collapsingToolbarLayout.setTitle("多肉收藏");
        View v = inflater.inflate(R.layout.succulent_favorite_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo 显示占拉符页面,再初始化页面
        //切换到每日推荐的fragment
    }
}
