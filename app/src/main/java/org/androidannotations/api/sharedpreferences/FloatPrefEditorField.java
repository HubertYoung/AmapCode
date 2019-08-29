package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

public final class FloatPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    FloatPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public final T put(float value) {
        this.editorHelper.getEditor().putFloat(this.key, value);
        return this.editorHelper;
    }
}
