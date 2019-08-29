package com.alipay.android.phone.inside.log.behavior;

import com.alipay.android.phone.inside.log.LogCollect;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.log.field.BehaviorField;

public class BehaviorLoggerImpl implements BehaviorLogger {
    public final void a(Behavior behavior) {
        if (behavior != null) {
            BehaviorField behaviorField = new BehaviorField(behavior);
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("add behavior:");
            sb.append(behaviorField.a());
            f.b((String) "inside", sb.toString());
            LogCollect.a().a(behaviorField);
        }
    }

    public final Behavior a(String str, BehaviorType behaviorType, String str2) {
        Behavior behavior = new Behavior();
        behavior.a = str;
        behavior.b = behaviorType;
        behavior.c = str2;
        a(behavior);
        return behavior;
    }

    public final void b(String str, BehaviorType behaviorType, String str2) {
        Behavior behavior = new Behavior();
        behavior.a = str;
        behavior.b = behaviorType;
        behavior.c = str2;
        BehaviorField behaviorField = new BehaviorField(behavior);
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("add behavior:");
        sb.append(behaviorField.a());
        f.b((String) "inside", sb.toString());
        LogCollect.a().a(behaviorField);
    }

    public final void a(String str, BehaviorType behaviorType, String str2, String str3) {
        Behavior behavior = new Behavior();
        behavior.a = str;
        behavior.b = behaviorType;
        behavior.c = str2;
        behavior.a("", str3);
        BehaviorField behaviorField = new BehaviorField(behavior);
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("add behavior:");
        sb.append(behaviorField.a());
        f.b((String) "inside", sb.toString());
        LogCollect.a().a(behaviorField);
    }
}
