package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.Nullable;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxContainerDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxFrameDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxHtmlDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxImageDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxInputDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxLabelDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxScrollerDomNode;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxTextAreaDomNode;
import com.autonavi.minimap.ajx3.memory.MemoryTracker;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class JsDomNode extends JsDomNodeData {
    private long mId;
    private int mTag;
    private String mTagName;

    private native long[] nativeGetChildren(long j);

    private native long nativeGetNodeId(long j);

    private native int nativeGetTag(long j);

    private native String nativeGetTagName(long j);

    public /* bridge */ /* synthetic */ float[] dimensions() {
        return super.dimensions();
    }

    public /* bridge */ /* synthetic */ int getAttributeCount() {
        return super.getAttributeCount();
    }

    public /* bridge */ /* synthetic */ String getAttributeKey(int i) {
        return super.getAttributeKey(i);
    }

    public /* bridge */ /* synthetic */ String getAttributeValue(int i) {
        return super.getAttributeValue(i);
    }

    public /* bridge */ /* synthetic */ int getPropertyCount() {
        return super.getPropertyCount();
    }

    public /* bridge */ /* synthetic */ int getPropertyKey(int i) {
        return super.getPropertyKey(i);
    }

    public /* bridge */ /* synthetic */ int getPropertyStyle(int i) {
        return super.getPropertyStyle(i);
    }

    public /* bridge */ /* synthetic */ int getPropertyValueType(int i) {
        return super.getPropertyValueType(i);
    }

    public /* bridge */ /* synthetic */ float[] paddings() {
        return super.paddings();
    }

    public /* bridge */ /* synthetic */ boolean propertyBooleanValue(int i) {
        return super.propertyBooleanValue(i);
    }

    public /* bridge */ /* synthetic */ Object propertyFilterValue(int i) {
        return super.propertyFilterValue(i);
    }

    public /* bridge */ /* synthetic */ float[] propertyFloatArray(int i) {
        return super.propertyFloatArray(i);
    }

    public /* bridge */ /* synthetic */ float propertyFloatValue(int i) {
        return super.propertyFloatValue(i);
    }

    public /* bridge */ /* synthetic */ int[] propertyIntArray(int i) {
        return super.propertyIntArray(i);
    }

    public /* bridge */ /* synthetic */ int propertyIntValue(int i) {
        return super.propertyIntValue(i);
    }

    public /* bridge */ /* synthetic */ Object[] propertyObjArray(int i) {
        return super.propertyObjArray(i);
    }

    public /* bridge */ /* synthetic */ String propertyOriginValue(int i) {
        return super.propertyOriginValue(i);
    }

    public /* bridge */ /* synthetic */ String propertyStringValue(int i) {
        return super.propertyStringValue(i);
    }

    JsDomNode() {
    }

    JsDomNode(long j) {
        super(j);
        this.mId = nativeGetNodeId(this.mShadow);
        this.mTag = nativeGetTag(this.mShadow);
        this.mTagName = nativeGetTagName(this.mShadow);
        MemoryTracker.INSTANCE.track(this, j, 0);
    }

    public AjxDomNode createTemplateAjxNode() {
        AjxDomNode ajxDomNode;
        switch (this.mTag) {
            case Property.NODE_TYPE_FLEX /*1056964749*/:
            case Property.NODE_TYPE_CELL /*1056964759*/:
                ajxDomNode = new AjxContainerDomNode(this);
                break;
            case Property.NODE_TYPE_LABEL /*1056964750*/:
                ajxDomNode = new AjxLabelDomNode(this);
                break;
            case Property.NODE_TYPE_HTML /*1056964751*/:
                ajxDomNode = new AjxHtmlDomNode(this);
                break;
            case Property.NODE_TYPE_INPUT /*1056964752*/:
                ajxDomNode = new AjxInputDomNode(this);
                break;
            case Property.NODE_TYPE_TEXT_AREA /*1056964753*/:
                ajxDomNode = new AjxTextAreaDomNode(this);
                break;
            case Property.NODE_TYPE_IMAGE /*1056964754*/:
                ajxDomNode = new AjxImageDomNode(this);
                break;
            case Property.NODE_TYPE_SCROLLER /*1056964755*/:
                ajxDomNode = new AjxScrollerDomNode(this);
                break;
            case Property.NODE_TYPE_FRAME /*1056964756*/:
                ajxDomNode = new AjxFrameDomNode(this);
                break;
            case Property.NODE_TYPE_LIST /*1056964757*/:
                ajxDomNode = new AjxListDomNode(this);
                break;
            case Property.NODE_TYPE_WATERFALL /*1056964762*/:
                ajxDomNode = new AjxListDomNode(this, true);
                break;
            default:
                ajxDomNode = new AjxDomNode(this);
                break;
        }
        ajxDomNode.setIsTemplate();
        return ajxDomNode;
    }

    public AjxDomNode createAjxNode() {
        switch (this.mTag) {
            case Property.NODE_TYPE_FLEX /*1056964749*/:
            case Property.NODE_TYPE_CELL /*1056964759*/:
                return new AjxContainerDomNode(this);
            case Property.NODE_TYPE_LABEL /*1056964750*/:
                return new AjxLabelDomNode(this);
            case Property.NODE_TYPE_HTML /*1056964751*/:
                return new AjxHtmlDomNode(this);
            case Property.NODE_TYPE_INPUT /*1056964752*/:
                return new AjxInputDomNode(this);
            case Property.NODE_TYPE_TEXT_AREA /*1056964753*/:
                return new AjxTextAreaDomNode(this);
            case Property.NODE_TYPE_IMAGE /*1056964754*/:
                return new AjxImageDomNode(this);
            case Property.NODE_TYPE_SCROLLER /*1056964755*/:
                return new AjxScrollerDomNode(this);
            case Property.NODE_TYPE_FRAME /*1056964756*/:
                return new AjxFrameDomNode(this);
            case Property.NODE_TYPE_LIST /*1056964757*/:
                return new AjxListDomNode(this);
            case Property.NODE_TYPE_WATERFALL /*1056964762*/:
                return new AjxListDomNode(this, true);
            default:
                return new AjxDomNode(this);
        }
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

    public IJsDomData[] getChildren() {
        if (this.mChildren == null) {
            long[] nativeGetChildren = nativeGetChildren(this.mShadow);
            if (nativeGetChildren == null || nativeGetChildren.length <= 0) {
                return null;
            }
            JsDomNodeData[] jsDomNodeDataArr = new JsDomNodeData[nativeGetChildren.length];
            for (int i = 0; i < nativeGetChildren.length; i++) {
                jsDomNodeDataArr[i] = new JsDomNode(nativeGetChildren[i]);
            }
            this.mChildren = jsDomNodeDataArr;
        }
        return this.mChildren;
    }
}
