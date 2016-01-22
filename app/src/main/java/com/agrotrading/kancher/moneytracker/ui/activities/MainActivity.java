package com.agrotrading.kancher.moneytracker.ui.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.event.MessageEvent;
import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.category.CategoryData;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoryModel;
import com.agrotrading.kancher.moneytracker.sync.TrackerSyncAdapter;
import com.agrotrading.kancher.moneytracker.ui.fragments.CategoriesFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.ExpensesFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.SettingsFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.StatisticsFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

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

        try {
            saveAndRest(new Categories("Fun"));
            saveAndRest(new Categories("Electronics"));
            saveAndRest(new Categories("Food"));
            saveAndRest(new Categories("Telephone"));
        } catch (UnauthorizedException e) {
            UserLoginActivity_.intent(this).start();
            finish();
        }

    }

    public void saveAndRest(Categories category) throws UnauthorizedException {

        RestService restService = new RestService();
        UserCategoryModel createCategory = restService.createCategory(category.name);
        CategoryData data = createCategory.getData();
        Log.d(LOG_TAG, "Status: " + createCategory.getStatus() + ", title: " + data.getTitle() + ", id: " + data.getId());
        category.save();

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
