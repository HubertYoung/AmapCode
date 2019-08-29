package test.tinyapp.alipay.com.testlibrary.service.performancepanel.biz;

import android.os.SystemClock;
import android.util.Log;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import test.tinyapp.alipay.com.testlibrary.a.c;
import test.tinyapp.alipay.com.testlibrary.core.DataProvider;
import test.tinyapp.alipay.com.testlibrary.core.DataProvider.UserAction;
import test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean.a;

public final class PerformanceDataProvider implements DataProvider<List<a>> {
    private static final Map<String, Enum> a = new HashMap();
    private static final Map<String, String> b = new HashMap(0);
    private static AtomicInteger d = new AtomicInteger(0);
    private static AtomicLong e = new AtomicLong(0);
    private static AtomicLong f = new AtomicLong(0);
    private static AtomicBoolean g = new AtomicBoolean(false);
    private Map<String, String> c = new HashMap(4);

    private static class ParseException extends Exception {
        private ParseException() {
        }
    }

    public static void a(long time) {
        e.set(time);
    }

    private static long a() {
        return e.get();
    }

    public static void a(int time) {
        d.set(time);
    }

    private static int b() {
        return d.get();
    }

    public static void b(long time) {
        f.set(time);
    }

    private static long c() {
        return f.get();
    }

    private static boolean d() {
        return g.get();
    }

    public static void a(boolean cached) {
        g.set(cached);
    }

    public final List<a> a(UserAction userAction) {
        Map performanceData;
        a performanceDataSource = (a) H5Utils.getH5ProviderManager().getProvider(a.class.getName());
        boolean runInWallet = performanceDataSource == null;
        if (runInWallet) {
            performanceData = e();
            if (test.tinyapp.alipay.com.testlibrary.a.a.a(performanceData)) {
                performanceData = f();
            }
        } else {
            performanceData = performanceDataSource.a();
        }
        if (test.tinyapp.alipay.com.testlibrary.a.a.a(performanceData)) {
            return g();
        }
        if (!runInWallet && !a(performanceData)) {
            return g();
        }
        a(performanceData, userAction);
        return b(performanceData);
    }

    private void a(Map<String, String> performanceData, UserAction userAction) {
        switch (userAction) {
            case ACTION_NORMAL:
                this.c.put("startup_time", performanceData.get("startup_time"));
                return;
            case ACTION_SWITCH_TAB:
                performanceData.put("startup_time", this.c.get("startup_time"));
                return;
            case ACTION_SWITCH_PAGE:
                performanceData.put("startup_time", this.c.get("startup_time"));
                return;
            default:
                return;
        }
    }

    private static boolean a(Map<String, String> performanceData) {
        return performanceData.containsKey("startup_time") && performanceData.containsKey("page_switch_time") && performanceData.containsKey("cache_size");
    }

    private static Map<String, String> e() {
        Object obj = c.a("com.alipay.mobile.liteprocess.perf.PerformanceLogger", "getPerformanceData");
        if (obj == null || !(obj instanceof Map)) {
            return b;
        }
        Map performanceDataMap = new HashMap((Map) obj);
        if (performanceDataMap.size() <= 0) {
            return b;
        }
        long openAppTime = a(performanceDataMap, (Object) "open_app_time");
        long preLoadedTime = a(performanceDataMap, (Object) "preload_complete_cost");
        long startupTime = a() - openAppTime;
        long pageSwitchTime = (long) b();
        long tempTime = pageSwitchTime;
        if (d() && c() != 0) {
            pageSwitchTime = SystemClock.elapsedRealtime() - c();
        }
        if (pageSwitchTime <= 0) {
            pageSwitchTime = tempTime;
        }
        int cacheSize = TinyAppStorage.getInstance().getCurrentStorageSize();
        if (startupTime <= 0) {
            return b;
        }
        Map performanceData = new HashMap(4);
        performanceData.put("startup_time", String.valueOf(startupTime));
        performanceData.put("page_switch_time", String.valueOf(pageSwitchTime));
        performanceData.put("pre_loaded_time", String.valueOf(preLoadedTime));
        performanceData.put("cache_size", String.valueOf(cacheSize));
        return performanceData;
    }

