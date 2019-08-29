package com.autonavi.minimap.ajx3.modules;

import android.text.TextUtils;
import android.util.Log;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleLocalStorage;
import com.autonavi.minimap.ajx3.util.LocalStorageHelper;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.localStorage")
public class ModuleAmapLocalStorage extends AjxModuleLocalStorage {
    private Map<String, AmapLocalStorageHelper> mCaches = new HashMap();

    public ModuleAmapLocalStorage(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setItemEncrypted")
    public void setItemEncrypted(String str, String str2, Object obj, JsFunctionCallback jsFunctionCallback) {
        boolean z = (TextUtils.isEmpty(str) ^ true) && getLocalStorageHelper(str).setItem(str2, obj);
        if (jsFunctionCallback != null) {
            Object[] objArr = new Object[1];
            if (!z) {
                str2 = null;
            }
            objArr[0] = str2;
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "setItemEncryptedSync")
    public boolean setItemEncryptedSync(String str, String str2, Object obj) {
        if (!(!TextUtils.isEmpty(str)) || !getLocalStorageHelper(str).setItem(str2, obj)) {
            return false;
        }
        return true;
    }

    @AjxMethod("getItemEncrypted")
    public void getItemEncrypted(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        boolean z = !TextUtils.isEmpty(str);
        if (jsFunctionCallback != null) {
            Object[] objArr = new Object[1];
            String str3 = null;
            if (z) {
                str3 = getLocalStorageHelper(str).getItem(str2, null);
            }
            objArr[0] = str3;
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getItemEncryptedSync")
    public String getItemEncryptedSync(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            return getLocalStorageHelper(str).getItem(str2, null);
        }
        return null;
    }

    @AjxMethod("getAllItemsEncrypted")
    public void getAllItemsEncrypted(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(getAllItems(str));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getAllItemsEncryptedSync")
    public JSONObject getAllItemsEncryptedSync(String str) {
        return getAllItems(str);
    }

    private JSONObject getAllItems(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Map<String, ?> allItems = getLocalStorageHelper(str).getAllItems();
        JSONObject jSONObject = new JSONObject();
        if (allItems != null && allItems.size() > 0) {
            try {
                for (String next : allItems.keySet()) {
                    jSONObject.put(next, LocalStorageHelper.valueToString(allItems.get(next)));
                }
            } catch (JSONException e) {
                StringBuilder sb = new StringBuilder("ajx.localStorage getAllItemsEncrypted error! stack:\n");
                sb.append(Log.getStackTraceString(e));
                LogHelper.e(sb.toString());
            }
        }
        return jSONObject;
    }

    @AjxMethod("removeItemEncrypted")
    public void removeItemEncrypted(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        boolean z = (TextUtils.isEmpty(str) ^ true) && getLocalStorageHelper(str).removeItem(str2);
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(z));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "removeItemEncryptedSync")
    public boolean removeItemEncryptedSync(String str, String str2) {
        if (!(!TextUtils.isEmpty(str)) || !getLocalStorageHelper(str).removeItem(str2)) {
            return false;
        }
        return true;
    }

    @AjxMethod("clearEncrypted")
    public void clearEncrypted(String str, JsFunctionCallback jsFunctionCallback) {
        boolean z = (TextUtils.isEmpty(str) ^ true) && getLocalStorageHelper(str).clear();
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(z));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "clearEncryptedSync")
    public boolean clearEncryptedSync(String str) {
        if (!(!TextUtils.isEmpty(str)) || !getLocalStorageHelper(str).clear()) {
            return false;
        }
        return true;
    }

    @AjxMethod("setDefaultItemsEncrypted")
    public void setDefaultItemsEncrypted(String str, String[] strArr, Object[] objArr, JsFunctionCallback jsFunctionCallback) {
        boolean z = (TextUtils.isEmpty(str) ^ true) && getLocalStorageHelper(str).setDefaultItems(strArr, objArr);
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(z));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "setDefaultItemsEncryptedSync")
    public boolean setDefaultItemsEncryptedSync(String str, String[] strArr, Object[] objArr) {
        if (!(!TextUtils.isEmpty(str)) || !getLocalStorageHelper(str).setDefaultItems(strArr, objArr)) {
            return false;
        }
        return true;
    }

    private AmapLocalStorageHelper getLocalStorageHelper(String str) {
        AmapLocalStorageHelper amapLocalStorageHelper = this.mCaches.get(str);
        if (amapLocalStorageHelper != null) {
            return amapLocalStorageHelper;
        }
        AmapLocalStorageHelper amapLocalStorageHelper2 = new AmapLocalStorageHelper(str);
        this.mCaches.put(str, amapLocalStorageHelper2);
        return amapLocalStorageHelper2;
    }
}
