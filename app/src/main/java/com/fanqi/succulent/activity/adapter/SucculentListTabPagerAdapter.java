package com.fanqi.succulent.activity.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fanqi.succulent.activity.fragment.SucculentListTabFragment;

import java.util.List;

public class SucculentListTabPagerAdapter extends FragmentPagerAdapter {

    private List<SucculentListTabFragment> mTabFragments;

    public SucculentListTabPagerAdapter(@NonNull FragmentManager fm,List<SucculentListTabFragment> tabFragments) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mTabFragments=tabFragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mTabFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabFragments.size();
    }
}
