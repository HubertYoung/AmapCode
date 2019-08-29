package com.alipay.mobile.common.nbnet.biz.upload;

import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.nbnet.biz.db.UploadRecordDao;
import com.alipay.mobile.common.nbnet.biz.db.UploadRecordDo;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;

public final class UploadDBHelper {
    public static final void a(NBNetUploadResponse uploadResponse, UploadActionSession uploadAction, ContentDescription contentDescription) {
        try {
            b(uploadResponse, uploadAction, contentDescription);
        } catch (Throwable e) {
            NBNetLogCat.b("UploadDBHelper", "updataRecord exception. ", e);
        }
    }

    private static void b(NBNetUploadResponse uploadResponse, UploadActionSession uploadAction, ContentDescription contentDescription) {
        if (uploadAction == null || uploadAction.c()) {
            return;
        }
        if ((uploadAction.d() || uploadAction.e() || uploadAction.f()) && contentDescription != null && contentDescription.c() && !TextUtils.isEmpty(contentDescription.a())) {
            UploadRecordDo uploadRecordDo = uploadAction.j();
            if (uploadRecordDo == null || TextUtils.isEmpty(uploadRecordDo.md5)) {
                uploadRecordDo = new UploadRecordDo();
                uploadRecordDo.md5 = contentDescription.a();
            }
            if (!TextUtils.isEmpty(contentDescription.f())) {
                uploadRecordDo.fileId = contentDescription.f();
            }
            if (!TextUtils.isEmpty(uploadResponse.getContent())) {
                uploadRecordDo.resultContent = uploadResponse.getContent();
            }
            if (!TextUtils.isEmpty(uploadResponse.getTraceId())) {
                uploadRecordDo.traceId = uploadResponse.getTraceId();
            }
            if (uploadAction.f()) {
                if (TextUtils.isEmpty(uploadResponse.getFileId())) {
                    NBNetLogCat.d("UploadDBHelper", "uploadAction is done, but fileId maybe empty.");
                    return;
                }
                uploadRecordDo.uploadStatus = 3;
            } else if (uploadAction.d()) {
                uploadRecordDo.uploadStatus = 2;
            } else if (uploadAction.e()) {
                Pair offset = uploadAction.b;
                if (offset == null) {
                    uploadRecordDo.uploadStatus = 2;
                } else {
                    uploadRecordDo.uploadStatus = 1;
                    uploadRecordDo.offset = offset.first + ":" + offset.second;
                }
            }
            UploadRecordDao.a(NBNetEnvUtils.a()).b(uploadRecordDo);
        }
    }
}
