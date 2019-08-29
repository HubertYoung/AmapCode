package com.android.dingtalk.share.ddsharemodule.message;

import android.os.Bundle;
import com.android.dingtalk.share.ddsharemodule.message.DDMediaMessage.IMediaObject;

public class DDVideoMessage implements IMediaObject {
    public boolean checkArgs() {
        return false;
    }

    public void serialize(Bundle bundle) {
    }

    public int type() {
        return 0;
    }

    public void unserialize(Bundle bundle) {
    }
}
