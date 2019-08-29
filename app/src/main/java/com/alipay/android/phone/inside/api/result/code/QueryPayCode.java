package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class QueryPayCode extends ResultCode {
    public static final QueryPayCode AUTH_INVALID = new QueryPayCode("query_code_9005", "付款结果查询，请重新授权。");
    public static final QueryPayCode INNER_EX = new QueryPayCode("query_code_9006", "付款结果查询，内部异常，请重试。");
    public static final QueryPayCode PARAMS_ILLEGAL = new QueryPayCode("query_code_9004", "付款结果查询，参数非法，请重试。");
    public static final QueryPayCode QUERY_EXPIRED = new QueryPayCode("query_code_9007", "付款结果查询，请重新生码。");
    public static final QueryPayCode QUERY_FAILED = new QueryPayCode("query_code_9003", "付款结果查询，查询失败，请重试。");
    public static final QueryPayCode QUERY_IGNORE = new QueryPayCode("query_code_9002", "付款结果查询，忽略。");
    public static final QueryPayCode QUERY_UNKNOWN = new QueryPayCode("query_code_9001", "付款结果查询，未知，请重试。");
    public static final QueryPayCode SUCCESS = new QueryPayCode("query_code_9000", "付款结果查询，支付成功。");
    private static final List<QueryPayCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(QUERY_UNKNOWN);
        mCodeList.add(QUERY_IGNORE);
        mCodeList.add(QUERY_FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(AUTH_INVALID);
        mCodeList.add(INNER_EX);
        mCodeList.add(QUERY_EXPIRED);
    }

    protected QueryPayCode(String str, String str2) {
        super(str, str2);
    }

    public static QueryPayCode parse(String str) {
        for (QueryPayCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
