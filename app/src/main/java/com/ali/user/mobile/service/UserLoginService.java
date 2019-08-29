package com.ali.user.mobile.service;

import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.MsgLoginParam;
import com.ali.user.mobile.rpc.vo.mobilegw.SupplyPassGwReq;
import com.ali.user.mobile.rpc.vo.mobilegw.SupplyPassGwRes;
import com.ali.user.mobile.rpc.vo.mobilegw.login.LoginSendMSGResPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.SupplyLoginPwdResPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;

public interface UserLoginService {
    SupplyPassGwRes a(SupplyPassGwReq supplyPassGwReq);

    LoginSendMSGResPb a(MsgLoginParam msgLoginParam);

    SupplyLoginPwdResPb a(String str, String str2, String str3);

    UnifyLoginRes a(LoginParam loginParam);
}
