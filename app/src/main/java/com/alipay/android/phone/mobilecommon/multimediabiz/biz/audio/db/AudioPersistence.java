package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.db;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APStorageCacheInfo;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.config.MultimediaDb;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.DbPersistence;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.nebula.filecache.FileCache;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.JoinPoint;

public class AudioPersistence extends DbPersistence<AudioCacheRecord> {
    private static final Logger a = Logger.getLogger((String) "AudioPersistence");

    public AudioPersistence(Context context) {
        super(context, MultimediaDb.getInstance().getDbHelper(context), AudioCacheRecord.class);
    }

    public boolean saveAudioRecord(APAudioInfo info) {
        AudioCacheRecord record = new AudioCacheRecord();
        record.audioId = !TextUtils.isEmpty(info.getCloudId()) ? info.getCloudId() : info.getLocalId();
        record.createTime = System.currentTimeMillis();
        record.lastModifiedTime = System.currentTimeMillis();
        record.filePath = info.getSavePath();
        record.fileSize = FileUtils.fileSize(info.getSavePath());
        record.cBusinessId = info.businessId;
        record.cLock = info.getExtra().getBoolean(JoinPoint.SYNCHRONIZATION_LOCK, false);
        try {
            save(record);
            return true;
        } catch (Exception e) {
            a.w("saveAudioRecord error: " + record, new Object[0]);
            return false;
        }
    }

    public boolean saveOrUpdateAudioRecord(APAudioInfo info) {
        AudioCacheRecord record;
        String id = info.getLocalId();
        try {
            record = (AudioCacheRecord) query(AudioCacheRecord.class, id);
        } catch (Exception e) {
            record = null;
        }
        if (record != null) {
            try {
                record.audioId = info.getCloudId();
                record.lastModifiedTime = System.currentTimeMillis();
                record.filePath = info.getSavePath();
                record.fileSize = FileUtils.fileSize(info.getSavePath());
                record.cLock = false;
                delete(AudioCacheRecord.class, id);
                save(record);
            } catch (Exception e2) {
                a.w("saveAudioRecord id: " + id + ", info: " + info + ", exp: " + e2, new Object[0]);
                return false;
            }
        } else {
            saveAudioRecord(info);
        }
        return true;
    }

    public boolean updateAudioRecord(APAudioInfo info) {
        try {
            AudioCacheRecord record = (AudioCacheRecord) query(AudioCacheRecord.class, !TextUtils.isEmpty(info.getCloudId()) ? info.getCloudId() : info.getLocalId());
            if (record == null) {
                return false;
            }
            if (TextUtils.isEmpty(record.cBusinessId)) {
                record.cBusinessId = info.businessId;
            }
            record.lastModifiedTime = System.currentTimeMillis();
            record.filePath = info.getSavePath();
            record.fileSize = FileUtils.fileSize(info.getSavePath());
            save(record);
            return true;
        } catch (Exception e) {
            a.w("updateAudioRecord info: " + info + ", exp: " + e, new Object[0]);
            return false;
        }
    }

    public List<AudioCacheRecord> queryUpgradeRecord() {
        List records = new ArrayList();
        try {
            List lockRecords = getDao().queryBuilder().where().eq(APStorageCacheInfo.F_CACHE_LOCK, Boolean.valueOf(true)).query();
            if (lockRecords != null && !lockRecords.isEmpty()) {
                records.addAll(lockRecords);
            }
            List recentRecords = getDao().queryBuilder().limit(Long.valueOf(100)).orderBy(AudioCacheRecord.FIELD_LAST_MODIFIED_TIME, false).where().gt(AudioCacheRecord.FIELD_LAST_MODIFIED_TIME, Long.valueOf(System.currentTimeMillis() - FileCache.EXPIRE_TIME)).query();
            if (recentRecords != null && !recentRecords.isEmpty()) {
                records.addAll(recentRecords);
            }
        } catch (Exception e) {
            a.w("queryUpgradeRecord, exp: " + e, new Object[0]);
        }
        return records;
    }

    public String getTableName() {
        return "tbl_audio_cache";
    }
}
