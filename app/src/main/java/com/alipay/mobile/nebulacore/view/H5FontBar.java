package com.alipay.mobile.nebulacore.view;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Scenario;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;

public class H5FontBar extends H5SimplePlugin implements OnClickListener {
    public static final String HIDE_FONT_BAR = "hideFontBar";
    public static final String SHOW_FONT_BAR = "showFontBar";
    public static final String TAG = "H5FontBar";
    private View a;
    private View b;
    private View c;
    private View d;
    private View e;
    private View f;
    private ImageView g;
    private ImageView h;
    private ImageView i;
    private ImageView j;
    private View k;
    private ViewGroup l;
    private H5Page m;
    private boolean n = false;

    public H5FontBar(H5Page page) {
        this.m = page;
    }

    private void a() {
        if (this.m != null) {
            Activity activity = (Activity) this.m.getContext().getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            this.l = (ViewGroup) activity.getWindow().getDecorView().findViewById(16908290);
            this.k = layoutInflater.inflate(R.layout.h5_font_bar, this.l, false);
            this.a = this.k.findViewById(R.id.h5_font_blank);
            this.a.setOnClickListener(this);
            this.k.findViewById(R.id.h5_font_bar).setOnClickListener(this);
            this.g = (ImageView) this.k.findViewById(R.id.h5_iv_font_size1);
            this.h = (ImageView) this.k.findViewById(R.id.h5_iv_font_size2);
            this.i = (ImageView) this.k.findViewById(R.id.h5_iv_font_size3);
            this.j = (ImageView) this.k.findViewById(R.id.h5_iv_font_size4);
            this.f = this.k.findViewById(R.id.h5_font_close);
            this.b = this.k.findViewById(R.id.h5_font_size1);
            this.c = this.k.findViewById(R.id.h5_font_size2);
            this.d = this.k.findViewById(R.id.h5_font_size3);
            this.e = this.k.findViewById(R.id.h5_font_size4);
            this.b.setOnClickListener(this);
            this.c.setOnClickListener(this);
            this.d.setOnClickListener(this);
            this.e.setOnClickListener(this);
            this.f.setOnClickListener(this);
            int fontSize = 100;
            H5Scenario scenario = this.m.getSession().getScenario();
            if (scenario != null) {
                String fontStr = scenario.getData().get(H5Param.FONT_SIZE);
                if (!TextUtils.isEmpty(fontStr)) {
                    fontSize = Integer.parseInt(fontStr);
                }
                b(fontSize);
            }
        }
    }

    public void onClick(View view) {
        int targetFontSize = -1;
        if (view.equals(this.a) || view.equals(this.f)) {
            c();
            return;
        }
        if (view.equals(this.b)) {
            targetFontSize = 75;
        } else if (view.equals(this.c)) {
            targetFontSize = 100;
        } else if (view.equals(this.d)) {
            targetFontSize = 150;
        } else if (view.equals(this.e)) {
            targetFontSize = 200;
        }
        if (targetFontSize != -1) {
            a(targetFontSize);
        }
    }

    private void a(int size) {
        if (this.m != null) {
            JSONObject param = new JSONObject();
            param.put((String) "size", (Object) Integer.valueOf(size));
            this.m.sendEvent(CommonEvents.H5_PAGE_FONT_SIZE, param);
            b(size);
        }
    }

    private void b(int size) {
        this.g.setImageResource(R.drawable.h5_font_size1_enable);
        this.h.setImageResource(R.drawable.h5_font_size2_enable);
        this.i.setImageResource(R.drawable.h5_font_size3_enable);
        this.j.setImageResource(R.drawable.h5_font_size4_enable);
        if (size == 75) {
            this.g.setImageResource(R.drawable.h5_font_size1_disable);
        } else if (size == 100) {
            this.h.setImageResource(R.drawable.h5_font_size2_disable);
        } else if (size == 150) {
            this.i.setImageResource(R.drawable.h5_font_size3_disable);
        } else if (size == 200) {
            this.j.setImageResource(R.drawable.h5_font_size4_disable);
        }
    }

    private void b() {
        d();
        try {
            if (this.k.getParent() != null) {
                ((ViewGroup) this.k.getParent()).removeView(this.k);
            }
            this.l.addView(this.k);
        } catch (Throwable t) {
            H5Log.e(TAG, "fatal view state error ", t);
        }
        this.l.bringChildToFront(this.k);
        this.n = true;
    }

    private void c() {
        d();
        this.l.removeView(this.k);
        this.n = false;
    }

    public void onRelease() {
        this.m = null;
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (!CommonEvents.H5_PAGE_BACK.equals(event.getAction()) || !this.n) {
            return false;
        }
        c();
        return true;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (SHOW_FONT_BAR.equals(action)) {
            b();
        } else if (!HIDE_FONT_BAR.equals(action)) {
            return false;
        } else {
            c();
        }
        return true;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(SHOW_FONT_BAR);
        filter.addAction(HIDE_FONT_BAR);
        filter.addAction(CommonEvents.H5_PAGE_BACK);
    }

    private void d() {
        if (this.l == null) {
            a();
        }
    }
}
