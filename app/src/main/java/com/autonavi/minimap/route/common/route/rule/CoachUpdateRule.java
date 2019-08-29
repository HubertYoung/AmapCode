package com.autonavi.minimap.route.common.route.rule;

import com.autonavi.bundle.routecommon.inter.IUpdateRule;

public final class CoachUpdateRule implements IUpdateRule {

    enum InputStatus {
        NULL,
        ONLY_START,
        ONLY_END,
        COMPLETE
    }
}
