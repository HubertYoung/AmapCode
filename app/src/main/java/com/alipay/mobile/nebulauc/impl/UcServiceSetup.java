package com.alipay.mobile.nebulauc.impl;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.storage.sp.APSharedPreferences;
import com.alipay.android.phone.mobilesdk.storage.sp.SharedPreferencesManager;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.DeviceHWInfo;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.alipay.mobile.nebula.provider.H5GetOptionProvidedKeyProvider;
import com.alipay.mobile.nebula.provider.H5UcInitProvider;
import com.alipay.mobile.nebula.util.H5DeviceHelper;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.TestDataUtils;
import com.alipay.mobile.nebulauc.impl.setup.UcArSetup;
import com.alipay.mobile.nebulauc.impl.setup.UcNetworkSetup;
import com.alipay.mobile.nebulauc.impl.setup.UcOtherBizSetup;
import com.alipay.mobile.nebulauc.impl.setup.UcServiceWorkerSetup;
import com.alipay.mobile.nebulauc.impl.setup.UcSetupExceptionHelper;
import com.alipay.mobile.nebulauc.impl.setup.UcSetupTracing;
import com.alipay.mobile.nebulauc.impl.setup.UcVideoSetup;
import com.alipay.mobile.nebulauc.provider.UCServiceWorkerProvider;
import com.alipay.mobile.nebulauc.util.CommonUtil;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.nebulauc.util.ProcessLock;
import com.alipay.mobile.nebulaucsdk.UcSdkConstants;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.subpackage.TinyAppSubPackagePlugin;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import com.taobao.accs.common.Constants;
import com.taobao.applink.util.TBAppLinkUtil;
import com.uc.webview.export.Build;
import com.uc.webview.export.Build.Version;
import com.uc.webview.export.WebView;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.extension.UCSettings;
import com.uc.webview.export.utility.SetupTask;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.zip.ZipFile;

public class UcServiceSetup {
    private static final String INIT_CONFIG_NOTIFY_CORE_EVENT = "notifyCoreEvent";
    private static final String INIT_CONFIG_SET_GLOBAL_BOOL_VALUE = "setGlobalBoolValue";
    private static final String INIT_CONFIG_UPDATE_BUSSINESS_INFO = "updateBussinessInfo";
    /* access modifiers changed from: private */
    public static final boolean IS_DIFF_BUNDLE = UcSdkConstants.IS_DIFF_BUNDLE;
    static final String KEY_LAST_SUCCESS_ODEX_VERSION = "KEY_LAST_SUCCESS_ODEX_VERSION";
    static final String KEY_MAIN_UCODEX_INIT_SUCCESS = "KEY_MAIN_UCODEX_INIT_SUCCESS";
    private static final String TAG = "H5UcService";
    private static final String UC_CORE_VERSION_FOR_WOODPECKER = "ucCoreVersion";
    private static String m7zPath = "";
    static boolean s7zInited = false;
    static int sFallbackLimit = 3;
    private static String sInitUcFromSdcardPath = "";
    public static boolean sIsolateSpeedUp = false;
    private static boolean sOldDirCleared = false;
    private static OnConfigChangeListener sOnWebViewPoolChange = new OnConfigChangeListener() {
        public void onChange(String newValue) {
            UcServiceSetup.configureWebViewPool(newValue);
        }
    };
    private static boolean sOtherInited = false;
    static int sProcessMode = 0;
    public static int sRebindIsolateProcessTimeout = 8000;
    public static int sRenderProcessLaunchTimeout = 11000;
    static boolean sUcInited = false;
    static int sWebViewCreateDelay = 3000;
    static boolean sWebViewPoolKeep = false;
    static boolean sWebViewPoolReallyUse = false;
    static int sWebViewPoolSize = 1;

    private static class ShareCoreParam {
        public String mBak7zDirPathCd;
        public String mBakZipFilePathCd;
        public String mCopySdcardDirCd;
        public String mCopySubDirCd;
        public String mEnableCopyToSdcardCd;
        public String mHostUcmVersionsCd;
        public String mLoadPolicyCd;
        public String mM57Version;
        public String mPkgNames;
        public String mThirtyUcmVersionsCd;
        public String mZipFileCd;
        private final String sDefaultCopySubDirCd;
        private final String sDefaultEnableCopyToSdcardCd;
        private final String sDefaultHostUcmVersionsCd;
        private final String sDefaultLoadPolicyCd;
        private final String sDefaultPkgNames;

        private ShareCoreParam() {
            this.sDefaultCopySubDirCd = "/alipay/com.eg.android.AlipayGphone/";
            this.sDefaultEnableCopyToSdcardCd = "true";
            this.sDefaultHostUcmVersionsCd = "2.13.1.*";
            this.sDefaultPkgNames = TBAppLinkUtil.TAOPACKAGENAME;
            this.sDefaultLoadPolicyCd = "";
        }

