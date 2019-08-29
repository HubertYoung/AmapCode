package defpackage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

/* renamed from: cbv reason: default package */
/* compiled from: SherlorkDetector */
public final class cbv {
    public Context a;
    boolean b;
    private b c;
    private boolean d;
    private Map<ViewGroup, WeakReference<SearchPoi>> e;
    private int f;
    private int g;
    private a h;

    /* renamed from: cbv$a */
    /* compiled from: SherlorkDetector */
    public static final class a {
        /* access modifiers changed from: private */
        public static final cbv a = new cbv(AMapAppGlobal.getApplication(), 0);
    }

    /* renamed from: cbv$b */
    /* compiled from: SherlorkDetector */
    static class b implements SensorEventListener {
        /* access modifiers changed from: private */
        public final a a;
        private long b;
        private int c;
        private double[] d;
        private long[] e;
        private long f = 0;

        /* renamed from: cbv$b$a */
        /* compiled from: SherlorkDetector */
        public interface a {
            void a();
        }

        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        public b(a aVar) {
            this.a = aVar;
        }

        public final void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.timestamp - this.b >= 20) {
                float f2 = sensorEvent.values[0];
                float f3 = sensorEvent.values[1];
                float f4 = sensorEvent.values[2];
                this.b = sensorEvent.timestamp;
                this.e[this.c] = sensorEvent.timestamp;
                this.d[this.c] = Math.sqrt((double) ((f2 * f2) + (f3 * f3) + (f4 * f4)));
                long j = sensorEvent.timestamp;
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < 25; i3++) {
                    int i4 = ((this.c - i3) + 25) % 25;
                    if (j - this.e[i4] < 500) {
                        i2++;
                        if (this.d[i4] >= 25.0d) {
                            i++;
                        }
                    }
                }
                if (((double) i) / ((double) i2) > 0.66d) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.f >= 2000) {
                        this.f = currentTimeMillis;
                        aho.a(new Runnable() {
                            public final void run() {
                                b.this.a.a();
                            }
                        });
                    }
                }
                this.c = (this.c + 1) % 25;
            }
        }
    }

    /* renamed from: cbv$c */
    /* compiled from: SherlorkDetector */
    public static class c {
        public TextView a;
        public TextView b;

        private c() {
        }

        public /* synthetic */ c(byte b2) {
            this();
        }
    }

    /* synthetic */ cbv(Context context, byte b2) {
        this(context);
    }

    private cbv(Context context) {
        this.d = false;
        this.b = false;
        this.e = new WeakHashMap();
        this.h = new a() {
            public final void a() {
                cbv.this.b = !cbv.this.b;
            }
        };
        this.a = context;
        this.c = new b(this.h);
        this.f = context.getResources().getColor(R.color.white);
        this.g = agn.a(context, 2.0f);
    }

    public static String a(PoiLayoutTemplate poiLayoutTemplate) {
        Field[] declaredFields;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        a(linkedHashMap, "name", poiLayoutTemplate.getName());
        a(linkedHashMap, "type", poiLayoutTemplate.getType());
        a(linkedHashMap, "value", poiLayoutTemplate.getValue());
        for (Field field : poiLayoutTemplate.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                try {
                    field.setAccessible(true);
                    a(linkedHashMap, field.getName(), field.get(poiLayoutTemplate));
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Entry entry : linkedHashMap.entrySet()) {
            sb.append(String.format("%s:%s", new Object[]{entry.getKey(), entry.getValue()}));
            sb.append(MultipartUtility.LINE_FEED);
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 2);
        }
        return sb.toString();
    }

    private static void a(Map<String, String> map, String str, Object obj) {
        if (obj != null) {
            if (obj.getClass().isArray()) {
                Object[] objArr = (Object[]) obj;
                if (objArr.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (Object obj2 : objArr) {
                        String obj3 = obj2.toString();
                        if (!TextUtils.isEmpty(obj3)) {
                            sb.append(obj3);
                            sb.append(",");
                        }
                    }
                    if (sb.length() > 0) {
                        map.put(str, sb.substring(0, sb.length() - 1));
                    }
                }
                return;
            }
            String obj4 = obj.toString();
            if (!TextUtils.isEmpty(obj4)) {
                map.put(str, obj4);
            }
        }
    }
}
