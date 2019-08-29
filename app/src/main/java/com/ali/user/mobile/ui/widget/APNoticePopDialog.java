package com.ali.user.mobile.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.ali.user.mobile.security.ui.R;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class APNoticePopDialog extends Dialog {
    private Context a;
    private LayoutInflater b;
    private View c;
    private Button d;
    private Button e;
    private APTextView f;
    private APTextView g;
    private CharSequence h;
    private CharSequence i;
    /* access modifiers changed from: private */
    public OnClickPositiveListener j;
    /* access modifiers changed from: private */
    public OnClickNegativeListener k;
    private String l;
    private String m;
    private LinearLayout n;
    private RelativeLayout o;
    private boolean p;

    public interface OnClickNegativeListener {
        void a();
    }

    public interface OnClickPositiveListener {
        void onClick();
    }

    public APNoticePopDialog(Context context, String str, String str2, String str3, String str4) {
        this(context, str, str2, str3, str4, false);
    }

    private APNoticePopDialog(Context context, String str, String str2, String str3, String str4, boolean z) {
        super(context, R.style.h);
        this.p = false;
        this.a = context;
        this.h = str;
        this.i = str2;
        this.l = str3;
        this.m = str4;
        this.b = LayoutInflater.from(context);
        this.p = z;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = this.b.inflate(R.layout.E, null);
        this.e = (Button) this.c.findViewById(R.id.W);
        this.d = (Button) this.c.findViewById(R.id.H);
        this.f = (APTextView) this.c.findViewById(R.id.cc);
        this.g = (APTextView) this.c.findViewById(R.id.aC);
        this.n = (LinearLayout) this.c.findViewById(R.id.G);
        this.o = (RelativeLayout) this.c.findViewById(R.id.T);
        a(this.f, this.h);
        a(this.g, this.i);
        setCanceledOnTouchOutside(this.p);
        if (!TextUtils.isEmpty(this.m) || !TextUtils.isEmpty(this.l)) {
            this.n.setVisibility(0);
            this.d.setText(this.m);
            if (TextUtils.isEmpty(this.m)) {
                this.d.setVisibility(8);
            }
            this.d.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    APNoticePopDialog.this.cancel();
                    if (APNoticePopDialog.this.k != null) {
                        APNoticePopDialog.this.k.a();
                    }
                }
            });
            this.e.setText(this.l);
            if (TextUtils.isEmpty(this.l)) {
                this.e.setVisibility(8);
            }
            this.e.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    APNoticePopDialog.this.dismiss();
                    if (APNoticePopDialog.this.j != null) {
                        APNoticePopDialog.this.j.onClick();
                    }
                }
            });
            return;
        }
        this.n.setVisibility(8);
    }

    private static void a(APTextView aPTextView, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            aPTextView.setVisibility(8);
            return;
        }
        aPTextView.setVisibility(0);
        aPTextView.setText(charSequence);
    }

    public void show() {
        super.show();
        setContentView(this.c);
        LayoutParams attributes = getWindow().getAttributes();
        attributes.width = ((WindowManager) this.a.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth() - this.a.getResources().getDimensionPixelSize(R.dimen.r);
        getWindow().setAttributes(attributes);
    }

    public final void a(OnClickPositiveListener onClickPositiveListener) {
        this.j = onClickPositiveListener;
    }

    public final void a(OnClickNegativeListener onClickNegativeListener) {
        this.k = onClickNegativeListener;
    }
}
