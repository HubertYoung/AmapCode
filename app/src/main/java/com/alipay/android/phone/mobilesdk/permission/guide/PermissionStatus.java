package com.alipay.android.phone.mobilesdk.permission.guide;

import android.util.SparseArray;
import java.io.Serializable;

public enum PermissionStatus implements Serializable {
    NONE(-1, "-1", "-1"),
    DENIED(2, "0", "2"),
    GRANTED(1, "1", "1"),
    NOT_SURE(0, "2", "0");
    
    private static final SparseArray<PermissionStatus> sValueToEnum = null;
    private final String h5Name;
    private final String rpcName;
    private final int value;

    static {
        int i;
        PermissionStatus[] values;
        sValueToEnum = new SparseArray<>();
        for (PermissionStatus status : values()) {
            sValueToEnum.put(status.value, status);
        }
    }

    private PermissionStatus(int v, String rpc, String h5) {
        this.value = v;
        this.rpcName = rpc;
        this.h5Name = h5;
    }

    public final String getRpcName() {
        return this.rpcName;
    }

    public final String getH5Name() {
        return this.h5Name;
    }

    public final int getValue() {
        return this.value;
    }

    public static PermissionStatus fromValue(int value2) {
        return sValueToEnum.get(value2);
    }
}
