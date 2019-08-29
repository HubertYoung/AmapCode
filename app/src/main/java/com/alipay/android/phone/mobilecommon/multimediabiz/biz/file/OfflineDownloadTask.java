package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FileOfflineUploadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileOfflineUploadResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.List;

public class OfflineDownloadTask extends FileMMTask {
    private static final String a;
    private static final Logger b;

    static {
        String simpleName = OfflineDownloadTask.class.getSimpleName();
        a = simpleName;
        b = Logger.getLogger(simpleName);
    }

    protected OfflineDownloadTask(Context context, List reqList, APMultimediaTaskModel taskInfo) {
        super(context, reqList, taskInfo);
    }

    public APFileDownloadRsp taskRun() {
        APFileReq req = (APFileReq) this.fileReqList.get(0);
        APFileDownloadRsp rsp = new APFileDownloadRsp();
        DjangoClient djangoClient = getDjangoClient(req.getRequestParam());
        FileOfflineUploadReq fileOfflineUploadReq = new FileOfflineUploadReq();
        fileOfflineUploadReq.downloadUrl = req.getCloudId();
        fileOfflineUploadReq.synchoronous = req.isSync();
        b.d("fileOfflineUploadReq req: " + fileOfflineUploadReq, new Object[0]);
        FileOfflineUploadResp resp = djangoClient.getFileApi().fileOfflineUpload(fileOfflineUploadReq);
        b.d("fileOfflineUpload resp: " + resp, new Object[0]);
        if (resp != null && resp.isSuccess()) {
            req.setCloudId(resp.getFileInfo().getId());
            rsp.setRetCode(0);
            rsp.setMsg(resp.getMsg());
        } else if (resp != null) {
            rsp.setRetCode(resp.getCode());
            rsp.setMsg(resp.getMsg());
        } else {
            rsp.setRetCode(2);
            rsp.setMsg("FileOfflineUploadResp is null");
        }
        return rsp;
    }
}
