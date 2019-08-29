package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

final class Settings {
    static final int CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
    static final int COUNT = 10;
    static final int CURRENT_CWND = 5;
    static final int DEFAULT_INITIAL_WINDOW_SIZE = 65536;
    static final int DOWNLOAD_BANDWIDTH = 2;
    static final int DOWNLOAD_RETRANS_RATE = 6;
    static final int FLAG_CLEAR_PREVIOUSLY_PERSISTED_SETTINGS = 1;
    static final int FLOW_CONTROL_OPTIONS = 9;
    static final int FLOW_CONTROL_OPTIONS_DISABLED = 1;
    static final int INITIAL_WINDOW_SIZE = 7;
    static final int MAX_CONCURRENT_STREAMS = 4;
    static final int PERSISTED = 2;
    static final int PERSIST_VALUE = 1;
    static final int ROUND_TRIP_TIME = 3;
    static final int UPLOAD_BANDWIDTH = 1;
    private int persistValue;
    private int persisted;
    private int set;
    private final int[] values = new int[10];

    Settings() {
    }

    /* access modifiers changed from: 0000 */
    public final void set(int id, int idFlags, int value) {
        if (id < this.values.length) {
            int bit = 1 << id;
            this.set |= bit;
            if ((idFlags & 1) != 0) {
                this.persistValue |= bit;
            } else {
                this.persistValue &= bit ^ -1;
            }
            if ((idFlags & 2) != 0) {
                this.persisted |= bit;
            } else {
                this.persisted &= bit ^ -1;
            }
            this.values[id] = value;
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean isSet(int id) {
        if ((this.set & (1 << id)) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final int get(int id) {
        return this.values[id];
    }

    /* access modifiers changed from: 0000 */
    public final int flags(int id) {
        int result = 0;
        if (isPersisted(id)) {
            result = 2;
        }
        if (persistValue(id)) {
            return result | 1;
        }
        return result;
    }

    /* access modifiers changed from: 0000 */
    public final int size() {
        return Integer.bitCount(this.set);
    }

    /* access modifiers changed from: 0000 */
    public final int getUploadBandwidth(int defaultValue) {
        return (this.set & 2) != 0 ? this.values[1] : defaultValue;
    }

    /* access modifiers changed from: 0000 */
    public final int getDownloadBandwidth(int defaultValue) {
        return (this.set & 4) != 0 ? this.values[2] : defaultValue;
    }

    /* access modifiers changed from: 0000 */
    public final int getRoundTripTime(int defaultValue) {
        return (this.set & 8) != 0 ? this.values[3] : defaultValue;
    }

    /* access modifiers changed from: 0000 */
    public final int getMaxConcurrentStreams(int defaultValue) {
        return (this.set & 16) != 0 ? this.values[4] : defaultValue;
    }

    /* access modifiers changed from: 0000 */
    public final int getCurrentCwnd(int defaultValue) {
        return (this.set & 32) != 0 ? this.values[5] : defaultValue;
    }

    /* access modifiers changed from: 0000 */
    public final int getDownloadRetransRate(int defaultValue) {
        return (this.set & 64) != 0 ? this.values[6] : defaultValue;
    }

    /* access modifiers changed from: 0000 */
    public final int getInitialWindowSize(int defaultValue) {
        return (this.set & 128) != 0 ? this.values[7] : defaultValue;
    }

    /* access modifiers changed from: 0000 */
    public final int getClientCertificateVectorSize(int defaultValue) {
        return (this.set & 256) != 0 ? this.values[8] : defaultValue;
    }

    /* access modifiers changed from: 0000 */
    public final boolean isFlowControlDisabled() {
        int i;
        if ((this.set & 512) != 0) {
            i = this.values[9];
        } else {
            i = 0;
        }
        if ((i & 1) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final boolean persistValue(int id) {
        if ((this.persistValue & (1 << id)) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final boolean isPersisted(int id) {
        if ((this.persisted & (1 << id)) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final void merge(Settings other) {
        for (int i = 0; i < 10; i++) {
            if (other.isSet(i)) {
                set(i, other.flags(i), other.get(i));
            }
        }
    }
}
