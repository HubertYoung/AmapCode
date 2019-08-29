package com.autonavi.minimap.ajx3.platform.impl;

import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.IComponentMeasurement;
import com.autonavi.minimap.ajx3.widget.AjxViewSizeProvider;

public class ComponentMeasurement implements IComponentMeasurement {
    public float[] measure(String str, String str2) {
        return AjxViewSizeProvider.requestViewSize(str, str2);
    }
}
