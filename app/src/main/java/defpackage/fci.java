package defpackage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* renamed from: fci reason: default package */
/* compiled from: MessageSender */
public final class fci implements Runnable {
    private static String d = "CommandSession";
    private BlockingQueue<fcu> a = new LinkedBlockingQueue(100);
    private fch b;
    private boolean c;

    public fci(fch fch) {
        this.b = fch;
    }

    public final void a(fcu fcu) {
        try {
            this.a.put(fcu);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public final void a() {
        this.c = true;
        this.b = null;
    }

    public final void run() {
        while (!this.c) {
            try {
                fcu poll = this.a.poll(100, TimeUnit.MILLISECONDS);
                if (!(poll == null || this.b == null)) {
                    fch fch = this.b;
                    String str = null;
                    int i = poll.T;
                    if (i != 100) {
                        switch (i) {
                            case 1:
                                str = fch.a((fbw) poll);
                                break;
                            case 2:
                                str = fch.a((fbx) poll);
                                break;
                            case 3:
                                str = fch.a((fbz) poll);
                                break;
                            case 4:
                                str = fch.a((fcj) poll);
                                break;
                            case 5:
                                str = fch.a((fcq) poll);
                                break;
                            case 6:
                                str = fch.a((fcm) poll);
                                break;
                            case 7:
                                str = fch.a((fcl) poll);
                                break;
                            case 8:
                                str = fch.a((fbp) poll);
                                break;
                            case 9:
                                str = fch.a((fcf) poll);
                                break;
                            case 10:
                                str = fch.a((fbt) poll);
                                break;
                            case 11:
                                str = fch.a((fcp) poll);
                                break;
                            case 12:
                                str = fch.a((fbq) poll);
                                break;
                            case 13:
                                str = fch.a((fck) poll);
                                break;
                            case 14:
                                str = fch.a((fco) poll);
                                break;
                            case 15:
                                str = fch.a((fcn) poll);
                                break;
                        }
                    } else {
                        str = fch.a((fcr) poll);
                    }
                    if (str != null) {
                        try {
                            new StringBuilder("real sending len=").append(str.length());
                            "real sending------ ".concat(String.valueOf(str));
                            OutputStream outputStream = fch.c;
                            int length = str.getBytes().length;
                            ByteBuffer allocate = ByteBuffer.allocate(length + 10);
                            allocate.put("ABBC".getBytes());
                            allocate.put(1);
                            allocate.put(0);
                            allocate.put((byte) (length & 255));
                            allocate.put((byte) ((65280 & length) >> 8));
                            allocate.put((byte) ((16711680 & length) >> 16));
                            allocate.put((byte) (length >> 24));
                            allocate.put(str.getBytes());
                            outputStream.write(allocate.array());
                            new StringBuilder("send buffer=").append(allocate.array().length);
                            fch.c.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                if (fch.a != null) {
                                    fch.a.close();
                                }
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            } catch (InterruptedException unused) {
                return;
            }
        }
    }
}
