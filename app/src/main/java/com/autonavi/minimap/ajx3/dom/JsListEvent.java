package com.autonavi.minimap.ajx3.dom;

public final class JsListEvent implements IJsListEventData {
    public static final int EVENT_TYPE_LIST_CELL_ADD = 4;
    public static final int EVENT_TYPE_LIST_CELL_REMOVE = 5;
    public static final int EVENT_TYPE_LIST_CELL_REPLACE = 6;
    public static final int EVENT_TYPE_LIST_DATA_ATTRIBUTE_ADD = 10;
    public static final int EVENT_TYPE_LIST_DATA_ATTRIBUTE_REMOVE = 11;
    public static final int EVENT_TYPE_LIST_DATA_PROPERTY_ADD = 8;
    public static final int EVENT_TYPE_LIST_DATA_PROPERTY_REMOVE = 9;
    public static final int EVENT_TYPE_LIST_DATA_SIZE_CHANGE = 7;
    public static final int EVENT_TYPE_LIST_DATA_TEMPLATE_CHANGE = 12;
    public static final int EVENT_TYPE_LIST_INIT = 0;
    public static final int EVENT_TYPE_LIST_SECTION_ADD = 1;
    public static final int EVENT_TYPE_LIST_SECTION_REMOVE = 2;
    public static final int EVENT_TYPE_LIST_SECTION_REPLACE = 3;
    public static final int EVENT_TYPE_LIST_UN_KNOW = -1;
    public static final int EVENT_TYPE_SCROLL_INTO_VIEW = 13;
    private int mCellIndex;
    private int mEventType;
    private int mListPosition;
    private long mPtrListData;
    private long mPtrNextEvent;
    private long mScrollIntoViewPtr;
    private int mSectionIndex;

    private native int nativeGetCellIndex(long j);

    private native int nativeGetEventType(long j);

    private native long nativeGetJsDomScrollIntoView(long j);

    private native int nativeGetListPosition(long j);

    private native long nativeGetPtrListData(long j);

    private native long nativeGetPtrNextEvent(long j);

    private native int nativeGetSectionIndex(long j);

    private native void nativeReleaseSelf(long j);

    public JsListEvent(long j) {
        this.mSectionIndex = nativeGetSectionIndex(j);
        this.mCellIndex = nativeGetCellIndex(j);
        this.mListPosition = nativeGetListPosition(j);
        this.mPtrNextEvent = nativeGetPtrNextEvent(j);
        this.mEventType = nativeGetEventType(j);
        this.mPtrListData = nativeGetPtrListData(j);
        this.mScrollIntoViewPtr = nativeGetJsDomScrollIntoView(j);
        nativeReleaseSelf(j);
    }

    public final int getSectionIndex() {
        return this.mSectionIndex;
    }

    public final int getCellIndex() {
        return this.mCellIndex;
    }

    public final int getListPosition() {
        return this.mListPosition;
    }

    public final long getPtrNextEvent() {
        return this.mPtrNextEvent;
    }

    public final int getEventType() {
        return this.mEventType;
    }

    public final long getPtrListData() {
        return this.mPtrListData;
    }

    public final JsDomScrollIntoView getJsDomScrollIntoView() {
        if (this.mScrollIntoViewPtr != 0) {
            return new JsDomScrollIntoView(this.mScrollIntoViewPtr);
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{EventType:");
        sb.append(getEventType());
        sb.append("},");
        sb.append("{SectionIndex:");
        sb.append(getSectionIndex());
        sb.append("},");
        sb.append("{CellIndex:");
        sb.append(getCellIndex());
        sb.append("},");
        sb.append("{ListPosition:");
        sb.append(getListPosition());
        sb.append("},");
        sb.append("{PtrNextEvent:");
        sb.append(getPtrNextEvent());
        sb.append("},");
        sb.append("{PtrListData:");
        sb.append(getPtrListData());
        sb.append("},");
        return sb.toString();
    }
}
