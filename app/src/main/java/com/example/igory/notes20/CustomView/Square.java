package com.example.igory.notes20.CustomView;

import android.content.Context;
import android.util.AttributeSet;

public class Square extends android.support.v7.widget.AppCompatImageView {
    public Square(Context context) {
        super(context);
    }

    public Square(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());
    }
}
