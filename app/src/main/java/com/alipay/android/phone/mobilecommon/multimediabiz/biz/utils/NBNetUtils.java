package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.mobile.common.nbnet.api.NBNetFactory;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadClient;
import com.alipay.mobile.common.transport.TransportStrategy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NBNetUtils {
    private static Boolean a = null;
    private static Boolean b = null;

    public static NBNetDownloadClient getNBNetDownloadClient() {
        return NBNetFactory.getDefault().getDownloadClient();
    }

    public static NBNetUploadClient getNBNetUploadClient() {
        return NBNetFactory.getDefault().getUploadClient();
    }

    public static boolean getNBNetDLSwitch() {
        boolean z;
        boolean ret1 = ConfigManager.getInstance().getNBNetDlSwitch();
        boolean ret2 = TransportStrategy.isEnableNBNetDLSwitch();
        if (a == null) {
            if (!ret1 || !ret2) {
                z = false;
            } else {
                z = true;
            }
            a = Boolean.valueOf(z);
            Logger.D("NBNetUtils", "getNBNetSwitch ret1=" + ret1 + ";ret2=" + ret2, new Object[0]);
        }
        if (!ret1 || !ret2) {
            return false;
        }
        return true;
    }

    public static boolean getNBNetDLSwitch(String business) {
        if (!getNBNetDLSwitch() || !a(ConfigManager.getInstance().getNBNetBizConfig().dlbizs, ConfigManager.getInstance().getNBNetBizConfig().copyFromShareStats(), business)) {
            return false;
        }
        return true;
    }

    public static boolean getNBNetUPSwitch(String business) {
        if (!getNBNetUPSwitch() || !a(ConfigManager.getInstance().getNBNetBizConfig().upbizs, business)) {
            return false;
        }
        return true;
    }

    public static boolean getNBNetUPSwitch() {
        boolean z;
        boolean ret1 = ConfigManager.getInstance().getNBNetUpSwitch();
        boolean ret2 = TransportStrategy.isEnableNBNetUPSwitch();
        if (b == null) {
            if (!ret1 || !ret2) {
                z = false;
            } else {
                z = true;
            }
            b = Boolean.valueOf(z);
            Logger.D("NBNetUtils", "getNBNetUPSwitch ret1=" + ret1 + ";ret2=" + ret2, new Object[0]);
        }
        if (!ret1 || !ret2) {
            return false;
        }
        return true;
    }

    public static boolean isNBNetUpFileSizeCheck() {
        return ConfigManager.getInstance().getCommonConfigItem().httpClientConf.nbNetUPSizeSwitch == 1;
    }

    public static int getNBNetUpFileSizeLimit() {
        return ConfigManager.getInstance().getCommonConfigItem().fileUpSizeLimit;
    }

    private static boolean a(HashMap<String, Float> bizs, HashMap<String, String> blackBizs, String business) {
        if (ConfigManager.getInstance().getNBNetBizConfig().dlSwitch != 0 && !b(blackBizs, business)) {
            return c(bizs, business);
        }
        return false;
    }

    private static boolean a(HashMap<String, Float> bizs, String business) {
        if (ConfigManager.getInstance().getNBNetBizConfig().upSwitch == 0) {
            return true;
        }
        return c(bizs, business);
    }

    private static boolean b(HashMap<String, String> blackBizs, String business) {
        if (TextUtils.isEmpty(business) || blackBizs == null) {
            return false;
        }
        String target = null;
        Set keySet = blackBizs.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return false;
        }
        Iterator<String> it = keySet.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String key = it.next();
            if (business.startsWith(key)) {
                target = key;
                break;
            }
        }
        if (!TextUtils.isEmpty(target)) {
            return true;
        }
        return false;
    }

    private static boolean c(HashMap<String, Float> bizs, String business) {
        if (TextUtils.isEmpty(business) || bizs == null) {
            return false;
        }
        String target = null;
        Set keySet = bizs.keySet();
        if (keySet == null || keySet.size() <= 0) {
            return false;
        }
        Iterator<String> it = keySet.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String key = it.next();
            if (business.contains(key)) {
                target = key;
                break;
            }
        }
        if (TextUtils.isEmpty(target)) {
            return false;
        }
        Float rate = bizs.get(target);
        if (rate == null || Math.random() >= ((double) rate.floatValue())) {
            return false;
        }
        return true;
    }

    public static int getDwonloadFileConfigTimeout() {
        return ConfigManager.getInstance().getCommonConfigItem().net.nbnetFileDownloadTimeOut;
    }

    public static int getDownloadImageConfigTimeout() {
        return ConfigManager.getInstance().getCommonConfigItem().net.nbnetImageDownloadTimeOut;
    }
}
