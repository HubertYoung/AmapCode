package com.autonavi.minimap.manager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.utils.Constant;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.inter.IAppUpDateManager;
import com.autonavi.minimap.bundle.featureguide.api.GuideStartType;
import com.autonavi.minimap.bundle.splashscreen.api.ISplashManager;
import java.util.List;

public class SplashManager implements ISplashManager, dmk {
    private Activity a;
    private FragmentManager b;
    private dmj c;
    private cxs d;
    private ddp e;
    private String f;
    private String g;
    private String h;

    public void setSplashActivity(Activity activity) {
        this.a = activity;
    }

    public void setSplashOnDestroy(boolean z) {
        if (this.d != null) {
            this.d.c();
        }
        if (this.c != null) {
            this.c.f();
        }
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
    }

    public void setSplashOnRestart(boolean z) {
        if (this.c == null) {
            new dru();
            this.c = dru.a(this.a);
        }
        this.c.c(z);
    }

    public void setSplashOnPause(boolean z) {
        if (this.c == null) {
            new dru();
            this.c = dru.a(this.a);
        }
        this.c.b(z);
    }

    public void setMapStartListener(ddp ddp) {
        this.e = ddp;
    }

    public void initAfpSplash() {
        if (this.c == null) {
            new dru();
            this.c = dru.a(this.a);
            this.c.a((dmk) this);
        }
        this.c.a();
    }

    public void updateSplashData(boolean z) {
        if (this.c == null) {
            new dru();
            this.c = dru.a(this.a);
            this.c.a((dmk) this);
        }
        this.c.a(z);
    }

    public void initUserGuideSplash(GuideStartType guideStartType) {
        if (this.d == null) {
            new dru();
            this.d = dru.b(this.a);
        }
        this.d.a(this.b);
        this.d.a(guideStartType);
        this.d.a();
    }

    public void setSplashActivityFragmentManager(FragmentManager fragmentManager) {
        this.b = fragmentManager;
    }

    public boolean getIsGuideViewCreated() {
        if (this.d == null) {
            new dru();
            this.d = dru.b(this.a);
        }
        return this.d.b();
    }

    public final void a(String str, String str2) {
        Intent intent;
        ckb.a((String) "SplashManager#startMapView");
        if (!(str == null || str2 == null)) {
            if (str.equals("scheme")) {
                this.f = str2;
            } else if (str.equals("webview_url")) {
                this.g = str2;
            } else if (str.equals("external_url")) {
                this.h = str2;
            } else {
                str.equals("no_click");
            }
        }
        IAppUpDateManager iAppUpDateManager = (IAppUpDateManager) ank.a(IAppUpDateManager.class);
        if (iAppUpDateManager != null) {
            iAppUpDateManager.setLegalDownloadUrl(str2);
        }
        if (!TextUtils.isEmpty(this.f)) {
            intent = new Intent();
            intent.setClassName(this.a, Constant.LAUNCHER_ACTIVITY_NAME);
            intent.setAction("com.autonavi.bundle.splash.scheme.ACTION");
            intent.setData(Uri.parse(this.f));
            StringBuilder sb = new StringBuilder();
            sb.append(Thread.currentThread().getName());
            sb.append(Token.SEPARATOR);
            sb.append(SystemClock.elapsedRealtime());
            sb.append(":SPLASH_SCHME_ACTION");
            AMapLog.d("开屏广告：", sb.toString());
        } else if (!TextUtils.isEmpty(this.g)) {
            intent = new Intent();
            intent.setClassName(this.a, Constant.LAUNCHER_ACTIVITY_NAME);
            intent.setData(Uri.parse(this.g));
            intent.setAction("com.autonavi.bundle.splash.scheme.ACTION");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Thread.currentThread().getName());
            sb2.append(Token.SEPARATOR);
            sb2.append(SystemClock.elapsedRealtime());
            sb2.append(":111 AFP_WEBVIEW_URL");
            AMapLog.d("开屏广告：", sb2.toString());
            intent.putExtra(ISplashManager.AFP_WEBVIEW_URL_FLAG, ISplashManager.AFP_WEBVIEW_URL);
        } else {
            List list = null;
            if (!TextUtils.isEmpty(this.h)) {
                intent = new Intent();
                intent.setClassName(this.a, Constant.LAUNCHER_ACTIVITY_NAME);
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.putExtra(ISplashManager.AFP_EXTERNAL_URL_FLAG, ISplashManager.AFP_EXTERNAL_URL);
                intent.setData(Uri.parse(this.h));
                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.VIEW");
                intent2.addCategory("android.intent.category.BROWSABLE");
                intent2.setData(Uri.parse(this.h));
                List<ResolveInfo> queryIntentActivities = this.a.getPackageManager().queryIntentActivities(intent2, 65536);
                if (queryIntentActivities != null && queryIntentActivities.size() > 0) {
                    list = queryIntentActivities;
                }
                if (!(list != null && list.size() > 0)) {
                    intent.removeExtra(ISplashManager.AFP_EXTERNAL_URL_FLAG);
                    intent.putExtra(ISplashManager.AFP_INVALID_URL_FLAG, ISplashManager.AFP_INVALID_URL);
                    ToastHelper.showToast(this.a.getString(R.string.splash_no_browser_guide_toast));
                }
            } else if (a()) {
                intent = new Intent();
                intent.setClassName(this.a, Constant.LAUNCHER_ACTIVITY_NAME);
            } else {
                intent = new Intent(this.a.getIntent());
                intent.setClassName(this.a, Constant.LAUNCHER_ACTIVITY_NAME);
                if (this.a.getIntent() != null && (this.a.getIntent().getFlags() & 1048576) == 1048576) {
                    intent.setData(null);
                    intent.setAction("");
                    intent.putExtras(new Bundle());
                }
            }
        }
        this.a.setIntent(intent);
        if (this.e != null) {
            this.e.a();
        }
    }

    private boolean a() {
        if (this.a != null && !TextUtils.isEmpty(this.a.getIntent().getAction()) && this.a.getIntent().getAction().contentEquals("android.intent.action.MAIN") && this.a.getIntent().getCategories() != null && this.a.getIntent().getCategories().size() > 0) {
            for (String contentEquals : this.a.getIntent().getCategories()) {
                if (contentEquals.contentEquals("android.intent.category.LAUNCHER")) {
                    return true;
                }
            }
        }
        return false;
    }
}
