package com.fanqi.succulent.activity.view;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.fanqi.succulent.R;

public class SucculentDailyCardView extends CardView {


    public SucculentDailyCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(getResources().getColor(R.color.colorGreen800));
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }
}
