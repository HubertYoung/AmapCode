package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.minimap.bundle.apm.base.plugin.Plugin;
import com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a;
import com.autonavi.minimap.bundle.apm.internal.report.BeanReportImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* renamed from: cvh reason: default package */
/* compiled from: TelescopeContextImpl */
public final class cvh implements cuu {
    public cut a;
    public ArrayList<Object> b = new ArrayList<>();
    private HashMap<Integer, Set<String>> c = new HashMap<>();
    private BeanReportImpl d = new BeanReportImpl();

    public final void a(@NonNull final cur cur) {
        if (c()) {
            b(cur);
            c(cur);
            return;
        }
        b.b.post(new Runnable() {
            public final void run() {
                cvh.this.b(cur);
                cvh.this.c(cur);
            }
        });
    }

    public final void a(final int i, @NonNull final String str) {
        if (c()) {
            b(i, str);
        } else {
            b.b.post(new Runnable() {
                public final void run() {
                    cvh.this.b(i, str);
                }
            });
        }
    }

    public final cut a() {
        return this.a;
    }

    public final cuv b() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public final void b(@NonNull cur cur) {
        Set<String> set = this.c.get(Integer.valueOf(cur.b));
        if (set != null) {
            for (String a2 : set) {
                Plugin a3 = cvf.a(a2);
                if (a3 != null) {
                    a3.a(cur.b, cur);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void c(cur cur) {
        HashMap hashMap = new HashMap();
        hashMap.put("appKey", cva.a);
        hashMap.put("versionName", cva.b);
        hashMap.put("packageName", cva.c);
        hashMap.put("utdid", cva.d);
        hashMap.put("isRooted", String.valueOf(a.a.b()));
        hashMap.put("isEmulator", String.valueOf(a.a.c()));
        hashMap.put("mobileBrand", String.valueOf(a.a.r));
        hashMap.put("mobileModel", String.valueOf(a.a.q));
        hashMap.put("apiLevel", String.valueOf(a.a.s.intValue()));
        hashMap.put("osVersion", String.valueOf(a.a.t));
        hashMap.put("storeTotalSize", String.valueOf(a.a.q()));
        hashMap.put("deviceTotalMemory", String.valueOf(a.a.d()));
        hashMap.put("memoryThreshold", String.valueOf(a.a.e()));
        hashMap.put("cpuModel", String.valueOf(a.a.g()));
        hashMap.put("cpuBrand", String.valueOf(a.a.f()));
        hashMap.put("cpuArch", String.valueOf(a.a.h()));
        hashMap.put("cpuProcessCount", String.valueOf(a.a.i()));
        hashMap.put("cpuFreqArray", Arrays.toString(a.a.l()));
        hashMap.put("cpuMaxFreq", String.valueOf(a.a.j()));
        hashMap.put("cpuMinFreq", String.valueOf(a.a.k()));
        hashMap.put("gpuMaxFreq", String.valueOf(a.a.m()));
        hashMap.put("screenWidth", String.valueOf(a.a.n()));
        hashMap.put("screenHeight", String.valueOf(a.a.o()));
        hashMap.put("screenDensity", String.valueOf(a.a.p()));
        if (cur.b == 3 && this.b.size() != 0) {
            Iterator<Object> it = this.b.iterator();
            while (it.hasNext()) {
                it.next();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(int i, @NonNull String str) {
        Set set = this.c.get(Integer.valueOf(i));
        if (set == null) {
            set = new HashSet();
            this.c.put(Integer.valueOf(i), set);
        }
        set.add(str);
    }

    private static boolean c() {
        return Thread.currentThread() == b.a.getThread();
    }
}
