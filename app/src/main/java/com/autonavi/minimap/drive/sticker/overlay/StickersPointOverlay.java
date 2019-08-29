package com.autonavi.minimap.drive.sticker.overlay;

import com.autonavi.minimap.base.overlay.PointOverlay;
import defpackage.dja;

public class StickersPointOverlay<E extends dja> extends PointOverlay<E> {
    public StickersPointOverlay(bty bty) {
        super(bty);
    }

    public void addItem(E e) {
        e.a = this.mItemList.size();
        super.addItem(e);
    }
}
