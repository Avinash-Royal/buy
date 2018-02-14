package com.septems.avinash.ngrid.utils.TyperTextUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by GAvinash on 8/27/2017.
 */

@SuppressLint("AppCompatCustomView")
public abstract class HTextView extends TextView {
    public HTextView(Context context) {
        this(context, null);
    }

    public HTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void setProgress(float progress);

    public abstract void animateText(CharSequence text);
}
