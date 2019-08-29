package com.alipay.android.phone.inside.api.result.buscode;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;
import java.util.ArrayList;
import java.util.List;

public class BusGenCode extends ResultCode {
    public static final BusGenCode ALIPAY_NOT_INSTALL = new BusGenCode("bus_gen_code_4000", "支付宝未安装。");
    public static final BusGenCode ALIPAY_SIGN_ERROR = new BusGenCode("bus_gen_code_4001", "支付宝签名异常。");
    public static final BusGenCode ALIPAY_VERSION_UNMATCH = new BusGenCode("bus_gen_code_4002", "支付宝版本太低。");
    public static final BusGenCode FAILED = new BusGenCode("bus_gen_code_8000", "生成乘车码，失败，请重试。");
    public static final BusGenCode NOCARD = new BusGenCode("bus_gen_code_7001", "生成乘车码，未领卡。");
    public static final BusGenCode PARAMS_ILLEGAL = new BusGenCode("bus_gen_code_8001", "生成乘车码，参数非法，请重试。");
    public static final BusGenCode RETRY_IN_ALIPAY = new BusGenCode("bus_gen_code_8002", "生成乘车码，失败，请跳转支付宝app尝试。");
    public static final BusGenCode SUCCESS = new BusGenCode("bus_gen_code_9000", "生成乘车码，成功。");
    public static final BusGenCode UNAUTH = new BusGenCode("bus_gen_code_7000", "生成乘车码，未授权。");
    public static final BusGenCode VERIFY_TIMOUT = new BusGenCode("bus_gen_code_5000", "生成乘车码，核身超时。");
    private static final List<BusGenCode> mCodeList;

    static {
        ArrayList arrayList = new ArrayList();
        mCodeList = arrayList;
        arrayList.add(SUCCESS);
        mCodeList.add(FAILED);
        mCodeList.add(PARAMS_ILLEGAL);
        mCodeList.add(RETRY_IN_ALIPAY);
        mCodeList.add(UNAUTH);
        mCodeList.add(NOCARD);
        mCodeList.add(VERIFY_TIMOUT);
        mCodeList.add(ALIPAY_NOT_INSTALL);
        mCodeList.add(ALIPAY_SIGN_ERROR);
        mCodeList.add(ALIPAY_VERSION_UNMATCH);
    }

    protected BusGenCode(String str, String str2) {
        super(str, str2);
    }

    public static BusGenCode parse(String str) {
        for (BusGenCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
