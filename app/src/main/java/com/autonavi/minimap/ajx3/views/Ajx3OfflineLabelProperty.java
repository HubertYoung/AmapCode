package com.autonavi.minimap.ajx3.views;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.IAjxImageLoader;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.util.PathUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import pl.droidsonroids.gif.GifDrawable;

class Ajx3OfflineLabelProperty extends BaseProperty<OfflineLabel> {
    private ImageCallback mImageCallback = new ImageCallback() {
        public void onBitmapFailed(Drawable drawable) {
        }

        public void onPrepareLoad(Drawable drawable) {
        }

        public void onGifLoaded(GifDrawable gifDrawable) {
            ((OfflineLabel) Ajx3OfflineLabelProperty.this.mView).setImageDrawable(gifDrawable);
        }

        public void onBitmapLoaded(Bitmap bitmap) {
            ((OfflineLabel) Ajx3OfflineLabelProperty.this.mView).setImageDrawable(new BitmapDrawable(((OfflineLabel) Ajx3OfflineLabelProperty.this.mView).getResources(), bitmap));
        }
    };
    private String mSrcImageUrl;

    public Ajx3OfflineLabelProperty(@NonNull OfflineLabel offlineLabel, @NonNull IAjxContext iAjxContext) {
        super(offlineLabel, iAjxContext);
    }

    public void updateStyle(int i, Object obj, boolean z) {
        if (i == 1056964659) {
            updateFont(obj);
        } else if (i != 1056964667) {
            super.updateStyle(i, obj, z);
        } else {
            updateColor(obj);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r3, java.lang.Object r4) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -1719693451(0xffffffff997f8f75, float:-1.3212162E-23)
            if (r0 == r1) goto L_0x0039
            r1 = -599647561(0xffffffffdc421ab7, float:-2.18542075E17)
            if (r0 == r1) goto L_0x002f
            r1 = 114148(0x1bde4, float:1.59955E-40)
            if (r0 == r1) goto L_0x0024
            r1 = 3556653(0x36452d, float:4.983932E-39)
            if (r0 == r1) goto L_0x0019
            goto L_0x0043
        L_0x0019:
            java.lang.String r0 = "text"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 0
            goto L_0x0044
        L_0x0024:
            java.lang.String r0 = "src"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 1
            goto L_0x0044
        L_0x002f:
            java.lang.String r0 = "imagevisible"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 3
            goto L_0x0044
        L_0x0039:
            java.lang.String r0 = "childpadding"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 2
            goto L_0x0044
        L_0x0043:
            r0 = -1
        L_0x0044:
            switch(r0) {
                case 0: goto L_0x0057;
                case 1: goto L_0x0053;
                case 2: goto L_0x004f;
                case 3: goto L_0x004b;
                default: goto L_0x0047;
            }
        L_0x0047:
            super.updateAttribute(r3, r4)
            return
        L_0x004b:
            r2.updateImageVisible(r4)
            return
        L_0x004f:
            r2.updatePadding(r4)
            return
        L_0x0053:
            r2.updateSrc(r4)
            return
        L_0x0057:
            r2.updateText(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3OfflineLabelProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateText(Object obj) {
        if (obj instanceof String) {
            ((OfflineLabel) this.mView).setText((String) obj);
        } else {
            ((OfflineLabel) this.mView).setText("");
        }
    }

    private void updateColor(Object obj) {
        if (obj == null) {
            ((OfflineLabel) this.mView).setTextColor(-16777216);
        } else {
            ((OfflineLabel) this.mView).setTextColor(((Integer) obj).intValue());
        }
    }

    private void updateFont(Object obj) {
        if (obj instanceof Integer) {
            ((OfflineLabel) this.mView).setTextSize(((Integer) obj).intValue());
        }
    }

    private void updateSrc(Object obj) {
        String str = (String) obj;
        if (!TextUtils.equals(this.mSrcImageUrl, str)) {
            ((OfflineLabel) this.mView).setImageDrawable(null);
        }
        this.mSrcImageUrl = str;
        if (!TextUtils.isEmpty(this.mSrcImageUrl)) {
            String scheme = Uri.parse(this.mSrcImageUrl).getScheme();
            String jsPath = this.mAjxContext.getJsPath();
            String str2 = this.mSrcImageUrl;
            if (TextUtils.isEmpty(scheme) && !TextUtils.isEmpty(jsPath)) {
                str2 = PathUtils.processPath(jsPath.substring(0, jsPath.lastIndexOf("/") + 1), this.mSrcImageUrl);
            }
            IAjxImageLoader lookupLoader = Ajx.getInstance().lookupLoader(str2);
            if (lookupLoader != null) {
                PictureParams pictureParams = new PictureParams();
                pictureParams.url = str2;
                lookupLoader.loadImage(this.mView, this.mAjxContext, pictureParams, this.mImageCallback);
            }
        }
    }

    private void updatePadding(Object obj) {
        int i = 0;
        if (obj instanceof Integer) {
            i = ((Integer) obj).intValue();
        } else if (obj instanceof String) {
            String str = (String) obj;
            int indexOf = str.indexOf(Params.UNIT_PX);
            if (indexOf != -1) {
                str = str.substring(0, indexOf);
            }
            try {
                i = Integer.parseInt(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ((OfflineLabel) this.mView).setChildPadding(i);
    }

    private void updateImageVisible(Object obj) {
        if (obj instanceof String) {
            ((OfflineLabel) this.mView).setImageVisible(StringUtils.parseBoolean((String) obj));
        } else if (obj instanceof Boolean) {
            ((OfflineLabel) this.mView).setImageVisible(((Boolean) obj).booleanValue());
        } else {
            ((OfflineLabel) this.mView).setImageVisible(false);
        }
    }
}
