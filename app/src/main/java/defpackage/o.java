package defpackage;

import anet.channel.statist.RequestStatistic;
import java.util.List;
import java.util.Map;

/* renamed from: o reason: default package */
/* compiled from: RequestCb */
public interface o {
    void onDataReceive(aa aaVar, boolean z);

    void onFinish(int i, String str, RequestStatistic requestStatistic);

    void onResponseCode(int i, Map<String, List<String>> map);
}
