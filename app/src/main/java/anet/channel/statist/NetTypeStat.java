package anet.channel.statist;

import anet.channel.status.NetworkStatusHelper;

@Monitor(module = "networkPrefer", monitorPoint = "nettype")
public class NetTypeStat extends StatObject {
    @Dimension
    public String carrierName = NetworkStatusHelper.d();
    @Dimension
    public int ipStackType;
    @Dimension
    public int lastIpStackType;
    @Dimension
    public String mnc = NetworkStatusHelper.e();
    @Dimension
    public String nat64Prefix;
    @Dimension
    public String netType = NetworkStatusHelper.a().getType();
}
