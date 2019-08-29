package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayQueryBindResultCode extends ResultCode {
    public static final IotPayQueryBindResultCode FAIL = new IotPayQueryBindResultCode("iot_pay_bind_result_9003", "失败，系统异常，请重试");
    public static final IotPayQueryBindResultCode NOT_FINISH = new IotPayQueryBindResultCode("iot_pay_bind_result_9001", "继续轮训");
    public static final IotPayQueryBindResultCode PARAMS_ILLEGAL = new IotPayQueryBindResultCode("iot_pay_bind_result_9002", "参数异常，请重新生成绑定二维码");
    public static final IotPayQueryBindResultCode SUCCESS = new IotPayQueryBindResultCode("iot_pay_bind_result_9000", "绑定成功，轮训结束");
    public static final IotPayQueryBindResultCode TOKEN_EXPIRE = new IotPayQueryBindResultCode("iot_pay_bind_result_9004", "token过期，请重新生成绑定二维码");
    private static final List<IotPayQueryBindResultCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(NOT_FINISH);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(FAIL);
        mCodeList.add(TOKEN_EXPIRE);
    }

    protected IotPayQueryBindResultCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayQueryBindResultCode parse(String str) {
        for (IotPayQueryBindResultCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
