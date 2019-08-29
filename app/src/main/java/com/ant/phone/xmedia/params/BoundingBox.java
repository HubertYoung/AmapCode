package com.ant.phone.xmedia.params;

import com.alibaba.fastjson.annotation.JSONField;

public class BoundingBox {
    @JSONField(name = "height")
    public float mHeight;
    @JSONField(name = "left")
    public float mLeft;
    @JSONField(name = "top")
    public float mTop;
    @JSONField(name = "width")
    public float mWidth;

    public BoundingBox(float l, float t, float w, float h) {
        this.mLeft = l;
        this.mTop = t;
        this.mWidth = w;
        this.mHeight = h;
    }

    public String toString() {
        return "BoundingBox:[left:" + this.mLeft + ",top:" + this.mTop + ",width:" + this.mWidth + ",height:" + this.mHeight + "]";
    }
}
