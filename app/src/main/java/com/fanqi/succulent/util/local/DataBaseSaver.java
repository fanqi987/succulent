package com.fanqi.succulent.util.local;

public class DataBaseSaver implements Saver {
    private String mResponse;

    @Override
    public void addValue(Object value, String retrofitCallbackFlag) {
        mResponse = (String) value;
    }

    @Override
    public Object getValues() {
        return null;
    }
}
