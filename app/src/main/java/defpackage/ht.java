package defpackage;

import android.graphics.PointF;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/* renamed from: ht reason: default package */
/* compiled from: ShapeData */
public final class ht {
    public final List<go> a;
    public PointF b;
    public boolean c;

    /* renamed from: ht$a */
    /* compiled from: ShapeData */
    public static class a implements defpackage.hj.a<ht> {
        public static final a a = new a();

        private a() {
        }

        private static PointF a(int i, JSONArray jSONArray) {
            if (i >= jSONArray.length()) {
                StringBuilder sb = new StringBuilder("Invalid index ");
                sb.append(i);
                sb.append(". There are only ");
                sb.append(jSONArray.length());
                sb.append(" points.");
                throw new IllegalArgumentException(sb.toString());
            }
            JSONArray optJSONArray = jSONArray.optJSONArray(i);
            Object opt = optJSONArray.opt(0);
            Object opt2 = optJSONArray.opt(1);
            return new PointF(opt instanceof Double ? ((Double) opt).floatValue() : (float) ((Integer) opt).intValue(), opt2 instanceof Double ? ((Double) opt2).floatValue() : (float) ((Integer) opt2).intValue());
        }

        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0018, code lost:
            if (r14.has("v") != false) goto L_0x002b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0027, code lost:
            if (r14.has("v") != false) goto L_0x002b;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final /* synthetic */ java.lang.Object a(java.lang.Object r14, float r15) {
            /*
                r13 = this;
                boolean r0 = r14 instanceof org.json.JSONArray
                r1 = 0
                r2 = 0
                if (r0 == 0) goto L_0x001b
                org.json.JSONArray r14 = (org.json.JSONArray) r14
                java.lang.Object r14 = r14.opt(r2)
                boolean r0 = r14 instanceof org.json.JSONObject
                if (r0 == 0) goto L_0x002a
                org.json.JSONObject r14 = (org.json.JSONObject) r14
                java.lang.String r0 = "v"
                boolean r0 = r14.has(r0)
                if (r0 == 0) goto L_0x002a
                goto L_0x002b
            L_0x001b:
                boolean r0 = r14 instanceof org.json.JSONObject
                if (r0 == 0) goto L_0x002a
                org.json.JSONObject r14 = (org.json.JSONObject) r14
                java.lang.String r0 = "v"
                boolean r0 = r14.has(r0)
                if (r0 == 0) goto L_0x002a
                goto L_0x002b
            L_0x002a:
                r14 = r1
            L_0x002b:
                if (r14 != 0) goto L_0x002e
                return r1
            L_0x002e:
                java.lang.String r0 = "v"
                org.json.JSONArray r0 = r14.optJSONArray(r0)
                java.lang.String r1 = "i"
                org.json.JSONArray r1 = r14.optJSONArray(r1)
                java.lang.String r3 = "o"
                org.json.JSONArray r3 = r14.optJSONArray(r3)
                java.lang.String r4 = "c"
                boolean r4 = r14.optBoolean(r4, r2)
                if (r0 == 0) goto L_0x0130
                if (r1 == 0) goto L_0x0130
                if (r3 == 0) goto L_0x0130
                int r5 = r0.length()
                int r6 = r1.length()
                if (r5 != r6) goto L_0x0130
                int r5 = r0.length()
                int r6 = r3.length()
                if (r5 == r6) goto L_0x0062
                goto L_0x0130
            L_0x0062:
                int r14 = r0.length()
                if (r14 != 0) goto L_0x0077
                ht r14 = new ht
                android.graphics.PointF r15 = new android.graphics.PointF
                r15.<init>()
                java.util.List r0 = java.util.Collections.emptyList()
                r14.<init>(r15, r2, r0, r2)
                return r14
            L_0x0077:
                int r14 = r0.length()
                android.graphics.PointF r5 = a(r2, r0)
                float r6 = r5.x
                float r6 = r6 * r15
                r5.x = r6
                float r6 = r5.y
                float r6 = r6 * r15
                r5.y = r6
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>(r14)
                r7 = 1
                r8 = 1
            L_0x0092:
                if (r8 >= r14) goto L_0x00dd
                android.graphics.PointF r9 = a(r8, r0)
                int r10 = r8 + -1
                android.graphics.PointF r11 = a(r10, r0)
                android.graphics.PointF r10 = a(r10, r3)
                android.graphics.PointF r12 = a(r8, r1)
                android.graphics.PointF r10 = defpackage.ii.a(r11, r10)
                android.graphics.PointF r11 = defpackage.ii.a(r9, r12)
                float r12 = r10.x
                float r12 = r12 * r15
                r10.x = r12
                float r12 = r10.y
                float r12 = r12 * r15
                r10.y = r12
                float r12 = r11.x
                float r12 = r12 * r15
                r11.x = r12
                float r12 = r11.y
                float r12 = r12 * r15
                r11.y = r12
                float r12 = r9.x
                float r12 = r12 * r15
                r9.x = r12
                float r12 = r9.y
                float r12 = r12 * r15
                r9.y = r12
                go r12 = new go
                r12.<init>(r10, r11, r9)
                r6.add(r12)
                int r8 = r8 + 1
                goto L_0x0092
            L_0x00dd:
                if (r4 == 0) goto L_0x012a
                android.graphics.PointF r8 = a(r2, r0)
                int r14 = r14 - r7
                android.graphics.PointF r0 = a(r14, r0)
                android.graphics.PointF r14 = a(r14, r3)
                android.graphics.PointF r1 = a(r2, r1)
                android.graphics.PointF r14 = defpackage.ii.a(r0, r14)
                android.graphics.PointF r0 = defpackage.ii.a(r8, r1)
                r1 = 1065353216(0x3f800000, float:1.0)
                int r1 = (r15 > r1 ? 1 : (r15 == r1 ? 0 : -1))
                if (r1 == 0) goto L_0x0122
                float r1 = r14.x
                float r1 = r1 * r15
                r14.x = r1
                float r1 = r14.y
                float r1 = r1 * r15
                r14.y = r1
                float r1 = r0.x
                float r1 = r1 * r15
                r0.x = r1
                float r1 = r0.y
                float r1 = r1 * r15
                r0.y = r1
                float r1 = r8.x
                float r1 = r1 * r15
                r8.x = r1
                float r1 = r8.y
                float r1 = r1 * r15
                r8.y = r1
            L_0x0122:
                go r15 = new go
                r15.<init>(r14, r0, r8)
                r6.add(r15)
            L_0x012a:
                ht r14 = new ht
                r14.<init>(r5, r4, r6, r2)
                return r14
            L_0x0130:
                java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
                java.lang.String r0 = "Unable to process points array or tangents. "
                java.lang.String r14 = java.lang.String.valueOf(r14)
                java.lang.String r14 = r0.concat(r14)
                r15.<init>(r14)
                throw r15
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.ht.a.a(java.lang.Object, float):java.lang.Object");
        }
    }

    /* synthetic */ ht(PointF pointF, boolean z, List list, byte b2) {
        this(pointF, z, list);
    }

    private ht(PointF pointF, boolean z, List<go> list) {
        this.a = new ArrayList();
        this.b = pointF;
        this.c = z;
        this.a.addAll(list);
    }

    public ht() {
        this.a = new ArrayList();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ShapeData{numCurves=");
        sb.append(this.a.size());
        sb.append("closed=");
        sb.append(this.c);
        sb.append('}');
        return sb.toString();
    }
}
