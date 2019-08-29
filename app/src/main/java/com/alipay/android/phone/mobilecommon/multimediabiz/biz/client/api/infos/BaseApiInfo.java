package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.EnvSwitcher;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ServerAddress;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ServerAddress.ServerType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import java.util.ArrayList;

public class BaseApiInfo {
    private static ServerAddress a;
    protected String api;
    protected String apiPath;
    protected String host;
    protected HttpMethod httpMethod;
    protected ArrayList<String> ips;
    protected int port = 80;
    protected ServerType serverType;
    protected String urlApi;

    static {
        a = null;
        a = EnvSwitcher.getCurrentEnv().getServerAddress();
    }

    public BaseApiInfo(ServerType serverType2, String apiPath2, HttpMethod httpMethod2) {
        this.serverType = serverType2;
        this.apiPath = apiPath2;
        this.httpMethod = httpMethod2;
        initServerAddress(a);
    }

    public void initServerAddress(ServerAddress serverAddress) {
        switch (this.serverType) {
            case UPLOAD:
                this.port = serverAddress.getUploadServerPort();
                this.host = serverAddress.getUploadServerHost();
                return;
            case DOWNLOAD:
                this.port = serverAddress.getDownloandServerPort();
                this.host = serverAddress.getDownloadServerHost();
                return;
            case API:
                this.port = serverAddress.getApiServerPort();
                this.host = serverAddress.getApiServerHost();
                return;
            default:
                return;
        }
    }

    public String getApi() {
        this.api = String.format(DjangoConstant.API_URL_FORMAT_HTTP, new Object[]{getHost(), this.apiPath});
        return this.api;
    }

    public String getHost() {
        return this.host;
    }

    public String getUrlApi() {
        if (TextUtils.isEmpty(this.urlApi)) {
            this.urlApi = String.format(DjangoConstant.API_URL_FORMAT_HTTP, new Object[]{getHost(), this.apiPath});
        }
        return this.urlApi;
    }

    public String getIp() {
        this.ips = ApiInfoUtil.getIpInfosByHost(this.host);
        return this.ips.get(0);
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }
}