        public void setDefaultParamIfNeed() {
            if (TextUtils.isEmpty(this.mCopySdcardDirCd)) {
                this.mCopySdcardDirCd = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), TextUtils.isEmpty(this.mCopySubDirCd) ? "/alipay/com.eg.android.AlipayGphone/" : this.mCopySubDirCd).getAbsolutePath();
            }
            if (TextUtils.isEmpty(this.mEnableCopyToSdcardCd)) {
                this.mEnableCopyToSdcardCd = "true";
            }
            if (TextUtils.isEmpty(this.mHostUcmVersionsCd)) {
                this.mHostUcmVersionsCd = "2.13.1.*";
            }
            if (TextUtils.isEmpty(this.mPkgNames)) {
                this.mPkgNames = TBAppLinkUtil.TAOPACKAGENAME;
            }
            if (TextUtils.isEmpty(this.mLoadPolicyCd)) {
                this.mLoadPolicyCd = "";
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:63:0x0209  */
        /* JADX WARNING: Removed duplicated region for block: B:72:0x024b  */
        /* JADX WARNING: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void setParam(com.uc.webview.export.utility.SetupTask r12, java.lang.String r13, java.lang.String r14) {
            /*
                r11 = this;
                r6 = 1
                r7 = 0
                java.lang.String r8 = "H5UcService"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r10 = "setParam ("
                java.lang.StringBuilder r9 = r9.append(r10)
                java.lang.StringBuilder r9 = r9.append(r13)
                java.lang.String r10 = ", "
                java.lang.StringBuilder r9 = r9.append(r10)
                java.lang.StringBuilder r9 = r9.append(r14)
                java.lang.String r10 = ",)"
                java.lang.StringBuilder r9 = r9.append(r10)
                java.lang.String r9 = r9.toString()
                com.alipay.mobile.nebula.util.H5Log.d(r8, r9)
                r3 = 0
                r0 = 0
                java.lang.String r8 = r11.mM57Version     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 == 0) goto L_0x005a
                java.lang.String r7 = "H5UcService"
                java.lang.String r8 = "setParam mM57Version is empty"
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ Throwable -> 0x0252 }
                r3 = 1
                java.lang.String r7 = "H5UcService"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "needSetPreVersionOption : "
                java.lang.StringBuilder r8 = r8.append(r9)
                java.lang.StringBuilder r8 = r8.append(r3)
                java.lang.String r8 = r8.toString()
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)
                if (r3 == 0) goto L_0x0059
                r12.setup(r13, r14)
            L_0x0059:
                return
            L_0x005a:
                r11.setDefaultParamIfNeed()     // Catch:{ Throwable -> 0x0252 }
                java.lang.String r8 = r11.mCopySdcardDirCd     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 != 0) goto L_0x01be
                java.lang.String r8 = r11.mHostUcmVersionsCd     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 != 0) goto L_0x01be
                java.lang.String r8 = r11.mZipFileCd     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 != 0) goto L_0x01be
                java.lang.String r8 = r11.mEnableCopyToSdcardCd     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 != 0) goto L_0x01be
                r5 = r6
            L_0x007e:
                java.lang.String r8 = r11.mLoadPolicyCd     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 != 0) goto L_0x01c1
                java.lang.String r8 = r11.mCopySdcardDirCd     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 != 0) goto L_0x01c1
                java.lang.String r8 = r11.mThirtyUcmVersionsCd     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 != 0) goto L_0x01c1
                java.lang.String r8 = r11.mBak7zDirPathCd     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 == 0) goto L_0x00a6
                java.lang.String r8 = r11.mBakZipFilePathCd     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 != 0) goto L_0x01c1
            L_0x00a6:
                java.lang.String r8 = r11.mPkgNames     // Catch:{ Throwable -> 0x0252 }
                boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0252 }
                if (r8 != 0) goto L_0x01c1
            L_0x00ae:
                java.lang.String r7 = "H5UcService"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0252 }
                r8.<init>()     // Catch:{ Throwable -> 0x0252 }
                java.lang.String r9 = "validCopyToSdcardUCParam: "
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0252 }
                java.lang.StringBuilder r8 = r8.append(r5)     // Catch:{ Throwable -> 0x0252 }
                java.lang.String r9 = " validInitFromSdcardUCParam: "
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0252 }
                java.lang.StringBuilder r8 = r8.append(r6)     // Catch:{ Throwable -> 0x0252 }
                java.lang.String r9 = " isM57Version: "
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0252 }
                java.lang.String r9 = r11.mM57Version     // Catch:{ Throwable -> 0x0252 }
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0252 }
                java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0252 }
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ Throwable -> 0x0252 }
                com.alibaba.fastjson.JSONObject r1 = new com.alibaba.fastjson.JSONObject     // Catch:{ Throwable -> 0x0252 }
                r1.<init>()     // Catch:{ Throwable -> 0x0252 }
                java.lang.String r7 = r11.mM57Version     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                boolean r7 = r7.booleanValue()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                if (r7 == 0) goto L_0x010b
                if (r5 == 0) goto L_0x010b
                java.lang.String r7 = "sc_cpy"
                java.lang.String r8 = r11.mEnableCopyToSdcardCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r7 = "sc_ta_fp"
                java.lang.String r8 = r11.mCopySdcardDirCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r7 = "sc_hucmv"
                java.lang.String r8 = r11.mHostUcmVersionsCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r7 = "sc_cd_fp"
                java.lang.String r8 = r11.mZipFileCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
            L_0x010b:
                java.lang.String r7 = "H5UcService"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r8.<init>()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r9 = "validCopyToSdcardUCParam: "
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r9 = r1.toString()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r7 = r11.mM57Version     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                boolean r7 = r7.booleanValue()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                if (r7 != 0) goto L_0x020e
                if (r6 == 0) goto L_0x020e
                java.lang.String r7 = "sc_ta_fp"
                java.lang.String r8 = r11.mCopySdcardDirCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r7 = "sc_ldpl"
                java.lang.String r8 = r11.mLoadPolicyCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r7 = "sc_taucmv"
                java.lang.String r8 = r11.mThirtyUcmVersionsCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r7 = "sc_pkgl"
                java.lang.String r8 = r11.mPkgNames     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r7 = r11.mBak7zDirPathCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                if (r7 != 0) goto L_0x01c4
                java.lang.String r7 = "sc_bakkrldir"
                java.lang.String r8 = r11.mBak7zDirPathCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
            L_0x0160:
                java.lang.String r7 = "H5UcService"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r8.<init>()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r9 = "validInitFromSdcardUCParam: "
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r9 = r1.toString()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
            L_0x017c:
                if (r1 == 0) goto L_0x022d
                int r7 = r1.size()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                if (r7 <= 0) goto L_0x022d
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r7.<init>()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r8 = "JSON_CMD"
                java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r8 = r1.toString()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r2 = r7.toString()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                com.uc.webview.export.extension.UCCore.setParam(r2)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
            L_0x019e:
                java.lang.String r7 = "H5UcService"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "needSetPreVersionOption : "
                java.lang.StringBuilder r8 = r8.append(r9)
                java.lang.StringBuilder r8 = r8.append(r3)
                java.lang.String r8 = r8.toString()
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)
                if (r3 == 0) goto L_0x0254
                r12.setup(r13, r14)
                r0 = r1
                goto L_0x0059
            L_0x01be:
                r5 = r7
                goto L_0x007e
            L_0x01c1:
                r6 = r7
                goto L_0x00ae
            L_0x01c4:
                java.lang.String r7 = r11.mBakZipFilePathCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                if (r7 != 0) goto L_0x0160
                java.lang.String r7 = "sc_bakzipfp"
                java.lang.String r8 = r11.mBakZipFilePathCd     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r1.put(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                goto L_0x0160
            L_0x01d4:
                r4 = move-exception
                r0 = r1
            L_0x01d6:
                java.lang.String r7 = "H5UcService"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0230 }
                r8.<init>()     // Catch:{ all -> 0x0230 }
                java.lang.String r9 = "failed to setup uc param"
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0230 }
                java.lang.StringBuilder r8 = r8.append(r4)     // Catch:{ all -> 0x0230 }
                java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0230 }
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ all -> 0x0230 }
                r3 = 1
                java.lang.String r7 = "H5UcService"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "needSetPreVersionOption : "
                java.lang.StringBuilder r8 = r8.append(r9)
                java.lang.StringBuilder r8 = r8.append(r3)
                java.lang.String r8 = r8.toString()
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)
                if (r3 == 0) goto L_0x0059
                r12.setup(r13, r14)
                goto L_0x0059
            L_0x020e:
                java.lang.String r7 = "H5UcService"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r8.<init>()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.StringBuilder r8 = r8.append(r13)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r9 = ": "
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.StringBuilder r8 = r8.append(r14)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ Throwable -> 0x01d4, all -> 0x024f }
                r3 = 1
                goto L_0x017c
            L_0x022d:
                r3 = 1
                goto L_0x019e
            L_0x0230:
                r7 = move-exception
            L_0x0231:
                java.lang.String r8 = "H5UcService"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r10 = "needSetPreVersionOption : "
                java.lang.StringBuilder r9 = r9.append(r10)
                java.lang.StringBuilder r9 = r9.append(r3)
                java.lang.String r9 = r9.toString()
                com.alipay.mobile.nebula.util.H5Log.d(r8, r9)
                if (r3 == 0) goto L_0x024e
                r12.setup(r13, r14)
            L_0x024e:
                throw r7
            L_0x024f:
                r7 = move-exception
                r0 = r1
                goto L_0x0231
            L_0x0252:
                r4 = move-exception
                goto L_0x01d6
            L_0x0254:
                r0 = r1
                goto L_0x0059
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulauc.impl.UcServiceSetup.ShareCoreParam.setParam(com.uc.webview.export.utility.SetupTask, java.lang.String, java.lang.String):void");
        }
    }

    static synchronized boolean init(boolean enableHA) {
        boolean z = true;
        synchronized (UcServiceSetup.class) {
            try {
                H5UcInitProvider h5UcInitProvider = (H5UcInitProvider) H5Utils.getProvider(H5UcInitProvider.class.getName());
                if (h5UcInitProvider != null) {
                    z = h5UcInitProvider.initUC();
                    H5Trace.sessionEnd("UcServiceSetup", null, new String[0]);
                } else {
                    H5Trace.sessionBegin("UcServiceSetup", null, new String[0]);
                    H5Log.d(TAG, "s7zInited " + s7zInited + " sUcInited:" + sUcInited);
                    commonSetBeforeInit();
                    if (!IS_DIFF_BUNDLE) {
                        init7zSo();
                    } else {
                        H5Log.d(TAG, "[bundle diff] unzip 7z so by uc core");
                    }
                    initCore(enableHA);
                    if (H5Utils.isMainProcess()) {
                        cleanOldFilesIfNeed();
                    }
                    boolean isForeground = CommonUtil.isForeground();
                    UcSetupTracing.addCommonInfo("fgbg_end", "fg_" + isForeground);
                    H5Log.d(TAG, "init end in foreground: " + isForeground);
                    H5Trace.sessionEnd("UcServiceSetup", null, new String[0]);
                    if (!sOtherInited) {
                        sOtherInited = true;
                        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                        if (configProvider != null) {
                            configureWebViewPool(configProvider.getConfigWithNotifyChange("h5_enableWebViewPool", sOnWebViewPoolChange));
                            H5Log.e((String) TAG, (String) "h5ConfigProvider == null");
                        }
                    }
                }
            } catch (Throwable th) {
                H5Trace.sessionEnd("UcServiceSetup", null, new String[0]);
                throw th;
            }
        }
        return z;
    }

    private static void commonSetBeforeInit() {
        NativeCrashHandlerApi.addCrashHeadInfo("cpuHardware", H5DeviceHelper.getCpuHardware());
        String ucCoreVersion = Version.NAME + "_" + Build.CORE_TIME;
        NativeCrashHandlerApi.addCrashHeadInfo(UC_CORE_VERSION_FOR_WOODPECKER, ucCoreVersion);
        H5DevConfig.setStringConfig(UC_CORE_VERSION_FOR_WOODPECKER, ucCoreVersion);
        H5Log.d(TAG, "setUCCoreVersion: " + ucCoreVersion);
        boolean isForeground = CommonUtil.isForeground();
        UcSetupTracing.addCommonInfo("fgbg_start", "fg_" + isForeground);
        H5Log.d(TAG, "init start in foreground: " + isForeground);
    }

    /* access modifiers changed from: private */
    public static void configureWebViewPool(String value) {
        JSONObject jsonObject = H5Utils.parseObject(value);
        if (jsonObject != null) {
            Boolean enable = jsonObject.getBoolean("enable");
            if (enable == null || !enable.booleanValue()) {
                sWebViewPoolSize = 0;
                return;
            }
            int poolSize = H5Utils.getInt(jsonObject, (String) "size", sWebViewPoolSize);
            if (poolSize > 4 || poolSize <= 0) {
                sWebViewPoolSize = 2;
            } else {
                sWebViewPoolSize = poolSize;
            }
            int delay = H5Utils.getInt(jsonObject, (String) "delay", sWebViewCreateDelay);
            if (delay > 0) {
                sWebViewCreateDelay = delay;
            }
            sWebViewPoolReallyUse = H5Utils.getBoolean(jsonObject, (String) "use", sWebViewPoolReallyUse);
            if (H5Utils.isInTinyProcess()) {
                sWebViewPoolKeep = true;
            }
            sWebViewPoolKeep = H5Utils.getBoolean(jsonObject, (String) "keep", sWebViewPoolKeep);
        }
    }

    private static void initCore(boolean enableHA) {
        int VERIFY_POLICY;
        int DELETE_CORE_POLICY;
        String soPathKey;
        String soPathValue;
        String string;
        if (!sUcInited) {
            UcSetupTracing.beginTrace("initCore");
            UcSetupTracing.setTrace("initCoreStart");
            UcSetupTracing.beginTrace("initCore2success");
            H5Trace.sessionBegin("initCore", null, new String[0]);
            H5Log.d(TAG, "initCore " + UcSdkConstants.UC_VERSION + ", enableHA " + enableHA);
            sUcInited = true;
            long initCoreTime = System.currentTimeMillis();
            final Context context = H5Utils.getContext();
            dumpUCMSDKIfNeed(context);
            boolean isInTinyProcess = H5Utils.isInTinyProcess();
            if (!H5Utils.isDebuggable(context) || (!TextUtils.isEmpty(sInitUcFromSdcardPath) && !H5DevConfig.getBooleanConfig("h5_disable_uc_init_verify", false))) {
                VERIFY_POLICY = 31;
                if (isInTinyProcess && "YES".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_ucSetupVerifyAsync"))) {
                    VERIFY_POLICY = 31 | Integer.MIN_VALUE;
                }
                DELETE_CORE_POLICY = 63;
            } else {
                VERIFY_POLICY = 0;
                DELETE_CORE_POLICY = 0;
            }
            String pid = null;
            if (isInTinyProcess) {
                if (TextUtils.equals("YES", H5ConfigUtil.getConfig("h5_tinyUseVerifyPolicyNone"))) {
                    VERIFY_POLICY = 0;
                    H5Log.d(TAG, "in tiny use VERIFY_POLICY_NONE");
                }
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    pid = h5EventHandlerService.getLitePid() + "";
                } else {
                    H5Log.w(TAG, "h5EventHandlerService == null");
                }
            }
            UcSetupTracing.beginTrace("initCore_setupTask");
            if (IS_DIFF_BUNDLE) {
                File zipFile = getZipSoFromDiffBundle(context);
                soPathKey = UCCore.OPTION_UCM_ZIP_FILE;
                soPathValue = zipFile.getPath();
                H5Log.d(TAG, "[bundle diff] got zip file: " + soPathValue + " exists: " + zipFile.exists());
            } else {
                File rootDir = context.getDir("h5container", 0);
                File file = new File(rootDir, "uc/" + UcSdkConstants.UC_VERSION + "/so");
                soPathKey = UCCore.OPTION_DEX_FILE_PATH;
                soPathValue = file.getAbsolutePath();
                H5Log.d(TAG, "got so unzip dir: " + soPathValue + " exists: " + file.exists());
                H5Log.d(TAG, "rootDir canonicalPath: " + rootDir.getCanonicalPath());
                H5Log.d(TAG, "rootDir absolutePath: " + rootDir.getAbsolutePath());
                H5Log.d(TAG, "OPTION_DEX_FILE_PATH soPath: " + soPathValue);
            }
            UcSetupTracing.addCommonInfo("soPath", soPathValue);
            H5Log.d(TAG, "pid " + pid + ", subprocess " + isInTinyProcess);
            SetupTask setupTask = (SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) UCCore.setup(UCCore.OPTION_CONTEXT, context).setup((String) UCCore.OPTION_MULTI_CORE_TYPE, (Object) Boolean.valueOf(true))).setup((String) UCCore.OPTION_USE_SYSTEM_WEBVIEW, (Object) "false")).setup((String) UCCore.OPTION_LOAD_POLICY, (Object) UCCore.LOAD_POLICY_SPECIFIED_ONLY)).setup((String) UCCore.OPTION_PRIVATE_DATA_DIRECTORY_SUFFIX, (Object) pid);
            String str = enableHA ? "true" : "false";
            r1 = UCCore.OPTION_HARDWARE_ACCELERATED;
            AnonymousClass2 r0 = new ValueCallback<SetupTask>() {
                public void onReceiveValue(SetupTask setupTask) {
                    UcSetupTracing.beginTrace("setupTaskSuccess");
                    H5Log.d(UcServiceSetup.TAG, "uc kernel init success, coreType : " + WebView.getCoreType());
                    H5Flag.ucReady = true;
                    H5Flag.initUcNormal = true;
                    boolean isForeground = CommonUtil.isForeground();
                    UcSetupTracing.addCommonInfo("fgbg_success", "fg_" + isForeground);
                    H5Log.d(UcServiceSetup.TAG, "init success in foreground: " + isForeground);
                    String lastSuccessVersion = H5DevConfig.getStringConfig(UcServiceSetup.KEY_LAST_SUCCESS_ODEX_VERSION, null);
                    if (H5Utils.isMainProcess() && ((!UcSdkConstants.UC_VERSION.equalsIgnoreCase(lastSuccessVersion) || !H5DevConfig.getBooleanConfig(UcServiceSetup.KEY_MAIN_UCODEX_INIT_SUCCESS, false)) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("h5_notifyUcInitSuccess")))) {
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                try {
                                    H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                                    if (h5EventHandlerService != null) {
                                        h5EventHandlerService.notifyUcInitSuccess();
                                    }
                                } catch (Throwable th) {
                                    H5Log.d(UcServiceSetup.TAG, "notifyUcInitSuccess error!");
                                }
                            }
                        }, 2000);
                    }
                    H5DevConfig.setStringConfig(UcServiceSetup.KEY_LAST_SUCCESS_ODEX_VERSION, UcSdkConstants.UC_VERSION);
                    if (H5Utils.isMainProcess()) {
                        H5DevConfig.setBooleanConfig(UcServiceSetup.KEY_MAIN_UCODEX_INIT_SUCCESS, true);
                    }
                    if (!H5Utils.isMainProcess() || UcServiceSetup.sProcessMode == 0) {
                        UCWebView.preCreateOnMainWithDelay(0);
                    } else {
                        UCWebView.preCreateForMultiProcess();
                    }
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            UcServiceSetup.dumpUCMSDKIfNeed(context);
                            if (!UcNetworkSetup.useNewInitTiming()) {
                                UcNetworkSetup.initNetworkConfig();
                                UcServiceWorkerSetup.initServiceWorkerCallback();
                                UcServiceWorkerSetup.initServiceWorkerController();
                                UcNetworkSetup.clearUcHttpCache();
                            }
                        }
                    });
                    UcSetupTracing.endTrace("setupTaskSuccess");
                    UcSetupTracing.endTrace("initCore2success");
                    UcSetupTracing.setTrace("initCoreEnd");
                }
            };
            SetupTask setupTask2 = (SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) setupTask.setup((String) UCCore.OPTION_HARDWARE_ACCELERATED, (Object) str)).setup((String) UCCore.OPTION_VERIFY_POLICY, (Object) Integer.valueOf(VERIFY_POLICY))).setup((String) UCCore.OPTION_DELETE_CORE_POLICY, (Object) Integer.valueOf(DELETE_CORE_POLICY))).setup((String) UCCore.OPTION_INIT_IN_SETUP_THREAD, (Object) Boolean.valueOf(true))).setup((String) UCCore.OPTION_PROVIDED_KEYS, (Object) getOptionProviderKey())).setup((String) UCCore.OPTION_DISTINGUISH_JS_ERROR, (Object) Boolean.valueOf(true))).onEvent((String) LogCategory.CATEGORY_EXCEPTION, (ValueCallback<CALLBACK_TYPE>) new ValueCallback<SetupTask>() {
                public void onReceiveValue(SetupTask value) {
                    Throwable t = null;
                    if (value != null) {
                        t = value.getException();
                        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_UC_INIT_EXCEPTION").param1().add(Version.NAME, null).param2().add("ext0", CommonUtil.stringify(t)));
                    }
                    H5Log.e(UcServiceSetup.TAG, "uc kernel init exception", t);
                    H5Flag.ucReady = false;
                    H5Flag.initUcNormal = false;
                    UcServiceSetup.sUcInited = false;
                }
            })).onEvent((String) "success", (ValueCallback<CALLBACK_TYPE>) r0);
            JSONObject jsonObjShareCore = H5ConfigUtil.getConfigJSONObject("h5_ucShareCoreConfig");
            if ("yes".equalsIgnoreCase(H5Utils.getString(jsonObjShareCore, (String) "enable", (String) ""))) {
                ShareCoreParam shareCoreParam = new ShareCoreParam();
                shareCoreParam.mCopySubDirCd = "/alipay/com.eg.android.AlipayGphone/";
                shareCoreParam.mEnableCopyToSdcardCd = H5Utils.getString(jsonObjShareCore, (String) "enableCopyToSDCard", (String) "");
                shareCoreParam.mHostUcmVersionsCd = H5Utils.getString(jsonObjShareCore, (String) "hostUcmVersion", (String) "");
                if (IS_DIFF_BUNDLE) {
                    shareCoreParam.mZipFileCd = soPathValue;
                    shareCoreParam.mBakZipFilePathCd = soPathValue;
                } else {
                    shareCoreParam.mZipFileCd = TextUtils.isEmpty(m7zPath) ? getZipPathFromLibDir(context) : m7zPath;
                    shareCoreParam.mBak7zDirPathCd = soPathValue;
                }
                shareCoreParam.mLoadPolicyCd = H5Utils.getString(jsonObjShareCore, (String) UCCore.OPTION_LOAD_POLICY, (String) "");
                shareCoreParam.mThirtyUcmVersionsCd = H5Utils.getString(jsonObjShareCore, (String) "thirtyUcmVersion", (String) "");
                shareCoreParam.mPkgNames = H5Utils.getString(jsonObjShareCore, (String) "pkgNames", (String) "");
                shareCoreParam.mM57Version = "true";
                H5Log.d(TAG, "ShareCore Infomation: CopySubDirCd=" + shareCoreParam.mCopySubDirCd + "^EnableCopyToSdcardCd=" + shareCoreParam.mEnableCopyToSdcardCd + "^HostUcmVersionsCd=" + shareCoreParam.mHostUcmVersionsCd + "^isDiffBundle=" + IS_DIFF_BUNDLE + "^ZipFileCd=" + shareCoreParam.mZipFileCd + "^Bak7zDirPathCd=" + shareCoreParam.mBak7zDirPathCd + "^BakZipFilePathCd=" + shareCoreParam.mBakZipFilePathCd + "^LoadPolicyCd=" + shareCoreParam.mLoadPolicyCd + "^ThirtyUcmVersionsCd=" + shareCoreParam.mThirtyUcmVersionsCd + "^PkgNames=" + shareCoreParam.mPkgNames + "^M57Version =" + shareCoreParam.mM57Version);
                shareCoreParam.setParam(setupTask2, soPathKey, soPathValue);
            } else {
                setupTask2.setup(soPathKey, (Object) soPathValue);
            }
            JSONObject jsonObjApollo = H5ConfigUtil.getConfigJSONObject("h5_ucApolloConfig");
            boolean useApollo = false;
            boolean downloadApolloIn4G = true;
            boolean downloadApolloInLiteProcess = false;
            if (jsonObjApollo != null) {
                useApollo = "YES".equals(jsonObjApollo.getString("useApollo"));
                downloadApolloIn4G = "YES".equals(jsonObjApollo.getString("downloadApolloIn4G"));
                downloadApolloInLiteProcess = "YES".equalsIgnoreCase(jsonObjApollo.getString("downloadApolloInLiteProcess"));
            }
            H5Log.d(TAG, "useApollo " + useApollo);
            if (!useApollo) {
                setupTask2.setup((String) UCCore.OPTION_USE_UC_PLAYER, (Object) Boolean.valueOf(false));
            }
            setupTask2.setup((String) UCCore.OPTION_LOG_CONFIG, (Object) initLogConfig());
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_disable_multi_unknown_crash"))) {
                setupTask2.setup((String) UCCore.OPTION_MULTI_UNKNOWN_CRASH_DISABLE, (Object) Boolean.valueOf(true));
            }
            if (H5Utils.isMainProcess()) {
                JSONObject multiProcessConfig = H5ConfigUtil.getConfigJSONObject("h5_ucCoreMultiProcess");
                sProcessMode = getMultiProcessMode(multiProcessConfig, context);
                H5Log.d(TAG, "setup multi process mode: " + sProcessMode);
                if (sProcessMode == 2) {
                    sIsolateSpeedUp = H5Utils.getBoolean(multiProcessConfig, (String) "isolateSpeedUp", false);
                    setupTask2.setup((String) UCCore.OPTION_WEBVIEW_MULTI_PROCESS_ENABLE_SERVICE_SPEEDUP, (Object) Boolean.valueOf(sIsolateSpeedUp));
                    setupTask2.setup((String) UCCore.OPTION_WEBVIEW_MULTI_PROCESS_FALLBACK_TIMEOUT, (Object) Integer.valueOf(sRebindIsolateProcessTimeout));
                }
                setupTask2.setup((String) UCCore.OPTION_WEBVIEW_MULTI_PROCESS, (Object) Integer.valueOf(sProcessMode));
            }
            JSONObject commonSetupConfig = H5Utils.parseObject(H5ConfigUtil.getConfig("h5_ucCommonSetupConfig"));
            if (commonSetupConfig != null) {
                for (String key : commonSetupConfig.keySet()) {
                    setupTask2.setup(UcSetupExceptionHelper.setupKeyMap.get(key), commonSetupConfig.get(key));
                }
            }
            setupTask2.start();
            UcSetupTracing.endTrace("initCore_setupTask");
            H5Log.d(TAG, "setUpTask cost:" + (System.currentTimeMillis() - initCoreTime));
            try {
                if (!H5Utils.isMainProcess() && context.getPackageManager().getPackageInfo(context.getPackageName(), 128).applicationInfo.metaData.getBoolean("perf_test")) {
                    UcDebugLogger.init();
                }
            } catch (Throwable th) {
            }
            if (!UcNetworkSetup.useNewInitTiming()) {
                if (!UcArSetup.disableAR()) {
                    UcArSetup.init();
                }
                if (jsonObjApollo == null) {
                    string = null;
                } else {
                    string = jsonObjApollo.getString(TinyAppSubPackagePlugin.DOWNLOAD_URL);
                }
                UcVideoSetup.initVideoControl(context, useApollo, string, downloadApolloIn4G, downloadApolloInLiteProcess);
                UcOtherBizSetup.init();
            }
            UCSettings.setEnableCustomErrorPage(true);
            UCSettings.setEnableAllResourceCallBack(true);
            UCSettings.setEnableAdblock(false);
            UCSettings.setGlobalEnableUCProxy(false);
            UCSettings.setEnableDispatcher(false);
            UCSettings.setEnableMultiThreadParser(false);
            UCSettings.setEnableMediaCache(true);
            if ("YES".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_openUCCacheLog"))) {
                UCSettings.setGlobalBoolValue("OPEN_CACHE_LOG", true);
            }
            UCSettings.setGlobalBoolValue("DISABLE_PREFER_CACHE", true);
            UCSettings.setForceUserScalable(2);
            JSONArray disableACCanvasArray = H5Utils.parseArray(H5ConfigUtil.getConfig("h5_ucDisableACCanvas"));
            if (disableACCanvasArray != null && !disableACCanvasArray.isEmpty()) {
                String phoneInfo = android.os.Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + android.os.Build.MODEL + MetaRecord.LOG_SEPARATOR + VERSION.SDK_INT;
                H5Log.d(TAG, "phoneInfo " + phoneInfo);
                int i = 0;
                while (true) {
                    if (i >= disableACCanvasArray.size()) {
                        break;
                    } else if (TextUtils.equals(phoneInfo, disableACCanvasArray.getString(i))) {
                        H5Log.d(TAG, "DisableACCanvas");
                        UCSettings.setGlobalBoolValue(UCSettings.KEY_DISABLE_ACCELERATE_CANVAS, true);
                        break;
                    } else {
                        i++;
                    }
                }
            }
            long initCoreAllTime = System.currentTimeMillis() - initCoreTime;
            H5Log.d(TAG, "initCore elapse " + initCoreAllTime);
            if (H5Utils.isDebuggable(H5Utils.getContext())) {
                TestDataUtils.storeJSParams("ucInit|initCore", Long.valueOf(initCoreAllTime));
                TestDataUtils.storeJSParams("ucInit|coreVersion", UcSdkConstants.UC_VERSION);
            }
            if (H5Utils.isInTinyProcess()) {
                if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("H5_clean_sw_cache"))) {
                    long time = System.currentTimeMillis();
                    new UCServiceWorkerProvider().clearServiceWorker("");
                    H5Log.d(TAG, "clearServiceWorker cache cost " + (System.currentTimeMillis() - time));
                }
                preInitTinyNet();
            }
            initCommonConfig("h5_ucCommonConfigBeforeCreateWebView");
            H5Trace.sessionEnd("initCore", null, new String[0]);
            UcSetupTracing.endTrace("initCore");
        }
    }

    private static String[] getOptionProviderKey() {
        H5GetOptionProvidedKeyProvider h5GetOptionProvidedKeyProvider = (H5GetOptionProvidedKeyProvider) H5Utils.getProvider(H5GetOptionProvidedKeyProvider.class.getName());
        if (h5GetOptionProvidedKeyProvider != null) {
            return h5GetOptionProvidedKeyProvider.getOptionProvidedKey();
        }
        return null;
    }

    private static int getMultiProcessMode(JSONObject multiProcessConfig, Context context) {
        APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "multi_process");
        if (preferences != null) {
            if (UcSdkConstants.UC_VERSION.equalsIgnoreCase(preferences.getString("ucversion", null))) {
                H5Log.d(TAG, "pageStart uncalled user, keep fallback");
                return 0;
            }
        }
        int mode = H5Utils.getInt(multiProcessConfig, (String) Constants.KEY_MODE, 0);
        if (mode == 0) {
            return mode;
        }
        sRebindIsolateProcessTimeout = H5Utils.getInt(multiProcessConfig, (String) "rebindIsolateTimeout", 8000);
        sRenderProcessLaunchTimeout = H5Utils.getInt(multiProcessConfig, (String) "launchTimeout", 11000);
        if (preferences == null || !preferences.getBoolean("launch_failed", false)) {
            if (!H5Utils.getBoolean(multiProcessConfig, (String) "disableFallback", false) && mode > 0) {
                sFallbackLimit = H5Utils.getInt(multiProcessConfig, (String) "fallbackLimit", 3);
                if (preferences != null && preferences.getInt(NewHtcHomeBadger.COUNT, 0) > sFallbackLimit) {
                    H5Log.d(TAG, "pageStart uncalled user, auto fallback, limit：" + sFallbackLimit);
                    reportMultiProcessAutoFallback(preferences, false);
                    return 0;
                }
            }
            if (mode == 2 && android.os.Build.MANUFACTURER.equals("Xiaomi") && ((int) ((DeviceHWInfo.getTotalMemory(context) / 1024) / 1024)) < H5Utils.getInt(multiProcessConfig, (String) "sandboxXiaomiMemory", 0)) {
                mode = 1;
            }
            String phoneInfo = android.os.Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + android.os.Build.MODEL + MetaRecord.LOG_SEPARATOR + VERSION.SDK_INT;
            JSONArray jsonArray = H5Utils.getJSONArray(multiProcessConfig, "sandboxDeviceList", null);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    if (TextUtils.equals(phoneInfo, jsonArray.getString(i))) {
                        H5Log.d(TAG, "device should use normal render process");
                        return 1;
                    }
                }
            }
            JSONArray jsonArray2 = H5Utils.getJSONArray(multiProcessConfig, "sandboxSdkIntList", null);
            if (jsonArray2 != null && !jsonArray2.isEmpty()) {
                for (int i2 = 0; i2 < jsonArray2.size(); i2++) {
                    if (VERSION.SDK_INT == jsonArray2.getIntValue(i2)) {
                        H5Log.d(TAG, "sdk version should use normal render process");
                        return 1;
                    }
                }
            }
            JSONArray jsonArray3 = H5Utils.getJSONArray(multiProcessConfig, "singleDeviceList", null);
            if (jsonArray3 != null && !jsonArray3.isEmpty()) {
                for (int i3 = 0; i3 < jsonArray3.size(); i3++) {
                    if (TextUtils.equals(phoneInfo, jsonArray3.getString(i3))) {
                        H5Log.d(TAG, "device should use single process");
                        return 0;
                    }
                }
            }
            JSONArray jsonArray4 = H5Utils.getJSONArray(multiProcessConfig, "singleSdkIntList", null);
            if (jsonArray4 != null && !jsonArray4.isEmpty()) {
                for (int i4 = 0; i4 < jsonArray4.size(); i4++) {
                    if (VERSION.SDK_INT == jsonArray4.getIntValue(i4)) {
                        H5Log.d(TAG, "sdk version should use single process");
                        return 0;
                    }
                }
            }
            H5Log.d(TAG, "use default multi process config " + mode);
            return mode;
        }
        reportMultiProcessAutoFallback(preferences, true);
        return 0;
    }

    private static void reportMultiProcessAutoFallback(final APSharedPreferences preferences, final boolean isLaunchFailed) {
        H5Utils.executeOrdered(UcSetupTracing.TAG, new Runnable() {
            public void run() {
                int pageStartUnCalledCount = isLaunchFailed ? 0 : preferences.getInt(NewHtcHomeBadger.COUNT, 0);
                preferences.putBoolean("launch_failed", false);
                preferences.putInt(NewHtcHomeBadger.COUNT, 0);
                preferences.putString("ucversion", UcSdkConstants.UC_VERSION);
                H5Log.d(UcServiceSetup.TAG, "clear auto fallback status: " + preferences.commit());
                H5LogData networkData = H5LogData.seedId("H5_UC_MULTI_PROCESS_AUTO_FALLBACK");
                networkData.param3().add("launchFailed", Boolean.valueOf(isLaunchFailed));
                networkData.param3().add("pageStartUnCalledCount", Integer.valueOf(pageStartUnCalledCount));
                UcServiceSetup.addCommonInfoForMultiProcess(networkData);
                H5LogUtil.logNebulaTech(networkData);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void dumpUCMSDKIfNeed(Context context) {
        if (context != null && "YES".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_dumpUCMSDK"))) {
            long start = System.currentTimeMillis();
            H5Log.d(TAG, "begin dump app_ucmsdk");
            CommonUtil.dumpDir(context.getDir("ucmsdk", 0));
            H5Log.d(TAG, "end dump app_ucmsdk used time: " + (System.currentTimeMillis() - start));
        }
    }

    static void initCommonConfig(String configName) {
        JSONObject commonSetupConfig = H5Utils.parseObject(H5ConfigUtil.getConfig(configName));
        if (commonSetupConfig != null && !commonSetupConfig.isEmpty()) {
            UcSetupTracing.beginTrace("commonConfig_" + configName);
            for (String key : commonSetupConfig.keySet()) {
                JSONArray arr = H5Utils.getJSONArray(commonSetupConfig, key, null);
                if (arr != null && !arr.isEmpty()) {
                    for (int i = 0; i < arr.size(); i++) {
                        setEachCommonConfigByKey(key, arr.getJSONObject(i));
                    }
                }
            }
            UcSetupTracing.endTrace("commonConfig_" + configName);
        }
    }

    private static void setEachCommonConfigByKey(String key, JSONObject obj) {
        if (obj != null && !obj.isEmpty()) {
            Boolean onlyMain = obj.getBoolean("onlyMain");
            if (onlyMain == null) {
                return;
            }
            if (H5Utils.isInTinyProcess() && onlyMain.booleanValue()) {
                return;
            }
            if (INIT_CONFIG_UPDATE_BUSSINESS_INFO.equals(key)) {
                Integer var0 = obj.getInteger("var0");
                Integer var1 = obj.getInteger("var1");
                String var2 = obj.getString("var2");
                Object var3 = obj.get("var3");
                if (var0 != null && var1 != null && !TextUtils.isEmpty(var2) && var3 != null) {
                    H5Log.d(TAG, "common config updateBussinessInfo: " + var0 + Token.SEPARATOR + var1 + Token.SEPARATOR + var3.toString());
                    UCSettings.updateBussinessInfo(var0.intValue(), var1.intValue(), var2, var3);
                }
            } else if (INIT_CONFIG_NOTIFY_CORE_EVENT.equals(key)) {
                Integer var02 = obj.getInteger("var0");
                Object var12 = obj.get("var1");
                Boolean var22 = obj.getBoolean("var2");
                if (var02 != null && var12 != null && var22 != null) {
                    boolean hasCallback = var22.booleanValue();
                    H5Log.d(TAG, "common config notifyCoreEvent: " + var02 + Token.SEPARATOR + var12.toString() + Token.SEPARATOR + hasCallback);
                    if (hasCallback) {
                        UCCore.notifyCoreEvent(var02.intValue(), var12, null);
                    } else {
                        UCCore.notifyCoreEvent(var02.intValue(), var12);
                    }
                }
            } else if (INIT_CONFIG_SET_GLOBAL_BOOL_VALUE.equals(key)) {
                String var03 = obj.getString("var0");
                Boolean var13 = obj.getBoolean("var1");
                if (!TextUtils.isEmpty(var03) && var13 != null) {
                    H5Log.d(TAG, "common config setGlobalBoolValue: " + var03 + Token.SEPARATOR + var13);
                    UCSettings.setGlobalBoolValue(var03, var13.booleanValue());
                }
            }
        }
    }

    private static Object[] initLogConfig() {
        return new Object[]{Boolean.valueOf(true), Boolean.valueOf(false), new ValueCallback<Object[]>() {
            public void onReceiveValue(Object[] value) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(":");
                    sb.append(value[1]);
                    sb.append(":");
                    sb.append(value[2]);
                    sb.append(":");
                    sb.append(value[5]);
                    String logs = sb.toString();
                    if (value[6] != null) {
                        if ("v".equals(value[3])) {
                            H5Log.d("H5UcService:" + value[4], logs + value[6]);
                        } else if ("d".equals(value[3])) {
                            H5Log.d("H5UcService:" + value[4], logs + value[6]);
                        } else if ("i".equals(value[3])) {
                            H5Log.d("H5UcService:" + value[4], logs + value[6]);
                        } else if ("w".equals(value[3])) {
                            H5Log.e("H5UcService:" + value[4], logs, value[6]);
                            UcSetupExceptionHelper.logUcInitException(value[6], UcSetupExceptionHelper.ERROR_TYPE_FROM_WARN);
                        } else if ("e".equals(value[3])) {
                            H5Log.e("H5UcService:" + value[4], logs, value[6]);
                            UcSetupExceptionHelper.logUcInitException(value[6], UcSetupExceptionHelper.ERROR_TYPE_FROM_ERROR);
                        }
                    } else if ("v".equals(value[3])) {
                        H5Log.d("H5UcService:" + value[4], logs);
                    } else if ("d".equals(value[3])) {
                        H5Log.d("H5UcService:" + value[4], logs);
                    } else if ("i".equals(value[3])) {
                        H5Log.d("H5UcService:" + value[4], logs);
                    } else if ("w".equals(value[3])) {
                        H5Log.d("H5UcService:" + value[4], logs);
                    } else if ("e".equals(value[3])) {
                        H5Log.e("H5UcService:" + value[4], logs);
                    }
                } catch (Throwable t) {
                    H5Log.e((String) UcServiceSetup.TAG, t);
                }
            }
        }, "[all]", "[all]"};
    }

    private static File getZipSoFromDiffBundle(Context context) {
        File ucmFinalFile;
        File ucmParentDir = context.getDir("plugins_lib", 0);
        File ucmTmpFile = new File(ucmParentDir, UcSdkConstants.SO_NAME);
        String zipName = UcSdkConstants.SO_NAME.replace("7z_uc.so", "zip_uc.so");
        H5Log.d(TAG, "ucmTmpFile " + ucmTmpFile);
        if (ucmTmpFile.exists()) {
            H5Log.d(TAG, "ucmTmpFile.exists()");
            try {
                H5Log.d(TAG, "ucmTmpFile is a zipfile " + new ZipFile(ucmTmpFile).getName());
                ucmFinalFile = new File(ucmParentDir, zipName);
                ucmTmpFile.renameTo(ucmFinalFile);
                H5Log.d(TAG, "ucmTmpFile renameto ucmFinalFile");
            } catch (Exception e) {
                H5Log.e(TAG, "ucmTmpFile not a zipfile ", e);
                ucmFinalFile = ucmTmpFile;
            }
        } else {
            H5Log.d(TAG, "!ucmTmpFile.exists()");
            ucmFinalFile = new File(ucmParentDir, zipName);
        }
        H5Log.d(TAG, "getZipSoFromDiffBundle " + UcSdkConstants.UC_VERSION + " ucmPath " + ucmFinalFile.exists() + ucmFinalFile.getPath());
        return ucmFinalFile;
    }

    private static void preInitTinyNet() {
        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService != null) {
            h5EventHandlerService.preLoadInTinyProcess();
        }
    }

    private static void init7zSo() {
        File file;
        if (!s7zInited) {
            UcSetupTracing.beginTrace("init7zSo");
            UcSetupTracing.setTrace("init7zSoStart");
            s7zInited = true;
            H5Trace.sessionBegin("init7zSo", null, new String[0]);
            H5Log.d(TAG, "init7zSo");
            ProcessLock processLock = new ProcessLock(H5Utils.getContext().getCacheDir() + "/.init7zSo.lock");
            try {
                processLock.lock();
                long time = System.currentTimeMillis();
                Context context = H5Utils.getContext();
                APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "h5_ucsdkLocalUpdatePath");
                String unzipLocalUpdatePath = preferences.getString("path", null);
                String unzipLocalUpdateVersion = preferences.getString("version", null);
                if (H5Utils.isDebuggable(H5Utils.getContext())) {
                    File file2 = new File("/sdcard/slm/libWebViewCore_ri_7z_uc.so");
                    if (file2.exists()) {
                        sInitUcFromSdcardPath = file2.getAbsolutePath();
                    } else {
                        r2 = "libWebViewCore_ri_7z_uc.so";
                        File file3 = new File(context.getApplicationInfo().nativeLibraryDir, "libWebViewCore_ri_7z_uc.so");
                        if (file3.exists() && TextUtils.isEmpty(unzipLocalUpdatePath) && TextUtils.isEmpty(unzipLocalUpdateVersion)) {
                            unzipLocalUpdatePath = file3.getAbsolutePath();
                            unzipLocalUpdateVersion = UcSdkConstants.UC_VERSION + "_ri";
                        }
                    }
                }
                if (!TextUtils.isEmpty(unzipLocalUpdatePath) && !TextUtils.isEmpty(unzipLocalUpdateVersion)) {
                    UcSdkConstants.UC_VERSION = unzipLocalUpdateVersion;
                }
                File h5Container = context.getDir("h5container", 0);
                File file4 = new File(h5Container, "uc");
                File unzipTargetDir = new File(file4, UcSdkConstants.UC_VERSION + "/so");
                File mainSoFile = new File(new File(unzipTargetDir, "/lib"), "libwebviewuc.so");
                File coreDexFile = new File(unzipTargetDir, "core.jar");
                CommonUtil.logFolderTree(h5Container, "init7zSo begin");
                if (!unzipTargetDir.exists() || !mainSoFile.exists() || !coreDexFile.exists() || UcSetupExceptionHelper.isRetryInitUc() || !TextUtils.isEmpty(sInitUcFromSdcardPath)) {
                    UcSetupTracing.setTrace("init7zSoUnzip");
                    if (H5Utils.isMainProcess()) {
                        H5Log.d(TAG, "init on main process, mark uc not init!");
                        H5DevConfig.setBooleanConfig(KEY_MAIN_UCODEX_INIT_SUCCESS, false);
                    }
                    if ((H5PageData.sCreateScene == 2 || H5PageData.sCreateScene == 1) && H5PageData.sCreateTimestamp > 0) {
                        H5PageData.setInitScenario(H5PageData.sCreateTimestamp, 0);
                    }
                    if (H5Utils.isUCM57()) {
                        H5Log.d(TAG, "isUCM57, try copy libv8uc.so");
                        File[] oldVersions = file4.listFiles(new FilenameFilter() {
                            public boolean accept(File dir, String filename) {
                                return !filename.equals(UcSdkConstants.UC_VERSION);
                            }
                        });
                        if (oldVersions != null) {
                            File libv8ucso = null;
                            int length = oldVersions.length;
                            for (int i = 0; i < length; i++) {
                                File oldVersion = oldVersions[i];
                                H5Log.d(TAG, "check " + oldVersion.getName());
                                if (CommonUtil.isM40(oldVersion.getName())) {
                                    libv8ucso = new File(oldVersion, "so/lib/libv8uc.so");
                                    if (libv8ucso.exists()) {
                                        break;
                                    }
                                }
                            }
                            if (libv8ucso == null || !libv8ucso.exists()) {
                                H5Log.d(TAG, "cannot find libv8uc.so");
                            } else {
                                File file5 = new File(h5Container, "libv8uc.so");
                                libv8ucso.renameTo(file5);
                                H5Log.d(TAG, "got libv8uc.so: " + libv8ucso + ", move it to : " + file5);
                            }
                        }
                    }
                    try {
                        delete(file4, true);
                    } catch (Throwable t) {
                        H5Log.e(TAG, "delete unzipTargetParentDir error", t);
                    }
                    unzipTargetDir.mkdirs();
                    if (!TextUtils.isEmpty(sInitUcFromSdcardPath)) {
                        H5Log.d(TAG, "init 7z so from sdcard");
                        m7zPath = sInitUcFromSdcardPath;
                    } else if (TextUtils.isEmpty(unzipLocalUpdatePath) || TextUtils.isEmpty(unzipLocalUpdateVersion)) {
                        H5Log.d(TAG, "init 7z so by default");
                        m7zPath = getZipPathFromLibDir(context);
                    } else {
                        H5Log.d(TAG, "init 7z so from local update path " + unzipLocalUpdatePath);
                        m7zPath = unzipLocalUpdatePath;
                    }
                    do {
                        file = new File(context.getCacheDir(), "alipay_temp_dec_" + String.valueOf(System.currentTimeMillis()));
                    } while (file.exists());
                    file.mkdirs();
                    UcSetupTracing.addCommonInfo("has_extract_7zso", "true");
                    UCCore.extractWebCoreLibraryIfNeeded(context, m7zPath, file.getAbsolutePath(), !TextUtils.isEmpty(sInitUcFromSdcardPath));
                    file.renameTo(unzipTargetDir);
                }
                CommonUtil.logFolderTree(h5Container, "init7zSo end");
                long delta = System.currentTimeMillis() - time;
                H5Log.d(TAG, "init7zSo elapse " + delta);
                if (H5Utils.isDebuggable(H5Utils.getContext())) {
                    TestDataUtils.storeJSParams("ucInit|init7zSo", Long.valueOf(delta));
                }
                processLock.unlock();
                H5Trace.sessionEnd("init7zSo", null, new String[0]);
                UcSetupTracing.endTrace("init7zSo");
            } catch (Exception e) {
                H5Log.e(TAG, "catch exception ", e);
                s7zInited = false;
                throw e;
            } catch (Throwable th) {
                processLock.unlock();
                H5Trace.sessionEnd("init7zSo", null, new String[0]);
                UcSetupTracing.endTrace("init7zSo");
                throw th;
            }
        }
    }

    private static void cleanOldFilesIfNeed() {
        if (!sOldDirCleared) {
            sOldDirCleared = true;
            boolean configSwitch = true;
            H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (provider != null) {
                configSwitch = !BQCCameraParam.VALUE_NO.equalsIgnoreCase(provider.getConfig("h5_clearOldUCCoreFiles"));
            }
            if (configSwitch) {
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        Context context = H5Utils.getContext();
                        if (context != null) {
                            if (UcServiceSetup.IS_DIFF_BUNDLE) {
                                File rootDir = context.getDir("h5container", 0);
                                if (rootDir != null && rootDir.exists()) {
                                    UcServiceSetup.delete(rootDir, false);
                                    H5Log.d(UcServiceSetup.TAG, "[bundle diff] clean old so dir! " + rootDir);
                                }
                                CommonUtil.logFolderTree(rootDir, "clean " + rootDir + " end");
                                return;
                            }
                            File decompressDir = new File(context.getDir("ucmsdk", 0), "decompresses");
                            if (decompressDir.exists()) {
                                UcServiceSetup.delete(decompressDir, false);
                                H5Log.d(UcServiceSetup.TAG, "clean old diff dir! " + decompressDir);
                            }
                            CommonUtil.logFolderTree(decompressDir, "clean " + decompressDir + " end");
                        }
                    }
                });
            }
        }
    }

    private static String getZipPathFromLibDir(Context context) {
        File[] files = context.getCacheDir().listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("alipay_temp_dec_");
            }
        });
        H5Log.d(TAG, "getZipPathFromLibDir recursiveDelete cache/alipay_temp_dec_ files length " + files.length);
        if (files != null && files.length > 0) {
            for (File fil : files) {
                UCCyclone.recursiveDelete(fil, false, null);
            }
        }
        String zipPath = null;
        ClassLoader classLoader = UcServiceSetup.class.getClassLoader();
        try {
            Method findMethod = ClassLoader.class.getDeclaredMethod("findLibrary", new Class[]{String.class});
            findMethod.setAccessible(true);
            zipPath = (String) findMethod.invoke(classLoader, new Object[]{UcSdkConstants.PURE_SO_NAME});
            H5Log.d(TAG, "getZipPathFromLibDir from reflect " + zipPath);
        } catch (Throwable t) {
            H5Log.e(TAG, "catch exception", t);
        }
        if (TextUtils.isEmpty(zipPath)) {
            File libFile = new File(context.getApplicationInfo().nativeLibraryDir, UcSdkConstants.SO_NAME);
            if (libFile.exists()) {
                H5Log.d(TAG, "getZipPathFromLibDir from android api " + zipPath);
                zipPath = libFile.getAbsolutePath();
            }
        }
        if (!TextUtils.isEmpty(zipPath)) {
            return zipPath;
        }
        throw new IllegalStateException("zip path empty.");
    }

    /* access modifiers changed from: private */
    public static boolean delete(File file, boolean deleteDirItSelf) {
        boolean bRet = true;
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    bRet &= delete(child, true);
                }
            }
        }
        String path = file.getAbsolutePath();
        if (!deleteDirItSelf || !H5FileUtil.checkPathValid(path)) {
            H5Log.d(TAG, "delete file invalid or !deleteDirItSelf " + path + ", bRet=" + bRet);
            return bRet;
        }
        boolean bRet2 = bRet & file.delete();
        H5Log.d(TAG, "delete file valid " + path + ", bRet=" + bRet2);
        return bRet2;
    }

    public static void addCommonInfoForMultiProcess(H5LogData data) {
        if (data != null) {
            data.param3().add("ucVersion", UcSdkConstants.UC_VERSION);
            data.param3().add("webviewVersion", UCWebView.WEBVIEW_VERSION);
            data.param3().add("cpuHardware", H5DeviceHelper.getCpuHardware());
            data.param3().add("renderProcessLaunchTimeout", Integer.valueOf(sRenderProcessLaunchTimeout));
            data.param3().add("rebindIsolateProcessTimeout", Integer.valueOf(sRebindIsolateProcessTimeout));
        }
    }
}
