package com.alipay.android.phone.mobilecommon.multimedia.material;

import com.alibaba.fastjson.annotation.JSONField;

public class APFilterInfo {
    @JSONField(name = "filterId")
    public int filterId;
    @JSONField(name = "iconId")
    public String iconId;
    @JSONField(name = "shortCut")
    public String shortCut;

    public String toString() {
        return "APFilterInfo{filterId=" + this.filterId + ", iconId='" + this.iconId + '\'' + ", shortCut='" + this.shortCut + '\'' + '}';
    }
}
