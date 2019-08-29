package defpackage;

/* renamed from: tg reason: default package */
/* compiled from: DownloadManager */
public final class tg {
    private static tg a;
    private static th b;

    private tg() {
    }

    public static synchronized tg a() {
        tg tgVar;
        synchronized (tg.class) {
            try {
                if (a == null) {
                    a = new tg();
                }
                tgVar = a;
            }
        }
        return tgVar;
    }

    public final synchronized void a(String str, String str2, a aVar) {
        th thVar = new th(str, str2, aVar);
        b = thVar;
        if (!(thVar.d != null && !thVar.d.isCancelled())) {
            th thVar2 = b;
            thVar2.d = new bjg(thVar2.c);
            thVar2.d.setUrl(thVar2.b);
            bjh.a().a(thVar2.d, thVar2.e);
        }
    }
}
