package com.amap.bundle.lotuspool.internal.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.server.aos.serverkey;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressFBWarnings({"WMI_WRONG_MAP_ITERATOR"})
public class Command implements Parcelable {
    public static final Creator<Command> CREATOR = new Creator<Command>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Command[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new Command(parcel);
        }
    };
    public String a;
    public long b;
    public long c;
    public long d;
    public int e;
    public int f;
    public String g;
    public int h;
    public long i;
    public long j;
    public long k;
    private Map<String, String> l;

    public int describeContents() {
        return 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
        r8 = r0;
     */
    /* JADX WARNING: Illegal instructions before constructor call commented (this can break semantics) */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Command(java.lang.String r11, long r12, long r14, int r16, org.json.JSONObject r17) {
        /*
            r10 = this;
            java.lang.String r0 = "commandName"
            r9 = r17
            java.lang.String r0 = r9.optString(r0)
            java.lang.String r1 = "setconfig"
            boolean r1 = r1.equalsIgnoreCase(r0)
            r2 = 6
            if (r1 == 0) goto L_0x0015
            r0 = 1
            r8 = 1
            goto L_0x005f
        L_0x0015:
            java.lang.String r1 = "deletefile"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 == 0) goto L_0x0020
            r0 = 2
            r8 = 2
            goto L_0x005f
        L_0x0020:
            java.lang.String r1 = "uploadfile"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 == 0) goto L_0x002c
            r0 = 3
            r8 = 3
            goto L_0x005f
        L_0x002c:
            java.lang.String r1 = "replacefile"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 == 0) goto L_0x0038
            r0 = 4
            r8 = 4
            goto L_0x005f
        L_0x0038:
            java.lang.String r1 = "hotfix"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 == 0) goto L_0x0043
            r0 = 5
            r8 = 5
            goto L_0x005f
        L_0x0043:
            java.lang.String r1 = "executefile"
            boolean r1 = r1.equalsIgnoreCase(r0)
            if (r1 == 0) goto L_0x004d
            r8 = 6
            goto L_0x005f
        L_0x004d:
            boolean r1 = defpackage.xi.b(r0)
            if (r1 == 0) goto L_0x005d
            int r0 = java.lang.Integer.parseInt(r0)
            if (r0 <= 0) goto L_0x005d
            if (r0 > r2) goto L_0x005d
            r8 = r0
            goto L_0x005f
        L_0x005d:
            r0 = 0
            r8 = 0
        L_0x005f:
            r1 = r10
            r2 = r11
            r3 = r12
            r5 = r14
            r7 = r16
            r1.<init>(r2, r3, r5, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.lotuspool.internal.model.bean.Command.<init>(java.lang.String, long, long, int, org.json.JSONObject):void");
    }

    public Command() {
    }

    public final boolean a() {
        try {
            JSONObject jSONObject = new JSONObject(serverkey.amapDecode(this.g));
            StringBuilder sb = new StringBuilder("source cmd=");
            sb.append(serverkey.amapDecode(this.g));
            AMapLog.d("lotuspool", sb.toString());
            if (this.c != jSONObject.optLong("_uuid")) {
                AMapLog.e("lotuspool", "verify uuid err!");
                return false;
            } else if (!TextUtils.equals(this.a, jSONObject.optString("_dispatchId"))) {
                AMapLog.e("lotuspool", "verify dispatchId err!");
                return false;
            } else if (this.d != jSONObject.optLong("_timestamp")) {
                AMapLog.e("lotuspool", "verify dispatchTime err!");
                return false;
            } else {
                this.h = jSONObject.optInt("_cmd_type", 0);
                this.i = jSONObject.optLong("_command_id");
                this.j = jSONObject.optLong("delay_time", 0);
                this.k = jSONObject.optLong("limit_time", 0);
                JSONArray names = jSONObject.names();
                int length = names.length();
                this.l = new HashMap(length);
                for (int i2 = 0; i2 < length; i2++) {
                    String optString = names.optString(i2);
                    if (!TextUtils.isEmpty(optString) && !"_cmd_type".equalsIgnoreCase(optString)) {
                        this.l.put(optString, jSONObject.optString(optString));
                    }
                }
                return true;
            }
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder("parseCmd err=");
            sb2.append(e2.getLocalizedMessage());
            AMapLog.e("lotuspool", sb2.toString());
            return false;
        }
    }

    public final boolean a(String str) {
        return this.l != null && !TextUtils.isEmpty(this.l.get(str));
    }

    private Command(String str, long j2, long j3, int i2, int i3, JSONObject jSONObject) {
        this.a = str;
        this.b = j2;
        this.d = j3;
        this.e = i2;
        this.h = i3;
        this.f = jSONObject.optInt("sequence");
        this.i = jSONObject.optLong("commandId");
        this.c = j2 - (((long) this.f) << 32);
        JSONObject optJSONObject = jSONObject.optJSONObject("params");
        if (optJSONObject == null) {
            this.g = b();
            return;
        }
        this.j = optJSONObject.optLong("delay_time", 0);
        this.k = optJSONObject.optLong("limit_time", -1);
        JSONArray names = optJSONObject.names();
        int length = names.length();
        if (length <= 0) {
            this.g = b();
            return;
        }
        this.l = new HashMap(length);
        for (int i4 = 0; i4 < length; i4++) {
            String optString = names.optString(i4);
            this.l.put(optString, optJSONObject.optString(optString));
        }
        this.g = b();
    }

    public final int b(String str) {
        if (this.l == null || this.l.isEmpty() || !this.l.containsKey(str)) {
            throw new InvalidParameterException();
        }
        String str2 = this.l.get(str);
        if (xi.b(str2)) {
            return Integer.parseInt(str2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(" is not number");
        throw new InvalidParameterException(sb.toString());
    }

    public final int c(String str) {
        try {
            return b(str);
        } catch (InvalidParameterException unused) {
            return 4;
        }
    }

    public final String e(String str) {
        if (this.l != null && !this.l.isEmpty() && this.l.containsKey(str)) {
            return this.l.get(str);
        }
        throw new InvalidParameterException();
    }

    public final String a(String str, String str2) {
        try {
            return e(str);
        } catch (InvalidParameterException unused) {
            return str2;
        }
    }

    public Command(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readLong();
        this.c = parcel.readLong();
        this.d = parcel.readLong();
        this.e = parcel.readInt();
        this.f = parcel.readInt();
        this.h = parcel.readInt();
        this.i = parcel.readLong();
        this.j = parcel.readLong();
        this.k = parcel.readLong();
        this.l = parcel.readHashMap(HashMap.class.getClassLoader());
        this.g = b();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.a);
        parcel.writeLong(this.b);
        parcel.writeLong(this.c);
        parcel.writeLong(this.d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
        parcel.writeInt(this.h);
        parcel.writeLong(this.i);
        parcel.writeLong(this.j);
        parcel.writeLong(this.k);
        parcel.writeMap(this.l);
    }

    public final long d(String str) {
        try {
            if (this.l != null && !this.l.isEmpty()) {
                if (this.l.containsKey(str)) {
                    String str2 = this.l.get(str);
                    if (xi.b(str2)) {
                        return Long.parseLong(str2);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(" is not number");
                    throw new InvalidParameterException(sb.toString());
                }
            }
            throw new InvalidParameterException();
        } catch (InvalidParameterException unused) {
            return 104857600;
        }
    }

    private String b() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("_cmd_type", this.h);
            jSONObject.put("_command_id", this.i);
            if (this.j != 0) {
                jSONObject.put("delay_time", this.j);
            }
            if (this.k >= 0) {
                jSONObject.put("limit_time", this.k);
            }
            if (this.l != null && !this.l.isEmpty()) {
                for (String next : this.l.keySet()) {
                    String str = this.l.get(next);
                    if (!TextUtils.isEmpty(str)) {
                        jSONObject.put(next, str);
                    }
                }
            }
            jSONObject.put("_uuid", this.c);
            jSONObject.put("_dispatchId", this.a);
            jSONObject.put("_timestamp", this.d);
            return serverkey.amapEncode(jSONObject.toString());
        } catch (JSONException unused) {
            return null;
        }
    }
}
