package defpackage;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.zoloz.toyger.BuildConfig;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* renamed from: kx reason: default package */
/* compiled from: OperatorUtil */
public final class kx {
    static String[] a = {"中国联通", "中国联通,", "CHINA UNICOM", "G3伴侣", "UNICOM", "中國聯通", ",中国联通", "46001", "CHN Unicom", "China Unicom", "China Unicom,", "CN UNICOM", "联通新时空", ",China Unicom", "CUCC", "chn-cugsm", "46010", "联通"};
    static String[] b = {"T-Mobile"};
    static String[] c = {"46003", "ctnet", "中国电信,", "禁用漫游指示符", "China Telecom", "China Telecom,", "460003", "中國電信", "46003,", "China Telecomm", "ChinaNet"};
    static String[] d = {"中国移动", "中国移动,", ",中国移动", "China Mobile", "CT-GSM", ",China Mobile", "China Mobile,", "CHINA PTT", "46000", "46002", "china mobile", "@CMCC", "中国移动-3G", "China mobile", "China Mobile Communication Corp.", "CMCC", "移动", "动感地带", "神州行"};
    static String[] e = {"CHINA MOBILE 3G", "䜀㌀㑏ꍏ", ",", "OpenBTS", "3", "CT Macao", "AT&T", "one2free", "SmarTone HK", "PCCW-HKT", "DiGi", "LG U", "CTM", "MY MAXIS", "OpenBTS@", "Sprint", "olleh", "只能拨打紧急呼救电话", "遠傳電信", "Chunghwa Telecom", BuildConfig.UI_ORANGE, "NWMOBILE", "正在搜索服务", "TURKCELL", "远传电信", "DEFACE", "TW Mobile", "AVEA", "dtac", "SMART", "VODAFONE TR", "DTAC", "亚太电信", "China Mobile HK", "Chunghwa", "StarHub", "vodafone IT", "台灣大哥大", "TELKOMSEL", "Far EasTone", "U Mobile", "STARHUB", "3 Macau", "-1", "PCCW", "中華電信", "CU", "MY CELCOM", "YES OPTUS", "Beeline", "I WIND", "3(2G)", "ROGERS", "MPTGSM", "movistar", "FarEasTone", "TRUE", "Globe Telecom", "I TIM", "TR TURKCELL", "SGP-M1", "Vodafone AU", "Range", "中华电信", "SmarToneVodafone", "VN MOBIFONE", "Orange F", "vodafone NZ", "UNITEL", "O2 - UK", "NTT DOCOMO", "�动", "Lycamobile", "Taiwan Mobile Co. Ltd", "YNK", "du", "TH GSM", "VINAPHONE", "MegaFon", "SoftBank", "2degrees", "Singapore Telecom", "o2 - de", "Viettel Mobile", "AirTel", "vodafone AU", "UNICOM HK", "Vodafone", AUScreenAdaptTool.PREFIX_ID, "启用漫游指示符", "China Mobile Hong Kong Company Limited", "DIGITEL", "U.S. Cellular", "Claro", "Verizon Wireless", "0,", "Globe", "460092", "Vodafone IN", "airtel", "VIETTEL", "MTS-RUS", "Vodafone.de", "LAO GSM", "SmarTone", "Fido", "Verizon", "MTS", "SUN", "Hong Kong CSL Limited", "F-Bouygues Telecom", "VN VINAPHONE", "1O1O", "SGP-M1-3GSM", "vodafone UK", "HK CSL", "ASIACELL", "AIRTEL", "Telcel GSM", "Myanmar Post and Telecommunication", "SmarTone Mobile Comms", "正在搜索服务…", "中国电信（澳门）", "漫游指示符正在闪烁", "MOBITEL - KHM"};
    static String[] f = {"SingTel", "StarHub", "STARHUB"};
    static String[] g = {"CSL", "CMHK", "China Mobile HK", "PCCW"};

    /* renamed from: kx$a */
    /* compiled from: OperatorUtil */
    public static class a {
        public String a;
        public String b;
        public int c;
    }

    public static String a(Context context) {
        if (context == null) {
            return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return (telephonyManager == null || telephonyManager.getSimState() != 5) ? "" : telephonyManager.getNetworkOperator();
    }

    private static List<String> a(String str, String[] strArr) {
        if (str == null || strArr == null) {
            return new ArrayList();
        }
        for (String equals : strArr) {
            if (str.equals(equals)) {
                return new ArrayList(Arrays.asList(new String[]{str}));
            }
        }
        return new ArrayList();
    }

    public static int a() {
        String b2 = agm.b(AMapAppGlobal.getApplication());
        if (b2 != null) {
            if (b2.startsWith("46000") || b2.startsWith("46002")) {
                return 3;
            }
            if (b2.startsWith("46001")) {
                return 1;
            }
            if (b2.startsWith("46003")) {
                return 2;
            }
        }
        return 0;
    }

    public static a[] b(Context context) {
        int i;
        String[] strArr;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        a[] aVarArr = new a[1];
        a aVar = new a();
        aVar.a = telephonyManager.getNetworkOperatorName();
        aVar.b = telephonyManager.getNetworkOperator();
        String networkOperatorName = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
        if (networkOperatorName != null) {
            if (networkOperatorName.indexOf(",") == 1 || networkOperatorName.length() - 1 == networkOperatorName.lastIndexOf(",")) {
                strArr = new String[]{networkOperatorName};
            } else {
                strArr = networkOperatorName.split(",");
            }
            int[] iArr = new int[strArr.length];
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (a(strArr[i2], a).size() != 0) {
                    iArr[i2] = 1;
                } else if (a(strArr[i2], c).size() != 0) {
                    iArr[i2] = 2;
                } else if (a(strArr[i2], d).size() != 0) {
                    iArr[i2] = 3;
                } else {
                    iArr[i2] = a();
                }
            }
            if (iArr.length == 1) {
                i = iArr[0];
            } else if (iArr.length == 2) {
                i = iArr[0] | iArr[1];
            }
            aVar.c = i;
            aVarArr[0] = aVar;
            return aVarArr;
        }
        i = 0;
        aVar.c = i;
        aVarArr[0] = aVar;
        return aVarArr;
    }
}
