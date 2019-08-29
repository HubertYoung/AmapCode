package com.uc.webview.export.extension;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.sdk.util.e;
import com.uc.webview.export.WebResourceResponse;
import com.uc.webview.export.WebView;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.annotations.Interface;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.INetLogger;
import com.uc.webview.export.internal.interfaces.INetwork;
import com.uc.webview.export.internal.interfaces.INetworkDecider;
import com.uc.webview.export.internal.interfaces.INetworkDelegate;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.InvokeObject;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import com.uc.webview.export.internal.setup.UCSetupException;
import com.uc.webview.export.internal.setup.af;
import com.uc.webview.export.internal.setup.cn;
import com.uc.webview.export.internal.setup.t;
import com.uc.webview.export.internal.setup.v;
import com.uc.webview.export.internal.uc.b;
import com.uc.webview.export.internal.uc.startup.StartupStats;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.ReflectionUtil;
import com.uc.webview.export.utility.SetupTask;
import com.uc.webview.export.utility.download.UpdateTask;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;
import java.util.Map.Entry;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@Api
/* compiled from: ProGuard */
public class UCCore {
    public static final String ADAPTER_BUILD_TIMING = "adapter_build_timing";
    public static final String ADAPTER_BUILD_VERSOPM = "adapter_build_version";
    public static final String BUSINESS_INIT_BY_NEW_CORE_DEX_DIR = "bit_by_new_dex_dir";
    public static final String BUSINESS_INIT_BY_NEW_CORE_ZIP_FILE = "bit_by_new_zip_file";
    public static final String BUSINESS_INIT_BY_OLD_CORE_DEX_DIR = "bit_by_old_dex_dir";
    public static final String CD_DISABLE_UCDNS = "disable_ucdns";
    public static final String CD_ENABLE_NET_THREAD_REDUCE = "dec_thread";
    public static final String CD_ENABLE_TRAFFIC_STAT = "traffic_stat";
    public static final int COMPATIBLE_POLICY_ALL = 7;
    public static final int COMPATIBLE_POLICY_ARMV5 = 1;
    public static final int COMPATIBLE_POLICY_ARMV7 = 2;
    public static final int COMPATIBLE_POLICY_X86 = 4;
    public static final int CORE_EVENT_CLEAR_DNS_CACHE = 0;
    public static final int CORE_EVENT_CLEAR_HTTP_CACHE = 3;
    public static final int CORE_EVENT_CONSOLE_CALLBACK = 6;
    public static final int CORE_EVENT_DELETE_SERVICEWORKER_CACHE = 4;
    public static final int CORE_EVENT_DELETE_SERVICEWORKER_CACHE_SYNC = 9;
    public static final int CORE_EVENT_GET_HTTP_CACHE_SIZE = 1;
    public static final int CORE_EVENT_HTTP2_HOST_LIST = 8;
    public static final int CORE_EVENT_MAX_REQUEST_LIMITATION = 7;
    public static final int CORE_EVENT_ON_ACTIVITY_RECREATE = 11;
    public static final int CORE_EVENT_PUSH_DNS_RESULT = 5;
    public static final int CORE_EVENT_SERVICEWORKER_PUSHMESSAGE = 2;
    public static final int CORE_EVENT_SET_MAX_CACHESIZE = 10;
    public static final int CORE_EVENT_UPDATE_PRIVATE_DATA_DIRECTORY_SUFFIX = 12;
    public static final int DELETE_CORE_POLICY_ALL = 63;
    public static final int DELETE_CORE_POLICY_FILE_VERIFY_FAILED = 16;
    public static final int DELETE_CORE_POLICY_LOAD_SO_ERROR = 2;
    public static final int DELETE_CORE_POLICY_MULTI_CRASH = 4;
    public static final int DELETE_CORE_POLICY_NONE = 0;
    public static final int DELETE_CORE_POLICY_OTHER = 32;
    public static final int DELETE_CORE_POLICY_SO_HASH_MISMATCH = 8;
    public static final int DELETE_CORE_POLICY_SO_SIZE_MISMATCH = 1;
    public static final int DEX2OAT_POLICY_DEFAULT = 0;
    public static final int DEX2OAT_POLICY_DELAY = 1;
    public static final String ENABLE_WEBVIEW_LISTENER_STANDARDIZATION_OPTION = "enable_webview_listener_standardization";
    public static final String EVENT_DELETE_FILE_FINISH = "be_del_fi";
    public static final String EVENT_INIT_CORE_EXCEPTON = "be_init_exception";
    public static final String EVENT_INIT_CORE_SUCCESS = "be_init_success";
    public static final String LOAD_POLICY_SPECIFIED_ONLY = "SPECIFIED_ONLY";
    public static final String LOAD_POLICY_SPECIFIED_OR_UCMOBILE = "SPECIFIED_OR_UCMOBILE";
    public static final String LOAD_POLICY_UCMOBILE_ONLY = "UCMOBILE_ONLY";
    public static final String LOAD_POLICY_UCMOBILE_OR_SPECIFIED = "UCMOBILE_OR_SPECIFIED";
    @Deprecated
    public static final String OPTION_BREAKPAD_CONFIG = "BREAKPAD_CONFIG";
    public static final String OPTION_BUSINESS_DECOMPRESS_ROOT_PATH = "bo_dec_r_p";
    public static final String OPTION_BUSINESS_INIT_TYPE = "bo_init_type";
    public static final String OPTION_COMPATIBLE_POLICY = "COMPATIBLE_POLICY";
    public static final String OPTION_CONNECTION_CONNECT_TIMEOUT = "conn_to";
    public static final String OPTION_CONNECTION_READ_TIMEOUT = "read_to";
    public static final String OPTION_CONTEXT = "CONTEXT";
    public static final String OPTION_CONTINUE_ODEX_ON_DECOMPRESSED = "bo_continue_odex";
    public static final String OPTION_CORE_VERSION_EXCLUDE = "core_ver_excludes";
    public static final String OPTION_DECOMPRESS_AND_ODEX_CALLBACK = "bo_dec_odex_cb";
    public static final String OPTION_DECOMPRESS_CALLBACK = "bo_dec_cl";
    public static final String OPTION_DECOMPRESS_ROOT_DIR = "bo_dec_root_dir";
    public static final String OPTION_DELETE_AFTER_EXTRACT = "bo_del_aft_extract";
    public static final String OPTION_DELETE_CORE_POLICY = "delete_core";
    public static final String OPTION_DELETE_OLD_DEX_DIR = "bo_dex_old_dex_dir";
    public static final String OPTION_DEX2OAT_POLICY = "DEX2OAT_POLICY";
    public static final String OPTION_DEX_FILE_PATH = "dexFilePath";
    public static final String OPTION_DISTINGUISH_JS_ERROR = "distinguish_js_error";
    public static final String OPTION_DOWNLOAD_CHECKER = "dlChecker";
    public static final String OPTION_DWN_RETRY_MAX_WAIT_MILIS = "dwnRetryMaxWait";
    public static final String OPTION_DWN_RETRY_WAIT_MILIS = "dwnRetryWait";
    public static final String OPTION_ENABLE_LOAD_CLASS = "bo_enable_load_class";
    public static final String OPTION_EXACT_LAST_MODIFIED_CHECK = "exact_mod";
    public static final String OPTION_EXACT_OLD_KERNEL_CHECK = "exact_old";
    public static final String OPTION_FORBID_GEN_REPAIR_DIR = "forbid_repair";
    public static final String OPTION_FORCE_USE_BUSINESS_DECOMPRESS_ROOT_PATH = "bo_f_u_dec_r_p";
    public static final String OPTION_GRANT_ALL_BUILDS = "grant_all_builds";
    public static final String OPTION_HARDWARE_ACCELERATED = "AC";
    public static final String OPTION_INIT_IN_SETUP_THREAD = "init_setup_thread";
    public static final String OPTION_LOAD_KERNEL_TYPE = "load";
    public static final String OPTION_LOAD_POLICY = "loadPolicy";
    public static final String OPTION_LOAD_SHARE_CORE_HOST = "load_share_core_host";
    public static final String OPTION_LOG_CONFIG = "log_conf";
    public static final String OPTION_MULTI_CORE_TYPE = "MULTI_CORE_TYPE";
    public static final String OPTION_MULTI_UNKNOWN_CRASH_DISABLE = "disable_multi_unknown_crash";
    public static final String OPTION_NEW_UCM_ZIP_FILE = "bo_new_ucm_zf";
    public static final String OPTION_NEW_UCM_ZIP_TYPE = "bo_new_ucm_z_type";
    public static final String OPTION_NOT_SWITCH_UCCORE = "oNotSwUCCore";
    public static final String OPTION_NOT_USE_7Z_CORE = "not_use_7z_core";
    public static final String OPTION_OLD_DEX_DIR_PATH = "bo_old_dex_dp";
    public static final String OPTION_PRECREATE_WEBVIEW = "precreate_webview";
    public static final String OPTION_PRECREATE_WEBVIEW_URL = "precreate_webview_url";
    public static final String OPTION_PRIVATE_DATA_DIRECTORY_SUFFIX = "PRIVATE_DATA_DIRECTORY_SUFFIX";
    public static final String OPTION_PROMISE_SPECIAL_VERSION_CORE_INIT = "bo_prom_sp_v_c_i";
    public static final String OPTION_PROVIDED_KEYS = "provided_keys";
    public static final String OPTION_RES_FILE_PATH = "resFilePath";
    public static final String OPTION_ROOT_TASK_KEY = "root_task_key";
    public static final String OPTION_SDK_VERSION_EXCLUDE = "sdk_ver_excludes";
    public static final String OPTION_SETUP_THREAD_PRIORITY = "setup_priority";
    public static final String OPTION_SET_ODEX_ROOT_PATH = "set_odex_path";
    public static final String OPTION_SKIP_OLD_KERNEL = "skip_old_extra_kernel";
    public static final String OPTION_SKIP_PRECONDITIONS_IO_CHECK = "bo_skip_io_dc";
    public static final String OPTION_SO_FILE_PATH = "soFilePath";
    public static final String OPTION_SPEEDUP_DEXOPT_POLICY = "speedup_dexopt";
    public static final String OPTION_START_INIT_UC_CORE = "bo_s_i_uc_core";
    public static final String OPTION_UCMOBILE_INIT = "bo_ucm_init";
    public static final String OPTION_UCM_CFG_FILE = "ucmCfgFile";
    public static final String OPTION_UCM_KRL_DIR = "ucmKrlDir";
    public static final String OPTION_UCM_LIB_DIR = "ucmLibDir";
    public static final String OPTION_UCM_PATCH_DIR = "ucmPatDir";
    public static final String OPTION_UCM_UPD_URL = "ucmUpdUrl";
    public static final String OPTION_UCM_ZIP_DIR = "ucmZipDir";
    public static final String OPTION_UCM_ZIP_FILE = "ucmZipFile";
    public static final String OPTION_UC_PLAYER_ROOT = "ucPlayerRoot";
    public static final String OPTION_UC_PROXY_ADBLOCK = "proxy_adblock";
    public static final String OPTION_UPD_SETUP_TASK_WAIT_MILIS = "updWait";
    public static final String OPTION_USE_SDK_SETUP = "sdk_setup";
    public static final String OPTION_USE_SYSTEM_WEBVIEW = "SYSTEM_WEBVIEW";
    public static final String OPTION_USE_UC_PLAYER = "ucPlayer";
    public static final String OPTION_VERIFY_POLICY = "VERIFY_POLICY";
    public static final String OPTION_VIDEO_HARDWARE_ACCELERATED = "VIDEO_AC";
    public static final String OPTION_VMSIZE_SAVING = "vmsize_saving";
    public static final String OPTION_WAP_DENY = "wap_deny";
    public static final String OPTION_WEBVIEW_MULTI_PROCESS = "WEBVIEW_MULTI_PROCESS";
    public static final String OPTION_WEBVIEW_MULTI_PROCESS_ENABLE_SERVICE_SPEEDUP = "WEBVIEW_MULTI_PROCESS_ENABLE_SERVICE_SPEEDUP";
    public static final String OPTION_WEBVIEW_MULTI_PROCESS_FALLBACK_TIMEOUT = "WEBVIEW_MULTI_PROCESS_FALLBACK_TIMEOUT";
    public static final String OPTION_WEBVIEW_POLICY = "WEBVIEW_POLICY";
    public static final String OPTION_WEBVIEW_POLICY_WAIT_MILLIS = "wait_fallback_sys";
    public static final String OPTION_ZIP_FILE_TYPE = "o_zio_file_type";
    public static final String PROCESS_PRIVATE_DATA_DIR_SUFFIX_OPTION = "process_private_data_dir_suffix";
    public static final int SPEEDUP_DEXOPT_POLICY_ALL = 2047;
    public static final int SPEEDUP_DEXOPT_POLICY_ART = 1920;
    public static final int SPEEDUP_DEXOPT_POLICY_DAVIK = 127;
    public static final int SPEEDUP_DEXOPT_POLICY_NONE = 0;
    public static final int VERIFY_POLICY_ALL = 1073741871;
    public static final int VERIFY_POLICY_ALL_FULL_HASH = -1073741697;
    public static final int VERIFY_POLICY_ASYNC = Integer.MIN_VALUE;
    public static final int VERIFY_POLICY_BROWSER_IF = 2;
    public static final int VERIFY_POLICY_CORE_IMPL = 4;
    public static final int VERIFY_POLICY_NONE = 0;
    public static final int VERIFY_POLICY_PAK = 32;
    public static final int VERIFY_POLICY_PAK_FULL_HASH = 64;
    public static final int VERIFY_POLICY_PAK_QUICK = 536870912;
    public static final int VERIFY_POLICY_QUICK = 1073741824;
    public static final int VERIFY_POLICY_SDK_SHELL = 1;
    public static final int VERIFY_POLICY_SO = 8;
    public static final int VERIFY_POLICY_SO_FULL_HASH = 16;
    public static final int VERIFY_POLICY_SO_QUICK = 268435456;
    public static final int VERIFY_POLICY_WITH_MD5 = 1048576;
    public static final int VERIFY_POLICY_WITH_SHA1 = 2097152;
    public static final int VERIFY_POLICY_WITH_SHA256 = 4194304;
    public static final int WEBVIEW_MULTI_PROCESS_ISOLATE = 2;
    public static final int WEBVIEW_MULTI_PROCESS_NONE = 0;
    public static final int WEBVIEW_MULTI_PROCESS_NORMAL = 1;
    public static final int WEBVIEW_POLICY_WAIT_UNTIL_EXCEPTION = 3;
    public static final int WEBVIEW_POLICY_WAIT_UNTIL_FALLBACK_SYSTEM = 2;
    public static final int WEBVIEW_POLICY_WAIT_UNTIL_LOADED = 1;
    private static af a;
    private static v b;

