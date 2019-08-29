package com.alipay.android.phone.inside.api.result.ftfpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotFtfPayCheckBindCode extends ResultCode {
    public static final IotFtfPayCheckBindCode BIND = new IotFtfPayCheckBindCode("iot_ftf_pay_check_bind_9000", "已绑定");
    public static final IotFtfPayCheckBindCode FAILED = new IotFtfPayCheckBindCode("iot_ftf_pay_check_bind_8000", "请求失败，请重试");
    public static final IotFtfPayCheckBindCode UNBIND = new IotFtfPayCheckBindCode("iot_ftf_pay_check_bind_9001", "未绑定");
    private static final List<IotFtfPayCheckBindCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(BIND);
        mCodeList.add(UNBIND);
        mCodeList.add(FAILED);
    }

    protected IotFtfPayCheckBindCode(String str, String str2) {
        super(str, str2);
    }

    public static IotFtfPayCheckBindCode parse(String str) {
        for (IotFtfPayCheckBindCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
