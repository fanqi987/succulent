package com.fanqi.succulent.viewmodel;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fanqi.succulent.R;
import com.fanqi.succulent.activity.MainActivity;
import com.fanqi.succulent.activity.adapter.SucculentListAdapter;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.databinding.SucculentListFragmentBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.listener.SucculentItemClickedCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表页面的model
 */
public class SucculentListViewModel extends BaseViewModel implements
        SwipeRefreshLayout.OnRefreshListener,
        SucculentItemClickedCallback {

    private SucculentListFragmentBinding mBinding;
    private SucculentListAdapter mListAdapter;
    private List<Succulent> mSucculentList;

    public SucculentListViewModel() {
        mSucculentList = new ArrayList<>();
        mSucculentList = LocalDataUtil.getSucculents();

    }

    public void setBinding(SucculentListFragmentBinding binding) {
        mBinding = binding;
    }

    public void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mFragment.getContext(), 2);
        mBinding.succulentListRecyclerView.setLayoutManager(layoutManager);
        mListAdapter = new SucculentListAdapter(mFragment.getContext(), mBroccoli);
        mListAdapter.setData(mSucculentList);
        mListAdapter.setItemClickedCallback(this);
        mBinding.succulentListRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    public void onRefresh() {
        mListAdapter = new SucculentListAdapter(mFragment.getContext(), mBroccoli);
        mListAdapter.setData(mSucculentList);
        mBinding.succulentListRecyclerView.setAdapter(mListAdapter);
        mMainAcBinding.swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onClick(View v, Succulent succulent) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ViewModel.SUCCULENT, succulent);
        ((MainActivity) mFragment.getActivity()).getNavigationPresenter()
                .navigate(R.id.action_succulentListFragment_to_succulentFragment, bundle);
    }

}
