package com.autonavi.minimap.ajx3.debug;

import android.os.Build;
import android.os.Process;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.log.LogConfig;
import com.autonavi.minimap.ajx3.log.LogConfig.Builder;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.util.ToastUtils;
import java.net.URLEncoder;

public class EagleEyeUtil {
    public static void openEagleEye() {
        LogManager.close();
        StringBuilder sb = new StringBuilder();
        sb.append(Process.myPid());
        LogConfig build = new Builder().setId(sb.toString()).setDevice(Build.MODEL).setDiu(URLEncoder.encode(NetworkParam.getTaobaoID())).setDiv(NetworkParam.getDiv()).setDibv(NetworkParam.getDibv()).setPlatform("android").setBasejsVersion(Ajx.getInstance().getBaseJsVersion()).build();
        if (checkLogConfig(build)) {
            LogManager.init(build);
            LogManager.connect(AjxInspector.getFileContent(AjxSocketHandler.IP_PATH).replaceAll("\\n", "").replaceAll("\\r", ""));
        }
    }

    public static void closeEagleEye() {
        LogManager.close();
    }

    private static boolean checkLogConfig(LogConfig logConfig) {
        if (logConfig.getBasejsVersion() == null) {
            ToastUtils.showToast("BasejsVersion is null", 1);
            return false;
        } else if (logConfig.getDevice() == null) {
            ToastUtils.showToast("Device is null", 1);
            return false;
        } else if (logConfig.getDibv() == null) {
            ToastUtils.showToast("dibv is null", 1);
            return false;
        } else if (logConfig.getDiu() == null) {
            ToastUtils.showToast("tid is null", 1);
            return false;
        } else if (logConfig.getDiv() == null) {
            ToastUtils.showToast("div is null", 1);
            return false;
        } else if (logConfig.getId() == null) {
            ToastUtils.showToast("pid is null", 1);
            return false;
        } else if (logConfig.getPlatform() != null) {
            return true;
        } else {
            ToastUtils.showToast("platform is null", 1);
            return false;
        }
    }
}
