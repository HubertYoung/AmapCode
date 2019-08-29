package anet.channel.statist;

import org.json.JSONObject;

@Monitor(module = "networkPrefer", monitorPoint = "session")
public class SessionStatistic extends StatObject {
    public static int maxRetryTime;
    @Measure
    public long ackTime;
    @Measure(max = 15000.0d)
    public long authTime;
    @Measure
    public long cfRCount;
    @Dimension
    public String closeReason;
    @Measure(max = 15000.0d, name = "connTime")
    public long connectionTime;
    @Dimension(name = "protocolType")
    public String conntype;
    @Dimension
    public long errorCode;
    @Dimension
    public JSONObject extra = null;
    @Dimension
    public String host;
    @Measure
    public long inceptCount;
    @Dimension
    public String ip;
    @Dimension
    public int ipRefer = 0;
    @Dimension
    public int ipType = 1;
    @Dimension
    public boolean isBackground;
    public boolean isCommitted = false;
    @Dimension
    public long isKL;
    @Dimension
    public int isProxy = 0;
    @Dimension
    public String isTunnel;
    @Measure
    public int lastPingInterval;
    @Measure(max = 86400.0d)
    public long liveTime = 0;
    @Dimension
    public String netType;
    @Measure
    public long pRate;
    @Dimension
    public int port;
    @Measure
    public long ppkgCount;
    @Measure
    public long recvSizeCount;
    @Measure(constantValue = 1.0d)
    public long requestCount = 1;
    @Dimension
    public int ret;
    @Dimension
    public long retryTimes;
    @Dimension
    public int sdkv;
    @Measure
    public long sendSizeCount;
    @Measure(max = 15000.0d)
    public long sslCalTime;
    @Measure(max = 15000.0d)
    public long sslTime;
    @Measure(constantValue = 0.0d)
    public long stdRCount = 1;

    public SessionStatistic(af afVar) {
        this.ip = afVar.a();
        this.port = afVar.b();
        if (afVar.a != null) {
            this.ipRefer = afVar.a.c();
            this.ipType = afVar.a.b();
        }
        this.pRate = (long) afVar.d();
        this.conntype = afVar.c().toString();
        this.retryTimes = (long) afVar.d;
        maxRetryTime = afVar.e;
    }

    public boolean beforeCommit() {
        if (this.ret == 0 && (this.retryTimes != ((long) maxRetryTime) || this.errorCode == -2613 || this.errorCode == -2601)) {
            if (cl.a(1)) {
                cl.a("SessionStat no need commit", null, "retry:", Long.valueOf(this.retryTimes), "maxRetryTime", Integer.valueOf(maxRetryTime), "errorCode", Long.valueOf(this.errorCode));
            }
            return false;
        } else if (this.isCommitted) {
            return false;
        } else {
            this.isCommitted = true;
            return true;
        }
    }

    public bj getAlarmObject() {
        bj bjVar = new bj();
        bjVar.e = "networkPrefer";
        bjVar.f = "connect_succ_rate";
        bjVar.a = this.ret != 0;
        if (bjVar.a) {
            bjVar.b = this.closeReason;
        } else {
            bjVar.c = String.valueOf(this.errorCode);
        }
        return bjVar;
    }
}
