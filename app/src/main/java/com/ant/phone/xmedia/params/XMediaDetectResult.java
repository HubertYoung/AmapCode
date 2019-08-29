package com.ant.phone.xmedia.params;

import com.alibaba.fastjson.annotation.JSONField;

public class XMediaDetectResult extends XMediaClassifyResult {
    @JSONField(name = "bbox")
    public BoundingBox mBoundingBox;
}
