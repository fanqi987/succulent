package com.fanqi.succulent.viewmodel.bean;

import android.graphics.Bitmap;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class SucculentBaseBean extends BaseObservable {

    public String name;
    public String familyName;
    public String genera;
    public Bitmap image;
    public String summary;
    public String water;
    public String light;

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = "水分：" + water;
        notifyPropertyChanged(BR._all);
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = "光照：" + light;
        notifyPropertyChanged(BR._all);

    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);

    }

    @Bindable
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
        notifyPropertyChanged(BR.familyName);

    }

    @Bindable
    public String getGeneraName() {
        return genera;
    }

    public void setGeneraName(String genera) {
        this.genera = genera;
        notifyPropertyChanged(BR.generaName);

    }

    @Bindable
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Bindable
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
        notifyPropertyChanged(BR.summary);
    }

}
