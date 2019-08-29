package android.support.v4.media;

import android.media.Rating;

class RatingCompatApi21 {
    RatingCompatApi21() {
    }

    public static Object a(int i) {
        return Rating.newUnratedRating(i);
    }

    public static Object a(boolean z) {
        return Rating.newHeartRating(z);
    }

    public static Object b(boolean z) {
        return Rating.newThumbRating(z);
    }

    public static Object a(int i, float f) {
        return Rating.newStarRating(i, f);
    }

    public static Object a(float f) {
        return Rating.newPercentageRating(f);
    }

    public static boolean a(Object obj) {
        return ((Rating) obj).isRated();
    }

    public static int b(Object obj) {
        return ((Rating) obj).getRatingStyle();
    }

    public static boolean c(Object obj) {
        return ((Rating) obj).hasHeart();
    }

    public static boolean d(Object obj) {
        return ((Rating) obj).isThumbUp();
    }

    public static float e(Object obj) {
        return ((Rating) obj).getStarRating();
    }

    public static float f(Object obj) {
        return ((Rating) obj).getPercentRating();
    }
}
