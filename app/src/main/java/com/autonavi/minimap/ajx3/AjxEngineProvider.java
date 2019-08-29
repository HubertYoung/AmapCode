package com.autonavi.minimap.ajx3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.sdk.authjs.a;
import com.autonavi.minimap.ajx3.context.AjxContext;
import com.autonavi.minimap.ajx3.context.AjxServiceContext;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsContextObserver;
import com.autonavi.minimap.ajx3.core.JsContextRef;
import com.autonavi.minimap.ajx3.core.JsEngine;
import com.autonavi.minimap.ajx3.core.JsEngineInstance;
import com.autonavi.minimap.ajx3.core.JsModuleCallback;
import com.autonavi.minimap.ajx3.core.PageConfig;
import com.autonavi.minimap.ajx3.exception.IllegalEngineException;
import com.autonavi.minimap.ajx3.loader.AjxLoaderManager;
import com.autonavi.minimap.ajx3.loader.IAjxImageLoader;
import com.autonavi.minimap.ajx3.modules.AjxModuleManager;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.widget.AjxView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AjxEngineProvider implements JsModuleCallback {
    private static final String EXCEPTION_KEYWORD = "Caused by:";
    private static final String PATH_PRE_FIX = "path://";
    private final AjxConfig mAjxConfig;
    private ConcurrentHashMap<Long, IAjxContext> mAjxContextMap = new ConcurrentHashMap<>();
    private List<String> mBundleList = new ArrayList();
    private HashMap<String, Integer> mBundlesIndex = new HashMap<>();
    private String mBundlesIndexString = "";
    private boolean mIsDestroy;
    private final JsEngine mJsEngine;
    private final AjxLoaderManager mLoaderManager;
    private boolean mModuleInitByFile = false;
    private final AjxModuleManager mModuleManager = new AjxModuleManager(this);

    AjxEngineProvider(@NonNull Context context, @NonNull AjxConfig ajxConfig, @NonNull AjxLoaderManager ajxLoaderManager, @NonNull JsEngine jsEngine) {
        this.mLoaderManager = ajxLoaderManager;
        this.mIsDestroy = false;
        this.mJsEngine = jsEngine;
        this.mJsEngine.setJsModuleCallback(this);
        this.mAjxConfig = ajxConfig;
        initModule(context);
        initBundlesIndex();
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0052 A[SYNTHETIC, Splitter:B:34:0x0052] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initModule(android.content.Context r4) {
        /*
            r3 = this;
            com.autonavi.minimap.ajx3.AjxConfig r0 = r3.mAjxConfig
            java.lang.String r0 = r0.getModuleConfigPath()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x005b
            java.lang.String r1 = "asset://"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x005b
            android.content.res.AssetManager r4 = r4.getAssets()
            r1 = 0
            r2 = 8
            java.lang.String r2 = r0.substring(r2)     // Catch:{ Exception -> 0x0041 }
            java.io.InputStream r4 = r4.open(r2)     // Catch:{ Exception -> 0x0041 }
            if (r4 == 0) goto L_0x0033
            r1 = 1
            r3.mModuleInitByFile = r1     // Catch:{ Exception -> 0x0030, all -> 0x002e }
            com.autonavi.minimap.ajx3.core.JsEngine r1 = r3.mJsEngine     // Catch:{ Exception -> 0x0030, all -> 0x002e }
            r1.registerModule(r0)     // Catch:{ Exception -> 0x0030, all -> 0x002e }
            goto L_0x0033
        L_0x002e:
            r0 = move-exception
            goto L_0x0050
        L_0x0030:
            r0 = move-exception
            r1 = r4
            goto L_0x0042
        L_0x0033:
            if (r4 == 0) goto L_0x005b
            r4.close()     // Catch:{ IOException -> 0x0039 }
            return
        L_0x0039:
            r4 = move-exception
            r4.printStackTrace()
            return
        L_0x003e:
            r0 = move-exception
            r4 = r1
            goto L_0x0050
        L_0x0041:
            r0 = move-exception
        L_0x0042:
            r0.printStackTrace()     // Catch:{ all -> 0x003e }
            if (r1 == 0) goto L_0x005b
            r1.close()     // Catch:{ IOException -> 0x004b }
            return
        L_0x004b:
            r4 = move-exception
            r4.printStackTrace()
            return
        L_0x0050:
            if (r4 == 0) goto L_0x005a
            r4.close()     // Catch:{ IOException -> 0x0056 }
            goto L_0x005a
        L_0x0056:
            r4 = move-exception
            r4.printStackTrace()
        L_0x005a:
            throw r0
        L_0x005b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.AjxEngineProvider.initModule(android.content.Context):void");
    }

    public final void onDestroy() {
        this.mAjxContextMap.clear();
        this.mModuleManager.clearAllContextModules();
        this.mIsDestroy = true;
    }

    public final void invokeJsContextDestroyEvent(@NonNull JsContextRef jsContextRef) {
        checkEngine();
        this.mJsEngine.destroyContext(jsContextRef);
        this.mModuleManager.onContextDestroy(jsContextRef);
    }

    public final void destroyAjxModule(@NonNull JsContextRef jsContextRef) {
        this.mModuleManager.onContextDestroy(jsContextRef);
    }

    public final void destroyAjxContext(@NonNull long j) {
        checkEngine();
        this.mAjxContextMap.remove(Long.valueOf(j));
        this.mModuleManager.clearContextModules(j);
        Ajx.getInstance().onContextDestroy(j);
    }

    public final AjxConfig getAjxConfig() {
        return this.mAjxConfig;
    }

    public final IAjxContext createAjxContext(AjxView ajxView, JsRunInfo jsRunInfo) {
        checkEngine();
        JsContextRef alloc = this.mJsEngine.alloc();
        initBundlesIndex();
        String bundleName = AjxFileInfo.getBundleName(jsRunInfo.getUrl());
        int latestPatchIndex = AjxFileInfo.getLatestPatchIndex(bundleName);
        String url = jsRunInfo.getUrl();
        if (this.mBundleList.contains(bundleName)) {
            JsEngine jsEngine = this.mJsEngine;
            StringBuilder sb = new StringBuilder("path://");
            sb.append(bundleName);
            sb.append(File.separator);
            sb.append(JsEngineInstance.VALUE_PAGE_CONFIG_PATH_DEFAULT);
            jsEngine.setGlobalProperty(JsEngineInstance.KEY_PAGE_CONFIG_PATH, sb.toString());
        } else if (!TextUtils.isEmpty(jsRunInfo.getAjxPageConfigPath())) {
            this.mJsEngine.setGlobalProperty(JsEngineInstance.KEY_PAGE_CONFIG_PATH, jsRunInfo.getAjxPageConfigPath());
        } else {
            this.mJsEngine.setGlobalProperty(JsEngineInstance.KEY_PAGE_CONFIG_PATH, "path://ajx_page.txt");
        }
        PageConfig pageConfig = this.mJsEngine.getPageConfig(url, latestPatchIndex);
        if (pageConfig != null) {
            jsRunInfo.setPageConfig(pageConfig);
        }
        AjxContext ajxContext = new AjxContext(ajxView, this, alloc, jsRunInfo, latestPatchIndex, this.mBundlesIndexString, this.mBundlesIndex);
        this.mAjxContextMap.put(Long.valueOf(alloc.shadow()), ajxContext);
        Ajx.getInstance().onContextCreate(ajxContext, bundleName);
        return ajxContext;
    }

    public final JsContextRef createAjxServiceContext(Context context, long j, String str) {
        JsContextRef jsContextRef = new JsContextRef(j);
        initBundlesIndex();
        String bundleName = AjxFileInfo.getBundleName(str);
        AjxServiceContext ajxServiceContext = new AjxServiceContext(context, this, jsContextRef, AjxFileInfo.getLatestPatchIndex(bundleName), this.mBundlesIndexString, this.mBundlesIndex);
        this.mAjxContextMap.put(Long.valueOf(j), ajxServiceContext);
        Ajx.getInstance().onContextCreate(ajxServiceContext, bundleName);
        return jsContextRef;
    }

    private void initBundlesIndex() {
        this.mBundlesIndex.clear();
        this.mBundleList.clear();
        this.mBundlesIndexString = AjxFileInfo.getAllBundlesIndexSnapshot();
        if (!TextUtils.isEmpty(this.mBundlesIndexString)) {
            try {
                JSONArray jSONArray = new JSONArray(this.mBundlesIndexString);
                if (jSONArray.length() > 0) {
                    int length = jSONArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject jSONObject = new JSONObject(jSONArray.getString(i));
                        if (jSONObject.has(a.d) && jSONObject.has("index")) {
                            String string = jSONObject.getString(a.d);
                            if (!TextUtils.isEmpty(string) && !this.mBundleList.contains(string)) {
                                this.mBundleList.add(string);
                            }
                            this.mBundlesIndex.put(string, Integer.valueOf(jSONObject.getInt("index")));
                        }
                    }
                }
            } catch (JSONException unused) {
            }
        }
    }

    public final void run(@NonNull IAjxContext iAjxContext, @NonNull JsRunInfo jsRunInfo, @NonNull JsContextObserver jsContextObserver) {
        checkEngine();
        jsRunInfo.checkUrl();
        int run = this.mJsEngine.run(iAjxContext.getJsContext(), jsRunInfo.getUrl(), jsRunInfo.getData(), jsRunInfo.getTag(), jsRunInfo.getRunWidth(), jsRunInfo.getRunHeight(), iAjxContext.getPatchIndex(AjxFileInfo.getBundleName(jsRunInfo.getUrl())), iAjxContext.getPatchIndexString(), jsContextObserver, jsRunInfo.getEnvironment(), jsRunInfo.getParentCtxID());
        if (run == 0) {
            iAjxContext.getJsContext().isRunOnUI();
            return;
        }
        destroyAjxContext(iAjxContext.getJsContext().shadow());
        try {
            Ajx instance = Ajx.getInstance();
            String url = jsRunInfo.getUrl();
            StringBuilder sb = new StringBuilder("run[");
            sb.append(jsRunInfo.getUrl());
            sb.append("] error! status:");
            sb.append(run);
            instance.onLineLog(url, "js_content_error", "B002", "p1", sb.toString());
        } catch (Exception unused) {
        }
    }

    public final void startService(@NonNull Context context, @NonNull String str, @NonNull String str2, Object obj, String str3) {
        checkEngine();
        String bundleName = AjxFileInfo.getBundleName(str2);
        int latestPatchIndex = AjxFileInfo.getLatestPatchIndex(bundleName);
        if (this.mBundleList.contains(bundleName)) {
            JsEngine jsEngine = this.mJsEngine;
            StringBuilder sb = new StringBuilder("path://");
            sb.append(bundleName);
            sb.append(File.separator);
            sb.append(JsEngineInstance.VALUE_PAGE_CONFIG_PATH_DEFAULT);
            jsEngine.setGlobalProperty(JsEngineInstance.KEY_PAGE_CONFIG_PATH, sb.toString());
        } else if (AjxFileInfo.isDebug) {
            String str4 = "path://ajx_page.txt";
            if (str2.startsWith("path://") && !TextUtils.isEmpty(bundleName)) {
                StringBuilder sb2 = new StringBuilder("path://");
                sb2.append(bundleName);
                sb2.append(File.separator);
                sb2.append(JsEngineInstance.VALUE_PAGE_CONFIG_PATH_DEFAULT);
                str4 = sb2.toString();
            }
            this.mJsEngine.setGlobalProperty(JsEngineInstance.KEY_PAGE_CONFIG_PATH, str4);
        } else {
            this.mJsEngine.setGlobalProperty(JsEngineInstance.KEY_PAGE_CONFIG_PATH, "path://ajx_page.txt");
        }
        this.mJsEngine.startService(str, str2, obj, latestPatchIndex, AjxFileInfo.getAllBundlesIndexSnapshot(), str3);
    }

    public final void stopService(@NonNull String str) {
        checkEngine();
        this.mJsEngine.stopService(str);
    }

    /* access modifiers changed from: 0000 */
    public final boolean registerModule(@NonNull Class... clsArr) throws Exception {
        if (!this.mModuleInitByFile) {
            for (Class registerModule : clsArr) {
                this.mModuleManager.registerModule(registerModule, true);
            }
        }
        return true;
    }

    public final void registerJsModule(@NonNull String str, @NonNull String str2, boolean z) {
        checkEngine();
        if (TextUtils.isEmpty(str)) {
            throw new IllegalEngineException((String) "Error: module name is null.");
        }
        this.mJsEngine.registerModule(str, str2, z);
    }

    public final Object onModuleCall(long j, String str, int i, Object... objArr) {
        try {
            IAjxContext ajxContext = getAjxContext(j);
            if (ajxContext == null) {
                return null;
            }
            return this.mModuleManager.onMethodCall(ajxContext, str, i, objArr);
        } catch (Exception e) {
            String stackTraceString = Log.getStackTraceString(e);
            if (stackTraceString.contains(EXCEPTION_KEYWORD)) {
                stackTraceString = stackTraceString.substring(stackTraceString.indexOf(EXCEPTION_KEYWORD) + 10);
            }
            StringBuilder sb = new StringBuilder("Exception: onModuleCall ");
            sb.append(str);
            sb.append("(methodId:");
            sb.append(i);
            sb.append(") ");
            sb.append(stackTraceString);
            LogHelper.loge(sb.toString());
            return null;
        }
    }

    public final Object onModuleGetter(long j, String str, int i) {
        try {
            IAjxContext ajxContext = getAjxContext(j);
            if (ajxContext == null) {
                return null;
            }
            return this.mModuleManager.onGetField(ajxContext, str, i);
        } catch (Exception e) {
            String stackTraceString = Log.getStackTraceString(e);
            if (stackTraceString.contains(EXCEPTION_KEYWORD)) {
                stackTraceString = stackTraceString.substring(stackTraceString.indexOf(EXCEPTION_KEYWORD) + 10);
            }
            StringBuilder sb = new StringBuilder("Exception: onModuleGetter ");
            sb.append(str);
            sb.append("(fieldId:");
            sb.append(i);
            sb.append(") ");
            sb.append(stackTraceString);
            LogHelper.loge(sb.toString());
            return null;
        }
    }

    public final void onModuleSetter(long j, String str, int i, Object obj) {
        try {
            IAjxContext ajxContext = getAjxContext(j);
            if (ajxContext != null) {
                this.mModuleManager.onSetField(ajxContext, str, i, obj);
            }
        } catch (Exception e) {
            String stackTraceString = Log.getStackTraceString(e);
            if (stackTraceString.contains(EXCEPTION_KEYWORD)) {
                stackTraceString = stackTraceString.substring(stackTraceString.indexOf(EXCEPTION_KEYWORD) + 10);
            }
            StringBuilder sb = new StringBuilder("Exception: onModuleSetter ");
            sb.append(str);
            sb.append("(fieldId:");
            sb.append(i);
            sb.append(") ");
            sb.append(stackTraceString);
            LogHelper.loge(sb.toString());
        }
    }

    public final IAjxImageLoader lookupLoader(@NonNull String str) {
        return this.mLoaderManager.lookupLoader(str);
    }

    /* access modifiers changed from: protected */
    public final void setGlobalProperty(@NonNull String str, String str2) {
        checkEngine();
        if (TextUtils.isEmpty(str)) {
            throw new IllegalEngineException((String) "Error: property key is null.");
        }
        this.mJsEngine.setGlobalProperty(str, str2);
    }

    /* access modifiers changed from: 0000 */
    public final Object getModuleIns(@NonNull IAjxContext iAjxContext, @NonNull String str) {
        try {
            return this.mModuleManager.getModuleIns(iAjxContext, str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final IAjxContext getAjxContext(long j) {
        return this.mAjxContextMap.get(Long.valueOf(j));
    }

    private void checkEngine() {
        if (this.mIsDestroy) {
            throw new IllegalEngineException((String) "engine destroy.");
        }
    }
}
