package com.fanqi.succulent.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.fanqi.succulent.R;
import com.fanqi.succulent.databinding.MainAcBinding;
import com.fanqi.succulent.presenter.listener.CallbackPresenter;
import com.fanqi.succulent.presenter.listener.NavigationPresenterCallback;
import com.fanqi.succulent.presenter.listener.PresenterCallback;
import com.fanqi.succulent.util.FileSaverUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivityCallbackPresenter implements
        CallbackPresenter,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        Toolbar.OnMenuItemClickListener,
        View.OnClickListener, View.OnLongClickListener,
        PopupWindow.OnDismissListener {

    private Context mContext;
    private MainAcBinding mBinding;
    private NavigationPresenterCallback mNavigationPresenterCallback;

    private View mHelpView;
    private View mSaveImageBtnView;
    private View mRootView;
    private PopupWindow mPopupWindow;
    private Window mWindow;
    private WindowManager.LayoutParams mWLayoutParams;

    //待保存的图片控件
    private ImageView mOnSaveImageView;


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
                mPopupWindow.setContentView(mHelpView);
                initAndShowPopWindow();
                break;
        }
        return true;
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_image:
                mPopupWindow.setContentView(mSaveImageBtnView);
                initAndShowPopWindow();
                mOnSaveImageView = (ImageView) v;
                break;
        }
        return true;
    }

    public MainActivityCallbackPresenter(Context context, MainAcBinding binding) {
        mContext = context;
        mBinding = binding;
        initViews();

    }

    private void initViews() {
        mHelpView = LayoutInflater.from(mContext).inflate(R.layout.help_view, null);
        mHelpView.findViewById(R.id.help_view_btn).setOnClickListener(this);
        mSaveImageBtnView = LayoutInflater.from(mContext).inflate(R.layout.save_image_btn_view, null);
        mSaveImageBtnView.setOnClickListener(this);
        mPopupWindow = new PopupWindow(mContext);
        mRootView = ((Activity) mContext).getWindow().getDecorView();
        mWindow = ((Activity) mContext).getWindow();
        mWLayoutParams = mWindow.getAttributes();
    }

    @Override
    public void setPresenterCallback(PresenterCallback callback) {
        mNavigationPresenterCallback = (NavigationPresenterCallback) callback;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.help_view_btn:
                mPopupWindow.dismiss();
                break;
            case R.id.save_image_btn:
                mPopupWindow.dismiss();
                FileSaverUtil.saveImage(mContext, mOnSaveImageView);
                break;
        }
    }


    private void initAndShowPopWindow() {
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        mPopupWindow.setBackgroundDrawable(null);
        mPopupWindow.setOnDismissListener(this);
        mPopupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
        mWLayoutParams.alpha = 0.7f;
        mWindow.setAttributes(mWLayoutParams);
    }

    @Override
    public void onDismiss() {
        mWLayoutParams.alpha = 1f;
        mWindow.setAttributes(mWLayoutParams);
    }
}
