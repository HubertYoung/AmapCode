package com.amap.bundle.drivecommon.overlay;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.MeasureSpec;
import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointItem;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.List;

public abstract class DriveBaseBoardPointOverlay<E extends DriveBaseBoardPointItem> extends DriveBasePointOverlay<E> {
    public static final int COLLIDE_AVOID_RECT = 4;
    public static final int COLLIDE_AVOID_SCREEN = 2;
    public static final int COLLIDE_HIDE = 1;
    public static final int STATE_HIDE_WHEN_COLLIDE = 7;
    public static final int STATE_HOLD_WHEN_COLLIDE = 6;
    private transient a mItemChangedListener;

    public enum BUBBLE_STYLE {
        RIGHT_TOP,
        LEFT_TOP,
        LEFT_BOTTOM,
        RIGHT_BOTTOM
    }

    public interface a {
    }

    public abstract int getCollideType();

    public boolean isCalcingOverlay() {
        return true;
    }

    public boolean isForceUpdateBoard() {
        return false;
    }

    public boolean isReversedCalcing() {
        return false;
    }

    public void onUpdate(int[] iArr, int i) {
    }

    public abstract void onUpdateDirection(int i, int i2);

    public DriveBaseBoardPointOverlay(bty bty) {
        super(bty);
    }

    public DriveBaseBoardPointOverlay(int i, bty bty) {
        super(i, bty);
    }

    public void addItem(E e) {
        addItem(e, true);
    }

    private void addItem(E e, boolean z) {
        e.e = this.mMapView;
        setPointItemVisble((PointOverlayItem) e, !isCalcingOverlay(), !isCalcingOverlay());
        super.addItem(e);
        if (this.mItemChangedListener != null && z) {
            hashCode();
            isForceUpdateBoard();
        }
    }

    public void addItem(@NonNull List<E> list) {
        if (list != null && list.size() > 0) {
            for (E e : list) {
                addItem(e, false);
                setPointItemVisble((PointOverlayItem) e, !isCalcingOverlay(), !isCalcingOverlay());
            }
            if (this.mItemChangedListener != null && !list.isEmpty()) {
                hashCode();
                isForceUpdateBoard();
            }
        }
    }

    public void addItemInverse(@NonNull List<? extends E> list) {
        if (!list.isEmpty()) {
            for (int size = list.size() - 1; size >= 0; size--) {
                DriveBaseBoardPointItem driveBaseBoardPointItem = (DriveBaseBoardPointItem) list.get(size);
                addItem((E) driveBaseBoardPointItem);
                setPointItemVisble((PointOverlayItem) driveBaseBoardPointItem, !isCalcingOverlay(), !isCalcingOverlay());
            }
            if (this.mItemChangedListener != null && !list.isEmpty()) {
                hashCode();
                isForceUpdateBoard();
            }
        }
    }

    public boolean clear() {
        List items = getItems();
        if (items == null || items.size() <= 0) {
            return super.clear();
        }
        boolean clear = super.clear();
        if (this.mItemChangedListener != null && clear) {
            hashCode();
            isForceUpdateBoard();
        }
        return clear;
    }

    public void removeItem(int i) {
        super.removeItem(i);
        if (this.mItemChangedListener != null) {
            hashCode();
            isForceUpdateBoard();
        }
    }

    public void removeItem(int i, boolean z) {
        super.removeItem(i);
        if (this.mItemChangedListener != null) {
            hashCode();
        }
    }

    public void removeItem(E e, boolean z) {
        super.removeItem((Object) e);
        if (this.mItemChangedListener != null) {
            hashCode();
        }
    }

    public void setItemChangedListener(a aVar) {
        this.mItemChangedListener = aVar;
    }

    public Rect[][] getVisibleRect() {
        int size = this.mItemList.size();
        Rect[][] rectArr = new Rect[size][];
        for (int i = 0; i < size; i++) {
            if (((DriveBaseBoardPointItem) this.mItemList.get(i)).d) {
                rectArr[i] = ((DriveBaseBoardPointItem) this.mItemList.get(i)).a();
            } else {
                rectArr[i] = new Rect[]{new Rect(-1000, -1000, -1000, -1000)};
            }
        }
        return rectArr;
    }

    public Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }
}
