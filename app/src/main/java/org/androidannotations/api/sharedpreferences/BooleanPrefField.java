package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;

public final class BooleanPrefField extends AbstractPrefField<Boolean> {
    BooleanPrefField(SharedPreferences sharedPreferences, String key, Boolean defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    public final Boolean getOr(Boolean defaultValue) {
        return Boolean.valueOf(this.sharedPreferences.getBoolean(this.key, defaultValue.booleanValue()));
    }

    /* access modifiers changed from: protected */
    public final void putInternal(Boolean value) {
        apply(edit().putBoolean(this.key, value.booleanValue()));
    }
}
