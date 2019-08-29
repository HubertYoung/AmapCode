package com.alipay.mobile.h5plugin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.zoloz.R;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5LocationDialogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5LocationPlugin extends H5SimplePlugin {
    public static final String CHOOSE_LOCATION = "chooseLocation";
    public static final String GET_CURRENT_LOCATION = "getCurrentLocation";
    public static final String GET_LOCATION = "getLocation";
    public static final String OPEN_LOCATION = "openLocation";
    public static final String PREFETCH_LOCATION = "prefetchLocation";
    public static final String START_CONTINUOUS_LOCATION = "startContinuousLocation";
    public static final String START_INDOOR_LOCATION = "startIndoorLocation";
    public static final String STOP_CONTINUOUS_LOCATION = "stopContinuousLocation";
    public static final String STOP_INDOOR_LOCATION = "stopIndoorLocation";
    public static final String TAG = "H5LocationPlugin";
    private BroadcastReceiver backgroundLocationReceiver;
    private Context context;
    /* access modifiers changed from: private */
    public H5Location h5Location = new H5Location();
    private boolean mReceiverTag = false;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(GET_LOCATION);
        filter.addAction(START_INDOOR_LOCATION);
        filter.addAction(STOP_INDOOR_LOCATION);
        filter.addAction("prefetchLocation");
        filter.addAction(OPEN_LOCATION);
        filter.addAction(CHOOSE_LOCATION);
        filter.addAction(START_CONTINUOUS_LOCATION);
        filter.addAction(STOP_CONTINUOUS_LOCATION);
        filter.addAction(GET_CURRENT_LOCATION);
    }

    public void onRelease() {
        if (this.h5Location != null) {
            stopIndoorLocation();
            stopContinuousLocation();
            this.h5Location = null;
            try {
                if (this.context != null) {
                    unregisterReceiver(this.context);
                    LoggerFactory.getTraceLogger().info("LBSLocationManagerProxy", "unregisterReceiver");
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e);
            }
            this.backgroundLocationReceiver = null;
            this.context = null;
        }
    }

    public boolean handleEvent(final H5Event event, H5BridgeContext bridgeContext) {
        if (event == null) {
            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "event == null");
            return false;
        }
        this.context = event.getActivity();
        long startTime = System.currentTimeMillis();
        String action = event.getAction();
        if (GET_CURRENT_LOCATION.equals(action)) {
            H5Page h5Page = null;
            if (event.getTarget() instanceof H5Page) {
                h5Page = (H5Page) event.getTarget();
            }
            if (!judgeGrantForCurrentLocation(h5Page, bridgeContext)) {
                return true;
            }
            getCurrentLocation(event, bridgeContext, startTime);
            return true;
        } else if (GET_LOCATION.equals(action)) {
            H5Page h5Page2 = null;
            if (event.getTarget() instanceof H5Page) {
                h5Page2 = (H5Page) event.getTarget();
            }
            if (!judgeGrant(h5Page2, bridgeContext)) {
                return true;
            }
            getLocation(event, bridgeContext, startTime);
            return true;
        } else if (START_INDOOR_LOCATION.equals(action)) {
            startIndoorLocation(event, bridgeContext);
            return true;
        } else if (STOP_INDOOR_LOCATION.equals(action)) {
            stopIndoorLocation();
            return true;
        } else if ("prefetchLocation".equals(action)) {
            H5Page h5Page3 = null;
            if (event.getTarget() instanceof H5Page) {
                h5Page3 = (H5Page) event.getTarget();
            }
            if (!judgeGrant(h5Page3, bridgeContext)) {
                H5Log.d("H5LocationPlugin", "no grant location.");
                return false;
            } else if (this.h5Location == null) {
                return true;
            } else {
                this.h5Location.getLocation(event, bridgeContext, new LocationListener() {
                    public void onLocationResult(JSONObject resLoc, int type) {
                        String locStr = resLoc.toJSONString();
                        JSONObject param = new JSONObject();
                        H5Page h5Page = null;
                        if (event.getTarget() instanceof H5Page) {
                            h5Page = (H5Page) event.getTarget();
                        }
                        param.put((String) "geoLocation", (Object) locStr);
                        if (h5Page != null) {
                            h5Page.sendEvent(CommonEvents.H5_PAGE_JS_PARAM, param);
                        }
                    }
                }, true, startTime);
                return true;
            }
        } else if (OPEN_LOCATION.equals(action)) {
            openLocation(event, bridgeContext);
            return true;
        } else if (CHOOSE_LOCATION.equals(action)) {
            chooseLocation(event, bridgeContext);
            return true;
        } else if (START_CONTINUOUS_LOCATION.equals(action)) {
            try {
                if (this.context != null) {
                    String bizType = H5Utils.getString(event.getParam(), (String) "bizType", (String) null);
                    registerReceiver(event.getActivity(), event, bridgeContext, bizType);
                    LoggerFactory.getTraceLogger().info("H5LocationPlugin", bizType + " registerReceiver");
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e);
            }
            startContinuousLocation(event, bridgeContext);
            return true;
        } else if (!STOP_CONTINUOUS_LOCATION.equals(action)) {
            return true;
        } else {
            try {
                if (this.context != null) {
                    unregisterReceiver(this.context);
                    LoggerFactory.getTraceLogger().info("H5LocationPlugin", "unregisterReceiver");
                }
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e2);
            }
            stopContinuousLocation();
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void startContinuousLocation(H5Event event, H5BridgeContext bridgeContext) {
        LoggerFactory.getTraceLogger().info("H5LocationPlugin", START_CONTINUOUS_LOCATION);
        H5Page h5Page = null;
        if (event.getTarget() instanceof H5Page) {
            h5Page = (H5Page) event.getTarget();
        }
        if (h5Page != null) {
            String url = h5Page.getUrl();
            final String finalDomain = H5UrlHelper.parseUrl(url).getHost();
            final H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName());
            H5AppProvider provider = (H5AppProvider) h5Service.getProviderManager().getProvider(H5AppProvider.class.getName());
            String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            if ((this.h5Location == null || !TextUtils.isEmpty(finalDomain)) && !finalDomain.endsWith("alipay.com") && !finalDomain.endsWith("alipay.net") && !"Y".equals(h5Service.getSharedData(finalDomain)) && ((provider == null || !provider.isH5App(appId)) && !h5Service.permitLocation(url))) {
                final H5Event h5Event = event;
                final H5BridgeContext h5BridgeContext = bridgeContext;
                OnClickListener listener = new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == -1) {
                            if (h5Service != null) {
                                h5Service.setSharedData(finalDomain, "Y");
                            }
                            if (H5LocationPlugin.this.h5Location != null) {
                                H5LocationPlugin.this.h5Location.startContinuousLocation(h5Event, h5BridgeContext);
                            }
                        } else if (h5BridgeContext != null) {
                            JSONObject result = new JSONObject();
                            result.put((String) "error", (Object) Integer.valueOf(4));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.notagreeuseloc));
                            h5BridgeContext.sendBridgeResult(result);
                        }
                    }
                };
                Activity activity = event.getActivity();
                if (activity != null && !activity.isFinishing()) {
                    showLocationDialog(activity, finalDomain, listener);
                }
            } else if (this.h5Location != null) {
                this.h5Location.startContinuousLocation(event, bridgeContext);
            }
        }
    }

    /* access modifiers changed from: private */
    public void stopContinuousLocation() {
        LoggerFactory.getTraceLogger().info("H5LocationPlugin", STOP_CONTINUOUS_LOCATION);
        if (this.h5Location != null) {
            this.h5Location.stopContinuousLocation();
        }
    }

    private void getLocation(H5Event event, H5BridgeContext bridgeContext, long startTime) {
        H5Page h5Page = null;
        if (event.getTarget() instanceof H5Page) {
            h5Page = (H5Page) event.getTarget();
        }
        if (h5Page != null) {
            String url = h5Page.getUrl();
            final String finalDomain = H5UrlHelper.parseUrl(url).getHost();
            final H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName());
            H5AppProvider provider = (H5AppProvider) h5Service.getProviderManager().getProvider(H5AppProvider.class.getName());
            String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            if ((this.h5Location == null || !TextUtils.isEmpty(finalDomain)) && !finalDomain.endsWith("alipay.com") && !finalDomain.endsWith("alipay.net") && !"Y".equals(h5Service.getSharedData(finalDomain)) && ((provider == null || !provider.isH5App(appId)) && !h5Service.permitLocation(url))) {
                final H5Event h5Event = event;
                final H5BridgeContext h5BridgeContext = bridgeContext;
                OnClickListener listener = new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == -1) {
                            if (h5Service != null) {
                                h5Service.setSharedData(finalDomain, "Y");
                            }
                            if (H5LocationPlugin.this.h5Location != null) {
                                H5LocationPlugin.this.h5Location.getLocation(h5Event, h5BridgeContext, null, false, System.currentTimeMillis());
                            }
                        } else if (h5BridgeContext != null) {
                            JSONObject result = new JSONObject();
                            result.put((String) "error", (Object) Integer.valueOf(4));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.notagreeuseloc));
                            h5BridgeContext.sendBridgeResult(result);
                        }
                    }
                };
                Activity activity = event.getActivity();
                if (activity != null && !activity.isFinishing()) {
                    showLocationDialog(activity, finalDomain, listener);
                    return;
                }
                return;
            }
            this.h5Location.getLocation(event, bridgeContext, null, false, startTime);
        }
    }

    private void getCurrentLocation(H5Event event, H5BridgeContext bridgeContext, long startTime) {
        H5Page h5Page = null;
        if (event.getTarget() instanceof H5Page) {
            h5Page = (H5Page) event.getTarget();
        }
        if (h5Page != null) {
            String url = h5Page.getUrl();
            final String finalDomain = H5UrlHelper.parseUrl(url).getHost();
            final H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName());
            H5AppProvider provider = (H5AppProvider) h5Service.getProviderManager().getProvider(H5AppProvider.class.getName());
            String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            if ((this.h5Location == null || !TextUtils.isEmpty(finalDomain)) && !finalDomain.endsWith("alipay.com") && !finalDomain.endsWith("alipay.net") && !"Y".equals(h5Service.getSharedData(finalDomain)) && ((provider == null || !provider.isH5App(appId)) && !h5Service.permitLocation(url))) {
                final H5Event h5Event = event;
                final H5BridgeContext h5BridgeContext = bridgeContext;
                OnClickListener listener = new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == -1) {
                            if (h5Service != null) {
                                h5Service.setSharedData(finalDomain, "Y");
                            }
                            if (H5LocationPlugin.this.h5Location != null) {
                                H5LocationPlugin.this.h5Location.getCurrentLocation(h5Event, h5BridgeContext, null, false, System.currentTimeMillis());
                            }
                        } else if (h5BridgeContext != null) {
                            JSONObject result = new JSONObject();
                            result.put((String) "error", (Object) Integer.valueOf(4));
                            result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.notagreeuseloc));
                            h5BridgeContext.sendBridgeResult(result);
                        }
                    }
                };
                Activity activity = event.getActivity();
                if (activity != null && !activity.isFinishing()) {
                    showLocationDialog(activity, finalDomain, listener);
                    return;
                }
                return;
            }
            this.h5Location.getCurrentLocation(event, bridgeContext, null, false, startTime);
        }
    }

    private void chooseLocation(H5Event event, H5BridgeContext bridgeContext) {
    }

    private void openLocation(H5Event event, H5BridgeContext bridgeContext) {
        if (this.h5Location != null) {
            this.h5Location.openLocation(event, bridgeContext);
        }
    }

    private void showLocationDialog(Activity activity, String finalDomain, OnClickListener listener) {
        try {
            AlertDialog dialog = ((H5LocationDialogProvider) ((H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(H5Service.class.getName())).getProviderManager().getProvider(H5LocationDialogProvider.class.getName())).createLocationDialog(activity, finalDomain, listener);
            if (dialog != null) {
                dialog.show();
            }
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", e);
        }
    }

    private void startIndoorLocation(H5Event event, H5BridgeContext bridgeContext) {
        LoggerFactory.getTraceLogger().info("H5LocationPlugin", START_INDOOR_LOCATION);
        if (this.h5Location != null) {
            this.h5Location.startIndoorLocation(event, bridgeContext);
        }
    }

    private void stopIndoorLocation() {
        LoggerFactory.getTraceLogger().info("H5LocationPlugin", STOP_INDOOR_LOCATION);
        if (this.h5Location != null) {
            this.h5Location.stopIndoorLocation();
        }
    }

    private boolean judgeGrant(H5Page h5Page, H5BridgeContext bridgeContext) {
        boolean isGrant = false;
        if (h5Page != null) {
            int coarseLocaton = -1;
            try {
                coarseLocaton = PermissionChecker.checkSelfPermission(LauncherApplicationAgent.getInstance().getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION");
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", "t.msg=" + t.getMessage());
            }
            if (coarseLocaton == 0) {
                isGrant = true;
            }
            H5Log.d("H5LocationPlugin", "location grant:" + isGrant + " location:" + coarseLocaton);
            if (!isGrant) {
                H5Log.d("H5LocationPlugin", "no grant location.");
                JSONObject result = new JSONObject();
                result.put((String) "error", (Object) Integer.valueOf(16));
                result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_auth_failed));
                if (bridgeContext != null) {
                    bridgeContext.sendBridgeResult(result);
                }
                JSONObject param = new JSONObject();
                param.put((String) "result", (Object) "error");
                param.put((String) "failCode", (Object) "16");
            }
        }
        return isGrant;
    }

    private boolean judgeGrantForCurrentLocation(H5Page h5Page, H5BridgeContext bridgeContext) {
        boolean isGrant = false;
        if (h5Page != null) {
            int coarseLocaton = -1;
            try {
                coarseLocaton = PermissionChecker.checkSelfPermission(LauncherApplicationAgent.getInstance().getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION");
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) "H5LocationPlugin", "t.msg=" + t.getMessage());
            }
            if (coarseLocaton == 0) {
                isGrant = true;
            }
            H5Log.d("H5LocationPlugin", "location grant:" + isGrant + " location:" + coarseLocaton);
            if (!isGrant) {
                H5Log.d("H5LocationPlugin", "no grant location.");
                JSONObject result = new JSONObject();
                result.put((String) "error", (Object) Integer.valueOf(11));
                result.put((String) "errorMessage", (Object) LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle("android-phone-wallet-tinyappcommon").getString(R.string.get_location_auth_failed));
                if (bridgeContext != null) {
                    bridgeContext.sendBridgeResult(result);
                }
                JSONObject param = new JSONObject();
                param.put((String) "result", (Object) "error");
                param.put((String) "failCode", (Object) "11");
            }
        }
        return isGrant;
    }

    public void registerReceiver(Context context2, final H5Event event, final H5BridgeContext bridgeContext, String bizType) {
        if (this.backgroundLocationReceiver == null) {
            this.backgroundLocationReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (context != null && intent != null) {
                        String action = intent.getAction();
                        if ("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND".equals(action)) {
                            H5LocationPlugin.this.startContinuousLocation(event, bridgeContext);
                        } else if ("com.alipay.mobile.framework.USERLEAVEHINT".equals(action)) {
                            H5LocationPlugin.this.stopContinuousLocation();
                        }
                    }
                }
            };
            if (!this.mReceiverTag) {
                IntentFilter filter = new IntentFilter();
                filter.addAction("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND");
                filter.addAction("com.alipay.mobile.framework.USERLEAVEHINT");
                LocalBroadcastManager.getInstance(context2).registerReceiver(this.backgroundLocationReceiver, filter);
                this.mReceiverTag = true;
                LoggerFactory.getTraceLogger().info("H5LocationPlugin", "registerReceiver");
            }
        }
    }

    public void unregisterReceiver(Context context2) {
        if (this.mReceiverTag && this.backgroundLocationReceiver != null) {
            LocalBroadcastManager.getInstance(context2).unregisterReceiver(this.backgroundLocationReceiver);
            this.mReceiverTag = false;
            this.backgroundLocationReceiver = null;
            LoggerFactory.getTraceLogger().info("H5LocationPlugin", "unregisterReceiver");
        }
    }
}
