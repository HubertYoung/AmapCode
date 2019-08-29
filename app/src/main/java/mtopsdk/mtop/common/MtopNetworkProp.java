package mtopsdk.mtop.common;

import android.os.Handler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import mtopsdk.mtop.domain.ApiTypeEnum;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.domain.MethodEnum;
import mtopsdk.mtop.domain.ProtocolEnum;

public class MtopNetworkProp implements Serializable {
    private static final long serialVersionUID = -3528337805304245196L;
    public String accessToken;
    public ApiTypeEnum apiType;
    public String authCode;
    public boolean autoRedirect = true;
    public boolean backGround;
    public int bizId;
    public List<String> cacheKeyBlackList = null;
    public String clientTraceId;
    public int connTimeout = 10000;
    public String customDailyDomain;
    public String customDomain;
    public String customOnlineDomain;
    public String customPreDomain;
    public boolean enableProgressListener;
    public EnvModeEnum envMode = EnvModeEnum.ONLINE;
    public boolean forceRefreshCache = false;
    public Handler handler;
    public boolean isInnerOpen;
    public MethodEnum method = MethodEnum.GET;
    public int netParam;
    public String openAppKey = "DEFAULT_AUTH";
    public String pageName;
    public String pageUrl;
    public Map<String, String> priorityData = null;
    public boolean priorityFlag;
    public ProtocolEnum protocol = ProtocolEnum.HTTPSECURE;
    public Map<String, String> queryParameterMap;
    public String reqAppKey;
    public String reqBizExt;
    public Object reqContext = null;
    public int reqSource;
    public String reqUserId;
    public Map<String, String> requestHeaders;
    public int retryTimes = 1;
    public boolean skipCacheCallback = false;
    public int socketTimeout = HttpConstants.CONNECTION_TIME_OUT;
    public boolean timeCalibrated = false;
    public String ttid;
    public boolean useCache = false;
    public String userInfo = "DEFAULT";
    public int wuaFlag = -1;

    @Deprecated
    public ProtocolEnum getProtocol() {
        fff.a();
        if (!fff.e()) {
            return ProtocolEnum.HTTP;
        }
        return this.protocol;
    }

    @Deprecated
    public void setProtocol(ProtocolEnum protocolEnum) {
        if (protocolEnum != null) {
            this.protocol = protocolEnum;
        }
    }

    @Deprecated
    public MethodEnum getMethod() {
        return this.method;
    }

    @Deprecated
    public void setMethod(MethodEnum methodEnum) {
        if (methodEnum != null) {
            this.method = methodEnum;
        }
    }

    @Deprecated
    public Map<String, String> getRequestHeaders() {
        return this.requestHeaders;
    }

    @Deprecated
    public void setRequestHeaders(Map<String, String> map) {
        this.requestHeaders = map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("MtopNetworkProp [ protocol=");
        sb.append(this.protocol);
        sb.append(", method=");
        sb.append(this.method);
        sb.append(", envMode=");
        sb.append(this.envMode);
        sb.append(", autoRedirect=");
        sb.append(this.autoRedirect);
        sb.append(", retryTimes=");
        sb.append(this.retryTimes);
        sb.append(", requestHeaders=");
        sb.append(this.requestHeaders);
        sb.append(", timeCalibrated=");
        sb.append(this.timeCalibrated);
        sb.append(", ttid=");
        sb.append(this.ttid);
        sb.append(", useCache=");
        sb.append(this.useCache);
        sb.append(", forceRefreshCache=");
        sb.append(this.forceRefreshCache);
        sb.append(", cacheKeyBlackList=");
        sb.append(this.cacheKeyBlackList);
        if (this.apiType != null) {
            sb.append(", apiType=");
            sb.append(this.apiType.getApiType());
            sb.append(", openAppKey=");
            sb.append(this.openAppKey);
            sb.append(", accessToken=");
            sb.append(this.accessToken);
        }
        sb.append(", queryParameterMap=");
        sb.append(this.queryParameterMap);
        sb.append(", connTimeout=");
        sb.append(this.connTimeout);
        sb.append(", socketTimeout=");
        sb.append(this.socketTimeout);
        sb.append(", bizId=");
        sb.append(this.bizId);
        sb.append(", reqBizExt=");
        sb.append(this.reqBizExt);
        sb.append(", reqUserId=");
        sb.append(this.reqUserId);
        sb.append(", reqAppKey=");
        sb.append(this.reqAppKey);
        sb.append(", authCode=");
        sb.append(this.authCode);
        sb.append(", clientTraceId =");
        sb.append(this.clientTraceId);
        sb.append(", netParam=");
        sb.append(this.netParam);
        sb.append(", reqSource=");
        sb.append(this.reqSource);
        sb.append("]");
        return sb.toString();
    }
}
