package com.autonavi.widget.expandabletextview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.autonavi.widget.R;

public class ExpandableTextView extends TextView {
    private static final int DEFAULT_ANIMATOR_DURATION = 300;
    private static final String ELLIPSIS_HINT = "...";
    private static final int EXPAND_HINT_COLOR = -13330213;
    private static final int EXPAND_HINT_PRESSED_BACKGROUND_COLOR = 1436129689;
    private static final String GAP_PLACEHOLDER = "gap";
    private static final int GAP_PLACEHOLDER_LENGTH = 3;
    private static final int MAX_SHRINK_LINES = 2;
    private static final int SHRINK_HINT_COLOR = -1618884;
    private static final int SHRINK_HINT_PRESSED_BACKGROUND_COLOR = 1436129689;
    public static final int STATE_EXPAND = 1;
    public static final int STATE_FIT = 2;
    public static final int STATE_PRE_EXPAND = 4;
    public static final int STATE_PRE_SHRINK = 3;
    public static final int STATE_SHRINK = 0;
    public static final int STATE_UNKNOWN = -1;
    private static final boolean WITH_ANIMATOR = false;
    private a mAnimator;
    private int mAnimatorDuration = 300;
    private BufferType mBufferType = BufferType.NORMAL;
    protected int mCurrState = -1;
    private float mEllipsisWidth;
    private b mExpandGap;
    private String mExpandHint;
    /* access modifiers changed from: private */
    public int mExpandHintColor = EXPAND_HINT_COLOR;
    private int mExpandHintPadding;
    /* access modifiers changed from: private */
    public int mExpandHintPressedBackgroundColor = 1436129689;
    private CharSequence mExpandText;
    protected int mExpectedState = 0;
    private int mMaxShrinkLines = 2;
    /* access modifiers changed from: private */
    public d mOnStateChangeListener;
    private CharSequence mOriginText;
    private b mShrinkGap;
    private String mShrinkHint;
    /* access modifiers changed from: private */
    public int mShrinkHintColor = SHRINK_HINT_COLOR;
    private int mShrinkHintPadding;
    /* access modifiers changed from: private */
    public int mShrinkHintPressedBackgroundColor = 1436129689;
    private e mTouchableSpan;
    private boolean mWithAnimator = false;

    class a {
        int a;
        int b;
        int c;
        ValueAnimator d;
        boolean e;
        int f;
        int g;
        CharSequence h;
        private Runnable j;

        private a() {
            this.a = 0;
            this.f = 300;
        }

        /* synthetic */ a(ExpandableTextView expandableTextView, byte b2) {
            this();
        }

        public final void a() {
            c();
            this.a = 1;
        }

        public final boolean b() {
            return this.a == 1;
        }

        public final void c() {
            if (this.j != null) {
                ExpandableTextView.this.removeCallbacks(this.j);
                this.j = null;
            }
            if (this.d != null && this.d.isRunning()) {
                this.d.cancel();
                if (this.c > 0) {
                    a(this.c);
                }
                if (!this.e && !TextUtils.isEmpty(this.h)) {
                    ExpandableTextView.this.setTextInternal(this.h, BufferType.SPANNABLE);
                }
                ExpandableTextView.this.dispatchStateChanged(this.e ? 1 : 0);
            }
            this.h = null;
            this.d = null;
            this.a = 0;
            this.b = 0;
            this.c = 0;
        }

        /* access modifiers changed from: 0000 */
        public final void a(int i2) {
            this.g = i2;
            ExpandableTextView.this.requestLayout();
        }

