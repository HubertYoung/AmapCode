package com.alipay.ma.analyze.a;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.text.TextUtils;
import com.alipay.ma.common.b.c;
import com.alipay.ma.common.b.e;
import com.alipay.ma.common.b.f;
import com.alipay.ma.decode.DecodeResult;
import com.alipay.ma.decode.MaDecode;
import com.alipay.ma.parser.MaParSer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: MaAnalyzeAPI */
public final class a {
    private static List<MaParSer> a = new ArrayList();
    private static String[] b = {"http://s.tb.cn", "https://s.tb.cn", "HTTP://S.TB.CN", "HTTPS://S.TB.CN"};

    public static c[] a(byte[] bitMatrix, int width, int height, Rect region, int binarizeMethod, e... maTypes) {
        int scanMode = 0;
        for (e a2 : maTypes) {
            scanMode |= a2.a();
        }
        DecodeResult[] decodeResults = MaDecode.codeDecodeBinarizedData(bitMatrix, width, height, width, region, scanMode, binarizeMethod, "", b);
        if (decodeResults == null) {
            return null;
        }
        ArrayList ret = new ArrayList();
        if (a.isEmpty()) {
            return null;
        }
        for (DecodeResult decodeResult : decodeResults) {
            if (decodeResult != null && !TextUtils.isEmpty(decodeResult.strCode)) {
                f wrapperResult = a(decodeResult);
                e maType = com.alipay.ma.analyze.b.a.a(wrapperResult);
                wrapperResult.f = maType;
                if (Arrays.asList(maTypes).contains(maType)) {
                    c maResult = null;
                    for (MaParSer decode : a) {
                        maResult = decode.decode(wrapperResult);
                        if (maResult != null) {
                            break;
                        }
                    }
                    ret.add(maResult);
                }
            }
        }
        return (c[]) ret.toArray(new c[ret.size()]);
    }

    public static c[] a(YuvImage yuvImage, Rect region, e... maTypes) {
        if (region == null) {
            region = a(yuvImage.getWidth(), yuvImage.getHeight());
        }
        int scanMode = 0;
        for (e a2 : maTypes) {
            scanMode |= a2.a();
        }
        DecodeResult[] decodeResults = MaDecode.yuvcodeDecode(yuvImage, region, scanMode, "", b);
        if (decodeResults == null) {
            return null;
        }
        ArrayList ret = new ArrayList();
        if (a.isEmpty()) {
            return null;
        }
        for (DecodeResult decodeResult : decodeResults) {
            if (decodeResult != null && !TextUtils.isEmpty(decodeResult.strCode)) {
                f wrapperResult = a(decodeResult);
                e maType = com.alipay.ma.analyze.b.a.a(wrapperResult);
                wrapperResult.f = maType;
                if (Arrays.asList(maTypes).contains(maType)) {
                    c maResult = null;
                    for (MaParSer decode : a) {
                        maResult = decode.decode(wrapperResult);
                        if (maResult != null) {
                            break;
                        }
                    }
                    ret.add(maResult);
                }
            }
        }
        return (c[]) ret.toArray(new c[ret.size()]);
    }

    public static c a(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        DecodeResult decodeResult = MaDecode.codeDecodePictureWithQr(path, 34304);
        if (decodeResult == null || TextUtils.isEmpty(decodeResult.strCode)) {
            return null;
        }
        if (decodeResult.type == 1) {
            if (decodeResult.subType == 32768) {
                return new c(e.GEN3, decodeResult.strCode);
            }
            return new c(e.QR, decodeResult.strCode, decodeResult.ecLevel, decodeResult.bitErrors, decodeResult.version, decodeResult.strategy);
        } else if (decodeResult.type == 65536) {
            if (decodeResult.subType == 0) {
                return new c(e.ARCODE, decodeResult.strCode, decodeResult.ecLevel, decodeResult.bitErrors, decodeResult.version, decodeResult.strategy);
            }
            return null;
        } else if (decodeResult.type == 1024) {
            return new c(e.DM, decodeResult.strCode, decodeResult.ecLevel, decodeResult.bitErrors, decodeResult.version, decodeResult.strategy);
        } else {
            if ((decodeResult.subType & 143) > 0) {
                return new c(e.PRODUCT, decodeResult.strCode);
            }
            return new c(e.EXPRESS, decodeResult.strCode);
        }
    }

    public static void a(List<MaParSer> parsers) {
        a.addAll(parsers);
    }

    public static boolean a() {
        return a.isEmpty();
    }

    private static Rect a(int width, int height) {
        int w = Math.min(width, height) & -8;
        return new Rect(Math.abs((width - height) / 2) & -4, 0, w, w);
    }

    private static f a(DecodeResult decodeResult) {
        f wrapperResult = new f();
        wrapperResult.a = decodeResult.type;
        wrapperResult.b = decodeResult.subType;
        wrapperResult.c = decodeResult.strCode;
        wrapperResult.d = decodeResult.decodeBytes;
        wrapperResult.e = decodeResult.hiddenData;
        wrapperResult.g = decodeResult.x;
        wrapperResult.h = decodeResult.y;
        wrapperResult.i = decodeResult.width;
        wrapperResult.j = decodeResult.height;
        wrapperResult.k = decodeResult.xCorner;
        wrapperResult.l = decodeResult.yCorner;
        wrapperResult.n = decodeResult.version;
        wrapperResult.o = decodeResult.bitErrors;
        wrapperResult.m = decodeResult.ecLevel;
        wrapperResult.p = decodeResult.strategy;
        return wrapperResult;
    }
}
