package com.fanqi.succulent.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

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
        BottomNavigationView.OnNavigationItemSelectedListener,
        Toolbar.OnMenuItemClickListener,
        View.OnClickListener {

    private Context mContext;
    private MainAcBinding mBinding;
    private NavigationPresenterCallback mNavigationPresenterCallback;

    private View mHelpView;
    private View mRootView;
    private PopupWindow mPopupWindow;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawer_settings:
                mBinding.drawerLayout.closeDrawers();
                //设置不要选中抽屉菜单项
                return false;
            case R.id.succulentDailyFragment:
            case R.id.succulentFavoriteFragment:
            case R.id.succulentListFragment:
                mNavigationPresenterCallback.onBottomMenuSelected
                        (R.navigation.navigation_succulent, menuItem.getItemId());
                break;
        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_help:
                mRootView = ((Activity) mContext).getWindow().getDecorView();
                mPopupWindow = new PopupWindow(mContext);
                mPopupWindow.setContentView(mHelpView);
                mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setFocusable(true);
                mPopupWindow.setOutsideTouchable(false);
                mPopupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
                mPopupWindow.setBackgroundDrawable(null);
                mPopupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
                break;
        }
        return true;
    }

    public MainActivityCallbackPresenter(Context context, MainAcBinding binding) {
        mContext = context;
        mBinding = binding;
        mHelpView = LayoutInflater.from(mContext).inflate(R.layout.help_view, null);
        mHelpView.findViewById(R.id.help_view_btn).setOnClickListener(this);
    }

    @Override
    public void setPresenterCallback(PresenterCallback callback) {
        mNavigationPresenterCallback = (NavigationPresenterCallback) callback;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.help_view_btn:
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                break;
        }
    }


}
