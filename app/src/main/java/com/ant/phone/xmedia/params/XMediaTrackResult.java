package com.ant.phone.xmedia.params;

import com.alibaba.fastjson.annotation.JSONField;

public class XMediaTrackResult extends XMediaClassifyResult {
    @JSONField(name = "bbox")
    public BoundingBox mBoundingBox;
    @JSONField(name = "isLost")
    public int mIsLost;
}
