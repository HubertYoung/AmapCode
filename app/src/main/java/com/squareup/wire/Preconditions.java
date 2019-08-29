package com.squareup.wire;

final class Preconditions {
    private Preconditions() {
    }

    static void checkNotNull(Object obj, String str) {
        if (obj == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" == null");
            throw new NullPointerException(sb.toString());
        }
    }

    static void checkArgument(boolean z, String str) {
        if (!z) {
            throw new IllegalArgumentException(str);
        }
    }
}
