package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils;

import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;

public class AudioBenchmark {
    public static final int DISK_CACHE = 2;
    public static final String KEY_CACHE_LOADING_TIME = "cache_loading_time";
    public static final String KEY_DECODE_ERR_CODE = "decode_err_code";
    public static final String KEY_DECODE_TIME = "decode_time";
    public static final String KEY_DOWNLOAD_SUCCESS = "download_success";
    public static final String KEY_DOWNLOAD_TIME = "download_time";
    public static final String KEY_ENCODE_AVG_TIME = "encode_avg_time";
    public static final String KEY_ENCODE_ERR_CODE = "encode_err_code";
    public static final String KEY_FILE_SIZE = "file_size";
    public static final String KEY_HAS_NETWORK = "has_network";
    public static final String KEY_RECORD_CANCEL = "record_cancel";
    public static final String KEY_RECORD_FINISH = "record_finish";
    public static final String KEY_RECORD_PREPARED = "record_prepared";
    public static final String KEY_RECORD_START = "record_start";
    public static final String KEY_TRACE_ID = "TRACE_ID";
    public static final String KEY_UPLOAD_FINISH = "upload_finish";
    public static final String KEY_UPLOAD_SUCCESS = "upload_success";
    public static final String KEY_USE_CACHE = "use_cache";
    public static final int MEM_CACHE = 1;
    public static final int NO_CACHE = 0;
    public static final String TAG = "AudioBenchmark";
    private static int[] a = {0, 0, 0};
    private static int[] b = {0, 0, 0};
    private static long c = 0;

    public static void reportUploading(APAudioInfo audioInfo) {
        boolean is_cancel = audioInfo.getExtra().getBoolean(KEY_RECORD_CANCEL);
        boolean upload_success = audioInfo.getExtra().getBoolean(KEY_UPLOAD_SUCCESS);
        long prepare_time = audioInfo.getExtra().getLong(KEY_RECORD_PREPARED) - audioInfo.getExtra().getLong("record_start");
        long finish_time = (is_cancel || !upload_success) ? 0 : audioInfo.getExtra().getLong(KEY_UPLOAD_FINISH) - audioInfo.getExtra().getLong("record_finish");
        UCLogUtil.UC_MM_C37(upload_success, audioInfo.getExtra().getLong("file_size"), finish_time, is_cancel, prepare_time, audioInfo.getExtra().getLong("encode_avg_time"), audioInfo.getExtra().getInt(KEY_ENCODE_ERR_CODE), audioInfo.getExtra().getString("TRACE_ID"), audioInfo.getBizType(), (long) audioInfo.getDuration(), audioInfo.getCloudId(), !audioInfo.getExtra().getBoolean(KEY_HAS_NETWORK) && !upload_success);
    }

    public static void reportDownloading(APAudioInfo audioInfo) {
        long file_size = audioInfo.getExtra().getLong("file_size");
        long download_time = audioInfo.getExtra().getLong(KEY_DOWNLOAD_TIME);
        boolean download_success = audioInfo.getExtra().getBoolean(KEY_DOWNLOAD_SUCCESS);
        UCLogUtil.UC_MM_C38(download_success, file_size, download_time, audioInfo.getExtra().getLong(KEY_DECODE_TIME), audioInfo.getExtra().getInt(KEY_DECODE_ERR_CODE), audioInfo.getExtra().getString("TRACE_ID"), audioInfo.getBizType(), !download_success && !audioInfo.getExtra().getBoolean(KEY_HAS_NETWORK));
    }

    public static void reportCacheLoading(APAudioInfo audioInfo) {
        int cache_type = audioInfo.getExtra().getInt(KEY_USE_CACHE);
        long cache_time = audioInfo.getExtra().getLong("cache_loading_time");
        int[] iArr = a;
        iArr[cache_type] = iArr[cache_type] + 1;
        int[] iArr2 = b;
        iArr2[cache_type] = (int) (((long) iArr2[cache_type]) + cache_time);
        if (c == 0) {
            c = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - c > 60000) {
            UCLogUtil.UC_MM_C39(true, b, a);
            int[] iArr3 = a;
            int[] iArr4 = a;
            a[2] = 0;
            iArr4[1] = 0;
            iArr3[0] = 0;
            int[] iArr5 = b;
            int[] iArr6 = b;
            b[2] = 0;
            iArr6[1] = 0;
            iArr5[0] = 0;
            c = 0;
        }
    }
}
