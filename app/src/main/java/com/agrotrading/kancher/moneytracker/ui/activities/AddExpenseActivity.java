package com.agrotrading.kancher.moneytracker.ui.activities;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.adapters.CategoriesSpinnerAdapter;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.Expenses;
import com.agrotrading.kancher.moneytracker.utils.ApplicationPreferences_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@EActivity(R.layout.activity_add_expense)
public class AddExpenseActivity extends AppCompatActivity {

    @Pref
    static ApplicationPreferences_ prefs;

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.til_sum)
    TextInputLayout expenseSumLayout;

    @ViewById(R.id.sum_field)
    EditText expenseSum;

    @ViewById(R.id.comment_field)
    EditText expenseComment;

    @ViewById(R.id.til_comment)
    TextInputLayout expenseCommentLayout;

    @ViewById(R.id.categories_spinner)
    Spinner categorySpinner;

    @ViewById(R.id.date_field)
    EditText expenseDate;

    @Bean
    CategoriesSpinnerAdapter categoriesSpinnerAdapter;

    @OptionsItem(R.id.home)
    void back() {
        onBackPressed();
        finish();
    }

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        expenseDate.setText(dateFormat.format(new Date()));
        setTitle(getString(R.string.add_expense));
    }

    @AfterViews
    void bindAdapter() {
        categorySpinner.setAdapter(categoriesSpinnerAdapter);
    }

    @Click(R.id.date_field)
    void changeDate() {

        Calendar calendar = Calendar.getInstance();
        final Date date = new Date();
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String month = (monthOfYear < 10 ? "0" : "") + monthOfYear;
                String day = (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                String dateWithFormat = getString(R.string.date_format, year, month, day);
                expenseDate.setText(dateWithFormat);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Click(R.id.cancel_button)
    void cancel() {
        back();
    }

    @TextChange(R.id.sum_field)
    void sumFieldChange() {
        expenseSumLayout.setErrorEnabled(false);
    }

    @TextChange(R.id.comment_field)
    void commentFieldChange() {
        expenseCommentLayout.setErrorEnabled(false);
    }

    @Click(R.id.add_button)
    void addExpense(View clickedView) {

        if(expenseSum.length() == 0) {
            expenseSumLayout.setError(getString(R.string.error_required_field));
            return;
        }

        if(expenseComment.length() == 0) {
            expenseCommentLayout.setError(getString(R.string.error_required_field));
            return;
        }

        Categories category = (Categories) categorySpinner.getSelectedItem();
        Expenses expense = new Expenses(Double.parseDouble(expenseSum.getText().toString()), expenseComment.getText().toString(), expenseDate.getText().toString(), category);
        expense.save();
        prefs.needSyncExpenses().put(true);
        finish();
    }

}