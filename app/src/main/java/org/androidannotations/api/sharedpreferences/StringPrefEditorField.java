package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

public final class StringPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    StringPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public final T put(String value) {
        this.editorHelper.getEditor().putString(this.key, value);
        return this.editorHelper;
    }
}
