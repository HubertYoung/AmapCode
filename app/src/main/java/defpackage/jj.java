package defpackage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.amap.bundle.appupgrade.AppUpgradeController;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.io.File;

/* renamed from: jj reason: default package */
/* compiled from: AppUpgradeIntentDispatcher */
public final class jj {
    public final Activity a;
    String b;
    String c;
    public AppUpgradeController d = null;
    private final String e = "/autonavi/apk/";
    private js f = new js() {
        public final void a(Throwable th) {
        }

        public final void d() {
            ahm.a(new Runnable() {
                public final void run() {
                    jj jjVar = jj.this;
                    AMapAppGlobal.getApplication().getSharedPreferences("appDownloadUrl", 0).edit().putString("SplashAppUrl", "").putString("SplashAppName", "").putString("SplashAppPckName", "").apply();
                    StringBuilder sb = new StringBuilder();
                    sb.append(jjVar.b);
                    sb.append(jjVar.c);
                    sb.append(FilePathHelper.SUFFIX_DOT_TMP);
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(jjVar.b);
                    sb3.append(jjVar.c);
                    ahd.a(sb2, sb3.toString());
                    dfm dfm = (dfm) ank.a(dfm.class);
                    if (dfm == null || !dfm.b()) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(jjVar.b);
                        sb4.append(jjVar.c);
                        File file = new File(sb4.toString());
                        if (file.exists()) {
                            if (TextUtils.isEmpty(jjVar.b) || jjVar.b.indexOf("data/data") == -1 || ahd.b(file) == 0) {
                                kl.a(jjVar.a, file);
                            } else {
                                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.intent_not_allow_install));
                            }
                        }
                    }
                }
            });
        }
    };

    public jj(Activity activity) {
        this.a = activity;
    }

    @SuppressLint({"CommitPrefEdits"})
    public final void a(String str, String str2, String str3, int i, int i2) {
        final String str4 = str;
        if (!bno.b) {
            if (1 != aaw.d(AMapAppGlobal.getApplication()) || TextUtils.isEmpty(str4) || str4.indexOf(AjxHttpLoader.DOMAIN_HTTP) == -1) {
                String str5 = str2;
                String str6 = str3;
                final int i3 = i2;
                if (!(aaw.d(AMapAppGlobal.getApplication()) == 0 || aaw.d(AMapAppGlobal.getApplication()) == 1 || TextUtils.isEmpty(str4) || str4.indexOf(AjxHttpLoader.DOMAIN_HTTP) == -1)) {
                    bid pageContext = AMapPageUtil.getPageContext();
                    if (pageContext != null) {
                        a aVar = new a(this.a);
                        aVar.b(R.string.network_download_warn_not_wlan);
                        aVar.a(R.string.network_flow_warn);
                        int i4 = R.string.sure;
                        final String str7 = str5;
                        final String str8 = str6;
                        final int i5 = i;
                        AnonymousClass2 r0 = new a() {
                            public final void onClick(AlertView alertView, int i) {
                                if (!TextUtils.isEmpty(str4) && str4.indexOf(AjxHttpLoader.DOMAIN_HTTP) != -1) {
                                    jj.this.a(str4, str7, str8, i3);
                                }
                            }
                        };
                        aVar.a(i4, (a) r0).b(R.string.cancel, (a) new a() {
                            public final void onClick(AlertView alertView, int i) {
                            }
                        });
                        aVar.b = new a() {
                            public final void onClick(AlertView alertView, int i) {
                            }
                        };
                        aVar.c = new a() {
                            public final void onClick(AlertView alertView, int i) {
                            }
                        };
                        aVar.a(false);
                        AlertView a2 = aVar.a();
                        pageContext.showViewLayer(a2);
                        a2.startAnimation();
                    } else {
                        return;
                    }
                }
                return;
            }
            a(str4, str2, str3, i2);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str, String str2, String str3, int i) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && jr.a().a(str)) {
            if (TextUtils.isEmpty(str3) || !ahp.a(str3)) {
                StringBuilder sb = new StringBuilder();
                sb.append(AMapAppGlobal.getApplication().getString(R.string.app_download_begin));
                sb.append(str2);
                ToastHelper.showLongToast(sb.toString());
                String str4 = "SplashAppName";
                String str5 = "SplashAppUrl";
                if (i == 0) {
                    str4 = "appName";
                    str5 = "mDownloadUrl";
                }
                Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("appDownloadUrl", 0).edit();
                edit.putString(str4, str2);
                edit.putString(str5, str);
                if (!TextUtils.isEmpty(str3)) {
                    edit.putString("SplashAppPckName", str3);
                }
                edit.commit();
                this.c = str.substring(str.lastIndexOf("/") + 1);
                this.b = FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication());
                if (!TextUtils.isEmpty(this.b) && this.b.indexOf("data/data") == -1) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.b);
                    sb2.append("/autonavi/apk/");
                    this.b = sb2.toString();
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.b);
                sb3.append(this.c);
                sb3.append(FilePathHelper.SUFFIX_DOT_TMP);
                String str6 = str;
                jg.a().a(str6, sb3.toString(), str2, i, false, 2, null, this.f);
            }
        }
    }
}
