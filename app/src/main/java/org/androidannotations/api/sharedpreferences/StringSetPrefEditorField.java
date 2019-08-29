package org.androidannotations.api.sharedpreferences;

import java.util.Set;
import org.androidannotations.api.sharedpreferences.EditorHelper;

public final class StringSetPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    StringSetPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public final T put(Set<String> value) {
        SharedPreferencesCompat.putStringSet(this.editorHelper.getEditor(), this.key, value);
        return this.editorHelper;
    }
}
