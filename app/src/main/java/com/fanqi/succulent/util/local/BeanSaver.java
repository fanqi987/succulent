package com.fanqi.succulent.util.local;

import com.fanqi.succulent.bean.Bean;
import com.fanqi.succulent.bean.Family;
import com.fanqi.succulent.bean.Genera;
import com.fanqi.succulent.bean.Succulent;
import com.fanqi.succulent.network.RetrofitCallback;
import com.fanqi.succulent.util.constant.Constant;

import org.litepal.LitePal;

import java.util.ArrayList;


/**
 * 用来保存第1次获取到的3个不同bean数组的信息
 * 或者是保存普通的相同bean数组信息（目前似乎不需要）
 */
public class BeanSaver implements Saver {

    private ArrayList<Bean[]> mBeans;


    public BeanSaver() {
        mBeans = new ArrayList<>();
    }


    @Override
    public void addValue(Object[] value) {
        mBeans.add((Bean[]) value);
    }

    @Override
    public void addValues(Object objects) {
        mBeans = (ArrayList<Bean[]>) objects;
    }

    @Override
    public Object[] getValue(int index) {
        return mBeans.get(index);
    }

    @Override
    public Object getValues() {
        return mBeans;
    }


    public void saveToLocal() {
        for (Bean[] beans : mBeans) {
            for (int i = 0; i < beans.length; i++) {
                beans[i].save();
            }
        }
    }

    public boolean checkCount(Object value) {
        if (value instanceof Succulent[]) {
            if (((Succulent[]) value).length == Constant.SUCCULENT_COUNT) {
                return true;
            }
        }
        if (value instanceof Family[]) {
            if (((Family[]) value).length == Constant.FAMILY_COUNT) {
                return true;
            }
        }
        if (value instanceof Genera[]) {
            if (((Genera[]) value).length == Constant.GENERAS_COUNT) {
                return true;
            }
        }
        return false;
    }

    public void clean() {
        mBeans = null;
    }
}
