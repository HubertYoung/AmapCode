package com.alipay.mobile.beehive.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import java.util.HashMap;

public class PhotoMenu implements Parcelable {
    public static final Creator<PhotoMenu> CREATOR = new Creator<PhotoMenu>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        private static PhotoMenu a(Parcel in) {
            return new PhotoMenu(in);
        }

        private static PhotoMenu[] a(int size) {
            return new PhotoMenu[size];
        }
    };
    public static final Byte SUPPORT_PHOTO = Byte.valueOf(4);
    public static final Byte SUPPORT_VIDEO = Byte.valueOf(2);
    public static final Byte SUPPORT_VIDEO_ORI = Byte.valueOf(1);
    public static final String TAG = "PhotoMenu";
    public static final String TAG_COLLECT = "collect";
    public static final String TAG_DELETE = "delete";
    public static final String TAG_SAVE = "save";
    public static final String TAG_SCAN_QR = "scan_qr";
    public static final String TAG_SHARE = "share";
    public String bizCode;
    public boolean enableImpl;
    public boolean enabled;
    public String extra;
    public String extra2;
    public String extra3;
    public HashMap<String, String> spmExtra;
    public String spmID;
    public Byte supportType;
    public String tag;
    public String title;

    public void setMenuSupportType(byte supportType2) {
        this.supportType = Byte.valueOf(supportType2);
    }

    public boolean isVideoSupport() {
        return (this.supportType.byteValue() & SUPPORT_VIDEO.byteValue()) > 0;
    }

    public boolean isPhotoSupport() {
        return (this.supportType.byteValue() & SUPPORT_PHOTO.byteValue()) > 0;
    }

    public boolean isVideoOriSupport() {
        return (this.supportType.byteValue() & SUPPORT_VIDEO_ORI.byteValue()) > 0;
    }

    public PhotoMenu(String title2, String tag2) {
        this(title2, tag2, true);
    }

    public PhotoMenu(String title2, String tag2, boolean enableImpl2) {
        this.supportType = SUPPORT_PHOTO;
        this.title = title2;
        this.tag = tag2;
        this.extra = null;
        this.enableImpl = enableImpl2;
        this.enabled = true;
    }

    public PhotoMenu(Parcel in) {
        boolean z = true;
        this.supportType = SUPPORT_PHOTO;
        this.title = in.readString();
        this.tag = in.readString();
        this.extra = in.readString();
        this.enableImpl = in.readInt() == 1;
        this.enabled = in.readInt() != 1 ? false : z;
        this.supportType = Byte.valueOf(in.readByte());
        this.extra2 = in.readString();
        this.extra3 = in.readString();
        this.spmID = in.readString();
        this.bizCode = in.readString();
        try {
            this.spmExtra = in.readHashMap(HashMap.class.getClassLoader());
        } catch (Exception e) {
            PhotoLogger.warn((String) TAG, "read spmExtra exception :" + e.getMessage());
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i = 1;
        dest.writeString(this.title);
        dest.writeString(this.tag);
        dest.writeString(this.extra);
        dest.writeInt(this.enableImpl ? 1 : 0);
        if (!this.enabled) {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeByte(this.supportType.byteValue());
        dest.writeString(this.extra2);
        dest.writeString(this.extra3);
        dest.writeString(this.spmID);
        dest.writeString(this.bizCode);
        try {
            dest.writeMap(this.spmExtra);
        } catch (Exception e) {
            PhotoLogger.warn((String) TAG, "write spmExtra exception :" + e.getMessage());
        }
    }
}
