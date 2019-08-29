package com.alipay.mobile.beehive.util;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import java.util.HashMap;
import java.util.Map;

public class BankCardColorUtil {
    public static final int BLUE = 4886754;
    public static final int BLUE2 = 43758;
    public static final int GRAY = 14342874;
    public static final int GREEN = 8701506;
    private static final TraceLogger LOG = LoggerFactory.getTraceLogger();
    private static final String LOG_TAG = BankCardColorUtil.class.getName();
    public static final int ORANGE = 16756006;
    public static final int RED = 14768737;
    private static Map<String, GradientColor> colorMap = new HashMap();
    private static Map<String, Integer> singleColorMap = new HashMap();

    static {
        String[] redBank = {"DLCZB", "BJBANK", "SPABANK", "GSBANK", "DYLSCB", "HXBANK", "HRXJB", "CMB", "CDN_GDB", "GDB", "BOQH", "CDN_BJBANK", "BJRCB", "CITIC", "TSBANK", "BOD", "BOCCCL", "EGBANK", "BODD", "ICBC", "HBC", "BOJZ", "JZCBANK", "JSB", "BOP", "UBCHN", "JHBANK", "YNRCC", "QJCCB", "NBBANK", "TLBANK", "JZBANK", "ASCB", "WZCB", "DLB", "ZYCBANK", "ZZBANK", "HSBANK", "HKBEA", "CRCBANK", "NXBANK", "GCB", "CSCB", "FDB", "QDCCB", "BOC"};
        String[] greenBank = {"SHRCB", "BSB", "ANTBANK", "NCB", "CDRCB", "CQBANK", "ZZBANK", "BHB", "YTBANK", "FBBANK", "ZJNX", "NBCBANK", "CMBC", "ABC", "PSBC", "NMGNXS", "SCRCU", "SCB", "BOSZ"};
        String[] blueBank = {"ZYB", "XCYH", "WOORI", "HBRCB", "BOQZ", "HZCB", "BOYK", "JSBANK", "CEBBANK", "CDN_SHRCB", "BOL", "CITICN", "SPDB", "COMM", "CIB", "CCB", "SHBANK", "GLBANK", "HKB", "LNRCC", "ZZYH", "TZCB"};
        for (int i = 0; i < 46; i++) {
            colorMap.put(redBank[i], GradientColor.RED);
        }
        for (int i2 = 0; i2 < 19; i2++) {
            colorMap.put(greenBank[i2], GradientColor.GREEN);
        }
        for (int i3 = 0; i3 < 22; i3++) {
            colorMap.put(blueBank[i3], GradientColor.BLUE);
        }
        for (int i4 = 0; i4 < 46; i4++) {
            singleColorMap.put(redBank[i4], Integer.valueOf(RED));
        }
        for (int i5 = 0; i5 < 19; i5++) {
            singleColorMap.put(greenBank[i5], Integer.valueOf(GREEN));
        }
        for (int i6 = 0; i6 < 22; i6++) {
            singleColorMap.put(blueBank[i6], Integer.valueOf(BLUE));
        }
        singleColorMap.put("ALIPAY", Integer.valueOf(BLUE2));
        singleColorMap.put("CASH", Integer.valueOf(ORANGE));
        singleColorMap.put("DEFAULT", Integer.valueOf(GRAY));
    }

    public static GradientColor getGradientColor(String instId) {
        GradientColor res = colorMap.get(instId);
        if (res != null) {
            return res;
        }
        LOG.error(LOG_TAG, "there is no given gradient color for instId:" + instId);
        return GradientColor.BLUE;
    }

    public static int getBankColor(String iconId) {
        Integer res = singleColorMap.get(iconId);
        return res == null ? BLUE2 : res.intValue();
    }

    public static void test() {
    }
}
