package com.fanqi.succulent.presenter;

import android.app.Activity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fanqi.succulent.R;
import com.fanqi.succulent.databinding.MainAcBinding;
import com.fanqi.succulent.presenter.listener.CallbackPresenter;
import com.fanqi.succulent.presenter.listener.NavigationPresenterCallback;

public class NavigationPresenter implements NavigationPresenterCallback {

    private MainAcBinding mBinding;
    private Activity mActivity;
    private MainActivityCallbackPresenter mCallbackPresenter;

    NavController navController;
    AppBarConfiguration appBarConfiguration;

    public NavigationPresenter(Activity activity, MainAcBinding binding, CallbackPresenter callbackPresenter) {
        this.mBinding = binding;
        this.mActivity = activity;
        this.mCallbackPresenter = (MainActivityCallbackPresenter) callbackPresenter;
        initNavigationUI();
    }

    private void initNavigationUI() {
        navController = Navigation.findNavController(mActivity, R.id.main_fragment);
        appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.succulentDailyFragment, R.id.succulentListFragment, R.id.succulentFavoriteFragment)
                .setDrawerLayout(mBinding.drawerLayout).build();
        NavigationUI.setupWithNavController(
                mBinding.collapsingToolbarLayout,
                mBinding.toolbar, navController, appBarConfiguration);
        mBinding.drawerNavigationView.setNavigationItemSelectedListener(mCallbackPresenter);
        mBinding.drawerBottomNavigationView.setOnNavigationItemSelectedListener(mCallbackPresenter);
    }

    @Override
    public void onBottomMenuSelected(int graphId, int fragmentId) {
        navController.setGraph(graphId);
        navController.navigate(fragmentId);
    }
}
