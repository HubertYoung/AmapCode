package com.ta.utdid2.core.persistent;

import java.util.Map;

public interface MySharedPreferences {

    public interface MyEditor {
        MyEditor clear();

        boolean commit();

        MyEditor putBoolean(String str, boolean z);

        MyEditor putFloat(String str, float f);

        MyEditor putInt(String str, int i);

        MyEditor putLong(String str, long j);

        MyEditor putString(String str, String str2);

        MyEditor remove(String str);
    }

    public interface OnSharedPreferenceChangeListener {
        void onSharedPreferenceChanged(MySharedPreferences mySharedPreferences, String str);
    }

    boolean checkFile();

    boolean contains(String str);

    MyEditor edit();

    Map<String, ?> getAll();

    boolean getBoolean(String str, boolean z);

    float getFloat(String str, float f);

    int getInt(String str, int i);

    long getLong(String str, long j);

    String getString(String str, String str2);

    void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener);

    void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener);
}
