package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.Nullable;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxContainerDomNode;

public class JsDomNodeFake extends JsDomNode {
    private long mId;
    private int mTag;
    private String mTagName;

    public int getAttributeCount() {
        return 0;
    }

    public String getAttributeKey(int i) {
        return null;
    }

    public String getAttributeValue(int i) {
        return null;
    }

    public IJsDomData[] getChildren() {
        return null;
    }

    public int getPropertyCount() {
        return 0;
    }

    public int getPropertyKey(int i) {
        return 0;
    }

    public int getPropertyStyle(int i) {
        return 0;
    }

    public int getPropertyValueType(int i) {
        return 0;
    }

    public boolean propertyBooleanValue(int i) {
        return false;
    }

    public Object propertyFilterValue(int i) {
        return null;
    }

    public float propertyFloatValue(int i) {
        return 0.0f;
    }

    public int propertyIntValue(int i) {
        return 0;
    }

    public String propertyOriginValue(int i) {
        return null;
    }

    public String propertyStringValue(int i) {
        return null;
    }

    public JsDomNodeFake(long j) {
        this.mId = j;
        this.mTag = (int) j;
        this.mTagName = String.valueOf(j);
    }

    public float[] dimensions() {
        return new float[]{0.0f, 0.0f, 0.0f, 0.0f};
    }

    public float[] paddings() {
        return new float[]{0.0f, 0.0f, 0.0f, 0.0f};
    }

    public int[] propertyIntArray(int i) {
        return new int[0];
    }

    public float[] propertyFloatArray(int i) {
        return new float[0];
    }

    public Object[] propertyObjArray(int i) {
        return new Object[0];
    }

    public AjxDomNode createAjxNode() {
        return new AjxContainerDomNode(this);
    }

    public long id() {
        return this.mId;
    }

    public int tag() {
        return this.mTag;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public String getTagName() {
        return this.mTagName;
    }
}
