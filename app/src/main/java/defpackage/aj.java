package defpackage;

import anet.channel.statist.RequestStatistic;

/* renamed from: aj reason: default package */
/* compiled from: FlowStat */
public final class aj {
    public String a;
    public String b;
    public String c;
    public long d;
    public long e;

    public aj() {
    }

    public aj(String str, RequestStatistic requestStatistic) {
        this.a = str;
        this.b = requestStatistic.protocolType;
        this.c = requestStatistic.url;
        this.d = requestStatistic.sendDataSize;
        this.e = requestStatistic.recDataSize;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FlowStat{refer='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", protocoltype='");
        sb.append(this.b);
        sb.append('\'');
        sb.append(", req_identifier='");
        sb.append(this.c);
        sb.append('\'');
        sb.append(", upstream=");
        sb.append(this.d);
        sb.append(", downstream=");
        sb.append(this.e);
        sb.append('}');
        return sb.toString();
    }
}
