package com.alipay.android.phone.inside.offlinecode.utils;

import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.offlinecode.rpc.ScardCenterRpcProvider;
import com.alipay.android.phone.inside.offlinecode.rpc.model.ScardCenterRes;
import com.alipay.android.phone.inside.storage.file.FileUtils;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.File;
import java.io.FileNotFoundException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ScriptManager {
    public static final String CLIENT_SDK_VERSION = "0.0.5";
    private static final String TAG = "ScriptManager";

    static class Holder {
        static ScriptManager instance = new ScriptManager();

        private Holder() {
        }
    }

    public static ScriptManager getInstance() {
        return Holder.instance;
    }

    private ScriptManager() {
        Encryption.getInstance();
    }

    public void delete(String str) {
        try {
            File file = new File(getScriptCachePath(), str);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            LoggerFactory.e().a(TAG, (String) "BusScriptDeleteFileEx", (Throwable) e);
        }
    }

    public String get(String str, String str2, String str3, String str4) {
        try {
            String loadFromFile = loadFromFile(str);
            if (verifySign(loadFromFile, str4, str3)) {
                return loadFromFile;
            }
            Behavior a = LoggerFactory.d().a(TAG, BehaviorType.EVENT, "BusScriptSignNotMatch");
            a.g = str2;
            a.h = str3;
            a.i = "cache";
            delete(str);
            throw new Exception("scriptMac not match");
        } catch (Exception unused) {
            String downloadScript = downloadScript(str2, str3);
            if (verifySign(downloadScript, str4, str3)) {
                saveToFile(str, downloadScript);
                return downloadScript;
            }
            Behavior a2 = LoggerFactory.d().a(TAG, BehaviorType.EVENT, "BusScriptSignNotMatch");
            a2.g = str2;
            a2.h = str3;
            a2.i = LogConstant.SPLASH_SCREEN_DOWNLOADED;
            throw new RuntimeException();
        }
    }

    private void saveToFile(String str, String str2) {
        try {
            File file = new File(getScriptCachePath());
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, str);
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileUtils.a(file2.getAbsolutePath(), Encryption.getInstance().encrypt(str2));
        } catch (Exception e) {
            LoggerFactory.e().a(TAG, (String) "BusScriptSaveFileEx", (Throwable) e);
        }
    }

    private String downloadScript(String str, String str2) {
        String str3 = null;
        try {
            ScardCenterRes queryScript = new ScardCenterRpcProvider().queryScript(LauncherApplication.a(), str, str2);
            if (TextUtils.equals(queryScript.code, GenBusCodeService.CODE_SUCESS)) {
                str3 = queryScript.getJSONResult().getString("scriptCode");
            }
            Behavior a = LoggerFactory.d().a(TAG, BehaviorType.EVENT, "BusScriptDownload");
            a.g = str;
            a.h = str2;
            a.i = queryScript.code;
        } catch (Exception e) {
            LoggerFactory.e().a(TAG, (String) "BusScriptDownloadEx", (Throwable) e);
        }
        return str3;
    }

    private static String getScriptCachePath() throws Exception {
        String absolutePath = LauncherApplication.a().getFilesDir().getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append(absolutePath);
        sb.append(File.separator);
        sb.append("buscode_scripts");
        return sb.toString();
    }

    private String loadFromFile(String str) throws Exception {
        File file = new File(getScriptCachePath(), str);
        if (!file.exists()) {
            Behavior a = LoggerFactory.d().a(TAG, BehaviorType.EVENT, "BusScriptLocalStatus");
            a.g = "false";
            a.i = "cache";
            throw new FileNotFoundException(str);
        }
        Behavior a2 = LoggerFactory.d().a(TAG, BehaviorType.EVENT, "BusScriptLocalStatus");
        a2.g = "true";
        a2.i = "cache";
        return Encryption.getInstance().decrypt(FileUtils.a(file));
    }

    public static boolean verifySign(String str, String str2, String str3) {
        byte[] bytes = str.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(str3.getBytes(), "HmacMD5");
        try {
            Mac instance = Mac.getInstance(secretKeySpec.getAlgorithm());
            instance.init(secretKeySpec);
            return str2.equals(HexUtils.toHexString(instance.doFinal(bytes)));
        } catch (Exception unused) {
            return false;
        }
    }
}
