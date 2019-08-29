package com.autonavi.bundle.uitemplate.mapwidget.inter.layer;

import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;

public interface ILayerStateListener {
    Object dismissTips();

    boolean isTipsShown();

    boolean showTips(AmapMessage amapMessage);
}
