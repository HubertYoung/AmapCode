package com.alipay.mobile.nebulauc.plugin;

import android.os.Process;
import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.storage.sp.APSharedPreferences;
import com.alipay.android.phone.mobilesdk.storage.sp.SharedPreferencesManager;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.DownloadService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.util.CommonUtil;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;

public class H5UpdateUCCorePlugin extends H5SimplePlugin {
    private static final String ACTION_DOWNLOAD_UC_CORE = "downloadUCCore";
    private static final String ACTION_RELOAD_UC_CORE = "reloadUCCore";
    private static final int ARGUMENT_ERROR = 102;
    private static final String TAG = "H5UpdateUCCorePlugin";
    private static final int USER_ID_ERROR = 103;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_DOWNLOAD_UC_CORE);
        filter.addAction(ACTION_RELOAD_UC_CORE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (ACTION_DOWNLOAD_UC_CORE.equals(action)) {
            updateUCCore(event, bridgeContext);
        } else if (ACTION_RELOAD_UC_CORE.equals(action)) {
            if (H5Utils.isDebuggable(H5Utils.getContext()) || "yes".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_enableUpdateUCCore"))) {
                Process.killProcess(Process.myPid());
            } else {
                CommonUtil.sendError(bridgeContext, 103, "uid not allow");
            }
        }
        return true;
    }

    private void updateUCCore(H5Event event, final H5BridgeContext bridgeContext) {
        if (H5Utils.isDebuggable(H5Utils.getContext()) || "yes".equalsIgnoreCase(H5ConfigUtil.getConfig("h5_enableUpdateUCCore"))) {
            final String coreVersion = H5Utils.getString(event.getParam(), (String) "coreVersion", (String) "");
            String classifier = H5Utils.getString(event.getParam(), (String) "classifier", (String) "");
            String downloadUrl = H5Utils.getString(event.getParam(), (String) "url", (String) "");
            if (TextUtils.isEmpty(coreVersion) || TextUtils.isEmpty(downloadUrl) || TextUtils.isEmpty(classifier)) {
                CommonUtil.sendError(bridgeContext, 102, "UC core version is empty");
                return;
            }
            final String fileName = LauncherApplicationAgent.getInstance().getApplicationContext().getDir("plugins_lib", 0) + "/" + classifier.substring(8, classifier.length());
            DownloadRequest request = new DownloadRequest(downloadUrl);
            request.setPath(fileName);
            request.setTransportCallback(new TransportCallback() {
                public void onCancelled(Request request) {
                }

                public void onPreExecute(Request request) {
                }

                public void onPostExecute(Request request, Response response) {
                    APSharedPreferences preferences = SharedPreferencesManager.getInstance(H5Utils.getContext(), "h5_ucsdkLocalUpdatePath");
                    preferences.putString("path", fileName);
                    preferences.putString("version", coreVersion);
                    preferences.commit();
                    bridgeContext.sendBridgeResult("result", Boolean.valueOf(true));
                }

                public void onProgressUpdate(Request request, double v) {
                }

                public void onFailed(Request request, int i, String s) {
                    CommonUtil.sendError(bridgeContext, i, s);
                }
            });
            ((DownloadService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(DownloadService.class.getName())).addDownload(request);
            return;
        }
        CommonUtil.sendError(bridgeContext, 103, "uid not allow");
    }
}
