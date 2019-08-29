package com.alipay.mobile.nebula.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.log.linkmonitor.H5LinkMonitor;
import com.alipay.mobile.nebula.log.linkmonitor.H5LinkMonitor.ExpectationListener;
import com.alipay.mobile.nebula.log.linkmonitor.H5LinkMonitorImpl;
import com.alipay.mobile.nebula.log.linkmonitor.H5LinkMonitorTree;
import com.alipay.mobile.nebula.log.linkmonitor.H5LinkNode;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5MainLinkMonitor {
    public static final String CLIENT_CALL_NODE = "client_call";
    public static final String FIRST_REQUEST_HANDLE = "firstRequest_handle";
    public static int FIRST_REQUEST_HANDLE_TIME = 3000;
    public static final String FIRST_REQUEST_START = "firstRequest_start";
    public static int FIRST_REQUEST_START_TIME = 3000;
    public static final String LINK_ID = "app_start";
    public static final String PAGE_APPEAR = "page_appear";
    public static int PAGE_APPEAR_TIME = 3000;
    public static final String PAGE_FAIL = "page_fail";
    public static final String PAGE_FINISH = "page_finish";
    public static int PAGE_FINISH_TIME = 8000;
    public static final String PAGE_START = "page_start";
    public static int PAGE_START_TIME = 3000;
    public static final String SESSION_CREATE = "session_create";
    public static int SESSION_CREATE_TIME = 5000;
    private static final String TAG = "H5LinkMonitorImpl";
    public static JSONObject mainLinkConfig;
    private static boolean mainLinkEnable = false;
    private static Map<String, H5LinkMonitor> monitorMap = new ConcurrentHashMap();

    private static boolean enableMainLinkMonitor() {
        if (mainLinkConfig != null) {
            return mainLinkEnable;
        }
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            JSONObject config = h5ConfigProvider.getConfigJSONObject("h5_mainLinkConfig");
            if (config != null && !config.isEmpty()) {
                mainLinkConfig = config;
                JSONArray jsonArray = H5Utils.getJSONArray(config, "enable", null);
                setTimeout(H5Utils.getJSONObject(config, "timeout", null));
                if (!jsonArray.isEmpty()) {
                    boolean contains = jsonArray.contains(LINK_ID);
                    mainLinkEnable = contains;
                    return contains;
                }
            }
        }
        return false;
    }

    private static void setTimeout(JSONObject config) {
        if (config != null && !config.isEmpty()) {
            int sessionCreateT = H5Utils.getInt(config, (String) "client_call^session_create");
            if (sessionCreateT > 0) {
                SESSION_CREATE_TIME = sessionCreateT;
            }
            int pageAppearT = H5Utils.getInt(config, (String) "session_create^page_appear");
            if (pageAppearT > 0) {
                PAGE_APPEAR_TIME = pageAppearT;
            }
            int firstRequestHandleT = H5Utils.getInt(config, (String) "session_create^firstRequest_handle");
            if (firstRequestHandleT > 0) {
                FIRST_REQUEST_HANDLE_TIME = firstRequestHandleT;
            }
            int firstRequestStartT = H5Utils.getInt(config, (String) "firstRequest_handle^firstRequest_start");
            if (firstRequestStartT > 0) {
                FIRST_REQUEST_START_TIME = firstRequestStartT;
            }
            int pageStartT = H5Utils.getInt(config, (String) "firstRequest_start^page_start");
            if (pageStartT > 0) {
                PAGE_START_TIME = pageStartT;
            }
            int pageFinishT = H5Utils.getInt(config, (String) "page_start^page_finish");
            if (pageFinishT > 0) {
                PAGE_FINISH_TIME = pageFinishT;
            }
        }
    }

    public static void startMainLinkMonitor(String appId) {
        if (!enableMainLinkMonitor()) {
            H5Log.d(TAG, "enableMainLinkMonitor false, return");
            return;
        }
        H5LinkMonitor linkMonitor = new H5LinkMonitorImpl();
        monitorMap.put(appId, linkMonitor);
        linkMonitor.addLinkMonitorTree(LINK_ID);
        linkMonitor.setLinkData(LINK_ID, CLIENT_CALL_NODE, System.currentTimeMillis(), null);
        linkMonitor.createExpectation(LINK_ID, SESSION_CREATE, CLIENT_CALL_NODE, SESSION_CREATE_TIME);
    }

    public static void cancelLinkMonitor(String appId) {
        H5LinkMonitor linkMonitor = monitorMap.remove(appId);
        if (linkMonitor != null) {
            linkMonitor.cancelLinkMonitor(LINK_ID);
        }
    }

    public static void triggerSessionCreateLink(H5Session session, String appId) {
        H5LinkMonitor linkMonitor = monitorMap.remove(appId);
        if (linkMonitor != null) {
            session.setH5LinkMonitor(linkMonitor);
            linkMonitor.fulfillExpectation(LINK_ID, SESSION_CREATE, System.currentTimeMillis());
            linkMonitor.createExpectation(LINK_ID, PAGE_APPEAR, SESSION_CREATE, PAGE_APPEAR_TIME);
            linkMonitor.createExpectation(LINK_ID, FIRST_REQUEST_HANDLE, SESSION_CREATE, FIRST_REQUEST_HANDLE_TIME);
        }
    }

    public static void triggerAppearLink(H5Page h5Page) {
        if (checkValidity(h5Page)) {
            h5Page.getSession().getH5LinkMonitor().fulfillExpectation(LINK_ID, PAGE_APPEAR, System.currentTimeMillis());
        }
    }

    public static void triggerHandleUrlLink(H5Page h5Page) {
        if (checkValidity(h5Page)) {
            triggerNodeEvent(h5Page.getSession().getH5LinkMonitor(), FIRST_REQUEST_HANDLE, FIRST_REQUEST_START, FIRST_REQUEST_START_TIME);
        }
    }

    public static void triggerLoadUrlLink(H5Page h5Page) {
        if (checkValidity(h5Page)) {
            triggerNodeEvent(h5Page.getSession().getH5LinkMonitor(), FIRST_REQUEST_START, PAGE_START, PAGE_START_TIME);
        }
    }

    public static void triggerPageStartedLink(H5Page h5Page) {
        if (checkValidity(h5Page)) {
            H5LinkMonitor linkMonitor = h5Page.getSession().getH5LinkMonitor();
            linkMonitor.fulfillExpectation(LINK_ID, PAGE_START, System.currentTimeMillis());
            linkMonitor.checkExpectation(LINK_ID, PAGE_FINISH, PAGE_FINISH_TIME, new ExpectationListener() {
                public final void checkExpectation(H5LinkMonitorTree linkMonitorTree) {
                    H5LinkNode pageFinishNode = linkMonitorTree.getLinkNodeByName(H5MainLinkMonitor.PAGE_FINISH);
                    H5LinkNode pageFailNode = linkMonitorTree.getLinkNodeByName(H5MainLinkMonitor.PAGE_FAIL);
                    if (pageFinishNode != null && pageFinishNode.getTriggerTime() > 0) {
                        H5Log.d(H5MainLinkMonitor.TAG, "checkExpectation nodeName : " + pageFinishNode.getNodeName() + " triggerTime : " + pageFinishNode.getTriggerTime());
                    } else if (pageFailNode == null || pageFailNode.getTriggerTime() <= 0) {
                        linkMonitorTree.logExpectationFail(H5MainLinkMonitor.LINK_ID, H5MainLinkMonitor.PAGE_FINISH, H5MainLinkMonitor.PAGE_FINISH_TIME);
                    } else {
                        H5Log.d(H5MainLinkMonitor.TAG, "checkExpectation nodeName : " + pageFailNode.getNodeName() + " triggerTime : " + pageFailNode.getTriggerTime());
                    }
                }
            });
        }
    }

    public static void triggerPageFinishLink(H5Page h5Page) {
        if (checkValidity(h5Page)) {
            H5LinkMonitor linkMonitor = h5Page.getSession().getH5LinkMonitor();
            linkMonitor.setLinkData(LINK_ID, PAGE_FINISH, System.currentTimeMillis(), PAGE_START);
            linkMonitor.setLinkCompleted(true);
            logLinkTimingStatistic(h5Page, linkMonitor);
        }
    }

    public static void triggerPageFailLink(H5Page h5Page) {
        if (checkValidity(h5Page)) {
            H5LinkMonitor linkMonitor = h5Page.getSession().getH5LinkMonitor();
            linkMonitor.setLinkData(LINK_ID, PAGE_FAIL, System.currentTimeMillis(), PAGE_START);
            linkMonitor.setLinkCompleted(true);
            logLinkTimingStatistic(h5Page, linkMonitor);
        }
    }

    private static void logLinkTimingStatistic(H5Page h5Page, H5LinkMonitor h5LinkMonitor) {
        H5LogData logData = H5LogData.seedId("H5_LINK_TIMING_STATISTIC").param3().add("linkId", LINK_ID);
        List nodeList = h5LinkMonitor.getLinkData(LINK_ID);
        if (nodeList != null && nodeList.size() > 0) {
            for (H5LinkNode linkNode : nodeList) {
                logData.param3().add(linkNode.getNodeName(), Long.valueOf(linkNode.getTriggerTime()));
            }
        }
        if (!(h5Page == null || h5Page.getPageData() == null)) {
            logData.param4().addUniteParam(h5Page.getPageData());
        }
        H5LogUtil.logNebulaTech(logData);
    }

    private static boolean checkValidity(H5Page h5Page) {
        if (h5Page == null || h5Page.getSession() == null || h5Page.getSession().getH5LinkMonitor() == null || h5Page.getSession().getH5LinkMonitor().linkCompleted()) {
            return false;
        }
        return true;
    }

    private static void triggerNodeEvent(H5LinkMonitor linkMonitor, String nodeName, String nextNodeName, int timeout) {
        if (linkMonitor != null) {
            linkMonitor.fulfillExpectation(LINK_ID, nodeName, System.currentTimeMillis());
            linkMonitor.createExpectation(LINK_ID, nextNodeName, nodeName, timeout);
        }
    }
}
