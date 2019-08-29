package com.alipay.android.phone.inside.api.result.ftfpay;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotFtfPayCode extends ResultCode {
    public static final IotFtfPayCode BIND_ERROR = new IotFtfPayCode("iot_ftf_pay_7000", "绑定关系不匹配，请重新绑定");
    public static final IotFtfPayCode FAIL = new IotFtfPayCode("iot_ftf_pay_8000", UserTrackerConstants.EM_PAY_FAILURE);
    public static final IotFtfPayCode PARAMS_ILLEGAL = new IotFtfPayCode("iot_ftf_pay_8002", "参数异常");
    public static final IotFtfPayCode SUCCESS = new IotFtfPayCode("iot_ftf_pay_9000", "支付成功");
    public static final IotFtfPayCode UNKNOWN = new IotFtfPayCode("iot_ftf_pay_8001", "支付结果未知");
    private static final List<IotFtfPayCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
        mCodeList.add(UNKNOWN);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(BIND_ERROR);
    }

    protected IotFtfPayCode(String str, String str2) {
        super(str, str2);
    }

    public static IotFtfPayCode parse(String str) {
        for (IotFtfPayCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
