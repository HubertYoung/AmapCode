package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.QueryPayCode;

public class QueryCodeOp implements IBizOperation<QueryPayCode> {
    public QueryPayCode parseResultCode(String str, String str2) {
        return QueryPayCode.parse(str2);
    }

    public ActionEnum getAction() {
        return ActionEnum.QUERY_PAY_RESULT;
    }
}
