package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;

public final class LongPrefField extends AbstractPrefField<Long> {
    LongPrefField(SharedPreferences sharedPreferences, String key, Long defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    public final Long getOr(Long defaultValue) {
        try {
            return Long.valueOf(this.sharedPreferences.getLong(this.key, defaultValue.longValue()));
        } catch (ClassCastException e) {
            try {
                return Long.valueOf(Long.parseLong(this.sharedPreferences.getString(this.key, String.valueOf(defaultValue))));
            } catch (Exception e2) {
                throw e;
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void putInternal(Long value) {
        apply(edit().putLong(this.key, value.longValue()));
    }
}
