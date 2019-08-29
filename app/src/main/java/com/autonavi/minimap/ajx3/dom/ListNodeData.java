package com.autonavi.minimap.ajx3.dom;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ListNodeData {
    private long mShadow;

    private native long nativeGetCell(long j, int i);

    private native int nativeGetCellCount(long j);

    private static native float nativeGetCellTotalHeight(long j, int i);

    private static native long nativeGetMergeData(long j, long j2);

    private static native int nativeGetNodeAttributeCount(long j);

    private static native String nativeGetNodeAttributeKey(long j, int i);

    private static native String nativeGetNodeAttributeValue(long j, int i);

    private static native long nativeGetNodeChildAt(long j, int i);

    private static native int nativeGetNodeChildCount(long j);

    private static native float[] nativeGetNodeDimensions(long j);

    private static native long nativeGetNodeId(long j);

    private static native boolean nativeGetNodePropertyBooleanValue(long j, boolean z, int i);

    private static native int nativeGetNodePropertyCount(long j, boolean z);

    private static native float[] nativeGetNodePropertyFloatArrayValue(long j, boolean z, int i);

    private static native float nativeGetNodePropertyFloatValue(long j, boolean z, int i);

    private static native int[] nativeGetNodePropertyIntArrayValue(long j, boolean z, int i);

    private static native int nativeGetNodePropertyIntValue(long j, boolean z, int i);

    private static native int nativeGetNodePropertyKey(long j, boolean z, int i);

    private static native Object[] nativeGetNodePropertyObjectArrayValue(long j, boolean z, int i);

    private static native String nativeGetNodePropertyStringValue(long j, boolean z, int i);

    private static native int nativeGetNodePropertyValueType(long j, boolean z, int i);

    private static native int nativeGetNodeTag(long j);

    private static native String nativeGetNodeTagName(long j);

    private static native long nativeGetNodeTemplate(long j, long j2);

    private static native long nativeGetNodeTemplateId(long j);

    private static native float[] nativeGetPadding(long j);

    private static native int nativeGetPositionIndex(long j, int i, int i2);

    private static native long nativeGetSectionFooterAtPosition(long j, int i);

    private static native int nativeGetSectionFooterIndex(long j, int i);

    private static native long nativeGetSectionHeaderAtPosition(long j, int i);

    private static native int nativeGetSectionHeaderIndex(long j, int i);

    private static native long nativeGetTemplateById(long j, long j2);

    private static native long nativeGetTemplateId(long j, int i);

    private static native void nativeReleaseNode(long j);

    public static native void nativeTest(long j);

    public native long nativeGetId(long j);

    public ListNodeData(long j) {
        this.mShadow = j;
    }

    public long getId() {
        return nativeGetId(this.mShadow);
    }

    public int getCellCount() {
        return nativeGetCellCount(this.mShadow);
    }

    public long getCell(int i) {
        return nativeGetCell(this.mShadow, i);
    }

    public long getTemplateId(int i) {
        return nativeGetTemplateId(this.mShadow, i);
    }

    public long getTemplateById(long j) {
        return nativeGetTemplateById(this.mShadow, j);
    }

    public long getMergeData(long j, long j2) {
        return nativeGetMergeData(j, j2);
    }

    public long getSectionHeaderAtPosition(int i) {
        return nativeGetSectionHeaderAtPosition(this.mShadow, i);
    }

    public long getSectionFooterAtPosition(int i) {
        return nativeGetSectionFooterAtPosition(this.mShadow, i);
    }

    public int getSectionHeaderIndex(int i) {
        return nativeGetSectionHeaderIndex(this.mShadow, i);
    }

    public int getSectionFooterIndex(int i) {
        return nativeGetSectionFooterIndex(this.mShadow, i);
    }

    public int getPositionIndex(int i, int i2) {
        return nativeGetPositionIndex(this.mShadow, i, i2);
    }

    public float getCellTotalHeight(int i) {
        return nativeGetCellTotalHeight(this.mShadow, i);
    }

    public static long getNodeId(long j) {
        return nativeGetNodeId(j);
    }

    public long getNodeTemplate(long j) {
        return nativeGetNodeTemplate(this.mShadow, j);
    }

    public static long getNodeTemplateId(long j) {
        return nativeGetNodeTemplateId(j);
    }

    public static int getNodeTag(long j) {
        return nativeGetNodeTag(j);
    }

    public static String getNodeTagName(long j) {
        return nativeGetNodeTagName(j);
    }

    public static float[] getNodeDimension(long j) {
        return nativeGetNodeDimensions(j);
    }

    public static float[] getNodePadding(long j) {
        return nativeGetPadding(j);
    }

    public static int getNodeChildCount(long j) {
        return nativeGetNodeChildCount(j);
    }

    public static long getNodeChildAt(long j, int i) {
        return nativeGetNodeChildAt(j, i);
    }

    public static void releaseNode(long j) {
        nativeReleaseNode(j);
    }

    public static int getNodeAttrCount(long j) {
        return nativeGetNodeAttributeCount(j);
    }

    public static String getNodeAttrKey(long j, int i) {
        return nativeGetNodeAttributeKey(j, i);
    }

    public static String getNodeAttrValue(long j, int i) {
        return nativeGetNodeAttributeValue(j, i);
    }

    public static int getNodePropCount(long j, boolean z) {
        return nativeGetNodePropertyCount(j, z);
    }

    public static int getNodePropKey(long j, boolean z, int i) {
        return nativeGetNodePropertyKey(j, z, i);
    }

    public static int getNodePropValueType(long j, boolean z, int i) {
        return nativeGetNodePropertyValueType(j, z, i);
    }

    public static boolean getNodePropBoolValue(long j, boolean z, int i) {
        return nativeGetNodePropertyBooleanValue(j, z, i);
    }

    public static int getNodePropIntValue(long j, boolean z, int i) {
        return nativeGetNodePropertyIntValue(j, z, i);
    }

    public static float getNodePropFloatValue(long j, boolean z, int i) {
        return nativeGetNodePropertyFloatValue(j, z, i);
    }

    public static String getNodePropStrValue(long j, boolean z, int i) {
        return nativeGetNodePropertyStringValue(j, z, i);
    }

    public static int[] getNodePropIntArray(long j, boolean z, int i) {
        return nativeGetNodePropertyIntArrayValue(j, z, i);
    }

    public static float[] getNodePropFloatArray(long j, boolean z, int i) {
        return nativeGetNodePropertyFloatArrayValue(j, z, i);
    }

    public static Object[] getNodePropObjArray(long j, boolean z, int i) {
        return nativeGetNodePropertyObjectArrayValue(j, z, i);
    }
}
