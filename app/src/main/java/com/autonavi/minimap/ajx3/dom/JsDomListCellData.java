package com.autonavi.minimap.ajx3.dom;

import com.autonavi.minimap.ajx3.memory.MemoryTracker;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public final class JsDomListCellData extends JsDomNodeData {
    private long mCellNodeId;
    private int mDataIndex = Integer.MIN_VALUE;
    private long mNodeId;
    private int mSectionIndex = Integer.MIN_VALUE;

    private native long[] nativeGetChildren(long j);

    private native int[] nativeGetListIndexes(long j);

    private native long[] nativeGetNodeIds(long j);

    public final /* bridge */ /* synthetic */ float[] dimensions() {
        return super.dimensions();
    }

    public final /* bridge */ /* synthetic */ int getAttributeCount() {
        return super.getAttributeCount();
    }

    public final /* bridge */ /* synthetic */ String getAttributeKey(int i) {
        return super.getAttributeKey(i);
    }

    public final /* bridge */ /* synthetic */ String getAttributeValue(int i) {
        return super.getAttributeValue(i);
    }

    public final /* bridge */ /* synthetic */ int getPropertyCount() {
        return super.getPropertyCount();
    }

    public final /* bridge */ /* synthetic */ int getPropertyKey(int i) {
        return super.getPropertyKey(i);
    }

    public final /* bridge */ /* synthetic */ int getPropertyStyle(int i) {
        return super.getPropertyStyle(i);
    }

    public final /* bridge */ /* synthetic */ int getPropertyValueType(int i) {
        return super.getPropertyValueType(i);
    }

    public final /* bridge */ /* synthetic */ float[] paddings() {
        return super.paddings();
    }

    public final /* bridge */ /* synthetic */ boolean propertyBooleanValue(int i) {
        return super.propertyBooleanValue(i);
    }

    public final /* bridge */ /* synthetic */ Object propertyFilterValue(int i) {
        return super.propertyFilterValue(i);
    }

    public final /* bridge */ /* synthetic */ float[] propertyFloatArray(int i) {
        return super.propertyFloatArray(i);
    }

    public final /* bridge */ /* synthetic */ float propertyFloatValue(int i) {
        return super.propertyFloatValue(i);
    }

    public final /* bridge */ /* synthetic */ int[] propertyIntArray(int i) {
        return super.propertyIntArray(i);
    }

    public final /* bridge */ /* synthetic */ int propertyIntValue(int i) {
        return super.propertyIntValue(i);
    }

    public final /* bridge */ /* synthetic */ Object[] propertyObjArray(int i) {
        return super.propertyObjArray(i);
    }

    public final /* bridge */ /* synthetic */ String propertyOriginValue(int i) {
        return super.propertyOriginValue(i);
    }

    public final /* bridge */ /* synthetic */ String propertyStringValue(int i) {
        return super.propertyStringValue(i);
    }

    JsDomListCellData(long j) {
        super(j);
        initIndexs();
        initNodeIDs();
        MemoryTracker.INSTANCE.track(this, j, 1);
    }

    public final IJsDomData[] getChildren() {
        if (this.mChildren == null) {
            long[] nativeGetChildren = nativeGetChildren(this.mShadow);
            if (nativeGetChildren == null || nativeGetChildren.length <= 0) {
                return null;
            }
            JsDomListCellData[] jsDomListCellDataArr = new JsDomListCellData[nativeGetChildren.length];
            for (int i = 0; i < nativeGetChildren.length; i++) {
                jsDomListCellDataArr[i] = new JsDomListCellData(nativeGetChildren[i]);
            }
            this.mChildren = jsDomListCellDataArr;
        }
        return this.mChildren;
    }

    private void initIndexs() {
        int[] nativeGetListIndexes = nativeGetListIndexes(this.mShadow);
        if (nativeGetListIndexes != null && nativeGetListIndexes.length == 3) {
            this.mSectionIndex = nativeGetListIndexes[0];
            this.mDataIndex = nativeGetListIndexes[1];
        }
    }

    private void initNodeIDs() {
        long[] nativeGetNodeIds = nativeGetNodeIds(this.mShadow);
        if (nativeGetNodeIds != null && nativeGetNodeIds.length == 2) {
            this.mNodeId = nativeGetNodeIds[0];
            this.mCellNodeId = nativeGetNodeIds[1];
        }
    }

    public final long getNodeId() {
        return this.mNodeId;
    }

    public final long getCellNodeId() {
        return this.mCellNodeId;
    }

    public final int getSectionIndex() {
        return this.mSectionIndex;
    }

    public final int getDataIndex() {
        return this.mDataIndex;
    }

    public final float[] getSize() {
        return this.mDimensions;
    }
}
