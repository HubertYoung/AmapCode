package com.autonavi.minimap.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Iterator;

public class AutofitHelper {
    private static final int DEFAULT_MIN_TEXT_SIZE = 11;
    private static final float DEFAULT_PRECISION = 0.5f;
    private static final boolean SPEW = false;
    private static final String TAG = "AutoFitTextHelper";
    private float mDensity;
    private boolean mEnabled;
    private boolean mIsAutofitting;
    private ArrayList<OnTextSizeChangeListener> mListeners;
    private int mMaxLines;
    private float mMaxTextSize;
    private float mMinTextSize;
    private TextPaint mPaint;
    private float mPrecision;
    private float mScaledDensity;
    private float mTextSize;
    private TextView mTextView;
    private TextWatcher mTextWatcher = new AutofitTextWatcher(this, 0);

    class AutofitTextWatcher implements TextWatcher {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        private AutofitTextWatcher() {
        }

        /* synthetic */ AutofitTextWatcher(AutofitHelper autofitHelper, byte b) {
            this();
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            AutofitHelper.this.autofit();
        }
    }

    public interface OnTextSizeChangeListener {
        void onTextSizeChange(float f, float f2);
    }

    public static AutofitHelper create(TextView textView) {
        return create(textView, null, 0);
    }

    public static AutofitHelper create(TextView textView, AttributeSet attributeSet) {
        return create(textView, attributeSet, 0);
    }

