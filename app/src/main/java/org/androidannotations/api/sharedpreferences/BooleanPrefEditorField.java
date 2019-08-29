package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

public final class BooleanPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    BooleanPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public final T put(boolean value) {
        this.editorHelper.getEditor().putBoolean(this.key, value);
        return this.editorHelper;
    }
}
