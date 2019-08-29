package defpackage;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.AEUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.jni.ae.pos.LocListener;
import com.autonavi.jni.drive.offline.BehaviorService.OnBehaviorLogInterface;
import com.autonavi.jni.drive.offline.HttpBaseRequest;
import com.autonavi.jni.drive.offline.HttpRequestQueue.OnCreateHttpRequestListener;
import com.autonavi.jni.drive.offline.Logger.OnLogCallBackListener;
import com.autonavi.jni.drive.offline.NativeDriveOfflineManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: oh reason: default package */
/* compiled from: DriveOfflineManager */
public class oh {
    private static volatile oh l;
    public NativeDriveOfflineManager a = new NativeDriveOfflineManager(AMapPageUtil.getAppContext());
    public int b = -1;
    public int[] c;
    public boolean d = false;
    public boolean e = false;
    public boolean f = false;
    public int g = -1;
    @SuppressLint({"HandlerLeak"})
    public Handler h = new Handler() {
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    oh.a((String) "MSG_NATIVE_INTI");
                    if (!oh.this.d) {
                        oh.b(oh.this);
                        if (!oh.this.f) {
                            oh.this.c();
                            return;
                        }
                    }
                    break;
                case 1:
                    int a2 = oh.b(LocationInstrument.getInstance().getLatestPosition());
                    StringBuilder sb = new StringBuilder("MSG_CHANGE_CURRENT_CITY---adcode=");
                    sb.append(a2);
                    sb.append("---***mCurrentAdcode=");
                    sb.append(oh.this.g);
                    AMapLog.d("DriveOfflineManager", sb.toString());
                    if (oh.this.d && oh.this.g != a2) {
                        oh.this.a.nativeChangeCurrentCity(a2);
                        oh.this.g = a2;
                        return;
                    }
                case 2:
                    StringBuilder sb2 = new StringBuilder("adcode=");
                    sb2.append(oh.b(LocationInstrument.getInstance().getLatestPosition()));
                    AMapLog.d("DriveOfflineManager", sb2.toString());
                    int a3 = oh.b(LocationInstrument.getInstance().getLatestPosition());
                    if (oh.this.d && oh.this.g != a3) {
                        oh.this.g = a3;
                        oh.this.a.nativeChangeCurrentCity(oh.this.g);
                        return;
                    }
                case 3:
                    if (oh.this.d) {
                        oh.this.a.nativeChangePernanentCity(oh.this.b);
                        return;
                    }
                    break;
                case 4:
                    StringBuilder sb3 = new StringBuilder("MSG_CHANGE_AVAILABLE_CITIES---isInit=");
                    sb3.append(oh.this.d);
                    AMapLog.d("DriveOfflineManager", sb3.toString());
                    if (oh.this.d) {
                        oh.this.a(lt.a().c.z);
                        oh.this.a.nativeChangeAvailableCityList(oh.this.c);
                        break;
                    }
                    break;
            }
        }
    };
    public lp i = new lp() {
        public final void onConfigResultCallBack(int i, String str) {
            StringBuilder sb = new StringBuilder("onConfigResultCallBack----status=");
            sb.append(i);
            sb.append("***result==");
            sb.append(str);
            AMapLog.d("DriveOfflineManager", sb.toString());
            if (i != 1) {
                str = lo.a().a((String) "3d_cross");
            }
            oh.a(oh.this, str);
        }

        public final void onConfigCallBack(int i) {
            oh.a(oh.this, lo.a().a((String) "3d_cross"));
        }
    };
    public a j = new a() {
        public final void a(JSONObject jSONObject) {
            if (jSONObject != null) {
                oh.this.h.sendEmptyMessage(4);
            }
        }
    };
    public lp k = new lp() {
        public final void onConfigResultCallBack(int i, String str) {
            StringBuilder sb = new StringBuilder("mUserInfoListener----status=");
            sb.append(i);
            sb.append("***result==");
            sb.append(str);
            AMapLog.d("DriveOfflineManager", sb.toString());
            if (i != 1) {
                str = lo.a().a((String) "_user_profile_");
            }
            oh.b(oh.this, str);
        }

        public final void onConfigCallBack(int i) {
            oh.b(oh.this, lo.a().a((String) "_user_profile_"));
        }
    };
    private int m;
    /* access modifiers changed from: private */
    public long n;
    private OnCreateHttpRequestListener o = new OnCreateHttpRequestListener() {
        public final void onHttpRequestCreate(HttpBaseRequest httpBaseRequest) {
            oi oiVar = new oi();
            oiVar.b = httpBaseRequest;
            httpBaseRequest.setHttpRequest(oiVar);
        }
    };
    private LocListener p = new LocListener() {
        public final void updateNaviInfo(LocInfo locInfo) {
            if (locInfo != null && !oh.this.f) {
                if (!oh.this.e) {
                    oh.this.e = true;
                    oh.this.h.sendEmptyMessage(2);
                    return;
                }
                if (System.currentTimeMillis() - oh.this.n >= 300000) {
                    oh.this.n = System.currentTimeMillis();
                    oh.this.h.sendEmptyMessage(1);
                }
            }
        }
    };
    private OnLogCallBackListener q = new OnLogCallBackListener() {
        public final void onLogCall(int i, String str, String str2, String str3, String str4, String str5) {
            if (oh.a(i) == ALCLogLevel.P1) {
                AMapLog.logFatalNative(str, str3, str4, str5);
            }
            if (bno.a) {
                ku.a().c("DriveOfflineManager", str5);
            }
        }
    };
    private OnBehaviorLogInterface r = new OnBehaviorLogInterface() {
        public final void onBehaviorLogCall(String str, String str2, String str3) {
            if (TextUtils.isEmpty(str)) {
                AMapLog.e("DriveOfflineManager", "BehaviorService.log pageId is empty");
            }
            if (TextUtils.isEmpty(str2)) {
                AMapLog.e("DriveOfflineManager", "BehaviorService.log btnId is empty");
            }
            if (TextUtils.isEmpty(str3)) {
                AMapLog.e("DriveOfflineManager", "BehaviorService.log jsonParams is empty");
            }
            try {
                LogManager.actionLogV2(str, str2, new JSONObject(str3));
            } catch (JSONException unused) {
                StringBuilder sb = new StringBuilder("BehaviorService.log failed to converty ");
                sb.append(str3);
                sb.append(", to json object");
                AMapLog.e("DriveOfflineManager", sb.toString());
            }
        }
    };

    public static oh a() {
        if (l == null) {
            synchronized (oh.class) {
                try {
                    if (l == null) {
                        l = new oh();
                    }
                }
            }
        }
        return l;
    }

    public final void b() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("3d_cross");
        arrayList.add("_user_profile_");
        lo.a().a((String) "3d_cross", this.i);
        lo.a().a((String) "_user_profile_", this.k);
        lo.a().a((List<String>) arrayList);
        anf.a(this.p, 1);
        lt.a().a(this.j);
    }

    private oh() {
        StringBuilder sb = new StringBuilder("DriveOfflineManager---mOnCreateHttpRequestListener=");
        sb.append(this.o);
        AMapLog.e("DriveOfflineManager", sb.toString());
        this.a.setOnLogCallBackListener(this.q);
        this.a.setOnBehaviorLogInterface(this.r);
        this.a.setOnCreateHttpRequestListener(this.o);
    }

    public final void c() {
        a((String) "DriveOfflineManager.start");
        if (e() && this.d) {
            this.a.nativeStart();
            this.f = false;
        }
    }

    /* access modifiers changed from: private */
    public static int b(GeoPoint geoPoint) {
        li a2 = li.a();
        if (a2 != null) {
            lj b2 = a2.b(geoPoint.x, geoPoint.y);
            if (b2 != null) {
                return b2.j;
            }
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public void a(List<ma> list) {
        if (list != null && list.size() != 0) {
            this.c = new int[list.size()];
            int i2 = 0;
            while (i2 < list.size()) {
                try {
                    this.c[i2] = Integer.parseInt(list.get(i2).a);
                    i2++;
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
        }
    }

    public final boolean d() {
        File[] listFiles;
        String a2 = PathManager.a().a(DirType.DRIVE_OFFLINE);
        File file = new File(a2);
        if (!file.exists()) {
            StringBuilder sb = new StringBuilder("DriveOfflineManager::hasOfflineData ");
            sb.append(a2);
            sb.append(" does not exist");
            AMapLog.e("DriveOfflineManager", sb.toString());
            StringBuilder sb2 = new StringBuilder("DriveOfflineManager::hasOfflineData ");
            sb2.append(a2);
            sb2.append(" does not exist");
            a(sb2.toString());
            return false;
        } else if (!file.isDirectory()) {
            StringBuilder sb3 = new StringBuilder("DriveOfflineManager::hasOfflineData ");
            sb3.append(a2);
            sb3.append(" is not a directory");
            AMapLog.e("DriveOfflineManager", sb3.toString());
            StringBuilder sb4 = new StringBuilder("DriveOfflineManager::hasOfflineData ");
            sb4.append(a2);
            sb4.append(" is not a directory");
            a(sb4.toString());
            return false;
        } else {
            for (File file2 : file.listFiles(new FilenameFilter() {
                public final boolean accept(File file, String str) {
                    if (TextUtils.isEmpty(str) || str.length() < 2 || !str.startsWith("a")) {
                        return false;
                    }
                    try {
                        Integer.parseInt(str.substring(1));
                        return true;
                    } catch (NumberFormatException unused) {
                        return false;
                    }
                }
            })) {
                if (file2.isDirectory()) {
                    StringBuilder sb5 = new StringBuilder(file2.getPath());
                    sb5.append(File.separator);
                    sb5.append("m4_pro.ans");
                    String sb6 = sb5.toString();
                    File file3 = new File(sb6);
                    if (file3.exists() && file3.isFile()) {
                        return true;
                    }
                    StringBuilder sb7 = new StringBuilder("DriveOfflineManager::hasOfflineData file ");
                    sb7.append(sb6);
                    sb7.append(" does not exist or not a file");
                    AMapLog.e("DriveOfflineManager", sb7.toString());
                    StringBuilder sb8 = new StringBuilder("DriveOfflineManager::hasOfflineData file ");
                    sb8.append(sb6);
                    sb8.append(" does not exist or not a file");
                    a(sb8.toString());
                }
            }
            return false;
        }
    }

    private boolean e() {
        return this.m == 1;
    }

    public static void a(String str) {
        AMapLog.d("DriveOfflineManager", str);
        ku.a().c("DriveOfflineManager", str);
    }

    static /* synthetic */ void b(oh ohVar) {
        if (!ohVar.e()) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("initConfig: not driver, mDriverUser = ");
                sb.append(ohVar.m);
                a(sb.toString());
            }
            return;
        }
        String replace = AMapAppGlobal.getApplication().getFilesDir().getAbsolutePath().replace(AutoJsonUtils.JSON_FILES, "offline3dcross");
        AMapLog.d("DriveOfflineManager", "initConfig---tempPath=".concat(String.valueOf(replace)));
        String a2 = PathManager.a().a(DirType.DRIVE_OFFLINE);
        ohVar.g = b(LocationInstrument.getInstance().getLatestPosition());
        String keyValue = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY);
        ohVar.a(lt.a().c.z);
        StringBuilder sb2 = new StringBuilder("initConfig---currentAdcode=");
        sb2.append(ohVar.g);
        sb2.append("**mResidentCity=");
        sb2.append(ohVar.b);
        AMapLog.e("DriveOfflineManager", sb2.toString());
        try {
            ohVar.a.nativeInit(replace, a2, keyValue, ohVar.g, ohVar.b, ohVar.c);
            ohVar.d = true;
        } catch (UnsatisfiedLinkError e2) {
            DebugLog.e("DriveOfflineManager", "error:", e2);
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) AMapPageUtil.getAppContext().getSystemService("connectivity");
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        ohVar.a.setNetworkStatus(networkInfo);
    }

    static /* synthetic */ void a(oh ohVar, String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                ohVar.m = new JSONObject(str).optInt("drive_user");
            } catch (JSONException e2) {
                DebugLog.e("DriveOfflineManager", "e:", e2);
            }
        }
        int i2 = 1;
        if (ohVar.m != 1) {
            if (AEUtil.IS_DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory().toString());
                sb.append("/autonavi/offline3dcross.stage");
                File file = new File(sb.toString());
                if (!file.exists() || !file.isFile()) {
                    AMapLog.e("DriveOfflineManager", "isStage is false");
                } else {
                    AMapLog.e("DriveOfflineManager", "isStage is true");
                    ohVar.m = i2;
                }
            }
            i2 = 0;
            ohVar.m = i2;
        }
        ohVar.h.sendEmptyMessageDelayed(0, 15000);
    }

    static /* synthetic */ void b(oh ohVar, String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                ohVar.b = Integer.parseInt(new JSONObject(str).optString("adCode"));
            } catch (Exception e2) {
                DebugLog.e("DriveOfflineManager", "e:", e2);
            }
            ohVar.h.sendEmptyMessage(3);
        }
        ohVar.b = 0;
        ohVar.h.sendEmptyMessage(3);
    }

    static /* synthetic */ ALCLogLevel a(int i2) {
        switch (i2) {
            case 1:
                return ALCLogLevel.P1;
            case 2:
                return ALCLogLevel.P2;
            case 3:
                return ALCLogLevel.P3;
            case 4:
                return ALCLogLevel.P4;
            case 5:
                return ALCLogLevel.P5;
            default:
                return ALCLogLevel.P5;
        }
    }
}
