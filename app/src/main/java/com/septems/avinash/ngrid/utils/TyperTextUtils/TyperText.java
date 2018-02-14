package com.septems.avinash.ngrid.utils.TyperTextUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;


import com.septems.avinash.ngrid.R;

import java.util.Random;

/**
 * Created by GAvinash on 8/27/2017.
 */

public class TyperText extends HTextView {

    public static final int INVALIDATE = 0x767;
    private Random random;
    private CharSequence mText;
    private Handler handler;
    private int charIncrease;
    private int typerSpeed;

    public TyperText(Context context) {
        this(context, null);
    }

    public TyperText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TyperText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TyperText);
        typerSpeed = typedArray.getInt(R.styleable.TyperText_typerSpeed, 30);
        charIncrease = typedArray.getInt(R.styleable.TyperText_charIncrease, 3);
        typedArray.recycle();

        random = new Random();
        mText = getText();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int currentLength = getText().length();
                if (currentLength < mText.length()) {
                    if (currentLength + charIncrease > mText.length()) {
                        charIncrease = mText.length() - currentLength;
                    }
                    append(mText.subSequence(currentLength, currentLength + charIncrease));
                    long randomTime = typerSpeed + random.nextInt(typerSpeed);
                    Message message = Message.obtain();
                    message.what = INVALIDATE;
                    handler.sendMessageDelayed(message, randomTime);
                    return false;
                }
                return false;
            }
        });
    }


    public int getTyperSpeed() {
        return typerSpeed;
    }

    public void setTyperSpeed(int typerSpeed) {
        this.typerSpeed = typerSpeed;
    }

    public int getCharIncrease() {
        return charIncrease;
    }

    public void setCharIncrease(int charIncrease) {
        this.charIncrease = charIncrease;
    }

    @Override
    public void setProgress(float progress) {
        setText(mText.subSequence(0, (int) (mText.length() * progress)));
    }

    @Override
    public void animateText(CharSequence text) {
        if (text == null) {
            throw new RuntimeException("text must not  be null");
        }
        mText = text;
        setText("");
        Message message = Message.obtain();
        message.what = INVALIDATE;
        handler.sendMessage(message);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeMessages(INVALIDATE);
    }
}
