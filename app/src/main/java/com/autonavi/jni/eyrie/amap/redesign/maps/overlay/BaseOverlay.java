package com.autonavi.jni.eyrie.amap.redesign.maps.overlay;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseOverlay<T extends BaseOverlayItem> {
    private final ClickListenerWrapper mClickListenerWrapper;
    protected final List<T> mItemList;
    protected final Map<Integer, T> mItemMap;
    protected BaseLayer mLayer;
    protected final String mName;
    protected long mNativePtr = 0;

    public enum EOverlaySubType {
        ESubTypeMember(20180622);
        
        private int mValue;

        private EOverlaySubType(int i) {
            this.mValue = i;
        }

        public final int value() {
            return this.mValue;
        }
    }

    public enum OverlayType {
        Point(1),
        Line(2),
        Polygon(3),
        GPS(4);
        
        private final int mValue;

        private OverlayType(int i) {
            this.mValue = i;
        }

        public final int getValue() {
            return this.mValue;
        }
    }

    private static native void nativeClear(long j);

    private static native void nativeRefresh(long j);

    private static native void nativeSetClickListener(long j, ClickListenerWrapper clickListenerWrapper);

    private static native void nativeSetClickable(long j, boolean z);

    private static native void nativeSetPriority(long j, String str, String str2);

    private static native void nativeSetSubType(long j, int i);

    private static native void nativeSetVisible(long j, boolean z);

    /* access modifiers changed from: protected */
    public abstract void initOverlay();

    protected BaseOverlay(BaseLayer baseLayer, String str) {
        this.mLayer = baseLayer;
        this.mName = str;
        this.mItemMap = new Hashtable();
        this.mItemList = new ArrayList();
        initOverlay();
        this.mClickListenerWrapper = new ClickListenerWrapper(this);
        nativeSetClickListener(this.mNativePtr, this.mClickListenerWrapper);
    }

    public String getName() {
        return this.mName;
    }

    public void setClickListener(IClickListener iClickListener) {
        this.mClickListenerWrapper.setInnerListener(iClickListener);
    }

    @Deprecated
    public T getItem(int i) {
        if (i < this.mItemList.size()) {
            return (BaseOverlayItem) this.mItemList.get(i);
        }
        return null;
    }

    public T getItemById(int i) {
        return (BaseOverlayItem) this.mItemMap.get(Integer.valueOf(i));
    }

    public int getItemCount() {
        return this.mItemMap.size();
    }

    public Map<Integer, T> getItems() {
        return this.mItemMap;
    }

    public void clear() {
        this.mItemMap.clear();
        this.mItemList.clear();
        nativeClear(this.mNativePtr);
    }

    /* access modifiers changed from: 0000 */
    public void onDestroy() {
        nativeSetClickListener(this.mNativePtr, null);
        this.mClickListenerWrapper.release();
        this.mNativePtr = 0;
    }

    public void setVisible(boolean z) {
        nativeSetVisible(this.mNativePtr, z);
    }

    public void addItem(T t, boolean z) {
        this.mItemMap.put(Integer.valueOf(t.getID()), t);
        if (!this.mItemList.contains(t)) {
            this.mItemList.add(t);
        }
    }

    public void updateItem(T t, boolean z) {
        this.mItemMap.put(Integer.valueOf(t.getID()), t);
    }

    public void removeItem(int i, boolean z) {
        this.mItemMap.remove(Integer.valueOf(i));
        Iterator<T> it = this.mItemList.iterator();
        while (it.hasNext()) {
            it.next();
        }
    }

    public void removeItem(T t, boolean z) {
        removeItem(t.getID(), z);
        this.mItemList.remove(t);
    }

    public void addItem(T t) {
        addItem(t, true);
    }

    public void updateItem(T t) {
        updateItem(t, true);
    }

    public void removeItem(int i) {
        removeItem(i, true);
    }

    public void refresh() {
        nativeRefresh(this.mNativePtr);
    }

    public void setSubType(EOverlaySubType eOverlaySubType) {
        nativeSetSubType(this.mNativePtr, eOverlaySubType.value());
    }

    public void setClickable(boolean z) {
        nativeSetClickable(this.mNativePtr, z);
    }

    public void setPriority(String str, String str2) {
        nativeSetPriority(this.mNativePtr, str, str2);
    }
}
