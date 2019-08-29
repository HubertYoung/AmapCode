package com.alipay.mobile.bqcscanservice;

public class BQCCameraParam {
    public static final String VALUE_NO = "no";
    public static final String VALUE_YES = "yes";

    public enum CameraConfigType {
        ;

        private CameraConfigType(String str) {
        }
    }

    public class CameraPropertyParam {
        public static final String FRAME_GAP = "Frame_Gap";
        public static final String PREVIEW_HEIGHT = "Preview_Height";
        public static final String PREVIEW_WIDTH = "Preview_Width";
    }

    public class ConfigParam {
        public static final String KEY_AB_CAMERA_PARAMS = "key_ab_camera_params";
        public static final String KEY_COMPATIBLE_ROTATION = "key_compatible_rotation";
        public static final String KEY_CONTINUOUS_FOCUS_MODEL = "key_continuous_focus_model";
        public static final String KEY_DISABLE_USE_ZOOM = "key_disable_use_zoom";
        public static final String KEY_ENABLE_FOCUS_AREA = "FocusArea";
        public static final String KEY_ENABLE_FRAME_RECOGNIZED_CALLBACK = "key_enable_frame_recog_callback";
        public static final String KEY_NOT_USE_DOUBLE_BUFFER = "key_not_use_double_buffer";
    }

    public enum MaEngineType {
        ALL(0),
        BAR(1),
        QRCODE(2);
        
        public int type;

        private MaEngineType(int type2) {
            this.type = type2;
        }

        public final int getType() {
            return this.type;
        }
    }

    public class ServiceConfig {
        public static final String KEY_NOT_USE_SCAN_CODE_STATE = "not_use_scan_code_state";
    }
}
