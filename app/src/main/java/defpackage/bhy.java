package defpackage;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.tripgroup.api.IVehicleInfoEvent;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.offline.preference.OfflinePreference;
import com.autonavi.server.aos.serverkey;
import com.autonavi.sync.GirfSyncServiceSDK;
import com.autonavi.sync.GirfSyncServiceSDK.GirfSyncService;
import com.autonavi.sync.GrifSyncSDK;
import com.autonavi.sync.GrifSyncSDK.GirfSync;
import com.autonavi.sync.ICallback;
import com.autonavi.sync.INetwork;
import com.autonavi.sync.beans.GirfFavoritePoint;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.autonavi.sync.beans.JsonDataWithId;
import com.autonavi.sync.beans.JsonDatasWithType;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bhy reason: default package */
/* compiled from: SyncManagerImpl */
public class bhy implements bik {
    public static boolean G = false;
    private static volatile int O;
    @Deprecated
    public static bir a;
    @Deprecated
    public static biq b;
    public static bix k;
    public boolean A = false;
    public boolean B = false;
    GrifSyncSDK C;
    GirfSyncServiceSDK D;
    GirfSync E;
    GirfSyncService F;
    private bij H;
    private JsFunctionCallback I = null;
    private String J = bim.class.getSimpleName();
    private String K = "ws/sync/files/download";
    private String L = "id";
    private boolean M = false;
    private bip N = ((bip) ank.a(bip.class));
    private int P = 0;
    public biy c = null;
    public biz d = null;
    public bjb e = null;
    agl<biy> f = new agl<>();
    agl<bja> g = new agl<>();
    agl<bix> h = new agl<>();
    agl<bjc> i = new agl<>();
    agl<Object> j = new agl<>();
    @Deprecated
    public biv l = null;
    @Deprecated
    public biw m = null;
    @Deprecated
    public bis n = null;
    @Deprecated
    public bit o = null;
    @Deprecated
    public biu p = null;
    @Deprecated
    public List<bii> q = new LinkedList();
    public boolean r = false;
    public boolean s = false;
    public boolean t = true;
    public boolean u = false;
    public boolean v = false;
    public boolean w = false;
    public boolean x = false;
    public boolean y = false;
    public boolean z = false;

    @Deprecated
    public static bhy a() {
        return (bhy) ank.a(bik.class);
    }

    public final void a(biy biy) {
        this.c = biy;
    }

    public final void a(biz biz) {
        this.d = biz;
    }

    public final void b(biy biy) {
        this.f.a(biy);
    }

    public final void c(biy biy) {
        this.f.b(biy);
    }

