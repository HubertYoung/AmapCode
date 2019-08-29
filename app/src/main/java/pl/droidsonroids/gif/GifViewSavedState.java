package pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.view.View.BaseSavedState;

class GifViewSavedState extends BaseSavedState {
    public static final Creator<GifViewSavedState> CREATOR = new Creator<GifViewSavedState>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new GifViewSavedState[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new GifViewSavedState(parcel, 0);
        }
    };
    final long[][] a;

    /* synthetic */ GifViewSavedState(Parcel parcel, byte b) {
        this(parcel);
    }

    GifViewSavedState(Parcelable parcelable, Drawable... drawableArr) {
        super(parcelable);
        this.a = new long[drawableArr.length][];
        for (int i = 0; i < drawableArr.length; i++) {
            GifDrawable gifDrawable = drawableArr[i];
            if (gifDrawable instanceof GifDrawable) {
                this.a[i] = gifDrawable.mNativeInfoHandle.r();
            } else {
                this.a[i] = null;
            }
        }
    }

    private GifViewSavedState(Parcel parcel) {
        super(parcel);
        this.a = new long[parcel.readInt()][];
        for (int i = 0; i < this.a.length; i++) {
            this.a[i] = parcel.createLongArray();
        }
    }

    GifViewSavedState(Parcelable parcelable, long[] jArr) {
        super(parcelable);
        this.a = new long[1][];
        this.a[0] = jArr;
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.a.length);
        for (long[] writeLongArray : this.a) {
            parcel.writeLongArray(writeLongArray);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Drawable drawable, int i) {
        if (this.a[i] != null && (drawable instanceof GifDrawable)) {
            GifDrawable gifDrawable = (GifDrawable) drawable;
            gifDrawable.startAnimation((long) gifDrawable.mNativeInfoHandle.a(this.a[i], gifDrawable.mBuffer));
        }
    }
}
