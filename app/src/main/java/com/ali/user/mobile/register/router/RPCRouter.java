package com.ali.user.mobile.register.router;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.register.LogUtils;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.register.model.SimpleRequest;
import com.ali.user.mobile.register.model.State;
import com.ali.user.mobile.register.model.StateUtils;
import com.ali.user.mobile.register.store.ActionCenter;
import com.ali.user.mobile.register.store.IStorageCallback;
import com.ali.user.mobile.register.ui.AliUserRegisterSixPasswordActivity;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.util.StringUtil;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobileapp.biz.rpc.unifyregister.vo.pb.UnifyRegisterAllResPb;
import com.autonavi.map.core.MapCustomizeManager;

public class RPCRouter implements IStorageCallback {
    public final void a(final State state) {
        IRouterHandler a = RouterPages.a();
        if (a != null && a.handleStateChange(state)) {
            AliUserLog.c("Reg_RPCRouter", "handled by top handler ".concat(String.valueOf(a)));
        } else if (state == null || -2 != state.b || state.c == null) {
            AliUserLog.d("Reg_RPCRouter", "wrong state ".concat(String.valueOf(state)));
        } else {
            StringBuilder sb = new StringBuilder("server code ");
            sb.append(state.c.resultStatus);
            sb.append(Token.SEPARATOR);
            sb.append(state.d);
            AliUserLog.c("Reg_RPCRouter", sb.toString());
            if (a == null || state.c.resultStatus == null) {
                AliUserLog.d("Reg_RPCRouter", "null handler/result status");
                return;
            }
            int intValue = state.c.resultStatus.intValue();
            String str = state.c.memo;
            if (intValue == 200) {
                AliUserLog.c("Reg_RPCRouter", "handle RPC success");
                if (TextUtils.equals(state.d, "registerPreVerify")) {
                    ActionCenter actionCenter = RegContext.a().c;
                    if (actionCenter == null) {
                        AliUserLog.d("Reg_RPCRouter", "manual sms, null action center");
                    } else {
                        actionCenter.a((String) RPCDataParser.TIME_MS);
                    }
                } else if (TextUtils.equals(state.d, "sendSms")) {
                    AliUserLog.d("Reg_RPCRouter", "send sms success, should handled by activity");
                } else {
                    if (TextUtils.equals(state.d, "verifySms") || TextUtils.equals(state.d, "setLoginPassword")) {
                        if (state.c.removePaymentPass == null || !state.c.removePaymentPass.booleanValue()) {
                            StringBuilder sb2 = new StringBuilder("reg success, to pay pwd ");
                            sb2.append(state.c.removePaymentPass);
                            AliUserLog.c("Reg_RPCRouter", sb2.toString());
                            BaseActivity activity = a.getActivity();
                            if (activity == null) {
                                AliUserLog.d("Reg_RPCRouter", "set pay pwd, null activity");
                                return;
                            }
                            UnifyRegisterAllResPb unifyRegisterAllResPb = state.c;
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("optionStatus", false);
                            Intent intent = new Intent(activity, AliUserRegisterSixPasswordActivity.class);
                            intent.putExtras(bundle);
                            intent.putExtra("token", unifyRegisterAllResPb.token);
                            if (state.a() != null) {
                                intent.putExtra("mobile_for_sms", state.a().getAccountForRPC());
                            }
                            intent.putExtra("from_register", true);
                            activity.startActivity(intent);
                            StateUtils.c();
                        } else {
                            StringBuilder sb3 = new StringBuilder("reg success, to login ");
                            sb3.append(state.c.removePaymentPass);
                            AliUserLog.c("Reg_RPCRouter", sb3.toString());
                            LogUtils.a("UC-ZC-150512-29", "zcsuccess", State.a, null);
                            b(state, a.getActivity(), "afterreg", state.c.token);
                        }
                    }
                }
            } else if (intValue != 207) {
                if (!(intValue == 1123 || intValue == 2001)) {
                    if (intValue == 2004 || intValue == 2006) {
                        final BaseActivity activity2 = a.getActivity();
                        activity2.alert(str, "", activity2.getResources().getString(R.string.bX), new OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActionCenter actionCenter = RegContext.a().c;
                                if (actionCenter != null) {
                                    SimpleRequest simpleRequest = new SimpleRequest();
                                    simpleRequest.b = "sendSms";
                                    actionCenter.a(simpleRequest, activity2);
                                }
                            }
                        }, "", null);
                        return;
                    } else if (intValue != 3081) {
                        switch (intValue) {
                            case amb.GL_MARKER_LINE_ARROW_DOT /*3003*/:
                            case 3004:
                                break;
                            default:
                                switch (intValue) {
                                    case 3061:
                                        a(a.getActivity().getString(R.string.cl), R.string.bt, a.getActivity());
                                        return;
                                    case 3062:
                                    case 3064:
                                        break;
                                    case 3063:
                                    case 3065:
                                        ActionCenter actionCenter2 = RegContext.a().c;
                                        if (actionCenter2 == null) {
                                            AliUserLog.d("Reg_RPCRouter", "exist person, null action center");
                                            return;
                                        } else {
                                            actionCenter2.a((String) "e");
                                            return;
                                        }
                                    default:
                                        switch (intValue) {
                                            case 3068:
                                                break;
                                            case 3069:
                                            case 3070:
                                                ActionCenter actionCenter3 = RegContext.a().c;
                                                if (actionCenter3 == null) {
                                                    AliUserLog.d("Reg_RPCRouter", "login pwd, null action center");
                                                    return;
                                                } else {
                                                    actionCenter3.a((String) "sp");
                                                    return;
                                                }
                                            default:
                                                a.getActivity().toast(str, 3000);
                                                return;
                                        }
                                }
                                final BaseActivity activity3 = a.getActivity();
                                if (activity3 == null || state.a() == null) {
                                    AliUserLog.d("Reg_RPCRouter", "alert login null activity/account");
                                    return;
                                }
                                UnifyRegisterAllResPb unifyRegisterAllResPb2 = state.c;
                                String string = activity3.getResources().getString(R.string.bz);
                                if (unifyRegisterAllResPb2.existUserInfo != null && !TextUtils.isEmpty(unifyRegisterAllResPb2.existUserInfo.ButtonFstMemo)) {
                                    AliUserLog.c("Reg_RPCRouter", "alert login, use server side wordings");
                                    string = unifyRegisterAllResPb2.existUserInfo.ButtonFstMemo;
                                }
                                activity3.alert(unifyRegisterAllResPb2.memo, "", string, new OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        LogUtils.a("UC-ZC-161209-02", "loginnow");
                                        RPCRouter.b(state, activity3, "withlogintoken", state.c.loginToken);
                                    }
                                }, "", null);
                                return;
                        }
                    }
                }
                a(str, R.string.bt, a.getActivity());
            } else {
                BaseActivity activity4 = a.getActivity();
                if (activity4 == null) {
                    AliUserLog.d("Reg_RPCRouter", "207 null activity ");
                } else {
                    activity4.alert(str, "", activity4.getResources().getString(R.string.H), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActionCenter actionCenter = RegContext.a().c;
                            if (actionCenter == null) {
                                AliUserLog.d("Reg_RPCRouter", "207 null action center");
                            } else {
                                actionCenter.a((String) "m");
                            }
                        }
                    }, null, null);
                }
            }
        }
    }

    private static void a(String str, int i, BaseActivity baseActivity) {
        baseActivity.alert(str, "", baseActivity.getResources().getString(i), null, "", null);
    }

    /* access modifiers changed from: private */
    public static void b(State state, BaseActivity baseActivity, String str, String str2) {
        boolean z;
        if (baseActivity == null) {
            AliUserLog.d("Reg_RPCRouter", "direct login, null activity");
            return;
        }
        UnifyRegisterAllResPb unifyRegisterAllResPb = state.c;
        String b = StringUtil.b(state.a().getFullAreaCode(), state.a().getPhoneNumber());
        boolean z2 = true;
        if (unifyRegisterAllResPb.newUserHasQueryPassword == null) {
            z = true;
        } else {
            z = unifyRegisterAllResPb.newUserHasQueryPassword.booleanValue();
        }
        LoginParam loginParam = new LoginParam();
        loginParam.loginAccount = b;
        loginParam.token = str2;
        loginParam.validateTpye = str;
        Intent a = AliuserLoginContext.a(baseActivity.getApplicationContext());
        a.putExtra("login_param", loginParam);
        a.putExtra("from_register", true);
        if (z) {
            z2 = false;
        }
        a.putExtra("noQueryPwdUser", String.valueOf(z2));
        a.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
        a.addFlags(536870912);
        baseActivity.startActivity(a);
        LogAgent.b("UC-ZC-150512-10", "zccfdlok", "RegisterExistUser", state.a().getPhoneNumber(), unifyRegisterAllResPb.token);
        baseActivity.finish();
        AliUserLog.c("Reg_RPCRouter", "direct login, current activity finished");
        StateUtils.c();
    }
}
