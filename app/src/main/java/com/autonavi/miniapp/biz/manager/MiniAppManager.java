package com.autonavi.miniapp.biz.manager;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.nebulaapp.MiniAppDataUtil;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.miniapp.biz.db.dao.DaoHelper;
import com.autonavi.miniapp.biz.db.model.RecentSmallProModel;
import com.autonavi.miniapp.biz.network.dataobject.RecentUploadDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MiniAppManager {
    private static final int MAXNUM_RECENT_ITEMS = 20;
    private static final String TAG = "com.autonavi.miniapp.biz.manager.MiniAppManager";
    /* access modifiers changed from: private */
    public final Object LOCK = new Object();
    /* access modifiers changed from: private */
    public final ConcurrentHashMap<String, String> debugModeApps = new ConcurrentHashMap<>();
    private boolean isRecentInit = false;
    /* access modifiers changed from: private */
    public List<RecentSmallProModel> recentList = new ArrayList();

    static class MiniAppManagerHolder {
        public static MiniAppManager miniAppManager = new MiniAppManager();

        private MiniAppManagerHolder() {
        }
    }

    public static MiniAppManager getInstance() {
        return MiniAppManagerHolder.miniAppManager;
    }

    /* access modifiers changed from: private */
    public List<RecentSmallProModel> getRecentList() {
        if (!this.isRecentInit) {
            synchronized (this.LOCK) {
                this.recentList = DaoHelper.getRecentList(getUserId());
                for (RecentSmallProModel appId : this.recentList) {
                    String appId2 = appId.getAppId();
                    if (!TextUtils.isEmpty(appId2) && appId2.contains("-")) {
                        String substring = appId2.substring(0, appId2.indexOf("-"));
                        if (!TextUtils.isEmpty(substring)) {
                            this.debugModeApps.put(substring, substring);
                        }
                    }
                }
                this.isRecentInit = true;
            }
        }
        return this.recentList;
    }

    public synchronized void deleteRecentApp(final String str) {
        ahl.a(new a<Object>() {
            public Object doBackground() throws Exception {
                synchronized (MiniAppManager.this.LOCK) {
                    MiniAppManager.this.recentList = MiniAppManager.this.getRecentList();
                    if (MiniAppManager.this.recentList != null) {
                        int size = MiniAppManager.this.recentList.size();
                        int i = 0;
                        while (true) {
                            if (i >= size) {
                                break;
                            } else if (str.equals(((RecentSmallProModel) MiniAppManager.this.recentList.get(i)).getAppId())) {
                                MiniAppManager.this.recentList.remove(i);
                                break;
                            } else {
                                i++;
                            }
                        }
                        DaoHelper.saveRecentList(MiniAppManager.this.getUserId(), new ArrayList(MiniAppManager.this.recentList));
                    }
                }
                if (str.contains("-")) {
                    MiniAppManager.this.debugModeApps.remove(str);
                }
                return null;
            }
        });
    }

    public synchronized void addRecentApp(String str, RecentSmallProModel recentSmallProModel) {
        if (!TextUtils.isEmpty(str)) {
            addAppIdToRecent(recentSmallProModel);
        }
    }

    private void addAppIdToRecent(RecentSmallProModel recentSmallProModel) {
        synchronized (this.LOCK) {
            this.recentList = getRecentList();
            if (!this.recentList.remove(recentSmallProModel) && this.recentList.size() >= 20) {
                this.recentList.remove(19);
            }
            this.recentList.add(0, recentSmallProModel);
            DaoHelper.saveRecentList(getUserId(), new ArrayList(this.recentList));
        }
    }

    /* access modifiers changed from: 0000 */
    public String getUserId() {
        String adiu = NetworkParam.getAdiu();
        if (adiu != null) {
            return adiu;
        }
        return MiniAppDataUtil.getTaobaoId();
    }

    public JSONObject getSmallProgramList() {
        return buildResult(JSONArray.parseArray(recentMiniApps()), Boolean.TRUE, "");
    }

    public String recentMiniApps() {
        return JSONArray.toJSONString(DaoHelper.getRecentList(getUserId()));
    }

    public String recentMiniAppsSync() {
        List<RecentSmallProModel> recentList2 = DaoHelper.getRecentList(getUserId());
        ArrayList arrayList = new ArrayList();
        for (RecentSmallProModel next : recentList2) {
            RecentUploadDTO recentUploadDTO = new RecentUploadDTO();
            recentUploadDTO.setChinfo(next.getChInfo());
            recentUploadDTO.setLastClickTime(next.getLastClickTime());
            recentUploadDTO.setAppid(next.getAppId());
            arrayList.add(recentUploadDTO);
        }
        return JSONArray.toJSONString(arrayList);
    }

    private JSONObject buildResult(Object obj, Boolean bool, String str) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "data", obj);
        jSONObject.put((String) "success", (Object) bool);
        jSONObject.put((String) "resultMessage", (Object) str);
        return jSONObject;
    }
}
