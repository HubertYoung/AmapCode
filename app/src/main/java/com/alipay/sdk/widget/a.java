package com.alipay.sdk.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class a {
    public static final String a = "正在加载";
    public static final String b = "去支付宝付款";
    public static final String c = "去支付宝授权";
    public static String d = "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";
    public boolean e = false;
    /* access modifiers changed from: private */
    public C0006a f;
    /* access modifiers changed from: private */
    public Activity g;
    /* access modifiers changed from: private */
    public String h;

    /* renamed from: com.alipay.sdk.widget.a$a reason: collision with other inner class name */
    public class C0006a extends AlertDialog {
        protected C0006a(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            Context context = getContext();
            LinearLayout linearLayout = new LinearLayout(context);
            LayoutParams layoutParams = new LayoutParams(-2, a(context, 50.0f));
            layoutParams.gravity = 17;
            linearLayout.setOrientation(0);
            linearLayout.setLayoutParams(layoutParams);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(-450944201);
            gradientDrawable.setCornerRadius((float) a(context, 5.0f));
            linearLayout.setBackgroundDrawable(gradientDrawable);
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(a(context, 20.0f), a(context, 20.0f));
            layoutParams2.gravity = 16;
            layoutParams2.setMargins(a((Context) a.this.g, 17.0f), a((Context) a.this.g, 10.0f), a((Context) a.this.g, 8.0f), a((Context) a.this.g, 10.0f));
            imageView.setLayoutParams(layoutParams2);
            imageView.setScaleType(ScaleType.FIT_CENTER);
            imageView.setImageDrawable(a(context, a.d));
            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 359.0f, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setRepeatCount(-1);
            rotateAnimation.setDuration(900);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            imageView.startAnimation(rotateAnimation);
            TextView textView = new TextView(context);
            textView.setText(TextUtils.isEmpty(a.this.h) ? a.a : a.this.h);
            textView.setTextSize(16.0f);
            textView.setTextColor(-1);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams3.gravity = 16;
            layoutParams3.setMargins(0, 0, a(context, 17.0f), 0);
            textView.setLayoutParams(layoutParams3);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            setContentView(linearLayout);
            getWindow().setBackgroundDrawable(new ColorDrawable(17170445));
        }

        private View a(Context context) {
            Context context2 = context;
            LinearLayout linearLayout = new LinearLayout(context2);
            LayoutParams layoutParams = new LayoutParams(-2, a(context2, 50.0f));
            layoutParams.gravity = 17;
            linearLayout.setOrientation(0);
            linearLayout.setLayoutParams(layoutParams);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(-450944201);
            gradientDrawable.setCornerRadius((float) a(context2, 5.0f));
            linearLayout.setBackgroundDrawable(gradientDrawable);
            ImageView imageView = new ImageView(context2);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(a(context2, 20.0f), a(context2, 20.0f));
            layoutParams2.gravity = 16;
            layoutParams2.setMargins(a((Context) a.this.g, 17.0f), a((Context) a.this.g, 10.0f), a((Context) a.this.g, 8.0f), a((Context) a.this.g, 10.0f));
            imageView.setLayoutParams(layoutParams2);
            imageView.setScaleType(ScaleType.FIT_CENTER);
            imageView.setImageDrawable(a(context2, a.d));
            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 359.0f, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setRepeatCount(-1);
            rotateAnimation.setDuration(900);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            imageView.startAnimation(rotateAnimation);
            TextView textView = new TextView(context2);
            textView.setText(TextUtils.isEmpty(a.this.h) ? a.a : a.this.h);
            textView.setTextSize(16.0f);
            textView.setTextColor(-1);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams3.gravity = 16;
            layoutParams3.setMargins(0, 0, a(context2, 17.0f), 0);
            textView.setLayoutParams(layoutParams3);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            return linearLayout;
        }

        /* JADX WARNING: Removed duplicated region for block: B:13:0x0037 A[SYNTHETIC, Splitter:B:13:0x0037] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x003e A[SYNTHETIC, Splitter:B:21:0x003e] */
        /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static android.graphics.drawable.Drawable a(android.content.Context r3, java.lang.String r4) {
            /*
                r0 = 0
                java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Throwable -> 0x003b, all -> 0x0033 }
                byte[] r4 = com.alipay.sdk.encrypt.a.a(r4)     // Catch:{ Throwable -> 0x003b, all -> 0x0033 }
                r1.<init>(r4)     // Catch:{ Throwable -> 0x003b, all -> 0x0033 }
                android.graphics.BitmapFactory$Options r4 = new android.graphics.BitmapFactory$Options     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                r4.<init>()     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                r2 = 480(0x1e0, float:6.73E-43)
                r4.inDensity = r2     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                android.content.res.Resources r2 = r3.getResources()     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                android.util.DisplayMetrics r2 = r2.getDisplayMetrics()     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                int r2 = r2.densityDpi     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                r4.inTargetDensity = r2     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                android.graphics.Bitmap r4 = android.graphics.BitmapFactory.decodeStream(r1, r0, r4)     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                android.graphics.drawable.BitmapDrawable r2 = new android.graphics.drawable.BitmapDrawable     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                android.content.res.Resources r3 = r3.getResources()     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                r2.<init>(r3, r4)     // Catch:{ Throwable -> 0x003c, all -> 0x0031 }
                r1.close()     // Catch:{ Exception -> 0x002f }
            L_0x002f:
                r0 = r2
                goto L_0x0041
            L_0x0031:
                r3 = move-exception
                goto L_0x0035
            L_0x0033:
                r3 = move-exception
                r1 = r0
            L_0x0035:
                if (r1 == 0) goto L_0x003a
                r1.close()     // Catch:{ Exception -> 0x003a }
            L_0x003a:
                throw r3
            L_0x003b:
                r1 = r0
            L_0x003c:
                if (r1 == 0) goto L_0x0041
                r1.close()     // Catch:{ Exception -> 0x0041 }
            L_0x0041:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.widget.a.C0006a.a(android.content.Context, java.lang.String):android.graphics.drawable.Drawable");
        }

        private static int a(Context context, float f) {
            return (int) (f * (context == null ? Resources.getSystem() : context.getResources()).getDisplayMetrics().density);
        }
    }

    private a(Activity activity) {
        this.g = activity;
    }

    public a(Activity activity, String str) {
        this.g = activity;
        this.h = str;
    }

    private void c() {
        this.e = true;
    }

    private void a(String str) {
        this.h = str;
    }

    private String d() {
        return this.h;
    }

    public final void a() {
        if (this.g != null) {
            this.g.runOnUiThread(new b(this));
        }
    }

    public final void b() {
        if (this.g != null) {
            this.g.runOnUiThread(new c(this));
        }
    }

    private void e() {
        this.g = null;
        this.f = null;
    }
}
