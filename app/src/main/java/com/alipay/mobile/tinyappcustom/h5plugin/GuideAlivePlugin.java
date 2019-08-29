package com.alipay.mobile.tinyappcustom.h5plugin;

import android.app.AlertDialog.Builder;
import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionGuideCallback;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionGuideResult;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionGuideService;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionStatus;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionType;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.SchemeService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.monitor.api.MonitorFactory;
import com.alipay.mobile.monitor.util.MonitorUtils;
import com.alipay.mobile.tinyappcustom.a.a;
import com.alipay.mobile.tinyappcustom.a.b;
import java.util.Arrays;
import java.util.List;

public class GuideAlivePlugin extends H5SimplePlugin {
    public static final String ACTION_GET_AUTHSTATUS = "getAuthStatus";
    public static final String ACTION_GET_OPTION_STATUS = "guideAlivePlugin.getOptionStatus";
    public static final String ACTION_GET_PERMISSION_GUIDE_CONTENT = "getPermissionGuideContent";
    public static final String ACTION_HAS_PERMISSION_GUIDE_PATH = "hasPermissionGuidePath";
    public static final String ACTION_IS_OPTION_SUPPORT = "guideAlivePlugin.isOptionSupport";
    public static final String ACTION_PAGE_IN = "guideAlivePlugin.pageIn";
    public static final String ACTION_PAGE_OUT = "guideAlivePlugin.pageOut";
    public static final String ACTION_SHOW_AUTHGUIDE = "showAuthGuide";
    public static final String ACTION_SHOW_GUIDE = "guideAlivePlugin.showGuide";
    public static final String ACTION_START_PERMISSION_PAGE = "startPermissionPage";
    private final List<String> a = Arrays.asList(new String[]{PermissionType.SELFSTARTING.name(), PermissionType.BACKGROUNDER.name(), PermissionType.NOTIFICATION.name(), PermissionType.LBS.name(), PermissionType.LBSSERVICE.name()});

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "handleEvent, action: " + action + ", params: " + event.getParam());
        if ("guideAlivePlugin.pageIn".equals(action)) {
            a(event);
            return true;
        } else if ("guideAlivePlugin.getOptionStatus".equals(action)) {
            e(event, context);
            return true;
        } else if ("guideAlivePlugin.isOptionSupport".equals(action)) {
            d(event, context);
            return true;
        } else if ("guideAlivePlugin.showGuide".equals(action)) {
            f(event, context);
            return true;
        } else if ("showAuthGuide".equals(action)) {
            h(event, context);
            return true;
        } else if ("getAuthStatus".equals(action)) {
            g(event, context);
            return true;
        } else if ("guideAlivePlugin.pageOut".equals(action)) {
            b(event);
            return true;
        } else if (ACTION_HAS_PERMISSION_GUIDE_PATH.equals(action)) {
            a(event, context);
            return true;
        } else if (ACTION_START_PERMISSION_PAGE.equals(action)) {
            c(event, context);
            return true;
        } else if (!ACTION_GET_PERMISSION_GUIDE_CONTENT.equals(action)) {
            return super.handleEvent(event, context);
        } else {
            b(event, context);
            return true;
        }
    }

    private static String a(JSONObject params) {
        if (params.get("bizType") == null) {
            return "";
        }
        return params.get("bizType").toString();
    }

    private static String b(JSONObject params) {
        if (!params.containsKey("authType") || params.get("authType") == null) {
            return "";
        }
        return params.get("authType").toString();
    }

    private static void a(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "checkPermissionGuidePath params is null");
            return;
        }
        String bizType = a(params);
        String authType = b(params);
        LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "bizType=" + bizType + ",authType=" + authType);
        JSONObject jsOb = new JSONObject();
        if (TextUtils.isEmpty(bizType) || TextUtils.isEmpty(authType)) {
            jsOb.put((String) "hasPath", (Object) "false");
            context.sendBridgeResult(jsOb);
            return;
        }
        boolean hasPath = false;
        try {
            hasPath = ((PermissionGuideService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PermissionGuideService.class.getName())).hasPermissionGuidePath(bizType, PermissionType.valueOf(authType));
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "checkPermissionGuidePath, error=" + th);
        }
        jsOb.put((String) "hasPath", (Object) hasPath ? "true" : "false");
        context.sendBridgeResult(jsOb);
    }

    private static void b(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "checkPermissionGuidePath params is null");
            return;
        }
        String bizType = a(params);
        String authType = b(params);
        LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "bizType=" + bizType + ",authType=" + authType);
        JSONObject jsOb = new JSONObject();
        if (TextUtils.isEmpty(bizType) || TextUtils.isEmpty(authType)) {
            jsOb.put((String) "guideContent", (Object) "");
            context.sendBridgeResult(jsOb);
            return;
        }
        String guideContent = "";
        try {
            guideContent = ((PermissionGuideService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PermissionGuideService.class.getName())).getPermissionGuideContent(bizType, PermissionType.valueOf(authType));
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "checkPermissionGuidePath, error=" + th);
        }
        jsOb.put((String) "guideContent", (Object) guideContent);
        context.sendBridgeResult(jsOb);
    }

    private static void c(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "checkPermissionGuidePath params is null");
            return;
        }
        String bizType = a(params);
        String authType = b(params);
        LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "bizType=" + bizType + ",authType=" + authType);
        JSONObject jsOb = new JSONObject();
        if (TextUtils.isEmpty(bizType) || TextUtils.isEmpty(authType)) {
            jsOb.put((String) "hasPath", (Object) "false");
            context.sendBridgeResult(jsOb);
            return;
        }
        try {
            ((PermissionGuideService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PermissionGuideService.class.getName())).startPermissionPathActivity(bizType, PermissionType.valueOf(authType));
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "checkPermissionGuidePath, error=" + th);
        }
    }

    private static void d(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "isOptionSupport params is null");
            return;
        }
        String issue = params.getString("issue");
        String source = params.getString("source");
        String option = params.getString("option");
        boolean isSupport = false;
        if ("volumeissue".equals(option)) {
            isSupport = true;
        } else {
            try {
                PermissionGuideService newPermissionGuideService = (PermissionGuideService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PermissionGuideService.class.getName());
                if (newPermissionGuideService != null) {
                    PermissionType[] permissionTypes = "autoboot".equals(option) ? new PermissionType[]{PermissionType.SELFSTARTING} : "keepalive".equals(option) ? new PermissionType[]{PermissionType.BACKGROUNDER} : null;
                    if (permissionTypes != null) {
                        isSupport = newPermissionGuideService.isPermissionGuideValid("CommonBizPG", permissionTypes)[0];
                    }
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) "GuideAlivePlugin", e);
            }
        }
        LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "isOptionSupport isSupport = " + isSupport);
        JSONObject jsOb = new JSONObject();
        jsOb.put((String) "support", (Object) Boolean.valueOf(isSupport));
        context.sendBridgeResult(jsOb);
        new b("h5_isOptionSupport").a("TransferQRCode").a().a("issue", issue).a("source", source).a("option", option).a("support", Boolean.valueOf(isSupport)).b();
    }

    private static void e(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "getOptionStatus params is null");
            return;
        }
        String issue = params.getString("issue");
        String source = params.getString("source");
        String option = params.getString("option");
        int status = -1;
        String config = MonitorUtils.getConfigValueByKeyOnBrandOrSDK("GuideAlivePlugin_OptionStatus_" + option, "");
        if (!TextUtils.isEmpty(config)) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "getOptionStatus, config: " + config);
            try {
                status = Integer.parseInt(config);
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) "GuideAlivePlugin", t);
            }
        } else if ("autoboot".equals(option)) {
            status = MonitorFactory.getMonitorContext().autoStartWhitelistStatus();
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "autoStartWhitelistStatus = " + status);
        } else if ("keepalive".equals(option)) {
            status = MonitorFactory.getMonitorContext().keepAliveWhitelistStatus();
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "keepAliveWhitelistStatus = " + status);
        } else if ("volumeissue".equals(option)) {
            boolean isProblem = a.a();
            status = isProblem ? 2 : 1;
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "isProblemUserByVolumeIssue = " + isProblem);
        }
        LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "getOptionStatus, status: " + status);
        JSONObject jsOb = new JSONObject();
        jsOb.put((String) "status", (Object) Integer.valueOf(status));
        context.sendBridgeResult(jsOb);
        new b("h5_getOptionStatus").a("TransferQRCode").a().a("issue", issue).a("source", source).a("option", option).a("status", Integer.valueOf(status)).b();
    }

    private void f(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "showGuide params is null");
            return;
        }
        String issue = params.getString("issue");
        String source = params.getString("source");
        String option = params.getString("option");
        boolean isSuccess = false;
        b logger = new b("h5_showGuide").a("TransferQRCode").a();
        try {
            if ("volumeissue".equals(option)) {
                String appId = params.getString("appId");
                String appArg = params.getString("appArg");
                int result = a(appId, appArg, issue, source);
                isSuccess = result == 0;
                logger.a("appId", appId);
                logger.a("appArg", appArg);
                logger.a("reason", Integer.valueOf(result));
            } else {
                isSuccess = a(option, issue, source);
            }
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) "GuideAlivePlugin", t);
        }
        JSONObject jsOb = new JSONObject();
        jsOb.put((String) "result", (Object) Boolean.valueOf(isSuccess));
        context.sendBridgeResult(jsOb);
        logger.a("issue", issue);
        logger.a("source", source);
        logger.a("option", option);
        logger.a("result", Boolean.valueOf(isSuccess));
        logger.b();
    }

    private static int a(String appId, String appArg, String issue, String source) {
        if (TextUtils.isEmpty(appId)) {
            appId = "20001108";
        }
        if (appArg == null) {
            appArg = "";
        }
        String scheme = "alipays://platformapi/startapp?appId=" + appId + "&issue=" + issue + "&source=" + source + appArg;
        int result = -1;
        try {
            result = ((SchemeService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(SchemeService.class.getName())).process(Uri.parse(scheme));
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "doAdjustVolume, scheme: " + scheme + ", result: " + result);
            return result;
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) "GuideAlivePlugin", t);
            return result;
        }
    }

    private boolean a(String option, String issue, String source) {
        PermissionType[] permissionTypes;
        if ("autoboot".equals(option)) {
            permissionTypes = new PermissionType[]{PermissionType.SELFSTARTING};
        } else if ("keepalive".equals(option)) {
            permissionTypes = new PermissionType[]{PermissionType.BACKGROUNDER};
        } else {
            LoggerFactory.getTraceLogger().warn((String) "GuideAlivePlugin", "doShowGuide fail, option: " + option);
            return false;
        }
        boolean success = false;
        try {
            PermissionGuideService newPermissionGuideService = (PermissionGuideService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PermissionGuideService.class.getName());
            if (newPermissionGuideService == null) {
                return false;
            }
            success = newPermissionGuideService.startPermissionGuide((String) "CommonBizPG", permissionTypes, (PermissionGuideCallback) new PermissionGuideCallback() {
                public void onPermissionGuideResult(PermissionType[] permissionTypes, PermissionGuideResult[] permissionGuideResults) {
                    if (permissionTypes == null || permissionGuideResults == null) {
                        LoggerFactory.getTraceLogger().warn((String) "GuideAlivePlugin", (String) "onPermissionGuideResult permissionTypes or permissionGuideResults is null");
                    } else if (permissionTypes.length <= 0 || permissionGuideResults.length <= 0) {
                        LoggerFactory.getTraceLogger().warn((String) "GuideAlivePlugin", (String) "permissionTypes or permissionGuideResults <= 0!");
                    } else {
                        LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "permissionTypes = " + permissionTypes[0].name() + " permissionGuideResults = " + permissionGuideResults[0].name());
                    }
                }
            }, true);
            new b("h5_doShowGuide").a("TransferQRCode").a().a("issue", issue).a("source", source).a("guideType", option).a("success", Boolean.valueOf(success)).b();
            return success;
        } catch (Throwable throwable) {
            LoggerFactory.getTraceLogger().error((String) "GuideAlivePlugin", throwable);
            return success;
        }
    }

    private void g(H5Event event, H5BridgeContext context) {
        String authType = null;
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "getAuthStatus params is null");
            return;
        }
        String issue = params.getString("issue");
        String source = params.getString("source");
        String option = params.getString("option");
        if (params.containsKey("authType")) {
            authType = params.get("authType").toString();
        }
        JSONObject jsOb = new JSONObject();
        if (TextUtils.isEmpty(authType)) {
            jsOb.put((String) "authStatus", (Object) PermissionStatus.NOT_SURE.getRpcName());
            context.sendBridgeResult(jsOb);
            return;
        }
        try {
            if (!this.a.contains(authType)) {
                if (a()) {
                    new Builder(event.getActivity()).setTitle("不推荐的调用方式").setMessage("请勿使用该 API 判断【自启】，【后台运行】，【定位】以及【通知栏开关】之外的权限，因为无法提供可靠的判断结果！（该提示仅在测试环境下弹出）").setPositiveButton("朕知道了", null).show();
                } else {
                    LoggerFactory.getTraceLogger().warn((String) "GuideAlivePlugin", "Deprecated jsApi (getAuthStatus) has been called, we only allow it to be used in the following scene: " + this.a);
                }
            }
            PermissionStatus status = ((PermissionGuideService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PermissionGuideService.class.getName())).checkPermissionStatus(authType);
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "status = " + status.getRpcName());
            jsOb.put((String) "authStatus", (Object) status.getRpcName());
            context.sendBridgeResult(jsOb);
            new b("h5_getAuthStatus").a("TransferQRCode").a().a("issue", issue).a("source", source).a("option", option).a("authType", authType).a("authStatus", status.getRpcName()).b();
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "GuideAlivePlugin", e);
        }
    }

    private void h(H5Event event, H5BridgeContext context) {
        JSONObject params = event.getParam();
        if (params == null || context == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "showAuthGuide params is null");
            return;
        }
        JSONObject jsOb = new JSONObject();
        String issue = params.getString("issue");
        String source = params.getString("source");
        String option = params.getString("option");
        String authType = params.containsKey("authType") ? params.get("authType").toString() : null;
        if (TextUtils.isEmpty(authType)) {
            new b("h5_showAuthGuide").a("TransferQRCode").a().a("issue", issue).a("source", source).a("option", option).a("authType", authType).a("return", Boolean.valueOf(true)).b();
            jsOb.put((String) "shown", (Object) Boolean.valueOf(false));
            context.sendBridgeResult(jsOb);
            return;
        }
        String bizType = params.get("bizType").toString();
        LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "showAuthGuide bizType = " + bizType);
        try {
            boolean isShown = ((PermissionGuideService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PermissionGuideService.class.getName())).startPermissionGuide(bizType, new PermissionType[]{PermissionType.valueOf(authType)}, new PermissionGuideCallback() {
                public void onPermissionGuideResult(PermissionType[] permissionTypes, PermissionGuideResult[] permissionGuideResults) {
                    if (permissionTypes == null || permissionGuideResults == null) {
                        LoggerFactory.getTraceLogger().warn((String) "GuideAlivePlugin", (String) "onPermissionGuideResult permissionTypes or permissionGuideResults is null");
                    } else if (permissionTypes.length <= 0 || permissionGuideResults.length <= 0) {
                        LoggerFactory.getTraceLogger().warn((String) "GuideAlivePlugin", (String) "permissionTypes or permissionGuideResults <= 0!");
                    } else {
                        LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "permissionTypes = " + permissionTypes[0].name() + " permissionGuideResults = " + permissionGuideResults[0].name());
                    }
                }
            });
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "showAuthGuide, isShown: " + isShown);
            jsOb.put((String) "shown", (Object) Boolean.valueOf(isShown));
            context.sendBridgeResult(jsOb);
            new b("h5_showAuthGuide").a("TransferQRCode").a().a("issue", issue).a("source", source).a("option", option).a("authType", authType).a("bizType", bizType).a("isShown", Boolean.valueOf(isShown)).b();
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "GuideAlivePlugin", e);
            jsOb.put((String) "shown", (Object) Boolean.valueOf(false));
            context.sendBridgeResult(jsOb);
        }
    }

    private static void a(H5Event event) {
        JSONObject params = event.getParam();
        if (params == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "onEventPageIn params is null");
            return;
        }
        String issue = params.getString("issue");
        String source = params.getString("source");
        new b("h5_onEventPageIn").a("TransferQRCode").a().a("issue", issue).a("source", source).a("option", params.getString("option")).b();
    }

    private static void b(H5Event event) {
        JSONObject params = event.getParam();
        if (params == null) {
            LoggerFactory.getTraceLogger().info("GuideAlivePlugin", "onEventPageOut params is null");
            return;
        }
        String issue = params.getString("issue");
        String source = params.getString("source");
        new b("h5_onEventPageOut").a("TransferQRCode").a().a("issue", issue).a("source", source).a("option", params.getString("option")).b();
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("guideAlivePlugin.pageIn");
        filter.addAction("guideAlivePlugin.isOptionSupport");
        filter.addAction("guideAlivePlugin.getOptionStatus");
        filter.addAction("guideAlivePlugin.showGuide");
        filter.addAction("showAuthGuide");
        filter.addAction("getAuthStatus");
        filter.addAction("guideAlivePlugin.pageOut");
        filter.addAction(ACTION_HAS_PERMISSION_GUIDE_PATH);
        filter.addAction(ACTION_GET_PERMISSION_GUIDE_CONTENT);
        filter.addAction(ACTION_START_PERMISSION_PAGE);
        super.onPrepare(filter);
    }

    private static boolean a() {
        try {
            return LoggingUtil.isDebuggable(LoggerFactory.getLogContext().getApplicationContext());
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("GuideAlivePlugin", "Can't judge apk debuggable state", tr);
            return false;
        }
    }
}
