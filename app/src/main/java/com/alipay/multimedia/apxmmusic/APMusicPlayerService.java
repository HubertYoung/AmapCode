package com.alipay.multimedia.apxmmusic;

import android.os.Bundle;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.multimedia.mediaplayer.service.APMediaPlayerService;

public abstract class APMusicPlayerService extends ExternalService implements APMediaPlayerService {
    public APMediaPlayerService createPlayService(Bundle extra) {
        return null;
    }

    public void invalidate() {
    }
}
