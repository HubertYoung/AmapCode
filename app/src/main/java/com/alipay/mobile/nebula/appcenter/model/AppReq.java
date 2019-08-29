package com.alipay.mobile.nebula.appcenter.model;

import java.io.Serializable;

public class AppReq implements Serializable {
    public static final int OPEN_PLAT_REQ_MODE_All = 1;
    public static final int OPEN_PLAT_REQ_MODE_HPM = 3;
    public static final int OPEN_PLAT_REQ_MODE_ONE = 2;
    public static final String REQMODE_ASYNC = "async";
    public static final String REQMODE_SYNCFORCE = "syncforce";
    public static final String REQMODE_SYNCTRY = "synctry";
    public String bundleid;
    public String channel;
    public String client;
    public String clientExtra;
    public String diu;
    public String env;
    public String existed;
    public String grayRules;
    public String httpReqUrl;
    public String localAppInfo;
    public String nbsource;
    public int openPlatReqMode = 2;
    public String platform;
    public String preferLocal;
    public String query;
    public String reqmode;
    public String scene;
    public String sdk;
    public String stableRpc;
    public String system;

    public String toString() {
        return "AppReq{system='" + this.system + '\'' + ", client='" + this.client + '\'' + ", sdk='" + this.sdk + '\'' + ", platform='" + this.platform + '\'' + ", env='" + this.env + '\'' + ", channel='" + this.channel + '\'' + ", bundleid='" + this.bundleid + '\'' + ", query='" + this.query + '\'' + ", existed='" + this.existed + '\'' + ", grayRules='" + this.grayRules + '\'' + ", localAppInfo='" + this.localAppInfo + '\'' + ", stableRpc='" + this.stableRpc + '\'' + ", scene='" + this.scene + '\'' + ", nbsource='" + this.nbsource + '\'' + ", preferLocal='" + this.preferLocal + '\'' + ", reqmode='" + this.reqmode + '\'' + ", httpReqUrl='" + this.httpReqUrl + '\'' + ", openPlatReqMode=" + this.openPlatReqMode + ", diu='" + this.diu + '\'' + ", clientExtra='" + this.clientExtra + '\'' + '}';
    }
}
