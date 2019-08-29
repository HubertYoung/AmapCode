package com.alipay.android.phone.mobilecommon.multimedia.material;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public class APPackageInfo {
    @JSONField(name = "cloudId")
    public String cloudId;
    @JSONField(name = "iconId")
    public String iconId;
    @JSONField(name = "materials")
    public List<APMaterialInfo> mMaterialInfos;
    @JSONField(name = "packageId")
    public String packageId;
    @JSONField(name = "selectedIconId")
    public String selectedIconId;
    @JSONField(name = "shortCut")
    public String shortCut;

    public String toString() {
        return "APPackageInfo{packageId='" + this.packageId + '\'' + ", cloudId='" + this.cloudId + '\'' + ", shortCut='" + this.shortCut + '\'' + ", iconId='" + this.iconId + '\'' + ", selectedIconId='" + this.selectedIconId + '\'' + ", mMaterialInfos=" + this.mMaterialInfos + '}';
    }
}
