package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

public final class IntPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    IntPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public final T put(int value) {
        this.editorHelper.getEditor().putInt(this.key, value);
        return this.editorHelper;
    }
}
