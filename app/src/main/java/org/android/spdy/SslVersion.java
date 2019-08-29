package org.android.spdy;

public enum SslVersion {
    SLIGHT_VERSION_V1(0);
    
    private int code;

    private SslVersion(int i) {
        this.code = i;
    }

    /* access modifiers changed from: 0000 */
    public final int getint() {
        return this.code;
    }
}
