package com.ant.phone.xmedia.benchmark;

import android.os.Bundle;
import com.ant.phone.xmedia.uclog.UCLogUtil;
import java.util.HashMap;

public class AlgorithmBenchmark {
    public static int a = 1;
    public static int b = 2;
    private static HashMap<String, Bundle> c = new HashMap<>();

    public static Bundle a(String key) {
        if (c.containsKey(key)) {
            return c.get(key);
        }
        Bundle bundle = new Bundle();
        c.put(key, bundle);
        return bundle;
    }

    public static void a(int result) {
        Bundle bundle = c.get("KEY_INIT");
        if (bundle != null) {
            int i = result;
            UCLogUtil.a(i, bundle.getString("MODEL_SIZE"), bundle.getLong("ENGINE_INIT_END") - bundle.getLong("ENGINE_INIT_START"), bundle.getString("BUSSINESS_ID"), bundle.getString("MODEL_FILE_ID"), bundle.getInt("ENGINE_TYPE"));
        }
    }

    public static void b(int result) {
        Bundle bundle = c.get("KEY_IMAGE_CLS");
        if (bundle != null) {
            int i = result;
            UCLogUtil.a(i, bundle.getString("FILE_SIZE"), bundle.getLong("IMAGE_CLS_END") - bundle.getLong("IMAGE_CLS_START"), bundle.getString("BUSSINESS_ID"), bundle.getString("MODEL_FILE_ID"), bundle.getInt("ERROR_CODE"), bundle.getString("ERROR_MSG"));
        }
    }

    public static void c(int result) {
        Bundle bundle = c.get("KEY_IMAGE_DET");
        if (bundle != null) {
            int i = result;
            UCLogUtil.b(i, bundle.getString("FILE_SIZE"), bundle.getLong("IMAGE_DET_END") - bundle.getLong("IMAGE_DET_START"), bundle.getString("BUSSINESS_ID"), bundle.getString("MODEL_FILE_ID"), bundle.getInt("ERROR_CODE"), bundle.getString("ERROR_MSG"));
        }
    }

    public static void a() {
        Bundle bundle = c.get("KEY_FRAME_DET");
        if (bundle != null) {
            int framecount = bundle.getInt("FRAME_TOTAL_COUNT_XNN");
            if (framecount > 0) {
                UCLogUtil.a(bundle.getString("FILE_SIZE"), (long) ((((float) (bundle.getLong("FRAME_DET_END") - bundle.getLong("FRAME_DET_START"))) * 1.0f) / ((float) framecount)), bundle.getString("BUSSINESS_ID"), bundle.getString("MODEL_FILE_ID"), (long) ((((float) bundle.getLong("ENGINE_TOTAL_TIME_XNN")) * 1.0f) / ((float) framecount)));
            }
        }
    }

    public static void b() {
        Bundle bundle = c.get("KEY_FRAME_CLS");
        if (bundle != null) {
            int framecount = bundle.getInt("FRAME_TOTAL_COUNT_XNN");
            if (framecount > 0) {
                UCLogUtil.b(bundle.getString("FILE_SIZE"), (long) ((((float) (bundle.getLong("FRAME_CLS_END") - bundle.getLong("FRAME_CLS_START"))) * 1.0f) / ((float) framecount)), bundle.getString("BUSSINESS_ID"), bundle.getString("MODEL_FILE_ID"), (long) ((((float) bundle.getLong("ENGINE_TOTAL_TIME_XNN")) * 1.0f) / ((float) framecount)));
            }
        }
    }

    public static void c() {
        Bundle bundle = c.get("KEY_FRAME_DET_TRACK");
        if (bundle != null) {
            long time1 = bundle.getLong("FRAME_DET_END") - bundle.getLong("FRAME_DET_START");
            long time2 = bundle.getLong("FRAME_TRACK_END") - bundle.getLong("FRAME_TRACK_START");
            int frames = bundle.getInt("FRAME_TOTAL_COUNT_TRACK") + bundle.getInt("FRAME_TOTAL_COUNT_XNN");
            if (frames > 0) {
                UCLogUtil.a(bundle.getString("FILE_SIZE"), (long) ((((float) (time1 + time2)) * 1.0f) / ((float) frames)), bundle.getString("BUSSINESS_ID"), bundle.getString("MODEL_FILE_ID"), (long) ((((float) bundle.getLong("ENGINE_TOTAL_TIME_XNN")) * 1.0f) / ((float) bundle.getInt("FRAME_TOTAL_COUNT_XNN"))), (long) ((((float) bundle.getLong("ENGINE_TOTAL_TIME_TRACK")) * 1.0f) / ((float) bundle.getInt("FRAME_TOTAL_COUNT_TRACK"))));
            }
        }
    }
}
