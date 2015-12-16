package com.agrotrading.kancher.moneytracker.ui.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.activeandroid.query.Select;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.adapters.CategoriesSpinnerAdapter;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.Expenses;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.List;

@EActivity(R.layout.activity_add_expense)
public class AddExpenseActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.date_field)
    EditText expenseDate;

    @ViewById(R.id.sum_field)
    EditText expenseSum;

    @ViewById(R.id.comment_field)
    EditText expenseComment;

    @ViewById(R.id.categories_spinner)
    Spinner categorySpinner;

    @ViewById(R.id.date_picker)
    DatePicker datePicker;

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

        Calendar calendar = Calendar.getInstance();

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                        expenseDate.setText(String.format(getString(R.string.date_format), dayOfMonth, monthOfYear + 1, year));
                    }
                }
        );

        expenseDate.setText(String.format(getString(R.string.date_format), datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear()));

        setTitle(getString(R.string.add_expense));
    }

    @AfterViews
    void bindAdapter() {
        categorySpinner.setAdapter(categoriesSpinnerAdapter);
    }

    @Click(R.id.cancel_button)
    void cancel() {
        back();
    }

    @Click(R.id.add_button)
    void addExpense(View clickedView) {
        if(expenseSum.length() == 0 || expenseComment.length() == 0) {
            Snackbar.make(clickedView, getString(R.string.error_required_field), Snackbar.LENGTH_SHORT).show();
            return;
        }
        Categories category = (Categories) categorySpinner.getSelectedItem();
        Expenses expense = new Expenses(expenseSum.getText().toString(), expenseComment.getText().toString(), expenseDate.getText().toString(), category);
        expense.save();
        back();
    }

    private List<Categories> getDataList() {
        return new Select()
                .from(Categories.class)
                .execute();
    }

}