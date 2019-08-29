package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;

@Keep
public class ATServerStateRequest {
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }
}
