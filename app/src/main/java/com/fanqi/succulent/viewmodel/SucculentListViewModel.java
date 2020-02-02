package com.fanqi.succulent.viewmodel;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fanqi.succulent.R;
import com.fanqi.succulent.activity.MainActivity;
import com.fanqi.succulent.activity.adapter.SucculentListAdapter;
import com.fanqi.succulent.activity.adapter.SucculentListTabPagerAdapter;
import com.fanqi.succulent.activity.fragment.SucculentListTabFragment;
import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.databinding.SucculentListFragmentBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.SettingsUtil;
import com.fanqi.succulent.util.constant.Constant;
import com.fanqi.succulent.viewmodel.listener.SucculentItemClickedCallback;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 列表页面的model
 */
public class SucculentListViewModel extends BaseViewModel {

    private SucculentListFragmentBinding mBinding;
    private List<Succulent> mSucculentList;

    private List<SucculentListTabFragment> mTabFragments;
    private List<String> mTitleList;

    public SucculentListViewModel() {
        mSucculentList = LocalDataUtil.getSucculents();
        mTabFragments = new ArrayList<>();
        mTitleList = new ArrayList<>();
    }

    public void setBinding(SucculentListFragmentBinding binding) {
        mBinding = binding;
    }

    public void initView() {
        //导航控制
        mNavigationPresenter.listViewNav(mFragment.getResources()
                .getString(R.string.toolbar_title_succulent_list));
        //手动设置fragment的最小高度
        mBinding.listFragmentLayout.getLayoutParams().height = (int) (SettingsUtil
                .getDisplayMetrics(mFragment.getActivity()).heightPixels * 0.9);
        //初始化tab,标题
        List<Family> families = LocalDataUtil.getFamilies();
        for (int i = 0; i < families.size(); i++) {
            mTabFragments.add(new SucculentListTabFragment(families.get(i)));
            mBinding.tabLayout.addTab(mBinding.tabLayout.newTab());
            mTitleList.add(families.get(i).getName());
        }

        //设置pagerView
        SucculentListTabPagerAdapter adapter = new SucculentListTabPagerAdapter(mFragment.getActivity()
                .getSupportFragmentManager(), mTabFragments);
        mBinding.viewPager.setAdapter(adapter);
        //pagerview加入tabview
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);

        for (int i = 0; i < mTitleList.size(); i++) {
            mBinding.tabLayout.getTabAt(i).setText(mTitleList.get(i));
        }
    }

    @Override
    public void onRefresh() {
    }
}
