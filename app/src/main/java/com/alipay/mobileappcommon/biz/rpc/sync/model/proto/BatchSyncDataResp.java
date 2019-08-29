package com.alipay.mobileappcommon.biz.rpc.sync.model.proto;

import com.squareup.wire.Message;
import com.squareup.wire.Message.Datatype;
import com.squareup.wire.Message.Label;
import com.squareup.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class BatchSyncDataResp extends Message {
    public static final List<DataMap> DEFAULT_DATAMAP = Collections.emptyList();
    public static final String DEFAULT_REASON = "";
    public static final Boolean DEFAULT_SUCCESS = Boolean.valueOf(false);
    public static final int TAG_DATAMAP = 3;
    public static final int TAG_REASON = 2;
    public static final int TAG_SUCCESS = 1;
    @ProtoField(label = Label.REPEATED, tag = 3)
    public List<DataMap> dataMap;
    @ProtoField(tag = 2, type = Datatype.STRING)
    public String reason;
    @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.BOOL)
    public Boolean success;

    public final class DataMap extends Message {
        public static final String DEFAULT_KEY = "";
        public static final int TAG_KEY = 1;
        public static final int TAG_VALUE = 2;
        @ProtoField(label = Label.REQUIRED, tag = 1, type = Datatype.STRING)
        public String key;
        @ProtoField(label = Label.REQUIRED, tag = 2)
        public SyncData value;

        public DataMap(DataMap message) {
            super(message);
            if (message != null) {
                this.key = message.key;
                this.value = message.value;
            }
        }

        public DataMap() {
        }

        public final DataMap fillTagValue(int tag, Object value2) {
            switch (tag) {
                case 1:
                    this.key = (String) value2;
                    break;
                case 2:
                    this.value = (SyncData) value2;
                    break;
            }
            return this;
        }

        public final boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof DataMap)) {
                return false;
            }
            DataMap o = (DataMap) other;
            if (!equals((Object) this.key, (Object) o.key) || !equals((Object) this.value, (Object) o.value)) {
                return false;
            }
            return true;
        }

        public final int hashCode() {
            int i;
            int i2 = 0;
            int result = this.hashCode;
            if (result != 0) {
                return result;
            }
            if (this.key != null) {
                i = this.key.hashCode();
            } else {
                i = 0;
            }
            int i3 = i * 37;
            if (this.value != null) {
                i2 = this.value.hashCode();
            }
            int result2 = i3 + i2;
            this.hashCode = result2;
            return result2;
        }
    }

    public final class SyncData extends Message {
        public static final String DEFAULT_DATA = "";
        public static final String DEFAULT_TYPE = "";
        public static final String DEFAULT_UPDATETIME = "";
        public static final int TAG_DATA = 2;
        public static final int TAG_TYPE = 1;
        public static final int TAG_UPDATETIME = 3;
        @ProtoField(tag = 2, type = Datatype.STRING)
        public String data;
        @ProtoField(tag = 1, type = Datatype.STRING)
        public String type;
        @ProtoField(tag = 3, type = Datatype.STRING)
        public String updateTime;

        public SyncData(SyncData message) {
            super(message);
            if (message != null) {
                this.type = message.type;
                this.data = message.data;
                this.updateTime = message.updateTime;
            }
        }

        public SyncData() {
        }

        public final SyncData fillTagValue(int tag, Object value) {
            switch (tag) {
                case 1:
                    this.type = (String) value;
                    break;
                case 2:
                    this.data = (String) value;
                    break;
                case 3:
                    this.updateTime = (String) value;
                    break;
            }
            return this;
        }

        public final boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof SyncData)) {
                return false;
            }
            SyncData o = (SyncData) other;
            if (!equals((Object) this.type, (Object) o.type) || !equals((Object) this.data, (Object) o.data) || !equals((Object) this.updateTime, (Object) o.updateTime)) {
                return false;
            }
            return true;
        }

        public final int hashCode() {
            int i;
            int i2 = 0;
            int result = this.hashCode;
            if (result != 0) {
                return result;
            }
            int hashCode = (this.type != null ? this.type.hashCode() : 0) * 37;
            if (this.data != null) {
                i = this.data.hashCode();
            } else {
                i = 0;
            }
            int i3 = (i + hashCode) * 37;
            if (this.updateTime != null) {
                i2 = this.updateTime.hashCode();
            }
            int result2 = i3 + i2;
            this.hashCode = result2;
            return result2;
        }
    }

    public BatchSyncDataResp(BatchSyncDataResp message) {
        super(message);
        if (message != null) {
            this.success = message.success;
            this.reason = message.reason;
            this.dataMap = copyOf(message.dataMap);
        }
    }

    public BatchSyncDataResp() {
    }

    public final BatchSyncDataResp fillTagValue(int tag, Object value) {
        switch (tag) {
            case 1:
                this.success = (Boolean) value;
                break;
            case 2:
                this.reason = (String) value;
                break;
            case 3:
                this.dataMap = immutableCopyOf((List) value);
                break;
        }
        return this;
    }

    public final boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BatchSyncDataResp)) {
            return false;
        }
        BatchSyncDataResp o = (BatchSyncDataResp) other;
        if (!equals((Object) this.success, (Object) o.success) || !equals((Object) this.reason, (Object) o.reason) || !equals(this.dataMap, o.dataMap)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode = (this.success != null ? this.success.hashCode() : 0) * 37;
        if (this.reason != null) {
            i = this.reason.hashCode();
        }
        int result2 = ((hashCode + i) * 37) + (this.dataMap != null ? this.dataMap.hashCode() : 1);
        this.hashCode = result2;
        return result2;
    }
}
