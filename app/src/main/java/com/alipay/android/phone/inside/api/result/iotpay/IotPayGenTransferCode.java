package com.alipay.android.phone.inside.api.result.iotpay;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotPayGenTransferCode extends ResultCode {
    public static final IotPayGenTransferCode FAILED = new IotPayGenTransferCode("iot_pay_gen_transfer_code_9002", "失败，请重试");
    public static final IotPayGenTransferCode SUCCESS = new IotPayGenTransferCode("iot_pay_gen_transfer_code_9000", "成功");
    public static final IotPayGenTransferCode UNBIND = new IotPayGenTransferCode("iot_pay_gen_transfer_code_9001", "未绑定");
    private static final List<IotPayGenTransferCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(UNBIND);
        mCodeList.add(FAILED);
    }

    protected IotPayGenTransferCode(String str, String str2) {
        super(str, str2);
    }

    public static IotPayGenTransferCode parse(String str) {
        for (IotPayGenTransferCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
