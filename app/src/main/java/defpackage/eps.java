package defpackage;

import android.support.annotation.NonNull;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import java.text.NumberFormat;
import org.json.JSONException;
import org.json.JSONStringer;

/* renamed from: eps reason: default package */
/* compiled from: SensorDataItem */
public final class eps extends epm {
    private final NumberFormat b;
    private final epr c;
    private final epr d;
    private final epr e;
    private final float[] f;
    private final float[] g = new float[4];
    private boolean h;
    private final int i;
    private final int j;

    public eps(@NonNull epr epr, epr epr2, epr epr3, long j2, int i2, int i3, boolean z) {
        super(((double) j2) / 1000.0d);
        this.h = z;
        if (!epr.a(epr)) {
            this.c = epr;
            this.f = new float[3];
            this.i = i3;
            if (!epr.a(epr2)) {
                this.e = epr2;
                this.j = i2;
            } else {
                this.e = new epr();
                this.j = 0;
            }
            if (!epr.a(epr3)) {
                this.d = epr3;
            } else {
                this.d = new epr();
            }
            this.b = NumberFormat.getNumberInstance();
            this.b.setGroupingUsed(false);
            this.b.setMaximumFractionDigits(20);
            return;
        }
        throw new IllegalStateException("Linear Acc is not null but Gravity is null?");
    }

    /* access modifiers changed from: protected */
    public final String a() {
        try {
            JSONStringer jSONStringer = new JSONStringer();
            jSONStringer.object();
            jSONStringer.key(SuperId.BIT_1_REALTIMEBUS_BUSSTATION).value(LogItem.MM_C22_K4_MODE);
            jSONStringer.key("v").object();
            jSONStringer.key(LogItem.MM_C15_K4_TIME).value(this.b.format(this.a));
            jSONStringer.key("q").array();
            jSONStringer.value(this.b.format((double) this.g[1])).value(this.b.format((double) this.g[2])).value(this.b.format((double) this.g[3])).value(this.b.format((double) this.g[0]));
            jSONStringer.endArray();
            jSONStringer.key("a").array();
            if (this.h) {
                float[] a = epn.a(1).a(this.c.a());
                jSONStringer.value(this.b.format((double) a[0])).value(this.b.format((double) a[1])).value(this.b.format((double) a[2]));
            } else {
                jSONStringer.value(this.b.format(this.c.x)).value(this.b.format(this.c.y)).value(this.b.format(this.c.z));
            }
            jSONStringer.endArray();
            jSONStringer.key("aa").value((long) this.i);
            jSONStringer.key("m").array();
            if (this.h) {
                float[] a2 = epn.a(2).a(this.e.a());
                jSONStringer.value(this.b.format((double) a2[0])).value(this.b.format((double) a2[1])).value(this.b.format((double) a2[2]));
            } else {
                jSONStringer.value(this.b.format(this.e.x)).value(this.b.format(this.e.y)).value(this.b.format(this.e.z));
            }
            jSONStringer.endArray();
            jSONStringer.key("ma").value((long) this.j);
            jSONStringer.key(SuperId.BIT_1_NAVI).array();
            if (this.h) {
                float[] a3 = epn.a(4).a(this.d.a());
                jSONStringer.value(this.b.format((double) a3[0])).value(this.b.format((double) a3[1])).value(this.b.format((double) a3[2]));
            } else {
                jSONStringer.value(this.b.format(this.d.x)).value(this.b.format(this.d.y)).value(this.b.format(this.d.z));
            }
            jSONStringer.endArray();
            jSONStringer.endObject();
            jSONStringer.endObject();
            return jSONStringer.toString();
        } catch (JSONException unused) {
            return "";
        }
    }
}
