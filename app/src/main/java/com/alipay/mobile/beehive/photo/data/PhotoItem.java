package com.alipay.mobile.beehive.photo.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.alipay.mobile.beehive.service.PhotoInfo;

public class PhotoItem extends PhotoInfo {
    public static final Creator<PhotoItem> CREATOR = new Creator<PhotoItem>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        private static PhotoItem a(Parcel in) {
            return new PhotoItem(in);
        }

        private static PhotoItem[] a(int size) {
            return new PhotoItem[size];
        }
    };
    public static final int TYPE_PHOTO_GIF = 10;
    public boolean isDisabledByIntercept;
    public boolean isLoadedOnce;
    public String modifyTimeDesc;
    private boolean selectable;
    public boolean takePhoto;

    public PhotoItem() {
        this(null, false, "", "");
    }

    public PhotoItem(String photoPath) {
        this(photoPath, false, "", "");
    }

    public PhotoItem(String photoPath, boolean isSelected) {
        this(photoPath, isSelected, "", "");
    }

    public PhotoItem(String photoPath, boolean isSelected, String leftPhotoText, String rightPhotoText) {
        super(photoPath);
        setSelected(isSelected);
        setLeftText(leftPhotoText);
        setRightText(rightPhotoText);
        this.selectable = true;
        this.takePhoto = false;
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public PhotoItem(Parcel in) {
        boolean z;
        // boolean z2 = true;
        super(in);
        if (in.readInt() > 0) {
            z = true;
        } else {
            z = false;
        }
        this.selectable = z;
        this.takePhoto = in.readInt() <= 0 ? false : z2;
    }

    public PhotoItem(PhotoInfo photoInfo) {
        super(photoInfo);
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public void setSelectable(boolean selectable2) {
        this.selectable = selectable2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        super.writeToParcel(dest, flags);
        if (this.selectable) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        if (!this.takePhoto) {
            i2 = 0;
        }
        dest.writeInt(i2);
    }
}
