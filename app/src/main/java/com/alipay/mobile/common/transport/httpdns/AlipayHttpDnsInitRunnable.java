package com.alipay.mobile.common.transport.httpdns;

import android.content.Context;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import java.net.URL;

public final class AlipayHttpDnsInitRunnable implements Runnable {
    private Context a;
    private int b = 0;
    private boolean c = true;

    public AlipayHttpDnsInitRunnable(Context context, int flag) {
        this.a = context;
        this.b = flag;
    }

    public AlipayHttpDnsInitRunnable(Context context, int flag, boolean refreshFromServer) {
        this.a = context;
        this.b = flag;
        this.c = refreshFromServer;
    }

    public final void run() {
        AlipayHttpDnsClient.dnsClientInit(this.a, a(), this.b, this.c);
    }

    private String a() {
        StringBuilder hostsBuilder = new StringBuilder(20);
        if (MiscUtils.isInAlipayClient(this.a)) {
            if (this.a != null && MiscUtils.isRCVersion(this.a)) {
                hostsBuilder.append("mygwrc.alipay.com,");
            }
            hostsBuilder.append("mygw.alipay.com,");
            hostsBuilder.append("alipay.up.django.t.taobao.com,");
            hostsBuilder.append("alipay.dl.django.t.taobao.com,");
            hostsBuilder.append("api.django.t.taobao.com,");
            hostsBuilder.append("oalipay-dl-django.alicdn.com,");
            hostsBuilder.append("mobilepmgw.alipay.com,");
            hostsBuilder.append("mcgw.alipay.com,");
            hostsBuilder.append("img01.taobaocdn.com,");
        }
        try {
            String gwHost = new URL(ReadSettingServerUrl.getInstance().getGWFURL(this.a)).getHost();
            if (!"mobilegw.alipay.com".equals(gwHost)) {
                hostsBuilder.append(gwHost).append(",");
            }
        } catch (Exception e) {
            LogCatUtil.warn((String) "AlipayHttpDnsInitRunnable", "equals gwHost  exception : " + e.toString());
        }
        hostsBuilder.append("mobilegw.alipay.com,");
        hostsBuilder.append("bkmobilegw.mybank.cn,");
        hostsBuilder.append("t.alipayobjects.com,");
        hostsBuilder.append("tfs.alipayobjects.com,");
        hostsBuilder.append("i.alipayobjects.com,");
        hostsBuilder.append("pic.alipayobjects.com,");
        hostsBuilder.append("mdgw.alipay.com,");
        hostsBuilder.append("mugw.alipay.com,");
        hostsBuilder.append("os.alipayobjects.com,");
        hostsBuilder.append("mygwshort.alipay.com,");
        hostsBuilder.append("zos.alipayobjects.com,");
        hostsBuilder.append("dl-sh.django.t.taobao.com,");
        hostsBuilder.append("amdc.alipay.com,");
        hostsBuilder.append("dl.django.t.taobao.com,");
        hostsBuilder.append("render.alipay.com,");
        hostsBuilder.append("api-mayi.django.t.taobao.com,");
        hostsBuilder.append("up-mayi.django.t.taobao.com,");
        hostsBuilder.append("gw.alicdn.com,");
        hostsBuilder.append("gw.alipayobjects.com,");
        hostsBuilder.append("mdap.alipaylog.com,");
        return hostsBuilder.toString();
    }
}
