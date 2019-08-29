package com.alipay.android.phone.inside.api.result.iotads;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotAdsPayResultCode extends ResultCode {
    public static final IotAdsPayResultCode FAIL = new IotAdsPayResultCode("iot_ads_pay_result_9001", "失败");
    public static final IotAdsPayResultCode SUCCESS = new IotAdsPayResultCode("iot_ads_pay_result_9000", "成功");
    private static final List<IotAdsPayResultCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotAdsPayResultCode parse(String str) {
        for (IotAdsPayResultCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    public IotAdsPayResultCode(String str, String str2) {
        super(str, str2);
    }
}
