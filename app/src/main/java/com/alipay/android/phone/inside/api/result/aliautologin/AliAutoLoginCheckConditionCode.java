package com.alipay.android.phone.inside.api.result.aliautologin;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class AliAutoLoginCheckConditionCode extends ResultCode {
    public static final AliAutoLoginCheckConditionCode CAN_AUTO_LOGIN = new AliAutoLoginCheckConditionCode("ali_auto_login_checkcondition_9000", "可以淘宝免登");
    public static final AliAutoLoginCheckConditionCode CAN_NOT_AUTO_LOGIN = new AliAutoLoginCheckConditionCode("ali_auto_login_checkcondition_8000", "不可以淘宝免登");
    private static final List<AliAutoLoginCheckConditionCode> mCodeList;

    protected AliAutoLoginCheckConditionCode(String str, String str2) {
        super(str, str2);
    }

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(CAN_AUTO_LOGIN);
        mCodeList.add(CAN_NOT_AUTO_LOGIN);
    }

    public static AliAutoLoginCheckConditionCode parse(String str) {
        for (AliAutoLoginCheckConditionCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
