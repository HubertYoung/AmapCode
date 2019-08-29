package com.alibaba.analytics.utils;

import android.text.TextUtils;
import java.util.Arrays;
import java.util.Comparator;

public class KeyArraySorter {
    private static KeyArraySorter s_instance = new KeyArraySorter();
    private ResourcesASCComparator mASCComparator = new ResourcesASCComparator();
    private ResourcesDESCComparator mDESCComparator = new ResourcesDESCComparator();

    class ResourcesASCComparator implements Comparator<String> {
        private ResourcesASCComparator() {
        }

        public int compare(String str, String str2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return 0;
            }
            return str.compareTo(str2);
        }
    }

    class ResourcesDESCComparator implements Comparator<String> {
        private ResourcesDESCComparator() {
        }

        public int compare(String str, String str2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return 0;
            }
            return str.compareTo(str2) * -1;
        }
    }

    private KeyArraySorter() {
    }

    public static KeyArraySorter getInstance() {
        return s_instance;
    }

    public String[] sortResourcesList(String[] strArr, boolean z) {
        Comparator comparator;
        if (z) {
            comparator = this.mASCComparator;
        } else {
            comparator = this.mDESCComparator;
        }
        if (comparator == null || strArr == null || strArr.length <= 0) {
            return null;
        }
        Arrays.sort(strArr, comparator);
        return strArr;
    }
}
