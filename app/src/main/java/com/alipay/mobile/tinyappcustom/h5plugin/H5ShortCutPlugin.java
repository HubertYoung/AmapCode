package com.alipay.mobile.tinyappcustom.h5plugin;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.a.a.a.c;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ImageProvider;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.service.ShortCutService;
import com.alipay.mobile.service.ShortCutService.SCCallback;
import com.alipay.mobile.service.ShortCutService.SCInfo;
import com.alipay.mobile.service.ShortCutService.SCResult;
import com.mpaas.nebula.NebulaBiz;
import java.util.HashMap;
import java.util.Map;

public class H5ShortCutPlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("setShortCut");
        filter.addAction("removeShortCut");
        filter.addAction("isSupportShortCut");
        filter.addAction("haveShortCut");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        final JSONObject jsonObject = event.getParam();
        final ShortCutService shortCutService = (ShortCutService) NebulaBiz.findServiceByInterface(ShortCutService.class.getName());
        if (shortCutService == null) {
            return false;
        }
        if ("isSupportShortCut".equals(event.getAction())) {
            context.sendBridgeResult("result", Boolean.valueOf(shortCutService.isSupportInstallDesktopShortCut()));
            return true;
        }
        final String appId = H5Utils.getString(jsonObject, (String) "appId");
        final String appName = H5Utils.getString(jsonObject, (String) "appName");
        String iconBitmap = H5Utils.getString(jsonObject, (String) "iconBitmap");
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(appName) || TextUtils.isEmpty(iconBitmap)) {
            context.sendError(event, Error.INVALID_PARAM);
            return true;
        }
        if (iconBitmap.startsWith("http")) {
            H5ImageProvider h5ImageProvider = (H5ImageProvider) H5Utils.getProvider(H5ImageProvider.class.getName());
            if (h5ImageProvider != null) {
                final H5Event h5Event = event;
                final H5BridgeContext h5BridgeContext = context;
                h5ImageProvider.loadImage(iconBitmap, new H5ImageListener() {
                    public void onImage(final Bitmap bitmap) {
                        if (bitmap != null) {
                            H5Utils.runOnMain(new Runnable() {
                                public void run() {
                                    H5ShortCutPlugin.this.a(h5Event, jsonObject, shortCutService, appId, appName, bitmap, h5BridgeContext);
                                }
                            });
                        } else if (h5BridgeContext != null) {
                            h5BridgeContext.sendError(2, NebulaBiz.getResources().getString(c.save_image_failed));
                        }
                    }
                });
            }
        } else {
            a(event, jsonObject, shortCutService, appId, appName, H5ImageUtil.base64ToBitmap(iconBitmap), context);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, JSONObject jsonObject, ShortCutService shortCutService, String appId, String appName, Bitmap bitmap, H5BridgeContext context) {
        Map map = new HashMap();
        JSONObject extParams = H5Utils.getJSONObject(jsonObject, "params", null);
        if (extParams != null && !extParams.isEmpty()) {
            for (String key : extParams.keySet()) {
                map.put(key, extParams.get(key).toString());
            }
        }
        if (H5Utils.isInTinyProcess()) {
            map.put("FORCE_SCHEME_ACTIVITY", Const.SchemeStartActivity);
        }
        if ("setShortCut".equals(event.getAction())) {
            try {
                if (!map.containsKey("chInfo")) {
                    map.put("chInfo", "ch_desktop");
                }
                if (!map.containsKey(MicroApplication.KEY_APP_SCENE_ID)) {
                    map.put(MicroApplication.KEY_APP_SCENE_ID, "1023");
                }
                boolean needConfirmDialog = H5Utils.getBoolean(event.getParam(), (String) "needConfirmDialog", true);
                boolean needTipToasts = H5Utils.getBoolean(event.getParam(), (String) "needTipToasts", true);
                SCInfo scInfo = new SCInfo();
                scInfo.shortcutType = 1;
                scInfo.shortcutUniqueId = appId;
                scInfo.appId = appId;
                scInfo.title = appName;
                scInfo.iconBitmap = bitmap;
                scInfo.params = map;
                scInfo.needConfirmDialog = needConfirmDialog;
                scInfo.needTipToasts = needTipToasts;
                scInfo.flags = 335544320;
                if (H5Utils.getBoolean(jsonObject, (String) "openDirectly", false)) {
                    H5Log.d("H5ShortCutPlugin", "openDirectly");
                    scInfo.directly = true;
                }
                final H5BridgeContext h5BridgeContext = context;
                shortCutService.installShortcut(scInfo, new SCCallback() {
                    public void onShortcutResult(SCResult scResult) {
                        if (scResult == null) {
                            H5Log.d("H5ShortCutPlugin", "scResult==null");
                            h5BridgeContext.sendSuccess();
                        } else if (scResult.result == 1) {
                            h5BridgeContext.sendSuccess();
                        } else {
                            String errMsg = "";
                            if (scResult.result == 3) {
                                errMsg = NebulaBiz.getResources().getString(c.h5_shortcut_bad_param);
                            } else if (scResult.result == 4) {
                                errMsg = NebulaBiz.getResources().getString(c.h5_shortcut_no_activity);
                            } else if (scResult.result == 5) {
                                errMsg = NebulaBiz.getResources().getString(c.h5_shortcut_not_support);
                            } else if (scResult.result == 6) {
                                errMsg = NebulaBiz.getResources().getString(c.h5_shortcut_is_create);
                            } else if (scResult.result == 7) {
                                errMsg = NebulaBiz.getResources().getString(c.h5_shortcut_use_cancel);
                            }
                            h5BridgeContext.sendError(scResult.result, errMsg);
                        }
                    }
                });
            } catch (Exception e) {
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5ShortCutPlugin").param4().add("installAppSchemeShortCut", e));
            }
        } else if ("removeShortCut".equals(event.getAction())) {
            try {
                SCInfo scInfo2 = new SCInfo();
                scInfo2.shortcutType = 1;
                scInfo2.shortcutUniqueId = appId;
                scInfo2.appId = appId;
                scInfo2.title = appName;
                scInfo2.iconBitmap = bitmap;
                scInfo2.params = map;
                final H5BridgeContext h5BridgeContext2 = context;
                shortCutService.uninstallShortcut(scInfo2, new SCCallback() {
                    public void onShortcutResult(SCResult scResult) {
                        if (scResult == null || scResult.result == 1001) {
                            h5BridgeContext2.sendSuccess();
                        } else if (scResult.result == 1003) {
                            h5BridgeContext2.sendError(scResult.result, NebulaBiz.getResources().getString(c.h5_shortcut_bad_param));
                        }
                    }
                });
            } catch (Exception e2) {
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5ShortCutPlugin").param4().add("uninstallAppSchemeShortCut", e2));
            }
        } else if ("haveShortCut".equals(event.getAction())) {
            try {
                context.sendBridgeResult("result", Boolean.valueOf(shortCutService.isThereAShortCutOnDesktop(appId, appName, bitmap, map)));
            } catch (Throwable throwable) {
                H5Log.e((String) "H5ShortCutPlugin", throwable);
            }
        }
    }
}
