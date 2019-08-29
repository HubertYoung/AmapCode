package com.ali.user.mobile.register.router;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.register.Account;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.register.model.State;
import com.ali.user.mobile.register.store.IStorageCallback;
import com.ali.user.mobile.register.ui.RegExistUserActivity;
import com.ali.user.mobile.register.ui.RegLoginPwdActivity;
import com.ali.user.mobile.register.ui.RegManualSmsActivity;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllResPb;
import com.autonavi.map.core.MapCustomizeManager;

public class LocalRouter implements IStorageCallback {
    public final void a(State state) {
        if (state == null || -1 != state.b || TextUtils.isEmpty(state.g)) {
            AliUserLog.d("Reg_LocalRouter", "null state/type/page");
            return;
        }
        AliUserLog.c("Reg_LocalRouter", "handle state ".concat(String.valueOf(state)));
        IRouterHandler a = RouterPages.a();
        if (a != null && a.handleStateChange(state)) {
            AliUserLog.c("Reg_LocalRouter", "handled by top handler");
        } else if (a == null || a.getActivity() == null) {
            AliUserLog.d("Reg_LocalRouter", "null handler");
        } else {
            String str = state.g;
            if (TextUtils.equals(str, "m")) {
                BaseActivity activity = a.getActivity();
                if (activity == null) {
                    AliUserLog.d("Reg_LocalRouter", "restart reg, null activity");
                } else {
                    RegContext.a().a(activity, null, null);
                }
            } else if (TextUtils.equals(str, RPCDataParser.TIME_MS)) {
                BaseActivity activity2 = a.getActivity();
                if (activity2 == null) {
                    AliUserLog.d("Reg_LocalRouter", "sms manual, null activity");
                    return;
                }
                Intent intent = new Intent(activity2, RegManualSmsActivity.class);
                if (activity2.getIntent().getExtras() != null) {
                    intent.putExtras(activity2.getIntent().getExtras());
                }
                if (state.h != null) {
                    intent.putExtras(state.h);
                }
                activity2.startActivity(intent);
            } else if (TextUtils.equals(str, "sp")) {
                BaseActivity activity3 = a.getActivity();
                if (activity3 == null) {
                    AliUserLog.d("Reg_LocalRouter", "supply pwd, null activity");
                } else {
                    activity3.startActivity(new Intent(activity3, RegLoginPwdActivity.class));
                }
            } else if (TextUtils.equals(str, "e")) {
                BaseActivity activity4 = a.getActivity();
                if (activity4 == null) {
                    AliUserLog.d("Reg_LocalRouter", "exist person, null activity");
                } else {
                    activity4.startActivity(new Intent(activity4, RegExistUserActivity.class));
                }
            } else {
                if (TextUtils.equals(str, H5Param.PREFETCH_LOCATION)) {
                    BaseActivity activity5 = a.getActivity();
                    if (activity5 == null) {
                        AliUserLog.d("Reg_LocalRouter", "pwd login, null activity");
                        return;
                    }
                    Intent a2 = AliuserLoginContext.a((Context) activity5);
                    Account a3 = state.a();
                    if (a3 != null) {
                        AliUserLog.c("Reg_LocalRouter", "pwd login, fill account");
                        LoginParam loginParam = new LoginParam();
                        loginParam.loginAccount = a3.asAccount();
                        a2.putExtra("login_param", loginParam);
                    }
                    UnifyRegisterAllResPb unifyRegisterAllResPb = state.c;
                    if (!(unifyRegisterAllResPb == null || unifyRegisterAllResPb.existUserHasQueryPassword == null)) {
                        StringBuilder sb = new StringBuilder("pwd login, has pwd ");
                        sb.append(unifyRegisterAllResPb.existUserHasQueryPassword);
                        AliUserLog.c("Reg_LocalRouter", sb.toString());
                        a2.putExtra("noQueryPwdUser", String.valueOf(!unifyRegisterAllResPb.existUserHasQueryPassword.booleanValue()));
                    }
                    a2.putExtra("from_register", true);
                    a2.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
                    a2.addFlags(536870912);
                    activity5.startActivity(a2);
                }
            }
        }
    }
}
