package com.agrotrading.kancher.moneytracker.ui.activities;

import android.app.Fragment;
import android.content.DialogInterface;
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
import android.view.View;

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
import org.androidannotations.annotations.UiThread;
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
        setupWindowAnimations();

        if (savedInstanceState == null) {
            Fragment fragment = new ExpensesFragment_();
            applyAnimation(fragment);
            drawerHelper.initDrawerItemIdStack(R.id.drawer_expenses);
            getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
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
                        fragment = new SettingsFragment();
                        break;
                    case R.id.drawer_logout:
                        logoutInit();
                        return false;
                    default:
                        fragment = new ExpensesFragment_();
                        break;
                }
                drawerHelper.addToDrawerItemIdStack(menuItem.setChecked(true).getItemId());
                applyAnimation(fragment);
                getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                return false;
            }
        });
    }

    private void applyAnimation(Fragment fragment) {
        Slide slide = new Slide(Gravity.RIGHT);
        slide.setDuration(300);
        fragment.setEnterTransition(slide);
        fragment.setExitTransition(slide);
    }

    void logoutInit() {
        int rId = R.string.message_dialog_logout;
        boolean sync = true;

        if (!prefs.needSyncCategories().get() && !prefs.needSyncExpenses().get()) {
            rId = R.string.message_dialog_logout_without_sync;
            sync = false;
        }

        final boolean finalSync = sync;
        dialogHelper.showAlertDialog(R.string.title_dialog_logout, rId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (finalSync) {
                    dialogHelper.showProgressDialog(getString(R.string.progress_dialog_sync));
                    dbRestBridge.initSync();
                }
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
        }
    }

    @Override
    public void onBackPressed() {
        Fragment findingFragment = getFragmentManager().findFragmentById(R.id.main_container);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (findingFragment != null && findingFragment instanceof ExpensesFragment_) {
            super.onBackPressed();
            finish();
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

    @UiThread
    void showSnackBar(int resId) {
        View view;
        Fragment findingFragment = getFragmentManager().findFragmentById(R.id.main_container);
        if (findingFragment.getView() != null) {
            view = findingFragment.getView();
        } else {
            view = drawerLayout;
        }
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show();
    }

    public void onEventMainThread(MessageEvent event) {
        dialogHelper.hideProgressDialog();
        switch (event.code) {
            case MessageEvent.MOVE_USER_TO_LOGIN:
                UserLoginActivity_.intent(this).start();
                finish();
                break;
            case MessageEvent.ALERT_NO_INTERNET:
                showSnackBar(R.string.network_not_available);
                break;
            case MessageEvent.CONNECTION_TIMEOUT:
                showSnackBar(R.string.connection_timeout);
                break;
            case MessageEvent.SERVER_NOT_RESPOND:
                showSnackBar(R.string.server_error);
                break;
        }
    }

    private void setupWindowAnimations() {
        Slide slideTransition = new Slide(Gravity.LEFT);
        slideTransition.setDuration(500);
        getWindow().setEnterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }
}