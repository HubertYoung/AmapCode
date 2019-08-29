package com.alipay.mobile.nebulacore.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoEffect;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Param.OptionType;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.api.H5TitleBar;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.provider.H5SharePanelProvider;
import com.alipay.mobile.nebula.provider.H5TransStatusBarColorProvider;
import com.alipay.mobile.nebula.provider.H5ViewProvider;
import com.alipay.mobile.nebula.search.H5InputListen;
import com.alipay.mobile.nebula.search.H5SearchType;
import com.alipay.mobile.nebula.search.H5SearchView;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThirdDisclaimerUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5NavMenuItem;
import com.alipay.mobile.nebula.view.H5TitleBarFrameLayout;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.alipay.mobile.nebula.view.IH5TinyPopMenu;
import com.alipay.mobile.nebula.webview.H5ScrollChangedCallback;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.search.H5SearchInputListen;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.nebulacore.ui.H5ViewHolder;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.taobao.accs.common.Constants;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H5NavigationBar extends H5SimplePlugin {
    private static final Map<String, Integer> u = new HashMap();
    private static final Map<String, Integer> v = new HashMap();
    private H5ViewHolder A;
    private boolean B = false;
    /* access modifiers changed from: private */
    public int C;
    /* access modifiers changed from: private */
    public int D;
    /* access modifiers changed from: private */
    public int E = 1;
    private boolean F = false;
    /* access modifiers changed from: private */
    public int G;
    private float H;
    private boolean I = false;
    private boolean J = false;
    private Context K;
    private boolean L;
    private H5Page a;
    private View b;
    /* access modifiers changed from: private */
    public ColorDrawable c;
    private View d;
    private boolean e;
    private String f;
    private boolean g;
    private boolean h;
    /* access modifiers changed from: private */
    public H5TitleView i;
    private H5NavMenu j;
    private H5SharePanelProvider k;
    private H5NavMenu l;
    private IH5TinyPopMenu m;
    private boolean n;
    private String o = "";
    private boolean p;
    private boolean q = false;
    private String[] r;
    private boolean s = false;
    private boolean t = false;
    private String w;
    private String x;
    private boolean y = false;
    /* access modifiers changed from: private */
    public View z = null;

    static {
        u.put("user", Integer.valueOf(R.drawable.user));
        u.put("info", Integer.valueOf(R.drawable.info));
        u.put("locate", Integer.valueOf(R.drawable.locate));
        u.put("add", Integer.valueOf(R.drawable.plus));
        u.put("scan", Integer.valueOf(R.drawable.richscan));
        u.put(H5SearchType.SEARCH, Integer.valueOf(R.drawable.f20search));
        u.put("settings", Integer.valueOf(R.drawable.settings));
        u.put("help", Integer.valueOf(R.drawable.help));
        u.put(APVideoEffect.TYPE_FILTER, Integer.valueOf(R.drawable.filter));
        u.put("mail", Integer.valueOf(R.drawable.mail));
        u.put("more", Integer.valueOf(R.drawable.h5_titlebar_more_normal));
        v.put("user", Integer.valueOf(R.drawable.userw));
        v.put("info", Integer.valueOf(R.drawable.infow));
        v.put("locate", Integer.valueOf(R.drawable.locatew));
        v.put("add", Integer.valueOf(R.drawable.plusw));
        v.put("scan", Integer.valueOf(R.drawable.richscanw));
        v.put(H5SearchType.SEARCH, Integer.valueOf(R.drawable.searchw));
        v.put("settings", Integer.valueOf(R.drawable.settingsw));
        v.put("help", Integer.valueOf(R.drawable.helpw));
        v.put(APVideoEffect.TYPE_FILTER, Integer.valueOf(R.drawable.filterw));
        v.put("mail", Integer.valueOf(R.drawable.mailw));
        v.put("more", Integer.valueOf(R.drawable.h5_white_titlebar_more_normal));
    }

    public H5NavigationBar(Context context, Bundle startParams, H5ViewHolder viewHolder) {
        this.K = context;
        this.g = false;
        this.h = false;
        this.e = false;
        this.A = viewHolder;
        H5ViewProvider viewProvider = (H5ViewProvider) H5ProviderManagerImpl.getInstance().getProvider(H5ViewProvider.class.getName());
        if (viewProvider != null) {
            this.i = viewProvider.createTitleView(context);
        }
        if (this.i == null) {
            this.i = new H5TitleBar(context);
        }
        this.b = this.i.getContentView();
        this.b.setTag("h5_titlebar");
        this.c = this.i.getContentBgView();
        if (!a(this.a)) {
            this.i.setOptionType(OptionType.MENU, 0, true);
        }
        String navSearchBar_type = H5Utils.getString(startParams, (String) H5Param.LONG_NAV_SEARCH_BAR_TYPE);
        if (H5Utils.getBoolean(startParams, (String) H5Param.LONG_PACKAGE_LOADING_SHOWN, false) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_newloadpage"))) {
            this.i.setTitle(H5Utils.getString(startParams, (String) "walletAppName"));
        }
        if (!TextUtils.isEmpty(navSearchBar_type)) {
            H5Log.d("H5NavigationBar", "navSearchBar_type:" + navSearchBar_type);
            this.y = true;
            this.z = this.i.setTitleBarSearch(startParams);
        }
        a(false);
        this.j = new H5NavMenu(context);
        this.L = H5Utils.getBoolean(startParams, (String) "isTinyApp", false);
        this.l = new H5NavMenu(context);
        this.w = H5Utils.getString(startParams, (String) "appId");
        this.x = H5Utils.getString(startParams, (String) "appVersion");
    }

    public H5SharePanelProvider getH5SharePanelProvider() {
        if (this.k == null) {
            this.k = (H5SharePanelProvider) Nebula.getProviderManager().getProvider(H5SharePanelProvider.class.getName());
        }
        return this.k;
    }

    public void setPage(H5Page h5Page) {
        this.J = false;
        this.a = h5Page;
        h5Page.setH5TitleBar(this.i);
        if (this.y) {
            H5ProviderManager providerManager = Nebula.getProviderManager();
            String name = H5InputListen.class.getName();
            H5SearchInputListen h5SearchInputListen = new H5SearchInputListen(h5Page);
            providerManager.setProvider(name, h5SearchInputListen);
        }
        this.j.setPage(h5Page);
        this.l.setPage(h5Page);
        Bundle params = h5Page.getParams();
        if (a(h5Page)) {
            a(this.K);
        } else {
            boolean visible = H5Utils.getBoolean(params, (String) "showOptionMenu", true);
            if (!this.F) {
                b(visible);
            }
        }
        this.i.setH5Page(h5Page);
        this.n = H5Utils.getBoolean(params, (String) "readTitle", true);
        JSONObject defaultTitleParam = new JSONObject();
        String defaultTitle = H5Utils.getString(params, (String) H5Param.LONG_TITLE_IMAGE);
        if (!TextUtils.isEmpty(defaultTitle)) {
            defaultTitleParam.put((String) "image", (Object) defaultTitle);
            defaultTitleParam.put((String) "fromJS", (Object) Boolean.valueOf(false));
            c(defaultTitleParam);
        } else {
            String defaultTitle2 = H5Utils.getString(params, (String) H5Param.LONG_DEFAULT_TITLE);
            if (!TextUtils.isEmpty(defaultTitle2)) {
                defaultTitleParam.put((String) "title", (Object) defaultTitle2);
                defaultTitleParam.put((String) "fromJS", (Object) Boolean.valueOf(false));
                c(defaultTitleParam);
            }
        }
        if (!H5Utils.getBoolean(params, (String) H5Param.LONG_SHOW_TITLEBAR, true) && !H5Environment.isInWallet()) {
            b();
        }
        a(H5Utils.getBoolean(params, (String) "showTitleLoading", false), (H5BridgeContext) null);
        int mode = H5ThirdDisclaimerUtils.needShowDisclaimer(params, "");
        if (mode == 1 || mode == 2) {
            this.i.showTitleDisclaimer(true);
            this.i.getMainTitleView().setMaxWidth(H5DimensionUtil.dip2px(H5Utils.getContext(), 200.0f));
        }
        int titleBarColor = H5Utils.getInt(params, (String) H5Param.LONG_TITLE_BAR_COLOR, -16777216) | -16777216;
        if (params != null && params.containsKey(H5Param.LONG_TITLE_BAR_COLOR)) {
            setContentBgViewColor(titleBarColor);
            if (titleBarColor != -1) {
                this.i.switchToWhiteTheme();
                c();
            } else {
                this.i.switchToBlueTheme();
                d();
            }
        }
        a();
        if (this.i != null && Nebula.useH5StatusBar(h5Page)) {
            int color = 1325400064;
            H5TransStatusBarColorProvider h5TransStatusBarColorProvider = (H5TransStatusBarColorProvider) Nebula.getProviderManager().getProvider(H5TransStatusBarColorProvider.class.getName());
            if (h5TransStatusBarColorProvider != null) {
                color = h5TransStatusBarColorProvider.getColor();
            }
            this.i.openTranslucentStatusBarSupport(color);
        }
        if (H5Utils.canTransferH5ToTiny(this.w) || (a(h5Page) && H5Utils.getBoolean(this.a.getParams(), (String) "isTinyApp", false) && !H5Utils.getBoolean(this.a.getParams(), (String) H5Param.LONG_ISPRERENDER, false) && !H5Utils.isTinyMiniService(this.a.getParams()))) {
            int sessionSize = 0;
            H5Session h5Session = Nebula.getService().getSession(H5Utils.getString(h5Page.getParams(), (String) "sessionId"));
            if (!(h5Session == null || h5Session.getPages() == null)) {
                sessionSize = Nebula.getSessionPagesWithOutPrerender(h5Session.getPages()).size();
            }
            if (sessionSize == 1 || H5Utils.getBoolean(this.a.getParams(), (String) "closeAllWindow", false) || TextUtils.equals(H5Fragment.subtab, H5Utils.getString(this.a.getParams(), (String) H5Fragment.fragmentType))) {
                c(false, null);
            }
        }
    }

    private void a() {
        Bundle params = this.a.getParams();
        this.p = false;
        if (params != null && params.containsKey(H5Param.LONG_TRANSPARENT_TITLE)) {
            this.o = H5Utils.getString(params, (String) H5Param.LONG_TRANSPARENT_TITLE);
            if (!TextUtils.isEmpty(this.o)) {
                this.p = TextUtils.equals("always", this.o) || TextUtils.equals("auto", this.o);
                if (TextUtils.equals("custom", this.o) && params.containsKey(H5Param.LONG_BACKBTN_IMAGE) && params.containsKey(H5Param.LONG_BACKBTN_TEXTCOLOR) && params.containsKey(H5Param.LONG_TITLE_COLOR)) {
                    String bbi = H5Utils.getString(params, (String) H5Param.LONG_BACKBTN_IMAGE);
                    int bbc = H5Utils.getInt(params, (String) H5Param.LONG_BACKBTN_TEXTCOLOR);
                    int tc = H5Utils.getInt(params, (String) H5Param.LONG_TITLE_COLOR);
                    if ((TextUtils.isEmpty(bbi) || TextUtils.equals(bbi, "default")) && bbc == -16777216 && tc == -16777216) {
                        this.p = false;
                    } else {
                        this.p = true;
                    }
                }
                H5Log.d("H5NavigationBar", "transTitle is " + this.p);
            }
        }
        int currentColor = this.c.getColor() | -16777216;
        if (this.p) {
            if (TextUtils.equals(this.o, "auto")) {
                this.C = H5Utils.getInt(params, (String) H5Param.LONG_TITLE_SCROLLDISTANCE);
                int scale = this.C / 255;
                if (scale == 0) {
                    scale = 1;
                }
                this.E = scale;
                this.D = (this.C * 3) / 4;
                H5Log.d("H5NavigationBar", "transparentTitleBar finalMaxScrollHeight is " + this.C + ", switchThemePoint is " + this.D);
                this.B = false;
                b(this.C, this.E, false);
                a(currentColor, this.D, false);
                this.G = this.a.getWebView().getScrollY();
                if ("YES".equalsIgnoreCase(H5Utils.getString(params, (String) H5Param.LONG_TRANSPARENT_TITLE_TEXTAUTO))) {
                    this.I = true;
                } else {
                    this.I = false;
                }
            } else {
                this.H = 0.0f;
                this.B = true;
                this.I = false;
            }
            this.d.setVisibility(8);
            this.d = this.i.getHdividerInTitle();
            this.d.setVisibility(0);
            boolean hasPackage = H5Utils.getBoolean(this.a.getParams(), (String) Nebula.HAS_H5_PKG, false);
            if (currentColor != -1) {
                this.d.setBackgroundColor(currentColor);
            } else {
                View view = this.d;
                if (!this.L) {
                    currentColor = -16777216 | Color.parseColor("#C6C8C9");
                }
                view.setBackgroundColor(currentColor);
                if (!TextUtils.equals(this.o, "custom")) {
                    H5Log.d("H5NavigationBar", "transTitle appid " + this.w + ", appVersion " + this.x + ", hasPackage " + hasPackage);
                    if (hasPackage) {
                        this.i.switchToWhiteTheme();
                        c();
                    } else if (!this.F) {
                        this.i.switchToBlueTheme();
                        d();
                    }
                }
            }
            a(this.o, params);
        } else {
            this.H = 1.0f;
            if (currentColor != -1) {
                this.d.setBackgroundColor(currentColor);
            } else {
                View view2 = this.d;
                if (!this.L) {
                    currentColor = -16777216 | Color.parseColor("#C6C8C9");
                }
                view2.setBackgroundColor(currentColor);
                this.i.switchToBlueTheme();
                d();
            }
            setTitleAlpha();
            this.B = true;
            this.I = false;
            if (params.containsKey(H5Param.LONG_BACKBTN_IMAGE) && params.containsKey(H5Param.LONG_BACKBTN_TEXTCOLOR) && params.containsKey(H5Param.LONG_TITLE_COLOR)) {
                String bbi2 = H5Utils.getString(params, (String) H5Param.LONG_BACKBTN_IMAGE);
                int bbc2 = H5Utils.getInt(params, (String) H5Param.LONG_BACKBTN_TEXTCOLOR);
                int tc2 = H5Utils.getInt(params, (String) H5Param.LONG_TITLE_COLOR);
                if (!(TextUtils.equals(bbi2, "default") || bbc2 == -16777216 || tc2 == -16777216)) {
                    this.J = true;
                    this.i.setBackCloseBtnImage(bbi2);
                    this.i.setTitleTxtColor(tc2);
                }
            }
        }
        this.F = false;
    }

    private void a(String transparentTitle, Bundle params) {
        if (!TextUtils.isEmpty(transparentTitle)) {
            setTitleAlpha();
            if (TextUtils.equals(transparentTitle, "auto")) {
                this.a.getWebView().setOnScrollChangedCallback(new H5ScrollChangedCallback() {
                    public void onScroll(int dx, int dy) {
                        H5NavigationBar.this.G = H5NavigationBar.this.G + dy;
                        H5NavigationBar.this.b(H5NavigationBar.this.C, H5NavigationBar.this.E, true);
                        int currentColor = H5NavigationBar.this.c.getColor() | -16777216;
                        if (H5NavigationBar.this.z != null) {
                            H5SearchView h5SearchView = (H5SearchView) Nebula.getProviderManager().getProvider(H5SearchView.class.getName());
                            if (h5SearchView != null) {
                                h5SearchView.setSearchBarColor(currentColor);
                            }
                        }
                        H5NavigationBar.this.a(currentColor, H5NavigationBar.this.D, true);
                    }
                });
            }
            if (TextUtils.equals(transparentTitle, "custom")) {
                String backBtnImage = H5Utils.getString(params, (String) H5Param.LONG_BACKBTN_IMAGE, (String) "");
                int titleColor = H5Utils.getInt(params, (String) H5Param.LONG_TITLE_COLOR);
                this.i.setBackCloseBtnImage(backBtnImage);
                this.i.setTitleTxtColor(titleColor);
            }
            if ("YES".equalsIgnoreCase(H5Utils.getString(params, (String) H5Param.LONG_TITLE_PENETRATE, (String) "NO")) && (this.i instanceof H5TitleBar)) {
                View contentView = this.i.getContentView();
                if (contentView instanceof H5TitleBarFrameLayout) {
                    ((H5TitleBarFrameLayout) contentView).setPreventTouchEvent(false);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        if (r6.z != null) goto L_0x001d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r7, int r8, boolean r9) {
        /*
            r6 = this;
            if (r9 == 0) goto L_0x0059
            int r0 = r6.G
        L_0x0004:
            boolean r3 = r6.B
            if (r3 != 0) goto L_0x0058
            r3 = -1
            if (r7 != r3) goto L_0x0024
            int r3 = java.lang.Math.abs(r0)
            if (r3 < r8) goto L_0x0064
            com.alipay.mobile.nebula.view.H5TitleView r3 = r6.i
            r3.switchToBlueTheme()
            r6.d()
            android.view.View r3 = r6.z
            if (r3 == 0) goto L_0x0024
        L_0x001d:
            android.view.View r3 = r6.z
            r4 = 1065353216(0x3f800000, float:1.0)
            r3.setAlpha(r4)
        L_0x0024:
            android.graphics.drawable.ColorDrawable r3 = r6.c
            float r4 = r6.H
            r5 = 1132396544(0x437f0000, float:255.0)
            float r4 = r4 * r5
            int r4 = (int) r4
            r3.setAlpha(r4)
            android.view.View r3 = r6.d
            float r4 = r6.H
            r3.setAlpha(r4)
            boolean r3 = r6.I
            if (r3 == 0) goto L_0x0058
            com.alipay.mobile.nebula.view.H5TitleView r3 = r6.i
            if (r3 == 0) goto L_0x0058
            com.alipay.mobile.nebula.view.H5TitleView r3 = r6.i
            android.widget.TextView r1 = r3.getMainTitleView()
            if (r1 == 0) goto L_0x004b
            float r3 = r6.H
            r1.setAlpha(r3)
        L_0x004b:
            com.alipay.mobile.nebula.view.H5TitleView r3 = r6.i
            android.widget.TextView r2 = r3.getSubTitleView()
            if (r2 == 0) goto L_0x0058
            float r3 = r6.H
            r2.setAlpha(r3)
        L_0x0058:
            return
        L_0x0059:
            com.alipay.mobile.h5container.api.H5Page r3 = r6.a
            com.alipay.mobile.nebula.webview.APWebView r3 = r3.getWebView()
            int r0 = r3.getScrollY()
            goto L_0x0004
        L_0x0064:
            com.alipay.mobile.nebula.view.H5TitleView r3 = r6.i
            r3.switchToWhiteTheme()
            r6.c()
            android.view.View r3 = r6.z
            if (r3 == 0) goto L_0x0024
            float r3 = r6.H
            r4 = 1041865114(0x3e19999a, float:0.15)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x001d
            android.view.View r3 = r6.z
            float r4 = r6.H
            r3.setAlpha(r4)
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.view.H5NavigationBar.a(int, int, boolean):void");
    }

    /* access modifiers changed from: private */
    public void b(int finalMaxScrollHeight, int finalScale, boolean ifAutoMode) {
        int dimen = ifAutoMode ? this.G : this.a.getWebView().getScrollY();
        if (Math.abs(dimen) < finalMaxScrollHeight) {
            this.H = ((float) Math.abs(dimen / finalScale)) / 255.0f;
        } else if (dimen <= 0) {
            this.H = 0.0f;
        } else {
            this.H = 1.0f;
        }
    }

    public View getContent() {
        return this.b;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.SHOW_TITLE_BAR);
        filter.addAction(CommonEvents.HIDE_TITLE_BAR);
        filter.addAction("showTitleLoading");
        filter.addAction(CommonEvents.HIDE_TITLE_LOADING);
        filter.addAction("showOptionMenu");
        filter.addAction(CommonEvents.HIDE_OPTION_MENU);
        filter.addAction("showBackButton");
        filter.addAction("hideBackButton");
        filter.addAction(CommonEvents.SET_OPTION_MENU);
        filter.addAction("setOptionMenuItem");
        filter.addAction(CommonEvents.SET_TITLE);
        filter.addAction("readTitle");
        filter.addAction(CommonEvents.SET_TOOL_MENU);
        filter.addAction(CommonEvents.SHOW_POP_MENU);
        filter.addAction(CommonEvents.H5_SHOW_TIPS);
        filter.addAction(CommonEvents.H5_PAGE_STARTED);
        filter.addAction(CommonEvents.H5_PAGE_SHOW_CLOSE);
        filter.addAction(CommonEvents.H5_PAGE_FINISHED);
        filter.addAction(CommonEvents.H5_PAGE_RECEIVED_TITLE);
        filter.addAction("showFavorites");
        filter.addAction(CommonEvents.HIDE_FAVORITES);
        filter.addAction(CommonEvents.SET_TITLE_COLOR);
        filter.addAction(CommonEvents.GET_TITLE_COLOR);
        filter.addAction(CommonEvents.H5_TITLEBAR_OPTIONS);
        filter.addAction("h5PageErrorForTitlebar");
        filter.addAction("setBarBottomLineColor");
        filter.addAction("setTransparentTitle");
        filter.addAction(CommonEvents.HIDE_CLOSE_BUTTON);
        filter.addAction("showDisClaimer");
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        JSONObject param = event.getParam();
        if (CommonEvents.H5_PAGE_STARTED.equals(action)) {
            this.e = true;
            this.g = false;
            Bundle params = this.a.getParams();
            if (!H5Utils.getBoolean(params, (String) "isTinyApp", false) && !H5Utils.canTransferH5ToTinyWithAnimation(this.w, params)) {
                this.i.showBackButton(true);
            }
            int appType = H5Utils.getInt(params, (String) "appType", 2);
            if (!H5AppUtil.isPublicAppId(H5Utils.getString(params, (String) "appId")) && appType == 2) {
                if (this.j.hasMenu(H5Param.MENU_SHARE)) {
                    H5Log.e((String) "H5NavigationBar", (String) "Share menu has been already existed, won't bother to add more");
                    return true;
                }
                this.j.removeMenu(H5Param.MENU_SHARE_FRIEND);
                Resources resources = H5Environment.getResources();
                int size = 0;
                int i2 = 0;
                while (true) {
                    if (i2 >= this.j.a.size()) {
                        break;
                    } else if (H5Param.MENU_COPY.equals(((H5NavMenuItem) this.j.a.get(i2)).tag) || H5Param.MENU_FAVORITES.equals(((H5NavMenuItem) this.j.a.get(i2)).tag)) {
                        size = i2;
                    } else {
                        i2++;
                    }
                }
                size = i2;
                this.j.addMenu(size, new H5NavMenuItem(resources.getString(R.string.h5_menu_share), H5Param.MENU_SHARE_FRIEND, resources.getDrawable(R.drawable.h5_nav_share_friend), false));
            }
        } else if (CommonEvents.H5_PAGE_RECEIVED_TITLE.equals(action)) {
            a(param);
        } else if (CommonEvents.H5_PAGE_FINISHED.equals(action)) {
            this.s = true;
            if (H5Utils.getBoolean(param, (String) H5Param.PAGE_UPDATED, false)) {
                a(param);
            }
            CharSequence currentTitle = this.i.getTitle();
            if (!TextUtils.isEmpty(this.f) && TextUtils.isEmpty(currentTitle) && !e()) {
                this.i.setTitle(this.f);
            }
            if (this.p && !this.t) {
                boolean hasPackage = H5Utils.getBoolean(this.a.getParams(), (String) Nebula.HAS_H5_PKG, false);
                H5Log.d("H5NavigationBar", "transTitle appid " + this.w + ", appVersion " + this.x + ", hasPackage " + hasPackage);
                if (!hasPackage && !TextUtils.equals(this.o, "custom")) {
                    this.i.switchToWhiteTheme();
                    c();
                }
            }
        } else if ("showFavorites".equals(action)) {
            if (this.j.hasMenu(H5Param.MENU_FAVORITES)) {
                H5Log.e((String) "H5NavigationBar", (String) "favorites menu has been already existed");
                return true;
            }
            Resources resources2 = H5Environment.getResources();
            String tag = "";
            if (this.j.a.size() > 0) {
                tag = ((H5NavMenuItem) this.j.a.get(0)).tag;
            }
            if (tag.equals(H5Param.MENU_COPY)) {
                this.j.addMenu(0, new H5NavMenuItem(resources2.getString(R.string.h5_menu_favorites), H5Param.MENU_FAVORITES, resources2.getDrawable(R.drawable.h5_nav_favorites), false));
            }
            if (tag.equals(H5Param.MENU_SHARE_FRIEND)) {
                this.j.addMenu(1, new H5NavMenuItem(resources2.getString(R.string.h5_menu_favorites), H5Param.MENU_FAVORITES, resources2.getDrawable(R.drawable.h5_nav_favorites), false));
            }
            bridgeContext.sendBridgeResult("status", "true");
        } else if (CommonEvents.HIDE_FAVORITES.equals(action) && this.j.hasMenu(H5Param.MENU_FAVORITES)) {
            this.j.removeMenu(H5Param.MENU_FAVORITES);
            bridgeContext.sendBridgeResult("status", "true");
        }
        return false;
    }

    public void onRelease() {
        this.a = null;
        if (this.i != null) {
            this.i.releaseViewList();
        }
        if (this.m != null) {
            this.m.onRelease();
        }
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        int parseColor;
        String action = event.getAction();
        JSONObject param = event.getParam();
        if (!CommonEvents.SHOW_TITLE_BAR.equals(action) && !CommonEvents.HIDE_TITLE_BAR.equals(action)) {
            if ("showOptionMenu".equals(action)) {
                if (H5Utils.canTransferH5ToTiny(this.w) && this.m != null) {
                    this.m.setH5ShowOptionMenuFlag();
                }
                b(true, bridgeContext);
            } else if (CommonEvents.SET_OPTION_MENU.equals(action) || "setOptionMenuItem".equals(action)) {
                a(event, bridgeContext);
            } else if (CommonEvents.HIDE_OPTION_MENU.equals(action)) {
                if (!H5Utils.canTransferH5ToTiny(this.w)) {
                    b(false, bridgeContext);
                }
            } else if ("showBackButton".equals(action)) {
                c(true, bridgeContext);
            } else if ("hideBackButton".equals(action)) {
                c(false, bridgeContext);
            } else if (CommonEvents.SET_TITLE.equals(action)) {
                c(event.getParam());
                bridgeContext.sendBridgeResult("success", "true");
            } else if ("readTitle".equals(action)) {
                this.n = H5Utils.getBoolean(param, (String) "readTitle", true);
            } else if (CommonEvents.H5_SHOW_TIPS.equals(action)) {
                H5Tip.showTip(this.a.getContext().getContext(), (ViewGroup) this.b, H5Utils.getString(event.getParam(), (String) H5Param.TIP_CONTENT));
            } else if (CommonEvents.H5_PAGE_SHOW_CLOSE.equals(action) || CommonEvents.HIDE_CLOSE_BUTTON.equals(action)) {
                a(H5Utils.getBoolean(param, (String) ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW, false));
            } else if ("showTitleLoading".equals(action)) {
                a(true, bridgeContext);
            } else if (CommonEvents.HIDE_TITLE_LOADING.equals(action)) {
                a(false, bridgeContext);
            } else if (CommonEvents.SET_TOOL_MENU.equals(action) || CommonEvents.SHOW_POP_MENU.equals(action)) {
                if (CommonEvents.SHOW_POP_MENU.equals(action)) {
                    this.l.setIsShowPopMenu(true);
                    this.l.setMenu(event, this.e);
                } else {
                    this.j.setIsShowPopMenu(false);
                    if (this.L) {
                        this.j.clearMenuList();
                    }
                    this.j.setMenu(event, this.e);
                }
                if (CommonEvents.SHOW_POP_MENU.equals(action)) {
                    if (!H5Utils.canTransferH5ToTiny(this.w)) {
                        this.l.showMenu(this.i.getPopAnchor());
                    } else if (this.m != null) {
                        this.m.setH5MenuList(this.l.getNavMenuItemList(), true);
                    }
                }
                bridgeContext.sendBridgeResult("success", "true");
            } else if (CommonEvents.SET_TITLE_COLOR.equals(action)) {
                if (this.c != null) {
                    JSONObject params = event.getParam();
                    int titleBarColor = H5Utils.getInt(params, (String) "color", -16777216);
                    boolean reset = H5Utils.getBoolean(params, (String) "reset", false);
                    boolean resetTransparent = H5Utils.getBoolean(params, (String) "resetTransparent", false);
                    int titleBarColor2 = titleBarColor | -16777216;
                    if (params != null && params.containsKey("color") && !reset && !resetTransparent) {
                        setContentBgViewColor(titleBarColor2);
                        if (!this.J) {
                            if (titleBarColor2 != -1) {
                                this.i.switchToWhiteTheme();
                                c();
                            } else {
                                this.i.switchToBlueTheme();
                                d();
                            }
                        }
                        if (titleBarColor2 != -1) {
                            this.d.setBackgroundColor(titleBarColor2);
                        }
                        if (this.p) {
                            setTitleAlpha();
                        }
                        if (bridgeContext != null) {
                            bridgeContext.sendBridgeResult("success", "true");
                        }
                        return true;
                    } else if (reset) {
                        this.i.resetTitleColor(-1);
                        this.i.switchToBlueTheme();
                        d();
                        View view = this.d;
                        if (this.L) {
                            parseColor = -1;
                        } else {
                            parseColor = -16777216 | Color.parseColor("#C6C8C9");
                        }
                        view.setBackgroundColor(parseColor);
                        if (this.p) {
                            setTitleAlpha();
                        }
                        if (bridgeContext != null) {
                            bridgeContext.sendBridgeResult("success", "true");
                        }
                    } else if (!resetTransparent || !this.p) {
                        JSONObject info = new JSONObject();
                        info.put((String) "error", (Object) "2");
                        bridgeContext.sendBridgeResult(info);
                    } else {
                        this.i.switchToWhiteTheme();
                        c();
                        if (this.p) {
                            setTitleAlpha();
                        }
                        if (bridgeContext != null) {
                            bridgeContext.sendBridgeResult("success", "true");
                        }
                    }
                }
            } else if (CommonEvents.GET_TITLE_COLOR.equals(action)) {
                a(bridgeContext);
            } else if ("h5PageErrorForTitlebar".equals(action)) {
                if (this.i != null && H5Environment.isInWallet()) {
                    this.i.switchToBlueTheme();
                    d();
                    this.t = true;
                    if (this.p && this.a != null) {
                        this.B = true;
                    }
                }
            } else if ("setBarBottomLineColor".equals(action)) {
                if (this.d != null) {
                    int color = H5Utils.getInt(param, (String) "color", -16777216) | -16777216;
                    H5Log.d("H5NavigationBar", "setBarBottomLineColor color is " + color);
                    this.d.setBackgroundColor(color);
                }
                bridgeContext.sendSuccess();
            } else if ("setTransparentTitle".equals(action)) {
                String transparentTitleType = H5Utils.getString(param, (String) H5Param.LONG_TRANSPARENT_TITLE);
                H5Log.d("H5NavigationBar", "setTransparentTitle type " + transparentTitleType);
                Bundle startParams = this.a.getParams();
                H5Log.d("H5NavigationBar", "setTransparentTitle originType " + H5Utils.getString(startParams, (String) H5Param.LONG_TRANSPARENT_TITLE));
                startParams.putString(H5Param.LONG_TRANSPARENT_TITLE, transparentTitleType);
                if (this.A != null) {
                    this.F = true;
                    a();
                    this.A.refreshView();
                }
                bridgeContext.sendSuccess();
                if (this.a != null) {
                    this.a.sendEvent(CommonEvents.H5_TITLEBAR_TRANSSTATE_CHANGE, null);
                }
            } else if (CommonEvents.H5_TITLEBAR_OPTIONS.equals(action)) {
                boolean fromMenu = H5Utils.getBoolean(param, (String) "fromMenu", false);
                JSONObject data = new JSONObject();
                data.put((String) "data", (Object) param);
                this.a.getBridge().sendToWeb(action, data, null);
                if (fromMenu) {
                    if (getH5SharePanelProvider() == null) {
                        this.j.showMenu(this.i.getPopAnchor());
                    } else if (!this.q) {
                        getH5SharePanelProvider().showSharePanel(this.a, this.s);
                    }
                }
            } else if (!"showDisClaimer".equals(action)) {
                return false;
            } else {
                int mode = H5Utils.getInt(param, (String) Constants.KEY_MODE);
                boolean show = mode == 1 || mode == 2;
                this.i.showTitleDisclaimer(show);
                if (show) {
                    this.i.getMainTitleView().setMaxWidth(H5DimensionUtil.dip2px(H5Utils.getContext(), 200.0f));
                }
                return false;
            }
        }
        return true;
    }

    private void a(boolean show, H5BridgeContext bridgeContext) {
        this.i.showTitleLoading(show);
        if (bridgeContext != null) {
            bridgeContext.sendBridgeResult("success", "true");
        }
    }

    private void a(boolean show) {
        this.i.showCloseButton(show);
    }

    private void b() {
        this.b.setVisibility(8);
        this.d.setVisibility(8);
    }

    private void b(boolean show) {
        this.i.showOptionMenu(show);
    }

    private void b(boolean show, H5BridgeContext bridgeContext) {
        b(show);
        bridgeContext.sendBridgeResult("success", "true");
    }

    private void c(boolean show, H5BridgeContext bridgeContext) {
        this.i.showBackButton(show);
        if (bridgeContext != null) {
            bridgeContext.sendBridgeResult("success", "true");
        }
    }

    private void a(JSONObject param) {
        if (!e()) {
            if (param.get("title") == null) {
                H5Log.d("H5NavigationBar", "case 1, page title ignored!");
                return;
            }
            H5Log.d("H5NavigationBar", "readTitle:" + this.n);
            if (!this.n || this.g || this.h) {
                H5Log.d("H5NavigationBar", "case 2, page title ignored!");
                return;
            }
            String title = H5Utils.getString(param, (String) "title");
            if (this.a == null || !TextUtils.equals(title, this.a.getUrl())) {
                try {
                    String url = URLDecoder.decode(H5Utils.getString(param, (String) "url"));
                    H5TitleView h5TitleView = this.i;
                    if (url.equals(new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(title).toString())) {
                        title = this.f;
                    }
                    h5TitleView.setTitle(title);
                } catch (IllegalArgumentException e2) {
                    H5Log.e((String) "H5NavigationBar", (Throwable) e2);
                    this.i.setTitle(this.f);
                }
            } else {
                H5Log.d("H5NavigationBar", title + " not show");
            }
        }
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param == null || param.isEmpty()) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        this.q = H5Utils.getBoolean(param, (String) "preventDefault", false);
        if (!this.q) {
            if (!H5Utils.canTransferH5ToTiny(this.w)) {
                this.i.setOptionMenu(param);
            }
            boolean isTinySence = TextUtils.equals("tiny", H5Utils.getString(param, (String) "bizType"));
            boolean reset = H5Utils.getBoolean(param, (String) "reset", false);
            if (this.m != null) {
                if (reset) {
                    if (!a(this.a)) {
                        this.i.setOptionType(OptionType.MENU, 0, true);
                    } else {
                        this.i.setOptionType(OptionType.NONE, 0, true);
                    }
                    this.m.setTitleBarRightButtonViewShow(true);
                } else {
                    this.m.setTitleBarRightButtonViewShow(false);
                }
            }
            if (!reset || isTinySence) {
                boolean override = H5Utils.getBoolean(param, (String) "override", false);
                b(param);
                JSONArray menus = H5Utils.getJSONArray(param, "menus", null);
                if (menus == null || menus.isEmpty()) {
                    this.r = new String[2];
                    this.r[0] = H5Utils.getString(param, (String) "icontype");
                    this.r[1] = "stupid";
                    int index = isTinySence ? 1 : 0;
                    if (u == null || u.isEmpty() || !u.containsKey(this.r[0])) {
                        String icon = H5Utils.getString(param, (String) H5Param.MENU_ICON);
                        if (!TextUtils.isEmpty(icon)) {
                            a(icon, index);
                        }
                    } else if ((-16777216 | this.i.getMainTitleView().getCurrentTextColor()) != -15658735) {
                        a(a(v.get(this.r[0]).intValue()), index);
                    } else {
                        a(a(u.get(this.r[0]).intValue()), index);
                    }
                } else if (!override || isTinySence) {
                    JSONObject menusItem = menus.getJSONObject(0);
                    this.r = new String[2];
                    this.r[0] = "stupid";
                    this.r[1] = H5Utils.getString(menusItem, (String) "icontype");
                    if (u == null || u.isEmpty() || !u.containsKey(this.r[1])) {
                        String icon2 = H5Utils.getString(menusItem, (String) H5Param.MENU_ICON);
                        if (!TextUtils.isEmpty(icon2)) {
                            a(icon2, 1);
                        }
                    } else if ((-16777216 | this.i.getMainTitleView().getCurrentTextColor()) != -15658735) {
                        a(a(v.get(this.r[1]).intValue()), 1);
                    } else {
                        a(a(u.get(this.r[1]).intValue()), 1);
                    }
                } else {
                    int menuSize = menus.size() > 2 ? 2 : menus.size();
                    this.r = new String[menuSize];
                    for (int i2 = 0; i2 < menuSize; i2++) {
                        JSONObject menusItem2 = menus.getJSONObject(i2);
                        this.r[i2] = H5Utils.getString(menusItem2, (String) "icontype");
                        if (u == null || u.isEmpty() || !u.containsKey(this.r[i2])) {
                            String icon3 = H5Utils.getString(menusItem2, (String) H5Param.MENU_ICON);
                            if (!TextUtils.isEmpty(icon3)) {
                                a(icon3, i2);
                            }
                        } else if ((-16777216 | this.i.getMainTitleView().getCurrentTextColor()) != -15658735) {
                            a(a(v.get(this.r[i2]).intValue()), i2);
                        } else {
                            a(a(u.get(this.r[i2]).intValue()), i2);
                        }
                    }
                }
                bridgeContext.sendBridgeResult("success", "true");
                return;
            }
            this.q = false;
        }
    }

    private void b(JSONObject params) {
        if (H5Utils.canTransferH5ToTiny(this.w)) {
            JSONArray menus = H5Utils.getJSONArray(params, "menus", null);
            if (menus == null || menus.isEmpty()) {
                String title = H5Utils.getString(params, (String) "title");
                if (!TextUtils.isEmpty(title) && this.m != null) {
                    List menuItemList = new ArrayList();
                    menuItemList.add(new H5NavMenuItem(title, CommonEvents.H5_TITLEBAR_OPTIONS, null, false));
                    this.m.setH5OptionMenuTextFlag();
                    this.m.setH5MenuList(menuItemList, false);
                }
            } else if (this.m != null) {
                List menuItemList2 = new ArrayList();
                for (int i2 = 0; i2 < menus.size(); i2++) {
                    String title2 = H5Utils.getString(menus.getJSONObject(i2), (String) "title");
                    if (!TextUtils.isEmpty(title2)) {
                        menuItemList2.add(new H5NavMenuItem(title2, CommonEvents.H5_TITLEBAR_OPTIONS, null, false));
                        this.m.setH5OptionMenuTextFlag();
                    }
                }
                this.m.setH5MenuList(menuItemList2, false);
            }
        }
    }

    private void a(String imageUrl, final int index) {
        if (!imageUrl.startsWith("http")) {
            Bitmap bitmap = H5ImageUtil.base64ToBitmap(imageUrl);
            if (bitmap != null) {
                H5Log.d("H5NavigationBar", "loadImageAsync from base64");
                a(bitmap, index);
                return;
            }
        }
        if (this.a != null && this.a.getSession() != null && this.a.getSession().getWebProvider() != null) {
            String finalImageUrl = a(imageUrl);
            String onlineHost = H5Utils.getString(this.a.getParams(), (String) H5Param.ONLINE_HOST);
            H5Log.d("H5NavigationBar", "loadImageAsync originUrl " + imageUrl + ", finalImageUrl " + finalImageUrl + ", onlineHost " + onlineHost);
            if (!TextUtils.isEmpty(finalImageUrl)) {
                b(index);
                if (TextUtils.isEmpty(onlineHost) || !finalImageUrl.startsWith(onlineHost)) {
                    Nebula.loadImage(finalImageUrl, new H5ImageListener() {
                        public void onImage(Bitmap bitmap) {
                            if (bitmap != null) {
                                H5Log.d("H5NavigationBar", "loadImageAsync from online");
                                H5NavigationBar.this.a(bitmap, index);
                            }
                        }
                    });
                } else {
                    this.a.getSession().getWebProvider().getContent(finalImageUrl, (ResponseListen) new ResponseListen() {
                        public void onGetResponse(WebResourceResponse webResourceResponse) {
                            if (webResourceResponse != null && webResourceResponse.getData() != null) {
                                H5Log.d("H5NavigationBar", "loadImageAsync from offline");
                                Bitmap bitmap = BitmapFactory.decodeStream(webResourceResponse.getData());
                                if (bitmap != null) {
                                    H5NavigationBar.this.a(bitmap, index);
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private Bitmap a(int resId) {
        if (this.a == null) {
            return null;
        }
        return BitmapFactory.decodeResource(this.a.getContext().getContext().getResources(), resId);
    }

    private void b(final int index) {
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5NavigationBar.this.i.ctrlbtIcon(index, 4, true);
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(final Bitmap bitmap, final int index) {
        if (bitmap != null) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5NavigationBar.this.i.setOptionType(OptionType.ICON, index, true);
                    H5NavigationBar.this.i.setBtIcon(bitmap, index);
                }
            });
        }
    }

    private void c(JSONObject param) {
        if (param != null && !param.isEmpty() && !e()) {
            if (H5Utils.getBoolean(param, (String) "fromJS", true)) {
                this.g = true;
            }
            String titleImg = H5Utils.getString(param, (String) "image");
            if (!TextUtils.isEmpty(titleImg)) {
                this.h = true;
                final String contentDesc = H5Utils.getString(param, (String) "contentDesc");
                if (!titleImg.startsWith("http")) {
                    H5Log.d("H5NavigationBar", "setTitle image type base64");
                    Bitmap bitmap = H5ImageUtil.base64ToBitmap(titleImg);
                    if (bitmap != null) {
                        this.i.setImgTitle(bitmap, contentDesc);
                        return;
                    }
                }
                if (this.a != null && this.a.getSession() != null && this.a.getSession().getWebProvider() != null) {
                    String titleImg2 = H5UrlHelper.decode(titleImg);
                    String finalImageUrl = a(titleImg2);
                    String onlineHost = H5Utils.getString(this.a.getParams(), (String) H5Param.ONLINE_HOST);
                    H5Log.d("H5NavigationBar", "setTitle image type originUrl " + titleImg2 + ", finalImageUrl " + finalImageUrl + ", onlineHost " + onlineHost);
                    if (TextUtils.isEmpty(finalImageUrl)) {
                        return;
                    }
                    if (TextUtils.isEmpty(onlineHost) || !finalImageUrl.startsWith(onlineHost)) {
                        Nebula.loadImageKeepSize(finalImageUrl, new H5ImageListener() {
                            public void onImage(final Bitmap bitmap) {
                                if (bitmap != null) {
                                    H5Log.d("H5NavigationBar", "setTitle image type online");
                                    H5Utils.runOnMain(new Runnable() {
                                        public void run() {
                                            H5NavigationBar.this.i.setImgTitle(bitmap, contentDesc);
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        this.a.getSession().getWebProvider().getContent(finalImageUrl, (ResponseListen) new ResponseListen() {
                            public void onGetResponse(WebResourceResponse webResourceResponse) {
                                if (webResourceResponse != null && webResourceResponse.getData() != null) {
                                    H5Log.d("H5NavigationBar", "setTitle image type offline");
                                    final Bitmap bitmap = BitmapFactory.decodeStream(webResourceResponse.getData());
                                    H5Utils.runOnMain(new Runnable() {
                                        public void run() {
                                            if (H5NavigationBar.this.i != null && bitmap != null) {
                                                H5NavigationBar.this.i.setImgTitle(bitmap, contentDesc);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            } else {
                H5Log.d("H5NavigationBar", "setTitle text type");
                String title = H5Utils.getString(param, (String) "title");
                if (!TextUtils.isEmpty(title) && !this.e) {
                    this.f = title;
                }
                String subtitle = null;
                if (param.containsKey("subtitle")) {
                    subtitle = H5Utils.getString(param, (String) "subtitle", (String) null);
                }
                this.i.setTitle(title);
                this.i.setSubTitle(subtitle);
                this.a.setTitle(title);
                Intent intent = new Intent();
                intent.setAction("com.alipay.mobile.h5container.titleRefreshed");
                intent.putExtra("title", title);
                intent.putExtra("url", this.a != null ? this.a.getUrl() : "");
                H5Log.d("H5NavigationBar", "send page finished broadcast.");
                LocalBroadcastManager.getInstance(H5Environment.getContext()).sendBroadcast(intent);
            }
        }
    }

    private String a(String originUrl) {
        String result = originUrl;
        if (this.a == null || TextUtils.isEmpty(originUrl) || originUrl.startsWith("http")) {
            return result;
        }
        String entryUrl = H5Utils.getString(this.a.getParams(), (String) "url");
        if (!TextUtils.isEmpty(entryUrl)) {
            return H5Utils.getAbsoluteUrlV2(entryUrl, originUrl, null);
        }
        return result;
    }

    public void setTitleAlpha() {
        this.c.setAlpha((int) (this.H * 255.0f));
        this.d.setAlpha(this.H);
        if (this.z != null) {
            if (this.H > 0.15f) {
                this.z.setAlpha(this.H);
            } else {
                this.z.setAlpha(1.0f);
            }
        }
        if (this.I && this.i != null) {
            TextView mainTitle = this.i.getMainTitleView();
            if (mainTitle != null) {
                mainTitle.setAlpha(this.H);
            }
            TextView subTitle = this.i.getSubTitleView();
            if (subTitle != null) {
                subTitle.setAlpha(this.H);
            }
        }
    }

    public void setContentBgViewColor(int color) {
        this.c.setColor(color);
        if (this.z != null) {
            H5SearchView h5SearchViewProvider = (H5SearchView) Nebula.getProviderManager().getProvider(H5SearchView.class.getName());
            if (h5SearchViewProvider != null) {
                h5SearchViewProvider.setSearchBarColor(color);
            }
        }
    }

    public void setHdivider(View view) {
        this.d = view;
    }

    private void c() {
        if (this.r != null && this.r.length > 0) {
            for (int i2 = 0; i2 < this.r.length; i2++) {
                String type = this.r[i2];
                if (!TextUtils.isEmpty(type) && !TextUtils.equals(type, "stupid") && v.get(type) != null) {
                    a(a(v.get(type).intValue()), i2);
                }
            }
        }
    }

    private void d() {
        if (this.r != null && this.r.length > 0) {
            for (int i2 = 0; i2 < this.r.length; i2++) {
                String type = this.r[i2];
                if (!TextUtils.isEmpty(type) && !TextUtils.equals(type, "stupid") && u.get(type) != null) {
                    a(a(u.get(type).intValue()), i2);
                }
            }
        }
    }

    private boolean a(H5Page h5Page) {
        if (h5Page == null) {
            return false;
        }
        if ("YES".equalsIgnoreCase(H5Utils.getString(h5Page.getParams(), (String) "usePresetPopmenu")) || H5Utils.canTransferH5ToTiny(this.w)) {
            return true;
        }
        return false;
    }

    private void a(Context context) {
        View optionMenuContainer = this.i.getOptionMenuContainer();
        this.m = (IH5TinyPopMenu) H5Utils.getH5ProviderManager().getProviderUseCache(IH5TinyPopMenu.class.getName(), false);
        if (this.m != null) {
            this.i.setIH5TinyPopMenu(this.m);
            this.m.init(this.a, "", context, (ViewGroup) this.i.getOptionMenuContainer());
            if (optionMenuContainer != null) {
                H5Log.d("H5NavigationBar", "show option menu container.");
                optionMenuContainer.setVisibility(0);
                return;
            }
        }
        if (optionMenuContainer != null) {
            optionMenuContainer.setVisibility(8);
        }
    }

    private boolean e() {
        boolean isPrerender = false;
        boolean isTinyApp = false;
        if (this.a != null) {
            isPrerender = H5Utils.getBoolean(this.a.getParams(), (String) H5Param.LONG_ISPRERENDER, false);
            isTinyApp = H5Utils.getBoolean(this.a.getParams(), (String) "isTinyApp", false);
        }
        if (!isPrerender || !isTinyApp) {
            return false;
        }
        return true;
    }

    private void a(H5BridgeContext bridgeContext) {
        JSONObject params = new JSONObject();
        params.put((String) "color", (Object) Long.valueOf(((long) this.c.getColor()) & 4294967295L));
        bridgeContext.sendBridgeResult(params);
    }
}
