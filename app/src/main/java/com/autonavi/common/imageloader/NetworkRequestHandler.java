package com.autonavi.common.imageloader;

import android.net.NetworkInfo;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import java.io.IOException;
import java.io.InputStream;

public final class NetworkRequestHandler extends bkb {
    private final bkd a;
    private final bjr b;
    private final IDownloader c;

    public static class ContentLengthException extends IOException {
    }

    public final int a() {
        return 2;
    }

    public final boolean b() {
        return true;
    }

    public NetworkRequestHandler(IDownloader iDownloader, bkd bkd, bjr bjr) {
        this.c = iDownloader;
        this.a = bkd;
        this.b = bjr;
    }

    public final boolean a(bjz bjz) {
        String scheme = bjz.d.getScheme();
        return "http".equals(scheme) || "https".equals(scheme);
    }

    public final a a(bjz bjz, int i) throws IOException {
        a aVar;
        String a2 = bkh.a(bjz.d.toString());
        InputStream inputStream = null;
        if (NetworkPolicy.isOfflineOnly(i)) {
            if (this.b != null) {
                inputStream = this.b.a(a2);
            }
            return new a(inputStream, LoadedFrom.DISK);
        }
        if (NetworkPolicy.shouldReadFromDiskCache(i) && this.b != null) {
            InputStream a3 = this.b.a(a2);
            if (a3 != null) {
                return new a(a3, LoadedFrom.DISK);
            }
        }
        try {
            aVar = this.c.a(bjz.d);
            try {
                if (aVar.a == LoadedFrom.NETWORK && aVar.e > 0) {
                    bkd bkd = this.a;
                    bkd.c.sendMessage(bkd.c.obtainMessage(4, Long.valueOf(aVar.e)));
                }
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            aVar = null;
        }
        return aVar;
    }

    public final boolean a(NetworkInfo networkInfo) {
        return networkInfo == null || networkInfo.isConnected();
    }
}
