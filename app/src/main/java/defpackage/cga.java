package defpackage;

import android.content.SharedPreferences;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.aos.serverkey;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/* renamed from: cga reason: default package */
/* compiled from: EasyDriving */
public final class cga {
    public static void a(final Uri uri) {
        boolean z;
        String queryParameter = uri.getQueryParameter("needlogin");
        if (TextUtils.isEmpty(queryParameter) || queryParameter.equals("0")) {
            a(uri.getQueryParameter("url"));
            return;
        }
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            z = false;
        } else {
            z = iAccountService.a();
        }
        if (!z) {
            IAccountService iAccountService2 = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService2 != null) {
                iAccountService2.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                        if (z) {
                            if (TextUtils.isEmpty(cga.a())) {
                                cga.c(uri);
                                return;
                            }
                            cga.a(cga.b(uri));
                        }
                    }
                });
            }
        } else if (TextUtils.isEmpty(a())) {
            c(uri);
        } else {
            a(b(uri));
        }
    }

    static void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getApplicationContext().getSharedPreferences("category_save_v2", 0);
            Builder buildUpon = Uri.parse(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.REDIRECT)).buildUpon();
            buildUpon.appendQueryParameter("target", str);
            String builder = buildUpon.toString();
            if (!sharedPreferences.getBoolean("https://wap.amap.com/license/driving_1.html ", false)) {
                a("https://wap.amap.com/license/driving_1.html ", builder, AMapPageUtil.getAppContext().getString(R.string.easy_driving));
            } else {
                b(AMapPageUtil.getAppContext().getString(R.string.easy_driving), builder, AMapPageUtil.getAppContext().getString(R.string.easy_driving));
            }
        }
    }

    private static void a(String str, String str2, String str3) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("license_url", str);
        pageBundle.putString("url", str2);
        pageBundle.putString("website_name", str3);
        pageBundle.putBoolean("native_web", false);
        pageBundle.putString("redirect_action", "single.tab.webview");
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage((String) "amap.basemap.action.licenseconfirm_page", pageBundle);
        }
    }

    private static void b(final String str, String str2, final String str3) {
        aja aja = new aja(str2);
        aja.b = new ajf() {
            final /* synthetic */ boolean d = false;

            public final String b() {
                return str;
            }

            public final b c() {
                return new b() {
                    public final boolean a() {
                        return false;
                    }

                    public final long c() {
                        return 1000;
                    }

                    public final String b() {
                        return str3;
                    }
                };
            }

            public final boolean d() {
                return this.d;
            }
        };
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null) {
            aix.a(AMapPageUtil.getPageContext(), aja);
        }
    }

    static String b(Uri uri) {
        String queryParameter = uri.getQueryParameter("url");
        String queryParameter2 = uri.getQueryParameter("deskey");
        if (TextUtils.isEmpty(queryParameter) || TextUtils.isEmpty(queryParameter2)) {
            return "";
        }
        String amapDecode = serverkey.amapDecode(queryParameter2);
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        DPoint a = cfg.a((long) latestPosition.x, (long) latestPosition.y);
        Builder buildUpon = Uri.parse(queryParameter).buildUpon();
        String valueOf = String.valueOf(a.y);
        String valueOf2 = String.valueOf(a.x);
        String a2 = a();
        String a3 = a(amapDecode, valueOf);
        String a4 = a(amapDecode, valueOf2);
        String a5 = a(amapDecode, a2);
        String b = b(a3);
        String b2 = b(a4);
        String b3 = b(a5);
        buildUpon.appendQueryParameter("coordtype", "gcj02ll");
        buildUpon.appendQueryParameter("lat", b);
        buildUpon.appendQueryParameter(LocationParams.PARA_FLP_AUTONAVI_LON, b2);
        buildUpon.appendQueryParameter("phone", b3);
        return buildUpon.toString();
    }

    private static String a(String str, String str2) {
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str2.length(); i++) {
            sb.append((char) (str2.charAt(i) ^ charArray[i % charArray.length]));
        }
        return sb.toString();
    }

    private static String b(String str) {
        try {
            return String.format("%x", new Object[]{new BigInteger(1, str.getBytes("UTF-8"))});
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    static String a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.h;
    }

    static void c(final Uri uri) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(AMapPageUtil.getPageContext(), AMapAppGlobal.getApplication().getString(R.string.easy_dring_bind_mobile), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        cga.a(cga.b(uri));
                    }
                }
            });
        }
    }
}
