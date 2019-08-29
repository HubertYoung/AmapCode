package com.alipay.android.phone.inside.api.result.iotads;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotAdsLoadingCode extends ResultCode {
    public static final IotAdsLoadingCode FAIL = new IotAdsLoadingCode("iot_ads_loading_9001", "失败");
    public static final IotAdsLoadingCode SUCCESS = new IotAdsLoadingCode("iot_ads_loading_9000", "成功");
    private static final List<IotAdsLoadingCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotAdsLoadingCode parse(String str) {
        for (IotAdsLoadingCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotAdsLoadingCode(String str, String str2) {
        super(str, str2);
    }
}
