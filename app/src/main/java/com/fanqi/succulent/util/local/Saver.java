package com.fanqi.succulent.util.local;

public interface Saver {
    void addValue(Object value, String flag);

    void addValues(Object[] objects);

    Object[] getValues();

    Object getValue(int index);
}
