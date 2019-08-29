package com.alipay.mobileappcommon.biz.rpc.pginfo.model;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class ClientPGInfoRespPB extends Message {
    public static final List<PgDataPB> DEFAULT_FATIGUEDATA = Collections.emptyList();
    public static final String DEFAULT_LASTTIME = "";
    public static final List<PgTemplateInfoDataPB> DEFAULT_PGDATA = Collections.emptyList();
    public static final List<String> DEFAULT_PGDELETEDATA = Collections.emptyList();
    public static final Boolean DEFAULT_SUCCESS = Boolean.valueOf(false);
    public static final int TAG_FATIGUEDATA = 3;
    public static final int TAG_LASTTIME = 2;
    public static final int TAG_PGDATA = 4;
    public static final int TAG_PGDELETEDATA = 5;
    public static final int TAG_SUCCESS = 1;
    @ProtoField(label = Label.REPEATED, tag = 3)
    public List<PgDataPB> fatigueData;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String lastTime;
    @ProtoField(label = Label.REPEATED, tag = 4)
    public List<PgTemplateInfoDataPB> pgData;
    @ProtoField(label = Label.REPEATED, tag = 5, type = Datatype.STRING)
    public List<String> pgDeleteData;
    @ProtoField(tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public ClientPGInfoRespPB() {
    }

    public ClientPGInfoRespPB(ClientPGInfoRespPB clientPGInfoRespPB) {
        super(clientPGInfoRespPB);
        if (clientPGInfoRespPB != null) {
            this.success = clientPGInfoRespPB.success;
            this.lastTime = clientPGInfoRespPB.lastTime;
            this.fatigueData = copyOf(clientPGInfoRespPB.fatigueData);
            this.pgData = copyOf(clientPGInfoRespPB.pgData);
            this.pgDeleteData = copyOf(clientPGInfoRespPB.pgDeleteData);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ClientPGInfoRespPB)) {
            return false;
        }
        ClientPGInfoRespPB clientPGInfoRespPB = (ClientPGInfoRespPB) obj;
        return equals((Object) this.success, (Object) clientPGInfoRespPB.success) && equals((Object) this.lastTime, (Object) clientPGInfoRespPB.lastTime) && equals(this.fatigueData, clientPGInfoRespPB.fatigueData) && equals(this.pgData, clientPGInfoRespPB.pgData) && equals(this.pgDeleteData, clientPGInfoRespPB.pgDeleteData);
    }

    public final ClientPGInfoRespPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.success = (Boolean) obj;
                break;
            case 2:
                this.lastTime = (String) obj;
                break;
            case 3:
                this.fatigueData = immutableCopyOf((List) obj);
                break;
            case 4:
                this.pgData = immutableCopyOf((List) obj);
                break;
            case 5:
                this.pgDeleteData = immutableCopyOf((List) obj);
                break;
        }
        return this;
    }

    public final int hashCode() {
        int i = 0;
        int i2 = 1;
        int i3 = this.hashCode;
        if (i3 != 0) {
            return i3;
        }
        int hashCode = (this.success != null ? this.success.hashCode() : 0) * 37;
        if (this.lastTime != null) {
            i = this.lastTime.hashCode();
        }
        int hashCode2 = ((this.pgData != null ? this.pgData.hashCode() : 1) + (((this.fatigueData != null ? this.fatigueData.hashCode() : 1) + ((hashCode + i) * 37)) * 37)) * 37;
        if (this.pgDeleteData != null) {
            i2 = this.pgDeleteData.hashCode();
        }
        int i4 = hashCode2 + i2;
        this.hashCode = i4;
        return i4;
    }
}
