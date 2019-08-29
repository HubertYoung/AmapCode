package com.alipay.mobile.liteprocess.rpc;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;
import java.util.HashMap;
import java.util.Map.Entry;

public class LiteRpcInvokeContext extends InnerRpcInvokeContext implements Parcelable {
    public static final Creator<LiteRpcInvokeContext> CREATOR = new Creator<LiteRpcInvokeContext>() {
        public final LiteRpcInvokeContext createFromParcel(Parcel source) {
            return new LiteRpcInvokeContext(source);
        }

        public final LiteRpcInvokeContext[] newArray(int size) {
            return new LiteRpcInvokeContext[size];
        }
    };

    public LiteRpcInvokeContext(InnerRpcInvokeContext innerRpcInvokeContext) {
        this.timeout = innerRpcInvokeContext.timeout;
        this.gwUrl = innerRpcInvokeContext.gwUrl;
        this.requestHeaders = innerRpcInvokeContext.requestHeaders;
        this.compress = innerRpcInvokeContext.compress;
        this.appKey = innerRpcInvokeContext.appKey;
        this.resetCookie = innerRpcInvokeContext.resetCookie;
        this.bgRpc = innerRpcInvokeContext.bgRpc;
        this.responseHeader = innerRpcInvokeContext.responseHeader;
        this.allowRetry = innerRpcInvokeContext.allowRetry;
        this.isUrgent = innerRpcInvokeContext.isUrgent;
        this.isRpcV2 = innerRpcInvokeContext.isRpcV2;
        this.allowBgLogin = innerRpcInvokeContext.allowBgLogin;
        this.allowNonNet = innerRpcInvokeContext.allowNonNet;
        this.rpcInterceptorList = innerRpcInvokeContext.getRpcInterceptorList();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2;
        byte b3;
        byte b4 = 1;
        dest.writeLong(this.timeout);
        dest.writeString(this.gwUrl);
        if (this.requestHeaders == null) {
            this.requestHeaders = new HashMap();
        }
        dest.writeInt(this.requestHeaders.size());
        for (Entry entry : this.requestHeaders.entrySet()) {
            dest.writeString((String) entry.getKey());
            dest.writeString((String) entry.getValue());
        }
        dest.writeValue(this.compress);
        dest.writeString(this.appKey);
        dest.writeValue(this.resetCookie);
        dest.writeValue(this.bgRpc);
        if (this.responseHeader == null) {
            this.responseHeader = new HashMap();
        }
        dest.writeInt(this.responseHeader.size());
        for (Entry entry2 : this.responseHeader.entrySet()) {
            dest.writeString((String) entry2.getKey());
            dest.writeString((String) entry2.getValue());
        }
        dest.writeValue(this.allowRetry);
        if (this.isUrgent) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        if (this.isRpcV2) {
            b2 = 1;
        } else {
            b2 = 0;
        }
        dest.writeByte(b2);
        if (this.allowBgLogin) {
            b3 = 1;
        } else {
            b3 = 0;
        }
        dest.writeByte(b3);
        if (!this.allowNonNet) {
            b4 = 0;
        }
        dest.writeByte(b4);
    }

    public LiteRpcInvokeContext() {
    }

    protected LiteRpcInvokeContext(Parcel in) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        this.timeout = in.readLong();
        this.gwUrl = in.readString();
        int requestHeadersSize = in.readInt();
        this.requestHeaders = new HashMap(requestHeadersSize);
        for (int i = 0; i < requestHeadersSize; i++) {
            this.requestHeaders.put(in.readString(), in.readString());
        }
        this.compress = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.appKey = in.readString();
        this.resetCookie = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.bgRpc = (Boolean) in.readValue(Boolean.class.getClassLoader());
        int responseHeaderSize = in.readInt();
        this.responseHeader = new HashMap(responseHeaderSize);
        for (int i2 = 0; i2 < responseHeaderSize; i2++) {
            this.responseHeader.put(in.readString(), in.readString());
        }
        this.allowRetry = (Boolean) in.readValue(Boolean.class.getClassLoader());
        if (in.readByte() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.isUrgent = z;
        if (in.readByte() != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.isRpcV2 = z2;
        if (in.readByte() != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.allowBgLogin = z3;
        this.allowNonNet = in.readByte() == 0 ? false : z4;
    }
}
