package org.apache.thrift;

import java.io.ByteArrayOutputStream;
import org.apache.thrift.protocol.a.C0091a;
import org.apache.thrift.protocol.e;
import org.apache.thrift.transport.a;

public class g {
    public final ByteArrayOutputStream a;
    public e b;
    private final a c;

    public g() {
        this(new C0091a());
    }

    public g(org.apache.thrift.protocol.g gVar) {
        this.a = new ByteArrayOutputStream();
        this.c = new a(this.a);
        this.b = gVar.a(this.c);
    }
}
