package com.amap.bundle.cloudconfig.appinit;

import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import org.json.JSONArray;
import org.json.JSONObject;

public class FunctionSupportConfiger {
    public static final String LOCAL_LOG = "log_local";
    public static final String ROUTE_BUS_TAG = "rtbus";
    public static final int SPLASH_SHOW_SOURCE_AFP = 1;
    public static final int SPLASH_SHOW_SOURCE_AUTONAVI = 0;
    public static final int SPLASH_SHOW_SOURCE_INVALID = -1;
    public static final String SP_NAME_AfpSplashEvents = "AfpSplashEvents";
    public static final String SWITCH_TAG = "switch";
    public static final String TAXI_TAG = "taxi";
    private static FunctionSupportConfiger inst = null;
    public static final String splash_show_source = "splash_show_source";
    /* access modifiers changed from: private */
    public boolean isLoaded = false;
    a mRtBusSupportConfiger = new a(ROUTE_BUS_TAG);
    a mSwitchConfiger = new a(SWITCH_TAG);
    a mTaxiSupportConfiger = new a(TAXI_TAG);

    class a {
        String a = "";
        boolean b = false;
        String c = "";
        lj[] d = null;
        boolean e = false;
        JSONObject f = null;

        a(String str) {
            this.a = str;
        }

        public final boolean a(JSONObject jSONObject, boolean z) {
            this.f = jSONObject;
            if (z) {
                a(jSONObject);
            }
            return true;
        }

        public final boolean b(JSONObject jSONObject, boolean z) {
            if ("True".equals(agd.e(jSONObject, "update"))) {
                this.b = true;
            } else {
                this.b = false;
            }
            if (!this.b) {
                return false;
            }
            this.e = "1".equals(agd.e(jSONObject, "active"));
            this.c = agd.e(jSONObject, "version");
            JSONArray f2 = agd.f(jSONObject, "cities");
            if (f2 == null) {
                f2 = agd.f(jSONObject, "data");
            }
            if (f2 == null) {
                if (z) {
                    a(jSONObject);
                }
                return false;
            }
            int length = f2.length();
            if (length <= 0) {
                if (z) {
                    a(jSONObject);
                }
                return false;
            }
            this.d = new lj[length];
            FunctionSupportConfiger.this.isLoaded = true;
            for (int i = 0; i < length; i++) {
                try {
                    JSONObject jSONObject2 = f2.getJSONObject(i);
                    if (jSONObject2 != null) {
                        this.d[i] = new lj();
                        this.d[i].a = agd.e(jSONObject2, "name");
                        this.d[i].j = Integer.parseInt(agd.e(jSONObject2, AutoJsonUtils.JSON_ADCODE));
                    }
                } catch (Exception unused) {
                }
            }
            if (z) {
                a(jSONObject);
            }
            return true;
        }

