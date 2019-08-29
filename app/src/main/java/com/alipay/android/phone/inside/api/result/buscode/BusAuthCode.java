package com.alipay.android.phone.inside.api.result.buscode;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class BusAuthCode extends ResultCode {
    public static final BusAuthCode ALIPAY_NOT_INSTALL = new BusAuthCode("bus_auth_4000", "支付宝未安装。");
    public static final BusAuthCode ALIPAY_SIGN_ERROR = new BusAuthCode("bus_auth_4001", "支付宝签名异常。");
    public static final BusAuthCode ALIPAY_VERSION_UNMATCH = new BusAuthCode("bus_auth_4002", "支付宝版本太低。");
    public static final BusAuthCode FAILED = new BusAuthCode("bus_auth_8000", "授权失败，请重试。");
    public static final BusAuthCode PARAMS_ILLEGAL = new BusAuthCode("bus_auth_8001", "授权失败，参数非法。");
    public static final BusAuthCode SUCCESS = new BusAuthCode("bus_auth_9000", "授权成功。");
    public static final BusAuthCode TIMEOUT = new BusAuthCode("bus_auth_5000", "授权失败，超时返回。");
    private static final List<BusAuthCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(TIMEOUT);
        mCodeList.add(ALIPAY_NOT_INSTALL);
        mCodeList.add(ALIPAY_SIGN_ERROR);
        mCodeList.add(ALIPAY_VERSION_UNMATCH);
    }

    protected BusAuthCode(String str, String str2) {
        super(str, str2);
    }

    public static BusAuthCode parse(String str) {
        for (BusAuthCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
