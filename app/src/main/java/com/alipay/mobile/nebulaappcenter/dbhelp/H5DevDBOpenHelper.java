package com.alipay.mobile.nebulaappcenter.dbhelp;

import com.alipay.mobile.nebula.util.H5Utils;

public class H5DevDBOpenHelper extends H5BaseDBHelper {
    H5DevDBOpenHelper() {
        super(H5Utils.getContext(), "nebula_app_dev.db");
    }

    public final String a() {
        return "H5DBHelper_dev";
    }

    public final String b() {
        return "nebula_app_dev.db";
    }
}
