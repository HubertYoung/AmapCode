package com.autonavi.jni.ae.guide;

import android.content.Context;
import com.autonavi.jni.ae.guide.callback.OnVoiceConfigVersionCallback;
import com.autonavi.jni.ae.guide.model.AsyncInfo;
import com.autonavi.jni.ae.guide.model.GNaviPath;
import com.autonavi.jni.ae.guide.model.GuideConfig;
import com.autonavi.jni.ae.guide.model.GuideGPSInfo;
import com.autonavi.jni.ae.guide.model.GuideParam;
import com.autonavi.jni.ae.guide.model.NaviQuickPayInfo;
import com.autonavi.jni.ae.guide.observer.GCruiseObserver;
import com.autonavi.jni.ae.guide.observer.GSoundPlayObserver;
import com.autonavi.jni.ae.guide.observer.GStatusObserver;
import com.autonavi.jni.ae.route.observer.HttpInterface;
import java.util.HashSet;
import java.util.Iterator;

public class GuideService {
    public static final int DATA_ADD_OP = 2;
    public static final int DATA_DELETE_OP = 5;
    public static final int DATA_FINISH_OP = 4;
    public static final int DATA_UPDAE_OP = 3;
    private static final int FILE_TYPE_CHANGEPLAY = 3;
    private static final int FILE_TYPE_CITY = 1;
    private static final int FILE_TYPE_CUSTOM_VOICE_RIDE = 6;
    private static final int FILE_TYPE_CUSTOM_VOICE_WALK = 7;
    private static final int FILE_TYPE_GUIDESAFE = 4;
    private static final int FILE_TYPE_LUA = 8;
    private static final int FILE_TYPE_NAVISOUND = 2;
    private static final int FILE_TYPE_NAVISOUND_TRUCK = 5;
    public static final int NET_ERROR_CANCEL = 2;
    public static final int NET_ERROR_NO_NETWORK_CONNECTION = 3;
    public static final int NET_ERROR_OTHER = -1;
    public static final int NET_ERROR_TIMEOUT = 1;
    public static final int OFFLINE_DATAMANAGER = 1;
    public static final int VERSION_GET_OP = 1;
    private Context mContext;
    private GCruiseObserver mCruiseObserver;
    private HttpInterface mHttpProcess;
    private OnVoiceConfigVersionCallback mOnVoiceConfigVersionCallback;
    private long mPtr;
    private GSoundPlayObserver mSoundPlayObserver;
    private HashSet<GStatusObserver> mStatusListeners;

    public static native String getEngineVersion();

    public static native String getTravelSdkVersion();

    private final native void init(GuideConfig guideConfig);

    public native int control(int i, String str);

    public final native void destroy();

    public native GuideGPSInfo[] getRecentGPS(int i, int i2, int i3);

    public native void ignoreTmcSugguestNaviPath();

    public native void notifyQuickPayInfo(NaviQuickPayInfo naviQuickPayInfo);

    public native int obtainAsyncInfo(AsyncInfo asyncInfo);

    public native int obtainVoiceConfigVersion();

    public native int pauseNavi();

    public native int playNaviManual();

    public native int playTRManual(int i);

    public native void processHttpData(int i, int i2, byte[] bArr);

    public native void processHttpError(int i, int i2);

    public native int resumeNavi();

    public native void selectMainPathID(long j);

    public native void setNaviPath(GNaviPath gNaviPath, int i);

    public native int setParam(GuideParam guideParam);

    public native void setPressure(double d);

    public native int startNavi(int i);

    public native int stopNavi();

    public GuideService(GuideConfig guideConfig, Context context) {
        this.mContext = context;
        init(guideConfig);
    }

    public void registerHttpProcesser(HttpInterface httpInterface) {
        this.mHttpProcess = httpInterface;
    }

    public void setOnVoiceConfigVersionCallback(OnVoiceConfigVersionCallback onVoiceConfigVersionCallback) {
        this.mOnVoiceConfigVersionCallback = onVoiceConfigVersionCallback;
    }

    public void setSoundPlayObserver(GSoundPlayObserver gSoundPlayObserver) {
        this.mSoundPlayObserver = gSoundPlayObserver;
    }

    public void setElecEyeObserver(GCruiseObserver gCruiseObserver) {
        this.mCruiseObserver = gCruiseObserver;
    }

    public void addStatusObserver(GStatusObserver gStatusObserver) {
        if (this.mStatusListeners == null) {
            this.mStatusListeners = new HashSet<>();
        }
        if (!this.mStatusListeners.contains(gStatusObserver)) {
            this.mStatusListeners.add(gStatusObserver);
        }
    }

    public void removeStatusObserver(GStatusObserver gStatusObserver) {
        if (this.mStatusListeners != null && this.mStatusListeners.contains(gStatusObserver)) {
            this.mStatusListeners.remove(gStatusObserver);
        }
    }

