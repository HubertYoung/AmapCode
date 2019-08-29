package com.ali.user.mobile.context;

import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;

public interface LoginHandler {
    void onLoginPreFinish(UnifyLoginRes unifyLoginRes);
}
