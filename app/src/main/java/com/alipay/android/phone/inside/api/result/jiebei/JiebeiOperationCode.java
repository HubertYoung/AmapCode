package com.alipay.android.phone.inside.api.result.jiebei;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.sina.weibo.sdk.utils.WbAuthConstants;
import java.util.ArrayList;
import java.util.List;

public class JiebeiOperationCode extends ResultCode {
    public static final JiebeiOperationCode ALIPAY_NOT_INSTALL = new JiebeiOperationCode("4000", "支付宝未安装，请安装后再试");
    public static final JiebeiOperationCode ALIPAY_SIGN_ERROR = new JiebeiOperationCode("4001", "支付宝签名异常");
    public static final JiebeiOperationCode ALIPAY_VERSION_UNMATCH = new JiebeiOperationCode("4002", "支付宝当前版本不支持，请升级后再试");
    public static final JiebeiOperationCode FAILED = new JiebeiOperationCode(WbAuthConstants.AUTH_FAILED_NOT_INSTALL_CODE, "失败");
    public static final JiebeiOperationCode PARAMS_ILLEGAL = new JiebeiOperationCode(WbAuthConstants.AUTH_FAILED_INSTALL_APP_COUNTERFEIT_CODE, "参数非法");
    public static final JiebeiOperationCode SUCCESS = new JiebeiOperationCode(AlibcAlipay.PAY_SUCCESS_CODE, "成功");
    public static final JiebeiOperationCode TIMEOUT = new JiebeiOperationCode("5000", "支付宝处理超时");
    private static final List<JiebeiOperationCode> mCodeList;

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

    protected JiebeiOperationCode(String str, String str2) {
        super(str, str2);
    }

    public static JiebeiOperationCode parse(String str) {
        for (JiebeiOperationCode next : mCodeList) {
            if (TextUtils.equals(str, next.getValue())) {
                return next;
            }
        }
        return null;
    }
}
