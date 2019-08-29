package com.alipay.mobile.common.nbnet.biz.token;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.util.MD5Utils;
import com.alipay.mobile.common.nbnet.biz.util.SignUtil;
import com.alipay.mobile.common.utils.HexStringUtil;

public class DefaultTokenSignManager implements TokenSignManager {
    public final String a(String content) {
        String sign = SignUtil.a(content);
        return !TextUtils.isEmpty(sign) ? sign : new String(HexStringUtil.encodeHex(MD5Utils.a(content)));
    }
}
