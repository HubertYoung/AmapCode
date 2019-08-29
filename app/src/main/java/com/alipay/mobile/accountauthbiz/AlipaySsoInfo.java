package com.alipay.mobile.accountauthbiz;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.ali.user.mobile.log.AliUserLog;

public class AlipaySsoInfo implements Parcelable {
    public static final Creator<AlipaySsoInfo> CREATOR = new Creator<AlipaySsoInfo>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new AlipaySsoInfo[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new AlipaySsoInfo(parcel);
        }
    };
    private static final String TAG = "AlipaySsoInfo";
    public String headImg;
    public String loginId;
    public String ssoToken;
    public String userId;

    public int describeContents() {
        return 0;
    }

    public AlipaySsoInfo() {
    }

    public AlipaySsoInfo(Parcel parcel) {
        readFromSource(parcel);
    }

    public void readFromSource(Parcel parcel) {
        try {
            this.loginId = parcel.readString();
            this.userId = parcel.readString();
            this.headImg = parcel.readString();
            this.ssoToken = parcel.readString();
        } catch (Exception e) {
            AliUserLog.b((String) TAG, (Throwable) e);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeString(this.loginId);
            parcel.writeString(this.userId);
            parcel.writeString(this.headImg);
            parcel.writeString(this.ssoToken);
        } catch (Exception e) {
            AliUserLog.b((String) TAG, (Throwable) e);
        }
    }
}
