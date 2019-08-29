package com.android.dingtalk.share.ddsharemodule.message;

import android.os.Bundle;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;
import com.android.dingtalk.share.ddsharemodule.message.DDMediaMessage.IMediaObject;

public class DDZhiFuBaoMesseage implements IMediaObject {
    private static final int MAX_WEBPAGE_URL_LENGTH = 10240;
    private static final String TAG = "DDZhiFuBaoMesseage";
    public String mUrl;

    public int type() {
        return 0;
    }

    public void serialize(Bundle bundle) {
        bundle.putString(ShareConstant.EXTRA_ZHIFUBAO_OBJECT_URL, this.mUrl);
    }

    public void unserialize(Bundle bundle) {
        this.mUrl = bundle.getString(ShareConstant.EXTRA_ZHIFUBAO_OBJECT_URL);
    }

    public boolean checkArgs() {
        return (this.mUrl == null || this.mUrl.length() == 0 || this.mUrl.length() > 10240) ? false : true;
    }
}
