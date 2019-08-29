package com.alipay.android.phone.mobilecommon.multimediabiz.biz.file;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.NBNetUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTaskFactory {
    private static final Logger a = Logger.getLogger((String) "FileTaskFactory");

    public static FileMMTask createUploadMMTask(Context context, APFileReq req, Options options, APMultimediaTaskModel taskModel, APFileUploadCallback cb) {
        FileMMTask task;
        boolean z;
        List list = new ArrayList();
        list.add(req);
        if (!NBNetUtils.getNBNetUPSwitch(req.businessId) || !a(req)) {
            task = new HttpFileUpMMTask(context, list, taskModel, cb);
        } else {
            task = new NBNetFileUpMMTask(context, list, taskModel, cb);
        }
        task.setPriority(req.getPriority());
        Logger logger = a;
        StringBuilder append = new StringBuilder("createUploadTask path: ").append(req.getSavePath()).append(", sliceUpload: ");
        if (!(task instanceof HttpFileUpMMTask)) {
            z = true;
        } else {
            z = false;
        }
        logger.d(append.append(z).toString(), new Object[0]);
        return task;
    }

    private static boolean a(APFileReq req) {
        if (!NBNetUtils.isNBNetUpFileSizeCheck()) {
            return true;
        }
        int limit = NBNetUtils.getNBNetUpFileSizeLimit();
        long dataLen = 0;
        if (req.getUploadData() != null) {
            dataLen = (long) req.getUploadData().length;
        } else {
            File file = new File(PathUtils.extractPath(req.getSavePath()));
            if (file.exists() && file.isFile()) {
                dataLen = file.length();
            }
        }
        if (dataLen < ((long) limit) * 1048576) {
            return true;
        }
        return false;
    }
}
