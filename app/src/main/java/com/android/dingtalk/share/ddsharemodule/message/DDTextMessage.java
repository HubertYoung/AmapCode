package com.android.dingtalk.share.ddsharemodule.message;

import android.os.Bundle;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;
import com.android.dingtalk.share.ddsharemodule.message.DDMediaMessage.IMediaObject;

public class DDTextMessage implements IMediaObject {
    private static final String TAG = "DDTextMessage";
    private static final int TEXT_TITLE_MAX_LENGTH = 10240;
    public String mText;

    public int type() {
        return 2;
    }

    public void serialize(Bundle bundle) {
        bundle.putString(ShareConstant.EXTRA_TEXT_OBJECT_TEXT, this.mText);
    }

    public void unserialize(Bundle bundle) {
        this.mText = bundle.getString(ShareConstant.EXTRA_TEXT_OBJECT_TEXT);
    }

    public boolean checkArgs() {
        return (this.mText == null || this.mText.length() == 0 || this.mText.length() > 10240) ? false : true;
    }
}
