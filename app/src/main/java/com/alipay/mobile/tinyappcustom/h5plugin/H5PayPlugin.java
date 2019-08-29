package com.alipay.mobile.tinyappcustom.h5plugin;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcustom.process.H5EventHandlerServiceImpl;
import com.alipay.sdk.app.PayTask;
import java.util.Map;

public class H5PayPlugin extends H5SimplePlugin {
    /* access modifiers changed from: private */
    public static final String a = H5PayPlugin.class.getSimpleName();
    /* access modifiers changed from: private */
    public H5Event b;

    private class H5PayRunnable implements Runnable {
        private H5BridgeContext b;
        private String c;

        public H5PayRunnable(H5BridgeContext context, String orderStr) {
            this.b = context;
            this.c = orderStr;
        }

        public void run() {
            try {
                Map result = new PayTask(H5PayPlugin.this.b.getActivity()).payV2(this.c, true);
                H5Log.d(H5PayPlugin.a, "tradePay..result=" + result.toString());
                PayResult payResult = new PayResult(result);
                JSONObject jsonResult = new JSONObject();
                jsonResult.put((String) "resultCode", (Object) payResult.getResultStatus());
                this.b.sendBridgeResult(jsonResult);
                if (H5PayPlugin.this.b.getCallBack() != null) {
                    H5PayPlugin.this.b.getCallBack().onCallBack(jsonResult);
                }
            } catch (Throwable e) {
                this.b.sendError(15, (String) UserTrackerConstants.EM_PAY_FAILURE);
                H5Log.e(H5PayPlugin.a, "tradePay...e=" + e);
            }
        }
    }

    private static class PayParam {
        String a;
        String b;
        String c;

        private PayParam() {
        }

        /* synthetic */ PayParam(byte b2) {
            this();
        }

        public String toOrderStr() {
            return "app_name=\"alipay\"&trade_no=\"" + this.a + "\"&bizcontext=\"" + this.b + "\"biz_type=\"trade\"appenv=\"appid=alipay^system=android^version=" + this.c + "\"";
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(H5EventHandlerServiceImpl.tradePay);
    }

    public void onRelease() {
        this.b = null;
        super.onRelease();
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!(event.getTarget() instanceof H5Page)) {
            return false;
        }
        if (H5EventHandlerServiceImpl.tradePay.equals(event.getAction())) {
            a(event, context);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext context) {
        String orderStr = H5Utils.getString(event.getParam(), (String) "orderStr");
        String tradeNO = H5Utils.getString(event.getParam(), (String) "tradeNO");
        String bizContextFromParam = H5Utils.getString(event.getParam(), (String) "bizContext");
        this.b = event;
        if (!TextUtils.isEmpty(orderStr) || !TextUtils.isEmpty(tradeNO)) {
            if (TextUtils.isEmpty(orderStr)) {
                JSONObject bizContext = H5Utils.parseObject(bizContextFromParam);
                if (bizContext == null) {
                    bizContext = new JSONObject();
                }
                if (event.getH5page() != null) {
                    bizContext.put((String) "tinyAppId", (Object) H5Utils.getString(event.getH5page().getParams(), (String) "appId"));
                }
                bizContext.put((String) H5Param.SAFEPAY_CONTEXT, (Object) "tinyapp");
                PayParam payParam = new PayParam(0);
                payParam.a = tradeNO;
                payParam.b = bizContext.toJSONString();
                orderStr = payParam.toOrderStr();
            }
            new Thread(new H5PayRunnable(context, orderStr)).start();
            return;
        }
        context.sendError(event, Error.INVALID_PARAM);
    }
}
