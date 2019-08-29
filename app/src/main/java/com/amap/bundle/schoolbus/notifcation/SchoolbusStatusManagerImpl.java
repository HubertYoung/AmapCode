package com.amap.bundle.schoolbus.notifcation;

import com.autonavi.bundle.account.api.IAccountService;

public class SchoolbusStatusManagerImpl implements ISchoolbusStatusMangger {
    public boolean isNeedOnbackground() {
        return adl.a().b();
    }

    public boolean isTravelling() {
        int i;
        adl a = adl.a();
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null || !iAccountService.a()) {
            i = -1;
        } else {
            i = a.b;
        }
        if (i != 2) {
            adl.a((String) "school_manager", (String) "isTravelling not teacher role");
            return false;
        } else if (!a.b()) {
            adl.a((String) "school_manager", (String) "isTravelling no need on background");
            return false;
        } else {
            StringBuilder sb = new StringBuilder("isTravelling:");
            sb.append(a.f);
            adl.a((String) "school_manager", sb.toString());
            return a.f;
        }
    }
}
