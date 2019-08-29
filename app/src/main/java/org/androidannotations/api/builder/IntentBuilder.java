package org.androidannotations.api.builder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;
import org.androidannotations.api.builder.IntentBuilder;

public abstract class IntentBuilder<I extends IntentBuilder<I>> extends Builder {
    protected final Context context;
    protected final Intent intent;

    public IntentBuilder(Context context2, Class<?> clazz) {
        this(context2, new Intent(context2, clazz));
    }

    public IntentBuilder(Context context2, Intent intent2) {
        this.context = context2;
        this.intent = intent2;
    }

    public Context getContext() {
        return this.context;
    }

    public Intent get() {
        return this.intent;
    }

    public I flags(int flags) {
        this.intent.setFlags(flags);
        return this;
    }

    public I action(String action) {
        this.intent.setAction(action);
        return this;
    }

    public I extra(String name, boolean value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, byte value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, char value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, short value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, int value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, long value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, float value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, double value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, String value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, CharSequence value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, Parcelable value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, Parcelable[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I parcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        this.intent.putParcelableArrayListExtra(name, value);
        return this;
    }

    public I integerArrayListExtra(String name, ArrayList<Integer> value) {
        this.intent.putIntegerArrayListExtra(name, value);
        return this;
    }

    public I stringArrayListExtra(String name, ArrayList<String> value) {
        this.intent.putStringArrayListExtra(name, value);
        return this;
    }

    public I extra(String name, Serializable value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, boolean[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, byte[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, short[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, char[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, int[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, long[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, float[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, double[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, String[] value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extra(String name, Bundle value) {
        this.intent.putExtra(name, value);
        return this;
    }

    public I extras(Intent src) {
        this.intent.putExtras(src);
        return this;
    }
}
