package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayTradeCloseCode extends ResultCode {
    public static final IotPayTradeCloseCode BIND_ERROR = new IotPayTradeCloseCode("iot_pay_trade_close_7000", "绑定关系不匹配，请重新绑定");
    public static final IotPayTradeCloseCode FAIL = new IotPayTradeCloseCode("iot_pay_trade_close_8000", "关单失败");
    public static final IotPayTradeCloseCode PARAMS_ILLEGAL = new IotPayTradeCloseCode("iot_pay_trade_close_8002", "参数异常");
    public static final IotPayTradeCloseCode SUCCESS = new IotPayTradeCloseCode("iot_pay_trade_close_9000", "关单成功");
    public static final IotPayTradeCloseCode UNKNOWN = new IotPayTradeCloseCode("iot_pay_trade_close_8001", "关单结果未知");
    private static final List<IotPayTradeCloseCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
        mCodeList.add(UNKNOWN);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(BIND_ERROR);
    }

    protected IotPayTradeCloseCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayTradeCloseCode parse(String str) {
        for (IotPayTradeCloseCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