        private void a(JSONObject jSONObject) {
            FileWriter fileWriter;
            Throwable th;
            try {
                File access$100 = FunctionSupportConfiger.this.getFilePath();
                if (access$100 != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(access$100.toString());
                    sb.append("/");
                    sb.append(this.a);
                    sb.append(".cfg");
                    File file = new File(sb.toString());
                    if (file.exists()) {
                        file.delete();
                    }
                    try {
                        fileWriter = new FileWriter(file, true);
                        fileWriter.write(jSONObject.toString());
                        fileWriter.close();
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                } else {
                    return;
                }
                throw th;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }

        public final void a() {
            FileInputStream fileInputStream;
            File access$100 = FunctionSupportConfiger.this.getFilePath();
            if (access$100 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(access$100.toString());
                sb.append("/");
                sb.append(this.a);
                sb.append(".cfg");
                try {
                    fileInputStream = new FileInputStream(sb.toString());
                    byte[] bArr = new byte[fileInputStream.available()];
                    fileInputStream.read(bArr);
                    JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
                    if (this.a == FunctionSupportConfiger.SWITCH_TAG) {
                        a(jSONObject, false);
                    } else {
                        b(jSONObject, false);
                    }
                    fileInputStream.close();
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                } catch (Throwable th) {
                    r0.addSuppressed(th);
                }
            } else {
                return;
            }
            throw th;
        }

        public final boolean a(String str) {
            if (str == null || str.length() <= 0 || this.d == null) {
                return false;
            }
            if (r1 <= 0) {
                return false;
            }
            for (lj ljVar : this.d) {
                if (!(ljVar == null || ljVar.j == 0)) {
                    if (str.equals(String.valueOf(ljVar.j))) {
                        return true;
                    }
                    if ("0000".equals(String.valueOf(ljVar.j).substring(2, 6))) {
                        try {
                            String substring = str.substring(0, 2);
                            StringBuilder sb = new StringBuilder();
                            sb.append(substring);
                            sb.append("0000");
                            if (sb.toString().equals(String.valueOf(ljVar.j))) {
                                return true;
                            }
                        } catch (Exception unused) {
                            continue;
                        }
                    } else {
                        String substring2 = String.valueOf(ljVar.j).substring(0, 4);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(substring2);
                        sb2.append("00");
                        if (sb2.toString().equals(str)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    public static FunctionSupportConfiger getInst() {
        if (inst == null) {
            inst = new FunctionSupportConfiger();
        }
        return inst;
    }

    private FunctionSupportConfiger() {
    }

    private a getFromTag(String str) {
        if (TAXI_TAG.equals(str)) {
            return this.mTaxiSupportConfiger;
        }
        if (ROUTE_BUS_TAG.equals(str)) {
            return this.mRtBusSupportConfiger;
        }
        if (SWITCH_TAG.equals(str)) {
            return this.mSwitchConfiger;
        }
        return null;
    }

    public void load(String str) {
        a fromTag = getFromTag(str);
        if (fromTag != null) {
            fromTag.a();
        }
    }

    public void load() {
        this.mTaxiSupportConfiger.a();
        this.mRtBusSupportConfiger.a();
        this.mSwitchConfiger.a();
    }

    public boolean decode(JSONObject jSONObject, String str) {
        a fromTag = getFromTag(str);
        if (fromTag == null) {
            return false;
        }
        return fromTag.b(jSONObject, true);
    }

    public boolean decodeSwitch(JSONObject jSONObject) {
        if (this.mSwitchConfiger == null) {
            return false;
        }
        return this.mSwitchConfiger.a(jSONObject, true);
    }

    public boolean isSupport(String str, String str2) {
        a fromTag = getFromTag(str2);
        if (fromTag == null) {
            return false;
        }
        return fromTag.a(str);
    }

    public String getVersion(String str) {
        a fromTag = getFromTag(str);
        return fromTag == null ? "" : fromTag.c;
    }

    public boolean isLocalLogActive() {
        if (this.mSwitchConfiger == null) {
            return false;
        }
        a aVar = this.mSwitchConfiger;
        if (aVar.f != null && !"1".equals(agd.e(aVar.f, LOCAL_LOG)) && !"".equals(agd.e(aVar.f, LOCAL_LOG))) {
            return false;
        }
        return true;
    }

    public boolean isLocalAppInfoActive() {
        lu luVar = lt.a().d;
        if (luVar.a != null) {
            return luVar.a.booleanValue();
        }
        return true;
    }

    public boolean isBusCollectActive() {
        ls lsVar = lt.a().c;
        if (lsVar.e != null) {
            return lsVar.e.booleanValue();
        }
        return false;
    }

    public boolean isSmartScenicActive() {
        lu luVar = lt.a().d;
        if (luVar.b != null) {
            return luVar.b.booleanValue();
        }
        return true;
    }

    public boolean isETAIncidentReportDialogActive() {
        lu luVar = lt.a().d;
        if (luVar.p != null) {
            return luVar.p.booleanValue();
        }
        return false;
    }

    public boolean isETAIncidentReportButtonActive() {
        lu luVar = lt.a().d;
        if (luVar.q != null) {
            return luVar.q.booleanValue();
        }
        return false;
    }

    public boolean isTrafficAlarm() {
        if (!NetworkReachability.b()) {
            return false;
        }
        lu luVar = lt.a().d;
        if (luVar.c != null) {
            return luVar.c.booleanValue();
        }
        return false;
    }

    public boolean isShareFunctionActive() {
        lu luVar = lt.a().d;
        if (luVar.m != null) {
            return luVar.m.booleanValue();
        }
        return true;
    }

    public int splashScreenSource() {
        ls lsVar = lt.a().c;
        String str = lsVar.d != null ? lsVar.d : null;
        if (str == null) {
            return 0;
        }
        int i = -1;
        try {
            if (!TextUtils.isEmpty(str)) {
                i = Integer.parseInt(str);
            }
        } catch (Exception unused) {
        }
        new MapSharePreference((String) SP_NAME_AfpSplashEvents).putIntValue(splash_show_source, i);
        return i;
    }

    public boolean isShareStateActive() {
        lu luVar = lt.a().d;
        if (luVar.l != null) {
            return luVar.l.booleanValue();
        }
        return false;
    }

    public boolean isSharePopupAllActive() {
        lu luVar = lt.a().d;
        if (luVar.o != null) {
            return luVar.o.booleanValue();
        }
        return false;
    }

    public boolean isSharePopupOver100kmAlive() {
        lu luVar = lt.a().d;
        if (luVar.n != null) {
            return luVar.n.booleanValue();
        }
        return false;
    }

    /* access modifiers changed from: private */
    public File getFilePath() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            File file = new File(FileUtil.getCacheDir(), "support");
            if (!file.exists()) {
                file.mkdir();
            }
            return file;
        }
        File file2 = new File(Environment.getExternalStorageDirectory(), "autonavi");
        if (!file2.exists()) {
            file2.mkdir();
        }
        File file3 = new File(file2, "support");
        if (file3.exists()) {
            return file3;
        }
        file3.mkdir();
        return file3;
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }
}
