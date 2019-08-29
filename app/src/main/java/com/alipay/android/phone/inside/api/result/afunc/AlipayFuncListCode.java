package com.alipay.android.phone.inside.api.result.afunc;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class AlipayFuncListCode extends ResultCode {
    public static final AlipayFuncListCode FAILED = new AlipayFuncListCode("alipay_func_list_8000", "失败。");
    public static final AlipayFuncListCode PARAMS_ILLEGAL = new AlipayFuncListCode("alipay_func_list_8001", "参数非法。");
    public static final AlipayFuncListCode SUCCESS = new AlipayFuncListCode("alipay_func_list_9000", "成功。");
    private static final List<AlipayFuncListCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
    }

    protected AlipayFuncListCode(String str, String str2) {
        super(str, str2);
    }

    public static AlipayFuncListCode parse(String str) {
        for (AlipayFuncListCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
