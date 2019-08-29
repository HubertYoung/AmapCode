package com.alipay.android.phone.inside.api.result.aliautologin;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class AliAutoLoginCode extends ResultCode {
    public static final AliAutoLoginCode FAILED = new AliAutoLoginCode("ali_auto_login_8000", "免登淘宝失败");
    public static final AliAutoLoginCode SUCCESS = new AliAutoLoginCode("ali_auto_login_9000", "免登淘宝成功");
    private static final List<AliAutoLoginCode> mCodeList;

    protected AliAutoLoginCode(String str, String str2) {
        super(str, str2);
    }

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
    }

    public static AliAutoLoginCode parse(String str) {
        for (AliAutoLoginCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
