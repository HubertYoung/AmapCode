package com.alipay.mobile.common.nbnet.biz.upload;

import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.common.nbnet.biz.db.UploadRecordDao;
import com.alipay.mobile.common.nbnet.biz.db.UploadRecordDo;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;

public class UploadActionHelper {
    public static final UploadActionSession a(String md5) {
        UploadRecordDao recordDao = UploadRecordDao.a(NBNetEnvUtils.a());
        UploadRecordDo uploadRecordDo = recordDao.a(md5);
        if (uploadRecordDo == null) {
            return UploadActionSession.b();
        }
        if (uploadRecordDo.uploadStatus == 3) {
            UploadActionSession uploadActionSession = UploadActionSession.b();
            if (uploadRecordDo != null) {
                uploadActionSession.a(uploadRecordDo);
            }
            NBNetLogCat.a((String) "UploadActionHelper", (String) "STATUS_DONE to newAskAction");
            return uploadActionSession;
        } else if (uploadRecordDo.uploadStatus == 2) {
            UploadActionSession uploadActionSession2 = UploadActionSession.b();
            uploadActionSession2.a(uploadRecordDo);
            NBNetLogCat.a((String) "UploadActionHelper", (String) "STATUS_UNCONFIRMED to newAskAction");
            return uploadActionSession2;
        } else if (uploadRecordDo.uploadStatus != 1) {
            return a(recordDao, uploadRecordDo);
        } else {
            if (TextUtils.isEmpty(uploadRecordDo.offset)) {
                NBNetLogCat.d("UploadActionHelper", "STATUS_UPLOADING offset is null.");
                return a(recordDao, uploadRecordDo);
            }
            NBNetLogCat.a((String) "UploadActionHelper", "offset is " + uploadRecordDo.offset);
            try {
                String[] offsetBulk = uploadRecordDo.offset.split(":");
                if (offsetBulk.length != 2) {
                    NBNetLogCat.d("UploadActionHelper", "Dirty offset : " + uploadRecordDo.offset);
                    a(recordDao, uploadRecordDo);
                }
                Pair offset = new Pair(Integer.valueOf(Integer.parseInt(offsetBulk[0])), Integer.valueOf(Integer.parseInt(offsetBulk[1])));
                NBNetLogCat.a((String) "UploadActionHelper", "newOffsetAction offset=[" + offset.first + "," + offset.second + "]");
                UploadActionSession uploadActionSession3 = UploadActionSession.a(offset);
                uploadActionSession3.a(uploadRecordDo);
                return uploadActionSession3;
            } catch (Throwable e) {
                NBNetLogCat.b("UploadActionHelper", "Dirty offset exception, offset = " + uploadRecordDo.offset, e);
                return a(recordDao, uploadRecordDo);
            }
        }
    }

    private static final UploadActionSession a(UploadRecordDao recordDao, UploadRecordDo uploadRecordDo) {
        uploadRecordDo.offset = "";
        uploadRecordDo.uploadStatus = 2;
        recordDao.a(uploadRecordDo);
        return UploadActionSession.b();
    }
}
