package com.autonavi.miniapp.plugin.mtop;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5AvailablePageData;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.miniapp.plugin.BasePlugin;
import java.util.ArrayList;
import java.util.List;
import mtopsdk.mtop.domain.MethodEnum;

public class H5MtopPlugin extends BasePlugin {
    private static final String API_NAME = "apiName";
    private static final String API_VERSION = "apiVersion";
    private static final String HEADERS = "headers";
    private static final String ISV_ACCESS_TOKEN = "isvAccessToken";
    private static final String ISV_OPEN_APPKEY = "isvOpenAppkey";
    private static final String MTOP = "mtop";
    private static final String NEED_ECODE_SIGN = "needEcodeSign";
    private static final String NEED_HTTPS = "needHttps";
    private static final String NEED_SPDY = "needSpdy";
    private static final String NEED_WUA = "needWua";
    public static final String TAG = "H5MtopPlugin";
    private static final String TTID = "ttid";
    private static final String TYPE = "type";
    private static final String USE_POST = "usePost";
    private List<String> mActions = new ArrayList();

    class MTopRequest implements Runnable {
        H5BridgeContext bridgeContext;
        H5Event event = null;

        public MTopRequest(H5Event h5Event, H5BridgeContext h5BridgeContext) {
            this.event = h5Event;
            this.bridgeContext = h5BridgeContext;
        }

        public void run() {
            String str;
            if (this.event != null && this.bridgeContext != null) {
                JSONObject param = this.event.getParam();
                H5CoreNode target = this.event.getTarget();
                MtopService mtopService = (MtopService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MtopService.class.getName());
                if (mtopService != null) {
                    mtopService.setUseAlipaySession(true);
                    if (!(target instanceof H5Page)) {
                        this.bridgeContext.sendError(this.event, Error.INVALID_PARAM);
                        return;
                    }
                    H5AvailablePageData availablePageData = ((H5Page) target).getAvailablePageData();
                    if (availablePageData != null) {
                        availablePageData.reportReqStart();
                    }
                    String string = H5Utils.getString(param, (String) "apiName");
                    if (TextUtils.isEmpty(string)) {
                        this.bridgeContext.sendError(this.event, Error.INVALID_PARAM);
                        return;
                    }
                    String string2 = H5Utils.getString(param, (String) "apiVersion");
                    if (TextUtils.isEmpty(string2)) {
                        string2 = "*";
                    } else {
                        param.remove("apiVersion");
                    }
                    MethodEnum methodEnum = H5Utils.getBoolean(param, (String) H5MtopPlugin.USE_POST, false) ? MethodEnum.POST : MethodEnum.GET;
                    boolean z = H5Utils.getBoolean(param, (String) H5MtopPlugin.NEED_ECODE_SIGN, false);
                    boolean z2 = H5Utils.getBoolean(param, (String) H5MtopPlugin.NEED_WUA, false);
                    boolean z3 = H5Utils.getBoolean(param, (String) H5MtopPlugin.NEED_HTTPS, true);
                    boolean z4 = H5Utils.getBoolean(param, (String) H5MtopPlugin.NEED_SPDY, true);
                    String string3 = H5Utils.getString(param, (String) H5MtopPlugin.ISV_OPEN_APPKEY);
                    String string4 = H5Utils.getString(param, (String) H5MtopPlugin.ISV_ACCESS_TOKEN);
                    String string5 = H5Utils.getString(param, (String) "type");
                    String string6 = H5Utils.getString(param, (String) "tb_eagleeyex_scm_project");
                    long currentTimeMillis = System.currentTimeMillis();
                    JSONObject jSONObject = param.getJSONObject("data");
                    JSONObject jSONObject2 = param.getJSONObject(H5MtopPlugin.HEADERS);
                    String string7 = H5Utils.getString(param, (String) "ttid");
                    if (TextUtils.isEmpty(string7)) {
                        string7 = H5Utils.getString(jSONObject, (String) "ttid");
                    }
                    String str2 = string7;
                    if (jSONObject != null) {
                        try {
                            str = jSONObject.toJSONString();
                        } catch (Throwable th) {
                            H5Log.e(H5MtopPlugin.TAG, "exception detail", th);
                        }
                    } else {
                        str = null;
                    }
                    H5Log.d(H5MtopPlugin.TAG, string6);
                    Pair<Handler, JSONObject> syncRequestFastJson = mtopService.syncRequestFastJson(string, string2, z, z2, z3, z4, str, str2, jSONObject2, string5, string6, string3, string4, methodEnum);
                    if (syncRequestFastJson != null) {
                        final JSONObject jSONObject3 = (JSONObject) syncRequestFastJson.second;
                        if (jSONObject3 != null) {
                            Handler handler = (Handler) syncRequestFastJson.first;
                            if (handler != null) {
                                handler.post(new Runnable() {
                                    public void run() {
                                        MTopRequest.this.bridgeContext.sendBridgeResult(jSONObject3);
                                    }
                                });
                                return;
                            } else {
                                this.bridgeContext.sendBridgeResult(jSONObject3);
                                return;
                            }
                        } else {
                            H5Log.e((String) H5MtopPlugin.TAG, (String) "mtop syncRequestFastJson response is null");
                        }
                    } else {
                        H5Log.e((String) H5MtopPlugin.TAG, (String) "mtop syncRequestFastJson returns null");
                    }
                    StringBuilder sb = new StringBuilder("mtop request time ");
                    sb.append(System.currentTimeMillis() - currentTimeMillis);
                    H5Log.d(H5MtopPlugin.TAG, sb.toString());
                    if (availablePageData != null) {
                        availablePageData.reportReqEnd();
                    }
                    this.bridgeContext.sendError(this.event, Error.UNKNOWN_ERROR);
                }
            }
        }
    }

    public H5MtopPlugin() {
        this.mActions.add(MTOP);
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        super.onPrepare(h5EventFilter);
        h5EventFilter.addAction(MTOP);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (!MTOP.equals(h5Event.getAction())) {
            return false;
        }
        mtop(h5Event, h5BridgeContext);
        return true;
    }

    private void mtop(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        H5Utils.getExecutor("RPC").execute(new MTopRequest(h5Event, h5BridgeContext));
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public String getEvents() {
        return getEvents(this.mActions);
    }
}
