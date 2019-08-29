package com.ant.phone.xmedia.params;

import com.alibaba.fastjson.annotation.JSONField;

public class XMediaClassifyResult extends XMediaResult {
    @JSONField(name = "conf")
    public float mConfidence;
    @JSONField(name = "label")
    public String mLabel;
}
