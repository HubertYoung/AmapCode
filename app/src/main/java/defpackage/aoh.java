package defpackage;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.api.result.OperationResult;

/* renamed from: aoh reason: default package */
/* compiled from: AlipayNewInsideAuthResult */
public final class aoh {
    public String a;
    public String b;
    private String c;

    public aoh(OperationResult operationResult) {
        if (operationResult != null) {
            if (operationResult.getCode() != null) {
                this.c = operationResult.getCode().getMemo();
                this.a = operationResult.getCode().getValue();
            }
            String result = operationResult.getResult();
            if (!TextUtils.isEmpty(result)) {
                this.b = JSONObject.parseObject(result).getString("auth_code");
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AlipayNewInsideAuthResult{resultStatus='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", memo='");
        sb.append(this.c);
        sb.append('\'');
        sb.append(", authCode='");
        sb.append(this.b);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
