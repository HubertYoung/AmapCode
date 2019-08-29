package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocHistoryTrace implements Serializable {
    public LocHistoryPoint[] historyPosBuffer;

    public LocHistoryTrace() {
    }

    public LocHistoryTrace(LocHistoryPoint[] locHistoryPointArr) {
        this.historyPosBuffer = locHistoryPointArr;
    }
}
