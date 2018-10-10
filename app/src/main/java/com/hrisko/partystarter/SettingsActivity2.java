package com.hrisko.partystarter;

import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class SettingsActivity2 extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }

    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int preferenceFile_toLoad = -1;
            String settings = getArguments().getString("settings");
            if ("prefs_general".equalsIgnoreCase(settings)) {
                // Load the preferences from an XML resource
                preferenceFile_toLoad = R.xml.pref_general;
            } else if ("prefs_notification".equalsIgnoreCase(settings)) {
                // Load the preferences from an XML resource
                preferenceFile_toLoad = R.xml.pref_notification;
            } else if ("prefs_sync".equals(settings)) {
                // Load the preferences from an XML resource
                preferenceFile_toLoad = R.xml.pref_data_sync;
            }

            addPreferencesFromResource(preferenceFile_toLoad);
        }
    }

}
