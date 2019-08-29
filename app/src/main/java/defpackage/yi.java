package defpackage;

/* renamed from: yi reason: default package */
/* compiled from: MQTTConfig */
public final class yi {
    public final String a;
    public final String b;
    public final String c;
    public final String d;
    public final String e;
    public final String f;
    public final String g;
    public final String h;
    public final String i;
    public final String j;
    public final String k;

    /* renamed from: yi$a */
    /* compiled from: MQTTConfig */
    public static final class a {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;
        public String j;
        public String k;

        public final yi a() {
            return new yi(this, 0);
        }
    }

    /* synthetic */ yi(a aVar, byte b2) {
        this(aVar);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MQTTConfig{clientId='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", serverHost='");
        sb.append(this.b);
        sb.append('\'');
        sb.append(", password='");
        sb.append(this.c);
        sb.append('\'');
        sb.append(", tid='");
        sb.append(this.d);
        sb.append('\'');
        sb.append(", channel='");
        sb.append(this.e);
        sb.append('\'');
        sb.append(", dip='");
        sb.append(this.f);
        sb.append('\'');
        sb.append(", dic='");
        sb.append(this.g);
        sb.append('\'');
        sb.append(", diu='");
        sb.append(this.h);
        sb.append('\'');
        sb.append(", aDiu='");
        sb.append(this.i);
        sb.append('\'');
        sb.append(", autoDiv='");
        sb.append(this.j);
        sb.append('\'');
        sb.append(", sessionId='");
        sb.append(this.k);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    private yi(a aVar) {
        this.a = aVar.a;
        this.b = aVar.b;
        this.c = aVar.c;
        this.d = aVar.d;
        this.e = aVar.e;
        this.f = aVar.f;
        this.g = aVar.g;
        this.h = aVar.h;
        this.i = aVar.i;
        this.j = aVar.j;
        this.k = aVar.k;
    }
}
