package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Set;

public final class StringSetPrefField extends AbstractPrefField<Set<String>> {
    StringSetPrefField(SharedPreferences sharedPreferences, String key, Set<String> defaultValue) {
        super(sharedPreferences, key, defaultValue);
    }

    public final Set<String> getOr(Set<String> defaultValue) {
        return SharedPreferencesCompat.getStringSet(this.sharedPreferences, this.key, defaultValue);
    }

    /* access modifiers changed from: protected */
    public final void putInternal(Set<String> value) {
        Editor editor = this.sharedPreferences.edit();
        SharedPreferencesCompat.putStringSet(editor, this.key, value);
        apply(editor);
    }
}
