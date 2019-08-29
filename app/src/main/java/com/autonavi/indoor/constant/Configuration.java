package com.autonavi.indoor.constant;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.location.common.a;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.indoor.util.L;
import com.autonavi.indoor.util.MapUtils;
import com.autonavi.indoor.util.NetworkHelper;
import com.autonavi.indoor.util.PackageHelper;
import com.autonavi.indoor.util.ProtocolHelper;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public final class Configuration {
    public final Context context;
    JSONObject jObject;
    private final String mAOSKey;
    public final int mDownloadPercentageBeforeLocating;
    public final DownloadSegment mDownloadSegment;
    public final String mKey;
    public final LocationMode mLocationMode;
    public LocationProvider mLocationProvider;
    public final int mNetworkType;
    public final PDRProvider mPDRProvider;
    public String mPackageName;
    public final int mReportInterval;
    public final String mSDKName;
    public final String mSDKVersion;
    public String mSHA1;
    public final SensorManager mSensorManager;
    public final String mServer;
    public final ServerType mServerType;
    String mSign;
    public final String mSimulateFile;
    public final String mSqlitePath;
    public final Resources resources;

    public static class Builder {
        public static final int DEFAULT_DOWNLOAD_PERCENTAGE = 80;
        public static final String DEFAULT_LBS_SERVER = "restapi.amap.com";
        public static final LocationProvider DEFAULT_PROVIDER = LocationProvider.WIFI;
        public static final int DEFAULT_REPORT_INTEVAL = 1000;
        public static final String DEFAULT_SQLITE_FILE;
        public static final String KEY_DEFAULT_BID = "default_bid";
        public static final String KEY_ONLINE_LOCATION_SERVER = "OnlineLocationServer";
        public static final String KEY_SIMULATEFILE = "SimulateFile";
        public final DownloadSegment DEFAULT_DOWNLOAD_SEGMENT = DownloadSegment.SMALL_SEGMENT;
        public final LocationMode DEFAULT_LOCATION_MODE = LocationMode.AUTO;
        /* access modifiers changed from: private */
        public Context context;
        private String mAOSChannel = "aos_channel";
        /* access modifiers changed from: private */
        public String mAOSKey = "";
        /* access modifiers changed from: private */
        public String mAOSParam = "unset";
        /* access modifiers changed from: private */
        public int mDownloadPercentageBeforeLocating = 80;
        /* access modifiers changed from: private */
        public DownloadSegment mDownloadSegment = this.DEFAULT_DOWNLOAD_SEGMENT;
        /* access modifiers changed from: private */
        public String mKey = "unset";
        /* access modifiers changed from: private */
        public LocationMode mLocationMode = this.DEFAULT_LOCATION_MODE;
        /* access modifiers changed from: private */
        public LocationProvider mLocationProvider = DEFAULT_PROVIDER;
        /* access modifiers changed from: private */
        public int mNetworkType = 15;
        PDRProvider mPDRProvider = PDRProvider.DEFAULT;
        /* access modifiers changed from: private */
        public int mReportInterval = 1000;
        /* access modifiers changed from: private */
        public String mSDKName = "IndoorLocationSDK";
        /* access modifiers changed from: private */
        public String mSDKVersion = "6.9";
        /* access modifiers changed from: private */
        public String mServer = DEFAULT_LBS_SERVER;
        /* access modifiers changed from: private */
        public ServerType mServerType = ServerType.SERVER_LBS;
        public String mSimulateFile;
        /* access modifiers changed from: private */
        public String mSqlitePath = DEFAULT_SQLITE_FILE;

        private void initEmptyFieldsWithDefaultValues() {
        }

        static {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getPath());
            sb.append("/autonavi/indoor/fp.db");
            DEFAULT_SQLITE_FILE = sb.toString();
        }

        @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.BLUETOOTH"})
        public Builder(Context context2) {
            setContext(context2);
        }

        @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.BLUETOOTH"})
        public Builder setContext(Context context2) {
            final Context applicationContext = context2.getApplicationContext();
            this.context = applicationContext;
            new Thread() {
                public void run() {
                    MapUtils.getImei(applicationContext);
                    MapUtils.getImsi(applicationContext);
                    a.f(applicationContext);
                    NetworkHelper.getNetworkType(applicationContext);
                }
            }.start();
            return this;
        }

        public Builder setReportInterval(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("interval must be a positive number");
            }
            this.mReportInterval = i;
            return this;
        }

        public Builder setServer(ServerType serverType, String str) {
            if (TextUtils.isEmpty(str)) {
                return this;
            }
            this.mServer = str;
            this.mServerType = serverType;
            return this;
        }

        public Builder setSqlitePath(String str) {
            this.mSqlitePath = str;
            return this;
        }

        public Builder setLocationProvider(LocationProvider locationProvider) {
            this.mLocationProvider = locationProvider;
            return this;
        }

        public Builder setDownloadPercentageBeforeLocating(int i) {
            this.mDownloadPercentageBeforeLocating = i;
            return this;
        }

        public Builder setLocationMode(LocationMode locationMode) {
            this.mLocationMode = locationMode;
            return this;
        }

        public Builder setNetworkType(int i) {
            this.mNetworkType = i;
            return this;
        }

        public Builder setAOSParam(String str, String str2) {
            this.mAOSParam = str;
            this.mAOSKey = str2;
            return this;
        }

        public Builder setLBSParam(String str) {
            this.mKey = str;
            return this;
        }

        public Builder setSDKName(String str) {
            this.mSDKName = str;
            return this;
        }

        public Builder setSDKVersion(String str) {
            this.mSDKVersion = str;
            return this;
        }

        public Builder setPDRProvider(PDRProvider pDRProvider) {
            this.mPDRProvider = pDRProvider;
            return this;
        }

        public int getNetworkType() {
            return this.mNetworkType;
        }

        public LocationProvider getLocationProvider() {
            return this.mLocationProvider;
        }

        public String getConfig(Properties properties, String str) {
            if (properties != null) {
                return properties.getProperty(str);
            }
            if (L.isLogging) {
                L.d((String) "properties: you need call init first");
            }
            return null;
        }

        public Configuration build() {
            initEmptyFieldsWithDefaultValues();
            return new Configuration(this);
        }
    }

    public enum DownloadSegment {
        SMALL_SEGMENT,
        LARGE_SEGMENT
    }

    public enum LocationMode {
        OFFLINE,
        AUTO
    }

    public enum LocationProvider {
        WIFI,
        BLE,
        FUSION_WIFI_BLE
    }

    public enum PDRProvider {
        DEFAULT,
        STEPANGLE
    }

    public enum ServerType {
        SERVER_LBS,
        SERVER_AOS,
        SERVER_CUSTOM
    }

    private Configuration(Builder builder) {
        this.jObject = null;
        this.mSign = null;
        this.mSHA1 = null;
        this.mPackageName = null;
        this.resources = builder.context.getResources();
        this.context = builder.context;
        this.mServer = builder.mServer;
        this.mServerType = builder.mServerType;
        this.mSimulateFile = builder.mSimulateFile;
        this.mLocationProvider = builder.mLocationProvider;
        this.mLocationMode = builder.mLocationMode;
        this.mSensorManager = (SensorManager) builder.context.getSystemService("sensor");
        this.mReportInterval = builder.mReportInterval;
        this.mSqlitePath = builder.mSqlitePath;
        this.mDownloadPercentageBeforeLocating = builder.mDownloadPercentageBeforeLocating;
        this.mDownloadSegment = builder.mDownloadSegment;
        this.mNetworkType = builder.mNetworkType;
        if (!TextUtils.isEmpty(builder.mAOSParam) && !builder.mAOSParam.equals("unset")) {
            setAOSParam(builder.mAOSParam);
        }
        this.mAOSKey = builder.mAOSKey;
        this.mKey = builder.mKey;
        this.mSDKName = builder.mSDKName;
        this.mSDKVersion = builder.mSDKVersion;
        this.mPDRProvider = builder.mPDRProvider;
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.BLUETOOTH"})
    public static Configuration createDefault(Context context2) {
        return new Builder(context2).build();
    }

    public final void setAOSParam(String str) {
        try {
            setAOSParam(new JSONObject(str));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void setAOSParam(JSONObject jSONObject) {
        this.jObject = jSONObject;
        String str = "amap7";
        try {
            if (jSONObject.has("channel") && !TextUtils.isEmpty(jSONObject.getString("channel"))) {
                str = jSONObject.getString("channel");
            }
            String str2 = "unkown";
            if (jSONObject.has(LocationParams.PARA_COMMON_DIU) && !TextUtils.isEmpty(jSONObject.getString(LocationParams.PARA_COMMON_DIU))) {
                str2 = jSONObject.getString(LocationParams.PARA_COMMON_DIU);
            }
            String str3 = "unkown";
            if (jSONObject.has(LocationParams.PARA_COMMON_DIV) && !TextUtils.isEmpty(jSONObject.getString(LocationParams.PARA_COMMON_DIV))) {
                str3 = jSONObject.getString(LocationParams.PARA_COMMON_DIV);
            }
            this.mSign = genAOSSign(str, str2, str3);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final String getServerAdd() {
        String str;
        if (this.mServer.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
            str = this.mServer;
        } else {
            StringBuilder sb = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS);
            sb.append(this.mServer);
            str = sb.toString();
        }
        if (this.mServerType == ServerType.SERVER_AOS) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("/ws/transfer/auth/indoorlocation");
            return sb2.toString();
        } else if (this.mServerType == ServerType.SERVER_LBS) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("/v3/indoor/indoorlocation");
            return sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append("/indoorlocation");
            return sb4.toString();
        }
    }

    public final String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(getServerAdd());
        sb.append("?");
        String sb2 = sb.toString();
        if (this.mServerType == ServerType.SERVER_AOS) {
            String[] strArr = {LocationParams.PARA_COMMON_DIP, LocationParams.PARA_COMMON_DIV, LocationParams.PARA_COMMON_DIBV, LocationParams.PARA_COMMON_DIC, LocationParams.PARA_COMMON_DIU, LocationParams.PARA_COMMON_DIU2, LocationParams.PARA_COMMON_DIU3, "tid", LocationParams.PARA_COMMON_ADIU, "stepid", "appstartid", LocationParams.PARA_COMMON_CIFA, "channel"};
            StringBuilder sb3 = new StringBuilder();
            try {
                sb3.append("ver=");
                sb3.append(this.mSDKVersion);
                if (this.jObject != null) {
                    for (int i = 0; i < 13; i++) {
                        String str = strArr[i];
                        if (!this.jObject.has(str) || TextUtils.isEmpty(this.jObject.getString(str))) {
                            sb3.append("&");
                            sb3.append(str);
                            sb3.append("=unkown");
                        } else {
                            sb3.append("&");
                            sb3.append(str);
                            sb3.append("=");
                            sb3.append(this.jObject.getString(str));
                        }
                    }
                    String session = ProtocolHelper.getSession();
                    if (TextUtils.isEmpty(session)) {
                        sb3.append("&session=unkown");
                    } else {
                        sb3.append("&session=");
                        sb3.append(session);
                    }
                    String spm = ProtocolHelper.getSpm();
                    if (TextUtils.isEmpty(spm)) {
                        sb3.append("&spm=unkown");
                    } else {
                        sb3.append("&spm=");
                        sb3.append(spm);
                    }
                    String str2 = "0";
                    if (NetworkHelper.mNetworkType == 2) {
                        str2 = "1";
                    } else if (NetworkHelper.mNetworkType == 4) {
                        str2 = "2";
                    } else if (NetworkHelper.mNetworkType == 8) {
                        str2 = "3";
                    } else if (NetworkHelper.mNetworkType == 1) {
                        str2 = "4";
                    } else if (L.isLogging) {
                        StringBuilder sb4 = new StringBuilder("unknown network:");
                        sb4.append(NetworkHelper.mNetworkType);
                        L.d(sb4.toString());
                    }
                    sb3.append("&output=bin&client_network_class=");
                    sb3.append(str2);
                    sb3.append("&sign=");
                    sb3.append(this.mSign);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb2);
            sb5.append(sb3);
            String sb6 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(sb6);
            sb7.append("&csid=");
            sb7.append(UUID.randomUUID().toString());
            return MapUtils.amapEncodeUrl(sb7.toString());
        } else if (this.mServerType != ServerType.SERVER_LBS) {
            return sb2;
        } else {
            String ts = getTs();
            if (TextUtils.isEmpty(this.mSHA1)) {
                this.mSHA1 = PackageHelper.getSHA1(this.context);
            }
            if (TextUtils.isEmpty(this.mPackageName)) {
                this.mPackageName = this.context.getPackageName();
            }
            String str3 = null;
            try {
                str3 = scode(this.mSHA1, this.mPackageName, ts, this.mKey);
            } catch (Throwable th) {
                th.printStackTrace();
            }
            StringBuilder sb8 = new StringBuilder();
            sb8.append(sb2);
            sb8.append("key=");
            sb8.append(this.mKey);
            sb8.append("&ts=");
            sb8.append(ts);
            sb8.append("&scode=");
            sb8.append(str3);
            return sb8.toString();
        }
    }

    static String toOldQueryString(Map<?, ?> map) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (Entry next : map.entrySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(next.getKey());
                sb.append("=");
                stringBuffer.append(sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(next.getValue());
                sb2.append("&");
                stringBuffer.append(sb2.toString());
            }
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
        } catch (Exception e) {
            if (L.isLogging) {
                L.d((Throwable) e);
            }
        }
        return stringBuffer.toString();
    }

    static String toEncodeQueryString(Map<?, ?> map) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (Entry next : map.entrySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(next.getKey());
                sb.append("=");
                stringBuffer.append(sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode((String) next.getValue(), "UTF-8"));
                sb2.append("&");
                stringBuffer.append(sb2.toString());
            }
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
        } catch (Exception e) {
            if (L.isLogging) {
                L.d((Throwable) e);
            }
        }
        return stringBuffer.toString();
    }

    static String getMd5StandardString(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            if (L.isLogging) {
                L.d((Throwable) e);
            }
            messageDigest = null;
        }
        try {
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            if (L.isLogging) {
                L.d((Throwable) e2);
            }
        }
        return standardBytes2HexString(messageDigest.digest());
    }

    static String standardBytes2HexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = "0".concat(String.valueOf(hexString));
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    private String getTs() {
        String valueOf = String.valueOf(System.currentTimeMillis());
        int length = valueOf.length();
        StringBuilder sb = new StringBuilder();
        sb.append(valueOf.substring(0, length - 2));
        sb.append("1");
        sb.append(valueOf.substring(length - 1));
        return sb.toString();
    }

    private String genAOSSign(String str, String str2, String str3) {
        String str4 = this.mAOSKey;
        if (TextUtils.isEmpty(str4)) {
            if ("amap7".equals(str)) {
                str4 = "1071a2a4e3gte2Uc32cY3a98Tf33H1c4Gc23f";
            } else if ("amap7a".equals(str)) {
                str4 = "xnaEwInMxaMQ2m0cw6Y1bDm7ns0YVxYS9v7JlC8I";
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(str4);
        return md5(sb.toString());
    }

    public static String scode(String str, String str2, String str3, String str4) throws Throwable {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        sb.append(":");
        sb.append(str3.substring(0, str3.length() - 3));
        sb.append(":key=");
        sb.append(str4);
        MessageDigest instance = MessageDigest.getInstance("MD5");
        if (L.isLogging) {
            L.d(sb.toString());
        }
        instance.update(sb.toString().getBytes("UTF-8"));
        byte[] digest = instance.digest();
        StringBuilder sb2 = new StringBuilder();
        for (byte b : digest) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = "0".concat(String.valueOf(hexString));
            }
            sb2.append(hexString);
        }
        return sb2.toString();
    }

    private static final String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("md5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte valueOf : digest) {
                sb.append(String.format("%02X", new Object[]{Byte.valueOf(valueOf)}));
            }
            return sb.toString().toLowerCase();
        } catch (Exception unused) {
            return "";
        }
    }

    public final boolean isUsingWifi() {
        return this.mLocationProvider == LocationProvider.WIFI || this.mLocationProvider == LocationProvider.FUSION_WIFI_BLE;
    }

    public final boolean isUsingBLE() {
        return this.mLocationProvider == LocationProvider.BLE || this.mLocationProvider == LocationProvider.FUSION_WIFI_BLE;
    }
}
