package com.fanqi.succulent.presenter.listener;


/*
 * 导航代理器的要实现的回调方法
 *
 */
public interface NavigationPresenterCallback extends PresenterCallback {
    void onBottomMenuSelected(int graphId, int fragmentId);

    //本来以为popupwindow需要获取当前的fragment的视图才能显示，后来也没有正确获取，并且仍然是以activity的视图为父视图
//    int onToolbarHelpClick();

}
