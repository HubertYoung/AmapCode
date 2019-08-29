package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public final class MediaDescriptionCompat implements Parcelable {
    public static final Creator<MediaDescriptionCompat> CREATOR = new Creator<MediaDescriptionCompat>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new MediaDescriptionCompat[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            if (VERSION.SDK_INT < 21) {
                return new MediaDescriptionCompat(parcel);
            }
            return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.a(parcel));
        }
    };
    private final CharSequence mDescription;
    private Object mDescriptionObj;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    public static final class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public final Builder setMediaId(@Nullable String str) {
            this.mMediaId = str;
            return this;
        }

        public final Builder setTitle(@Nullable CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public final Builder setSubtitle(@Nullable CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public final Builder setDescription(@Nullable CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public final Builder setIconBitmap(@Nullable Bitmap bitmap) {
            this.mIcon = bitmap;
            return this;
        }

        public final Builder setIconUri(@Nullable Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public final Builder setExtras(@Nullable Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public final Builder setMediaUri(@Nullable Uri uri) {
            this.mMediaUri = uri;
            return this;
        }

        public final MediaDescriptionCompat build() {
            MediaDescriptionCompat mediaDescriptionCompat = new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
            return mediaDescriptionCompat;
        }
    }

    public final int describeContents() {
        return 0;
    }

    private MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.mMediaId = str;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.mIconUri = uri;
        this.mExtras = bundle;
        this.mMediaUri = uri2;
    }

    private MediaDescriptionCompat(Parcel parcel) {
        this.mMediaId = parcel.readString();
        this.mTitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDescription = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = (Bitmap) parcel.readParcelable(null);
        this.mIconUri = (Uri) parcel.readParcelable(null);
        this.mExtras = parcel.readBundle();
        this.mMediaUri = (Uri) parcel.readParcelable(null);
    }

    @Nullable
    public final String getMediaId() {
        return this.mMediaId;
    }

    @Nullable
    public final CharSequence getTitle() {
        return this.mTitle;
    }

    @Nullable
    public final CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Nullable
    public final CharSequence getDescription() {
        return this.mDescription;
    }

    @Nullable
    public final Bitmap getIconBitmap() {
        return this.mIcon;
    }

    @Nullable
    public final Uri getIconUri() {
        return this.mIconUri;
    }

    @Nullable
    public final Bundle getExtras() {
        return this.mExtras;
    }

    @Nullable
    public final Uri getMediaUri() {
        return this.mMediaUri;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        if (VERSION.SDK_INT < 21) {
            parcel.writeString(this.mMediaId);
            TextUtils.writeToParcel(this.mTitle, parcel, i);
            TextUtils.writeToParcel(this.mSubtitle, parcel, i);
            TextUtils.writeToParcel(this.mDescription, parcel, i);
            parcel.writeParcelable(this.mIcon, i);
            parcel.writeParcelable(this.mIconUri, i);
            parcel.writeBundle(this.mExtras);
            return;
        }
        MediaDescriptionCompatApi21.a(getMediaDescription(), parcel, i);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mTitle);
        sb.append(", ");
        sb.append(this.mSubtitle);
        sb.append(", ");
        sb.append(this.mDescription);
        return sb.toString();
    }

    public final Object getMediaDescription() {
        if (this.mDescriptionObj != null || VERSION.SDK_INT < 21) {
            return this.mDescriptionObj;
        }
        Object a = Builder.a();
        Builder.a(a, this.mMediaId);
        Builder.a(a, this.mTitle);
        Builder.b(a, this.mSubtitle);
        Builder.c(a, this.mDescription);
        Builder.a(a, this.mIcon);
        Builder.a(a, this.mIconUri);
        Builder.a(a, this.mExtras);
        if (VERSION.SDK_INT >= 23) {
            Builder.b(a, this.mMediaUri);
        }
        this.mDescriptionObj = Builder.a(a);
        return this.mDescriptionObj;
    }

    public static MediaDescriptionCompat fromMediaDescription(Object obj) {
        if (obj == null || VERSION.SDK_INT < 21) {
            return null;
        }
        Builder builder = new Builder();
        builder.setMediaId(MediaDescriptionCompatApi21.a(obj));
        builder.setTitle(MediaDescriptionCompatApi21.b(obj));
        builder.setSubtitle(MediaDescriptionCompatApi21.c(obj));
        builder.setDescription(MediaDescriptionCompatApi21.d(obj));
        builder.setIconBitmap(MediaDescriptionCompatApi21.e(obj));
        builder.setIconUri(MediaDescriptionCompatApi21.f(obj));
        builder.setExtras(MediaDescriptionCompatApi21.g(obj));
        if (VERSION.SDK_INT >= 23) {
            builder.setMediaUri(MediaDescriptionCompatApi23.h(obj));
        }
        MediaDescriptionCompat build = builder.build();
        build.mDescriptionObj = obj;
        return build;
    }
}