    @Interface
    /* compiled from: ProGuard */
    public interface Callable<V, T> {
        V call(T t) throws Exception;
    }

    /* compiled from: ProGuard */
    class a {
    }

    @Deprecated
    public static void uploadCrashLogs() {
    }

    static /* synthetic */ void a(Context context, String str, java.util.concurrent.Callable callable, Map map) throws UCSetupException {
        File uCPlayerRoot = UpdateTask.getUCPlayerRoot(context);
        SDKFactory.invoke(10002, Long.valueOf(1024));
        SDKFactory.invoke(10002, Long.valueOf(2048));
        SDKFactory.invoke(10002, Long.valueOf(4096));
        SDKFactory.invoke(10002, Long.valueOf(8192));
        SDKFactory.invoke(10002, Long.valueOf(16384));
        SDKFactory.invoke(10002, Long.valueOf(32768));
        SDKFactory.invoke(10002, Long.valueOf(IjkMediaMeta.AV_CH_TOP_BACK_CENTER));
        SDKFactory.D.remove(IWaStat.VIDEO_ERROR_CODE_UPDATE_CHECK_REQUEST);
        SDKFactory.D.remove(IWaStat.VIDEO_ERROR_CODE_DOWNLOAD);
        SDKFactory.D.remove(IWaStat.VIDEO_ERROR_CODE_VERIFY);
        SDKFactory.D.remove(IWaStat.VIDEO_ERROR_CODE_UNZIP);
        Context context2 = context;
        String str2 = str;
        UpdateTask updateTask = new UpdateTask(context2, str2, uCPlayerRoot.getAbsolutePath(), "libu3player.so", new h(), null, null);
        updateTask.onEvent("beginDownload", new e()).onEvent("beginUnZip", new d()).onEvent("unzipSuccess", new o()).onEvent("check", new n(callable)).onEvent("success", new m(context, map)).onEvent(e.b, new l()).onEvent(LogCategory.CATEGORY_EXCEPTION, new j(map)).onEvent("exists", new i(map, uCPlayerRoot)).start();
    }

