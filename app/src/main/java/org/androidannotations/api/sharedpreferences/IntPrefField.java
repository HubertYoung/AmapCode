package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;

public final class IntPrefField extends AbstractPrefField<Integer> {
    IntPrefField(SharedPreferences sharedPreferences, String key, Integer defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    public final Integer getOr(Integer defaultValue) {
        try {
            return Integer.valueOf(this.sharedPreferences.getInt(this.key, defaultValue.intValue()));
        } catch (ClassCastException e) {
            try {
                return Integer.valueOf(Integer.parseInt(this.sharedPreferences.getString(this.key, String.valueOf(defaultValue))));
            } catch (Exception e2) {
                throw e;
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void putInternal(Integer value) {
        apply(edit().putInt(this.key, value.intValue()));
    }
}
