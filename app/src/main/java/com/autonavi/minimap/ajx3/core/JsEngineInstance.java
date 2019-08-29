package com.autonavi.minimap.ajx3.core;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.AjxConfig;
import com.autonavi.minimap.ajx3.loader.AjxLoaderManager;
import com.autonavi.minimap.ajx3.platform.ackor.IPlatformServiceManager;
import com.autonavi.minimap.ajx3.util.FileUtil;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.ToastUtils;
import java.io.File;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class JsEngineInstance {
    private static final String KEY_BASE_JS_PATH = "base_js_path";
    private static final String KEY_DEBUG_BASE_JS_PATH = "debug_base_js_path";
    public static final String KEY_PAGE_CONFIG_PATH = "page_config_path";
    private static final String SO_NAME = "ajx_v3";
    private static final String VALUE_BASE_JS_PATH_DEFAULT = "path://base.js";
    public static final String VALUE_PAGE_CONFIG_PATH_DEFAULT = "ajx_page.txt";
    private AjxLoaderManager mAjxLoaderManager;
    private CloudConfig mCloudConfig;
    private JsEngine mJsEngine = null;

    private native void nativeDestroy();

    private native String nativeGetBaseJsVersion();

    private native String nativeGetJavaScriptEngine();

    private native String nativeGetVersion();

    private native long nativeInit(IPlatformServiceManager iPlatformServiceManager, JsEngineObserver jsEngineObserver);

    private native void nativeSetAjxNodeControl(long j);

    private native void nativeSetAjxOverlayFactory(long j);

    private native void nativeSetBusinessControl(long j);

    private native void nativeSetMapViewControl(long j);

    private native void nativeSetTBTControl(long j);

    static {
        System.loadLibrary(SO_NAME);
    }

    public JsEngineInstance(@NonNull Context context, @NonNull AjxConfig ajxConfig, JsEngineObserver jsEngineObserver) {
        this.mCloudConfig = ajxConfig.getCloudConfig();
        this.mAjxLoaderManager = new AjxLoaderManager(context, ajxConfig);
        AjxPlatformServiceManager ajxPlatformServiceManager = new AjxPlatformServiceManager(context, this.mAjxLoaderManager, ajxConfig);
        if (this.mJsEngine == null) {
            this.mJsEngine = new JsEngine(nativeInit(ajxPlatformServiceManager, jsEngineObserver));
        }
        String baseJsPath = ajxConfig.getBaseJsPath();
        String debugBaseJsPath = ajxConfig.getDebugBaseJsPath();
        baseJsPath = TextUtils.isEmpty(baseJsPath) ? VALUE_BASE_JS_PATH_DEFAULT : baseJsPath;
        debugBaseJsPath = TextUtils.isEmpty(debugBaseJsPath) ? null : debugBaseJsPath;
        this.mJsEngine.setGlobalProperty(KEY_BASE_JS_PATH, baseJsPath);
        this.mJsEngine.setGlobalProperty(KEY_DEBUG_BASE_JS_PATH, debugBaseJsPath);
        JsEngine jsEngine = this.mJsEngine;
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getExternalStorageDirectory());
        sb.append("/autonavi/AJXEngine/");
        jsEngine.setGlobalProperty("writable_path", sb.toString());
        this.mJsEngine.setGlobalProperty("device_name", Build.MODEL);
        this.mJsEngine.setGlobalProperty("device_os", VERSION.RELEASE);
        File file = new File(context.getCacheDir(), "AJXDebugConfig");
        if (!file.exists()) {
            file.mkdirs();
        } else if (!file.isDirectory()) {
            file.delete();
            file.mkdirs();
        }
        this.mJsEngine.setGlobalProperty("debugcfg_path", file.getAbsolutePath());
    }

    public AjxLoaderManager getAjxLoaderManager() {
        return this.mAjxLoaderManager;
    }

    public JsEngine get() {
        return this.mJsEngine;
    }

    public String getVersion() {
        return nativeGetVersion();
    }

    public String getBaseJsVersion() {
        return nativeGetBaseJsVersion();
    }

    public String getJSEngineType() {
        return nativeGetJavaScriptEngine();
    }

    public void setAjxOverlayFactory(long j) {
        nativeSetAjxOverlayFactory(j);
    }

    public void setMapViewControl(long j) {
        nativeSetMapViewControl(j);
    }

    public void setTbtControl(long j) {
        nativeSetTBTControl(j);
    }

    public void setAjxNodeControl(long j) {
        nativeSetAjxNodeControl(j);
    }

    public void setBusinessControl(long j) {
        nativeSetBusinessControl(j);
    }

    public void destroy() {
        nativeDestroy();
        this.mJsEngine = null;
        this.mCloudConfig = null;
    }

    private static boolean loadEngineFromSdCard(String str) {
        if (ToastUtils.getContext() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(ToastUtils.getContext().getFilesDir().toString());
            sb.append(File.separator);
            sb.append(FileUtil.getSoFileName(str));
            String sb2 = sb.toString();
            File file = new File("/sdcard/", FileUtil.getSoFileName(str));
            if (file.exists()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("/sdcard/");
                sb3.append(FileUtil.getSoFileName(str));
                if (!FileUtil.copyFile(sb2, sb3.toString())) {
                    return false;
                }
                file.delete();
                try {
                    System.load(sb2);
                    StringBuilder sb4 = new StringBuilder("加载");
                    sb4.append("/sdcard/");
                    sb4.append("libajx_v3.so ");
                    ToastUtils.showToast(sb4.toString(), 1);
                    StringBuilder sb5 = new StringBuilder("警告:load libajx_v3.so from ");
                    sb5.append("/sdcard/");
                    sb5.append(" !!!");
                    LogHelper.addEngineLoge(sb5.toString());
                    return true;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    File file2 = new File(sb2);
                    if (file2.exists()) {
                        file2.delete();
                    }
                    return false;
                }
            } else if (!new File(sb2).exists()) {
                return false;
            } else {
                System.load(sb2);
                ToastUtils.showToast("加载".concat(String.valueOf(sb2)), 1);
                StringBuilder sb6 = new StringBuilder("警告:load libajx_v3.so from ");
                sb6.append(sb2);
                sb6.append(" !!!");
                LogHelper.addEngineLoge(sb6.toString());
                return true;
            }
        } else {
            LogHelper.addEngineLoge("警告:ToastUtils 尚未初始化 !!!");
            return false;
        }
    }

    public String getCloudConfig(String str) {
        if (this.mCloudConfig != null) {
            return this.mCloudConfig.getConfig(str);
        }
        return null;
    }
}
