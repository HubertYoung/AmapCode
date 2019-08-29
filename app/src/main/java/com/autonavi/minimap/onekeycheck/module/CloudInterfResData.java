package com.autonavi.minimap.onekeycheck.module;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.autonavi.minimap.onekeycheck.module.CloudInterfInfos.RequestUnit;
import java.util.HashMap;

public class CloudInterfResData extends ResultData {
    private Object mCurNode;
    private String mCurNodeName = "";
    private boolean mIsLastResponse;
    private String mParentNodeName;

    public CloudInterfResData() {
    }

    public CloudInterfResData(String str, RequestUnit requestUnit, String str2, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            this.mParentNodeName = str;
            if ("urls".equals(str)) {
                JSONObject jSONObject = new JSONObject();
                if (requestUnit != null) {
                    this.mCurNodeName = requestUnit.name;
                    jSONObject.put((String) "success", (Object) Boolean.valueOf(z));
                    jSONObject.put((String) "csid", (Object) requestUnit.csId);
                }
                jSONObject.put((String) "message", (Object) str2);
                this.mCurNode = jSONObject;
            } else if ("cdn".equals(str) && requestUnit != null) {
                HashMap hashMap = new HashMap();
                hashMap.put(requestUnit.url, str2);
                this.mCurNode = hashMap;
            }
        }
    }

    public void setIsLastResponse(boolean z) {
        this.mIsLastResponse = z;
    }

    public void setStatusCode(int i) {
        if (this.mCurNode != null && (this.mCurNode instanceof JSONObject)) {
            ((JSONObject) this.mCurNode).put((String) "status_code", (Object) String.valueOf(i));
        }
    }

    public Object getCurNode() {
        return this.mCurNode;
    }

    public String getTag() {
        return this.mParentNodeName;
    }

    public String getCurNodeName() {
        return this.mCurNodeName;
    }

    public boolean isLastResponse() {
        return this.mIsLastResponse;
    }
}
