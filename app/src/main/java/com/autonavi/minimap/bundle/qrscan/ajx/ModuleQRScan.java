package com.autonavi.minimap.bundle.qrscan.ajx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("qrscan")
@KeepPublicClassMembers
@KeepName
public class ModuleQRScan extends AbstractModule {
    private static final String KEY_SCAN_SUPPORT_FEATURE = "scan_support_feature";
    public static final String MODULE_NAME = "qrscan";

    public ModuleQRScan(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    @com.autonavi.minimap.ajx3.modules.AjxMethod("getScanSupportFeature")
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getScanSupportFeature(com.autonavi.minimap.ajx3.core.JsFunctionCallback r4) {
        /*
            r3 = this;
            lo r0 = defpackage.lo.a()
            java.lang.String r1 = "scan_support_feature"
            java.lang.String r0 = r0.a(r1)
            java.lang.String r1 = ""
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x0024
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0020 }
            r2.<init>(r0)     // Catch:{ JSONException -> 0x0020 }
            java.lang.String r0 = "scan_support_feature"
            java.lang.String r0 = r2.optString(r0)     // Catch:{ JSONException -> 0x0020 }
            goto L_0x0025
        L_0x0020:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0024:
            r0 = r1
        L_0x0025:
            if (r4 == 0) goto L_0x0030
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r2 = 0
            r1[r2] = r0
            r4.callback(r1)
        L_0x0030:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bundle.qrscan.ajx.ModuleQRScan.getScanSupportFeature(com.autonavi.minimap.ajx3.core.JsFunctionCallback):void");
    }

    @AjxMethod("openWebViewFromScanPage")
    public void openWebViewFromScanPage(String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.finish();
        }
        VerifyParam verifyParam = new VerifyParam();
        verifyParam.redirect_url = str.trim();
        String url = aax.a(verifyParam).buildHttpRequest().getUrl();
        if (!TextUtils.isEmpty(url)) {
            aja aja = new aja(url);
            aja.b = new ajf() {
                public final boolean f() {
                    return true;
                }
            };
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a(AMapPageUtil.getPageContext(), aja);
            }
        }
    }

    @AjxMethod("scanQRUri")
    public void scanQRUri(final String str, final JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.isEmpty(str)) {
            jsFunctionCallback.callback("");
            return;
        }
        ahm.a(new Runnable() {
            public final void run() {
                Object obj;
                Bitmap bitmap;
                awt awt = (awt) a.a.a(awt.class);
                if (awt != null) {
                    try {
                        try {
                            InputStream openInputStream = AMapPageUtil.getAppContext().getContentResolver().openInputStream(Uri.parse(str));
                            bitmap = BitmapFactory.decodeStream(openInputStream);
                            try {
                                openInputStream.close();
                            } catch (Exception e) {
                                e = e;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bitmap = null;
                            e.printStackTrace();
                            obj = awt.a(bitmap);
                            AMapLog.d("ModuleQRScan", "onFavorClick scanQRUri ".concat(String.valueOf(obj)));
                            jsFunctionCallback.callback(obj);
                        }
                        obj = awt.a(bitmap);
                    } catch (Exception unused) {
                    }
                    AMapLog.d("ModuleQRScan", "onFavorClick scanQRUri ".concat(String.valueOf(obj)));
                    jsFunctionCallback.callback(obj);
                }
                obj = "";
                AMapLog.d("ModuleQRScan", "onFavorClick scanQRUri ".concat(String.valueOf(obj)));
                jsFunctionCallback.callback(obj);
            }
        });
    }

    @AjxMethod("scanQRUriAndMaplatform")
    public void scanQRUriAndMaplatform(final String str, final JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.isEmpty(str)) {
            jsFunctionCallback.callback("");
            return;
        }
        ahm.a(new Runnable() {
            public final void run() {
                Object obj;
                Bitmap bitmap;
                awt awt = (awt) a.a.a(awt.class);
                if (awt != null) {
                    try {
                        try {
                            InputStream openInputStream = AMapPageUtil.getAppContext().getContentResolver().openInputStream(Uri.parse(str));
                            bitmap = BitmapFactory.decodeStream(openInputStream);
                            try {
                                openInputStream.close();
                            } catch (Exception e) {
                                e = e;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bitmap = null;
                            e.printStackTrace();
                            obj = awt.b(bitmap);
                            AMapLog.d("ModuleQRScan", "onFavorClick scanQRUri ".concat(String.valueOf(obj)));
                            jsFunctionCallback.callback(obj);
                        }
                        obj = awt.b(bitmap);
                    } catch (Exception unused) {
                    }
                    AMapLog.d("ModuleQRScan", "onFavorClick scanQRUri ".concat(String.valueOf(obj)));
                    jsFunctionCallback.callback(obj);
                }
                obj = "";
                AMapLog.d("ModuleQRScan", "onFavorClick scanQRUri ".concat(String.valueOf(obj)));
                jsFunctionCallback.callback(obj);
            }
        });
    }

    @AjxMethod("createQRCode")
    public void createQRCode(String str, int i, final JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.isEmpty(str)) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new Object[0]);
            }
            return;
        }
        try {
            dby.a(new String(hex2bytes(str), "ISO-8859-1"), i, new a() {
                public final void a(String str) {
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(str);
                    }
                }

                public final void a(int i) {
                    if (i == 2 && jsFunctionCallback != null) {
                        jsFunctionCallback.callback(new Object[0]);
                    }
                }
            });
        } catch (UnsupportedEncodingException unused) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new Object[0]);
            }
        }
    }

    public static byte[] hex2bytes(String str) {
        int length = str.length() / 2;
        char[] charArray = str.toUpperCase().toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    private static byte charToByte(char c) {
        return (byte) HexUtils.HEX_CHARS.indexOf(c);
    }
}
