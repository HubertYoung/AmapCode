package com.alipay.android.phone.inside.api.result.iotads;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotAdsPreMemberCode extends ResultCode {
    public static final IotAdsPreMemberCode FAIL = new IotAdsPreMemberCode("iot_ads_premember_9001", "失败");
    public static final IotAdsPreMemberCode SUCCESS = new IotAdsPreMemberCode("iot_ads_premember_9000", "成功");
    private static final List<IotAdsPreMemberCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotAdsPreMemberCode parse(String str) {
        for (IotAdsPreMemberCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotAdsPreMemberCode(String str, String str2) {
        super(str, str2);
    }
}
