package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import org.json.JSONObject;

/* renamed from: cmd reason: default package */
/* compiled from: MonitorConfigProvider */
class cmd implements g, lp {
    private boolean a;

    public void onConfigCallBack(int i) {
    }

    public cmd() {
        a(b());
    }

    public final boolean a() {
        return this.a;
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                int i = new JSONObject(str).getInt("monitorEnable");
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                this.a = z;
            } catch (Exception e) {
                if (bno.a) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onConfigResultCallBack(int i, String str) {
        StringBuilder sb = new StringBuilder("status[");
        sb.append(i);
        sb.append("]result : ");
        sb.append(str);
        AMapLog.e("StartMonitor", sb.toString());
        if (i != 3) {
            switch (i) {
                case 0:
                case 1:
                    b(str);
                    a(str);
                    return;
            }
        } else {
            c();
        }
    }

    private synchronized boolean b(String str) {
        if (AMapPageUtil.getAppContext() == null) {
            return false;
        }
        new MapSharePreference((String) "start_monitor_log_config").putStringValue("start_monitor_log_config_sp_key", str);
        return true;
    }

    private synchronized String b() {
        String str;
        str = "";
        if (AMapPageUtil.getAppContext() != null) {
            str = new MapSharePreference((String) "start_monitor_log_config").getStringValue("start_monitor_log_config_sp_key", "");
        }
        return str;
    }

    private synchronized boolean c() {
        if (AMapPageUtil.getAppContext() == null) {
            return false;
        }
        new MapSharePreference((String) "start_monitor_log_config").edit().clear().apply();
        return true;
    }
}
