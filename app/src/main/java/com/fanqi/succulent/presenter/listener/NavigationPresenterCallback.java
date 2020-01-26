package com.fanqi.succulent.presenter.listener;


/*
 * 导航代理器的要实现的回调方法
 *
 */
public interface NavigationPresenterCallback extends PresenterCallback {
    void onBottomMenuSelected(int graphId, int fragmentId);
}
