package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class ScanCode extends ResultCode {
    public static final ScanCode ALIPAY_NOT_INSTALL = new ScanCode("scan_9006", "支付宝未安装");
    public static final ScanCode ALIPAY_SIGN_ERROR = new ScanCode("scan_9007", "支付宝签名异常");
    public static final ScanCode ALIPAY_VERSION_UNMATCH = new ScanCode("scan_9008", "支付宝版本太低");
    public static final ScanCode AUTH_INVALID = new ScanCode("scan_9003", "授权过期");
    public static final ScanCode CODE_UNKNOWN = new ScanCode("scan_9002", "码值无法处理");
    public static final ScanCode FAILED = new ScanCode("scan_9001", "码值解析异常");
    public static final ScanCode INNER_EX = new ScanCode("scan_9004", "内部异常");
    public static final ScanCode PARAMS_ILLEGAL = new ScanCode("scan_9005", "参数异常");
    public static final ScanCode SUCCESS = new ScanCode("scan_9000", "码值执行成功");
    private static final List<ScanCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(CODE_UNKNOWN);
        mCodeList.add(AUTH_INVALID);
        mCodeList.add(INNER_EX);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(ALIPAY_NOT_INSTALL);
        mCodeList.add(ALIPAY_SIGN_ERROR);
        mCodeList.add(ALIPAY_VERSION_UNMATCH);
    }

    protected ScanCode(String str, String str2) {
        super(str, str2);
    }

    public static ScanCode parse(String str) {
        for (ScanCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