    static /* synthetic */ void a(String str, Object obj, Map map) {
        if (map != null) {
            if ("downloadException".equals(str)) {
                ValueCallback valueCallback = (ValueCallback) map.get(LogCategory.CATEGORY_EXCEPTION);
                if (valueCallback != null) {
                    try {
                        valueCallback.onReceiveValue(((t) obj).getException());
                    } catch (Throwable unused) {
                    }
                }
            } else if ("updateProgress".equals(str)) {
                ValueCallback valueCallback2 = (ValueCallback) map.get("progress");
                if (valueCallback2 != null) {
                    try {
                        valueCallback2.onReceiveValue((Integer) ((t) obj).invokeO(UCAsyncTask.getPercent, null));
                    } catch (Throwable unused2) {
                    }
                }
            }
        }
    }

    public static int init(Context context, boolean z, a aVar, Map<String, String> map) throws RuntimeException {
        ((SetupTask) ((SetupTask) a().setup((String) OPTION_CONTEXT, (Object) context)).setup((String) OPTION_BREAKPAD_CONFIG, (Object) aVar)).setup((String) OPTION_USE_SYSTEM_WEBVIEW, (Object) Boolean.valueOf(z));
        if (map != null) {
            for (Entry next : map.entrySet()) {
                a.setup((String) next.getKey(), next.getValue());
            }
        }
        SDKFactory.D.remove(IWaStat.ERROR_CODE_INIT);
        a.startSync();
        return 0;
    }

