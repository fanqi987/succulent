package com.fanqi.succulent.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fanqi.succulent.R;
import com.fanqi.succulent.databinding.MainAcBinding;
import com.fanqi.succulent.presenter.listener.CallbackPresenter;
import com.fanqi.succulent.presenter.listener.NavigationPresenterCallback;
import com.fanqi.succulent.util.constant.Constant;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class NavigationPresenter implements NavigationPresenterCallback {

    private MainAcBinding mBinding;
    private Activity mActivity;
    private MainActivityCallbackPresenter mCallbackPresenter;

    private AppBarLayout.LayoutParams mAppBarLayoutParams;

    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;
    private CollapsingToolbarLayout.LayoutParams mCollapsingToolbarLayoutParams;

    public NavigationPresenter(Activity activity, MainAcBinding binding, CallbackPresenter callbackPresenter) {
        this.mBinding = binding;
        this.mActivity = activity;
        this.mCallbackPresenter = (MainActivityCallbackPresenter) callbackPresenter;
        this.mAppBarLayoutParams = ((AppBarLayout.LayoutParams) mBinding
                .collapsingToolbarLayout.getLayoutParams());
        this.mCollapsingToolbarLayoutParams = (CollapsingToolbarLayout.LayoutParams) mBinding
                .toolbarImage.getLayoutParams();
        initNavigationUI();
    }

    private void initNavigationUI() {
        mNavController = Navigation.findNavController(mActivity, R.id.main_fragment);
//        mNavController.setGraph(null);
        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.succulentDailyFragment, R.id.succulentListFragment, R.id.succulentFavoriteFragment)
                .setDrawerLayout(mBinding.drawerLayout).build();
        NavigationUI.setupWithNavController(
                mBinding.collapsingToolbarLayout,
                mBinding.toolbar, mNavController, mAppBarConfiguration);
        mBinding.drawerNavigationView.setNavigationItemSelectedListener(mCallbackPresenter);
        mBinding.drawerBottomNavigationView.setOnNavigationItemSelectedListener(mCallbackPresenter);
    }

    @Override
    public void onBottomMenuSelected(int graphId, int fragmentId) {
//        mNavController.setGraph(graphId);
        mNavController.navigate(fragmentId);
    }

    public void firstNavigate() {
        mNavController.setGraph(R.navigation.navigation_daily);
        mNavController.navigate(R.id.succulentDailyFragment);

    }

    public void navigate(int actionId) {
        mNavController.navigate(actionId);
    }

    public void navigate(int actionId, Bundle bundle) {

        mNavController.navigate(actionId, bundle);
    }

    public void hiddenBottomNavigation() {
        mBinding.drawerBottomNavigationView.setVisibility(View.GONE);
    }

    public void showBottomNavigation() {
        mBinding.drawerBottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void dailyViewNav(String titleName) {
//        mBinding.appbarLayout.setExpanded(false);
        mBinding.collapsingToolbarLayout.setTitle(titleName);
        mBinding.toolbar.getMenu().findItem(R.id.toolbar_refresh).setVisible(false);
        mBinding.toolbar.getMenu().findItem(R.id.toolbar_favorite).setVisible(false);
        mAppBarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED |
                AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
//        mCollapsingToolbarLayoutParams.height = Constant.Navigation.NORMAL_TOOLBAR_HEIGHT;
        mBinding.drawerLayout.setEnabled(true);
    }

    public void listViewNav(String titleName) {
        mBinding.appbarLayout.setExpanded(false);
        mBinding.collapsingToolbarLayout.setTitle(titleName);
        mBinding.toolbar.getMenu().findItem(R.id.toolbar_refresh).setVisible(false);
        mBinding.toolbar.getMenu().findItem(R.id.toolbar_favorite).setVisible(false);
        mAppBarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED |
                AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
//        mCollapsingToolbarLayoutParams.height = Constant.Navigation.NORMAL_TOOLBAR_HEIGHT;
        mBinding.drawerLayout.setEnabled(true);
    }

    public void itemViewNav(String titleName) {
        mBinding.appbarLayout.setExpanded(true);
        mBinding.collapsingToolbarLayout.setTitle(titleName);
        mBinding.toolbar.getMenu().findItem(R.id.toolbar_refresh).setVisible(true);
        mBinding.toolbar.getMenu().findItem(R.id.toolbar_favorite).setVisible(false);
//        mAppBarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
//                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED |
//                AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        mAppBarLayoutParams.setScrollFlags(0);
//        mCollapsingToolbarLayoutParams.height = Constant.Navigation.ITEM_TOOLBAR_HEIGHT;
        mBinding.drawerLayout.setEnabled(false);
    }

    public void onBackPressed() {
        mNavController.navigateUp();
    }
}
