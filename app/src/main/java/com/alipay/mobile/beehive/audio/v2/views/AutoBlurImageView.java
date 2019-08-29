package com.alipay.mobile.beehive.audio.v2.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.mobile.beehive.audio.activity.GeneralAudioPlayActivity;
import com.alipay.mobile.beehive.audio.utils.BundleLogger;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.beehive.util.blur.StackBlurManager;

public class AutoBlurImageView extends ImageView {
    private static final int BLUR_RADIUS = 5;
    private static final String MULTI_MEDIA_BIZ_ID = "Beehive_Audio";
    private MultimediaImageService mImageServices;
    /* access modifiers changed from: private */
    public BundleLogger mLogger;
    private String mPath;

    public AutoBlurImageView(Context context) {
        this(context, null);
    }

    public AutoBlurImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoBlurImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mLogger = BundleLogger.getLogger(AutoBlurImageView.class);
        this.mImageServices = (MultimediaImageService) MicroServiceUtil.getMicroService(MultimediaImageService.class);
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (!TextUtils.isEmpty(this.mPath)) {
            notifyBlurImageSet();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (!TextUtils.isEmpty(this.mPath)) {
            notifyBlurImageSet();
        }
    }

    private void notifyBlurImageSet() {
        Context ctx = getContext();
        if (ctx instanceof GeneralAudioPlayActivity) {
            ((GeneralAudioPlayActivity) ctx).onPostBlur();
        }
    }

    public void loadImage(final String path) {
        if (!TextUtils.equals(path, this.mPath)) {
            this.mLogger.d("loadImage:###" + path);
            this.mPath = path;
            this.mImageServices.loadImage(path, (ImageView) this, (Drawable) null, (ImageWorkerPlugin) new ImageWorkerPlugin() {
                public final String getPluginKey() {
                    AutoBlurImageView.this.mLogger.d("Blur plugin $getPluginKey: " + path);
                    return path;
                }

                public final Bitmap process(APMultimediaTaskModel apMultimediaTaskModel, Bitmap bitmap) {
                    AutoBlurImageView.this.mLogger.d("Blur plugin processing..");
                    Bitmap afterBlur = new StackBlurManager(bitmap).process(5);
                    if (afterBlur == null) {
                        AutoBlurImageView.this.mLogger.d("Blur bitmap failed.");
                    }
                    return afterBlur;
                }
            }, (String) MULTI_MEDIA_BIZ_ID);
        }
    }

    public void removeImage() {
        loadImage(null);
    }
}