        public final void a(boolean z, int i2, int i3) {
            if (this.a == 1) {
                this.a = 2;
                this.e = z;
                this.b = i2;
                this.c = i3;
                this.g = i2;
                this.j = new Runnable() {
                    public final void run() {
                        a aVar = a.this;
                        aVar.d = ValueAnimator.ofInt(new int[]{aVar.b, aVar.c});
                        aVar.d.setDuration((long) aVar.f);
                        aVar.d.addUpdateListener(new AnimatorUpdateListener() {
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                a.this.a(((Integer) valueAnimator.getAnimatedValue()).intValue());
                            }
                        });
                        aVar.d.addListener(new AnimatorListenerAdapter() {
                            public final void onAnimationEnd(Animator animator) {
                                ExpandableTextView.this.dispatchStateChanged(a.this.e ? 1 : 0);
                                if (!a.this.e && !TextUtils.isEmpty(a.this.h)) {
                                    ExpandableTextView.this.setTextInternal(a.this.h, BufferType.SPANNABLE);
                                }
                                a.this.c();
                            }
                        });
                        aVar.d.start();
                    }
                };
                ExpandableTextView.this.post(this.j);
            }
        }
    }

    static class b extends ReplacementSpan {
        int a;

        public final void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        }

        public b(int i) {
            this.a = i;
        }

        public final int getSize(Paint paint, CharSequence charSequence, int i, int i2, FontMetricsInt fontMetricsInt) {
            return this.a;
        }
    }

    public class c extends LinkMovementMethod {
        private e b;

        public c() {
        }

        public final boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                this.b = a(textView, spannable, motionEvent);
                if (this.b != null) {
                    this.b.a = true;
                    Selection.setSelection(spannable, spannable.getSpanStart(this.b), spannable.getSpanEnd(this.b));
                }
            } else if (motionEvent.getAction() == 2) {
                e a2 = a(textView, spannable, motionEvent);
                if (!(this.b == null || a2 == this.b)) {
                    this.b.a = false;
                    this.b = null;
                    Selection.removeSelection(spannable);
                }
            } else {
                if (this.b != null) {
                    this.b.a = false;
                    super.onTouchEvent(textView, spannable, motionEvent);
                }
                this.b = null;
                Selection.removeSelection(spannable);
            }
            return true;
        }

        private static e a(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            int x = ((int) motionEvent.getX()) - textView.getTotalPaddingLeft();
            int y = ((int) motionEvent.getY()) - textView.getTotalPaddingTop();
            int scrollX = x + textView.getScrollX();
            Layout layout = textView.getLayout();
            int offsetForHorizontal = layout.getOffsetForHorizontal(layout.getLineForVertical(y + textView.getScrollY()), (float) scrollX);
            e[] eVarArr = (e[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, e.class);
            if (eVarArr.length > 0) {
                return eVarArr[0];
            }
            return null;
        }
    }

    public interface d {
    }

    class e extends ClickableSpan {
        boolean a;
        OnClickListener b;

        private e() {
        }

        /* synthetic */ e(ExpandableTextView expandableTextView, byte b2) {
            this();
        }

        public void onClick(View view) {
            if (this.b != null) {
                this.b.onClick(view);
            }
        }

        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            switch (ExpandableTextView.this.mCurrState) {
                case 0:
                    textPaint.setColor(ExpandableTextView.this.mExpandHintColor);
                    textPaint.bgColor = this.a ? ExpandableTextView.this.mExpandHintPressedBackgroundColor : 0;
                    break;
                case 1:
                case 3:
                case 4:
                    textPaint.setColor(ExpandableTextView.this.mShrinkHintColor);
                    textPaint.bgColor = this.a ? ExpandableTextView.this.mShrinkHintPressedBackgroundColor : 0;
                    break;
            }
            textPaint.setUnderlineText(false);
        }
    }

    public ExpandableTextView(Context context) {
        super(context);
        init();
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initAttr(context, attributeSet);
        init();
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initAttr(context, attributeSet);
        init();
    }

    private void initAttr(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableTextView);
            if (obtainStyledAttributes != null) {
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int index = obtainStyledAttributes.getIndex(i);
                    if (index == R.styleable.ExpandableTextView_maxShrinkLines) {
                        this.mMaxShrinkLines = obtainStyledAttributes.getInteger(index, 2);
                    } else if (index == R.styleable.ExpandableTextView_expandHint) {
                        this.mExpandHint = obtainStyledAttributes.getString(index);
                    } else if (index == R.styleable.ExpandableTextView_shrinkHint) {
                        this.mShrinkHint = obtainStyledAttributes.getString(index);
                    } else if (index == R.styleable.ExpandableTextView_expandHintColor) {
                        this.mExpandHintColor = obtainStyledAttributes.getInteger(index, EXPAND_HINT_COLOR);
                    } else if (index == R.styleable.ExpandableTextView_shrinkHintColor) {
                        this.mShrinkHintColor = obtainStyledAttributes.getInteger(index, SHRINK_HINT_COLOR);
                    } else if (index == R.styleable.ExpandableTextView_expandHintPressedBackgroundColor) {
                        this.mExpandHintPressedBackgroundColor = obtainStyledAttributes.getInteger(index, 1436129689);
                    } else if (index == R.styleable.ExpandableTextView_shrinkHintPressedBackgroundColor) {
                        this.mShrinkHintPressedBackgroundColor = obtainStyledAttributes.getInteger(index, 1436129689);
                    } else if (index == R.styleable.ExpandableTextView_initState) {
                        this.mExpectedState = obtainStyledAttributes.getInteger(index, 0);
                    } else if (index == R.styleable.ExpandableTextView_expandHintPadding) {
                        this.mExpandHintPadding = obtainStyledAttributes.getDimensionPixelOffset(index, 0);
                    } else if (index == R.styleable.ExpandableTextView_shrinkHintPadding) {
                        this.mShrinkHintPadding = obtainStyledAttributes.getDimensionPixelOffset(index, 0);
                    } else if (index == R.styleable.ExpandableTextView_animatorDuration) {
                        this.mAnimatorDuration = obtainStyledAttributes.getInteger(index, 300);
                    } else if (index == R.styleable.ExpandableTextView_withAnimator) {
                        setWithAnimator(obtainStyledAttributes.getBoolean(index, false));
                    }
                }
                obtainStyledAttributes.recycle();
            }
        }
    }

    public int getCurrState() {
        return this.mCurrState;
    }

    private void init() {
        this.mEllipsisWidth = getPaint().measureText(ELLIPSIS_HINT);
        ensureMovementMethod();
    }

    private void ensureMovementMethod() {
        if (this.mTouchableSpan == null && (!TextUtils.isEmpty(this.mExpandHint) || !TextUtils.isEmpty(this.mShrinkHint))) {
            this.mTouchableSpan = new e(this, 0);
            this.mTouchableSpan.b = new OnClickListener() {
                public final void onClick(View view) {
                    ExpandableTextView.this.toggle();
                }
            };
            setMovementMethod(new c());
        }
    }

    public void setTextSize(int i, float f) {
        this.mEllipsisWidth = getPaint().measureText(ELLIPSIS_HINT);
        super.setTextSize(i, f);
        if (!TextUtils.isEmpty(this.mOriginText)) {
            setText(this.mOriginText, this.mBufferType);
        }
    }

    public void setWithAnimator(boolean z) {
        if (this.mWithAnimator != z) {
            this.mWithAnimator = z;
            if (this.mWithAnimator && this.mAnimator == null) {
                this.mAnimator = new a(this, 0);
                this.mAnimator.f = this.mAnimatorDuration;
            }
        }
    }

    public void setAnimatorDuration(int i) {
        if (this.mAnimatorDuration != i) {
            this.mAnimatorDuration = i;
            if (this.mAnimator != null) {
                this.mAnimator.f = this.mAnimatorDuration;
            }
        }
    }

    public d getOnStateChangeListener() {
        return this.mOnStateChangeListener;
    }

    public void setOnStateChangeListener(d dVar) {
        this.mOnStateChangeListener = dVar;
    }

    public int getMaxShrinkLines() {
        return this.mMaxShrinkLines;
    }

    public void setMaxShrinkLines(int i) {
        if (this.mMaxShrinkLines != i) {
            this.mMaxShrinkLines = i;
        }
    }

    public void setStateHint(@Nullable String str, @Nullable String str2) {
        this.mExpandHint = str;
        this.mShrinkHint = str2;
        ensureMovementMethod();
        if (this.mCurrState != -1 && this.mCurrState != 2 && !TextUtils.isEmpty(this.mOriginText)) {
            setText(this.mOriginText, this.mBufferType);
        }
    }

    public void setStateHintPadding(int i, int i2) {
        this.mExpandHintPadding = i;
        this.mShrinkHintPadding = i2;
        if (this.mCurrState != -1 && this.mCurrState != 2 && !TextUtils.isEmpty(this.mOriginText)) {
            setText(this.mOriginText, this.mBufferType);
        }
    }

    public void setStateHintColor(int i, int i2) {
        this.mExpandHintColor = i;
        this.mShrinkHintColor = i2;
        if (!TextUtils.isEmpty(this.mOriginText) && this.mCurrState != -1) {
            invalidate();
        }
    }

    public void setStateHintPressedBackgroundColor(int i, int i2) {
        this.mExpandHintPressedBackgroundColor = i;
        this.mShrinkHintPressedBackgroundColor = i2;
        if (!TextUtils.isEmpty(this.mOriginText) && this.mCurrState != -1) {
            invalidate();
        }
    }

    public int getExpectedState() {
        return this.mExpectedState;
    }

    private void ensureAnimator() {
        if (this.mAnimator == null) {
            this.mAnimator = new a(this, 0);
            this.mAnimator.f = this.mAnimatorDuration;
        }
    }

    public void expand(boolean z) {
        if (this.mExpectedState != 1) {
            this.mExpectedState = 1;
            if (!TextUtils.isEmpty(this.mOriginText)) {
                if (z && this.mCurrState == 0) {
                    ensureAnimator();
                    this.mAnimator.a();
                }
                setTextInternal(this.mOriginText, this.mBufferType);
            }
        }
    }

    public void shrink(boolean z) {
        if (this.mExpectedState != 0) {
            this.mExpectedState = 0;
            if (!TextUtils.isEmpty(this.mOriginText)) {
                if (z && this.mCurrState == 1) {
                    ensureAnimator();
                    this.mAnimator.a();
                }
                setTextInternal(this.mOriginText, this.mBufferType);
                requestLayout();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3 = 0;
        if (this.mAnimator != null) {
            if (this.mAnimator.a == 2) {
                setMeasuredDimension(getMeasuredWidth(), this.mAnimator.g);
                return;
            }
        }
        int measuredHeight = getMeasuredHeight();
        super.onMeasure(i, i2);
        if (this.mOriginText != null && this.mMaxShrinkLines > 0) {
            Layout layout = getLayout();
            if (layout.getLineCount() <= this.mMaxShrinkLines) {
                if (this.mCurrState != 0) {
                    i3 = 2;
                }
                dispatchStateChanged(i3);
            } else if (this.mExpectedState == 1) {
                measureExpand(i, i2, measuredHeight);
            } else {
                if (this.mExpectedState == 0) {
                    measureShrink(i, i2, layout, measuredHeight);
                }
            }
        }
    }

    @SuppressLint({"WrongCall"})
    private void measureShrink(int i, int i2, Layout layout, int i3) {
        boolean z;
        int i4;
        int width = layout.getWidth();
        TextPaint paint = getPaint();
        float f = ((float) width) - this.mEllipsisWidth;
        if (!TextUtils.isEmpty(this.mExpandHint)) {
            f = (f - ((float) this.mExpandHintPadding)) - paint.measureText(this.mExpandHint);
            z = true;
        } else {
            z = false;
        }
        int i5 = this.mMaxShrinkLines - 1;
        if (f <= 0.0f) {
            i4 = layout.getLineStart(i5);
        } else {
            i4 = layout.getOffsetForHorizontal(i5, f);
        }
        while (i4 >= 0) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(this.mOriginText.subSequence(0, i4));
            spannableStringBuilder.append(ELLIPSIS_HINT);
            if (z) {
                if (this.mExpandHintPadding > 0) {
                    if (this.mExpandGap == null) {
                        this.mExpandGap = new b(this.mExpandHintPadding);
                    } else {
                        this.mExpandGap.a = this.mExpandHintPadding;
                    }
                    spannableStringBuilder.append(GAP_PLACEHOLDER);
                    int length = spannableStringBuilder.length();
                    spannableStringBuilder.setSpan(this.mExpandGap, length - 3, length, 33);
                }
                spannableStringBuilder.append(this.mExpandHint);
                spannableStringBuilder.setSpan(this.mTouchableSpan, spannableStringBuilder.length() - this.mExpandHint.length(), spannableStringBuilder.length(), 34);
            }
            setTextInternal(spannableStringBuilder, z ? BufferType.SPANNABLE : this.mBufferType);
            if (this.mAnimator != null) {
                this.mAnimator.h = spannableStringBuilder;
            }
            super.onMeasure(i, i2);
            if (getLayout().getLineCount() <= this.mMaxShrinkLines) {
                break;
            }
            i4--;
        }
        if (this.mAnimator == null || !this.mAnimator.b()) {
            dispatchStateChanged(0);
            return;
        }
        int measuredHeight = getMeasuredHeight();
        if (!TextUtils.isEmpty(this.mExpandText)) {
            setTextInternal(this.mExpandText, BufferType.SPANNABLE);
        }
        setMeasuredDimension(getMeasuredWidth(), i3);
        this.mAnimator.a(false, i3, measuredHeight);
        dispatchStateChanged(3);
    }

    @SuppressLint({"WrongCall"})
    private void measureExpand(int i, int i2, int i3) {
        if (this.mCurrState != 1 && !TextUtils.isEmpty(this.mShrinkHint)) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.mOriginText);
            if (this.mShrinkHintPadding > 0) {
                if (this.mShrinkGap == null) {
                    this.mShrinkGap = new b(this.mShrinkHintPadding);
                } else {
                    this.mShrinkGap.a = this.mShrinkHintPadding;
                }
                spannableStringBuilder.append(GAP_PLACEHOLDER);
                int length = spannableStringBuilder.length();
                spannableStringBuilder.setSpan(this.mShrinkGap, length - 3, length, 33);
            }
            spannableStringBuilder.append(this.mShrinkHint);
            spannableStringBuilder.setSpan(this.mTouchableSpan, spannableStringBuilder.length() - this.mShrinkHint.length(), spannableStringBuilder.length(), 33);
            setTextInternal(spannableStringBuilder, BufferType.SPANNABLE);
            this.mExpandText = spannableStringBuilder;
            super.onMeasure(i, i2);
        } else if (this.mCurrState != 1) {
            this.mExpandText = this.mOriginText;
        }
        if (this.mAnimator == null || !this.mAnimator.b()) {
            dispatchStateChanged(1);
            return;
        }
        int measuredHeight = getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), i3);
        this.mAnimator.a(true, i3, measuredHeight);
        dispatchStateChanged(4);
    }

    /* access modifiers changed from: private */
    public void dispatchStateChanged(final int i) {
        if (this.mCurrState != i) {
            this.mCurrState = i;
            if (this.mOnStateChangeListener != null) {
                post(new Runnable() {
                    public final void run() {
                        switch (i) {
                            case 0:
                                ExpandableTextView.this.mOnStateChangeListener;
                                return;
                            case 1:
                                ExpandableTextView.this.mOnStateChangeListener;
                                return;
                            case 2:
                                ExpandableTextView.this.mOnStateChangeListener;
                                return;
                            case 3:
                                ExpandableTextView.this.mOnStateChangeListener;
                                break;
                            case 4:
                                ExpandableTextView.this.mOnStateChangeListener;
                                return;
                        }
                    }
                });
            }
        }
    }

    public void toggle() {
        switch (this.mCurrState) {
            case 0:
                expand(this.mWithAnimator);
                return;
            case 1:
                shrink(this.mWithAnimator);
                break;
        }
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        if (this.mAnimator != null) {
            this.mAnimator.c();
        }
        this.mCurrState = -1;
        this.mOriginText = charSequence;
        this.mBufferType = bufferType;
        setTextInternal(this.mOriginText, bufferType);
        requestLayout();
    }

    /* access modifiers changed from: private */
    public void setTextInternal(CharSequence charSequence, BufferType bufferType) {
        super.setText(charSequence, bufferType);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mAnimator != null) {
            this.mAnimator.c();
        }
        super.onDetachedFromWindow();
    }
}
