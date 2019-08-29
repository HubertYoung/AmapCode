package com.alipay.android.phone.inside.api.result.buscode;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class BusUnauthCode extends ResultCode {
    public static final BusUnauthCode FAILED = new BusUnauthCode("bus_unauth_8000", "解除授权失败。");
    public static final BusUnauthCode PARAMS_ILLEGAL = new BusUnauthCode("bus_unauth_8001", "解除授权失败。");
    public static final BusUnauthCode SUCCESS = new BusUnauthCode("bus_unauth_9000", "解除授权成功。");
    private static final List<BusUnauthCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
    }

    protected BusUnauthCode(String str, String str2) {
        super(str, str2);
    }

    public static BusUnauthCode parse(String str) {
        for (BusUnauthCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
