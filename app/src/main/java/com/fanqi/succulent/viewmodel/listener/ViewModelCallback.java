package com.fanqi.succulent.viewmodel.listener;

import android.os.Bundle;

public interface ViewModelCallback {

    void onSuccessed(Bundle bundle);

    void onFailed(Throwable e);
}
