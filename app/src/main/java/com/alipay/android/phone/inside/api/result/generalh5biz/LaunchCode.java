package com.alipay.android.phone.inside.api.result.generalh5biz;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.sina.weibo.sdk.utils.WbAuthConstants;
import java.util.ArrayList;
import java.util.List;

public class LaunchCode extends ResultCode {
    public static final LaunchCode ALIPAY_NOT_INSTALL = new LaunchCode("4000", "支付宝未安装，请安装后再试");
    public static final LaunchCode ALIPAY_SIGN_ERROR = new LaunchCode("4001", "支付宝签名异常");
    public static final LaunchCode ALIPAY_VERSION_UNMATCH = new LaunchCode("4002", "支付宝当前版本不支持，请升级后再试");
    public static final LaunchCode FAILED = new LaunchCode(WbAuthConstants.AUTH_FAILED_NOT_INSTALL_CODE, "失败");
    public static final LaunchCode PARAMS_ILLEGAL = new LaunchCode(WbAuthConstants.AUTH_FAILED_INSTALL_APP_COUNTERFEIT_CODE, "参数非法");
    public static final LaunchCode SUCCESS = new LaunchCode(AlibcAlipay.PAY_SUCCESS_CODE, "成功");
    public static final LaunchCode TIMEOUT = new LaunchCode("5000", "支付宝处理超时");
    private static final List<LaunchCode> mCodeList;

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

    protected LaunchCode(String str, String str2) {
        super(str, str2);
    }

    public static LaunchCode parse(String str) {
        for (LaunchCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
