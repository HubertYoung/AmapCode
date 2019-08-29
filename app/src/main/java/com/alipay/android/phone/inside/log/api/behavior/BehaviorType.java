package com.alipay.android.phone.inside.log.api.behavior;

import com.alipay.mobile.common.logging.api.behavor.BehavorID;

public enum BehaviorType {
    CLICK("clicked"),
    OPENPAGE(BehavorID.OPENPAGE),
    LONGCLICK(BehavorID.LONGCLICK),
    SUBMITE(BehavorID.SUBMITE),
    SLIDE(BehavorID.SLIDE),
    AUTOOPENPAGE(BehavorID.AUTOOPENPAGE),
    AUTOCLICK(BehavorID.AUTOCLICK),
    AUTOEVENT(BehavorID.AUTOEVENT),
    EVENT("event"),
    EXPOSURE(BehavorID.EXPOSURE);
    
    private String mType;

    private BehaviorType(String str) {
        this.mType = str;
    }

    public final String toString() {
        return this.mType;
    }
}
