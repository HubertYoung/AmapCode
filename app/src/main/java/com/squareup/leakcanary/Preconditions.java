package com.squareup.leakcanary;

final class Preconditions {
    static <T> T checkNotNull(T t, String str) {
        if (t != null) {
            return t;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" must not be null");
        throw new NullPointerException(sb.toString());
    }

    private Preconditions() {
        throw new AssertionError();
    }
}
