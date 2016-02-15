package com.agrotrading.kancher.moneytracker.ui.activities;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.adapters.CategoriesSpinnerAdapter;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.Expenses;
import com.agrotrading.kancher.moneytracker.utils.ApplicationPreferences_;
import com.agrotrading.kancher.moneytracker.utils.DialogHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@EActivity(R.layout.activity_add_expense)
public class AddExpenseActivity extends AppCompatActivity {

    @Bean
    CategoriesSpinnerAdapter categoriesSpinnerAdapter;

    @Bean
    DialogHelper dialogHelper;

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

    @AfterViews
    void ready() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.add_expense));

        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        expenseDate.setText(dateFormat.format(new Date()));

        categorySpinner.setAdapter(categoriesSpinnerAdapter);
    }

    @OptionsItem(R.id.home)
    void back() {
        finish();
    }

    @Click(R.id.date_field)
    void changeDate() {
        dialogHelper.showDatePickerDialogForTextView(expenseDate);
    }

    @Click(R.id.cancel_button)
    void cancel() {
        finish();
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

        if (categorySpinner.getAdapter().getCount() < 1) {
            Snackbar.make(clickedView, getString(R.string.no_categories), Snackbar.LENGTH_LONG).show();
        } else if (expenseSum.length() == 0) {
            expenseSumLayout.setError(getString(R.string.error_required_field));
        } else if (expenseComment.length() == 0) {
            expenseCommentLayout.setError(getString(R.string.error_required_field));
        } else {
            new Expenses(
                    Double.parseDouble(expenseSum.getText().toString()),
                    expenseComment.getText().toString(),
                    expenseDate.getText().toString(),
                    (Categories) categorySpinner.getSelectedItem()
            ).save();

            prefs.needSyncExpenses().put(true);
            finish();
        }
    }
}