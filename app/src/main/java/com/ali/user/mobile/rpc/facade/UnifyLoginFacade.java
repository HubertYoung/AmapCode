package com.ali.user.mobile.rpc.facade;

import com.ali.user.mobile.rpc.vo.mobilegw.SupplyPassGwReq;
import com.ali.user.mobile.rpc.vo.mobilegw.SupplyPassGwRes;
import com.ali.user.mobile.rpc.vo.mobilegw.login.LoginSendMSGReqPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.LoginSendMSGResPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.SupplyLoginPwdReqPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.SupplyLoginPwdResPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginReq;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginReqPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginResPb;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface UnifyLoginFacade {
    @SignCheck
    @OperationType("ali.user.gw.unifyLogin.sendMsg")
    LoginSendMSGResPb initMsgLogin(LoginSendMSGReqPb loginSendMSGReqPb);

    @SignCheck
    @OperationType("ali.user.gw.unifyLogin.supplyLoginPwd")
    SupplyLoginPwdResPb supplyLoginPwd(SupplyLoginPwdReqPb supplyLoginPwdReqPb);

    @OperationType("ali.user.gw.login.supplysimplepass")
    SupplyPassGwRes supplySimplePassword(SupplyPassGwReq supplyPassGwReq);

    @OperationType("ali.user.gw.unifyLogin")
    UnifyLoginRes unifyLogin(UnifyLoginReq unifyLoginReq);

    @OperationType("ali.user.gw.unifyLogin.pb")
    UnifyLoginResPb unifyLoginPb(UnifyLoginReqPb unifyLoginReqPb);
}
