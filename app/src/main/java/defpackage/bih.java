package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.offline.preference.OfflinePreference;
import com.autonavi.sync.beans.JsonDataWithId;
import com.autonavi.sync.beans.JsonDatasWithType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: bih reason: default package */
/* compiled from: CacheService */
public class bih implements bip {
    agl<bio> a = new agl<>();
    private Map<String, String> b = new HashMap();
    /* access modifiers changed from: private */
    public Object c = new Object();

    public bih() {
        this.b.clear();
        this.b.put("101", "mapMode");
        this.b.put("103", "traffic_ugc");
        this.b.put("104", "isSaveOverLay");
        this.b.put("201", "lockMapAngle");
        this.b.put("202", "screenon");
        this.b.put(UploadConstants.STATUS_TASK_BY_PUSH, "showzoombtn");
        this.b.put(UploadConstants.STATUS_TASK_BY_CONFIG, "errorReport");
        this.b.put(UploadConstants.STATUS_NET_NOT_MATCH, OfflinePreference.KEY_WIFI_ENABLED);
        this.b.put(UploadConstants.STATUS_FILE_UPLOADING_RETRY, "MapRoadStatus");
        this.b.put(UploadConstants.STATUS_PUSH_RECEIVED, "pushEnabled");
        this.b.put(UploadConstants.STATUS_PUSH_NOTIFIED, "wifiAutoUpdateEnabled");
    }

    public final int a(String str) {
        int c2;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        synchronized (this.c) {
            c2 = c(str);
        }
        return c2;
    }

    public final void a(String str, int i) {
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.c) {
                b(str, i);
            }
        }
    }

    public final void a() {
        ahm.a(new Runnable() {
            public final void run() {
                JsonDatasWithType h = bim.aa().h((String) "201");
                if (h != null) {
                    List<JsonDataWithId> list = h.jsonDataWithId;
                    if (list != null && list.size() != 0) {
                        HashMap hashMap = new HashMap();
                        for (JsonDataWithId next : list) {
                            try {
                                hashMap.put(next.id, Integer.valueOf(agd.a(new JSONObject(next.data), "value")));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        synchronized (bih.this.c) {
                            for (String str : hashMap.keySet()) {
                                bih.this.b(str, ((Integer) hashMap.get(str)).intValue());
                            }
                        }
                        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                        if (iOfflineManager != null) {
                            boolean z = true;
                            if (bih.c(UploadConstants.STATUS_NET_NOT_MATCH) != 1) {
                                z = false;
                            }
                            iOfflineManager.setAutoDownloadInWifiSwitchState(z);
                        }
                        aho.a(new Runnable() {
                            public final void run() {
                                bih.this.a.a((a<T>) new a<bio>() {
                                    public final /* synthetic */ void onNotify(Object obj) {
                                        ((bio) obj).a();
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static int c(String str) {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        if (str.equals("101")) {
            return mapSharePreference.getIntValue("mapMode", 0);
        }
        return a(mapSharePreference, str);
    }

    /* access modifiers changed from: private */
    public void b(String str, int i) {
        if (this.b.containsKey(str)) {
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            if (str.equals("101")) {
                mapSharePreference.putIntValue("mapMode", i);
            } else {
                a(mapSharePreference, str, i);
            }
        }
    }

    private void a(MapSharePreference mapSharePreference, String str, int i) {
        mapSharePreference.putBooleanValue(this.b.get(str), i > 0);
    }

    private static int a(MapSharePreference mapSharePreference, String str) {
        return str.equals("103") ? mapSharePreference.getBooleanValue("traffic_ugc", false) ? 1 : 0 : str.equals("104") ? mapSharePreference.getBooleanValue("isSaveOverLay", true) ? 1 : 0 : str.equals("201") ? mapSharePreference.getBooleanValue("lockMapAngle", true) ? 1 : 0 : str.equals("202") ? mapSharePreference.getBooleanValue("screenon", false) ? 1 : 0 : str.equals(UploadConstants.STATUS_TASK_BY_PUSH) ? mapSharePreference.getBooleanValue("showzoombtn", false) ? 1 : 0 : str.equals(UploadConstants.STATUS_TASK_BY_CONFIG) ? mapSharePreference.getBooleanValue("errorReport", true) ? 1 : 0 : str.equals(UploadConstants.STATUS_NET_NOT_MATCH) ? mapSharePreference.getBooleanValue(OfflinePreference.KEY_WIFI_ENABLED, true) ? 1 : 0 : str.equals(UploadConstants.STATUS_FILE_UPLOADING_RETRY) ? mapSharePreference.getBooleanValue("MapRoadStatus", false) ? 1 : 0 : str.equals(UploadConstants.STATUS_PUSH_RECEIVED) ? mapSharePreference.getBooleanValue("pushEnabled", true) ? 1 : 0 : (!str.equals(UploadConstants.STATUS_PUSH_NOTIFIED) || !mapSharePreference.getBooleanValue("wifiAutoUpdateEnabled", true)) ? 0 : 1;
    }

    public final void a(bio bio) {
        this.a.a(bio);
    }
}
