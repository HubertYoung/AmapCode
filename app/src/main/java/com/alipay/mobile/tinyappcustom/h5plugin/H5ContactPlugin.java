package com.alipay.mobile.tinyappcustom.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcustom.h5plugin.contact.ContactPickerCallback;
import com.alipay.mobile.tinyappcustom.h5plugin.contact.ContactService;

public class H5ContactPlugin extends H5SimplePlugin {
    public static final String CONTACT = "contact";

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        if (CONTACT.equals(event.getAction())) {
            a(bridgeContext);
        }
        return true;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CONTACT);
    }

    private void a(final H5BridgeContext bridgeContext) {
        H5Log.d("H5ContactPlugin", "selectContact");
        new ContactService();
        ContactService.pickFromContactsList(new ContactPickerCallback() {
            public void onAccountReturned(ContactAccount account) {
                H5Log.d("H5ContactPlugin", "onAccountReturned");
                if (account != null) {
                    String name = account.getName();
                    String phoneNumber = account.getPhoneNumber();
                    if (TextUtils.isEmpty(phoneNumber)) {
                        bridgeContext.sendError(10, (String) "没有权限获取手机号码");
                        return;
                    }
                    H5Log.d("H5ContactPlugin", "ContactAccount name" + name + " phoneNumber" + phoneNumber);
                    JSONObject contact = new JSONObject();
                    contact.put((String) "name", (Object) name);
                    contact.put((String) "mobile", (Object) phoneNumber);
                    bridgeContext.sendBridgeResult(contact);
                    return;
                }
                bridgeContext.sendError(11, (String) "用户取消操作（或设备未授权使用通讯录）");
            }
        });
    }
}
