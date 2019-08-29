package mtopsdk.network.domain;

import java.io.Serializable;

public class NetworkStats implements Serializable, Cloneable {
    private static final long serialVersionUID = -3538602124202475612L;
    public String connectionType = "";
    public long dataSpeed = 0;
    public long firstDataTime = 0;
    public String host = "";
    public String ip_port = "";
    public boolean isRequestSuccess = false;
    public boolean isSSL = false;
    public String netStatSum;
    public long oneWayTime_ANet = 0;
    public long recDataTime = 0;
    public long recvSize = 0;
    public int resultCode = 0;
    public int retryTimes;
    public long sendSize = 0;
    public long sendWaitTime = 0;
    public long serverRT = 0;

    public String sumNetStat() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("oneWayTime_ANet=");
        sb.append(this.oneWayTime_ANet);
        sb.append(",resultCode=");
        sb.append(this.resultCode);
        sb.append(",isRequestSuccess=");
        sb.append(this.isRequestSuccess);
        sb.append(",host=");
        sb.append(this.host);
        sb.append(",ip_port=");
        sb.append(this.ip_port);
        sb.append(",isSSL=");
        sb.append(this.isSSL);
        sb.append(",connType=");
        sb.append(this.connectionType);
        sb.append(",firstDataTime=");
        sb.append(this.firstDataTime);
        sb.append(",recDataTime=");
        sb.append(this.recDataTime);
        sb.append(",sendWaitTime=");
        sb.append(this.sendWaitTime);
        sb.append(",serverRT=");
        sb.append(this.serverRT);
        sb.append(",sendSize=");
        sb.append(this.sendSize);
        sb.append(",recvSize=");
        sb.append(this.recvSize);
        sb.append(",dataSpeed=");
        sb.append(this.dataSpeed);
        sb.append(",retryTimes=");
        sb.append(this.retryTimes);
        return sb.toString();
    }

    public String toString() {
        if (fdd.b(this.netStatSum)) {
            this.netStatSum = sumNetStat();
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append("NetworkStats [");
        sb.append(this.netStatSum);
        sb.append("]");
        return sb.toString();
    }
}
