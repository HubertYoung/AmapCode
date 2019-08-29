package defpackage;

import android.support.v4.media.TransportMediator;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.autonavi.indoor.constant.InnerMessageCode;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.ajx3.Ajx3DebugService;
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
import com.standardar.common.Util;
import java.io.IOException;
import java.util.Map;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* renamed from: c reason: default package */
/* compiled from: Poilite */
public interface c {

    /* renamed from: c$a */
    /* compiled from: Poilite */
    public static final class a extends MessageNano {
        public C0059c a;
        public d[] b;
        public bi c;
        public bf d;
        public aq e;
        public bv f;
        public i[] g;
        public ar h;

        public a() {
            this.a = null;
            this.b = d.a();
            this.c = null;
            this.d = null;
            this.e = null;
            this.f = null;
            this.g = i.a();
            this.h = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null) {
                codedOutputByteBufferNano.writeMessage(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (d dVar : this.b) {
                    if (dVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, dVar);
                    }
                }
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
            if (this.g != null && this.g.length > 0) {
                for (i iVar : this.g) {
                    if (iVar != null) {
                        codedOutputByteBufferNano.writeMessage(7, iVar);
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
            if (this.a != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                int i = computeSerializedSize;
                for (d dVar : this.b) {
                    if (dVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(2, dVar);
                    }
                }
                computeSerializedSize = i;
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
            if (this.g != null && this.g.length > 0) {
                for (i iVar : this.g) {
                    if (iVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, iVar);
                    }
                }
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
                    if (this.a == null) {
                        this.a = new C0059c();
                    }
                    codedInputByteBufferNano.readMessage(this.a);
                } else if (readTag == 18) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length = this.b == null ? 0 : this.b.length;
                    d[] dVarArr = new d[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, dVarArr, 0, length);
                    }
                    while (length < dVarArr.length - 1) {
                        dVarArr[length] = new d();
                        codedInputByteBufferNano.readMessage(dVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    dVarArr[length] = new d();
                    codedInputByteBufferNano.readMessage(dVarArr[length]);
                    this.b = dVarArr;
                } else if (readTag == 26) {
                    if (this.c == null) {
                        this.c = new bi();
                    }
                    codedInputByteBufferNano.readMessage(this.c);
                } else if (readTag == 34) {
                    if (this.d == null) {
                        this.d = new bf();
                    }
                    codedInputByteBufferNano.readMessage(this.d);
                } else if (readTag == 42) {
                    if (this.e == null) {
                        this.e = new aq();
                    }
                    codedInputByteBufferNano.readMessage(this.e);
                } else if (readTag == 50) {
                    if (this.f == null) {
                        this.f = new bv();
                    }
                    codedInputByteBufferNano.readMessage(this.f);
                } else if (readTag == 58) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                    int length2 = this.g == null ? 0 : this.g.length;
                    i[] iVarArr = new i[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.g, 0, iVarArr, 0, length2);
                    }
                    while (length2 < iVarArr.length - 1) {
                        iVarArr[length2] = new i();
                        codedInputByteBufferNano.readMessage(iVarArr[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    iVarArr[length2] = new i();
                    codedInputByteBufferNano.readMessage(iVarArr[length2]);
                    this.g = iVarArr;
                } else if (readTag == 66) {
                    if (this.h == null) {
                        this.h = new ar();
                    }
                    codedInputByteBufferNano.readMessage(this.h);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$aa */
    /* compiled from: Poilite */
    public static final class aa extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;

        public aa() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
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

    /* renamed from: c$ab */
    /* compiled from: Poilite */
    public static final class ab extends MessageNano {
        public bh[] a;

        public ab() {
            this.a = bh.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (bh bhVar : this.a) {
                    if (bhVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, bhVar);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                for (bh bhVar : this.a) {
                    if (bhVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, bhVar);
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
                    bh[] bhVarArr = new bh[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, bhVarArr, 0, length);
                    }
                    while (length < bhVarArr.length - 1) {
                        bhVarArr[length] = new bh();
                        codedInputByteBufferNano.readMessage(bhVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bhVarArr[length] = new bh();
                    codedInputByteBufferNano.readMessage(bhVarArr[length]);
                    this.a = bhVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$ac */
    /* compiled from: Poilite */
    public static final class ac extends MessageNano {
        private static volatile ac[] c;
        public int a;
        public String b;

        public static ac[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new ac[0];
                    }
                }
            }
            return c;
        }

        public ac() {
            this.a = 0;
            this.b = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, this.a);
            }
            return !this.b.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(2, this.b) : computeSerializedSize;
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$ad */
    /* compiled from: Poilite */
    public static final class ad extends MessageNano {
        private static volatile ad[] c;
        public String a;
        public String b;

        public static ad[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new ad[0];
                    }
                }
            }
            return c;
        }

        public ad() {
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

    /* renamed from: c$ae */
    /* compiled from: Poilite */
    public static final class ae extends MessageNano {
        private static volatile ae[] f;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;

        public static ae[] a() {
            if (f == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (f == null) {
                        f = new ae[0];
                    }
                }
            }
            return f;
        }

        public ae() {
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

    /* renamed from: c$af */
    /* compiled from: Poilite */
    public static final class af extends MessageNano {
        public String a;
        public String b;
        public ag[] c;
        public ah[] d;

        public af() {
            this.a = "";
            this.b = "";
            this.c = ag.a();
            this.d = ah.a();
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
                for (ag agVar : this.c) {
                    if (agVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, agVar);
                    }
                }
            }
            if (this.d != null && this.d.length > 0) {
                for (ah ahVar : this.d) {
                    if (ahVar != null) {
                        codedOutputByteBufferNano.writeMessage(4, ahVar);
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
                int i = computeSerializedSize;
                for (ag agVar : this.c) {
                    if (agVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(3, agVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (this.d != null && this.d.length > 0) {
                for (ah ahVar : this.d) {
                    if (ahVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, ahVar);
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
                    ag[] agVarArr = new ag[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, agVarArr, 0, length);
                    }
                    while (length < agVarArr.length - 1) {
                        agVarArr[length] = new ag();
                        codedInputByteBufferNano.readMessage(agVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    agVarArr[length] = new ag();
                    codedInputByteBufferNano.readMessage(agVarArr[length]);
                    this.c = agVarArr;
                } else if (readTag == 34) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                    int length2 = this.d == null ? 0 : this.d.length;
                    ah[] ahVarArr = new ah[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.d, 0, ahVarArr, 0, length2);
                    }
                    while (length2 < ahVarArr.length - 1) {
                        ahVarArr[length2] = new ah();
                        codedInputByteBufferNano.readMessage(ahVarArr[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    ahVarArr[length2] = new ah();
                    codedInputByteBufferNano.readMessage(ahVarArr[length2]);
                    this.d = ahVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$ag */
    /* compiled from: Poilite */
    public static final class ag extends MessageNano {
        private static volatile ag[] g;
        public String a;
        public int b;
        public String c;
        public String d;
        public String e;
        public bh[] f;

        public static ag[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new ag[0];
                    }
                }
            }
            return g;
        }

        public ag() {
            this.a = "";
            this.b = 0;
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = bh.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
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
            if (!this.e.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.e);
            }
            if (this.f != null && this.f.length > 0) {
                for (bh bhVar : this.f) {
                    if (bhVar != null) {
                        codedOutputByteBufferNano.writeMessage(6, bhVar);
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
                for (bh bhVar : this.f) {
                    if (bhVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, bhVar);
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
                    this.c = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    this.d = codedInputByteBufferNano.readString();
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                    int length = this.f == null ? 0 : this.f.length;
                    bh[] bhVarArr = new bh[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.f, 0, bhVarArr, 0, length);
                    }
                    while (length < bhVarArr.length - 1) {
                        bhVarArr[length] = new bh();
                        codedInputByteBufferNano.readMessage(bhVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bhVarArr[length] = new bh();
                    codedInputByteBufferNano.readMessage(bhVarArr[length]);
                    this.f = bhVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$ah */
    /* compiled from: Poilite */
    public static final class ah extends MessageNano {
        private static volatile ah[] d;
        public String a;
        public String b;
        public String c;

        public static ah[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new ah[0];
                    }
                }
            }
            return d;
        }

        public ah() {
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

    /* renamed from: c$ai */
    /* compiled from: Poilite */
    public static final class ai extends MessageNano {
        public String a;
        public String b;

        public ai() {
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

    /* renamed from: c$aj */
    /* compiled from: Poilite */
    public static final class aj extends MessageNano {
        private static volatile aj[] c;
        public String a;
        public String b;

        public static aj[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new aj[0];
                    }
                }
            }
            return c;
        }

        public aj() {
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

    /* renamed from: c$ak */
    /* compiled from: Poilite */
    public static final class ak extends MessageNano {
        public String a;
        public String b;

        public ak() {
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

    /* renamed from: c$al */
    /* compiled from: Poilite */
    public static final class al extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;

        public al() {
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

    /* renamed from: c$am */
    /* compiled from: Poilite */
    public static final class am extends MessageNano {
        private static volatile am[] d;
        public br[] a;
        public an[] b;
        public String c;

        public static am[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new am[0];
                    }
                }
            }
            return d;
        }

        public am() {
            this.a = br.a();
            this.b = an.a();
            this.c = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (br brVar : this.a) {
                    if (brVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, brVar);
                    }
                }
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
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                int i = computeSerializedSize;
                for (br brVar : this.a) {
                    if (brVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(1, brVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (this.b != null && this.b.length > 0) {
                for (an anVar : this.b) {
                    if (anVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, anVar);
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    int length = this.a == null ? 0 : this.a.length;
                    br[] brVarArr = new br[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, brVarArr, 0, length);
                    }
                    while (length < brVarArr.length - 1) {
                        brVarArr[length] = new br();
                        codedInputByteBufferNano.readMessage(brVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    brVarArr[length] = new br();
                    codedInputByteBufferNano.readMessage(brVarArr[length]);
                    this.a = brVarArr;
                } else if (readTag == 18) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length2 = this.b == null ? 0 : this.b.length;
                    an[] anVarArr = new an[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.b, 0, anVarArr, 0, length2);
                    }
                    while (length2 < anVarArr.length - 1) {
                        anVarArr[length2] = new an();
                        codedInputByteBufferNano.readMessage(anVarArr[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    anVarArr[length2] = new an();
                    codedInputByteBufferNano.readMessage(anVarArr[length2]);
                    this.b = anVarArr;
                } else if (readTag == 26) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$an */
    /* compiled from: Poilite */
    public static final class an extends MessageNano {
        private static volatile an[] g;
        public String a;
        public String b;
        public String c;
        public float d;
        public String e;
        public String f;

        public static an[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new an[0];
                    }
                }
            }
            return g;
        }

        public an() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = 0.0f;
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
            if (Float.floatToIntBits(this.d) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(4, this.d);
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
            if (Float.floatToIntBits(this.d) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(4, this.d);
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
                } else if (readTag == 37) {
                    this.d = codedInputByteBufferNano.readFloat();
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

    /* renamed from: c$ao */
    /* compiled from: Poilite */
    public static final class ao extends MessageNano {
        private static volatile ao[] h;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;

        public static ao[] a() {
            if (h == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (h == null) {
                        h = new ao[0];
                    }
                }
            }
            return h;
        }

        public ao() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
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

    /* renamed from: c$ap */
    /* compiled from: Poilite */
    public static final class ap extends MessageNano {
        public ao[] a;
        public ao[] b;
        public ao[] c;

        public ap() {
            this.a = ao.a();
            this.b = ao.a();
            this.c = ao.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (ao aoVar : this.a) {
                    if (aoVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, aoVar);
                    }
                }
            }
            if (this.b != null && this.b.length > 0) {
                for (ao aoVar2 : this.b) {
                    if (aoVar2 != null) {
                        codedOutputByteBufferNano.writeMessage(2, aoVar2);
                    }
                }
            }
            if (this.c != null && this.c.length > 0) {
                for (ao aoVar3 : this.c) {
                    if (aoVar3 != null) {
                        codedOutputByteBufferNano.writeMessage(3, aoVar3);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                int i = computeSerializedSize;
                for (ao aoVar : this.a) {
                    if (aoVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(1, aoVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (this.b != null && this.b.length > 0) {
                int i2 = computeSerializedSize;
                for (ao aoVar2 : this.b) {
                    if (aoVar2 != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(2, aoVar2);
                    }
                }
                computeSerializedSize = i2;
            }
            if (this.c != null && this.c.length > 0) {
                for (ao aoVar3 : this.c) {
                    if (aoVar3 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, aoVar3);
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
                    ao[] aoVarArr = new ao[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, aoVarArr, 0, length);
                    }
                    while (length < aoVarArr.length - 1) {
                        aoVarArr[length] = new ao();
                        codedInputByteBufferNano.readMessage(aoVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    aoVarArr[length] = new ao();
                    codedInputByteBufferNano.readMessage(aoVarArr[length]);
                    this.a = aoVarArr;
                } else if (readTag == 18) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length2 = this.b == null ? 0 : this.b.length;
                    ao[] aoVarArr2 = new ao[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.b, 0, aoVarArr2, 0, length2);
                    }
                    while (length2 < aoVarArr2.length - 1) {
                        aoVarArr2[length2] = new ao();
                        codedInputByteBufferNano.readMessage(aoVarArr2[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    aoVarArr2[length2] = new ao();
                    codedInputByteBufferNano.readMessage(aoVarArr2[length2]);
                    this.b = aoVarArr2;
                } else if (readTag == 26) {
                    int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length3 = this.c == null ? 0 : this.c.length;
                    ao[] aoVarArr3 = new ao[(repeatedFieldArrayLength3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.c, 0, aoVarArr3, 0, length3);
                    }
                    while (length3 < aoVarArr3.length - 1) {
                        aoVarArr3[length3] = new ao();
                        codedInputByteBufferNano.readMessage(aoVarArr3[length3]);
                        codedInputByteBufferNano.readTag();
                        length3++;
                    }
                    aoVarArr3[length3] = new ao();
                    codedInputByteBufferNano.readMessage(aoVarArr3[length3]);
                    this.c = aoVarArr3;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$aq */
    /* compiled from: Poilite */
    public static final class aq extends MessageNano {
        public String a;
        public String b;

        public aq() {
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

    /* renamed from: c$ar */
    /* compiled from: Poilite */
    public static final class ar extends MessageNano {
        public ax A;
        public ax B;
        public ax C;
        public ax D;
        public ax E;
        public av a;
        public String b;
        public String[] c;
        public au[] d;
        public as e;
        public ax f;
        public ax g;
        public ax h;
        public ax i;
        public ax j;
        public ax k;
        public ax l;
        public ax m;
        public ax n;
        public ax o;
        public ax p;
        public ax q;
        public ax r;
        public ax s;
        public ax t;
        public ax u;
        public ax v;
        public ax w;
        public ax x;
        public ax y;
        public ax z;

        public ar() {
            this.a = null;
            this.b = "";
            this.c = WireFormatNano.EMPTY_STRING_ARRAY;
            this.d = au.a();
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
                for (au auVar : this.d) {
                    if (auVar != null) {
                        codedOutputByteBufferNano.writeMessage(4, auVar);
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
                for (au auVar : this.d) {
                    if (auVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, auVar);
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
            return this.E != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(31, this.E) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        if (this.a == null) {
                            this.a = new av();
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
                        au[] auVarArr = new au[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.d, 0, auVarArr, 0, length2);
                        }
                        while (length2 < auVarArr.length - 1) {
                            auVarArr[length2] = new au();
                            codedInputByteBufferNano.readMessage(auVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        auVarArr[length2] = new au();
                        codedInputByteBufferNano.readMessage(auVarArr[length2]);
                        this.d = auVarArr;
                        break;
                    case 42:
                        if (this.e == null) {
                            this.e = new as();
                        }
                        codedInputByteBufferNano.readMessage(this.e);
                        break;
                    case 50:
                        if (this.f == null) {
                            this.f = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.f);
                        break;
                    case 58:
                        if (this.g == null) {
                            this.g = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.g);
                        break;
                    case 66:
                        if (this.h == null) {
                            this.h = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.h);
                        break;
                    case 74:
                        if (this.i == null) {
                            this.i = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.i);
                        break;
                    case 82:
                        if (this.j == null) {
                            this.j = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.j);
                        break;
                    case 90:
                        if (this.k == null) {
                            this.k = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.k);
                        break;
                    case 98:
                        if (this.l == null) {
                            this.l = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.l);
                        break;
                    case 106:
                        if (this.m == null) {
                            this.m = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.m);
                        break;
                    case 114:
                        if (this.n == null) {
                            this.n = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.n);
                        break;
                    case 122:
                        if (this.o == null) {
                            this.o = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.o);
                        break;
                    case 130:
                        if (this.p == null) {
                            this.p = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.p);
                        break;
                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                        if (this.q == null) {
                            this.q = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.q);
                        break;
                    case 146:
                        if (this.r == null) {
                            this.r = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.r);
                        break;
                    case ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE /*154*/:
                        if (this.s == null) {
                            this.s = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.s);
                        break;
                    case EndBill.UNKNOWN_END_ORDER_FAILED /*162*/:
                        if (this.t == null) {
                            this.t = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.t);
                        break;
                    case 170:
                        if (this.u == null) {
                            this.u = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.u);
                        break;
                    case 178:
                        if (this.v == null) {
                            this.v = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.v);
                        break;
                    case 186:
                        if (this.w == null) {
                            this.w = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.w);
                        break;
                    case 194:
                        if (this.x == null) {
                            this.x = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.x);
                        break;
                    case 202:
                        if (this.y == null) {
                            this.y = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.y);
                        break;
                    case 210:
                        if (this.z == null) {
                            this.z = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.z);
                        break;
                    case 218:
                        if (this.A == null) {
                            this.A = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.A);
                        break;
                    case 226:
                        if (this.B == null) {
                            this.B = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.B);
                        break;
                    case 234:
                        if (this.C == null) {
                            this.C = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.C);
                        break;
                    case FavoritesPointFragment.REQUEST_COMPNAY /*242*/:
                        if (this.D == null) {
                            this.D = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.D);
                        break;
                    case Callback.DEFAULT_SWIPE_ANIMATION_DURATION /*250*/:
                        if (this.E == null) {
                            this.E = new ax();
                        }
                        codedInputByteBufferNano.readMessage(this.E);
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

    /* renamed from: c$as */
    /* compiled from: Poilite */
    public static final class as extends MessageNano {
        public at[] a;

        public as() {
            this.a = at.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (at atVar : this.a) {
                    if (atVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, atVar);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                for (at atVar : this.a) {
                    if (atVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, atVar);
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
                    at[] atVarArr = new at[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, atVarArr, 0, length);
                    }
                    while (length < atVarArr.length - 1) {
                        atVarArr[length] = new at();
                        codedInputByteBufferNano.readMessage(atVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    atVarArr[length] = new at();
                    codedInputByteBufferNano.readMessage(atVarArr[length]);
                    this.a = atVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$at */
    /* compiled from: Poilite */
    public static final class at extends MessageNano {
        private static volatile at[] e;
        public String a;
        public String b;
        public String c;
        public String d;

        public static at[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new at[0];
                    }
                }
            }
            return e;
        }

        public at() {
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

    /* renamed from: c$au */
    /* compiled from: Poilite */
    public static final class au extends MessageNano {
        private static volatile au[] d;
        public String a;
        public String b;
        public au[] c;

        public static au[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new au[0];
                    }
                }
            }
            return d;
        }

        public au() {
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
                for (au auVar : this.c) {
                    if (auVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, auVar);
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
                for (au auVar : this.c) {
                    if (auVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, auVar);
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
                    au[] auVarArr = new au[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, auVarArr, 0, length);
                    }
                    while (length < auVarArr.length - 1) {
                        auVarArr[length] = new au();
                        codedInputByteBufferNano.readMessage(auVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    auVarArr[length] = new au();
                    codedInputByteBufferNano.readMessage(auVarArr[length]);
                    this.c = auVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$av */
    /* compiled from: Poilite */
    public static final class av extends MessageNano {
        public bc a;
        public bc[] b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public bb[] h;

        public av() {
            this.a = null;
            this.b = bc.a();
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = bb.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null) {
                codedOutputByteBufferNano.writeMessage(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (bc bcVar : this.b) {
                    if (bcVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, bcVar);
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
                for (bb bbVar : this.h) {
                    if (bbVar != null) {
                        codedOutputByteBufferNano.writeMessage(8, bbVar);
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
                for (bc bcVar : this.b) {
                    if (bcVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(2, bcVar);
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
                for (bb bbVar : this.h) {
                    if (bbVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, bbVar);
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
                        this.a = new bc();
                    }
                    codedInputByteBufferNano.readMessage(this.a);
                } else if (readTag == 18) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length = this.b == null ? 0 : this.b.length;
                    bc[] bcVarArr = new bc[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, bcVarArr, 0, length);
                    }
                    while (length < bcVarArr.length - 1) {
                        bcVarArr[length] = new bc();
                        codedInputByteBufferNano.readMessage(bcVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bcVarArr[length] = new bc();
                    codedInputByteBufferNano.readMessage(bcVarArr[length]);
                    this.b = bcVarArr;
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
                    bb[] bbVarArr = new bb[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.h, 0, bbVarArr, 0, length2);
                    }
                    while (length2 < bbVarArr.length - 1) {
                        bbVarArr[length2] = new bb();
                        codedInputByteBufferNano.readMessage(bbVarArr[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    bbVarArr[length2] = new bb();
                    codedInputByteBufferNano.readMessage(bbVarArr[length2]);
                    this.h = bbVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$aw */
    /* compiled from: Poilite */
    public static final class aw extends MessageNano {
        public String a;
        public Map<String, String> b;
        public Map<String, String> c;

        public aw() {
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

    /* renamed from: c$ax */
    /* compiled from: Poilite */
    public static final class ax extends MessageNano {
        public String a;
        public ay[] b;
        public Map<String, aw> c;

        public ax() {
            this.a = "";
            this.b = ay.a();
            this.c = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (ay ayVar : this.b) {
                    if (ayVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, ayVar);
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
                for (ay ayVar : this.b) {
                    if (ayVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, ayVar);
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
                    ay[] ayVarArr = new ay[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, ayVarArr, 0, length);
                    }
                    while (length < ayVarArr.length - 1) {
                        ayVarArr[length] = new ay();
                        codedInputByteBufferNano.readMessage(ayVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    ayVarArr[length] = new ay();
                    codedInputByteBufferNano.readMessage(ayVarArr[length]);
                    this.b = ayVarArr;
                } else if (readTag == 26) {
                    this.c = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.c, mapFactory, 9, 11, new aw(), 10, 18);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$ay */
    /* compiled from: Poilite */
    public static final class ay extends MessageNano {
        private static volatile ay[] H;
        public String A;
        public az[] B;
        public String C;
        public String D;
        public Map<String, aw> E;
        public Map<String, String> F;
        public String G;
        public String a;
        public String b;
        public String c;
        public String[] d;
        public ba[] e;
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
        public az[] q;
        public Map<String, String> r;
        public String s;
        public int t;
        public int u;
        public String v;
        public String w;
        public String x;
        public int y;
        public int z;

        public static ay[] a() {
            if (H == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (H == null) {
                        H = new ay[0];
                    }
                }
            }
            return H;
        }

        public ay() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = WireFormatNano.EMPTY_STRING_ARRAY;
            this.e = ba.a();
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
            this.q = az.a();
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
            this.B = az.a();
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
                for (ba baVar : this.e) {
                    if (baVar != null) {
                        codedOutputByteBufferNano.writeMessage(5, baVar);
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
                for (az azVar : this.q) {
                    if (azVar != null) {
                        codedOutputByteBufferNano.writeMessage(17, azVar);
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
                for (az azVar2 : this.B) {
                    if (azVar2 != null) {
                        codedOutputByteBufferNano.writeMessage(28, azVar2);
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
                for (ba baVar : this.e) {
                    if (baVar != null) {
                        i4 += CodedOutputByteBufferNano.computeMessageSize(5, baVar);
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
                for (az azVar : this.q) {
                    if (azVar != null) {
                        i5 += CodedOutputByteBufferNano.computeMessageSize(17, azVar);
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
                for (az azVar2 : this.B) {
                    if (azVar2 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(28, azVar2);
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
                        ba[] baVarArr = new ba[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.e, 0, baVarArr, 0, length2);
                        }
                        while (length2 < baVarArr.length - 1) {
                            baVarArr[length2] = new ba();
                            codedInputByteBufferNano.readMessage(baVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        baVarArr[length2] = new ba();
                        codedInputByteBufferNano.readMessage(baVarArr[length2]);
                        this.e = baVarArr;
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
                        az[] azVarArr = new az[(repeatedFieldArrayLength3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.q, 0, azVarArr, 0, length3);
                        }
                        while (length3 < azVarArr.length - 1) {
                            azVarArr[length3] = new az();
                            codedInputByteBufferNano.readMessage(azVarArr[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        azVarArr[length3] = new az();
                        codedInputByteBufferNano.readMessage(azVarArr[length3]);
                        this.q = azVarArr;
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
                        az[] azVarArr2 = new az[(repeatedFieldArrayLength4 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.B, 0, azVarArr2, 0, length4);
                        }
                        while (length4 < azVarArr2.length - 1) {
                            azVarArr2[length4] = new az();
                            codedInputByteBufferNano.readMessage(azVarArr2[length4]);
                            codedInputByteBufferNano.readTag();
                            length4++;
                        }
                        azVarArr2[length4] = new az();
                        codedInputByteBufferNano.readMessage(azVarArr2[length4]);
                        this.B = azVarArr2;
                        break;
                    case 234:
                        this.C = codedInputByteBufferNano.readString();
                        break;
                    case FavoritesPointFragment.REQUEST_COMPNAY /*242*/:
                        this.D = codedInputByteBufferNano.readString();
                        break;
                    case Callback.DEFAULT_SWIPE_ANIMATION_DURATION /*250*/:
                        this.E = InternalNano.mergeMapEntry(codedInputByteBufferNano, this.E, mapFactory, 9, 11, new aw(), 10, 18);
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

    /* renamed from: c$az */
    /* compiled from: Poilite */
    public static final class az extends MessageNano {
        private static volatile az[] g;
        public String a;
        public String b;
        public String c;
        public String d;
        public Map<String, String> e;
        public String f;

        public static az[] a() {
            if (g == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (g == null) {
                        g = new az[0];
                    }
                }
            }
            return g;
        }

        public az() {
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

    /* renamed from: c$b */
    /* compiled from: Poilite */
    public static final class b extends MessageNano {
        private static volatile b[] f;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;

        public static b[] a() {
            if (f == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (f == null) {
                        f = new b[0];
                    }
                }
            }
            return f;
        }

        public b() {
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

    /* renamed from: c$ba */
    /* compiled from: Poilite */
    public static final class ba extends MessageNano {
        private static volatile ba[] i;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;

        public static ba[] a() {
            if (i == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (i == null) {
                        i = new ba[0];
                    }
                }
            }
            return i;
        }

        public ba() {
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

    /* renamed from: c$bb */
    /* compiled from: Poilite */
    public static final class bb extends MessageNano {
        private static volatile bb[] c;
        public String a;
        public String b;

        public static bb[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new bb[0];
                    }
                }
            }
            return c;
        }

        public bb() {
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

    /* renamed from: c$bc */
    /* compiled from: Poilite */
    public static final class bc extends MessageNano {
        private static volatile bc[] d;
        public String a;
        public String b;
        public String c;

        public static bc[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new bc[0];
                    }
                }
            }
            return d;
        }

        public bc() {
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

    /* renamed from: c$bd */
    /* compiled from: Poilite */
    public static final class bd extends MessageNano {
        private static volatile bd[] k;
        public String a;
        public int b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public int h;
        public int i;
        public be j;

        public static bd[] a() {
            if (k == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (k == null) {
                        k = new bd[0];
                    }
                }
            }
            return k;
        }

        public bd() {
            this.a = "";
            this.b = 0;
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = 0;
            this.i = 0;
            this.j = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
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
            if (this.j != null) {
                codedOutputByteBufferNano.writeMessage(10, this.j);
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
            return this.j != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(10, this.j) : computeSerializedSize;
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
                    case 16:
                        this.b = codedInputByteBufferNano.readInt32();
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
                    case 82:
                        if (this.j == null) {
                            this.j = new be();
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

    /* renamed from: c$be */
    /* compiled from: Poilite */
    public static final class be extends MessageNano {
        public String a;
        public String b;
        public String c;

        public be() {
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

    /* renamed from: c$bf */
    /* compiled from: Poilite */
    public static final class bf extends MessageNano {
        public int a;
        public int b;
        public int c;
        public bg d;
        public bd[] e;

        public bf() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = null;
            this.e = bd.a();
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
            if (this.d != null) {
                codedOutputByteBufferNano.writeMessage(4, this.d);
            }
            if (this.e != null && this.e.length > 0) {
                for (bd bdVar : this.e) {
                    if (bdVar != null) {
                        codedOutputByteBufferNano.writeMessage(5, bdVar);
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
            if (this.b != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, this.b);
            }
            if (this.c != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, this.c);
            }
            if (this.d != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, this.d);
            }
            if (this.e != null && this.e.length > 0) {
                for (bd bdVar : this.e) {
                    if (bdVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, bdVar);
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
                    this.b = codedInputByteBufferNano.readInt32();
                } else if (readTag == 24) {
                    this.c = codedInputByteBufferNano.readInt32();
                } else if (readTag == 34) {
                    if (this.d == null) {
                        this.d = new bg();
                    }
                    codedInputByteBufferNano.readMessage(this.d);
                } else if (readTag == 42) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                    int length = this.e == null ? 0 : this.e.length;
                    bd[] bdVarArr = new bd[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.e, 0, bdVarArr, 0, length);
                    }
                    while (length < bdVarArr.length - 1) {
                        bdVarArr[length] = new bd();
                        codedInputByteBufferNano.readMessage(bdVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bdVarArr[length] = new bd();
                    codedInputByteBufferNano.readMessage(bdVarArr[length]);
                    this.e = bdVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bg */
    /* compiled from: Poilite */
    public static final class bg extends MessageNano {
        public String a;

        public bg() {
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

    /* renamed from: c$bh */
    /* compiled from: Poilite */
    public static final class bh extends MessageNano {
        private static volatile bh[] g;
        public String a;
        public String b;
        public int c;
        public String d;
        public String e;
        public String f;

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

    /* renamed from: c$bi */
    /* compiled from: Poilite */
    public static final class bi extends MessageNano {
        public bp[] a;
        public bo b;
        public String c;
        public String d;
        public int e;
        public int f;
        public String g;
        public String[] h;
        public int i;
        public String j;
        public bn[] k;
        public bq[] l;
        public bt m;
        public bu[] n;
        public bm[] o;
        public bk p;
        public int q;

        public bi() {
            this.a = bp.a();
            this.b = null;
            this.c = "";
            this.d = "";
            this.e = 0;
            this.f = 0;
            this.g = "";
            this.h = WireFormatNano.EMPTY_STRING_ARRAY;
            this.i = 0;
            this.j = "";
            this.k = bn.a();
            this.l = bq.a();
            this.m = null;
            this.n = bu.a();
            this.o = bm.a();
            this.p = null;
            this.q = 0;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (bp bpVar : this.a) {
                    if (bpVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, bpVar);
                    }
                }
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
            if (this.e != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.e);
            }
            if (this.f != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.f);
            }
            if (!this.g.equals("")) {
                codedOutputByteBufferNano.writeString(7, this.g);
            }
            if (this.h != null && this.h.length > 0) {
                for (String str : this.h) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(8, str);
                    }
                }
            }
            if (this.i != 0) {
                codedOutputByteBufferNano.writeInt32(9, this.i);
            }
            if (!this.j.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.j);
            }
            if (this.k != null && this.k.length > 0) {
                for (bn bnVar : this.k) {
                    if (bnVar != null) {
                        codedOutputByteBufferNano.writeMessage(11, bnVar);
                    }
                }
            }
            if (this.l != null && this.l.length > 0) {
                for (bq bqVar : this.l) {
                    if (bqVar != null) {
                        codedOutputByteBufferNano.writeMessage(12, bqVar);
                    }
                }
            }
            if (this.m != null) {
                codedOutputByteBufferNano.writeMessage(13, this.m);
            }
            if (this.n != null && this.n.length > 0) {
                for (bu buVar : this.n) {
                    if (buVar != null) {
                        codedOutputByteBufferNano.writeMessage(14, buVar);
                    }
                }
            }
            if (this.o != null && this.o.length > 0) {
                for (bm bmVar : this.o) {
                    if (bmVar != null) {
                        codedOutputByteBufferNano.writeMessage(15, bmVar);
                    }
                }
            }
            if (this.p != null) {
                codedOutputByteBufferNano.writeMessage(16, this.p);
            }
            if (this.q != 0) {
                codedOutputByteBufferNano.writeInt32(17, this.q);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                int i2 = computeSerializedSize;
                for (bp bpVar : this.a) {
                    if (bpVar != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(1, bpVar);
                    }
                }
                computeSerializedSize = i2;
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
            if (this.e != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, this.e);
            }
            if (this.f != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, this.f);
            }
            if (!this.g.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(7, this.g);
            }
            if (this.h != null && this.h.length > 0) {
                int i3 = 0;
                int i4 = 0;
                for (String str : this.h) {
                    if (str != null) {
                        i4++;
                        i3 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i3 + (i4 * 1);
            }
            if (this.i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, this.i);
            }
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            if (this.k != null && this.k.length > 0) {
                int i5 = computeSerializedSize;
                for (bn bnVar : this.k) {
                    if (bnVar != null) {
                        i5 += CodedOutputByteBufferNano.computeMessageSize(11, bnVar);
                    }
                }
                computeSerializedSize = i5;
            }
            if (this.l != null && this.l.length > 0) {
                int i6 = computeSerializedSize;
                for (bq bqVar : this.l) {
                    if (bqVar != null) {
                        i6 += CodedOutputByteBufferNano.computeMessageSize(12, bqVar);
                    }
                }
                computeSerializedSize = i6;
            }
            if (this.m != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(13, this.m);
            }
            if (this.n != null && this.n.length > 0) {
                int i7 = computeSerializedSize;
                for (bu buVar : this.n) {
                    if (buVar != null) {
                        i7 += CodedOutputByteBufferNano.computeMessageSize(14, buVar);
                    }
                }
                computeSerializedSize = i7;
            }
            if (this.o != null && this.o.length > 0) {
                for (bm bmVar : this.o) {
                    if (bmVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(15, bmVar);
                    }
                }
            }
            if (this.p != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(16, this.p);
            }
            return this.q != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(17, this.q) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                        int length = this.a == null ? 0 : this.a.length;
                        bp[] bpVarArr = new bp[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.a, 0, bpVarArr, 0, length);
                        }
                        while (length < bpVarArr.length - 1) {
                            bpVarArr[length] = new bp();
                            codedInputByteBufferNano.readMessage(bpVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        bpVarArr[length] = new bp();
                        codedInputByteBufferNano.readMessage(bpVarArr[length]);
                        this.a = bpVarArr;
                        break;
                    case 18:
                        if (this.b == null) {
                            this.b = new bo();
                        }
                        codedInputByteBufferNano.readMessage(this.b);
                        break;
                    case 26:
                        this.c = codedInputByteBufferNano.readString();
                        break;
                    case 34:
                        this.d = codedInputByteBufferNano.readString();
                        break;
                    case 40:
                        this.e = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        this.f = codedInputByteBufferNano.readInt32();
                        break;
                    case 58:
                        this.g = codedInputByteBufferNano.readString();
                        break;
                    case 66:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        int length2 = this.h == null ? 0 : this.h.length;
                        String[] strArr = new String[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.h, 0, strArr, 0, length2);
                        }
                        while (length2 < strArr.length - 1) {
                            strArr[length2] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        strArr[length2] = codedInputByteBufferNano.readString();
                        this.h = strArr;
                        break;
                    case 72:
                        this.i = codedInputByteBufferNano.readInt32();
                        break;
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
                        break;
                    case 90:
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 90);
                        int length3 = this.k == null ? 0 : this.k.length;
                        bn[] bnVarArr = new bn[(repeatedFieldArrayLength3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.k, 0, bnVarArr, 0, length3);
                        }
                        while (length3 < bnVarArr.length - 1) {
                            bnVarArr[length3] = new bn();
                            codedInputByteBufferNano.readMessage(bnVarArr[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        bnVarArr[length3] = new bn();
                        codedInputByteBufferNano.readMessage(bnVarArr[length3]);
                        this.k = bnVarArr;
                        break;
                    case 98:
                        int repeatedFieldArrayLength4 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 98);
                        int length4 = this.l == null ? 0 : this.l.length;
                        bq[] bqVarArr = new bq[(repeatedFieldArrayLength4 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.l, 0, bqVarArr, 0, length4);
                        }
                        while (length4 < bqVarArr.length - 1) {
                            bqVarArr[length4] = new bq();
                            codedInputByteBufferNano.readMessage(bqVarArr[length4]);
                            codedInputByteBufferNano.readTag();
                            length4++;
                        }
                        bqVarArr[length4] = new bq();
                        codedInputByteBufferNano.readMessage(bqVarArr[length4]);
                        this.l = bqVarArr;
                        break;
                    case 106:
                        if (this.m == null) {
                            this.m = new bt();
                        }
                        codedInputByteBufferNano.readMessage(this.m);
                        break;
                    case 114:
                        int repeatedFieldArrayLength5 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 114);
                        int length5 = this.n == null ? 0 : this.n.length;
                        bu[] buVarArr = new bu[(repeatedFieldArrayLength5 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.n, 0, buVarArr, 0, length5);
                        }
                        while (length5 < buVarArr.length - 1) {
                            buVarArr[length5] = new bu();
                            codedInputByteBufferNano.readMessage(buVarArr[length5]);
                            codedInputByteBufferNano.readTag();
                            length5++;
                        }
                        buVarArr[length5] = new bu();
                        codedInputByteBufferNano.readMessage(buVarArr[length5]);
                        this.n = buVarArr;
                        break;
                    case 122:
                        int repeatedFieldArrayLength6 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 122);
                        int length6 = this.o == null ? 0 : this.o.length;
                        bm[] bmVarArr = new bm[(repeatedFieldArrayLength6 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.o, 0, bmVarArr, 0, length6);
                        }
                        while (length6 < bmVarArr.length - 1) {
                            bmVarArr[length6] = new bm();
                            codedInputByteBufferNano.readMessage(bmVarArr[length6]);
                            codedInputByteBufferNano.readTag();
                            length6++;
                        }
                        bmVarArr[length6] = new bm();
                        codedInputByteBufferNano.readMessage(bmVarArr[length6]);
                        this.o = bmVarArr;
                        break;
                    case 130:
                        if (this.p == null) {
                            this.p = new bk();
                        }
                        codedInputByteBufferNano.readMessage(this.p);
                        break;
                    case 136:
                        this.q = codedInputByteBufferNano.readInt32();
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

    /* renamed from: c$bj */
    /* compiled from: Poilite */
    public static final class bj extends MessageNano {
        public String a;
        public String b;

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

    /* renamed from: c$bk */
    /* compiled from: Poilite */
    public static final class bk extends MessageNano {
        public int a;
        public String b;
        public bl c;

        public bk() {
            this.a = 0;
            this.b = "";
            this.c = null;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            if (this.c != null) {
                codedOutputByteBufferNano.writeMessage(3, this.c);
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
            return this.c != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(3, this.c) : computeSerializedSize;
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
                    if (this.c == null) {
                        this.c = new bl();
                    }
                    codedInputByteBufferNano.readMessage(this.c);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bl */
    /* compiled from: Poilite */
    public static final class bl extends MessageNano {
        public String a;
        public String b;
        public String c;

        public bl() {
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

    /* renamed from: c$bm */
    /* compiled from: Poilite */
    public static final class bm extends MessageNano {
        private static volatile bm[] e;
        public String a;
        public String b;
        public String c;
        public br[] d;

        public static bm[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new bm[0];
                    }
                }
            }
            return e;
        }

        public bm() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = br.a();
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
                for (br brVar : this.d) {
                    if (brVar != null) {
                        codedOutputByteBufferNano.writeMessage(4, brVar);
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
            if (this.d != null && this.d.length > 0) {
                for (br brVar : this.d) {
                    if (brVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, brVar);
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                    int length = this.d == null ? 0 : this.d.length;
                    br[] brVarArr = new br[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.d, 0, brVarArr, 0, length);
                    }
                    while (length < brVarArr.length - 1) {
                        brVarArr[length] = new br();
                        codedInputByteBufferNano.readMessage(brVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    brVarArr[length] = new br();
                    codedInputByteBufferNano.readMessage(brVarArr[length]);
                    this.d = brVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bn */
    /* compiled from: Poilite */
    public static final class bn extends MessageNano {
        private static volatile bn[] c;
        public String a;
        public bs[] b;

        public static bn[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new bn[0];
                    }
                }
            }
            return c;
        }

        public bn() {
            this.a = "";
            this.b = bs.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (bs bsVar : this.b) {
                    if (bsVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, bsVar);
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
                for (bs bsVar : this.b) {
                    if (bsVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, bsVar);
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
                    bs[] bsVarArr = new bs[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, bsVarArr, 0, length);
                    }
                    while (length < bsVarArr.length - 1) {
                        bsVarArr[length] = new bs();
                        codedInputByteBufferNano.readMessage(bsVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bsVarArr[length] = new bs();
                    codedInputByteBufferNano.readMessage(bsVarArr[length]);
                    this.b = bsVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bo */
    /* compiled from: Poilite */
    public static final class bo extends MessageNano {
        public int a;
        public int b;
        public int c;
        public int d;

        public bo() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = 0;
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
            if (this.d != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.d);
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
            return this.d != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(4, this.d) : computeSerializedSize;
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
                } else if (readTag == 32) {
                    this.d = codedInputByteBufferNano.readInt32();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bp */
    /* compiled from: Poilite */
    public static final class bp extends MessageNano {
        private static volatile bp[] h;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public int g;

        public static bp[] a() {
            if (h == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (h == null) {
                        h = new bp[0];
                    }
                }
            }
            return h;
        }

        public bp() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
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
            if (!this.e.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.e);
            }
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.f);
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
            if (!this.e.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.e);
            }
            if (!this.f.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(6, this.f);
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
                } else if (readTag == 42) {
                    this.e = codedInputByteBufferNano.readString();
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (readTag == 56) {
                    this.g = codedInputByteBufferNano.readInt32();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bq */
    /* compiled from: Poilite */
    public static final class bq extends MessageNano {
        private static volatile bq[] f;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;

        public static bq[] a() {
            if (f == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (f == null) {
                        f = new bq[0];
                    }
                }
            }
            return f;
        }

        public bq() {
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

    /* renamed from: c$br */
    /* compiled from: Poilite */
    public static final class br extends MessageNano {
        private static volatile br[] l;
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

        public static br[] a() {
            if (l == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (l == null) {
                        l = new br[0];
                    }
                }
            }
            return l;
        }

        public br() {
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
            return !this.k.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(11, this.k) : computeSerializedSize;
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

    /* renamed from: c$bs */
    /* compiled from: Poilite */
    public static final class bs extends MessageNano {
        private static volatile bs[] d;
        public String a;
        public String b;
        public String c;

        public static bs[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new bs[0];
                    }
                }
            }
            return d;
        }

        public bs() {
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

    /* renamed from: c$bt */
    /* compiled from: Poilite */
    public static final class bt extends MessageNano {
        public int a;
        public String b;
        public String c;
        public bj d;

        public bt() {
            this.a = 0;
            this.b = "";
            this.c = "";
            this.d = null;
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
            if (this.d != null) {
                codedOutputByteBufferNano.writeMessage(4, this.d);
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
            return this.d != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(4, this.d) : computeSerializedSize;
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
                } else if (readTag == 34) {
                    if (this.d == null) {
                        this.d = new bj();
                    }
                    codedInputByteBufferNano.readMessage(this.d);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bu */
    /* compiled from: Poilite */
    public static final class bu extends MessageNano {
        private static volatile bu[] c;
        public int a;
        public String b;

        public static bu[] a() {
            if (c == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (c == null) {
                        c = new bu[0];
                    }
                }
            }
            return c;
        }

        public bu() {
            this.a = 0;
            this.b = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.a);
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, this.a);
            }
            return !this.b.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(2, this.b) : computeSerializedSize;
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
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bv */
    /* compiled from: Poilite */
    public static final class bv extends MessageNano {
        public bx a;
        public ca b;
        public bw c;
        public bz d;

        public bv() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
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
            return this.d != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(4, this.d) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    if (this.a == null) {
                        this.a = new bx();
                    }
                    codedInputByteBufferNano.readMessage(this.a);
                } else if (readTag == 18) {
                    if (this.b == null) {
                        this.b = new ca();
                    }
                    codedInputByteBufferNano.readMessage(this.b);
                } else if (readTag == 26) {
                    if (this.c == null) {
                        this.c = new bw();
                    }
                    codedInputByteBufferNano.readMessage(this.c);
                } else if (readTag == 34) {
                    if (this.d == null) {
                        this.d = new bz();
                    }
                    codedInputByteBufferNano.readMessage(this.d);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bw */
    /* compiled from: Poilite */
    public static final class bw extends MessageNano {
        public cc a;

        public bw() {
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
                        this.a = new cc();
                    }
                    codedInputByteBufferNano.readMessage(this.a);
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$bx */
    /* compiled from: Poilite */
    public static final class bx extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public int f;
        public String g;
        public String h;

        public bx() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = 0;
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
            if (this.f != 0) {
                codedOutputByteBufferNano.writeInt32(6, this.f);
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
            if (this.f != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, this.f);
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
                } else if (readTag == 48) {
                    this.f = codedInputByteBufferNano.readInt32();
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

    /* renamed from: c$by */
    /* compiled from: Poilite */
    public static final class by extends MessageNano {
        private static volatile by[] m;
        public int a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public float h;
        public int i;
        public String j;
        public String k;
        public String l;

        public static by[] a() {
            if (m == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (m == null) {
                        m = new by[0];
                    }
                }
            }
            return m;
        }

        public by() {
            this.a = 0;
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = 0.0f;
            this.i = 0;
            this.j = "";
            this.k = "";
            this.l = "";
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
            if (Float.floatToIntBits(this.h) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(8, this.h);
            }
            if (this.i != 0) {
                codedOutputByteBufferNano.writeInt32(9, this.i);
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
            if (this.a != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, this.a);
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
            if (Float.floatToIntBits(this.h) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(8, this.h);
            }
            if (this.i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, this.i);
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
                    case 8:
                        this.a = codedInputByteBufferNano.readInt32();
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
                    case 69:
                        this.h = codedInputByteBufferNano.readFloat();
                        break;
                    case 72:
                        this.i = codedInputByteBufferNano.readInt32();
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

    /* renamed from: c$bz */
    /* compiled from: Poilite */
    public static final class bz extends MessageNano {
        public by a;
        public by b;
        public by[] c;
        public by[] d;
        public by[] e;

        public bz() {
            this.a = null;
            this.b = null;
            this.c = by.a();
            this.d = by.a();
            this.e = by.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null) {
                codedOutputByteBufferNano.writeMessage(1, this.a);
            }
            if (this.b != null) {
                codedOutputByteBufferNano.writeMessage(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                for (by byVar : this.c) {
                    if (byVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, byVar);
                    }
                }
            }
            if (this.d != null && this.d.length > 0) {
                for (by byVar2 : this.d) {
                    if (byVar2 != null) {
                        codedOutputByteBufferNano.writeMessage(4, byVar2);
                    }
                }
            }
            if (this.e != null && this.e.length > 0) {
                for (by byVar3 : this.e) {
                    if (byVar3 != null) {
                        codedOutputByteBufferNano.writeMessage(5, byVar3);
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
            if (this.b != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, this.b);
            }
            if (this.c != null && this.c.length > 0) {
                int i = computeSerializedSize;
                for (by byVar : this.c) {
                    if (byVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(3, byVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (this.d != null && this.d.length > 0) {
                int i2 = computeSerializedSize;
                for (by byVar2 : this.d) {
                    if (byVar2 != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(4, byVar2);
                    }
                }
                computeSerializedSize = i2;
            }
            if (this.e != null && this.e.length > 0) {
                for (by byVar3 : this.e) {
                    if (byVar3 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, byVar3);
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
                        this.a = new by();
                    }
                    codedInputByteBufferNano.readMessage(this.a);
                } else if (readTag == 18) {
                    if (this.b == null) {
                        this.b = new by();
                    }
                    codedInputByteBufferNano.readMessage(this.b);
                } else if (readTag == 26) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length = this.c == null ? 0 : this.c.length;
                    by[] byVarArr = new by[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, byVarArr, 0, length);
                    }
                    while (length < byVarArr.length - 1) {
                        byVarArr[length] = new by();
                        codedInputByteBufferNano.readMessage(byVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    byVarArr[length] = new by();
                    codedInputByteBufferNano.readMessage(byVarArr[length]);
                    this.c = byVarArr;
                } else if (readTag == 34) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                    int length2 = this.d == null ? 0 : this.d.length;
                    by[] byVarArr2 = new by[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.d, 0, byVarArr2, 0, length2);
                    }
                    while (length2 < byVarArr2.length - 1) {
                        byVarArr2[length2] = new by();
                        codedInputByteBufferNano.readMessage(byVarArr2[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    byVarArr2[length2] = new by();
                    codedInputByteBufferNano.readMessage(byVarArr2[length2]);
                    this.d = byVarArr2;
                } else if (readTag == 42) {
                    int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                    int length3 = this.e == null ? 0 : this.e.length;
                    by[] byVarArr3 = new by[(repeatedFieldArrayLength3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.e, 0, byVarArr3, 0, length3);
                    }
                    while (length3 < byVarArr3.length - 1) {
                        byVarArr3[length3] = new by();
                        codedInputByteBufferNano.readMessage(byVarArr3[length3]);
                        codedInputByteBufferNano.readTag();
                        length3++;
                    }
                    byVarArr3[length3] = new by();
                    codedInputByteBufferNano.readMessage(byVarArr3[length3]);
                    this.e = byVarArr3;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$c reason: collision with other inner class name */
    /* compiled from: Poilite */
    public static final class C0059c extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;
        public int j;
        public float k;
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

        public C0059c() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = 0;
            this.k = 0.0f;
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
            if (this.j != 0) {
                codedOutputByteBufferNano.writeInt32(10, this.j);
            }
            if (Float.floatToIntBits(this.k) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(11, this.k);
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
            if (this.j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(10, this.j);
            }
            if (Float.floatToIntBits(this.k) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(11, this.k);
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
            return !this.v.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(22, this.v) : computeSerializedSize;
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
                    case 80:
                        this.j = codedInputByteBufferNano.readInt32();
                        break;
                    case 93:
                        this.k = codedInputByteBufferNano.readFloat();
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

    /* renamed from: c$ca */
    /* compiled from: Poilite */
    public static final class ca extends MessageNano {
        public cb[] a;
        public String b;

        public ca() {
            this.a = cb.a();
            this.b = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (cb cbVar : this.a) {
                    if (cbVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, cbVar);
                    }
                }
            }
            if (!this.b.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.b);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                for (cb cbVar : this.a) {
                    if (cbVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, cbVar);
                    }
                }
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
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    int length = this.a == null ? 0 : this.a.length;
                    cb[] cbVarArr = new cb[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, cbVarArr, 0, length);
                    }
                    while (length < cbVarArr.length - 1) {
                        cbVarArr[length] = new cb();
                        codedInputByteBufferNano.readMessage(cbVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    cbVarArr[length] = new cb();
                    codedInputByteBufferNano.readMessage(cbVarArr[length]);
                    this.a = cbVarArr;
                } else if (readTag == 18) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$cb */
    /* compiled from: Poilite */
    public static final class cb extends MessageNano {
        private static volatile cb[] d;
        public String a;
        public String b;
        public String c;

        public static cb[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new cb[0];
                    }
                }
            }
            return d;
        }

        public cb() {
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

    /* renamed from: c$cc */
    /* compiled from: Poilite */
    public static final class cc extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public int f;
        public float g;
        public String[] h;
        public int i;
        public cd j;

        public cc() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = 0;
            this.g = 0.0f;
            this.h = WireFormatNano.EMPTY_STRING_ARRAY;
            this.i = 0;
            this.j = null;
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
            if (Float.floatToIntBits(this.g) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(7, this.g);
            }
            if (this.h != null && this.h.length > 0) {
                for (String str : this.h) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(8, str);
                    }
                }
            }
            if (this.i != 0) {
                codedOutputByteBufferNano.writeInt32(9, this.i);
            }
            if (this.j != null) {
                codedOutputByteBufferNano.writeMessage(10, this.j);
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
            if (Float.floatToIntBits(this.g) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(7, this.g);
            }
            if (this.h != null && this.h.length > 0) {
                int i2 = 0;
                int i3 = 0;
                for (String str : this.h) {
                    if (str != null) {
                        i3++;
                        i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i2 + (i3 * 1);
            }
            if (this.i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, this.i);
            }
            return this.j != null ? computeSerializedSize + CodedOutputByteBufferNano.computeMessageSize(10, this.j) : computeSerializedSize;
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
                    case 48:
                        this.f = codedInputByteBufferNano.readInt32();
                        break;
                    case 61:
                        this.g = codedInputByteBufferNano.readFloat();
                        break;
                    case 66:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        int length = this.h == null ? 0 : this.h.length;
                        String[] strArr = new String[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.h, 0, strArr, 0, length);
                        }
                        while (length < strArr.length - 1) {
                            strArr[length] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        strArr[length] = codedInputByteBufferNano.readString();
                        this.h = strArr;
                        break;
                    case 72:
                        this.i = codedInputByteBufferNano.readInt32();
                        break;
                    case 82:
                        if (this.j == null) {
                            this.j = new cd();
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

    /* renamed from: c$cd */
    /* compiled from: Poilite */
    public static final class cd extends MessageNano {
        public float a;
        public int b;
        public float c;
        public float d;
        public int e;
        public float f;
        public float g;
        public int h;
        public float i;

        public cd() {
            this.a = 0.0f;
            this.b = 0;
            this.c = 0.0f;
            this.d = 0.0f;
            this.e = 0;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 0;
            this.i = 0.0f;
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (Float.floatToIntBits(this.a) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(1, this.a);
            }
            if (this.b != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.b);
            }
            if (Float.floatToIntBits(this.c) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(3, this.c);
            }
            if (Float.floatToIntBits(this.d) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(4, this.d);
            }
            if (this.e != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.e);
            }
            if (Float.floatToIntBits(this.f) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(6, this.f);
            }
            if (Float.floatToIntBits(this.g) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(7, this.g);
            }
            if (this.h != 0) {
                codedOutputByteBufferNano.writeInt32(8, this.h);
            }
            if (Float.floatToIntBits(this.i) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(9, this.i);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (Float.floatToIntBits(this.a) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(1, this.a);
            }
            if (this.b != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, this.b);
            }
            if (Float.floatToIntBits(this.c) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(3, this.c);
            }
            if (Float.floatToIntBits(this.d) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(4, this.d);
            }
            if (this.e != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, this.e);
            }
            if (Float.floatToIntBits(this.f) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(6, this.f);
            }
            if (Float.floatToIntBits(this.g) != Float.floatToIntBits(0.0f)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeFloatSize(7, this.g);
            }
            if (this.h != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(8, this.h);
            }
            return Float.floatToIntBits(this.i) != Float.floatToIntBits(0.0f) ? computeSerializedSize + CodedOutputByteBufferNano.computeFloatSize(9, this.i) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 13) {
                    this.a = codedInputByteBufferNano.readFloat();
                } else if (readTag == 16) {
                    this.b = codedInputByteBufferNano.readInt32();
                } else if (readTag == 29) {
                    this.c = codedInputByteBufferNano.readFloat();
                } else if (readTag == 37) {
                    this.d = codedInputByteBufferNano.readFloat();
                } else if (readTag == 40) {
                    this.e = codedInputByteBufferNano.readInt32();
                } else if (readTag == 53) {
                    this.f = codedInputByteBufferNano.readFloat();
                } else if (readTag == 61) {
                    this.g = codedInputByteBufferNano.readFloat();
                } else if (readTag == 64) {
                    this.h = codedInputByteBufferNano.readInt32();
                } else if (readTag == 77) {
                    this.i = codedInputByteBufferNano.readFloat();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$ce */
    /* compiled from: Poilite */
    public static final class ce extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public a e;
        public String f;

        public ce() {
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
                codedOutputByteBufferNano.writeMessage(5, this.e);
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
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, this.e);
            }
            return !this.f.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(6, this.f) : computeSerializedSize;
        }

        public static ce a(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (ce) MessageNano.mergeFrom(new ce(), bArr);
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
                        this.e = new a();
                    }
                    codedInputByteBufferNano.readMessage(this.e);
                } else if (readTag == 50) {
                    this.f = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$d */
    /* compiled from: Poilite */
    public static final class d extends MessageNano {
        private static volatile d[] bV;
        public int A;
        public String B;
        public l[] C;
        public String D;
        public int E;
        public int F;
        public ad[] G;
        public String H;
        public String I;
        public g[] J;
        public h[] K;
        public String L;
        public String M;
        public String N;
        public String O;
        public String P;
        public String Q;
        public int R;
        public int S;
        public af T;
        public String U;
        public b[] V;
        public int W;
        public f X;
        public String Y;
        public int Z;
        public String a;
        public int aA;
        public ae[] aB;
        public s aC;
        public r aD;
        public String aE;
        public String aF;
        public String aG;
        public String aH;
        public String aI;
        public String aJ;
        public String aK;
        public String aL;
        public String aM;
        public String aN;
        public String aO;
        public String aP;
        public String aQ;
        public int aR;
        public String aS;
        public String aT;
        public bh[] aU;
        public String aV;
        public String aW;
        public String aX;
        public String aY;
        public String aZ;
        public int aa;
        public String ab;
        public String ac;
        public String ad;
        public String ae;
        public int af;
        public String[] ag;
        public String ah;
        public ab ai;
        public ab aj;
        public aa ak;
        public ac[] al;
        public String am;
        public String an;
        public String ao;
        public String ap;
        public String aq;
        public String ar;
        public String as;
        public String at;
        public String au;
        public String av;
        public int aw;
        public int ax;
        public int ay;
        public int az;
        public String b;
        public String bA;
        public String bB;
        public String bC;
        public String bD;
        public String bE;
        public int bF;
        public ap bG;
        public String[] bH;
        public String bI;
        public am[] bJ;
        public String bK;
        public String bL;
        public String bM;
        public String bN;
        public String bO;
        public String bP;
        public String bQ;
        public String bR;
        public String bS;
        public String bT;
        public String bU;
        public String ba;
        public String bb;
        public String bc;
        public String bd;
        public int be;
        public int bf;
        public String bg;
        public String bh;
        public String bi;
        public String bj;
        public y[] bk;
        public int bl;
        public String bm;
        public String bn;
        public String bo;
        public String bp;
        public String bq;
        public ai br;
        public aj[] bs;
        public al bt;
        public ak bu;
        public String bv;
        public String bw;
        public String bx;
        public String by;
        public String bz;
        public String c;
        public String d;
        public String e;
        public String[] f;
        public String g;
        public String h;
        public n[] i;
        public p j;
        public u k;
        public String l;
        public String m;
        public String n;
        public String o;
        public String p;
        public String q;
        public int r;
        public String s;
        public String t;
        public m[] u;
        public String v;
        public String w;
        public int x;
        public k y;
        public int z;

        public static d[] a() {
            if (bV == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (bV == null) {
                        bV = new d[0];
                    }
                }
            }
            return bV;
        }

        public d() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = WireFormatNano.EMPTY_STRING_ARRAY;
            this.g = "";
            this.h = "";
            this.i = n.a();
            this.j = null;
            this.k = null;
            this.l = "";
            this.m = "";
            this.n = "";
            this.o = "";
            this.p = "";
            this.q = "";
            this.r = 0;
            this.s = "";
            this.t = "";
            this.u = m.a();
            this.v = "";
            this.w = "";
            this.x = 0;
            this.y = null;
            this.z = 0;
            this.A = 0;
            this.B = "";
            this.C = l.a();
            this.D = "";
            this.E = 0;
            this.F = 0;
            this.G = ad.a();
            this.H = "";
            this.I = "";
            this.J = g.a();
            this.K = h.a();
            this.L = "";
            this.M = "";
            this.N = "";
            this.O = "";
            this.P = "";
            this.Q = "";
            this.R = 0;
            this.S = 0;
            this.T = null;
            this.U = "";
            this.V = b.a();
            this.W = 0;
            this.X = null;
            this.Y = "";
            this.Z = 0;
            this.aa = 0;
            this.ab = "";
            this.ac = "";
            this.ad = "";
            this.ae = "";
            this.af = 0;
            this.ag = WireFormatNano.EMPTY_STRING_ARRAY;
            this.ah = "";
            this.ai = null;
            this.aj = null;
            this.ak = null;
            this.al = ac.a();
            this.am = "";
            this.an = "";
            this.ao = "";
            this.ap = "";
            this.aq = "";
            this.ar = "";
            this.as = "";
            this.at = "";
            this.au = "";
            this.av = "";
            this.aw = 0;
            this.ax = 0;
            this.ay = 0;
            this.az = 0;
            this.aA = 0;
            this.aB = ae.a();
            this.aC = null;
            this.aD = null;
            this.aE = "";
            this.aF = "";
            this.aG = "";
            this.aH = "";
            this.aI = "";
            this.aJ = "";
            this.aK = "";
            this.aL = "";
            this.aM = "";
            this.aN = "";
            this.aO = "";
            this.aP = "";
            this.aQ = "";
            this.aR = 0;
            this.aS = "";
            this.aT = "";
            this.aU = bh.a();
            this.aV = "";
            this.aW = "";
            this.aX = "";
            this.aY = "";
            this.aZ = "";
            this.ba = "";
            this.bb = "";
            this.bc = "";
            this.bd = "";
            this.be = 0;
            this.bf = 0;
            this.bg = "";
            this.bh = "";
            this.bi = "";
            this.bj = "";
            this.bk = y.a();
            this.bl = 0;
            this.bm = "";
            this.bn = "";
            this.bo = "";
            this.bp = "";
            this.bq = "";
            this.br = null;
            this.bs = aj.a();
            this.bt = null;
            this.bu = null;
            this.bv = "";
            this.bw = "";
            this.bx = "";
            this.by = "";
            this.bz = "";
            this.bA = "";
            this.bB = "";
            this.bC = "";
            this.bD = "";
            this.bE = "";
            this.bF = 0;
            this.bG = null;
            this.bH = WireFormatNano.EMPTY_STRING_ARRAY;
            this.bI = "";
            this.bJ = am.a();
            this.bK = "";
            this.bL = "";
            this.bM = "";
            this.bN = "";
            this.bO = "";
            this.bP = "";
            this.bQ = "";
            this.bR = "";
            this.bS = "";
            this.bT = "";
            this.bU = "";
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
                for (String str : this.f) {
                    if (str != null) {
                        codedOutputByteBufferNano.writeString(6, str);
                    }
                }
            }
            if (!this.g.equals("")) {
                codedOutputByteBufferNano.writeString(7, this.g);
            }
            if (!this.h.equals("")) {
                codedOutputByteBufferNano.writeString(8, this.h);
            }
            if (this.i != null && this.i.length > 0) {
                for (n nVar : this.i) {
                    if (nVar != null) {
                        codedOutputByteBufferNano.writeMessage(9, nVar);
                    }
                }
            }
            if (this.j != null) {
                codedOutputByteBufferNano.writeMessage(10, this.j);
            }
            if (this.k != null) {
                codedOutputByteBufferNano.writeMessage(11, this.k);
            }
            if (!this.l.equals("")) {
                codedOutputByteBufferNano.writeString(13, this.l);
            }
            if (!this.m.equals("")) {
                codedOutputByteBufferNano.writeString(14, this.m);
            }
            if (!this.n.equals("")) {
                codedOutputByteBufferNano.writeString(15, this.n);
            }
            if (!this.o.equals("")) {
                codedOutputByteBufferNano.writeString(16, this.o);
            }
            if (!this.p.equals("")) {
                codedOutputByteBufferNano.writeString(17, this.p);
            }
            if (!this.q.equals("")) {
                codedOutputByteBufferNano.writeString(18, this.q);
            }
            if (this.r != 0) {
                codedOutputByteBufferNano.writeInt32(19, this.r);
            }
            if (!this.s.equals("")) {
                codedOutputByteBufferNano.writeString(20, this.s);
            }
            if (!this.t.equals("")) {
                codedOutputByteBufferNano.writeString(21, this.t);
            }
            if (this.u != null && this.u.length > 0) {
                for (m mVar : this.u) {
                    if (mVar != null) {
                        codedOutputByteBufferNano.writeMessage(22, mVar);
                    }
                }
            }
            if (!this.v.equals("")) {
                codedOutputByteBufferNano.writeString(23, this.v);
            }
            if (!this.w.equals("")) {
                codedOutputByteBufferNano.writeString(24, this.w);
            }
            if (this.x != 0) {
                codedOutputByteBufferNano.writeInt32(25, this.x);
            }
            if (this.y != null) {
                codedOutputByteBufferNano.writeMessage(26, this.y);
            }
            if (this.z != 0) {
                codedOutputByteBufferNano.writeInt32(27, this.z);
            }
            if (this.A != 0) {
                codedOutputByteBufferNano.writeInt32(28, this.A);
            }
            if (!this.B.equals("")) {
                codedOutputByteBufferNano.writeString(29, this.B);
            }
            if (this.C != null && this.C.length > 0) {
                for (l lVar : this.C) {
                    if (lVar != null) {
                        codedOutputByteBufferNano.writeMessage(30, lVar);
                    }
                }
            }
            if (!this.D.equals("")) {
                codedOutputByteBufferNano.writeString(31, this.D);
            }
            if (this.E != 0) {
                codedOutputByteBufferNano.writeInt32(32, this.E);
            }
            if (this.F != 0) {
                codedOutputByteBufferNano.writeInt32(33, this.F);
            }
            if (this.G != null && this.G.length > 0) {
                for (ad adVar : this.G) {
                    if (adVar != null) {
                        codedOutputByteBufferNano.writeMessage(34, adVar);
                    }
                }
            }
            if (!this.H.equals("")) {
                codedOutputByteBufferNano.writeString(35, this.H);
            }
            if (!this.I.equals("")) {
                codedOutputByteBufferNano.writeString(36, this.I);
            }
            if (this.J != null && this.J.length > 0) {
                for (g gVar : this.J) {
                    if (gVar != null) {
                        codedOutputByteBufferNano.writeMessage(37, gVar);
                    }
                }
            }
            if (this.K != null && this.K.length > 0) {
                for (h hVar : this.K) {
                    if (hVar != null) {
                        codedOutputByteBufferNano.writeMessage(38, hVar);
                    }
                }
            }
            if (!this.L.equals("")) {
                codedOutputByteBufferNano.writeString(39, this.L);
            }
            if (!this.M.equals("")) {
                codedOutputByteBufferNano.writeString(40, this.M);
            }
            if (!this.N.equals("")) {
                codedOutputByteBufferNano.writeString(41, this.N);
            }
            if (!this.O.equals("")) {
                codedOutputByteBufferNano.writeString(42, this.O);
            }
            if (!this.P.equals("")) {
                codedOutputByteBufferNano.writeString(43, this.P);
            }
            if (!this.Q.equals("")) {
                codedOutputByteBufferNano.writeString(44, this.Q);
            }
            if (this.R != 0) {
                codedOutputByteBufferNano.writeInt32(45, this.R);
            }
            if (this.S != 0) {
                codedOutputByteBufferNano.writeInt32(46, this.S);
            }
            if (this.T != null) {
                codedOutputByteBufferNano.writeMessage(47, this.T);
            }
            if (!this.U.equals("")) {
                codedOutputByteBufferNano.writeString(48, this.U);
            }
            if (this.V != null && this.V.length > 0) {
                for (b bVar : this.V) {
                    if (bVar != null) {
                        codedOutputByteBufferNano.writeMessage(49, bVar);
                    }
                }
            }
            if (this.W != 0) {
                codedOutputByteBufferNano.writeInt32(50, this.W);
            }
            if (this.X != null) {
                codedOutputByteBufferNano.writeMessage(51, this.X);
            }
            if (!this.Y.equals("")) {
                codedOutputByteBufferNano.writeString(52, this.Y);
            }
            if (this.Z != 0) {
                codedOutputByteBufferNano.writeInt32(53, this.Z);
            }
            if (this.aa != 0) {
                codedOutputByteBufferNano.writeInt32(54, this.aa);
            }
            if (!this.ab.equals("")) {
                codedOutputByteBufferNano.writeString(55, this.ab);
            }
            if (!this.ac.equals("")) {
                codedOutputByteBufferNano.writeString(58, this.ac);
            }
            if (!this.ad.equals("")) {
                codedOutputByteBufferNano.writeString(60, this.ad);
            }
            if (!this.ae.equals("")) {
                codedOutputByteBufferNano.writeString(61, this.ae);
            }
            if (this.af != 0) {
                codedOutputByteBufferNano.writeInt32(62, this.af);
            }
            if (this.ag != null && this.ag.length > 0) {
                for (String str2 : this.ag) {
                    if (str2 != null) {
                        codedOutputByteBufferNano.writeString(63, str2);
                    }
                }
            }
            if (!this.ah.equals("")) {
                codedOutputByteBufferNano.writeString(64, this.ah);
            }
            if (this.ai != null) {
                codedOutputByteBufferNano.writeMessage(65, this.ai);
            }
            if (this.aj != null) {
                codedOutputByteBufferNano.writeMessage(66, this.aj);
            }
            if (this.ak != null) {
                codedOutputByteBufferNano.writeMessage(67, this.ak);
            }
            if (this.al != null && this.al.length > 0) {
                for (ac acVar : this.al) {
                    if (acVar != null) {
                        codedOutputByteBufferNano.writeMessage(68, acVar);
                    }
                }
            }
            if (!this.am.equals("")) {
                codedOutputByteBufferNano.writeString(69, this.am);
            }
            if (!this.an.equals("")) {
                codedOutputByteBufferNano.writeString(70, this.an);
            }
            if (!this.ao.equals("")) {
                codedOutputByteBufferNano.writeString(71, this.ao);
            }
            if (!this.ap.equals("")) {
                codedOutputByteBufferNano.writeString(72, this.ap);
            }
            if (!this.aq.equals("")) {
                codedOutputByteBufferNano.writeString(73, this.aq);
            }
            if (!this.ar.equals("")) {
                codedOutputByteBufferNano.writeString(74, this.ar);
            }
            if (!this.as.equals("")) {
                codedOutputByteBufferNano.writeString(75, this.as);
            }
            if (!this.at.equals("")) {
                codedOutputByteBufferNano.writeString(76, this.at);
            }
            if (!this.au.equals("")) {
                codedOutputByteBufferNano.writeString(77, this.au);
            }
            if (!this.av.equals("")) {
                codedOutputByteBufferNano.writeString(78, this.av);
            }
            if (this.aw != 0) {
                codedOutputByteBufferNano.writeInt32(79, this.aw);
            }
            if (this.ax != 0) {
                codedOutputByteBufferNano.writeInt32(80, this.ax);
            }
            if (this.ay != 0) {
                codedOutputByteBufferNano.writeInt32(81, this.ay);
            }
            if (this.az != 0) {
                codedOutputByteBufferNano.writeInt32(82, this.az);
            }
            if (this.aA != 0) {
                codedOutputByteBufferNano.writeInt32(83, this.aA);
            }
            if (this.aB != null && this.aB.length > 0) {
                for (ae aeVar : this.aB) {
                    if (aeVar != null) {
                        codedOutputByteBufferNano.writeMessage(84, aeVar);
                    }
                }
            }
            if (this.aC != null) {
                codedOutputByteBufferNano.writeMessage(85, this.aC);
            }
            if (this.aD != null) {
                codedOutputByteBufferNano.writeMessage(86, this.aD);
            }
            if (!this.aE.equals("")) {
                codedOutputByteBufferNano.writeString(87, this.aE);
            }
            if (!this.aF.equals("")) {
                codedOutputByteBufferNano.writeString(88, this.aF);
            }
            if (!this.aG.equals("")) {
                codedOutputByteBufferNano.writeString(89, this.aG);
            }
            if (!this.aH.equals("")) {
                codedOutputByteBufferNano.writeString(90, this.aH);
            }
            if (!this.aI.equals("")) {
                codedOutputByteBufferNano.writeString(91, this.aI);
            }
            if (!this.aJ.equals("")) {
                codedOutputByteBufferNano.writeString(92, this.aJ);
            }
            if (!this.aK.equals("")) {
                codedOutputByteBufferNano.writeString(93, this.aK);
            }
            if (!this.aL.equals("")) {
                codedOutputByteBufferNano.writeString(94, this.aL);
            }
            if (!this.aM.equals("")) {
                codedOutputByteBufferNano.writeString(95, this.aM);
            }
            if (!this.aN.equals("")) {
                codedOutputByteBufferNano.writeString(96, this.aN);
            }
            if (!this.aO.equals("")) {
                codedOutputByteBufferNano.writeString(97, this.aO);
            }
            if (!this.aP.equals("")) {
                codedOutputByteBufferNano.writeString(98, this.aP);
            }
            if (!this.aQ.equals("")) {
                codedOutputByteBufferNano.writeString(99, this.aQ);
            }
            if (this.aR != 0) {
                codedOutputByteBufferNano.writeInt32(100, this.aR);
            }
            if (!this.aS.equals("")) {
                codedOutputByteBufferNano.writeString(101, this.aS);
            }
            if (!this.aT.equals("")) {
                codedOutputByteBufferNano.writeString(102, this.aT);
            }
            if (this.aU != null && this.aU.length > 0) {
                for (bh bhVar : this.aU) {
                    if (bhVar != null) {
                        codedOutputByteBufferNano.writeMessage(103, bhVar);
                    }
                }
            }
            if (!this.aV.equals("")) {
                codedOutputByteBufferNano.writeString(104, this.aV);
            }
            if (!this.aW.equals("")) {
                codedOutputByteBufferNano.writeString(105, this.aW);
            }
            if (!this.aX.equals("")) {
                codedOutputByteBufferNano.writeString(106, this.aX);
            }
            if (!this.aY.equals("")) {
                codedOutputByteBufferNano.writeString(107, this.aY);
            }
            if (!this.aZ.equals("")) {
                codedOutputByteBufferNano.writeString(108, this.aZ);
            }
            if (!this.ba.equals("")) {
                codedOutputByteBufferNano.writeString(109, this.ba);
            }
            if (!this.bb.equals("")) {
                codedOutputByteBufferNano.writeString(110, this.bb);
            }
            if (!this.bc.equals("")) {
                codedOutputByteBufferNano.writeString(111, this.bc);
            }
            if (!this.bd.equals("")) {
                codedOutputByteBufferNano.writeString(112, this.bd);
            }
            if (this.be != 0) {
                codedOutputByteBufferNano.writeInt32(113, this.be);
            }
            if (this.bf != 0) {
                codedOutputByteBufferNano.writeInt32(114, this.bf);
            }
            if (!this.bg.equals("")) {
                codedOutputByteBufferNano.writeString(115, this.bg);
            }
            if (!this.bh.equals("")) {
                codedOutputByteBufferNano.writeString(116, this.bh);
            }
            if (!this.bi.equals("")) {
                codedOutputByteBufferNano.writeString(117, this.bi);
            }
            if (!this.bj.equals("")) {
                codedOutputByteBufferNano.writeString(118, this.bj);
            }
            if (this.bk != null && this.bk.length > 0) {
                for (y yVar : this.bk) {
                    if (yVar != null) {
                        codedOutputByteBufferNano.writeMessage(119, yVar);
                    }
                }
            }
            if (this.bl != 0) {
                codedOutputByteBufferNano.writeInt32(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, this.bl);
            }
            if (!this.bm.equals("")) {
                codedOutputByteBufferNano.writeString(121, this.bm);
            }
            if (!this.bn.equals("")) {
                codedOutputByteBufferNano.writeString(122, this.bn);
            }
            if (!this.bo.equals("")) {
                codedOutputByteBufferNano.writeString(123, this.bo);
            }
            if (!this.bp.equals("")) {
                codedOutputByteBufferNano.writeString(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA, this.bp);
            }
            if (!this.bq.equals("")) {
                codedOutputByteBufferNano.writeString(125, this.bq);
            }
            if (this.br != null) {
                codedOutputByteBufferNano.writeMessage(TransportMediator.KEYCODE_MEDIA_PLAY, this.br);
            }
            if (this.bs != null && this.bs.length > 0) {
                for (aj ajVar : this.bs) {
                    if (ajVar != null) {
                        codedOutputByteBufferNano.writeMessage(127, ajVar);
                    }
                }
            }
            if (this.bt != null) {
                codedOutputByteBufferNano.writeMessage(128, this.bt);
            }
            if (this.bu != null) {
                codedOutputByteBufferNano.writeMessage(129, this.bu);
            }
            if (!this.bv.equals("")) {
                codedOutputByteBufferNano.writeString(130, this.bv);
            }
            if (!this.bw.equals("")) {
                codedOutputByteBufferNano.writeString(131, this.bw);
            }
            if (!this.bx.equals("")) {
                codedOutputByteBufferNano.writeString(132, this.bx);
            }
            if (!this.by.equals("")) {
                codedOutputByteBufferNano.writeString(133, this.by);
            }
            if (!this.bz.equals("")) {
                codedOutputByteBufferNano.writeString(134, this.bz);
            }
            if (!this.bA.equals("")) {
                codedOutputByteBufferNano.writeString(135, this.bA);
            }
            if (!this.bB.equals("")) {
                codedOutputByteBufferNano.writeString(136, this.bB);
            }
            if (!this.bC.equals("")) {
                codedOutputByteBufferNano.writeString(DumpSegment.ANDROID_ROOT_INTERNED_STRING, this.bC);
            }
            if (!this.bD.equals("")) {
                codedOutputByteBufferNano.writeString(DumpSegment.ANDROID_ROOT_FINALIZING, this.bD);
            }
            if (!this.bE.equals("")) {
                codedOutputByteBufferNano.writeString(DumpSegment.ANDROID_ROOT_DEBUGGER, this.bE);
            }
            if (this.bF != 0) {
                codedOutputByteBufferNano.writeInt32(140, this.bF);
            }
            if (this.bG != null) {
                codedOutputByteBufferNano.writeMessage(DumpSegment.ANDROID_ROOT_VM_INTERNAL, this.bG);
            }
            if (this.bH != null && this.bH.length > 0) {
                for (String str3 : this.bH) {
                    if (str3 != null) {
                        codedOutputByteBufferNano.writeString(DumpSegment.ANDROID_ROOT_JNI_MONITOR, str3);
                    }
                }
            }
            if (!this.bI.equals("")) {
                codedOutputByteBufferNano.writeString(143, this.bI);
            }
            if (this.bJ != null && this.bJ.length > 0) {
                for (am amVar : this.bJ) {
                    if (amVar != null) {
                        codedOutputByteBufferNano.writeMessage(144, amVar);
                    }
                }
            }
            if (!this.bK.equals("")) {
                codedOutputByteBufferNano.writeString(145, this.bK);
            }
            if (!this.bL.equals("")) {
                codedOutputByteBufferNano.writeString(146, this.bL);
            }
            if (!this.bM.equals("")) {
                codedOutputByteBufferNano.writeString(147, this.bM);
            }
            if (!this.bN.equals("")) {
                codedOutputByteBufferNano.writeString(148, this.bN);
            }
            if (!this.bO.equals("")) {
                codedOutputByteBufferNano.writeString(149, this.bO);
            }
            if (!this.bP.equals("")) {
                codedOutputByteBufferNano.writeString(150, this.bP);
            }
            if (!this.bQ.equals("")) {
                codedOutputByteBufferNano.writeString(151, this.bQ);
            }
            if (!this.bR.equals("")) {
                codedOutputByteBufferNano.writeString(152, this.bR);
            }
            if (!this.bS.equals("")) {
                codedOutputByteBufferNano.writeString(153, this.bS);
            }
            if (!this.bT.equals("")) {
                codedOutputByteBufferNano.writeString(ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE, this.bT);
            }
            if (!this.bU.equals("")) {
                codedOutputByteBufferNano.writeString(155, this.bU);
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
                int i2 = 0;
                int i3 = 0;
                for (String str : this.f) {
                    if (str != null) {
                        i3++;
                        i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                    }
                }
                computeSerializedSize = computeSerializedSize + i2 + (i3 * 1);
            }
            if (!this.g.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(7, this.g);
            }
            if (!this.h.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(8, this.h);
            }
            if (this.i != null && this.i.length > 0) {
                int i4 = computeSerializedSize;
                for (n nVar : this.i) {
                    if (nVar != null) {
                        i4 += CodedOutputByteBufferNano.computeMessageSize(9, nVar);
                    }
                }
                computeSerializedSize = i4;
            }
            if (this.j != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(10, this.j);
            }
            if (this.k != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(11, this.k);
            }
            if (!this.l.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(13, this.l);
            }
            if (!this.m.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(14, this.m);
            }
            if (!this.n.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(15, this.n);
            }
            if (!this.o.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(16, this.o);
            }
            if (!this.p.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(17, this.p);
            }
            if (!this.q.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(18, this.q);
            }
            if (this.r != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(19, this.r);
            }
            if (!this.s.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(20, this.s);
            }
            if (!this.t.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(21, this.t);
            }
            if (this.u != null && this.u.length > 0) {
                int i5 = computeSerializedSize;
                for (m mVar : this.u) {
                    if (mVar != null) {
                        i5 += CodedOutputByteBufferNano.computeMessageSize(22, mVar);
                    }
                }
                computeSerializedSize = i5;
            }
            if (!this.v.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(23, this.v);
            }
            if (!this.w.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(24, this.w);
            }
            if (this.x != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(25, this.x);
            }
            if (this.y != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(26, this.y);
            }
            if (this.z != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(27, this.z);
            }
            if (this.A != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(28, this.A);
            }
            if (!this.B.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(29, this.B);
            }
            if (this.C != null && this.C.length > 0) {
                int i6 = computeSerializedSize;
                for (l lVar : this.C) {
                    if (lVar != null) {
                        i6 += CodedOutputByteBufferNano.computeMessageSize(30, lVar);
                    }
                }
                computeSerializedSize = i6;
            }
            if (!this.D.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(31, this.D);
            }
            if (this.E != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(32, this.E);
            }
            if (this.F != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(33, this.F);
            }
            if (this.G != null && this.G.length > 0) {
                int i7 = computeSerializedSize;
                for (ad adVar : this.G) {
                    if (adVar != null) {
                        i7 += CodedOutputByteBufferNano.computeMessageSize(34, adVar);
                    }
                }
                computeSerializedSize = i7;
            }
            if (!this.H.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(35, this.H);
            }
            if (!this.I.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(36, this.I);
            }
            if (this.J != null && this.J.length > 0) {
                int i8 = computeSerializedSize;
                for (g gVar : this.J) {
                    if (gVar != null) {
                        i8 += CodedOutputByteBufferNano.computeMessageSize(37, gVar);
                    }
                }
                computeSerializedSize = i8;
            }
            if (this.K != null && this.K.length > 0) {
                int i9 = computeSerializedSize;
                for (h hVar : this.K) {
                    if (hVar != null) {
                        i9 += CodedOutputByteBufferNano.computeMessageSize(38, hVar);
                    }
                }
                computeSerializedSize = i9;
            }
            if (!this.L.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(39, this.L);
            }
            if (!this.M.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(40, this.M);
            }
            if (!this.N.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(41, this.N);
            }
            if (!this.O.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(42, this.O);
            }
            if (!this.P.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(43, this.P);
            }
            if (!this.Q.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(44, this.Q);
            }
            if (this.R != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(45, this.R);
            }
            if (this.S != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(46, this.S);
            }
            if (this.T != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(47, this.T);
            }
            if (!this.U.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(48, this.U);
            }
            if (this.V != null && this.V.length > 0) {
                int i10 = computeSerializedSize;
                for (b bVar : this.V) {
                    if (bVar != null) {
                        i10 += CodedOutputByteBufferNano.computeMessageSize(49, bVar);
                    }
                }
                computeSerializedSize = i10;
            }
            if (this.W != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(50, this.W);
            }
            if (this.X != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(51, this.X);
            }
            if (!this.Y.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(52, this.Y);
            }
            if (this.Z != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(53, this.Z);
            }
            if (this.aa != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(54, this.aa);
            }
            if (!this.ab.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(55, this.ab);
            }
            if (!this.ac.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(58, this.ac);
            }
            if (!this.ad.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(60, this.ad);
            }
            if (!this.ae.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(61, this.ae);
            }
            if (this.af != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(62, this.af);
            }
            if (this.ag != null && this.ag.length > 0) {
                int i11 = 0;
                int i12 = 0;
                for (String str2 : this.ag) {
                    if (str2 != null) {
                        i12++;
                        i11 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
                    }
                }
                computeSerializedSize = computeSerializedSize + i11 + (i12 * 2);
            }
            if (!this.ah.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(64, this.ah);
            }
            if (this.ai != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(65, this.ai);
            }
            if (this.aj != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(66, this.aj);
            }
            if (this.ak != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(67, this.ak);
            }
            if (this.al != null && this.al.length > 0) {
                int i13 = computeSerializedSize;
                for (ac acVar : this.al) {
                    if (acVar != null) {
                        i13 += CodedOutputByteBufferNano.computeMessageSize(68, acVar);
                    }
                }
                computeSerializedSize = i13;
            }
            if (!this.am.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(69, this.am);
            }
            if (!this.an.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(70, this.an);
            }
            if (!this.ao.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(71, this.ao);
            }
            if (!this.ap.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(72, this.ap);
            }
            if (!this.aq.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(73, this.aq);
            }
            if (!this.ar.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(74, this.ar);
            }
            if (!this.as.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(75, this.as);
            }
            if (!this.at.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(76, this.at);
            }
            if (!this.au.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(77, this.au);
            }
            if (!this.av.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(78, this.av);
            }
            if (this.aw != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(79, this.aw);
            }
            if (this.ax != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(80, this.ax);
            }
            if (this.ay != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(81, this.ay);
            }
            if (this.az != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(82, this.az);
            }
            if (this.aA != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(83, this.aA);
            }
            if (this.aB != null && this.aB.length > 0) {
                int i14 = computeSerializedSize;
                for (ae aeVar : this.aB) {
                    if (aeVar != null) {
                        i14 += CodedOutputByteBufferNano.computeMessageSize(84, aeVar);
                    }
                }
                computeSerializedSize = i14;
            }
            if (this.aC != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(85, this.aC);
            }
            if (this.aD != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(86, this.aD);
            }
            if (!this.aE.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(87, this.aE);
            }
            if (!this.aF.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(88, this.aF);
            }
            if (!this.aG.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(89, this.aG);
            }
            if (!this.aH.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(90, this.aH);
            }
            if (!this.aI.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(91, this.aI);
            }
            if (!this.aJ.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(92, this.aJ);
            }
            if (!this.aK.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(93, this.aK);
            }
            if (!this.aL.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(94, this.aL);
            }
            if (!this.aM.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(95, this.aM);
            }
            if (!this.aN.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(96, this.aN);
            }
            if (!this.aO.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(97, this.aO);
            }
            if (!this.aP.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(98, this.aP);
            }
            if (!this.aQ.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(99, this.aQ);
            }
            if (this.aR != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(100, this.aR);
            }
            if (!this.aS.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(101, this.aS);
            }
            if (!this.aT.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(102, this.aT);
            }
            if (this.aU != null && this.aU.length > 0) {
                int i15 = computeSerializedSize;
                for (bh bhVar : this.aU) {
                    if (bhVar != null) {
                        i15 += CodedOutputByteBufferNano.computeMessageSize(103, bhVar);
                    }
                }
                computeSerializedSize = i15;
            }
            if (!this.aV.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(104, this.aV);
            }
            if (!this.aW.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(105, this.aW);
            }
            if (!this.aX.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(106, this.aX);
            }
            if (!this.aY.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(107, this.aY);
            }
            if (!this.aZ.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(108, this.aZ);
            }
            if (!this.ba.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(109, this.ba);
            }
            if (!this.bb.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(110, this.bb);
            }
            if (!this.bc.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(111, this.bc);
            }
            if (!this.bd.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(112, this.bd);
            }
            if (this.be != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(113, this.be);
            }
            if (this.bf != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(114, this.bf);
            }
            if (!this.bg.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(115, this.bg);
            }
            if (!this.bh.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(116, this.bh);
            }
            if (!this.bi.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(117, this.bi);
            }
            if (!this.bj.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(118, this.bj);
            }
            if (this.bk != null && this.bk.length > 0) {
                int i16 = computeSerializedSize;
                for (y yVar : this.bk) {
                    if (yVar != null) {
                        i16 += CodedOutputByteBufferNano.computeMessageSize(119, yVar);
                    }
                }
                computeSerializedSize = i16;
            }
            if (this.bl != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, this.bl);
            }
            if (!this.bm.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(121, this.bm);
            }
            if (!this.bn.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(122, this.bn);
            }
            if (!this.bo.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(123, this.bo);
            }
            if (!this.bp.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA, this.bp);
            }
            if (!this.bq.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(125, this.bq);
            }
            if (this.br != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(TransportMediator.KEYCODE_MEDIA_PLAY, this.br);
            }
            if (this.bs != null && this.bs.length > 0) {
                int i17 = computeSerializedSize;
                for (aj ajVar : this.bs) {
                    if (ajVar != null) {
                        i17 += CodedOutputByteBufferNano.computeMessageSize(127, ajVar);
                    }
                }
                computeSerializedSize = i17;
            }
            if (this.bt != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(128, this.bt);
            }
            if (this.bu != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(129, this.bu);
            }
            if (!this.bv.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(130, this.bv);
            }
            if (!this.bw.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(131, this.bw);
            }
            if (!this.bx.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(132, this.bx);
            }
            if (!this.by.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(133, this.by);
            }
            if (!this.bz.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(134, this.bz);
            }
            if (!this.bA.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(135, this.bA);
            }
            if (!this.bB.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(136, this.bB);
            }
            if (!this.bC.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(DumpSegment.ANDROID_ROOT_INTERNED_STRING, this.bC);
            }
            if (!this.bD.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(DumpSegment.ANDROID_ROOT_FINALIZING, this.bD);
            }
            if (!this.bE.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(DumpSegment.ANDROID_ROOT_DEBUGGER, this.bE);
            }
            if (this.bF != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(140, this.bF);
            }
            if (this.bG != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(DumpSegment.ANDROID_ROOT_VM_INTERNAL, this.bG);
            }
            if (this.bH != null && this.bH.length > 0) {
                int i18 = 0;
                int i19 = 0;
                for (String str3 : this.bH) {
                    if (str3 != null) {
                        i19++;
                        i18 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
                    }
                }
                computeSerializedSize = computeSerializedSize + i18 + (i19 * 2);
            }
            if (!this.bI.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(143, this.bI);
            }
            if (this.bJ != null && this.bJ.length > 0) {
                for (am amVar : this.bJ) {
                    if (amVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(144, amVar);
                    }
                }
            }
            if (!this.bK.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(145, this.bK);
            }
            if (!this.bL.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(146, this.bL);
            }
            if (!this.bM.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(147, this.bM);
            }
            if (!this.bN.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(148, this.bN);
            }
            if (!this.bO.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(149, this.bO);
            }
            if (!this.bP.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(150, this.bP);
            }
            if (!this.bQ.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(151, this.bQ);
            }
            if (!this.bR.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(152, this.bR);
            }
            if (!this.bS.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(153, this.bS);
            }
            if (!this.bT.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE, this.bT);
            }
            return !this.bU.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(155, this.bU) : computeSerializedSize;
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
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                        int length = this.f == null ? 0 : this.f.length;
                        String[] strArr = new String[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.f, 0, strArr, 0, length);
                        }
                        while (length < strArr.length - 1) {
                            strArr[length] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        strArr[length] = codedInputByteBufferNano.readString();
                        this.f = strArr;
                        break;
                    case 58:
                        this.g = codedInputByteBufferNano.readString();
                        break;
                    case 66:
                        this.h = codedInputByteBufferNano.readString();
                        break;
                    case 74:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        int length2 = this.i == null ? 0 : this.i.length;
                        n[] nVarArr = new n[(repeatedFieldArrayLength2 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.i, 0, nVarArr, 0, length2);
                        }
                        while (length2 < nVarArr.length - 1) {
                            nVarArr[length2] = new n();
                            codedInputByteBufferNano.readMessage(nVarArr[length2]);
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        nVarArr[length2] = new n();
                        codedInputByteBufferNano.readMessage(nVarArr[length2]);
                        this.i = nVarArr;
                        break;
                    case 82:
                        if (this.j == null) {
                            this.j = new p();
                        }
                        codedInputByteBufferNano.readMessage(this.j);
                        break;
                    case 90:
                        if (this.k == null) {
                            this.k = new u();
                        }
                        codedInputByteBufferNano.readMessage(this.k);
                        break;
                    case 106:
                        this.l = codedInputByteBufferNano.readString();
                        break;
                    case 114:
                        this.m = codedInputByteBufferNano.readString();
                        break;
                    case 122:
                        this.n = codedInputByteBufferNano.readString();
                        break;
                    case 130:
                        this.o = codedInputByteBufferNano.readString();
                        break;
                    case DumpSegment.ANDROID_ROOT_FINALIZING /*138*/:
                        this.p = codedInputByteBufferNano.readString();
                        break;
                    case 146:
                        this.q = codedInputByteBufferNano.readString();
                        break;
                    case 152:
                        this.r = codedInputByteBufferNano.readInt32();
                        break;
                    case EndBill.UNKNOWN_END_ORDER_FAILED /*162*/:
                        this.s = codedInputByteBufferNano.readString();
                        break;
                    case 170:
                        this.t = codedInputByteBufferNano.readString();
                        break;
                    case 178:
                        int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 178);
                        int length3 = this.u == null ? 0 : this.u.length;
                        m[] mVarArr = new m[(repeatedFieldArrayLength3 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.u, 0, mVarArr, 0, length3);
                        }
                        while (length3 < mVarArr.length - 1) {
                            mVarArr[length3] = new m();
                            codedInputByteBufferNano.readMessage(mVarArr[length3]);
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        mVarArr[length3] = new m();
                        codedInputByteBufferNano.readMessage(mVarArr[length3]);
                        this.u = mVarArr;
                        break;
                    case 186:
                        this.v = codedInputByteBufferNano.readString();
                        break;
                    case 194:
                        this.w = codedInputByteBufferNano.readString();
                        break;
                    case 200:
                        this.x = codedInputByteBufferNano.readInt32();
                        break;
                    case 210:
                        if (this.y == null) {
                            this.y = new k();
                        }
                        codedInputByteBufferNano.readMessage(this.y);
                        break;
                    case MessageCode.MSG_ONLINE_BUILDING_CHANGED /*216*/:
                        this.z = codedInputByteBufferNano.readInt32();
                        break;
                    case 224:
                        this.A = codedInputByteBufferNano.readInt32();
                        break;
                    case 234:
                        this.B = codedInputByteBufferNano.readString();
                        break;
                    case FavoritesPointFragment.REQUEST_COMPNAY /*242*/:
                        int repeatedFieldArrayLength4 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, FavoritesPointFragment.REQUEST_COMPNAY);
                        int length4 = this.C == null ? 0 : this.C.length;
                        l[] lVarArr = new l[(repeatedFieldArrayLength4 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.C, 0, lVarArr, 0, length4);
                        }
                        while (length4 < lVarArr.length - 1) {
                            lVarArr[length4] = new l();
                            codedInputByteBufferNano.readMessage(lVarArr[length4]);
                            codedInputByteBufferNano.readTag();
                            length4++;
                        }
                        lVarArr[length4] = new l();
                        codedInputByteBufferNano.readMessage(lVarArr[length4]);
                        this.C = lVarArr;
                        break;
                    case Callback.DEFAULT_SWIPE_ANIMATION_DURATION /*250*/:
                        this.D = codedInputByteBufferNano.readString();
                        break;
                    case 256:
                        this.E = codedInputByteBufferNano.readInt32();
                        break;
                    case 264:
                        this.F = codedInputByteBufferNano.readInt32();
                        break;
                    case 274:
                        int repeatedFieldArrayLength5 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 274);
                        int length5 = this.G == null ? 0 : this.G.length;
                        ad[] adVarArr = new ad[(repeatedFieldArrayLength5 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.G, 0, adVarArr, 0, length5);
                        }
                        while (length5 < adVarArr.length - 1) {
                            adVarArr[length5] = new ad();
                            codedInputByteBufferNano.readMessage(adVarArr[length5]);
                            codedInputByteBufferNano.readTag();
                            length5++;
                        }
                        adVarArr[length5] = new ad();
                        codedInputByteBufferNano.readMessage(adVarArr[length5]);
                        this.G = adVarArr;
                        break;
                    case 282:
                        this.H = codedInputByteBufferNano.readString();
                        break;
                    case 290:
                        this.I = codedInputByteBufferNano.readString();
                        break;
                    case 298:
                        int repeatedFieldArrayLength6 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 298);
                        int length6 = this.J == null ? 0 : this.J.length;
                        g[] gVarArr = new g[(repeatedFieldArrayLength6 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.J, 0, gVarArr, 0, length6);
                        }
                        while (length6 < gVarArr.length - 1) {
                            gVarArr[length6] = new g();
                            codedInputByteBufferNano.readMessage(gVarArr[length6]);
                            codedInputByteBufferNano.readTag();
                            length6++;
                        }
                        gVarArr[length6] = new g();
                        codedInputByteBufferNano.readMessage(gVarArr[length6]);
                        this.J = gVarArr;
                        break;
                    case SecExceptionCode.SEC_ERROR_STA_KEY_NOT_EXISTED /*306*/:
                        int repeatedFieldArrayLength7 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, SecExceptionCode.SEC_ERROR_STA_KEY_NOT_EXISTED);
                        int length7 = this.K == null ? 0 : this.K.length;
                        h[] hVarArr = new h[(repeatedFieldArrayLength7 + length7)];
                        if (length7 != 0) {
                            System.arraycopy(this.K, 0, hVarArr, 0, length7);
                        }
                        while (length7 < hVarArr.length - 1) {
                            hVarArr[length7] = new h();
                            codedInputByteBufferNano.readMessage(hVarArr[length7]);
                            codedInputByteBufferNano.readTag();
                            length7++;
                        }
                        hVarArr[length7] = new h();
                        codedInputByteBufferNano.readMessage(hVarArr[length7]);
                        this.K = hVarArr;
                        break;
                    case 314:
                        this.L = codedInputByteBufferNano.readString();
                        break;
                    case 322:
                        this.M = codedInputByteBufferNano.readString();
                        break;
                    case 330:
                        this.N = codedInputByteBufferNano.readString();
                        break;
                    case 338:
                        this.O = codedInputByteBufferNano.readString();
                        break;
                    case 346:
                        this.P = codedInputByteBufferNano.readString();
                        break;
                    case 354:
                        this.Q = codedInputByteBufferNano.readString();
                        break;
                    case 360:
                        this.R = codedInputByteBufferNano.readInt32();
                        break;
                    case 368:
                        this.S = codedInputByteBufferNano.readInt32();
                        break;
                    case 378:
                        if (this.T == null) {
                            this.T = new af();
                        }
                        codedInputByteBufferNano.readMessage(this.T);
                        break;
                    case 386:
                        this.U = codedInputByteBufferNano.readString();
                        break;
                    case 394:
                        int repeatedFieldArrayLength8 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 394);
                        int length8 = this.V == null ? 0 : this.V.length;
                        b[] bVarArr = new b[(repeatedFieldArrayLength8 + length8)];
                        if (length8 != 0) {
                            System.arraycopy(this.V, 0, bVarArr, 0, length8);
                        }
                        while (length8 < bVarArr.length - 1) {
                            bVarArr[length8] = new b();
                            codedInputByteBufferNano.readMessage(bVarArr[length8]);
                            codedInputByteBufferNano.readTag();
                            length8++;
                        }
                        bVarArr[length8] = new b();
                        codedInputByteBufferNano.readMessage(bVarArr[length8]);
                        this.V = bVarArr;
                        break;
                    case 400:
                        this.W = codedInputByteBufferNano.readInt32();
                        break;
                    case H5ErrorCode.HTTP_GONE /*410*/:
                        if (this.X == null) {
                            this.X = new f();
                        }
                        codedInputByteBufferNano.readMessage(this.X);
                        break;
                    case Util.TOF_TAG /*418*/:
                        this.Y = codedInputByteBufferNano.readString();
                        break;
                    case 424:
                        this.Z = codedInputByteBufferNano.readInt32();
                        break;
                    case 432:
                        this.aa = codedInputByteBufferNano.readInt32();
                        break;
                    case 442:
                        this.ab = codedInputByteBufferNano.readString();
                        break;
                    case 466:
                        this.ac = codedInputByteBufferNano.readString();
                        break;
                    case 482:
                        this.ad = codedInputByteBufferNano.readString();
                        break;
                    case 490:
                        this.ae = codedInputByteBufferNano.readString();
                        break;
                    case 496:
                        this.af = codedInputByteBufferNano.readInt32();
                        break;
                    case MessageCode.MSG_BLE_NO_SCAN /*506*/:
                        int repeatedFieldArrayLength9 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, MessageCode.MSG_BLE_NO_SCAN);
                        int length9 = this.ag == null ? 0 : this.ag.length;
                        String[] strArr2 = new String[(repeatedFieldArrayLength9 + length9)];
                        if (length9 != 0) {
                            System.arraycopy(this.ag, 0, strArr2, 0, length9);
                        }
                        while (length9 < strArr2.length - 1) {
                            strArr2[length9] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length9++;
                        }
                        strArr2[length9] = codedInputByteBufferNano.readString();
                        this.ag = strArr2;
                        break;
                    case 514:
                        this.ah = codedInputByteBufferNano.readString();
                        break;
                    case 522:
                        if (this.ai == null) {
                            this.ai = new ab();
                        }
                        codedInputByteBufferNano.readMessage(this.ai);
                        break;
                    case 530:
                        if (this.aj == null) {
                            this.aj = new ab();
                        }
                        codedInputByteBufferNano.readMessage(this.aj);
                        break;
                    case 538:
                        if (this.ak == null) {
                            this.ak = new aa();
                        }
                        codedInputByteBufferNano.readMessage(this.ak);
                        break;
                    case 546:
                        int repeatedFieldArrayLength10 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 546);
                        int length10 = this.al == null ? 0 : this.al.length;
                        ac[] acVarArr = new ac[(repeatedFieldArrayLength10 + length10)];
                        if (length10 != 0) {
                            System.arraycopy(this.al, 0, acVarArr, 0, length10);
                        }
                        while (length10 < acVarArr.length - 1) {
                            acVarArr[length10] = new ac();
                            codedInputByteBufferNano.readMessage(acVarArr[length10]);
                            codedInputByteBufferNano.readTag();
                            length10++;
                        }
                        acVarArr[length10] = new ac();
                        codedInputByteBufferNano.readMessage(acVarArr[length10]);
                        this.al = acVarArr;
                        break;
                    case 554:
                        this.am = codedInputByteBufferNano.readString();
                        break;
                    case 562:
                        this.an = codedInputByteBufferNano.readString();
                        break;
                    case 570:
                        this.ao = codedInputByteBufferNano.readString();
                        break;
                    case IjkMediaMeta.FF_PROFILE_H264_CONSTRAINED_BASELINE /*578*/:
                        this.ap = codedInputByteBufferNano.readString();
                        break;
                    case 586:
                        this.aq = codedInputByteBufferNano.readString();
                        break;
                    case 594:
                        this.ar = codedInputByteBufferNano.readString();
                        break;
                    case 602:
                        this.as = codedInputByteBufferNano.readString();
                        break;
                    case SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE_DATA /*610*/:
                        this.at = codedInputByteBufferNano.readString();
                        break;
                    case 618:
                        this.au = codedInputByteBufferNano.readString();
                        break;
                    case 626:
                        this.av = codedInputByteBufferNano.readString();
                        break;
                    case 632:
                        this.aw = codedInputByteBufferNano.readInt32();
                        break;
                    case 640:
                        this.ax = codedInputByteBufferNano.readInt32();
                        break;
                    case 648:
                        this.ay = codedInputByteBufferNano.readInt32();
                        break;
                    case 656:
                        this.az = codedInputByteBufferNano.readInt32();
                        break;
                    case 664:
                        this.aA = codedInputByteBufferNano.readInt32();
                        break;
                    case 674:
                        int repeatedFieldArrayLength11 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 674);
                        int length11 = this.aB == null ? 0 : this.aB.length;
                        ae[] aeVarArr = new ae[(repeatedFieldArrayLength11 + length11)];
                        if (length11 != 0) {
                            System.arraycopy(this.aB, 0, aeVarArr, 0, length11);
                        }
                        while (length11 < aeVarArr.length - 1) {
                            aeVarArr[length11] = new ae();
                            codedInputByteBufferNano.readMessage(aeVarArr[length11]);
                            codedInputByteBufferNano.readTag();
                            length11++;
                        }
                        aeVarArr[length11] = new ae();
                        codedInputByteBufferNano.readMessage(aeVarArr[length11]);
                        this.aB = aeVarArr;
                        break;
                    case 682:
                        if (this.aC == null) {
                            this.aC = new s();
                        }
                        codedInputByteBufferNano.readMessage(this.aC);
                        break;
                    case 690:
                        if (this.aD == null) {
                            this.aD = new r();
                        }
                        codedInputByteBufferNano.readMessage(this.aD);
                        break;
                    case SecExceptionCode.SEC_ERROR_SIGNATURE_NONSUPPORTED_SIGN_TYPE /*698*/:
                        this.aE = codedInputByteBufferNano.readString();
                        break;
                    case 706:
                        this.aF = codedInputByteBufferNano.readString();
                        break;
                    case MessageCode.MSG_LBS_INVALID_USER_KEY /*714*/:
                        this.aG = codedInputByteBufferNano.readString();
                        break;
                    case 722:
                        this.aH = codedInputByteBufferNano.readString();
                        break;
                    case 730:
                        this.aI = codedInputByteBufferNano.readString();
                        break;
                    case 738:
                        this.aJ = codedInputByteBufferNano.readString();
                        break;
                    case 746:
                        this.aK = codedInputByteBufferNano.readString();
                        break;
                    case 754:
                        this.aL = codedInputByteBufferNano.readString();
                        break;
                    case 762:
                        this.aM = codedInputByteBufferNano.readString();
                        break;
                    case 770:
                        this.aN = codedInputByteBufferNano.readString();
                        break;
                    case 778:
                        this.aO = codedInputByteBufferNano.readString();
                        break;
                    case 786:
                        this.aP = codedInputByteBufferNano.readString();
                        break;
                    case 794:
                        this.aQ = codedInputByteBufferNano.readString();
                        break;
                    case 800:
                        this.aR = codedInputByteBufferNano.readInt32();
                        break;
                    case 810:
                        this.aS = codedInputByteBufferNano.readString();
                        break;
                    case 818:
                        this.aT = codedInputByteBufferNano.readString();
                        break;
                    case 826:
                        int repeatedFieldArrayLength12 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 826);
                        int length12 = this.aU == null ? 0 : this.aU.length;
                        bh[] bhVarArr = new bh[(repeatedFieldArrayLength12 + length12)];
                        if (length12 != 0) {
                            System.arraycopy(this.aU, 0, bhVarArr, 0, length12);
                        }
                        while (length12 < bhVarArr.length - 1) {
                            bhVarArr[length12] = new bh();
                            codedInputByteBufferNano.readMessage(bhVarArr[length12]);
                            codedInputByteBufferNano.readTag();
                            length12++;
                        }
                        bhVarArr[length12] = new bh();
                        codedInputByteBufferNano.readMessage(bhVarArr[length12]);
                        this.aU = bhVarArr;
                        break;
                    case 834:
                        this.aV = codedInputByteBufferNano.readString();
                        break;
                    case 842:
                        this.aW = codedInputByteBufferNano.readString();
                        break;
                    case 850:
                        this.aX = codedInputByteBufferNano.readString();
                        break;
                    case 858:
                        this.aY = codedInputByteBufferNano.readString();
                        break;
                    case 866:
                        this.aZ = codedInputByteBufferNano.readString();
                        break;
                    case 874:
                        this.ba = codedInputByteBufferNano.readString();
                        break;
                    case 882:
                        this.bb = codedInputByteBufferNano.readString();
                        break;
                    case 890:
                        this.bc = codedInputByteBufferNano.readString();
                        break;
                    case 898:
                        this.bd = codedInputByteBufferNano.readString();
                        break;
                    case SecExceptionCode.SEC_ERROR_UMID_NETWORK_ERROR /*904*/:
                        this.be = codedInputByteBufferNano.readInt32();
                        break;
                    case 912:
                        this.bf = codedInputByteBufferNano.readInt32();
                        break;
                    case 922:
                        this.bg = codedInputByteBufferNano.readString();
                        break;
                    case 930:
                        this.bh = codedInputByteBufferNano.readString();
                        break;
                    case 938:
                        this.bi = codedInputByteBufferNano.readString();
                        break;
                    case 946:
                        this.bj = codedInputByteBufferNano.readString();
                        break;
                    case 954:
                        int repeatedFieldArrayLength13 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 954);
                        int length13 = this.bk == null ? 0 : this.bk.length;
                        y[] yVarArr = new y[(repeatedFieldArrayLength13 + length13)];
                        if (length13 != 0) {
                            System.arraycopy(this.bk, 0, yVarArr, 0, length13);
                        }
                        while (length13 < yVarArr.length - 1) {
                            yVarArr[length13] = new y();
                            codedInputByteBufferNano.readMessage(yVarArr[length13]);
                            codedInputByteBufferNano.readTag();
                            length13++;
                        }
                        yVarArr[length13] = new y();
                        codedInputByteBufferNano.readMessage(yVarArr[length13]);
                        this.bk = yVarArr;
                        break;
                    case 960:
                        this.bl = codedInputByteBufferNano.readInt32();
                        break;
                    case 970:
                        this.bm = codedInputByteBufferNano.readString();
                        break;
                    case 978:
                        this.bn = codedInputByteBufferNano.readString();
                        break;
                    case 986:
                        this.bo = codedInputByteBufferNano.readString();
                        break;
                    case 994:
                        this.bp = codedInputByteBufferNano.readString();
                        break;
                    case 1002:
                        this.bq = codedInputByteBufferNano.readString();
                        break;
                    case 1010:
                        if (this.br == null) {
                            this.br = new ai();
                        }
                        codedInputByteBufferNano.readMessage(this.br);
                        break;
                    case 1018:
                        int repeatedFieldArrayLength14 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 1018);
                        int length14 = this.bs == null ? 0 : this.bs.length;
                        aj[] ajVarArr = new aj[(repeatedFieldArrayLength14 + length14)];
                        if (length14 != 0) {
                            System.arraycopy(this.bs, 0, ajVarArr, 0, length14);
                        }
                        while (length14 < ajVarArr.length - 1) {
                            ajVarArr[length14] = new aj();
                            codedInputByteBufferNano.readMessage(ajVarArr[length14]);
                            codedInputByteBufferNano.readTag();
                            length14++;
                        }
                        ajVarArr[length14] = new aj();
                        codedInputByteBufferNano.readMessage(ajVarArr[length14]);
                        this.bs = ajVarArr;
                        break;
                    case 1026:
                        if (this.bt == null) {
                            this.bt = new al();
                        }
                        codedInputByteBufferNano.readMessage(this.bt);
                        break;
                    case 1034:
                        if (this.bu == null) {
                            this.bu = new ak();
                        }
                        codedInputByteBufferNano.readMessage(this.bu);
                        break;
                    case 1042:
                        this.bv = codedInputByteBufferNano.readString();
                        break;
                    case 1050:
                        this.bw = codedInputByteBufferNano.readString();
                        break;
                    case 1058:
                        this.bx = codedInputByteBufferNano.readString();
                        break;
                    case 1066:
                        this.by = codedInputByteBufferNano.readString();
                        break;
                    case 1074:
                        this.bz = codedInputByteBufferNano.readString();
                        break;
                    case 1082:
                        this.bA = codedInputByteBufferNano.readString();
                        break;
                    case 1090:
                        this.bB = codedInputByteBufferNano.readString();
                        break;
                    case SecExceptionCode.SEC_ERROR_ATLAS_UNSUPPORTED /*1098*/:
                        this.bC = codedInputByteBufferNano.readString();
                        break;
                    case SecExceptionCode.SEC_ERROE_OPENSDK_INCORRECT_DATA_FILE /*1106*/:
                        this.bD = codedInputByteBufferNano.readString();
                        break;
                    case 1114:
                        this.bE = codedInputByteBufferNano.readString();
                        break;
                    case 1120:
                        this.bF = codedInputByteBufferNano.readInt32();
                        break;
                    case 1130:
                        if (this.bG == null) {
                            this.bG = new ap();
                        }
                        codedInputByteBufferNano.readMessage(this.bG);
                        break;
                    case 1138:
                        int repeatedFieldArrayLength15 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 1138);
                        int length15 = this.bH == null ? 0 : this.bH.length;
                        String[] strArr3 = new String[(repeatedFieldArrayLength15 + length15)];
                        if (length15 != 0) {
                            System.arraycopy(this.bH, 0, strArr3, 0, length15);
                        }
                        while (length15 < strArr3.length - 1) {
                            strArr3[length15] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            length15++;
                        }
                        strArr3[length15] = codedInputByteBufferNano.readString();
                        this.bH = strArr3;
                        break;
                    case 1146:
                        this.bI = codedInputByteBufferNano.readString();
                        break;
                    case InnerMessageCode.MSG_REPORT_GPS_NEMA /*1154*/:
                        int repeatedFieldArrayLength16 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, InnerMessageCode.MSG_REPORT_GPS_NEMA);
                        int length16 = this.bJ == null ? 0 : this.bJ.length;
                        am[] amVarArr = new am[(repeatedFieldArrayLength16 + length16)];
                        if (length16 != 0) {
                            System.arraycopy(this.bJ, 0, amVarArr, 0, length16);
                        }
                        while (length16 < amVarArr.length - 1) {
                            amVarArr[length16] = new am();
                            codedInputByteBufferNano.readMessage(amVarArr[length16]);
                            codedInputByteBufferNano.readTag();
                            length16++;
                        }
                        amVarArr[length16] = new am();
                        codedInputByteBufferNano.readMessage(amVarArr[length16]);
                        this.bJ = amVarArr;
                        break;
                    case 1162:
                        this.bK = codedInputByteBufferNano.readString();
                        break;
                    case 1170:
                        this.bL = codedInputByteBufferNano.readString();
                        break;
                    case 1178:
                        this.bM = codedInputByteBufferNano.readString();
                        break;
                    case 1186:
                        this.bN = codedInputByteBufferNano.readString();
                        break;
                    case 1194:
                        this.bO = codedInputByteBufferNano.readString();
                        break;
                    case 1202:
                        this.bP = codedInputByteBufferNano.readString();
                        break;
                    case 1210:
                        this.bQ = codedInputByteBufferNano.readString();
                        break;
                    case 1218:
                        this.bR = codedInputByteBufferNano.readString();
                        break;
                    case 1226:
                        this.bS = codedInputByteBufferNano.readString();
                        break;
                    case Ajx3DebugService.SCAN_REQUEST_CODE /*1234*/:
                        this.bT = codedInputByteBufferNano.readString();
                        break;
                    case 1242:
                        this.bU = codedInputByteBufferNano.readString();
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

    /* renamed from: c$e */
    /* compiled from: Poilite */
    public static final class e extends MessageNano {
        private static volatile e[] k;
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

        public static e[] a() {
            if (k == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (k == null) {
                        k = new e[0];
                    }
                }
            }
            return k;
        }

        public e() {
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

    /* renamed from: c$f */
    /* compiled from: Poilite */
    public static final class f extends MessageNano {
        public e[] a;
        public e[] b;
        public e[] c;

        public f() {
            this.a = e.a();
            this.b = e.a();
            this.c = e.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (this.a != null && this.a.length > 0) {
                for (e eVar : this.a) {
                    if (eVar != null) {
                        codedOutputByteBufferNano.writeMessage(1, eVar);
                    }
                }
            }
            if (this.b != null && this.b.length > 0) {
                for (e eVar2 : this.b) {
                    if (eVar2 != null) {
                        codedOutputByteBufferNano.writeMessage(2, eVar2);
                    }
                }
            }
            if (this.c != null && this.c.length > 0) {
                for (e eVar3 : this.c) {
                    if (eVar3 != null) {
                        codedOutputByteBufferNano.writeMessage(3, eVar3);
                    }
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.a != null && this.a.length > 0) {
                int i = computeSerializedSize;
                for (e eVar : this.a) {
                    if (eVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(1, eVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (this.b != null && this.b.length > 0) {
                int i2 = computeSerializedSize;
                for (e eVar2 : this.b) {
                    if (eVar2 != null) {
                        i2 += CodedOutputByteBufferNano.computeMessageSize(2, eVar2);
                    }
                }
                computeSerializedSize = i2;
            }
            if (this.c != null && this.c.length > 0) {
                for (e eVar3 : this.c) {
                    if (eVar3 != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, eVar3);
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
                    e[] eVarArr = new e[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.a, 0, eVarArr, 0, length);
                    }
                    while (length < eVarArr.length - 1) {
                        eVarArr[length] = new e();
                        codedInputByteBufferNano.readMessage(eVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    eVarArr[length] = new e();
                    codedInputByteBufferNano.readMessage(eVarArr[length]);
                    this.a = eVarArr;
                } else if (readTag == 18) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    int length2 = this.b == null ? 0 : this.b.length;
                    e[] eVarArr2 = new e[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.b, 0, eVarArr2, 0, length2);
                    }
                    while (length2 < eVarArr2.length - 1) {
                        eVarArr2[length2] = new e();
                        codedInputByteBufferNano.readMessage(eVarArr2[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    eVarArr2[length2] = new e();
                    codedInputByteBufferNano.readMessage(eVarArr2[length2]);
                    this.b = eVarArr2;
                } else if (readTag == 26) {
                    int repeatedFieldArrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length3 = this.c == null ? 0 : this.c.length;
                    e[] eVarArr3 = new e[(repeatedFieldArrayLength3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.c, 0, eVarArr3, 0, length3);
                    }
                    while (length3 < eVarArr3.length - 1) {
                        eVarArr3[length3] = new e();
                        codedInputByteBufferNano.readMessage(eVarArr3[length3]);
                        codedInputByteBufferNano.readTag();
                        length3++;
                    }
                    eVarArr3[length3] = new e();
                    codedInputByteBufferNano.readMessage(eVarArr3[length3]);
                    this.c = eVarArr3;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$g */
    /* compiled from: Poilite */
    public static final class g extends MessageNano {
        private static volatile g[] d;
        public String a;
        public String b;
        public String c;

        public static g[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new g[0];
                    }
                }
            }
            return d;
        }

        public g() {
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

    /* renamed from: c$h */
    /* compiled from: Poilite */
    public static final class h extends MessageNano {
        private static volatile h[] e;
        public String a;
        public String b;
        public String c;
        public int d;

        public static h[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new h[0];
                    }
                }
            }
            return e;
        }

        public h() {
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

    /* renamed from: c$i */
    /* compiled from: Poilite */
    public static final class i extends MessageNano {
        private static volatile i[] l;
        public String a;
        public String b;
        public int c;
        public String d;
        public String e;
        public String f;
        public String g;
        public String h;
        public j[] i;
        public String j;
        public String k;

        public static i[] a() {
            if (l == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (l == null) {
                        l = new i[0];
                    }
                }
            }
            return l;
        }

        public i() {
            this.a = "";
            this.b = "";
            this.c = 0;
            this.d = "";
            this.e = "";
            this.f = "";
            this.g = "";
            this.h = "";
            this.i = j.a();
            this.j = "";
            this.k = "";
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
            if (!this.g.equals("")) {
                codedOutputByteBufferNano.writeString(7, this.g);
            }
            if (!this.h.equals("")) {
                codedOutputByteBufferNano.writeString(8, this.h);
            }
            if (this.i != null && this.i.length > 0) {
                for (j jVar : this.i) {
                    if (jVar != null) {
                        codedOutputByteBufferNano.writeMessage(9, jVar);
                    }
                }
            }
            if (!this.j.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.j);
            }
            if (!this.k.equals("")) {
                codedOutputByteBufferNano.writeString(11, this.k);
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
                for (j jVar : this.i) {
                    if (jVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(9, jVar);
                    }
                }
            }
            if (!this.j.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(10, this.j);
            }
            return !this.k.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(11, this.k) : computeSerializedSize;
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
                    case 24:
                        this.c = codedInputByteBufferNano.readInt32();
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
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        int length = this.i == null ? 0 : this.i.length;
                        j[] jVarArr = new j[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.i, 0, jVarArr, 0, length);
                        }
                        while (length < jVarArr.length - 1) {
                            jVarArr[length] = new j();
                            codedInputByteBufferNano.readMessage(jVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        jVarArr[length] = new j();
                        codedInputByteBufferNano.readMessage(jVarArr[length]);
                        this.i = jVarArr;
                        break;
                    case 82:
                        this.j = codedInputByteBufferNano.readString();
                        break;
                    case 90:
                        this.k = codedInputByteBufferNano.readString();
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

    /* renamed from: c$j */
    /* compiled from: Poilite */
    public static final class j extends MessageNano {
        private static volatile j[] p;
        public int a;
        public String b;
        public String c;
        public int d;
        public String e;
        public String f;
        public int g;
        public int h;
        public String i;
        public String j;
        public int k;
        public String l;
        public String m;
        public String n;
        public String o;

        public static j[] a() {
            if (p == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (p == null) {
                        p = new j[0];
                    }
                }
            }
            return p;
        }

        public j() {
            this.a = 0;
            this.b = "";
            this.c = "";
            this.d = 0;
            this.e = "";
            this.f = "";
            this.g = 0;
            this.h = 0;
            this.i = "";
            this.j = "";
            this.k = 0;
            this.l = "";
            this.m = "";
            this.n = "";
            this.o = "";
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
            if (!this.e.equals("")) {
                codedOutputByteBufferNano.writeString(5, this.e);
            }
            if (!this.f.equals("")) {
                codedOutputByteBufferNano.writeString(6, this.f);
            }
            if (this.g != 0) {
                codedOutputByteBufferNano.writeInt32(7, this.g);
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
            if (this.k != 0) {
                codedOutputByteBufferNano.writeInt32(11, this.k);
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
            if (!this.e.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(5, this.e);
            }
            if (!this.f.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(6, this.f);
            }
            if (this.g != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(7, this.g);
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
            if (this.k != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(11, this.k);
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
            return !this.o.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(15, this.o) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.a = codedInputByteBufferNano.readInt32();
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
                    case 56:
                        this.g = codedInputByteBufferNano.readInt32();
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
                    case IjkMediaMeta.FF_PROFILE_H264_EXTENDED /*88*/:
                        this.k = codedInputByteBufferNano.readInt32();
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

    /* renamed from: c$k */
    /* compiled from: Poilite */
    public static final class k extends MessageNano {
        public String a;
        public String b;
        public String c;

        public k() {
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

    /* renamed from: c$l */
    /* compiled from: Poilite */
    public static final class l extends MessageNano {
        private static volatile l[] e;
        public String a;
        public String b;
        public String c;
        public String d;

        public static l[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new l[0];
                    }
                }
            }
            return e;
        }

        public l() {
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

    /* renamed from: c$m */
    /* compiled from: Poilite */
    public static final class m extends MessageNano {
        private static volatile m[] p;
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public bh[] f;
        public String g;
        public String h;
        public String i;
        public String j;
        public String k;
        public String l;
        public String m;
        public String n;
        public String o;

        public static m[] a() {
            if (p == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (p == null) {
                        p = new m[0];
                    }
                }
            }
            return p;
        }

        public m() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = bh.a();
            this.g = "";
            this.h = "";
            this.i = "";
            this.j = "";
            this.k = "";
            this.l = "";
            this.m = "";
            this.n = "";
            this.o = "";
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
                for (bh bhVar : this.f) {
                    if (bhVar != null) {
                        codedOutputByteBufferNano.writeMessage(6, bhVar);
                    }
                }
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
                for (bh bhVar : this.f) {
                    if (bhVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(6, bhVar);
                    }
                }
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
            return !this.o.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(15, this.o) : computeSerializedSize;
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
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                        int length = this.f == null ? 0 : this.f.length;
                        bh[] bhVarArr = new bh[(repeatedFieldArrayLength + length)];
                        if (length != 0) {
                            System.arraycopy(this.f, 0, bhVarArr, 0, length);
                        }
                        while (length < bhVarArr.length - 1) {
                            bhVarArr[length] = new bh();
                            codedInputByteBufferNano.readMessage(bhVarArr[length]);
                            codedInputByteBufferNano.readTag();
                            length++;
                        }
                        bhVarArr[length] = new bh();
                        codedInputByteBufferNano.readMessage(bhVarArr[length]);
                        this.f = bhVarArr;
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

    /* renamed from: c$n */
    /* compiled from: Poilite */
    public static final class n extends MessageNano {
        private static volatile n[] d;
        public String a;
        public String b;
        public String c;

        public static n[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new n[0];
                    }
                }
            }
            return d;
        }

        public n() {
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

    /* renamed from: c$o */
    /* compiled from: Poilite */
    public static final class o extends MessageNano {
        private static volatile o[] f;
        public String a;
        public String b;
        public int c;
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
            this.b = "";
            this.c = 0;
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
            if (this.c != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.c);
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
            if (this.c != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, this.c);
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
                } else if (readTag == 24) {
                    this.c = codedInputByteBufferNano.readInt32();
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

    /* renamed from: c$p */
    /* compiled from: Poilite */
    public static final class p extends MessageNano {
        public String a;
        public q[] b;

        public p() {
            this.a = "";
            this.b = q.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (q qVar : this.b) {
                    if (qVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, qVar);
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
                for (q qVar : this.b) {
                    if (qVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, qVar);
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
                    q[] qVarArr = new q[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, qVarArr, 0, length);
                    }
                    while (length < qVarArr.length - 1) {
                        qVarArr[length] = new q();
                        codedInputByteBufferNano.readMessage(qVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    qVarArr[length] = new q();
                    codedInputByteBufferNano.readMessage(qVarArr[length]);
                    this.b = qVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$q */
    /* compiled from: Poilite */
    public static final class q extends MessageNano {
        private static volatile q[] f;
        public String a;
        public String b;
        public int c;
        public String d;
        public String e;

        public static q[] a() {
            if (f == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (f == null) {
                        f = new q[0];
                    }
                }
            }
            return f;
        }

        public q() {
            this.a = "";
            this.b = "";
            this.c = 0;
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
            if (this.c != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.c);
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
            if (this.c != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, this.c);
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
                } else if (readTag == 24) {
                    this.c = codedInputByteBufferNano.readInt32();
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

    /* renamed from: c$r */
    /* compiled from: Poilite */
    public static final class r extends MessageNano {
        public String a;
        public t[] b;

        public r() {
            this.a = "";
            this.b = t.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (t tVar : this.b) {
                    if (tVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, tVar);
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
                for (t tVar : this.b) {
                    if (tVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, tVar);
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
                    t[] tVarArr = new t[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, tVarArr, 0, length);
                    }
                    while (length < tVarArr.length - 1) {
                        tVarArr[length] = new t();
                        codedInputByteBufferNano.readMessage(tVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    tVarArr[length] = new t();
                    codedInputByteBufferNano.readMessage(tVarArr[length]);
                    this.b = tVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$s */
    /* compiled from: Poilite */
    public static final class s extends MessageNano {
        public String a;
        public t[] b;

        public s() {
            this.a = "";
            this.b = t.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (t tVar : this.b) {
                    if (tVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, tVar);
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
                for (t tVar : this.b) {
                    if (tVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, tVar);
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
                    t[] tVarArr = new t[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, tVarArr, 0, length);
                    }
                    while (length < tVarArr.length - 1) {
                        tVarArr[length] = new t();
                        codedInputByteBufferNano.readMessage(tVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    tVarArr[length] = new t();
                    codedInputByteBufferNano.readMessage(tVarArr[length]);
                    this.b = tVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$t */
    /* compiled from: Poilite */
    public static final class t extends MessageNano {
        private static volatile t[] f;
        public String a;
        public int b;
        public String c;
        public String d;
        public String e;

        public static t[] a() {
            if (f == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (f == null) {
                        f = new t[0];
                    }
                }
            }
            return f;
        }

        public t() {
            this.a = "";
            this.b = 0;
            this.c = "";
            this.d = "";
            this.e = "";
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
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
            if (this.b != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, this.b);
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
                } else if (readTag == 16) {
                    this.b = codedInputByteBufferNano.readInt32();
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

    /* renamed from: c$u */
    /* compiled from: Poilite */
    public static final class u extends MessageNano {
        public String a;
        public int b;
        public int c;
        public v d;
        public v e;
        public w[] f;
        public x g;
        public o[] h;

        public u() {
            this.a = "";
            this.b = 0;
            this.c = 0;
            this.d = null;
            this.e = null;
            this.f = w.a();
            this.g = null;
            this.h = o.a();
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
            if (this.d != null) {
                codedOutputByteBufferNano.writeMessage(4, this.d);
            }
            if (this.e != null) {
                codedOutputByteBufferNano.writeMessage(5, this.e);
            }
            if (this.f != null && this.f.length > 0) {
                for (w wVar : this.f) {
                    if (wVar != null) {
                        codedOutputByteBufferNano.writeMessage(6, wVar);
                    }
                }
            }
            if (this.g != null) {
                codedOutputByteBufferNano.writeMessage(7, this.g);
            }
            if (this.h != null && this.h.length > 0) {
                for (o oVar : this.h) {
                    if (oVar != null) {
                        codedOutputByteBufferNano.writeMessage(8, oVar);
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
            if (this.c != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, this.c);
            }
            if (this.d != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, this.d);
            }
            if (this.e != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(5, this.e);
            }
            if (this.f != null && this.f.length > 0) {
                int i = computeSerializedSize;
                for (w wVar : this.f) {
                    if (wVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(6, wVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (this.g != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(7, this.g);
            }
            if (this.h != null && this.h.length > 0) {
                for (o oVar : this.h) {
                    if (oVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(8, oVar);
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
                } else if (readTag == 24) {
                    this.c = codedInputByteBufferNano.readInt32();
                } else if (readTag == 34) {
                    if (this.d == null) {
                        this.d = new v();
                    }
                    codedInputByteBufferNano.readMessage(this.d);
                } else if (readTag == 42) {
                    if (this.e == null) {
                        this.e = new v();
                    }
                    codedInputByteBufferNano.readMessage(this.e);
                } else if (readTag == 50) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                    int length = this.f == null ? 0 : this.f.length;
                    w[] wVarArr = new w[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.f, 0, wVarArr, 0, length);
                    }
                    while (length < wVarArr.length - 1) {
                        wVarArr[length] = new w();
                        codedInputByteBufferNano.readMessage(wVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    wVarArr[length] = new w();
                    codedInputByteBufferNano.readMessage(wVarArr[length]);
                    this.f = wVarArr;
                } else if (readTag == 58) {
                    if (this.g == null) {
                        this.g = new x();
                    }
                    codedInputByteBufferNano.readMessage(this.g);
                } else if (readTag == 66) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                    int length2 = this.h == null ? 0 : this.h.length;
                    o[] oVarArr = new o[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.h, 0, oVarArr, 0, length2);
                    }
                    while (length2 < oVarArr.length - 1) {
                        oVarArr[length2] = new o();
                        codedInputByteBufferNano.readMessage(oVarArr[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    oVarArr[length2] = new o();
                    codedInputByteBufferNano.readMessage(oVarArr[length2]);
                    this.h = oVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$v */
    /* compiled from: Poilite */
    public static final class v extends MessageNano {
        public String a;
        public String b;
        public String c;

        public v() {
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
                codedOutputByteBufferNano.writeString(3, this.b);
            }
            if (!this.c.equals("")) {
                codedOutputByteBufferNano.writeString(4, this.c);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public final int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.a.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.a);
            }
            if (!this.b.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(3, this.b);
            }
            return !this.c.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(4, this.c) : computeSerializedSize;
        }

        public final /* synthetic */ MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    return this;
                }
                if (readTag == 10) {
                    this.a = codedInputByteBufferNano.readString();
                } else if (readTag == 26) {
                    this.b = codedInputByteBufferNano.readString();
                } else if (readTag == 34) {
                    this.c = codedInputByteBufferNano.readString();
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$w */
    /* compiled from: Poilite */
    public static final class w extends MessageNano {
        private static volatile w[] e;
        public String a;
        public String b;
        public String c;
        public String d;

        public static w[] a() {
            if (e == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (e == null) {
                        e = new w[0];
                    }
                }
            }
            return e;
        }

        public w() {
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

    /* renamed from: c$x */
    /* compiled from: Poilite */
    public static final class x extends MessageNano {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;

        public x() {
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

    /* renamed from: c$y */
    /* compiled from: Poilite */
    public static final class y extends MessageNano {
        private static volatile y[] d;
        public String a;
        public z[] b;
        public bh[] c;

        public static y[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new y[0];
                    }
                }
            }
            return d;
        }

        public y() {
            this.a = "";
            this.b = z.a();
            this.c = bh.a();
            this.cachedSize = -1;
        }

        public final void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            if (!this.a.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.a);
            }
            if (this.b != null && this.b.length > 0) {
                for (z zVar : this.b) {
                    if (zVar != null) {
                        codedOutputByteBufferNano.writeMessage(2, zVar);
                    }
                }
            }
            if (this.c != null && this.c.length > 0) {
                for (bh bhVar : this.c) {
                    if (bhVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, bhVar);
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
                int i = computeSerializedSize;
                for (z zVar : this.b) {
                    if (zVar != null) {
                        i += CodedOutputByteBufferNano.computeMessageSize(2, zVar);
                    }
                }
                computeSerializedSize = i;
            }
            if (this.c != null && this.c.length > 0) {
                for (bh bhVar : this.c) {
                    if (bhVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, bhVar);
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
                    z[] zVarArr = new z[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.b, 0, zVarArr, 0, length);
                    }
                    while (length < zVarArr.length - 1) {
                        zVarArr[length] = new z();
                        codedInputByteBufferNano.readMessage(zVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    zVarArr[length] = new z();
                    codedInputByteBufferNano.readMessage(zVarArr[length]);
                    this.b = zVarArr;
                } else if (readTag == 26) {
                    int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                    int length2 = this.c == null ? 0 : this.c.length;
                    bh[] bhVarArr = new bh[(repeatedFieldArrayLength2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.c, 0, bhVarArr, 0, length2);
                    }
                    while (length2 < bhVarArr.length - 1) {
                        bhVarArr[length2] = new bh();
                        codedInputByteBufferNano.readMessage(bhVarArr[length2]);
                        codedInputByteBufferNano.readTag();
                        length2++;
                    }
                    bhVarArr[length2] = new bh();
                    codedInputByteBufferNano.readMessage(bhVarArr[length2]);
                    this.c = bhVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }

    /* renamed from: c$z */
    /* compiled from: Poilite */
    public static final class z extends MessageNano {
        private static volatile z[] d;
        public String a;
        public String b;
        public bh[] c;

        public static z[] a() {
            if (d == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (d == null) {
                        d = new z[0];
                    }
                }
            }
            return d;
        }

        public z() {
            this.a = "";
            this.b = "";
            this.c = bh.a();
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
                for (bh bhVar : this.c) {
                    if (bhVar != null) {
                        codedOutputByteBufferNano.writeMessage(3, bhVar);
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
                for (bh bhVar : this.c) {
                    if (bhVar != null) {
                        computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(3, bhVar);
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
                    bh[] bhVarArr = new bh[(repeatedFieldArrayLength + length)];
                    if (length != 0) {
                        System.arraycopy(this.c, 0, bhVarArr, 0, length);
                    }
                    while (length < bhVarArr.length - 1) {
                        bhVarArr[length] = new bh();
                        codedInputByteBufferNano.readMessage(bhVarArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    bhVarArr[length] = new bh();
                    codedInputByteBufferNano.readMessage(bhVarArr[length]);
                    this.c = bhVarArr;
                } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    return this;
                }
            }
        }
    }
}
