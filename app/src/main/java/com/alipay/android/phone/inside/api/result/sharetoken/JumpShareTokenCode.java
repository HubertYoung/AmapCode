package com.alipay.android.phone.inside.api.result.sharetoken;

import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.sina.weibo.sdk.utils.WbAuthConstants;
import java.util.ArrayList;
import java.util.List;

public class JumpShareTokenCode extends ResultCode {
    public static final JumpShareTokenCode FAILED = new JumpShareTokenCode(WbAuthConstants.AUTH_FAILED_NOT_INSTALL_CODE, "失败");
    public static final JumpShareTokenCode SUCCESS = new JumpShareTokenCode(AlibcAlipay.PAY_SUCCESS_CODE, "成功");
    private static final List<JumpShareTokenCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    protected JumpShareTokenCode(String str, String str2) {
        super(str, str2);
    }
}
