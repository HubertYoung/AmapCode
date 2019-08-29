package com.autonavi.miniapp.biz.service;

import com.alipay.mobile.tinyappcommon.api.TinyAppFavoriteService;
import com.autonavi.miniapp.biz.network.AmapRemoteBusiness;
import com.autonavi.miniapp.biz.network.dataobject.UserFavoriteDO;
import com.autonavi.miniapp.biz.network.request.QueryFavoriteStatusRequest;
import com.autonavi.miniapp.biz.network.response.QueryFavoriteStatusResponse;
import com.autonavi.miniapp.plugin.util.AMapUserInfoUtil;

public class TinyAppFavoriteServiceImpl implements TinyAppFavoriteService {
    private String mAmapUserId;
    private String mAppId;

    public static class TinyAppFavoriteServiceHolder {
        /* access modifiers changed from: private */
        public static final TinyAppFavoriteService INSTANCE = new TinyAppFavoriteServiceImpl();
    }

    public static TinyAppFavoriteService getInstance() {
        return TinyAppFavoriteServiceHolder.INSTANCE;
    }

    public boolean isTinyAppFavorite(String str) {
        this.mAppId = str;
        if (!AMapUserInfoUtil.getInstance().isLogin()) {
            return false;
        }
        this.mAmapUserId = AMapUserInfoUtil.getInstance().getUserInfo().a;
        QueryFavoriteStatusRequest queryFavoriteStatusRequest = new QueryFavoriteStatusRequest();
        queryFavoriteStatusRequest.setAppId(this.mAppId);
        queryFavoriteStatusRequest.setUid(this.mAmapUserId);
        QueryFavoriteStatusResponse queryFavoriteStatusResponse = (QueryFavoriteStatusResponse) ffx.a(AmapRemoteBusiness.build(queryFavoriteStatusRequest).syncRequest().getBytedata(), QueryFavoriteStatusResponse.class);
        if (queryFavoriteStatusResponse == null || queryFavoriteStatusResponse.getData() == null || !queryFavoriteStatusResponse.getData().result || queryFavoriteStatusResponse.getData().data == null || ((UserFavoriteDO) queryFavoriteStatusResponse.getData().data).getFavorite() != 1) {
            return false;
        }
        return true;
    }
}
