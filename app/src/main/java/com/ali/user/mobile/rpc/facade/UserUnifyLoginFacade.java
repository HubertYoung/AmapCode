package com.ali.user.mobile.rpc.facade;

import com.ali.user.mobile.account.model.UserLoginReq;
import com.ali.user.mobile.account.model.UserLoginResult;
import com.ali.user.mobile.rpc.vo.mobilegw.autologin.UserLoginGWReqPb;
import com.ali.user.mobile.rpc.vo.mobilegw.autologin.UserLoginGWResultPb;
import com.ali.user.mobile.rpc.vo.mobilegw.logout.UserLogoutGWReq;
import com.ali.user.mobile.rpc.vo.mobilegw.logout.UserLogoutGWResult;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.ResetCookie;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface UserUnifyLoginFacade {
    @OperationType("alipay.user.login")
    @ResetCookie
    UserLoginResult login(UserLoginReq userLoginReq);

    @OperationType("alipay.user.login.pb")
    UserLoginGWResultPb loginPb(UserLoginGWReqPb userLoginGWReqPb);

    @SignCheck
    @OperationType("alipay.user.logout")
    UserLogoutGWResult logout(UserLogoutGWReq userLogoutGWReq);
}
