package com.example.user.myapplication;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SettingsActivity extends PreferenceActivity {

    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);
        addPreferencesFromResource(R.xml.settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                finish();
            }
        });

      toolbar.setBackgroundColor(getToolBarColor());

    }

    protected void onResume(){
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener(){
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("pref_theme")){
                    recreate();
                } else if (key.equals("pref_address")){
                    updateBluetoothAddressSummary();
                } else if (key.equals("pref_toolbar_color")){
                    Toolbar toolbar = findViewById(R.id.toolbar);
                    toolbar.setBackgroundColor(getToolBarColor());;
                }
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(listener);
        updateBluetoothAddressSummary();
        updateThemeSummary();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribeSharedPreferencesChanges();
    }

    private int getToolBarColor(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        int color = preferences.getInt("pref_toolbar_color", Color.BLUE);
return color;
    }

    private void updateBluetoothAddressSummary(){

        Preference preference = findPreference("pref_address");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String value = preferences.getString("pref_address","ДЕФОЛТ");
        preference.setSummary(value);

    }

    private void updateThemeSummary(){
        Preference preference = findPreference("pref_theme");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String value = preferences.getString("pref_theme","ДЕФОЛТ");
        preference.setSummary(value);

    }

    private void updateTheme() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = preferences.getString("pref_theme", "Light");
        if (theme.equals("Light")) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppTheme_Dark);
        }
    }

    private void subscribeSharedPreferencesChanges(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("pref_theme")){
                    recreate();
                }
            }
        };
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    private void unsubscribeSharedPreferencesChanges(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

}
