package com.iflytek.tts.TtsService;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class TtsManagerUtil {
    public static void deleteOldVoiceFile() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                StringBuilder sb = new StringBuilder();
                sb.append(FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication()));
                sb.append("/autonavi/TTS");
                String sb2 = sb.toString();
                File file = new File(sb2);
                if (file.exists()) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append("/common.irf");
                    File file2 = new File(sb3.toString());
                    if (file2.exists() && file2.isFile()) {
                        file2.delete();
                    }
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(sb2);
                    sb4.append("/xiaoyan.irf");
                    File file3 = new File(sb4.toString());
                    if (file3.exists() && file3.isFile()) {
                        file3.delete();
                    }
                    file.delete();
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append(FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication()));
                sb5.append("/autonavi/data/voice/common.irf");
                File file4 = new File(sb5.toString());
                if (file4.exists() && file4.isFile()) {
                    file4.delete();
                }
                return;
            }
            StringBuilder sb6 = new StringBuilder();
            sb6.append(FileUtil.getFilesDir());
            sb6.append("/data/voice");
            String sb7 = sb6.toString();
            File file5 = new File(sb7);
            if (file5.exists()) {
                StringBuilder sb8 = new StringBuilder();
                sb8.append(sb7);
                sb8.append("/common.irf");
                File file6 = new File(sb8.toString());
                if (file6.exists() && file6.isFile()) {
                    file6.delete();
                }
                StringBuilder sb9 = new StringBuilder();
                sb9.append(sb7);
                sb9.append("/xiaoyan.irf");
                File file7 = new File(sb9.toString());
                if (file7.exists() && file7.isFile()) {
                    file7.delete();
                }
                file5.delete();
            }
        } catch (Exception e) {
            LogUtil.actionLogV2("P00067", "B020", LogUtil.createJSONObj("type", "delete old voice file failed"));
            e.printStackTrace();
        }
    }

    public static boolean buildIsGdgVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_GDG.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static boolean buildIsZhouXingxingVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_ZXX.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static boolean buildIsLzlVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_LZL_COMMON.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static boolean buildIsTFBoysYYQXVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_YYQX.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static boolean buildIsTFBoysWYVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_WY.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static boolean buildIsTFBoysWJKVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_WJK.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static boolean buildIsLYHVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_LYH.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static boolean buildIsGXSVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_GXS.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static boolean buildIsHXMNXVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_HXM_NX.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static boolean buildIsHXMGZVoice() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return IVoicePackageManager.VOICE_PACKAGE_HXM_GZ.equals(iVoicePackageManager.getCurrentTtsName2());
        }
        return false;
    }

    public static void setUserMode(int i) {
        Tts.getInstance().setParam(TtsManager.TTS_PARAM_USERMODE, i);
        log("TtsManager.setUserMode = ".concat(String.valueOf(i)));
    }

    public static String getCommonFilePath() {
        if (TextUtils.isEmpty(TtsManager.COMMON_FILE_PATH)) {
            StringBuilder sb = new StringBuilder();
            sb.append(FileUtil.getFilesDir());
            sb.append("/voice");
            TtsManager.COMMON_FILE_PATH = sb.toString();
        }
        return TtsManager.COMMON_FILE_PATH;
    }

    public static String getDefaultFilePath(Context context) {
        if (TextUtils.isEmpty(TtsManager.DEFAULT_TTS_PATH)) {
            StringBuilder sb = new StringBuilder();
            sb.append(FileUtil.getFilesDir());
            sb.append("/voice");
            TtsManager.DEFAULT_TTS_PATH = sb.toString();
            StringBuilder sb2 = new StringBuilder("DEFAULT_TTS_PATH = ");
            sb2.append(TtsManager.DEFAULT_TTS_PATH);
            log(sb2.toString());
        }
        return TtsManager.DEFAULT_TTS_PATH;
    }

    public static String getCommonFileFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCommonFilePath());
        sb.append("/common.irf");
        return sb.toString();
    }

    public static String getDefaultFileFullName(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDefaultFilePath(context));
        sb.append("/lzl.irf");
        return sb.toString();
    }

    public static String getXiaoyanFileFullName(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDefaultFilePath(context));
        sb.append("/xiaoyan.irf");
        return sb.toString();
    }

    public static void setParam() {
        if (buildIsLzlVoice()) {
            Tts.getInstance().setParam(1280, 99);
            Tts.getInstance().setParam(1282, 2000);
        } else if (buildIsGdgVoice()) {
            Tts.getInstance().setParam(1280, 99);
        } else {
            Tts.getInstance().setParam(256, 1);
        }
        Tts.getInstance().setParam(1284, 32767);
        setUserMode(1);
        setVolumeGain(0);
    }

    public static void setParam(String str) {
        AMapLog.i(TtsManager.TAG, "daihq    setParam   playType:".concat(String.valueOf(str)));
        if (!TextUtils.isEmpty(str)) {
            if (str.equals("1") || str.equals("9")) {
                Tts.getInstance().setParam(1280, 99);
                Tts.getInstance().setParam(1282, 2000);
            } else if (str.equals("2")) {
                Tts.getInstance().setParam(1280, 99);
            } else if (str.equals("21")) {
                Tts.getInstance().setParam(1282, 2000);
                Tts.getInstance().setParam(256, 1);
            } else {
                Tts.getInstance().setParam(256, 1);
            }
            Tts.getInstance().setParam(1284, 32767);
            setUserMode(1);
            setVolumeGain(0);
        }
    }

    public static void setVolumeGain(int i) {
        Tts.getInstance().setParam(1285, i);
        log("TtsManager.setVolumeGain = ".concat(String.valueOf(i)));
    }

    public static int getVolumeGain() {
        int param = Tts.getInstance().getParam(1285);
        log("TtsManager, gain = ".concat(String.valueOf(param)));
        return param;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007c, code lost:
        if (r0 != null) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00aa, code lost:
        if (r0 != null) goto L_0x007e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b0 A[SYNTHETIC, Splitter:B:33:0x00b0] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0171 A[SYNTHETIC, Splitter:B:71:0x0171] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0178 A[SYNTHETIC, Splitter:B:77:0x0178] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void checkCommonTtsFile() {
        /*
            deleteOldVoiceFile()
            java.io.File r0 = new java.io.File
            java.lang.String r1 = getCommonFileFullName()
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x0046
            boolean r1 = r0.isFile()
            if (r1 == 0) goto L_0x0046
            long r1 = r0.length()
            com.amap.bundle.mapstorage.MapSharePreference r3 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r4 = "SharedPreferences"
            r3.<init>(r4)
            java.lang.String r4 = "tts_common"
            com.amap.bundle.blutils.app.ConfigerHelper r5 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r6 = "tts_common"
            java.lang.String r5 = r5.getKeyValue(r6)
            long r5 = java.lang.Long.parseLong(r5)
            long r4 = r3.getLongValue(r4, r5)
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0046
            r0.delete()
            java.lang.String r1 = "tts_common"
            r3.remove(r1)
        L_0x0046:
            boolean r0 = r0.exists()
            r1 = 0
            if (r0 != 0) goto L_0x00b4
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0087, all -> 0x0082 }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ Exception -> 0x0087, all -> 0x0082 }
            java.lang.String r2 = "tts/Resource_6.0_common.png"
            java.io.InputStream r0 = r0.open(r2)     // Catch:{ Exception -> 0x0087, all -> 0x0082 }
            if (r0 == 0) goto L_0x007c
            java.lang.String r2 = getCommonFilePath()     // Catch:{ Exception -> 0x007a }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x007a }
            r3.<init>(r2)     // Catch:{ Exception -> 0x007a }
            boolean r4 = r3.isFile()     // Catch:{ Exception -> 0x007a }
            if (r4 == 0) goto L_0x0076
            boolean r4 = r3.exists()     // Catch:{ Exception -> 0x007a }
            if (r4 == 0) goto L_0x0076
            r3.delete()     // Catch:{ Exception -> 0x007a }
        L_0x0076:
            defpackage.ahf.a(r0, r2)     // Catch:{ Exception -> 0x007a }
            goto L_0x007c
        L_0x007a:
            r2 = move-exception
            goto L_0x0089
        L_0x007c:
            if (r0 == 0) goto L_0x00b4
        L_0x007e:
            r0.close()     // Catch:{ Exception -> 0x00b4 }
            goto L_0x00b4
        L_0x0082:
            r0 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
            goto L_0x00ae
        L_0x0087:
            r2 = move-exception
            r0 = r1
        L_0x0089:
            java.lang.String r3 = "P00067"
            java.lang.String r4 = "B020"
            java.lang.String r5 = "type"
            java.lang.String r6 = "Resource_6.0_common decompress failed"
            org.json.JSONObject r5 = com.amap.bundle.statistics.util.LogUtil.createJSONObj(r5, r6)     // Catch:{ all -> 0x00ad }
            com.amap.bundle.statistics.util.LogUtil.actionLogV2(r3, r4, r5)     // Catch:{ all -> 0x00ad }
            java.lang.String r3 = "P0009"
            java.lang.String r4 = "E005"
            java.lang.String r5 = "06 "
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = r5.concat(r2)     // Catch:{ all -> 0x00ad }
            com.iflytek.tts.TtsService.alc.ALCTtsLog.p2(r3, r4, r2)     // Catch:{ all -> 0x00ad }
            if (r0 == 0) goto L_0x00b4
            goto L_0x007e
        L_0x00ad:
            r1 = move-exception
        L_0x00ae:
            if (r0 == 0) goto L_0x00b3
            r0.close()     // Catch:{ Exception -> 0x00b3 }
        L_0x00b3:
            throw r1
        L_0x00b4:
            java.io.File r0 = new java.io.File
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r2 = getDefaultFileFullName(r2)
            r0.<init>(r2)
            boolean r2 = r0.exists()
            if (r2 == 0) goto L_0x0101
            boolean r2 = r0.isFile()
            if (r2 == 0) goto L_0x0101
            long r2 = r0.length()
            java.lang.String r4 = "checkCommonTtsFile    fSize:"
            java.lang.String r5 = java.lang.String.valueOf(r2)
            java.lang.String r4 = r4.concat(r5)
            log(r4)
            com.amap.bundle.blutils.app.ConfigerHelper r4 = com.amap.bundle.blutils.app.ConfigerHelper.getInstance()
            java.lang.String r5 = "tts_lzl"
            java.lang.String r4 = r4.getKeyValue(r5)
            long r4 = java.lang.Long.parseLong(r4)
            java.lang.String r6 = "checkCommonTtsFile    ftmpSize:"
            java.lang.String r7 = java.lang.String.valueOf(r4)
            java.lang.String r6 = r6.concat(r7)
            log(r6)
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x0101
            r0.delete()
        L_0x0101:
            boolean r0 = r0.exists()
            if (r0 != 0) goto L_0x017c
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x014a, all -> 0x0145 }
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch:{ Exception -> 0x014a, all -> 0x0145 }
            java.lang.String r2 = "tts/Resource_6.0_lzl.png"
            java.io.InputStream r0 = r0.open(r2)     // Catch:{ Exception -> 0x014a, all -> 0x0145 }
            if (r0 == 0) goto L_0x013f
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x013d }
            java.lang.String r1 = getDefaultFilePath(r1)     // Catch:{ Exception -> 0x013d }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x013d }
            r2.<init>(r1)     // Catch:{ Exception -> 0x013d }
            boolean r3 = r2.isFile()     // Catch:{ Exception -> 0x013d }
            if (r3 == 0) goto L_0x0134
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x013d }
            if (r3 == 0) goto L_0x0134
            r2.delete()     // Catch:{ Exception -> 0x013d }
        L_0x0134:
            defpackage.ahf.a(r0, r1)     // Catch:{ Exception -> 0x013d }
            java.lang.String r1 = "checkCommonTtsFile    将assets中的林志玲语音包解压到default目录"
            log(r1)     // Catch:{ Exception -> 0x013d }
            goto L_0x013f
        L_0x013d:
            r1 = move-exception
            goto L_0x014e
        L_0x013f:
            if (r0 == 0) goto L_0x017c
            r0.close()     // Catch:{ Exception -> 0x0144 }
        L_0x0144:
            return
        L_0x0145:
            r0 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
            goto L_0x0176
        L_0x014a:
            r0 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
        L_0x014e:
            java.lang.String r2 = "P00067"
            java.lang.String r3 = "B020"
            java.lang.String r4 = "type"
            java.lang.String r5 = "Resource_6.0_lzl decompress failed"
            org.json.JSONObject r4 = com.amap.bundle.statistics.util.LogUtil.createJSONObj(r4, r5)     // Catch:{ all -> 0x0175 }
            com.amap.bundle.statistics.util.LogUtil.actionLogV2(r2, r3, r4)     // Catch:{ all -> 0x0175 }
            java.lang.String r2 = "P0009"
            java.lang.String r3 = "E005"
            java.lang.String r4 = "15 "
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0175 }
            java.lang.String r1 = r4.concat(r1)     // Catch:{ all -> 0x0175 }
            com.iflytek.tts.TtsService.alc.ALCTtsLog.p2(r2, r3, r1)     // Catch:{ all -> 0x0175 }
            if (r0 == 0) goto L_0x017c
            r0.close()     // Catch:{ Exception -> 0x0174 }
        L_0x0174:
            return
        L_0x0175:
            r1 = move-exception
        L_0x0176:
            if (r0 == 0) goto L_0x017b
            r0.close()     // Catch:{ Exception -> 0x017b }
        L_0x017b:
            throw r1
        L_0x017c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iflytek.tts.TtsService.TtsManagerUtil.checkCommonTtsFile():void");
    }

    public static boolean isCommonFileExist() {
        File file = new File(getCommonFileFullName());
        return file.exists() && file.isFile();
    }

    public static boolean isXiaoyanFileExist() {
        File file = new File(getXiaoyanFileFullName(AMapAppGlobal.getApplication()));
        return file.exists() && file.isFile();
    }

    public static boolean isDefaultVoiceFileExist() {
        File file = new File(getDefaultFileFullName(AMapAppGlobal.getApplication()));
        return file.exists() && file.isFile();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:26|27|(2:28|(1:30)(1:83))|31|32|33|34|35|36) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x0063 */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0074 A[SYNTHETIC, Splitter:B:48:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0079 A[SYNTHETIC, Splitter:B:52:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0080 A[SYNTHETIC, Splitter:B:60:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0085 A[SYNTHETIC, Splitter:B:64:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x008c A[SYNTHETIC, Splitter:B:72:0x008c] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0091 A[SYNTHETIC, Splitter:B:76:0x0091] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyFile(java.lang.String r3, java.lang.String r4) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x0097
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L_0x000e
            goto L_0x0097
        L_0x000e:
            java.io.File r0 = new java.io.File
            r0.<init>(r3)
            java.io.File r3 = new java.io.File
            r3.<init>(r4)
            boolean r4 = r0.exists()
            if (r4 != 0) goto L_0x001f
            return
        L_0x001f:
            boolean r4 = r0.isFile()
            if (r4 != 0) goto L_0x0026
            return
        L_0x0026:
            boolean r4 = r0.canRead()
            if (r4 != 0) goto L_0x002d
            return
        L_0x002d:
            java.io.File r4 = r3.getParentFile()
            if (r4 == 0) goto L_0x0096
            boolean r4 = r4.exists()
            if (r4 != 0) goto L_0x003a
            goto L_0x0096
        L_0x003a:
            boolean r4 = r3.exists()
            if (r4 == 0) goto L_0x0043
            r3.delete()
        L_0x0043:
            r4 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0089, Exception -> 0x007d, all -> 0x0070 }
            r1.<init>(r0)     // Catch:{ IOException -> 0x0089, Exception -> 0x007d, all -> 0x0070 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x008a, Exception -> 0x007e, all -> 0x006e }
            r0.<init>(r3)     // Catch:{ IOException -> 0x008a, Exception -> 0x007e, all -> 0x006e }
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]     // Catch:{ IOException -> 0x006c, Exception -> 0x006a, all -> 0x0067 }
        L_0x0052:
            int r4 = r1.read(r3)     // Catch:{ IOException -> 0x006c, Exception -> 0x006a, all -> 0x0067 }
            if (r4 <= 0) goto L_0x005d
            r2 = 0
            r0.write(r3, r2, r4)     // Catch:{ IOException -> 0x006c, Exception -> 0x006a, all -> 0x0067 }
            goto L_0x0052
        L_0x005d:
            r0.flush()     // Catch:{ IOException -> 0x006c, Exception -> 0x006a, all -> 0x0067 }
            r1.close()     // Catch:{ Exception -> 0x0063 }
        L_0x0063:
            r0.close()     // Catch:{ Exception -> 0x0066 }
        L_0x0066:
            return
        L_0x0067:
            r3 = move-exception
            r4 = r0
            goto L_0x0072
        L_0x006a:
            r4 = r0
            goto L_0x007e
        L_0x006c:
            r4 = r0
            goto L_0x008a
        L_0x006e:
            r3 = move-exception
            goto L_0x0072
        L_0x0070:
            r3 = move-exception
            r1 = r4
        L_0x0072:
            if (r1 == 0) goto L_0x0077
            r1.close()     // Catch:{ Exception -> 0x0077 }
        L_0x0077:
            if (r4 == 0) goto L_0x007c
            r4.close()     // Catch:{ Exception -> 0x007c }
        L_0x007c:
            throw r3
        L_0x007d:
            r1 = r4
        L_0x007e:
            if (r1 == 0) goto L_0x0083
            r1.close()     // Catch:{ Exception -> 0x0083 }
        L_0x0083:
            if (r4 == 0) goto L_0x0095
            r4.close()     // Catch:{ Exception -> 0x0088 }
        L_0x0088:
            return
        L_0x0089:
            r1 = r4
        L_0x008a:
            if (r1 == 0) goto L_0x008f
            r1.close()     // Catch:{ Exception -> 0x008f }
        L_0x008f:
            if (r4 == 0) goto L_0x0095
            r4.close()     // Catch:{ Exception -> 0x0094 }
        L_0x0094:
            return
        L_0x0095:
            return
        L_0x0096:
            return
        L_0x0097:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iflytek.tts.TtsService.TtsManagerUtil.copyFile(java.lang.String, java.lang.String):void");
    }

    public static void playbackLog(int i, String str, int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", i2);
            jSONObject.put("play_id", i);
            if (i <= 0) {
                jSONObject.put("text", str);
            }
            if (i2 == 1) {
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                if (latestPosition != null) {
                    jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, latestPosition.getLongitude());
                    jSONObject.put("lat", latestPosition.getLatitude());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder("playbackLog    ");
        sb.append(jSONObject.toString());
        log(sb.toString());
        AMapLog.playback(System.currentTimeMillis(), 5, 4, jSONObject.toString());
    }

    private static void log(String str) {
        if (bno.a) {
            AMapLog.debug(ALCTtsConstant.GROUP_NAME, "android", "TtsManagerUtil   ".concat(String.valueOf(str)));
        }
    }
}
