package com.ali.user.mobile.accountbiz.extservice;

import com.ali.user.mobile.account.LoginCallBack;
import com.ali.user.mobile.account.bean.UserLoginResultBiz;
import com.ali.user.mobile.account.model.UserLoginReq;
import com.ali.user.mobile.account.model.UserLoginResult;

public interface LoginService {
    void autoLogin(LoginCallBack loginCallBack);

    UserLoginResultBiz login(String str, String str2, String str3, String str4, String str5);

    UserLoginResultBiz login(String str, String str2, String str3, String str4, String str5, boolean z);

    UserLoginResultBiz processLoginResult(UserLoginResult userLoginResult, UserLoginResultBiz userLoginResultBiz, UserLoginReq userLoginReq, boolean z);

    void sendLoginBroadcast(boolean z, UserLoginReq userLoginReq, UserLoginResultBiz userLoginResultBiz);
}
