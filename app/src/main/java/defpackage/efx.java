package defpackage;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: efx reason: default package */
/* compiled from: RunningTextTypeUtil */
public final class efx {
    private static volatile Typeface a;

    static {
        try {
            a = erp.a(AMapPageUtil.getAppContext()).a((String) "regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
            a = null;
        }
    }

    public static Typeface a() {
        if (a == null) {
            try {
                a = erp.a((Context) AMapAppGlobal.getApplication()).a((String) "regular.ttf");
            } catch (Exception e) {
                e.printStackTrace();
                a = null;
            }
        }
        return a;
    }

    public static boolean a(final String str, final TextView textView) {
        if (textView == null) {
            return false;
        }
        ViewTreeObserver viewTreeObserver = textView.getViewTreeObserver();
        if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
            a(str, textView, null, 0.0f);
        } else {
            viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
                final /* synthetic */ Typeface c = null;
                final /* synthetic */ float d = 0.0f;

                public final boolean onPreDraw() {
                    ViewTreeObserver viewTreeObserver = textView.getViewTreeObserver();
                    if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                        viewTreeObserver.removeOnPreDrawListener(this);
                    }
                    efx.a(str, textView, this.c, this.d);
                    return true;
                }
            });
        }
        return true;
    }

    public static float a(String str, TextView textView, Typeface typeface, float f) {
        int i;
        TextPaint textPaint = new TextPaint();
        if (typeface != null) {
            textPaint.setTypeface(typeface);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText());
        String sb2 = sb.toString();
        float textSize = textView.getTextSize();
        if (TextUtils.isEmpty(str)) {
            if (TextUtils.isEmpty(sb2)) {
                return textSize;
            }
            str = sb2;
        }
        textPaint.setTextSize(textSize);
        if (f != 0.0f) {
            i = (int) f;
        } else {
            i = textView.getWidth();
        }
        if (i == 0) {
            return textSize;
        }
        for (float measureText = textPaint.measureText(str); measureText > ((float) i); measureText = textPaint.measureText(str)) {
            textSize -= 1.0f;
            textPaint.setTextSize(textSize);
        }
        textView.setTextSize(0, textSize);
        return textSize;
    }

    public static boolean a(TextView textView) {
        Typeface a2 = a();
        if (a2 != null) {
            textView.setTypeface(a2);
        }
        return true;
    }
}
