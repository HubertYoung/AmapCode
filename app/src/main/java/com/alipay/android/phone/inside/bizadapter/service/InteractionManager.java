package com.alipay.android.phone.inside.bizadapter.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;

public class InteractionManager {
    private static IInteractionProxy a;

    public static void a(IInteractionProxy iInteractionProxy) {
        BehaviorLogger d = LoggerFactory.d();
        BehaviorType behaviorType = BehaviorType.EVENT;
        StringBuilder sb = new StringBuilder("interactionProxy: ");
        sb.append(a);
        d.a("bizadapter", behaviorType, "SetInteractionProxy", sb.toString());
        a = iInteractionProxy;
    }

    public static void a(Bundle bundle) {
        if (a == null) {
            LoggerFactory.d().b("bizadapter", BehaviorType.EVENT, "NotifyInteractionNull");
            return;
        }
        try {
            LoggerFactory.d().a("bizadapter", BehaviorType.EVENT, "NotifyInteraction", "params: ".concat(String.valueOf(bundle)));
            a.a(bundle);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "bizadapter", (String) "NotifyInteractionEx", th);
        }
    }
}
