package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView.BufferType;
import com.ali.user.mobile.ui.widget.font.TextSizeGearGetter;
import com.ali.user.mobile.utils.DensityUtil;

public class APEditText extends EditText {
    private static TextSizeGearGetter d;
    private boolean a;
    private int b;
    private float c;
    public OnPasteListener listener;

    public interface OnPasteListener {
    }

    public void setPasteListener(OnPasteListener onPasteListener) {
        this.listener = onPasteListener;
    }

    public APEditText(Context context) {
        super(context);
        this.a = false;
        this.b = 0;
        this.c = getTextSize();
    }

    public APEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = false;
        this.b = 0;
        a();
    }

    public APEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = false;
        this.b = 0;
        a();
    }

    public void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        this.c = getTextSize();
    }

    private void a() {
        this.c = getTextSize();
    }

    public void setEmojiSize(int i) {
        this.b = i;
    }

    public void setSupportEmoji(boolean z) {
        this.a = z;
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        super.setText(charSequence, bufferType);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null || !this.a) {
            super.onTextChanged(charSequence, i, i2, i3);
        } else {
            super.onTextChanged(charSequence, i, i2, i3);
        }
    }

    public int getEmojiSize() {
        if (this.b <= 0) {
            return ((int) getTextSize()) + DensityUtil.a(getContext(), 2.0f);
        }
        return this.b;
    }

    public static TextSizeGearGetter getTextSizeGearGetter() {
        return d;
    }

    public static void setTextSizeGearGetter(TextSizeGearGetter textSizeGearGetter) {
        d = textSizeGearGetter;
    }

    public boolean onTextContextMenuItem(int i) {
        if (i != 16908322) {
            return super.onTextContextMenuItem(i);
        }
        super.onTextContextMenuItem(i);
        return true;
    }
}
