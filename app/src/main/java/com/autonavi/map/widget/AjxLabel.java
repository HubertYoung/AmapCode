package com.autonavi.map.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.platform.impl.TextMeasurement;
import com.autonavi.minimap.ajx3.util.ComputeUtils;

public class AjxLabel extends View {
    public static final int DEFAULT_FONT_SIZE = 24;
    public static final String TAG = "NewLabel";
    private boolean isMultiLineheight = true;
    private Alignment layoutAlignment = Alignment.ALIGN_NORMAL;
    private int mCurTextColor = -16777216;
    private int mFontSize = 24;
    private int mFontWeight = Property.NODE_PROPERTY_FONT_NORMAL;
    private int mGravity = 51;
    private Layout mLayout;
    private int mLineClamp = Integer.MAX_VALUE;
    private float mLineHeight = 1.0f;
    private int mPatinFlags = 1;
    private String mText = "";
    private int mTextOverflow = Property.NODE_PROPERTY_TEXT_OVERFLOW_UNDEFINED;

    /* access modifiers changed from: protected */
    public boolean isRich() {
        return false;
    }

    public AjxLabel(@NonNull Context context) {
        super(context);
    }

    public CharSequence getText() {
        return this.mText;
    }

    public void setText(String str) {
        if (!TextUtils.equals(str, this.mText)) {
            this.mText = str;
            prepareRelayout();
        }
    }

    public void setFontSize(int i) {
        if (this.mFontSize != i) {
            this.mFontSize = i;
            prepareRelayout();
        }
    }

    public void setLineClamp(int i) {
        if (this.mLineClamp != i) {
            this.mLineClamp = i;
            prepareRelayout();
        }
    }

    public void setLineHeight(float f, boolean z) {
        if (this.isMultiLineheight != z || !ComputeUtils.floatsEqual(this.mLineHeight, f)) {
            this.mLineHeight = f;
            this.isMultiLineheight = z;
            prepareRelayout();
        }
    }

    public void setFontWeight(int i) {
        if (this.mFontWeight != i) {
            this.mFontWeight = i;
            prepareRelayout();
        }
    }

    public void setTextOverflow(int i) {
        if (this.mTextOverflow != i) {
            this.mTextOverflow = i;
            prepareRelayout();
        }
    }

    public void setTextColor(int i) {
        boolean z;
        if (i != this.mCurTextColor) {
            this.mCurTextColor = i;
            z = true;
        } else {
            z = false;
        }
        if (z) {
            invalidate();
        }
    }

    private Typeface generateTypeface() {
        if (this.mFontWeight != 1056964735) {
            return Typeface.DEFAULT;
        }
        return Typeface.DEFAULT_BOLD;
    }

    private float getVerticalOffset() {
        int compoundPaddingTop = getCompoundPaddingTop();
        int height = (getHeight() - compoundPaddingTop) - getCompoundPaddingBottom();
        int height2 = this.mLayout.getHeight();
        if (height2 >= height) {
            return (float) compoundPaddingTop;
        }
        int i = this.mGravity & 112;
        if (i == 48) {
            return (float) compoundPaddingTop;
        }
        if (i == 80) {
            return (float) ((compoundPaddingTop + height) - height2);
        }
        return (float) (compoundPaddingTop + ((height - height2) / 2));
    }

    private float getHorizontalOffset() {
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int width = (getWidth() - compoundPaddingLeft) - getCompountPaddingRight();
        int textRightMost = (int) getTextRightMost();
        if (textRightMost >= width) {
            return (float) compoundPaddingLeft;
        }
        int i = this.mGravity & 7;
        if (i == 3) {
            return (float) compoundPaddingLeft;
        }
        if (i == 5) {
            return (float) ((compoundPaddingLeft + width) - textRightMost);
        }
        return (float) (compoundPaddingLeft + ((width - textRightMost) / 2));
    }

    private float getTextRightMost() {
        int lineCount = this.mLayout.getLineCount();
        float f = Float.MIN_VALUE;
        for (int i = 0; i < lineCount; i++) {
            float lineRight = this.mLayout.getLineRight(i);
            if (lineRight > f) {
                f = lineRight;
            }
        }
        return f;
    }

    public void setGravity(int i) {
        if (i == 17) {
            setLayoutAlignment(Alignment.ALIGN_CENTER);
        }
        if (i != this.mGravity) {
            this.mGravity = i;
            invalidate();
        }
    }

    public void setLayoutAlignment(Alignment alignment) {
        if (this.layoutAlignment != alignment) {
            this.layoutAlignment = alignment;
        }
    }

    public int getPaintFlags() {
        return this.mPatinFlags;
    }

    public void setPaintFlags(int i) {
        if (this.mPatinFlags != i) {
            this.mPatinFlags = i;
            invalidate();
        }
    }

    private int getCompoundPaddingTop() {
        return getPaddingTop();
    }

    private int getCompoundPaddingBottom() {
        return getPaddingBottom();
    }

    private int getCompoundPaddingLeft() {
        return getPaddingLeft();
    }

    private int getCompountPaddingRight() {
        return getPaddingRight();
    }

    private void prepareRelayout() {
        if (this.mLayout != null) {
            this.mLayout = null;
        }
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!handleEmptyText()) {
            canvas.save();
            if (this.mLayout != null) {
                canvas.translate(getHorizontalOffset(), getVerticalOffset());
                Layout layout = this.mLayout;
                TextPaint paint = layout.getPaint();
                paint.setFlags(this.mPatinFlags);
                paint.setColor(this.mCurTextColor);
                layout.draw(canvas);
                canvas.restore();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int compoundPaddingLeft = getCompoundPaddingLeft() + getCompountPaddingRight();
        int compoundPaddingTop = getCompoundPaddingTop() + getCompoundPaddingBottom();
        if (this.mLayout == null && !handleEmptyText()) {
            this.mLayout = assumeLayout(size);
        }
        if (this.mLayout != null) {
            compoundPaddingLeft += this.mLayout.getWidth();
            compoundPaddingTop += this.mLayout.getHeight();
        }
        setMeasuredDimension(resolveSize(compoundPaddingLeft, i), resolveSize(compoundPaddingTop, i2));
    }

    private Layout assumeLayout(int i) {
        Layout buildTextLayout = TextMeasurement.buildTextLayout(this.mText, isRich(), generateTypeface(), this.mFontSize, (i - getCompoundPaddingLeft()) - getCompountPaddingRight(), 2, this.mLineClamp, this.isMultiLineheight, this.mLineHeight, this.mTextOverflow, this.layoutAlignment, this);
        int width = buildTextLayout.getWidth();
        int height = buildTextLayout.getHeight();
        setMinimumWidth(width);
        setMinimumHeight(height);
        return buildTextLayout;
    }

    private boolean handleEmptyText() {
        return TextUtils.isEmpty(this.mText);
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence text = getText();
        if (!TextUtils.isEmpty(text)) {
            accessibilityEvent.getText().add(text);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        CharSequence text = getText();
        if (!TextUtils.isEmpty(text)) {
            accessibilityNodeInfo.setText(text);
        }
    }
}
