package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayTradeRefundCode extends ResultCode {
    public static final IotPayTradeRefundCode BIND_ERROR = new IotPayTradeRefundCode("iot_pay_trade_refund_7000", "绑定关系不匹配，请重新绑定");
    public static final IotPayTradeRefundCode FAIL = new IotPayTradeRefundCode("iot_pay_trade_refund_8000", "退款失败");
    public static final IotPayTradeRefundCode PARAMS_ILLEGAL = new IotPayTradeRefundCode("iot_pay_trade_refund_8002", "参数异常");
    public static final IotPayTradeRefundCode SUCCESS = new IotPayTradeRefundCode("iot_pay_trade_refund_9000", "退款成功");
    public static final IotPayTradeRefundCode UNKNOWN = new IotPayTradeRefundCode("iot_pay_trade_refund_8001", "退款结果未知");
    private static final List<IotPayTradeRefundCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
        mCodeList.add(UNKNOWN);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(BIND_ERROR);
    }

    protected IotPayTradeRefundCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayTradeRefundCode parse(String str) {
        for (IotPayTradeRefundCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
