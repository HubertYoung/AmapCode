package com.autonavi.miniapp.biz.plugin;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.nebulaapp.MiniAppAuthHelper;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthCallback;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.biz.network.AmapRemoteBusiness;
import com.autonavi.miniapp.biz.network.dataobject.MiniAppDO;
import com.autonavi.miniapp.biz.network.dataobject.MiniAppDTO;
import com.autonavi.miniapp.biz.network.request.CreateOrUpdateUserFavoriteRequest;
import com.autonavi.miniapp.biz.network.request.ListUserFavoriteAppsRequest;
import com.autonavi.miniapp.biz.network.response.CreateOrUpdateUserFavoriteResponse;
import com.autonavi.miniapp.biz.network.response.ListUserFavoriteAppsResponse;
import com.autonavi.miniapp.plugin.BasePlugin;
import com.autonavi.miniapp.plugin.util.AMapUserInfoUtil;
import com.taobao.tao.remotebusiness.IRemoteBaseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.MtopResponse;

public class H5FavoritePlugin extends BasePlugin {
    public static final String ADD_FAVORITE = "addFavorite";
    public static final String ADD_TO_FAVORITE = "add2Favorite";
    private static final String ADD_TO_TOP = "add2Top";
    private static final String CANCEL_FAVORITE = "cancelFavorite";
    private static final String CANCEL_KEEP_FAVORITE = "cancelKeepFavorite";
    private static final String CANCEL_TOP = "cancelTop";
    private static final String FAVORITE_NOTIFICATION = "favoriteNotify";
    public static final String POST_FAVORITE_NOTIFY = "postFavoriteNotification";
    private static final String QUERY_ALL_KEEP_FAVORITE = "queryAllFavorite";
    private static final String QUERY_IS_KEEP_FAVORITE = "queryIsFavorite";
    /* access modifiers changed from: private */
    public static final String TAG = "H5FavoritePlugin";

    public void onPrepare(H5EventFilter h5EventFilter) {
        super.onPrepare(h5EventFilter);
        h5EventFilter.addAction(ADD_TO_FAVORITE);
        h5EventFilter.addAction(ADD_FAVORITE);
        h5EventFilter.addAction(CANCEL_KEEP_FAVORITE);
        h5EventFilter.addAction(CANCEL_FAVORITE);
        h5EventFilter.addAction(QUERY_IS_KEEP_FAVORITE);
        h5EventFilter.addAction(QUERY_ALL_KEEP_FAVORITE);
        h5EventFilter.addAction(ADD_TO_TOP);
        h5EventFilter.addAction(CANCEL_TOP);
        h5EventFilter.addAction(FAVORITE_NOTIFICATION);
        h5EventFilter.addAction(POST_FAVORITE_NOTIFY);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        String action = h5Event.getAction();
        if (ADD_TO_FAVORITE.equals(action)) {
            addFavorite(h5Event, h5BridgeContext, false);
            return true;
        } else if (ADD_FAVORITE.equals(action)) {
            addFavorite(h5Event, h5BridgeContext, true);
            return true;
        } else if (CANCEL_KEEP_FAVORITE.equals(action)) {
            cancelFavorite(h5Event, h5BridgeContext, false);
            return true;
        } else if (CANCEL_FAVORITE.equals(action)) {
            cancelFavorite(h5Event, h5BridgeContext, true);
            return true;
        } else if (QUERY_IS_KEEP_FAVORITE.equals(action)) {
            return true;
        } else {
            if (QUERY_ALL_KEEP_FAVORITE.equals(action)) {
                queryAllFavorite(h5Event, h5BridgeContext);
                return true;
            } else if (ADD_TO_TOP.equals(action)) {
                add2Top(h5Event, h5BridgeContext);
                return true;
            } else if (!CANCEL_TOP.equals(action)) {
                return FAVORITE_NOTIFICATION.equals(action) || POST_FAVORITE_NOTIFY.equals(action);
            } else {
                cancelTop(h5Event, h5BridgeContext);
                return true;
            }
        }
    }

