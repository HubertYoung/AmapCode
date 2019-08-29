package com.autonavi.minimap.ajx3.views;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.AEUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3Page.AjxPageResultExecutor;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.ajx3.JsRunInfo;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.loader.AjxPathLoader;
import com.autonavi.minimap.ajx3.loading.LoadingConfig;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleOS;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.upgrade.Ajx3ActionLogUtil;
import com.autonavi.minimap.ajx3.upgrade.Ajx3SpUtil;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;
import com.autonavi.minimap.ajx3.util.AjxDebugUtils;
import com.autonavi.minimap.ajx3.util.AjxPathList;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.OpenThirdAppUtil;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class AmapAjxView extends AjxView implements AmapAjxViewInterface {
    public static final int AJX_REQUEST_CODE = 99;
    private static final int MSG_CHECK_BLANK = 3441;
    private boolean isAjxResource;
    private AjxLifeCircleListener mAjxLifeCircleListener;
    private AjxPageResultExecutor mAjxPageResultExecutor;
    private AttributeListener mAjxViewAttributeListener;
    private AjxViewLayerListener mAjxViewLayerListener;
    private BackCallback mBackCallback;
    private long mCheckInterval;
    private ChildViewSHowListener mChildViewSHowListener;
    private final float mHalfScreenHeight;
    private Handler mHandler;
    private long mLoadJsTime;
    private Callback<AmapAjxView> mLoadingCallback;
    private Callback<AmapAjxView> mOnEndLoadCallback;
    private Callback<AmapAjxView> mOnUiCallback;
    private bid mPageContext;
    private String mSPM;
    private String mSPMUrl;

    public interface AjxLifeCircleListener {
        void onAjxContxtCreated(IAjxContext iAjxContext);

        void onJsBack(Object obj, String str);
    }

    public interface AjxViewLayerListener {
        void onAddLayer(String str, String str2, Object obj);

        void onRemoveLayer(String str);
    }

    public interface AttributeListener {
        boolean handleAttr(String str, Object obj);
    }

    public interface BackCallback {
        void back(Object obj, String str);
    }

    public interface ChildViewSHowListener {
        void onDrawChildView();
    }

    private boolean checkJSFileExist(String str) {
        return false;
    }

    public void attachPage(bid bid) {
        this.mPageContext = bid;
    }

    @Nullable
    public bid getPage() {
        return this.mPageContext;
    }

    public void setSPM(String str, String str2) {
        this.mSPMUrl = str;
        this.mSPM = str2;
    }

    private void tryLoadSPM(String str) {
        if (!TextUtils.equals(str, this.mSPMUrl)) {
            LoadingConfig loadingConfig = Ajx3Path.getLoadingConfig(getContext(), str);
            setSPM(str, loadingConfig != null ? loadingConfig.spm : null);
        }
    }

    private void notifyUTPageshow() {
        if (!TextUtils.isEmpty(this.mSPM)) {
            kd.a(this.mSPM, getUTPageObject());
        }
    }

    private void notifyUTPagehide() {
        if (!TextUtils.isEmpty(this.mSPM)) {
            kd.a(getUTPageObject());
        }
    }

    private Object getUTPageObject() {
        return this.mPageContext != null ? this.mPageContext : getContext();
    }

    public AmapAjxView(@NonNull Context context) {
        this(context, null);
    }

    public AmapAjxView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AmapAjxView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAjxResource = true;
        this.mCheckInterval = -1;
        this.mHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                if (message.what == AmapAjxView.MSG_CHECK_BLANK) {
                    AmapAjxView.this.checkBlank(0);
                }
            }
        };
        this.mHalfScreenHeight = DimensionUtils.pixelToStandardUnit((float) this.mScreenHeight) / 2.0f;
    }

    public void setAttributeListener(AttributeListener attributeListener) {
        this.mAjxViewAttributeListener = attributeListener;
    }

    public void setAjxLifeCircleListener(AjxLifeCircleListener ajxLifeCircleListener) {
        this.mAjxLifeCircleListener = ajxLifeCircleListener;
    }

    public void setAjxViewLayerListener(AjxViewLayerListener ajxViewLayerListener) {
        this.mAjxViewLayerListener = ajxViewLayerListener;
    }

    public void setBackCallBack(BackCallback backCallback) {
        this.mBackCallback = backCallback;
    }

    public void setOnChildViewSHowListener(ChildViewSHowListener childViewSHowListener) {
        this.mChildViewSHowListener = childViewSHowListener;
    }

    public void onAjxContextCreated(Callback<AmapAjxView> callback) {
        this.mOnEndLoadCallback = callback;
    }

    public void setOnUiLoadCallback(Callback<AmapAjxView> callback) {
        this.mOnUiCallback = callback;
    }

    public void setLoadingCallback(Callback<AmapAjxView> callback) {
        this.mLoadingCallback = callback;
    }

    public void setAjxFragmentResultExecutor(AjxPageResultExecutor ajxPageResultExecutor) {
        this.mAjxPageResultExecutor = ajxPageResultExecutor;
    }

    public AjxPageResultExecutor getAjxFragmentResultExecutor() {
        return this.mAjxPageResultExecutor;
    }

    public void loadJs(JsRunInfo jsRunInfo) {
        if (AjxFileInfo.initMode == AjxFileInfo.FILE_INIT_MODE || checkJSFileExist(jsRunInfo.getUrl())) {
            loadDirectly(jsRunInfo.getUrl(), jsRunInfo.getData(), jsRunInfo.getPageId(), jsRunInfo.getTag(), jsRunInfo.getWidth(), jsRunInfo.getHeight(), jsRunInfo.getEnvironment(), jsRunInfo.getParentCtxID(), jsRunInfo.getRunParams());
            return;
        }
        loadInternal(jsRunInfo.getUrl(), jsRunInfo.getData(), jsRunInfo.getPageId(), jsRunInfo.getTag(), jsRunInfo.getWidth(), jsRunInfo.getHeight(), jsRunInfo.getEnvironment(), jsRunInfo.getParentCtxID(), jsRunInfo.getRunParams());
    }

    public void load(@NonNull String str, @Nullable Object obj, String str2) {
        load(str, obj, null, str2, 0, 0, null, -1);
    }

    public void load(@NonNull String str, @Nullable Object obj, String str2, String str3) {
        load(str, obj, str2, str3, 0, 0, null, -1);
    }

    public void loadJsWithFullScreen(@NonNull String str, @Nullable Object obj, String str2, String str3) {
        int[] fullScreenSize = Ajx3Page.getFullScreenSize((Activity) getContext());
        load(str, obj, null, str2, fullScreenSize[0], fullScreenSize[1], str3, -1);
    }

    public void load(@NonNull String str, @Nullable Object obj, String str2, int i, int i2) {
        load(str, obj, null, str2, i, i2, null, -1);
    }

    public void load(@NonNull String str, @Nullable Object obj, String str2, String str3, int i, int i2, String str4) {
        load(str, obj, str2, str3, i, i2, str4, -1);
    }

    public void load(@NonNull String str, @Nullable Object obj, String str2, String str3, int i, int i2, String str4, long j) {
        if (AjxFileInfo.initMode == AjxFileInfo.FILE_INIT_MODE || checkJSFileExist(str)) {
            loadDirectly(str, obj, str2, str3, i, i2, str4, j, null);
        } else {
            loadInternal(str, obj, str2, str3, i, i2, str4, j, null);
        }
    }

    /* access modifiers changed from: protected */
    public void loadInternal(@NonNull String str, @Nullable Object obj, String str2, String str3, int i, int i2, String str4, long j, HashMap<String, Object> hashMap) {
        Object obj2;
        String str5 = str;
        Object obj3 = obj;
        String str6 = "";
        if (str5.contains("&")) {
            String[] split = str5.split("&");
            if (split.length == 2 && !TextUtils.isEmpty(split[0]) && !TextUtils.isEmpty(split[1]) && split[1].startsWith("__bv__=")) {
                str5 = split[0];
                str6 = split[1].substring(7);
            }
        }
        String transform = AjxPathList.transform(str5);
        if (obj3 instanceof String) {
            try {
                JSONObject jSONObject = new JSONObject((String) obj3);
                if (jSONObject.has("__webloader_bizcheck_finish__")) {
                    loadDirectly(transform, jSONObject.has("__webloader_bizrealpagedata__") ? jSONObject.get("__webloader_bizrealpagedata__") : "", jSONObject.optString("__webloader_bizrealpageid__", ""), str3, i, i2, str4, j, hashMap);
                    return;
                }
            } catch (Exception unused) {
            }
        } else if (obj3 instanceof JSONObject) {
            JSONObject jSONObject2 = (JSONObject) obj3;
            if (jSONObject2.has("__webloader_bizcheck_finish__")) {
                try {
                    obj2 = jSONObject2.has("__webloader_bizrealpagedata__") ? jSONObject2.get("__webloader_bizrealpagedata__") : "";
                } catch (Exception unused2) {
                    obj2 = obj3;
                }
                loadDirectly(transform, obj2, jSONObject2.optString("__webloader_bizrealpageid__", ""), str3, i, i2, str4, j, hashMap);
                return;
            }
        }
        Object obj4 = obj3;
        boolean checkUrlExist = checkUrlExist(transform);
        if (checkUrlExist && !TextUtils.isEmpty(str6)) {
            String bundleName = AjxFileInfo.getBundleName(transform);
            String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
            if (TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
                loadedDiffAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(bundleName);
            }
            if (!str6.equals(loadedDiffAjxFileVersion)) {
                checkUrlExist = false;
            }
        }
        if (checkUrlExist) {
            if (Ajx3UpgradeManager.getInstance().needCheckUpdate(AjxFileInfo.getBundleName(transform))) {
                checkUrlExist = false;
            }
        }
        if (!checkUrlExist) {
            JSONObject jSONObject3 = new JSONObject();
            try {
                jSONObject3.put("__webloader_bizcheck_finish__", true);
                jSONObject3.put("__webloader_bizrealpagedata__", obj4);
                jSONObject3.put("__webloader_bizrealpageid__", str2);
            } catch (JSONException unused3) {
            }
            JSONObject jSONObject4 = new JSONObject();
            try {
                jSONObject4.put("__webloader_bizpath__", transform);
                jSONObject4.put("__webloader_bizpagedata__", jSONObject3);
            } catch (JSONException unused4) {
            }
            loadDirectly(Ajx3Page.LOADING_JS_URL, jSONObject4.toString(), "web_loading_ajx", str3, i, i2, str4, j, hashMap);
            return;
        }
        loadDirectly(transform, obj4, str2, str3, i, i2, str4, j, hashMap);
    }

    public void loadDirectly(@NonNull String str, @Nullable Object obj, String str2, String str3, int i, int i2, String str4, long j) {
        loadDirectly(str, obj, str2, str3, i, i2, str4, j, null);
    }

    public void loadDirectly(@NonNull String str, @Nullable Object obj, String str2, String str3, int i, int i2, String str4, long j, HashMap<String, Object> hashMap) {
        String transform = AjxPathList.transform(str);
        Ajx3ActionLogUtil.actionLogAjx("B004", transform);
        JsRunInfo jsRunInfo = new JsRunInfo(transform, obj, i, i2);
        jsRunInfo.setPageId(str2);
        jsRunInfo.setTag(str3);
        jsRunInfo.setEnvironment(str4);
        jsRunInfo.setParentCtxID(j);
        jsRunInfo.setRunParams(hashMap);
        if (i == 0 || i2 == 0) {
            jsRunInfo.updateWidthAndHeight(getWidth(), getHeight());
        }
        load(jsRunInfo);
        tryLoadSPM(transform);
        askToCheck();
        this.mLoadJsTime = System.currentTimeMillis();
        Ajx3ActionLogUtil.actionLogOpenAjxPage(transform);
    }

    private void changeResource(JsRunInfo jsRunInfo) {
        String str;
        JsRunInfo jsRunInfo2 = jsRunInfo;
        AjxDebugUtils.changeResource(false);
        String url = jsRunInfo.getUrl();
        if (AEUtil.isAjx3Debug) {
            if (url.startsWith("file://")) {
                jsRunInfo2.setAjxPageConfigPath(Ajx3SpUtil.getJsAjxPageConfig(getContext(), url));
            } else if (url.startsWith(AjxPathLoader.DOMAIN) && AjxFileInfo.initMode == AjxFileInfo.FILE_INIT_MODE) {
                jsRunInfo2.setAjxPageConfigPath(Ajx3SpUtil.getJsAjxPageConfig(getContext(), url));
            }
        }
        if (!debugUrlExistCheck(url)) {
            String bundleName = AjxFileInfo.getBundleName(url);
            long currentTimeMillis = System.currentTimeMillis();
            String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(bundleName);
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            sb.append(" doesn't exist");
            str = url;
            LogManager.aLog(-1, 2, 4, currentTimeMillis, "生命周期", "", baseAjxFileVersion, url, sb.toString(), "", "", "");
            AjxDebugUtils.changeResource(true);
            if (!debugUrlExistCheck(str)) {
                AjxDebugUtils.changeResource(false);
                ToastHelper.showLongToast("sd卡及ajx中都未找到\n".concat(String.valueOf(str)));
            } else if (AjxDebugUtils.isChangeResToastOpen) {
                StringBuilder sb2 = new StringBuilder("sd卡中未找到\n");
                sb2.append(str);
                sb2.append(" \n当前加载ajx中的资源");
                ToastHelper.showLongToast(sb2.toString());
            }
        } else {
            str = url;
            if (AjxDebugUtils.isChangeResToastOpen) {
                ToastHelper.showLongToast("加载sd卡中的资源\n".concat(String.valueOf(str)));
            }
        }
        this.isAjxResource = AjxFileInfo.isReadFromAjxFile;
        AjxDebugUtils.sendDebugPageShowAction(str, this.isAjxResource);
        LogHelper.jniLogForce("AJX3-JS-LOG load:".concat(String.valueOf(str)));
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mAjxViewAttributeListener == null || !this.mAjxViewAttributeListener.handleAttr(str, obj)) {
            super.setAttribute(str, obj, z, z2, z3, z4);
        }
    }

    public <T> T getJsModule(String str) {
        IAjxContext ajxContext = getAjxContext();
        if (ajxContext == null) {
            return null;
        }
        try {
            return Ajx.getInstance().getModuleIns(ajxContext, str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onJsStartLoad(String str) {
        Ajx3ActionLogUtil.actionLogUrl("B014", str);
    }

    public float getHalfScreenHeight() {
        return this.mHalfScreenHeight;
    }

    public void onJsLoadEnd() {
        super.onJsLoadEnd();
        if (this.mOnEndLoadCallback != null) {
            this.mOnEndLoadCallback.callback(this);
        }
        if (this.mAjxLifeCircleListener != null) {
            this.mAjxLifeCircleListener.onAjxContxtCreated(getAjxContext());
        }
    }

    public void onJsUiLoad() {
        super.onJsUiLoad();
        if (this.mOnUiCallback != null) {
            this.mOnUiCallback.callback(this);
        }
    }

    public void onLoadingDismiss() {
        super.onLoadingDismiss();
        if (this.mLoadingCallback != null) {
            this.mLoadingCallback.callback(this);
        }
    }

    public void onOpen(String str, String str2, Object obj, String str3) {
        if (!TextUtils.isEmpty(str)) {
            ku a = ku.a();
            StringBuilder sb = new StringBuilder("onOpen url:");
            sb.append(str);
            sb.append("\ndata:");
            sb.append(obj);
            a.c("auiLog", sb.toString());
            if (str.startsWith("amapuri") || str.startsWith("androidamap")) {
                onCustomActon(str, obj);
            } else if (str.startsWith("appurl")) {
                OpenThirdAppUtil.openThirdApp((String) obj);
            } else {
                if (a.a.a.a(str)) {
                    Application application = AMapAppGlobal.getApplication();
                    Uri parse = Uri.parse(str);
                    boolean z = false;
                    if (!(parse == null || new Intent("android.intent.action.VIEW", parse).resolveActivity(application.getPackageManager()) == null)) {
                        z = true;
                    }
                    if (z) {
                        OpenThirdAppUtil.openOuterApp(str);
                        return;
                    }
                }
                bid bid = this.mPageContext;
                if (bid == null) {
                    bid = AMapPageUtil.getPageContext();
                }
                startPage(bid, str, str2, obj, str3);
            }
        }
    }

    public void onReplace(String str, String str2, Object obj, String str3) {
        Object obj2;
        super.onReplace(str, str2, obj, str3);
        if (!TextUtils.isEmpty(str)) {
            bid bid = this.mPageContext;
            if (bid == null) {
                bid = AMapPageUtil.getPageContext();
            }
            if (bid != null) {
                pageHide(false);
                ku a = ku.a();
                StringBuilder sb = new StringBuilder("onReplace url:");
                sb.append(str);
                sb.append("\ndata:");
                sb.append(obj);
                a.c("auiLog", sb.toString());
                if (str.startsWith("amapuri") || str.startsWith("androidamap")) {
                    bid.finish();
                    onCustomActon(str, obj);
                } else if (str.startsWith("appurl")) {
                    bid.finish();
                    OpenThirdAppUtil.openThirdApp((String) obj);
                } else {
                    if (Ajx3Page.LOADING_JS_URL.equals(getUrl())) {
                        bid.finish();
                        this.mPageContext = null;
                        ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
                        if (pagesStacks != null) {
                            int size = pagesStacks.size() - 2;
                            if (size >= 0) {
                                bid = AMapPageUtil.getStackFragment(size);
                            }
                            startPage(bid, str, str2, obj, str3);
                            return;
                        }
                    }
                    if (obj == null) {
                        obj2 = null;
                    } else {
                        obj2 = obj.toString();
                    }
                    load(str, obj2, str3, null);
                    pageShow(false, null);
                }
            }
        }
    }

    public void onChildViewShow() {
        if (this.mChildViewSHowListener != null) {
            this.mChildViewSHowListener.onDrawChildView();
        }
    }

    public void onBack(Object obj, String str) {
        if (this.mAjxLifeCircleListener != null) {
            this.mAjxLifeCircleListener.onJsBack(obj, str);
        }
        if (this.mBackCallback != null) {
            this.mBackCallback.back(obj, str);
        }
    }

    public void onResume(boolean z, Object obj) {
        super.onResume(z, obj);
        notifyUTPageshow();
    }

    public void onPause() {
        super.onPause();
        notifyUTPagehide();
    }

    public void onPause(boolean z) {
        super.onPause(z);
        notifyUTPagehide();
    }

    public void pageShow(boolean z, Object obj) {
        super.pageShow(z, obj);
        notifyUTPageshow();
    }

    public void pageHide(boolean z) {
        super.pageHide(z);
        notifyUTPagehide();
    }

    private void onCustomActon(@NonNull String str, @Nullable Object obj) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(str));
        intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
        if (obj != null) {
            intent.putExtra("ajxData", String.valueOf(obj));
        }
        DoNotUseTool.startScheme(intent);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0033 A[Catch:{ Exception -> 0x0046 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0066 A[SYNTHETIC, Splitter:B:24:0x0066] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x009f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void startPage(defpackage.bid r5, java.lang.String r6, java.lang.String r7, java.lang.Object r8, java.lang.String r9) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x0003
            return
        L_0x0003:
            r0 = 0
            if (r8 == 0) goto L_0x0045
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0045 }
            r2 = r8
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0045 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0045 }
            java.lang.String r2 = "isDialogPage"
            boolean r2 = r1.has(r2)     // Catch:{ Exception -> 0x0045 }
            if (r2 == 0) goto L_0x002a
            java.lang.String r2 = "isDialogPage"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Exception -> 0x0045 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0045 }
            if (r3 != 0) goto L_0x002a
            boolean r2 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r2)     // Catch:{ Exception -> 0x0045 }
            if (r2 == 0) goto L_0x002a
            r2 = 1
            goto L_0x002b
        L_0x002a:
            r2 = 0
        L_0x002b:
            java.lang.String r3 = "clearStack"
            boolean r3 = r1.has(r3)     // Catch:{ Exception -> 0x0046 }
            if (r3 == 0) goto L_0x0046
            java.lang.String r3 = "clearStack"
            java.lang.Object r1 = r1.get(r3)     // Catch:{ Exception -> 0x0046 }
            java.lang.String r3 = "1"
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x0046 }
            boolean r1 = r3.equalsIgnoreCase(r1)     // Catch:{ Exception -> 0x0046 }
            r0 = r1
            goto L_0x0046
        L_0x0045:
            r2 = 0
        L_0x0046:
            android.content.Context r1 = r4.getContext()
            com.autonavi.common.PageBundle r6 = com.autonavi.minimap.ajx3.util.AjxPageUtil.makePageBundle(r1, r6)
            java.lang.String r1 = "jsData"
            r6.putObject(r1, r8)
            java.lang.String r8 = "pageId"
            r6.putString(r8, r9)
            java.lang.String r8 = "env"
            r6.putString(r8, r7)
            java.lang.String r7 = "resultExecutor"
            com.autonavi.minimap.ajx3.Ajx3Page$AjxPageResultExecutor r8 = r4.mAjxPageResultExecutor
            r6.putObject(r7, r8)
            if (r0 == 0) goto L_0x0095
            esb r7 = defpackage.esb.a.a     // Catch:{ Exception -> 0x007f }
            java.lang.Class<apr> r8 = defpackage.apr.class
            esc r7 = r7.a(r8)     // Catch:{ Exception -> 0x007f }
            apr r7 = (defpackage.apr) r7     // Catch:{ Exception -> 0x007f }
            if (r7 == 0) goto L_0x007b
            bid r8 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()     // Catch:{ Exception -> 0x007f }
            r7.b(r8)     // Catch:{ Exception -> 0x007f }
        L_0x007b:
            com.autonavi.minimap.widget.ConfirmDlgLifeCircle.removeAll()     // Catch:{ Exception -> 0x007f }
            goto L_0x0095
        L_0x007f:
            r7 = move-exception
            java.lang.String r8 = "IntentController"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r7 = r7.getMessage()
            r9.append(r7)
            java.lang.String r7 = r9.toString()
            com.amap.bundle.logs.AMapLog.e(r8, r7)
        L_0x0095:
            r7 = 99
            if (r2 == 0) goto L_0x009f
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3DialogPage> r8 = com.autonavi.minimap.ajx3.Ajx3DialogPage.class
            r5.startPageForResult(r8, r6, r7)
            return
        L_0x009f:
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r8 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r5.startPageForResult(r8, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.AmapAjxView.startPage(bid, java.lang.String, java.lang.String, java.lang.Object, java.lang.String):void");
    }

    public void onRefresh(@Nullable String str) {
        reload();
    }

    public void startAnimation(Animation animation) {
        super.startAnimation(animation);
    }

    public void onDestroy() {
        checkBlank(1);
        setAjxLifeCircleListener(null);
        destroy();
        this.mPageContext = null;
        this.mAjxLifeCircleListener = null;
        this.mAjxViewAttributeListener = null;
        this.mBackCallback = null;
        this.mOnEndLoadCallback = null;
        this.mOnUiCallback = null;
        this.mAjxPageResultExecutor = null;
    }

    public void setSoftInputMode(int i) {
        bid bid = this.mPageContext;
        if (bid == null) {
            bid = AMapPageUtil.getPageContext();
        }
        if (bid instanceof AbstractBasePage) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) bid;
            boolean isAllowSetSoftInputMode = abstractBasePage.isAllowSetSoftInputMode();
            abstractBasePage.setAllowSetSoftMode(true);
            abstractBasePage.setSoftInputMode(i);
            abstractBasePage.setAllowSetSoftMode(isAllowSetSoftInputMode);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public void present(String str, int i, int i2, int i3, int i4) {
        AmapAjxView amapAjxView = new AmapAjxView(getContext());
        int i5 = i;
        int i6 = i2;
        LayoutParams layoutParams = new LayoutParams(i5, i6);
        layoutParams.topMargin = i4;
        layoutParams.leftMargin = i3;
        addView(amapAjxView, layoutParams);
        amapAjxView.load(str, null, "", "tag", i5, i6, null, getAjxContext().getJsContext().shadow());
        this.mSubAjxViewMap.put(amapAjxView.getAjxContext().getJsContext().shadow(), amapAjxView);
    }

    public void dismissSub(long j) {
        AjxView ajxView = (AjxView) this.mSubAjxViewMap.get(j);
        if (ajxView != null) {
            removeView(ajxView);
        }
    }

    public void onAddLayer(String str, String str2, Object obj) {
        if (this.mAjxViewLayerListener != null) {
            this.mAjxViewLayerListener.onAddLayer(str, str2, obj);
        }
    }

    public void onRemoveLayer(String str) {
        if (this.mAjxViewLayerListener != null) {
            this.mAjxViewLayerListener.onRemoveLayer(str);
        }
    }

    private boolean isBlank() {
        IAjxContext ajxContext = getAjxContext();
        if (ajxContext == null || ajxContext.hasDestroy()) {
            return false;
        }
        if (ajxContext.getDomTree() == null) {
            return true;
        }
        AjxDomNode rootNode = ajxContext.getDomTree().getRootNode();
        if (rootNode == null || rootNode.getChildren() == null || rootNode.getChildren().size() <= 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void checkBlank(int i) {
        this.mHandler.removeMessages(MSG_CHECK_BLANK);
        if (isBlank()) {
            Ajx3ActionLogUtil.actionLogBlankPage(getUrl(), i, System.currentTimeMillis() - this.mLoadJsTime, getCheckInterval());
        }
    }

    private void askToCheck() {
        this.mHandler.sendEmptyMessageDelayed(MSG_CHECK_BLANK, getCheckInterval());
    }

    private long getCheckInterval() {
        if (this.mCheckInterval < 0) {
            long j = 4;
            try {
                j = new JSONObject(lo.a().a((String) "blank_check_time")).optLong("blank_check_time", 4);
            } catch (Exception unused) {
            }
            this.mCheckInterval = j * 1000;
        }
        return this.mCheckInterval;
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        AjxModuleOS ajxModuleOS = (AjxModuleOS) getJsModule(AjxModuleOS.MODULE_NAME);
        if (ajxModuleOS != null) {
            ajxModuleOS.updateDisplayMetrics();
        }
        super.onSizeChanged(i, i2, i3, i4);
    }
}
