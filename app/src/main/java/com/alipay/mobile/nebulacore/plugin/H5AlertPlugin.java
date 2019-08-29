package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.process.H5IpcServer;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider.OnClickNegativeListener;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider.OnClickPositiveListener;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.view.H5Alert;
import com.alipay.mobile.nebulacore.view.H5Alert.H5AlertListener;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Value;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class H5AlertPlugin extends H5SimplePlugin {
    public static final String STARTUP_PARAM_USE_SYS_WEBVIEW = "enablePolyfillWorker";
    public static final String TAG = "H5AlertPlugin";
    public static final String showUCFailDialog = "showUCFailDialog";
    /* access modifiers changed from: private */
    public H5Alert a;

    public void onRelease() {
        this.a = null;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.SHOW_ALERT);
        filter.addAction("alert");
        filter.addAction(CommonEvents.CONFIRM);
        filter.addAction(showUCFailDialog);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.SHOW_ALERT.equals(action)) {
            c(event, bridgeContext);
        } else if ("alert".equals(action)) {
            a(event, bridgeContext);
        } else if (CommonEvents.CONFIRM.equals(action)) {
            b(event, bridgeContext);
        } else if (showUCFailDialog.equals(action) && (event.getTarget() instanceof H5Page)) {
            a(event, (H5Page) event.getTarget(), bridgeContext);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        Activity activity = event.getActivity();
        if (event != null) {
            JSONObject params = event.getParam();
            if (params != null) {
                String title = params.getString("title");
                String message = params.getString("message");
                String button = params.getString(PoiLayoutTemplate.BUTTON);
                String align = params.getString("align");
                String confirmColor = params.getString("confirmColor");
                String cancelColor = params.getString("cancelColor");
                if (TextUtils.isEmpty(button)) {
                    button = H5Environment.getResources().getString(R.string.h5_default_confirm);
                }
                String[] buttons = {button};
                final H5DialogManagerProvider h5DialogManagerProvider = (H5DialogManagerProvider) H5ProviderManagerImpl.getInstance().getProvider(H5DialogManagerProvider.class.getName());
                if (h5DialogManagerProvider != null) {
                    Dialog dialog = h5DialogManagerProvider.createDialog(activity, title, message, buttons[0], null, align);
                    h5DialogManagerProvider.setPositiveTextColor(confirmColor);
                    h5DialogManagerProvider.setNegativeTextColor(cancelColor);
                    final H5BridgeContext h5BridgeContext = bridgeContext;
                    h5DialogManagerProvider.setPositiveListener(new OnClickPositiveListener() {
                        public void onClick() {
                            h5DialogManagerProvider.disMissDialog();
                            h5BridgeContext.sendBridgeResult(null);
                            h5DialogManagerProvider.release();
                        }
                    });
                    h5DialogManagerProvider.showDialog();
                    if (dialog != null) {
                        final H5BridgeContext h5BridgeContext2 = bridgeContext;
                        dialog.setOnCancelListener(new OnCancelListener() {
                            public void onCancel(DialogInterface dialogInterface) {
                                h5BridgeContext2.sendBridgeResult(null);
                                h5DialogManagerProvider.release();
                            }
                        });
                        return;
                    }
                    return;
                }
                final H5BridgeContext h5BridgeContext3 = bridgeContext;
                H5AlertListener listener = new H5AlertListener() {
                    public void onClick(H5Alert alert, int index) {
                        h5BridgeContext3.sendBridgeResult(null);
                        H5AlertPlugin.this.a = null;
                    }

                    public void onCancel(H5Alert alert) {
                        h5BridgeContext3.sendBridgeResult(null);
                        H5AlertPlugin.this.a = null;
                    }
                };
                if (this.a != null) {
                    this.a.dismiss();
                    this.a = null;
                }
                this.a = new H5Alert(activity).cancelable(false).title(title).message(message).buttons(buttons).listener(listener).show();
            }
        }
    }

    private void b(H5Event event, H5BridgeContext bridgeContext) {
        if (event != null) {
            JSONObject params = event.getParam();
            if (params != null) {
                String title = params.getString("title");
                String message = params.getString("message");
                String confirm = params.getString("okButton");
                String confirmColor = params.getString("confirmColor");
                String cancelColor = params.getString("cancelColor");
                Resources resources = H5Environment.getResources();
                if (TextUtils.isEmpty(confirm)) {
                    confirm = resources.getString(R.string.h5_default_confirm);
                }
                String cancel = params.getString("cancelButton");
                if (TextUtils.isEmpty(cancel)) {
                    cancel = resources.getString(R.string.h5_default_cancel);
                }
                String[] buttons = {confirm, cancel};
                String align = params.getString("align");
                Activity activity = event.getActivity();
                final H5DialogManagerProvider h5DialogManagerProvider = (H5DialogManagerProvider) H5ProviderManagerImpl.getInstance().getProvider(H5DialogManagerProvider.class.getName());
                if (h5DialogManagerProvider != null) {
                    Dialog dialog = h5DialogManagerProvider.createDialog(activity, title, message, buttons[0], buttons[1], align);
                    h5DialogManagerProvider.setPositiveTextColor(confirmColor);
                    h5DialogManagerProvider.setNegativeTextColor(cancelColor);
                    final H5BridgeContext h5BridgeContext = bridgeContext;
                    h5DialogManagerProvider.setPositiveListener(new OnClickPositiveListener() {
                        public void onClick() {
                            h5DialogManagerProvider.disMissDialog();
                            JSONObject result = new JSONObject();
                            result.put((String) Value.OK, (Object) Boolean.valueOf(true));
                            h5BridgeContext.sendBridgeResult(result);
                            h5DialogManagerProvider.release();
                        }
                    });
                    final H5BridgeContext h5BridgeContext2 = bridgeContext;
                    h5DialogManagerProvider.setNegativeListener(new OnClickNegativeListener() {
                        public void onClick() {
                            h5DialogManagerProvider.disMissDialog();
                            JSONObject result = new JSONObject();
                            result.put((String) Value.OK, (Object) Boolean.valueOf(false));
                            h5BridgeContext2.sendBridgeResult(result);
                            h5DialogManagerProvider.release();
                        }
                    });
                    if (dialog != null) {
                        final H5BridgeContext h5BridgeContext3 = bridgeContext;
                        dialog.setOnCancelListener(new OnCancelListener() {
                            public void onCancel(DialogInterface dialogInterface) {
                                JSONObject result = new JSONObject();
                                result.put((String) Value.OK, (Object) Boolean.valueOf(false));
                                h5BridgeContext3.sendBridgeResult(result);
                                h5DialogManagerProvider.release();
                            }
                        });
                    }
                    h5DialogManagerProvider.showDialog();
                    return;
                }
                final H5BridgeContext h5BridgeContext4 = bridgeContext;
                H5AlertListener listener = new H5AlertListener() {
                    public void onClick(H5Alert alert, int index) {
                        boolean confirmed = index == 0;
                        JSONObject result = new JSONObject();
                        result.put((String) Value.OK, (Object) Boolean.valueOf(confirmed));
                        h5BridgeContext4.sendBridgeResult(result);
                        H5AlertPlugin.this.a = null;
                    }

                    public void onCancel(H5Alert alert) {
                        JSONObject result = new JSONObject();
                        result.put((String) Value.OK, (Object) Boolean.valueOf(false));
                        h5BridgeContext4.sendBridgeResult(result);
                        H5AlertPlugin.this.a = null;
                    }
                };
                if (this.a != null) {
                    this.a.dismiss();
                    this.a = null;
                }
                this.a = new H5Alert(activity).cancelable(false).title(title).message(message).buttons(buttons).listener(listener).show();
            }
        }
    }

    private void c(H5Event event, final H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param == null) {
            H5Log.e((String) TAG, (String) "none params");
            return;
        }
        String title = H5Utils.getString(param, (String) "title", (String) null);
        String message = H5Utils.getString(param, (String) "message", (String) null);
        String[] buttonLabels = null;
        try {
            JSONArray buttons = H5Utils.getJSONArray(param, "buttons", null);
            if (buttons.size() > 0) {
                buttonLabels = new String[buttons.size()];
                for (int i = 0; i != buttons.size(); i++) {
                    buttonLabels[i] = buttons.getString(i);
                }
                H5AlertListener listener = new H5AlertListener() {
                    public void onClick(H5Alert alert, int index) {
                        alert.dismiss();
                        JSONObject data = new JSONObject();
                        data.put((String) "success", (Object) "true");
                        data.put((String) "index", (Object) Integer.valueOf(index));
                        bridgeContext.sendBridgeResult(data);
                        H5AlertPlugin.this.a = null;
                    }

                    public void onCancel(H5Alert alert) {
                        JSONObject data = new JSONObject();
                        data.put((String) "success", (Object) "true");
                        data.put((String) "index", (Object) Integer.valueOf(3));
                        bridgeContext.sendBridgeResult(data);
                        H5AlertPlugin.this.a = null;
                    }
                };
                if (this.a != null) {
                    this.a.dismiss();
                    this.a = null;
                }
                this.a = new H5Alert(event.getActivity()).cancelable(false).title(title).message(message).buttons(buttonLabels).listener(listener).show();
            }
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
        }
    }

    private void a(final H5Event event, final H5Page h5Page, final H5BridgeContext h5BridgeContext) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if ((h5ConfigProvider == null || !TextUtils.equals(h5ConfigProvider.getConfigWithProcessCache("h5_show_uc_fail_dialog"), BQCCameraParam.VALUE_NO)) && h5Page != null) {
            final String url = H5Utils.getString(h5Page.getParams(), (String) H5Param.LONG_NB_URL);
            if (!TextUtils.isEmpty(url)) {
                H5LogData logData = H5LogData.seedId("H5_UC_FAIL_FALLBACK_NBURL");
                if (h5Page.getParams() != null) {
                    logData.param4().add(H5Utils.getString(h5Page.getParams(), (String) "appId"), null).add(H5Param.LONG_NB_URL, url);
                }
                H5LogUtil.logNebulaTech(logData);
                if (H5Utils.isInTinyProcess()) {
                    Nebula.moveTaskToBack(event.getActivity());
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            try {
                                event.getActivity().finish();
                                String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
                                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                                if (h5EventHandlerService != null) {
                                    H5IpcServer h5IpcServer = (H5IpcServer) h5EventHandlerService.getIpcProxy(H5IpcServer.class);
                                    if (h5IpcServer != null) {
                                        h5IpcServer.killTinyOpenMainUrl(appId, url);
                                    }
                                }
                            } catch (Throwable throwable) {
                                H5Log.e((String) H5AlertPlugin.TAG, throwable);
                            }
                        }
                    }, 500);
                    return;
                }
                Activity activity = event.getActivity();
                if (activity != null) {
                    activity.finish();
                }
                H5Utils.openUrl(url);
                return;
            }
            H5DialogManagerProvider h5DialogManagerProvider = (H5DialogManagerProvider) H5Utils.getProvider(H5DialogManagerProvider.class.getName());
            if (h5DialogManagerProvider != null) {
                final Activity activity2 = event.getActivity();
                if (activity2 != null && !activity2.isFinishing()) {
                    h5DialogManagerProvider.createDialog(event.getActivity(), null, "系统框架异常，暂时无法启动，请稍后再试。", "确认", null, null);
                    h5DialogManagerProvider.setPositiveListener(new OnClickPositiveListener() {
                        public void onClick() {
                            if (h5BridgeContext != null) {
                                h5BridgeContext.sendSuccess();
                            }
                            H5LogData logData = H5LogData.seedId("H5_UC_FAIL_DIALOG");
                            if (!(h5Page == null || h5Page.getParams() == null)) {
                                logData.param4().add(H5Utils.getString(h5Page.getParams(), (String) "appId"), null);
                            }
                            H5LogUtil.logNebulaTech(logData);
                            activity2.finish();
                            if (H5Utils.isInTinyProcess()) {
                                H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                                if (h5EventHandlerService != null) {
                                    h5EventHandlerService.stopSelfProcess();
                                }
                            }
                        }
                    });
                    h5DialogManagerProvider.showDialog();
                }
            }
        }
    }
}
