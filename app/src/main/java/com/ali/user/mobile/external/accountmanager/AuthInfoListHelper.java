package com.ali.user.mobile.external.accountmanager;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class AuthInfoListHelper {
    private static final String TAG = "AuthInfoListHelper";

    public static List<AuthInfo> getTesqtAuthInfoList() {
        return null;
    }

    public static List<AuthInfo> getAuthInfoList() {
        ArrayList arrayList = new ArrayList();
        try {
            String string = ((Bundle) ServiceExecutor.b("COMMONBIZ_SERVICE_LIST_BIZ", new Bundle())).getString("bizList");
            AliUserLog.c(TAG, "authJson: ".concat(String.valueOf(string)));
            if (!TextUtils.isEmpty(string)) {
                JSONArray jSONArray = new JSONArray(string);
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    AuthInfo authInfo = new AuthInfo();
                    authInfo.authServiceName = jSONObject.getString("name");
                    authInfo.isOpen = jSONObject.getBoolean("isOpen");
                    arrayList.add(authInfo);
                }
            }
            return arrayList;
        } catch (Throwable th) {
            AliUserLog.b(TAG, "getAuthInfoList error", th);
            return arrayList;
        }
    }
}
