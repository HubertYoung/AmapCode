package defpackage;

import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.offline.preference.OfflinePreference;
import com.autonavi.server.aos.serverkey;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.TtsManagerUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* renamed from: tl reason: default package */
/* compiled from: FeedBckSettingTools */
public class tl {
    private static volatile tl c;
    public List<GeoPoint> a = new ArrayList();
    public List<GeoPoint> b = new ArrayList();
    private List<GeoPoint> d = new ArrayList();
    private List<GeoPoint> e = new ArrayList();
    private List<GeoPoint> f = new ArrayList();
    private List<GeoPoint> g = new ArrayList();

    private tl() {
    }

    public static tl a() {
        tl tlVar;
        synchronized (tl.class) {
            try {
                if (c == null) {
                    c = new tl();
                }
                tlVar = c;
            }
        }
        return tlVar;
    }

    public final String a(Context context, String str, sd sdVar) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(c())) {
                jSONObject.put("mainroad", serverkey.amapEncode(c()));
            }
            if (!TextUtils.isEmpty(d())) {
                jSONObject.put("sideroad", serverkey.amapEncode(d()));
            }
            if (!TextUtils.isEmpty(e())) {
                jSONObject.put("offhook", serverkey.amapEncode(e()));
            }
            if (!TextUtils.isEmpty(f())) {
                jSONObject.put("ringoff", serverkey.amapEncode(f()));
            }
            if (!TextUtils.isEmpty(g())) {
                jSONObject.put("backgrounder", serverkey.amapEncode(g()));
            }
            if (!TextUtils.isEmpty(h())) {
                jSONObject.put("backinterface", serverkey.amapEncode(h()));
            }
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("naviid", str);
            }
            if (sdVar != null) {
                jSONObject.put("otheraudioplaying", sdVar.a);
                jSONObject.put("volume", sdVar.b);
                jSONObject.put("ttsxiaoyanfile", sdVar.d);
                jSONObject.put("ttscommonfile", sdVar.c);
                jSONObject.put("lastttstimeslot", sdVar.e);
            }
            int i = 0;
            jSONObject.put("offlinemap", new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(OfflinePreference.KEY_OFFLINE_MAP_PRIORI_ENABLED, false) ? "1" : "0");
            jSONObject.put("offlinenav", DriveSpUtil.getSearchRouteInNetMode(context) ^ true ? "1" : "0");
            jSONObject.put("offlinedata", new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(OfflinePreference.KEY_WIFI_ENABLED, true) ? "1" : "0");
            StringBuilder sb = new StringBuilder();
            int i2 = DriveSpUtil.getInt(context, DriveSpUtil.BROADCAST_MODE, 2);
            if (i2 == 2) {
                sb.append("0");
            } else if (i2 == 1) {
                sb.append("1");
            }
            jSONObject.put("broadcastmode", sb.toString());
            jSONObject.put("speedcamera", DriveSpUtil.getBool(context, DriveSpUtil.PLAY_ELE_EYE, true) ? "1" : "0");
            jSONObject.put("roadahead", DriveSpUtil.getBool(context, DriveSpUtil.PLAY_ROUTE_TRAFFIC, true) ? "1" : "0");
            jSONObject.put("parking", DriveSpUtil.getBool(context, DriveSpUtil.PARKING_RECOMMEND, false) ? "1" : "0");
            switch (aaw.b(AMapAppGlobal.getApplication())) {
                case 0:
                    break;
                case 1:
                    i = 2;
                    break;
                case 2:
                    i = 3;
                    break;
                case 3:
                    i = 4;
                    break;
                case 4:
                    i = 1;
                    break;
            }
            jSONObject.put("nettype", String.valueOf(i));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    private String c() {
        StringBuilder sb = new StringBuilder();
        int size = this.d.size();
        for (int i = 0; i < size; i++) {
            GeoPoint geoPoint = this.d.get(i);
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            if (i != size - 1) {
                sb.append(MergeUtil.SEPARATOR_KV);
            }
        }
        return sb.toString();
    }

    private String d() {
        StringBuilder sb = new StringBuilder();
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            GeoPoint geoPoint = this.e.get(i);
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            if (i != size - 1) {
                sb.append(MergeUtil.SEPARATOR_KV);
            }
        }
        return sb.toString();
    }

    private String e() {
        StringBuilder sb = new StringBuilder();
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            GeoPoint geoPoint = this.a.get(i);
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            if (i != size - 1) {
                sb.append(MergeUtil.SEPARATOR_KV);
            }
        }
        return sb.toString();
    }

    private String f() {
        StringBuilder sb = new StringBuilder();
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            GeoPoint geoPoint = this.b.get(i);
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            if (i != size - 1) {
                sb.append(MergeUtil.SEPARATOR_KV);
            }
        }
        return sb.toString();
    }

    private String g() {
        StringBuilder sb = new StringBuilder();
        int size = this.f.size();
        for (int i = 0; i < size; i++) {
            GeoPoint geoPoint = this.f.get(i);
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            if (i != size - 1) {
                sb.append(MergeUtil.SEPARATOR_KV);
            }
        }
        return sb.toString();
    }

    private String h() {
        StringBuilder sb = new StringBuilder();
        int size = this.g.size();
        for (int i = 0; i < size; i++) {
            GeoPoint geoPoint = this.g.get(i);
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            if (i != size - 1) {
                sb.append(MergeUtil.SEPARATOR_KV);
            }
        }
        return sb.toString();
    }

    public static sd b() {
        int i;
        sd sdVar = new sd();
        sdVar.a = (!bnz.a() || PlaySoundUtils.getInstance().isPlaying()) ? 0 : 1;
        AudioManager audioManager = (AudioManager) AMapAppGlobal.getApplication().getApplicationContext().getSystemService("audio");
        if (audioManager == null) {
            i = -1;
        } else {
            i = audioManager.getStreamVolume(3);
        }
        sdVar.b = i;
        sdVar.c = TtsManagerUtil.isCommonFileExist() ? 1 : 0;
        sdVar.d = TtsManagerUtil.isDefaultVoiceFileExist() ? 1 : 0;
        sdVar.e = (int) ((System.currentTimeMillis() - PlaySoundUtils.getInstance().getLastTTSTime()) / 1000);
        return sdVar;
    }
}
