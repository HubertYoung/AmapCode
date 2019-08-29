package defpackage;

import anetwork.channel.aidl.ParcelableInputStream;
import java.util.List;
import java.util.Map;

/* renamed from: df reason: default package */
/* compiled from: NetworkCallBack */
public final class df {

    /* renamed from: df$a */
    /* compiled from: NetworkCallBack */
    public interface a extends dh {
        void onFinished(defpackage.dg.a aVar, Object obj);
    }

    /* renamed from: df$b */
    /* compiled from: NetworkCallBack */
    public interface b extends dh {
        void onInputStreamGet(ParcelableInputStream parcelableInputStream, Object obj);
    }

    /* renamed from: df$c */
    /* compiled from: NetworkCallBack */
    public interface c extends dh {
    }

    /* renamed from: df$d */
    /* compiled from: NetworkCallBack */
    public interface d extends dh {
        boolean onResponseCode(int i, Map<String, List<String>> map, Object obj);
    }
}
