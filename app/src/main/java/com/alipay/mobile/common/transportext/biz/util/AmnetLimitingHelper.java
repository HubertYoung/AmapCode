package com.alipay.mobile.common.transportext.biz.util;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcConnection;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse;
import java.util.HashMap;
import java.util.Map;

public final class AmnetLimitingHelper {
    public static final MRpcResponse getLimitingMRpcResponse() {
        String limitPrompt = MRpcConnection.getInstance().getLimitPrompt();
        LogCatUtil.info("MRpcStream", " createLimitMrpcResponse.  limitPrompt=[" + limitPrompt + "] ");
        Map headers = new HashMap(3);
        headers.put("Result-Status", "1002");
        if (!TextUtils.isEmpty(limitPrompt)) {
            headers.put("Control", limitPrompt);
        }
        headers.put("Memo", "系统流量太大，请稍后再试！");
        MRpcResponse mRpcResponse = new MRpcResponse();
        mRpcResponse.headers = headers;
        mRpcResponse.body = new byte[0];
        return mRpcResponse;
    }

    public static boolean isServerLimiting() {
        return MRpcConnection.getInstance().isServerLimiting();
    }

    public static long getLimitingEndTime() {
        return MRpcConnection.getInstance().getLimitingEndTime();
    }
}
