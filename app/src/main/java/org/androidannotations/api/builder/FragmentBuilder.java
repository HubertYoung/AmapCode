package org.androidannotations.api.builder;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import java.io.Serializable;
import java.util.ArrayList;
import org.androidannotations.api.builder.FragmentBuilder;

public abstract class FragmentBuilder<I extends FragmentBuilder<I, F>, F> extends Builder {
    protected Bundle args = new Bundle();

    public abstract F build();

    public I arg(Bundle map) {
        this.args.putAll(map);
        return this;
    }

    public I arg(String key, boolean value) {
        this.args.putBoolean(key, value);
        return this;
    }

    public I arg(String key, byte value) {
        this.args.putByte(key, value);
        return this;
    }

    public I arg(String key, char value) {
        this.args.putChar(key, value);
        return this;
    }

    public I arg(String key, short value) {
        this.args.putShort(key, value);
        return this;
    }

    public I arg(String key, int value) {
        this.args.putInt(key, value);
        return this;
    }

    public I arg(String key, long value) {
        this.args.putLong(key, value);
        return this;
    }

    public I arg(String key, float value) {
        this.args.putFloat(key, value);
        return this;
    }

    public I arg(String key, double value) {
        this.args.putDouble(key, value);
        return this;
    }

    public I arg(String key, String value) {
        this.args.putString(key, value);
        return this;
    }

    public I arg(String key, CharSequence value) {
        this.args.putCharSequence(key, value);
        return this;
    }

    public I arg(String key, Parcelable value) {
        this.args.putParcelable(key, value);
        return this;
    }

    public I arg(String key, Parcelable[] value) {
        this.args.putParcelableArray(key, value);
        return this;
    }

    public I parcelableArrayListArg(String key, ArrayList<? extends Parcelable> value) {
        this.args.putParcelableArrayList(key, value);
        return this;
    }

    public I arg(String key, SparseArray<? extends Parcelable> value) {
        this.args.putSparseParcelableArray(key, value);
        return this;
    }

    public I integerArrayListArg(String key, ArrayList<Integer> value) {
        this.args.putIntegerArrayList(key, value);
        return this;
    }

    public I stringArrayListArg(String key, ArrayList<String> value) {
        this.args.putStringArrayList(key, value);
        return this;
    }

    public I arg(String key, Serializable value) {
        this.args.putSerializable(key, value);
        return this;
    }

    public I arg(String key, boolean[] value) {
        this.args.putBooleanArray(key, value);
        return this;
    }

    public I arg(String key, byte[] value) {
        this.args.putByteArray(key, value);
        return this;
    }

    public I arg(String key, short[] value) {
        this.args.putShortArray(key, value);
        return this;
    }

    public I arg(String key, char[] value) {
        this.args.putCharArray(key, value);
        return this;
    }

    public I arg(String key, int[] value) {
        this.args.putIntArray(key, value);
        return this;
    }

    public I arg(String key, long[] value) {
        this.args.putLongArray(key, value);
        return this;
    }

    public I arg(String key, float[] value) {
        this.args.putFloatArray(key, value);
        return this;
    }

    public I arg(String key, double[] value) {
        this.args.putDoubleArray(key, value);
        return this;
    }

    public I arg(String key, String[] value) {
        this.args.putStringArray(key, value);
        return this;
    }

    public I arg(String key, Bundle value) {
        this.args.putBundle(key, value);
        return this;
    }

    public Bundle args() {
        return this.args;
    }
}
