package com.alipay.mobile.common.transport.zfeatures;

import android.content.Context;
import com.alipay.mobile.common.transport.http.HttpException;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class LoginRefreshHelper {
    public static final void recordRpc(HttpWorker httpWorker, Context context) {
        try {
            if (httpWorker.isRpcRequest() && LoginRefreshManager.getInstance().isEnabledLoginRefresh(context)) {
                LoginRefreshManager.getInstance().recordRpc(httpWorker);
            }
        } catch (Throwable e) {
            LogCatUtil.error(HttpWorker.TAG, "LoginRefreshHelper#recordRpc error. ", e);
        }
    }

    public static final void checkIn(HttpWorker httpWorker, Context context) {
        try {
            if (httpWorker.isRpcRequest() && LoginRefreshManager.getInstance().isEnabledLoginRefresh(context) && !LoginRefreshManager.getInstance().checkIn(httpWorker)) {
                throw new HttpException(Integer.valueOf(50), " Login refresh check don't pass. API:" + httpWorker.getOperationType());
            }
        } catch (HttpException e) {
            throw e;
        } catch (Throwable e2) {
            LogCatUtil.error(HttpWorker.TAG, "LoginRefreshHelper#checkIn error. ", e2);
        }
    }

    public static final void removeRpc(HttpWorker httpWorker, Context context) {
        try {
            if (httpWorker.isRpcRequest() && LoginRefreshManager.getInstance().isEnabledLoginRefresh(context)) {
                LoginRefreshManager.getInstance().removeRecord(httpWorker);
            }
        } catch (Throwable e) {
            LogCatUtil.error(HttpWorker.TAG, "LoginRefreshHelper#removeRpc error. ", e);
        }
    }
}
