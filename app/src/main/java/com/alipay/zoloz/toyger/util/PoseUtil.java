package com.alipay.zoloz.toyger.util;

import android.content.res.Resources;
import android.graphics.RectF;
import com.alipay.mobile.security.bio.config.bean.Coll;
import com.alipay.mobile.security.bio.utils.StringUtil;
import com.alipay.zoloz.toyger.R;
import com.alipay.zoloz.toyger.algorithm.TGFaceAttr;
import com.alipay.zoloz.toyger.workspace.FaceRemoteConfig;

public class PoseUtil {
    private String topText_angle = "";
    private String topText_blink = "";
    private String topText_blur = "";
    private String topText_integrity = "";
    private String topText_light = "";
    private String topText_max_rectwidth = "";
    private String topText_noface = "";
    private String topText_quality = "";
    private String topText_rectwidth = "";
    private String topText_stay = "";

    private void initLocalText(Resources resources) {
        if (resources != null) {
            this.topText_noface = resources.getString(R.string.topText_noface);
            this.topText_light = resources.getString(R.string.topText_light);
            this.topText_rectwidth = resources.getString(R.string.topText_rectwidth);
            this.topText_integrity = resources.getString(R.string.topText_integrity);
            this.topText_angle = resources.getString(R.string.topText_angle);
            this.topText_blur = resources.getString(R.string.topText_blur);
            this.topText_quality = resources.getString(R.string.topText_quality);
            this.topText_blink = resources.getString(R.string.topText_blink);
            this.topText_stay = resources.getString(R.string.topText_stay);
            this.topText_max_rectwidth = resources.getString(R.string.topText_max_rectwidth);
        }
    }

    private void initRemoteText(FaceRemoteConfig faceRemoteConfig) {
        if (faceRemoteConfig != null) {
            Coll coll = faceRemoteConfig.getColl();
            if (coll != null) {
                if (!StringUtil.isNullorEmpty(coll.getTopText_noface())) {
                    this.topText_noface = coll.getTopText_noface();
                }
                if (!StringUtil.isNullorEmpty(coll.getTopText_light())) {
                    this.topText_light = coll.getTopText_light();
                }
                if (!StringUtil.isNullorEmpty(coll.getTopText_rectwidth())) {
                    this.topText_rectwidth = coll.getTopText_rectwidth();
                }
                if (!StringUtil.isNullorEmpty(coll.getTopText_integrity())) {
                    this.topText_integrity = coll.getTopText_integrity();
                }
                if (!StringUtil.isNullorEmpty(coll.getTopText_angle())) {
                    this.topText_angle = coll.getTopText_angle();
                }
                if (!StringUtil.isNullorEmpty(coll.getTopText_blur())) {
                    this.topText_blur = coll.getTopText_blur();
                }
                if (!StringUtil.isNullorEmpty(coll.getTopText_quality())) {
                    this.topText_quality = coll.getTopText_quality();
                }
                if (!StringUtil.isNullorEmpty(coll.getTopText_blink())) {
                    this.topText_blink = coll.getTopText_blink();
                }
                if (!StringUtil.isNullorEmpty(coll.getTopText_stay())) {
                    this.topText_stay = coll.getTopText_stay();
                }
                if (!StringUtil.isNullorEmpty(coll.getTopText_max_rectwidth())) {
                    this.topText_max_rectwidth = coll.getTopText_max_rectwidth();
                }
            }
        }
    }

    private void initText(FaceRemoteConfig faceRemoteConfig, Resources resources) {
        initLocalText(resources);
        initRemoteText(faceRemoteConfig);
    }

    private boolean isFaceInScreen(TGFaceAttr tGFaceAttr) {
        if (tGFaceAttr == null) {
            return false;
        }
        RectF rectF = tGFaceAttr.faceRegion;
        if (rectF == null || rectF.left < 0.0f || rectF.top < 0.0f || rectF.left + rectF.width() > 1.0f) {
            return false;
        }
        if (rectF.height() + rectF.top <= 1.0f) {
            return true;
        }
        return false;
    }

    public PoseUtil(FaceRemoteConfig faceRemoteConfig, Resources resources) {
        initText(faceRemoteConfig, resources);
    }

    public String getTopText_noface() {
        return this.topText_noface;
    }

    public String getTopText_quality() {
        return this.topText_quality;
    }

    public String getTopText_blink() {
        return this.topText_blink;
    }

    public String getTopText_light() {
        return this.topText_light;
    }

    public String getTopText_rectwidth() {
        return this.topText_rectwidth;
    }

    public String getTopText_integrity() {
        return this.topText_integrity;
    }

    public String getTopText_angle() {
        return this.topText_angle;
    }

    public String getTopText_blur() {
        return this.topText_blur;
    }

    public String getTopText_stay() {
        return this.topText_stay;
    }

    public String getTopText_max_rectwidth() {
        return this.topText_max_rectwidth;
    }
}
