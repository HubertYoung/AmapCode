package com.ali.user.mobile.login;

import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;

public interface OnLoginCaller {
    void a(AbsNotifyFinishCaller absNotifyFinishCaller);

    void a(UnifyLoginRes unifyLoginRes, AbsNotifyFinishCaller absNotifyFinishCaller);

    void b(UnifyLoginRes unifyLoginRes, AbsNotifyFinishCaller absNotifyFinishCaller);

    void c(UnifyLoginRes unifyLoginRes, AbsNotifyFinishCaller absNotifyFinishCaller);
}
