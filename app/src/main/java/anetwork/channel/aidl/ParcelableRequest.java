package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import anet.channel.request.BodyEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParcelableRequest implements Parcelable {
    public static final Creator<ParcelableRequest> CREATOR = new Creator<ParcelableRequest>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ParcelableRequest[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return ParcelableRequest.a(parcel);
        }
    };
    public dj a;
    public BodyEntry b;
    public int c;
    public String d;
    public String e;
    public boolean f;
    public String g;
    public Map<String, String> h = null;
    public Map<String, String> i = null;
    public int j;
    public int k;
    public String l;
    public String m;
    public Map<String, String> n;

    public int describeContents() {
        return 0;
    }

    public ParcelableRequest(dj djVar) {
        this.a = djVar;
        if (djVar != null) {
            this.d = djVar.a();
            this.c = djVar.e();
            this.e = djVar.g();
            this.f = djVar.b();
            this.g = djVar.d();
            List<dc> c2 = djVar.c();
            if (c2 != null) {
                this.h = new HashMap();
                for (dc next : c2) {
                    this.h.put(next.a(), next.b());
                }
            }
            List<di> f2 = djVar.f();
            if (f2 != null) {
                this.i = new HashMap();
                for (di next2 : f2) {
                    this.i.put(next2.a(), next2.b());
                }
            }
            this.b = djVar.h();
            this.j = djVar.i();
            this.k = djVar.j();
            this.l = djVar.k();
            this.m = djVar.l();
            this.n = djVar.m();
        }
    }

    public ParcelableRequest() {
    }

    public void writeToParcel(Parcel parcel, int i2) {
        if (this.a != null) {
            try {
                parcel.writeInt(this.a.e());
                parcel.writeString(this.d);
                parcel.writeString(this.a.g());
                parcel.writeInt(this.a.b() ? 1 : 0);
                parcel.writeString(this.a.d());
                int i3 = 1;
                parcel.writeInt(this.h == null ? 0 : 1);
                if (this.h != null) {
                    parcel.writeMap(this.h);
                }
                parcel.writeInt(this.i == null ? 0 : 1);
                if (this.i != null) {
                    parcel.writeMap(this.i);
                }
                parcel.writeParcelable(this.b, 0);
                parcel.writeInt(this.a.i());
                parcel.writeInt(this.a.j());
                parcel.writeString(this.a.k());
                parcel.writeString(this.a.l());
                Map<String, String> m2 = this.a.m();
                if (m2 == null) {
                    i3 = 0;
                }
                parcel.writeInt(i3);
                if (m2 != null) {
                    parcel.writeMap(m2);
                }
            } catch (Throwable th) {
                cl.a("anet.ParcelableRequest", "[writeToParcel]", null, th, new Object[0]);
            }
        }
    }

    public static ParcelableRequest a(Parcel parcel) {
        ParcelableRequest parcelableRequest = new ParcelableRequest();
        try {
            parcelableRequest.c = parcel.readInt();
            parcelableRequest.d = parcel.readString();
            parcelableRequest.e = parcel.readString();
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            parcelableRequest.f = z;
            parcelableRequest.g = parcel.readString();
            if (parcel.readInt() != 0) {
                parcelableRequest.h = parcel.readHashMap(ParcelableRequest.class.getClassLoader());
            }
            if (parcel.readInt() != 0) {
                parcelableRequest.i = parcel.readHashMap(ParcelableRequest.class.getClassLoader());
            }
            parcelableRequest.b = (BodyEntry) parcel.readParcelable(ParcelableRequest.class.getClassLoader());
            parcelableRequest.j = parcel.readInt();
            parcelableRequest.k = parcel.readInt();
            parcelableRequest.l = parcel.readString();
            parcelableRequest.m = parcel.readString();
            if (parcel.readInt() != 0) {
                parcelableRequest.n = parcel.readHashMap(ParcelableRequest.class.getClassLoader());
            }
        } catch (Throwable th) {
            cl.a("anet.ParcelableRequest", "[readFromParcel]", null, th, new Object[0]);
        }
        return parcelableRequest;
    }

    public final String a(String str) {
        if (this.n == null) {
            return null;
        }
        return this.n.get(str);
    }
}
