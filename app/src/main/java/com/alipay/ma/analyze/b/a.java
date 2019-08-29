package com.alipay.ma.analyze.b;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.ma.common.b.e;
import com.alipay.ma.common.b.f;

/* compiled from: MaAnalyzeHelper */
public final class a {
    private static boolean a(String text) {
        return !TextUtils.isEmpty(text) && ((text.startsWith("8") && text.length() == 20) || ((text.startsWith("10") || text.startsWith("11")) && text.length() == 16));
    }

    private static boolean b(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        Uri uri = Uri.parse(text);
        if (uri == null || TextUtils.isEmpty(uri.getHost()) || !TextUtils.equals("s.tb.cn", uri.getHost().toLowerCase())) {
            return false;
        }
        return true;
    }

    private static boolean b(int subType) {
        return subType == 2050;
    }

    private static boolean c(int subType) {
        return subType == 32768;
    }

    public static boolean a(int subType) {
        return subType == 1024;
    }

    public static e a(f wrapperResult) {
        switch (wrapperResult.a) {
            case 0:
                return e.PRODUCT;
            case 1:
                if (b(wrapperResult.c)) {
                    return e.TB_ANTI_FAKE;
                }
                if (c(wrapperResult.b)) {
                    return e.GEN3;
                }
                if (b(wrapperResult.b)) {
                    return e.TB_4G;
                }
                return e.QR;
            case 2:
                if (a(wrapperResult.c)) {
                    return e.MEDICINE;
                }
                return e.EXPRESS;
            case 127:
                if (wrapperResult.b == 128) {
                    return e.PRODUCT;
                }
                return null;
            case 1024:
                return e.DM;
            case 65536:
                if (b(wrapperResult.a, wrapperResult.b)) {
                    return e.ARCODE;
                }
                return null;
            default:
                return null;
        }
    }

    public static boolean a(int decodeType, int decodeSubType) {
        return decodeType == 0 || decodeType == 2 || (decodeType == 127 && decodeSubType == 128);
    }

    public static boolean a(int decodeType, e maType, int decodeSubType) {
        return decodeType == 1 && maType == e.QR && decodeSubType == 512;
    }

    public static boolean a(int decodeType, e maType) {
        return decodeType == 1 && maType == e.TB_ANTI_FAKE;
    }

    public static boolean b(int decodeType, e maType, int decodeSubType) {
        return decodeType == 1 && maType == e.GEN3 && decodeSubType == 32768;
    }

    public static boolean c(int decodeType, e maType, int decodeSubType) {
        return decodeType == 1 && maType == e.TB_4G && decodeSubType == 2050;
    }

    public static boolean b(int decodeType, int subType) {
        return decodeType == 65536 && subType == 0;
    }
}
