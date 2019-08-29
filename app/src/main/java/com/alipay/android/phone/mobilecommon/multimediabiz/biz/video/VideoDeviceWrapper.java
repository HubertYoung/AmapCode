package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.VideoConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.BeautyConfigItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.LiveConfigItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.streammedia.encode.utils.OMXConfig;
import com.alipay.streammedia.encode.utils.OMXConfigUtil;

public class VideoDeviceWrapper {
    public static final int CPU_ENCODE_DEFAULT_LEVEL = 4;
    private static Boolean a = null;
    private static VideoConfig b = null;
    private static OMXConfig c = null;
    private static Boolean d = null;
    private static String[] e = {"meizu&m353", "meizu&m351"};
    private static String f = "{\"cmdc\":{\"m623c\":{\"19\":\"10\"}},\n\"htc\":{\"htc m8w\":{\"19\":\"10\"},\"htc d816t\":{\"23\":\"10\"}},\n\"gionee\":{\"m5\":{\"22\":\"10\"},\"f103\":{\"21\":\"10\"},\"gn9000\":{\"19\":\"10\"}},\n\"tcl\":{\"tcl m2m\":{\"19\":\"6\"}},\n\"zte\":{\"n918st\":{\"19\":\"10\"},\"nx508j\":{\"19\":\"10\"}},\n\"huawei\":{\"huawei mt7-cl00\":{\"22\":\"10\"},\"che2-ul00\":{\"19\":\"6\"},\"huawei nxt-tl00\":{\"23\":\"10\"},\"huawei ale-cl00\":{\"19\":\"10\"},\"che-tl00h\":{\"21\":\"10\"},\"huawei tag-al00\":{\"22\":\"10\"},\"nexus 6p\":{\"23\":\"10|||1\"},\"vie-al10\":{\"23\":\"10\"},\"chm-cl00\":{\"19\":\"10\"},\"huawei p7-l09\":{\"22\":\"6\"},\"che1-cl20\":{\"19\":\"10\"},\"huawei rio-al00\":{\"22\":\"10\"}},\n\"meizu\":{\"m2 note\":{\"22\":\"10\"},\"pro 6\":{\"23\":\"10\"},\"m3 note\":{\"22\":\"10\"},\"mx5\":{\"22\":\"10\"},\"m1 note\":{\"19\":\"6\"},\"m1 metal\":{\"22\":\"10\"},\"m570c\":{\"22\":\"10\"}},\n\"letv\":{\"x600\":{\"21\":\"10\"},\"x800\":{\"21\":\"10|||1\"},\"x900\":{\"21\":\"10|||1\"}},\n\"nubia\":{\"nx508j\":{\"21\":\"10\"},\"nx523j_v1\":{\"22\":\"10|||1\"},\"nx511j\":{\"21\":\"10|||1\"}},\n\"meitu\":{\"meitu m4\":{\"19\":\"6\"},\"mp1503\":{\"23\":\"10\"}},\n\"qiku\":{\"8681-a01\":{\"22\":\"10\"},\"8692-a00\":{\"22\":\"10\"}},\n\"yusun\":{\"hlj-xl\":{\"19\":\"10\"}},\n\"xiaomi\":{\"2014813\":{\"19\":\"10\"},\"redmi 3\":{\"22\":\"10\"},\"mi 4lte\":{\"23\":\"10\"},\"mi-4c\":{\"22\":\"10\"},\"mi 2\":{\"19\":\"10\"},\"hm note 1s\":{\"19\":\"10\"},\"2014821\":{\"19\":\"10\"},\"mi 4w\":{\"23\":\"10\"},\"mi 5\":{\"23\":\"10|||1\"},\"redmi note 3\":{\"21\":\"10\",\"22\":\"10\"},\"redmi note 2\":{\"21\":\"12\"},\"hm note 1td\":{\"19\":\"10\"}},\n\"lge\":{\"lg-d802\":{\"19\":\"10\"}},\n\"lenovo\":{\"lenovo a808t\":{\"19\":\"6\"},\"lenovo s90-t\":{\"19\":\"10\"},\"lenovo k30-t\":{\"19\":\"10\"},\"lenovo s820\":{\"19\":\"6\"}},\n\"smartisan\":{\"sm801\":{\"22\":\"10\"}},\n\"sony\":{\"l50u\":{\"19\":\"10\"}},\n\"lemobile\":{\"le x620\":{\"23\":\"10\"},\"le x820\":{\"23\":\"10|||1\"}},\n\"motorola\":{\"xt1085\":{\"22\":\"10\"}},\n\"samsung\":{\"sm-n9109w\":{\"22\":\"10\"},\"sm-g9008v\":{\"21\":\"10\"},\"gt-i9500\":{\"19\":\"10\"},\"sm-a5000\":{\"19\":\"10\"},\"sm-n9008\":{\"21\":\"10\"},\"sm-n9009\":{\"19\":\"10\",\"21\":\"10\",\"22\":\"10\"},\"sm-a8000\":{\"22\":\"10\"},\"gt-i9300\":{\"19\":\"6\"},\"sm-g9300\":{\"23\":\"10\"},\"sm-n9008v\":{\"19\":\"10\"},\"sm-n9006\":{\"21\":\"10\"},\"sm-g9350\":{\"23\":\"10|||1\"},\"sm-g9308\":{\"23\":\"10|||1\"},\"sm-g5308w\":{\"19\":\"10\"}},\n\"vivo\":{\"vivo x6d\":{\"22\":\"10\"},\"vivo x5m\":{\"21\":\"10\"},\"vivo x5pro d\":{\"21\":\"10\"},\"vivo x6s a\":{\"22\":\"10\"},\"vivo xplay5a\":{\"22\":\"10\"},\"vivo x5s l\":{\"19\":\"10\"}},\n\"zuk\":{\"zuk z2121\":{\"23\":\"10|||1\"}},\n\"oneplus\":{\"a0001\":{\"22\":\"10\"},\"one a2001\":{\"22\":\"10|||1\"}},\n\"oppo\":{\"a31\":{\"19\":\"10\"},\"oppo r7s\":{\"19\":\"6\"},\"oppo r9tm\":{\"22\":\"10\"},\"r7plusm\":{\"22\":\"10\"},\"oppo r7sm\":{\"22\":\"10\"},\"1107\":{\"19\":\"10\"},\"a31u\":{\"19\":\"10\"},\"oppo r9m\":{\"22\":\"10\"},\"oppo r9 plustm a\":{\"22\":\"10\"}}}\n";

