package com.alipay.android.phone.inside.api.result.ftfpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotFtfPayQueryMerchantCode extends ResultCode {
    public static final IotFtfPayQueryMerchantCode FAILED = new IotFtfPayQueryMerchantCode("iot_ftf_pay_querymerchant_8000", "失败，请重试");
    public static final IotFtfPayQueryMerchantCode PARAMS_ILLEGAL = new IotFtfPayQueryMerchantCode("iot_ftf_pay_querymerchant_8001", "参数非法");
    public static final IotFtfPayQueryMerchantCode SUCCESS = new IotFtfPayQueryMerchantCode("iot_ftf_pay_querymerchant_9000", "成功");
    private static final List<IotFtfPayQueryMerchantCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
    }

    protected IotFtfPayQueryMerchantCode(String str, String str2) {
        super(str, str2);
    }

    public static IotFtfPayQueryMerchantCode parse(String str) {
        for (IotFtfPayQueryMerchantCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
