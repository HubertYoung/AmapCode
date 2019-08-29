package com.ant.phone.xmedia.params;

import com.alibaba.fastjson.annotation.JSONField;

public class XMediaFilterItem {
    @JSONField(name = "bbox")
    public float[] mBoundingBox;
    @JSONField(name = "confid")
    public float mConfidence;
}
