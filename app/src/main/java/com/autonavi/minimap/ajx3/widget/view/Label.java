package com.autonavi.minimap.ajx3.widget.view;

import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.platform.impl.TextMeasurement;
import com.autonavi.minimap.ajx3.util.ComputeUtils;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.LabelProperty;

public class Label extends View implements ViewExtension {
    public static final float BOLD_RATIO = 0.2f;
    public static final int DEFAULT_FONT_SIZE = DimensionUtils.standardUnitToPixel(28.0f);
    public static final float STROKE_WIDTH = (DimensionUtils.getDensisty() * 0.2f);
    public static final String TAG = "NewLabel";
    private boolean isMultiLineheight = true;
    private Alignment layoutAlignment = Alignment.ALIGN_NORMAL;
    private final IAjxContext mAjxContext;
    private int mCurTextColor = -16777216;
    private String mFontFamily;
    private int mFontSize = DEFAULT_FONT_SIZE;
    private int mFontStyle = Property.NODE_PROPERTY_FONT_NORMAL;
    private int mFontWeight = Property.NODE_PROPERTY_FONT_NORMAL;
    private int mGravity = 51;
    private Layout mLayout;
    private int mLineClamp = Integer.MAX_VALUE;
    private float mLineHeight = 1.0f;
    private int mPatinFlags = 1;
    private final BaseProperty mProperty;
    private String mText = "";
    private int mTextOverflow = Property.NODE_PROPERTY_TEXT_OVERFLOW_UNDEFINED;

    /* access modifiers changed from: protected */
    public boolean isRich() {
        return false;
    }

    public Label(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mProperty = createProperty(iAjxContext);
    }

    /* access modifiers changed from: protected */
    public BaseProperty createProperty(@NonNull IAjxContext iAjxContext) {
        return new LabelProperty(this, iAjxContext);
    }

    public void setText(String str) {
        if (!TextUtils.equals(str, this.mText)) {
            this.mText = str;
            prepareRelayout();
        }
    }

    public CharSequence getText() {
        return this.mText;
    }

    public void setFontSize(int i) {
        if (this.mFontSize != i) {
            this.mFontSize = i;
            prepareRelayout();
        }
    }

    public void setLayoutAlignment(Alignment alignment) {
        if (this.layoutAlignment != alignment) {
            this.layoutAlignment = alignment;
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

    public void setFontStyle(int i) {
        if (this.mFontStyle != i) {
            this.mFontStyle = i;
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

    public void setFontFamily(String str) {
        if (!StringUtils.isEqual(this.mFontFamily, str)) {
            this.mFontFamily = str;
            prepareRelayout();
        }
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
        int width2 = this.mLayout.getWidth();
        if (width2 >= width) {
            return (float) compoundPaddingLeft;
        }
        int i = this.mGravity & 7;
        if (i == 3) {
            return (float) compoundPaddingLeft;
        }
        if (i == 5) {
            return (float) ((compoundPaddingLeft + width) - width2);
        }
        return (float) (compoundPaddingLeft + ((width - width2) / 2));
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
        if (i != this.mGravity) {
            this.mGravity = i;
        }
        prepareRelayout();
    }

    public void setPaintFlags(int i) {
        if (this.mPatinFlags != i) {
            this.mPatinFlags = i;
            invalidate();
        }
    }

    public int getPaintFlags() {
        return this.mPatinFlags;
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

    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
        prepareRelayout();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        prepareRelayout();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!handleEmptyText()) {
            canvas.save();
            if (this.mLayout == null) {
                this.mLayout = assumeLayout();
                if (this.mLayout == null) {
                    return;
                }
            }
            canvas.translate(getHorizontalOffset(), getVerticalOffset());
            Layout layout = this.mLayout;
            TextPaint paint = layout.getPaint();
            paint.setFlags(this.mPatinFlags | paint.getFlags());
            if (((LabelProperty) this.mProperty).hasAdditionalStroke()) {
                paint.setColor(((LabelProperty) this.mProperty).getStrokeColor());
                paint.setStyle(Style.STROKE);
                paint.setStrokeWidth(((LabelProperty) this.mProperty).getStrokeWidth());
                layout.draw(canvas);
                if (this.mFontWeight == 1056964735 || this.mFontWeight == 1056964741) {
                    paint.setStyle(Style.FILL_AND_STROKE);
                    paint.setStrokeWidth(STROKE_WIDTH);
                } else {
                    paint.setStyle(Style.FILL);
                }
            }
            paint.setColor(this.mCurTextColor);
            layout.draw(canvas);
            canvas.restore();
        }
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public Layout getCacheLayout(long j) {
        return TextMeasurement.fetchLayout(j);
    }

    private Layout assumeLayout() {
        Layout cacheLayout = getCacheLayout(this.mAjxContext.getDomTree().getNodeId(this));
        int width = (getWidth() - getCompoundPaddingLeft()) - getCompountPaddingRight();
        if (cacheLayout != null && cacheLayout.getWidth() == width) {
            return cacheLayout;
        }
        Typeface generateTypeface = TextMeasurement.generateTypeface(getContext(), this.mFontWeight, this.mFontStyle, this.mFontFamily);
        Layout buildTextLayout = TextMeasurement.buildTextLayout(this.mText, isRich(), generateTypeface, this.mFontSize, width, 2, this.mLineClamp, this.isMultiLineheight, this.mLineHeight, this.mTextOverflow, this.layoutAlignment, this);
        if (buildTextLayout != null && generateTypeface == Ajx.getInstance().getDefaultTypeface(0) && TextMeasurement.isFontBold(this.mFontWeight)) {
            buildTextLayout.getPaint().setFakeBoldText(true);
        }
        return buildTextLayout;
    }

    private boolean handleEmptyText() {
        return TextUtils.isEmpty(this.mText);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
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
