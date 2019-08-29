package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import android.content.Context;
import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.BaseInfo;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.Base64Optimization;
import java.lang.ref.WeakReference;

public class BaseReq extends BaseInfo {
    public static final String KEY_FILE_KEY = "filekey";
    public static final String KEY_NETCHECK = "netcheck";
    public static final String KEY_REFID = "refid";
    public static final String KEY_SSID = "ssid";
    public static final int MAX_SUPER_SIZE = 16000;
    public static final int PRIORITY_HIGH = 10;
    public static final int PRIORITY_LOW = 1;
    public static final int PRIORITY_MID = 5;
    public static final int QUALITY_DJ_DEFUALT = 80;
    public static final int QUALITY_DJ_MIN = 40;
    public static final int QUALITY_NONE = -1;
    public String aliasPath;
    private boolean bProgressive = false;
    public Base64Optimization base64Optimization;
    public BaseOptions baseOptions;
    public Bundle bundle;
    public CutScaleType cutScaleType;
    public boolean detectedGif;
    public boolean enableSaliency = false;
    public String fileKey;
    public Integer height;
    public APImageMarkRequest imageMarkRequest;
    private WeakReference<Context> mContext;
    public ImageWorkerPlugin plugin;
    private Integer quality = Integer.valueOf(-1);
    public Float scale;
    public CutScaleType secondaryCutScaleType;
    public boolean shareGifMemCache = true;
    public Size srcSize;
    public boolean usingSourceType;
    public Integer width;

    public void setQuality(int quality2) {
        this.quality = Integer.valueOf(checkQuality(quality2));
    }

    public int getQuality() {
        return this.quality.intValue();
    }

    public void setPriority(int priority) {
        super.setPriority(priority);
    }

    public int getPriority() {
        return super.getPriority();
    }

    public void setProgressive(boolean bProgressive2) {
        this.bProgressive = bProgressive2;
    }

    public boolean isProgressive() {
        return this.bProgressive;
    }

    public void setContext(Context context) {
        if (context != null) {
            this.mContext = new WeakReference<>(context);
        }
    }

    public Context getContext() {
        if (this.mContext == null) {
            return null;
        }
        return (Context) this.mContext.get();
    }

    private int checkQuality(int quality2) {
        if (quality2 <= 0) {
            return -1;
        }
        if (quality2 >= 80) {
            return 80;
        }
        if (quality2 <= 0 || quality2 >= 40) {
            return (quality2 / 10) * 10;
        }
        return 40;
    }

    public static boolean isSuperSize(Integer size) {
        return (size == null || size.intValue() <= 16000 || size.intValue() == Integer.MAX_VALUE) ? false : true;
    }

    public void setEnableSaliency(boolean enable) {
        this.enableSaliency = enable;
    }

    public String toString() {
        return "BaseReq{width=" + this.width + ", height=" + this.height + ", quality=" + this.quality + ", bProgressive=" + this.bProgressive + ", scale=" + this.scale + ", srcSize=" + this.srcSize + ", businessId='" + this.businessId + ", cutScaleType=" + this.cutScaleType + ", plugin=" + this.plugin + ", imageMarkRequest=" + this.imageMarkRequest + ", aliasPath=" + this.aliasPath + ", usingSourceType=" + this.usingSourceType + ", context=" + getContext() + ", secondaryCutScaleType=" + this.secondaryCutScaleType + ", detectedGif=" + this.detectedGif + ", shareGifMemCache=" + this.shareGifMemCache + ", enableSaliency=" + this.enableSaliency + ", baseInfo=" + super.toString() + ", fileKey=" + this.fileKey + ", bundle=" + this.bundle + '}';
    }
}
