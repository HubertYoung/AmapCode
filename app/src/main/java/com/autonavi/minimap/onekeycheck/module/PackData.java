package com.autonavi.minimap.onekeycheck.module;

import com.alibaba.fastjson.JSONObject;

public class PackData extends ResultData {
    private boolean mIsLastPack;
    private JSONObject mRootNode = new JSONObject();

    public JSONObject getNodeByKey(String str) {
        JSONObject jSONObject = this.mRootNode.getJSONObject(str);
        if (jSONObject != null) {
            return jSONObject;
        }
        JSONObject jSONObject2 = new JSONObject();
        this.mRootNode.put(str, (Object) jSONObject2);
        return jSONObject2;
    }

    public JSONObject getPackRootNode() {
        return this.mRootNode;
    }

    public void setPackFlag(boolean z) {
        this.mIsLastPack = z;
    }

    public boolean isLashPack() {
        return this.mIsLastPack;
    }
}
