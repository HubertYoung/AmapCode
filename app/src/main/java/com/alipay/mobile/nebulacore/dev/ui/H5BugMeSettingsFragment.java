package com.alipay.mobile.nebulacore.dev.ui;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.Nebula;

public class H5BugMeSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.h5_bugme_setting);
        a(getPreferenceScreen());
        try {
            if (!Nebula.DEBUG) {
                PreferenceCategory category = (PreferenceCategory) findPreference("h5_bugme_advance_category");
                if (getPreferenceScreen() != null) {
                    getPreferenceScreen().removePreference(category);
                }
            }
        } catch (Throwable throwable) {
            H5Log.e("H5BugMeSettingsFragment", "Remove debug setting", throwable);
        }
    }

    public void onResume() {
        super.onResume();
        if (getPreferenceScreen() != null && getPreferenceScreen().getSharedPreferences() != null) {
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }
    }

    public void onPause() {
        super.onPause();
        if (getPreferenceScreen() != null && getPreferenceScreen().getSharedPreferences() != null) {
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        b(findPreference(key));
    }

    private void a(Preference preference) {
        if (preference instanceof PreferenceGroup) {
            PreferenceGroup group = (PreferenceGroup) preference;
            for (int index = 0; index < group.getPreferenceCount(); index++) {
                a(group.getPreference(index));
            }
            return;
        }
        b(preference);
    }

    private static void b(Preference preference) {
        if (preference instanceof ListPreference) {
            preference.setSummary(((ListPreference) preference).getEntry());
        } else if (preference instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference) preference;
            if (preference.getTitle().toString().contains("password")) {
                preference.setSummary("******");
            } else {
                preference.setSummary(editTextPref.getText());
            }
        } else if (preference instanceof MultiSelectListPreference) {
            preference.setSummary(((EditTextPreference) preference).getText());
        }
    }
}
