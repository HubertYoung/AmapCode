package com.autonavi.io.monitor.annotation;

public enum AccessMode {
    MK_DIR,
    REMOVE,
    RENAME,
    OPEN,
    OPEN_R,
    OPEN_W;

    public static AccessMode[] defaultModes() {
        return new AccessMode[]{MK_DIR, REMOVE, RENAME, OPEN_R, OPEN_W};
    }

    public static AccessMode[] defaultFileModes() {
        return new AccessMode[]{REMOVE, RENAME, OPEN_R, OPEN_W};
    }
}
