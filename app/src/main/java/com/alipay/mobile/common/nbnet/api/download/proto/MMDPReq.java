package com.alipay.mobile.common.nbnet.api.download.proto;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;
import okio.ByteString;

public final class MMDPReq extends Message {
    public static final String DEFAULT_BIZTYPE = "";
    public static final MMDPCmdType DEFAULT_COMMAND = MMDPCmdType.FILE_DOWNLOAD;
    public static final List<MMDPExtraData> DEFAULT_EXTRA = Collections.emptyList();
    public static final String DEFAULT_FILEID = "";
    public static final Integer DEFAULT_FILELENGTH = Integer.valueOf(0);
    public static final String DEFAULT_FILEMD5 = "";
    public static final ByteString DEFAULT_PARAMDATA = ByteString.EMPTY;
    public static final Integer DEFAULT_RANGEEND = Integer.valueOf(0);
    public static final Integer DEFAULT_RANGESTART = Integer.valueOf(0);
    public static final MMDPResType DEFAULT_RESTYPE = MMDPResType.FILE;
    public static final MMDPSourceType DEFAULT_SRCTYPE = MMDPSourceType.FILEID;
    public static final Long DEFAULT_TIMESTAMP = Long.valueOf(0);
    public static final String DEFAULT_TRACEID = "";
    public static final String DEFAULT_USERID = "";
    public static final int TAG_BIZTYPE = 8;
    public static final int TAG_COMMAND = 1;
    public static final int TAG_EXTRA = 14;
    public static final int TAG_FILEID = 4;
    public static final int TAG_FILELENGTH = 12;
    public static final int TAG_FILEMD5 = 11;
    public static final int TAG_PARAMDATA = 13;
    public static final int TAG_RANGEEND = 10;
    public static final int TAG_RANGESTART = 9;
    public static final int TAG_RESTYPE = 5;
    public static final int TAG_SRCTYPE = 6;
    public static final int TAG_TIMESTAMP = 3;
    public static final int TAG_TRACEID = 2;
    public static final int TAG_USERID = 7;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String biztype;
    @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.ENUM)
    public MMDPCmdType command;
    @ProtoField(label = Label.REPEATED, tag = 14)
    public List<MMDPExtraData> extra;
    @ProtoField(label = Label.REQUIRED, tag = 4, type = Datatype.STRING)
    public String fileid;
    @ProtoField(tag = 12, type = Datatype.UINT32)
    public Integer filelength;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String filemd5;
    @ProtoField(tag = 13, type = Datatype.BYTES)
    public ByteString paramdata;
    @ProtoField(tag = 10, type = Datatype.UINT32)
    public Integer rangeend;
    @ProtoField(tag = 9, type = Datatype.UINT32)
    public Integer rangestart;
    @ProtoField(label = Label.REQUIRED, tag = 5, type = Datatype.ENUM)
    public MMDPResType restype;
    @ProtoField(label = Label.REQUIRED, tag = 6, type = Datatype.ENUM)
    public MMDPSourceType srctype;
    @ProtoField(label = Label.REQUIRED, tag = 3, type = Datatype.UINT64)
    public Long timestamp;
    @ProtoField(label = Label.REQUIRED, tag = 2, type = Datatype.STRING)
    public String traceid;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String userid;

    public MMDPReq(MMDPReq message) {
        super(message);
        if (message != null) {
            this.command = message.command;
            this.traceid = message.traceid;
            this.timestamp = message.timestamp;
            this.fileid = message.fileid;
            this.restype = message.restype;
            this.srctype = message.srctype;
            this.userid = message.userid;
            this.biztype = message.biztype;
            this.rangestart = message.rangestart;
            this.rangeend = message.rangeend;
            this.filemd5 = message.filemd5;
            this.filelength = message.filelength;
            this.paramdata = message.paramdata;
            this.extra = copyOf(message.extra);
        }
    }

    public MMDPReq() {
    }

    public final MMDPReq fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.command = (MMDPCmdType) value;
                break;
            case 2:
                this.traceid = (String) value;
                break;
            case 3:
                this.timestamp = (Long) value;
                break;
            case 4:
                this.fileid = (String) value;
                break;
            case 5:
                this.restype = (MMDPResType) value;
                break;
            case 6:
                this.srctype = (MMDPSourceType) value;
                break;
            case 7:
                this.userid = (String) value;
                break;
            case 8:
                this.biztype = (String) value;
                break;
            case 9:
                this.rangestart = (Integer) value;
                break;
            case 10:
                this.rangeend = (Integer) value;
                break;
            case 11:
                this.filemd5 = (String) value;
                break;
            case 12:
                this.filelength = (Integer) value;
                break;
            case 13:
                this.paramdata = (ByteString) value;
                break;
            case 14:
                this.extra = immutableCopyOf((List) value);
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MMDPReq)) {
            return false;
        }
        MMDPReq o = (MMDPReq) other;
        if (!equals((Object) this.command, (Object) o.command) || !equals((Object) this.traceid, (Object) o.traceid) || !equals((Object) this.timestamp, (Object) o.timestamp) || !equals((Object) this.fileid, (Object) o.fileid) || !equals((Object) this.restype, (Object) o.restype) || !equals((Object) this.srctype, (Object) o.srctype) || !equals((Object) this.userid, (Object) o.userid) || !equals((Object) this.biztype, (Object) o.biztype) || !equals((Object) this.rangestart, (Object) o.rangestart) || !equals((Object) this.rangeend, (Object) o.rangeend) || !equals((Object) this.filemd5, (Object) o.filemd5) || !equals((Object) this.filelength, (Object) o.filelength) || !equals((Object) this.paramdata, (Object) o.paramdata) || !equals(this.extra, o.extra)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.command != null ? this.command.hashCode() : 0) * 37;
        if (this.traceid != null) {
            i = this.traceid.hashCode();
        } else {
            i = 0;
        }
        int i13 = (i + hashCode) * 37;
        if (this.timestamp != null) {
            i2 = this.timestamp.hashCode();
        } else {
            i2 = 0;
        }
        int i14 = (i2 + i13) * 37;
        if (this.fileid != null) {
            i3 = this.fileid.hashCode();
        } else {
            i3 = 0;
        }
        int i15 = (i3 + i14) * 37;
        if (this.restype != null) {
            i4 = this.restype.hashCode();
        } else {
            i4 = 0;
        }
        int i16 = (i4 + i15) * 37;
        if (this.srctype != null) {
            i5 = this.srctype.hashCode();
        } else {
            i5 = 0;
        }
        int i17 = (i5 + i16) * 37;
        if (this.userid != null) {
            i6 = this.userid.hashCode();
        } else {
            i6 = 0;
        }
        int i18 = (i6 + i17) * 37;
        if (this.biztype != null) {
            i7 = this.biztype.hashCode();
        } else {
            i7 = 0;
        }
        int i19 = (i7 + i18) * 37;
        if (this.rangestart != null) {
            i8 = this.rangestart.hashCode();
        } else {
            i8 = 0;
        }
        int i20 = (i8 + i19) * 37;
        if (this.rangeend != null) {
            i9 = this.rangeend.hashCode();
        } else {
            i9 = 0;
        }
        int i21 = (i9 + i20) * 37;
        if (this.filemd5 != null) {
            i10 = this.filemd5.hashCode();
        } else {
            i10 = 0;
        }
        int i22 = (i10 + i21) * 37;
        if (this.filelength != null) {
            i11 = this.filelength.hashCode();
        } else {
            i11 = 0;
        }
        int i23 = (i11 + i22) * 37;
        if (this.paramdata != null) {
            i12 = this.paramdata.hashCode();
        }
        int result2 = ((i23 + i12) * 37) + (this.extra != null ? this.extra.hashCode() : 1);
        this.hashCode = result2;
        return result2;
    }
}
