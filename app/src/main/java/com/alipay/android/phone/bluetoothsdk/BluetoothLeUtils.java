package com.alipay.android.phone.bluetoothsdk;

import android.util.SparseArray;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BluetoothLeUtils {
    static String toString(List<String> list) {
        if (list == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        for (int i = 0; i < list.size(); i++) {
            buffer.append(list.get(i));
            if (i != list.size() - 1) {
                buffer.append(',');
            }
        }
        buffer.append(']');
        return buffer.toString();
    }

    static String toString(SparseArray<byte[]> array) {
        if (array == null) {
            return "null";
        }
        if (array.size() == 0) {
            return bny.c;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append('{');
        for (int i = 0; i < array.size(); i++) {
            buffer.append(array.keyAt(i)).append("=").append(Arrays.toString(array.valueAt(i)));
        }
        buffer.append('}');
        return buffer.toString();
    }

    static <T> String toString(Map<T, String> map) {
        if (map == null) {
            return "null";
        }
        if (map.isEmpty()) {
            return bny.c;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append('{');
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Object key = it.next().getKey();
            buffer.append(key).append("=").append(map.get(key));
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append('}');
        return buffer.toString();
    }

    static boolean equals(SparseArray<byte[]> array, SparseArray<byte[]> otherArray) {
        if (array == otherArray) {
            return true;
        }
        if (array == null || otherArray == null) {
            return false;
        }
        if (array.size() != otherArray.size()) {
            return false;
        }
        for (int i = 0; i < array.size(); i++) {
            if (array.keyAt(i) != otherArray.keyAt(i) || !Arrays.equals(array.valueAt(i), otherArray.valueAt(i))) {
                return false;
            }
        }
        return true;
    }
}
