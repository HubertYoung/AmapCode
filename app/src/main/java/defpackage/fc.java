package defpackage;

import android.graphics.PointF;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: fc reason: default package */
/* compiled from: Keyframe */
public class fc<T> {
    /* access modifiers changed from: private */
    public static final Interpolator f = new LinearInterpolator();
    @Nullable
    public final T a;
    @Nullable
    public final T b;
    @Nullable
    public final Interpolator c;
    public final float d;
    @Nullable
    public Float e;
    private final ev g;
    private float h = Float.MIN_VALUE;
    private float i = Float.MIN_VALUE;

    /* renamed from: fc$a */
    /* compiled from: Keyframe */
    public static class a {
        private static SparseArrayCompat<WeakReference<Interpolator>> a;

        @Nullable
        private static WeakReference<Interpolator> a(int i) {
            WeakReference<Interpolator> weakReference;
            synchronized (a.class) {
                if (a == null) {
                    a = new SparseArrayCompat<>();
                }
                weakReference = (WeakReference) a.get(i);
            }
            return weakReference;
        }

        private static void a(int i, WeakReference<Interpolator> weakReference) {
            synchronized (a.class) {
                a.put(i, weakReference);
            }
        }

        private a() {
        }

        public static <T> fc<T> a(JSONObject jSONObject, ev evVar, float f, defpackage.hj.a<T> aVar) {
            float f2;
            Interpolator interpolator;
            Object obj;
            Object obj2;
            PointF pointF;
            PointF pointF2;
            Interpolator interpolator2 = null;
            if (jSONObject.has(LogItem.MM_C15_K4_TIME)) {
                float optDouble = (float) jSONObject.optDouble(LogItem.MM_C15_K4_TIME, 0.0d);
                Object opt = jSONObject.opt("s");
                Object a2 = opt != null ? aVar.a(opt, f) : null;
                Object opt2 = jSONObject.opt("e");
                Object a3 = opt2 != null ? aVar.a(opt2, f) : null;
                JSONObject optJSONObject = jSONObject.optJSONObject("o");
                JSONObject optJSONObject2 = jSONObject.optJSONObject("i");
                if (optJSONObject == null || optJSONObject2 == null) {
                    pointF2 = null;
                    pointF = null;
                } else {
                    pointF2 = JsonUtils.a(optJSONObject, f);
                    pointF = JsonUtils.a(optJSONObject2, f);
                }
                boolean z = true;
                if (jSONObject.optInt("h", 0) != 1) {
                    z = false;
                }
                if (z) {
                    f2 = optDouble;
                    interpolator = fc.f;
                    obj2 = a2;
                    obj = obj2;
                } else {
                    if (pointF2 != null) {
                        float f3 = -f;
                        pointF2.x = ii.a(pointF2.x, f3, f);
                        pointF2.y = ii.a(pointF2.y, -100.0f, 100.0f);
                        pointF.x = ii.a(pointF.x, f3, f);
                        pointF.y = ii.a(pointF.y, -100.0f, 100.0f);
                        int a4 = ij.a(pointF2.x, pointF2.y, pointF.x, pointF.y);
                        WeakReference<Interpolator> a5 = a(a4);
                        if (a5 != null) {
                            interpolator2 = (Interpolator) a5.get();
                        }
                        if (a5 == null || interpolator2 == null) {
                            interpolator2 = PathInterpolatorCompat.create(pointF2.x / f, pointF2.y / f, pointF.x / f, pointF.y / f);
                            try {
                                a(a4, new WeakReference(interpolator2));
                            } catch (ArrayIndexOutOfBoundsException unused) {
                            }
                        }
                    } else {
                        interpolator2 = fc.f;
                    }
                    obj = a3;
                    f2 = optDouble;
                    interpolator = interpolator2;
                    obj2 = a2;
                }
            } else {
                interpolator = null;
                obj2 = aVar.a(jSONObject, f);
                obj = obj2;
                f2 = 0.0f;
            }
            fc fcVar = new fc(evVar, obj2, obj, interpolator, f2, null);
            return fcVar;
        }

        public static <T> List<fc<T>> a(JSONArray jSONArray, ev evVar, float f, defpackage.hj.a<T> aVar) {
            int length = jSONArray.length();
            if (length == 0) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                arrayList.add(a(jSONArray.optJSONObject(i), evVar, f, aVar));
            }
            fc.a((List<? extends fc<?>>) arrayList);
            return arrayList;
        }
    }

    public static void a(List<? extends fc<?>> list) {
        int i2;
        int size = list.size();
        int i3 = 0;
        while (true) {
            i2 = size - 1;
            if (i3 >= i2) {
                break;
            }
            i3++;
            ((fc) list.get(i3)).e = Float.valueOf(((fc) list.get(i3)).d);
        }
        fc fcVar = (fc) list.get(i2);
        if (fcVar.a == null) {
            list.remove(fcVar);
        }
    }

    public fc(ev evVar, @Nullable T t, @Nullable T t2, @Nullable Interpolator interpolator, float f2, @Nullable Float f3) {
        this.g = evVar;
        this.a = t;
        this.b = t2;
        this.c = interpolator;
        this.d = f2;
        this.e = f3;
    }

    public final float a() {
        if (this.h == Float.MIN_VALUE) {
            this.h = (this.d - ((float) this.g.i)) / this.g.b();
        }
        return this.h;
    }

    public final float b() {
        if (this.i == Float.MIN_VALUE) {
            if (this.e == null) {
                this.i = 1.0f;
            } else {
                this.i = a() + ((this.e.floatValue() - this.d) / this.g.b());
            }
        }
        return this.i;
    }

    public final boolean a(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return f2 >= a() && f2 < b();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Keyframe{startValue=");
        sb.append(this.a);
        sb.append(", endValue=");
        sb.append(this.b);
        sb.append(", startFrame=");
        sb.append(this.d);
        sb.append(", endFrame=");
        sb.append(this.e);
        sb.append(", interpolator=");
        sb.append(this.c);
        sb.append('}');
        return sb.toString();
    }
}
