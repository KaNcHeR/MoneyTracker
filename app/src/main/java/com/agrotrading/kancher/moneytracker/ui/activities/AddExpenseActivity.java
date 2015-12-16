package com.agrotrading.kancher.moneytracker.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.activeandroid.query.Select;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.adapters.CategoriesSpinnerAdapter;
import com.agrotrading.kancher.moneytracker.database.Categories;

import org.androidannotations.annotations.AfterViews;
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

    @ViewById(R.id.categories_spinner)
    Spinner categorySpinner;

    @ViewById(R.id.date_picker)
    DatePicker datePicker;

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

        setupCategorySpinner();
        setTitle(getString(R.string.add_expense));
    }

    private void setupCategorySpinner() {
        CategoriesSpinnerAdapter categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this, getDataList());
        categorySpinner.setAdapter(categoriesSpinnerAdapter);
    }

    private List<Categories> getDataList() {
        return new Select()
                .from(Categories.class)
                .execute();
    }

}