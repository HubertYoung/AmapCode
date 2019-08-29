package com.amap.location.uptunnel.core;

import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import com.amap.location.common.e.c;
import com.amap.location.uptunnel.ConfigContainer;
import com.amap.location.uptunnel.core.c.b;
import com.amap.location.uptunnel.core.c.d;
import java.nio.charset.Charset;

/* compiled from: UpTunnelContainer */
public class a {
    private static Charset a = Charset.forName("UTF-8");
    private b b;
    private d c;
    private d d;
    private d e;
    private d f;
    private b g;
    private c h;

    public void a(@NonNull b bVar, @NonNull ConfigContainer configContainer, @NonNull Looper looper) {
        this.g = bVar;
        this.h = configContainer.mHttpClient;
        if (configContainer.mCountTunnelConfig != null) {
            this.b = new b();
            this.b.a(this.g, configContainer.mCountTunnelConfig, this.h, looper);
        }
        if (configContainer.mEventTunnelConfig != null) {
            this.c = new d();
            this.c.a(this.g, configContainer.mEventTunnelConfig, this.h, 2, looper);
        }
        if (configContainer.mDataBlockTunnelConfig != null) {
            this.d = new d();
            this.d.a(this.g, configContainer.mDataBlockTunnelConfig, this.h, 5, looper);
        }
        if (configContainer.mKeyLogConfig != null) {
            this.e = new d();
            this.e.a(this.g, configContainer.mKeyLogConfig, this.h, 3, looper);
        }
        if (configContainer.mLogConfig != null) {
            this.f = new d();
            this.f.a(this.g, configContainer.mLogConfig, this.h, 4, looper);
        }
    }

    public void a(Message message) {
        switch (message.arg1) {
            case 1:
                if (this.b != null) {
                    this.b.a(message.arg2);
                    return;
                }
                break;
            case 2:
                if (this.c != null) {
                    this.c.a(message.arg2, (byte[]) message.obj);
                    return;
                }
                break;
            case 3:
                if (this.e != null) {
                    this.e.a(message.arg2, (byte[]) message.obj);
                    return;
                }
                break;
            case 4:
                if (this.f != null) {
                    this.f.a(message.arg2, ((String) message.obj).getBytes(a));
                    break;
                }
                break;
            case 5:
                if (this.d != null) {
                    this.d.a(message.arg2, (byte[]) message.obj);
                    return;
                }
                break;
        }
    }

    public void a() {
        int c2 = this.g.c();
        if (this.b != null) {
            this.b.b(c2);
        }
        if (this.c != null) {
            this.c.a(c2);
        }
        if (this.e != null) {
            this.e.a(c2);
        }
        if (this.f != null) {
            this.f.a(c2);
        }
        if (this.d != null) {
            this.d.a(c2);
        }
    }

    public void b() {
        if (this.b != null) {
            this.b.a();
        }
        if (this.c != null) {
            this.c.a();
        }
        if (this.e != null) {
            this.e.a();
        }
        if (this.f != null) {
            this.f.a();
        }
        if (this.d != null) {
            this.d.a();
        }
    }

    public void a(@NonNull com.amap.location.uptunnel.a.a aVar) {
        if ((aVar instanceof com.amap.location.uptunnel.a.c) && this.g.c() != -1) {
            com.amap.location.uptunnel.a.c cVar = (com.amap.location.uptunnel.a.c) aVar;
            switch (cVar.c()) {
                case 3:
                    if (this.e != null) {
                        this.e.b();
                        break;
                    }
                    break;
                case 4:
                    if (this.f != null) {
                        this.f.b();
                        break;
                    }
                    break;
            }
            new Thread(new com.amap.location.uptunnel.a.a.a(this.g, cVar, this.h), "command_thread").start();
        }
    }
}
