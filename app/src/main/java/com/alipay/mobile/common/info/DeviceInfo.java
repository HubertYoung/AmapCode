package com.alipay.mobile.common.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationManagerProxy;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.ta.utdid2.device.UTDevice;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeviceInfo {
    public static final String ANY_ZERO_STR = "[0]+";
    public static final int CLIENT_KEY_MAX_LENGTH = 10;
    public static final String CMCC = "cmcc";
    public static final String CTCC = "ctcc";
    public static final String CUCC = "cucc";
    public static final int HARDWARD_INVALID_LEN = 5;
    public static final int IMEI_LEN = 15;
    public static final String NULL = "null";
    public static final String UNKNOWN = "unknown";
    private static DeviceInfo a;
    private static boolean u = false;
    /* access modifiers changed from: private */
    public final Context b;
    public String[] baseString = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", SuperId.BIT_1_RQBXY, SuperId.BIT_1_NEARBY_SEARCH, "d", "e", "f", SuperId.BIT_1_NAVI, "h", "i", "j", SuperId.BIT_1_REALTIMEBUS_BUSSTATION, "l", "m", SuperId.BIT_1_MAIN_BUSSTATION, "o", SuperId.BIT_1_MAIN_VOICE_ASSISTANT, "q", UploadQueueMgr.MSGTYPE_REALTIME, "s", LogItem.MM_C15_K4_TIME, H5Param.URL, "v", "w", DictionaryKeys.CTRLXY_X, DictionaryKeys.CTRLXY_Y, "z", "A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", LogHelper.DEFAULT_LEVEL, "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "O", "P", "Q", "R", "S", "T", "U", SecureSignatureDefine.SG_KEY_SIGN_VERSION, "W", "X", "Y", "Z"};
    private int c;
    private int d;
    private int e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private boolean o;
    /* access modifiers changed from: private */
    public String p;
    private String q;
    /* access modifiers changed from: private */
    public final AtomicBoolean r = new AtomicBoolean(false);
    private String s = null;
    /* access modifiers changed from: private */
    public final Object t = new Object();
    @SuppressLint({"SimpleDateFormat"})
    private final SimpleDateFormat v = new SimpleDateFormat("yyMMddHHmmssSSS");
    private WifiManager w;

    private DeviceInfo(Context context) {
        this.b = context;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static synchronized DeviceInfo getInstance() {
        DeviceInfo deviceInfo;
        synchronized (DeviceInfo.class) {
            if (a == null) {
                throw new IllegalStateException("DeviceManager must be create by call createInstance(Context context)");
            }
            deviceInfo = a;
        }
        return deviceInfo;
    }

    public static synchronized DeviceInfo createInstance(Context context) {
        DeviceInfo deviceInfo;
        synchronized (DeviceInfo.class) {
            try {
                if (a == null) {
                    DeviceInfo deviceInfo2 = new DeviceInfo(context);
                    a = deviceInfo2;
                    deviceInfo2.b();
                }
                deviceInfo = a;
            }
        }
        return deviceInfo;
    }

    public static synchronized void getSecurityInstance() {
        synchronized (DeviceInfo.class) {
            if (!u && a != null) {
                a.a();
            }
        }
    }

    public static synchronized void forceRefreashInstance() {
        synchronized (DeviceInfo.class) {
            if (a != null) {
                a.a();
            }
        }
    }

    private void a() {
        this.f = g();
        this.g = h();
        this.h = i();
        u = true;
    }

    private void b() {
        Resources resources = this.b.getResources();
        if (resources == null) {
            try {
                resources = this.b.getPackageManager().getResourcesForApplication(this.b.getPackageName());
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().warn((String) "DeviceInfo", e2);
            }
        }
        if (resources == null) {
            try {
                resources = Resources.getSystem();
            } catch (Throwable e3) {
                LoggerFactory.getTraceLogger().warn((String) "DeviceInfo", e3);
            }
        }
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        this.c = displayMetrics.widthPixels;
        this.d = displayMetrics.heightPixels;
        this.e = displayMetrics.densityDpi;
        this.f = g();
        this.g = h();
        this.h = i();
        this.q = d();
        c();
        this.k = Build.BRAND;
        this.m = Build.MODEL;
        this.n = VERSION.RELEASE;
        this.o = f();
        try {
            this.w = (WifiManager) this.b.getSystemService("wifi");
        } catch (Throwable e4) {
            LoggerFactory.getTraceLogger().error("DeviceInfo", " mContext.getSystemService(Context.WIFI_SERVICE); Exception.", e4);
        }
    }

    public void refreashUtDid() {
        new Thread(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                try {
                    LoggerFactory.getTraceLogger().info("DeviceInfo", "start refreashUtDid");
                    DeviceInfo.this.p = UTDevice.getUtdidForUpdate(DeviceInfo.this.b);
                    LoggerFactory.getTraceLogger().info("DeviceInfo", "end refreashUtDid");
                } catch (Throwable e) {
                    LoggerFactory.getTraceLogger().error((String) "DeviceInfo", e);
                }
            }
        }).start();
    }

    private void c() {
        new Thread(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                DeviceInfo.this.r.set(true);
                DeviceInfo.this.p = com.ut.device.UTDevice.getUtdid(DeviceInfo.this.b);
                DeviceInfo.this.r.set(false);
                synchronized (DeviceInfo.this.t) {
                    DeviceInfo.this.t.notifyAll();
                }
            }
        }).start();
    }

    private String d() {
        SharedPreferences settings = this.b.getSharedPreferences(this.b.getPackageName() + ".config", 0);
        String clientKey = settings.getString("clientKey", "");
        if (!"".equals(clientKey)) {
            return clientKey;
        }
        String clientKey2 = e();
        settings.edit().putString("clientKey", clientKey2).apply();
        return clientKey2;
    }

    public String refleshClientKey() {
        SharedPreferences settings = this.b.getSharedPreferences(this.b.getPackageName() + ".config", 0);
        String clientKey = e();
        settings.edit().putString("clientKey", clientKey).apply();
        this.q = clientKey;
        return clientKey;
    }

    private String e() {
        Random random = new Random(System.currentTimeMillis());
        int length = this.baseString.length;
        String randomString = "";
        for (int i2 = 0; i2 < 10; i2++) {
            randomString = randomString + this.baseString[random.nextInt(length)];
        }
        return randomString;
    }

    private static boolean f() {
        Object value = null;
        try {
            value = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"ro.secure"});
        } catch (Exception e2) {
            Log.e("DeviceInfo", "", e2);
        }
        if (value != null && "1".equals(value)) {
            return false;
        }
        if (value == null || !"0".equals(value)) {
            return false;
        }
        return true;
    }

    public String getmDid() {
        if (this.r.get()) {
            synchronized (this.t) {
                try {
                    this.t.wait();
                } catch (InterruptedException e2) {
                    Log.e("DeviceInfo", "", e2);
                }
            }
        }
        LoggerFactory.getTraceLogger().info("DeviceInfo", "getmDid=" + this.p);
        if (this.p == null || "".equals(this.p)) {
            return this.f + MergeUtil.SEPARATOR_KV + this.q;
        }
        return this.p;
    }

    public String getmDidAsync() {
        LoggerFactory.getTraceLogger().info("DeviceInfo", "getmDidAsync=" + this.p);
        return (this.p == null || "".equals(this.p)) ? this.f + MergeUtil.SEPARATOR_KV + this.q : this.p;
    }

    public void setmDid(String mDid) {
        this.p = mDid;
    }

    public int getmScreenWidth() {
        return this.c;
    }

    public int getmScreenHeight() {
        return this.d;
    }

    public String getmMobileBrand() {
        return this.k;
    }

    public String getmMobileModel() {
        return this.m;
    }

    public String getmSystemVersion() {
        return this.n;
    }

    public boolean ismRooted() {
        return this.o;
    }

    public int getScreenWidth() {
        return this.c;
    }

    public int getScreenHeight() {
        return this.d;
    }

    public int getDencity() {
        return this.e;
    }

    public String getOsVersion() {
        return VERSION.RELEASE;
    }

    public String getUserAgent() {
        return Build.MANUFACTURER + Build.MODEL;
    }

    public String getImei() {
        return this.g;
    }

    public String getImsi() {
        return this.h;
    }

    @Deprecated
    public String getExternalStoragePath(String dir) {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }
        String path = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + this.b.getPackageName();
        File file = new File(path);
        if (!file.exists() && !file.mkdir()) {
            Log.e("DeviceInfo", "fail to creat " + dir + " dir:" + path);
            return path;
        } else if (!file.isDirectory()) {
            Log.e("DeviceInfo", dir + " dir exist,but not directory:" + path);
            return null;
        } else {
            String path2 = path + File.separatorChar + dir;
            File file2 = new File(path2);
            if (!file2.exists() && !file2.mkdir()) {
                Log.e("DeviceInfo", "fail to creat " + dir + " dir:" + path2);
                return path2;
            } else if (file2.isDirectory()) {
                return path2;
            } else {
                Log.e("DeviceInfo", dir + " dir exist,but not directory:" + path2);
                return null;
            }
        }
    }

    public String getExternalCacheDir() {
        if (this.b != null && TextUtils.isEmpty(this.s)) {
            try {
                File cacheFile = this.b.getExternalCacheDir();
                if (cacheFile != null) {
                    this.s = cacheFile.getAbsolutePath();
                }
            } catch (Throwable th) {
            }
        }
        return this.s;
    }

    private static boolean a(String imsiOrimei) {
        if (imsiOrimei == null || imsiOrimei.trim().length() == 0) {
            return false;
        }
        String trimS = imsiOrimei.trim();
        if (trimS.equalsIgnoreCase("unknown") || trimS.equalsIgnoreCase("null") || trimS.matches(ANY_ZERO_STR) || trimS.length() <= 5) {
            return false;
        }
        return true;
    }

    private static boolean b(String s2) {
        return s2 == null || s2.trim().length() == 0;
    }

    private String g() {
        String newClientId;
        SharedPreferences settings = this.b.getSharedPreferences(this.b.getPackageName() + ".config", 0);
        String imei = "";
        String imsi = "";
        int hasPermission = 0;
        if (VERSION.SDK_INT >= 21) {
            try {
                hasPermission = ContextCompat.checkSelfPermission(this.b, "android.permission.READ_PHONE_STATE");
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error((String) "DeviceInfo", e2);
                hasPermission = -1;
            }
        }
        if (hasPermission == -1) {
            Log.e("DeviceInfo", "PERMISSION_DENIED");
        } else {
            try {
                TelephonyManager telMgr = (TelephonyManager) this.b.getSystemService("phone");
                imei = telMgr.getDeviceId();
                imsi = telMgr.getSubscriberId();
            } catch (Exception e3) {
                Log.e("DeviceInfo", "", e3);
            }
            Log.d("DeviceInfo", "origin imei:" + imei);
        }
        this.i = imei;
        Log.d("DeviceInfo", "origin imsi:" + imsi);
        this.j = imsi;
        String savedClientId = settings.getString("clientId", "");
        Log.d("DeviceInfo", "saved clientid:" + savedClientId);
        if (c(savedClientId)) {
            Log.d("DeviceInfo", "client id is valid:" + savedClientId);
            String savedImsi = savedClientId.substring(0, 15);
            if (a(imsi)) {
                String imsiT = e(imsi);
                if (imsiT.length() > 15) {
                    imsiT = imsiT.substring(0, 15);
                }
                if (!savedImsi.startsWith(imsiT)) {
                    savedImsi = imsi;
                }
            }
            String savedImei = savedClientId.substring(savedClientId.length() - 15, savedClientId.length());
            if (a(imei)) {
                String imeiT = e(imei);
                if (imeiT.length() > 15) {
                    imeiT = imeiT.substring(0, 15);
                }
                if (!savedImei.startsWith(imeiT)) {
                    savedImei = imei;
                }
            }
            Log.d("DeviceInfo", "client id is valid:" + savedImsi + MergeUtil.SEPARATOR_KV + savedImei);
            newClientId = a(savedImsi, savedImei);
            Log.d("DeviceInfo", "normarlize, imsi:" + imsi + ",imei:" + imei + ",newClientId:" + newClientId);
            if (!TextUtils.equals(newClientId, savedClientId)) {
                Editor editor = settings.edit();
                editor.putString("clientId", newClientId);
                editor.apply();
            }
        } else {
            Log.d("DeviceInfo", "client is is not valid, imei:" + imei + ",imsi:" + imsi);
            if (!a(imei)) {
                imei = getTimeStamp();
            }
            if (!a(imsi)) {
                imsi = getTimeStamp();
            }
            newClientId = a(imsi, imei);
            Log.d("DeviceInfo", "normalize, imei:" + imei + ",imsi:" + imsi + ",newClientId:" + newClientId);
            if (!TextUtils.equals(newClientId, savedClientId)) {
                Editor editor2 = settings.edit();
                editor2.putString("clientId", newClientId);
                editor2.apply();
            }
        }
        return newClientId;
    }

    private static boolean c(String clientID) {
        if (b(clientID)) {
            return false;
        }
        return clientID.matches("[[a-z][A-Z][0-9]]{15}\\|[[a-z][A-Z][0-9]]{15}");
    }

    private String d(String imeiOrImsi) {
        if (!a(imeiOrImsi)) {
            imeiOrImsi = getTimeStamp();
        }
        return e((imeiOrImsi + "123456789012345").substring(0, 15));
    }

    private static String e(String imei) {
        if (b(imei)) {
            return imei;
        }
        byte[] byteClientId = imei.getBytes();
        for (int i2 = 0; i2 < byteClientId.length; i2++) {
            if (!a(byteClientId[i2])) {
                byteClientId[i2] = 48;
            }
        }
        return new String(byteClientId);
    }

    private static boolean a(byte c2) {
        return (c2 >= 48 && c2 <= 57) || (c2 >= 97 && c2 <= 122) || (c2 >= 65 && c2 <= 90);
    }

    private String a(String imsi, String imei) {
        return d(imsi) + MergeUtil.SEPARATOR_KV + d(imei);
    }

    public String getTimeStamp() {
        return this.v.format(Long.valueOf(System.currentTimeMillis()));
    }

    private String h() {
        if (c(this.f)) {
            return this.f.substring(this.f.length() - 15);
        }
        return "";
    }

    private String i() {
        if (c(this.f)) {
            return this.f.substring(0, (this.f.length() - 15) - 1);
        }
        return "";
    }

    public String getDefImei() {
        return this.i;
    }

    public void setDefImei(String defImei) {
        this.i = defImei;
    }

    public String getDefImsi() {
        return this.j;
    }

    public void setDefImsi(String defImsi) {
        this.j = defImsi;
    }

    public String getClientId() {
        return this.f;
    }

    public String getAccessPoint() {
        String apn = "wifi";
        try {
            NetworkInfo ni = ((ConnectivityManager) this.b.getSystemService("connectivity")).getActiveNetworkInfo();
            String extra = ni != null ? ni.getExtraInfo() : "unkown";
            if (extra == null || extra.indexOf(Constants.ANIMATOR_NONE) != -1) {
                apn = ni.getTypeName();
            } else {
                apn = extra;
            }
        } catch (Exception e2) {
            Log.e("DeviceInfo", "", e2);
        }
        return apn.replace("internet", "wifi").replace("\"", "");
    }

    public String getCellInfo() {
        try {
            CellLocation temp = ((TelephonyManager) this.b.getSystemService("phone")).getCellLocation();
            if (temp == null) {
                return "-1;-1";
            }
            StringBuilder sbcellInfo = new StringBuilder();
            if (temp instanceof GsmCellLocation) {
                GsmCellLocation gsmcl = (GsmCellLocation) temp;
                int cellid = gsmcl.getCid();
                sbcellInfo.append(gsmcl.getLac());
                sbcellInfo.append(";");
                sbcellInfo.append(cellid);
                return sbcellInfo.toString();
            } else if (!(temp instanceof CdmaCellLocation)) {
                return "-1;-1";
            } else {
                CdmaCellLocation cdmacl = (CdmaCellLocation) temp;
                int cellid2 = cdmacl.getBaseStationLatitude();
                sbcellInfo.append(cdmacl.getBaseStationLongitude());
                sbcellInfo.append(";");
                sbcellInfo.append(cellid2);
                return sbcellInfo.toString();
            }
        } catch (Exception e2) {
            Log.e("DeviceInfo", "", e2);
            return "-1;-1";
        }
    }

    public String getmClientKey() {
        return this.q;
    }

    public String getKey() {
        return new SimpleDateFormat("MMddHHmmss", Locale.getDefault()).format(new Date());
    }

    public String getOperator() {
        if (this.l == null) {
            String imsi = null;
            try {
                imsi = ((TelephonyManager) this.b.getSystemService("phone")).getSubscriberId();
            } catch (Exception e2) {
                Log.e("DeviceInfo", "", e2);
            }
            if (imsi == null) {
                return "unknown";
            }
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
                this.l = CMCC;
            } else if (imsi.startsWith("46001")) {
                this.l = CUCC;
            } else if (imsi.startsWith("46003")) {
                this.l = CTCC;
            } else {
                this.l = "unknown";
            }
        }
        return this.l;
    }

    public boolean isInstalled(String packageName) {
        try {
            if (this.b.getPackageManager().getPackageInfo(packageName, 0) != null) {
                return true;
            }
            return false;
        } catch (NameNotFoundException e2) {
            Log.e("DeviceInfo", "", e2);
            return false;
        }
    }

    public String getMacAddress() {
        if (this.w == null) {
            return "";
        }
        return this.w.getConnectionInfo().getMacAddress();
    }

    public String getSSID() {
        if (this.w == null) {
            return "";
        }
        return this.w.getConnectionInfo().getSSID();
    }

    public String getLatitude() {
        LBSLocation location = LBSLocationManagerProxy.getInstance().getLastKnownLocation(this.b);
        if (location != null) {
            return String.valueOf(location.getLatitude());
        }
        return null;
    }

    public String getLongitude() {
        LBSLocation location = LBSLocationManagerProxy.getInstance().getLastKnownLocation(this.b);
        if (location != null) {
            return String.valueOf(location.getLongitude());
        }
        return null;
    }

    public void installApk(String path) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        this.b.startActivity(intent);
    }
}
