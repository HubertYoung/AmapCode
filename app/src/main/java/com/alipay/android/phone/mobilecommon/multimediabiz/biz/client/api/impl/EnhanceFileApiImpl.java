package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.db.UpCacheHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FileRapidUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FileUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.InputStreamUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileParallelUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp;
import org.apache.http.client.HttpClient;

public class EnhanceFileApiImpl extends FileApiImpl {
    public EnhanceFileApiImpl(DjangoClient djangoClient, ConnectionManager<HttpClient> connectionManager) {
        super(djangoClient, connectionManager);
    }

    public FileUpResp uploadDirectRapid(FileRapidUpReq fileRapidUpReq) {
        String md5 = a(fileRapidUpReq.getPublic(), fileRapidUpReq.getMd5());
        FileUpResp upResp = a(FileUpResp.class, md5);
        if (upResp != null) {
            return upResp;
        }
        FileUpResp upResp2 = super.uploadDirectRapid(fileRapidUpReq);
        a(upResp2, md5);
        return upResp2;
    }

    public FileUpResp uploadDirect(FileUpReq fileUpReq) {
        String md5 = a(fileUpReq.getPublic(), fileUpReq.getMd5());
        FileUpResp upResp = a(FileUpResp.class, md5);
        if (upResp != null) {
            return upResp;
        }
        FileUpResp upResp2 = super.uploadDirect(fileUpReq);
        a(upResp2, md5);
        return upResp2;
    }

    public FileUpResp uploadDirect(InputStreamUpReq upReq) {
        String md5 = a(upReq.getPublic(), upReq.getMd5());
        FileUpResp upResp = a(FileUpResp.class, md5);
        if (upResp != null) {
            return upResp;
        }
        FileUpResp upResp2 = super.uploadDirect(upReq);
        a(upResp2, md5);
        return upResp2;
    }

    public FileUpResp uploadRange(FileUpReq fileUpReq) {
        String md5 = a(fileUpReq.getPublic(), fileUpReq.getMd5());
        FileUpResp upResp = a(FileUpResp.class, md5);
        if (upResp != null) {
            return upResp;
        }
        FileUpResp upResp2 = super.uploadRange(fileUpReq);
        a(upResp2, md5);
        return upResp2;
    }

    public FileUpResp uploadRange(InputStreamUpReq upReq) {
        String md5 = a(upReq.getPublic(), upReq.getMd5());
        FileUpResp upResp = a(FileUpResp.class, md5);
        if (upResp != null) {
            return upResp;
        }
        FileUpResp upResp2 = super.uploadRange(upReq);
        a(upResp2, md5);
        return upResp2;
    }

    public FileUpResp uploadRapidRange(FileRapidUpReq fileRapidUpReq) {
        String md5 = a(fileRapidUpReq.getPublic(), fileRapidUpReq.getMd5());
        FileUpResp upResp = a(FileUpResp.class, md5);
        if (upResp != null) {
            return upResp;
        }
        FileUpResp upResp2 = super.uploadRapidRange(fileRapidUpReq);
        a(upResp2, md5);
        return upResp2;
    }

    public FileParallelUpResp uploadParallelRapidRange(FileRapidUpReq fileRapidUpReq) {
        String md5 = a(fileRapidUpReq.getPublic(), fileRapidUpReq.getMd5());
        FileParallelUpResp upResp = (FileParallelUpResp) a(FileParallelUpResp.class, md5);
        if (upResp != null) {
            return upResp;
        }
        FileParallelUpResp upResp2 = super.uploadParallelRapidRange(fileRapidUpReq);
        a((FileUpResp) upResp2, md5);
        return upResp2;
    }

    public FileParallelUpResp uploadParallelRange(FileUpReq fileUpReq) {
        String md5 = a(fileUpReq.getPublic(), fileUpReq.getMd5());
        FileParallelUpResp upResp = (FileParallelUpResp) a(FileParallelUpResp.class, md5);
        if (upResp != null) {
            return upResp;
        }
        FileParallelUpResp upResp2 = super.uploadParallelRange(fileUpReq);
        a((FileUpResp) upResp2, md5);
        return upResp2;
    }

    public FileParallelUpResp uploadParallelRange(InputStreamUpReq upReq) {
        String md5 = a(upReq.getPublic(), upReq.getMd5());
        FileParallelUpResp upResp = (FileParallelUpResp) a(FileParallelUpResp.class, md5);
        if (upResp != null) {
            return upResp;
        }
        FileParallelUpResp upResp2 = super.uploadParallelRange(upReq);
        a((FileUpResp) upResp2, md5);
        return upResp2;
    }

    private static <T extends FileUpResp> T a(Class<T> clazz, String md5) {
        return UpCacheHelper.loadExistsResult(clazz, md5);
    }

    private static void a(FileUpResp rsp, String md5) {
        UpCacheHelper.saveToLocal(rsp, md5);
    }

    private static String a(Boolean setPublic, String md5) {
        if (setPublic == null || !setPublic.booleanValue()) {
            return md5;
        }
        return md5 + "_pub";
    }
}
