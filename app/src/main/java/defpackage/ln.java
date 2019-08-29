package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amap.bundle.cloudconfig.aocs.CloudConfigRequest;
import com.amap.bundle.cloudconfig.aocs.model.CacheData;
import com.amap.bundle.cloudconfig.aocs.model.ConfigModule;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.common.json.JsonUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: ln reason: default package */
/* compiled from: CloudConfigDataFetcher */
public final class ln {
    private static final MapSharePreference g = new MapSharePreference((String) "cloudconfig_aocs_sp");
    public Timer a = new Timer("ConfigManager_Timer");
    public TimerTask b = null;
    public CloudConfigRequest c;
    CacheData d = new CacheData();
    public com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.a e;
    AtomicBoolean f = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public a h;
    private Map<String, Long> i = null;
    private List<String> j = new ArrayList();
    private CloudConfigRequest k;

    /* renamed from: ln$a */
    /* compiled from: CloudConfigDataFetcher */
    static class a extends Handler {
        private WeakReference<ln> a;

        /* synthetic */ a(ln lnVar, Looper looper, byte b) {
            this(lnVar, looper);
        }

        private a(ln lnVar, Looper looper) {
            super(looper);
            this.a = new WeakReference<>(lnVar);
        }

        public final void handleMessage(Message message) {
            AMapLog.e("TimeFrequencyManager", "[ConfigManager]: --update Request Timer in handlerMessage");
            ln lnVar = (ln) this.a.get();
            if (lnVar != null && message.what == 1) {
                ln.b(lnVar);
            }
        }
    }

    /* renamed from: ln$b */
    /* compiled from: CloudConfigDataFetcher */
    public class b implements com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.b {
        private b() {
        }

        public /* synthetic */ b(ln lnVar, byte b) {
            this();
        }

        public final String a(String str) {
            return ln.this.b(str);
        }
    }

    public ln(Looper looper) {
        this.h = new a(this, looper, 0);
    }

    public final void a(List<String> list, @NonNull final com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.a aVar) {
        if (list != null && list.size() > 0) {
            if (this.k != null) {
                this.k.a();
                this.k = null;
            }
            this.k = new CloudConfigRequest("2", list);
            this.k.a((com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.b) new b(this, 0), (com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.a) new com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.a() {
                public final boolean a(ArrayList<lq> arrayList) {
                    ln.this.a(ln.b(ln.this, arrayList));
                    aVar.a(arrayList);
                    return true;
                }

                public final void a(int i, List<String> list) {
                    aVar.a(i, list);
                }
            });
        }
    }

    public final void a() {
        if (this.f.compareAndSet(false, true)) {
            String stringValue = g.getStringValue("cloudconfig_aocs_sp_key", null);
            if (stringValue != null) {
                try {
                    this.d.clear();
                    this.d.putAll((CacheData) JsonUtil.fromString(stringValue, CacheData.class));
                } catch (Exception e2) {
                    AMapLog.e("TimeFrequencyManager", "CacheLoadCallback#onComplete: ".concat(String.valueOf(e2)));
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final ConfigModule a(String str) {
        return (ConfigModule) this.d.get(str);
    }

    public final String b(String str) {
        ConfigModule configModule = (ConfigModule) this.d.get(str);
        if (configModule == null) {
            return null;
        }
        return configModule.getVersion();
    }

    /* access modifiers changed from: 0000 */
    public final void a(Map<String, ConfigModule> map) {
        if (map != null && !map.isEmpty() && this.f.get()) {
            for (Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                ConfigModule configModule = (ConfigModule) next.getValue();
                if (configModule == null) {
                    this.d.remove(str);
                } else {
                    this.d.put(str, configModule);
                }
            }
            try {
                g.putStringValue("cloudconfig_aocs_sp_key", JsonUtil.toString(this.d));
            } catch (Exception unused) {
            }
        }
    }

    @Nullable
    public final List<String> b() {
        ArrayList arrayList = new ArrayList();
        if (this.d.size() == 0) {
            return arrayList;
        }
        for (Entry key : this.d.entrySet()) {
            arrayList.add(key.getKey());
        }
        return arrayList;
    }

    private void a(List<String> list) {
        long j2;
        AMapLog.e("TimeFrequencyManager", "[ConfigManager]:  -- updateLoopModulesRequestTimeLocked");
        if (list.size() > 0) {
            if (this.j != null) {
                this.j.clear();
            }
            if (this.i == null) {
                this.i = new HashMap();
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                String str = list.get(i2);
                if (!this.i.containsKey(str)) {
                    this.i.put(str, Long.valueOf(SystemClock.elapsedRealtime()));
                } else {
                    long elapsedRealtime = SystemClock.elapsedRealtime() - this.i.get(str).longValue();
                    ConfigModule configModule = (ConfigModule) this.d.get(str);
                    if (configModule == null) {
                        j2 = -1;
                    } else {
                        j2 = configModule.getLoopDuration();
                    }
                    if (elapsedRealtime >= j2) {
                        if (this.j != null) {
                            this.j.add(str);
                        }
                        this.i.put(str, Long.valueOf(SystemClock.elapsedRealtime()));
                    }
                }
            }
        }
    }

    static /* synthetic */ Map a(ln lnVar, ArrayList arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            lq lqVar = (lq) arrayList.get(i2);
            if (lqVar.b == 0 || lqVar.b == 1) {
                ConfigModule configModule = new ConfigModule(lqVar.a, lqVar.c, lqVar.d);
                if (lqVar.e > 0) {
                    configModule.setLoopDuration(lqVar.e);
                    configModule.setByFlag(1);
                } else {
                    configModule.setByFlag(0);
                }
                hashMap.put(lqVar.a, configModule);
            } else if (lqVar.b == 2) {
                hashMap.put(lqVar.a, lnVar.a(lqVar.a));
            } else if (lqVar.b == 3) {
                hashMap.put(lqVar.a, null);
            }
        }
        return hashMap;
    }

    static /* synthetic */ Map b(ln lnVar, ArrayList arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList2 = new ArrayList(lnVar.d.keySet());
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            String str = (String) arrayList2.get(i2);
            ConfigModule a2 = lnVar.a(str);
            int i3 = 0;
            while (true) {
                if (i3 >= arrayList.size()) {
                    break;
                }
                lq lqVar = (lq) arrayList.get(i3);
                if (!str.equals(lqVar.a)) {
                    i3++;
                } else if (lqVar.b == 0 || lqVar.b == 1 || lqVar.b == 3) {
                    a2.setValue(lqVar.d);
                    a2.setLoopDuration(lqVar.e);
                    a2.setVersion(lqVar.c);
                }
            }
            hashMap.put(str, a2);
        }
        return hashMap;
    }

    static /* synthetic */ void b(ln lnVar) {
        AMapLog.e("TimeFrequencyManager", "[ConfigManager]:  -- requestTimeFrequencyTask");
        ArrayList arrayList = new ArrayList();
        for (Entry entry : lnVar.d.entrySet()) {
            String str = (String) entry.getKey();
            ConfigModule configModule = (ConfigModule) entry.getValue();
            if (configModule != null && configModule.getByFlag() == 1) {
                arrayList.add(str);
            }
        }
        if (arrayList.size() > 0) {
            lnVar.a((List<String>) arrayList);
            lnVar.a(lnVar.j, lnVar.e);
        }
    }
}
