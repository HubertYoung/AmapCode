package com.alipay.multimedia.js.video;

import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.APAlbumVideoInfo;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.io.FileUtils;
import com.alipay.multimedia.io.PathUtils;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.file.H5FileDownloadPlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;

public class H5VideoCompressPlugin extends MMH5SimplePlugin {

    public class CompressVideoParams {
        @JSONField(name = "business")
        public String business = "apm-h5";
        @JSONField(name = "videoPath")
        public String videoPath;

        public CompressVideoParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public H5VideoCompressPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("compressVideo");
    }

    public boolean handleEvent(H5Event event, final H5BridgeContext context) {
        Logger.debug("H5VideoCompress", "handleEvent action: " + event.getAction() + ", params: " + event.getParam());
        final CompressVideoParams params = (CompressVideoParams) parseParams(event, CompressVideoParams.class);
        if (params == null || !FileUtils.checkFile(PathUtils.trimFilePath(params.videoPath))) {
            return context.sendError(event, Error.INVALID_PARAM);
        }
        final MultimediaVideoService service = (MultimediaVideoService) Utils.getService(MultimediaVideoService.class);
        if (service == null) {
            return context.sendError(event, Error.UNKNOWN_ERROR);
        }
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                int i;
                JSONObject result = new JSONObject();
                try {
                    APAlbumVideoInfo info = service.compressVideo(H5VideoCompressPlugin.this.decodeToPath(params.videoPath), params.business);
                    if (info.mSuccess) {
                        i = 0;
                    } else {
                        i = -1;
                    }
                    result.put((String) "error", (Object) Integer.valueOf(i));
                    result.put((String) H5FileDownloadPlugin.RESULT_IDENTIFIER, (Object) info.mId);
                    result.put((String) "tempPath", (Object) H5VideoCompressPlugin.this.encodeToLocalId(info.mPath));
                    result.put((String) "duration", (Object) Integer.valueOf(info.mDuration));
                    result.put((String) "size", (Object) Long.valueOf(info.mSize));
                } catch (Exception e) {
                    result.put((String) "error", (Object) Integer.valueOf(-1));
                    result.put((String) "errorMessage", (Object) e.getMessage());
                }
                context.sendBridgeResult(result);
            }
        });
        return true;
    }
}
