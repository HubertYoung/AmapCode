package com.amap.bundle.lotuspool.internal.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.server.aos.serverkey;
import org.json.JSONException;
import org.json.JSONObject;

public class CommandResult implements Parcelable {
    public static final Creator<CommandResult> CREATOR = new Creator<CommandResult>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CommandResult[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CommandResult(parcel);
        }
    };
    private static final String k = "CommandResult";
    public String a;
    public long b;
    public long c;
    public long d;
    public int e;
    public int f;
    public String g;
    public long h;
    public int i;
    public String j;

    public int describeContents() {
        return 0;
    }

    public CommandResult() {
    }

    public CommandResult(String str, long j2, long j3, int i2, long j4, int i3, int i4, String str2) {
        this.a = str;
        this.b = j2;
        this.e = i2;
        this.d = j3;
        this.f = i3;
        this.h = j4;
        this.c = j2 - (((long) i3) << 32);
        this.i = i4;
        this.j = str2;
        c();
    }

    public final String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append("_");
        sb.append(this.d);
        return sb.toString();
    }

    public final boolean b() {
        try {
            JSONObject jSONObject = new JSONObject(serverkey.amapDecode(this.g));
            if (this.c != jSONObject.optLong("_uuid")) {
                if (bno.a) {
                    AMapLog.e(k, "verify uuid err!", true);
                }
                return false;
            } else if (!TextUtils.equals(this.a, jSONObject.optString("_dispatchId"))) {
                if (bno.a) {
                    AMapLog.e(k, "verify dispatchId err!", true);
                }
                return false;
            } else if (this.d != jSONObject.optLong("_timestamp")) {
                if (bno.a) {
                    AMapLog.e(k, "verify dispatchTime err!", true);
                }
                return false;
            } else {
                this.h = jSONObject.optLong("_command_id");
                this.i = jSONObject.optInt("result_code");
                this.j = jSONObject.optString("message", null);
                return true;
            }
        } catch (Exception e2) {
            if (bno.a) {
                String str = k;
                StringBuilder sb = new StringBuilder("parseResult err:");
                sb.append(Log.getStackTraceString(e2));
                AMapLog.e(str, sb.toString(), true);
            }
            return false;
        }
    }

    private void c() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("result_code", this.i);
            jSONObject.put("message", this.j);
            jSONObject.put("_uuid", this.c);
            jSONObject.put("_timestamp", this.d);
            jSONObject.put("_dispatchId", this.a);
            jSONObject.put("_command_id", this.h);
            this.g = serverkey.amapEncode(jSONObject.toString());
        } catch (JSONException unused) {
            AMapLog.e("lotuspool", "resetResult err");
        }
    }

    public CommandResult(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readLong();
        this.d = parcel.readLong();
        this.e = parcel.readInt();
        this.f = parcel.readInt();
        this.h = parcel.readLong();
        this.i = parcel.readInt();
        this.j = parcel.readString();
        this.c = this.b - (((long) this.f) << 32);
        c();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeLong(this.b);
        parcel.writeLong(this.d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
        parcel.writeLong(this.h);
        parcel.writeInt(this.i);
        parcel.writeString(this.j);
    }
}
