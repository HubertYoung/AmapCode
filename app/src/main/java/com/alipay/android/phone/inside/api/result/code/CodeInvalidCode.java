package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class CodeInvalidCode extends ResultCode {
    public static final CodeInvalidCode FAILED = new CodeInvalidCode("code_invalid_8000", "失败。");
    public static final CodeInvalidCode INNER_EX = new CodeInvalidCode("code_invalid_8002", "内部异常。");
    public static final CodeInvalidCode PARAMS_ILLEGAL = new CodeInvalidCode("code_invalid_8001", "参数非法。");
    public static final CodeInvalidCode SUCCESS = new CodeInvalidCode("code_invalid_9000", "成功。");
    private static final List<CodeInvalidCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(INNER_EX);
        mCodeList.add(PARAMS_ILLEGAL);
    }

    protected CodeInvalidCode(String str, String str2) {
        super(str, str2);
    }

    public static CodeInvalidCode parse(String str) {
        for (CodeInvalidCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
