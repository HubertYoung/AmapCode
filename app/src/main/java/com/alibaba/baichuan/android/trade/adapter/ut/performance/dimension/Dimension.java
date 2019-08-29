package com.alibaba.baichuan.android.trade.adapter.ut.performance.dimension;

import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.utils.a;
import com.alibaba.baichuan.android.trade.utils.d;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import java.io.Serializable;

public class Dimension implements Serializable {
    private static final String e = "Dimension";
    protected String a = AlibcContext.getAppKey();
    protected String b = d.b(AlibcContext.context);
    protected String c = AlibcContext.sdkVersion;
    protected String d = "android";

    public static DimensionSet getDimensionSet() {
        return DimensionSet.create().addDimension((String) "appkey").addDimension((String) "app_version").addDimension((String) "sdk_version").addDimension((String) "platform");
    }

    public DimensionValueSet getDimensionValues() {
        if (this.a != null && this.b != null) {
            return DimensionValueSet.create().setValue("appkey", this.a).setValue("app_version", this.b).setValue("sdk_version", this.c).setValue("platform", this.d);
        }
        a.a(e, "getDimensionValues", "appkey/appVersion is null");
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Dimension{appkey='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", appVersion='");
        sb.append(this.b);
        sb.append('\'');
        sb.append(", sdkVersion='");
        sb.append(this.c);
        sb.append('\'');
        sb.append(", platform='");
        sb.append(this.d);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
