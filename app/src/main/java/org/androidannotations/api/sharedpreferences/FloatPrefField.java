package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;

public final class FloatPrefField extends AbstractPrefField<Float> {
    FloatPrefField(SharedPreferences sharedPreferences, String key, Float defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    public final Float getOr(Float defaultValue) {
        try {
            return Float.valueOf(this.sharedPreferences.getFloat(this.key, defaultValue.floatValue()));
        } catch (ClassCastException e) {
            try {
                return Float.valueOf(Float.parseFloat(this.sharedPreferences.getString(this.key, String.valueOf(defaultValue))));
            } catch (Exception e2) {
                throw e;
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void putInternal(Float value) {
        apply(edit().putFloat(this.key, value.floatValue()));
    }
}
