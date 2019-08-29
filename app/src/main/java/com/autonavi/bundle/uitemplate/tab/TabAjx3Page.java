package com.autonavi.bundle.uitemplate.tab;

import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.minimap.ajx3.Ajx3Page;

public class TabAjx3Page extends Ajx3Page implements beo {
    protected ben a;

    public final boolean o() {
        return false;
    }

    public void s() {
    }

    public void t() {
    }

    public final void a(ben ben) {
        this.a = ben;
    }

    public void showViewLayer(IViewLayer iViewLayer) {
        if (this.a != null) {
            this.a.showViewLayer(iViewLayer);
        } else {
            super.showViewLayer(iViewLayer);
        }
    }

    public void dismissViewLayer(IViewLayer iViewLayer) {
        if (this.a != null) {
            this.a.dismissViewLayer(iViewLayer);
        } else {
            super.dismissViewLayer(iViewLayer);
        }
    }
}
