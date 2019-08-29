package com.alipay.mobile.nebulacore.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5DisClaimerProvider;
import com.alipay.mobile.nebula.provider.H5WarningTipProvider;
import com.alipay.mobile.nebula.util.H5ThirdDisclaimerUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.taobao.accs.common.Constants;

public class H5TransWebContent extends H5SimplePlugin {
    public static final String TAG = "H5TransWebContent";
    /* access modifiers changed from: private */
    public H5PageImpl a;
    private View b;
    private RelativeLayout c;
    private LinearLayout d;
    private ImageView e;
    private H5DisClaimerProvider f;
    private int g;

    public H5TransWebContent(H5PageImpl h5Page) {
        this.a = h5Page;
        a();
    }

    private void a() {
        this.b = LayoutInflater.from(this.a.getContext().getContext()).inflate(R.layout.h5_trans_web_content, null);
        this.c = (RelativeLayout) this.b.findViewById(R.id.h5_trans_web_content);
        this.d = (LinearLayout) this.b.findViewById(R.id.h5_tf_nav_ly);
        this.e = (ImageView) this.b.findViewById(R.id.h5_tf_nav_back);
        LayoutParams rl = new LayoutParams(-1, -1);
        if (H5Utils.getBoolean(this.a.getParams(), (String) H5Param.LONG_TRANS_ANIMATE, false)) {
            H5TransProgressContent h5ProgressContainer = new H5TransProgressContent(this.a);
            this.a.getPluginManager().register((H5Plugin) h5ProgressContainer);
            this.c.addView(h5ProgressContainer.getContent(), rl);
        } else {
            this.c.addView(this.a.getWebView().getView(), rl);
        }
        this.e.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (H5TransWebContent.this.a != null) {
                    H5TransWebContent.this.a.sendEvent(CommonEvents.H5_PAGE_BACK, null);
                }
            }
        });
        if (this.a != null) {
            if (!H5Utils.getBoolean(this.a.getParams(), (String) H5Param.LONG_FULLSCREEN, false)) {
                this.d.setVisibility(8);
            }
            this.g = H5ThirdDisclaimerUtils.needShowDisclaimer(this.a.getParams(), "");
            this.f = (H5DisClaimerProvider) H5Utils.getProvider(H5DisClaimerProvider.class.getName());
            if (this.f != null && this.g != 0) {
                this.f.showDisclaimer(this.a, this.g);
            }
        }
    }

    public View getContent() {
        return this.b;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.CLOSE_WEBVIEW);
        filter.addAction(CommonEvents.H5_PAGE_PHYSICAL_BACK);
        filter.addAction("hideTransBack");
        filter.addAction("disClaimerClick");
        filter.addAction("showDisClaimer");
        filter.addAction(CommonEvents.H5_PAGE_ERROR);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (CommonEvents.H5_PAGE_ERROR.equals(event.getAction())) {
            if (!(this.f == null || this.g == 0)) {
                this.f.hideDisclaimer(this.a);
            }
            H5WarningTipProvider tipProvider = (H5WarningTipProvider) H5Utils.getProvider(H5WarningTipProvider.class.getName());
            if (tipProvider != null) {
                tipProvider.hideWarningTip(this.a);
            }
        }
        return super.interceptEvent(event, context);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        boolean z = true;
        String action = event.getAction();
        JSONObject param = event.getParam();
        if (CommonEvents.CLOSE_WEBVIEW.equals(action)) {
            this.a.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
        } else if (CommonEvents.H5_PAGE_PHYSICAL_BACK.equals(action) || CommonEvents.H5_TOOLBAR_BACK.equals(action)) {
            this.a.sendEvent(CommonEvents.H5_PAGE_BACK, null);
        } else if ("hideTransBack".equals(action)) {
            this.d.setVisibility(8);
            context.sendSuccess();
        } else if ("disClaimerClick".equals(action)) {
            if (!(this.f == null || this.g == 0 || !b())) {
                this.f.showDisclaimer(this.a, this.g);
            }
        } else if (!"showDisClaimer".equals(action) || this.f == null) {
            return false;
        } else {
            int mode = H5Utils.getInt(param, (String) Constants.KEY_MODE);
            if (!(mode == 1 || mode == 2)) {
                z = false;
            }
            if (z) {
                this.f.showDisclaimer(this.a, mode);
                return false;
            }
            this.f.hideDisclaimer(this.a);
            return false;
        }
        return true;
    }

    private boolean b() {
        if (this.a == null || this.a.getPageData() == null || this.a.getPageData().getWarningTipSet() == null) {
            return true;
        }
        if (this.a.getPageData().getWarningTipSet().contains("inputWarning") || this.a.getPageData().getWarningTipSet().contains("dataFlow")) {
            return false;
        }
        return true;
    }

    public void onRelease() {
        super.onRelease();
    }
}
