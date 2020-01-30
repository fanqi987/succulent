package com.fanqi.succulent.util.provider;

import com.fanqi.succulent.bean.Bean;
import com.fanqi.succulent.util.local.BeanSaver;
import com.fanqi.succulent.util.local.Saver;

import okhttp3.ResponseBody;

public class SaverProvider {

    private Saver mSaver;
    private Object mValue;

    public SaverProvider(Object value) {
        mValue = value;
        if (mValue instanceof Bean[]) {
            mSaver = new BeanSaver();
        }
    }

    public Saver getSaver() {
        return mSaver;
    }
}
