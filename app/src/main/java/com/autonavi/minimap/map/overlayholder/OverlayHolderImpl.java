package com.autonavi.minimap.map.overlayholder;

import com.autonavi.minimap.base.overlay.SimpleOverlayHolder;

public class OverlayHolderImpl implements IOverlayHolder {
    public SimpleOverlayHolder simpleOverlayHolder;

    public OverlayHolderImpl(bty bty) {
        this.simpleOverlayHolder = new SimpleOverlayHolder(bty);
    }

    public void clearAndRemove() {
        if (this.simpleOverlayHolder != null) {
            this.simpleOverlayHolder.onDestroy();
        }
    }

    public void save() {
        this.simpleOverlayHolder.onSave();
    }

    public void restore() {
        this.simpleOverlayHolder.onRestore();
    }
}
