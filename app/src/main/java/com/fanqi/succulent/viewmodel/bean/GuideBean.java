package com.fanqi.succulent.viewmodel.bean;

import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class GuideBean extends BaseObservable {

    public String guideEnterText;

    @Bindable
    public String  getGuideEnterText() {
        return guideEnterText;
    }

    public void setGuideEnterText(String guideEnterText) {
        this.guideEnterText = guideEnterText;
        notifyPropertyChanged(BR._all);
    }


}
