package defpackage;

import android.content.SharedPreferences;
import android.content.res.Resources;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.mine.page.toolsbox.page.ToolsBoxPage;
import com.autonavi.minimap.R;

@BundleInterface(ddq.class)
/* renamed from: cgp reason: default package */
/* compiled from: ToolBoxService */
public class cgp extends esi implements ddq {
    public final String e() {
        return "single.tab.webview";
    }

    public final void a() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(ToolsBoxPage.class, (PageBundle) null);
        }
    }

    public final void b() {
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getApplicationContext().getSharedPreferences("category_save_v2", 0);
        String keyValue = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.REDIRECT);
        StringBuilder sb = new StringBuilder();
        sb.append(keyValue);
        sb.append("target=");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(ConfigerHelper.EASY_DRIVING_URL);
        String sb4 = sb3.toString();
        if (!sharedPreferences.getBoolean("https://wap.amap.com/license/driving.html", false)) {
            a("https://wap.amap.com/license/driving.html", sb4, AMapAppGlobal.getApplication().getString(R.string.e_designated_drive));
            return;
        }
        Resources resources = AMapAppGlobal.getApplication().getResources();
        b(resources.getString(R.string.find_designated_drive), sb4, resources.getString(R.string.e_designated_drive));
    }

    public final void c() {
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getApplicationContext().getSharedPreferences("category_save_v2", 0);
        String keyValue = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.REDIRECT);
        StringBuilder sb = new StringBuilder();
        sb.append(keyValue);
        sb.append("target=");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(ConfigerHelper.E_JIAJIE_URL);
        String sb4 = sb3.toString();
        if (!sharedPreferences.getBoolean("https://wap.amap.com/license/cleaning.html", false)) {
            a("https://wap.amap.com/license/cleaning.html", sb4, AMapAppGlobal.getApplication().getString(R.string.e_home_clean));
        } else {
            b(AMapAppGlobal.getApplication().getString(R.string.e_home_clean), sb4, AMapAppGlobal.getApplication().getString(R.string.e_home_clean));
        }
    }

    public final void d() {
        String string = AMapAppGlobal.getApplication().getResources().getString(R.string.alipay_name);
        b(string, "https://wap.alipay.com", string);
    }

    private static void a(String str, String str2, String str3) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("license_url", str);
        pageBundle.putString("url", str2);
        pageBundle.putString("website_name", str3);
        pageBundle.putBoolean("native_web", false);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage((String) "amap.basemap.action.licenseconfirm_page", pageBundle);
        }
    }

    private static void b(final String str, String str2, final String str3) {
        aja aja = new aja(str2);
        aja.b = new ajf() {
            final /* synthetic */ boolean d = true;

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
}
