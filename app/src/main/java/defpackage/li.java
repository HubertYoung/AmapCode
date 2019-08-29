package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.jni.ae.data.ADCityInfo;
import com.autonavi.jni.ae.data.DataService;
import com.autonavi.minimap.map.DPoint;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: li reason: default package */
/* compiled from: CityInfoService */
public class li {
    private static volatile li b;
    public volatile SoftReference<ArrayList<lj>> a;
    private LinkedHashMap<Long, Integer> c = new LinkedHashMap<>();
    private volatile Map<Integer, lj> d;
    private volatile Map<String, lj> e;
    private volatile Map<String, lj> f;

    private static long c(double d2, double d3) {
        return (((long) ((int) (d2 * 100.0d))) << 32) | ((long) ((int) (d3 * 100.0d)));
    }

    private li() {
    }

    public static li a() {
        if (b == null) {
            synchronized (li.class) {
                try {
                    if (b == null) {
                        b = new li();
                    }
                }
            }
        }
        return b;
    }

    public final int a(double d2, double d3) {
        long c2 = c(d3, d2);
        int a2 = a(c2);
        if (a2 > 0) {
            return a2;
        }
        DataService instance = DataService.getInstance();
        if (instance == null) {
            AMapLog.error("paas.cityinfo", "CityInfoService", "dataService is null!");
            return 0;
        }
        int adminCode = instance.getAdminCode((int) (d2 * 1000000.0d), (int) (d3 * 1000000.0d));
        a(c2, adminCode);
        return adminCode;
    }

    public final lj b(int i, int i2) {
        return a((int) a(i, i2));
    }

    public final lj b(double d2, double d3) {
        return a((int) ((long) a(d2, d3)));
    }

    public final ArrayList<lj> b() {
        ArrayList<lj> arrayList = new ArrayList<>();
        Map<Integer, lj> c2 = c();
        if (c2 == null || c2.isEmpty()) {
            return arrayList;
        }
        for (lj next : c2.values()) {
            if (next != null) {
                arrayList.add(next.clone());
            }
        }
        return arrayList;
    }

    private synchronized int a(long j) {
        if (this.c.containsKey(Long.valueOf(j))) {
            Integer num = this.c.get(Long.valueOf(j));
            if (num != null) {
                return num.intValue();
            }
        }
        return -1;
    }

    private synchronized void a(long j, int i) {
        if (this.c.size() > 100) {
            Iterator<Entry<Long, Integer>> it = this.c.entrySet().iterator();
            if (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
        this.c.put(Long.valueOf(j), Integer.valueOf(i));
    }

    public final lj a(int i) {
        Map<Integer, lj> c2 = c();
        if (c2 == null || c2.isEmpty()) {
            return null;
        }
        lj ljVar = c2.get(Integer.valueOf(i));
        if (ljVar == null) {
            return null;
        }
        return ljVar.clone();
    }

    private Map<Integer, lj> c() {
        if (this.d != null) {
            return this.d;
        }
        d();
        return this.d;
    }

    private void d() {
        this.d = new ConcurrentHashMap();
        this.e = new ConcurrentHashMap();
        this.f = new ConcurrentHashMap();
        if (DataService.getInstance() == null) {
            AMapLog.error("paas.cityinfo", "CityInfoService", "dataService is null");
            return;
        }
        ADCityInfo[] allCities = DataService.getInstance().getAllCities();
        if (allCities != null) {
            for (ADCityInfo aDCityInfo : allCities) {
                if (aDCityInfo != null) {
                    lj ljVar = new lj(aDCityInfo);
                    this.d.put(Integer.valueOf(ljVar.j), ljVar);
                    if (bno.a && this.e.containsKey(ljVar.i)) {
                        StringBuilder sb = new StringBuilder("sameCityCode-cityCode:");
                        sb.append(ljVar.i);
                        sb.append(",cityInfo:");
                        sb.append(ljVar.toString());
                        AMapLog.debug("paas.cityinfo", "CityInfoService", sb.toString());
                    }
                    this.e.put(ljVar.i, ljVar);
                    this.f.put(ljVar.a, ljVar);
                    if (bno.a) {
                        StringBuilder sb2 = new StringBuilder("cityInfo:");
                        sb2.append(ljVar.toString());
                        AMapLog.debug("paas.cityinfo", "CityInfoService", sb2.toString());
                    }
                }
            }
        }
    }

    public final long a(int i, int i2) {
        DPoint a2 = cob.a((long) i, (long) i2, 20);
        long c2 = c(a2.x, a2.y);
        long a3 = (long) a(c2);
        if (a3 > 0) {
            return a3;
        }
        DataService instance = DataService.getInstance();
        if (instance == null) {
            AMapLog.error("paas.cityinfo", "CityInfoService", "dataService is null!");
            return 0;
        }
        int adminCode = instance.getAdminCode((int) (a2.x * 1000000.0d), (int) (a2.y * 1000000.0d));
        a(c2, adminCode);
        return (long) adminCode;
    }

    public final lj a(String str) {
        Map<String, lj> map;
        if (str == null) {
            return null;
        }
        if (this.e != null) {
            map = this.e;
        } else {
            d();
            map = this.e;
        }
        if (map == null || map.isEmpty()) {
            return null;
        }
        lj ljVar = map.get(str);
        if (ljVar == null) {
            return null;
        }
        return ljVar.clone();
    }

    public final lj b(String str) {
        Map<String, lj> map;
        if (str == null) {
            return null;
        }
        if (this.f != null) {
            map = this.f;
        } else {
            d();
            map = this.f;
        }
        if (map == null || map.isEmpty()) {
            return null;
        }
        lj ljVar = map.get(str);
        if (ljVar == null) {
            return null;
        }
        return ljVar.clone();
    }
}
