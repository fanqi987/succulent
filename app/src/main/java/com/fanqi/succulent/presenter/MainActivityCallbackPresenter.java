package com.fanqi.succulent.presenter;

import android.content.Context;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.fanqi.succulent.R;
import com.fanqi.succulent.databinding.MainAcBinding;
import com.fanqi.succulent.presenter.listener.CallbackPresenter;
import com.fanqi.succulent.presenter.listener.NavigationPresenterCallback;
import com.fanqi.succulent.presenter.listener.PresenterCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivityCallbackPresenter implements
        CallbackPresenter,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private MainAcBinding mBinding;
    private NavigationPresenterCallback mNavigationPresenterCallback;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawer_settings:
                mBinding.drawerLayout.closeDrawers();
                break;
            case R.id.succulentDailyFragment:
            case R.id.succulentFavoriteFragment:
            case R.id.succulentListFragment:
                mNavigationPresenterCallback.onBottomMenuSelected
                        (R.navigation.navigation_daily, menuItem.getItemId());
                break;
        }
        return true;
    }


    public MainActivityCallbackPresenter(Context context, MainAcBinding binding) {
        mContext = context;
        mBinding = binding;
    }

    @Override
    public void setPresenterCallback(PresenterCallback callback) {
        mNavigationPresenterCallback = (NavigationPresenterCallback) callback;
    }
}
