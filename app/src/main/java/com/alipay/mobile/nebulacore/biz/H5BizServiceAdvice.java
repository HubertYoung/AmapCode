package com.alipay.mobile.nebulacore.biz;

import android.util.Pair;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.ui.H5Activity;

class H5BizServiceAdvice implements Advice {
    private String a;

    H5BizServiceAdvice(String key) {
        this.a = key;
    }

    public void onCallBefore(String pointCut, Object thisPoint, Object[] args) {
        H5Log.d("H5BizServiceAdvice", "onCallBefore: " + pointCut + ", thisPoint: " + thisPoint);
    }

    public Pair<Boolean, Object> onCallAround(String pointCut, Object thisPoint, Object[] args) {
        H5Log.d("H5BizServiceAdvice", "onCallAround: " + pointCut + ", thisPoint: " + thisPoint);
        return null;
    }

    public void onCallAfter(String pointCut, Object thisPoint, Object[] args) {
        H5Log.d("H5BizServiceAdvice", "onCallAfter: " + pointCut + ", thisPoint: " + thisPoint);
    }

    public void onExecutionBefore(String pointCut, Object thisPoint, Object[] args) {
        H5Log.d("H5BizServiceAdvice", "onExecutionBefore: " + pointCut + ", thisPoint: " + thisPoint);
    }

    public Pair<Boolean, Object> onExecutionAround(String pointCut, Object thisPoint, Object[] args) {
        H5Log.d("H5BizServiceAdvice", "onExecutionAround: " + pointCut + ", thisPoint: " + thisPoint);
        return null;
    }

    public void onExecutionAfter(String pointCut, Object thisPoint, Object[] args) {
        H5Log.d("H5BizServiceAdvice", "onExecutionAfter: " + pointCut + ", thisPoint: " + thisPoint);
        if (thisPoint instanceof H5Activity) {
            H5Activity activity = (H5Activity) thisPoint;
            String appId = activity.getActivityApplication().getAppId();
            String key = appId + activity.getActivityApplication().getSourceId();
            if (key.equals(this.a)) {
                H5Log.d("H5BizServiceAdvice", "result poped: " + key);
                H5BizUtil.b(this.a);
            }
        }
    }
}
