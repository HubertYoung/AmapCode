package anet.channel.entity;

import android.text.TextUtils;
import anet.channel.strategy.ConnProtocol;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.iflytek.tts.TtsService.Tts;
import java.util.HashMap;
import java.util.Map;

public final class ConnType {
    public static ConnType a = new ConnType("http");
    public static ConnType b = new ConnType("https");
    private static Map<ConnProtocol, ConnType> e = new HashMap();
    public int c;
    public String d;
    private String f = "";

    @Deprecated
    public enum TypeLevel {
        SPDY,
        HTTP
    }

    private ConnType(String str) {
        this.f = str;
    }

    public static ConnType a(ConnProtocol connProtocol) {
        if (connProtocol == null) {
            return null;
        }
        if ("http".equalsIgnoreCase(connProtocol.protocol)) {
            return a;
        }
        if ("https".equalsIgnoreCase(connProtocol.protocol)) {
            return b;
        }
        synchronized (e) {
            try {
                if (e.containsKey(connProtocol)) {
                    ConnType connType = e.get(connProtocol);
                    return connType;
                }
                ConnType connType2 = new ConnType(connProtocol.toString());
                connType2.d = connProtocol.publicKey;
                if ("http2".equalsIgnoreCase(connProtocol.protocol)) {
                    connType2.c |= 8;
                } else if (ExtTransportStrategy.EXT_PROTO_SPDY.equalsIgnoreCase(connProtocol.protocol)) {
                    connType2.c |= 2;
                } else if ("h2s".equals(connProtocol.protocol)) {
                    connType2.c = 40;
                } else if ("quic".equalsIgnoreCase(connProtocol.protocol)) {
                    connType2.c = 12;
                } else if ("quicplain".equalsIgnoreCase(connProtocol.protocol)) {
                    connType2.c = Tts.TTS_ERROR_SIZE_EXCEED_BUFFER;
                }
                if (connType2.c == 0) {
                    return null;
                }
                if (!TextUtils.isEmpty(connProtocol.publicKey)) {
                    connType2.c |= 128;
                    if ("1rtt".equalsIgnoreCase(connProtocol.rtt)) {
                        connType2.c |= 8192;
                    } else if (!"0rtt".equalsIgnoreCase(connProtocol.rtt)) {
                        return null;
                    } else {
                        connType2.c |= 4096;
                    }
                }
                e.put(connProtocol, connType2);
                return connType2;
            }
        }
    }

    public final boolean a() {
        return (this.c & 4) != 0;
    }

    public final boolean b() {
        return equals(a) || equals(b);
    }

    public final boolean c() {
        return (this.c & 128) != 0 || (this.c & 32) != 0 || this.c == 12 || equals(b);
    }

    public final String toString() {
        return this.f;
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ConnType)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return this.f.equals(((ConnType) obj).f);
    }

    public final int d() {
        if (equals(a) || equals(b)) {
            return ai.b;
        }
        return ai.a;
    }

    private int e() {
        if ((this.c & 8) != 0) {
            return 0;
        }
        return (this.c & 2) != 0 ? 1 : 2;
    }

    public static int a(ConnType connType, ConnType connType2) {
        return connType.e() - connType2.e();
    }
}
