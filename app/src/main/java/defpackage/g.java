package defpackage;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MapFactories;
import com.google.protobuf.nano.MapFactories.MapFactory;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Map;

/* renamed from: g reason: default package */
/* compiled from: InfoDetail */
public interface g {

    /* renamed from: g$a */
    /* compiled from: InfoDetail */
    public static final class a extends MessageNano {
        private static volatile a[] d;
        public String a;
        public String b;
        public String c;

        public static a[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new a[0];
                    }
                }
            }
            return d;
        }

        public a() {
            this.a = "";
            this.b = "";
            this.c = "";
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
            return !this.c.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(3, this.c) : computeSerializedSize;
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$b */
    /* compiled from: InfoDetail */
    public static final class b extends MessageNano {
        public a[] a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String[] g;
        public h h;

        public b() {
            this.a = a.a();
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = WireFormatNano.EMPTY_STRING_ARRAY;
            this.h = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (a aVar : this.a) {
                    if (aVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, aVar);
                    }
                }
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
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                for (String str : this.g) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(7, str);
                    }
                }
            }
            if (this.h != null) {
                codedOutputByteBufferNano.writeMessage(8, this.h);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                int i = computeSerializedSize;
                for (a aVar : this.a) {
                    if (aVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(1, aVar);
                    }
                }
                computeSerializedSize = i;
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
            if (!this.f.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                int i2 = 0;
                int i3 = 0;
                for (String str : this.g) {
                    if (str != null) {
                        i3++;
                        i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i2 + (i3 * 1);
            }
            return this.h != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(8, this.h) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    int length = this.a == null ? 0 : this.a.length;
                    a[] aVarArr = new a[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, aVarArr, 0, length);
                    }
                    while (length < aVarArr.length - 1) {
                        aVarArr[length] = new a();
                        codedInputByteBufferNano.readMessage(aVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    aVarArr[length] = new a();
                    codedInputByteBufferNano.readMessage(aVarArr[length]);
                    this.a = aVarArr;
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 58) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    int length2 = this.g == null ? 0 : this.g.length;
                    String[] strArr = new String[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.g, 0, strArr, 0, length2);
                    }
                    while (length2 < strArr.length - 1) {
                        strArr[length2] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    strArr[length2] = codedInputByteBufferNano.readString();
                    this.g = strArr;
                } else if (readTag == 66) {
                    if (this.h == null) {
                        this.h = new h();
                    }
                    codedInputByteBufferNano.readMessage(this.h);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$c */
    /* compiled from: InfoDetail */
    public static final class c extends MessageNano {
        private static volatile c[] e;
        public String a;
        public String b;
        public String c;
        public String d;

        public static c[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new c[0];
                    }
                }
            }
            return e;
        }

        public c() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
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
            return !this.d.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(4, this.d) : computeSerializedSize;
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$d */
    /* compiled from: InfoDetail */
    public static final class d extends MessageNano {
        public j[] a;
        public String b;
        public j[] c;
        public String d;
        public l e;
        public j[] f;

        public d() {
            this.a = j.a();
            this.b = "";
            this.c = j.a();
            this.d = "";
            this.e = null;
            this.f = j.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (j jVar : this.a) {
                    if (jVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, jVar);
                    }
                }
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (j jVar2 : this.c) {
                    if (jVar2 != null) {
                        codedOutputByteBufferNano.writeMessage(3, jVar2);
                    }
                }
            }
            if (!this.d.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.d);
            }
            if (this.e != null) {
                codedOutputByteBufferNano.writeMessage(5, this.e);
            }
            if (this.f != null && this.f.length > 0) {
                for (j jVar3 : this.f) {
                    if (jVar3 != null) {
                        codedOutputByteBufferNano.writeMessage(6, jVar3);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                int i = computeSerializedSize;
                for (j jVar : this.a) {
                    if (jVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(1, jVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (!this.b.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                int i2 = computeSerializedSize;
                for (j jVar2 : this.c) {
                    if (jVar2 != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(3, jVar2);
                    }
                }
                computeSerializedSize = i2;
            }
            if (!this.d.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.d);
            }
            if (this.e != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, this.e);
            }
            if (this.f != null && this.f.length > 0) {
                for (j jVar3 : this.f) {
                    if (jVar3 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, jVar3);
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    int length = this.a == null ? 0 : this.a.length;
                    j[] jVarArr = new j[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, jVarArr, 0, length);
                    }
                    while (length < jVarArr.length - 1) {
                        jVarArr[length] = new j();
                        codedInputByteBufferNano.readMessage(jVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    jVarArr[length] = new j();
                    codedInputByteBufferNano.readMessage(jVarArr[length]);
                    this.a = jVarArr;
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length2 = this.c == null ? 0 : this.c.length;
                    j[] jVarArr2 = new j[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.c, 0, jVarArr2, 0, length2);
                    }
                    while (length2 < jVarArr2.length - 1) {
                        jVarArr2[length2] = new j();
                        codedInputByteBufferNano.readMessage(jVarArr2[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    jVarArr2[length2] = new j();
                    codedInputByteBufferNano.readMessage(jVarArr2[length2]);
                    this.c = jVarArr2;
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    if (this.e == null) {
                        this.e = new l();
                    }
                    codedInputByteBufferNano.readMessage(this.e);
                } else if (readTag == 50) {
                    int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                    int length3 = this.f == null ? 0 : this.f.length;
                    j[] jVarArr3 = new j[(repeatedFieldArrayLength3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.f, 0, jVarArr3, 0, length3);
                    }
                    while (length3 < jVarArr3.length - 1) {
                        jVarArr3[length3] = new j();
                        codedInputByteBufferNano.readMessage(jVarArr3[length3]);
                        codedInputByteBufferNano.readTag();
                        length3++;
                    }
                    jVarArr3[length3] = new j();
                    codedInputByteBufferNano.readMessage(jVarArr3[length3]);
                    this.f = jVarArr3;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$e */
    /* compiled from: InfoDetail */
    public static final class e extends MessageNano {
        private static volatile e[] c;
        public String a;
        public String b;

        public static e[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new e[0];
                    }
                }
            }
            return c;
        }

        public e() {
            this.a = "";
            this.b = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            return !this.b.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(2, this.b) : computeSerializedSize;
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$f */
    /* compiled from: InfoDetail */
    public static final class f extends MessageNano {
        private static volatile f[] k;
        public i a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;
        public h j;

        public static f[] a() {
            if (k == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (k == null) {
                        k = new f[0];
                    }
                }
            }
            return k;
        }

        public f() {
            this.a = null;
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null) {
                codedOutputByteBufferNano.writeMessage(1, this.a);
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
            if (this.j != null) {
                codedOutputByteBufferNano.writeMessage(11, this.j);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, this.a);
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
            return this.j != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(11, this.j) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        if (this.a == null) {
                            this.a = new i();
                        }
                        codedInputByteBufferNano.readMessage(this.a);
                        break;
                    case 18:
                        this.b = codedInputByteBufferNano.readString();
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
                    case 90:
                        if (this.j == null) {
                            this.j = new h();
                        }
                        codedInputByteBufferNano.readMessage(this.j);
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

    /* renamed from: g$g reason: collision with other inner class name */
    /* compiled from: InfoDetail */
    public static final class C0089g extends MessageNano {
        private static volatile C0089g[] g;
        public String a;
        public String b;
        public String c;
        public String d;
        public o[] e;
        public String f;

        public static C0089g[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new C0089g[0];
                    }
                }
            }
            return g;
        }

        public C0089g() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = o.a();
            this.f = "";
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
                codedOutputByteBufferNano.writeString(5, this.d);
            }
            if (this.e != null && this.e.length > 0) {
                for (o oVar : this.e) {
                    if (oVar != null) {
                        codedOutputByteBufferNano.writeMessage(6, oVar);
                    }
                }
            }
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(7, this.f);
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
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.d);
            }
            if (this.e != null && this.e.length > 0) {
                for (o oVar : this.e) {
                    if (oVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, oVar);
                    }
                }
            }
            return !this.f.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(7, this.f) : computeSerializedSize;
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
                } else if (readTag == 42) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                    int length = this.e == null ? 0 : this.e.length;
                    o[] oVarArr = new o[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.e, 0, oVarArr, 0, length);
                    }
                    while (length < oVarArr.length - 1) {
                        oVarArr[length] = new o();
                        codedInputByteBufferNano.readMessage(oVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    oVarArr[length] = new o();
                    codedInputByteBufferNano.readMessage(oVarArr[length]);
                    this.e = oVarArr;
                } else if (readTag == 58) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$h */
    /* compiled from: InfoDetail */
    public static final class h extends MessageNano {
        public q a;

        public h() {
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
                        this.a = new q();
                    }
                    codedInputByteBufferNano.readMessage(this.a);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$i */
    /* compiled from: InfoDetail */
    public static final class i extends MessageNano {
        public String[] a;
        public String b;
        public String c;
        public String d;
        public String e;

        public i() {
            this.a = WireFormatNano.EMPTY_STRING_ARRAY;
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (String str : this.a) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(1, str);
                    }
                }
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
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                int i = 0;
                int i2 = 0;
                for (String str : this.a) {
                    if (str != null) {
                        i2++;
                        i += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i + (i2 * 1);
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
            return !this.e.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(5, this.e) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    int length = this.a == null ? 0 : this.a.length;
                    String[] strArr = new String[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    strArr[length] = codedInputByteBufferNano.readString();
                    this.a = strArr;
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
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

    /* renamed from: g$j */
    /* compiled from: InfoDetail */
    public static final class j extends MessageNano {
        private static volatile j[] b;
        public String a;

        public static j[] a() {
            if (b == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (b == null) {
                        b = new j[0];
                    }
                }
            }
            return b;
        }

        public j() {
            this.a = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            return !this.a.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(1, this.a) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.a = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$k */
    /* compiled from: InfoDetail */
    public static final class k extends MessageNano {
        public String a;
        public f[] b;

        public k() {
            this.a = "";
            this.b = f.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (f fVar : this.b) {
                    if (fVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, fVar);
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
            if (this.b != null && this.b.length > 0) {
                for (f fVar : this.b) {
                    if (fVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, fVar);
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length = this.b == null ? 0 : this.b.length;
                    f[] fVarArr = new f[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, fVarArr, 0, length);
                    }
                    while (length < fVarArr.length - 1) {
                        fVarArr[length] = new f();
                        codedInputByteBufferNano.readMessage(fVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    fVarArr[length] = new f();
                    codedInputByteBufferNano.readMessage(fVarArr[length]);
                    this.b = fVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$l */
    /* compiled from: InfoDetail */
    public static final class l extends MessageNano {
        public String a;
        public m[] b;

        public l() {
            this.a = "";
            this.b = m.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (m mVar : this.b) {
                    if (mVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, mVar);
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
            if (this.b != null && this.b.length > 0) {
                for (m mVar : this.b) {
                    if (mVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, mVar);
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length = this.b == null ? 0 : this.b.length;
                    m[] mVarArr = new m[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, mVarArr, 0, length);
                    }
                    while (length < mVarArr.length - 1) {
                        mVarArr[length] = new m();
                        codedInputByteBufferNano.readMessage(mVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    mVarArr[length] = new m();
                    codedInputByteBufferNano.readMessage(mVarArr[length]);
                    this.b = mVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$m */
    /* compiled from: InfoDetail */
    public static final class m extends MessageNano {
        private static volatile m[] c;
        public String a;
        public String b;

        public static m[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new m[0];
                    }
                }
            }
            return c;
        }

        public m() {
            this.a = "";
            this.b = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            return !this.b.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(2, this.b) : computeSerializedSize;
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$n */
    /* compiled from: InfoDetail */
    public static final class n extends MessageNano {
        public String a;
        public C0089g[] b;
        public String c;

        public n() {
            this.a = "";
            this.b = C0089g.a();
            this.c = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (C0089g gVar : this.b) {
                    if (gVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, gVar);
                    }
                }
            }
            if (!this.c.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.c);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (C0089g gVar : this.b) {
                    if (gVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, gVar);
                    }
                }
            }
            return !this.c.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(3, this.c) : computeSerializedSize;
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length = this.b == null ? 0 : this.b.length;
                    C0089g[] gVarArr = new C0089g[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, gVarArr, 0, length);
                    }
                    while (length < gVarArr.length - 1) {
                        gVarArr[length] = new C0089g();
                        codedInputByteBufferNano.readMessage(gVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    gVarArr[length] = new C0089g();
                    codedInputByteBufferNano.readMessage(gVarArr[length]);
                    this.b = gVarArr;
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$o */
    /* compiled from: InfoDetail */
    public static final class o extends MessageNano {
        private static volatile o[] j;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String[] i;

        public static o[] a() {
            if (j == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (j == null) {
                        j = new o[0];
                    }
                }
            }
            return j;
        }

        public o() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = WireFormatNano.EMPTY_STRING_ARRAY;
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
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.f);
            }
            if (!this.g.equals("")) {
                codedOutputByteBufferNano.writeString(7, this.g);
            }
            if (!this.h.equals("")) {
                codedOutputByteBufferNano.writeString(8, this.h);
            }
            if (this.i != null && this.i.length > 0) {
                for (String str : this.i) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(9, str);
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
            if (this.i == null || this.i.length <= 0) {
                return computeSerializedSize;
            }
            int i2 = 0;
            int i3 = 0;
            for (String str : this.i) {
                if (str != null) {
                    i3++;
                    i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                }
            }
            return computeSerializedSize + i2 + (i3 * 1);
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
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 58) {
                    this.g = codedInputByteBufferNano.readString();
                } else if (readTag == 66) {
                    this.h = codedInputByteBufferNano.readString();
                } else if (readTag == 74) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                    int length = this.i == null ? 0 : this.i.length;
                    String[] strArr = new String[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.i, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    strArr[length] = codedInputByteBufferNano.readString();
                    this.i = strArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$p */
    /* compiled from: InfoDetail */
    public static final class p extends MessageNano {
        public String[] a;
        public Map<String, k> b;
        public String c;
        public String d;
        public b e;
        public c[] f;
        public n g;
        public d h;
        public r i;
        public String j;
        public String k;
        public String l;
        public String m;

        public p() {
            this.a = WireFormatNano.EMPTY_STRING_ARRAY;
            this.b = null;
            this.c = "";
            this.d = "";
            this.e = null;
            this.f = c.a();
            this.g = null;
            this.h = null;
            this.i = null;
            this.j = "";
            this.k = "";
            this.l = "";
            this.m = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (String str : this.a) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(1, str);
                    }
                }
            }
            if (this.b != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.b, 2, 9, 11);
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
            if (this.f != null && this.f.length > 0) {
                for (c cVar : this.f) {
                    if (cVar != null) {
                        codedOutputByteBufferNano.writeMessage(6, cVar);
                    }
                }
            }
            if (this.g != null) {
                codedOutputByteBufferNano.writeMessage(7, this.g);
            }
            if (this.h != null) {
                codedOutputByteBufferNano.writeMessage(8, this.h);
            }
            if (this.i != null) {
                codedOutputByteBufferNano.writeMessage(9, this.i);
            }
            if (!this.j.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.j);
            }
            if (!this.k.equals("")) {
                codedOutputByteBufferNano.writeString(11, this.k);
            }
            if (!this.l.equals("")) {
                codedOutputByteBufferNano.writeString(12, this.l);
            }
            if (!this.m.equals("")) {
                codedOutputByteBufferNano.writeString(13, this.m);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                int i2 = 0;
                int i3 = 0;
                for (String str : this.a) {
                    if (str != null) {
                        i3++;
                        i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i2 + (i3 * 1);
            }
            if (this.b != null) {
                computeSerializedSize += InternalNano.computeMapFieldSize(this.b, 2, 9, 11);
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
            if (this.f != null && this.f.length > 0) {
                for (c cVar : this.f) {
                    if (cVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, cVar);
                    }
                }
            }
            if (this.g != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, this.g);
            }
            if (this.h != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, this.h);
            }
            if (this.i != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(9, this.i);
            }
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            if (!this.k.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(11, this.k);
            }
            if (!this.l.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(12, this.l);
            }
            return !this.m.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(13, this.m) : computeSerializedSize;
        }

        public static p a(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (p) MessageNano.mergeFrom(new p(), bArr);
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            MapFactory mapFactory = MapFactories.getMapFactory();
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                        int length = this.a == null ? 0 : this.a.length;
                        String[] strArr = new String[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.a, 0, strArr, 0, length);
                        }
                        while (length < strArr.length - 1) {
                            strArr[length] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        strArr[length] = codedInputByteBufferNano.readString();
                        this.a = strArr;
                        break;
                    case 18:
                        this.b = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.b, mapFactory, 9, 11, new k(), 10, 18);
                        break;
                    case 26:
                        this.c = codedInputByteBufferNano.readString();
                        break;
                    case 34:
                        this.d = codedInputByteBufferNano.readString();
                        break;
                    case 42:
                        if (this.e == null) {
                            this.e = new b();
                        }
                        codedInputByteBufferNano.readMessage(this.e);
                        break;
                    case 50:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                        int length2 = this.f == null ? 0 : this.f.length;
                        c[] cVarArr = new c[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.f, 0, cVarArr, 0, length2);
                        }
                        while (length2 < cVarArr.length - 1) {
                            cVarArr[length2] = new c();
                            codedInputByteBufferNano.readMessage(cVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        cVarArr[length2] = new c();
                        codedInputByteBufferNano.readMessage(cVarArr[length2]);
                        this.f = cVarArr;
                        break;
                    case 58:
                        if (this.g == null) {
                            this.g = new n();
                        }
                        codedInputByteBufferNano.readMessage(this.g);
                        break;
                    case 66:
                        if (this.h == null) {
                            this.h = new d();
                        }
                        codedInputByteBufferNano.readMessage(this.h);
                        break;
                    case 74:
                        if (this.i == null) {
                            this.i = new r();
                        }
                        codedInputByteBufferNano.readMessage(this.i);
                        break;
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
                        break;
                    case 90:
                        this.k = codedInputByteBufferNano.readString();
                        break;
                    case 98:
                        this.l = codedInputByteBufferNano.readString();
                        break;
                    case 106:
                        this.m = codedInputByteBufferNano.readString();
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

    /* renamed from: g$q */
    /* compiled from: InfoDetail */
    public static final class q extends MessageNano {
        public String a;
        public Map<String, String> b;

        public q() {
            this.a = "";
            this.b = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.b, 2, 9, 9);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            return this.b != null ? computeSerializedSize + InternalNano.computeMapFieldSize(this.b, 2, 9, 9) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            MapFactory mapFactory = MapFactories.getMapFactory();
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.a = codedInputByteBufferNano.readString();
                } else if (readTag == 18) {
                    this.b = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.b, mapFactory, 9, 9, null, 10, 18);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: g$r */
    /* compiled from: InfoDetail */
    public static final class r extends MessageNano {
        public String a;
        public e[] b;

        public r() {
            this.a = "";
            this.b = e.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (e eVar : this.b) {
                    if (eVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, eVar);
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
            if (this.b != null && this.b.length > 0) {
                for (e eVar : this.b) {
                    if (eVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, eVar);
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length = this.b == null ? 0 : this.b.length;
                    e[] eVarArr = new e[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, eVarArr, 0, length);
                    }
                    while (length < eVarArr.length - 1) {
                        eVarArr[length] = new e();
                        codedInputByteBufferNano.readMessage(eVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    eVarArr[length] = new e();
                    codedInputByteBufferNano.readMessage(eVarArr[length]);
                    this.b = eVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }
}
