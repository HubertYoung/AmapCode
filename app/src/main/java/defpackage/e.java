package defpackage;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* renamed from: e reason: default package */
/* compiled from: RecommendSearch */
public interface e {

    /* renamed from: e$a */
    /* compiled from: RecommendSearch */
    public static final class a extends MessageNano {
        public b a;

        public a() {
            this.a = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null) {
                codedOutputByteBufferNano.writeMessage(1, this.a);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            return this.a != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(1, this.a) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    if (this.a == null) {
                        this.a = new b();
                    }
                    codedInputByteBufferNano.readMessage(this.a);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: e$b */
    /* compiled from: RecommendSearch */
    public static final class b extends MessageNano {
        public int a;
        public C0087e b;
        public String c;
        public String d;
        public String e;

        public b() {
            this.a = 0;
            this.b = null;
            this.c = "";
            this.d = "";
            this.e = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.a);
            }
            if (this.b != null) {
                codedOutputByteBufferNano.writeMessage(2, this.b);
            }
            if (!this.c.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.c);
            }
            if (!this.d.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.d);
            }
            if (!this.e.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.e);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, this.a);
            }
            if (this.b != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, this.b);
            }
            if (!this.c.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.c);
            }
            if (!this.d.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.d);
            }
            return !this.e.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(5, this.e) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 8) {
                    this.a = codedInputByteBufferNano.readInt32();
                } else if (readTag == 18) {
                    if (this.b == null) {
                        this.b = new C0087e();
                    }
                    codedInputByteBufferNano.readMessage(this.b);
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: e$c */
    /* compiled from: RecommendSearch */
    public static final class c extends MessageNano {
        public int a;
        public int b;
        public String c;

        public c() {
            this.a = 0;
            this.b = 0;
            this.c = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.a);
            }
            if (this.b != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.b);
            }
            if (!this.c.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.c);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, this.a);
            }
            if (this.b != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, this.b);
            }
            return !this.c.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(3, this.c) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 8) {
                    this.a = codedInputByteBufferNano.readInt32();
                } else if (readTag == 16) {
                    this.b = codedInputByteBufferNano.readInt32();
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: e$d */
    /* compiled from: RecommendSearch */
    public static final class d extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public a f;

        public d() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (!this.c.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.c);
            }
            if (!this.d.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.d);
            }
            if (!this.e.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.e);
            }
            if (this.f != null) {
                codedOutputByteBufferNano.writeMessage(6, this.f);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            if (!this.b.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.b);
            }
            if (!this.c.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.c);
            }
            if (!this.d.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.d);
            }
            if (!this.e.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.e);
            }
            return this.f != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(6, this.f) : computeSerializedSize;
        }

        public static d a(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (d) MessageNano.mergeFrom(new d(), bArr);
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.a = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    if (this.f == null) {
                        this.f = new a();
                    }
                    codedInputByteBufferNano.readMessage(this.f);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: e$e reason: collision with other inner class name */
    /* compiled from: RecommendSearch */
    public static final class C0087e extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public c e;
        public int f;
        public g[] g;

        public C0087e() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = null;
            this.f = 0;
            this.g = g.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (!this.c.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.c);
            }
            if (!this.d.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.d);
            }
            if (this.e != null) {
                codedOutputByteBufferNano.writeMessage(5, this.e);
            }
            if (this.f != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                for (g gVar : this.g) {
                    if (gVar != null) {
                        codedOutputByteBufferNano.writeMessage(7, gVar);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            if (!this.b.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.b);
            }
            if (!this.c.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.c);
            }
            if (!this.d.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.d);
            }
            if (this.e != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, this.e);
            }
            if (this.f != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                for (g gVar : this.g) {
                    if (gVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, gVar);
                    }
                }
            }
            return computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.a = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    if (this.e == null) {
                        this.e = new c();
                    }
                    codedInputByteBufferNano.readMessage(this.e);
                } else if (readTag == 48) {
                    this.f = codedInputByteBufferNano.readInt32();
                } else if (readTag == 58) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    int length = this.g == null ? 0 : this.g.length;
                    g[] gVarArr = new g[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.g, 0, gVarArr, 0, length);
                    }
                    while (length < gVarArr.length - 1) {
                        gVarArr[length] = new g();
                        codedInputByteBufferNano.readMessage(gVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    gVarArr[length] = new g();
                    codedInputByteBufferNano.readMessage(gVarArr[length]);
                    this.g = gVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: e$f */
    /* compiled from: RecommendSearch */
    public static final class f extends MessageNano {
        private static volatile f[] q;
        public c a;
        public float b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;
        public int j;
        public String k;
        public float l;
        public String m;
        public String n;
        public String o;
        public String p;

        public static f[] a() {
            if (q == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (q == null) {
                        q = new f[0];
                    }
                }
            }
            return q;
        }

        public f() {
            this.a = null;
            this.b = 0.0f;
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = 0;
            this.k = "";
            this.l = 0.0f;
            this.m = "";
            this.n = "";
            this.o = "";
            this.p = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null) {
                codedOutputByteBufferNano.writeMessage(1, this.a);
            }
            if (Float.floatToIntBits(this.b) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(2, this.b);
            }
            if (!this.c.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.c);
            }
            if (!this.d.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.d);
            }
            if (!this.e.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.e);
            }
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.f);
            }
            if (!this.g.equals("")) {
                codedOutputByteBufferNano.writeString(7, this.g);
            }
            if (!this.h.equals("")) {
                codedOutputByteBufferNano.writeString(8, this.h);
            }
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
            }
            if (this.j != 0) {
                codedOutputByteBufferNano.writeInt32(10, this.j);
            }
            if (!this.k.equals("")) {
                codedOutputByteBufferNano.writeString(11, this.k);
            }
            if (Float.floatToIntBits(this.l) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(12, this.l);
            }
            if (!this.m.equals("")) {
                codedOutputByteBufferNano.writeString(13, this.m);
            }
            if (!this.n.equals("")) {
                codedOutputByteBufferNano.writeString(14, this.n);
            }
            if (!this.o.equals("")) {
                codedOutputByteBufferNano.writeString(15, this.o);
            }
            if (!this.p.equals("")) {
                codedOutputByteBufferNano.writeString(16, this.p);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, this.a);
            }
            if (Float.floatToIntBits(this.b) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(2, this.b);
            }
            if (!this.c.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.c);
            }
            if (!this.d.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.d);
            }
            if (!this.e.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.e);
            }
            if (!this.f.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(6, this.f);
            }
            if (!this.g.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(7, this.g);
            }
            if (!this.h.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(8, this.h);
            }
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
            }
            if (this.j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(10, this.j);
            }
            if (!this.k.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(11, this.k);
            }
            if (Float.floatToIntBits(this.l) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(12, this.l);
            }
            if (!this.m.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(13, this.m);
            }
            if (!this.n.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(14, this.n);
            }
            if (!this.o.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(15, this.o);
            }
            return !this.p.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(16, this.p) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        if (this.a == null) {
                            this.a = new c();
                        }
                        codedInputByteBufferNano.readMessage(this.a);
                        break;
                    case 21:
                        this.b = codedInputByteBufferNano.readFloat();
                        break;
                    case 26:
                        this.c = codedInputByteBufferNano.readString();
                        break;
                    case 34:
                        this.d = codedInputByteBufferNano.readString();
                        break;
                    case 42:
                        this.e = codedInputByteBufferNano.readString();
                        break;
                    case 50:
                        this.f = codedInputByteBufferNano.readString();
                        break;
                    case 58:
                        this.g = codedInputByteBufferNano.readString();
                        break;
                    case 66:
                        this.h = codedInputByteBufferNano.readString();
                        break;
                    case 74:
                        this.i = codedInputByteBufferNano.readString();
                        break;
                    case 80:
                        this.j = codedInputByteBufferNano.readInt32();
                        break;
                    case 90:
                        this.k = codedInputByteBufferNano.readString();
                        break;
                    case 101:
                        this.l = codedInputByteBufferNano.readFloat();
                        break;
                    case 106:
                        this.m = codedInputByteBufferNano.readString();
                        break;
                    case 114:
                        this.n = codedInputByteBufferNano.readString();
                        break;
                    case 122:
                        this.o = codedInputByteBufferNano.readString();
                        break;
                    case 130:
                        this.p = codedInputByteBufferNano.readString();
                        break;
                    default:
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }
    }

    /* renamed from: e$g */
    /* compiled from: RecommendSearch */
    public static final class g extends MessageNano {
        private static volatile g[] i;
        public String a;
        public String b;
        public int c;
        public f[] d;
        public int e;
        public String f;
        public String g;
        public String h;

        public static g[] a() {
            if (i == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (i == null) {
                        i = new g[0];
                    }
                }
            }
            return i;
        }

        public g() {
            this.a = "";
            this.b = "";
            this.c = 0;
            this.d = f.a();
            this.e = 0;
            this.f = "";
            this.g = "";
            this.h = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.c);
            }
            if (this.d != null && this.d.length > 0) {
                for (f fVar : this.d) {
                    if (fVar != null) {
                        codedOutputByteBufferNano.writeMessage(4, fVar);
                    }
                }
            }
            if (this.e != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.e);
            }
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.f);
            }
            if (!this.g.equals("")) {
                codedOutputByteBufferNano.writeString(7, this.g);
            }
            if (!this.h.equals("")) {
                codedOutputByteBufferNano.writeString(8, this.h);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            if (!this.b.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.b);
            }
            if (this.c != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, this.c);
            }
            if (this.d != null && this.d.length > 0) {
                for (f fVar : this.d) {
                    if (fVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, fVar);
                    }
                }
            }
            if (this.e != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, this.e);
            }
            if (!this.f.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(6, this.f);
            }
            if (!this.g.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(7, this.g);
            }
            return !this.h.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(8, this.h) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.a = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 24) {
                    this.c = codedInputByteBufferNano.readInt32();
                } else if (readTag == 34) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                    int length = this.d == null ? 0 : this.d.length;
                    f[] fVarArr = new f[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.d, 0, fVarArr, 0, length);
                    }
                    while (length < fVarArr.length - 1) {
                        fVarArr[length] = new f();
                        codedInputByteBufferNano.readMessage(fVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    fVarArr[length] = new f();
                    codedInputByteBufferNano.readMessage(fVarArr[length]);
                    this.d = fVarArr;
                } else if (readTag == 40) {
                    this.e = codedInputByteBufferNano.readInt32();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 58) {
                    this.g = codedInputByteBufferNano.readString();
                } else if (readTag == 66) {
                    this.h = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }
}
