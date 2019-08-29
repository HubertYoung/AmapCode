package com.alipay.mobile.nebulauc.impl.setup;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.nebulaucsdk.UcSdkConstants;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.UCSetupException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UcSetupExceptionHelper {
    public static final int ERROR_TYPE_FROM_ERROR = 30002;
    public static final int ERROR_TYPE_FROM_INIT = 30003;
    public static final int ERROR_TYPE_FROM_WARN = 30001;
    public static final String H5_UC_INIT_FAIL_INFO = "h5_uc_init_fail_info";
    public static final String H5_UC_RETRY_FLAG = "h5_uc_retry_flag";
    public static final String TAG = "UcSetupExceptionHelper";
    public static List<String> errorCodeList = new ArrayList();
    public static boolean isResultUpload = false;
    public static boolean isRetryInitUc = false;
    public static String lastErrorInfo = "";
    public static Map<String, String> setupKeyMap = new HashMap();
    public static String ucErrorCode = "";
    public static String ucErrorInfo = "";

    static {
        errorCodeList.add("1001");
        errorCodeList.add("1002");
        errorCodeList.add(Result.TAOBAO_ACTIVE);
        errorCodeList.add("1004");
        errorCodeList.add("1005");
        errorCodeList.add("1006");
        errorCodeList.add("1007");
        errorCodeList.add("1008");
        errorCodeList.add("1009");
        errorCodeList.add("1010");
        errorCodeList.add("1011");
        errorCodeList.add("2001");
        errorCodeList.add("2002");
        errorCodeList.add("2003");
        errorCodeList.add("2004");
        errorCodeList.add(FrameworkMonitor.MICROAPP_STARTUP_FAIL_RESTART_FAIL);
        errorCodeList.add(FrameworkMonitor.MICROAPP_STARTUP_FAIL_TINYAPP_FAIL);
        errorCodeList.add(FrameworkMonitor.MICROAPP_STARTUP_FAIL_NEED_LOGIN);
        errorCodeList.add(FrameworkMonitor.MICROAPP_STARTUP_FAIL_WAIT_AUTH);
        errorCodeList.add("3001");
        errorCodeList.add("3003");
        errorCodeList.add("3005");
        errorCodeList.add("3007");
        errorCodeList.add("4001");
        errorCodeList.add(Result.RUBBISH_ACCOUNT);
        errorCodeList.add("4007");
        errorCodeList.add("4012");
        errorCodeList.add("4016");
        errorCodeList.add("6015");
        errorCodeList.add("9004");
        errorCodeList.add("9101");
        errorCodeList.add("-99999");
        setupKeyMap.put("OPTION_VIDEO_HARDWARE_ACCELERATED", UCCore.OPTION_VIDEO_HARDWARE_ACCELERATED);
        setupKeyMap.put("OPTION_WEBVIEW_POLICY", UCCore.OPTION_WEBVIEW_POLICY);
        setupKeyMap.put("OPTION_SETUP_THREAD_PRIORITY", UCCore.OPTION_SETUP_THREAD_PRIORITY);
    }

    public static void logUcInitException(Throwable throwable, int errorType) {
        if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("h5_log_uc_init_exception")) && throwable != null) {
            try {
                String errString = throwable.getMessage();
                ucErrorCode = getErrorCodeFromThrowable(throwable);
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_UC_INIT_EXCEPTION_DETAIL").param1().add("isTinyApp", String.valueOf(H5Utils.isInTinyProcess())).add("errorType", Integer.valueOf(errorType)).add("ucVersion", UcSdkConstants.UC_VERSION).add("isRetry", Boolean.valueOf(isRetryInitUc)).add("errorCode", ucErrorCode).param2().add(getExtraReportInfo(), null).param4().add("ext0", errString));
                saveUcErrorInfo(errString);
            } catch (Throwable t) {
                H5Log.e(TAG, "throwable : ", t);
            }
        }
    }

    private static String getErrorCodeFromThrowable(Throwable t) {
        if (t instanceof UCSetupException) {
            return ((UCSetupException) t).errCode() + "";
        }
        return "";
    }

    private static String getExtraReportInfo() {
        String extraInfo = "model=" + Build.MODEL + "^sdk_int=" + VERSION.SDK_INT + "^manufacturer=" + Build.MANUFACTURER + "^board=" + Build.BOARD;
        H5Service service = H5ServiceUtils.getH5Service();
        if (service == null) {
            return extraInfo;
        }
        H5Page page = service.getTopH5Page();
        if (page == null) {
            return extraInfo;
        }
        return extraInfo + "^appId=" + H5Utils.getString(page.getParams(), (String) "appId");
    }

    private static void saveUcErrorInfo(String errStr) {
        if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("h5_uc_init_retry")) && !TextUtils.isEmpty(errStr) && TextUtils.isEmpty(ucErrorInfo)) {
            String saveStr = errStr.replaceAll("\\n\\t", "");
            if (!TextUtils.isEmpty(saveStr) && saveStr.length() > 200) {
                saveStr = saveStr.substring(0, 200);
            }
            if (containRetryCode(saveStr)) {
                ucErrorInfo = saveStr;
                saveStringConfig(H5_UC_INIT_FAIL_INFO, ucErrorInfo);
                if (!TextUtils.isEmpty(lastErrorInfo) && isRetryInitUc) {
                    uploadUcRetryResult(false);
                }
            }
        }
    }

    public static boolean isRetryInitUc() {
        if (!inRightProcess()) {
            H5Log.d(TAG, "not inRightProcess");
            return false;
        }
        String failStr = getStringConfig(H5_UC_INIT_FAIL_INFO, "");
        String retryFlag = getStringConfig(H5_UC_RETRY_FLAG, "");
        if (TextUtils.isEmpty(failStr) || !TextUtils.isEmpty(retryFlag)) {
            H5Log.d(TAG, "failStr : " + failStr + " retryFlag : " + retryFlag);
            return false;
        }
        lastErrorInfo = failStr;
        saveStringConfig(H5_UC_INIT_FAIL_INFO, "");
        saveStringConfig(H5_UC_RETRY_FLAG, ActionConstant.TYPE_RETRY);
        isRetryInitUc = true;
        H5Log.d(TAG, "isRetryInitUc : " + isRetryInitUc);
        return true;
    }

    private static boolean inRightProcess() {
        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
        if (h5EventHandlerService != null) {
            return H5Utils.isMainProcess() && h5EventHandlerService.isAllLiteProcessHide();
        }
        return H5Utils.isMainProcess();
    }

    public static void uploadUcRetryResult(boolean success) {
        if (!isResultUpload) {
            try {
                isResultUpload = true;
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_UC_INIT_RETRY_RESULT").param1().add("isTinyApp", String.valueOf(H5Utils.isInTinyProcess())).add("success", String.valueOf(success)).add("errorCode", ucErrorCode).param4().add("ucErrorInfo", ucErrorInfo).add("lastErrorInfo", lastErrorInfo));
            } catch (Throwable t) {
                H5Log.e(TAG, "throwable : ", t);
            }
        }
    }

    private static boolean containRetryCode(String errStr) {
        if (errorCodeList != null && errorCodeList.size() > 0) {
            for (String code : errorCodeList) {
                if (errStr.contains(code)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void saveStringConfig(String key, String value) {
        try {
            if (!H5Utils.isMainProcess()) {
                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                if (h5EventHandlerService != null) {
                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                    if (h5IpcServer != null) {
                        h5IpcServer.setStringConfig(key, value);
                        return;
                    }
                    return;
                }
                return;
            }
            H5DevConfig.setStringConfig(key, value);
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
    }

    private static String getStringConfig(String key, String df) {
        try {
            if (H5Utils.isMainProcess()) {
                return H5DevConfig.getStringConfig(key, df);
            }
            H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
            if (h5EventHandlerService == null) {
                return "";
            }
            H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
            if (h5IpcServer != null) {
                return h5IpcServer.getStringConfig(key, df);
            }
            return "";
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            return "";
        }
    }
}
