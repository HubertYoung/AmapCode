package anet.channel.statist;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.ConnProtocol;

@Monitor(module = "networkPrefer", monitorPoint = "horseRace")
public class HorseRaceStat extends StatObject {
    @Dimension
    public volatile String bssid = NetworkStatusHelper.g();
    @Dimension
    public volatile int connErrorCode;
    @Dimension
    public volatile int connRet = 0;
    @Measure
    public volatile long connTime;
    @Dimension
    public volatile String host;
    @Dimension
    public volatile String ip;
    @Dimension
    public volatile String mnc = NetworkStatusHelper.d();
    @Dimension
    public volatile String nettype = NetworkStatusHelper.b();
    @Dimension
    public volatile String path;
    @Dimension
    public volatile int port;
    @Dimension
    public volatile String protocol;
    @Dimension
    public volatile int reqErrorCode;
    @Dimension
    public volatile int reqRet = 0;
    @Measure
    public volatile long reqTime;

    public HorseRaceStat(String str, e eVar) {
        this.host = str;
        this.ip = eVar.a;
        this.port = eVar.b.a;
        this.protocol = ConnProtocol.valueOf(eVar.b).name;
        this.path = eVar.c;
    }
}
