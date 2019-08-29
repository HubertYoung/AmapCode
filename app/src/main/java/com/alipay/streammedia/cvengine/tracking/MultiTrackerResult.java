package com.alipay.streammedia.cvengine.tracking;

import java.util.List;

public class MultiTrackerResult {
    public int[] DrawData;
    public List<TargetRectResult> objRects;
    public int retCode;

    public int getRetCode() {
        return this.retCode;
    }

    public List<TargetRectResult> getObjRects() {
        return this.objRects;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }

    public void setObjRects(List<TargetRectResult> objRects2) {
        this.objRects = objRects2;
    }
}
