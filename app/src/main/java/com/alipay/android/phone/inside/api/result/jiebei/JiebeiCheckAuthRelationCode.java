package com.alipay.android.phone.inside.api.result.jiebei;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class JiebeiCheckAuthRelationCode extends ResultCode {
    public static final JiebeiCheckAuthRelationCode ACCOUNT_UNMATCH = new JiebeiCheckAuthRelationCode("jiebei_check_Auth_8004", "账号不一致");
    public static final JiebeiCheckAuthRelationCode FAILED = new JiebeiCheckAuthRelationCode("jiebei_check_Auth_8003", "查询失败，请重试");
    public static final JiebeiCheckAuthRelationCode NO_AUTH = new JiebeiCheckAuthRelationCode("jiebei_check_Auth_8001", "当前未授权");
    public static final JiebeiCheckAuthRelationCode NO_LOGIN = new JiebeiCheckAuthRelationCode("jiebei_check_Auth_8002", "当前未登录，查询失败");
    public static final JiebeiCheckAuthRelationCode SUCCESS = new JiebeiCheckAuthRelationCode("jiebei_check_Auth_9000", "授权关系正常");
    private static final List<JiebeiCheckAuthRelationCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(NO_AUTH);
        mCodeList.add(NO_LOGIN);
        mCodeList.add(FAILED);
        mCodeList.add(ACCOUNT_UNMATCH);
    }

    protected JiebeiCheckAuthRelationCode(String str, String str2) {
        super(str, str2);
    }

    public static JiebeiCheckAuthRelationCode parse(String str) {
        for (JiebeiCheckAuthRelationCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