    private void notifyStatusChanged(int i) {
        if (this.mStatusListeners != null) {
            Iterator<GStatusObserver> it = this.mStatusListeners.iterator();
            while (it.hasNext()) {
                it.next().onTbtStatusChanged(i, 0);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x008b A[SYNTHETIC, Splitter:B:63:0x008b] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0095 A[SYNTHETIC, Splitter:B:68:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00a1 A[SYNTHETIC, Splitter:B:75:0x00a1] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00ab A[SYNTHETIC, Splitter:B:80:0x00ab] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x00b7 A[SYNTHETIC, Splitter:B:87:0x00b7] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x00c1 A[SYNTHETIC, Splitter:B:92:0x00c1] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:60:0x0086=Splitter:B:60:0x0086, B:72:0x009c=Splitter:B:72:0x009c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] readAssetsFile(int r6, int r7) {
        /*
            r5 = this;
            r0 = 1
            r1 = 0
            if (r6 != r0) goto L_0x0007
            java.lang.String r6 = "navi/cityinfo.dat"
            goto L_0x0036
        L_0x0007:
            r2 = 2
            r3 = 3
            if (r6 != r2) goto L_0x0018
            if (r7 != r0) goto L_0x0010
            java.lang.String r6 = "navi/odd_config.bin"
            goto L_0x0036
        L_0x0010:
            if (r7 != r3) goto L_0x0015
            java.lang.String r6 = "navi/default_config_eng.bin"
            goto L_0x0036
        L_0x0015:
            java.lang.String r6 = "navi/default_config.bin"
            goto L_0x0036
        L_0x0018:
            if (r6 != r3) goto L_0x001d
            java.lang.String r6 = "navi/changeplay.bin"
            goto L_0x0036
        L_0x001d:
            r2 = 4
            if (r6 != r2) goto L_0x0023
            java.lang.String r6 = "navi/GSafeConfig.dat"
            goto L_0x0036
        L_0x0023:
            r2 = 5
            if (r6 != r2) goto L_0x002e
            if (r7 != r0) goto L_0x002b
            java.lang.String r6 = "navi/odd_config_truck.bin"
            goto L_0x0036
        L_0x002b:
            java.lang.String r6 = "navi/default_config_truck.bin"
            goto L_0x0036
        L_0x002e:
            r7 = 8
            if (r6 != r7) goto L_0x0035
            java.lang.String r6 = "navi/guidelua.bin"
            goto L_0x0036
        L_0x0035:
            r6 = r1
        L_0x0036:
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 == 0) goto L_0x003d
            return r1
        L_0x003d:
            android.content.Context r7 = r5.mContext     // Catch:{ Exception -> 0x0099, OutOfMemoryError -> 0x0083, all -> 0x007f }
            android.content.res.AssetManager r7 = r7.getAssets()     // Catch:{ Exception -> 0x0099, OutOfMemoryError -> 0x0083, all -> 0x007f }
            java.io.InputStream r6 = r7.open(r6)     // Catch:{ Exception -> 0x0099, OutOfMemoryError -> 0x0083, all -> 0x007f }
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x007c, OutOfMemoryError -> 0x0079, all -> 0x0076 }
            r7.<init>()     // Catch:{ Exception -> 0x007c, OutOfMemoryError -> 0x0079, all -> 0x0076 }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r0]     // Catch:{ Exception -> 0x0074, OutOfMemoryError -> 0x0072 }
        L_0x0050:
            r3 = 0
            int r4 = r6.read(r2, r3, r0)     // Catch:{ Exception -> 0x0074, OutOfMemoryError -> 0x0072 }
            if (r4 <= 0) goto L_0x005b
            r7.write(r2, r3, r4)     // Catch:{ Exception -> 0x0074, OutOfMemoryError -> 0x0072 }
            goto L_0x0050
        L_0x005b:
            byte[] r0 = r7.toByteArray()     // Catch:{ Exception -> 0x0074, OutOfMemoryError -> 0x0072 }
            if (r6 == 0) goto L_0x0069
            r6.close()     // Catch:{ IOException -> 0x0065 }
            goto L_0x0069
        L_0x0065:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0069:
            r7.close()     // Catch:{ IOException -> 0x006d }
            goto L_0x0071
        L_0x006d:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0071:
            return r0
        L_0x0072:
            r0 = move-exception
            goto L_0x0086
        L_0x0074:
            r0 = move-exception
            goto L_0x009c
        L_0x0076:
            r0 = move-exception
            r7 = r1
            goto L_0x00b5
        L_0x0079:
            r0 = move-exception
            r7 = r1
            goto L_0x0086
        L_0x007c:
            r0 = move-exception
            r7 = r1
            goto L_0x009c
        L_0x007f:
            r0 = move-exception
            r6 = r1
            r7 = r6
            goto L_0x00b5
        L_0x0083:
            r0 = move-exception
            r6 = r1
            r7 = r6
        L_0x0086:
            r0.printStackTrace()     // Catch:{ all -> 0x00b4 }
            if (r6 == 0) goto L_0x0093
            r6.close()     // Catch:{ IOException -> 0x008f }
            goto L_0x0093
        L_0x008f:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0093:
            if (r7 == 0) goto L_0x00b3
            r7.close()     // Catch:{ IOException -> 0x00af }
            goto L_0x00b3
        L_0x0099:
            r0 = move-exception
            r6 = r1
            r7 = r6
        L_0x009c:
            r0.printStackTrace()     // Catch:{ all -> 0x00b4 }
            if (r6 == 0) goto L_0x00a9
            r6.close()     // Catch:{ IOException -> 0x00a5 }
            goto L_0x00a9
        L_0x00a5:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00a9:
            if (r7 == 0) goto L_0x00b3
            r7.close()     // Catch:{ IOException -> 0x00af }
            goto L_0x00b3
        L_0x00af:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00b3:
            return r1
        L_0x00b4:
            r0 = move-exception
        L_0x00b5:
            if (r6 == 0) goto L_0x00bf
            r6.close()     // Catch:{ IOException -> 0x00bb }
            goto L_0x00bf
        L_0x00bb:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00bf:
            if (r7 == 0) goto L_0x00c9
            r7.close()     // Catch:{ IOException -> 0x00c5 }
            goto L_0x00c9
        L_0x00c5:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00c9:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.guide.GuideService.readAssetsFile(int, int):byte[]");
    }

    public void setBackUpPath(GNaviPath gNaviPath) {
        setNaviPath(gNaviPath, -1);
    }
}
