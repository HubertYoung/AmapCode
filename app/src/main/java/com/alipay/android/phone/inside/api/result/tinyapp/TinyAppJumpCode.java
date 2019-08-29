package com.alipay.android.phone.inside.api.result.tinyapp;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.sina.weibo.sdk.utils.WbAuthConstants;
import java.util.ArrayList;
import java.util.List;

public class TinyAppJumpCode extends ResultCode {
    public static final TinyAppJumpCode FAILED = new TinyAppJumpCode(WbAuthConstants.AUTH_FAILED_NOT_INSTALL_CODE, "失败");
    public static final TinyAppJumpCode SUCCESS = new TinyAppJumpCode(AlibcAlipay.PAY_SUCCESS_CODE, "成功");
    private static final List<TinyAppJumpCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    protected TinyAppJumpCode(String str, String str2) {
        super(str, str2);
    }

    public static TinyAppJumpCode parse(String str) {
        for (TinyAppJumpCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
