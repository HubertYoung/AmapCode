package com.alipay.android.phone.inside.api.result.iotads;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotAdsInitCode extends ResultCode {
    public static final IotAdsInitCode FAIL = new IotAdsInitCode("iot_ads_init_9001", "失败");
    public static final IotAdsInitCode SUCCESS = new IotAdsInitCode("iot_ads_init_9000", "成功");
    private static final List<IotAdsInitCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotAdsInitCode parse(String str) {
        for (IotAdsInitCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotAdsInitCode(String str, String str2) {
        super(str, str2);
    }
}
