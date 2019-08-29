package com.alipay.mobile.nebulaappcenter.dbhelp;

import com.alipay.mobile.nebula.util.H5Utils;

public class H5OnLineDBHelper extends H5BaseDBHelper {
    public H5OnLineDBHelper() {
        super(H5Utils.getContext(), "nebula_app.db");
    }

    public final String a() {
        return "H5DBHelper_online";
    }

    public final String b() {
        return "nebula_app.db";
    }
}
