package com.autonavi.map.fragmentcontainer.page;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.IViewLayer;

public abstract class AbstractBaseDialog implements IViewLayer {
    protected final bid mContext;

    public void onCreate(PageBundle pageBundle) {
    }

    public AbstractBaseDialog(bid bid) {
        this.mContext = bid;
    }

    public final void show() {
        this.mContext.showViewLayer(this);
    }

    public final void dismiss() {
        this.mContext.dismissViewLayer(this);
    }

    public final boolean isShowing() {
        return this.mContext.isViewLayerShowing(this);
    }
}
