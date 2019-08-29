package com.autonavi.mine.page.setting.sysabout.page;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.AEUtil;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import com.autonavi.widget.ui.TitleBar;

@PageAction("amap.basemap.action.sys_about_page")
public class SysAboutPage extends AbstractBasePage<cgs> implements OnClickListener, OnCheckedChangeListener, LocationNone {
    public static final String SP_KEY_AUI_PACKAGE_VERSION = "aui_package_version";
    private static int clickTimes;
    private RelativeLayout amap_url;
    private RelativeLayout amap_weibo_url;
    private ImageButton btnBack;
    private ImageButton btnClose;
    private TextView currentVersion;
    private int mClickCountOnIcon = 0;
    private ImageView mIconImage;
    private RelativeLayout mThanksRL;
    private TitleBar mTitleBar;
    private TextView tvAeVersion;
    private RelativeLayout userAgreeLayout;
    private RelativeLayout userExperienceLayout;
    private View wxView;

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
    }

    /* access modifiers changed from: protected */
    public cgs createPresenter() {
        return new cgs(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.setting_about_fragment);
        initView(getContentView());
        initAuiConfigVersion();
    }

    private void initAuiConfigVersion() {
        ahm.a(new Runnable() {
            public final void run() {
                SysAboutPage.this.readAuiVersionFromConfig();
            }
        });
    }

    private void initView(View view) {
        this.mIconImage = (ImageView) view.findViewById(R.id.about_pic);
        this.mIconImage.setOnClickListener(this);
        this.mTitleBar = (TitleBar) view.findViewById(R.id.title);
        this.mTitleBar.setTitle(getString(R.string.about_amap));
        this.mTitleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                SysAboutPage.this.finish();
            }
        });
        this.userAgreeLayout = (RelativeLayout) view.findViewById(R.id.about_user_agreement);
        this.userAgreeLayout.setOnClickListener(this);
        this.userExperienceLayout = (RelativeLayout) view.findViewById(R.id.user_experience_plan);
        this.userExperienceLayout.setOnClickListener(this);
        this.mThanksRL = (RelativeLayout) view.findViewById(R.id.thanks);
        this.mThanksRL.setOnClickListener(this);
        this.amap_url = (RelativeLayout) view.findViewById(R.id.amap_url);
        this.amap_url.setOnClickListener(this);
        this.amap_weibo_url = (RelativeLayout) view.findViewById(R.id.amap_weibo_url);
        this.amap_weibo_url.setOnClickListener(this);
        this.currentVersion = (TextView) view.findViewById(R.id.about_tv_ver);
        this.currentVersion.setOnClickListener(this);
        try {
            String str = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
            TextView textView = this.currentVersion;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.current_version));
            sb.append(str);
            textView.setText(sb.toString());
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        showVersionCode();
        this.wxView = view.findViewById(R.id.amap_weixin);
        this.wxView.setOnClickListener(this);
        if (AEUtil.IS_DEBUG) {
            this.tvAeVersion = (TextView) view.findViewById(R.id.tv_ae_version);
            this.tvAeVersion.setText(AEUtil.getVersionInfo());
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:8|9|(2:10|(1:12)(1:52))|13|(1:15)|(2:17|18)|19|20|21) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0046 */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0059 A[SYNTHETIC, Splitter:B:31:0x0059] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x005e A[SYNTHETIC, Splitter:B:35:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0065 A[SYNTHETIC, Splitter:B:43:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x006a A[SYNTHETIC, Splitter:B:47:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x006e A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void readAuiVersionFromConfig() {
        /*
            r6 = this;
            r0 = 0
            android.content.Context r1 = r6.getContext()     // Catch:{ Exception -> 0x0062, all -> 0x0053 }
            if (r1 != 0) goto L_0x0008
            return
        L_0x0008:
            android.content.Context r1 = r6.getContext()     // Catch:{ Exception -> 0x0062, all -> 0x0053 }
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch:{ Exception -> 0x0062, all -> 0x0053 }
            java.lang.String r2 = "page/config.txt"
            java.io.InputStream r1 = r1.open(r2)     // Catch:{ Exception -> 0x0062, all -> 0x0053 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0063, all -> 0x004e }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0063, all -> 0x004e }
            r3.<init>(r1)     // Catch:{ Exception -> 0x0063, all -> 0x004e }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0063, all -> 0x004e }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            r0.<init>()     // Catch:{ Exception -> 0x004c, all -> 0x004a }
        L_0x0025:
            java.lang.String r3 = r2.readLine()     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            if (r3 == 0) goto L_0x002f
            r0.append(r3)     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            goto L_0x0025
        L_0x002f:
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            if (r0 == 0) goto L_0x0041
            com.amap.bundle.mapstorage.MapSharePreference r3 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            r3.<init>(r4)     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            java.lang.String r4 = "aui_package_version"
            r3.putStringValue(r4, r0)     // Catch:{ Exception -> 0x004c, all -> 0x004a }
        L_0x0041:
            if (r1 == 0) goto L_0x0046
            r1.close()     // Catch:{ IOException -> 0x0046 }
        L_0x0046:
            r2.close()     // Catch:{ IOException -> 0x0049 }
        L_0x0049:
            return
        L_0x004a:
            r0 = move-exception
            goto L_0x0057
        L_0x004c:
            r0 = r2
            goto L_0x0063
        L_0x004e:
            r2 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x0057
        L_0x0053:
            r1 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
        L_0x0057:
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch:{ IOException -> 0x005c }
        L_0x005c:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x0061 }
        L_0x0061:
            throw r0
        L_0x0062:
            r1 = r0
        L_0x0063:
            if (r1 == 0) goto L_0x0068
            r1.close()     // Catch:{ IOException -> 0x0068 }
        L_0x0068:
            if (r0 == 0) goto L_0x006e
            r0.close()     // Catch:{ IOException -> 0x006d }
        L_0x006d:
            return
        L_0x006e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.mine.page.setting.sysabout.page.SysAboutPage.readAuiVersionFromConfig():void");
    }

    public void onClick(View view) {
        if (view == this.amap_url) {
            if (bno.b) {
                Activity activity = getActivity();
                StringBuilder sb = new StringBuilder("http://wap.amap.com?type=");
                sb.append(NetworkParam.getDic());
                bnz.a((Context) activity, sb.toString());
                return;
            }
            bnz.a((Context) getActivity(), (String) "http://wap.amap.com");
        } else if (view == this.amap_weibo_url) {
            bnz.a((Context) getActivity(), (String) "https://weibo.com/minimap");
        } else if (view == this.btnClose) {
            finish();
        } else if (view == this.wxView) {
            copyText(getResources().getString(R.string.sina_account_value));
        } else if (view == this.userAgreeLayout) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ConfigerHelper.getInstance().getServiceItemUrl())));
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        } else if (view == this.userExperienceLayout) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ConfigerHelper.getInstance().getPrivacyRightUrl())));
            } catch (ActivityNotFoundException e2) {
                e2.printStackTrace();
            }
        } else {
            if (view == this.currentVersion) {
                if (clickTimes < 5) {
                    clickTimes++;
                    showVersionCode();
                }
            } else if (view == this.mIconImage) {
                this.mClickCountOnIcon++;
                if (this.mClickCountOnIcon >= 5) {
                    startPage((String) "amap.basemap.action.config_page", (PageBundle) null);
                    this.mClickCountOnIcon = 0;
                }
            } else if (view == this.mThanksRL) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("amapuri://webview/amaponline?url=https://wap.amap.com/thanks/thanks.html"));
                DoNotUseTool.startScheme(intent);
            }
        }
    }

    private void showVersionCode() {
        if (clickTimes == 5) {
            String keyValue = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.CUSTOM_ID_KEY);
            TextView textView = this.currentVersion;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.current_version));
            sb.append(a.a().a);
            sb.append(Token.SEPARATOR);
            if (keyValue == null) {
                keyValue = "";
            }
            sb.append(keyValue);
            textView.setText(sb.toString());
        }
    }

    @SuppressLint({"NewApi"})
    private void copyText(String str) {
        if (VERSION.SDK_INT >= 11) {
            try {
                ((ClipboardManager) getContext().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", str));
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else {
            ((android.text.ClipboardManager) getContext().getSystemService("clipboard")).setText(str);
        }
        dcb dcb = (dcb) a.a.a(dcb.class);
        if (dcb == null || !dcb.c().a()) {
            ToastHelper.showToast(getResources().getString(R.string.wx_copyed));
        } else {
            showSureDialog(getResources().getString(R.string.open_wx_title), getResources().getString(R.string.open_wx_message));
        }
    }

    private void showSureDialog(String str, String str2) {
        a aVar = new a(getActivity());
        aVar.a((CharSequence) str);
        aVar.b((CharSequence) str2);
        aVar.a(R.string.Ok, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                dcb dcb = (dcb) a.a.a(dcb.class);
                if (dcb != null) {
                    dcb.c().b();
                }
                SysAboutPage.this.dismissViewLayer(alertView);
            }
        });
        aVar.b(R.string.cancel, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                SysAboutPage.this.dismissViewLayer(alertView);
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
        AlertView a = aVar.a();
        showViewLayer(a);
        a.startAnimation();
    }

    public int getClickVersionTimes() {
        return clickTimes;
    }

    public void setClickVersionTimes(int i) {
        clickTimes = i;
    }
}
