package com.autonavi.minimap.offline.model.compat.compatdb;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.voicesquare.DownloadVoiceDao;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.offline.model.OfflineDbHelper;
import com.autonavi.minimap.offline.utils.OfflineLog;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import com.autonavi.minimap.offline.utils.VoiceUtils;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class CompatDb {
    private static final String CONFIG_FILE_NAME = "F8xxDialectVoiceList.cfg";
    private static final String CONFIG_FILE_NAME_V2 = "F8xxDialectVoiceList.txt";
    private static final String CONFIG_SUB_FOLDER = "/autonavi/800_850/";
    protected static final String TAG = "CompatDB";
    protected SQLiteDatabase db = null;
    protected File voiceConfig = null;

    public abstract boolean dataRestore();

    public CompatDb(String str) {
        this.db = openDB(str);
        this.voiceConfig = new File(FilePathHelper.getInstance().getVoiceTtsConfigDir(), CONFIG_FILE_NAME);
        if (!this.voiceConfig.isFile() || !this.voiceConfig.exists()) {
            this.voiceConfig = new File(FilePathHelper.getInstance().getVoiceTtsConfigDir(), CONFIG_FILE_NAME_V2);
        }
    }

    @Nullable
    public static SQLiteDatabase openDB(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return SQLiteDatabase.openDatabase(str, null, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean restoreVoicesFromConfig() {
        ArrayList arrayList = new ArrayList();
        String readDataFromFile = (!this.voiceConfig.exists() || !this.voiceConfig.isFile() || this.voiceConfig.length() <= 0) ? null : VoiceUtils.readDataFromFile(this.voiceConfig);
        OfflineLog.d(TAG, "loadConfigData(): ".concat(String.valueOf(readDataFromFile)));
        if (!TextUtils.isEmpty(readDataFromFile)) {
            try {
                JSONArray optJSONArray = new JSONObject(readDataFromFile).optJSONArray("101");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    int length = optJSONArray.length();
                    for (int i = 0; i < length; i++) {
                        tw twVar = new tw();
                        ua uaVar = new ua();
                        JSONObject jSONObject = optJSONArray.getJSONObject(i);
                        int optInt = jSONObject.optInt("7");
                        int optInt2 = jSONObject.optInt("4");
                        String optString = jSONObject.optString("71");
                        String optString2 = jSONObject.optString("74");
                        twVar.c = jSONObject.optString("72");
                        long j = (long) optInt2;
                        twVar.g = j;
                        twVar.d = optString;
                        twVar.e = jSONObject.optString("73");
                        twVar.o = jSONObject.optInt("5");
                        twVar.f = jSONObject.optString("74");
                        twVar.h = jSONObject.optInt("10");
                        twVar.i = jSONObject.optString("75");
                        twVar.j = jSONObject.optString("76");
                        twVar.k = jSONObject.optString("77");
                        twVar.l = jSONObject.optString("78");
                        twVar.m = jSONObject.optString("79");
                        twVar.n = jSONObject.optString("80");
                        if (optInt > 0) {
                            String generateDataPath = VoiceUtils.generateDataPath(optString);
                            uaVar.b = optString2;
                            uaVar.d = j;
                            uaVar.c = 64;
                            uaVar.f = generateDataPath;
                            uaVar.e = 0;
                            arrayList.add(uaVar);
                        }
                    }
                }
                DownloadVoiceDao downloadVoiceDao = OfflineDbHelper.getInstance().getDownloadVoiceDao();
                if (downloadVoiceDao != null) {
                    downloadVoiceDao.deleteAll();
                }
                if (arrayList.size() > 0) {
                    if (downloadVoiceDao != null) {
                        downloadVoiceDao.insertOrReplaceInTx((Iterable<T>) arrayList);
                    }
                    OfflineSpUtil.setOfflineTTSGuideTipShown(false);
                    IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
                    if (iVoicePackageManager != null) {
                        iVoicePackageManager.setIsUpgradeAe8TTSVersion(true);
                        iVoicePackageManager.restoreDefaultTTS();
                    }
                }
            } catch (JSONException unused) {
            }
        }
        return true;
    }
}
