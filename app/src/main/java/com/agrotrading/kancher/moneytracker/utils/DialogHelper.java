package com.agrotrading.kancher.moneytracker.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.ui.fragments.AddCategoryDialogFragment;
import com.agrotrading.kancher.moneytracker.ui.fragments.AddCategoryDialogFragment_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import java.util.Calendar;

@EBean
public class DialogHelper {

    @RootContext
    Context context;

    ProgressDialog progressDialog;

    @UiThread
    public void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @UiThread
    public void hideProgressDialog() {
        progressDialog.hide();
    }

    @UiThread
    public void showAlertDialog(int titleId, int messageId, DialogInterface.OnClickListener listenerPositiveButton) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setMessage(messageId)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, listenerPositiveButton)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showDatePickerDialogForTextView(final TextView textView) {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String month = (monthOfYear < 10 ? "0" : "") + monthOfYear;
                String day = (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                String dateWithFormat = context.getString(R.string.date_format_string, year, month, day);
                textView.setText(dateWithFormat);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }

    public void showAddCategoryDialogFragment(AddCategoryDialogFragment.AddingCategoryListener listener) {
        AddCategoryDialogFragment_ addCategoryDialogFragment = new AddCategoryDialogFragment_();
        addCategoryDialogFragment.initListener(listener);
        addCategoryDialogFragment.show(((Activity) context).getFragmentManager(), "addCategoryDialogFragment");
    }
}