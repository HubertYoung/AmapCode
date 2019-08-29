package com.alipay.android.phone.inside.api.result.ftfpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotFtfPayOrderAndPayCode extends ResultCode {
    public static final IotFtfPayOrderAndPayCode BIND_ERROR = new IotFtfPayOrderAndPayCode("iot_ftf_pay_order_and_pay_9001", "绑定关系不匹配，请重新绑定");
    public static final IotFtfPayOrderAndPayCode FAIL = new IotFtfPayOrderAndPayCode("iot_ftf_pay_order_and_pay_9002", "失败，请重试");
    public static final IotFtfPayOrderAndPayCode SUCCESS = new IotFtfPayOrderAndPayCode("iot_ftf_pay_order_and_pay_9000", "成功");
    private static final List<IotFtfPayOrderAndPayCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(BIND_ERROR);
        mCodeList.add(FAIL);
    }

    protected IotFtfPayOrderAndPayCode(String str, String str2) {
        super(str, str2);
    }

    public static IotFtfPayOrderAndPayCode parse(String str) {
        for (IotFtfPayOrderAndPayCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
