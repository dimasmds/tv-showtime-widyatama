package com.widyatama.tvshowtime.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.widyatama.tvshowtime.utils.ThemeUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.themeState(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean oldState = getSharedPreferences(ThemeUtils.themeState, Context.MODE_PRIVATE).getBoolean(ThemeUtils.themeStateKey, false);
        boolean newState = ThemeUtils.getState(this);

        if (oldState != newState) {
            ThemeUtils.setState(this, newState);
            recreate();
        }

        ThemeUtils.themeState(this);
    }
}
