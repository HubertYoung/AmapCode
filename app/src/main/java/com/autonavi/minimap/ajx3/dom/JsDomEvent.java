package com.autonavi.minimap.ajx3.dom;

import com.autonavi.minimap.ajx3.analyzer.AjxAnalyzerEvent;
import com.autonavi.minimap.ajx3.memory.MemoryTracker;

public class JsDomEvent {
    public static final int EVENT_TYPE_ANIMATION_CANCEL = 32;
    public static final int EVENT_TYPE_ANIMATION_CLEAR = 33;
    public static final int EVENT_TYPE_ANIMATION_FINISH = 30;
    public static final int EVENT_TYPE_ANIMATION_FORBID_EVENT = 34;
    public static final int EVENT_TYPE_ANIMATION_NEW = 27;
    public static final int EVENT_TYPE_ANIMATION_PARALLEL = 36;
    public static final int EVENT_TYPE_ANIMATION_PAUSE = 29;
    public static final int EVENT_TYPE_ANIMATION_PLAY = 28;
    public static final int EVENT_TYPE_ANIMATION_REVERSE = 31;
    public static final int EVENT_TYPE_ANIMATION_SERIAL = 35;
    public static final int EVENT_TYPE_ATTRIBUTE_ADD = 10;
    public static final int EVENT_TYPE_ATTRIBUTE_REMOVE = 11;
    public static final int EVENT_TYPE_FRAME_INIT = 26;
    public static final int EVENT_TYPE_LIST_CELL_GROUP_CHANGED = 42;
    public static final int EVENT_TYPE_LIST_DATA_ADD = 16;
    public static final int EVENT_TYPE_LIST_DATA_ATTRIBUTE_ADD = 22;
    public static final int EVENT_TYPE_LIST_DATA_ATTRIBUTE_REMOVE = 23;
    public static final int EVENT_TYPE_LIST_DATA_PROPERTY_ADD = 20;
    public static final int EVENT_TYPE_LIST_DATA_PROPERTY_REMOVE = 21;
    public static final int EVENT_TYPE_LIST_DATA_REMOVE = 17;
    public static final int EVENT_TYPE_LIST_DATA_REPLACE = 18;
    public static final int EVENT_TYPE_LIST_DATA_SIZE_CHANGE = 19;
    public static final int EVENT_TYPE_LIST_INIT = 12;
    public static final int EVENT_TYPE_LIST_SECTION_ADD = 13;
    public static final int EVENT_TYPE_LIST_SECTION_REMOVE = 14;
    public static final int EVENT_TYPE_LIST_SECTION_REPLACE = 15;
    public static final int EVENT_TYPE_LIST_TEMPLATE_ADD = 24;
    public static final int EVENT_TYPE_LIST_TEMPLATE_ATTRIBUTE_ADD = 45;
    public static final int EVENT_TYPE_LIST_TEMPLATE_ATTRIBUTE_REMOVE = 46;
    public static final int EVENT_TYPE_LIST_TEMPLATE_PROPERTY_ADD = 43;
    public static final int EVENT_TYPE_LIST_TEMPLATE_PROPERTY_REMOVE = 44;
    public static final int EVENT_TYPE_NODE_ADD = 2;
    public static final int EVENT_TYPE_NODE_FULL = 1;
    public static final int EVENT_TYPE_NODE_INSERT = 3;
    public static final int EVENT_TYPE_NODE_REMOVE = 5;
    public static final int EVENT_TYPE_NODE_REPLACE = 4;
    public static final int EVENT_TYPE_NODE_SIZE_CHANGE = 6;
    public static final int EVENT_TYPE_NODE_SNAPSHOT = 7;
    public static final int EVENT_TYPE_NODE_UNKNOW = 0;
    public static final int EVENT_TYPE_PROPERTY_ADD = 8;
    public static final int EVENT_TYPE_PROPERTY_REMOVE = 9;
    public static final int EVENT_TYPE_RELATIVE_ANIMATION_ADD_OBSERVER = 38;
    public static final int EVENT_TYPE_RELATIVE_ANIMATION_BIND_TARGET = 37;
    public static final int EVENT_TYPE_RELATIVE_ANIMATION_CLEAR = 40;
    public static final int EVENT_TYPE_RELATIVE_ANIMATION_REMOVE_BY_NODE = 39;
    public static final int EVENT_TYPE_SCROLLER_INIT = 25;
    public static final int EVENT_TYPE_SCROLL_INTO_VIEW = 41;
    private AjxAnalyzerEvent mAjxAnalyzerEvent;
    public final long nextPtr;
    public final int type;

