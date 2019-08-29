package com.ali.auth.third.mtop.rpc.impl;

import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.config.Environment;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.device.DeviceInfo;
import com.ali.auth.third.core.model.RpcRequest;
import com.ali.auth.third.core.model.RpcResponse;
import com.ali.auth.third.core.service.RpcService;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.mtop.rpc.MtopRemoteLoginImpl;
import com.ali.auth.third.mtop.rpc.a;
import com.taobao.tao.remotebusiness.login.RemoteLogin;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.intf.Mtop;

public class MtopRpcServiceImpl implements RpcService {
    Mtop a;

    public MtopRpcServiceImpl() {
        Mtop mtop;
        EnvModeEnum envModeEnum;
        if (ConfigManager.DEBUG) {
            TBSdkLog.a();
            TBSdkLog.a(true);
        }
        ffh.a("OPEN", 0, 0);
        ffh.a((String) "OPEN", CommonUtils.getAppVersion());
        this.a = Mtop.a((String) "OPEN", KernelContext.context);
        if (KernelContext.getEnvironment() == Environment.TEST) {
            mtop = this.a;
            envModeEnum = EnvModeEnum.TEST;
        } else if (KernelContext.getEnvironment() == Environment.PRE) {
            mtop = this.a;
            envModeEnum = EnvModeEnum.PREPARE;
        } else {
            mtop = this.a;
            envModeEnum = EnvModeEnum.ONLINE;
        }
        mtop.a(envModeEnum);
        RemoteLogin.setLoginImpl(this.a, new MtopRemoteLoginImpl());
    }

    public String getDeviceId() {
        return DeviceInfo.deviceId;
    }

    public <T> RpcResponse<T> invoke(RpcRequest rpcRequest, Class<T> cls) {
        return a.a().a(rpcRequest, cls);
    }

    public String invoke(RpcRequest rpcRequest) {
        return a.a().a(rpcRequest);
    }

    public void registerSessionInfo(String str, String str2) {
        this.a.a((String) null, str, str2);
    }

    public void logout() {
        this.a.c();
    }
}
