package com.autonavi.minimap.ajx3.upgrade;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3RequestHolder;
import com.autonavi.minimap.ajx3.debug.EventLogRecorder;
import com.autonavi.minimap.ajx3.param.UpdatableInitRequest;
import com.autonavi.minimap.ajx3.param.UpdatableSchemeRequest;
import com.autonavi.minimap.ajx3.param.UpdatableWebRequest;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.aocs.AocsRequestHolder;
import com.autonavi.minimap.aocs.param.Updatable11Request;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;

public class Ajx3UpgradeManager {
    static final String AJX_DIFF_DIR = "ajx_diff";
    static final String AJX_DIFF_TMP_DIR = "ajx_diff_tmp";
    static final String CHECK_INTERVAL = "checkInterval";
    static final String LAST_CHECK_TIME = "lastCheckTime";
    private static final int MSG_SHOW_TOAST = 101;
    private static final long ONE_HOUR = 3600000;
    static final String PACKAGES = "packages";
    private static final String TAG = "Ajx3UpgradeManager";
    /* access modifiers changed from: private */
    public static List<String> sDependenceWhitList = new ArrayList();
    private static Ajx3UpgradeManager sInstance;
    /* access modifiers changed from: private */
    public Ajx3BundleDataManager dataManager = null;
    /* access modifiers changed from: private */
    public boolean isChecking = false;
    private boolean isColdStart = true;
    /* access modifiers changed from: private */
    public BundleUpgradeCallback mCallback = null;
    private Ajx3WebCloudConfig mCloudConfig = null;
    private Context mContext;
    private ConcurrentHashMap<Long, ContextBundleInfo> mContextBundleMap = new ConcurrentHashMap<>();
    private long mCurrentTime;
    /* access modifiers changed from: private */
    public Ajx3BundleDownloadManager mDownloadManager;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == 101 && (message.obj instanceof String)) {
                Toast.makeText(Ajx3UpgradeManager.this.getContext(), (String) message.obj, 1).show();
            }
        }
    };
    private Ajx3WebResourcesConfig mResourceConfig = null;
    /* access modifiers changed from: private */
    public RollbackDownloadFinishCallback mRollbackDownloadCallback = null;
    public String mStatId = "";
    private List<BundleGroup> mSuspendList = new ArrayList();
    private AosRequest mUpdateRequest;
    private long mWebStartTime;

    public interface BundleUpgradeCallback {
        void onFailed();

        void onFailed(String str);

        void onSuccess();
    }

    class ContextBundleInfo {
        List<String> mDependence = new ArrayList();
        String mEnterBundle;
        long shadow;

        ContextBundleInfo(long j, String str, String str2) {
            this.shadow = j;
            this.mEnterBundle = str;
            if (!TextUtils.isEmpty(this.mEnterBundle) && !Ajx3UpgradeManager.sDependenceWhitList.contains(this.mEnterBundle) && !this.mDependence.contains(this.mEnterBundle)) {
                this.mDependence.add(this.mEnterBundle);
            }
            if (!TextUtils.isEmpty(str2)) {
                try {
                    JSONArray jSONArray = new JSONArray(str2);
                    if (jSONArray.length() > 0) {
                        for (int i = 0; i < jSONArray.length(); i++) {
                            String string = jSONArray.getString(i);
                            if (!TextUtils.isEmpty(string) && !Ajx3UpgradeManager.sDependenceWhitList.contains(string) && !this.mDependence.contains(string)) {
                                this.mDependence.add(string);
                            }
                        }
                    }
                } catch (JSONException unused) {
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean contains(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            if (str.equals(this.mEnterBundle)) {
                return true;
            }
            return this.mDependence.contains(str);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.shadow);
            sb.append("bundleName: ");
            sb.append(this.mEnterBundle);
            sb.append("\n     dependence: ");
            for (String append : this.mDependence) {
                sb.append(append);
                sb.append(" , ");
            }
            return sb.toString();
        }
    }

    public enum Type {
        init,
        web,
        scheme,
        rollback
    }

    interface WebCloudCheckCallBack {
        void onFinished();
    }

    public void setStartTime() {
        this.mWebStartTime = System.currentTimeMillis();
    }

    public long getDownloadTime() {
        return System.currentTimeMillis() - this.mWebStartTime;
    }

    public static Ajx3UpgradeManager getInstance() {
        if (sInstance == null) {
            synchronized (Ajx3UpgradeManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new Ajx3UpgradeManager();
                        initWhitList();
                    }
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDownloadManager = new Ajx3BundleDownloadManager(context);
        this.dataManager = new Ajx3BundleDataManager(context);
        if (this.mCloudConfig == null) {
            this.mCloudConfig = new Ajx3WebCloudConfig(this.mContext);
        }
    }

    /* access modifiers changed from: 0000 */
    public Context getContext() {
        return this.mContext;
    }

    public String getDiffDir() {
        return this.dataManager.mDiffDir.getAbsolutePath();
    }

    public String getFileInfoMap() {
        return this.dataManager.getFileInfoMap();
    }

    public String reInitBundleInfo() {
        return this.dataManager.reInitBundleInfo();
    }

    public void checkUpgrade() {
        if (this.mResourceConfig == null) {
            this.mResourceConfig = new Ajx3WebResourcesConfig(this.mContext);
        }
        this.mCloudConfig.checkUpdate(true, new WebCloudCheckCallBack() {
            public void onFinished() {
                Ajx3Rollback.getInstance().askToCheckRollBack();
            }
        });
        this.mResourceConfig.checkUpdate(this.isColdStart);
        checkUpgradeInit();
        this.isColdStart = false;
    }

    private boolean preRun(Type type) {
        if (!this.isChecking && !this.mDownloadManager.isRunning()) {
            return true;
        }
        switch (type) {
            case scheme:
            case web:
            case rollback:
                if (this.isChecking) {
                    if (this.mUpdateRequest instanceof UpdatableRollbackRequest) {
                        return false;
                    }
                    if (this.mUpdateRequest != null && !this.mUpdateRequest.isCanceled()) {
                        this.mUpdateRequest.cancel();
                        this.mUpdateRequest = null;
                    }
                    if (this.mCallback != null) {
                        this.mCallback.onFailed("new request: ".concat(String.valueOf(type)));
                        this.mCallback = null;
                    }
                } else if (this.mDownloadManager.isRunning()) {
                    if (this.mDownloadManager.getDownloadType() == Type.rollback) {
                        return false;
                    }
                    this.mDownloadManager.cancel();
                    if (this.mCallback != null) {
                        this.mCallback.onFailed("new request: ".concat(String.valueOf(type)));
                        this.mCallback = null;
                    }
                }
                return true;
            default:
                return false;
        }
    }

    private void checkUpgradeInit() {
        if (preRun(Type.init)) {
            if (System.currentTimeMillis() - this.mCurrentTime < 3600000) {
                if (preRun(Type.init) && !TextUtils.isEmpty(Ajx3SpUtil.getLastCheckResponse(this.mContext))) {
                    this.mDownloadManager.restore();
                }
                return;
            }
            this.mStatId = Ajx3ActionLogUtil.generateStatId();
            this.isChecking = true;
            this.mCurrentTime = System.currentTimeMillis();
            UpdatableInitRequest updatableInitRequest = new UpdatableInitRequest();
            updatableInitRequest.package_info = this.dataManager.generateRequestInfo((String) "");
            Ajx3ActionLogUtil.actionLogAjx("B001", updatableInitRequest.package_info);
            if (TextUtils.isEmpty(updatableInitRequest.package_info)) {
                this.isChecking = false;
                return;
            }
            Ajx3ActionLogUtil.actionLogAjxWeb(15, 0, "check update for bundle update", false, this.mStatId, Ajx3ActionLogUtil.generateWebAjxCheckRequestAndroidExt(false));
            Ajx3RequestHolder.getInstance().sendUpdatableInit(updatableInitRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
                public void onSuccess(AosByteResponse aosByteResponse) {
                    Ajx3BundleUpdatableParser ajx3BundleUpdatableParser = new Ajx3BundleUpdatableParser();
                    ajx3BundleUpdatableParser.parser((byte[]) aosByteResponse.getResult());
                    Ajx3UpgradeManager.this.mDownloadManager.run(ajx3BundleUpdatableParser.mData, Type.init);
                    Ajx3UpgradeManager.this.isChecking = false;
                    String str = Ajx3UpgradeManager.this.mDownloadManager.mDownloadInfo;
                    boolean z = true;
                    if (ajx3BundleUpdatableParser.code != 1) {
                        z = false;
                    }
                    Ajx3ActionLogUtil.actionLogAjxWeb(16, 0, "check update for bundle update", false, Ajx3UpgradeManager.this.mStatId, Ajx3ActionLogUtil.generateWebAjxCheckResponseAndroidExt(str, z, false));
                }

                public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                    Ajx3UpgradeManager.this.isChecking = false;
                    Ajx3ActionLogUtil.actionLogAjxWeb(11, -1, "check update error : ".concat(String.valueOf(aosResponseException)), false, Ajx3UpgradeManager.this.mStatId);
                }
            });
            this.mUpdateRequest = updatableInitRequest;
        }
    }

    public void checkUpgradeViaScheme(String str, BundleUpgradeCallback bundleUpgradeCallback) {
        if (TextUtils.isEmpty(str) || !NetworkReachability.b() || !preRun(Type.scheme)) {
            if (bundleUpgradeCallback != null) {
                bundleUpgradeCallback.onFailed("scheme is empty or network not connect or is doing rollback: ".concat(String.valueOf(str)));
            }
            return;
        }
        Ajx3SpUtil.setLastCheckTask(this.mContext, "");
        Ajx3SpUtil.setLastCheckResponse(this.mContext, "");
        this.mCallback = bundleUpgradeCallback;
        this.mStatId = Ajx3ActionLogUtil.generateStatId();
        this.isChecking = true;
        UpdatableSchemeRequest updatableSchemeRequest = new UpdatableSchemeRequest();
        updatableSchemeRequest.package_info = this.dataManager.generateRequestInfo((String) "");
        updatableSchemeRequest.scheme_key = str;
        Ajx3ActionLogUtil.actionLogAjx("B001", updatableSchemeRequest.package_info);
        Ajx3ActionLogUtil.actionLogAjxWeb(15, 0, "startCheckUpdatingRequest", true, this.mStatId, Ajx3ActionLogUtil.generateSchemeAjxCheckRequestAndroidExt());
        Ajx3RequestHolder.getInstance().sendUpdatableScheme(updatableSchemeRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public void onSuccess(AosByteResponse aosByteResponse) {
                if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                    if (Ajx3UpgradeManager.this.mCallback != null) {
                        Ajx3UpgradeManager.this.mCallback.onFailed("no_bizdata");
                        Ajx3UpgradeManager.this.mCallback = null;
                    }
                    return;
                }
                Ajx3BundleUpdatableParser ajx3BundleUpdatableParser = new Ajx3BundleUpdatableParser();
                ajx3BundleUpdatableParser.parser((byte[]) aosByteResponse.getResult());
                int run = Ajx3UpgradeManager.this.mDownloadManager.run(ajx3BundleUpdatableParser.mData, Type.scheme);
                Ajx3UpgradeManager.this.isChecking = false;
                if (run <= 0 && Ajx3UpgradeManager.this.mCallback != null) {
                    Ajx3UpgradeManager.this.mCallback.onFailed("no_bizdata");
                    Ajx3UpgradeManager.this.mCallback = null;
                }
                String str = Ajx3UpgradeManager.this.mDownloadManager.mDownloadInfo;
                int i = ajx3BundleUpdatableParser.code;
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                Ajx3ActionLogUtil.actionLogAjxWeb(16, 0, "receivedCheckUpdateResponse", true, Ajx3UpgradeManager.this.mStatId, Ajx3ActionLogUtil.generateSchemeAjxCheckResponseAndroidExt(str, z));
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                if (Ajx3UpgradeManager.this.mCallback != null) {
                    Ajx3UpgradeManager.this.mCallback.onFailed("unreachable_network");
                    Ajx3UpgradeManager.this.mCallback = null;
                }
                Ajx3UpgradeManager.this.isChecking = false;
                StringBuilder sb = new StringBuilder("Failed to check ajx patch updating: ");
                sb.append(aosResponseException.getLocalizedMessage());
                Ajx3ActionLogUtil.actionLogAjxWeb(11, -1, sb.toString(), true, Ajx3UpgradeManager.this.mStatId);
            }
        });
        this.mUpdateRequest = updatableSchemeRequest;
    }

    public void checkUpgradeViaBundleName(final String str, @NonNull BundleUpgradeCallback bundleUpgradeCallback) {
        if (TextUtils.isEmpty(str) || !NetworkReachability.b() || !preRun(Type.web)) {
            StringBuilder sb = new StringBuilder("bundleName is empty or network not connect or is doing rollback: ");
            sb.append(str);
            sb.append(" ,isConnected: ");
            sb.append(NetworkReachability.b());
            bundleUpgradeCallback.onFailed(sb.toString());
            return;
        }
        this.mStatId = Ajx3ActionLogUtil.generateStatId();
        Ajx3SpUtil.setLastCheckTask(this.mContext, "");
        Ajx3SpUtil.setLastCheckResponse(this.mContext, "");
        this.mCallback = bundleUpgradeCallback;
        UpdatableWebRequest updatableWebRequest = new UpdatableWebRequest();
        updatableWebRequest.package_info = this.dataManager.generateRequestInfo(str);
        if (TextUtils.isEmpty(updatableWebRequest.package_info)) {
            this.mCallback.onFailed("empty request info");
            this.mCallback = null;
            return;
        }
        this.isChecking = true;
        Ajx3ActionLogUtil.actionLogAjx("B001", updatableWebRequest.package_info);
        Ajx3ActionLogUtil.actionLogAjxWeb(15, 0, "check update for web ajx page open", true, this.mStatId, Ajx3ActionLogUtil.generateWebAjxCheckRequestAndroidExt(true));
        Ajx3RequestHolder.getInstance().sendUpdatableWeb(updatableWebRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public void onSuccess(AosByteResponse aosByteResponse) {
                if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                    if (Ajx3UpgradeManager.this.mCallback != null) {
                        Ajx3UpgradeManager.this.mCallback.onFailed(" check error");
                        Ajx3UpgradeManager.this.mCallback = null;
                    }
                    return;
                }
                Ajx3BundleUpdatableParser ajx3BundleUpdatableParser = new Ajx3BundleUpdatableParser();
                ajx3BundleUpdatableParser.parser((byte[]) aosByteResponse.getResult());
                int run = Ajx3UpgradeManager.this.mDownloadManager.run(ajx3BundleUpdatableParser.mData, Type.web);
                boolean z = false;
                Ajx3UpgradeManager.this.isChecking = false;
                if (run <= 0) {
                    Ajx3BundleFileInfo ajx3BundleFileInfo = Ajx3UpgradeManager.this.dataManager.getBundleFileInfoMap().get(str);
                    if (ajx3BundleFileInfo != null) {
                        ajx3BundleFileInfo.mLastCheckTime = System.currentTimeMillis();
                    }
                    if (Ajx3UpgradeManager.this.mCallback != null) {
                        Ajx3UpgradeManager.this.mCallback.onFailed(" check error");
                        Ajx3UpgradeManager.this.mCallback = null;
                    }
                }
                String str = Ajx3UpgradeManager.this.mDownloadManager.mDownloadInfo;
                if (ajx3BundleUpdatableParser.code == 1) {
                    z = true;
                }
                Ajx3ActionLogUtil.actionLogAjxWeb(16, 0, "check update for bundle update", true, Ajx3UpgradeManager.this.mStatId, Ajx3ActionLogUtil.generateWebAjxCheckResponseAndroidExt(str, z, true));
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                Ajx3UpgradeManager.this.isChecking = false;
                if (Ajx3UpgradeManager.this.mCallback != null) {
                    Ajx3UpgradeManager.this.mCallback.onFailed(" check error");
                    Ajx3UpgradeManager.this.mCallback = null;
                }
                Ajx3ActionLogUtil.actionLogAjxWeb(11, -1, "check update error : ".concat(String.valueOf(aosResponseException)), true, Ajx3UpgradeManager.this.mStatId);
            }
        });
        this.mUpdateRequest = updatableWebRequest;
    }

    /* access modifiers changed from: 0000 */
    public void checkRollback(final List<RollbackInfo> list, RollbackDownloadFinishCallback rollbackDownloadFinishCallback) {
        if (rollbackDownloadFinishCallback != null) {
            this.mRollbackDownloadCallback = rollbackDownloadFinishCallback;
            if (list == null || list.size() <= 0) {
                this.mRollbackDownloadCallback.onDownloadFinish(null);
                this.mRollbackDownloadCallback = null;
                return;
            }
            UpdatableRollbackRequest updatableRollbackRequest = new UpdatableRollbackRequest();
            updatableRollbackRequest.package_info = this.dataManager.generateRequestInfo(list);
            if (TextUtils.isEmpty(updatableRollbackRequest.package_info)) {
                if (this.mRollbackDownloadCallback != null) {
                    this.mRollbackDownloadCallback.onDownloadFinish(null);
                    this.mRollbackDownloadCallback = null;
                }
            } else if (!preRun(Type.rollback)) {
                if (this.mRollbackDownloadCallback != null) {
                    this.mRollbackDownloadCallback.onDownloadFinish(null);
                    this.mRollbackDownloadCallback = null;
                }
            } else {
                this.isChecking = true;
                this.mStatId = Ajx3ActionLogUtil.generateStatId();
                Ajx3SpUtil.setLastCheckTask(this.mContext, "");
                Ajx3SpUtil.setLastCheckResponse(this.mContext, "");
                sendUpdatableRollback(updatableRollbackRequest, new dkn(), new AosResponseCallbackOnUi<AosByteResponse>() {
                    public void onSuccess(AosByteResponse aosByteResponse) {
                        if (aosByteResponse != null && aosByteResponse.getResult() != null) {
                            Ajx3BundleUpdatableParser ajx3BundleUpdatableParser = new Ajx3BundleUpdatableParser();
                            ajx3BundleUpdatableParser.parser((byte[]) aosByteResponse.getResult());
                            if (Ajx3UpgradeManager.this.mDownloadManager.run(ajx3BundleUpdatableParser.mData, Type.rollback, list) <= 0 && Ajx3UpgradeManager.this.mRollbackDownloadCallback != null) {
                                Ajx3UpgradeManager.this.mRollbackDownloadCallback.onDownloadFinish(null);
                                Ajx3UpgradeManager.this.mRollbackDownloadCallback = null;
                            }
                            Ajx3UpgradeManager.this.isChecking = false;
                        }
                    }

                    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                        if (Ajx3UpgradeManager.this.mRollbackDownloadCallback != null) {
                            Ajx3UpgradeManager.this.mRollbackDownloadCallback.onDownloadFinish(null);
                            Ajx3UpgradeManager.this.mRollbackDownloadCallback = null;
                        }
                        Ajx3UpgradeManager.this.isChecking = false;
                    }
                });
                this.mUpdateRequest = updatableRollbackRequest;
            }
        }
    }

    private void onlineLog() {
        OnlineLogRequest onlineLogRequest = new OnlineLogRequest();
        onlineLogRequest.package_info = this.dataManager.generateOnlineLogRequestInfo();
        sendOnlineLog(onlineLogRequest, new dkn(), new AosResponseCallbackOnUi<AosByteResponse>() {
            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            }

            public void onSuccess(AosByteResponse aosByteResponse) {
            }
        });
    }

    public void testWebCloudInterface() {
        Updatable11Request updatable11Request = new Updatable11Request();
        updatable11Request.d = "";
        updatable11Request.b = "1";
        updatable11Request.c = bny.c;
        final Ajx3WebCloudConfigParser ajx3WebCloudConfigParser = new Ajx3WebCloudConfigParser("hot_start", this.mStatId);
        AocsRequestHolder.getInstance().sendUpdatable11(updatable11Request, new AosResponseCallbackOnUi<AosByteResponse>() {
            public void onSuccess(AosByteResponse aosByteResponse) {
                if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                    EventLogRecorder.log("云控", "服务返回为空");
                    return;
                }
                ajx3WebCloudConfigParser.parser((byte[]) aosByteResponse.getResult());
                EventLogRecorder.log("云控", ajx3WebCloudConfigParser.mData);
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                EventLogRecorder.log("云控", aosResponseException.getLocalizedMessage());
            }
        });
    }

    public void testInitInterface() {
        UpdatableInitRequest updatableInitRequest = new UpdatableInitRequest();
        updatableInitRequest.package_info = this.dataManager.generateRequestInfo((String) "");
        Ajx3ActionLogUtil.actionLogAjx("B001", updatableInitRequest.package_info);
        Ajx3RequestHolder.getInstance().sendUpdatableInit(updatableInitRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public void onSuccess(AosByteResponse aosByteResponse) {
                Ajx3BundleUpdatableParser ajx3BundleUpdatableParser = new Ajx3BundleUpdatableParser();
                ajx3BundleUpdatableParser.parser((byte[]) aosByteResponse.getResult());
                EventLogRecorder.log("init", ajx3BundleUpdatableParser.mData);
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                EventLogRecorder.log("init", aosResponseException.toString());
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void updateDataByRollback(List<RollbackInfo> list) {
        if (this.dataManager != null) {
            this.dataManager.updateDataByRollback(list);
        }
    }

    /* access modifiers changed from: 0000 */
    public void rollbackAll() {
        if (this.dataManager != null) {
            this.dataManager.rollbackAll();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean hasPatch() {
        if (this.dataManager != null) {
            return this.dataManager.hasPatch();
        }
        return false;
    }

    public void cancelUpgrade(Type type) {
        if (type == Type.web) {
            Ajx3ActionLogUtil.actionLogAjxWeb(13, 0, "web ajx loading cancel", true, this.mStatId);
        }
        this.mCallback = null;
        if (this.mDownloadManager.getDownloadType() != Type.rollback) {
            this.mDownloadManager.cancel();
        }
        if (!(this.mUpdateRequest instanceof UpdatableRollbackRequest) && this.mUpdateRequest != null && !this.mUpdateRequest.isCanceled()) {
            this.mUpdateRequest.cancel();
        }
    }

    public void cancel() {
        this.mDownloadManager.onDestroy();
        if (this.mRollbackDownloadCallback != null) {
            this.mRollbackDownloadCallback.onDownloadFinish(null);
            this.mRollbackDownloadCallback = null;
        }
        if (this.mUpdateRequest != null && !this.mUpdateRequest.isCanceled()) {
            this.mUpdateRequest.cancel();
            this.mUpdateRequest = null;
        }
        this.dataManager.onCancel();
    }

    public boolean needCheckUpdate(String str) {
        if (this.dataManager == null || !this.dataManager.getBundleNameList().contains(str)) {
            return false;
        }
        for (ContextBundleInfo next : this.mContextBundleMap.values()) {
            if (next != null && next.contains(str)) {
                return false;
            }
        }
        Ajx3BundleFileInfo ajx3BundleFileInfo = this.dataManager.getBundleFileInfoMap().get(str);
        long checkInterval = this.mCloudConfig.getCheckInterval(str);
        if (ajx3BundleFileInfo != null) {
            int i = (checkInterval > 0 ? 1 : (checkInterval == 0 ? 0 : -1));
            if (i != 0 && (i <= 0 || System.currentTimeMillis() - ajx3BundleFileInfo.mLastCheckTime < checkInterval * 1000)) {
                return false;
            }
        }
        return true;
    }

    public String getConfigItemsByModuleName(String str) {
        return this.mCloudConfig != null ? this.mCloudConfig.getWebConfig(str) : "";
    }

    /* access modifiers changed from: 0000 */
    public Ajx3BundleFileInfo getBundleFileInfo(String str) {
        return this.dataManager.getBundleFileInfoMap().get(str);
    }

    /* access modifiers changed from: 0000 */
    public void onDownloadPatchReady(List<Ajx3BundlePatchInfo> list, String str, Type type, boolean z) {
        List<Ajx3BundlePatchInfo> list2 = null;
        if (type == Type.rollback) {
            RollbackDownloadFinishCallback rollbackDownloadFinishCallback = this.mRollbackDownloadCallback;
            if (z) {
                list2 = list;
            }
            rollbackDownloadFinishCallback.onDownloadFinish(list2);
        } else {
            boolean onDownloadPatchReady = this.dataManager.onDownloadPatchReady(list, this.mCallback != null, this.mStatId);
            if (this.mCallback != null) {
                if (onDownloadPatchReady) {
                    this.mCallback.onSuccess();
                } else {
                    this.mCallback.onFailed(str);
                }
                this.mCallback = null;
            }
        }
        if (list != null) {
            onlineLog();
        }
    }

    /* access modifiers changed from: 0000 */
    public String getBaseAjxFileName(String str) {
        Ajx3BundleFileInfo ajx3BundleFileInfo = this.dataManager.getBundleFileInfoMap().get(str);
        if (ajx3BundleFileInfo == null) {
            return "";
        }
        if (TextUtils.isEmpty(ajx3BundleFileInfo.baseAjxFileName)) {
            return "";
        }
        StringBuilder sb = new StringBuilder("ajx_file_base/");
        sb.append(ajx3BundleFileInfo.baseAjxFileName);
        return sb.toString();
    }

    public void handleScanAjx(String str) {
        this.dataManager.handleScanAjx(str);
    }

    public void handleScanJs(String str) {
        this.dataManager.handleScanJs(str);
    }

    public void setBundleListUpdateListener(BundleNameListUpdateListener bundleNameListUpdateListener) {
        this.dataManager.setBundleListUpdateListener(bundleNameListUpdateListener);
    }

    public List<String> getBundleNameList() {
        return this.dataManager.getBundleNameList();
    }

    public List<String> getJsBundleNameList() {
        return this.dataManager.getJsBundleNameList();
    }

    public void deleteAllDownloadBundle() {
        this.dataManager.deleteAllDownloadBundle();
    }

    /* access modifiers changed from: 0000 */
    public int getBundleUpdateType(String str) {
        if (this.dataManager != null) {
            Ajx3BundleFileInfo bundleFileInfo = this.dataManager.getBundleFileInfo(str);
            if (bundleFileInfo != null) {
                return bundleFileInfo.hasBase() ? 2 : 1;
            }
        }
        return -1;
    }

    private static void initWhitList() {
        sDependenceWhitList.add("amap_bundle_resource");
        sDependenceWhitList.add("amap_bundle_lib");
        sDependenceWhitList.add("amap_bundle_lib_app");
        sDependenceWhitList.add("amap_bundle_lib_aux");
    }

    public void onContextCreate(long j, String str, String str2) {
        if (j > 0 && !TextUtils.isEmpty(str)) {
            ContextBundleInfo contextBundleInfo = new ContextBundleInfo(j, str, str2);
            this.mContextBundleMap.put(Long.valueOf(j), contextBundleInfo);
        }
    }

    public void onContextDestroy(long j) {
        if (j > 0) {
            this.mContextBundleMap.remove(Long.valueOf(j));
            retryAppend();
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized boolean addToSuspend(BundleGroup bundleGroup) {
        if (bundleGroup != null) {
            if (!this.mSuspendList.contains(bundleGroup)) {
                this.mSuspendList.add(bundleGroup);
                return true;
            }
        }
        return false;
    }

    private synchronized void removeFromSuspend(BundleGroup bundleGroup) {
        if (bundleGroup != null) {
            if (this.mSuspendList.contains(bundleGroup)) {
                this.mSuspendList.remove(bundleGroup);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized boolean needSuspend(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (ContextBundleInfo next : this.mContextBundleMap.values()) {
            if (next != null && next.contains(str)) {
                return true;
            }
        }
        for (BundleGroup contains : this.mSuspendList) {
            if (contains.contains(str)) {
                return true;
            }
        }
        return false;
    }

    private List<String> generateActiveBundleList() {
        ArrayList arrayList = new ArrayList();
        if (this.mContextBundleMap.isEmpty()) {
            return arrayList;
        }
        for (ContextBundleInfo next : this.mContextBundleMap.values()) {
            if (next != null && next.mDependence.size() > 0) {
                for (String next2 : next.mDependence) {
                    if (!TextUtils.isEmpty(next2) && !arrayList.contains(next2)) {
                        arrayList.add(next2);
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0075, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void retryAppend() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.isChecking     // Catch:{ all -> 0x0076 }
            if (r0 != 0) goto L_0x0074
            com.autonavi.minimap.ajx3.upgrade.Ajx3BundleDownloadManager r0 = r5.mDownloadManager     // Catch:{ all -> 0x0076 }
            boolean r0 = r0.isRunning()     // Catch:{ all -> 0x0076 }
            if (r0 == 0) goto L_0x000e
            goto L_0x0074
        L_0x000e:
            java.util.List<com.autonavi.minimap.ajx3.upgrade.BundleGroup> r0 = r5.mSuspendList     // Catch:{ all -> 0x0076 }
            int r0 = r0.size()     // Catch:{ all -> 0x0076 }
            if (r0 > 0) goto L_0x0018
            monitor-exit(r5)
            return
        L_0x0018:
            java.util.List r0 = r5.generateActiveBundleList()     // Catch:{ all -> 0x0076 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0076 }
            r1.<init>()     // Catch:{ all -> 0x0076 }
            int r2 = r0.size()     // Catch:{ all -> 0x0076 }
            if (r2 > 0) goto L_0x002d
            java.util.List<com.autonavi.minimap.ajx3.upgrade.BundleGroup> r0 = r5.mSuspendList     // Catch:{ all -> 0x0076 }
            r1.addAll(r0)     // Catch:{ all -> 0x0076 }
            goto L_0x004b
        L_0x002d:
            java.util.List<com.autonavi.minimap.ajx3.upgrade.BundleGroup> r2 = r5.mSuspendList     // Catch:{ all -> 0x0076 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x0076 }
        L_0x0033:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0076 }
            if (r3 == 0) goto L_0x004b
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x0076 }
            com.autonavi.minimap.ajx3.upgrade.BundleGroup r3 = (com.autonavi.minimap.ajx3.upgrade.BundleGroup) r3     // Catch:{ all -> 0x0076 }
            if (r3 == 0) goto L_0x0033
            boolean r4 = r3.couldRetryAppend(r0)     // Catch:{ all -> 0x0076 }
            if (r4 == 0) goto L_0x0033
            r1.add(r3)     // Catch:{ all -> 0x0076 }
            goto L_0x0033
        L_0x004b:
            int r0 = r1.size()     // Catch:{ all -> 0x0076 }
            if (r0 > 0) goto L_0x0053
            monitor-exit(r5)
            return
        L_0x0053:
            java.util.Iterator r0 = r1.iterator()     // Catch:{ all -> 0x0076 }
        L_0x0057:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x0076 }
            if (r1 == 0) goto L_0x0072
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x0076 }
            com.autonavi.minimap.ajx3.upgrade.BundleGroup r1 = (com.autonavi.minimap.ajx3.upgrade.BundleGroup) r1     // Catch:{ all -> 0x0076 }
            r2 = 1
            com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager$Type r3 = com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.Type.web     // Catch:{ all -> 0x0076 }
            boolean r2 = r1.addPatch(r2, r3)     // Catch:{ all -> 0x0076 }
            if (r2 == 0) goto L_0x0070
            r5.removeFromSuspend(r1)     // Catch:{ all -> 0x0076 }
            goto L_0x0057
        L_0x0070:
            monitor-exit(r5)
            return
        L_0x0072:
            monitor-exit(r5)
            return
        L_0x0074:
            monitor-exit(r5)
            return
        L_0x0076:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.retryAppend():void");
    }

    public void testPatchInfo() {
        List<String> generateActiveBundleList = generateActiveBundleList();
        StringBuilder sb = new StringBuilder();
        sb.append(" activeBundleList: ");
        for (String append : generateActiveBundleList) {
            sb.append(append);
            sb.append(" , ");
        }
        EventLogRecorder.log("activeBundleList", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder("\najx引擎版本号: ");
        sb3.append(Ajx.getInstance().getAjxEngineVersion());
        sb2.append(sb3.toString());
        String allAjxFileBaseVersion = AjxFileInfo.getAllAjxFileBaseVersion();
        if (!TextUtils.isEmpty(allAjxFileBaseVersion)) {
            allAjxFileBaseVersion = allAjxFileBaseVersion.replaceAll(";", ";\n");
        }
        sb2.append("\nbase ajx版本号: ".concat(String.valueOf(allAjxFileBaseVersion)));
        String allAjxLatestPatchVersion = AjxFileInfo.getAllAjxLatestPatchVersion();
        if (!TextUtils.isEmpty(allAjxLatestPatchVersion)) {
            sb2.append("\ndiff ajx版本号: ".concat(String.valueOf(allAjxLatestPatchVersion.replaceAll(";", ";\n"))));
        }
        EventLogRecorder.log("PatchInfo", sb2.toString());
        StringBuilder sb4 = new StringBuilder();
        for (BundleGroup next : this.mSuspendList) {
            if (next != null) {
                sb4.append(next.toString());
                sb4.append(" \n ");
            }
        }
        EventLogRecorder.log("SuspendList", sb4.toString());
    }

    /* access modifiers changed from: 0000 */
    public void showToast(String str) {
        if (!TextUtils.isEmpty(str)) {
            Message message = new Message();
            message.what = 101;
            message.obj = str;
            this.mHandler.sendMessage(message);
        }
    }

    private void sendUpdatableRollback(UpdatableRollbackRequest updatableRollbackRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            updatableRollbackRequest.addHeaders(dkn.d);
            updatableRollbackRequest.setTimeout(dkn.b);
            updatableRollbackRequest.setRetryTimes(dkn.c);
        }
        updatableRollbackRequest.build();
        if (dkn != null) {
            in.a().a((AosRequest) updatableRollbackRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) updatableRollbackRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    private void sendOnlineLog(OnlineLogRequest onlineLogRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            onlineLogRequest.addHeaders(dkn.d);
            onlineLogRequest.setTimeout(dkn.b);
            onlineLogRequest.setRetryTimes(dkn.c);
        }
        onlineLogRequest.build();
        if (dkn != null) {
            in.a().a((AosRequest) onlineLogRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) onlineLogRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
