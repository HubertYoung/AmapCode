package com.alipay.android.phone.inside.api.result.iotads;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotAdsPayPrepareCode extends ResultCode {
    public static final IotAdsPayPrepareCode FAIL = new IotAdsPayPrepareCode("iot_ads_pay_prepare_9001", "失败");
    public static final IotAdsPayPrepareCode SUCCESS = new IotAdsPayPrepareCode("iot_pay_prepare_9000", "成功");
    private static final List<IotAdsPayPrepareCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotAdsPayPrepareCode parse(String str) {
        for (IotAdsPayPrepareCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotAdsPayPrepareCode(String str, String str2) {
        super(str, str2);
    }
}
