package com.alipay.android.phone.inside.api.result.afunc;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class JumpAlipayFuncCode extends ResultCode {
    public static final JumpAlipayFuncCode ALIPAY_NOT_INSTALL = new JumpAlipayFuncCode("jump_alipay_func_4000", "支付宝未安装");
    public static final JumpAlipayFuncCode ALIPAY_SIGN_ERROR = new JumpAlipayFuncCode("jump_alipay_func_4001", "支付宝签名异常");
    public static final JumpAlipayFuncCode ALIPAY_VERSION_UNMATCH = new JumpAlipayFuncCode("jump_alipay_func_4002", "支付宝版本太低");
    public static final JumpAlipayFuncCode FAILED = new JumpAlipayFuncCode("jump_alipay_func_8000", "跳转失败。");
    public static final JumpAlipayFuncCode PARAMS_ILLEGAL = new JumpAlipayFuncCode("jump_alipay_func_8001", "参数非法。");
    public static final JumpAlipayFuncCode SUCCESS = new JumpAlipayFuncCode("jump_alipay_func_9000", "跳转成功");
    private static final List<JumpAlipayFuncCode> mCodeList;

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

    protected JumpAlipayFuncCode(String str, String str2) {
        super(str, str2);
    }

    public static JumpAlipayFuncCode parse(String str) {
        for (JumpAlipayFuncCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
