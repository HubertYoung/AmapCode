package defpackage;

import android.text.TextUtils;
import anet.channel.statist.Monitor;
import anet.channel.statist.StatObject;
import com.alibaba.mtl.appmonitor.AppMonitor.Alarm;
import com.alibaba.mtl.appmonitor.AppMonitor.Counter;
import com.alibaba.mtl.appmonitor.AppMonitor.Stat;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: y reason: default package */
/* compiled from: DefaultAppMonitor */
public final class y implements z {
    private static boolean a = false;
    private static Map<Class<?>, List<Field>> b = new ConcurrentHashMap();
    private static Map<Class<?>, List<Field>> c = new ConcurrentHashMap();
    private static Map<Field, String> d = new ConcurrentHashMap();
    private static Random e = new Random();
    private static Set<Class<?>> f = Collections.newSetFromMap(new ConcurrentHashMap());

    public y() {
        try {
            Class.forName("com.alibaba.mtl.appmonitor.AppMonitor");
            a = true;
        } catch (Exception unused) {
            a = false;
        }
    }

    public final void a(StatObject statObject) {
        if (a && statObject != null) {
            Class<?> cls = statObject.getClass();
            Monitor monitor = (Monitor) cls.getAnnotation(Monitor.class);
            if (monitor != null) {
                if (!f.contains(cls)) {
                    a(cls);
                }
                if (statObject.beforeCommit()) {
                    if (monitor.monitorPoint().equals("network")) {
                        int j = ds.j();
                        if (j > 10000 || j < 0) {
                            j = 10000;
                        }
                        if (j != 10000 && e.nextInt(10000) >= j) {
                            return;
                        }
                    }
                    try {
                        DimensionValueSet create = DimensionValueSet.create();
                        MeasureValueSet create2 = MeasureValueSet.create();
                        List<Field> list = b.get(cls);
                        Map hashMap = cl.a(1) ? new HashMap() : null;
                        if (list != null) {
                            for (Field field : list) {
                                Object obj = field.get(statObject);
                                create.setValue(d.get(field), obj == null ? "" : obj.toString());
                            }
                            for (Field field2 : c.get(cls)) {
                                Double valueOf = Double.valueOf(field2.getDouble(statObject));
                                create2.setValue(d.get(field2), valueOf.doubleValue());
                                if (hashMap != null) {
                                    hashMap.put(d.get(field2), valueOf);
                                }
                            }
                        }
                        Stat.commit(monitor.module(), monitor.monitorPoint(), create, create2);
                        if (cl.a(1)) {
                            StringBuilder sb = new StringBuilder("commit stat: ");
                            sb.append(monitor.monitorPoint());
                            cl.a("awcn.DefaultAppMonitor", sb.toString(), null, "\nDimensions", create.getMap().toString(), "\nMeasures", hashMap.toString());
                        }
                    } catch (Throwable unused) {
                        cl.e("awcn.DefaultAppMonitor", "commit monitor point failed", null, new Object[0]);
                    }
                }
            }
        }
    }

    public final void a(bj bjVar) {
        if (a && bjVar != null && !TextUtils.isEmpty(bjVar.e) && !TextUtils.isEmpty(bjVar.f)) {
            if (cl.a(1)) {
                cl.a("awcn.DefaultAppMonitor", "commit alarm: ".concat(String.valueOf(bjVar)), null, new Object[0]);
            }
            if (!bjVar.a) {
                Alarm.commitFail(bjVar.e, bjVar.f, cz.a(bjVar.b), cz.a(bjVar.c), cz.a(bjVar.d));
            } else {
                Alarm.commitSuccess(bjVar.e, bjVar.f, cz.a(bjVar.b));
            }
        }
    }

