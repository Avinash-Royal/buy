package com.septems.avinash.ngrid.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by SKranthi on 5/10/2017.
 */

public class TextWatcherAdapter implements TextWatcher {

    public interface TextWatcherListener {
        void onTextChanged(EditText view, String text);
    }

    private final EditText view;
    private final TextWatcherListener listener;

    public TextWatcherAdapter(EditText editText, TextWatcherListener listener) {
        this.view = editText;
        this.listener = listener;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        listener.onTextChanged(view, s.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // pass
    }

    @Override
    public void afterTextChanged(Editable s) {
        // pass
    }
}