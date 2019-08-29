package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class SwitchChannelCode extends ResultCode {
    public static final SwitchChannelCode FAILED = new SwitchChannelCode("switch_channel_8000", "渠道切换失败。");
    public static final SwitchChannelCode SUCCESS = new SwitchChannelCode("switch_channel_9000", "渠道切换成功。");
    private static final List<SwitchChannelCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    protected SwitchChannelCode(String str, String str2) {
        super(str, str2);
    }

    public static SwitchChannelCode parse(String str) {
        for (SwitchChannelCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
