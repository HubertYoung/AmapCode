package com.alipay.mobile.beehive.capture.modle;

import com.alipay.android.phone.mobilecommon.multimedia.material.APFilterInfo;

public class Filter {
    public String desc;
    public String filterIcon;
    public int filterId;
    public boolean isSelected;

    public Filter(APFilterInfo info) {
        this.filterIcon = info.iconId;
        this.filterId = info.filterId;
        this.desc = info.shortCut;
    }
}
