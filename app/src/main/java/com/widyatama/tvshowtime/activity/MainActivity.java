package com.widyatama.tvshowtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.widyatama.tvshowtime.R;
import com.widyatama.tvshowtime.adapter.pager.TVShowsPagerAdapter;
import com.widyatama.tvshowtime.fragment.TVShowListFragment;

import java.util.Objects;

public class MainActivity extends BaseActivity {

    AppBarLayout appbar;
    ViewPager viewPager;
    BottomNavigationView bottomNavigation;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        appbar = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.mainActivityViewPager);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        TVShowsPagerAdapter pagerAdapter = new TVShowsPagerAdapter(getSupportFragmentManager());
        TVShowListFragment airingToday = new TVShowListFragment();
        Bundle args1 = new Bundle();
        args1.putInt(TVShowListFragment.POSITION, 0);
        airingToday.setArguments(args1);

        TVShowListFragment onTheAir = new TVShowListFragment();
        Bundle args2 = new Bundle();
        args2.putInt(TVShowListFragment.POSITION, 1);
        onTheAir.setArguments(args2);

        TVShowListFragment favorite = new TVShowListFragment();
        Bundle args3 = new Bundle();
        args3.putInt(TVShowListFragment.POSITION, 2);
        favorite.setArguments(args3);

        pagerAdapter.addFragment(airingToday);
        pagerAdapter.addFragment(onTheAir);
        pagerAdapter.addFragment(favorite);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearch:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.menuSettings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0);
        } else {
            finish();
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                bottomNavigation.getMenu().getItem(0).setChecked(false);
            }

            bottomNavigation.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNavigation.getMenu().getItem(position);
            resetToolbar();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuAiring:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.menuOnTheAir:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.menuFavorite:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return false;
        }
    };

    public void resetToolbar() {
        appbar.setExpanded(true, true);
    }
}