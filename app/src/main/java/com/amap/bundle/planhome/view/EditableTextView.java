package com.amap.bundle.planhome.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView.BufferType;

@SuppressLint({"AppCompatCustomView"})
public class EditableTextView extends EditText {
    private boolean mEditable = true;
    private String mEllipsizedText;
    private FontMetricsInt mFontMetricsInt;
    private CharSequence mText;
    private TextPaint mTextPaint;

    public EditableTextView(Context context) {
        super(context);
        init();
    }

    public EditableTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public EditableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setSingleLine();
        this.mTextPaint = getPaint();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mFontMetricsInt = this.mTextPaint.getFontMetricsInt();
        ellipsize(getMeasuredWidth());
    }

    private void ellipsize(int i) {
        String str;
        if (TextUtils.isEmpty(this.mText)) {
            this.mEllipsizedText = "";
            return;
        }
        CharSequence ellipsize = TextUtils.ellipsize(this.mText, this.mTextPaint, (float) i, TruncateAt.END);
        if (ellipsize == null) {
            str = "";
        } else {
            str = ellipsize.toString();
        }
        this.mEllipsizedText = str;
    }

    public void setEditable(boolean z) {
        if (this.mEditable != z) {
            this.mEditable = z;
            if (!z) {
                scrollTo(0, 0);
            }
            setFocusable(z);
            setFocusableInTouchMode(z);
            setCursorVisible(z);
            if (!z) {
                requestLayout();
            } else {
                invalidate();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
    }

    public boolean isEditable() {
        return this.mEditable;
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        this.mText = charSequence;
        super.setText(charSequence, bufferType);
        if (!isEditable()) {
            this.mEllipsizedText = null;
            requestLayout();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mEditable) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            setSelectAllOnFocus(true);
        } else if (action == 1 || action == 3 || action == 4) {
            post(new Runnable() {
                public final void run() {
                    EditableTextView.this.setSelectAllOnFocus(false);
                }
            });
        }
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mEditable || this.mFontMetricsInt == null || TextUtils.isEmpty(this.mEllipsizedText)) {
            super.onDraw(canvas);
            return;
        }
        getPaint().setColor(getCurrentTextColor());
        canvas.drawText(this.mEllipsizedText, 0.0f, (float) (-this.mFontMetricsInt.top), getPaint());
    }
}
