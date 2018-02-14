package com.septems.avinash.ngrid.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Avinash on 10/1/2017.
 */

public class OnClickDateListener implements View.OnClickListener {
    EditText activeField;
    FragmentActivity mContext;

    public OnClickDateListener(EditText field, Context context){
        activeField = field;
        mContext = (FragmentActivity)context;
        field.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DatePickerFragment DateFragment = new DatePickerFragment();
        DateFragment.setSetEditText(activeField);
        DateFragment.show(mContext.getSupportFragmentManager(), "datePicker");
    }
}

