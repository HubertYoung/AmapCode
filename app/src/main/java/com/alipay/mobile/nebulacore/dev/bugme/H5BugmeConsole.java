package com.alipay.mobile.nebulacore.dev.bugme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class H5BugmeConsole {
    static final String[] a = {"page", "jsBridge", "console", "network"};
    /* access modifiers changed from: private */
    public AlertDialog b;
    private WeakReference<APWebView> c;
    /* access modifiers changed from: private */
    public Context d;
    private Handler e = new Handler(Looper.getMainLooper());
    private View f;
    private H5TabLayout g;
    /* access modifiers changed from: private */
    public ViewPager h;
    /* access modifiers changed from: private */
    public List<H5BugMeListView> i;
    /* access modifiers changed from: private */
    public List<List<H5BugMeLogMsg>> j;
    private View k;
    private boolean l = false;

    public H5BugmeConsole(APWebView apWebView, Context context) {
        this.d = context;
        this.c = new WeakReference<>(apWebView);
    }

    public void startup() {
        if (!this.l) {
            try {
                a();
                b();
                this.l = true;
            } catch (Throwable throwable) {
                H5Log.e((String) "H5BugmeConsole", throwable);
            }
        }
    }

    private void a() {
        this.j = new ArrayList();
        this.i = new ArrayList();
        for (int i2 = 0; i2 < a.length; i2++) {
            this.j.add(new ArrayList());
            List<H5BugMeListView> list = this.i;
            H5BugMeListView h5BugMeListView = new H5BugMeListView(this.d, this, i2);
            list.add(h5BugMeListView);
        }
        LayoutInflater inflater = LayoutInflater.from(this.d);
        this.f = inflater.inflate(R.layout.h5_bugme_tabview, null);
        this.g = (H5TabLayout) this.f.findViewById(R.id.h5_bugme_tabs);
        this.h = (ViewPager) this.f.findViewById(R.id.h5_bugme_viewPager);
        ViewPager viewPager = this.h;
        H5BugMeViewPageAdapter h5BugMeViewPageAdapter = new H5BugMeViewPageAdapter(this);
        viewPager.setAdapter(h5BugMeViewPageAdapter);
        this.g.setupWithViewPager(this.h);
        View findViewById = this.f.findViewById(R.id.h5_bugme_clear_tab);
        AnonymousClass1 r0 = new OnClickListener() {
            public void onClick(View v) {
                int index = H5BugmeConsole.this.h.getCurrentItem();
                ((List) H5BugmeConsole.this.j.get(index)).clear();
                ((H5BugMeListView) H5BugmeConsole.this.i.get(index)).refresh();
            }
        };
        findViewById.setOnClickListener(r0);
        View findViewById2 = this.f.findViewById(R.id.h5_bugme_clear_all);
        AnonymousClass2 r02 = new OnClickListener() {
            public void onClick(View v) {
                for (List clear : H5BugmeConsole.this.j) {
                    clear.clear();
                }
                for (H5BugMeListView refresh : H5BugmeConsole.this.i) {
                    refresh.refresh();
                }
            }
        };
        findViewById2.setOnClickListener(r02);
        Builder builder = new Builder(this.d);
        AnonymousClass3 r03 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if ((H5BugmeConsole.this.d == null || !(H5BugmeConsole.this.d instanceof Activity) || !((Activity) H5BugmeConsole.this.d).isFinishing()) && H5BugmeConsole.this.b != null && H5BugmeConsole.this.b.isShowing()) {
                    H5BugmeConsole.this.b.dismiss();
                }
            }
        };
        builder.setPositiveButton("关闭", r03);
        this.b = builder.create();
        this.b.setView(this.f);
        this.b.setCanceledOnTouchOutside(false);
        if (this.b.getWindow() != null) {
            this.b.getWindow().setDimAmount(0.0f);
        }
        this.k = inflater.inflate(R.layout.h5_bugme_info, null);
        if (Nebula.getService() != null && Nebula.getService().getTopSession() != null && Nebula.getService().getTopSession().getTopPage() != null) {
            H5Page page = Nebula.getService().getTopSession().getTopPage();
            if (page != null) {
                String packageNick = H5Utils.getString(page.getParams(), (String) H5AppUtil.package_nick);
                String version = H5Utils.getString(page.getParams(), (String) "appVersion");
                String appId = H5Utils.getString(page.getParams(), (String) "appId");
                ((TextView) this.k.findViewById(R.id.h5_bugme_info_appid)).setText(appId);
                ((TextView) this.k.findViewById(R.id.h5_bugme_info_pkg_nick)).setText(packageNick);
                ((TextView) this.k.findViewById(R.id.h5_bugme_info_version)).setText(version);
                H5AppProvider appProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                if (appProvider != null) {
                    ((TextView) this.k.findViewById(R.id.h5_bugme_info_name)).setText(appProvider.getAppName(appId, version));
                }
                TextView webViewTextView = (TextView) this.k.findViewById(R.id.h5_bugme_info_webview);
                if (!(webViewTextView == null || this.c == null || this.c.get() == null)) {
                    String ua = ((APWebView) this.c.get()).getSettings().getUserAgentString();
                    if (ua != null) {
                        int start = ua.indexOf("Chrome/");
                        String chromeVer = "";
                        if (start > 0) {
                            chromeVer = ua.substring(start + 7, ua.indexOf(Token.SEPARATOR, start));
                        }
                        if (ua.contains("UCBS") || ua.contains("UWS")) {
                            webViewTextView.setText("U4 " + chromeVer);
                        } else if (ua.contains("u3")) {
                            webViewTextView.setText("U3 " + chromeVer);
                        } else {
                            webViewTextView.setText("System WebView " + chromeVer);
                        }
                    }
                }
                View findViewById3 = this.k.findViewById(R.id.h5_bugme_info_button_screenshot);
                AnonymousClass4 r04 = new OnClickListener() {
                    public void onClick(View v) {
                        if (Nebula.getService() != null && Nebula.getService().getTopSession() != null && Nebula.getService().getTopSession().getTopPage() != null) {
                            Nebula.getService().getTopSession().getTopPage().sendEvent(CommonEvents.H5_DEV_SCREENSHOT_UPLOAD, null);
                            H5Environment.showToast(H5BugmeConsole.this.d, "已经成功上报", 0);
                        }
                    }
                };
                findViewById3.setOnClickListener(r04);
            }
        }
    }

    private void b() {
        APWebView apWebView = (APWebView) this.c.get();
        this.c = new WeakReference<>(apWebView);
        ImageButton debugConsoleEntryButton = new H5BugmeConsoleButton(this.d);
        debugConsoleEntryButton.setImageDrawable(H5Environment.getResources().getDrawable(R.drawable.h5_btn_debug_console));
        debugConsoleEntryButton.setBackgroundColor(0);
        debugConsoleEntryButton.setTag(Integer.valueOf(apWebView.hashCode()));
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = 5;
        debugConsoleEntryButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (H5BugmeConsole.this.b != null) {
                    H5BugmeConsole.this.b.show();
                    if (H5BugmeConsole.this.b.getWindow() != null) {
                        H5BugmeConsole.this.b.getWindow().setLayout(H5DimensionUtil.getScreenWidth(H5BugmeConsole.this.d), Math.min(H5DimensionUtil.getScreenHeight(H5BugmeConsole.this.d), H5DimensionUtil.dip2px(H5BugmeConsole.this.d, 500.0f)));
                    }
                }
            }
        });
        ((ViewGroup) apWebView.getView()).addView(debugConsoleEntryButton, params);
    }

    public void recordLog(final JSONObject jsonObject) {
        String logType = jsonObject.getString("type");
        if ("jsapi".equals(logType) || "event".equals(logType)) {
            logType = "jsBridge";
        }
        int index = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= a.length) {
                break;
            } else if (logType.equals(a[i2])) {
                index = i2;
                break;
            } else {
                i2++;
            }
        }
        final int pos = index;
        this.e.post(new Runnable() {
            public void run() {
                if (H5BugmeConsole.this.j != null) {
                    ((List) H5BugmeConsole.this.j.get(pos)).add(new H5BugMeLogMsg(jsonObject));
                }
                if (H5BugmeConsole.this.i != null && H5BugmeConsole.this.b != null && H5BugmeConsole.this.b.isShowing()) {
                    ((H5BugMeListView) H5BugmeConsole.this.i.get(pos)).refresh();
                }
            }
        });
    }

    public void release() {
        this.i.clear();
        this.j.clear();
        this.i = null;
        this.j = null;
        this.b = null;
        this.f = null;
        this.g = null;
        this.h = null;
    }

    public List<H5BugMeLogMsg> getLogDataList(int pos) {
        return this.j.get(pos);
    }

    public int getTabSize() {
        return this.i.size();
    }

    public View getSubContentView(int position) {
        if (position == 0) {
            return this.k;
        }
        return this.i.get(position);
    }
}