    private static synchronized af a() {
        af afVar;
        synchronized (UCCore.class) {
            if (a == null) {
                a = new af();
            }
            afVar = a;
        }
        return afVar;
    }

    public static synchronized v getCurrentLoadClassTask() {
        v vVar;
        synchronized (UCCore.class) {
            if (b == null) {
                b = new v();
            }
            vVar = b;
        }
        return vVar;
    }

    public static SetupTask setup(String str, Object obj) {
        StartupStats.a();
        StartupTrace.traceEvent("UCCore.setup");
        return (SetupTask) a().setup(str, obj);
    }

    public static void update(Context context, String str, java.util.concurrent.Callable<Boolean> callable) throws UCSetupException {
        a().a(str, callable);
    }

    public static void updateUCCore(Context context, String str, java.util.concurrent.Callable<Boolean> callable, Map<String, ValueCallback> map, Map<String, Object> map2) throws UCSetupException {
        a aVar = new a(context, map, callable, str, map2);
        aVar.start();
    }

    public static void updateUCPlayer(Context context, String str, java.util.concurrent.Callable<Boolean> callable) throws UCSetupException {
        updateUCPlayer(context, str, callable, null);
    }

    public static void updateUCPlayer(Context context, String str, java.util.concurrent.Callable<Boolean> callable, Map<String, ValueCallback> map) throws UCSetupException {
        new g(context, str, callable, map).start();
    }