    public final void a(bk bkVar) {
        if (a && bkVar != null && !TextUtils.isEmpty(bkVar.c) && !TextUtils.isEmpty(bkVar.d)) {
            if (cl.a(2)) {
                cl.b("awcn.DefaultAppMonitor", "commit count: ".concat(String.valueOf(bkVar)), null, new Object[0]);
            }
            Counter.commit(bkVar.c, bkVar.d, cz.a(bkVar.a), bkVar.b);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x010e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void a(java.lang.Class<?> r20) {
        /*
            r19 = this;
            r1 = r20
            monitor-enter(r19)
            if (r1 == 0) goto L_0x010d
            boolean r2 = a     // Catch:{ all -> 0x0109 }
            if (r2 != 0) goto L_0x000b
            goto L_0x010d
        L_0x000b:
            java.util.Set<java.lang.Class<?>> r3 = f     // Catch:{ Exception -> 0x00fc }
            boolean r3 = r3.contains(r1)     // Catch:{ Exception -> 0x00fc }
            if (r3 == 0) goto L_0x0015
            monitor-exit(r19)
            return
        L_0x0015:
            java.lang.Class<anet.channel.statist.Monitor> r3 = anet.channel.statist.Monitor.class
            java.lang.annotation.Annotation r3 = r1.getAnnotation(r3)     // Catch:{ Exception -> 0x00fc }
            anet.channel.statist.Monitor r3 = (anet.channel.statist.Monitor) r3     // Catch:{ Exception -> 0x00fc }
            if (r3 != 0) goto L_0x0021
            monitor-exit(r19)
            return
        L_0x0021:
            java.lang.reflect.Field[] r4 = r20.getFields()     // Catch:{ Exception -> 0x00fc }
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x00fc }
            r5.<init>()     // Catch:{ Exception -> 0x00fc }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Exception -> 0x00fc }
            r6.<init>()     // Catch:{ Exception -> 0x00fc }
            com.alibaba.mtl.appmonitor.model.DimensionSet r7 = com.alibaba.mtl.appmonitor.model.DimensionSet.create()     // Catch:{ Exception -> 0x00fc }
            com.alibaba.mtl.appmonitor.model.MeasureSet r8 = com.alibaba.mtl.appmonitor.model.MeasureSet.create()     // Catch:{ Exception -> 0x00fc }
            r9 = 0
        L_0x0038:
            int r10 = r4.length     // Catch:{ Exception -> 0x00fc }
            if (r9 >= r10) goto L_0x00dc
            r10 = r4[r9]     // Catch:{ Exception -> 0x00fc }
            java.lang.Class<anet.channel.statist.Dimension> r11 = anet.channel.statist.Dimension.class
            java.lang.annotation.Annotation r11 = r10.getAnnotation(r11)     // Catch:{ Exception -> 0x00fc }
            anet.channel.statist.Dimension r11 = (anet.channel.statist.Dimension) r11     // Catch:{ Exception -> 0x00fc }
            r12 = 1
            if (r11 == 0) goto L_0x006c
            r10.setAccessible(r12)     // Catch:{ Exception -> 0x00fc }
            r5.add(r10)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r12 = r11.name()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r13 = ""
            boolean r12 = r12.equals(r13)     // Catch:{ Exception -> 0x00fc }
            if (r12 == 0) goto L_0x005f
            java.lang.String r11 = r10.getName()     // Catch:{ Exception -> 0x00fc }
            goto L_0x0063
        L_0x005f:
            java.lang.String r11 = r11.name()     // Catch:{ Exception -> 0x00fc }
        L_0x0063:
            java.util.Map<java.lang.reflect.Field, java.lang.String> r12 = d     // Catch:{ Exception -> 0x00fc }
            r12.put(r10, r11)     // Catch:{ Exception -> 0x00fc }
            r7.addDimension(r11)     // Catch:{ Exception -> 0x00fc }
            goto L_0x00d0
        L_0x006c:
            java.lang.Class<anet.channel.statist.Measure> r11 = anet.channel.statist.Measure.class
            java.lang.annotation.Annotation r11 = r10.getAnnotation(r11)     // Catch:{ Exception -> 0x00fc }
            anet.channel.statist.Measure r11 = (anet.channel.statist.Measure) r11     // Catch:{ Exception -> 0x00fc }
            if (r11 == 0) goto L_0x00d0
            r10.setAccessible(r12)     // Catch:{ Exception -> 0x00fc }
            r6.add(r10)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r12 = r11.name()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r13 = ""
            boolean r12 = r12.equals(r13)     // Catch:{ Exception -> 0x00fc }
            if (r12 == 0) goto L_0x008d
            java.lang.String r12 = r10.getName()     // Catch:{ Exception -> 0x00fc }
            goto L_0x0091
        L_0x008d:
            java.lang.String r12 = r11.name()     // Catch:{ Exception -> 0x00fc }
        L_0x0091:
            java.util.Map<java.lang.reflect.Field, java.lang.String> r13 = d     // Catch:{ Exception -> 0x00fc }
            r13.put(r10, r12)     // Catch:{ Exception -> 0x00fc }
            double r13 = r11.max()     // Catch:{ Exception -> 0x00fc }
            r15 = 9218868437227405311(0x7fefffffffffffff, double:1.7976931348623157E308)
            int r10 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r10 == 0) goto L_0x00c8
            com.alibaba.mtl.appmonitor.model.Measure r10 = new com.alibaba.mtl.appmonitor.model.Measure     // Catch:{ Exception -> 0x00fc }
            double r13 = r11.constantValue()     // Catch:{ Exception -> 0x00fc }
            java.lang.Double r13 = java.lang.Double.valueOf(r13)     // Catch:{ Exception -> 0x00fc }
            r17 = r3
            double r2 = r11.min()     // Catch:{ Exception -> 0x00fc }
            java.lang.Double r2 = java.lang.Double.valueOf(r2)     // Catch:{ Exception -> 0x00fc }
            r18 = r4
            double r3 = r11.max()     // Catch:{ Exception -> 0x00fc }
            java.lang.Double r3 = java.lang.Double.valueOf(r3)     // Catch:{ Exception -> 0x00fc }
            r10.<init>(r12, r13, r2, r3)     // Catch:{ Exception -> 0x00fc }
            r8.addMeasure(r10)     // Catch:{ Exception -> 0x00fc }
            goto L_0x00d4
        L_0x00c8:
            r17 = r3
            r18 = r4
            r8.addMeasure(r12)     // Catch:{ Exception -> 0x00fc }
            goto L_0x00d4
        L_0x00d0:
            r17 = r3
            r18 = r4
        L_0x00d4:
            int r9 = r9 + 1
            r3 = r17
            r4 = r18
            goto L_0x0038
        L_0x00dc:
            r17 = r3
            java.util.Map<java.lang.Class<?>, java.util.List<java.lang.reflect.Field>> r2 = b     // Catch:{ Exception -> 0x00fc }
            r2.put(r1, r5)     // Catch:{ Exception -> 0x00fc }
            java.util.Map<java.lang.Class<?>, java.util.List<java.lang.reflect.Field>> r2 = c     // Catch:{ Exception -> 0x00fc }
            r2.put(r1, r6)     // Catch:{ Exception -> 0x00fc }
            r3 = r17
            java.lang.String r2 = r3.module()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r3 = r3.monitorPoint()     // Catch:{ Exception -> 0x00fc }
            com.alibaba.mtl.appmonitor.AppMonitor.register(r2, r3, r8, r7)     // Catch:{ Exception -> 0x00fc }
            java.util.Set<java.lang.Class<?>> r2 = f     // Catch:{ Exception -> 0x00fc }
            r2.add(r1)     // Catch:{ Exception -> 0x00fc }
            monitor-exit(r19)
            return
        L_0x00fc:
            java.lang.String r1 = "awcn.DefaultAppMonitor"
            java.lang.String r2 = "register fail"
            r3 = 0
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0109 }
            defpackage.cl.e(r1, r2, r3, r4)     // Catch:{ all -> 0x0109 }
            monitor-exit(r19)
            return
        L_0x0109:
            r0 = move-exception
            r1 = r0
            monitor-exit(r19)
            throw r1
        L_0x010d:
            monitor-exit(r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.y.a(java.lang.Class):void");
    }
}
