package com.fanqi.succulent.viewmodel.bean;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class SucculentDailyViewBean extends SucculentBaseBean {

    public String luckySucculentName;
    public String luckySucculentWish;
    public String luckySucculentBtnShowing;

    @Bindable
    public String getLuckySucculentBtnShowing() {
        return luckySucculentBtnShowing;
    }

    public void setLuckySucculentBtnShowing(String luckySucculentBtnShowing) {
        this.luckySucculentBtnShowing = luckySucculentBtnShowing;
        notifyPropertyChanged(BR._all);
    }

    @Bindable
    public String getLuckySucculentWish() {
        return luckySucculentWish;
    }

    public void setLuckySucculentWish(String luckySucculentWish) {
        this.luckySucculentWish = luckySucculentWish;
        notifyPropertyChanged(BR._all);
    }
    @Bindable
    public String getLuckySucculentName() {
        return luckySucculentName;
    }

    public void setLuckySucculentName(String luckySucculentName) {
        this.luckySucculentName = luckySucculentName;
        notifyPropertyChanged(BR._all);
    }
}
