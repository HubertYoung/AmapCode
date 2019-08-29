package com.autonavi.miniapp.biz.db.dao;

import com.alipay.mobile.common.logging.LogCatLog;
import com.autonavi.miniapp.biz.db.AppDbHelper;
import com.autonavi.miniapp.biz.db.DaoExcutor;

public class DaoTemplate {
    private static final String TAG = "AppDAO";

    /* access modifiers changed from: 0000 */
    public <T> T excute(DaoExcutor<T> daoExcutor) {
        try {
            return daoExcutor.excute(AppDbHelper.getDbHelper());
        } catch (Exception e) {
            LogCatLog.e(TAG, e.getLocalizedMessage(), e);
            return null;
        }
    }
}
