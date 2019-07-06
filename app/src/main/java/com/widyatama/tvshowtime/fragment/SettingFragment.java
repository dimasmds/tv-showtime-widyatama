package com.widyatama.tvshowtime.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.widyatama.tvshowtime.R;
import com.widyatama.tvshowtime.reminder.DailyAlarmReceiver;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private DailyAlarmReceiver dailyAlarmReceiver = new DailyAlarmReceiver();

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.setting_preference);

        String dailyReminder = getString(R.string.pref_daily_reminder);
        String localizationPreference = getString(R.string.pref_language);
        String theme = getString(R.string.pref_theme);

        findPreference(dailyReminder).setOnPreferenceChangeListener(this);
        findPreference(theme).setOnPreferenceChangeListener(this);
        findPreference(localizationPreference).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        boolean isOn = (boolean) newValue;

        if (key.equals(getString(R.string.pref_daily_reminder))) {
            if (isOn) {
                dailyAlarmReceiver.setAlarm(getActivity(),
                        getString(R.string.daily_content_text));
            } else {
                dailyAlarmReceiver.cancelAlarm(getActivity());
            }
        } else {
            getActivity().recreate();
        }

        return true;

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(intent);
        return true;
    }
}
