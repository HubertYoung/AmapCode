package defpackage;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/* renamed from: cxi reason: default package */
/* compiled from: EvaluateHandler */
public class cxi {
    private static cxi b;
    public Handler a = null;

    private cxi() {
    }

    public static cxi a() {
        if (b == null) {
            synchronized (cxi.class) {
                try {
                    if (b == null) {
                        cxi cxi = new cxi();
                        b = cxi;
                        HandlerThread handlerThread = new HandlerThread("-EvAluAtE-");
                        handlerThread.start();
                        cxi.a = new Handler(handlerThread.getLooper()) {
                            public final void handleMessage(Message message) {
                                super.handleMessage(message);
                                int i = message.what;
                                if (i == 256) {
                                    cxa.a(message);
                                } else if (i == 512) {
                                    cxa.b(message);
                                } else if (i == 768) {
                                    cxa.c(message);
                                } else if (i != 1024) {
                                    if (i == 1280) {
                                        cxa.e(message);
                                    }
                                } else {
                                    cxa.d(message);
                                }
                            }
                        };
                    }
                }
            }
        }
        return b;
    }
}
