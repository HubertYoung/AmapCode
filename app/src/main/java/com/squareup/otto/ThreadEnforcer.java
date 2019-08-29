package com.squareup.otto;

import android.os.Looper;

public interface ThreadEnforcer {
    public static final ThreadEnforcer ANY = new ThreadEnforcer() {
        public final void enforce(Bus bus) {
        }
    };
    public static final ThreadEnforcer MAIN = new ThreadEnforcer() {
        public final void enforce(Bus bus) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                StringBuilder sb = new StringBuilder("Event bus ");
                sb.append(bus);
                sb.append(" accessed from non-main thread ");
                sb.append(Looper.myLooper());
                throw new IllegalStateException(sb.toString());
            }
        }
    };

    void enforce(Bus bus);
}
