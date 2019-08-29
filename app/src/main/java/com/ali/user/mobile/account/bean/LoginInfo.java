package com.ali.user.mobile.account.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.HashMap;
import java.util.Map;

public class LoginInfo implements Parcelable {
    public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {
        public final LoginInfo[] newArray(int i) {
            return new LoginInfo[i];
        }

        public final LoginInfo createFromParcel(Parcel parcel) {
            return new LoginInfo(parcel);
        }
    };
    private String account;
    private boolean forAutoLogin;
    private boolean isMobileUser;
    private boolean isTaobaoQUser;
    private String pwdInputed;
    private String userHeadImg;
    private Map<String, String> userMap = new HashMap();
    private String userStatus;

    public int describeContents() {
        return 0;
    }

    public LoginInfo() {
    }

    public LoginInfo(Parcel parcel) {
        this.account = parcel.readString();
        boolean[] zArr = new boolean[1];
        parcel.readBooleanArray(zArr);
        this.forAutoLogin = zArr[0];
        boolean[] zArr2 = new boolean[1];
        parcel.readBooleanArray(zArr2);
        this.isMobileUser = zArr2[0];
        parcel.readMap(this.userMap, getClass().getClassLoader());
        this.pwdInputed = parcel.readString();
        this.userHeadImg = parcel.readString();
        this.userStatus = parcel.readString();
        boolean[] zArr3 = new boolean[1];
        parcel.readBooleanArray(zArr3);
        this.isTaobaoQUser = zArr3[0];
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String str) {
        this.account = str;
    }

    public boolean isForAutoLogin() {
        return this.forAutoLogin;
    }

    public void setForAutoLogin(boolean z) {
        this.forAutoLogin = z;
    }

    public boolean isMobileUser() {
        return this.isMobileUser;
    }

    public void setMobileUser(boolean z) {
        this.isMobileUser = z;
    }

    public Map<String, String> getUserMap() {
        return this.userMap;
    }

    public void setUserMap(Map<String, String> map) {
        this.userMap = map;
    }

    public String getPwdInputed() {
        return this.pwdInputed;
    }

    public void setPwdInputed(String str) {
        this.pwdInputed = str;
    }

    public String getUserHeadImg() {
        return this.userHeadImg;
    }

    public void setUserHeadImg(String str) {
        this.userHeadImg = str;
    }

    public String getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(String str) {
        this.userStatus = str;
    }

    public boolean isTaobaoQUser() {
        return this.isTaobaoQUser;
    }

    public void setTaobaoQUser(boolean z) {
        this.isTaobaoQUser = z;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.account);
        parcel.writeBooleanArray(new boolean[]{this.forAutoLogin});
        parcel.writeBooleanArray(new boolean[]{this.isMobileUser});
        parcel.writeMap(this.userMap);
        parcel.writeString(this.pwdInputed);
        parcel.writeString(this.userHeadImg);
        parcel.writeString(this.userStatus);
        parcel.writeBooleanArray(new boolean[]{this.isTaobaoQUser});
    }
}
