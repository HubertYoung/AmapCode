package com.xiaomi.push.service;

import android.content.Context;
import android.os.IBinder.DeathRecipient;
import android.os.Messenger;
import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.tencent.connect.common.Constants;
import com.xiaomi.push.service.XMPushService.h;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class aq {
    private static aq a;
    private ConcurrentHashMap<String, HashMap<String, b>> b = new ConcurrentHashMap<>();
    private List<a> c = new ArrayList();

    public interface a {
        void a();
    }

    public static class b {
        public String a;
        public String b;
        public String c;
        public String d;
        public boolean e;
        public String f;
        public String g;
        public String h;
        public String i;
        public String j;
        public e k;
        public Context l;
        c m = c.unbind;
        c n = null;
        DeathRecipient o = null;
        final C0084b p = new C0084b();
        private int q = 0;
        private List<a> r = new ArrayList();
        /* access modifiers changed from: private */
        public XMPushService s;
        /* access modifiers changed from: private */
        public Messenger t;
        private boolean u = false;
        /* access modifiers changed from: private */
        public b v = new b(this);

        public interface a {
            void a(c cVar, c cVar2, int i);
        }

        /* renamed from: com.xiaomi.push.service.aq$b$b reason: collision with other inner class name */
        class C0084b extends h {
            int b;
            int c;
            String d;
            String e;

            public C0084b() {
                super(0);
            }

            public h a(int i, int i2, String str, String str2) {
                this.b = i;
                this.c = i2;
                this.e = str2;
                this.d = str;
                return this;
            }

            public void a() {
                if (b.this.a(this.b, this.c, this.e)) {
                    b.this.a(this.b, this.c, this.d, this.e);
                    return;
                }
                StringBuilder sb = new StringBuilder(" ignore notify client :");
                sb.append(b.this.h);
                com.xiaomi.channel.commonutils.logger.b.b(sb.toString());
            }

            public String b() {
                return "notify job";
            }
        }

        class c implements DeathRecipient {
            final b a;
            final Messenger b;

            c(b bVar, Messenger messenger) {
                this.a = bVar;
                this.b = messenger;
            }

            public void binderDied() {
                StringBuilder sb = new StringBuilder("peer died, chid = ");
                sb.append(this.a.h);
                com.xiaomi.channel.commonutils.logger.b.b(sb.toString());
                b.this.s.a((h) new as(this, 0), 0);
            }
        }

        public b() {
        }

        public b(XMPushService xMPushService) {
            this.s = xMPushService;
            a((a) new ar(this));
        }

        /* access modifiers changed from: private */
        public void a(int i2, int i3, String str, String str2) {
            this.n = this.m;
            if (i2 == 2) {
                this.k.a(this.l, this, i3);
            } else if (i2 == 3) {
                this.k.a(this.l, this, str2, str);
            } else {
                if (i2 == 1) {
                    boolean z = this.m == c.binded;
                    if (!z && "wait".equals(str2)) {
                        this.q++;
                    } else if (z) {
                        this.q = 0;
                    }
                    this.k.a(this.l, this, z, i3, str);
                }
            }
        }

        /* access modifiers changed from: private */
        public boolean a(int i2, int i3, String str) {
            StringBuilder sb;
            if (this.n == null || !this.u) {
                return true;
            }
            if (this.n == this.m) {
                sb = new StringBuilder(" status recovered, don't notify client:");
            } else if (this.t == null || !this.u) {
                sb = new StringBuilder("peer died, ignore notify ");
            } else {
                StringBuilder sb2 = new StringBuilder("Peer alive notify status to client:");
                sb2.append(this.h);
                com.xiaomi.channel.commonutils.logger.b.b(sb2.toString());
                return true;
            }
            sb.append(this.h);
            com.xiaomi.channel.commonutils.logger.b.b(sb.toString());
            return false;
        }

        private boolean b(int i2, int i3, String str) {
            switch (i2) {
                case 1:
                    if (this.m == c.binded || !this.s.f() || i3 == 21) {
                        return false;
                    }
                    return i3 != 7 || !"wait".equals(str);
                case 2:
                    return this.s.f();
                case 3:
                    return !"wait".equals(str);
                default:
                    return false;
            }
        }

        public String a(int i2) {
            switch (i2) {
                case 1:
                    return "OPEN";
                case 2:
                    return "CLOSE";
                case 3:
                    return "KICK";
                default:
                    return "unknown";
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            try {
                Messenger messenger = this.t;
                if (!(messenger == null || this.o == null)) {
                    messenger.getBinder().unlinkToDeath(this.o, 0);
                }
            } catch (Exception unused) {
            }
            this.n = null;
        }

        /* access modifiers changed from: 0000 */
        public void a(Messenger messenger) {
            a();
            if (messenger != null) {
                try {
                    this.t = messenger;
                    this.u = true;
                    this.o = new c(this, messenger);
                    messenger.getBinder().linkToDeath(this.o, 0);
                } catch (Exception e2) {
                    StringBuilder sb = new StringBuilder("peer linkToDeath err: ");
                    sb.append(e2.getMessage());
                    com.xiaomi.channel.commonutils.logger.b.b(sb.toString());
                    this.t = null;
                    this.u = false;
                }
            } else {
                StringBuilder sb2 = new StringBuilder("peer linked with old sdk chid = ");
                sb2.append(this.h);
                com.xiaomi.channel.commonutils.logger.b.b(sb2.toString());
            }
        }

        public void a(a aVar) {
            synchronized (this.r) {
                this.r.add(aVar);
            }
        }

        public void a(c cVar, int i2, int i3, String str, String str2) {
            synchronized (this.r) {
                for (a a2 : this.r) {
                    a2.a(this.m, cVar, i3);
                }
            }
            int i4 = 0;
            if (this.m != cVar) {
                com.xiaomi.channel.commonutils.logger.b.a(String.format("update the client %7$s status. %1$s->%2$s %3$s %4$s %5$s %6$s", new Object[]{this.m, cVar, a(i2), at.a(i3), str, str2, this.h}));
                this.m = cVar;
            }
            if (this.k == null) {
                com.xiaomi.channel.commonutils.logger.b.d("status changed while the client dispatcher is missing");
            } else if (cVar != c.binding) {
                if (this.n != null && this.u) {
                    i4 = (this.t == null || !this.u) ? Constants.REQUEST_API : 1000;
                }
                this.s.b((h) this.p);
                if (b(i2, i3, str2)) {
                    a(i2, i3, str, str2);
                } else {
                    this.s.a(this.p.a(i2, i3, str, str2), (long) i4);
                }
            }
        }

        public long b() {
            return (((long) ((Math.random() * 20.0d) - 10.0d)) + ((long) ((this.q + 1) * 15))) * 1000;
        }

        public void b(a aVar) {
            synchronized (this.r) {
                this.r.remove(aVar);
            }
        }
    }

    public enum c {
        unbind,
        binding,
        binded
    }

    private aq() {
    }

    public static synchronized aq a() {
        aq aqVar;
        synchronized (aq.class) {
            try {
                if (a == null) {
                    a = new aq();
                }
                aqVar = a;
            }
        }
        return aqVar;
    }

    private String d(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int indexOf = str.indexOf(AUScreenAdaptTool.PREFIX_ID);
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        return str;
    }

    public synchronized void a(Context context) {
        for (HashMap<String, b> values : this.b.values()) {
            for (b a2 : values.values()) {
                a2.a(c.unbind, 1, 3, (String) null, (String) null);
            }
        }
    }

    public synchronized void a(Context context, int i) {
        for (HashMap<String, b> values : this.b.values()) {
            for (b a2 : values.values()) {
                a2.a(c.unbind, 2, i, (String) null, (String) null);
            }
        }
    }

    public synchronized void a(a aVar) {
        this.c.add(aVar);
    }

    public synchronized void a(b bVar) {
        HashMap hashMap = this.b.get(bVar.h);
        if (hashMap == null) {
            hashMap = new HashMap();
            this.b.put(bVar.h, hashMap);
        }
        hashMap.put(d(bVar.b), bVar);
        for (a a2 : this.c) {
            a2.a();
        }
    }

    public synchronized void a(String str) {
        HashMap hashMap = this.b.get(str);
        if (hashMap != null) {
            for (b a2 : hashMap.values()) {
                a2.a();
            }
            hashMap.clear();
            this.b.remove(str);
        }
        for (a a3 : this.c) {
            a3.a();
        }
    }

    public synchronized void a(String str, String str2) {
        HashMap hashMap = this.b.get(str);
        if (hashMap != null) {
            b bVar = (b) hashMap.get(d(str2));
            if (bVar != null) {
                bVar.a();
            }
            hashMap.remove(d(str2));
            if (hashMap.isEmpty()) {
                this.b.remove(str);
            }
        }
        for (a a2 : this.c) {
            a2.a();
        }
    }

    public synchronized b b(String str, String str2) {
        try {
            HashMap hashMap = this.b.get(str);
            if (hashMap == null) {
                return null;
            }
            return (b) hashMap.get(d(str2));
        }
    }

    public synchronized ArrayList<b> b() {
        ArrayList<b> arrayList;
        try {
            arrayList = new ArrayList<>();
            for (HashMap<String, b> values : this.b.values()) {
                arrayList.addAll(values.values());
            }
        }
        return arrayList;
    }

    public synchronized List<String> b(String str) {
        ArrayList arrayList;
        try {
            arrayList = new ArrayList();
            for (HashMap<String, b> values : this.b.values()) {
                for (b bVar : values.values()) {
                    if (str.equals(bVar.a)) {
                        arrayList.add(bVar.h);
                    }
                }
            }
        }
        return arrayList;
    }

    public synchronized int c() {
        try {
        }
        return this.b.size();
    }

    public synchronized Collection<b> c(String str) {
        try {
            if (!this.b.containsKey(str)) {
                return new ArrayList();
            }
            return ((HashMap) this.b.get(str).clone()).values();
        }
    }

    public synchronized void d() {
        Iterator<b> it = b().iterator();
        while (it.hasNext()) {
            it.next().a();
        }
        this.b.clear();
    }

    public synchronized void e() {
        this.c.clear();
    }
}
