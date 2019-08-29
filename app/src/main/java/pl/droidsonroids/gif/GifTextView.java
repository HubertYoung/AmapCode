package pl.droidsonroids.gif;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.common.share.widget.ResUtils;
import java.io.IOException;

public class GifTextView extends TextView {
    private boolean mFreezesAnimation;

    public GifTextView(Context context) {
        super(context);
    }

    public GifTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0, 0);
    }

    public GifTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i, 0);
    }

    @RequiresApi(21)
    public GifTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(attributeSet, i, i2);
    }

    private void init(AttributeSet attributeSet, int i, int i2) {
        if (attributeSet != null) {
            Drawable gifOrDefaultDrawable = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableLeft", 0));
            Drawable gifOrDefaultDrawable2 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableTop", 0));
            Drawable gifOrDefaultDrawable3 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableRight", 0));
            Drawable gifOrDefaultDrawable4 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableBottom", 0));
            Drawable gifOrDefaultDrawable5 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableStart", 0));
            Drawable gifOrDefaultDrawable6 = getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableEnd", 0));
            if (VERSION.SDK_INT >= 17) {
                if (getLayoutDirection() == 0) {
                    if (gifOrDefaultDrawable5 == null) {
                        gifOrDefaultDrawable5 = gifOrDefaultDrawable;
                    }
                    if (gifOrDefaultDrawable6 == null) {
                        gifOrDefaultDrawable6 = gifOrDefaultDrawable3;
                    }
                } else {
                    if (gifOrDefaultDrawable5 == null) {
                        gifOrDefaultDrawable5 = gifOrDefaultDrawable3;
                    }
                    if (gifOrDefaultDrawable6 == null) {
                        gifOrDefaultDrawable6 = gifOrDefaultDrawable;
                    }
                }
                setCompoundDrawablesRelativeWithIntrinsicBounds(gifOrDefaultDrawable5, gifOrDefaultDrawable2, gifOrDefaultDrawable6, gifOrDefaultDrawable4);
            }
            setCompoundDrawablesWithIntrinsicBounds(gifOrDefaultDrawable, gifOrDefaultDrawable2, gifOrDefaultDrawable3, gifOrDefaultDrawable4);
            setBackgroundInternal(getGifOrDefaultDrawable(attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", Subscribe.THREAD_BACKGROUND, 0)));
        }
        this.mFreezesAnimation = fhl.a((View) this, attributeSet, i, i2);
    }

    private void setBackgroundInternal(Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        setBackgroundInternal(getGifOrDefaultDrawable(i));
    }

    private Drawable getGifOrDefaultDrawable(int i) {
        if (i == 0) {
            return null;
        }
        Resources resources = getResources();
        if (!isInEditMode() && ResUtils.DRAWABLE.equals(resources.getResourceTypeName(i))) {
            try {
                return new GifDrawable(resources, i);
            } catch (NotFoundException | IOException unused) {
            }
        }
        if (VERSION.SDK_INT >= 21) {
            return resources.getDrawable(i, getContext().getTheme());
        }
        return resources.getDrawable(i);
    }

    @RequiresApi(17)
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(getGifOrDefaultDrawable(i), getGifOrDefaultDrawable(i2), getGifOrDefaultDrawable(i3), getGifOrDefaultDrawable(i4));
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        setCompoundDrawablesWithIntrinsicBounds(getGifOrDefaultDrawable(i), getGifOrDefaultDrawable(i2), getGifOrDefaultDrawable(i3), getGifOrDefaultDrawable(i4));
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hideCompoundDrawables(getCompoundDrawables());
        if (VERSION.SDK_INT >= 17) {
            hideCompoundDrawables(getCompoundDrawablesRelative());
        }
    }

    private void hideCompoundDrawables(Drawable[] drawableArr) {
        for (Drawable drawable : drawableArr) {
            if (drawable != null) {
                drawable.setVisible(false, false);
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        Drawable[] drawableArr = new Drawable[7];
        if (this.mFreezesAnimation) {
            Drawable[] compoundDrawables = getCompoundDrawables();
            System.arraycopy(compoundDrawables, 0, drawableArr, 0, compoundDrawables.length);
            if (VERSION.SDK_INT >= 17) {
                Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
                drawableArr[4] = compoundDrawablesRelative[0];
                drawableArr[5] = compoundDrawablesRelative[2];
            }
            drawableArr[6] = getBackground();
        }
        return new GifViewSavedState(super.onSaveInstanceState(), drawableArr);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        GifViewSavedState gifViewSavedState = (GifViewSavedState) parcelable;
        super.onRestoreInstanceState(gifViewSavedState.getSuperState());
        Drawable[] compoundDrawables = getCompoundDrawables();
        gifViewSavedState.a(compoundDrawables[0], 0);
        gifViewSavedState.a(compoundDrawables[1], 1);
        gifViewSavedState.a(compoundDrawables[2], 2);
        gifViewSavedState.a(compoundDrawables[3], 3);
        if (VERSION.SDK_INT >= 17) {
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            gifViewSavedState.a(compoundDrawablesRelative[0], 4);
            gifViewSavedState.a(compoundDrawablesRelative[2], 5);
        }
        gifViewSavedState.a(getBackground(), 6);
    }

    public void setFreezesAnimation(boolean z) {
        this.mFreezesAnimation = z;
    }
}
