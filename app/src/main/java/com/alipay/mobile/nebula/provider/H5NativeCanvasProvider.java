package com.alipay.mobile.nebula.provider;

import com.alibaba.fastjson.JSONObject;

public interface H5NativeCanvasProvider {

    public interface H5CanvasCallback {
        void onSaveFinished(JSONObject jSONObject);
    }

    void saveTempData(byte[] bArr, String str, String str2, String str3, H5CanvasCallback h5CanvasCallback);
}