    public static void setLocationManager(ILocationManager iLocationManager) {
        if (SDKFactory.d != null) {
            SDKFactory.d.setLocationManagerUC(iLocationManager);
        }
    }

    public static void onLowMemory() {
        if (SDKFactory.d != null) {
            try {
                SDKFactory.d.onLowMemory();
            } catch (Throwable unused) {
            }
        }
    }

    public static void onTrimMemory(int i) {
        if (SDKFactory.d != null) {
            try {
                SDKFactory.d.onTrimMemory(i);
            } catch (Throwable unused) {
            }
        }
    }

    public static void setNotAvailableUCListener(NotAvailableUCListener notAvailableUCListener) {
        SDKFactory.a = notAvailableUCListener;
    }

    public static String getCoreInfo() throws RuntimeException {
        return (String) SDKFactory.invoke(UCMPackageInfo.getKernalShareJarLnkRoot, new Object[0]);
    }

    public static void setPrintLog(boolean z) {
        SDKFactory.invoke(10048, Boolean.valueOf(z), new Object[]{Boolean.valueOf(z), Boolean.TRUE, null, "[all]", "[all]"});
    }

    public static void setDrawableResource(String str, Drawable drawable) {
        if (SDKFactory.d != null) {
            SDKFactory.d.getWebResources().setDrawable(str, drawable);
        }
    }

