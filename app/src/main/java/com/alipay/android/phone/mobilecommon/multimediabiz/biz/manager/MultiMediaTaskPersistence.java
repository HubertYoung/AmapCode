package com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.config.MultimediaTaskDb;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.DbPersistence;

public class MultiMediaTaskPersistence extends DbPersistence<APMultimediaTaskModel> {
    public MultiMediaTaskPersistence(Context context) {
        super(context, MultimediaTaskDb.getInstance().getDbHelper(context), APMultimediaTaskModel.class);
    }

    public String getTableName() {
        return "multi_media_task";
    }
}
