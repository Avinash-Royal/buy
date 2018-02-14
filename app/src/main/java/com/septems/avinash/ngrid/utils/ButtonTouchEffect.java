package com.septems.avinash.ngrid.utils;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.septems.avinash.ngrid.R;


/**
 * Created by hanuma on 9/3/2017.
 */

public class ButtonTouchEffect extends android.support.v7.widget.AppCompatButton{
    public ButtonTouchEffect(Context context) {
        super(context);
    }

    public ButtonTouchEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonTouchEffect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }
        });
        setOnTouchListener(new OnTouchListener() {
            private static final int MAX_CLICK_DURATION = 200;
            private long startClickTime;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        AnimatorSet reducer = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.reduce_size);
                        reducer.setTarget(view);
                        reducer.start();
                        break;
                    case MotionEvent.ACTION_UP:
//                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
//                        if(clickDuration < MAX_CLICK_DURATION) {
//                            callOnClick();
//                        }
                        AnimatorSet regainer = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.regain_size);
                        regainer.setTarget(view);
                        regainer.start();
                        break;
                }
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
        super.onDraw(canvas);
    }

}