    public static Pair<Long, Long> getTraffic() {
        return (Pair) SDKFactory.invoke(UCMPackageInfo.initUCMBuildInfo, new Object[0]);
    }

    public static WebResourceResponse getResponseByUrl(String str) {
        return (WebResourceResponse) SDKFactory.invoke(10031, str);
    }

    public static void setThirdNetwork(INetwork iNetwork, INetworkDecider iNetworkDecider) {
        if (WebView.getCoreType() != 2 && SDKFactory.d != null) {
            SDKFactory.d.setThirdNetwork(iNetwork, iNetworkDecider);
        }
    }

    public static void setNetworkDelegate(INetworkDelegate iNetworkDelegate) {
        if (WebView.getCoreType() != 1 || SDKFactory.d == null) {
            if (WebView.getCoreType() == 3 && SDKFactory.d != null) {
                Log.e("network delegate", "UCCore U4 setNetworkDelegate");
                try {
                    SDKFactory.d.setNetworkDelegate(iNetworkDelegate);
                    return;
                } catch (Exception e) {
                    Log.e("network delegate", "setNetworkDelegate", e);
                }
            }
            return;
        }
        Log.e("network delegate", "invoke setNetworkDelegate");
        try {
            ReflectionUtil.invoke((Object) SDKFactory.d, (String) "setNetworkDelegate", new Class[]{InvokeObject.class}, new Object[]{new b(iNetworkDelegate)});
        } catch (Exception e2) {
            Log.e("network delegate", "setNetworkDelegate", e2);
        }
    }

    public static void setNetLogger(INetLogger iNetLogger) {
        SDKFactory.d.setNetLogger(iNetLogger);
    }

    public static void extractWebCoreLibraryIfNeeded(Context context, String str, String str2, boolean z) throws UCSetupException {
        UCCyclone.decompressIfNeeded(context, true, new File(str), new File(str2), (FilenameFilter) null, z);
    }

    public static boolean extractWebCoreLibraryIfNeeded(Context context, String str, String str2, String str3, boolean z) throws UCSetupException {
        return UCCyclone.decompressIfNeeded(context, str2, new File(str), new File(str3), (FilenameFilter) null, z);
    }

    public static void setInitCallback(InitCallback initCallback) {
        SDKFactory.p = initCallback;
    }

    public static void setStatDataCallback(ValueCallback<String> valueCallback) {
        SDKFactory.z = valueCallback;
    }

    public static void setStatDataCheckCallback(ValueCallback<String> valueCallback) {
        SDKFactory.A = valueCallback;
    }

    public static void setSetupExceptionListener(ValueCallback<UCSetupException> valueCallback) {
        SDKFactory.B = valueCallback;
    }

    public static void setParam(String str) {
        SDKFactory.invoke(10004, str);
    }

    public static String getParam(String str) {
        return (String) SDKFactory.invoke(10065, str);
    }

    public static void updateTypefacePath(String str, Runnable runnable) {
        SDKFactory.invoke(UCMPackageInfo.deleteTempDecFiles, str, runnable);
    }

    public static void clearHttpCache() {
        if (WebView.getCoreType() != 2 && SDKFactory.d != null) {
            SDKFactory.d.updateBussinessInfo(3, 0, "SETTING_CLEAR_RECORD", "15");
        }
    }

