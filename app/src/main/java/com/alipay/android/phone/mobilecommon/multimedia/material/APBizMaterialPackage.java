package com.alipay.android.phone.mobilecommon.multimedia.material;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public class APBizMaterialPackage {
    @JSONField(name = "business")
    public APBusinessInfo bizInfo;
    @JSONField(name = "packages")
    public List<APPackageInfo> mPackageInfos;

    public String toString() {
        return "APBizMaterialPackage{bizInfo=" + this.bizInfo + ", mPackageInfos=" + this.mPackageInfos + '}';
    }
}
