package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.text.TextUtils;
import android.util.Log;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.LocalStorageHelper;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.localStorage")
public class AjxModuleLocalStorage extends AbstractModule {
    public static final String MODULE_NAME = "ajx.localStorage";
    private static OnOpListener onOpListener;
    private Map<String, LocalStorageHelper> mCaches = new HashMap();

    public interface OnOpListener {
        void onDomStorageItemAdded(String str, String str2, String str3);

        void onDomStorageItemRemoved(String str, String str2);

        void onDomStorageItemUpdated(String str, String str2, String str3, String str4);

        void onNamespaceAdd(String str);
    }

    public AjxModuleLocalStorage(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setItem")
    public void setItem(String str, String str2, Object obj, JsFunctionCallback jsFunctionCallback) {
        boolean item = setItem(str, str2, obj);
        if (jsFunctionCallback != null) {
            Object[] objArr = new Object[1];
            if (!item) {
                str2 = null;
            }
            objArr[0] = str2;
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "setItemSync")
    public boolean setItemSync(String str, String str2, Object obj) {
        setItem(str, str2, obj);
        return setItem(str, str2, obj);
    }

    private boolean setItem(String str, String str2, Object obj) {
        boolean z = !TextUtils.isEmpty(str);
        if (!z) {
            return z;
        }
        boolean preferenceExist = preferenceExist(str);
        LocalStorageHelper localStorageHelper = getLocalStorageHelper(str);
        boolean contains = localStorageHelper.contains(str2);
        String item = contains ? localStorageHelper.getItem(str2, "") : null;
        boolean item2 = localStorageHelper.setItem(str2, obj);
        if (onOpListener != null) {
            if (!preferenceExist) {
                onOpListener.onNamespaceAdd(str);
            }
            if (item2) {
                String valueToString = LocalStorageHelper.valueToString(obj);
                if (contains) {
                    onOpListener.onDomStorageItemUpdated(str, str2, item, valueToString);
                } else {
                    onOpListener.onDomStorageItemAdded(str, str2, valueToString);
                }
            }
        }
        return item2;
    }

    @AjxMethod("getItem")
    public void getItem(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            String str3 = null;
            if (!TextUtils.isEmpty(str)) {
                str3 = getLocalStorageHelper(str).getItem(str2, null);
            }
            jsFunctionCallback.callback(str3);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getItemSync")
    public String getItemSync(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            return getLocalStorageHelper(str).getItem(str2, null);
        }
        return null;
    }

    @AjxMethod("getAllItems")
    public void getAllItem(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(getAllItems(str));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getAllItemsSync")
    public JSONObject getAllItemsSync(String str) {
        return getAllItems(str);
    }

    @AjxMethod("removeItem")
    public void removeItem(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        boolean z = (TextUtils.isEmpty(str) ^ true) && getLocalStorageHelper(str).removeItem(str2);
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(z));
        }
        if (z && onOpListener != null) {
            onOpListener.onDomStorageItemRemoved(str, str2);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "removeItemSync")
    public boolean removeItemSync(String str, String str2) {
        boolean z = true;
        if (!(!TextUtils.isEmpty(str)) || !getLocalStorageHelper(str).removeItem(str2)) {
            z = false;
        }
        if (z && onOpListener != null) {
            onOpListener.onDomStorageItemRemoved(str, str2);
        }
        return z;
    }

    @AjxMethod("clear")
    public void clear(String str, JsFunctionCallback jsFunctionCallback) {
        boolean z = (TextUtils.isEmpty(str) ^ true) && getLocalStorageHelper(str).clear();
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(z));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "clearSync")
    public boolean clearSync(String str) {
        if (!(!TextUtils.isEmpty(str)) || !getLocalStorageHelper(str).clear()) {
            return false;
        }
        return true;
    }

    @AjxMethod("setDefaultItems")
    public void setDefaultItems(String str, String[] strArr, Object[] objArr, JsFunctionCallback jsFunctionCallback) {
        boolean z = (TextUtils.isEmpty(str) ^ true) && getLocalStorageHelper(str).setDefaultItems(strArr, objArr);
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(z));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "setDefaultItemsSync")
    public boolean setDefaultItemsSync(String str, String[] strArr, Object[] objArr) {
        if (!(!TextUtils.isEmpty(str)) || !getLocalStorageHelper(str).setDefaultItems(strArr, objArr)) {
            return false;
        }
        return true;
    }

    private LocalStorageHelper getLocalStorageHelper(String str) {
        LocalStorageHelper localStorageHelper = this.mCaches.get(str);
        if (localStorageHelper != null) {
            return localStorageHelper;
        }
        LocalStorageHelper localStorageHelper2 = new LocalStorageHelper(getNativeContext(), str);
        this.mCaches.put(str, localStorageHelper2);
        return localStorageHelper2;
    }

    private JSONObject getAllItems(String str) {
        Map<String, ?> allItems = getLocalStorageHelper(str).getAllItems();
        JSONObject jSONObject = new JSONObject();
        if (allItems != null && allItems.size() > 0) {
            try {
                for (String next : allItems.keySet()) {
                    jSONObject.put(next, LocalStorageHelper.valueToString(allItems.get(next)));
                }
            } catch (JSONException e) {
                StringBuilder sb = new StringBuilder("ajx.localStorage getAllItemSync error! stack:\n");
                sb.append(Log.getStackTraceString(e));
                LogHelper.e(sb.toString());
            }
        }
        return jSONObject;
    }

    private boolean preferenceExist(String str) {
        StringBuilder sb = new StringBuilder("/data/data/");
        sb.append(getNativeContext().getPackageName());
        sb.append("/shared_prefs/");
        sb.append(str);
        sb.append(".xml");
        return new File(sb.toString()).exists();
    }

    public static void setOnOpListener(OnOpListener onOpListener2) {
        onOpListener = onOpListener2;
    }
}
