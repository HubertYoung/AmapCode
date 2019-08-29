package com.ali.user.mobile.login;

import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;

public class DefaultLoginCaller implements OnLoginCaller {
    public void a(UnifyLoginRes unifyLoginRes, AbsNotifyFinishCaller absNotifyFinishCaller) {
        if (absNotifyFinishCaller != null) {
            absNotifyFinishCaller.a();
        }
    }

    public void c(UnifyLoginRes unifyLoginRes, AbsNotifyFinishCaller absNotifyFinishCaller) {
        if (absNotifyFinishCaller != null) {
            absNotifyFinishCaller.a();
        }
    }

    public void a(AbsNotifyFinishCaller absNotifyFinishCaller) {
        if (absNotifyFinishCaller != null) {
            absNotifyFinishCaller.a();
        }
    }

    public void b(UnifyLoginRes unifyLoginRes, AbsNotifyFinishCaller absNotifyFinishCaller) {
        if (absNotifyFinishCaller != null) {
            absNotifyFinishCaller.a();
        }
    }
}
