package com.ali.user.mobile.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ali.user.mobile.security.ui.R;

public class APNormalPopDialog extends Dialog {
    private final Context a;
    private final LayoutInflater b;
    private View c;
    private ImageView d;
    private Button e;
    private Button f;
    private TextView g;
    private TextView h;
    private TextView i;
    private String j;
    private String k;
    private String l;
    private OnClickPositiveListener m;
    private OnClickNegativeListener n;
    private Drawable o;
    private Drawable p;
    private ColorStateList q;
    private int r = Integer.MAX_VALUE;

    public interface OnClickNegativeListener {
    }

    public interface OnClickPositiveListener {
    }

    public APNormalPopDialog(Context context, String str, String str2, String str3) {
        super(context, R.style.h);
        this.a = context;
        this.j = str;
        this.k = str2;
        this.l = str3;
        this.b = LayoutInflater.from(context);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        View inflate = this.b.inflate(R.layout.u, null);
        this.c = inflate;
        this.f = (Button) inflate.findViewById(R.id.W);
        this.e = (Button) inflate.findViewById(R.id.H);
        this.g = (TextView) inflate.findViewById(R.id.cc);
        this.h = (TextView) inflate.findViewById(R.id.bL);
        this.i = (TextView) inflate.findViewById(R.id.aC);
        this.d = (ImageView) inflate.findViewById(R.id.ad);
        if (TextUtils.isEmpty(this.j)) {
            this.g.setVisibility(8);
        } else {
            this.g.setText(this.j);
            this.g.setVisibility(0);
        }
        if (TextUtils.isEmpty(this.k)) {
            this.h.setVisibility(8);
            this.d.setVisibility(0);
        } else {
            this.h.setText(this.k);
            this.h.setVisibility(0);
        }
        if (TextUtils.isEmpty(this.l)) {
            this.i.setVisibility(8);
        } else {
            this.i.setText(this.l);
            this.i.setVisibility(0);
        }
        if (!(this.f == null || this.o == null)) {
            a(this.f, this.o);
        }
        if (!(this.e == null || this.p == null)) {
            a(this.e, this.p);
        }
        if (!(this.f == null || this.q == null)) {
            this.f.setTextColor(this.q);
        }
        if (!(this.g == null || this.r == Integer.MAX_VALUE)) {
            this.g.setTextColor(this.r);
        }
        setCanceledOnTouchOutside(false);
        this.e.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                APPopClickTimeRecoder.a();
                APNormalPopDialog.this.cancel();
            }
        });
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                APPopClickTimeRecoder.a();
                APNormalPopDialog.this.dismiss();
            }
        });
    }

    public void show() {
        super.show();
        setContentView(this.c);
        LayoutParams attributes = getWindow().getAttributes();
        DisplayMetrics displayMetrics = this.a.getResources().getDisplayMetrics();
        attributes.width = displayMetrics.widthPixels > displayMetrics.heightPixels ? displayMetrics.heightPixels : displayMetrics.widthPixels;
        getWindow().setBackgroundDrawable(this.a.getResources().getDrawable(R.color.R));
        getWindow().setAttributes(attributes);
    }

    public final Button a() {
        return this.f;
    }

    public final void a(OnClickPositiveListener onClickPositiveListener) {
        this.m = onClickPositiveListener;
    }

    public final void a(OnClickNegativeListener onClickNegativeListener) {
        this.n = onClickNegativeListener;
    }

    private static void a(View view, Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
