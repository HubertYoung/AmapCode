package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.db;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DjangoConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class UpCacheHelper {
    public static <T extends FileUpResp> T loadExistsResult(Class<T> clazz, String md5) {
        FileUpResp result = null;
        if (ConfigManager.getInstance().djangoConf().useLocalRapidUpload != 1 || TextUtils.isEmpty(md5)) {
            return null;
        }
        UploadCacheModel model = UploadCacheDbPersistence.get().queryByMd5(md5);
        if (model == null || TextUtils.isEmpty(model.data)) {
            return null;
        }
        try {
            result = (FileUpResp) JSON.parseObject(model.data, clazz);
            Logger.D("UpCacheHelper", "loadExistsResult success: " + result, new Object[0]);
            return result;
        } catch (Exception e) {
            Logger.W("UpCacheHelper", "loadExistsResult error, " + e + ", md5: " + md5 + ", clazz: " + clazz, new Object[0]);
            return result;
        }
    }

    public static void saveToLocal(FileUpResp rsp, String md5) {
        DjangoConf conf = ConfigManager.getInstance().djangoConf();
        if (conf.useLocalRapidUpload == 1 && !TextUtils.isEmpty(md5) && rsp != null && rsp.isSuccess() && rsp.getFileInfo() != null && !TextUtils.isEmpty(rsp.getFileInfo().getId()) && md5.startsWith(rsp.getFileInfo().getMd5())) {
            UploadCacheDbPersistence persistence = UploadCacheDbPersistence.get();
            if (persistence != null) {
                UploadCacheModel model = new UploadCacheModel();
                model.md5 = md5;
                model.data = JSON.toJSONString(rsp);
                try {
                    synchronized (UploadCacheDbPersistence.class) {
                        persistence.save(model);
                    }
                } catch (Exception e) {
                    Logger.W("UpCacheHelper", "saveToLocal error, e: " + e + ", rsp: " + rsp + ", md5: " + md5, new Object[0]);
                }
                persistence.trimDbTableSize(conf.localRapidMaxCount, conf.localRapidTrimCount);
            }
        }
    }
}
