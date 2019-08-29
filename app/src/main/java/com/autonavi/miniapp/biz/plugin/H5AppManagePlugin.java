package com.autonavi.miniapp.biz.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.util.H5Log;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.biz.manager.MiniAppManager;
import com.autonavi.miniapp.biz.network.AmapRemoteBusiness;
import com.autonavi.miniapp.biz.network.dataobject.MiniAppDO;
import com.autonavi.miniapp.biz.network.dataobject.MiniAppDTO;
import com.autonavi.miniapp.biz.network.request.ListRecommendMiniAppsRequest;
import com.autonavi.miniapp.biz.network.response.ListRecommendMiniAppsResponse;
import com.autonavi.miniapp.biz.network.response.ListUserFavoriteAppsResponse;
import com.autonavi.miniapp.plugin.BasePlugin;
import com.taobao.tao.remotebusiness.IRemoteBaseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.MtopResponse;

public class H5AppManagePlugin extends BasePlugin {
    public static final String DELETE_RECENT_USED_TINY_APP_RECODE = "deleteRecentUsedTinyAppRecode";
    public static final String GET_RECOMMENDED_TINY_APPS = "getRecommendedTinyApps";
    public static final String RECENT_USED_TINY_APPS = "recentUsedTinyApps";
    private static final String RESULTMESSAGE = "message";
    private static final String SUCCESS = "success";
    public static final String TAG = "H5AppManagePlugin";

    public void onPrepare(H5EventFilter h5EventFilter) {
        super.onPrepare(h5EventFilter);
        h5EventFilter.addAction(RECENT_USED_TINY_APPS);
        h5EventFilter.addAction(DELETE_RECENT_USED_TINY_APP_RECODE);
        h5EventFilter.addAction(GET_RECOMMENDED_TINY_APPS);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        String action = h5Event.getAction();
        if (RECENT_USED_TINY_APPS.equals(action)) {
            H5Log.d(TAG, "get action: recentUsedTinyApps");
            JSONObject smallProgramList = MiniAppManager.getInstance().getSmallProgramList();
            JSONObject buildResult = buildResult(smallProgramList.getJSONArray("data"), smallProgramList.getBoolean("success"), smallProgramList.getString("resultMessage"));
            H5Log.d(TAG, String.format("%s execute result : %s", new Object[]{action, buildResult.toJSONString()}));
            h5BridgeContext.sendBridgeResult(buildResult);
            return true;
        } else if (DELETE_RECENT_USED_TINY_APP_RECODE.equals(action)) {
            H5Log.d(TAG, "get action: deleteRecentUsedTinyAppRecode");
            JSONObject deleteRecentUse = deleteRecentUse(h5Event.getParam());
            h5BridgeContext.sendBridgeResult(deleteRecentUse);
            H5Log.d(TAG, String.format("%s execute result : %s", new Object[]{action, deleteRecentUse.toJSONString()}));
            return true;
        } else if (!GET_RECOMMENDED_TINY_APPS.equals(action)) {
            return false;
        } else {
            H5Log.d(TAG, "get action: getRecommendedTinyApps");
            getRecommendTinyApps(h5BridgeContext);
            return true;
        }
    }

    private void getRecommendTinyApps(final H5BridgeContext h5BridgeContext) {
        AmapRemoteBusiness.build(new ListRecommendMiniAppsRequest()).registeListener(new IRemoteBaseListener() {
            public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
                AMapLog.debug("infoservice.miniapp", H5AppManagePlugin.TAG, mtopResponse.toString());
                H5AppManagePlugin.this.callMtopBizJsError((ListRecommendMiniAppsResponse) ffx.a(mtopResponse.getBytedata(), ListRecommendMiniAppsResponse.class), mtopResponse, h5BridgeContext);
            }

            public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
                AMapLog.debug("infoservice.miniapp", H5AppManagePlugin.TAG, mtopResponse.toString());
                JSONObject jSONObject = new JSONObject();
                jSONObject.put((String) "success", (Object) Boolean.TRUE);
                jSONObject.put((String) "resultCode", (Object) GenBusCodeService.CODE_SUCESS);
                jSONObject.put((String) "resultMsg", (Object) "获取成功");
                jSONObject.put((String) "hasKeep", (Object) Boolean.TRUE);
                ArrayList arrayList = new ArrayList();
                for (MiniAppDO miniAppDO : (List) ((ListRecommendMiniAppsResponse) ffx.a(mtopResponse.getBytedata(), ListRecommendMiniAppsResponse.class)).getData().data) {
                    MiniAppDTO miniAppDTO = new MiniAppDTO();
                    miniAppDTO.setAppId(miniAppDO.getAppId());
                    miniAppDTO.setIconUrl(miniAppDO.getAppLogo());
                    miniAppDTO.setName(miniAppDO.getAppName());
                    miniAppDTO.setAppSlogan(miniAppDO.getAppSlogan());
                    arrayList.add(miniAppDTO);
                }
                jSONObject.put((String) "recommendApps", (Object) arrayList);
                h5BridgeContext.sendBridgeResult(jSONObject);
            }

            public void onError(int i, MtopResponse mtopResponse, Object obj) {
                AMapLog.debug("infoservice.miniapp", H5AppManagePlugin.TAG, mtopResponse.toString());
                H5AppManagePlugin.this.callMtopBizJsError((ListUserFavoriteAppsResponse) ffx.a(mtopResponse.getBytedata(), ListUserFavoriteAppsResponse.class), mtopResponse, h5BridgeContext);
            }
        }).startRequest();
    }

    private JSONObject deleteRecentUse(JSONObject jSONObject) {
        String str;
        try {
            str = jSONObject.getString("appId");
        } catch (Exception unused) {
            H5Log.d(TAG, "appId is null");
            str = "";
        }
        if (TextUtils.isEmpty(str)) {
            return buildResult(null, Boolean.FALSE, "");
        }
        MiniAppManager.getInstance().deleteRecentApp(str);
        return buildResult(null, Boolean.TRUE, "");
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public String getEvents() {
        return getEvents(Arrays.asList(new String[]{RECENT_USED_TINY_APPS, DELETE_RECENT_USED_TINY_APP_RECODE, GET_RECOMMENDED_TINY_APPS}));
    }
}
