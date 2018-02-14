package com.septems.avinash.ngrid.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Avinash on 10/1/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText setEditText;
    Calendar activeDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    public void setSetEditText(EditText setEditText) {
        this.setEditText = setEditText;
        if (!"".equals(setEditText.getText().toString())) {
            try {
                Date d = simpleDateFormat.parse(setEditText.getText().toString());
                activeDate = Calendar.getInstance();
                activeDate.setTime(d);

            } catch (ParseException ex) {
            }
        }
    }

    public DatePickerFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year;
        int month;
        int day;

        if ("".equals(setEditText.getText().toString())) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            year = activeDate.get(Calendar.YEAR);
            month = activeDate.get(Calendar.MONTH);
            day = activeDate.get(Calendar.DAY_OF_MONTH);
        }


        DatePickerDialog cdp = new DatePickerDialog(getActivity(), this, year, month, day);
        cdp.setButton(DialogInterface.BUTTON_NEUTRAL, "Clear", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEUTRAL) {
                    dialog.dismiss();
                    setEditText.setText("");
                }
            }
        });
        return cdp;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, dayOfMonth);
        setEditText.setText(simpleDateFormat.format(newDate.getTime()));
    }

}