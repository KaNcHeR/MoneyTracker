package com.agrotrading.kancher.moneytracker.ui.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Categories;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.dialog_window)
public class AddCategoryDialogFragment extends DialogFragment {

    AddingCategoryListener addingCategoryListener;

    public void initListener(AddingCategoryListener addingCategoryListener) {
        this.addingCategoryListener = addingCategoryListener;
    }

    @ViewById(R.id.til_category)
    TextInputLayout categoryFieldLayout;

    @ViewById(R.id.category_field)
    EditText categoryField;

    @AfterViews
    void init() {
        categoryFieldLayout.setHint(getString(R.string.add_category_field_title, getResources().getInteger(R.integer.max_length_category_name)));
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Click(R.id.add_button)
    void addCategory() {

        if(categoryField.length() == 0) {
            categoryFieldLayout.setError(getString(R.string.error_required_field));
            return;
        }

        Categories category = new Categories(categoryField.getText().toString());
        category.save();

        if(addingCategoryListener != null) {
            addingCategoryListener.onCategoryAdded(category);
        }

        dismiss();
    }

    @Click(R.id.cancel_button)
    void closeDialog() {
        dismiss();
    }

    public interface AddingCategoryListener {
        void onCategoryAdded(Categories category);
    }
}
