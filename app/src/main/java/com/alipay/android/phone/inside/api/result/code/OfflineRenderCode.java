package com.alipay.android.phone.inside.api.result.code;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.result.ResultCode;

public class OfflineRenderCode extends ResultCode {
    public static final OfflineRenderCode FAILED = new OfflineRenderCode("render_9001", "渲染失败，请重试。");
    public static final OfflineRenderCode PARAMS_ILLEGAL = new OfflineRenderCode("render_9002", "参数非法，请重试。");
    public static final OfflineRenderCode SUCCESS = new OfflineRenderCode("render_9000", "渲染成功。");

    protected OfflineRenderCode(String str, String str2) {
        super(str, str2);
    }

    public static OfflineRenderCode parse(String str) {
        if (TextUtils.equals(str, SUCCESS.getValue())) {
            return SUCCESS;
        }
        if (TextUtils.equals(str, FAILED.getValue())) {
            return FAILED;
        }
        if (TextUtils.equals(str, PARAMS_ILLEGAL.getValue())) {
            return PARAMS_ILLEGAL;
        }
        return null;
    }
}
