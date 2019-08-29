package com.autonavi.minimap.life.order.hotel.page;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.login.LoginService;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.AbstractBaseWebView.d;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.amap.bundle.webview.widget.ExtendedWebView.a;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.bundle.account.api.IThirdAuth.IBaichuanSDKWebViewApi;
import com.autonavi.common.PageBundle;
import com.autonavi.common.js.action.LifeEntity;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.model.OrderRequest;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.uc.webview.export.WebSettings;
import com.uc.webview.export.WebView;

public class OrderHotelDetailPage extends AbstractBasePage<dqf> implements d, a, LocationNone, OnWebViewEventListener {
    public JsAdapter a;
    public ExtendedWebView b;
    anq c = new anq() {
        public final void loginOrBindCancel() {
        }

        public final void onComplete(boolean z) {
            OrderHotelDetailPage.this.b.reload();
        }
    };
    private ImageButton d;
    private TextView e;
    private boolean f = false;

    public void onWebViewPageCanceled(WebView webView) {
    }

    public void onWebViewPageFinished(WebView webView) {
    }

    public void onWebViewPageRefresh(WebView webView) {
    }

    public void onWebViewPageStart(WebView webView) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.order_base_detail_layout);
        View contentView = getContentView();
        this.b = (ExtendedWebView) contentView.findViewById(R.id.movie_detail_webview);
        if (VERSION.SDK_INT <= 18) {
            this.b.getWebView().getSettings().setSavePassword(false);
        }
        this.d = (ImageButton) contentView.findViewById(R.id.title_btn_left);
        this.e = (TextView) contentView.findViewById(R.id.title_text_name);
        View findViewById = contentView.findViewById(R.id.title_btn_right);
        this.e.setText(R.string.life_order_hotel);
        this.b.setOnWebViewEventListener(this);
        this.b.setOnWebViewOverloadListener(this);
        findViewById.setVisibility(4);
        this.d.setOnClickListener((OnClickListener) this.mPresenter);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            OrderRequest orderRequest = (OrderRequest) arguments.getObject(OrderRequest.INTENT_KEY);
            this.f = arguments.getBoolean("IsNew");
            if (orderRequest != null) {
                this.a = new JsAdapter(getPageContext(), (a) this.b);
                if (this.a != null) {
                    LifeEntity lifeEntity = new LifeEntity();
                    lifeEntity.oid = orderRequest.oid;
                    lifeEntity.type = orderRequest.type;
                    lifeEntity.srcType = orderRequest.src_type;
                    bkq.a(lifeEntity);
                }
                this.b.initializeWebView((Object) this.a, (Handler) null, true, false);
                this.b.setVisibility(0);
                this.b.clearView();
                WebView webView = this.b.getmCurWebView();
                if (webView != null) {
                    WebSettings settings = webView.getSettings();
                    if (settings != null) {
                        settings.setCacheMode(2);
                    }
                }
                String str = this.f ? "hotelOrderDetail.html" : "exHotelOrderDetail.html";
                if (ConfigerHelper.getInstance().isLoadPoiPageFromInternet()) {
                    this.b.loadUrl("http://tpl.testing.amap.com/and/".concat(String.valueOf(str)));
                    return;
                }
                bgx bgx = (bgx) a.a.a(bgx.class);
                if (bgx != null) {
                    this.b.loadUrl(bgx.getUrl(str));
                }
            }
        }
    }

    public void onReceivedTitle(WebView webView, String str) {
        if (this.e != null && !TextUtils.isEmpty(str)) {
            this.e.setText(str);
        }
    }

    public final void a() {
        if (this.b.canGoBack()) {
            this.b.stopLoading();
            this.b.goBack();
            return;
        }
        finish();
    }

    public final boolean a(String str) {
        LoginService loginService = (LoginService) MemberSDK.getService(LoginService.class);
        if (loginService == null) {
            return false;
        }
        try {
            return loginService.isLoginUrl(str);
        } catch (NullPointerException unused) {
            return false;
        }
    }

    public final boolean b(String str) {
        return !str.contains("https://h5.m.taobao.com/trip/hotel-react/order-detail/index.html");
    }

    public final void c() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (!iAccountService.a()) {
                iAccountService.a(AccountType.Taobao, this.c);
            } else if (!iAccountService.a(AccountType.Taobao)) {
                iAccountService.a(getPageContext(), AccountType.Taobao, this.c);
            } else {
                IBaichuanSDKWebViewApi b2 = iAccountService.c().b();
                if (b2 != null && !b2.a()) {
                    iAccountService.a(getPageContext(), AccountType.Taobao, this.c);
                }
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dqf(this);
    }
}