    public static boolean isEncoderSupportHard() {
        boolean z;
        if (a == null) {
            if (VERSION.SDK_INT < 18) {
                z = true;
            } else {
                z = false;
            }
            a = Boolean.valueOf(z);
            Logger.D("VideoDeviceWrapper", "isLiveUseCpuEncode: " + a, new Object[0]);
        }
        if (!a.booleanValue()) {
            return true;
        }
        return false;
    }

    public static VideoConfig getVideoConfig() {
        VideoConfig videoDeviceConfig = ConfigManager.getInstance().getVideoDeviceConfig();
        b = videoDeviceConfig;
        return videoDeviceConfig;
    }

    public static int[] getWaterMarkVideoSize() {
        int[] size = {360, 640};
        try {
            String val = ConfigManager.getInstance().getDeviceSubConfig(ConfigConstants.APMULTIMEDIA_DEVICE_CONFIG_VIDEOSIZE_KEY);
            if (!TextUtils.isEmpty(val) && val.contains(MergeUtil.SEPARATOR_KV)) {
                String[] items = val.split("\\|");
                size[0] = Integer.parseInt(items[0]);
                size[1] = Integer.parseInt(items[1]);
            }
        } catch (Exception e2) {
            Logger.D("VideoDeviceWrapper", "getWaterMarkVideoSize exp=" + e2.toString(), new Object[0]);
        }
        Logger.D("VideoDeviceWrapper", "getWaterMarkVideoSize w=" + size[0] + ";h=" + size[1], new Object[0]);
        return size;
    }

    public static boolean isLivePlayHardDecode() {
        int type = ConfigManager.getInstance().getCommonConfigItem().liveConf.decodeType;
        String config = ConfigManager.getInstance().getLivePlayDecodeConfig();
        if (!TextUtils.isEmpty(config)) {
            try {
                String[] items = config.split("\\|");
                if (!TextUtils.isEmpty(items[0])) {
                    type = Integer.parseInt(items[0]);
                }
            } catch (Exception e2) {
                Logger.D("VideoDeviceWrapper", "isLivePlayHardDecode exp=" + e2.toString(), new Object[0]);
            }
        }
        if (type == 1) {
            return true;
        }
        return false;
    }

    public static LiveConfigItem getLiveConfig() {
        return LiveConfigItem.parseLiveConfig(ConfigManager.getInstance().getCommonConfigItem().liveConf, ConfigManager.getInstance().getLiveConfig("{\"0|1\":{\"samsung\":\"sm-g9006|gt-i9500|sm-g9006v|sm-g9008v\",\"huawei\":\"huawei nxt-al10|huawei mt7|h60-l01|h60-l03\",\"xiaomi\":\"hm note 1lte|mi 4|mi 3|mi note|mi note lte|mi 3w|mi-4c|mi 4w\",\"meizu\":\"mx4 pro|m462|m355\",\"vivo\":\"vivo x6d|vivo x5|vivo x5max l|x5pro d\",\"yulong\":\"coolpad 8670\",\"motorola\":\"nexus 6\",\"smartisan\":\"sm701\",\"oppo\":\"r7plusm|r8107\"}}"));
    }

    public static BeautyConfigItem getBeautyConfig() {
        return parseBeautyConfig(ConfigManager.getInstance().getBeautyConfig());
    }

    public static BeautyConfigItem parseBeautyConfig(String param) {
        BeautyConfigItem item = new BeautyConfigItem();
        try {
            if (!TextUtils.isEmpty(param)) {
                String[] items = param.split("\\|");
                int size = items.length;
                item.type = Integer.parseInt(items[0]);
                if (size > 1) {
                    item.bvLevel = Integer.parseInt(items[1]);
                }
            }
        } catch (Exception e2) {
            Logger.D("VideoDeviceWrapper", "parseBeautyConfig exp=" + e2.toString(), new Object[0]);
        }
        return item;
    }

    public static boolean dynPermissionCheck() {
        if (d == null) {
            d = Boolean.valueOf(CompareUtils.strInIgnoreCase((Build.MANUFACTURER + "&" + Build.MODEL).toLowerCase(), e));
            Logger.D("VideoDeviceWrapper", "dynPermissionCheck: " + d, new Object[0]);
        }
        return d.booleanValue();
    }

    public static OMXConfig getVideoOMXConfig() {
        if (c == null) {
            try {
                c = OMXConfigUtil.parse(f, ConfigManager.getInstance().getVideoOMXconfig());
            } catch (Exception e2) {
                Logger.E((String) "VideoDeviceWrapper", (Throwable) e2, (String) "getVideoOMXConfig exp!!!", new Object[0]);
            }
        }
        return c;
    }
}
