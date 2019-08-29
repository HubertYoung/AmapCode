package com.alipay.mobile.nebulacore.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5TransProgressContent extends H5SimplePlugin {
    public static final int DEFAULT_SHOW_CLOSE_DELAY = 2000;
    public static final String KEY_SHOW_CLOSE_DELAY = "h5_showCloseDelay";
    public static final String TAG = "H5TransProgressContent";
    Runnable a = new Runnable() {
        public void run() {
            H5TransProgressContent.this.f.setVisibility(0);
        }
    };
    Runnable b = new Runnable() {
        public void run() {
            Drawable drawable = H5TransProgressContent.this.h.getResources().getDrawable(R.drawable.h5_title_bar_progress);
            drawable.setBounds(0, 0, H5Utils.dip2px(H5TransProgressContent.this.h, 26), H5Utils.dip2px(H5TransProgressContent.this.h, 26));
            H5TransProgressContent.this.e.setIndeterminateDrawable(drawable);
            H5TransProgressContent.this.e.setVisibility(0);
        }
    };
    /* access modifiers changed from: private */
    public H5PageImpl c;
    private RelativeLayout d;
    /* access modifiers changed from: private */
    public ProgressBar e;
    /* access modifiers changed from: private */
    public TextView f;
    private Handler g;
    /* access modifiers changed from: private */
    public Context h;
    private boolean i;

    public H5TransProgressContent(H5PageImpl h5Page) {
        this.c = h5Page;
        a();
    }

    private void a() {
        this.h = this.c.getContext().getContext();
        this.d = (RelativeLayout) LayoutInflater.from(this.h).inflate(R.layout.h5_trans_progress_content, null);
        this.e = (ProgressBar) this.d.findViewById(R.id.h5_progress_pb);
        this.f = (TextView) this.d.findViewById(R.id.h5_close_tv);
        if (this.c.getParams().containsKey("backgroundColor")) {
            this.d.setBackgroundColor(H5Utils.getInt(this.c.getParams(), (String) "backgroundColor"));
            this.c.getWebView().getView().setBackgroundColor(0);
        }
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                H5TransProgressContent.this.c.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
            }
        });
        this.g = new Handler(Looper.getMainLooper());
        this.g.postDelayed(this.b, 300);
        this.g.postDelayed(this.a, (long) c());
        this.i = false;
    }

    public View getContent() {
        return this.d;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_FINISHED);
        filter.addAction(CommonEvents.H5_PAGE_ERROR);
        filter.addAction(CommonEvents.H5_PAGE_PROGRESS);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        JSONObject param = event.getParam();
        if (CommonEvents.H5_PAGE_FINISHED.equals(action) || CommonEvents.H5_PAGE_ERROR.equals(action)) {
            b();
        } else if (CommonEvents.H5_PAGE_PROGRESS.equals(action) && H5Utils.getInt(param, (String) "progress") == 100) {
            b();
        }
        return super.interceptEvent(event, context);
    }

    private void b() {
        if (this.i) {
            H5Log.d(TAG, "alreadyAddView return");
            return;
        }
        this.i = true;
        this.g.removeCallbacks(this.b);
        this.g.removeCallbacks(this.a);
        this.d.removeAllViews();
        this.d.addView(this.c.getWebView().getView(), new LayoutParams(-1, -1));
    }

    public void onRelease() {
        super.onRelease();
        this.g.removeCallbacks(this.a);
        this.g.removeCallbacks(this.b);
    }

    private static int c() {
        String delayStr = H5Environment.getConfig(KEY_SHOW_CLOSE_DELAY);
        try {
            if (TextUtils.isEmpty(delayStr)) {
                return 2000;
            }
            return Integer.parseInt(delayStr);
        } catch (Exception globalException) {
            H5Log.e(TAG, "exception detail.", globalException);
            return 2000;
        }
    }
}
