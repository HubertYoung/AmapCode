package com.alipay.mobile.beehive.service;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.mobile.beehive.photo.data.PhotoMark;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.Map;

public class PhotoInfo implements Parcelable {
    public static final Creator<PhotoInfo> CREATOR = new Creator<PhotoInfo>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        private static PhotoInfo a(Parcel in) {
            return new PhotoInfo(in);
        }

        private static PhotoInfo[] a(int size) {
            return new PhotoInfo[size];
        }
    };
    private static final String GIF_SUFFIX = ".gif";
    public static final int TYPE_PHOTO = 0;
    public static final int TYPE_PHOTO_SUB_TYPE_GIF = 100;
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_VIDEO_ORIGNAL = 2;
    private static final long _1K = 1024;
    private static final long _1M = 1048576;
    public Bundle bizExtraParams;
    public Map<String, Object> extraInfo;
    private Drawable fail;
    private boolean haseMark;
    private boolean isAlbumMedia;
    public boolean isGiffSuffix;
    private boolean isInEdited;
    private boolean isPicCurrentlyTaked;
    private int largePhotoHeight;
    private int largePhotoWidth;
    private double latitude;
    private String leftText;
    private boolean loadOrigin;
    private Drawable loading;
    private double longitude;
    private int mMediaType;
    private int mediaId;
    private int mediaSubType;
    private long modifiedTime;
    public Size oriPhotoSize;
    private Drawable photo;
    private int photoGroupIndex;
    private int photoHeight;
    private int photoIndex;
    private PhotoMark photoMark;
    private int photoOrientation;
    private String photoPath;
    private long photoSize;
    private int photoWidth;
    private String rightText;
    private boolean selected;
    public String shadowPathInQ;
    private String tag;
    private Drawable thumb;
    private int thumbHeight;
    private String thumbPath;
    private int thumbWidth;
    private long videoDuration;
    public int videoHeight;
    public int videoWidth;
    public Rect viewBoundsOnScreen;

    public int getMediaSubType() {
        return this.mediaSubType;
    }

    public void setMediaSubType(int mediaSubType2) {
        this.mediaSubType = mediaSubType2;
    }

    public boolean isGif() {
        return this.mMediaType == 0 && this.mediaSubType == 100;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude2) {
        this.latitude = latitude2;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longtitude) {
        this.longitude = longtitude;
    }

    public String getVideoSizeStr() {
        if (this.photoSize > 1048576) {
            return (((float) Math.round((float) ((this.photoSize / 1048576) * 10))) / 10.0f) + " MB";
        }
        return (((float) Math.round((float) ((this.photoSize / 1024) * 10))) / 10.0f) + " KB";
    }

    public long getVideoDuration() {
        return this.videoDuration;
    }

    public void setVideoDuration(long videoDuration2) {
        this.videoDuration = videoDuration2;
    }

    public int getVideoWidth() {
        return this.videoWidth;
    }

    public void setVideoWidth(int videoWidth2) {
        this.videoWidth = videoWidth2;
    }

    public int getVideoHeight() {
        return this.videoHeight;
    }

    public void setVideoHeight(int videoHeight2) {
        this.videoHeight = videoHeight2;
    }

    public int getPhotoGroupIndex() {
        return this.photoGroupIndex;
    }

    public void setPhotoGroupIndex(int photoGroupIndex2) {
        this.photoGroupIndex = photoGroupIndex2;
    }

    public void setVideoResolution(String resolution) {
        try {
            String[] r = resolution.split(DictionaryKeys.CTRLXY_X);
            this.videoWidth = Integer.valueOf(r[0]).intValue();
            this.videoHeight = Integer.valueOf(r[1]).intValue();
        } catch (Exception e) {
            PhotoLogger.debug("videoCompact", "解析视频宽高失败：" + e.getMessage());
        }
    }

    public void setMediaType(int mediaType) {
        this.mMediaType = mediaType;
    }

    public int getMediaType() {
        return this.mMediaType;
    }

    public boolean isVideo() {
        return this.mMediaType == 1 || this.mMediaType == 2;
    }

    public void setIsAlbumMedia(boolean isAlbumMedia2) {
        this.isAlbumMedia = isAlbumMedia2;
    }

    public boolean getIsAlbumMedia() {
        return this.isAlbumMedia;
    }

    public void setMediaId(int mediaId2) {
        this.mediaId = mediaId2;
    }

    public int getMediaId() {
        return this.mediaId;
    }

    public boolean isPicCurrentlyTaked() {
        return this.isPicCurrentlyTaked;
    }

    public void setIsPicCurrentlyTaked(boolean isPicCurrentTaked) {
        this.isPicCurrentlyTaked = isPicCurrentTaked;
    }

    public PhotoInfo(String photoPath2) {
        this(photoPath2, "", "");
    }

    public PhotoInfo(String photoPath2, String leftPhotoText, String rightPhotoText) {
        this.mMediaType = 0;
        this.isInEdited = false;
        this.photoPath = photoPath2;
        this.isGiffSuffix = isGifSuffix(photoPath2);
        if (this.isGiffSuffix) {
            this.mediaSubType = 100;
        }
        this.leftText = leftPhotoText;
        this.rightText = rightPhotoText;
        this.photoSize = 0;
        this.photoWidth = 0;
        this.photoHeight = 0;
        this.largePhotoWidth = 0;
        this.largePhotoHeight = 0;
        this.thumbWidth = 0;
        this.thumbHeight = 0;
        this.loadOrigin = false;
        this.selected = false;
        this.photoIndex = -1;
    }

    public PhotoInfo(Parcel in) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5 = true;
        this.mMediaType = 0;
        this.isInEdited = false;
        this.photoPath = in.readString();
        this.thumbPath = in.readString();
        this.leftText = in.readString();
        this.rightText = in.readString();
        this.photoSize = in.readLong();
        this.modifiedTime = in.readLong();
        this.photoWidth = in.readInt();
        this.photoHeight = in.readInt();
        this.largePhotoWidth = in.readInt();
        this.largePhotoHeight = in.readInt();
        this.thumbWidth = in.readInt();
        this.thumbHeight = in.readInt();
        this.loadOrigin = in.readInt() > 0;
        if (in.readInt() > 0) {
            z = true;
        } else {
            z = false;
        }
        this.selected = z;
        this.tag = in.readString();
        this.photoIndex = in.readInt();
        this.photoGroupIndex = in.readInt();
        this.mMediaType = in.readInt();
        this.videoDuration = in.readLong();
        this.videoWidth = in.readInt();
        this.videoHeight = in.readInt();
        if (in.readInt() > 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.haseMark = z2;
        if (this.haseMark) {
            this.photoMark = (PhotoMark) in.readParcelable(PhotoMark.class.getClassLoader());
        }
        this.photoOrientation = in.readInt();
        if (in.readInt() > 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.isPicCurrentlyTaked = z3;
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        if (in.readInt() > 0) {
            z4 = true;
        } else {
            z4 = false;
        }
        this.isGiffSuffix = z4;
        this.mediaSubType = in.readInt();
        this.mediaId = in.readInt();
        this.isAlbumMedia = in.readInt() <= 0 ? false : z5;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0074, code lost:
        r0 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PhotoInfo(com.alipay.mobile.beehive.service.PhotoInfo r5) {
        /*
            r4 = this;
            r0 = 0
            r4.<init>()
            r4.mMediaType = r0
            r4.isInEdited = r0
            java.lang.String r1 = r5.photoPath
            r4.photoPath = r1
            android.graphics.drawable.Drawable r1 = r5.photo
            r4.photo = r1
            java.lang.String r1 = r5.leftText
            r4.leftText = r1
            java.lang.String r1 = r5.rightText
            r4.rightText = r1
            android.graphics.drawable.Drawable r1 = r5.fail
            r4.fail = r1
            android.graphics.drawable.Drawable r1 = r5.thumb
            r4.thumb = r1
            java.lang.String r1 = r5.thumbPath
            r4.thumbPath = r1
            long r2 = r5.photoSize
            r4.photoSize = r2
            long r2 = r5.modifiedTime
            r4.modifiedTime = r2
            android.graphics.drawable.Drawable r1 = r5.loading
            r4.loading = r1
            int r1 = r5.photoWidth
            r4.photoWidth = r1
            int r1 = r5.photoHeight
            r4.photoHeight = r1
            int r1 = r5.largePhotoWidth
            r4.largePhotoWidth = r1
            int r1 = r5.largePhotoHeight
            r4.largePhotoHeight = r1
            int r1 = r5.thumbWidth
            r4.thumbWidth = r1
            int r1 = r5.thumbHeight
            r4.thumbHeight = r1
            boolean r1 = r5.loadOrigin
            r4.loadOrigin = r1
            boolean r1 = r5.selected
            r4.selected = r1
            java.lang.String r1 = r5.tag
            r4.tag = r1
            int r1 = r5.photoIndex
            r4.photoIndex = r1
            int r1 = r5.photoGroupIndex
            r4.photoGroupIndex = r1
            int r1 = r5.mMediaType
            r4.mMediaType = r1
            long r2 = r5.videoDuration
            r4.videoDuration = r2
            int r1 = r5.videoHeight
            r4.videoHeight = r1
            int r1 = r5.videoWidth
            r4.videoWidth = r1
            boolean r1 = r5.haseMark
            if (r1 == 0) goto L_0x0075
            com.alipay.mobile.beehive.photo.data.PhotoMark r1 = r5.photoMark
            if (r1 == 0) goto L_0x0075
            r0 = 1
        L_0x0075:
            r4.haseMark = r0
            com.alipay.mobile.beehive.photo.data.PhotoMark r0 = r5.photoMark
            r4.photoMark = r0
            int r0 = r5.photoOrientation
            r4.photoOrientation = r0
            boolean r0 = r5.isPicCurrentlyTaked
            r4.isPicCurrentlyTaked = r0
            double r0 = r5.latitude
            r4.latitude = r0
            double r0 = r5.longitude
            r4.longitude = r0
            boolean r0 = r5.isGiffSuffix
            r4.isGiffSuffix = r0
            boolean r0 = r5.isInEdited
            r4.isInEdited = r0
            java.util.Map<java.lang.String, java.lang.Object> r0 = r5.extraInfo
            r4.extraInfo = r0
            android.os.Bundle r0 = r5.bizExtraParams
            r4.bizExtraParams = r0
            int r0 = r5.mediaSubType
            r4.mediaSubType = r0
            int r0 = r5.mediaId
            r4.mediaId = r0
            boolean r0 = r5.isAlbumMedia
            r4.isAlbumMedia = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.beehive.service.PhotoInfo.<init>(com.alipay.mobile.beehive.service.PhotoInfo):void");
    }

    public void setPhotoOrientation(int photoOrientation2) {
        this.photoOrientation = photoOrientation2;
    }

    public int getPhotoOrientation() {
        return this.photoOrientation;
    }

    public String getThumbPath() {
        return this.thumbPath;
    }

    public PhotoInfo setThumbPath(String thumbPath2) {
        this.thumbPath = thumbPath2;
        return this;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public PhotoInfo setPhotoPath(String photoPath2) {
        this.photoPath = photoPath2;
        this.isGiffSuffix = isGifSuffix(photoPath2);
        if (this.isGiffSuffix) {
            this.mediaSubType = 100;
        }
        return this;
    }

    public String getLeftText() {
        return this.leftText;
    }

    public PhotoInfo setLeftText(String text) {
        this.leftText = text;
        return this;
    }

    public String getRightText() {
        return this.rightText;
    }

    public PhotoInfo setRightText(String text) {
        this.rightText = text;
        return this;
    }

    public long getPhotoSize() {
        return this.photoSize;
    }

    public PhotoInfo setPhotoSize(long photoSize2) {
        this.photoSize = photoSize2;
        return this;
    }

    public long getModifiedTime() {
        return this.modifiedTime;
    }

    public PhotoInfo setModifiedTime(long modifiedTime2) {
        this.modifiedTime = modifiedTime2;
        return this;
    }

    public Drawable getPhoto() {
        return this.photo;
    }

    public PhotoInfo setPhoto(Drawable photo2) {
        this.photo = photo2;
        return this;
    }

    public Drawable getThumb() {
        return this.thumb;
    }

    public PhotoInfo setThumb(Drawable thumb2) {
        this.thumb = thumb2;
        return this;
    }

    public Drawable getFail() {
        return this.fail;
    }

    public PhotoInfo setFail(Drawable fail2) {
        this.fail = fail2;
        return this;
    }

    public Drawable getLoading() {
        return this.loading;
    }

    public PhotoInfo setLoading(Drawable loading2) {
        this.loading = loading2;
        return this;
    }

    public int getPhotoWidth() {
        return this.photoWidth;
    }

    public PhotoInfo setPhotoWidth(int photoWidth2) {
        this.photoWidth = photoWidth2;
        return this;
    }

    public int getPhotoHeight() {
        return this.photoHeight;
    }

    public PhotoInfo setPhotoHeight(int height) {
        this.photoHeight = height;
        return this;
    }

    public int getThumbWidth() {
        return this.thumbWidth;
    }

    public PhotoInfo setThumbWidth(int width) {
        this.thumbWidth = width;
        return this;
    }

    public int getThumbHeight() {
        return this.thumbHeight;
    }

    public PhotoInfo setThumbHeight(int height) {
        this.thumbHeight = height;
        return this;
    }

    public boolean getLoadOrigin() {
        return this.loadOrigin;
    }

    public PhotoInfo setLoadOrigin(boolean loadOrigin2) {
        this.loadOrigin = loadOrigin2;
        return this;
    }

    public int getPhotoIndex() {
        return this.photoIndex;
    }

    public PhotoInfo setPhotoIndex(int photoIndex2) {
        this.photoIndex = photoIndex2;
        return this;
    }

    public int getLargePhotoWidth() {
        return this.largePhotoWidth;
    }

    public void setLargePhotoWidth(int largePhotoWidth2) {
        this.largePhotoWidth = largePhotoWidth2;
    }

    public int getLargePhotoHeight() {
        return this.largePhotoHeight;
    }

    public void setLargePhotoHeight(int largePhotoHeight2) {
        this.largePhotoHeight = largePhotoHeight2;
    }

    public void setInEdited(boolean inEdited) {
        this.isInEdited = inEdited;
    }

    public boolean getInEdited() {
        return this.isInEdited;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public PhotoInfo setSelected(boolean selected2) {
        this.selected = selected2;
        return this;
    }

    public String getTag() {
        return this.tag;
    }

    public PhotoInfo setTag(String tag2) {
        this.tag = tag2;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = 1;
        dest.writeString(this.photoPath);
        dest.writeString(this.thumbPath);
        dest.writeString(this.leftText);
        dest.writeString(this.rightText);
        dest.writeLong(this.photoSize);
        dest.writeLong(this.modifiedTime);
        dest.writeInt(this.photoWidth);
        dest.writeInt(this.photoHeight);
        dest.writeInt(this.largePhotoWidth);
        dest.writeInt(this.largePhotoHeight);
        dest.writeInt(this.thumbWidth);
        dest.writeInt(this.thumbHeight);
        dest.writeInt(this.loadOrigin ? 1 : 0);
        if (this.selected) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeString(this.tag);
        dest.writeInt(this.photoIndex);
        dest.writeInt(this.photoGroupIndex);
        dest.writeInt(this.mMediaType);
        dest.writeLong(this.videoDuration);
        dest.writeInt(this.videoWidth);
        dest.writeInt(this.videoHeight);
        if (!this.haseMark || this.photoMark == null) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        dest.writeInt(i2);
        if (this.photoMark != null) {
            this.photoMark.writeToParcel(dest, flags);
        }
        dest.writeInt(this.photoOrientation);
        if (this.isPicCurrentlyTaked) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        dest.writeInt(i3);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        if (this.isGiffSuffix) {
            i4 = 1;
        } else {
            i4 = 0;
        }
        dest.writeInt(i4);
        dest.writeInt(this.mediaSubType);
        dest.writeInt(this.mediaId);
        if (!this.isAlbumMedia) {
            i5 = 0;
        }
        dest.writeInt(i5);
    }

    public PhotoMark getPhotoMark() {
        return this.photoMark;
    }

    public void setPhotoMark(PhotoMark photoMark2) {
        this.haseMark = true;
        this.photoMark = photoMark2;
    }

    public boolean isGifSuffix(String path) {
        if (path == null || path.length() <= 4) {
            return false;
        }
        int length = path.length();
        return path.substring(length - 4, length).equalsIgnoreCase(".gif");
    }
}
