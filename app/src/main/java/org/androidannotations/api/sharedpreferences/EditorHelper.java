package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.androidannotations.api.sharedpreferences.EditorHelper;

public abstract class EditorHelper<T extends EditorHelper<T>> {
    private final Editor a;

    public EditorHelper(SharedPreferences sharedPreferences) {
        this.a = sharedPreferences.edit();
    }

    /* access modifiers changed from: protected */
    public Editor getEditor() {
        return this.a;
    }

    public final T clear() {
        this.a.clear();
        return a();
    }

    public final void apply() {
        SharedPreferencesCompat.apply(this.a);
    }

    /* access modifiers changed from: protected */
    public IntPrefEditorField<T> intField(String key) {
        return new IntPrefEditorField<>(a(), key);
    }

    /* access modifiers changed from: protected */
    public StringPrefEditorField<T> stringField(String key) {
        return new StringPrefEditorField<>(a(), key);
    }

    /* access modifiers changed from: protected */
    public StringSetPrefEditorField<T> stringSetField(String key) {
        return new StringSetPrefEditorField<>(a(), key);
    }

    /* access modifiers changed from: protected */
    public BooleanPrefEditorField<T> booleanField(String key) {
        return new BooleanPrefEditorField<>(a(), key);
    }

    /* access modifiers changed from: protected */
    public FloatPrefEditorField<T> floatField(String key) {
        return new FloatPrefEditorField<>(a(), key);
    }

    /* access modifiers changed from: protected */
    public LongPrefEditorField<T> longField(String key) {
        return new LongPrefEditorField<>(a(), key);
    }

    private T a() {
        return this;
    }
}
