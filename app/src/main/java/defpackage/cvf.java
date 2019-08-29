package defpackage;

import android.app.Application;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import com.autonavi.minimap.bundle.apm.base.plugin.Plugin;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* renamed from: cvf reason: default package */
/* compiled from: PluginManager */
public final class cvf {
    /* access modifiers changed from: private */
    public static Map<String, Plugin> a = new ConcurrentHashMap();
    private static Map<String, Class> b = new HashMap();
    /* access modifiers changed from: private */
    public static volatile Application c;
    /* access modifiers changed from: private */
    public static volatile cuu d;
    private static boolean e;

    public static synchronized void a(@NonNull Application application, @NonNull cuu cuu) {
        synchronized (cvf.class) {
            if (!e) {
                c = application;
                d = cuu;
                a((String) "AppEventDetectPlugin", cvj.class);
                a((String) "UploadPlugin", cvz.class);
                if (VERSION.SDK_INT <= 26) {
                    a((String) "CpuPlugin", cvl.class);
                }
                a((String) "MemoryPlugin", cvu.class);
                a((String) "MainThreadBlockPlugin", cvr.class);
                a((String) "SmoothPlugin", cvy.class);
                a((String) "LaunchPlugin", cvo.class);
                e = true;
            }
        }
    }

    private static void a(String str, Class cls) {
        b.put(str, cls);
    }

    public static void a(@NonNull Map<String, cvi> map) {
        for (String str : map.keySet()) {
            cvi cvi = map.get(str);
            if (b.containsKey(cvi.a)) {
                final String str2 = cvi.a;
                final Class cls = b.get(cvi.a);
                final JSONObject jSONObject = cvi.b;
                d();
                AnonymousClass1 r4 = new Runnable() {
                    public final void run() {
                        try {
                            if (cvf.a.get(str2) != null) {
                                StringBuilder sb = new StringBuilder("plugin (");
                                sb.append(str2);
                                sb.append(") already exist!");
                                cwi.a("PLUGIN_MANAGER", sb.toString(), new RuntimeException("test"));
                                return;
                            }
                            Plugin plugin = (Plugin) cls.newInstance();
                            cvf.a.put(str2, plugin);
                            plugin.a = str2;
                            plugin.a(cvf.c, cvf.d, jSONObject);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str2);
                            sb2.append("is create");
                            cwl.c("PLUGIN_MANAGER", sb2.toString());
                        } catch (Throwable th) {
                            cwi.a(new RuntimeException("createPlugin error!", th));
                        }
                    }
                };
                if (str2.equals("StartPrefPlugin")) {
                    r4.run();
                } else {
                    b.b.post(r4);
                }
            }
        }
    }

    public static Plugin a(String str) {
        return a.get(str);
    }

    private static synchronized void d() {
        synchronized (cvf.class) {
            if (!e) {
                throw new IllegalStateException("please call init first");
            }
        }
    }
}
