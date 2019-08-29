package defpackage;

import com.yunos.carkitsdk.TransferInfo;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: fcd reason: default package */
/* compiled from: FileTransferManager */
public final class fcd {
    Map<Integer, fcc> a = new ConcurrentHashMap();
    public Map<Integer, fcc> b = new ConcurrentHashMap();
    public Map<Integer, fcb> c = new ConcurrentHashMap();
    public Map<Integer, fca> d = new ConcurrentHashMap();
    Vector<fcb> e = new Vector<>();
    public fce f;
    int g;
    int h = 1;

    public fcd(fce fce) {
        this.f = fce;
    }

    public final void a() {
        if (this.g > 0) {
            this.g--;
        }
        if (this.e.size() > 0) {
            this.g++;
            fcb remove = this.e.remove(0);
            this.c.put(Integer.valueOf(remove.a.i), remove);
            new Thread(remove).start();
            new StringBuilder("mFileSender.size()=").append(this.c.size());
            new StringBuilder("startNextSender concurrent=").append(this.g);
        }
    }

    public final void a(int i, int i2) {
        int i3 = i;
        int i4 = i2;
        "cancelFileTransferByPeerId peerid=".concat(String.valueOf(i));
        Iterator<fcc> it = this.b.values().iterator();
        while (true) {
            if (it.hasNext()) {
                fcc next = it.next();
                if (next.j == i3) {
                    this.b.remove(Integer.valueOf(next.i));
                    TransferInfo transferInfo = new TransferInfo(next.a, next.g, next.b, next.h, next.c, next.d, next.i, next.k);
                    transferInfo.l = i4;
                    this.f.c(transferInfo);
                    break;
                }
            } else {
                break;
            }
        }
        for (fca next2 : this.d.values()) {
            if (next2.a.j == i3) {
                this.d.remove(Integer.valueOf(i));
                next2.a(i4);
                next2.a();
                return;
            }
        }
    }

    public final void b(int i, int i2) {
        int i3 = i2;
        "cancelSenderTransferByLocalId transferId=".concat(String.valueOf(i));
        fcc fcc = this.a.get(Integer.valueOf(i));
        if (fcc != null) {
            TransferInfo transferInfo = r4;
            TransferInfo transferInfo2 = new TransferInfo(fcc.a, fcc.g, fcc.b, fcc.h, fcc.c, fcc.d, fcc.i, fcc.k);
            transferInfo.l = i3;
            transferInfo.h = 5;
            this.f.b(transferInfo);
        }
        this.a.remove(Integer.valueOf(i));
        fcb fcb = this.c.get(Integer.valueOf(i));
        if (fcb != null) {
            fcb.a(i3);
            fcb.a();
        }
        Iterator<fcb> it = this.e.iterator();
        while (it.hasNext()) {
            fcb next = it.next();
            if (next.a.i == i) {
                TransferInfo transferInfo3 = next.a;
                transferInfo3.l = i3;
                transferInfo3.h = 5;
                this.f.b(transferInfo3);
                this.e.remove(next);
                return;
            }
        }
    }

    public final void c(int i, int i2) {
        int i3 = i2;
        "cancelFileTransferByLocalId transferId=".concat(String.valueOf(i));
        fcc fcc = this.a.get(Integer.valueOf(i));
        if (fcc != null) {
            TransferInfo transferInfo = r4;
            TransferInfo transferInfo2 = new TransferInfo(fcc.a, fcc.g, fcc.b, fcc.h, fcc.c, fcc.d, fcc.i, fcc.k);
            transferInfo.l = i3;
            transferInfo.h = 5;
            this.f.b(transferInfo);
        }
        this.a.remove(Integer.valueOf(i));
        fcc fcc2 = this.b.get(Integer.valueOf(i));
        if (fcc2 != null) {
            TransferInfo transferInfo3 = new TransferInfo(fcc2.a, fcc2.g, fcc2.b, fcc2.h, fcc2.c, fcc2.d, fcc2.i, fcc2.k);
            transferInfo3.l = i3;
            transferInfo3.h = 5;
            this.f.c(transferInfo3);
        }
        this.b.remove(Integer.valueOf(i));
        fcb fcb = this.c.get(Integer.valueOf(i));
        if (fcb != null) {
            fcb.a(i3);
        }
        fca fca = this.d.get(Integer.valueOf(i));
        if (fca != null) {
            fca.a(i3);
        }
        Iterator<fcb> it = this.e.iterator();
        while (it.hasNext()) {
            fcb next = it.next();
            if (next.a.i == i) {
                TransferInfo transferInfo4 = next.a;
                transferInfo4.l = i3;
                transferInfo4.h = 5;
                this.f.b(transferInfo4);
                this.e.remove(next);
                return;
            }
        }
    }

    public final void b() {
        this.b.clear();
        this.a.clear();
        for (fcb a2 : this.c.values()) {
            a2.a();
        }
        this.c.clear();
        for (fca a3 : this.d.values()) {
            a3.a();
        }
        this.d.clear();
        this.g = 0;
        this.e.clear();
    }

    public final void a(int i) {
        this.c.remove(Integer.valueOf(i));
    }

    public final void b(int i) {
        this.d.remove(Integer.valueOf(i));
    }
}
