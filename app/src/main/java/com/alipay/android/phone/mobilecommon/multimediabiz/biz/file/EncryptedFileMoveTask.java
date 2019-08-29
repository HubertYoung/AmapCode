package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileRsp;
import java.util.List;

public class EncryptedFileMoveTask extends FileMMTask {
    private List<APFileReq> a = null;
    private APFileDownCallback b;

    public EncryptedFileMoveTask(Context context, List<APFileReq> reqList, APMultimediaTaskModel taskInfo, APFileDownCallback callback) {
        super(context, reqList, taskInfo);
        this.a = reqList;
        this.b = callback;
    }

    public APFileRsp taskRun() {
        String cacheFilePath = addCacheFile(this.a.get(0));
        APFileDownloadRsp rsp = new APFileDownloadRsp();
        if (!TextUtils.isEmpty(cacheFilePath)) {
            rsp.setRetCode(0);
            rsp.setMsg("get from cache file");
            rsp.setFileReq(this.a.get(0));
        } else {
            rsp.setRetCode(7);
            rsp.setMsg("file path empty");
            rsp.setFileReq(this.a.get(0));
        }
        this.b.onDownloadFinished(null, rsp);
        return null;
    }
}
