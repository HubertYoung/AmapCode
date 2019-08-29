package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

public final class LongPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    LongPrefEditorField(T editorHelper, String key) {
        super(editorHelper, key);
    }

    public final T put(long value) {
        this.editorHelper.getEditor().putLong(this.key, value);
        return this.editorHelper;
    }
}
