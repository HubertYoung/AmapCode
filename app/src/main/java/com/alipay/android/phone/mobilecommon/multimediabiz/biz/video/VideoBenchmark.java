package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import java.util.HashMap;

public class VideoBenchmark {
    public static final String KEY_BUFFER_PREPARED = "buffer_prepared";
    public static final String KEY_CACHE = "video_cache_";
    public static final String KEY_CACHE_DETAIL = "cache_detail";
    public static final String KEY_CACHE_LOADING_TIME = "cache_loading_time";
    public static final String KEY_CAMERA_FPS = "camera_fps";
    public static final String KEY_CAMERA_INIT_BEGIN = "camera_init_begin";
    public static final String KEY_CAMERA_INIT_END = "camera_init_end";
    public static final String KEY_CAMERA_SURFACE_READY = "camera_surface_ready";
    public static final String KEY_ENCODE_AVG_TIME = "encode_avg_time";
    public static final String KEY_ENCODE_BEGIN = "encode_begin";
    public static final String KEY_ENCODE_METHOD = "encode_method";
    public static final String KEY_FILE_SIZE = "file_size";
    public static final String KEY_FIRST_FRAME_SHOW = "first_frame_show";
    public static final String KEY_GL_PREPARED = "gl_prepared";
    public static final String KEY_PLAY = "video_play_";
    public static final String KEY_PLAY_START = "play_start";
    public static final String KEY_REC = "video_rec_";
    public static final String KEY_RECORD_FINISH = "record_finish";
    public static final String KEY_RECORD_START = "record_start";
    public static final String KEY_RECORD_STOP = "record_stop";
    public static final String KEY_THUMB_DECODE_TIME = "thumb_decode_time";
    public static final String KEY_THUMB_RENDER_BEGIN = "thumb_render_begin";
    public static final String KEY_THUMB_RENDER_END = "thumb_render_end";
    public static final String KEY_VIDEO_DURATION = "video_duration";
    public static final String KEY_VIDEO_FPS = "video_fps";
    public static final String KEY_VIDEO_PREPARED = "video_prepared";
    public static final String KEY_VIEW_CREATE = "view_create";
    public static final String TAG = "VideoBenchmark";
    public static final int THUMB_DISK_CACHE = 2;
    public static final int THUMB_MEM_CACHE = 1;
    public static final int THUMB_NO_CACHE = 0;
    public static final int VIDEO_DISK_CACHE = 5;
    public static final int VIDEO_ENCODE_MEDIACODEC = 1;
    public static final int VIDEO_ENCODE_OMX = 2;
    public static final int VIDEO_MEM_CACHE = 4;
    public static final int VIDEO_NO_CACHE = 3;
    private static HashMap<String, Bundle> a = new HashMap<>();
    private static int[] b = {0, 0, 0, 0, 0, 0};
    private static int[] c = {0, 0, 0, 0, 0, 0};
    private static long d = 0;

    public static Bundle getBundle(String key) {
        if (a.containsKey(key)) {
            return a.get(key);
        }
        Bundle bundle = new Bundle();
        a.put(key, bundle);
        return bundle;
    }

    public static void reportRecording(String key) {
        if (a.get(key) != null) {
            long file_size = a.get(key).getLong("file_size", -1);
            long video_duration = a.get(key).getLong(KEY_VIDEO_DURATION, -1);
            long buffer_time = a.get(key).getLong(KEY_BUFFER_PREPARED) - a.get(key).getLong(KEY_VIEW_CREATE);
            long camera_time = a.get(key).getLong(KEY_CAMERA_INIT_END) - a.get(key).getLong(KEY_CAMERA_INIT_BEGIN);
            long preview_time = a.get(key).getLong(KEY_FIRST_FRAME_SHOW) - a.get(key).getLong(KEY_CAMERA_INIT_END);
            long camera_surface_ready_time = 0;
            if (a.get(key).getLong(KEY_CAMERA_SURFACE_READY) > 0) {
                camera_surface_ready_time = a.get(key).getLong(KEY_CAMERA_SURFACE_READY) - a.get(key).getLong(KEY_VIEW_CREATE);
            }
            long encode_time = a.get(key).getLong("encode_avg_time");
            int video_fps = a.get(key).getInt(KEY_VIDEO_FPS);
            String camera_fps = a.get(key).getString(KEY_CAMERA_FPS);
            int encode_method = a.get(key).getInt(KEY_ENCODE_METHOD);
            a.get(key).clear();
            UCLogUtil.UC_MM_C43(true, file_size, video_duration, buffer_time, camera_time, preview_time, a.get(key).getLong(KEY_ENCODE_BEGIN) - a.get(key).getLong("record_start"), a.get(key).getLong("record_finish") - a.get(key).getLong(KEY_RECORD_STOP), encode_time, video_fps, camera_fps, encode_method, camera_surface_ready_time);
        }
    }

    public static void reportPlaying(String key) {
        if (a.get(key) != null) {
            a.remove(key);
            UCLogUtil.UC_MM_C44(true, a.get(key).getLong(KEY_FIRST_FRAME_SHOW) - a.get(key).getLong(KEY_VIDEO_PREPARED), a.get(key).getLong(KEY_BUFFER_PREPARED) - a.get(key).getLong(KEY_VIEW_CREATE), a.get(key).getLong(KEY_GL_PREPARED) - a.get(key).getLong(KEY_PLAY_START), a.get(key).getLong(KEY_THUMB_DECODE_TIME, -1), a.get(key).getLong(KEY_THUMB_RENDER_END) - a.get(key).getLong(KEY_THUMB_RENDER_BEGIN), a.get(key).getLong(KEY_VIDEO_PREPARED) - a.get(key).getLong(KEY_THUMB_RENDER_END));
        }
    }

    public static void reportCacheLoading(String key) {
        Bundle bundle = a.get(key);
        if (bundle != null) {
            int cache_type = bundle.getInt(KEY_CACHE_DETAIL);
            long cache_time = bundle.getLong("cache_loading_time");
            a.remove(key);
            int[] iArr = b;
            iArr[cache_type] = iArr[cache_type] + 1;
            int[] iArr2 = c;
            iArr2[cache_type] = (int) (((long) iArr2[cache_type]) + cache_time);
            if (d == 0) {
                d = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - d > 60000) {
                UCLogUtil.UC_MM_C45(true, c, b);
                int[] iArr3 = b;
                int[] iArr4 = b;
                b[2] = 0;
                iArr4[1] = 0;
                iArr3[0] = 0;
                int[] iArr5 = b;
                int[] iArr6 = b;
                b[5] = 0;
                iArr6[4] = 0;
                iArr5[3] = 0;
                int[] iArr7 = c;
                int[] iArr8 = c;
                c[2] = 0;
                iArr8[1] = 0;
                iArr7[0] = 0;
                int[] iArr9 = c;
                int[] iArr10 = c;
                c[5] = 0;
                iArr10[4] = 0;
                iArr9[3] = 0;
                d = 0;
            }
        }
    }
}