    public static Object notifyCoreEvent(int i, Object obj) {
        if (WebView.getCoreType() == 1 && SDKFactory.d != null) {
            Log.d("notifyCoreEvent", "notifyCoreEvent");
            try {
                return ReflectionUtil.invoke((Object) SDKFactory.d, (String) "notifyCoreEvent", new Class[]{InvokeObject.class}, new Object[]{new com.uc.webview.export.internal.uc.a(i, obj)});
            } catch (Throwable th) {
                Log.e("notifyCoreEvent", "notifyCoreEvent", th);
            }
        } else if (WebView.getCoreType() == 3 && SDKFactory.d != null) {
            try {
                return SDKFactory.d.notifyCoreEvent(i, obj);
            } catch (Exception e) {
                Log.e("notifyCoreEvent", "notifyCoreEvent error=", e);
            }
        }
        return null;
    }

    public static Object notifyCoreEvent(int i, Object obj, ValueCallback<Object> valueCallback) {
        if (WebView.getCoreType() == 3 && SDKFactory.d != null) {
            try {
                return SDKFactory.d.notifyCoreEvent(i, obj, valueCallback);
            } catch (Exception e) {
                Log.e("notifyCoreEvent", "notifyCoreEvent error=", e);
            }
        }
        return null;
    }

    public static void setSocketParam(String str, Object obj) {
        if (!(WebView.getCoreType() == 2 || SDKFactory.d == null)) {
            try {
                SDKFactory.d.setSocketParam(str, obj);
            } catch (AbstractMethodError unused) {
            }
        }
    }

    @Deprecated
    public static void preloadResource(String str, int i, int i2, ValueCallback<android.webkit.WebResourceResponse> valueCallback) {
        if (!(WebView.getCoreType() == 2 || SDKFactory.d == null)) {
            try {
                SDKFactory.d.preloadResource(str, i, i2, new f(valueCallback));
            } catch (AbstractMethodError unused) {
            }
        }
    }

    public static void prefetchResource(String str, int i, int i2, ValueCallback<WebResourceResponse> valueCallback) {
        if (!(WebView.getCoreType() == 2 || SDKFactory.d == null)) {
            try {
                SDKFactory.d.preloadResource(str, i, i2, valueCallback);
            } catch (AbstractMethodError unused) {
            }
        }
    }

    public static String utdid(Context context) {
        return com.uc.webview.export.internal.utility.j.a.a(context);
    }

    public static void startDownload() {
        af a2 = a();
        if (a2.a != null && (a2.a instanceof cn)) {
            ((cn) a2.a).a.startDownload();
        }
    }

    public static String getExtractDirPath(String str, String str2) {
        File file = new File(str2);
        StringBuilder sb = new StringBuilder();
        sb.append(UCCyclone.getSourceHash(file.getAbsolutePath()));
        sb.append("/");
        sb.append(UCCyclone.getSourceHash(file.length(), file.lastModified()));
        return new File(str, sb.toString()).getAbsolutePath();
    }

    public static String getExtractDirPath(Context context, String str) {
        return getExtractDirPath(((File) UCMPackageInfo.invoke(10003, context)).getAbsolutePath(), str);
    }

    public static String getODexDirPath(Context context, String str) {
        return ((File) UCMPackageInfo.invoke(UCMPackageInfo.expectCreateDirFile2P, (File) UCMPackageInfo.invoke(10004, context), (String) UCMPackageInfo.invoke(10012, UCMPackageInfo.subStringForAppFilePath(context, str)))).getAbsolutePath();
    }

    public static void setGlobalOption(String str, Object obj) {
        SDKFactory.invoke(10057, str, obj);
    }

    public static Object getGlobalOption(String str) {
        return SDKFactory.invoke(10058, str);
    }

    public static boolean getGlobalBooleanOption(String str) {
        Boolean bool = (Boolean) SDKFactory.invoke(10058, str);
        return bool != null && bool.booleanValue();
    }

    public static void startTCPDevtools(String str, int i) {
        notifyCoreEvent(101, new Object[]{str, Integer.valueOf(i)});
    }
}
