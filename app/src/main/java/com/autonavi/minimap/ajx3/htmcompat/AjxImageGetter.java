package com.autonavi.minimap.ajx3.htmcompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.IAjxImageLoader;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import java.util.HashMap;
import org.xml.sax.Attributes;
import pl.droidsonroids.gif.GifDrawable;

public class AjxImageGetter {
    public static final String TAG = "AjxImageGetter";
    /* access modifiers changed from: private */
    public View hostView;
    private boolean mDirty = false;

    class AjxImageCallback implements ImageCallback {
        private RemoteDrawable remoteDrawable;

        public void onBitmapFailed(Drawable drawable) {
        }

        public void onGifLoaded(GifDrawable gifDrawable) {
        }

        public void onPrepareLoad(Drawable drawable) {
        }

        private AjxImageCallback(RemoteDrawable remoteDrawable2) {
            this.remoteDrawable = remoteDrawable2;
        }

        public void onBitmapLoaded(Bitmap bitmap) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(AjxImageGetter.this.hostView.getResources(), bitmap);
            bitmapDrawable.setBounds(0, 0, this.remoteDrawable.getIntrinsicWidth(), this.remoteDrawable.getIntrinsicHeight());
            this.remoteDrawable.setDrawable(bitmapDrawable);
            AjxImageGetter.this.hostView.invalidate();
        }
    }

    public static class AjxImageSpan extends ImageSpan {
        public int h;
        public String id;
        public String src;
        public int w;
        public int x;
        public int y;

        public AjxImageSpan(Drawable drawable, String str, String str2, int i) {
            super(drawable, str2, i);
            this.id = str;
            this.src = str2;
        }

        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            super.draw(canvas, charSequence, i, i2, f, i3, i4, i5, paint);
            this.x = (int) f;
            this.y = i5 - getDrawable().getIntrinsicHeight();
            this.w = getDrawable().getIntrinsicWidth();
            this.h = getDrawable().getIntrinsicHeight();
        }
    }

    public class RemoteDrawable extends BitmapDrawable {
        private Drawable drawable;
        private int h;
        private AjxImageCallback mCallBack;
        private String url;
        private View view;
        private int w;

        private RemoteDrawable(View view2, String str, int i, int i2) {
            this.view = view2;
            this.url = str;
            this.w = i;
            this.h = i2;
        }

        public void draw(Canvas canvas) {
            if (this.drawable != null) {
                this.drawable.draw(canvas);
            }
        }

        public Drawable getDrawable() {
            return this.drawable;
        }

        public void setDrawable(Drawable drawable2) {
            this.drawable = drawable2;
        }

        public int getIntrinsicHeight() {
            return this.h;
        }

        public int getIntrinsicWidth() {
            return this.w;
        }

        public View getView() {
            return this.view;
        }

        public void update() {
            if (this.view instanceof ViewExtension) {
                IAjxImageLoader lookupLoader = Ajx.getInstance().lookupLoader(this.url);
                IAjxContext ajxContext = ((ViewExtension) this.view).getProperty().getAjxContext();
                if (this.mCallBack == null) {
                    this.mCallBack = new AjxImageCallback(this);
                }
                lookupLoader.loadImage(this.view, ajxContext, PictureParams.make(ajxContext, this.url, false), this.mCallBack);
            }
        }
    }

    public AjxImageGetter(View view) {
        this.hostView = view;
    }

    public Drawable getDrawable(String str, HashMap<String, String> hashMap) {
        Drawable drawable;
        String str2 = hashMap.get("width");
        String str3 = hashMap.get("height");
        int i = -1;
        IAjxContext iAjxContext = null;
        int parseStandUnit2Px = !TextUtils.isEmpty(str2) ? StringUtils.parseStandUnit2Px(null, str2) : -1;
        if (!TextUtils.isEmpty(str3)) {
            i = StringUtils.parseStandUnit2Px(null, str3);
        }
        if (parseStandUnit2Px < 0 && i < 0 && this.hostView != null) {
            IAjxImageLoader lookupLoader = Ajx.getInstance().lookupLoader(str);
            if (this.hostView instanceof ViewExtension) {
                BaseProperty property = ((ViewExtension) this.hostView).getProperty();
                if (property != null) {
                    iAjxContext = property.getAjxContext();
                }
            }
            float[] readImageSize = lookupLoader.readImageSize(PictureParams.make(iAjxContext, str, false));
            i = (int) readImageSize[1];
            parseStandUnit2Px = (int) readImageSize[0];
        }
        if (parseStandUnit2Px < 0) {
            parseStandUnit2Px = 0;
        }
        if (i < 0) {
            i = 0;
        }
        if (this.hostView == null) {
            drawable = new BitmapDrawable();
        } else {
            RemoteDrawable remoteDrawable = new RemoteDrawable(this.hostView, str, parseStandUnit2Px, i);
            drawable = remoteDrawable;
        }
        drawable.setBounds(0, 0, parseStandUnit2Px, i);
        setDirty(true);
        return drawable;
    }

    public Drawable getDrawable(String str, Attributes attributes) {
        Drawable drawable;
        String value = attributes.getValue("", "width");
        String value2 = attributes.getValue("", "height");
        int i = -1;
        IAjxContext iAjxContext = null;
        int parseStandUnit2Px = !TextUtils.isEmpty(value) ? StringUtils.parseStandUnit2Px(null, value) : -1;
        if (!TextUtils.isEmpty(value2)) {
            i = StringUtils.parseStandUnit2Px(null, value2);
        }
        if (parseStandUnit2Px < 0 && i < 0 && this.hostView != null) {
            IAjxImageLoader lookupLoader = Ajx.getInstance().lookupLoader(str);
            if (this.hostView instanceof ViewExtension) {
                BaseProperty property = ((ViewExtension) this.hostView).getProperty();
                if (property != null) {
                    iAjxContext = property.getAjxContext();
                }
            }
            float[] readImageSize = lookupLoader.readImageSize(PictureParams.make(iAjxContext, str, false));
            i = (int) readImageSize[1];
            parseStandUnit2Px = (int) readImageSize[0];
        }
        if (parseStandUnit2Px < 0) {
            parseStandUnit2Px = 0;
        }
        if (i < 0) {
            i = 0;
        }
        if (this.hostView == null) {
            drawable = new BitmapDrawable();
        } else {
            RemoteDrawable remoteDrawable = new RemoteDrawable(this.hostView, str, parseStandUnit2Px, i);
            drawable = remoteDrawable;
        }
        drawable.setBounds(0, 0, parseStandUnit2Px, i);
        setDirty(true);
        return drawable;
    }

    private void setDirty(boolean z) {
        this.mDirty = z;
    }

    public boolean isDirty() {
        return this.mDirty;
    }
}
