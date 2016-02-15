package com.agrotrading.kancher.moneytracker.ui.activities;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.sync.TrackerSyncAdapter;
import com.agrotrading.kancher.moneytracker.ui.fragments.CategoriesFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.ExpensesFragment_;
import com.agrotrading.kancher.moneytracker.ui.fragments.SettingsFragment;
import com.agrotrading.kancher.moneytracker.ui.fragments.StatisticsFragment_;
import com.agrotrading.kancher.moneytracker.utils.ApplicationPreferences_;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.DBRestBridge;
import com.agrotrading.kancher.moneytracker.utils.DialogHelper;
import com.agrotrading.kancher.moneytracker.utils.DrawerHelper;
import com.agrotrading.kancher.moneytracker.utils.event.MessageEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    DrawerHelper drawerHelper;

    @Bean
    DBRestBridge dbRestBridge;

    @Bean
    DialogHelper dialogHelper;

    @InstanceState
    Bundle savedInstanceState;

    @Pref
    ApplicationPreferences_ prefs;

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.navigation_view)
    NavigationView navigationView;

    @AfterViews
    void ready() {

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(R.id.main_container, new ExpensesFragment_()).commit();
            drawerHelper.initDrawerItemIdStack(R.id.drawer_expenses);
        }

        setupToolbar();
        setupDrawer();

        if (prefs.firstStartAfterAuth().get()) {
            TrackerSyncAdapter.initializeSyncAdapter(this);
            prefs.firstStartAfterAuth().put(false);
        }
    }

    @OptionsItem(android.R.id.home)
    void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void setupToolbar() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setTitle(getString(R.string.add_expense));
    }

    private void setupDrawer() {

        drawerHelper.fillDrawerHeader();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment fragment;

                drawerLayout.closeDrawers();
                drawerHelper.addToDrawerItemIdStack(menuItem.setChecked(true).getItemId());

                switch (menuItem.getItemId()) {
                    case R.id.drawer_expenses:
                        fragment = new ExpensesFragment_();
                        break;
                    case R.id.drawer_categories:
                        fragment = new CategoriesFragment_();
                        animationShowCategories(fragment);
                        break;
                    case R.id.drawer_statistics:
                        fragment = new StatisticsFragment_();
                        break;
                    case R.id.drawer_settings:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.drawer_logout:
                        logoutInit();
                        return false;
                    default:
                        fragment = new ExpensesFragment_();
                        break;
                }

                getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                return false;
            }
        });
    }

    void logoutInit() {

        dialogHelper.showAlertDialog(R.string.title_dialog_logout, R.string.message_dialog_logout, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialogHelper.showProgressDialog(getString(R.string.progress_dialog_sync));
                dbRestBridge.initSync();
                logout();
            }
        });
    }

    @Background(serial = "sync")
    void logout() {

        if (!prefs.needSyncCategories().get() && !prefs.needSyncExpenses().get()) {

            MoneyTrackerApplication.setGoogleToken(this, ConstantManager.DEFAULT_GOOGLE_TOKEN);
            MoneyTrackerApplication.setAuthToken(null);
            TrackerSyncAdapter.onRemoveSync(this);
            Categories.truncate();
            prefs.clear();

            UserLoginActivity_.intent(this).start();
            finish();

            return;
        }

        dialogHelper.hideProgressDialog();
        Snackbar.make(drawerLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Fragment findingFragment = getFragmentManager().findFragmentById(R.id.main_container);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(findingFragment != null && findingFragment instanceof ExpensesFragment_) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
            navigationView.getMenu().findItem(drawerHelper.getCheckedId()).setChecked(true);
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

    public void onEventMainThread(MessageEvent event) {

        switch (event.code) {
            case MessageEvent.MOVE_USER_TO_LOGIN:
                UserLoginActivity_.intent(this).start();
                finish();
                break;
            default:
                break;
        }
    }

    private void animationShowCategories(Fragment fragment) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.TOP);
            slide.setDuration(600);
            fragment.setEnterTransition(slide);
        }
    }
}