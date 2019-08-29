package defpackage;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareBase;
import com.sina.weibo.BuildConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dck reason: default package */
/* compiled from: MoreShare */
public final class dck extends ShareBase {
    private int a;
    private String b;
    private String c;
    private String d;
    private boolean e;
    private String f;

    public final int getShareType() {
        return 7;
    }

    public dck(String str, String str2, String str3, boolean z, String str4, int i) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.a = i;
        this.e = z;
        this.f = str4;
    }

    private static boolean a() {
        return VERSION.SDK_INT >= 24;
    }

    private static Uri a(File file) {
        if (a()) {
            return FileProvider.getUriForFile(AMapPageUtil.getAppContext(), FileUtil.FILE_PROVIDER, file);
        }
        return Uri.fromFile(file);
    }

    private void a(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
            notifyShareResult(-1);
            return;
        }
        if (!TextUtils.isEmpty(this.c)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.c);
            sb.append(Token.SEPARATOR);
            sb.append(str);
            str = sb.toString();
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", str);
        intent.setType("text/plain");
        PackageManager packageManager = AMapPageUtil.getAppContext().getPackageManager();
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo next : queryIntentActivities) {
            if (next != null) {
                ActivityInfo activityInfo = next.activityInfo;
                if (activityInfo != null && !activityInfo.packageName.equals("com.autonavi.minimap")) {
                    if (activityInfo.packageName.equals(BuildConfig.APPLICATION_ID)) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append("?gd_from=weibo");
                        str2 = sb2.toString();
                    } else if (activityInfo.packageName.equals("com.tencent.mobileqq")) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append("?gd_from=qq");
                        str2 = sb3.toString();
                    } else {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str);
                        sb4.append("?gd_from=unknow");
                        str2 = sb4.toString();
                    }
                    Intent intent2 = new Intent();
                    intent2.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                    intent2.setAction("android.intent.action.SEND");
                    intent2.setType("text/plain");
                    intent2.putExtra("android.intent.extra.TEXT", str2);
                    arrayList.add(new LabeledIntent(intent2, activityInfo.packageName, next.loadLabel(packageManager), next.icon));
                }
            }
        }
        if (arrayList.size() > 0) {
            Intent createChooser = Intent.createChooser((Intent) arrayList.remove(0), AMapPageUtil.getAppContext().getResources().getString(R.string.v4_share));
            createChooser.addFlags(268435456);
            createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", (LabeledIntent[]) arrayList.toArray(new LabeledIntent[arrayList.size()]));
            AMapPageUtil.getAppContext().startActivity(createChooser);
            notifyShareResult(0);
        }
        b();
    }

    private void b() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof AbstractBaseMapPage) {
            final AbstractBaseMapPage abstractBaseMapPage = (AbstractBaseMapPage) pageContext;
            abstractBaseMapPage.getContentView().postDelayed(new Runnable() {
                public final void run() {
                    try {
                        if (abstractBaseMapPage.getMapView().O()) {
                            abstractBaseMapPage.getMapView().N();
                        }
                    } catch (Throwable unused) {
                    }
                }
            }, 100);
        }
    }

    public final void startShare() {
        if (this.a == 1) {
            File file = new File(this.b);
            if (TextUtils.isEmpty(this.b) || !file.exists()) {
                ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.email_share_get_content_failed));
                notifyShareResult(-1);
                return;
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.STREAM", a(file));
            if (!TextUtils.isEmpty(this.c)) {
                intent.putExtra("android.intent.extra.TEXT", this.c);
            }
            PackageManager packageManager = AMapPageUtil.getAppContext().getPackageManager();
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
            ArrayList arrayList = new ArrayList();
            for (ResolveInfo next : queryIntentActivities) {
                if (next != null) {
                    ActivityInfo activityInfo = next.activityInfo;
                    if (activityInfo != null && !activityInfo.packageName.equals("com.autonavi.minimap")) {
                        Intent intent2 = new Intent();
                        intent2.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                        intent2.setAction("android.intent.action.SEND");
                        intent2.setType("image/*");
                        intent2.putExtra("android.intent.extra.STREAM", a(file));
                        if (!TextUtils.isEmpty(this.c)) {
                            intent2.putExtra("android.intent.extra.TEXT", this.c);
                        }
                        if (a()) {
                            intent2.addFlags(1);
                        }
                        arrayList.add(new LabeledIntent(intent2, activityInfo.packageName, next.loadLabel(packageManager), next.icon));
                    }
                }
            }
            if (arrayList.size() > 0) {
                Intent createChooser = Intent.createChooser((Intent) arrayList.remove(0), AMapPageUtil.getAppContext().getResources().getString(R.string.v4_share));
                LabeledIntent[] labeledIntentArr = (LabeledIntent[]) arrayList.toArray(new LabeledIntent[arrayList.size()]);
                createChooser.addFlags(268435456);
                if (a()) {
                    createChooser.addFlags(1);
                }
                createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", labeledIntentArr);
                AMapPageUtil.getAppContext().startActivity(createChooser);
                notifyShareResult(0);
            }
            b();
            return;
        }
        if (this.a == 2) {
            if (this.e) {
                requestShortUrl(this.d, this.f);
                return;
            }
            a(this.d);
        }
    }

    public final void onFinishResult(String str) {
        a(str);
    }
}
