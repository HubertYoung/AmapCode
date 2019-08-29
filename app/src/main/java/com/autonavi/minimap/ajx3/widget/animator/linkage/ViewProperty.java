package com.autonavi.minimap.ajx3.widget.animator.linkage;

import android.support.annotation.Nullable;
import com.autonavi.minimap.ajx3.dom.KeyDefine;

class ViewProperty {
    static final int PROPERTY_TYPE_ATTRIBUTE = 2;
    static final int PROPERTY_TYPE_SIZE = 0;
    static final int PROPERTY_TYPE_STYLE = 1;
    static final int TYPE_COLOR = 2;
    static final int TYPE_FLOAT = 1;
    static final int TYPE_FORMULA = 3;
    private String mFormula;
    private float mFrom;
    private Float mLastPercent;
    private float mLastValue;
    private String mProperty;
    private int mPropertyType;
    private int mStyle;
    private float mTo;
    private int mType;

    ViewProperty(String str, float f, float f2, int i, int i2) {
        this(str, f, f2, i, i2, null);
    }

    ViewProperty(String str, int i, int i2, String str2) {
        this(str, 0.0f, 0.0f, i, i2, str2);
    }

    private ViewProperty(String str, float f, float f2, int i, int i2, String str2) {
        this.mStyle = -1;
        this.mLastPercent = null;
        this.mFormula = null;
        this.mFrom = f;
        this.mTo = f2;
        this.mProperty = str;
        this.mType = i;
        this.mPropertyType = i2;
        this.mFormula = str2;
        if (this.mPropertyType == 1) {
            this.mStyle = KeyDefine.name2Type(this.mProperty);
        }
    }

    /* access modifiers changed from: 0000 */
    public float getFrom() {
        return this.mFrom;
    }

    /* access modifiers changed from: 0000 */
    public float getTo() {
        return this.mTo;
    }

    /* access modifiers changed from: 0000 */
    public String getProperty() {
        return this.mProperty;
    }

    /* access modifiers changed from: 0000 */
    public int getType() {
        return this.mType;
    }

    /* access modifiers changed from: 0000 */
    public Float getLastPercent() {
        return this.mLastPercent;
    }

    /* access modifiers changed from: 0000 */
    public void setLastPercent(float f) {
        this.mLastPercent = Float.valueOf(f);
    }

    /* access modifiers changed from: 0000 */
    public float getLastValue() {
        return this.mLastValue;
    }

    /* access modifiers changed from: 0000 */
    public void setLastValue(float f) {
        this.mLastValue = f;
    }

    /* access modifiers changed from: 0000 */
    public int getPropertyType() {
        return this.mPropertyType;
    }

    /* access modifiers changed from: 0000 */
    public void setStyleValue(int i) {
        this.mStyle = i;
    }

    /* access modifiers changed from: 0000 */
    public int getStyleValue() {
        return this.mStyle;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public String getFormula() {
        return this.mFormula;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("property:");
        sb.append(this.mProperty);
        sb.append("  from:");
        sb.append(this.mFrom);
        sb.append("    to:");
        sb.append(this.mTo);
        sb.append("   lastValue:");
        sb.append(this.mLastValue);
        sb.append("   lastPercent:");
        sb.append(this.mLastPercent);
        return sb.toString();
    }
}
