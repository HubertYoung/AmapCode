package com.alipay.mobile.antui.api;

import com.alipay.mobile.antui.basic.AUViewInterface;

public interface AULineGroupItemInterface extends AUViewInterface {
    public static final int BOTTOM = 18;
    public static final int CENTER = 19;
    public static final int LINE = 20;
    public static final int NONE = 21;
    public static final int NORMAL = 16;
    public static final int TOP = 17;
    public static final int VISUAL_STYLE_LIST_ITEM = 17;
    public static final int VISUAL_STYLE_ROUND_CORNER = 16;

    void setItemPositionStyle(int i);

    void setVisualStyle(int i);
}
