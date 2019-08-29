package defpackage;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.widget.gif.R;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import pl.droidsonroids.gif.GifDrawable;

/* renamed from: fhl reason: default package */
/* compiled from: GifViewUtils */
public final class fhl {
    public static final List<String> a = Arrays.asList(new String[]{ResUtils.RAW, ResUtils.DRAWABLE, "mipmap"});

    /* renamed from: fhl$a */
    /* compiled from: GifViewUtils */
    public static class a {
        public final int a;
        public final int b;
        public final boolean c;

        a(int i, int i2, boolean z) {
            this.a = i;
            this.b = i2;
            this.c = z;
        }
    }

    public static a a(ImageView imageView, AttributeSet attributeSet, int i, int i2) {
        if (attributeSet == null || imageView.isInEditMode()) {
            return new a(0, 0, false);
        }
        return new a(a(imageView, attributeSet, true), a(imageView, attributeSet, false), a((View) imageView, attributeSet, i, i2));
    }

    private static int a(ImageView imageView, AttributeSet attributeSet, boolean z) {
        int attributeResourceValue = attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", z ? "src" : Subscribe.THREAD_BACKGROUND, 0);
        if (attributeResourceValue > 0) {
            if (!a.contains(imageView.getResources().getResourceTypeName(attributeResourceValue)) || a(imageView, z, attributeResourceValue)) {
                return 0;
            }
            return attributeResourceValue;
        }
        return 0;
    }

    public static boolean a(ImageView imageView, boolean z, int i) {
        Resources resources = imageView.getResources();
        if (resources != null) {
            try {
                GifDrawable gifDrawable = new GifDrawable(resources, i);
                if (z) {
                    imageView.setImageDrawable(gifDrawable);
                } else if (VERSION.SDK_INT >= 16) {
                    imageView.setBackground(gifDrawable);
                } else {
                    imageView.setBackgroundDrawable(gifDrawable);
                }
                return true;
            } catch (NotFoundException | IOException unused) {
            }
        }
        return false;
    }

    public static boolean a(View view, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.GifView, i, i2);
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.GifView_freezesAnimation, false);
        obtainStyledAttributes.recycle();
        return z;
    }

    public static boolean a(ImageView imageView, Uri uri) {
        if (uri != null) {
            try {
                imageView.setImageDrawable(new GifDrawable(imageView.getContext().getContentResolver(), uri));
                return true;
            } catch (IOException unused) {
            }
        }
        return false;
    }

    public static float a(@NonNull Resources resources, @RawRes @DrawableRes int i) {
        TypedValue typedValue = new TypedValue();
        resources.getValue(i, typedValue, true);
        int i2 = typedValue.density;
        if (i2 == 0) {
            i2 = 160;
        } else if (i2 == 65535) {
            i2 = 0;
        }
        int i3 = resources.getDisplayMetrics().densityDpi;
        if (i2 <= 0 || i3 <= 0) {
            return 1.0f;
        }
        return ((float) i3) / ((float) i2);
    }
}
