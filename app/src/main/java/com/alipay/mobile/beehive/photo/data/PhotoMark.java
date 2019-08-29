package com.alipay.mobile.beehive.photo.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PhotoMark implements Parcelable {
    public static final Creator<PhotoMark> CREATOR = new Creator<PhotoMark>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        private static PhotoMark a(Parcel in) {
            return new PhotoMark(in);
        }

        private static PhotoMark[] a(int size) {
            return new PhotoMark[size];
        }
    };
    private static final int EMPTY_FLAG = 65535;
    private int markHeight;
    private String markId;
    private int markWidth;
    private int paddingX;
    private int paddingY;
    private Integer percent;
    private int position;
    private int transparency;

    public static class Builder {
        /* access modifiers changed from: private */
        public int markHeight;
        /* access modifiers changed from: private */
        public String markId;
        /* access modifiers changed from: private */
        public int markWidth;
        /* access modifiers changed from: private */
        public int paddingX;
        /* access modifiers changed from: private */
        public int paddingY;
        /* access modifiers changed from: private */
        public Integer percent;
        /* access modifiers changed from: private */
        public int position;
        /* access modifiers changed from: private */
        public int transparency;

        public Builder(String markId2) {
            this.markId = markId2;
        }

        public Builder markId(String markId2) {
            this.markId = markId2;
            return this;
        }

        public Builder position(int position2) {
            this.position = position2;
            return this;
        }

        public Builder transparency(int transparency2) {
            this.transparency = transparency2;
            return this;
        }

        public Builder markWidth(int markWidth2) {
            this.markWidth = markWidth2;
            return this;
        }

        public Builder markHeight(int markHeight2) {
            this.markHeight = markHeight2;
            return this;
        }

        public Builder paddingX(int paddingX2) {
            this.paddingX = paddingX2;
            return this;
        }

        public Builder paddingY(int paddingY2) {
            this.paddingY = paddingY2;
            return this;
        }

        public Builder percent(Integer percent2) {
            this.percent = percent2;
            return this;
        }

        public Builder percent(int percent2) {
            this.percent = Integer.valueOf(percent2);
            return this;
        }

        public PhotoMark build() {
            return new PhotoMark(this);
        }
    }

    public PhotoMark(String markId2) {
        this.markId = markId2;
    }

    public PhotoMark(Parcel in) {
        this.markId = in.readString();
        this.position = in.readInt();
        this.transparency = in.readInt();
        this.markWidth = in.readInt();
        this.markHeight = in.readInt();
        this.paddingX = in.readInt();
        this.paddingY = in.readInt();
        int percent2 = in.readInt();
        this.percent = percent2 == 65535 ? null : Integer.valueOf(percent2);
    }

    public PhotoMark(PhotoMark photoMark) {
        this.markId = photoMark.markId;
        this.position = photoMark.position;
        this.transparency = photoMark.transparency;
        this.markWidth = photoMark.markWidth;
        this.markHeight = photoMark.markHeight;
        this.paddingX = photoMark.paddingX;
        this.paddingY = photoMark.paddingY;
        this.percent = photoMark.percent;
    }

    public PhotoMark(Builder builder) {
        this.markId = builder.markId;
        this.position = builder.position;
        this.transparency = builder.transparency;
        this.markWidth = builder.markWidth;
        this.markHeight = builder.markHeight;
        this.paddingX = builder.paddingX;
        this.paddingY = builder.paddingY;
        this.percent = builder.percent;
    }

    public String getMarkId() {
        return this.markId;
    }

    public int getPosition() {
        return this.position;
    }

    public int getTransparency() {
        return this.transparency;
    }

    public int getMarkWidth() {
        return this.markWidth;
    }

    public int getMarkHeight() {
        return this.markHeight;
    }

    public int getPaddingX() {
        return this.paddingX;
    }

    public int getPaddingY() {
        return this.paddingY;
    }

    public Integer getPercent() {
        return this.percent;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.markId);
        dest.writeInt(this.position);
        dest.writeInt(this.transparency);
        dest.writeInt(this.markWidth);
        dest.writeInt(this.markHeight);
        dest.writeInt(this.paddingX);
        dest.writeInt(this.paddingY);
        if (this.percent != null) {
            dest.writeInt(this.percent.intValue());
        } else {
            dest.writeInt(65535);
        }
    }
}
