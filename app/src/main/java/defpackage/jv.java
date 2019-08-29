package defpackage;

import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: jv reason: default package */
/* compiled from: UpdateHintConfigDownloader */
public final class jv {
    private ConcurrentHashMap<String, Boolean> a = new ConcurrentHashMap<>();
    private final List<bph> b = new ArrayList();

    /* renamed from: jv$a */
    /* compiled from: UpdateHintConfigDownloader */
    public interface a {
        void a();

        void b();
    }

    /* renamed from: jv$b */
    /* compiled from: UpdateHintConfigDownloader */
    static class b implements bjf {
        List<defpackage.ji.b> a;
        defpackage.ji.b b;
        String c;
        a d;
        String e;
        Map<String, Boolean> f;

        public final void onProgressUpdate(long j, long j2) {
        }

        public final void onStart(long j, Map<String, List<String>> map, int i) {
        }

        public b(List<defpackage.ji.b> list, defpackage.ji.b bVar, String str, String str2, Map<String, Boolean> map, a aVar) {
            this.a = list;
            this.b = bVar;
            this.c = str;
            this.d = aVar;
            this.e = str2;
            this.f = map;
        }

        public final void onFinish(bpk bpk) {
            aho.a(new Runnable() {
                public final void run() {
                    File file = new File(b.this.a());
                    if (!file.exists()) {
                        b.this.d.b();
                        return;
                    }
                    final File absoluteFile = file.getAbsoluteFile();
                    ahn.b().execute(new Runnable() {
                        public final void run() {
                            File absoluteFile = absoluteFile.getAbsoluteFile();
                            if (absoluteFile != null && absoluteFile.isFile() && !TextUtils.isEmpty(b.this.b.d)) {
                                if (!jw.a(absoluteFile, b.this.b.d)) {
                                    absoluteFile.delete();
                                    return;
                                }
                                new StringBuilder("file have download complete,filePath:").append(absoluteFile.getAbsolutePath());
                            }
                            jw.a(b.this.b.d, b.this.e);
                            if ("LOTTIE".equals(b.this.b.f) && absoluteFile != null && absoluteFile.exists() && absoluteFile.isFile() && jw.b(absoluteFile.getAbsolutePath(), b.this.c)) {
                                new StringBuilder("unzip complete:").append(absoluteFile.getAbsolutePath());
                            }
                            if (jw.a(b.this.c, b.this.a)) {
                                if (b.this.f != null) {
                                    b.this.f.clear();
                                }
                                b.this.d.a();
                            }
                        }
                    });
                }
            });
        }

        public final void onError(int i, int i2) {
            aho.a(new Runnable() {
                public final void run() {
                    b.this.d.b();
                }
            });
        }

        public final String a() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.c);
            sb.append(jw.d(this.b.c));
            return sb.toString();
        }
    }

    /* JADX INFO: finally extract failed */
    public final void a(List<defpackage.ji.b> list, String str, String str2, a aVar) {
        String str3;
        String str4 = str2;
        synchronized (this.b) {
            try {
                a();
                HashMap hashMap = new HashMap(5);
                for (defpackage.ji.b next : list) {
                    StringBuilder sb = new StringBuilder("prepare download file:fileMD5");
                    sb.append(next.d);
                    sb.append(",fileURL:");
                    sb.append(next.c);
                    if (TextUtils.isEmpty(next.d) || TextUtils.isEmpty(next.c) || hashMap.containsKey(next.c)) {
                        String str5 = str;
                    } else {
                        hashMap.put(next.c, Boolean.TRUE);
                        if (!jw.c(next.d) || !jw.b(str4, next)) {
                            if (jw.c(next.d)) {
                                this.a.remove(next.d);
                                jw.b(next.d);
                            }
                            if (jw.b(str4, next)) {
                                this.a.put(next.d, Boolean.TRUE);
                                str3 = str;
                                jw.a(next.d, str3);
                            } else {
                                str3 = str;
                            }
                            b bVar = new b(list, next, str4, str3, this.a, aVar);
                            bjg bjg = new bjg(bVar.a());
                            bjg.setUrl(next.c);
                            bjg.b = true;
                            yq.a();
                            yq.a(bjg, (bjf) bVar);
                            this.a.put(next.d, Boolean.TRUE);
                            StringBuilder sb2 = new StringBuilder("downLoad configFile,MD5:");
                            sb2.append(next.d);
                            sb2.append(",URL:");
                            sb2.append(next.c);
                        } else {
                            StringBuilder sb3 = new StringBuilder("file have downLoad complete,MD5:");
                            sb3.append(next.d);
                            sb3.append(",URL:");
                            sb3.append(next.c);
                        }
                    }
                }
                hashMap.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void a() {
        synchronized (this.b) {
            for (bph next : this.b) {
                if (!next.isCancelled()) {
                    next.cancel();
                }
            }
            this.b.clear();
            this.a.clear();
        }
    }
}
