package com.alipay.android.phone.inside.api.result.wallet;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class JumpAlipaySchemeCode extends ResultCode {
    public static final JumpAlipaySchemeCode ALIPAY_NOT_INSTALL = new JumpAlipaySchemeCode("jump_alipay_4000", "支付宝未安装");
    public static final JumpAlipaySchemeCode ALIPAY_SIGN_ERROR = new JumpAlipaySchemeCode("jump_alipay_4001", "支付宝签名异常");
    public static final JumpAlipaySchemeCode ALIPAY_VERSION_UNMATCH = new JumpAlipaySchemeCode("jump_alipay_4002", "支付宝版本太低");
    public static final JumpAlipaySchemeCode FAILED = new JumpAlipaySchemeCode("jump_alipay_8000", "跳转失败。");
    public static final JumpAlipaySchemeCode PARAMS_ILLEGAL = new JumpAlipaySchemeCode("jump_alipay_8001", "参数非法。");
    public static final JumpAlipaySchemeCode SUCCESS = new JumpAlipaySchemeCode("jump_alipay_9000", "跳转成功");
    private static final List<JumpAlipaySchemeCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(ALIPAY_NOT_INSTALL);
        mCodeList.add(ALIPAY_SIGN_ERROR);
        mCodeList.add(ALIPAY_VERSION_UNMATCH);
    }

    protected JumpAlipaySchemeCode(String str, String str2) {
        super(str, str2);
    }

    public static JumpAlipaySchemeCode parse(String str) {
        for (JumpAlipaySchemeCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
