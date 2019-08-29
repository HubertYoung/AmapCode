package com.alipay.mobile.tinyappcustom.permission;

import android.text.TextUtils;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.tinypermission.H5ApiManagerImpl;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: MiniProgramSettingServiceImpl */
public final class d extends b {
    private Map<String, String> a = new HashMap();

    public d() {
        this.a.put("location", "location");
        this.a.put(WalletTinyappUtils.CONST_SCOPE_RECORD, WalletTinyappUtils.CONST_SCOPE_RECORD);
        this.a.put("album", "album");
        this.a.put(WalletTinyappUtils.CONST_SCOPE_CAMERA, WalletTinyappUtils.CONST_SCOPE_CAMERA);
    }

    public final Map<String, Boolean> a(String appId, String userId) {
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null) {
            H5Log.d("MiniProgramSettingService", "[getAllSettings] h5TinyAppService is null");
            return Collections.EMPTY_MAP;
        }
        Map res = ((H5ApiManagerImpl) h5TinyAppService).getAllPermissions(userId, appId);
        H5Log.d("MiniProgramSettingService", "[getAllSettings] appId = " + appId + ", userId = " + userId + ",  res = " + (res != null ? res.toString() : " is null."));
        return res;
    }

    public final boolean a(String appId, String userId, String settingKey, boolean switchValue) {
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null) {
            H5Log.d("MiniProgramSettingService", "[getAllSettings] h5TinyAppService is null");
            return false;
        }
        String permissionKey = this.a.get(settingKey);
        if (TextUtils.isEmpty(permissionKey)) {
            H5Log.w("MiniProgramSettingService", "[updateSetting] Can't find permission key,  unknown settingKey = " + settingKey);
            return false;
        }
        ((H5ApiManagerImpl) h5TinyAppService).changePermissionByKey(userId, appId, permissionKey, switchValue);
        H5Log.d("MiniProgramSettingService", "[updateSetting] Finished. settingKey = " + settingKey + ", permissionKey = " + permissionKey + ", switchValue = " + switchValue);
        return true;
    }
}
