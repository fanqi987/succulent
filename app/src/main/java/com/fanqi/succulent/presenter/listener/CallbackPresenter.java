package com.fanqi.succulent.presenter.listener;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

/*
 * 回调方法管理者
 *
 * 管理待实现的回调方法，调用别人注册的回调
 * （因为系统异步回调后，需要完成他人工作，所以调用他人的回调方法，如代理器）
 */
public interface CallbackPresenter {

    void setPresenterCallback(PresenterCallback callback);
}
