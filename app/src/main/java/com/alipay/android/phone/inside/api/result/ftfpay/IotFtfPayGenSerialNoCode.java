package com.alipay.android.phone.inside.api.result.ftfpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotFtfPayGenSerialNoCode extends ResultCode {
    public static final IotFtfPayGenSerialNoCode SUCCESS = new IotFtfPayGenSerialNoCode("iot_ftf_pay_gen_serial_no_9000", "成功");
    private static final List<IotFtfPayGenSerialNoCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
    }

    protected IotFtfPayGenSerialNoCode(String str, String str2) {
        super(str, str2);
    }

    public static IotFtfPayGenSerialNoCode parse(String str) {
        for (IotFtfPayGenSerialNoCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
