package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.ali.user.mobile.ui.widget.emotion.OnParseEmotionListener;
import com.ali.user.mobile.ui.widget.font.TextSizeGearGetter;
import com.ali.user.mobile.utils.DensityUtil;
import com.uc.webview.export.extension.UCCore;

public class APTextView extends TextView {
    private static OnParseEmotionListener i;
    private static TextSizeGearGetter j;
    private boolean a;
    private boolean b;
    private int c;
    private boolean d;
    private float e;
    private int f;
    private boolean g;
    private int h;
    private OnTextViewTextChangeListener k;

    interface OnTextViewTextChangeListener {
        void a(String str);
    }

    public OnTextViewTextChangeListener getOnTextChangeListener() {
        return this.k;
    }

    public void setOnTextChangeListener(OnTextViewTextChangeListener onTextViewTextChangeListener) {
        this.k = onTextViewTextChangeListener;
    }

    public APTextView(Context context) {
        super(context);
        this.c = 0;
        this.d = false;
        this.h = 9999;
        this.e = getTextSize();
        b();
    }

    public APTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = 0;
        this.d = false;
        this.h = 9999;
        a();
    }

    public APTextView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.c = 0;
        this.d = false;
        this.h = 9999;
        a();
    }

    public void setTextSize(int i2, float f2) {
        super.setTextSize(i2, f2);
        this.e = getTextSize();
        b();
    }

    private void a() {
        this.e = getTextSize();
        b();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(APViewEventHelper.a(onClickListener));
    }

    public void setSupportEmoji(boolean z) {
        this.a = z;
    }

    public void setSupportEmotion(boolean z) {
        this.b = z;
    }

    public void setEmojiSize(int i2) {
        this.c = i2;
    }

    private void b() {
        if (this.d && j != null) {
            float a2 = DensityUtil.a(DensityUtil.b(getContext(), this.e), j.a());
            if (!DensityUtil.a(DensityUtil.b(getContext(), getTextSize()), a2)) {
                super.setTextSize(2, a2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (this.b) {
            int i4 = 1;
            if (c() == 1) {
                if (j != null) {
                    i4 = j.a();
                }
                super.onMeasure(i2, MeasureSpec.makeMeasureSpec((int) (getTextSize() + DensityUtil.a((float) DensityUtil.a(getContext(), 6.0f), i4)), UCCore.VERIFY_POLICY_QUICK));
                return;
            }
        }
        super.onMeasure(i2, i3);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        b();
        if (((this.a && this.f > 0) || (this.b && this.g)) && getEllipsize() != null && c() == 1) {
            int leftPaddingOffset = ((i4 - i2) - getLeftPaddingOffset()) - getRightPaddingOffset();
            if (this.h != 9999) {
                leftPaddingOffset = Math.max(leftPaddingOffset, this.h);
            }
            CharSequence text = getText();
            CharSequence ellipsize = TextUtils.ellipsize(text, getPaint(), (float) leftPaddingOffset, getEllipsize());
            if (!TextUtils.equals(ellipsize, text)) {
                if (text.length() > ellipsize.length()) {
                    if (this.h == 9999) {
                        this.h = leftPaddingOffset;
                    } else {
                        this.h = Math.max(leftPaddingOffset, this.h);
                    }
                }
                setText(ellipsize);
            }
        }
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        if ((this.a || this.b) && charSequence != null) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
            if (this.b && i != null) {
                OnParseEmotionListener onParseEmotionListener = i;
                getContext();
                getEmojiSize();
                this.g = onParseEmotionListener.a();
            }
            if ((this.f <= 0 && !this.g) || getEllipsize() == null) {
                super.setText(spannableStringBuilder, bufferType);
            } else if (c() == 1) {
                super.setText(TextUtils.ellipsize(spannableStringBuilder, getPaint(), (float) this.h, getEllipsize()), bufferType);
            } else {
                super.setText(spannableStringBuilder, bufferType);
            }
            if (!(this.k == null || charSequence == null)) {
                this.k.a(spannableStringBuilder.toString());
            }
            return;
        }
        super.setText(charSequence, bufferType);
        if (!(this.k == null || charSequence == null)) {
            this.k.a(charSequence.toString());
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[ExcHandler: Exception (unused java.lang.Exception), SYNTHETIC, Splitter:B:9:0x0010] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int c() {
        /*
            r3 = this;
            r0 = 0
            java.lang.Class<android.widget.TextView> r1 = android.widget.TextView.class
            java.lang.String r2 = "mSingleLine"
            java.lang.reflect.Field r1 = r1.getDeclaredField(r2)     // Catch:{ NoSuchFieldException -> 0x000a }
            r0 = r1
        L_0x000a:
            if (r0 == 0) goto L_0x0019
            r1 = 1
            r0.setAccessible(r1)     // Catch:{  }
            boolean r0 = r0.getBoolean(r3)     // Catch:{ Exception -> 0x0019, Exception -> 0x0019 }
            if (r0 == 0) goto L_0x0017
            return r1
        L_0x0017:
            r0 = 0
            return r0
        L_0x0019:
            r0 = -1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.ui.widget.APTextView.c():int");
    }

    public int getEmojiSize() {
        if (this.c <= 0) {
            return ((int) getTextSize()) + DensityUtil.a(getContext(), 2.0f);
        }
        return this.c;
    }

    public boolean isDynamicTextSize() {
        return this.d;
    }

    public void setDynamicTextSize(boolean z) {
        this.d = z;
    }

    public static OnParseEmotionListener getParseEmotionListener() {
        return i;
    }

    public static void setParseEmotionListener(OnParseEmotionListener onParseEmotionListener) {
        i = onParseEmotionListener;
    }

    public static TextSizeGearGetter getTextSizeGearGetter() {
        return j;
    }

    public static void setTextSizeGearGetter(TextSizeGearGetter textSizeGearGetter) {
        j = textSizeGearGetter;
    }
}
