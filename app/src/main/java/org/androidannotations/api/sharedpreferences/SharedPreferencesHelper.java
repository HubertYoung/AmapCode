package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;
import java.util.Set;

public abstract class SharedPreferencesHelper {
    private final SharedPreferences a;

    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.a = sharedPreferences;
    }

    public final SharedPreferences getSharedPreferences() {
        return this.a;
    }

    public final void clear() {
        SharedPreferencesCompat.apply(this.a.edit().clear());
    }

    /* access modifiers changed from: protected */
    public IntPrefField intField(String key, int defaultValue) {
        return new IntPrefField(this.a, key, Integer.valueOf(defaultValue));
    }

    /* access modifiers changed from: protected */
    public StringPrefField stringField(String key, String defaultValue) {
        return new StringPrefField(this.a, key, defaultValue);
    }

    /* access modifiers changed from: protected */
    public StringSetPrefField stringSetField(String key, Set<String> defaultValue) {
        return new StringSetPrefField(this.a, key, defaultValue);
    }

    /* access modifiers changed from: protected */
    public BooleanPrefField booleanField(String key, boolean defaultValue) {
        return new BooleanPrefField(this.a, key, Boolean.valueOf(defaultValue));
    }

    /* access modifiers changed from: protected */
    public FloatPrefField floatField(String key, float defaultValue) {
        return new FloatPrefField(this.a, key, Float.valueOf(defaultValue));
    }

    /* access modifiers changed from: protected */
    public LongPrefField longField(String key, long defaultValue) {
        return new LongPrefField(this.a, key, Long.valueOf(defaultValue));
    }
}
