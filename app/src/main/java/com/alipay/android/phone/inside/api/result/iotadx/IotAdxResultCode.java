package com.alipay.android.phone.inside.api.result.iotadx;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotAdxResultCode extends ResultCode {
    public static final IotAdxResultCode FAILED = new IotAdxResultCode("iot_adx_resultcode_8000", "失败");
    public static final IotAdxResultCode SUCCESS = new IotAdxResultCode("iot_adx_resultcode_9000", "成功");
    private static final List<IotAdxResultCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    protected IotAdxResultCode(String str, String str2) {
        super(str, str2);
    }

    public static IotAdxResultCode parse(String str) {
        for (IotAdxResultCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
