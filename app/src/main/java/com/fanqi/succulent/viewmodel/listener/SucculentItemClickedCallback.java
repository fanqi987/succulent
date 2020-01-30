package com.fanqi.succulent.viewmodel.listener;

import android.view.View;

import com.fanqi.succulent.bean.Succulent;

public interface SucculentItemClickedCallback {
    void onClick(View v, Succulent succulent);
}
