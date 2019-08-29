package com.alipay.android.nebulaapp;

import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppBaseAdvice;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.config.TinyAppConfig;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.miniapp.biz.db.model.RecentSmallProModel;
import com.autonavi.miniapp.biz.manager.MiniAppManager;
import com.autonavi.miniapp.biz.network.AmapRemoteBusiness;
import com.autonavi.miniapp.biz.network.request.SyncRecentUseRequest;
import com.taobao.tao.remotebusiness.IRemoteBaseListener;
import java.util.ArrayList;
import java.util.List;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.MtopResponse;

public class AmapH5StartAppAdviceImpl extends H5StartAppBaseAdvice {
    public static final String TAG = "AmapH5StartAppAdviceImpl";
    private static List<String> mDefaultRecentBlackList;
    private String lastCallAppid;
    private long lastCallTime;

    static {
        ArrayList arrayList = new ArrayList();
        mDefaultRecentBlackList = arrayList;
        arrayList.add(Config.MY_LITTLE_APP_ID);
        mDefaultRecentBlackList.add(Config.ABOUT_APP_ID);
        mDefaultRecentBlackList.add(Config.CLOUD_CUSTOMER_SERVICE_APP_ID);
        mDefaultRecentBlackList.add(Config.FEEDBACK_APP_ID);
    }

    public boolean canHandler(String str) {
        ApplicationDescription findDescriptionByAppId = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findDescriptionByAppId(str);
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(Token.SEPARATOR);
        sb.append(findDescriptionByAppId);
        H5Log.d(str2, sb.toString());
        return findDescriptionByAppId != null && "H5App".equalsIgnoreCase(findDescriptionByAppId.getEngineType());
    }

    public void onCallBefore(String str, Object obj, Object[] objArr) {
        super.onCallBefore(str, obj, objArr);
        if ("yes".equals(objArr[2].getString(H5AppHandler.CHECK_KEY))) {
            String str2 = objArr[1];
            this.lastCallTime = System.currentTimeMillis();
            this.lastCallAppid = str2;
            String str3 = TAG;
            StringBuilder sb = new StringBuilder("lastCallTime=");
            sb.append(this.lastCallTime);
            H5Log.d(str3, sb.toString());
            String str4 = TAG;
            StringBuilder sb2 = new StringBuilder("lastCallAppid=");
            sb2.append(this.lastCallAppid);
            H5Log.d(str4, sb2.toString());
            String adiu = NetworkParam.getAdiu();
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            String string = objArr[2].getString("chInfo");
            List<String> recentMiniAppBlackList = TinyAppConfig.getInstance().getRecentMiniAppBlackList();
            if (!recentMiniAppBlackList.isEmpty() && recentMiniAppBlackList.contains(str2)) {
                String str5 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(" is in cloud recent_blacklist, skip addRecentApp.");
                H5Log.d(str5, sb3.toString());
            } else if (mDefaultRecentBlackList.contains(str2)) {
                String str6 = TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str2);
                sb4.append(" is in default recent_blacklist, skip addRecentApp.");
                H5Log.d(str6, sb4.toString());
            } else {
                H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                if (h5AppProvider != null) {
                    AppInfo appInfo = h5AppProvider.getAppInfo(str2);
                    if (appInfo != null) {
                        RecentSmallProModel recentSmallProModel = new RecentSmallProModel();
                        recentSmallProModel.setAppId(appInfo.app_id);
                        recentSmallProModel.setName(appInfo.name);
                        recentSmallProModel.setIconUrl(appInfo.icon_url);
                        recentSmallProModel.setSlogan(appInfo.slogan);
                        recentSmallProModel.setChInfo(string);
                        recentSmallProModel.setLastClickTime(Long.valueOf(currentTimeMillis));
                        MiniAppManager.getInstance().addRecentApp(str2, recentSmallProModel);
                        H5Log.d(TAG, "addRecentApp appId ".concat(String.valueOf(str2)));
                        H5Log.d(TAG, "addRecentApp model ".concat(String.valueOf(recentSmallProModel)));
                    }
                }
                SyncRecentUseRequest syncRecentUseRequest = new SyncRecentUseRequest();
                syncRecentUseRequest.setAdiu(adiu);
                syncRecentUseRequest.setAppId(str2);
                syncRecentUseRequest.setChinfo(string);
                syncRecentUseRequest.setLastClickTime(currentTimeMillis);
                syncRecentUseRequest.setRecentUseJson(MiniAppManager.getInstance().recentMiniAppsSync());
                AmapRemoteBusiness.build(syncRecentUseRequest).registeListener(new IRemoteBaseListener() {
                    public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
                        String str = AmapH5StartAppAdviceImpl.TAG;
                        StringBuilder sb = new StringBuilder("SyncRecentUseRequest onSystemError ");
                        sb.append(mtopResponse.getRetMsg());
                        H5Log.d(str, sb.toString());
                    }

                    public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
                        H5Log.d(AmapH5StartAppAdviceImpl.TAG, "SyncRecentUseRequest onSuccess");
                    }

                    public void onError(int i, MtopResponse mtopResponse, Object obj) {
                        String str = AmapH5StartAppAdviceImpl.TAG;
                        StringBuilder sb = new StringBuilder("SyncRecentUseRequest onError ");
                        sb.append(mtopResponse.getRetMsg());
                        H5Log.d(str, sb.toString());
                    }
                }).startRequest();
            }
        }
    }

    public void onCallAfter(String str, Object obj, Object[] objArr) {
        super.onCallAfter(str, obj, objArr);
    }

    public void onExecutionBefore(String str, Object obj, Object[] objArr) {
        super.onExecutionBefore(str, obj, objArr);
    }

    public void onExecutionAfter(String str, Object obj, Object[] objArr) {
        super.onExecutionAfter(str, obj, objArr);
    }
}
