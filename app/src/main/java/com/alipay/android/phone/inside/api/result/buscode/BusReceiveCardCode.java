package com.alipay.android.phone.inside.api.result.buscode;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class BusReceiveCardCode extends ResultCode {
    public static final BusReceiveCardCode ALIPAY_NOT_INSTALL = new BusReceiveCardCode("bus_receivecard_4000", "支付宝钱包未安装。");
    public static final BusReceiveCardCode ALIPAY_SIGN_ERROR = new BusReceiveCardCode("bus_receivecard_4002", "支付宝钱包签名异常。");
    public static final BusReceiveCardCode ALIPAY_UNMATCH = new BusReceiveCardCode("bus_receivecard_4001", "支付宝钱包版本太低。");
    public static final BusReceiveCardCode FAILED = new BusReceiveCardCode("bus_receivecard_8000", "领卡失败。");
    public static final BusReceiveCardCode PARAMS_ILLEGAL = new BusReceiveCardCode("bus_receivecard_8001", "参数非法。");
    public static final BusReceiveCardCode SUCCESS = new BusReceiveCardCode("bus_receivecard_9000", "领卡结束。");
    public static final BusReceiveCardCode TIMEOUT = new BusReceiveCardCode("bus_receivecard_5000", "超时返回。");
    private static final List<BusReceiveCardCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(TIMEOUT);
        mCodeList.add(ALIPAY_NOT_INSTALL);
        mCodeList.add(ALIPAY_UNMATCH);
        mCodeList.add(ALIPAY_SIGN_ERROR);
    }

    protected BusReceiveCardCode(String str, String str2) {
        super(str, str2);
    }

    public static BusReceiveCardCode parse(String str) {
        for (BusReceiveCardCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
