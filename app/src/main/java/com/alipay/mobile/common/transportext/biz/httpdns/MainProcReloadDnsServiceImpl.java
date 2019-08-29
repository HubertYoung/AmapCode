package com.alipay.mobile.common.transportext.biz.httpdns;

import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient;
import com.alipay.mobile.common.transport.httpdns.ipc.MainProcReloadDnsService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;

public class MainProcReloadDnsServiceImpl implements MainProcReloadDnsService {
    public void notifyReloadDns() {
        AlipayHttpDnsClient.getDnsClient().reloadDns();
        LogCatUtil.info("MainProcReloadDnsServiceImpl", "notifyReloadDns ");
        notifyReloadOversea();
    }

    private void notifyReloadOversea() {
        boolean oversea = SharedPreUtils.getBooleanData(TransportEnvUtil.getContext(), "oversea");
        LogCatUtil.debug("MainProcReloadDnsServiceImpl", "notifyReloadOversea,oversea=[" + oversea + "]");
        MiscUtils.setOversea(oversea);
    }
}
