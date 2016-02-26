package com.agrotrading.kancher.moneytracker.ui.activities;

import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.adapters.CategoriesSpinnerAdapter;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.Expenses;
import com.agrotrading.kancher.moneytracker.utils.ApplicationPreferences_;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EActivity(R.layout.activity_add_expense)
public class AddExpenseActivity extends AppCompatActivity {

    int numCharBeforeDot = 0;
    int numCharAfterDot = 0;

    @Bean
    CategoriesSpinnerAdapter categoriesSpinnerAdapter;

    @Bean
    DialogHelper dialogHelper;

    @Pref
    ApplicationPreferences_ prefs;

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
        setupWindowAnimations();

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.add_expense));

        expenseCommentLayout.setHint(getString(R.string.add_expense_comment, getResources().getInteger(R.integer.max_length_expense_name)));

        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        expenseDate.setText(dateFormat.format(new Date()));

        categorySpinner.setAdapter(categoriesSpinnerAdapter);

        numCharBeforeDot = getResources().getInteger(R.integer.num_char_before_dot);
        numCharAfterDot = getResources().getInteger(R.integer.num_char_after_dot);
    }

    @OptionsItem(android.R.id.home)
    void back() {
        finishCross();
    }

    @Click(R.id.date_field)
    void changeDate() {
        dialogHelper.showDatePickerDialogForTextView(expenseDate);
    }

    @Click(R.id.cancel_button)
    void cancel() {
        finishCross();
    }

    @TextChange(R.id.sum_field)
    void sumFieldChange(CharSequence text) {
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
        } else if (!checkExpenseSumValid()) {
            expenseSumLayout.setError(getString(R.string.error_sum_field, numCharBeforeDot, numCharAfterDot));
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
            finishCross();
        }
    }

    private boolean checkExpenseSumValid() {
        String pattern = ConstantManager.PATTERN_SUM_FIELD;
        Pattern p = Pattern.compile(String.format(pattern, numCharBeforeDot, numCharAfterDot));
        Matcher m = p.matcher(expenseSum.getText().toString());

        return m.matches();
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fadeTransition = new Fade();
            fadeTransition.setDuration(500);
            getWindow().setEnterTransition(fadeTransition);
        }
    }

    private void finishCross() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }
}