    private static Map<String, String> f() {
        Map<Object, Object> a2 = a((String) "com.alipay.mobile.liteprocess.perf.PerformanceLogger");
        Enum[] trackTypeConstants = c.a("com.alipay.mobile.liteprocess.perf.PerformanceLogger$TrackType");
        boolean foundError = false;
        if (a2 == null || a2.size() <= 0) {
            foundError = true;
        }
        if (!foundError && test.tinyapp.alipay.com.testlibrary.a.a.a((T[]) trackTypeConstants)) {
            foundError = true;
        }
        if (foundError) {
            return b;
        }
        a(a, trackTypeConstants);
        try {
            long openAppTime = a(a2, (Object) a.get("STARTUP_OPEN"));
            long domReadyTime = a(a2, (Object) a.get("STARTUP_DOM_READY"));
            long pageFinish = a(a2, (Object) a.get("STARTUP_PAGE_FINISH"));
            long appLoadedTime = a(a2, (Object) a.get("STARTUP_APP_LOADED"));
            long tinyProcessPreLoadedTime = a(a2, (Object) a.get("STARTUP_PROCESS_LAUNCH_TIME"));
            int cacheSize = TinyAppStorage.getInstance().getCurrentStorageSize();
            HashMap hashMap = new HashMap(4);
            hashMap.put("startup_time", String.valueOf(Math.max(Math.max(domReadyTime, pageFinish), appLoadedTime) - openAppTime));
            hashMap.put("page_switch_time", String.valueOf((long) b()));
            hashMap.put("pre_loaded_time", String.valueOf(tinyProcessPreLoadedTime));
            hashMap.put("cache_size", String.valueOf(cacheSize));
            return hashMap;
        } catch (Exception e2) {
            return b;
        }
    }

    private static List<a> b(Map<String, String> performanceData) {
        List displayItemInfoList = new ArrayList();
        displayItemInfoList.add(a.a("启动和切换"));
        String startupTime = performanceData.get("startup_time");
        displayItemInfoList.add(a.a("启动和切换", "启动耗时: ", "0".equals(startupTime) ? "--" : startupTime + RPCDataParser.TIME_MS));
        String switchPageTime = performanceData.get("page_switch_time");
        displayItemInfoList.add(a.a("启动和切换", "切页面耗时: ", "0".equals(switchPageTime) ? "--" : switchPageTime + RPCDataParser.TIME_MS));
        displayItemInfoList.add(a.a("其它"));
        displayItemInfoList.add(a.a("其它", "数据缓存: ", performanceData.get("cache_size") + DiskFormatter.B));
        return displayItemInfoList;
    }

    private static List<a> g() {
        Map performanceData = new HashMap(4);
        performanceData.put("startup_time", "0");
        performanceData.put("page_switch_time", "0");
        performanceData.put("pre_loaded_time", "0");
        performanceData.put("cache_size", "0");
        return b(performanceData);
    }

    private static Map<Object, Object> a(String className) {
        Field[] declaredFields;
        try {
            for (Field field : Class.forName(className).getDeclaredFields()) {
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers()) && field.getType().isAssignableFrom(Map.class)) {
                    Map trackInfoMap = (Map) field.get(null);
                    if (trackInfoMap.keySet().toArray()[0].getClass().isAssignableFrom(Class.forName("com.alipay.mobile.liteprocess.perf.PerformanceLogger$TrackType"))) {
                        return trackInfoMap;
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }

    private static void a(Map<String, Enum> source, Enum[] trackTypeConstants) {
        boolean z;
        if (source.isEmpty() || source.size() != trackTypeConstants.length) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            source.clear();
            for (Enum trackType : trackTypeConstants) {
                source.put(trackType.name(), trackType);
            }
        }
    }

    private static long a(Map<?, ?> source, Object key) {
        try {
            Object value = source.get(key);
            if (value.getClass() == Long.TYPE || (value instanceof Long)) {
                return ((Long) value).longValue();
            }
            if (value instanceof String) {
                return Long.parseLong((String) value);
            }
            return 0;
        } catch (Exception e2) {
            Log.w("PerformanceDataProvider", e2.getMessage());
            return 0;
        }
    }
}
