package com.fanqi.succulent.util.local;

import com.fanqi.succulent.bean.Bean;
import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.network.FirstEnterRequester;
import com.fanqi.succulent.network.RetrofitCallback;

import java.util.ArrayList;
import java.util.Arrays;

public class BeanSaver implements Saver {

    private ArrayList<Bean[]> mBeans;


    public BeanSaver() {
        mBeans = new ArrayList<>();
    }

    @Override
    public void addValue(Object value, String flag) {
        //成功的话
        //保存到实体类中，再保存到本地数据库
        mBeans.add((Bean[]) value);
        if (RetrofitCallback.RETROFIT_CALLBACK_FLAG.equals(flag)) {
            saveToLocal();
        }
    }

    @Override
    public void addValues(Object[] objects) {
        mBeans = (ArrayList<Bean[]>) Arrays.asList((Bean[]) objects);
    }

    @Override
    public Object[] getValues() {
        return mBeans.toArray();
    }

    @Override
    public Object getValue(int index) {
        return mBeans.get(index);
    }


    public void addValue(Object value) {
        mBeans.add((Bean[]) value);
    }

    public void saveToLocal() {
        for (Bean[] beans : mBeans) {
            for (int i = 0; i < beans.length; i++) {
                beans[i].save();
            }
        }
    }

    public boolean checkWrongCount(Object value) {
        if (value instanceof Succulent[]) {
            if (((Succulent[]) value).length != FirstEnterRequester.SUCCULENT_COUNT) {
                return false;
            }
        }
        if (value instanceof Family[]) {
            if (((Family[]) value).length != FirstEnterRequester.FAMILY_COUNT) {
                return false;
            }
        }
        if (value instanceof Genera[]) {
            if (((Genera[]) value).length != FirstEnterRequester.GENERAS_COUNT) {
                return false;
            }
        }
        return true;
    }

    public void clean() {
        mBeans = null;
    }
}
