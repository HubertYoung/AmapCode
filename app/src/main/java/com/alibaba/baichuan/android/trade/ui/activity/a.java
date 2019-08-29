package com.alibaba.baichuan.android.trade.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.c.b.d;
import com.alibaba.baichuan.android.trade.callback.AlibcCallbackContext;
import com.alibaba.baichuan.android.trade.utils.c;
import com.alibaba.baichuan.android.trade.utils.i;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.io.Serializable;
import java.util.Map;

public class a extends Activity {
    public static int e = c.a("OPEN_H5_TRADE");
    private static final String h = "a";
    protected WebView a;
    protected d b;
    public TextView c;
    public boolean d;
    private View f;
    private View g;

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        CallbackContext.onActivityResult(i, i2, intent);
    }

    public void onBackPressed() {
        if (this.b.a()) {
            this.b.e();
        } else {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        if (!AlibcContext.isShowTitleBar) {
            requestWindowFeature(1);
        }
        String str2 = null;
        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(i.a(this, "com_taobao_nb_sdk_web_view_activity"), null);
        Serializable serializable = getIntent() != null ? getIntent().getSerializableExtra("ui_contextParams") : bundle != null ? bundle.getSerializable("ui_contextParams") : null;
        com.alibaba.baichuan.android.trade.b.a aVar = AlibcCallbackContext.showProcessContext;
        AlibcCallbackContext.showProcessContext = null;
        if (aVar == null) {
            finish();
            return;
        }
        this.a = new WebView(this);
        d dVar = new d(this, aVar, this.a, serializable instanceof Map ? (Map) serializable : null, false);
        this.b = dVar;
        linearLayout.addView(this.a, new LayoutParams(-1, -1));
        setContentView(linearLayout);
        this.c = (TextView) findViewById(i.a(this, "id", "com_taobao_nb_sdk_web_view_title_bar_title"));
        this.g = findViewById(i.a(this, "id", "com_taobao_nb_sdk_web_view_title_bar_close_button"));
        if (this.g != null) {
            this.g.setOnClickListener(new b(this));
        }
        View findViewById = findViewById(i.a(this, "id", "com_taobao_nb_sdk_web_view_title_bar_back_button"));
        if (findViewById != null) {
            findViewById.setOnClickListener(new c(this));
        }
        View findViewById2 = findViewById(i.a(this, "id", "com_taobao_tae_sdk_web_view_title_bar_more_button"));
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(new d(this));
        }
        this.f = findViewById(i.a(this, "id", "com_taobao_tae_sdk_web_view_title_bar"));
        if (bundle != null) {
            str2 = bundle.getString("title");
            str = bundle.getString("title");
        } else {
            str = null;
        }
        if (TextUtils.isEmpty(str2) && aVar != null) {
            str2 = aVar.d;
        }
        if (TextUtils.isEmpty(str2)) {
            this.d = true;
        } else {
            this.d = false;
            this.c.setText(str2);
        }
        if (TextUtils.isEmpty(str)) {
            str = getIntent().getStringExtra("url");
        }
        AlibcLogger.d(h, "AliTrade SDK WebView首次加载的url=".concat(String.valueOf(str)));
        this.a.loadUrl(str);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.a != null) {
            ViewGroup viewGroup = (ViewGroup) this.a.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.a);
            }
            this.a.removeAllViews();
            this.a.destroy();
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("url", this.a.getUrl());
        bundle.putString("title", this.c.getText().toString());
        bundle.putSerializable("ui_contextParams", (Serializable) this.b.b());
    }
}
