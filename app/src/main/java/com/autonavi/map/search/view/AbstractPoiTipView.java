package com.autonavi.map.search.view;

import android.content.Context;
import android.view.ViewGroup;

public abstract class AbstractPoiTipView extends AbstractPoiView {
    public static final int TIP_ADDRESS = 2009;
    public static final int TIP_BTN1 = 1003;
    public static final int TIP_BTN2 = 2003;
    public static final int TIP_BTN3 = 1005;
    public static final int TIP_BTN4 = 1012;
    public static final int TIP_DEEP_INFO = 2010;
    public static final int TIP_DISTANT = 1014;
    public static final int TIP_EXTICON = 1013;
    public static final int TIP_ICON = 2005;
    public static final int TIP_POIDETAIL = 1002;
    public static final int TIP_POINAME = 2001;
    public static final int TIP_PRICE = 1009;
    public static final int TIP_RATE = 2006;
    public static final int TIP_TAG = 1008;

    public void adjustMargin() {
    }

    public void refreshByScreenState(boolean z) {
    }

    public void setAddressTip(String str) {
    }

    public AbstractPoiTipView(ViewGroup viewGroup, Context context) {
        super(context);
        this.parent = viewGroup;
    }
}
