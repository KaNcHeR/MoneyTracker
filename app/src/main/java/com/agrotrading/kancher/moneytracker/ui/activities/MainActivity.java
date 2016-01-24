package com.agrotrading.kancher.moneytracker.ui.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.Expenses;
import com.agrotrading.kancher.moneytracker.event.MessageEvent;
import com.agrotrading.kancher.moneytracker.sync.TrackerSyncAdapter;
import com.agrotrading.kancher.moneytracker.ui.fragments.CategoriesFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.ExpensesFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.SettingsFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.StatisticsFragment_;
import com.agrotrading.kancher.moneytracker.utils.ApplicationPreferences_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Fragment fragment;

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.navigation_view)
    NavigationView navigationView;

    @InstanceState
    Bundle savedInstanceState;

    @Pref
    static ApplicationPreferences_ prefs;

    @AfterViews
    void ready() {
        setupToolbar();
        setupDrawer();
        createCategories();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ExpensesFragment_()).commit();
        }
        TrackerSyncAdapter.initializeSyncAdapter(this);
    }

    @OptionsItem(android.R.id.home)
    void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void setupToolbar() {

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.add_expense));
    }


    private void setupDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.drawer_expenses:
                        fragment = new ExpensesFragment_();
                        break;
                    case R.id.drawer_categories:
                        fragment = new CategoriesFragment_();
                        break;
                    case R.id.drawer_statistics:
                        fragment = new StatisticsFragment_();
                        break;
                    case R.id.drawer_settings:
                        fragment = new SettingsFragment_();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @Background
    void createCategories() {

        Categories category = new Categories("Electronics");
        category.save();
        new Expenses(100.0, "TV", "2016-24-01", category).save();

        category = new Categories("Fun");
        category.save();
        new Expenses(100.0, "Rubber duck", "2016-23-01", category).save();

        category = new Categories("Food");
        category.save();
        new Expenses(100.0, "Hamburger", "2016-22-01", category).save();

        category = new Categories("Telephone");
        category.save();
        new Expenses(100.0, "Samsung Galaxy S6", "2016-21-01", category).save();

        prefs.needSyncCategories().put(true);
        prefs.needSyncExpenses().put(true);
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
            return;
        }

        super.onBackPressed();

        Fragment findingFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if(findingFragment != null) {
            int itemId = R.id.drawer_expenses;

            if(findingFragment instanceof ExpensesFragment_) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                itemId = R.id.drawer_expenses;
            } else if(findingFragment instanceof CategoriesFragment_) {
                itemId = R.id.drawer_categories;
            } else if(findingFragment instanceof StatisticsFragment_) {
                itemId = R.id.drawer_statistics;
            } else if(findingFragment instanceof SettingsFragment_) {
                itemId = R.id.drawer_settings;
            }

            navigationView.getMenu().findItem(itemId).setChecked(true);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(MessageEvent event){

        switch (event.code) {
            case MessageEvent.MOVE_USER_TO_LOGIN:
                UserLoginActivity_.intent(this).start();
                finish();
                break;
            default:
                break;
        }

    }

}
