package com.alipay.mobile.tinyappcustom.biz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.a.a.a.C0098a;
import com.alipay.mobile.a.a.a.b;
import com.alipay.mobile.a.a.a.c;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcustom.api.MiniProgramAuthCallback;
import com.alipay.mobile.tinyappcustom.api.MiniProgramAuthService;
import com.autonavi.map.core.MapCustomizeManager;

public class AlipayMiniProgramAuthActivity extends Activity implements OnClickListener {
    private TextView a;
    private TextView b;
    private TextView c;
    private ImageView d;
    private String e;
    private String f;
    private String g;
    private String h;
    private int i;
    /* access modifiers changed from: private */
    public MiniProgramAuthCallback j;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(b.mini_program_auth_activity);
        b();
        a();
    }

    private void a() {
        String string;
        this.a = (TextView) findViewById(C0098a.close);
        this.a.setOnClickListener(this);
        this.d = (ImageView) findViewById(C0098a.appIcon);
        if (this.i != 0) {
            this.d.setImageResource(this.i);
        }
        this.b = (TextView) findViewById(C0098a.description_1);
        this.b.setText(String.format(getResources().getString(c.description_1), new Object[]{this.e}));
        this.c = (TextView) findViewById(C0098a.action_button);
        this.c.setOnClickListener(this);
        TextView textView = this.c;
        if (d()) {
            string = getResources().getString(c.confirm);
        } else {
            string = getResources().getString(c.go_to_alipay_auth);
        }
        textView.setText(string);
        a(findViewById(C0098a.parent));
    }

    private void b() {
        Intent intent = getIntent();
        if (intent != null) {
            this.e = intent.getStringExtra("appName");
            this.f = intent.getStringExtra("userId");
            this.g = intent.getStringExtra("accessToken");
            this.h = intent.getStringExtra("appId");
            this.i = intent.getIntExtra("appIconResId", 0);
            this.j = MiniProgramAuthService.get(getApplicationContext()).getMiniProgramAuthCallback();
            H5Log.d("AlipayMiniProgramAuthActivity", "getIntentParams...appName=" + this.e + ",mUserId=" + this.f + ",appId=" + this.h + ",callback=" + this.j);
        }
    }

    public void onClick(View v) {
        if (v.getId() == C0098a.close) {
            onBackPressed();
        } else if (v.getId() == C0098a.action_button) {
            c();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void c() {
        new Thread(new Runnable() {
            public final void run() {
                try {
                    if (!AlipayMiniProgramAuthActivity.this.d() && AlipayMiniProgramAuthActivity.this.j != null) {
                        AlipayMiniProgramAuthActivity.this.j.startAlipayAuth();
                    }
                } catch (Throwable e) {
                    H5Log.e((String) "AlipayMiniProgramAuthActivity", "openMiniProgramOrDoAuth...e=" + e);
                }
            }
        }).start();
        finish();
    }

    /* access modifiers changed from: private */
    public boolean d() {
        return !TextUtils.isEmpty(this.f) && !TextUtils.isEmpty(this.g);
    }

    private void a(View view) {
        try {
            if (VERSION.SDK_INT >= 19) {
                getWindow().addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
                view.setPadding(0, a(getBaseContext()), 0, 0);
            }
        } catch (Throwable e2) {
            H5Log.e((String) "AlipayMiniProgramAuthActivity", "setImmerseLayout...e=" + e2);
        }
    }

    private static int a(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
