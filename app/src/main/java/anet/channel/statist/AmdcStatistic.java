package anet.channel.statist;

import anet.channel.status.NetworkStatusHelper;

@Monitor(module = "networkPrefer", monitorPoint = "amdc")
public class AmdcStatistic extends StatObject {
    @Dimension
    public String errorCode;
    @Dimension
    public String errorMsg;
    @Dimension
    public String host;
    @Dimension
    public String netType = NetworkStatusHelper.a().toString();
    @Dimension
    public String proxyType = NetworkStatusHelper.j();
    @Dimension
    public int retryTimes;
    @Dimension
    public String trace;
    @Dimension
    public String ttid = m.e();
    @Dimension
    public String url;
}
