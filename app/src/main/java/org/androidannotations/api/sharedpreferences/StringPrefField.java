package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;

public final class StringPrefField extends AbstractPrefField<String> {
    StringPrefField(SharedPreferences sharedPreferences, String key, String defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    public final String getOr(String defaultValue) {
        return this.sharedPreferences.getString(this.key, defaultValue);
    }

    /* access modifiers changed from: protected */
    public final void putInternal(String value) {
        apply(edit().putString(this.key, value));
    }
}
