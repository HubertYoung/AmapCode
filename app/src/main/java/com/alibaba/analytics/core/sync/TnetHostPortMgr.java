package com.alibaba.analytics.core.sync;

import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.SystemConfigMgr;
import com.alibaba.analytics.core.config.SystemConfigMgr.IKVChangeListener;
import com.alibaba.analytics.utils.AppInfoUtil;
import com.alibaba.analytics.utils.SpSetting;

public class TnetHostPortMgr implements IKVChangeListener {
    public static final String TAG_TNET_HOST_PORT = "utanalytics_tnet_host_port";
    public static TnetHostPortMgr instance;
    public TnetHostPort entity;

    public static class TnetHostPort {
        public String host = "adashx.m.taobao.com";
        public int port = 443;

        public String getHost() {
            return this.host;
        }

        public int getPort() {
            return this.port;
        }
    }

    public static synchronized TnetHostPortMgr getInstance() {
        TnetHostPortMgr tnetHostPortMgr;
        synchronized (TnetHostPortMgr.class) {
            try {
                if (instance == null) {
                    instance = new TnetHostPortMgr();
                }
                tnetHostPortMgr = instance;
            }
        }
        return tnetHostPortMgr;
    }

    TnetHostPortMgr() {
        try {
            this.entity = new TnetHostPort();
            parseConifg(AppInfoUtil.getString(Variables.getInstance().getContext(), TAG_TNET_HOST_PORT));
            parseConifg(SpSetting.get(Variables.getInstance().getContext(), TAG_TNET_HOST_PORT));
            parseConifg(SystemConfigMgr.getInstance().get(TAG_TNET_HOST_PORT));
            SystemConfigMgr.getInstance().register(TAG_TNET_HOST_PORT, this);
        } catch (Throwable unused) {
        }
    }

    public TnetHostPort getEntity() {
        return this.entity;
    }

    public void onChange(String str, String str2) {
        parseConifg(str2);
    }

    private void parseConifg(String str) {
        if (!TextUtils.isEmpty(str)) {
            String trim = str.trim();
            int indexOf = trim.indexOf(":");
            if (indexOf != -1) {
                String substring = trim.substring(0, indexOf);
                int parseInt = Integer.parseInt(trim.substring(indexOf + 1, trim.length()));
                if (!TextUtils.isEmpty(substring) && parseInt > 0) {
                    this.entity.host = substring;
                    this.entity.port = parseInt;
                }
            }
        }
    }
}
