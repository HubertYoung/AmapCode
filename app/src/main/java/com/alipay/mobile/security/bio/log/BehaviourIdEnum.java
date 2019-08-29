package com.alipay.mobile.security.bio.log;

import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.autonavi.minimap.ajx3.util.Constants;

public enum BehaviourIdEnum {
    NONE(Constants.ANIMATOR_NONE),
    EVENT("event"),
    CLICKED("clicked"),
    OPENPAGE(BehavorID.OPENPAGE),
    LONGCLICKED(BehavorID.LONGCLICK),
    DYNAMICLOADTOCHECK("dynamicLoadToCheck"),
    AUTO_CLICKED("auto_clicked"),
    AUTO_OPENPAGE(BehavorID.AUTOOPENPAGE),
    SUBMITED(BehavorID.SUBMITE),
    BIZLAUNCHED("bizLaunched"),
    ERROR("error"),
    EXCEPTION(LogCategory.CATEGORY_EXCEPTION),
    SETGESTURE("setGesture"),
    CHECKGESTURE("checkGesture"),
    SLIDED(BehavorID.SLIDE),
    MONITOR("monitor"),
    EXECCOMMAND("execCommand"),
    MONITORPERF("monitorPerf");
    
    private String desc;

    private BehaviourIdEnum(String str) {
        this.desc = str;
    }

    public final String getDes() {
        return this.desc;
    }

    public static BehaviourIdEnum convert(String str) {
        BehaviourIdEnum[] values;
        for (BehaviourIdEnum behaviourIdEnum : values()) {
            if (behaviourIdEnum.desc.equals(str)) {
                return behaviourIdEnum;
            }
        }
        return NONE;
    }
}
