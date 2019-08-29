package com.autonavi.minimap.drive.errorreport.overlay;

import com.autonavi.minimap.base.overlay.PointOverlay;
import defpackage.dff;

public class ErrorReportPointOverlay<E extends dff> extends PointOverlay<E> {
    public ErrorReportPointOverlay(bty bty) {
        super(bty);
    }

    public void addItem(E e) {
        if (e != null) {
            e.a = this.mItemList.size();
            super.addItem(e);
        }
    }

    public E get(int i) {
        return (dff) getItem(i);
    }
}
