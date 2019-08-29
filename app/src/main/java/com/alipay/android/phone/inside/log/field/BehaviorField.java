package com.alipay.android.phone.inside.log.field;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.util.DateUtil;
import java.util.Map;

public class BehaviorField extends AbstractLogField {
    private Behavior b;
    private String c = DateUtil.a();

    public BehaviorField(Behavior behavior) {
        this.b = behavior;
    }

    public final String a() {
        String str;
        Map<String, String> map = this.b.j;
        if (map == null || map.size() <= 0) {
            str = "-";
        } else {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (String next : map.keySet()) {
                if (!TextUtils.isEmpty(next)) {
                    sb.append(next);
                    sb.append(" : ");
                }
                sb.append(map.get(next));
                if (i < map.size() - 1) {
                    sb.append(" || ");
                }
                i++;
            }
            str = sb.toString();
        }
        BehaviorType behaviorType = BehaviorType.EVENT;
        if (this.b.b != null) {
            behaviorType = this.b.b;
        }
        return a(this.b.a, behaviorType.toString(), this.b.c, this.b.d, this.b.e, this.b.f, this.b.g, this.b.h, this.b.i, str, this.c);
    }
}
