package com.sina.weibo.sdk.api;

import android.os.Bundle;
import com.sina.weibo.sdk.constant.WBConstants.Msg;
import com.sina.weibo.sdk.utils.LogUtil;
import java.io.Serializable;

public final class WeiboMultiMessage implements Serializable {
    public static int NineImageType = 2;
    public static int OneImageType = 1;
    private static final String TAG = "WeiboMultiMessage";
    public ImageObject imageObject;
    public BaseMediaObject mediaObject;
    public int msgType;
    public MultiImageObject multiImageObject;
    public TextObject textObject;
    public VideoSourceObject videoSourceObject;

    public WeiboMultiMessage() {
    }

    public WeiboMultiMessage(Bundle bundle) {
        toBundle(bundle);
    }

    public final Bundle toBundle(Bundle bundle) {
        if (this.textObject != null) {
            bundle.putParcelable(Msg.TEXT, this.textObject);
            bundle.putString(Msg.TEXT_EXTRA, this.textObject.toExtraMediaString());
        } else {
            bundle.putParcelable(Msg.TEXT, null);
            bundle.putString(Msg.TEXT_EXTRA, null);
        }
        if (this.imageObject != null) {
            bundle.putParcelable(Msg.IMAGE, this.imageObject);
            bundle.putString(Msg.IMAGE_EXTRA, this.imageObject.toExtraMediaString());
        } else {
            bundle.putParcelable(Msg.IMAGE, null);
            bundle.putString(Msg.IMAGE_EXTRA, null);
        }
        if (this.mediaObject != null) {
            bundle.putParcelable(Msg.MEDIA, this.mediaObject);
            bundle.putString(Msg.MEDIA_EXTRA, this.mediaObject.toExtraMediaString());
        } else {
            bundle.putParcelable(Msg.MEDIA, null);
            bundle.putString(Msg.MEDIA_EXTRA, null);
        }
        if (this.multiImageObject != null) {
            bundle.putParcelable(Msg.MULTI_IMAGE, this.multiImageObject);
        } else {
            bundle.putParcelable(Msg.MULTI_IMAGE, null);
        }
        if (this.videoSourceObject != null) {
            bundle.putParcelable(Msg.VIDEO_SOURCE, this.videoSourceObject);
        } else {
            bundle.putParcelable(Msg.VIDEO_SOURCE, null);
        }
        return bundle;
    }

    public final WeiboMultiMessage toObject(Bundle bundle) {
        this.textObject = (TextObject) bundle.getParcelable(Msg.TEXT);
        if (this.textObject != null) {
            this.textObject.toExtraMediaObject(bundle.getString(Msg.TEXT_EXTRA));
        }
        this.imageObject = (ImageObject) bundle.getParcelable(Msg.IMAGE);
        if (this.imageObject != null) {
            this.imageObject.toExtraMediaObject(bundle.getString(Msg.IMAGE_EXTRA));
        }
        this.mediaObject = (BaseMediaObject) bundle.getParcelable(Msg.MEDIA);
        if (this.mediaObject != null) {
            this.mediaObject.toExtraMediaObject(bundle.getString(Msg.MEDIA_EXTRA));
        }
        this.multiImageObject = (MultiImageObject) bundle.getParcelable(Msg.MULTI_IMAGE);
        this.videoSourceObject = (VideoSourceObject) bundle.getParcelable(Msg.VIDEO_SOURCE);
        return this;
    }

    public final boolean checkArgs() {
        if (this.textObject != null && !this.textObject.checkArgs()) {
            LogUtil.e(TAG, "checkArgs fail, textObject is invalid");
            return false;
        } else if (this.imageObject != null && !this.imageObject.checkArgs()) {
            LogUtil.e(TAG, "checkArgs fail, imageObject is invalid");
            return false;
        } else if (this.mediaObject != null && !this.mediaObject.checkArgs()) {
            LogUtil.e(TAG, "checkArgs fail, mediaObject is invalid");
            return false;
        } else if (this.textObject != null || this.imageObject != null || this.mediaObject != null) {
            return true;
        } else {
            LogUtil.e(TAG, "checkArgs fail, textObject and imageObject and mediaObject is null");
            return false;
        }
    }

    public final void setMsgType(int i) {
        this.msgType = i;
    }

    public final int getMsgType() {
        return this.msgType;
    }
}
