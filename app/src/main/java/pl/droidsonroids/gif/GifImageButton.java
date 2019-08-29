package pl.droidsonroids.gif;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GifImageButton extends ImageButton {
    private boolean mFreezesAnimation;

    public GifImageButton(Context context) {
        super(context);
    }

    public GifImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        postInit(fhl.a((ImageView) this, attributeSet, 0, 0));
    }

    public GifImageButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        postInit(fhl.a((ImageView) this, attributeSet, i, 0));
    }

    @RequiresApi(21)
    public GifImageButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        postInit(fhl.a((ImageView) this, attributeSet, i, i2));
    }

    private void postInit(a aVar) {
        this.mFreezesAnimation = aVar.c;
        if (aVar.a > 0) {
            super.setImageResource(aVar.a);
        }
        if (aVar.b > 0) {
            super.setBackgroundResource(aVar.b);
        }
    }

    public void setImageURI(Uri uri) {
        if (!fhl.a((ImageView) this, uri)) {
            super.setImageURI(uri);
        }
    }

    public void setImageResource(int i) {
        if (!fhl.a((ImageView) this, true, i)) {
            super.setImageResource(i);
        }
    }

    public void setBackgroundResource(int i) {
        if (!fhl.a((ImageView) this, false, i)) {
            super.setBackgroundResource(i);
        }
    }

    public Parcelable onSaveInstanceState() {
        Drawable drawable = null;
        Drawable drawable2 = this.mFreezesAnimation ? getDrawable() : null;
        if (this.mFreezesAnimation) {
            drawable = getBackground();
        }
        return new GifViewSavedState(super.onSaveInstanceState(), drawable2, drawable);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        GifViewSavedState gifViewSavedState = (GifViewSavedState) parcelable;
        super.onRestoreInstanceState(gifViewSavedState.getSuperState());
        gifViewSavedState.a(getDrawable(), 0);
        gifViewSavedState.a(getBackground(), 1);
    }

    public void setFreezesAnimation(boolean z) {
        this.mFreezesAnimation = z;
    }
}
