package com.autonavi.ae;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.ae.search.SearchEngine;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.ae.data.DataService;
import com.autonavi.jni.ae.dice.InitConfig;
import com.autonavi.jni.ae.dice.NaviEngine;
import com.autonavi.jni.ae.gmap.GLMapEngine;
import com.autonavi.jni.ae.guide.GuideService;
import com.autonavi.jni.eyrie.amap.UiThreadWrapper;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.AjxConstant;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.vui.IVUIManager;
import java.io.File;
import java.util.concurrent.Executor;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AEUtil {
    public static final String CONFIGNAME = "GNaviConfig.xml";
    private static final String[] ENGINE_CLOUD_KEY_LIST = {"engine_pos", "engine_tbt", "engine_render", "engine_eyrie", "opt_record"};
    private static final int GLOG_OUTPUT_CLOSE = 0;
    private static final int GLOG_OUTPUT_CONSOLE = 2;
    private static final int GLOG_OUTPUT_FILE = 1;
    @Deprecated
    public static final boolean IS_AE = true;
    public static final boolean IS_DEBUG = false;
    @Deprecated
    public static final boolean IS_RECORD_GPS = false;
    public static final String RESZIPNAME = "res.zip";
    public static final String RES_PATH = "res/guide/";
    public static final String ROOTPATH = "/autonavi/";
    /* access modifiers changed from: private */
    public static final String TAG = "AEUtil";
    public static final String TBT_FILE_PATH = "/autonavi/";
    public static final String TEMP_PATH = "temp/";
    public static final String YUN_CONFITURATION_RES_BIN_NAME = "default_config.bin";
    public static boolean isAjx3Debug = false;
    public static boolean isAuiDebug = false;
    /* access modifiers changed from: private */
    public static MapSharePreference mSp = new MapSharePreference((String) "engine_cloud_cache");
    private static boolean sIsInited = false;
    private static final Executor singleExecutor = new ahn(1);

    static {
        AMapLog.d("SO_TRACK_AE_UTIL", Thread.currentThread().getName());
        long currentTimeMillis = System.currentTimeMillis();
        System.loadLibrary("dice");
        StringBuilder sb = new StringBuilder("loadLibrary : ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        AMapLog.i("MapApplication", sb.toString());
    }

    public static void init() {
        String str;
        if (!sIsInited) {
            String b = PathManager.a().b();
            if (new File(b).canWrite()) {
                StringBuilder sb = new StringBuilder();
                sb.append(b);
                sb.append("/autonavi/");
                str = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(FileUtil.getInternalSDCardPath(AMapAppGlobal.getApplication()));
                sb2.append("/autonavi/");
                str = sb2.toString();
            }
            AMapLog.d(TAG, "AEutil --init--currentPath=".concat(String.valueOf(str)));
            new File(str, CONFIGNAME);
            byte[] readAssetsFile = readAssetsFile("ae/GNaviConfig.xml");
            String str2 = null;
            if (readAssetsFile != null && readAssetsFile.length > 0) {
                if (readAssetsFile == null) {
                    throw new IllegalArgumentException("Parameter may not be null");
                }
                str2 = abd.a(readAssetsFile, readAssetsFile.length, "utf-8");
                AMapLog.d(TAG, "GNaviConfig.xml:".concat(String.valueOf(str2)));
            }
            loadEngineRes();
            InitConfig initConfig = new InitConfig();
            initConfig.mContext = AMapAppGlobal.getApplication();
            initConfig.mRootPath = PathManager.a().c(DirType.WORK_ROOT);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(CONFIGNAME);
            initConfig.mConfigPath = sb3.toString();
            initConfig.mConfigFileContent = str2;
            initConfig.mOfflineDataPath = PathManager.a().a(DirType.OFFLINE);
            initConfig.mP3dCrossPath = PathManager.a().a(DirType.DRIVE_OFFLINE);
            initConfig.mDebugConstant = false;
            initConfig.mUserCode = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.TBT_ACCOUNT);
            initConfig.mPassword = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.TBT_PASSWORD);
            initConfig.mMotorUserCode = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.MOTOR_TBT_ACCOUNT);
            initConfig.mMotorPassword = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.MOTOR_TBT_PASSWORD);
            initConfig.mUserBatch = "0";
            initConfig.mDeviceID = NetworkParam.getDiu();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication()));
            sb4.append("/autonavi//data");
            initConfig.mCachePath = sb4.toString();
            StringBuilder sb5 = new StringBuilder("mCachePath = ");
            sb5.append(initConfig.mCachePath);
            AMapLog.d(TAG, sb5.toString());
            UiThreadWrapper.init();
            initConfig.mUiWorkerPtr = UiThreadWrapper.getInstance().getNativePtr();
            initConfig.mWormHoleFlag = 1;
            setUpEngineCloudConfig();
            NaviEngine.init(initConfig);
            vh vhVar = (vh) a.a.a(vh.class);
            if (vhVar != null) {
                vhVar.a();
            }
            getOfflineDataServiceInstance();
            sIsInited = true;
            bmp.a((String) "Ver_Dice", NaviEngine.getLibDiceSoVersion());
        }
    }

    public static boolean isInited() {
        return sIsInited;
    }

    private static void setUpEngineCloudConfig() {
        String[] strArr;
        for (final String str : ENGINE_CLOUD_KEY_LIST) {
            final String stringValue = mSp.getStringValue(str, null);
            ku a = ku.a();
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" spCache: ");
            sb.append(stringValue);
            a.a(str2, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" spCache: ");
            sb2.append(stringValue);
            if (!TextUtils.isEmpty(stringValue)) {
                NaviEngine.onCloudConfigUpdate(str, stringValue);
            }
            lo.a().a(str, (lp) new lp() {
                public final void onConfigCallBack(int i) {
                }

                public final void onConfigResultCallBack(int i, String str) {
                    ku a2 = ku.a();
                    String access$000 = AEUtil.TAG;
                    StringBuilder sb = new StringBuilder("onConfigResultCallBack ");
                    sb.append(str);
                    sb.append(" status: ");
                    sb.append(i);
                    sb.append(" , result:");
                    sb.append(str);
                    a2.a(access$000, sb.toString());
                    AEUtil.TAG;
                    StringBuilder sb2 = new StringBuilder("onConfigResultCallBack ");
                    sb2.append(str);
                    sb2.append(" status: ");
                    sb2.append(i);
                    sb2.append(" , result:");
                    sb2.append(str);
                    if (i == 3) {
                        AEUtil.mSp.remove(str);
                        NaviEngine.onCloudConfigUpdate(str, "");
                        return;
                    }
                    ku a3 = ku.a();
                    String access$0002 = AEUtil.TAG;
                    StringBuilder sb3 = new StringBuilder("onConfigResultCallBack ");
                    sb3.append(str);
                    sb3.append(" equals: ");
                    sb3.append(TextUtils.equals(stringValue, str));
                    a3.a(access$0002, sb3.toString());
                    AEUtil.TAG;
                    StringBuilder sb4 = new StringBuilder("onConfigResultCallBack ");
                    sb4.append(str);
                    sb4.append(" equals: ");
                    sb4.append(TextUtils.equals(stringValue, str));
                    AEUtil.mSp.putStringValue(str, str);
                    if (!TextUtils.equals(stringValue, str)) {
                        NaviEngine.onCloudConfigUpdate(str, str);
                    }
                }
            });
        }
    }

    public static void unInit() {
        sIsInited = false;
    }

    public static DataService getDataServiceInstance() {
        return DataService.getInstance();
    }

    public static DataService getOfflineDataServiceInstance() {
        return DataService.getInstance();
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x005a A[SYNTHETIC, Splitter:B:26:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x006b A[SYNTHETIC, Splitter:B:37:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0078 A[SYNTHETIC, Splitter:B:46:0x0078] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:23:0x0055=Splitter:B:23:0x0055, B:34:0x0066=Splitter:B:34:0x0066} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void loadEngineRes() {
        /*
            com.amap.bundle.blutils.PathManager r0 = com.amap.bundle.blutils.PathManager.a()
            com.amap.bundle.blutils.PathManager$DirType r1 = com.amap.bundle.blutils.PathManager.DirType.RESOURCE
            java.lang.String r0 = r0.a(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0011
            return
        L_0x0011:
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r0 = checkEngineRes(r0)
            if (r0 != 0) goto L_0x0081
            r1.delete()
            r1.mkdirs()
            r0 = 0
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0063, OutOfMemoryError -> 0x0052, all -> 0x004e }
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch:{ Exception -> 0x0063, OutOfMemoryError -> 0x0052, all -> 0x004e }
            java.lang.String r3 = "ae/res.zip"
            java.io.InputStream r2 = r2.open(r3)     // Catch:{ Exception -> 0x0063, OutOfMemoryError -> 0x0052, all -> 0x004e }
            java.lang.String r0 = r1.getAbsolutePath()     // Catch:{ Exception -> 0x004c, OutOfMemoryError -> 0x004a }
            defpackage.ahf.a(r2, r0)     // Catch:{ Exception -> 0x004c, OutOfMemoryError -> 0x004a }
            java.lang.String r0 = TAG     // Catch:{ Exception -> 0x004c, OutOfMemoryError -> 0x004a }
            java.lang.String r1 = "loadEngineRes:OK"
            com.amap.bundle.logs.AMapLog.d(r0, r1)     // Catch:{ Exception -> 0x004c, OutOfMemoryError -> 0x004a }
            if (r2 == 0) goto L_0x0074
            r2.close()     // Catch:{ IOException -> 0x0045 }
            return
        L_0x0045:
            r0 = move-exception
            r0.printStackTrace()
            return
        L_0x004a:
            r0 = move-exception
            goto L_0x0055
        L_0x004c:
            r0 = move-exception
            goto L_0x0066
        L_0x004e:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L_0x0076
        L_0x0052:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0055:
            r0.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r2 == 0) goto L_0x0074
            r2.close()     // Catch:{ IOException -> 0x005e }
            return
        L_0x005e:
            r0 = move-exception
            r0.printStackTrace()
            return
        L_0x0063:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0066:
            r0.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r2 == 0) goto L_0x0074
            r2.close()     // Catch:{ IOException -> 0x006f }
            return
        L_0x006f:
            r0 = move-exception
            r0.printStackTrace()
            return
        L_0x0074:
            return
        L_0x0075:
            r0 = move-exception
        L_0x0076:
            if (r2 == 0) goto L_0x0080
            r2.close()     // Catch:{ IOException -> 0x007c }
            goto L_0x0080
        L_0x007c:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0080:
            throw r0
        L_0x0081:
            java.lang.String r0 = TAG
            java.lang.String r1 = "isReadyToUpdate:OK"
            com.amap.bundle.logs.AMapLog.d(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.ae.AEUtil.loadEngineRes():void");
    }

    public static boolean checkEngineRes(String str) {
        boolean z;
        boolean isInstalled = isInstalled();
        String[] strArr = {"global.db"};
        int i = 0;
        while (true) {
            if (i > 0) {
                z = true;
                break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(File.separator);
            sb.append(strArr[0]);
            File file = new File(sb.toString());
            if (!file.isFile() || !file.exists()) {
                z = false;
            } else {
                i++;
            }
        }
        z = false;
        return isInstalled && z;
    }

    public static String getEngineVersion() {
        try {
            return ((dfm) ank.a(dfm.class)).c("GuideService");
        } catch (Throwable unused) {
            return "n/a";
        }
    }

    public static String getNaviRouteVersion() {
        try {
            return ((dfm) ank.a(dfm.class)).c("RouteService");
        } catch (Throwable unused) {
            return "n/a";
        }
    }

    public static String getPosVersion() {
        try {
            return anf.d();
        } catch (Throwable unused) {
            return "n/a";
        }
    }

    public static String getSearchVersion() {
        try {
            return SearchEngine.getEngineVersion();
        } catch (Throwable unused) {
            return "n/a";
        }
    }

    public static String getOfflineEnginVersion() {
        try {
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            String str = null;
            if (iOfflineManager != null) {
                str = iOfflineManager.getOfflineEngineVersion();
            }
            return str;
        } catch (Throwable unused) {
            return "n/a";
        }
    }

    public static String getBusVersion() {
        try {
            System.loadLibrary("busnavi");
            Class<?> cls = Class.forName("com.autonavi.minimap.route.busnavi.BusNavi");
            return (String) ahv.a(cls, cls.newInstance(), "GetVersion", null, null);
        } catch (Throwable th) {
            th.printStackTrace();
            return "error";
        }
    }

    public static String getWTBTVersion() {
        try {
            return (String) ahv.a(Class.forName("com.autonavi.minimap.route.wtbt.WTBTDecoder"), (String) "getVersion");
        } catch (Throwable th) {
            th.printStackTrace();
            return "error";
        }
    }

    public static String getHealthEngineVersion() {
        try {
            return (String) ahv.a(Class.forName("com.autonavi.jni.route.health.IHealth"), (String) "GetVersion");
        } catch (Throwable th) {
            th.printStackTrace();
            return "error";
        }
    }

    public static String getVersionInfo() {
        StringBuffer stringBuffer = new StringBuffer(getDiceVersionInfo());
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        String str = "n/a";
        if (iOfflineManager != null) {
            str = iOfflineManager.getOfflineEngineVersion();
        }
        stringBuffer.append("离线引擎版本号: ");
        stringBuffer.append(str);
        stringBuffer.append("\n");
        stringBuffer.append("矢量大图引擎版本号: ");
        stringBuffer.append(getVectorgraphEngineVersion());
        stringBuffer.append("\n");
        stringBuffer.append("Global.db版本号: ");
        stringBuffer.append(lm.a());
        stringBuffer.append("\n");
        stringBuffer.append("ALC引擎版本号: ");
        stringBuffer.append(AMapLog.getALCEngineVersion());
        stringBuffer.append("\n");
        stringBuffer.append(NaviManager.getTotalVersion());
        stringBuffer.append("\n");
        stringBuffer.append("\n健康引擎版本号: ");
        stringBuffer.append(getHealthEngineVersion());
        stringBuffer.append("\n");
        stringBuffer.append(getDataVersion());
        stringBuffer.append("\n");
        stringBuffer.append(getAjx3Version());
        IVUIManager iVUIManager = (IVUIManager) ank.a(IVUIManager.class);
        if (iVUIManager != null) {
            stringBuffer.append("\n");
            stringBuffer.append(iVUIManager.getVersionInfo());
        }
        return stringBuffer.toString();
    }

    @NonNull
    private static String getDiceVersionInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("EngineAAR: 10.20.0.192\n");
        stringBuffer.append("libdice.so: ");
        stringBuffer.append(NaviEngine.getLibDiceSoVersion());
        stringBuffer.append("\n");
        stringBuffer.append("bl_dice: ");
        stringBuffer.append(NaviEngine.getBlDiceVersion());
        stringBuffer.append("\n");
        stringBuffer.append("GNaviDice版本号: ");
        stringBuffer.append(NaviEngine.getSdkVersion());
        stringBuffer.append("\n");
        stringBuffer.append("渲染引擎版本号: ");
        stringBuffer.append(getMapVersion());
        stringBuffer.append("\n");
        stringBuffer.append("搜索引擎版本号: ");
        stringBuffer.append(getSearchVersion());
        stringBuffer.append("\n");
        stringBuffer.append("定位引擎版本号: ");
        stringBuffer.append(getPosVersion());
        stringBuffer.append("\n");
        stringBuffer.append("路径引擎版本号: ");
        stringBuffer.append(getNaviRouteVersion());
        stringBuffer.append("\n");
        stringBuffer.append("引导引擎版本号: ");
        stringBuffer.append(getEngineVersion());
        stringBuffer.append("\n");
        stringBuffer.append("travel引擎版本号: ");
        stringBuffer.append(GuideService.getTravelSdkVersion());
        stringBuffer.append("\n");
        stringBuffer.append("数据引擎版本号: ");
        stringBuffer.append(DataService.getEngineVersion());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    private static String getAjx3Version() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder("\najx引擎版本号: ");
        sb.append(Ajx.getInstance().getAjxEngineVersion());
        stringBuffer.append(sb.toString());
        if (!TextUtils.isEmpty(AjxConstant.AAR_VERSION)) {
            StringBuilder sb2 = new StringBuilder("\najx aar版本号: ");
            sb2.append(AjxConstant.AAR_VERSION);
            stringBuffer.append(sb2.toString());
            stringBuffer.append("\najx aar is RELEASE: true");
        }
        StringBuilder sb3 = new StringBuilder("\najx MagicMirror版本号: ");
        sb3.append(Ajx.getInstance().getAjxMagicMirrorVersion());
        stringBuffer.append(sb3.toString());
        String str = null;
        if (TextUtils.isEmpty(null)) {
            str = AjxFileInfo.getAllAjxFileBaseVersion();
            if (!TextUtils.isEmpty(str)) {
                str = str.replaceAll(";", ";\n");
            }
        }
        stringBuffer.append("\nbase ajx版本号: ".concat(String.valueOf(str)));
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            stringBuffer.append("\ndiff ajx版本号: ".concat(String.valueOf(allAjxLatestPatchVersion.replaceAll(";", ";\n"))));
        }
        return stringBuffer.toString();
    }

    private static String getDataVersion() {
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n地图数据版本号: ");
        stringBuffer.append(iOfflineManager.getMapDataVersionForCurCity());
        stringBuffer.append("\n导航数据版本号: ");
        stringBuffer.append(iOfflineManager.getRouteDataVersionForCurCity());
        stringBuffer.append("\n路口放大图数据版本号: ");
        stringBuffer.append(iOfflineManager.getCrossDataVersionForCurCity());
        stringBuffer.append("\nPOI数据版本号: ");
        stringBuffer.append(iOfflineManager.getPoiDataVersionForCurCity());
        stringBuffer.append("\n3D放大图数据版本号: ");
        stringBuffer.append(iOfflineManager.get3dCrossDataVersionForCurCity());
        return stringBuffer.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x005b A[SYNTHETIC, Splitter:B:39:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0065 A[SYNTHETIC, Splitter:B:44:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0071 A[SYNTHETIC, Splitter:B:51:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x007b A[SYNTHETIC, Splitter:B:56:0x007b] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0087 A[SYNTHETIC, Splitter:B:63:0x0087] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0091 A[SYNTHETIC, Splitter:B:68:0x0091] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:36:0x0056=Splitter:B:36:0x0056, B:48:0x006c=Splitter:B:48:0x006c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] readAssetsFile(java.lang.String r7) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0069, OutOfMemoryError -> 0x0053, all -> 0x004e }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ Exception -> 0x0069, OutOfMemoryError -> 0x0053, all -> 0x004e }
            java.io.InputStream r7 = r0.open(r7)     // Catch:{ Exception -> 0x0069, OutOfMemoryError -> 0x0053, all -> 0x004e }
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x004b, OutOfMemoryError -> 0x0048, all -> 0x0043 }
            r0.<init>()     // Catch:{ Exception -> 0x004b, OutOfMemoryError -> 0x0048, all -> 0x0043 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r2]     // Catch:{ Exception -> 0x0041, OutOfMemoryError -> 0x003f }
        L_0x001d:
            r4 = 0
            int r5 = r7.read(r3, r4, r2)     // Catch:{ Exception -> 0x0041, OutOfMemoryError -> 0x003f }
            if (r5 <= 0) goto L_0x0028
            r0.write(r3, r4, r5)     // Catch:{ Exception -> 0x0041, OutOfMemoryError -> 0x003f }
            goto L_0x001d
        L_0x0028:
            byte[] r2 = r0.toByteArray()     // Catch:{ Exception -> 0x0041, OutOfMemoryError -> 0x003f }
            if (r7 == 0) goto L_0x0036
            r7.close()     // Catch:{ IOException -> 0x0032 }
            goto L_0x0036
        L_0x0032:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0036:
            r0.close()     // Catch:{ IOException -> 0x003a }
            goto L_0x003e
        L_0x003a:
            r7 = move-exception
            r7.printStackTrace()
        L_0x003e:
            return r2
        L_0x003f:
            r2 = move-exception
            goto L_0x0056
        L_0x0041:
            r2 = move-exception
            goto L_0x006c
        L_0x0043:
            r0 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x0085
        L_0x0048:
            r2 = move-exception
            r0 = r1
            goto L_0x0056
        L_0x004b:
            r2 = move-exception
            r0 = r1
            goto L_0x006c
        L_0x004e:
            r7 = move-exception
            r0 = r1
            r1 = r7
            r7 = r0
            goto L_0x0085
        L_0x0053:
            r2 = move-exception
            r7 = r1
            r0 = r7
        L_0x0056:
            r2.printStackTrace()     // Catch:{ all -> 0x0084 }
            if (r7 == 0) goto L_0x0063
            r7.close()     // Catch:{ IOException -> 0x005f }
            goto L_0x0063
        L_0x005f:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0063:
            if (r0 == 0) goto L_0x0083
            r0.close()     // Catch:{ IOException -> 0x007f }
            goto L_0x0083
        L_0x0069:
            r2 = move-exception
            r7 = r1
            r0 = r7
        L_0x006c:
            r2.printStackTrace()     // Catch:{ all -> 0x0084 }
            if (r7 == 0) goto L_0x0079
            r7.close()     // Catch:{ IOException -> 0x0075 }
            goto L_0x0079
        L_0x0075:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0079:
            if (r0 == 0) goto L_0x0083
            r0.close()     // Catch:{ IOException -> 0x007f }
            goto L_0x0083
        L_0x007f:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0083:
            return r1
        L_0x0084:
            r1 = move-exception
        L_0x0085:
            if (r7 == 0) goto L_0x008f
            r7.close()     // Catch:{ IOException -> 0x008b }
            goto L_0x008f
        L_0x008b:
            r7 = move-exception
            r7.printStackTrace()
        L_0x008f:
            if (r0 == 0) goto L_0x0099
            r0.close()     // Catch:{ IOException -> 0x0095 }
            goto L_0x0099
        L_0x0095:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0099:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.ae.AEUtil.readAssetsFile(java.lang.String):byte[]");
    }

    private static boolean isInstalled() {
        StringBuilder sb = new StringBuilder();
        sb.append(chl.b());
        sb.append(chl.d());
        String sb2 = sb.toString();
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("first_install", 0);
        if (sharedPreferences.getBoolean(sb2, false)) {
            return true;
        }
        deleteYunConfig();
        Editor edit = sharedPreferences.edit();
        if (edit != null) {
            edit.putBoolean(sb2, true);
            edit.apply();
        }
        return false;
    }

    public static void run(Runnable runnable) {
        singleExecutor.execute(runnable);
    }

    private static void deleteYunConfig() {
        singleExecutor.execute(new Runnable() {
            public final void run() {
                File file;
                StringBuilder sb = new StringBuilder();
                sb.append(PathManager.a().b());
                sb.append("/autonavi/res/guide/");
                String sb2 = sb.toString();
                File file2 = new File(sb2);
                if (file2.exists() && file2.isDirectory()) {
                    String[] list = file2.list();
                    if (list != null) {
                        for (int i = 0; i < list.length; i++) {
                            if (sb2.endsWith(File.separator)) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(sb2);
                                sb3.append(list[i]);
                                file = new File(sb3.toString());
                            } else {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(sb2);
                                sb4.append(File.separator);
                                sb4.append(list[i]);
                                file = new File(sb4.toString());
                            }
                            if (file.exists() && file.isFile()) {
                                try {
                                    file.delete();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public static String getMapVersion() {
        try {
            return GLMapEngine.nativeGetMapEngineVersion();
        } catch (Throwable unused) {
            return "n/a";
        }
    }

    public static String getVectorgraphEngineVersion() {
        try {
            return GLMapEngine.nativeGetNaviRebuildVersion();
        } catch (Throwable unused) {
            return "n/a";
        }
    }
}
