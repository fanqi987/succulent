package com.fanqi.succulent.viewmodel;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fanqi.succulent.R;
import com.fanqi.succulent.activity.MainActivity;
import com.fanqi.succulent.activity.adapter.SucculentListAdapter;
import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.databinding.SucculentListFragmentBinding;
import com.fanqi.succulent.databinding.SucculentListTabFragmentBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.listener.SucculentItemClickedCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表tab页面的model
 */
public class SucculentListTabViewModel extends BaseViewModel implements
        SwipeRefreshLayout.OnRefreshListener,
        SucculentItemClickedCallback {

    private SucculentListTabFragmentBinding mBinding;
    private SucculentListAdapter mListAdapter;
    private List<Succulent> mSucculentList;
    private GridLayoutManager mLayoutManager;
    private Family mFamily;

    public SucculentListTabViewModel(Family family) {
        mSucculentList = new ArrayList<>();
        mFamily = family;
    }

    public void setBinding(SucculentListTabFragmentBinding binding) {
        mBinding = binding;
    }

    public void initView() {
        mLayoutManager = new GridLayoutManager(mFragment.getContext(), 2);
        mBinding.succulentListRecyclerView.setLayoutManager(mLayoutManager);
        mListAdapter = new SucculentListAdapter(mFragment, mBroccoli);
//        if (mSucculentList.size() > 20) {
//            mListAdapter.setData(mSucculentList.subList(0, 20));
//        } else {
        mSucculentList = LocalDataUtil.getSucculents(mFamily.getPost_id());
        mListAdapter.setData(mSucculentList);
//        }
        mListAdapter.setItemClickedCallback(this);
        mBinding.succulentListRecyclerView.setAdapter(mListAdapter);
        mMainAcBinding.swipeRefreshLayout.setEnabled(true);

    }

    @Override
    public void onRefresh() {
        mListAdapter = new SucculentListAdapter(mFragment, mBroccoli);
        mListAdapter.setData(mSucculentList);
        mBinding.succulentListRecyclerView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
        mMainAcBinding.swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onClick(View v, Succulent succulent) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ViewModel.SUCCULENT, succulent);
        ((MainActivity) mFragment.getActivity()).getNavigationPresenter()
                .navigate(R.id.succulentFragment, bundle);
    }

    public void notifyDataSetChanged() {
        mListAdapter.notifyDataSetChanged();
    }
}
