package com.android.dingtalk.share.ddsharemodule.message;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;
import java.io.ByteArrayOutputStream;

public class DDMediaMessage {
    private static final int MAX_CONTENT_LENGTH = 1024;
    private static final int MAX_THUMB_DATA_LENGTH = 32768;
    private static final int MAX_TITLE_LENGTH = 512;
    private static final String TAG = "DDMediaMessage";
    public String mContent;
    public IMediaObject mMediaObject;
    public int mSDKVersion;
    public byte[] mThumbData;
    public String mThumbUrl;
    public String mTitle;
    public String mUrl;

    public static class Builder {
        public static Bundle toBundle(DDMediaMessage dDMediaMessage) {
            Bundle bundle = new Bundle();
            bundle.putInt(ShareConstant.EXTRA_AP_OBJECT_SDK_VERSION, dDMediaMessage.mSDKVersion);
            bundle.putString(ShareConstant.EXTRA_AP_OBJECT_TITLE, dDMediaMessage.mTitle);
            bundle.putString(ShareConstant.EXTRA_AP_OBJECT_DESCRIPTION, dDMediaMessage.mContent);
            bundle.putByteArray(ShareConstant.EXTRA_AP_OBJECT_THUMB_DATA, dDMediaMessage.mThumbData);
            bundle.putString(ShareConstant.EXTRA_AP_OBJECT_THUMB_URL, dDMediaMessage.mThumbUrl);
            if (dDMediaMessage.mMediaObject != null) {
                bundle.putString(ShareConstant.EXTRA_AP_OBJECT_IDENTIFIER, dDMediaMessage.mMediaObject.getClass().getName());
                dDMediaMessage.mMediaObject.serialize(bundle);
            }
            return bundle;
        }

        public static DDMediaMessage fromBundle(Bundle bundle) {
            DDMediaMessage dDMediaMessage = new DDMediaMessage();
            dDMediaMessage.mSDKVersion = bundle.getInt(ShareConstant.EXTRA_AP_OBJECT_SDK_VERSION);
            dDMediaMessage.mTitle = bundle.getString(ShareConstant.EXTRA_AP_OBJECT_TITLE);
            dDMediaMessage.mContent = bundle.getString(ShareConstant.EXTRA_AP_OBJECT_DESCRIPTION);
            dDMediaMessage.mThumbData = bundle.getByteArray(ShareConstant.EXTRA_AP_OBJECT_THUMB_DATA);
            dDMediaMessage.mThumbUrl = bundle.getString(ShareConstant.EXTRA_AP_OBJECT_THUMB_URL);
            String string = bundle.getString(ShareConstant.EXTRA_AP_OBJECT_IDENTIFIER);
            if (string == null || string.length() <= 0) {
                return dDMediaMessage;
            }
            try {
                dDMediaMessage.mMediaObject = (IMediaObject) Class.forName(string).newInstance();
                dDMediaMessage.mMediaObject.unserialize(bundle);
                return dDMediaMessage;
            } catch (Exception e) {
                e.printStackTrace();
                return dDMediaMessage;
            }
        }
    }

    public interface IMediaObject {
        public static final int TYPE_IMAGE = 3;
        public static final int TYPE_TEXT = 2;
        public static final int TYPE_WEBPAGE = 1;
        public static final int TYPE_ZHIFUBAO = 0;

        boolean checkArgs();

        void serialize(Bundle bundle);

        int type();

        void unserialize(Bundle bundle);
    }

    public DDMediaMessage() {
        this(null);
    }

    public DDMediaMessage(IMediaObject iMediaObject) {
        this.mMediaObject = iMediaObject;
    }

    public final int getType() {
        if (this.mMediaObject == null) {
            return 0;
        }
        return this.mMediaObject.type();
    }

    public final void setThumbImage(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 85, byteArrayOutputStream);
            this.mThumbData = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean checkArgs() {
        if (this.mThumbData != null && this.mThumbData.length > 32768) {
            return false;
        }
        if (this.mTitle != null && this.mTitle.length() > 512) {
            return false;
        }
        if ((this.mContent == null || this.mContent.length() <= 1024) && this.mMediaObject != null) {
            return this.mMediaObject.checkArgs();
        }
        return false;
    }
}
