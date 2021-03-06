package com.agrotrading.kancher.moneytracker.ui.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.ViewWrapper;
import com.agrotrading.kancher.moneytracker.adapters.CategoriesAdapter;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.utils.ApplicationPreferences_;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.DialogHelper;
import com.agrotrading.kancher.moneytracker.utils.DividerItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

@EFragment(R.layout.categories_fragment)
@OptionsMenu(R.menu.search_menu)
public class CategoriesFragment extends Fragment {

    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;

    @Pref
    ApplicationPreferences_ prefs;

    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @Bean
    CategoriesAdapter categoriesAdapter;

    @Bean
    DialogHelper dialogHelper;

    @ViewById(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.context_recyclerview)
    RecyclerView categoriesRecyclerView;

    @AfterViews
    void ready() {
        getActivity().setTitle(getString(R.string.nav_drawer_categories));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);
        categoriesRecyclerView.setHasFixedSize(true);
        categoriesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
    }

    @Click(R.id.fab)
    void showAddCategoryDialog() {
        dialogHelper.showAddCategoryDialogFragment(new AddCategoryDialogFragment.AddingCategoryListener() {
            @Override
            public void onCategoryAdded(Categories category) {
                categoriesAdapter.insertItemNameAsc(category);
                prefs.needSyncCategories().put(true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData("");
        initSwipeToRefresh();
    }

    private void initSwipeToRefresh() {
        swipeRefreshLayout.setColorSchemeColors(R.color.swipe_refresh_layout_scheme_color_1,
                R.color.swipe_refresh_layout_scheme_color_2,
                R.color.swipe_refresh_layout_scheme_color_3);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData("");
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                BackgroundExecutor.cancelAll(ConstantManager.FILTER_ID, true);
                delayedQuery(newText);
                return false;
            }
        });
    }

    @Background(delay = ConstantManager.FILTER_DELAY, id = ConstantManager.FILTER_ID)
    public void delayedQuery(String filter) {
        loadData(filter);
    }

    private void loadData(final String filter) {
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {
            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(getActivity()) {
                    @Override
                    public List<Categories> loadInBackground() {
                        return Categories.getAllCategories(filter);
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {
                swipeRefreshLayout.setRefreshing(false);
                categoriesAdapter.init(data, new ViewWrapper.ClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean onItemLongClicked(int position) {
                        if (actionMode == null) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            actionMode = activity.startSupportActionMode(actionModeCallback);
                        }
                        toggleSelection(position);
                        return true;
                    }
                });
                categoriesRecyclerView.setAdapter(categoriesAdapter);
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {}
        });
    }

    private void toggleSelection(int position) {
        categoriesAdapter.toggleSelection(position);
        int count = categoriesAdapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(getString(R.string.contextual_action_bar_title, count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_action_bar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    categoriesAdapter.removeItems(categoriesAdapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            categoriesAdapter.clearSelection();
            actionMode = null;
        }
    }
}