    public static AutofitHelper create(TextView textView, AttributeSet attributeSet, int i) {
        boolean z;
        AutofitHelper autofitHelper = new AutofitHelper(textView);
        if (attributeSet != null) {
            Context context = textView.getContext();
            float precision = autofitHelper.getPrecision();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AmapTextView, i, 0);
            z = obtainStyledAttributes.getBoolean(R.styleable.AmapTextView_sizeToFit, false);
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmapTextView_minFontSize, (int) autofitHelper.getMinTextSize());
            float f = obtainStyledAttributes.getFloat(R.styleable.AmapTextView_precision, precision);
            obtainStyledAttributes.recycle();
            autofitHelper.setMinTextSize(0, (float) dimensionPixelSize).setPrecision(f);
        } else {
            z = false;
        }
        autofitHelper.setEnabled(z);
        return autofitHelper;
    }

    private static void autofit(TextView textView, TextPaint textPaint, float f, float f2, int i, float f3) {
        if (i > 0 && i != Integer.MAX_VALUE) {
            int width = (textView.getWidth() - textView.getPaddingLeft()) - textView.getPaddingRight();
            if (width > 0) {
                CharSequence text = textView.getText();
                TransformationMethod transformationMethod = textView.getTransformationMethod();
                if (transformationMethod != null) {
                    text = transformationMethod.getTransformation(text, textView);
                }
                Context context = textView.getContext();
                Resources system = Resources.getSystem();
                if (context != null) {
                    system = context.getResources();
                }
                DisplayMetrics displayMetrics = system.getDisplayMetrics();
                textPaint.set(textView.getPaint());
                textPaint.setTextSize(f2);
                float autofitTextSize = ((i != 1 || textPaint.measureText(text, 0, text.length()) <= ((float) width)) && getLineCount(text, textPaint, f2, (float) width, displayMetrics) <= i) ? f2 : getAutofitTextSize(text, textPaint, (float) width, i, 0.0f, f2, f3, displayMetrics);
                textView.setTextSize(0, autofitTextSize < f ? f : autofitTextSize);
            }
        }
    }

    private static float getAutofitTextSize(CharSequence charSequence, TextPaint textPaint, float f, int i, float f2, float f3, float f4, DisplayMetrics displayMetrics) {
        StaticLayout staticLayout;
        int i2;
        TextPaint textPaint2 = textPaint;
        float f5 = f;
        int i3 = i;
        float f6 = f2;
        float f7 = f3;
        while (true) {
            float f8 = (f6 + f7) / 2.0f;
            textPaint2.setTextSize(TypedValue.applyDimension(0, f8, displayMetrics));
            if (i3 != 1) {
                staticLayout = r0;
                StaticLayout staticLayout2 = new StaticLayout(charSequence, textPaint2, (int) f5, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
                i2 = staticLayout.getLineCount();
            } else {
                staticLayout = null;
                i2 = 1;
            }
            if (i2 <= i3) {
                if (i2 < i3) {
                    CharSequence charSequence2 = charSequence;
                } else {
                    float f9 = 0.0f;
                    if (i3 == 1) {
                        f9 = textPaint2.measureText(charSequence, 0, charSequence.length());
                    } else {
                        CharSequence charSequence3 = charSequence;
                        for (int i4 = 0; i4 < i2; i4++) {
                            if (staticLayout.getLineWidth(i4) > f9) {
                                f9 = staticLayout.getLineWidth(i4);
                            }
                        }
                    }
                    if (f7 - f6 < f4) {
                        return f6;
                    }
                    if (f9 <= f5) {
                        if (f9 >= f5) {
                            return f8;
                        }
                    }
                }
                f6 = f8;
            } else if (f7 - f6 < f4) {
                return f6;
            } else {
                CharSequence charSequence4 = charSequence;
            }
            f7 = f8;
        }
    }

    private static int getLineCount(CharSequence charSequence, TextPaint textPaint, float f, float f2, DisplayMetrics displayMetrics) {
        textPaint.setTextSize(TypedValue.applyDimension(0, f, displayMetrics));
        StaticLayout staticLayout = new StaticLayout(charSequence, textPaint, (int) f2, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        return staticLayout.getLineCount();
    }

    private static int getMaxLines(TextView textView) {
        TransformationMethod transformationMethod = textView.getTransformationMethod();
        if (transformationMethod != null && (transformationMethod instanceof SingleLineTransformationMethod)) {
            return 1;
        }
        if (VERSION.SDK_INT >= 16) {
            return textView.getMaxLines();
        }
        return -1;
    }

    private AutofitHelper(TextView textView) {
        textView.getContext();
        DisplayMetrics displayMetrics = textView.getResources().getDisplayMetrics();
        this.mScaledDensity = displayMetrics.scaledDensity;
        this.mDensity = displayMetrics.density;
        this.mTextView = textView;
        this.mPaint = new TextPaint();
        setRawTextSize(textView.getTextSize());
        this.mMaxLines = getMaxLines(textView);
        this.mMinTextSize = this.mScaledDensity * 11.0f;
        this.mMaxTextSize = this.mTextSize;
        this.mPrecision = DEFAULT_PRECISION;
    }

    private float getScaleSpSize(float f) {
        if (this.mScaledDensity <= 0.0f) {
            return f;
        }
        float f2 = f / this.mScaledDensity;
        return this.mScaledDensity != this.mDensity ? f2 / (this.mScaledDensity / this.mDensity) : f2;
    }

    public AutofitHelper addOnTextSizeChangeListener(OnTextSizeChangeListener onTextSizeChangeListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(onTextSizeChangeListener);
        return this;
    }

    public AutofitHelper removeOnTextSizeChangeListener(OnTextSizeChangeListener onTextSizeChangeListener) {
        if (this.mListeners != null) {
            this.mListeners.remove(onTextSizeChangeListener);
        }
        return this;
    }

    public float getPrecision() {
        return this.mPrecision;
    }

    public AutofitHelper setPrecision(float f) {
        if (this.mPrecision != f) {
            this.mPrecision = f;
            autofit();
        }
        return this;
    }

    public float getMinTextSize() {
        return this.mMinTextSize;
    }

    public AutofitHelper setMinTextSize(float f) {
        return setMinTextSize(2, f);
    }

    public AutofitHelper setMinTextSize(int i, float f) {
        Context context = this.mTextView.getContext();
        Resources system = Resources.getSystem();
        if (context != null) {
            system = context.getResources();
        }
        setRawMinTextSize(TypedValue.applyDimension(i, f, system.getDisplayMetrics()));
        return this;
    }

    private void setRawMinTextSize(float f) {
        if (((double) Math.abs(f - this.mMinTextSize)) > 1.0E-7d) {
            this.mMinTextSize = f;
            autofit();
        }
    }

    public float getMaxTextSize() {
        return this.mMaxTextSize;
    }

    public AutofitHelper setMaxTextSize(float f) {
        return setMaxTextSize(2, f);
    }

    public AutofitHelper setMaxTextSize(int i, float f) {
        Context context = this.mTextView.getContext();
        Resources system = Resources.getSystem();
        if (context != null) {
            system = context.getResources();
        }
        setRawMaxTextSize(TypedValue.applyDimension(i, f, system.getDisplayMetrics()));
        return this;
    }

    private void setRawMaxTextSize(float f) {
        if (f != this.mMaxTextSize) {
            this.mMaxTextSize = f;
            autofit();
        }
    }

    public int getMaxLines() {
        return this.mMaxLines;
    }

    public AutofitHelper setMaxLines(int i) {
        if (this.mMaxLines != i) {
            this.mMaxLines = i;
            autofit();
        }
        return this;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public AutofitHelper setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            if (z) {
                this.mTextView.addTextChangedListener(this.mTextWatcher);
                autofit();
            } else {
                this.mTextView.removeTextChangedListener(this.mTextWatcher);
                this.mTextView.setTextSize(this.mTextSize);
            }
        }
        return this;
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    public void setTextSize(float f) {
        setTextSize(2, f);
    }

    public void setTextSize(int i, float f) {
        if (!this.mIsAutofitting) {
            Context context = this.mTextView.getContext();
            Resources system = Resources.getSystem();
            if (context != null) {
                system = context.getResources();
            }
            setRawTextSize(TypedValue.applyDimension(i, f, system.getDisplayMetrics()));
        }
    }

    private void setRawTextSize(float f) {
        if (this.mTextSize != f) {
            this.mTextSize = f;
        }
    }

    /* access modifiers changed from: private */
    public void autofit() {
        float textSize = this.mTextView.getTextSize();
        this.mIsAutofitting = true;
        autofit(this.mTextView, this.mPaint, this.mMinTextSize, this.mMaxTextSize, this.mMaxLines, this.mPrecision);
        this.mIsAutofitting = false;
        float textSize2 = this.mTextView.getTextSize();
        if (textSize2 != textSize) {
            sendTextSizeChange(textSize2, textSize);
        }
    }

    private void sendTextSizeChange(float f, float f2) {
        if (this.mListeners != null) {
            Iterator<OnTextSizeChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onTextSizeChange(f, f2);
            }
        }
    }
}
