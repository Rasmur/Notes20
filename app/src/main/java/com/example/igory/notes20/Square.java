package com.example.igory.notes20;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by igory on 27.03.2018.
 */

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
