package com.alipay.mobile.common.nbnet.biz.db;

import android.content.Context;
import com.alipay.mobile.common.nbnet.biz.Injection;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

public class NBNetDbHelperFactory {
    private static OrmLiteSqliteOpenHelper a;

    public static final OrmLiteSqliteOpenHelper a(Context context) {
        if (Injection.c != null) {
            return Injection.c;
        }
        if (a == null) {
            a = new NBNetDbHelper(context);
        }
        return a;
    }
}
