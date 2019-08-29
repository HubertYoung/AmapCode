package com.alipay.mobile.common.transport.strategy;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.ConnectionUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import java.util.List;

public class StrategyUtil {
    public static final boolean isUse4Net(Context context, String disabledNetKey) {
        if (context == null) {
            return false;
        }
        int connType = ConnectionUtil.getConnType(context);
        if (TextUtils.isEmpty(disabledNetKey) || !disabledNetKey.contains(String.valueOf(connType))) {
            return true;
        }
        return false;
    }

    public static final boolean isUse4SdkVersion(String disabledSdkVersion) {
        if (TextUtils.isEmpty(disabledSdkVersion) || !disabledSdkVersion.contains(String.valueOf(VERSION.SDK_INT))) {
            return true;
        }
        return false;
    }

    public static final boolean isUse4Brand(String brandBlackList) {
        if (TextUtils.isEmpty(brandBlackList) || TextUtils.isEmpty(Build.BRAND) || !brandBlackList.contains(Build.BRAND.trim().toLowerCase().replaceAll("\\s+", "_"))) {
            return true;
        }
        return false;
    }

    public static final boolean isUse4Model(String modelBlackList) {
        if (TextUtils.isEmpty(modelBlackList) || TextUtils.isEmpty(Build.MODEL) || !modelBlackList.contains(Build.MODEL.trim().toLowerCase().replaceAll("\\s+", "_"))) {
            return true;
        }
        return false;
    }

    public static final boolean isUse4Hardware(String cpuModelBackList) {
        if (TextUtils.isEmpty(cpuModelBackList) || TextUtils.isEmpty(Build.HARDWARE) || !cpuModelBackList.contains(Build.HARDWARE.trim().toLowerCase().replaceAll("\\s+", "_"))) {
            return true;
        }
        return false;
    }

    public static final boolean grayscaleUtdid(String utdid, int[] grayValues) {
        return MiscUtils.grayscaleUtdid(utdid, grayValues);
    }

    public static final boolean isUse4OperationType(List<String> operationTypeList, String operationType) {
        if (!operationTypeList.contains("ALL") && !operationTypeList.contains("ALL1") && !operationTypeList.contains(operationType)) {
            return false;
        }
        return true;
    }

    public static final boolean isSwitchRpc(String operationType) {
        if (TextUtils.isEmpty(operationType)) {
            return false;
        }
        if (TextUtils.equals(operationType, "alipay.client.switches.all.get.afterlogin") || operationType.contains("alipay.client.switches.all.get")) {
            return true;
        }
        return false;
    }
}
