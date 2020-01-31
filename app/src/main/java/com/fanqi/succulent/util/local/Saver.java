package com.fanqi.succulent.util.local;

import java.util.List;

public interface Saver {
    void addValue(Object[] value);

    void addValues(Object values);

    Object getValues();

    Object[] getValue(int index);
}
