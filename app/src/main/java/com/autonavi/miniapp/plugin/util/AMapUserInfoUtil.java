package com.autonavi.miniapp.plugin.util;

import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.bundle.account.api.IAccountService;

public class AMapUserInfoUtil {

    static class Holder {
        static AMapUserInfoUtil instance = new AMapUserInfoUtil();

        private Holder() {
        }
    }

    public static AMapUserInfoUtil getInstance() {
        return Holder.instance;
    }

    public FavoritePOI getHomePoi() {
        com com2 = (com) ank.a(com.class);
        if (com2 == null) {
            return null;
        }
        cop b = com2.b(com2.a());
        if (b == null) {
            return null;
        }
        return b.c();
    }

    public FavoritePOI getCompanyPoi() {
        com com2 = (com) ank.a(com.class);
        if (com2 == null) {
            return null;
        }
        cop b = com2.b(com2.a());
        if (b == null) {
            return null;
        }
        return b.d();
    }

    public boolean isLogin() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    public ant getUserInfo() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return null;
        }
        return iAccountService.e();
    }
}
