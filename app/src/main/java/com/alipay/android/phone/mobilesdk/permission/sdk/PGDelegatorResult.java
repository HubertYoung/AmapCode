package com.alipay.android.phone.mobilesdk.permission.sdk;

import com.alipay.android.phone.mobilesdk.permission.guide.PermissionType;
import java.io.Serializable;

public final class PGDelegatorResult implements Serializable {
    public String bizType;
    public boolean force;
    public PermissionType[] permissions;

    public PGDelegatorResult(String bizType2, PermissionType[] permissions2, boolean force2) {
        this.bizType = bizType2;
        this.permissions = permissions2;
        this.force = force2;
    }
}
