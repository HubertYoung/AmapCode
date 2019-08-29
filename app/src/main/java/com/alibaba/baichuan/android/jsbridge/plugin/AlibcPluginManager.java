package com.alibaba.baichuan.android.jsbridge.plugin;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.c.b.d;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AlibcPluginManager {
    public static final String KEY_METHOD = "method";
    public static final String KEY_NAME = "name";
    private static final Map a = new ConcurrentHashMap();

    static class a {
        private String a;
        private ClassLoader b;

        a(String str, ClassLoader classLoader) {
            this.a = str;
            this.b = classLoader;
        }

        public String a() {
            return this.a;
        }

        public ClassLoader b() {
            return this.b;
        }
    }

    public static AlibcApiPlugin createPlugin(String str, Context context, d dVar) {
        a aVar = (a) a.get(str);
        if (aVar == null || aVar.a() == null) {
            return null;
        }
        String a2 = aVar.a();
        try {
            ClassLoader b = aVar.b();
            Class<?> cls = b == null ? Class.forName(a2) : b.loadClass(a2);
            if (cls != null && AlibcApiPlugin.class.isAssignableFrom(cls)) {
                AlibcApiPlugin alibcApiPlugin = (AlibcApiPlugin) cls.newInstance();
                if (alibcApiPlugin.paramObj != null) {
                    alibcApiPlugin.initialize(context, dVar, alibcApiPlugin.paramObj);
                    return alibcApiPlugin;
                }
                alibcApiPlugin.initialize(context, dVar);
                return alibcApiPlugin;
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("create plugin error: ");
            sb.append(str);
            sb.append(". ");
            sb.append(e.getMessage());
            AlibcLogger.e("AlibcPluginManager", sb.toString());
        }
        AlibcLogger.w("AlibcPluginManager", "create plugin failed: ".concat(String.valueOf(str)));
        return null;
    }

    public static void registerPlugin(String str, Class cls) {
        registerPlugin(str, cls, true);
    }

    public static void registerPlugin(String str, Class cls, boolean z) {
        if (!TextUtils.isEmpty(str) && cls != null) {
            ClassLoader classLoader = null;
            if (z) {
                classLoader = cls.getClassLoader();
            }
            a.put(str, new a(cls.getName(), classLoader));
        }
    }

    public static void unregisterPlugin(String str) {
        a.remove(str);
    }
}