    private void cancelTop(H5Event h5Event, final H5BridgeContext h5BridgeContext) {
        CreateOrUpdateUserFavoriteRequest createOrUpdateUserFavoriteRequest = new CreateOrUpdateUserFavoriteRequest();
        createOrUpdateUserFavoriteRequest.setAppId((String) ((JSONArray) h5Event.getParam().get("appIds")).get(0));
        if (AMapUserInfoUtil.getInstance().isLogin()) {
            createOrUpdateUserFavoriteRequest.setUid(AMapUserInfoUtil.getInstance().getUserInfo().a);
            createOrUpdateUserFavoriteRequest.setAlipayUid(AMapUserInfoUtil.getInstance().getUserInfo().u);
            createOrUpdateUserFavoriteRequest.setSticky(Long.valueOf(0));
            AmapRemoteBusiness.build(createOrUpdateUserFavoriteRequest).registeListener(new IRemoteBaseListener() {
                public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    H5FavoritePlugin.this.callMtopSysJsError(mtopResponse, h5BridgeContext);
                }

                public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put((String) "success", (Object) Boolean.TRUE);
                    jSONObject.put((String) "resultCode", (Object) GenBusCodeService.CODE_SUCESS);
                    jSONObject.put((String) "resultMsg", (Object) "取消置顶成功");
                    h5BridgeContext.sendBridgeResult(jSONObject);
                }

                public void onError(int i, MtopResponse mtopResponse, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    H5FavoritePlugin.this.callMtopSysJsError(mtopResponse, h5BridgeContext);
                }
            }).startRequest();
            return;
        }
        callNotLoginError(h5BridgeContext);
    }

    private void add2Top(H5Event h5Event, final H5BridgeContext h5BridgeContext) {
        CreateOrUpdateUserFavoriteRequest createOrUpdateUserFavoriteRequest = new CreateOrUpdateUserFavoriteRequest();
        createOrUpdateUserFavoriteRequest.setAppId((String) ((JSONArray) h5Event.getParam().get("appIds")).get(0));
        if (AMapUserInfoUtil.getInstance().isLogin()) {
            createOrUpdateUserFavoriteRequest.setUid(AMapUserInfoUtil.getInstance().getUserInfo().a);
            createOrUpdateUserFavoriteRequest.setAlipayUid(AMapUserInfoUtil.getInstance().getUserInfo().u);
            createOrUpdateUserFavoriteRequest.setSticky(Long.valueOf(1));
            AmapRemoteBusiness.build(createOrUpdateUserFavoriteRequest).registeListener(new IRemoteBaseListener() {
                public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    H5FavoritePlugin.this.callMtopSysJsError(mtopResponse, h5BridgeContext);
                }

                public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put((String) "success", (Object) Boolean.TRUE);
                    jSONObject.put((String) "resultCode", (Object) GenBusCodeService.CODE_SUCESS);
                    jSONObject.put((String) "resultMsg", (Object) "置顶成功");
                    h5BridgeContext.sendBridgeResult(jSONObject);
                }

                public void onError(int i, MtopResponse mtopResponse, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    H5FavoritePlugin.this.callMtopSysJsError(mtopResponse, h5BridgeContext);
                }
            }).startRequest();
            return;
        }
        callNotLoginError(h5BridgeContext);
    }

    private void cancelFavorite(H5Event h5Event, final H5BridgeContext h5BridgeContext, boolean z) {
        CreateOrUpdateUserFavoriteRequest createOrUpdateUserFavoriteRequest = new CreateOrUpdateUserFavoriteRequest();
        if (z) {
            createOrUpdateUserFavoriteRequest.setAppId((String) ((JSONArray) h5Event.getParam().get("appIds")).get(0));
        } else {
            createOrUpdateUserFavoriteRequest.setAppId(h5Event.getH5page().getPageData().getAppId());
        }
        if (AMapUserInfoUtil.getInstance().isLogin()) {
            createOrUpdateUserFavoriteRequest.setUid(AMapUserInfoUtil.getInstance().getUserInfo().a);
            createOrUpdateUserFavoriteRequest.setAlipayUid(AMapUserInfoUtil.getInstance().getUserInfo().u);
            createOrUpdateUserFavoriteRequest.setFavorite(Long.valueOf(0));
            AmapRemoteBusiness.build(createOrUpdateUserFavoriteRequest).registeListener(new IRemoteBaseListener() {
                public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    H5FavoritePlugin.this.callMtopSysJsError(mtopResponse, h5BridgeContext);
                }

                public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put((String) "success", (Object) Boolean.TRUE);
                    jSONObject.put((String) "resultCode", (Object) GenBusCodeService.CODE_SUCESS);
                    jSONObject.put((String) "resultMsg", (Object) "取消收藏成功");
                    h5BridgeContext.sendBridgeResult(jSONObject);
                }

                public void onError(int i, MtopResponse mtopResponse, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    H5FavoritePlugin.this.callMtopSysJsError(mtopResponse, h5BridgeContext);
                }
            }).startRequest();
            return;
        }
        callNotLoginError(h5BridgeContext);
    }

    private void addFavorite(H5Event h5Event, final H5BridgeContext h5BridgeContext, boolean z) {
        final CreateOrUpdateUserFavoriteRequest createOrUpdateUserFavoriteRequest = new CreateOrUpdateUserFavoriteRequest();
        if (z) {
            createOrUpdateUserFavoriteRequest.setAppId((String) ((JSONArray) h5Event.getParam().get("appIds")).get(0));
        } else {
            createOrUpdateUserFavoriteRequest.setAppId(h5Event.getH5page().getPageData().getAppId());
        }
        createOrUpdateUserFavoriteRequest.setFavorite(Long.valueOf(1));
        if (AMapUserInfoUtil.getInstance().isLogin()) {
            createOrUpdateUserFavoriteRequest.setUid(AMapUserInfoUtil.getInstance().getUserInfo().a);
            createOrUpdateUserFavoriteRequest.setAlipayUid(AMapUserInfoUtil.getInstance().getUserInfo().u);
            add2FavoriteMtopReq(h5BridgeContext, createOrUpdateUserFavoriteRequest);
            return;
        }
        new MiniAppAuthHelper().authMiniApp(new IAccountOAuthCallback() {
            public void onAuthResult(String str, String str2, String str3, Bundle bundle) {
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                    TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                    String access$000 = H5FavoritePlugin.TAG;
                    StringBuilder sb = new StringBuilder("auth failed, amapUid:");
                    sb.append(str);
                    sb.append(", alipayUid:");
                    sb.append(str2);
                    sb.append(", token:");
                    sb.append(str3);
                    traceLogger.debug(access$000, sb.toString());
                    H5FavoritePlugin.this.callMtopBizJsError("登录后方可使用收藏功能", h5BridgeContext);
                    return;
                }
                createOrUpdateUserFavoriteRequest.setUid(str);
                createOrUpdateUserFavoriteRequest.setAlipayUid(str2);
                H5FavoritePlugin.this.add2FavoriteMtopReq(h5BridgeContext, createOrUpdateUserFavoriteRequest);
            }
        }, null, false, 0);
    }

    /* access modifiers changed from: private */
    public void add2FavoriteMtopReq(final H5BridgeContext h5BridgeContext, CreateOrUpdateUserFavoriteRequest createOrUpdateUserFavoriteRequest) {
        AmapRemoteBusiness.build(createOrUpdateUserFavoriteRequest).registeListener(new IRemoteBaseListener() {
            public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
                AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                H5FavoritePlugin.this.callMtopBizJsError((CreateOrUpdateUserFavoriteResponse) ffx.a(mtopResponse.getBytedata(), CreateOrUpdateUserFavoriteResponse.class), mtopResponse, h5BridgeContext);
            }

            public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
                AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                JSONObject jSONObject = new JSONObject();
                jSONObject.put((String) "success", (Object) Boolean.TRUE);
                jSONObject.put((String) "resultCode", (Object) GenBusCodeService.CODE_SUCESS);
                jSONObject.put((String) "resultMsg", (Object) "收藏成功");
                h5BridgeContext.sendBridgeResult(jSONObject);
            }

            public void onError(int i, MtopResponse mtopResponse, Object obj) {
                AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                H5FavoritePlugin.this.callMtopBizJsError((CreateOrUpdateUserFavoriteResponse) ffx.a(mtopResponse.getBytedata(), CreateOrUpdateUserFavoriteResponse.class), mtopResponse, h5BridgeContext);
            }
        }).startRequest();
    }

    private void queryAllFavorite(H5Event h5Event, final H5BridgeContext h5BridgeContext) {
        ListUserFavoriteAppsRequest listUserFavoriteAppsRequest = new ListUserFavoriteAppsRequest();
        if (AMapUserInfoUtil.getInstance().isLogin()) {
            listUserFavoriteAppsRequest.setUid(AMapUserInfoUtil.getInstance().getUserInfo().a);
            AmapRemoteBusiness.build(listUserFavoriteAppsRequest).registeListener(new IRemoteBaseListener() {
                public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    H5FavoritePlugin.this.callMtopBizJsError((ListUserFavoriteAppsResponse) ffx.a(mtopResponse.getBytedata(), ListUserFavoriteAppsResponse.class), mtopResponse, h5BridgeContext);
                }

                public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put((String) "success", (Object) Boolean.TRUE);
                    jSONObject.put((String) "resultCode", (Object) GenBusCodeService.CODE_SUCESS);
                    jSONObject.put((String) "resultMsg", (Object) "获取成功");
                    jSONObject.put((String) "hasKeep", (Object) Boolean.TRUE);
                    ArrayList arrayList = new ArrayList();
                    for (MiniAppDO miniAppDO : (List) ((ListUserFavoriteAppsResponse) ffx.a(mtopResponse.getBytedata(), ListUserFavoriteAppsResponse.class)).getData().data) {
                        MiniAppDTO miniAppDTO = new MiniAppDTO();
                        miniAppDTO.setAppId(miniAppDO.getAppId());
                        miniAppDTO.setIconUrl(miniAppDO.getAppLogo());
                        miniAppDTO.setName(miniAppDO.getAppName());
                        boolean z = true;
                        if (1 != miniAppDO.getSticky()) {
                            z = false;
                        }
                        miniAppDTO.setTop(z);
                        miniAppDTO.setAppSlogan(miniAppDO.getAppSlogan());
                        arrayList.add(miniAppDTO);
                    }
                    jSONObject.put((String) "keepMiniAppInfoResultList", (Object) arrayList);
                    h5BridgeContext.sendBridgeResult(jSONObject);
                }

                public void onError(int i, MtopResponse mtopResponse, Object obj) {
                    AMapLog.debug("infoservice.miniapp", H5FavoritePlugin.TAG, mtopResponse.toString());
                    H5FavoritePlugin.this.callMtopBizJsError((ListUserFavoriteAppsResponse) ffx.a(mtopResponse.getBytedata(), ListUserFavoriteAppsResponse.class), mtopResponse, h5BridgeContext);
                }
            }).startRequest();
            return;
        }
        callNotLoginError(h5BridgeContext);
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public String getEvents() {
        return getEvents(Arrays.asList(new String[]{QUERY_ALL_KEEP_FAVORITE, ADD_TO_FAVORITE, ADD_FAVORITE, CANCEL_KEEP_FAVORITE, CANCEL_FAVORITE, ADD_TO_TOP, CANCEL_TOP}));
    }
}
