package com.alipay.mobile.nebulacore.search;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.search.H5InputCallback;
import com.alipay.mobile.nebula.search.H5InputListen;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;

public class H5SearchPlugin extends H5SimplePlugin {
    private H5Page a;

    public H5SearchPlugin(H5Page h5Page) {
        this.a = h5Page;
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("navSearchBar");
    }

    private static H5InputCallback a() {
        return (H5InputCallback) Nebula.getProviderManager().getProvider(H5InputCallback.class.getName());
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        JSONObject jsonObject = event.getParam();
        if (TextUtils.equals(action, "navSearchBar")) {
            String actionType = H5Utils.getString(jsonObject, (String) "actionType");
            if (TextUtils.equals(actionType, ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW)) {
                Bundle bundle = new Bundle();
                JSONObject param = H5Utils.getJSONObject(jsonObject, "param", null);
                bundle.putString(H5Param.LONG_NAV_SEARCH_BAR_PLACEHOLDER, H5Utils.getString(param, (String) "placeholder"));
                bundle.putString(H5Param.LONG_NAV_SEARCH_BAR_VALUE, H5Utils.getString(param, (String) "value"));
                bundle.putInt(H5Param.LONG_NAV_SEARCH_BAR_MAX_LENGTH, H5Utils.getInt(param, (String) "maxLength"));
                this.a.getH5TitleBar().setTitleBarSearch(bundle);
                Nebula.getProviderManager().setProvider(H5InputListen.class.getName(), new H5SearchInputListen(this.a));
                bridgeContext.sendSuccess();
            } else if (TextUtils.equals(actionType, ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_HIDE)) {
                if (this.a != null) {
                    this.a.getH5TitleBar().switchToTitleBar();
                    bridgeContext.sendSuccess();
                }
            } else if (TextUtils.equals(actionType, "focus")) {
                if (!(a() == null || bridgeContext == null)) {
                    a().focusInNavSearchBar();
                    bridgeContext.sendSuccess();
                }
            } else if (TextUtils.equals(actionType, "blur")) {
                if (!(a() == null || bridgeContext == null)) {
                    a().focusOutNavSearchBar();
                    bridgeContext.sendSuccess();
                }
            } else if (TextUtils.equals(actionType, "set")) {
                if (!(a() == null || bridgeContext == null)) {
                    a().setNavSearchBarValue(H5Utils.getString(H5Utils.getJSONObject(jsonObject, "param", null), (String) "value"));
                    bridgeContext.sendSuccess();
                }
            } else if (TextUtils.equals(actionType, "get")) {
                if (!(a() == null || bridgeContext == null)) {
                    JSONObject data = new JSONObject();
                    data.put((String) "success", (Object) Boolean.valueOf(true));
                    data.put((String) "value", (Object) a().getNavSearchBarValue());
                    bridgeContext.sendBridgeResult(data);
                }
            } else if (TextUtils.equals(actionType, "enable")) {
                if (!(a() == null || bridgeContext == null)) {
                    a().enable();
                    bridgeContext.sendSuccess();
                }
            } else if (!(!TextUtils.equals(actionType, "disable") || a() == null || bridgeContext == null)) {
                a().disable();
                bridgeContext.sendSuccess();
            }
        }
        return true;
    }
}
