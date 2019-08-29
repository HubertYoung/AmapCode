package defpackage;

import android.graphics.PointF;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.SuperId;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: hb reason: default package */
/* compiled from: AnimatablePathValue */
public final class hb implements hj<PointF, PointF> {
    private final List<gb> a;
    private PointF b;

    /* renamed from: hb$a */
    /* compiled from: AnimatablePathValue */
    static class a implements defpackage.hj.a<PointF> {
        /* access modifiers changed from: private */
        public static final defpackage.hj.a<PointF> a = new a();

        private a() {
        }

        public final /* bridge */ /* synthetic */ Object a(Object obj, float f) {
            return JsonUtils.a((JSONArray) obj, f);
        }
    }

    public static hj<PointF, PointF> a(JSONObject jSONObject, ev evVar) {
        if (jSONObject.has(SuperId.BIT_1_REALTIMEBUS_BUSSTATION)) {
            return new hb(jSONObject.opt(SuperId.BIT_1_REALTIMEBUS_BUSSTATION), evVar);
        }
        return new hf(defpackage.gy.a.a(jSONObject.optJSONObject(DictionaryKeys.CTRLXY_X), evVar, true), defpackage.gy.a.a(jSONObject.optJSONObject(DictionaryKeys.CTRLXY_Y), evVar, true));
    }

    hb() {
        this.a = new ArrayList();
        this.b = new PointF(0.0f, 0.0f);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00d9  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    hb(java.lang.Object r21, defpackage.ev r22) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            r9 = r22
            r20.<init>()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r0.a = r2
            boolean r2 = r1 instanceof org.json.JSONArray
            r11 = 0
            if (r2 == 0) goto L_0x002c
            r2 = r1
            org.json.JSONArray r2 = (org.json.JSONArray) r2
            java.lang.Object r2 = r2.opt(r11)
            boolean r3 = r2 instanceof org.json.JSONObject
            if (r3 == 0) goto L_0x002c
            org.json.JSONObject r2 = (org.json.JSONObject) r2
            java.lang.String r3 = "t"
            boolean r2 = r2.has(r3)
            if (r2 == 0) goto L_0x002c
            r2 = 1
            goto L_0x002d
        L_0x002c:
            r2 = 0
        L_0x002d:
            if (r2 == 0) goto L_0x00d9
            r12 = r1
            org.json.JSONArray r12 = (org.json.JSONArray) r12
            int r13 = r12.length()
            r14 = 0
        L_0x0037:
            if (r14 >= r13) goto L_0x00d3
            org.json.JSONObject r1 = r12.optJSONObject(r14)
            hj$a r2 = defpackage.hb.a.a
            float r3 = r9.k
            fc r15 = defpackage.fc.a.a(r1, r9, r3, r2)
            java.lang.String r2 = "ti"
            org.json.JSONArray r2 = r1.optJSONArray(r2)
            java.lang.String r3 = "to"
            org.json.JSONArray r1 = r1.optJSONArray(r3)
            r3 = 0
            if (r2 == 0) goto L_0x0067
            if (r1 == 0) goto L_0x0067
            float r3 = r9.k
            android.graphics.PointF r3 = defpackage.JsonUtils.a(r1, r3)
            float r1 = r9.k
            android.graphics.PointF r1 = defpackage.JsonUtils.a(r2, r1)
            r7 = r1
            r8 = r3
            goto L_0x0069
        L_0x0067:
            r7 = r3
            r8 = r7
        L_0x0069:
            gb r6 = new gb
            T r1 = r15.a
            r3 = r1
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            T r1 = r15.b
            r4 = r1
            android.graphics.PointF r4 = (android.graphics.PointF) r4
            android.view.animation.Interpolator r5 = r15.c
            float r2 = r15.d
            java.lang.Float r1 = r15.e
            r16 = 0
            r17 = r1
            r1 = r6
            r18 = r2
            r2 = r9
            r10 = r6
            r6 = r18
            r11 = r7
            r7 = r17
            r19 = r12
            r12 = r8
            r8 = r16
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            T r1 = r15.b
            if (r1 == 0) goto L_0x00b1
            T r1 = r15.a
            if (r1 == 0) goto L_0x00b1
            T r1 = r15.a
            android.graphics.PointF r1 = (android.graphics.PointF) r1
            T r2 = r15.b
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            float r2 = r2.x
            T r3 = r15.b
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            float r3 = r3.y
            boolean r1 = r1.equals(r2, r3)
            if (r1 == 0) goto L_0x00b1
            r1 = 1
            goto L_0x00b2
        L_0x00b1:
            r1 = 0
        L_0x00b2:
            java.lang.Object r2 = r10.b
            if (r2 == 0) goto L_0x00c7
            if (r1 != 0) goto L_0x00c7
            T r1 = r15.a
            android.graphics.PointF r1 = (android.graphics.PointF) r1
            T r2 = r15.b
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            android.graphics.Path r1 = defpackage.ij.a(r1, r2, r12, r11)
            r10.f = r1
        L_0x00c7:
            java.util.List<gb> r1 = r0.a
            r1.add(r10)
            int r14 = r14 + 1
            r12 = r19
            r11 = 0
            goto L_0x0037
        L_0x00d3:
            java.util.List<gb> r1 = r0.a
            defpackage.fc.a(r1)
            return
        L_0x00d9:
            org.json.JSONArray r1 = (org.json.JSONArray) r1
            float r2 = r9.k
            android.graphics.PointF r1 = defpackage.JsonUtils.a(r1, r2)
            r0.b = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hb.<init>(java.lang.Object, ev):void");
    }

    public final fu<PointF, PointF> a() {
        if (!b()) {
            return new gh(this.b);
        }
        return new gc(this.a);
    }

    private boolean b() {
        return !this.a.isEmpty();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("initialPoint=");
        sb.append(this.b);
        return sb.toString();
    }
}
