package com.autonavi.miniapp.plugin.shortcut;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.miniapp.plugin.BasePlugin;
import java.util.Arrays;

public class ShortCutPlugin extends BasePlugin {
    private static final String HAVE_SHORTCUT = "haveShortCut";
    public static final int INSTALL_FAIL_IS_CREATED = 6;
    public static final int INSTALL_FAIL_NOT_SUPPORT = 5;
    public static final int INSTALL_FAIL_NO_ACTIVITY = 4;
    public static final int INSTALL_FAIL_USER_CANCEL = 7;
    private static final String IS_SUPPORT_SHORTCUT = "isSupportShortCut";
    private static final String REMOVE_SHORTCUT = "removeShortCut";
    private static final String SET_SHORTCUT = "setShortCut";
    private static final String TAG = "ShortCutPlugin";

    public void onPrepare(H5EventFilter h5EventFilter) {
        super.onPrepare(h5EventFilter);
        h5EventFilter.addAction(SET_SHORTCUT);
        h5EventFilter.addAction(REMOVE_SHORTCUT);
        h5EventFilter.addAction(IS_SUPPORT_SHORTCUT);
        h5EventFilter.addAction(HAVE_SHORTCUT);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        final JSONObject param = h5Event.getParam();
        if (IS_SUPPORT_SHORTCUT.equals(h5Event.getAction())) {
            MiniAppShortCutUtil.handleIsSupportShortcut(h5BridgeContext);
            return true;
        }
        final String string = H5Utils.getString(param, (String) "appId");
        final String string2 = H5Utils.getString(param, (String) "appName");
        String string3 = H5Utils.getString(param, (String) "iconBitmap");
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3)) {
            h5BridgeContext.sendError(h5Event, Error.INVALID_PARAM);
            return true;
        }
        if (string3.startsWith("http")) {
            H5ContentProvider webProvider = h5Event.getH5page().getSession().getWebProvider();
            final H5Event h5Event2 = h5Event;
            final H5BridgeContext h5BridgeContext2 = h5BridgeContext;
            AnonymousClass1 r0 = new ResponseListen() {
                public void onGetResponse(WebResourceResponse webResourceResponse) {
                    final Bitmap decodeStream = BitmapFactory.decodeStream(webResourceResponse.getData());
                    if (decodeStream != null) {
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                ShortCutPlugin.this.shortcut(h5Event2, param, string, string2, decodeStream, h5BridgeContext2);
                            }
                        });
                        return;
                    }
                    if (h5BridgeContext2 != null) {
                        h5BridgeContext2.sendError(2, (String) "图片保存失败");
                    }
                }
            };
            webProvider.getContent(string3, (ResponseListen) r0);
        } else {
            shortcut(h5Event, param, string, string2, H5ImageUtil.base64ToBitmap(string3), h5BridgeContext);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void shortcut(H5Event h5Event, JSONObject jSONObject, String str, String str2, Bitmap bitmap, H5BridgeContext h5BridgeContext) {
        if (HAVE_SHORTCUT.equals(h5Event.getAction())) {
            MiniAppShortCutUtil.handleHaveShortCut(h5Event, str, str2, h5BridgeContext);
        } else if (REMOVE_SHORTCUT.equals(h5Event.getAction())) {
            MiniAppShortCutUtil.handleRemoveShortCut(str, str2, h5BridgeContext);
        } else {
            if (SET_SHORTCUT.equals(h5Event.getAction())) {
                MiniAppShortCutUtil.handleSetShortCut(h5Event, str, str2, bitmap, h5BridgeContext);
            }
        }
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public String getEvents() {
        return getEvents(Arrays.asList(new String[]{SET_SHORTCUT, REMOVE_SHORTCUT, IS_SUPPORT_SHORTCUT, HAVE_SHORTCUT}));
    }
}
