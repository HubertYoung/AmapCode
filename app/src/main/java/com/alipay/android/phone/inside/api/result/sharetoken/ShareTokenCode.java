package com.alipay.android.phone.inside.api.result.sharetoken;

import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class ShareTokenCode extends ResultCode {
    public static final ShareTokenCode FAILED = new ShareTokenCode("share_token_8000", "失败");
    public static final ShareTokenCode SUCCESS = new ShareTokenCode("share_token_9000", "成功");
    private static final List<ShareTokenCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    protected ShareTokenCode(String str, String str2) {
        super(str, str2);
    }
}
