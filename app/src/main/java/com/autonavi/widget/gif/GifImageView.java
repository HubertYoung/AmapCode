package com.autonavi.widget.gif;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.InternalGifImageView;

public class GifImageView extends InternalGifImageView {
    private int mResource = 0;
    private Uri mUri;

    public GifImageView(Context context) {
        super(context);
    }

    public GifImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GifImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public GifImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setImageURI(Uri uri) {
        if (this.mUri == null || (this.mUri != uri && !this.mUri.equals(uri))) {
            super.setImageURI(uri);
            this.mUri = uri;
        }
    }

    public void setImageResource(int i) {
        if (this.mResource != i) {
            super.setImageResource(i);
            this.mResource = i;
        }
    }

    public void setBackgroundResource(int i) {
        if (this.mResource != i) {
            super.setBackgroundResource(i);
            this.mResource = i;
        }
    }

    @Nullable
    public int[] getImageResourceSize() {
        int[] iArr = new int[2];
        Drawable drawable = getDrawable();
        if (drawable instanceof GifDrawable) {
            GifDrawable gifDrawable = (GifDrawable) drawable;
            iArr[0] = gifDrawable.getIntrinsicWidth();
            iArr[1] = gifDrawable.getIntrinsicHeight();
        } else if (drawable != null) {
            iArr[0] = drawable.getBounds().width();
            iArr[1] = drawable.getBounds().height();
        }
        return iArr;
    }

    @Nullable
    public int[] getBackgroundResourceSize() {
        int[] iArr = new int[2];
        Drawable background = getBackground();
        if (background instanceof GifDrawable) {
            GifDrawable gifDrawable = (GifDrawable) background;
            iArr[0] = gifDrawable.getIntrinsicWidth();
            iArr[1] = gifDrawable.getIntrinsicHeight();
        } else if (background != null) {
            iArr[0] = background.getBounds().width();
            iArr[1] = background.getBounds().height();
        }
        return iArr;
    }

    public void play() {
        Drawable drawable = getDrawable();
        if (drawable instanceof GifDrawable) {
            ((GifDrawable) drawable).reset();
        }
    }

    public void pause() {
        Drawable drawable = getDrawable();
        if (drawable instanceof GifDrawable) {
            ((GifDrawable) drawable).pause();
        }
    }

    public void stop() {
        Drawable drawable = getDrawable();
        if (drawable instanceof GifDrawable) {
            GifDrawable gifDrawable = (GifDrawable) drawable;
            gifDrawable.stop();
            gifDrawable.seekTo(0);
        }
    }
}
