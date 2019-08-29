package com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db;

import android.content.Context;

public interface DbHelperCreator {
    DbHelper getDbHelper(Context context);

    String getDbName();

    int getDbVersion();

    OnDbCreateUpgradeHandler getOnDbCreateUpgradeHandler();
}
