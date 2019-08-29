package com.autonavi.minimap.onekeycheck.module;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class UploadDataResult extends ResultData {
    public static final String FAIL_MSG = "fail";
    public static final String SUCCESS_MSG = "success";
    public static final int SUCCSS_CODE = 1;
    private Map<Integer, String> mDataUploadReqMap = new HashMap();

    public void addPackageState(int i, String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mDataUploadReqMap.put(Integer.valueOf(i), str);
        }
    }

    public String getPackageState() {
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        for (Entry next : this.mDataUploadReqMap.entrySet()) {
            if (!"success".equalsIgnoreCase((String) next.getValue())) {
                z = false;
                stringBuffer.append("package");
                stringBuffer.append(next.getKey());
                stringBuffer.append("-fail");
            }
        }
        if (z) {
            return "success";
        }
        return stringBuffer.toString();
    }
}