    public final void b() {
        this.f.a((a<T>) new a<biy>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((biy) obj).updateSuccess();
            }
        });
    }

    public final void a(bja bja) {
        this.g.a(bja);
    }

    public final void b(bja bja) {
        this.g.b(bja);
    }

    public final void a(final boolean z2) {
        this.g.a((a<T>) new a<bja>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((bja) obj).onMergeEnd(z2);
            }
        });
    }

    public final void c() {
        this.h.a((a<T>) new a<bix>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((bix) obj).a();
            }
        });
    }

    public final void a(bjc bjc) {
        this.i.a(bjc);
    }

    public final void b(bjc bjc) {
        this.i.b(bjc);
    }

    public final void d() {
        this.i.a((a<T>) new a<bjc>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((bjc) obj).onWantMerge();
            }
        });
    }

    private void o(final String str) {
        this.j.a((a<T>) new a<Object>() {
            public final /* bridge */ /* synthetic */ void onNotify(Object obj) {
            }
        });
    }

    public final void a(bio bio) {
        if (this.N != null) {
            this.N.a(bio);
        }
    }

    public final void a(bix bix) {
        k = bix;
    }

    @Deprecated
    public final void a(biv biv) {
        this.l = biv;
    }

    @Deprecated
    public final void a(biw biw) {
        this.m = biw;
    }

    @Deprecated
    public final void a(bis bis) {
        this.n = bis;
    }

    @Deprecated
    public final void a(bit bit) {
        this.o = bit;
    }

    @Deprecated
    public final void a(biu biu) {
        this.p = biu;
    }

    @Deprecated
    public final void a(bii bii) {
        this.q.add(bii);
    }

    public final void e() {
        if (this.o != null) {
            this.o.showDialog();
        }
    }

    @Deprecated
    public final void f() {
        for (bii next : this.q) {
            if (this.E != null) {
                this.E.beginTransactionForChangeData();
            }
            next.a();
            if (this.E != null) {
                this.E.endTransactionForChangeData();
            }
            next.b();
        }
        if (this.q != null && this.q.size() > 0) {
            this.q.clear();
        }
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("FIRST_MERGE_MAP_SET", true);
    }

    public final void b(boolean z2) {
        this.v = z2;
    }

    public final void c(boolean z2) {
        this.M = z2;
    }

    public final boolean g() {
        return this.t;
    }

    public final void d(boolean z2) {
        this.t = z2;
    }

    public final boolean h() {
        return this.s;
    }

    public final void e(boolean z2) {
        this.s = z2;
    }

    public final void f(boolean z2) {
        this.z = z2;
    }

    public final boolean i() {
        return this.z;
    }

    public final void g(boolean z2) {
        this.B = z2;
    }

    public final boolean j() {
        return this.B;
    }

    public final void h(boolean z2) {
        this.A = z2;
    }

    public final void i(boolean z2) {
        this.y = z2;
    }

    public final void j(boolean z2) {
        this.x = z2;
    }

    public final boolean k() {
        return this.A;
    }

    public final boolean l() {
        return this.x;
    }

    public final boolean m() {
        return this.y;
    }

    public final GirfSyncService n() {
        return this.F;
    }

    public final List<GirfFavoritePoint> o() {
        if (this.E == null) {
            return null;
        }
        return this.E.getHomeListSorted();
    }

    public final List<GirfFavoritePoint> p() {
        if (this.E == null) {
            return null;
        }
        return this.E.getCompanyListSorted();
    }

    public final int a(String str) {
        if (this.E == null) {
            return 1280;
        }
        return this.E.getDataCountByType(str);
    }

    public static boolean q() {
        return bno.a;
    }

    public final void a(String str, bij bij) {
        if (bij == null) {
            this.H = bij.a;
        } else {
            this.H = bij;
        }
        if (bno.a) {
            GrifSyncSDK.turnOnDebug();
        }
        this.C = new GrifSyncSDK();
        this.C.init(str);
        ICallback iCallback = (ICallback) ank.a(ICallback.class);
        this.E = this.C.createSyncInstance("amap", "www.syncamap.com", (INetwork) ank.a(INetwork.class), iCallback);
        if (this.E == null) {
            AMapLog.logFatalNative(AMapLog.GROUP_COMMON, "P0004", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, "com.autonavi.common.cloudsync.SyncManager init return null");
        }
        if (this.E != null) {
            this.D = new GirfSyncServiceSDK();
            this.F = this.D.createInstance(this.E, iCallback);
        }
    }

    public final List<String> r() {
        if (this.E == null) {
            return null;
        }
        return this.E.getCityNames();
    }

    public final List<String> s() {
        ArrayList arrayList = new ArrayList();
        if (this.E == null) {
            return null;
        }
        int[] routeIds = this.E.getRouteIds();
        if (routeIds != null && routeIds.length > 0) {
            for (int append : routeIds) {
                StringBuilder sb = new StringBuilder();
                sb.append(append);
                arrayList.add(sb.toString());
            }
        }
        return arrayList;
    }

    public final boolean t() {
        return this.H.c();
    }

    public final List<String> u() {
        if (this.E == null) {
            return null;
        }
        return this.E.getClassifications();
    }

    public final GirfFavoriteRoute b(String str) {
        if (!TextUtils.isEmpty(str) && this.E != null) {
            return this.E.getFavoriteRouteObject(Integer.parseInt(str));
        }
        return null;
    }

    public final List<String> v() {
        return this.E.getCustomLabels();
    }

    public final List<String> c(String str) {
        ArrayList arrayList = new ArrayList();
        if (this.E == null || this.E == null) {
            return null;
        }
        int[] poiIdsByLabel = this.E.getPoiIdsByLabel(str);
        if (poiIdsByLabel == null || poiIdsByLabel.length == 0) {
            return null;
        }
        for (int append : poiIdsByLabel) {
            StringBuilder sb = new StringBuilder();
            sb.append(append);
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    public final List<String> d(String str) {
        ArrayList arrayList = new ArrayList();
        if (this.E == null) {
            return null;
        }
        int[] poiIdsByClassification = this.E.getPoiIdsByClassification(str);
        if (poiIdsByClassification == null || poiIdsByClassification.length == 0) {
            return null;
        }
        for (int append : poiIdsByClassification) {
            StringBuilder sb = new StringBuilder();
            sb.append(append);
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    public final List<String> e(String str) {
        ArrayList arrayList = new ArrayList();
        int[] poiIdsByCityName = this.E.getPoiIdsByCityName(str);
        if (poiIdsByCityName == null || poiIdsByCityName.length == 0) {
            return null;
        }
        for (int append : poiIdsByCityName) {
            StringBuilder sb = new StringBuilder();
            sb.append(append);
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    public final boolean w() {
        return this.r;
    }

    public final void k(boolean z2) {
        this.r = z2;
    }

    public final int a(String str, String str2, String str3, int i2) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str3);
        } catch (JSONException e2) {
            e2.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject != null) {
            agd.a(jSONObject, (String) "version", 1);
        }
        if (this.E == null) {
            return 1280;
        }
        int data = this.E.setData(str, str2, jSONObject.toString(), i2);
        if (data == 0 && str != null && (str.equals("101") || str.equals("102") || str.equals("103") || str.equals("104") || str.equals("105") || str.equals("901"))) {
            AMapAppGlobal.getApplication();
            bst.a();
            int a2 = bst.a(x());
            AMapAppGlobal.getApplication();
            bss.a();
            int a3 = a2 + bss.a(x()) + this.E.getDataCountByType("901");
            if (a3 > 0 && ((a3 == 3 || a3 % 5 == 0) && this.l != null)) {
                this.l.saveSucess();
            }
            if (str == "101") {
                if (this.m != null) {
                    this.m.a();
                } else {
                    this.z = true;
                }
            }
        }
        return data;
    }

    public final String x() {
        String str;
        try {
            str = this.H.a();
        } catch (Throwable th) {
            th.printStackTrace();
            str = null;
        }
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str)) {
            SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("SecurityPerson", 0);
            if (sharedPreferences != null) {
                String string = sharedPreferences.getString(Oauth2AccessToken.KEY_UID, null);
                if (string != null) {
                    str = serverkey.amapDecode(string);
                }
            }
        }
        return TextUtils.isEmpty(str) ? "public" : str;
    }

    public final int b(String str, String str2, String str3, int i2) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str3);
        } catch (JSONException e2) {
            e2.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject != null) {
            agd.a(jSONObject, (String) "version", 1);
        }
        if (this.E == null) {
            return 1280;
        }
        return this.E.setData(str, str2, jSONObject.toString(), 1);
    }

    public final int a(String str, String str2, int i2) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str2);
        } catch (JSONException unused) {
            jSONObject = null;
        }
        if (jSONObject != null) {
            agd.a(jSONObject, (String) "version", 1);
        }
        if (this.E == null) {
            return 1280;
        }
        return this.E.setData("301", str, jSONObject.toString(), i2);
    }

    public final JsonDatasWithType a(String str, String str2) {
        if (this.E == null) {
            return null;
        }
        return this.E.getDataItem(str, str2);
    }

    public final GirfFavoritePoint f(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int parseInt = Integer.parseInt(str);
        if (this.E == null) {
            return null;
        }
        return this.E.getFavoritePointItemById(parseInt);
    }

    public final int b(String str, String str2, int i2) {
        if (this.E == null) {
            return 1280;
        }
        int clearData = this.E.clearData(str, str2, i2);
        if (clearData == 0 && "101".equals(str)) {
            if (this.m != null) {
                this.m.a();
            } else {
                this.z = true;
            }
        }
        return clearData;
    }

    public final void a(int i2) {
        List<GirfFavoritePoint> o2 = o();
        if (o2 != null && !o2.isEmpty()) {
            for (GirfFavoritePoint girfFavoritePoint : o2) {
                b("101", girfFavoritePoint.item_id, 0);
            }
            if (i2 == 1) {
                z();
            }
        }
    }

    public final void b(int i2) {
        List<GirfFavoritePoint> p2 = p();
        if (p2 != null && !p2.isEmpty()) {
            for (GirfFavoritePoint girfFavoritePoint : p2) {
                b("101", girfFavoritePoint.item_id, 0);
            }
            if (i2 == 1) {
                z();
            }
        }
    }

    public final List<String> c(int i2) {
        if (this.E == null) {
            return null;
        }
        return this.E.getSearchHistory(i2);
    }

    public final List<String> y() {
        if (this.E == null) {
            return null;
        }
        int[] favoritePointIds = this.E.getFavoritePointIds();
        ArrayList arrayList = new ArrayList();
        if (favoritePointIds != null) {
            for (int append : favoritePointIds) {
                StringBuilder sb = new StringBuilder();
                sb.append(append);
                arrayList.add(sb.toString());
            }
        }
        return arrayList;
    }

    public final int a(String str, String str2, String str3, String str4) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str4);
        } catch (JSONException e2) {
            e2.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject != null) {
            agd.a(jSONObject, (String) "version", 1);
        }
        if (this.E == null) {
            return 1280;
        }
        return this.E.setDataForUser(str, str2, str3, jSONObject.toString());
    }

    public final List<JsonDataWithId> g(String str) {
        if (this.E == null) {
            return null;
        }
        return this.E.getDataByPoiid(str);
    }

    public final JsonDatasWithType h(String str) {
        if (this.E == null) {
            return null;
        }
        return this.E.getDataItem(str, "");
    }

    public final String b(String str, String str2) {
        String str3 = "";
        new ArrayList();
        if (this.E == null) {
            return str3;
        }
        JsonDatasWithType dataItem = this.E.getDataItem(str, str2);
        if (!(dataItem == null || dataItem.jsonDataWithId == null || dataItem.jsonDataWithId.size() <= 0)) {
            List<JsonDataWithId> list = dataItem.jsonDataWithId;
            if (!(list.get(0) == null || list.get(0).data == null)) {
                str3 = list.get(0).data;
            }
        }
        return str3;
    }

    public final int z() {
        if (this.E == null) {
            return 1280;
        }
        return this.E.startSync();
    }

    public final boolean A() {
        if (this.E == null) {
            return false;
        }
        return this.E.isSyncing();
    }

    public final void a(JsFunctionCallback jsFunctionCallback) {
        this.I = jsFunctionCallback;
    }

    public final void D() {
        this.H.b();
    }

    public final void a(String str, String str2, int i2, String str3) {
        AmapMessage amapMessage = new AmapMessage();
        amapMessage.id = AmapMessage.TOKEN_CLOUD_SYNC_DIALOG;
        amapMessage.title = "";
        amapMessage.descMessage = str;
        amapMessage.priority = i2;
        amapMessage.createdTime = System.currentTimeMillis();
        amapMessage.type = AmapMessage.TYPE_ACTIVITY;
        amapMessage.tag = -1;
        amapMessage.actionUri = str2;
        amapMessage.reside = str3;
        amapMessage.baricon = "";
        try {
            IMsgboxService iMsgboxService = (IMsgboxService) a.a.a(IMsgboxService.class);
            if (iMsgboxService != null) {
                iMsgboxService.getMsgboxStorageService().a(amapMessage);
            }
        } catch (Throwable unused) {
        }
    }

    public final void a(final String str, final int i2) {
        this.N.a(str, i2);
        ahm.a(new Runnable() {
            public final void run() {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("value", i2);
                    jSONObject.put("version", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bhy.this.a((String) "201", str, jSONObject.toString(), 1);
            }
        });
        p(str);
    }

    public final void c(final String str, final String str2) {
        ahm.a(new Runnable() {
            public final void run() {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("value", str2);
                    jSONObject.put("version", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bhy.this.a((String) "201", str, jSONObject.toString(), 1);
            }
        });
    }

    public final void b(final String str, final int i2) {
        this.N.a(str, i2);
        ahm.a(new Runnable() {
            public final void run() {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("value", i2);
                    jSONObject.put("version", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bhy.this.a((String) "201", str, jSONObject.toString(), 0);
            }
        });
        p(str);
    }

    private void p(String str) {
        if (str != null && str.equals(UploadConstants.STATUS_NET_NOT_MATCH)) {
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            if (iOfflineManager != null) {
                iOfflineManager.setAutoDownloadInWifiSwitchState(k((String) UploadConstants.STATUS_NET_NOT_MATCH));
            }
        }
    }

    public final boolean k(String str) {
        return this.N.a(str) > 0;
    }

    public final int l(String str) {
        return this.N.a(str);
    }

    public final String m(String str) {
        String b2 = b((String) "201", str);
        if (!TextUtils.isEmpty(b2)) {
            try {
                return new JSONObject(b2).optString("value");
            } catch (Exception unused) {
            }
        }
        r3 = "";
        return "";
    }

    public final void E() {
        this.N.a();
    }

    public final void F() {
        int i2;
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        boolean booleanValue = mapSharePreference.getBooleanValue("MapMode_Default", false);
        boolean booleanValue2 = mapSharePreference.getBooleanValue("satellite", false);
        boolean booleanValue3 = mapSharePreference.getBooleanValue("MapMode_Bus", false);
        if (!booleanValue) {
            if (booleanValue2) {
                i2 = 1;
            } else if (booleanValue3) {
                i2 = 2;
            }
            boolean booleanValue4 = mapSharePreference.getBooleanValue("isSaveOverLay", true);
            boolean booleanValue5 = mapSharePreference.getBooleanValue("traffic_ugc", false);
            boolean booleanValue6 = mapSharePreference.getBooleanValue("wifiAutoUpdateEnabled", true);
            boolean booleanValue7 = mapSharePreference.getBooleanValue("pushEnabled", true);
            boolean booleanValue8 = mapSharePreference.getBooleanValue("lockMapAngle", true);
            boolean booleanValue9 = mapSharePreference.getBooleanValue("screenon", false);
            boolean booleanValue10 = mapSharePreference.getBooleanValue("errorReport", true);
            boolean booleanValue11 = mapSharePreference.getBooleanValue(OfflinePreference.KEY_WIFI_ENABLED, true);
            boolean booleanValue12 = mapSharePreference.getBooleanValue("MapRoadStatus", false);
            boolean booleanValue13 = mapSharePreference.getBooleanValue("showzoombtn", false);
            b((String) "101", i2);
            b((String) "104", booleanValue4 ? 1 : 0);
            b((String) "103", booleanValue5 ? 1 : 0);
            b((String) UploadConstants.STATUS_PUSH_NOTIFIED, booleanValue6 ? 1 : 0);
            b((String) UploadConstants.STATUS_PUSH_RECEIVED, booleanValue7 ? 1 : 0);
            b((String) "201", booleanValue8 ? 1 : 0);
            b((String) "202", booleanValue9 ? 1 : 0);
            b((String) UploadConstants.STATUS_TASK_BY_CONFIG, booleanValue10 ? 1 : 0);
            b((String) UploadConstants.STATUS_NET_NOT_MATCH, booleanValue11 ? 1 : 0);
            b((String) UploadConstants.STATUS_FILE_UPLOADING_RETRY, booleanValue12 ? 1 : 0);
            b((String) UploadConstants.STATUS_TASK_BY_PUSH, booleanValue13 ? 1 : 0);
        }
        i2 = 0;
        boolean booleanValue42 = mapSharePreference.getBooleanValue("isSaveOverLay", true);
        boolean booleanValue52 = mapSharePreference.getBooleanValue("traffic_ugc", false);
        boolean booleanValue62 = mapSharePreference.getBooleanValue("wifiAutoUpdateEnabled", true);
        boolean booleanValue72 = mapSharePreference.getBooleanValue("pushEnabled", true);
        boolean booleanValue82 = mapSharePreference.getBooleanValue("lockMapAngle", true);
        boolean booleanValue92 = mapSharePreference.getBooleanValue("screenon", false);
        boolean booleanValue102 = mapSharePreference.getBooleanValue("errorReport", true);
        boolean booleanValue112 = mapSharePreference.getBooleanValue(OfflinePreference.KEY_WIFI_ENABLED, true);
        boolean booleanValue122 = mapSharePreference.getBooleanValue("MapRoadStatus", false);
        boolean booleanValue132 = mapSharePreference.getBooleanValue("showzoombtn", false);
        b((String) "101", i2);
        b((String) "104", booleanValue42 ? 1 : 0);
        b((String) "103", booleanValue52 ? 1 : 0);
        b((String) UploadConstants.STATUS_PUSH_NOTIFIED, booleanValue62 ? 1 : 0);
        b((String) UploadConstants.STATUS_PUSH_RECEIVED, booleanValue72 ? 1 : 0);
        b((String) "201", booleanValue82 ? 1 : 0);
        b((String) "202", booleanValue92 ? 1 : 0);
        b((String) UploadConstants.STATUS_TASK_BY_CONFIG, booleanValue102 ? 1 : 0);
        b((String) UploadConstants.STATUS_NET_NOT_MATCH, booleanValue112 ? 1 : 0);
        b((String) UploadConstants.STATUS_FILE_UPLOADING_RETRY, booleanValue122 ? 1 : 0);
        b((String) UploadConstants.STATUS_TASK_BY_PUSH, booleanValue132 ? 1 : 0);
    }

    public final List<String> c(String str, int i2) {
        if (this.E == null) {
            return null;
        }
        return this.E.getNaviSearchHistory(str, i2);
    }

    public final String G() {
        String a2 = this.H.a();
        return (TextUtils.isEmpty(a2) || "public".equals(a2)) ? "" : a2;
    }

    private static void d(int i2) {
        IVehicleInfoEvent iVehicleInfoEvent = (IVehicleInfoEvent) ank.a(IVehicleInfoEvent.class);
        if (iVehicleInfoEvent != null) {
            iVehicleInfoEvent.onVehicleInfoChanged(i2);
        }
    }

    public final void H() {
        if (this.m != null) {
            this.m.a();
        }
    }

    public final boolean I() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("MapMode_Default", false);
    }

    public final boolean J() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("MapMode_Bus", false);
    }

    public final String K() {
        int i2 = O + 1;
        O = i2;
        this.P = i2;
        return String.valueOf(this.P);
    }

    public final boolean n(String str) {
        int i2 = this.P;
        if (i2 <= 0 || !String.valueOf(i2).equals(str)) {
            return false;
        }
        this.P = 0;
        return true;
    }

    public final boolean L() {
        return this.P > 0;
    }

    public final void M() {
        this.P = 0;
    }

    public final int l(boolean z2) {
        this.r = false;
        if (this.E == null) {
            return 1280;
        }
        return this.E.confirmMerge(z2);
    }

    public final int B() {
        this.r = false;
        if (this.E == null) {
            return 1280;
        }
        int loginGuest = this.E.loginGuest();
        if (loginGuest == 0) {
            if (this.N != null) {
                this.N.a();
            }
            if (this.m != null) {
                this.m.a();
            } else {
                this.z = true;
            }
        }
        this.v = false;
        this.w = false;
        this.M = false;
        d(4);
        return loginGuest;
    }

    public final int C() {
        this.r = false;
        if (this.E == null) {
            return 1280;
        }
        int loginGuestWithoutSync = this.E.loginGuestWithoutSync();
        if (loginGuestWithoutSync == 0) {
            this.N.a();
            if (this.m != null) {
                this.m.a();
            } else {
                this.z = true;
            }
        }
        return loginGuestWithoutSync;
    }

    public final int i(String str) {
        if (bnm.b() && this.I != null) {
            this.I.callback(new Object[0]);
        }
        this.r = false;
        if (this.E == null) {
            return 1280;
        }
        int loginUser = this.E.loginUser(str);
        if (loginUser == 0) {
            o(str);
        }
        if (loginUser == 0) {
            this.N.a();
            if (this.m != null) {
                this.m.a();
            } else {
                this.z = true;
            }
            d(3);
        }
        return loginUser;
    }

    public final int j(String str) {
        this.r = false;
        if (this.E == null) {
            return 1280;
        }
        int loginUserWithoutSync = this.E.loginUserWithoutSync(str);
        if (loginUserWithoutSync == 0) {
            o(str);
        }
        if (loginUserWithoutSync == 0) {
            this.N.a();
            if (this.m != null) {
                this.m.a();
            } else {
                this.z = true;
            }
        }
        return loginUserWithoutSync;
    }
}
