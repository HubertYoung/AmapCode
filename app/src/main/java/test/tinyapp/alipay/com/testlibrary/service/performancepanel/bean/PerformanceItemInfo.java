package test.tinyapp.alipay.com.testlibrary.service.performancepanel.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

public class PerformanceItemInfo implements Parcelable {
    public static final Creator<PerformanceItemInfo> CREATOR = new Creator<PerformanceItemInfo>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        private static PerformanceItemInfo a(Parcel in) {
            return new PerformanceItemInfo(in);
        }

        private static PerformanceItemInfo[] a(int size) {
            return new PerformanceItemInfo[size];
        }
    };
    private String a;
    private String b;
    private String c;

    private PerformanceItemInfo(String category, String performanceItemName, String performanceItemValue) {
        this.a = category;
        this.b = performanceItemName;
        this.c = performanceItemValue;
    }

    public static PerformanceItemInfo a(String category, String performanceItemName, String performanceItemValue) {
        return new PerformanceItemInfo(category, performanceItemName, performanceItemValue);
    }

    private String c() {
        return this.a;
    }

    public final String a() {
        return this.b;
    }

    public final String b() {
        return this.c;
    }

    public boolean equals(Object itemInfo) {
        if (itemInfo == null || !(itemInfo instanceof PerformanceItemInfo)) {
            return false;
        }
        PerformanceItemInfo rhs = (PerformanceItemInfo) itemInfo;
        if (!this.a.equals(rhs.c()) || !this.b.equals(rhs.a()) || !this.c.equals(rhs.b())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((((this.a.hashCode() + 17) * 17) + this.b.hashCode()) * 17) + this.c.hashCode();
    }

    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Category", this.a);
            jsonObject.put("PerformanceItemName", this.b);
            jsonObject.put("PerformanceItemValue", this.c);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public PerformanceItemInfo(Parcel in) {
        this.a = in.readString();
        this.b = in.readString();
        this.c = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeString(this.b);
        dest.writeString(this.c);
    }
}
