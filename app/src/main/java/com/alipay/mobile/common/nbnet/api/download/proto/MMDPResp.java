package com.alipay.mobile.common.nbnet.api.download.proto;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class MMDPResp extends Message {
    public static final Boolean DEFAULT_CANRETRY = Boolean.valueOf(true);
    public static final Integer DEFAULT_DATALENGTH = Integer.valueOf(0);
    public static final Integer DEFAULT_ERRCODE = Integer.valueOf(0);
    public static final List<MMDPExtraData> DEFAULT_EXTRA = Collections.emptyList();
    public static final String DEFAULT_FILEID = "";
    public static final Integer DEFAULT_FILELENGTH = Integer.valueOf(0);
    public static final String DEFAULT_FILEMD5 = "";
    public static final Integer DEFAULT_FILEOFFSET = Integer.valueOf(0);
    public static final Long DEFAULT_TIMESTAMP = Long.valueOf(0);
    public static final String DEFAULT_TRACEID = "";
    public static final String DEFAULT_VIA = "";
    public static final int TAG_CANRETRY = 10;
    public static final int TAG_DATALENGTH = 8;
    public static final int TAG_ERRCODE = 4;
    public static final int TAG_EXTRA = 9;
    public static final int TAG_FILEID = 2;
    public static final int TAG_FILELENGTH = 6;
    public static final int TAG_FILEMD5 = 5;
    public static final int TAG_FILEOFFSET = 7;
    public static final int TAG_TIMESTAMP = 3;
    public static final int TAG_TRACEID = 1;
    public static final int TAG_VIA = 11;
    @ProtoField(tag = 10, type = Datatype.BOOL)
    public Boolean canRetry;
    @ProtoField(label = Label.REQUIRED, tag = 8, type = Datatype.UINT32)
    public Integer datalength;
    @ProtoField(label = Label.REQUIRED, tag = 4, type = Datatype.UINT32)
    public Integer errcode;
    @ProtoField(label = Label.REPEATED, tag = 9)
    public List<MMDPExtraData> extra;
    @ProtoField(label = Label.REQUIRED, tag = 2, type = Datatype.STRING)
    public String fileid;
    @ProtoField(tag = 6, type = Datatype.UINT32)
    public Integer filelength;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String filemd5;
    @ProtoField(tag = 7, type = Datatype.UINT32)
    public Integer fileoffset;
    @ProtoField(label = Label.REQUIRED, tag = 3, type = Datatype.UINT64)
    public Long timestamp;
    @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.STRING)
    public String traceid;
    @ProtoField(tag = 11, type = Datatype.STRING)
    public String via;

    public MMDPResp(MMDPResp message) {
        super(message);
        if (message != null) {
            this.traceid = message.traceid;
            this.fileid = message.fileid;
            this.timestamp = message.timestamp;
            this.errcode = message.errcode;
            this.filemd5 = message.filemd5;
            this.filelength = message.filelength;
            this.fileoffset = message.fileoffset;
            this.datalength = message.datalength;
            this.extra = copyOf(message.extra);
            this.canRetry = message.canRetry;
            this.via = message.via;
        }
    }

    public MMDPResp() {
    }

    public final MMDPResp fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.traceid = (String) value;
                break;
            case 2:
                this.fileid = (String) value;
                break;
            case 3:
                this.timestamp = (Long) value;
                break;
            case 4:
                this.errcode = (Integer) value;
                break;
            case 5:
                this.filemd5 = (String) value;
                break;
            case 6:
                this.filelength = (Integer) value;
                break;
            case 7:
                this.fileoffset = (Integer) value;
                break;
            case 8:
                this.datalength = (Integer) value;
                break;
            case 9:
                this.extra = immutableCopyOf((List) value);
                break;
            case 10:
                this.canRetry = (Boolean) value;
                break;
            case 11:
                this.via = (String) value;
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MMDPResp)) {
            return false;
        }
        MMDPResp o = (MMDPResp) other;
        if (!equals((Object) this.traceid, (Object) o.traceid) || !equals((Object) this.fileid, (Object) o.fileid) || !equals((Object) this.timestamp, (Object) o.timestamp) || !equals((Object) this.errcode, (Object) o.errcode) || !equals((Object) this.filemd5, (Object) o.filemd5) || !equals((Object) this.filelength, (Object) o.filelength) || !equals((Object) this.fileoffset, (Object) o.fileoffset) || !equals((Object) this.datalength, (Object) o.datalength) || !equals(this.extra, o.extra) || !equals((Object) this.canRetry, (Object) o.canRetry) || !equals((Object) this.via, (Object) o.via)) {
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
        int i9 = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.traceid != null ? this.traceid.hashCode() : 0) * 37;
        if (this.fileid != null) {
            i = this.fileid.hashCode();
        } else {
            i = 0;
        }
        int i10 = (i + hashCode) * 37;
        if (this.timestamp != null) {
            i2 = this.timestamp.hashCode();
        } else {
            i2 = 0;
        }
        int i11 = (i2 + i10) * 37;
        if (this.errcode != null) {
            i3 = this.errcode.hashCode();
        } else {
            i3 = 0;
        }
        int i12 = (i3 + i11) * 37;
        if (this.filemd5 != null) {
            i4 = this.filemd5.hashCode();
        } else {
            i4 = 0;
        }
        int i13 = (i4 + i12) * 37;
        if (this.filelength != null) {
            i5 = this.filelength.hashCode();
        } else {
            i5 = 0;
        }
        int i14 = (i5 + i13) * 37;
        if (this.fileoffset != null) {
            i6 = this.fileoffset.hashCode();
        } else {
            i6 = 0;
        }
        int i15 = (i6 + i14) * 37;
        if (this.datalength != null) {
            i7 = this.datalength.hashCode();
        } else {
            i7 = 0;
        }
        int hashCode2 = ((this.extra != null ? this.extra.hashCode() : 1) + ((i7 + i15) * 37)) * 37;
        if (this.canRetry != null) {
            i8 = this.canRetry.hashCode();
        } else {
            i8 = 0;
        }
        int i16 = (i8 + hashCode2) * 37;
        if (this.via != null) {
            i9 = this.via.hashCode();
        }
        int result2 = i16 + i9;
        this.hashCode = result2;
        return result2;
    }
}
