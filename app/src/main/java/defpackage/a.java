package defpackage;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.minimap.route.sharebike.model.EndBill;
import com.autonavi.minimap.route.sharebike.model.ScanQrcode;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MapFactories;
import com.google.protobuf.nano.MapFactories.MapFactory;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import com.iflytek.tts.TtsService.Tts;
import java.io.IOException;
import java.util.Map;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* renamed from: a reason: default package */
/* compiled from: DetailV2 */
public interface a {

    /* renamed from: a$a reason: collision with other inner class name */
    /* compiled from: DetailV2 */
    public static final class C0041a extends MessageNano {
        public int a;
        public String b;
        public String c;
        public int d;
        public ba[] e;

        public C0041a() {
            this.a = 0;
            this.b = "";
            this.c = "";
            this.d = 0;
            this.e = ba.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (!this.c.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.c);
            }
            if (this.d != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.d);
            }
            if (this.e != null && this.e.length > 0) {
                for (ba baVar : this.e) {
                    if (baVar != null) {
                        codedOutputByteBufferNano.writeMessage(5, baVar);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, this.a);
            }
            if (!this.b.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.b);
            }
            if (!this.c.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.c);
            }
            if (this.d != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, this.d);
            }
            if (this.e != null && this.e.length > 0) {
                for (ba baVar : this.e) {
                    if (baVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, baVar);
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
                if (readTag == 8) {
                    this.a = codedInputByteBufferNano.readInt32();
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (readTag == 32) {
                    this.d = codedInputByteBufferNano.readInt32();
                } else if (readTag == 42) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                    int length = this.e == null ? 0 : this.e.length;
                    ba[] baVarArr = new ba[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.e, 0, baVarArr, 0, length);
                    }
                    while (length < baVarArr.length - 1) {
                        baVarArr[length] = new ba();
                        codedInputByteBufferNano.readMessage(baVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    baVarArr[length] = new ba();
                    codedInputByteBufferNano.readMessage(baVarArr[length]);
                    this.e = baVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$aa */
    /* compiled from: DetailV2 */
    public static final class aa extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public y[] f;

        public aa() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = y.a();
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
            if (this.f != null && this.f.length > 0) {
                for (y yVar : this.f) {
                    if (yVar != null) {
                        codedOutputByteBufferNano.writeMessage(6, yVar);
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
            if (this.f != null && this.f.length > 0) {
                for (y yVar : this.f) {
                    if (yVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, yVar);
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
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                    int length = this.f == null ? 0 : this.f.length;
                    y[] yVarArr = new y[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.f, 0, yVarArr, 0, length);
                    }
                    while (length < yVarArr.length - 1) {
                        yVarArr[length] = new y();
                        codedInputByteBufferNano.readMessage(yVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    yVarArr[length] = new y();
                    codedInputByteBufferNano.readMessage(yVarArr[length]);
                    this.f = yVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$ab */
    /* compiled from: DetailV2 */
    public static final class ab extends MessageNano {
        private static volatile ab[] i;
        public long a;
        public String b;
        public int c;
        public String d;
        public String e;
        public String f;
        public long g;
        public String h;

        public static ab[] a() {
            if (i == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (i == null) {
                        i = new ab[0];
                    }
                }
            }
            return i;
        }

        public ab() {
            this.a = 0;
            this.b = "";
            this.c = 0;
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = 0;
            this.h = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt64(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.c);
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
            if (this.g != 0) {
                codedOutputByteBufferNano.writeInt64(7, this.g);
            }
            if (!this.h.equals("")) {
                codedOutputByteBufferNano.writeString(8, this.h);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, this.a);
            }
            if (!this.b.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.b);
            }
            if (this.c != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, this.c);
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
            if (this.g != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(7, this.g);
            }
            return !this.h.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(8, this.h) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 8) {
                    this.a = codedInputByteBufferNano.readInt64();
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 24) {
                    this.c = codedInputByteBufferNano.readInt32();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 56) {
                    this.g = codedInputByteBufferNano.readInt64();
                } else if (readTag == 66) {
                    this.h = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$ac */
    /* compiled from: DetailV2 */
    public static final class ac extends MessageNano {
        public ai A;
        public ai B;
        public ai C;
        public ai D;
        public ai E;
        public ai F;
        public ag a;
        public String b;
        public String[] c;
        public af[] d;
        public ad e;
        public ai f;
        public ai g;
        public ai h;
        public ai i;
        public ai j;
        public ai k;
        public ai l;
        public ai m;
        public ai n;
        public ai o;
        public ai p;
        public ai q;
        public ai r;
        public ai s;
        public ai t;
        public ai u;
        public ai v;
        public ai w;
        public ai x;
        public ai y;
        public e z;

        public ac() {
            this.a = null;
            this.b = "";
            this.c = WireFormatNano.EMPTY_STRING_ARRAY;
            this.d = af.a();
            this.e = null;
            this.f = null;
            this.g = null;
            this.h = null;
            this.i = null;
            this.j = null;
            this.k = null;
            this.l = null;
            this.m = null;
            this.n = null;
            this.o = null;
            this.p = null;
            this.q = null;
            this.r = null;
            this.s = null;
            this.t = null;
            this.u = null;
            this.v = null;
            this.w = null;
            this.x = null;
            this.y = null;
            this.z = null;
            this.A = null;
            this.B = null;
            this.C = null;
            this.D = null;
            this.E = null;
            this.F = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null) {
                codedOutputByteBufferNano.writeMessage(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (String str : this.c) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(3, str);
                    }
                }
            }
            if (this.d != null && this.d.length > 0) {
                for (af afVar : this.d) {
                    if (afVar != null) {
                        codedOutputByteBufferNano.writeMessage(4, afVar);
                    }
                }
            }
            if (this.e != null) {
                codedOutputByteBufferNano.writeMessage(5, this.e);
            }
            if (this.f != null) {
                codedOutputByteBufferNano.writeMessage(6, this.f);
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
            if (this.j != null) {
                codedOutputByteBufferNano.writeMessage(10, this.j);
            }
            if (this.k != null) {
                codedOutputByteBufferNano.writeMessage(11, this.k);
            }
            if (this.l != null) {
                codedOutputByteBufferNano.writeMessage(12, this.l);
            }
            if (this.m != null) {
                codedOutputByteBufferNano.writeMessage(13, this.m);
            }
            if (this.n != null) {
                codedOutputByteBufferNano.writeMessage(14, this.n);
            }
            if (this.o != null) {
                codedOutputByteBufferNano.writeMessage(15, this.o);
            }
            if (this.p != null) {
                codedOutputByteBufferNano.writeMessage(16, this.p);
            }
            if (this.q != null) {
                codedOutputByteBufferNano.writeMessage(17, this.q);
            }
            if (this.r != null) {
                codedOutputByteBufferNano.writeMessage(18, this.r);
            }
            if (this.s != null) {
                codedOutputByteBufferNano.writeMessage(19, this.s);
            }
            if (this.t != null) {
                codedOutputByteBufferNano.writeMessage(20, this.t);
            }
            if (this.u != null) {
                codedOutputByteBufferNano.writeMessage(21, this.u);
            }
            if (this.v != null) {
                codedOutputByteBufferNano.writeMessage(22, this.v);
            }
            if (this.w != null) {
                codedOutputByteBufferNano.writeMessage(23, this.w);
            }
            if (this.x != null) {
                codedOutputByteBufferNano.writeMessage(24, this.x);
            }
            if (this.y != null) {
                codedOutputByteBufferNano.writeMessage(25, this.y);
            }
            if (this.z != null) {
                codedOutputByteBufferNano.writeMessage(26, this.z);
            }
            if (this.A != null) {
                codedOutputByteBufferNano.writeMessage(27, this.A);
            }
            if (this.B != null) {
                codedOutputByteBufferNano.writeMessage(28, this.B);
            }
            if (this.C != null) {
                codedOutputByteBufferNano.writeMessage(29, this.C);
            }
            if (this.D != null) {
                codedOutputByteBufferNano.writeMessage(30, this.D);
            }
            if (this.E != null) {
                codedOutputByteBufferNano.writeMessage(31, this.E);
            }
            if (this.F != null) {
                codedOutputByteBufferNano.writeMessage(32, this.F);
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
            if (this.c != null && this.c.length > 0) {
                int i2 = 0;
                int i3 = 0;
                for (String str : this.c) {
                    if (str != null) {
                        i3++;
                        i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i2 + (i3 * 1);
            }
            if (this.d != null && this.d.length > 0) {
                for (af afVar : this.d) {
                    if (afVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, afVar);
                    }
                }
            }
            if (this.e != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, this.e);
            }
            if (this.f != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, this.f);
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
            if (this.j != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(10, this.j);
            }
            if (this.k != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(11, this.k);
            }
            if (this.l != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(12, this.l);
            }
            if (this.m != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(13, this.m);
            }
            if (this.n != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(14, this.n);
            }
            if (this.o != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(15, this.o);
            }
            if (this.p != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(16, this.p);
            }
            if (this.q != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(17, this.q);
            }
            if (this.r != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(18, this.r);
            }
            if (this.s != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(19, this.s);
            }
            if (this.t != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(20, this.t);
            }
            if (this.u != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(21, this.u);
            }
            if (this.v != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(22, this.v);
            }
            if (this.w != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(23, this.w);
            }
            if (this.x != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(24, this.x);
            }
            if (this.y != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(25, this.y);
            }
            if (this.z != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(26, this.z);
            }
            if (this.A != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(27, this.A);
            }
            if (this.B != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(28, this.B);
            }
            if (this.C != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(29, this.C);
            }
            if (this.D != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(30, this.D);
            }
            if (this.E != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(31, this.E);
            }
            return this.F != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(32, this.F) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        if (this.a == null) {
                            this.a = new ag();
                        }
                        codedInputByteBufferNano.readMessage(this.a);
                        break;
                    case 18:
                        this.b = codedInputByteBufferNano.readString();
                        break;
                    case 26:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        int length = this.c == null ? 0 : this.c.length;
                        String[] strArr = new String[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.c, 0, strArr, 0, length);
                        }
                        while (length < strArr.length - 1) {
                            strArr[length] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        strArr[length] = codedInputByteBufferNano.readString();
                        this.c = strArr;
                        break;
                    case 34:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                        int length2 = this.d == null ? 0 : this.d.length;
                        af[] afVarArr = new af[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.d, 0, afVarArr, 0, length2);
                        }
                        while (length2 < afVarArr.length - 1) {
                            afVarArr[length2] = new af();
                            codedInputByteBufferNano.readMessage(afVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        afVarArr[length2] = new af();
                        codedInputByteBufferNano.readMessage(afVarArr[length2]);
                        this.d = afVarArr;
                        break;
                    case 42:
                        if (this.e == null) {
                            this.e = new ad();
                        }
                        codedInputByteBufferNano.readMessage(this.e);
                        break;
                    case 50:
                        if (this.f == null) {
                            this.f = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.f);
                        break;
                    case 58:
                        if (this.g == null) {
                            this.g = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.g);
                        break;
                    case 66:
                        if (this.h == null) {
                            this.h = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.h);
                        break;
                    case 74:
                        if (this.i == null) {
                            this.i = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.i);
                        break;
                    case 82:
                        if (this.j == null) {
                            this.j = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.j);
                        break;
                    case 90:
                        if (this.k == null) {
                            this.k = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.k);
                        break;
                    case 98:
                        if (this.l == null) {
                            this.l = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.l);
                        break;
                    case 106:
                        if (this.m == null) {
                            this.m = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.m);
                        break;
                    case 114:
                        if (this.n == null) {
                            this.n = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.n);
                        break;
                    case 122:
                        if (this.o == null) {
                            this.o = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.o);
                        break;
                    case 130:
                        if (this.p == null) {
                            this.p = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.p);
                        break;
                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                        if (this.q == null) {
                            this.q = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.q);
                        break;
                    case 146:
                        if (this.r == null) {
                            this.r = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.r);
                        break;
                    case ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE /*154*/:
                        if (this.s == null) {
                            this.s = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.s);
                        break;
                    case EndBill.UNKNOWN_END_ORDER_FAILED /*162*/:
                        if (this.t == null) {
                            this.t = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.t);
                        break;
                    case 170:
                        if (this.u == null) {
                            this.u = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.u);
                        break;
                    case 178:
                        if (this.v == null) {
                            this.v = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.v);
                        break;
                    case 186:
                        if (this.w == null) {
                            this.w = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.w);
                        break;
                    case 194:
                        if (this.x == null) {
                            this.x = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.x);
                        break;
                    case 202:
                        if (this.y == null) {
                            this.y = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.y);
                        break;
                    case 210:
                        if (this.z == null) {
                            this.z = new e();
                        }
                        codedInputByteBufferNano.readMessage(this.z);
                        break;
                    case 218:
                        if (this.A == null) {
                            this.A = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.A);
                        break;
                    case 226:
                        if (this.B == null) {
                            this.B = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.B);
                        break;
                    case 234:
                        if (this.C == null) {
                            this.C = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.C);
                        break;
                    case FavoritesPointFragment.REQUEST_COMPNAY /*242*/:
                        if (this.D == null) {
                            this.D = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.D);
                        break;
                    case Callback.DEFAULT_SWIPE_ANIMATION_DURATION /*250*/:
                        if (this.E == null) {
                            this.E = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.E);
                        break;
                    case Tts.TTS_STATE_INVALID_DATA /*258*/:
                        if (this.F == null) {
                            this.F = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.F);
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

    /* renamed from: a$ad */
    /* compiled from: DetailV2 */
    public static final class ad extends MessageNano {
        public ae[] a;

        public ad() {
            this.a = ae.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (ae aeVar : this.a) {
                    if (aeVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, aeVar);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                for (ae aeVar : this.a) {
                    if (aeVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, aeVar);
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
                    ae[] aeVarArr = new ae[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, aeVarArr, 0, length);
                    }
                    while (length < aeVarArr.length - 1) {
                        aeVarArr[length] = new ae();
                        codedInputByteBufferNano.readMessage(aeVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    aeVarArr[length] = new ae();
                    codedInputByteBufferNano.readMessage(aeVarArr[length]);
                    this.a = aeVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$ae */
    /* compiled from: DetailV2 */
    public static final class ae extends MessageNano {
        private static volatile ae[] e;
        public String a;
        public String b;
        public String c;
        public String d;

        public static ae[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new ae[0];
                    }
                }
            }
            return e;
        }

        public ae() {
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

    /* renamed from: a$af */
    /* compiled from: DetailV2 */
    public static final class af extends MessageNano {
        private static volatile af[] d;
        public String a;
        public String b;
        public af[] c;

        public static af[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new af[0];
                    }
                }
            }
            return d;
        }

        public af() {
            this.a = "";
            this.b = "";
            this.c = a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (af afVar : this.c) {
                    if (afVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, afVar);
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
            if (this.c != null && this.c.length > 0) {
                for (af afVar : this.c) {
                    if (afVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, afVar);
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length = this.c == null ? 0 : this.c.length;
                    af[] afVarArr = new af[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, afVarArr, 0, length);
                    }
                    while (length < afVarArr.length - 1) {
                        afVarArr[length] = new af();
                        codedInputByteBufferNano.readMessage(afVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    afVarArr[length] = new af();
                    codedInputByteBufferNano.readMessage(afVarArr[length]);
                    this.c = afVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$ag */
    /* compiled from: DetailV2 */
    public static final class ag extends MessageNano {
        public an a;
        public an[] b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public am[] h;

        public ag() {
            this.a = null;
            this.b = an.a();
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = am.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null) {
                codedOutputByteBufferNano.writeMessage(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (an anVar : this.b) {
                    if (anVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, anVar);
                    }
                }
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
            if (this.h != null && this.h.length > 0) {
                for (am amVar : this.h) {
                    if (amVar != null) {
                        codedOutputByteBufferNano.writeMessage(8, amVar);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                int i = computeSerializedSize;
                for (an anVar : this.b) {
                    if (anVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(2, anVar);
                    }
                }
                computeSerializedSize = i;
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
            if (this.h != null && this.h.length > 0) {
                for (am amVar : this.h) {
                    if (amVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, amVar);
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
                    if (this.a == null) {
                        this.a = new an();
                    }
                    codedInputByteBufferNano.readMessage(this.a);
                } else if (readTag == 18) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length = this.b == null ? 0 : this.b.length;
                    an[] anVarArr = new an[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, anVarArr, 0, length);
                    }
                    while (length < anVarArr.length - 1) {
                        anVarArr[length] = new an();
                        codedInputByteBufferNano.readMessage(anVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    anVarArr[length] = new an();
                    codedInputByteBufferNano.readMessage(anVarArr[length]);
                    this.b = anVarArr;
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
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                    int length2 = this.h == null ? 0 : this.h.length;
                    am[] amVarArr = new am[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.h, 0, amVarArr, 0, length2);
                    }
                    while (length2 < amVarArr.length - 1) {
                        amVarArr[length2] = new am();
                        codedInputByteBufferNano.readMessage(amVarArr[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    amVarArr[length2] = new am();
                    codedInputByteBufferNano.readMessage(amVarArr[length2]);
                    this.h = amVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$ah */
    /* compiled from: DetailV2 */
    public static final class ah extends MessageNano {
        public String a;
        public Map<String, String> b;
        public Map<String, String> c;

        public ah() {
            this.a = "";
            this.b = null;
            this.c = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.b, 2, 9, 9);
            }
            if (this.c != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.c, 3, 9, 9);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            if (this.b != null) {
                computeSerializedSize += InternalNano.computeMapFieldSize(this.b, 2, 9, 9);
            }
            return this.c != null ? computeSerializedSize + InternalNano.computeMapFieldSize(this.c, 3, 9, 9) : computeSerializedSize;
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
                } else if (readTag == 26) {
                    this.c = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.c, mapFactory, 9, 9, null, 10, 18);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$ai */
    /* compiled from: DetailV2 */
    public static final class ai extends MessageNano {
        public String a;
        public aj[] b;
        public Map<String, ah> c;

        public ai() {
            this.a = "";
            this.b = aj.a();
            this.c = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (aj ajVar : this.b) {
                    if (ajVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, ajVar);
                    }
                }
            }
            if (this.c != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.c, 3, 9, 11);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (aj ajVar : this.b) {
                    if (ajVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, ajVar);
                    }
                }
            }
            return this.c != null ? computeSerializedSize + InternalNano.computeMapFieldSize(this.c, 3, 9, 11) : computeSerializedSize;
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length = this.b == null ? 0 : this.b.length;
                    aj[] ajVarArr = new aj[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, ajVarArr, 0, length);
                    }
                    while (length < ajVarArr.length - 1) {
                        ajVarArr[length] = new aj();
                        codedInputByteBufferNano.readMessage(ajVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    ajVarArr[length] = new aj();
                    codedInputByteBufferNano.readMessage(ajVarArr[length]);
                    this.b = ajVarArr;
                } else if (readTag == 26) {
                    this.c = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.c, mapFactory, 9, 11, new ah(), 10, 18);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$aj */
    /* compiled from: DetailV2 */
    public static final class aj extends MessageNano {
        private static volatile aj[] H;
        public String A;
        public ak[] B;
        public String C;
        public String D;
        public Map<String, ah> E;
        public Map<String, String> F;
        public String G;
        public String a;
        public String b;
        public String c;
        public String[] d;
        public al[] e;
        public String f;
        public String g;
        public String h;
        public String i;
        public String j;
        public String k;
        public String l;
        public String m;
        public String n;
        public String o;
        public int p;
        public ak[] q;
        public Map<String, String> r;
        public String s;
        public int t;
        public int u;
        public String v;
        public String w;
        public String x;
        public int y;
        public int z;

        public static aj[] a() {
            if (H == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (H == null) {
                        H = new aj[0];
                    }
                }
            }
            return H;
        }

        public aj() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = WireFormatNano.EMPTY_STRING_ARRAY;
            this.e = al.a();
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = "";
            this.k = "";
            this.l = "";
            this.m = "";
            this.n = "";
            this.o = "";
            this.p = 0;
            this.q = ak.a();
            this.r = null;
            this.s = "";
            this.t = 0;
            this.u = 0;
            this.v = "";
            this.w = "";
            this.x = "";
            this.y = 0;
            this.z = 0;
            this.A = "";
            this.B = ak.a();
            this.C = "";
            this.D = "";
            this.E = null;
            this.F = null;
            this.G = "";
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
            if (this.d != null && this.d.length > 0) {
                for (String str : this.d) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(4, str);
                    }
                }
            }
            if (this.e != null && this.e.length > 0) {
                for (al alVar : this.e) {
                    if (alVar != null) {
                        codedOutputByteBufferNano.writeMessage(5, alVar);
                    }
                }
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
            if (!this.n.equals("")) {
                codedOutputByteBufferNano.writeString(14, this.n);
            }
            if (!this.o.equals("")) {
                codedOutputByteBufferNano.writeString(15, this.o);
            }
            if (this.p != 0) {
                codedOutputByteBufferNano.writeInt32(16, this.p);
            }
            if (this.q != null && this.q.length > 0) {
                for (ak akVar : this.q) {
                    if (akVar != null) {
                        codedOutputByteBufferNano.writeMessage(17, akVar);
                    }
                }
            }
            if (this.r != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.r, 18, 9, 9);
            }
            if (!this.s.equals("")) {
                codedOutputByteBufferNano.writeString(19, this.s);
            }
            if (this.t != 0) {
                codedOutputByteBufferNano.writeInt32(20, this.t);
            }
            if (this.u != 0) {
                codedOutputByteBufferNano.writeInt32(21, this.u);
            }
            if (!this.v.equals("")) {
                codedOutputByteBufferNano.writeString(22, this.v);
            }
            if (!this.w.equals("")) {
                codedOutputByteBufferNano.writeString(23, this.w);
            }
            if (!this.x.equals("")) {
                codedOutputByteBufferNano.writeString(24, this.x);
            }
            if (this.y != 0) {
                codedOutputByteBufferNano.writeInt32(25, this.y);
            }
            if (this.z != 0) {
                codedOutputByteBufferNano.writeInt32(26, this.z);
            }
            if (!this.A.equals("")) {
                codedOutputByteBufferNano.writeString(27, this.A);
            }
            if (this.B != null && this.B.length > 0) {
                for (ak akVar2 : this.B) {
                    if (akVar2 != null) {
                        codedOutputByteBufferNano.writeMessage(28, akVar2);
                    }
                }
            }
            if (!this.C.equals("")) {
                codedOutputByteBufferNano.writeString(29, this.C);
            }
            if (!this.D.equals("")) {
                codedOutputByteBufferNano.writeString(30, this.D);
            }
            if (this.E != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.E, 31, 9, 11);
            }
            if (this.F != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.F, 32, 9, 9);
            }
            if (!this.G.equals("")) {
                codedOutputByteBufferNano.writeString(33, this.G);
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
            if (this.d != null && this.d.length > 0) {
                int i2 = 0;
                int i3 = 0;
                for (String str : this.d) {
                    if (str != null) {
                        i3++;
                        i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i2 + (i3 * 1);
            }
            if (this.e != null && this.e.length > 0) {
                int i4 = computeSerializedSize;
                for (al alVar : this.e) {
                    if (alVar != null) {
                        i4 += CodedOutputByteBufferNano.computeMessageSize(5, alVar);
                    }
                }
                computeSerializedSize = i4;
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
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            if (!this.k.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(11, this.k);
            }
            if (!this.l.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(12, this.l);
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
            if (this.p != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(16, this.p);
            }
            if (this.q != null && this.q.length > 0) {
                int i5 = computeSerializedSize;
                for (ak akVar : this.q) {
                    if (akVar != null) {
                        i5 += CodedOutputByteBufferNano.computeMessageSize(17, akVar);
                    }
                }
                computeSerializedSize = i5;
            }
            if (this.r != null) {
                computeSerializedSize += InternalNano.computeMapFieldSize(this.r, 18, 9, 9);
            }
            if (!this.s.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(19, this.s);
            }
            if (this.t != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(20, this.t);
            }
            if (this.u != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(21, this.u);
            }
            if (!this.v.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(22, this.v);
            }
            if (!this.w.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(23, this.w);
            }
            if (!this.x.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(24, this.x);
            }
            if (this.y != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(25, this.y);
            }
            if (this.z != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(26, this.z);
            }
            if (!this.A.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(27, this.A);
            }
            if (this.B != null && this.B.length > 0) {
                for (ak akVar2 : this.B) {
                    if (akVar2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(28, akVar2);
                    }
                }
            }
            if (!this.C.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(29, this.C);
            }
            if (!this.D.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(30, this.D);
            }
            if (this.E != null) {
                computeSerializedSize += InternalNano.computeMapFieldSize(this.E, 31, 9, 11);
            }
            if (this.F != null) {
                computeSerializedSize += InternalNano.computeMapFieldSize(this.F, 32, 9, 9);
            }
            return !this.G.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(33, this.G) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            MapFactory mapFactory = MapFactories.getMapFactory();
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        this.b = codedInputByteBufferNano.readString();
                        break;
                    case 26:
                        this.c = codedInputByteBufferNano.readString();
                        break;
                    case 34:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                        int length = this.d == null ? 0 : this.d.length;
                        String[] strArr = new String[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.d, 0, strArr, 0, length);
                        }
                        while (length < strArr.length - 1) {
                            strArr[length] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        strArr[length] = codedInputByteBufferNano.readString();
                        this.d = strArr;
                        break;
                    case 42:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                        int length2 = this.e == null ? 0 : this.e.length;
                        al[] alVarArr = new al[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.e, 0, alVarArr, 0, length2);
                        }
                        while (length2 < alVarArr.length - 1) {
                            alVarArr[length2] = new al();
                            codedInputByteBufferNano.readMessage(alVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        alVarArr[length2] = new al();
                        codedInputByteBufferNano.readMessage(alVarArr[length2]);
                        this.e = alVarArr;
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
                    case 114:
                        this.n = codedInputByteBufferNano.readString();
                        break;
                    case 122:
                        this.o = codedInputByteBufferNano.readString();
                        break;
                    case 128:
                        this.p = codedInputByteBufferNano.readInt32();
                        break;
                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, DumpSegment.ANDROID_ROOT_FINALIZING);
                        int length3 = this.q == null ? 0 : this.q.length;
                        ak[] akVarArr = new ak[(repeatedFieldArrayLength3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.q, 0, akVarArr, 0, length3);
                        }
                        while (length3 < akVarArr.length - 1) {
                            akVarArr[length3] = new ak();
                            codedInputByteBufferNano.readMessage(akVarArr[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        akVarArr[length3] = new ak();
                        codedInputByteBufferNano.readMessage(akVarArr[length3]);
                        this.q = akVarArr;
                        break;
                    case 146:
                        this.r = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.r, mapFactory, 9, 9, null, 10, 18);
                        break;
                    case ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE /*154*/:
                        this.s = codedInputByteBufferNano.readString();
                        break;
                    case 160:
                        this.t = codedInputByteBufferNano.readInt32();
                        break;
                    case 168:
                        this.u = codedInputByteBufferNano.readInt32();
                        break;
                    case 178:
                        this.v = codedInputByteBufferNano.readString();
                        break;
                    case 186:
                        this.w = codedInputByteBufferNano.readString();
                        break;
                    case 194:
                        this.x = codedInputByteBufferNano.readString();
                        break;
                    case 200:
                        this.y = codedInputByteBufferNano.readInt32();
                        break;
                    case 208:
                        this.z = codedInputByteBufferNano.readInt32();
                        break;
                    case 218:
                        this.A = codedInputByteBufferNano.readString();
                        break;
                    case 226:
                        int repeatedFieldArrayLength4 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 226);
                        int length4 = this.B == null ? 0 : this.B.length;
                        ak[] akVarArr2 = new ak[(repeatedFieldArrayLength4 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.B, 0, akVarArr2, 0, length4);
                        }
                        while (length4 < akVarArr2.length - 1) {
                            akVarArr2[length4] = new ak();
                            codedInputByteBufferNano.readMessage(akVarArr2[length4]);
                            codedInputByteBufferNano.readTag();
                            length4++;
                        }
                        akVarArr2[length4] = new ak();
                        codedInputByteBufferNano.readMessage(akVarArr2[length4]);
                        this.B = akVarArr2;
                        break;
                    case 234:
                        this.C = codedInputByteBufferNano.readString();
                        break;
                    case FavoritesPointFragment.REQUEST_COMPNAY /*242*/:
                        this.D = codedInputByteBufferNano.readString();
                        break;
                    case Callback.DEFAULT_SWIPE_ANIMATION_DURATION /*250*/:
                        this.E = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.E, mapFactory, 9, 11, new ah(), 10, 18);
                        break;
                    case Tts.TTS_STATE_INVALID_DATA /*258*/:
                        this.F = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.F, mapFactory, 9, 9, null, 10, 18);
                        break;
                    case 266:
                        this.G = codedInputByteBufferNano.readString();
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

    /* renamed from: a$ak */
    /* compiled from: DetailV2 */
    public static final class ak extends MessageNano {
        private static volatile ak[] g;
        public String a;
        public String b;
        public String c;
        public String d;
        public Map<String, String> e;
        public String f;

        public static ak[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new ak[0];
                    }
                }
            }
            return g;
        }

        public ak() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = null;
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
                codedOutputByteBufferNano.writeString(4, this.d);
            }
            if (this.e != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.e, 5, 9, 9);
            }
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.f);
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
                computeSerializedSize += InternalNano.computeMapFieldSize(this.e, 5, 9, 9);
            }
            return !this.f.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(6, this.f) : computeSerializedSize;
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
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    this.e = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.e, mapFactory, 9, 9, null, 10, 18);
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$al */
    /* compiled from: DetailV2 */
    public static final class al extends MessageNano {
        private static volatile al[] i;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;

        public static al[] a() {
            if (i == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (i == null) {
                        i = new al[0];
                    }
                }
            }
            return i;
        }

        public al() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$am */
    /* compiled from: DetailV2 */
    public static final class am extends MessageNano {
        private static volatile am[] c;
        public String a;
        public String b;

        public static am[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new am[0];
                    }
                }
            }
            return c;
        }

        public am() {
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

    /* renamed from: a$an */
    /* compiled from: DetailV2 */
    public static final class an extends MessageNano {
        private static volatile an[] d;
        public String a;
        public String b;
        public String c;

        public static an[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new an[0];
                    }
                }
            }
            return d;
        }

        public an() {
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

    /* renamed from: a$ao */
    /* compiled from: DetailV2 */
    public static final class ao extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public ac g;

        public ao() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = null;
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
            if (this.g != null) {
                codedOutputByteBufferNano.writeMessage(7, this.g);
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
            return this.g != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(7, this.g) : computeSerializedSize;
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
                    if (this.g == null) {
                        this.g = new ac();
                    }
                    codedInputByteBufferNano.readMessage(this.g);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$ap */
    /* compiled from: DetailV2 */
    public static final class ap extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public aq f;

        public ap() {
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
                        this.f = new aq();
                    }
                    codedInputByteBufferNano.readMessage(this.f);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$aq */
    /* compiled from: DetailV2 */
    public static final class aq extends MessageNano {
        public String a;
        public String b;
        public String c;

        public aq() {
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

    /* renamed from: a$ar */
    /* compiled from: DetailV2 */
    public static final class ar extends MessageNano {
        private static volatile ar[] m;
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
        public br[] k;
        public String l;

        public static ar[] a() {
            if (m == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (m == null) {
                        m = new ar[0];
                    }
                }
            }
            return m;
        }

        public ar() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = "";
            this.k = br.a();
            this.l = "";
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
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
            }
            if (!this.j.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.j);
            }
            if (this.k != null && this.k.length > 0) {
                for (br brVar : this.k) {
                    if (brVar != null) {
                        codedOutputByteBufferNano.writeMessage(11, brVar);
                    }
                }
            }
            if (!this.l.equals("")) {
                codedOutputByteBufferNano.writeString(12, this.l);
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
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
            }
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            if (this.k != null && this.k.length > 0) {
                for (br brVar : this.k) {
                    if (brVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(11, brVar);
                    }
                }
            }
            return !this.l.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(12, this.l) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
                        break;
                    case 90:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 90);
                        int length = this.k == null ? 0 : this.k.length;
                        br[] brVarArr = new br[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.k, 0, brVarArr, 0, length);
                        }
                        while (length < brVarArr.length - 1) {
                            brVarArr[length] = new br();
                            codedInputByteBufferNano.readMessage(brVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        brVarArr[length] = new br();
                        codedInputByteBufferNano.readMessage(brVarArr[length]);
                        this.k = brVarArr;
                        break;
                    case 98:
                        this.l = codedInputByteBufferNano.readString();
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

    /* renamed from: a$as */
    /* compiled from: DetailV2 */
    public static final class as extends MessageNano {
        private static volatile as[] m;
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
        public String l;

        public static as[] a() {
            if (m == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (m == null) {
                        m = new as[0];
                    }
                }
            }
            return m;
        }

        public as() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = "";
            this.k = "";
            this.l = "";
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
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
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
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
            }
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            if (!this.k.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(11, this.k);
            }
            return !this.l.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(12, this.l) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
                        break;
                    case 90:
                        this.k = codedInputByteBufferNano.readString();
                        break;
                    case 98:
                        this.l = codedInputByteBufferNano.readString();
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

    /* renamed from: a$at */
    /* compiled from: DetailV2 */
    public static final class at extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public int f;
        public as[] g;

        public at() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = 0;
            this.g = as.a();
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
            if (this.f != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                for (as asVar : this.g) {
                    if (asVar != null) {
                        codedOutputByteBufferNano.writeMessage(7, asVar);
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
            if (this.f != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                for (as asVar : this.g) {
                    if (asVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, asVar);
                    }
                }
            }
            return computeSerializedSize;
        }

        public static at a(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (at) MessageNano.mergeFrom(new at(), bArr);
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
                } else if (readTag == 48) {
                    this.f = codedInputByteBufferNano.readInt32();
                } else if (readTag == 58) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    int length = this.g == null ? 0 : this.g.length;
                    as[] asVarArr = new as[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.g, 0, asVarArr, 0, length);
                    }
                    while (length < asVarArr.length - 1) {
                        asVarArr[length] = new as();
                        codedInputByteBufferNano.readMessage(asVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    asVarArr[length] = new as();
                    codedInputByteBufferNano.readMessage(asVarArr[length]);
                    this.g = asVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$au */
    /* compiled from: DetailV2 */
    public static final class au extends MessageNano {
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

        public au() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = "";
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
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
            }
            if (!this.j.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.j);
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
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
            }
            return !this.j.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(10, this.j) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
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

    /* renamed from: a$av */
    /* compiled from: DetailV2 */
    public static final class av extends MessageNano {
        private static volatile av[] d;
        public String a;
        public String b;
        public String c;

        public static av[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new av[0];
                    }
                }
            }
            return d;
        }

        public av() {
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

    /* renamed from: a$aw */
    /* compiled from: DetailV2 */
    public static final class aw extends MessageNano {
        private static volatile aw[] y;
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
        public String l;
        public String m;
        public String n;
        public String o;
        public String p;
        public String q;
        public String r;
        public String s;
        public String t;
        public String u;
        public String v;
        public String w;
        public String x;

        public static aw[] a() {
            if (y == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (y == null) {
                        y = new aw[0];
                    }
                }
            }
            return y;
        }

        public aw() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = "";
            this.k = "";
            this.l = "";
            this.m = "";
            this.n = "";
            this.o = "";
            this.p = "";
            this.q = "";
            this.r = "";
            this.s = "";
            this.t = "";
            this.u = "";
            this.v = "";
            this.w = "";
            this.x = "";
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
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
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
            if (!this.n.equals("")) {
                codedOutputByteBufferNano.writeString(14, this.n);
            }
            if (!this.o.equals("")) {
                codedOutputByteBufferNano.writeString(15, this.o);
            }
            if (!this.p.equals("")) {
                codedOutputByteBufferNano.writeString(16, this.p);
            }
            if (!this.q.equals("")) {
                codedOutputByteBufferNano.writeString(17, this.q);
            }
            if (!this.r.equals("")) {
                codedOutputByteBufferNano.writeString(18, this.r);
            }
            if (!this.s.equals("")) {
                codedOutputByteBufferNano.writeString(19, this.s);
            }
            if (!this.t.equals("")) {
                codedOutputByteBufferNano.writeString(20, this.t);
            }
            if (!this.u.equals("")) {
                codedOutputByteBufferNano.writeString(21, this.u);
            }
            if (!this.v.equals("")) {
                codedOutputByteBufferNano.writeString(22, this.v);
            }
            if (!this.w.equals("")) {
                codedOutputByteBufferNano.writeString(23, this.w);
            }
            if (!this.x.equals("")) {
                codedOutputByteBufferNano.writeString(24, this.x);
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
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
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
            if (!this.m.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(13, this.m);
            }
            if (!this.n.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(14, this.n);
            }
            if (!this.o.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(15, this.o);
            }
            if (!this.p.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(16, this.p);
            }
            if (!this.q.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(17, this.q);
            }
            if (!this.r.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(18, this.r);
            }
            if (!this.s.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(19, this.s);
            }
            if (!this.t.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(20, this.t);
            }
            if (!this.u.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(21, this.u);
            }
            if (!this.v.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(22, this.v);
            }
            if (!this.w.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(23, this.w);
            }
            return !this.x.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(24, this.x) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 114:
                        this.n = codedInputByteBufferNano.readString();
                        break;
                    case 122:
                        this.o = codedInputByteBufferNano.readString();
                        break;
                    case 130:
                        this.p = codedInputByteBufferNano.readString();
                        break;
                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                        this.q = codedInputByteBufferNano.readString();
                        break;
                    case 146:
                        this.r = codedInputByteBufferNano.readString();
                        break;
                    case ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE /*154*/:
                        this.s = codedInputByteBufferNano.readString();
                        break;
                    case EndBill.UNKNOWN_END_ORDER_FAILED /*162*/:
                        this.t = codedInputByteBufferNano.readString();
                        break;
                    case 170:
                        this.u = codedInputByteBufferNano.readString();
                        break;
                    case 178:
                        this.v = codedInputByteBufferNano.readString();
                        break;
                    case 186:
                        this.w = codedInputByteBufferNano.readString();
                        break;
                    case 194:
                        this.x = codedInputByteBufferNano.readString();
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

    /* renamed from: a$ax */
    /* compiled from: DetailV2 */
    public static final class ax extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public aw[] i;

        public ax() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = aw.a();
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
                for (aw awVar : this.i) {
                    if (awVar != null) {
                        codedOutputByteBufferNano.writeMessage(9, awVar);
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
            if (this.i != null && this.i.length > 0) {
                for (aw awVar : this.i) {
                    if (awVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(9, awVar);
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
                    aw[] awVarArr = new aw[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.i, 0, awVarArr, 0, length);
                    }
                    while (length < awVarArr.length - 1) {
                        awVarArr[length] = new aw();
                        codedInputByteBufferNano.readMessage(awVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    awVarArr[length] = new aw();
                    codedInputByteBufferNano.readMessage(awVarArr[length]);
                    this.i = awVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$ay */
    /* compiled from: DetailV2 */
    public static final class ay extends MessageNano {
        public int a;
        public boolean b;
        public String c;
        public String d;
        public String e;
        public int f;
        public s[] g;
        public c[] h;

        public ay() {
            this.a = 0;
            this.b = false;
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = 0;
            this.g = s.a();
            this.h = c.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.a);
            }
            if (this.b) {
                codedOutputByteBufferNano.writeBool(2, this.b);
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
            if (this.f != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                for (s sVar : this.g) {
                    if (sVar != null) {
                        codedOutputByteBufferNano.writeMessage(7, sVar);
                    }
                }
            }
            if (this.h != null && this.h.length > 0) {
                for (c cVar : this.h) {
                    if (cVar != null) {
                        codedOutputByteBufferNano.writeMessage(8, cVar);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, this.a);
            }
            if (this.b) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(2, this.b);
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
            if (this.f != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                int i = computeSerializedSize;
                for (s sVar : this.g) {
                    if (sVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(7, sVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (this.h != null && this.h.length > 0) {
                for (c cVar : this.h) {
                    if (cVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, cVar);
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
                if (readTag == 8) {
                    this.a = codedInputByteBufferNano.readInt32();
                } else if (readTag == 16) {
                    this.b = codedInputByteBufferNano.readBool();
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 48) {
                    this.f = codedInputByteBufferNano.readInt32();
                } else if (readTag == 58) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    int length = this.g == null ? 0 : this.g.length;
                    s[] sVarArr = new s[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.g, 0, sVarArr, 0, length);
                    }
                    while (length < sVarArr.length - 1) {
                        sVarArr[length] = new s();
                        codedInputByteBufferNano.readMessage(sVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    sVarArr[length] = new s();
                    codedInputByteBufferNano.readMessage(sVarArr[length]);
                    this.g = sVarArr;
                } else if (readTag == 66) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                    int length2 = this.h == null ? 0 : this.h.length;
                    c[] cVarArr = new c[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.h, 0, cVarArr, 0, length2);
                    }
                    while (length2 < cVarArr.length - 1) {
                        cVarArr[length2] = new c();
                        codedInputByteBufferNano.readMessage(cVarArr[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    cVarArr[length2] = new c();
                    codedInputByteBufferNano.readMessage(cVarArr[length2]);
                    this.h = cVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$az */
    /* compiled from: DetailV2 */
    public static final class az extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;

        public az() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
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
            return !this.e.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(5, this.e) : computeSerializedSize;
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$b */
    /* compiled from: DetailV2 */
    public static final class b extends MessageNano {
        public String a;
        public String b;

        public b() {
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

    /* renamed from: a$ba */
    /* compiled from: DetailV2 */
    public static final class ba extends MessageNano {
        private static volatile ba[] j;
        public int a;
        public int b;
        public String c;
        public String d;
        public int e;
        public String f;
        public String g;
        public bb[] h;
        public String i;

        public static ba[] a() {
            if (j == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (j == null) {
                        j = new ba[0];
                    }
                }
            }
            return j;
        }

        public ba() {
            this.a = 0;
            this.b = 0;
            this.c = "";
            this.d = "";
            this.e = 0;
            this.f = "";
            this.g = "";
            this.h = bb.a();
            this.i = "";
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
            if (!this.d.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.d);
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
            if (this.h != null && this.h.length > 0) {
                for (bb bbVar : this.h) {
                    if (bbVar != null) {
                        codedOutputByteBufferNano.writeMessage(8, bbVar);
                    }
                }
            }
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
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
            if (!this.c.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.c);
            }
            if (!this.d.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.d);
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
            if (this.h != null && this.h.length > 0) {
                for (bb bbVar : this.h) {
                    if (bbVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, bbVar);
                    }
                }
            }
            return !this.i.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(9, this.i) : computeSerializedSize;
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
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 40) {
                    this.e = codedInputByteBufferNano.readInt32();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 58) {
                    this.g = codedInputByteBufferNano.readString();
                } else if (readTag == 66) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                    int length = this.h == null ? 0 : this.h.length;
                    bb[] bbVarArr = new bb[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.h, 0, bbVarArr, 0, length);
                    }
                    while (length < bbVarArr.length - 1) {
                        bbVarArr[length] = new bb();
                        codedInputByteBufferNano.readMessage(bbVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bbVarArr[length] = new bb();
                    codedInputByteBufferNano.readMessage(bbVarArr[length]);
                    this.h = bbVarArr;
                } else if (readTag == 74) {
                    this.i = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bb */
    /* compiled from: DetailV2 */
    public static final class bb extends MessageNano {
        private static volatile bb[] s;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public int h;
        public String i;
        public String j;
        public String k;
        public String l;
        public float m;
        public String n;
        public String o;
        public int p;
        public t[] q;
        public j[] r;

        public static bb[] a() {
            if (s == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (s == null) {
                        s = new bb[0];
                    }
                }
            }
            return s;
        }

        public bb() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = 0;
            this.i = "";
            this.j = "";
            this.k = "";
            this.l = "";
            this.m = 0.0f;
            this.n = "";
            this.o = "";
            this.p = 0;
            this.q = t.a();
            this.r = j.a();
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
            if (this.h != 0) {
                codedOutputByteBufferNano.writeInt32(8, this.h);
            }
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
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
            if (Float.floatToIntBits(this.m) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(13, this.m);
            }
            if (!this.n.equals("")) {
                codedOutputByteBufferNano.writeString(14, this.n);
            }
            if (!this.o.equals("")) {
                codedOutputByteBufferNano.writeString(15, this.o);
            }
            if (this.p != 0) {
                codedOutputByteBufferNano.writeInt32(16, this.p);
            }
            if (this.q != null && this.q.length > 0) {
                for (t tVar : this.q) {
                    if (tVar != null) {
                        codedOutputByteBufferNano.writeMessage(17, tVar);
                    }
                }
            }
            if (this.r != null && this.r.length > 0) {
                for (j jVar : this.r) {
                    if (jVar != null) {
                        codedOutputByteBufferNano.writeMessage(18, jVar);
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
            if (this.h != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(8, this.h);
            }
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
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
            if (Float.floatToIntBits(this.m) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(13, this.m);
            }
            if (!this.n.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(14, this.n);
            }
            if (!this.o.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(15, this.o);
            }
            if (this.p != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(16, this.p);
            }
            if (this.q != null && this.q.length > 0) {
                int i2 = computeSerializedSize;
                for (t tVar : this.q) {
                    if (tVar != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(17, tVar);
                    }
                }
                computeSerializedSize = i2;
            }
            if (this.r != null && this.r.length > 0) {
                for (j jVar : this.r) {
                    if (jVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(18, jVar);
                    }
                }
            }
            return computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 64:
                        this.h = codedInputByteBufferNano.readInt32();
                        break;
                    case 74:
                        this.i = codedInputByteBufferNano.readString();
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
                    case 109:
                        this.m = codedInputByteBufferNano.readFloat();
                        break;
                    case 114:
                        this.n = codedInputByteBufferNano.readString();
                        break;
                    case 122:
                        this.o = codedInputByteBufferNano.readString();
                        break;
                    case 128:
                        this.p = codedInputByteBufferNano.readInt32();
                        break;
                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, DumpSegment.ANDROID_ROOT_FINALIZING);
                        int length = this.q == null ? 0 : this.q.length;
                        t[] tVarArr = new t[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.q, 0, tVarArr, 0, length);
                        }
                        while (length < tVarArr.length - 1) {
                            tVarArr[length] = new t();
                            codedInputByteBufferNano.readMessage(tVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        tVarArr[length] = new t();
                        codedInputByteBufferNano.readMessage(tVarArr[length]);
                        this.q = tVarArr;
                        break;
                    case 146:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 146);
                        int length2 = this.r == null ? 0 : this.r.length;
                        j[] jVarArr = new j[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.r, 0, jVarArr, 0, length2);
                        }
                        while (length2 < jVarArr.length - 1) {
                            jVarArr[length2] = new j();
                            codedInputByteBufferNano.readMessage(jVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        jVarArr[length2] = new j();
                        codedInputByteBufferNano.readMessage(jVarArr[length2]);
                        this.r = jVarArr;
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

    /* renamed from: a$bc */
    /* compiled from: DetailV2 */
    public static final class bc extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public int g;
        public bd[] h;

        public bc() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = 0;
            this.h = bd.a();
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
            if (this.g != 0) {
                codedOutputByteBufferNano.writeInt32(7, this.g);
            }
            if (this.h != null && this.h.length > 0) {
                for (bd bdVar : this.h) {
                    if (bdVar != null) {
                        codedOutputByteBufferNano.writeMessage(8, bdVar);
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
            if (this.g != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(7, this.g);
            }
            if (this.h != null && this.h.length > 0) {
                for (bd bdVar : this.h) {
                    if (bdVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, bdVar);
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
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 56) {
                    this.g = codedInputByteBufferNano.readInt32();
                } else if (readTag == 66) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                    int length = this.h == null ? 0 : this.h.length;
                    bd[] bdVarArr = new bd[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.h, 0, bdVarArr, 0, length);
                    }
                    while (length < bdVarArr.length - 1) {
                        bdVarArr[length] = new bd();
                        codedInputByteBufferNano.readMessage(bdVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bdVarArr[length] = new bd();
                    codedInputByteBufferNano.readMessage(bdVarArr[length]);
                    this.h = bdVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bd */
    /* compiled from: DetailV2 */
    public static final class bd extends MessageNano {
        private static volatile bd[] f;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;

        public static bd[] a() {
            if (f == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (f == null) {
                        f = new bd[0];
                    }
                }
            }
            return f;
        }

        public bd() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
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
            return !this.e.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(5, this.e) : computeSerializedSize;
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$be */
    /* compiled from: DetailV2 */
    public static final class be extends MessageNano {
        public boolean a;
        public int b;
        public String c;
        public long d;
        public String e;
        public bf f;

        public be() {
            this.a = false;
            this.b = 0;
            this.c = "";
            this.d = 0;
            this.e = "";
            this.f = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a) {
                codedOutputByteBufferNano.writeBool(1, this.a);
            }
            if (this.b != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.b);
            }
            if (!this.c.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.c);
            }
            if (this.d != 0) {
                codedOutputByteBufferNano.writeInt64(4, this.d);
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
            if (this.a) {
                computeSerializedSize += CodedOutputByteBufferNano.computeBoolSize(1, this.a);
            }
            if (this.b != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, this.b);
            }
            if (!this.c.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.c);
            }
            if (this.d != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(4, this.d);
            }
            if (!this.e.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.e);
            }
            return this.f != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(6, this.f) : computeSerializedSize;
        }

        public static be a(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (be) MessageNano.mergeFrom(new be(), bArr);
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 8) {
                    this.a = codedInputByteBufferNano.readBool();
                } else if (readTag == 16) {
                    this.b = codedInputByteBufferNano.readInt32();
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (readTag == 32) {
                    this.d = codedInputByteBufferNano.readInt64();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    if (this.f == null) {
                        this.f = new bf();
                    }
                    codedInputByteBufferNano.readMessage(this.f);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bf */
    /* compiled from: DetailV2 */
    public static final class bf extends MessageNano {
        public ax a;
        public g b;
        public ao c;
        public aa d;
        public n e;
        public q f;
        public u g;
        public ap h;
        public at i;
        public ay j;
        public bc k;
        public bk l;
        public bo m;
        public bq n;
        public bu o;
        public au p;

        public bf() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
            this.f = null;
            this.g = null;
            this.h = null;
            this.i = null;
            this.j = null;
            this.k = null;
            this.l = null;
            this.m = null;
            this.n = null;
            this.o = null;
            this.p = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null) {
                codedOutputByteBufferNano.writeMessage(1, this.a);
            }
            if (this.b != null) {
                codedOutputByteBufferNano.writeMessage(2, this.b);
            }
            if (this.c != null) {
                codedOutputByteBufferNano.writeMessage(3, this.c);
            }
            if (this.d != null) {
                codedOutputByteBufferNano.writeMessage(4, this.d);
            }
            if (this.e != null) {
                codedOutputByteBufferNano.writeMessage(5, this.e);
            }
            if (this.f != null) {
                codedOutputByteBufferNano.writeMessage(6, this.f);
            }
            if (this.g != null) {
                codedOutputByteBufferNano.writeMessage(7, this.g);
            }
            if (this.h != null) {
                codedOutputByteBufferNano.writeMessage(9, this.h);
            }
            if (this.i != null) {
                codedOutputByteBufferNano.writeMessage(10, this.i);
            }
            if (this.j != null) {
                codedOutputByteBufferNano.writeMessage(11, this.j);
            }
            if (this.k != null) {
                codedOutputByteBufferNano.writeMessage(12, this.k);
            }
            if (this.l != null) {
                codedOutputByteBufferNano.writeMessage(14, this.l);
            }
            if (this.m != null) {
                codedOutputByteBufferNano.writeMessage(15, this.m);
            }
            if (this.n != null) {
                codedOutputByteBufferNano.writeMessage(16, this.n);
            }
            if (this.o != null) {
                codedOutputByteBufferNano.writeMessage(17, this.o);
            }
            if (this.p != null) {
                codedOutputByteBufferNano.writeMessage(18, this.p);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, this.a);
            }
            if (this.b != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, this.b);
            }
            if (this.c != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, this.c);
            }
            if (this.d != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, this.d);
            }
            if (this.e != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, this.e);
            }
            if (this.f != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, this.f);
            }
            if (this.g != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, this.g);
            }
            if (this.h != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(9, this.h);
            }
            if (this.i != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(10, this.i);
            }
            if (this.j != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(11, this.j);
            }
            if (this.k != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(12, this.k);
            }
            if (this.l != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(14, this.l);
            }
            if (this.m != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(15, this.m);
            }
            if (this.n != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(16, this.n);
            }
            if (this.o != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(17, this.o);
            }
            return this.p != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(18, this.p) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        if (this.a == null) {
                            this.a = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.a);
                        break;
                    case 18:
                        if (this.b == null) {
                            this.b = new g();
                        }
                        codedInputByteBufferNano.readMessage(this.b);
                        break;
                    case 26:
                        if (this.c == null) {
                            this.c = new ao();
                        }
                        codedInputByteBufferNano.readMessage(this.c);
                        break;
                    case 34:
                        if (this.d == null) {
                            this.d = new aa();
                        }
                        codedInputByteBufferNano.readMessage(this.d);
                        break;
                    case 42:
                        if (this.e == null) {
                            this.e = new n();
                        }
                        codedInputByteBufferNano.readMessage(this.e);
                        break;
                    case 50:
                        if (this.f == null) {
                            this.f = new q();
                        }
                        codedInputByteBufferNano.readMessage(this.f);
                        break;
                    case 58:
                        if (this.g == null) {
                            this.g = new u();
                        }
                        codedInputByteBufferNano.readMessage(this.g);
                        break;
                    case 74:
                        if (this.h == null) {
                            this.h = new ap();
                        }
                        codedInputByteBufferNano.readMessage(this.h);
                        break;
                    case 82:
                        if (this.i == null) {
                            this.i = new at();
                        }
                        codedInputByteBufferNano.readMessage(this.i);
                        break;
                    case 90:
                        if (this.j == null) {
                            this.j = new ay();
                        }
                        codedInputByteBufferNano.readMessage(this.j);
                        break;
                    case 98:
                        if (this.k == null) {
                            this.k = new bc();
                        }
                        codedInputByteBufferNano.readMessage(this.k);
                        break;
                    case 114:
                        if (this.l == null) {
                            this.l = new bk();
                        }
                        codedInputByteBufferNano.readMessage(this.l);
                        break;
                    case 122:
                        if (this.m == null) {
                            this.m = new bo();
                        }
                        codedInputByteBufferNano.readMessage(this.m);
                        break;
                    case 130:
                        if (this.n == null) {
                            this.n = new bq();
                        }
                        codedInputByteBufferNano.readMessage(this.n);
                        break;
                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                        if (this.o == null) {
                            this.o = new bu();
                        }
                        codedInputByteBufferNano.readMessage(this.o);
                        break;
                    case 146:
                        if (this.p == null) {
                            this.p = new au();
                        }
                        codedInputByteBufferNano.readMessage(this.p);
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

    /* renamed from: a$bg */
    /* compiled from: DetailV2 */
    public static final class bg extends MessageNano {
        private static volatile bg[] t;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public float f;
        public String g;
        public String h;
        public String i;
        public int j;
        public bj[] k;
        public String l;
        public String m;
        public bi[] n;
        public int o;
        public String p;
        public String q;
        public b r;
        public int s;

        public static bg[] a() {
            if (t == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (t == null) {
                        t = new bg[0];
                    }
                }
            }
            return t;
        }

        public bg() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = 0.0f;
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = 0;
            this.k = bj.a();
            this.l = "";
            this.m = "";
            this.n = bi.a();
            this.o = 0;
            this.p = "";
            this.q = "";
            this.r = null;
            this.s = 0;
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
            if (Float.floatToIntBits(this.f) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(6, this.f);
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
            if (this.k != null && this.k.length > 0) {
                for (bj bjVar : this.k) {
                    if (bjVar != null) {
                        codedOutputByteBufferNano.writeMessage(11, bjVar);
                    }
                }
            }
            if (!this.l.equals("")) {
                codedOutputByteBufferNano.writeString(12, this.l);
            }
            if (!this.m.equals("")) {
                codedOutputByteBufferNano.writeString(13, this.m);
            }
            if (this.n != null && this.n.length > 0) {
                for (bi biVar : this.n) {
                    if (biVar != null) {
                        codedOutputByteBufferNano.writeMessage(14, biVar);
                    }
                }
            }
            if (this.o != 0) {
                codedOutputByteBufferNano.writeInt32(15, this.o);
            }
            if (!this.p.equals("")) {
                codedOutputByteBufferNano.writeString(16, this.p);
            }
            if (!this.q.equals("")) {
                codedOutputByteBufferNano.writeString(17, this.q);
            }
            if (this.r != null) {
                codedOutputByteBufferNano.writeMessage(18, this.r);
            }
            if (this.s != 0) {
                codedOutputByteBufferNano.writeInt32(19, this.s);
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
            if (Float.floatToIntBits(this.f) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(6, this.f);
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
            if (this.k != null && this.k.length > 0) {
                int i2 = computeSerializedSize;
                for (bj bjVar : this.k) {
                    if (bjVar != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(11, bjVar);
                    }
                }
                computeSerializedSize = i2;
            }
            if (!this.l.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(12, this.l);
            }
            if (!this.m.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(13, this.m);
            }
            if (this.n != null && this.n.length > 0) {
                for (bi biVar : this.n) {
                    if (biVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(14, biVar);
                    }
                }
            }
            if (this.o != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(15, this.o);
            }
            if (!this.p.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(16, this.p);
            }
            if (!this.q.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(17, this.q);
            }
            if (this.r != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(18, this.r);
            }
            return this.s != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(19, this.s) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 53:
                        this.f = codedInputByteBufferNano.readFloat();
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
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 90);
                        int length = this.k == null ? 0 : this.k.length;
                        bj[] bjVarArr = new bj[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.k, 0, bjVarArr, 0, length);
                        }
                        while (length < bjVarArr.length - 1) {
                            bjVarArr[length] = new bj();
                            codedInputByteBufferNano.readMessage(bjVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        bjVarArr[length] = new bj();
                        codedInputByteBufferNano.readMessage(bjVarArr[length]);
                        this.k = bjVarArr;
                        break;
                    case 98:
                        this.l = codedInputByteBufferNano.readString();
                        break;
                    case 106:
                        this.m = codedInputByteBufferNano.readString();
                        break;
                    case 114:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 114);
                        int length2 = this.n == null ? 0 : this.n.length;
                        bi[] biVarArr = new bi[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.n, 0, biVarArr, 0, length2);
                        }
                        while (length2 < biVarArr.length - 1) {
                            biVarArr[length2] = new bi();
                            codedInputByteBufferNano.readMessage(biVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        biVarArr[length2] = new bi();
                        codedInputByteBufferNano.readMessage(biVarArr[length2]);
                        this.n = biVarArr;
                        break;
                    case MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_ /*120*/:
                        this.o = codedInputByteBufferNano.readInt32();
                        break;
                    case 130:
                        this.p = codedInputByteBufferNano.readString();
                        break;
                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                        this.q = codedInputByteBufferNano.readString();
                        break;
                    case 146:
                        if (this.r == null) {
                            this.r = new b();
                        }
                        codedInputByteBufferNano.readMessage(this.r);
                        break;
                    case 152:
                        this.s = codedInputByteBufferNano.readInt32();
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

    /* renamed from: a$bh */
    /* compiled from: DetailV2 */
    public static final class bh extends MessageNano {
        private static volatile bh[] g;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public b f;

        public static bh[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new bh[0];
                    }
                }
            }
            return g;
        }

        public bh() {
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
                        this.f = new b();
                    }
                    codedInputByteBufferNano.readMessage(this.f);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bi */
    /* compiled from: DetailV2 */
    public static final class bi extends MessageNano {
        private static volatile bi[] c;
        public String a;
        public String b;

        public static bi[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new bi[0];
                    }
                }
            }
            return c;
        }

        public bi() {
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

    /* renamed from: a$bj */
    /* compiled from: DetailV2 */
    public static final class bj extends MessageNano {
        private static volatile bj[] c;
        public String a;
        public String b;

        public static bj[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new bj[0];
                    }
                }
            }
            return c;
        }

        public bj() {
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

    /* renamed from: a$bk */
    /* compiled from: DetailV2 */
    public static final class bk extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public String n;
        public String o;
        public float p;
        public int q;
        public i[] r;
        public bn[] s;
        public bg[] t;
        public bg[] u;
        public bh[] v;
        public w w;
        public bl[] x;
        public Map<String, Integer> y;

        public bk() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = 0;
            this.i = 0;
            this.j = 0;
            this.k = 0;
            this.l = 0;
            this.m = 0;
            this.n = "";
            this.o = "";
            this.p = 0.0f;
            this.q = 0;
            this.r = i.a();
            this.s = bn.a();
            this.t = bg.a();
            this.u = bg.a();
            this.v = bh.a();
            this.w = null;
            this.x = bl.a();
            this.y = null;
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
            if (this.h != 0) {
                codedOutputByteBufferNano.writeInt32(8, this.h);
            }
            if (this.i != 0) {
                codedOutputByteBufferNano.writeInt32(9, this.i);
            }
            if (this.j != 0) {
                codedOutputByteBufferNano.writeInt32(10, this.j);
            }
            if (this.k != 0) {
                codedOutputByteBufferNano.writeInt32(11, this.k);
            }
            if (this.l != 0) {
                codedOutputByteBufferNano.writeInt32(12, this.l);
            }
            if (this.m != 0) {
                codedOutputByteBufferNano.writeInt32(13, this.m);
            }
            if (!this.n.equals("")) {
                codedOutputByteBufferNano.writeString(14, this.n);
            }
            if (!this.o.equals("")) {
                codedOutputByteBufferNano.writeString(15, this.o);
            }
            if (Float.floatToIntBits(this.p) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(16, this.p);
            }
            if (this.q != 0) {
                codedOutputByteBufferNano.writeInt32(17, this.q);
            }
            if (this.r != null && this.r.length > 0) {
                for (i iVar : this.r) {
                    if (iVar != null) {
                        codedOutputByteBufferNano.writeMessage(18, iVar);
                    }
                }
            }
            if (this.s != null && this.s.length > 0) {
                for (bn bnVar : this.s) {
                    if (bnVar != null) {
                        codedOutputByteBufferNano.writeMessage(19, bnVar);
                    }
                }
            }
            if (this.t != null && this.t.length > 0) {
                for (bg bgVar : this.t) {
                    if (bgVar != null) {
                        codedOutputByteBufferNano.writeMessage(20, bgVar);
                    }
                }
            }
            if (this.u != null && this.u.length > 0) {
                for (bg bgVar2 : this.u) {
                    if (bgVar2 != null) {
                        codedOutputByteBufferNano.writeMessage(21, bgVar2);
                    }
                }
            }
            if (this.v != null && this.v.length > 0) {
                for (bh bhVar : this.v) {
                    if (bhVar != null) {
                        codedOutputByteBufferNano.writeMessage(22, bhVar);
                    }
                }
            }
            if (this.w != null) {
                codedOutputByteBufferNano.writeMessage(23, this.w);
            }
            if (this.x != null && this.x.length > 0) {
                for (bl blVar : this.x) {
                    if (blVar != null) {
                        codedOutputByteBufferNano.writeMessage(24, blVar);
                    }
                }
            }
            if (this.y != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.y, 25, 9, 5);
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
            if (this.h != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(8, this.h);
            }
            if (this.i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, this.i);
            }
            if (this.j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(10, this.j);
            }
            if (this.k != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(11, this.k);
            }
            if (this.l != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(12, this.l);
            }
            if (this.m != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(13, this.m);
            }
            if (!this.n.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(14, this.n);
            }
            if (!this.o.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(15, this.o);
            }
            if (Float.floatToIntBits(this.p) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(16, this.p);
            }
            if (this.q != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(17, this.q);
            }
            if (this.r != null && this.r.length > 0) {
                int i2 = computeSerializedSize;
                for (i iVar : this.r) {
                    if (iVar != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(18, iVar);
                    }
                }
                computeSerializedSize = i2;
            }
            if (this.s != null && this.s.length > 0) {
                int i3 = computeSerializedSize;
                for (bn bnVar : this.s) {
                    if (bnVar != null) {
                        i3 += CodedOutputByteBufferNano.computeMessageSize(19, bnVar);
                    }
                }
                computeSerializedSize = i3;
            }
            if (this.t != null && this.t.length > 0) {
                int i4 = computeSerializedSize;
                for (bg bgVar : this.t) {
                    if (bgVar != null) {
                        i4 += CodedOutputByteBufferNano.computeMessageSize(20, bgVar);
                    }
                }
                computeSerializedSize = i4;
            }
            if (this.u != null && this.u.length > 0) {
                int i5 = computeSerializedSize;
                for (bg bgVar2 : this.u) {
                    if (bgVar2 != null) {
                        i5 += CodedOutputByteBufferNano.computeMessageSize(21, bgVar2);
                    }
                }
                computeSerializedSize = i5;
            }
            if (this.v != null && this.v.length > 0) {
                int i6 = computeSerializedSize;
                for (bh bhVar : this.v) {
                    if (bhVar != null) {
                        i6 += CodedOutputByteBufferNano.computeMessageSize(22, bhVar);
                    }
                }
                computeSerializedSize = i6;
            }
            if (this.w != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(23, this.w);
            }
            if (this.x != null && this.x.length > 0) {
                for (bl blVar : this.x) {
                    if (blVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(24, blVar);
                    }
                }
            }
            return this.y != null ? computeSerializedSize + InternalNano.computeMapFieldSize(this.y, 25, 9, 5) : computeSerializedSize;
        }

        public static bk a(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (bk) MessageNano.mergeFrom(new bk(), bArr);
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            MapFactory mapFactory = MapFactories.getMapFactory();
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 64:
                        this.h = codedInputByteBufferNano.readInt32();
                        break;
                    case 72:
                        this.i = codedInputByteBufferNano.readInt32();
                        break;
                    case 80:
                        this.j = codedInputByteBufferNano.readInt32();
                        break;
                    case IjkMediaMeta.FF_PROFILE_H264_EXTENDED /*88*/:
                        this.k = codedInputByteBufferNano.readInt32();
                        break;
                    case 96:
                        this.l = codedInputByteBufferNano.readInt32();
                        break;
                    case 104:
                        this.m = codedInputByteBufferNano.readInt32();
                        break;
                    case 114:
                        this.n = codedInputByteBufferNano.readString();
                        break;
                    case 122:
                        this.o = codedInputByteBufferNano.readString();
                        break;
                    case 133:
                        this.p = codedInputByteBufferNano.readFloat();
                        break;
                    case 136:
                        this.q = codedInputByteBufferNano.readInt32();
                        break;
                    case 146:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 146);
                        int length = this.r == null ? 0 : this.r.length;
                        i[] iVarArr = new i[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.r, 0, iVarArr, 0, length);
                        }
                        while (length < iVarArr.length - 1) {
                            iVarArr[length] = new i();
                            codedInputByteBufferNano.readMessage(iVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        iVarArr[length] = new i();
                        codedInputByteBufferNano.readMessage(iVarArr[length]);
                        this.r = iVarArr;
                        break;
                    case ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE /*154*/:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE);
                        int length2 = this.s == null ? 0 : this.s.length;
                        bn[] bnVarArr = new bn[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.s, 0, bnVarArr, 0, length2);
                        }
                        while (length2 < bnVarArr.length - 1) {
                            bnVarArr[length2] = new bn();
                            codedInputByteBufferNano.readMessage(bnVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        bnVarArr[length2] = new bn();
                        codedInputByteBufferNano.readMessage(bnVarArr[length2]);
                        this.s = bnVarArr;
                        break;
                    case EndBill.UNKNOWN_END_ORDER_FAILED /*162*/:
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, EndBill.UNKNOWN_END_ORDER_FAILED);
                        int length3 = this.t == null ? 0 : this.t.length;
                        bg[] bgVarArr = new bg[(repeatedFieldArrayLength3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.t, 0, bgVarArr, 0, length3);
                        }
                        while (length3 < bgVarArr.length - 1) {
                            bgVarArr[length3] = new bg();
                            codedInputByteBufferNano.readMessage(bgVarArr[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        bgVarArr[length3] = new bg();
                        codedInputByteBufferNano.readMessage(bgVarArr[length3]);
                        this.t = bgVarArr;
                        break;
                    case 170:
                        int repeatedFieldArrayLength4 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 170);
                        int length4 = this.u == null ? 0 : this.u.length;
                        bg[] bgVarArr2 = new bg[(repeatedFieldArrayLength4 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.u, 0, bgVarArr2, 0, length4);
                        }
                        while (length4 < bgVarArr2.length - 1) {
                            bgVarArr2[length4] = new bg();
                            codedInputByteBufferNano.readMessage(bgVarArr2[length4]);
                            codedInputByteBufferNano.readTag();
                            length4++;
                        }
                        bgVarArr2[length4] = new bg();
                        codedInputByteBufferNano.readMessage(bgVarArr2[length4]);
                        this.u = bgVarArr2;
                        break;
                    case 178:
                        int repeatedFieldArrayLength5 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 178);
                        int length5 = this.v == null ? 0 : this.v.length;
                        bh[] bhVarArr = new bh[(repeatedFieldArrayLength5 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.v, 0, bhVarArr, 0, length5);
                        }
                        while (length5 < bhVarArr.length - 1) {
                            bhVarArr[length5] = new bh();
                            codedInputByteBufferNano.readMessage(bhVarArr[length5]);
                            codedInputByteBufferNano.readTag();
                            length5++;
                        }
                        bhVarArr[length5] = new bh();
                        codedInputByteBufferNano.readMessage(bhVarArr[length5]);
                        this.v = bhVarArr;
                        break;
                    case 186:
                        if (this.w == null) {
                            this.w = new w();
                        }
                        codedInputByteBufferNano.readMessage(this.w);
                        break;
                    case 194:
                        int repeatedFieldArrayLength6 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 194);
                        int length6 = this.x == null ? 0 : this.x.length;
                        bl[] blVarArr = new bl[(repeatedFieldArrayLength6 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.x, 0, blVarArr, 0, length6);
                        }
                        while (length6 < blVarArr.length - 1) {
                            blVarArr[length6] = new bl();
                            codedInputByteBufferNano.readMessage(blVarArr[length6]);
                            codedInputByteBufferNano.readTag();
                            length6++;
                        }
                        blVarArr[length6] = new bl();
                        codedInputByteBufferNano.readMessage(blVarArr[length6]);
                        this.x = blVarArr;
                        break;
                    case 202:
                        this.y = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.y, mapFactory, 9, 5, null, 10, 16);
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

    /* renamed from: a$bl */
    /* compiled from: DetailV2 */
    public static final class bl extends MessageNano {
        private static volatile bl[] h;
        public int a;
        public int b;
        public int c;
        public String d;
        public String e;
        public String f;
        public String g;

        public static bl[] a() {
            if (h == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (h == null) {
                        h = new bl[0];
                    }
                }
            }
            return h;
        }

        public bl() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.a);
            }
            if (this.b != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.b);
            }
            if (this.c != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.c);
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
            if (this.c != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, this.c);
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
            return !this.g.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(7, this.g) : computeSerializedSize;
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
                } else if (readTag == 24) {
                    this.c = codedInputByteBufferNano.readInt32();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 58) {
                    this.g = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bm */
    /* compiled from: DetailV2 */
    public static final class bm extends MessageNano {
        private static volatile bm[] h;
        public String a;
        public String b;
        public String c;
        public int d;
        public String e;
        public String f;
        public String g;

        public static bm[] a() {
            if (h == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (h == null) {
                        h = new bm[0];
                    }
                }
            }
            return h;
        }

        public bm() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = 0;
            this.e = "";
            this.f = "";
            this.g = "";
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
            if (this.d != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.d);
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
            if (this.d != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, this.d);
            }
            if (!this.e.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.e);
            }
            if (!this.f.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(6, this.f);
            }
            return !this.g.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(7, this.g) : computeSerializedSize;
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
                } else if (readTag == 32) {
                    this.d = codedInputByteBufferNano.readInt32();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 58) {
                    this.g = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bn */
    /* compiled from: DetailV2 */
    public static final class bn extends MessageNano {
        private static volatile bn[] e;
        public String a;
        public int b;
        public int c;
        public int d;

        public static bn[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new bn[0];
                    }
                }
            }
            return e;
        }

        public bn() {
            this.a = "";
            this.b = 0;
            this.c = 0;
            this.d = 0;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.b);
            }
            if (this.c != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.c);
            }
            if (this.d != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.d);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            if (this.b != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, this.b);
            }
            if (this.c != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, this.c);
            }
            return this.d != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(4, this.d) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.a = codedInputByteBufferNano.readString();
                } else if (readTag == 16) {
                    this.b = codedInputByteBufferNano.readInt32();
                } else if (readTag == 24) {
                    this.c = codedInputByteBufferNano.readInt32();
                } else if (readTag == 32) {
                    this.d = codedInputByteBufferNano.readInt32();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bo */
    /* compiled from: DetailV2 */
    public static final class bo extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public h[] f;
        public ab[] g;

        public bo() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = h.a();
            this.g = ab.a();
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
            if (this.f != null && this.f.length > 0) {
                for (h hVar : this.f) {
                    if (hVar != null) {
                        codedOutputByteBufferNano.writeMessage(6, hVar);
                    }
                }
            }
            if (this.g != null && this.g.length > 0) {
                for (ab abVar : this.g) {
                    if (abVar != null) {
                        codedOutputByteBufferNano.writeMessage(7, abVar);
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
            if (this.f != null && this.f.length > 0) {
                int i = computeSerializedSize;
                for (h hVar : this.f) {
                    if (hVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(6, hVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (this.g != null && this.g.length > 0) {
                for (ab abVar : this.g) {
                    if (abVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, abVar);
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
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                    int length = this.f == null ? 0 : this.f.length;
                    h[] hVarArr = new h[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.f, 0, hVarArr, 0, length);
                    }
                    while (length < hVarArr.length - 1) {
                        hVarArr[length] = new h();
                        codedInputByteBufferNano.readMessage(hVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    hVarArr[length] = new h();
                    codedInputByteBufferNano.readMessage(hVarArr[length]);
                    this.f = hVarArr;
                } else if (readTag == 58) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    int length2 = this.g == null ? 0 : this.g.length;
                    ab[] abVarArr = new ab[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.g, 0, abVarArr, 0, length2);
                    }
                    while (length2 < abVarArr.length - 1) {
                        abVarArr[length2] = new ab();
                        codedInputByteBufferNano.readMessage(abVarArr[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    abVarArr[length2] = new ab();
                    codedInputByteBufferNano.readMessage(abVarArr[length2]);
                    this.g = abVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bp */
    /* compiled from: DetailV2 */
    public static final class bp extends MessageNano {
        private static volatile bp[] l;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public int h;
        public String i;
        public av[] j;
        public bm[] k;

        public static bp[] a() {
            if (l == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (l == null) {
                        l = new bp[0];
                    }
                }
            }
            return l;
        }

        public bp() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = 0;
            this.i = "";
            this.j = av.a();
            this.k = bm.a();
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
            if (this.h != 0) {
                codedOutputByteBufferNano.writeInt32(8, this.h);
            }
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
            }
            if (this.j != null && this.j.length > 0) {
                for (av avVar : this.j) {
                    if (avVar != null) {
                        codedOutputByteBufferNano.writeMessage(10, avVar);
                    }
                }
            }
            if (this.k != null && this.k.length > 0) {
                for (bm bmVar : this.k) {
                    if (bmVar != null) {
                        codedOutputByteBufferNano.writeMessage(11, bmVar);
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
            if (this.h != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(8, this.h);
            }
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
            }
            if (this.j != null && this.j.length > 0) {
                int i2 = computeSerializedSize;
                for (av avVar : this.j) {
                    if (avVar != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(10, avVar);
                    }
                }
                computeSerializedSize = i2;
            }
            if (this.k != null && this.k.length > 0) {
                for (bm bmVar : this.k) {
                    if (bmVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(11, bmVar);
                    }
                }
            }
            return computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 64:
                        this.h = codedInputByteBufferNano.readInt32();
                        break;
                    case 74:
                        this.i = codedInputByteBufferNano.readString();
                        break;
                    case 82:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 82);
                        int length = this.j == null ? 0 : this.j.length;
                        av[] avVarArr = new av[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.j, 0, avVarArr, 0, length);
                        }
                        while (length < avVarArr.length - 1) {
                            avVarArr[length] = new av();
                            codedInputByteBufferNano.readMessage(avVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        avVarArr[length] = new av();
                        codedInputByteBufferNano.readMessage(avVarArr[length]);
                        this.j = avVarArr;
                        break;
                    case 90:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 90);
                        int length2 = this.k == null ? 0 : this.k.length;
                        bm[] bmVarArr = new bm[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.k, 0, bmVarArr, 0, length2);
                        }
                        while (length2 < bmVarArr.length - 1) {
                            bmVarArr[length2] = new bm();
                            codedInputByteBufferNano.readMessage(bmVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        bmVarArr[length2] = new bm();
                        codedInputByteBufferNano.readMessage(bmVarArr[length2]);
                        this.k = bmVarArr;
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

    /* renamed from: a$bq */
    /* compiled from: DetailV2 */
    public static final class bq extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public bp[] f;

        public bq() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = bp.a();
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
            if (this.f != null && this.f.length > 0) {
                for (bp bpVar : this.f) {
                    if (bpVar != null) {
                        codedOutputByteBufferNano.writeMessage(6, bpVar);
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
            if (this.f != null && this.f.length > 0) {
                for (bp bpVar : this.f) {
                    if (bpVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, bpVar);
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
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                    int length = this.f == null ? 0 : this.f.length;
                    bp[] bpVarArr = new bp[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.f, 0, bpVarArr, 0, length);
                    }
                    while (length < bpVarArr.length - 1) {
                        bpVarArr[length] = new bp();
                        codedInputByteBufferNano.readMessage(bpVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bpVarArr[length] = new bp();
                    codedInputByteBufferNano.readMessage(bpVarArr[length]);
                    this.f = bpVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$br */
    /* compiled from: DetailV2 */
    public static final class br extends MessageNano {
        private static volatile br[] d;
        public String a;
        public String b;
        public String[] c;

        public static br[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new br[0];
                    }
                }
            }
            return d;
        }

        public br() {
            this.a = "";
            this.b = "";
            this.c = WireFormatNano.EMPTY_STRING_ARRAY;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (String str : this.c) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(3, str);
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
            if (this.c == null || this.c.length <= 0) {
                return computeSerializedSize;
            }
            int i = 0;
            int i2 = 0;
            for (String str : this.c) {
                if (str != null) {
                    i2++;
                    i += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                }
            }
            return computeSerializedSize + i + (i2 * 1);
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length = this.c == null ? 0 : this.c.length;
                    String[] strArr = new String[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    strArr[length] = codedInputByteBufferNano.readString();
                    this.c = strArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bs */
    /* compiled from: DetailV2 */
    public static final class bs extends MessageNano {
        private static volatile bs[] u;
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
        public float l;
        public float m;
        public String n;
        public String o;
        public int p;
        public int q;
        public int r;
        public int s;
        public String t;

        public static bs[] a() {
            if (u == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (u == null) {
                        u = new bs[0];
                    }
                }
            }
            return u;
        }

        public bs() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = "";
            this.k = "";
            this.l = 0.0f;
            this.m = 0.0f;
            this.n = "";
            this.o = "";
            this.p = 0;
            this.q = 0;
            this.r = 0;
            this.s = 0;
            this.t = "";
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
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
            }
            if (!this.j.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.j);
            }
            if (!this.k.equals("")) {
                codedOutputByteBufferNano.writeString(11, this.k);
            }
            if (Float.floatToIntBits(this.l) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(12, this.l);
            }
            if (Float.floatToIntBits(this.m) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(13, this.m);
            }
            if (!this.n.equals("")) {
                codedOutputByteBufferNano.writeString(14, this.n);
            }
            if (!this.o.equals("")) {
                codedOutputByteBufferNano.writeString(15, this.o);
            }
            if (this.p != 0) {
                codedOutputByteBufferNano.writeInt32(16, this.p);
            }
            if (this.q != 0) {
                codedOutputByteBufferNano.writeInt32(17, this.q);
            }
            if (this.r != 0) {
                codedOutputByteBufferNano.writeInt32(18, this.r);
            }
            if (this.s != 0) {
                codedOutputByteBufferNano.writeInt32(19, this.s);
            }
            if (!this.t.equals("")) {
                codedOutputByteBufferNano.writeString(20, this.t);
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
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
            }
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            if (!this.k.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(11, this.k);
            }
            if (Float.floatToIntBits(this.l) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(12, this.l);
            }
            if (Float.floatToIntBits(this.m) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(13, this.m);
            }
            if (!this.n.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(14, this.n);
            }
            if (!this.o.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(15, this.o);
            }
            if (this.p != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(16, this.p);
            }
            if (this.q != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(17, this.q);
            }
            if (this.r != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(18, this.r);
            }
            if (this.s != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(19, this.s);
            }
            return !this.t.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(20, this.t) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
                        break;
                    case 90:
                        this.k = codedInputByteBufferNano.readString();
                        break;
                    case 101:
                        this.l = codedInputByteBufferNano.readFloat();
                        break;
                    case 109:
                        this.m = codedInputByteBufferNano.readFloat();
                        break;
                    case 114:
                        this.n = codedInputByteBufferNano.readString();
                        break;
                    case 122:
                        this.o = codedInputByteBufferNano.readString();
                        break;
                    case 128:
                        this.p = codedInputByteBufferNano.readInt32();
                        break;
                    case 136:
                        this.q = codedInputByteBufferNano.readInt32();
                        break;
                    case 144:
                        this.r = codedInputByteBufferNano.readInt32();
                        break;
                    case 152:
                        this.s = codedInputByteBufferNano.readInt32();
                        break;
                    case EndBill.UNKNOWN_END_ORDER_FAILED /*162*/:
                        this.t = codedInputByteBufferNano.readString();
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

    /* renamed from: a$bt */
    /* compiled from: DetailV2 */
    public static final class bt extends MessageNano {
        private static volatile bt[] g;
        public String a;
        public String b;
        public bs[] c;
        public String d;
        public int e;
        public String f;

        public static bt[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new bt[0];
                    }
                }
            }
            return g;
        }

        public bt() {
            this.a = "";
            this.b = "";
            this.c = bs.a();
            this.d = "";
            this.e = 0;
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
            if (this.c != null && this.c.length > 0) {
                for (bs bsVar : this.c) {
                    if (bsVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, bsVar);
                    }
                }
            }
            if (!this.d.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.d);
            }
            if (this.e != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.e);
            }
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.f);
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
            if (this.c != null && this.c.length > 0) {
                for (bs bsVar : this.c) {
                    if (bsVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, bsVar);
                    }
                }
            }
            if (!this.d.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.d);
            }
            if (this.e != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, this.e);
            }
            return !this.f.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(6, this.f) : computeSerializedSize;
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length = this.c == null ? 0 : this.c.length;
                    bs[] bsVarArr = new bs[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, bsVarArr, 0, length);
                    }
                    while (length < bsVarArr.length - 1) {
                        bsVarArr[length] = new bs();
                        codedInputByteBufferNano.readMessage(bsVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bsVarArr[length] = new bs();
                    codedInputByteBufferNano.readMessage(bsVarArr[length]);
                    this.c = bsVarArr;
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 40) {
                    this.e = codedInputByteBufferNano.readInt32();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bu */
    /* compiled from: DetailV2 */
    public static final class bu extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public bv[] f;

        public bu() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = bv.a();
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
            if (this.f != null && this.f.length > 0) {
                for (bv bvVar : this.f) {
                    if (bvVar != null) {
                        codedOutputByteBufferNano.writeMessage(6, bvVar);
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
            if (this.f != null && this.f.length > 0) {
                for (bv bvVar : this.f) {
                    if (bvVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, bvVar);
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
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                    int length = this.f == null ? 0 : this.f.length;
                    bv[] bvVarArr = new bv[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.f, 0, bvVarArr, 0, length);
                    }
                    while (length < bvVarArr.length - 1) {
                        bvVarArr[length] = new bv();
                        codedInputByteBufferNano.readMessage(bvVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bvVarArr[length] = new bv();
                    codedInputByteBufferNano.readMessage(bvVarArr[length]);
                    this.f = bvVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bv */
    /* compiled from: DetailV2 */
    public static final class bv extends MessageNano {
        private static volatile bv[] d;
        public String a;
        public int b;
        public bw[] c;

        public static bv[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new bv[0];
                    }
                }
            }
            return d;
        }

        public bv() {
            this.a = "";
            this.b = 0;
            this.c = bw.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (bw bwVar : this.c) {
                    if (bwVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, bwVar);
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
            if (this.b != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (bw bwVar : this.c) {
                    if (bwVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, bwVar);
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
                } else if (readTag == 16) {
                    this.b = codedInputByteBufferNano.readInt32();
                } else if (readTag == 26) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length = this.c == null ? 0 : this.c.length;
                    bw[] bwVarArr = new bw[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, bwVarArr, 0, length);
                    }
                    while (length < bwVarArr.length - 1) {
                        bwVarArr[length] = new bw();
                        codedInputByteBufferNano.readMessage(bwVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bwVarArr[length] = new bw();
                    codedInputByteBufferNano.readMessage(bwVarArr[length]);
                    this.c = bwVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$bw */
    /* compiled from: DetailV2 */
    public static final class bw extends MessageNano {
        private static volatile bw[] g;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;

        public static bw[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new bw[0];
                    }
                }
            }
            return g;
        }

        public bw() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
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
                codedOutputByteBufferNano.writeString(4, this.d);
            }
            if (!this.e.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.e);
            }
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.f);
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
            return !this.f.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(6, this.f) : computeSerializedSize;
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$c */
    /* compiled from: DetailV2 */
    public static final class c extends MessageNano {
        private static volatile c[] j;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public ar[] i;

        public static c[] a() {
            if (j == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (j == null) {
                        j = new c[0];
                    }
                }
            }
            return j;
        }

        public c() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = ar.a();
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
                for (ar arVar : this.i) {
                    if (arVar != null) {
                        codedOutputByteBufferNano.writeMessage(9, arVar);
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
            if (this.i != null && this.i.length > 0) {
                for (ar arVar : this.i) {
                    if (arVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(9, arVar);
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
                    ar[] arVarArr = new ar[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.i, 0, arVarArr, 0, length);
                    }
                    while (length < arVarArr.length - 1) {
                        arVarArr[length] = new ar();
                        codedInputByteBufferNano.readMessage(arVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    arVarArr[length] = new ar();
                    codedInputByteBufferNano.readMessage(arVarArr[length]);
                    this.i = arVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$d */
    /* compiled from: DetailV2 */
    public static final class d extends MessageNano {
        public String a;
        public String b;

        public d() {
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

    /* renamed from: a$e */
    /* compiled from: DetailV2 */
    public static final class e extends MessageNano {
        public String a;
        public String b;
        public f[] c;

        public e() {
            this.a = "";
            this.b = "";
            this.c = f.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (f fVar : this.c) {
                    if (fVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, fVar);
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
            if (this.c != null && this.c.length > 0) {
                for (f fVar : this.c) {
                    if (fVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, fVar);
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length = this.c == null ? 0 : this.c.length;
                    f[] fVarArr = new f[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, fVarArr, 0, length);
                    }
                    while (length < fVarArr.length - 1) {
                        fVarArr[length] = new f();
                        codedInputByteBufferNano.readMessage(fVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    fVarArr[length] = new f();
                    codedInputByteBufferNano.readMessage(fVarArr[length]);
                    this.c = fVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$f */
    /* compiled from: DetailV2 */
    public static final class f extends MessageNano {
        private static volatile f[] e;
        public String a;
        public String b;
        public String c;
        public d d;

        public static f[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new f[0];
                    }
                }
            }
            return e;
        }

        public f() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = null;
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
            if (this.d != null) {
                codedOutputByteBufferNano.writeMessage(4, this.d);
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
            return this.d != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(4, this.d) : computeSerializedSize;
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
                    if (this.d == null) {
                        this.d = new d();
                    }
                    codedInputByteBufferNano.readMessage(this.d);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$g */
    /* compiled from: DetailV2 */
    public static final class g extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public C0041a f;
        public int g;
        public String h;
        public bb[] i;
        public String j;
        public String k;
        public x l;
        public bt[] m;
        public String n;
        public String o;
        public String p;

        public g() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = null;
            this.g = 0;
            this.h = "";
            this.i = bb.a();
            this.j = "";
            this.k = "";
            this.l = null;
            this.m = bt.a();
            this.n = "";
            this.o = "";
            this.p = "";
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
            if (this.g != 0) {
                codedOutputByteBufferNano.writeInt32(7, this.g);
            }
            if (!this.h.equals("")) {
                codedOutputByteBufferNano.writeString(8, this.h);
            }
            if (this.i != null && this.i.length > 0) {
                for (bb bbVar : this.i) {
                    if (bbVar != null) {
                        codedOutputByteBufferNano.writeMessage(9, bbVar);
                    }
                }
            }
            if (!this.j.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.j);
            }
            if (!this.k.equals("")) {
                codedOutputByteBufferNano.writeString(11, this.k);
            }
            if (this.l != null) {
                codedOutputByteBufferNano.writeMessage(12, this.l);
            }
            if (this.m != null && this.m.length > 0) {
                for (bt btVar : this.m) {
                    if (btVar != null) {
                        codedOutputByteBufferNano.writeMessage(13, btVar);
                    }
                }
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
            if (this.f != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, this.f);
            }
            if (this.g != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(7, this.g);
            }
            if (!this.h.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(8, this.h);
            }
            if (this.i != null && this.i.length > 0) {
                int i2 = computeSerializedSize;
                for (bb bbVar : this.i) {
                    if (bbVar != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(9, bbVar);
                    }
                }
                computeSerializedSize = i2;
            }
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            if (!this.k.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(11, this.k);
            }
            if (this.l != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(12, this.l);
            }
            if (this.m != null && this.m.length > 0) {
                for (bt btVar : this.m) {
                    if (btVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(13, btVar);
                    }
                }
            }
            if (!this.n.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(14, this.n);
            }
            if (!this.o.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(15, this.o);
            }
            return !this.p.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(16, this.p) : computeSerializedSize;
        }

        public static g a(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (g) MessageNano.mergeFrom(new g(), bArr);
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                        if (this.f == null) {
                            this.f = new C0041a();
                        }
                        codedInputByteBufferNano.readMessage(this.f);
                        break;
                    case 56:
                        this.g = codedInputByteBufferNano.readInt32();
                        break;
                    case 66:
                        this.h = codedInputByteBufferNano.readString();
                        break;
                    case 74:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        int length = this.i == null ? 0 : this.i.length;
                        bb[] bbVarArr = new bb[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.i, 0, bbVarArr, 0, length);
                        }
                        while (length < bbVarArr.length - 1) {
                            bbVarArr[length] = new bb();
                            codedInputByteBufferNano.readMessage(bbVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        bbVarArr[length] = new bb();
                        codedInputByteBufferNano.readMessage(bbVarArr[length]);
                        this.i = bbVarArr;
                        break;
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
                        break;
                    case 90:
                        this.k = codedInputByteBufferNano.readString();
                        break;
                    case 98:
                        if (this.l == null) {
                            this.l = new x();
                        }
                        codedInputByteBufferNano.readMessage(this.l);
                        break;
                    case 106:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 106);
                        int length2 = this.m == null ? 0 : this.m.length;
                        bt[] btVarArr = new bt[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.m, 0, btVarArr, 0, length2);
                        }
                        while (length2 < btVarArr.length - 1) {
                            btVarArr[length2] = new bt();
                            codedInputByteBufferNano.readMessage(btVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        btVarArr[length2] = new bt();
                        codedInputByteBufferNano.readMessage(btVarArr[length2]);
                        this.m = btVarArr;
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

    /* renamed from: a$h */
    /* compiled from: DetailV2 */
    public static final class h extends MessageNano {
        private static volatile h[] g;
        public long a;
        public String b;
        public int c;
        public String d;
        public long e;
        public long f;

        public static h[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new h[0];
                    }
                }
            }
            return g;
        }

        public h() {
            this.a = 0;
            this.b = "";
            this.c = 0;
            this.d = "";
            this.e = 0;
            this.f = 0;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt64(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.c);
            }
            if (!this.d.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.d);
            }
            if (this.e != 0) {
                codedOutputByteBufferNano.writeInt64(5, this.e);
            }
            if (this.f != 0) {
                codedOutputByteBufferNano.writeInt64(6, this.f);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, this.a);
            }
            if (!this.b.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.b);
            }
            if (this.c != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, this.c);
            }
            if (!this.d.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.d);
            }
            if (this.e != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(5, this.e);
            }
            return this.f != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt64Size(6, this.f) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 8) {
                    this.a = codedInputByteBufferNano.readInt64();
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 24) {
                    this.c = codedInputByteBufferNano.readInt32();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 40) {
                    this.e = codedInputByteBufferNano.readInt64();
                } else if (readTag == 48) {
                    this.f = codedInputByteBufferNano.readInt64();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$i */
    /* compiled from: DetailV2 */
    public static final class i extends MessageNano {
        private static volatile i[] d;
        public String a;
        public String b;
        public float c;

        public static i[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new i[0];
                    }
                }
            }
            return d;
        }

        public i() {
            this.a = "";
            this.b = "";
            this.c = 0.0f;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (Float.floatToIntBits(this.c) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(3, this.c);
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
            return Float.floatToIntBits(this.c) != Float.floatToIntBits(0.0f) ? computeSerializedSize + CodedOutputByteBufferNano.computeFloatSize(3, this.c) : computeSerializedSize;
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
                } else if (readTag == 29) {
                    this.c = codedInputByteBufferNano.readFloat();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$j */
    /* compiled from: DetailV2 */
    public static final class j extends MessageNano {
        private static volatile j[] c;
        public String a;
        public String b;

        public static j[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new j[0];
                    }
                }
            }
            return c;
        }

        public j() {
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

    /* renamed from: a$k */
    /* compiled from: DetailV2 */
    public static final class k extends MessageNano {
        private static volatile k[] l;
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
        public az k;

        public static k[] a() {
            if (l == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (l == null) {
                        l = new k[0];
                    }
                }
            }
            return l;
        }

        public k() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = "";
            this.k = null;
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
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
            }
            if (!this.j.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.j);
            }
            if (this.k != null) {
                codedOutputByteBufferNano.writeMessage(11, this.k);
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
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
            }
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            return this.k != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(11, this.k) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
                        break;
                    case 90:
                        if (this.k == null) {
                            this.k = new az();
                        }
                        codedInputByteBufferNano.readMessage(this.k);
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

    /* renamed from: a$l */
    /* compiled from: DetailV2 */
    public static final class l extends MessageNano {
        private static volatile l[] t;
        public String a;
        public String b;
        public String c;
        public int d;
        public String e;
        public String f;
        public String g;
        public p[] h;
        public String i;
        public String j;
        public String k;
        public int l;
        public int m;
        public String n;
        public String o;
        public String p;
        public String q;
        public o[] r;
        public m[] s;

        public static l[] a() {
            if (t == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (t == null) {
                        t = new l[0];
                    }
                }
            }
            return t;
        }

        public l() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = 0;
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = p.a();
            this.i = "";
            this.j = "";
            this.k = "";
            this.l = 0;
            this.m = 0;
            this.n = "";
            this.o = "";
            this.p = "";
            this.q = "";
            this.r = o.a();
            this.s = m.a();
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
            if (this.d != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.d);
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
            if (this.h != null && this.h.length > 0) {
                for (p pVar : this.h) {
                    if (pVar != null) {
                        codedOutputByteBufferNano.writeMessage(8, pVar);
                    }
                }
            }
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
            }
            if (!this.j.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.j);
            }
            if (!this.k.equals("")) {
                codedOutputByteBufferNano.writeString(11, this.k);
            }
            if (this.l != 0) {
                codedOutputByteBufferNano.writeInt32(12, this.l);
            }
            if (this.m != 0) {
                codedOutputByteBufferNano.writeInt32(13, this.m);
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
            if (!this.q.equals("")) {
                codedOutputByteBufferNano.writeString(17, this.q);
            }
            if (this.r != null && this.r.length > 0) {
                for (o oVar : this.r) {
                    if (oVar != null) {
                        codedOutputByteBufferNano.writeMessage(18, oVar);
                    }
                }
            }
            if (this.s != null && this.s.length > 0) {
                for (m mVar : this.s) {
                    if (mVar != null) {
                        codedOutputByteBufferNano.writeMessage(19, mVar);
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
            if (this.d != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, this.d);
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
            if (this.h != null && this.h.length > 0) {
                int i2 = computeSerializedSize;
                for (p pVar : this.h) {
                    if (pVar != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(8, pVar);
                    }
                }
                computeSerializedSize = i2;
            }
            if (!this.i.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(9, this.i);
            }
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            if (!this.k.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(11, this.k);
            }
            if (this.l != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(12, this.l);
            }
            if (this.m != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(13, this.m);
            }
            if (!this.n.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(14, this.n);
            }
            if (!this.o.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(15, this.o);
            }
            if (!this.p.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(16, this.p);
            }
            if (!this.q.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(17, this.q);
            }
            if (this.r != null && this.r.length > 0) {
                int i3 = computeSerializedSize;
                for (o oVar : this.r) {
                    if (oVar != null) {
                        i3 += CodedOutputByteBufferNano.computeMessageSize(18, oVar);
                    }
                }
                computeSerializedSize = i3;
            }
            if (this.s != null && this.s.length > 0) {
                for (m mVar : this.s) {
                    if (mVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(19, mVar);
                    }
                }
            }
            return computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        this.b = codedInputByteBufferNano.readString();
                        break;
                    case 26:
                        this.c = codedInputByteBufferNano.readString();
                        break;
                    case 32:
                        this.d = codedInputByteBufferNano.readInt32();
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
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        int length = this.h == null ? 0 : this.h.length;
                        p[] pVarArr = new p[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.h, 0, pVarArr, 0, length);
                        }
                        while (length < pVarArr.length - 1) {
                            pVarArr[length] = new p();
                            codedInputByteBufferNano.readMessage(pVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        pVarArr[length] = new p();
                        codedInputByteBufferNano.readMessage(pVarArr[length]);
                        this.h = pVarArr;
                        break;
                    case 74:
                        this.i = codedInputByteBufferNano.readString();
                        break;
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
                        break;
                    case 90:
                        this.k = codedInputByteBufferNano.readString();
                        break;
                    case 96:
                        this.l = codedInputByteBufferNano.readInt32();
                        break;
                    case 104:
                        this.m = codedInputByteBufferNano.readInt32();
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
                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                        this.q = codedInputByteBufferNano.readString();
                        break;
                    case 146:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 146);
                        int length2 = this.r == null ? 0 : this.r.length;
                        o[] oVarArr = new o[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.r, 0, oVarArr, 0, length2);
                        }
                        while (length2 < oVarArr.length - 1) {
                            oVarArr[length2] = new o();
                            codedInputByteBufferNano.readMessage(oVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        oVarArr[length2] = new o();
                        codedInputByteBufferNano.readMessage(oVarArr[length2]);
                        this.r = oVarArr;
                        break;
                    case ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE /*154*/:
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE);
                        int length3 = this.s == null ? 0 : this.s.length;
                        m[] mVarArr = new m[(repeatedFieldArrayLength3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.s, 0, mVarArr, 0, length3);
                        }
                        while (length3 < mVarArr.length - 1) {
                            mVarArr[length3] = new m();
                            codedInputByteBufferNano.readMessage(mVarArr[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        mVarArr[length3] = new m();
                        codedInputByteBufferNano.readMessage(mVarArr[length3]);
                        this.s = mVarArr;
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

    /* renamed from: a$m */
    /* compiled from: DetailV2 */
    public static final class m extends MessageNano {
        private static volatile m[] j;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;

        public static m[] a() {
            if (j == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (j == null) {
                        j = new m[0];
                    }
                }
            }
            return j;
        }

        public m() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
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
            if (!this.i.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.i);
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
            return !this.i.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(9, this.i) : computeSerializedSize;
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
                    this.i = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$n */
    /* compiled from: DetailV2 */
    public static final class n extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public int f;
        public l[] g;

        public n() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = 0;
            this.g = l.a();
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
            if (this.f != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                for (l lVar : this.g) {
                    if (lVar != null) {
                        codedOutputByteBufferNano.writeMessage(7, lVar);
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
            if (this.f != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, this.f);
            }
            if (this.g != null && this.g.length > 0) {
                for (l lVar : this.g) {
                    if (lVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, lVar);
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
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 48) {
                    this.f = codedInputByteBufferNano.readInt32();
                } else if (readTag == 58) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    int length = this.g == null ? 0 : this.g.length;
                    l[] lVarArr = new l[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.g, 0, lVarArr, 0, length);
                    }
                    while (length < lVarArr.length - 1) {
                        lVarArr[length] = new l();
                        codedInputByteBufferNano.readMessage(lVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    lVarArr[length] = new l();
                    codedInputByteBufferNano.readMessage(lVarArr[length]);
                    this.g = lVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$o */
    /* compiled from: DetailV2 */
    public static final class o extends MessageNano {
        private static volatile o[] f;
        public String a;
        public float b;
        public String c;
        public String d;
        public String e;

        public static o[] a() {
            if (f == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (f == null) {
                        f = new o[0];
                    }
                }
            }
            return f;
        }

        public o() {
            this.a = "";
            this.b = 0.0f;
            this.c = "";
            this.d = "";
            this.e = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
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
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
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
            return !this.e.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(5, this.e) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.a = codedInputByteBufferNano.readString();
                } else if (readTag == 21) {
                    this.b = codedInputByteBufferNano.readFloat();
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

    /* renamed from: a$p */
    /* compiled from: DetailV2 */
    public static final class p extends MessageNano {
        private static volatile p[] g;
        public String a;
        public String b;
        public int c;
        public String d;
        public String e;
        public String f;

        public static p[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new p[0];
                    }
                }
            }
            return g;
        }

        public p() {
            this.a = "";
            this.b = "";
            this.c = 0;
            this.d = "";
            this.e = "";
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
            if (this.c != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.c);
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
            if (!this.d.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(4, this.d);
            }
            if (!this.e.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.e);
            }
            return !this.f.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(6, this.f) : computeSerializedSize;
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
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$q */
    /* compiled from: DetailV2 */
    public static final class q extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public Map<String, r> i;
        public String[] j;
        public String[] k;
        public String[] l;

        public q() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = null;
            this.j = WireFormatNano.EMPTY_STRING_ARRAY;
            this.k = WireFormatNano.EMPTY_STRING_ARRAY;
            this.l = WireFormatNano.EMPTY_STRING_ARRAY;
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
            if (this.i != null) {
                InternalNano.serializeMapField(codedOutputByteBufferNano, this.i, 9, 9, 11);
            }
            if (this.j != null && this.j.length > 0) {
                for (String str : this.j) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(10, str);
                    }
                }
            }
            if (this.k != null && this.k.length > 0) {
                for (String str2 : this.k) {
                    if (str2 != null) {
                        codedOutputByteBufferNano.writeString(11, str2);
                    }
                }
            }
            if (this.l != null && this.l.length > 0) {
                for (String str3 : this.l) {
                    if (str3 != null) {
                        codedOutputByteBufferNano.writeString(12, str3);
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
            if (this.i != null) {
                computeSerializedSize += InternalNano.computeMapFieldSize(this.i, 9, 9, 11);
            }
            if (this.j != null && this.j.length > 0) {
                int i2 = 0;
                int i3 = 0;
                for (String str : this.j) {
                    if (str != null) {
                        i3++;
                        i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i2 + (i3 * 1);
            }
            if (this.k != null && this.k.length > 0) {
                int i4 = 0;
                int i5 = 0;
                for (String str2 : this.k) {
                    if (str2 != null) {
                        i5++;
                        i4 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
                    }
                }
                computeSerializedSize = computeSerializedSize + i4 + (i5 * 1);
            }
            if (this.l == null || this.l.length <= 0) {
                return computeSerializedSize;
            }
            int i6 = 0;
            int i7 = 0;
            for (String str3 : this.l) {
                if (str3 != null) {
                    i7++;
                    i6 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
                }
            }
            return computeSerializedSize + i6 + (i7 * 1);
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            MapFactory mapFactory = MapFactories.getMapFactory();
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.a = codedInputByteBufferNano.readString();
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
                        this.i = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.i, mapFactory, 9, 11, new r(), 10, 18);
                        break;
                    case 82:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 82);
                        int length = this.j == null ? 0 : this.j.length;
                        String[] strArr = new String[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.j, 0, strArr, 0, length);
                        }
                        while (length < strArr.length - 1) {
                            strArr[length] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        strArr[length] = codedInputByteBufferNano.readString();
                        this.j = strArr;
                        break;
                    case 90:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 90);
                        int length2 = this.k == null ? 0 : this.k.length;
                        String[] strArr2 = new String[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.k, 0, strArr2, 0, length2);
                        }
                        while (length2 < strArr2.length - 1) {
                            strArr2[length2] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        strArr2[length2] = codedInputByteBufferNano.readString();
                        this.k = strArr2;
                        break;
                    case 98:
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 98);
                        int length3 = this.l == null ? 0 : this.l.length;
                        String[] strArr3 = new String[(repeatedFieldArrayLength3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.l, 0, strArr3, 0, length3);
                        }
                        while (length3 < strArr3.length - 1) {
                            strArr3[length3] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        strArr3[length3] = codedInputByteBufferNano.readString();
                        this.l = strArr3;
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

    /* renamed from: a$r */
    /* compiled from: DetailV2 */
    public static final class r extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public int e;
        public int f;
        public int g;

        public r() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = 0;
            this.f = 0;
            this.g = 0;
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
            if (this.e != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.e);
            }
            if (this.f != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.f);
            }
            if (this.g != 0) {
                codedOutputByteBufferNano.writeInt32(7, this.g);
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
            if (this.e != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, this.e);
            }
            if (this.f != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, this.f);
            }
            return this.g != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(7, this.g) : computeSerializedSize;
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
                } else if (readTag == 40) {
                    this.e = codedInputByteBufferNano.readInt32();
                } else if (readTag == 48) {
                    this.f = codedInputByteBufferNano.readInt32();
                } else if (readTag == 56) {
                    this.g = codedInputByteBufferNano.readInt32();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$s */
    /* compiled from: DetailV2 */
    public static final class s extends MessageNano {
        private static volatile s[] e;
        public String a;
        public String b;
        public String c;
        public String d;

        public static s[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new s[0];
                    }
                }
            }
            return e;
        }

        public s() {
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

    /* renamed from: a$t */
    /* compiled from: DetailV2 */
    public static final class t extends MessageNano {
        private static volatile t[] c;
        public String a;
        public String b;

        public static t[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new t[0];
                    }
                }
            }
            return c;
        }

        public t() {
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

    /* renamed from: a$u */
    /* compiled from: DetailV2 */
    public static final class u extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public v f;

        public u() {
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

        public static u a(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (u) MessageNano.mergeFrom(new u(), bArr);
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
                        this.f = new v();
                    }
                    codedInputByteBufferNano.readMessage(this.f);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$v */
    /* compiled from: DetailV2 */
    public static final class v extends MessageNano {
        public int a;
        public String b;
        public k[] c;

        public v() {
            this.a = 0;
            this.b = "";
            this.c = k.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (k kVar : this.c) {
                    if (kVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, kVar);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, this.a);
            }
            if (!this.b.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (k kVar : this.c) {
                    if (kVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, kVar);
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
                if (readTag == 8) {
                    this.a = codedInputByteBufferNano.readInt32();
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length = this.c == null ? 0 : this.c.length;
                    k[] kVarArr = new k[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, kVarArr, 0, length);
                    }
                    while (length < kVarArr.length - 1) {
                        kVarArr[length] = new k();
                        codedInputByteBufferNano.readMessage(kVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    kVarArr[length] = new k();
                    codedInputByteBufferNano.readMessage(kVarArr[length]);
                    this.c = kVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$w */
    /* compiled from: DetailV2 */
    public static final class w extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public bj[] g;

        public w() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = bj.a();
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
            if (this.g != null && this.g.length > 0) {
                for (bj bjVar : this.g) {
                    if (bjVar != null) {
                        codedOutputByteBufferNano.writeMessage(7, bjVar);
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
            if (this.g != null && this.g.length > 0) {
                for (bj bjVar : this.g) {
                    if (bjVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, bjVar);
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
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 58) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    int length = this.g == null ? 0 : this.g.length;
                    bj[] bjVarArr = new bj[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.g, 0, bjVarArr, 0, length);
                    }
                    while (length < bjVarArr.length - 1) {
                        bjVarArr[length] = new bj();
                        codedInputByteBufferNano.readMessage(bjVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bjVarArr[length] = new bj();
                    codedInputByteBufferNano.readMessage(bjVarArr[length]);
                    this.g = bjVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$x */
    /* compiled from: DetailV2 */
    public static final class x extends MessageNano {
        public int a;
        public int b;
        public String c;

        public x() {
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

    /* renamed from: a$y */
    /* compiled from: DetailV2 */
    public static final class y extends MessageNano {
        private static volatile y[] f;
        public String a;
        public String b;
        public String c;
        public int d;
        public z[] e;

        public static y[] a() {
            if (f == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (f == null) {
                        f = new y[0];
                    }
                }
            }
            return f;
        }

        public y() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = 0;
            this.e = z.a();
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
            if (this.d != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.d);
            }
            if (this.e != null && this.e.length > 0) {
                for (z zVar : this.e) {
                    if (zVar != null) {
                        codedOutputByteBufferNano.writeMessage(5, zVar);
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
            if (this.d != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, this.d);
            }
            if (this.e != null && this.e.length > 0) {
                for (z zVar : this.e) {
                    if (zVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, zVar);
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
                } else if (readTag == 32) {
                    this.d = codedInputByteBufferNano.readInt32();
                } else if (readTag == 42) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                    int length = this.e == null ? 0 : this.e.length;
                    z[] zVarArr = new z[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.e, 0, zVarArr, 0, length);
                    }
                    while (length < zVarArr.length - 1) {
                        zVarArr[length] = new z();
                        codedInputByteBufferNano.readMessage(zVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    zVarArr[length] = new z();
                    codedInputByteBufferNano.readMessage(zVarArr[length]);
                    this.e = zVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: a$z */
    /* compiled from: DetailV2 */
    public static final class z extends MessageNano {
        private static volatile z[] e;
        public String a;
        public String b;
        public String c;
        public int d;

        public static z[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new z[0];
                    }
                }
            }
            return e;
        }

        public z() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = 0;
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
            if (this.d != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.d);
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
            return this.d != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(4, this.d) : computeSerializedSize;
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
                } else if (readTag == 32) {
                    this.d = codedInputByteBufferNano.readInt32();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }
}
