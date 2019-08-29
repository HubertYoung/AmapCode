package com.alipay.android.phone.inside.api.result.iotads;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class IotAdsExitCode extends ResultCode {
    public static final IotAdsExitCode FAIL = new IotAdsExitCode("iot_ads_exit_9001", "失败");
    public static final IotAdsExitCode SUCCESS = new IotAdsExitCode("iot_ads_exit_9000", "成功");
    private static final List<IotAdsExitCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAIL);
    }

    public static IotAdsExitCode parse(String str) {
        for (IotAdsExitCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }

    protected IotAdsExitCode(String str, String str2) {
        super(str, str2);
    }
}
