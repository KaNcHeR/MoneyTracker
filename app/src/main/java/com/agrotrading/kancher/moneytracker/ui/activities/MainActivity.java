package com.agrotrading.kancher.moneytracker.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.Expenses;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.event.MessageEvent;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenUserDataModel;
import com.agrotrading.kancher.moneytracker.sync.TrackerSyncAdapter;
import com.agrotrading.kancher.moneytracker.ui.fragments.CategoriesFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.ExpensesFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.SettingsFragment;
import com.agrotrading.kancher.moneytracker.ui.fragments.StatisticsFragment_;
import com.agrotrading.kancher.moneytracker.utils.ApplicationPreferences_;
import com.agrotrading.kancher.moneytracker.utils.DrawerHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private Fragment fragment;

    @Bean
    DrawerHelper drawerHelper;

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

    ImageView pictureImageView;

    @AfterViews
    void ready() {

        GoogleTokenUserDataModel accountData;
        setupToolbar();
        setupDrawer();
        //createCategories();

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.main_container, new ExpensesFragment_()).commit();
        }

        drawerHelper.fillDrawerHeader();
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            fragment.setEnterTransition(animationShowCategories());
                        }
                        break;
                    case R.id.drawer_statistics:
                        fragment = new StatisticsFragment_();
                        break;
                    case R.id.drawer_settings:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.drawer_logout:
                        logout();
                        finish();
                        break;
                }

                getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void logout() {
        MoneyTrackerApplication.setGoogleToken(this, ConstantManager.DEFAULT_GOOGLE_TOKEN);
        MoneyTrackerApplication.setAuthToken(null);
        UserLoginActivity_.intent(this).start();
    }

    void createCategories() {

        Categories category = new Categories("Electronics");
        category.save();
        new Expenses(100.0, "TV1", "24/01/2016", category).save();
        new Expenses(200.0, "TV2", "24/01/2016", category).save();
        new Expenses(300.0, "TV3", "24/01/2016", category).save();
        new Expenses(400.0, "TV4", "24/01/2016", category).save();

        category = new Categories("Fun");
        category.save();
        new Expenses(100.0, "Rubber duck1", "2016.01.24", category).save();
        new Expenses(100.0, "Rubber duck2", "2016.01.24", category).save();
        new Expenses(200.0, "Rubber duck3", "2016.01.24", category).save();
        new Expenses(100.0, "Rubber duck4", "2016.01.24", category).save();

        category = new Categories("Food");
        category.save();
        new Expenses(100.0, "Hamburger1", "2016.01.24", category).save();
        new Expenses(100.0, "Hamburger2", "2016.01.24", category).save();
        new Expenses(300.0, "Hamburger3", "2016.01.24", category).save();
        new Expenses(100.0, "Hamburger4", "2016.01.24", category).save();

        category = new Categories("Telephone");
        category.save();
        new Expenses(100.0, "Samsung Galaxy S61", "2016.01.24", category).save();
        new Expenses(100.0, "Samsung Galaxy S62", "2016.01.24", category).save();
        new Expenses(1000.0, "Samsung Galaxy S63", "2016.01.24", category).save();
        new Expenses(100.0, "Samsung Galaxy S64", "2016.01.24", category).save();

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

        Fragment findingFragment = getFragmentManager().findFragmentById(R.id.main_container);

        if(findingFragment != null) {
            int itemId = R.id.drawer_expenses;

            if(findingFragment instanceof ExpensesFragment_) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                itemId = R.id.drawer_expenses;
            } else if(findingFragment instanceof CategoriesFragment_) {
                itemId = R.id.drawer_categories;
            } else if(findingFragment instanceof StatisticsFragment_) {
                itemId = R.id.drawer_statistics;
            } else if(findingFragment instanceof SettingsFragment) {
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

    public NavigationView getNavigationView() {
        return navigationView;
    }

    private Slide animationShowCategories() {

        Slide slide = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide(Gravity.TOP);
            slide.setDuration(600);
        }
        return slide;
    }
}
