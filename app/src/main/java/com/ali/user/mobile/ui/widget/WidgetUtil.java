package com.ali.user.mobile.ui.widget;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.security.ui.R;

@SuppressLint({"NewApi"})
public class WidgetUtil {
    public static void a(final View view, EditText editText) {
        final OnFocusChangeListener onFocusChangeListener = editText.getOnFocusChangeListener();
        b(view, false);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean z) {
                WidgetUtil.b(view, z);
                if (onFocusChangeListener != null) {
                    onFocusChangeListener.onFocusChange(view, z);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void b(View view, boolean z) {
        if (z) {
            Drawable f = UIConfigManager.f();
            if (f == null) {
                view.setBackgroundResource(R.drawable.f);
            } else {
                a(view, f);
            }
        } else {
            view.setBackgroundResource(R.drawable.g);
        }
    }

    public static int a(Resources resources, EditText editText, int i, int i2) {
        LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
        layoutParams.addRule(9);
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.e);
        int dimensionPixelOffset2 = resources.getDimensionPixelOffset(R.dimen.m);
        int dimensionPixelOffset3 = resources.getDimensionPixelOffset(R.dimen.e);
        int max = Math.max(dimensionPixelOffset2, i2 + dimensionPixelOffset);
        layoutParams.setMargins(max, i, dimensionPixelOffset3, 0);
        editText.setLayoutParams(layoutParams);
        return max;
    }

    public static void a(View view, Drawable drawable) {
        if (view != null) {
            if (VERSION.SDK_INT >= 16) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }
    }

    public static void a(View view) {
        if (view != null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
            translateAnimation.setInterpolator(new CycleInterpolator(3.0f));
            translateAnimation.setDuration(400);
            view.startAnimation(translateAnimation);
        }
    }
}
