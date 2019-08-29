package android.support.v4.media;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RatingCompat implements Parcelable {
    public static final Creator<RatingCompat> CREATOR = new Creator<RatingCompat>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new RatingCompat[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new RatingCompat(parcel.readInt(), parcel.readFloat());
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private Object mRatingObj;
    private final int mRatingStyle;
    private final float mRatingValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

    private RatingCompat(int i, float f) {
        this.mRatingStyle = i;
        this.mRatingValue = f;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Rating:style=");
        sb.append(this.mRatingStyle);
        sb.append(" rating=");
        sb.append(this.mRatingValue < 0.0f ? "unrated" : String.valueOf(this.mRatingValue));
        return sb.toString();
    }

    public final int describeContents() {
        return this.mRatingStyle;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }

    public static RatingCompat newUnratedRating(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new RatingCompat(i, -1.0f);
            default:
                return null;
        }
    }

    public static RatingCompat newHeartRating(boolean z) {
        return new RatingCompat(1, z ? 1.0f : 0.0f);
    }

    public static RatingCompat newThumbRating(boolean z) {
        return new RatingCompat(2, z ? 1.0f : 0.0f);
    }

    public static RatingCompat newStarRating(int i, float f) {
        float f2;
        switch (i) {
            case 3:
                f2 = 3.0f;
                break;
            case 4:
                f2 = 4.0f;
                break;
            case 5:
                f2 = 5.0f;
                break;
            default:
                StringBuilder sb = new StringBuilder("Invalid rating style (");
                sb.append(i);
                sb.append(") for a star rating");
                return null;
        }
        if (f < 0.0f || f > f2) {
            return null;
        }
        return new RatingCompat(i, f);
    }

    public static RatingCompat newPercentageRating(float f) {
        if (f < 0.0f || f > 100.0f) {
            return null;
        }
        return new RatingCompat(6, f);
    }

    public final boolean isRated() {
        return this.mRatingValue >= 0.0f;
    }

    public final int getRatingStyle() {
        return this.mRatingStyle;
    }

    public final boolean hasHeart() {
        return this.mRatingStyle == 1 && this.mRatingValue == 1.0f;
    }

    public final boolean isThumbUp() {
        if (this.mRatingStyle == 2 && this.mRatingValue == 1.0f) {
            return true;
        }
        return false;
    }

    public final float getStarRating() {
        switch (this.mRatingStyle) {
            case 3:
            case 4:
            case 5:
                if (isRated()) {
                    return this.mRatingValue;
                }
                break;
        }
        return -1.0f;
    }

    public final float getPercentRating() {
        if (this.mRatingStyle != 6 || !isRated()) {
            return -1.0f;
        }
        return this.mRatingValue;
    }

    public static RatingCompat fromRating(Object obj) {
        RatingCompat ratingCompat;
        if (obj == null || VERSION.SDK_INT < 21) {
            return null;
        }
        int b = RatingCompatApi21.b(obj);
        if (RatingCompatApi21.a(obj)) {
            switch (b) {
                case 1:
                    ratingCompat = newHeartRating(RatingCompatApi21.c(obj));
                    break;
                case 2:
                    ratingCompat = newThumbRating(RatingCompatApi21.d(obj));
                    break;
                case 3:
                case 4:
                case 5:
                    ratingCompat = newStarRating(b, RatingCompatApi21.e(obj));
                    break;
                case 6:
                    ratingCompat = newPercentageRating(RatingCompatApi21.f(obj));
                    break;
                default:
                    return null;
            }
        } else {
            ratingCompat = newUnratedRating(b);
        }
        ratingCompat.mRatingObj = obj;
        return ratingCompat;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0046, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object getRating() {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mRatingObj
            if (r0 != 0) goto L_0x0052
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            if (r0 >= r1) goto L_0x000b
            goto L_0x0052
        L_0x000b:
            boolean r0 = r2.isRated()
            if (r0 == 0) goto L_0x0047
            int r0 = r2.mRatingStyle
            switch(r0) {
                case 1: goto L_0x003a;
                case 2: goto L_0x002f;
                case 3: goto L_0x0022;
                case 4: goto L_0x0022;
                case 5: goto L_0x0022;
                case 6: goto L_0x0017;
                default: goto L_0x0016;
            }
        L_0x0016:
            goto L_0x0045
        L_0x0017:
            float r0 = r2.getPercentRating()
            java.lang.Object r0 = android.support.v4.media.RatingCompatApi21.a(r0)
            r2.mRatingObj = r0
            goto L_0x0045
        L_0x0022:
            int r0 = r2.mRatingStyle
            float r1 = r2.getStarRating()
            java.lang.Object r0 = android.support.v4.media.RatingCompatApi21.a(r0, r1)
            r2.mRatingObj = r0
            goto L_0x004f
        L_0x002f:
            boolean r0 = r2.isThumbUp()
            java.lang.Object r0 = android.support.v4.media.RatingCompatApi21.b(r0)
            r2.mRatingObj = r0
            goto L_0x004f
        L_0x003a:
            boolean r0 = r2.hasHeart()
            java.lang.Object r0 = android.support.v4.media.RatingCompatApi21.a(r0)
            r2.mRatingObj = r0
            goto L_0x004f
        L_0x0045:
            r0 = 0
            return r0
        L_0x0047:
            int r0 = r2.mRatingStyle
            java.lang.Object r0 = android.support.v4.media.RatingCompatApi21.a(r0)
            r2.mRatingObj = r0
        L_0x004f:
            java.lang.Object r0 = r2.mRatingObj
            return r0
        L_0x0052:
            java.lang.Object r0 = r2.mRatingObj
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.RatingCompat.getRating():java.lang.Object");
    }
}
