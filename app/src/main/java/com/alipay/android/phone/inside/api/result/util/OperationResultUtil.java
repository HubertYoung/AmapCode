package com.alipay.android.phone.inside.api.result.util;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ResultCode;

public class OperationResultUtil {
    public static Bundle serializeResultToBundle(OperationResult operationResult) {
        Bundle bundle = new Bundle();
        bundle.putString("result", operationResult.getResult());
        bundle.putString("code", operationResult.getCodeValue());
        bundle.putString("memo", operationResult.getCodeMemo());
        bundle.putString("op", operationResult.getOp());
        return bundle;
    }

    public static <T extends ResultCode> OperationResult<T> analysisOperationResult(Bundle bundle, IBizOperation<T> iBizOperation) {
        String string = bundle.getString("op");
        return new OperationResult<>(iBizOperation.parseResultCode(string, bundle.getString("code")), string, bundle.getString("result"));
    }
}