    private native long nativeGetNext(long j);

    private static native int nativeGetType(long j);

    public static JsDomEvent create(long j) {
        int nativeGetType = nativeGetType(j);
        switch (nativeGetType) {
            case 1:
                return new JsDomEventFull(nativeGetType, j);
            case 2:
                return new JsDomEventNodeAdd(nativeGetType, j);
            case 3:
                return new JsDomEventNodeInsert(nativeGetType, j);
            case 4:
                return new JsDomEventNodeReplace(nativeGetType, j);
            case 5:
                return new JsDomEventNodeRemove(nativeGetType, j);
            case 6:
                return new JsDomEventNodeSizeChange(nativeGetType, j);
            case 7:
                return new JsDomEventNodeSnapshot(nativeGetType, j);
            case 8:
            case 9:
                return new JsDomEventNodeProperty(nativeGetType, j);
            case 10:
            case 11:
                return new JsDomEventNodeAttribute(nativeGetType, j);
            case 12:
                return new JsDomEventListInit(nativeGetType, j);
            case 13:
            case 14:
            case 15:
                return new JsDomEventListSection(nativeGetType, j);
            case 16:
            case 17:
            case 18:
                return new JsDomEventListCellData(nativeGetType, j);
            case 19:
                return new JsDomEventListDataSizeChange(nativeGetType, j);
            case 20:
            case 21:
                return new JsDomEventListDataProperty(nativeGetType, j);
            case 22:
            case 23:
                return new JsDomEventListDataAttribute(nativeGetType, j);
            case 24:
                return new JsDomEventListTemplateAdd(nativeGetType, j);
            case 25:
                return new JsDomEventScrollerInit(nativeGetType, j);
            case 26:
                return new JsDomEventFrameInit(nativeGetType, j);
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                return new JsDomEventAnimation(nativeGetType, j);
            case 35:
            case 36:
                return new JsDomEventAnimationGroup(nativeGetType, j);
            case 37:
            case 38:
            case 39:
            case 40:
                return new JsDomEventRelativeAnimation(nativeGetType, j);
            case 41:
                return new JsDomEventScrollIntoView(nativeGetType, j);
            case 43:
            case 44:
                return new JsDomEventListTemplateProperty(nativeGetType, j);
            case 45:
            case 46:
                return new JsDomEventListTemplateAttribute(nativeGetType, j);
            default:
                return null;
        }
    }

    JsDomEvent(int i, long j) {
        MemoryTracker.INSTANCE.track(this, j, 2);
        this.type = i;
        this.nextPtr = nativeGetNext(j);
    }

    public JsDomEvent getNext() {
        if (this.nextPtr != 0) {
            return create(this.nextPtr);
        }
        return null;
    }

    public void createAnalyzerEvent(int i, long j, long j2) {
        this.mAjxAnalyzerEvent = new AjxAnalyzerEvent();
        this.mAjxAnalyzerEvent.batch = i;
        this.mAjxAnalyzerEvent.arriveTime = j;
        this.mAjxAnalyzerEvent.lastArriveTime = j2;
        this.mAjxAnalyzerEvent.beginTime = System.currentTimeMillis();
    }

    public AjxAnalyzerEvent getAjxAnalyzerEvent() {
        if (this.mAjxAnalyzerEvent == null) {
            this.mAjxAnalyzerEvent = new AjxAnalyzerEvent();
        }
        return this.mAjxAnalyzerEvent;
    }
}
