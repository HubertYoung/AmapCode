package com.alipay.android.phone.mobilecommon.multimedia.material;

import com.alibaba.fastjson.annotation.JSONField;

public class APMaterialInfo {
    @JSONField(name = "iconId")
    public String iconId = "";
    @JSONField(name = "materialId")
    public String materialId = "";
    @JSONField(serialize = false)
    public String materialPath;
    @JSONField(name = "md5")
    public String md5;
    @JSONField(name = "seq")
    public String seq;
    @JSONField(name = "shortCut")
    public String shortCut = "";
    @JSONField(name = "type")
    public int type = 1;

    public String toString() {
        return "APMaterialInfo{seq='" + this.seq + '\'' + ", materialId='" + this.materialId + '\'' + ", iconId='" + this.iconId + '\'' + ", shortCut='" + this.shortCut + '\'' + ", type=" + this.type + ", materialPath='" + this.materialPath + '\'' + ", md5='" + this.md5 + '\'' + '}';
    }
}
