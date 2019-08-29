package com.autonavi.minimap.ajx3;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.sdk.authjs.a;
import com.autonavi.minimap.acanvas.ACanvasJNI;
import com.autonavi.minimap.acanvas.ACanvasVersion;
import com.autonavi.minimap.ajx3.ApplicationLifeCycle.APPLifeCycle;
import com.autonavi.minimap.ajx3.acanvas.AjxCanvasView;
import com.autonavi.minimap.ajx3.acanvas.module.AjxModuleCanvas;
import com.autonavi.minimap.ajx3.acanvas.module.AjxModuleTouch;
import com.autonavi.minimap.ajx3.action.IActionLog;
import com.autonavi.minimap.ajx3.audio.AjxAudioModule;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsContextObserver;
import com.autonavi.minimap.ajx3.core.JsContextRef;
import com.autonavi.minimap.ajx3.core.JsEngineInstance;
import com.autonavi.minimap.ajx3.core.JsEngineObserver;
import com.autonavi.minimap.ajx3.core.JsEngineObserver.InspectorHandler;
import com.autonavi.minimap.ajx3.core.MemoryStorageRef;
import com.autonavi.minimap.ajx3.core.PageConfig;
import com.autonavi.minimap.ajx3.debug.DebugAlertDialog;
import com.autonavi.minimap.ajx3.exception.IllegalEngineException;
import com.autonavi.minimap.ajx3.image.ImageCache;
import com.autonavi.minimap.ajx3.loader.AjxMemoryDataPool;
import com.autonavi.minimap.ajx3.loader.IAjxImageLoader;
import com.autonavi.minimap.ajx3.log.LogBody.Builder;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleApp;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleBluetooth;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleBridge;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleClipboard;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleDB;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleFile;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleKeyboard;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleLifeCycle;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleLocalStorage;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleOS;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModulePhone;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleTestTouch;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.platform.impl.TextMeasurement;
import com.autonavi.minimap.ajx3.support.scan.AjxScanView;
import com.autonavi.minimap.ajx3.util.AjxALCLog;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.HardwareAdapterUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.LogRecorder;
import com.autonavi.minimap.ajx3.util.SnapshotUtil;
import com.autonavi.minimap.ajx3.util.ToastUtils;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.AjxViewManager;
import com.autonavi.minimap.ajx3.widget.view.PointTipView;
import com.autonavi.minimap.ajx3.widget.view.SVG;
import com.autonavi.minimap.ajx3.widget.view.timepicker.Picker;
import com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView;
import com.autonavi.minimap.ajx3.widget.view.video.AjxVideo;
import com.autonavi.minimap.ajx3.widget.view.video.player.PlayerManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class Ajx {
    private static final int MAX_LOG_LEVEL = 4;
    /* access modifiers changed from: private */
    public static Ajx sAjx;
    public static long sStartTime;
    private Typeface[] defaultTypeface = new Typeface[4];
    /* access modifiers changed from: private */
    public AjxActionListener mActionListener;
    private IActionLog mActionLog;
    /* access modifiers changed from: private */
    public AjxEngineProvider mAjxEngineProvider;
    private ApplicationLifeCycle mApplicationLifeCycle = new ApplicationLifeCycle();
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public DebugAlertDialog mDebugAlert;
    /* access modifiers changed from: private */
    public InspectorHandler mInpectorHandler;
    private JsEngineInstance mJsEngineInstance;
    private JsEngineObserver mJsEngineObserver = new JsEngineObserver() {
        public void onEngineDestroyed() {
        }

        public void onEngineInitialized(int i) {
            AjxALCLog.info(AjxALCLog.TAG_PERFORMANCE, "native load ajx engine onEngineInitialized.");
            if (Ajx.this.mActionListener != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("result", i);
                } catch (JSONException unused) {
                }
                Ajx.this.mActionListener.dispatchMessage("onEngineInitialized", jSONObject.toString());
            }
        }

        public void onRuntimeException(long j, int i, String str, String str2) {
            if (Ajx.this.mJsRuntimeExceptionListener != null) {
                Ajx.this.mJsRuntimeExceptionListener.onRuntimeException(Ajx.this.mAjxEngineProvider.getAjxContext(j), i, str, str2);
            }
        }

        public JsContextRef onServiceCreated(long j, String str) {
            Context context = (Context) Ajx.this.mServicesNativeContextMap.remove(str);
            AjxEngineProvider access$200 = Ajx.this.mAjxEngineProvider;
            if (context == null) {
                context = Ajx.this.mContext;
            }
            JsContextRef createAjxServiceContext = access$200.createAjxServiceContext(context, j, str);
            Ajx.this.mServicesMap.put(Long.valueOf(j), createAjxServiceContext);
            String bundleName = AjxFileInfo.getBundleName(str);
            if (!TextUtils.isEmpty(bundleName)) {
                StringBuilder sb = new StringBuilder(" onServiceCreated  save bundle name : ");
                sb.append(str);
                sb.append(" , ");
                sb.append(j);
                sb.append(" , ");
                sb.append(bundleName);
                Ajx.this.mServicesBundleMap.put(Long.valueOf(j), bundleName);
            }
            return createAjxServiceContext;
        }

        public String getAllBundlesIndexSnapshot() {
            return AjxFileInfo.getAllBundlesIndexSnapshot();
        }

        public int getPatchIndex(String str) {
            String bundleName = AjxFileInfo.getBundleName(str);
            StringBuilder sb = new StringBuilder(" getPatchIndex name: ");
            sb.append(str);
            sb.append(" ,bundleName: ");
            sb.append(bundleName);
            return AjxFileInfo.getLatestPatchIndex(bundleName);
        }

        public JsContextObserver getJsServiceContextObserver() {
            return Ajx.this.mJsServiceContextObserver;
        }

        public void onServiceDestroyed(long j) {
            JsContextRef jsContextRef = (JsContextRef) Ajx.this.mServicesMap.remove(Long.valueOf(j));
            if (jsContextRef != null) {
                Ajx.this.mAjxEngineProvider.destroyAjxModule(jsContextRef);
            }
            Ajx.this.mAjxEngineProvider.destroyAjxContext(j);
            StringBuilder sb = new StringBuilder(" onServiceDestroyed  remove : ");
            sb.append(j);
            sb.append(" , ");
            sb.append((String) Ajx.this.mServicesBundleMap.remove(Long.valueOf(j)));
        }

        public void onLogPrint(String str, String str2, int i) {
            LogManager.jsPrintLog(Ajx.this.transferLogLevel(i), str, str2);
            StringBuilder sb = new StringBuilder(" page: ");
            sb.append(str2);
            sb.append(" log: ");
            sb.append(str);
            LogRecorder.log(LogHelper.DEFAULT_TAG, sb.toString());
        }

        public void onLog(int i, String str) {
            LogManager.log(new Builder().setLogLevel(Ajx.this.transferLogLevel(i)).setLogType(1).setMsg(str).setPagePath("").setContextId(Ajx.sAjx != null ? Ajx.getInstance().getCurrJsContext() : -1).setAjxFileVersion(AjxFileInfo.getAllAjxFileVersion()).setTag("AJXEngine Log").build());
            LogRecorder.log("AJX3-JS-ENGINE", str);
        }

        public void onReceiveInspectorMessage(long j, String str) {
            synchronized (Ajx.class) {
                StringBuilder sb = new StringBuilder("onReceiveInspectorMessage  ctxid = ");
                sb.append(j);
                sb.append("\n  msg");
                sb.append(str);
                if (Ajx.this.mInpectorHandler != null) {
                    Ajx.this.mInpectorHandler.handleMessage(j, str);
                }
            }
        }

        public void onJsCodeCoverageDataCollection(String str, Object obj, String str2) {
            if (Ajx.this.mActionListener != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("uri", str);
                    jSONObject.put("params", obj);
                    jSONObject.put("data", str2);
                } catch (JSONException unused) {
                }
                Ajx.this.dispatchMessage("coverage_test", jSONObject.toString());
            }
        }

        public void onStartWaittingDebugger(int i, long j, String str) {
            if (Ajx.this.mDebugAlert.isShowing()) {
                Ajx.this.mDebugAlert.dismiss();
            }
            if (i > 0) {
                Ajx.this.mDebugAlert.setData(j, str, i);
                Ajx.this.mDebugAlert.show();
            }
        }
    };
    /* access modifiers changed from: private */
    public IJsRuntimeExceptionListener mJsRuntimeExceptionListener;
    /* access modifiers changed from: private */
    public JsContextObserver mJsServiceContextObserver;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Long, String> mServicesBundleMap = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Long, JsContextRef> mServicesMap = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public HashMap<String, Context> mServicesNativeContextMap = new HashMap<>();

    public final int transferLogLevel(int i) {
        int i2 = i - 2;
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 > 4) {
            return 4;
        }
        return i2;
    }

    private Ajx(AjxConfig ajxConfig, @NonNull Context context, IJsRuntimeExceptionListener iJsRuntimeExceptionListener) {
        ajxConfig = ajxConfig == null ? new AjxConfig.Builder().build() : ajxConfig;
        AjxALCLog.setALCLog(ajxConfig.getALCInterface());
        AjxALCLog.info(AjxALCLog.TAG_PERFORMANCE, "native start load ajx engine.");
        this.mContext = context;
        this.mDebugAlert = new DebugAlertDialog(context);
        this.mDebugAlert.init();
        this.mJsEngineInstance = new JsEngineInstance(context, ajxConfig, this.mJsEngineObserver);
        this.mAjxEngineProvider = new AjxEngineProvider(context, ajxConfig, this.mJsEngineInstance.getAjxLoaderManager(), this.mJsEngineInstance.get());
        this.mJsRuntimeExceptionListener = iJsRuntimeExceptionListener;
        this.mActionLog = ajxConfig.getActionLog();
        this.mJsServiceContextObserver = ajxConfig.getJsServiceContextObserver();
        initDefaultTypeface();
    }

    public final String getCloudConfig(String str) {
        return this.mJsEngineInstance.getCloudConfig(str);
    }

    public final IJsRuntimeExceptionListener getJsRuntimeExceptionListener() {
        return this.mJsRuntimeExceptionListener;
    }

    public final void registerActionListener(AjxActionListener ajxActionListener) {
        this.mActionListener = ajxActionListener;
    }

    private void initDefaultTypeface() {
        TextView textView = new TextView(this.mContext);
        textView.setTypeface(Typeface.defaultFromStyle(0));
        this.defaultTypeface[0] = textView.getTypeface();
        textView.setTypeface(Typeface.defaultFromStyle(1));
        this.defaultTypeface[1] = textView.getTypeface();
        textView.setTypeface(Typeface.defaultFromStyle(2));
        this.defaultTypeface[2] = textView.getTypeface();
        textView.setTypeface(Typeface.defaultFromStyle(3));
        this.defaultTypeface[3] = textView.getTypeface();
    }

    public static synchronized void init(@NonNull Context context, @Nullable AjxConfig ajxConfig) {
        synchronized (Ajx.class) {
            if (sAjx != null) {
                throw new IllegalEngineException((String) "AjxEngine has already been initialized.");
            }
            DimensionUtils.initDensity(context.getApplicationContext());
            ToastUtils.init(context.getApplicationContext());
            Ajx ajx = new Ajx(ajxConfig, context, ajxConfig.getJsRuntimeExceptionListener());
            sAjx = ajx;
            ajx.registerModule(AjxModuleOS.class, AjxModuleLocalStorage.class, AjxModuleApp.class, AjxModuleBridge.class, AjxModuleTestTouch.class, AjxModuleFile.class, AjxModuleDB.class, AjxModuleClipboard.class, AjxModuleLifeCycle.class, AjxModuleMedia.class, AjxModulePhone.class, AjxModuleBluetooth.class, AjxModuleCanvas.class, AjxAudioModule.class, AjxModuleTouch.class, AjxModuleKeyboard.class);
            sAjx.registerView("datepicker", TimePickerView.class);
            sAjx.registerView("picker", Picker.class);
            sAjx.registerView("scan", AjxScanView.class);
            sAjx.registerView("bubbleview", PointTipView.class);
            sAjx.registerView("canvas", AjxCanvasView.class);
            sAjx.registerView("video", AjxVideo.class);
            sAjx.registerView("svg", SVG.class);
            AjxMemoryDataPool.getInstance().setDataDelegate(ajxConfig.getMemoryLoadDelegate());
            PlayerManager.init(context.getApplicationContext());
            SnapshotUtil.deleteAjx3Snapshot();
            HardwareAdapterUtils.updateHardwareAcceleratedForBorder();
        }
    }

    public static Ajx getInstance() {
        if (sAjx != null) {
            return sAjx;
        }
        throw new IllegalEngineException((String) "Ajx has not Initialized,please call init() method before getInstance()!");
    }

    public final IActionLog getActionLog() {
        return this.mActionLog;
    }

    @Nullable
    public final IAjxContext getAjxContext(long j) {
        return this.mAjxEngineProvider.getAjxContext(j);
    }

    public final IAjxContext createAjxContext(AjxView ajxView, JsRunInfo jsRunInfo) {
        return this.mAjxEngineProvider.createAjxContext(ajxView, jsRunInfo);
    }

    public final void destroy() {
        AjxModuleFile.destroy();
        AjxModuleDB.destroyHandleThread();
        this.mJsEngineInstance.destroy();
        ImageCache.getInstance(this.mContext).clear();
        if (sAjx != null) {
            sAjx.onDestroy();
        }
        sAjx = null;
    }

    public final void run(@NonNull IAjxContext iAjxContext, @NonNull JsRunInfo jsRunInfo, @NonNull JsContextObserver jsContextObserver) {
        this.mAjxEngineProvider.run(iAjxContext, jsRunInfo, jsContextObserver);
    }

    public final void startService(@NonNull String str, @NonNull String str2) {
        this.mAjxEngineProvider.startService(this.mContext, str, str2, null, null);
    }

    public final void startService(@NonNull String str, @NonNull String str2, Object obj, String str3) {
        this.mAjxEngineProvider.startService(this.mContext, str, str2, obj, str3);
    }

    public final void startService(@NonNull Context context, @NonNull String str, @NonNull String str2) {
        this.mServicesNativeContextMap.put(str2, context);
        this.mAjxEngineProvider.startService(context, str, str2, null, null);
    }

    public final void startService(@NonNull Context context, @NonNull String str, @NonNull String str2, Object obj, String str3) {
        this.mServicesNativeContextMap.put(str2, context);
        this.mAjxEngineProvider.startService(context, str, str2, obj, str3);
    }

    public final void stopService(@NonNull String str) {
        this.mAjxEngineProvider.stopService(str);
        this.mServicesNativeContextMap.remove(str);
    }

    public final boolean registerModule(@NonNull Class... clsArr) {
        try {
            return clsArr.length != 0 && this.mAjxEngineProvider.registerModule(clsArr);
        } catch (Exception e) {
            if (this.mJsRuntimeExceptionListener != null) {
                this.mJsRuntimeExceptionListener.onRuntimeException(this.mAjxEngineProvider.getAjxContext(getCurrJsContext()), 0, "registerModule", Log.getStackTraceString(e));
            }
            return false;
        }
    }

    public final void registerCustomTypeface(@NonNull String str, @NonNull String str2) {
        TextMeasurement.S_CUSTOM_FONTS.put(str, str2);
        ACanvasJNI.registerFont(this.mContext.getAssets(), str, str2);
    }

    public final <T> T getModuleIns(@NonNull IAjxContext iAjxContext, @NonNull String str) {
        return this.mAjxEngineProvider.getModuleIns(iAjxContext, str);
    }

    public final IAjxImageLoader lookupLoader(@NonNull String str) {
        return this.mAjxEngineProvider.lookupLoader(str);
    }

    public final void registerView(@NonNull String str, @NonNull Class<? extends View> cls) {
        if (!TextUtils.isEmpty(str)) {
            AjxViewManager.register(str, cls);
        }
    }

    public final PageConfig getPageConfig(String str, int i) {
        return this.mJsEngineInstance.get().getPageConfig(str, i);
    }

    public final void prepare() {
        this.mJsEngineInstance.get().prepare();
    }

    public final void addTimestamp(String str) {
        this.mJsEngineInstance.get().addTimestamp(str);
    }

    public final void addTimestampWithoutContext(String str) {
        this.mJsEngineInstance.get().addTimestampWithoutContext(str);
    }

    public final void prepare(String str) {
        this.mJsEngineInstance.get().prepare(str);
    }

    private void onDestroy() {
        this.mAjxEngineProvider.onDestroy();
    }

    @TargetApi(3)
    public final String getHostIp() {
        if (this.mContext != null) {
            return Formatter.formatIpAddress(((WifiManager) this.mContext.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getIpAddress());
        }
        throw new NullPointerException("mContext is null when try to getHostIp");
    }

    public final boolean isDebuggerSupported() {
        return this.mJsEngineInstance.get().isDebuggerSupported();
    }

    public final void startDebug(String str, String str2) {
        this.mJsEngineInstance.get().startDebug(str, str2);
    }

    public final void stopDebug() {
        this.mJsEngineInstance.get().stopDebug();
    }

    public final void cancelDebuggerWait(long j) {
        this.mJsEngineInstance.get().cancelDebuggerWait(j);
    }

    public final synchronized void setInpectorHandler(InspectorHandler inspectorHandler) {
        this.mInpectorHandler = inspectorHandler;
    }

    public final void sendInspectorMessage(long j, String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mJsEngineInstance.get().sendInspectorMessage(j, str);
        }
    }

    public final void trigJsCodeCoverageDataCollection(long j) {
        this.mJsEngineInstance.get().trigJsCodeCoverageDataCollection(j);
    }

    public final void setAjxOverlayFactory(long j) {
        this.mJsEngineInstance.setAjxOverlayFactory(j);
    }

    public final void setMapViewControl(long j) {
        this.mJsEngineInstance.setMapViewControl(j);
    }

    public final void setTbtControl(long j) {
        this.mJsEngineInstance.setTbtControl(j);
    }

    public final void setAjxNodeControl(long j) {
        this.mJsEngineInstance.setAjxNodeControl(j);
    }

    public final void setBusinessControl(long j) {
        this.mJsEngineInstance.setBusinessControl(j);
    }

    public final MemoryStorageRef getMemoryStorage(String str) {
        return this.mJsEngineInstance.get().getMemoryStorageRef(str);
    }

    public final SharedPreferences getSharedPreferences(String str) {
        return this.mContext.getSharedPreferences(str, 0);
    }

    public final String getPackageName() {
        return this.mContext.getPackageName();
    }

    public final AssetManager getAssets() {
        return this.mContext.getAssets();
    }

    public final void destroyMemoryStorage(MemoryStorageRef memoryStorageRef) {
        this.mJsEngineInstance.get().destroyMemoryStorageRef(memoryStorageRef);
    }

    public final void jniLog(String str) {
        this.mJsEngineInstance.get().jniLog(str);
    }

    public final String getAjxEngineVersion() {
        return this.mJsEngineInstance.getVersion();
    }

    public final String getAjxMagicMirrorVersion() {
        return ACanvasVersion.getMagicMirrorVersion();
    }

    public final String getJSEngineType() {
        return this.mJsEngineInstance.getJSEngineType();
    }

    public final boolean isEagleEyeEnable() {
        return this.mJsEngineInstance.get().isEagleEyeEnable();
    }

    public final void setEagleEyeEnable(boolean z) {
        this.mJsEngineInstance.get().setEagleEyeEnable(z);
    }

    public final long getCurrJsContext() {
        return this.mJsEngineInstance.get().getCurrJsContext();
    }

    public final String getBaseJsVersion() {
        return this.mJsEngineInstance.getBaseJsVersion();
    }

    public final boolean isPerformanceLogSupported() {
        return this.mJsEngineInstance.get().isPerformanceLogSupported();
    }

    public final boolean getPerformanceLogEnabled() {
        return this.mJsEngineInstance.get().getPerformanceLogEnabled();
    }

    public final void setPerformanceLogEnabled(boolean z) {
        this.mJsEngineInstance.get().setPerformanceLogEnabled(z);
    }

    public final Typeface getDefaultTypeface(int i) {
        if (i < 0 || i > 3) {
            i = 0;
        }
        Typeface typeface = this.defaultTypeface[i];
        if (typeface == null) {
            Typeface.defaultFromStyle(i);
        }
        return typeface;
    }

    /* access modifiers changed from: 0000 */
    public final void onAppLifeCycle(APPLifeCycle aPPLifeCycle, String str) {
        this.mApplicationLifeCycle.onAppLifeCycle(aPPLifeCycle, str, this.mServicesMap);
    }

    public final List<String> getActiveServicesBundleName() {
        ArrayList arrayList = new ArrayList();
        for (Long l : this.mServicesBundleMap.keySet()) {
            String str = this.mServicesBundleMap.get(l);
            if (!TextUtils.isEmpty(str) && !arrayList.contains(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public final void onContextCreate(IAjxContext iAjxContext, String str) {
        if (this.mActionListener != null) {
            long id = iAjxContext.getId();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("shadow", id);
                String bundleDependencies = AjxFileInfo.getBundleDependencies(str, iAjxContext.getPatchIndexString());
                jSONObject.put(a.d, str);
                jSONObject.put("bundleDependence", bundleDependencies);
            } catch (JSONException unused) {
            }
            this.mActionListener.dispatchMessage("onContextCreate", jSONObject.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void onContextDestroy(long j) {
        if (this.mActionListener != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("shadow", j);
            } catch (JSONException unused) {
            }
            this.mActionListener.dispatchMessage("onContextDestroy", jSONObject.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void dispatchMessage(String str, String str2) {
        if (this.mActionListener != null) {
            this.mActionListener.dispatchMessage(str, str2);
        }
    }

    public final void onLineLog(String str, String str2, String str3, String str4, String str5) {
        if (this.mActionListener != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("url", str);
                jSONObject.put("type", str2);
                jSONObject.put("btnId", str3);
                jSONObject.put(H5PermissionManager.level, str4);
                jSONObject.put("msg", str5);
            } catch (JSONException unused) {
            }
            this.mActionListener.dispatchMessage("onLineLog", jSONObject.toString());
        }
    }

    public final void reShowBlueBall() {
        if (this.mActionListener != null) {
            this.mActionListener.dispatchMessage("onReShowBlueBall", "onScanToDebug");
        }
    }
}
