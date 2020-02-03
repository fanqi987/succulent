package com.fanqi.succulent.viewmodel;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fanqi.succulent.R;
import com.fanqi.succulent.activity.adapter.SucculentListTabPagerAdapter;
import com.fanqi.succulent.activity.fragment.SucculentFragment;
import com.fanqi.succulent.activity.fragment.SucculentListTabFragment;
import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.databinding.SucculentListFragmentBinding;
import com.fanqi.succulent.util.LocalDataUtil;
import com.fanqi.succulent.util.SettingsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表页面的model
 */
public class SucculentListViewModel extends BaseViewModel {

    private SucculentListFragmentBinding mBinding;
    private List<Succulent> mSucculentList;
    private List<Family> mFamilies;
    private List<SucculentListTabFragment> mTabFragments;
    private List<String> mTitleList;
    private SucculentListTabPagerAdapter mAdapter;

    public SucculentListViewModel() {
        mSucculentList = LocalDataUtil.getSucculents();
        mTabFragments = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mFamilies = LocalDataUtil.getFamilies();
        //在model构造处创建了fragments
        for (int i = 0; i < mFamilies.size(); i++) {
            mTabFragments.add(new SucculentListTabFragment(mFamilies.get(i)));
            mTitleList.add(mFamilies.get(i).getName());
        }

    }

    public void initViewPagerAdapter() {

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
        for (int i = 0; i < mFamilies.size(); i++) {
            mBinding.tabLayout.addTab(mBinding.tabLayout.newTab());
        }
        //设置pagerView adapter
        // 注意：这里创建的是fragment的嵌套，所以适配器中设置的是父fragment管理子fragment的fragmentManager
        mAdapter = new SucculentListTabPagerAdapter(mFragment.getChildFragmentManager(), mTabFragments);
        mBinding.viewPager.setAdapter(mAdapter);

        //pagerview加入tabview
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        //tabview初始化titlelist
        for (int i = 0; i < mTitleList.size(); i++) {
            mBinding.tabLayout.getTabAt(i).setText(mTitleList.get(i));
        }
        //todo 现在的问题就是第1次进来到列表菜单后，
        // 子fragment初始化了，调用了onCreateView
        // 非第一次进入时，不会初始化，不调用onCreateView，
        // 并且因为listfragment不会入栈，所以没有保存之前的视图信息

        //解决方法我想有以下几点
        //控制Viewpager或tabView中谁的方法，切换到之前状态所在的tab菜单
//        mBinding.viewPager.setCurrentItem(5);

    }

    @Override
    public void onRefresh() {
    }

    public void notifyDataChanged() {
//        mBinding.viewPager.setAdapter(null);
//        mAdapter = null;
//        mAdapter.notifyDataSetChanged();
    }


}
