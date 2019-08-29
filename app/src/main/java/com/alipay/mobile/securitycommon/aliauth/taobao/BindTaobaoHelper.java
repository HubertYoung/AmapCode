package com.alipay.mobile.securitycommon.aliauth.taobao;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.account.adapter.CommonAdapter;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import com.alipay.mobile.securitycommon.aliauth.util.LogUtil;
import com.alipay.mobile.securitycommon.taobaobind.TaobaoBindService;
import com.alipay.mobile.securitycommon.taobaobind.util.H5Wrapper;
import com.alipay.mobileapp.biz.rpc.taobao.bind.vo.BindTaobaoRes;
import com.alipay.mobileapp.biz.rpc.taobao.login.vo.AutoLoginPbResPB;
import com.alipay.mobileapp.biz.rpc.taobao.login.vo.BindTaobaoPbResPB;

public class BindTaobaoHelper {
    public Bundle bindTaobao(Bundle bundle, AutoLoginPbResPB autoLoginPbResPB) {
        CommonAdapter.a();
        TaobaoBindService instance = TaobaoBindService.getInstance(CommonAdapter.b());
        if ("1002".equals(autoLoginPbResPB.resultStatus)) {
            LogUtil.log("BindTaobaoHelper", "需要绑定手机先");
            instance.bindPhoneForResult(bundle, a("1002", autoLoginPbResPB.bindTaobaoPbRes));
        } else if (Result.TAOBAO_ACTIVE.equals(autoLoginPbResPB.resultStatus)) {
            LogUtil.log("BindTaobaoHelper", "需要激活淘宝账号");
            instance.taobaoActiveForResult(bundle, a(Result.TAOBAO_ACTIVE, autoLoginPbResPB.bindTaobaoPbRes));
        } else if ("1001".equals(autoLoginPbResPB.resultStatus)) {
            LogUtil.log("BindTaobaoHelper", "绑定淘宝失败");
            instance.bindPhoneForResult(bundle, a("1001", autoLoginPbResPB.bindTaobaoPbRes));
        } else if (Result.PUNISH_ACCOUNT.equals(autoLoginPbResPB.resultStatus) || Result.RUBBISH_ACCOUNT.equals(autoLoginPbResPB.resultStatus)) {
            LogUtil.log("BindTaobaoHelper", "垃圾账号或处罚账号");
            if (!TextUtils.isEmpty(autoLoginPbResPB.noticeUrl)) {
                H5Wrapper.startPage(autoLoginPbResPB.noticeUrl);
            }
        }
        return bundle;
    }

    private static BindTaobaoRes a(String str, BindTaobaoPbResPB bindTaobaoPbResPB) {
        BindTaobaoRes bindTaobaoRes = new BindTaobaoRes();
        bindTaobaoRes.btnMemo = bindTaobaoPbResPB.btnMemo;
        bindTaobaoRes.h5Url = bindTaobaoPbResPB.h5Url;
        bindTaobaoRes.memo = bindTaobaoPbResPB.memo;
        bindTaobaoRes.resultCode = str;
        bindTaobaoRes.taobaoId = bindTaobaoPbResPB.taobaoId;
        bindTaobaoRes.txtMemo = bindTaobaoPbResPB.txtMemo;
        return bindTaobaoRes;
    }
}
