package org.androidannotations.api.bundle;

import android.os.Bundle;
import android.os.Parcelable;
import java.lang.reflect.Array;

public final class BundleHelper {
    private BundleHelper() {
    }

    public static <T extends Parcelable> T[] getParcelableArray(Bundle bundle, String key, Class<T[]> type) {
        Parcelable[] value = bundle.getParcelableArray(key);
        if (value == null) {
            return null;
        }
        Object copy = Array.newInstance(type.getComponentType(), value.length);
        System.arraycopy(value, 0, copy, 0, value.length);
        return (Parcelable[]) copy;
    }
}
