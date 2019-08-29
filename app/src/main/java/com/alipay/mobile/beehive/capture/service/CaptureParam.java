package com.alipay.mobile.beehive.capture.service;

import java.io.Serializable;

public class CaptureParam {
    public static final String ACTION_ADD_ONE_MORE = "addOneMore";
    public static final String ACTION_DONE_CAPTURE = "done";
    public static final String ACTION_RE_CAPTURE = "recapture";
    public static final String CAPTURE_CMD_KEY = "action";
    public static final String CAPTURE_CMD_QUIT = "quit";
    public static final String CAPTURE_CMD_TAKE_PHOTO = "takePhoto";
    public static final String CAPTURE_MASK_HEIGHT_PERCENT = "heightPercent";
    public static final String CAPTURE_MASK_WIDTH_HEIGHT_RATIO = "aspectRatio";
    public static final String CAPTURE_MASK_WIDTH_PERCENT = "widthPercent";
    public static final String CAPTURE_MODE = "CAPTURE_MODE";
    public static final int CAPTURE_MODE_BOTH = 3;
    public static final int CAPTURE_MODE_IMAGE = 2;
    public static final int CAPTURE_MODE_VIDEO = 1;
    public static final String CAPTURE_NEED_CROP = "needCrop";
    public static final String CAPTURE_PICTURE_SIZE = "resolution";
    public static final String CAPTURE_SCAN_EFFECT = "scanEffect";
    public static final String CAPTURE_STYLE = "CAPTURE_STYLE";
    public static final int CAPTURE_STYLE_INDUSTRY = 2;
    public static final int CAPTURE_STYLE_LIFE_CIRCLE = 1;
    public static final String CAPTURE_TIP = "captureTip";
    public static final String CMD_QUIT_STARTED = "quitIfStarted";
    public static final int DEFAULT_CAPTURE_MODE = 3;
    public static final int DEFAULT_CAPTURE_QUALITY = 100;
    public static final int DEFAULT_CAPTURE_STYLE = 1;
    public static final boolean DEFAULT_ENABLE_MULTI_TIME_RECORD = false;
    public static final boolean DEFAULT_ENABLE_SET_BEAUTY = true;
    public static final boolean DEFAULT_ENABLE_SET_FILTER = true;
    public static final boolean DEFAULT_ENABLE_SET_WATER_MARK = true;
    public static final boolean DEFAULT_ENABLE_SHOW_LATEST_RECORD_ENTRY = false;
    public static final boolean DEFAULT_ENABLE_SWITCH_CAMERA = true;
    public static final boolean DEFAULT_FINISH_AFTER_CALLBACK = true;
    public static final int DEFAULT_INIT_CAMERA_FACING = 0;
    public static final int DEFAULT_INIT_TYPE = 0;
    public static final int DEFAULT_MAX_DURATION = 6000;
    public static final int DEFAULT_ORIENTATION_MODE = 1;
    public static final int DEFAULT_PRE_LOAD_THUMBNAIL_SIZE = -1;
    public static final int DEFAULT_PRE_SET_BEAUTY_LEVEL = 80;
    public static final boolean DEFAULT_SAVE_VIDEO_TO_ALBUM = true;
    public static final String ENABLE_AI_SCAN_EFFECT = "enableAIScanEffect";
    public static final String ENABLE_CROP_FRAME = "needCaptureFrame";
    public static final String ENABLE_MULTI_TIME_RECORD = "ENABLE_MULTI_TIME_RECORD";
    public static final String ENABLE_SET_BEAUTY = "ENABLE_SET_BEAUTY";
    public static final String ENABLE_SET_FILTER = "ENABLE_SET_FILTER";
    public static final String ENABLE_SET_WATER_MARK = "ENABLE_SET_WATER_MARK";
    public static final String ENABLE_SHOW_LATEST_RECORD_ENTRY = "ENABLE_SHOW_LATEST_RECORD_ENTRY";
    public static final String ENABLE_SWITCH_CAMERA = "ENABLE_SWITCH_CAMERA";
    public static final String FINISH_AFTER_CALLBACK = "FINISH_AFTER_CALLBACK";
    public static final String INIT_CAMERA_FACING = "cameraFacing";
    public static final int INIT_CAMERA_FACING_BACK = 0;
    public static final int INIT_CAMERA_FACING_FRONT = 1;
    public static final String INIT_TYPE = "cameraType";
    public static final int INIT_TYPE_IMAGE = 1;
    public static final int INIT_TYPE_VIDEO = 0;
    public static final String KEY_CAPTURE_QUALITY = "captureQuality";
    public static final String KEY_EXTRA_EXIF = "exifInfo";
    public static final String KEY_MAX_DURATION = "KEY_MAX_DURATION";
    public static final String KEY_RECORD_TYPE = "recordType";
    public static final String KEY_SHOW_SWITCH_CAMERA = "switchCamera";
    public static final int LANDSCAPE_MODE = 2;
    public static final String LATEST_RECORD_ENTRY_ICON_PATH = "LATEST_RECORD_ENTRY_ICON_PATH";
    public static final String MASK_MODE = "maskMode";
    public static final int MASK_MODE_CARD_INSURANCE_HIDE_TOP_BOTTOM_MASK = 3;
    public static final int MASK_MODE_CAR_INSURANCE = 1;
    public static final int MASK_MODE_DEFAULT = 0;
    public static final int MASK_MODE_SCAN = 2;
    public static final String NEED_PREVIEW = "needPreview";
    public static final String ORIENTATION_MODE = "orientation";
    public static final int PORTRAIT_MODE = 1;
    public static final String PREVIEW_ACTIONS = "PREVIEW_ACTIONS";
    public static final String PRE_LOAD_THUMBNAIL_SIZE = "PRE_LOAD_THUMBNAIL_SIZE";
    public static final String PRE_SET_BEAUTY_LEVEL = "PRE_SET_BEAUTY_LEVEL";
    public static final String SAVE_FILE_TO_PRIVATE_DIRECTORY = "saveFileToPrivateDirectory";
    public static final String SAVE_VIDEO_TO_ALBUM = "SAVE_VIDEO_TO_PHOTO_ALBUM";
    public static final int SCAN_EFFECT_DEFAULT = 0;
    public static final int SCAN_EFFECT_EDGE = 1;
    public static final String SUPPORT_CONTINUE_SHOOTING = "supportContinueShooting";
    public static final String TOAST_WHEN_RECORD_DONE_ONE_TIME = "TOAST_WHEN_RECORD_DONE_ONE_TIME";
    public static String UPDATE_UI_CENTER_TIP = "centerTip";
    public static String UPDATE_UI_ENABLE_RECORD_BTN = "enableRecordBtn";
    public static String UPDATE_UI_FOCUS_IMAGE = "focusImage";
    public static String UPDATE_UI_GUIDE_IMAGE = "guideImage";
    public static String UPDATE_UI_PREVIEW_IMAGE = "previewImage";
    public static String UPDATE_UI_SAMPLE_IMAGE = "sampleImage";
    public static String UPDATE_UI_SCENE_TEXT = "sceneText";
    public static String UPDATE_UI_SHOW_FLASH_BTN = "showFlashBtn";
    public static String UPDATE_UI_SHOW_SCAN_ANIM = "showScanAnimation";
    public static String UPDATE_UI_TIP_DURATION = "tipDuration";
    public static String UPDATE_UI_TIP_TEXT = "tipText";

    public static class PreviewAction implements Serializable {
        public String actionText;
        public String actionType;
        public boolean specialColor;
    }
}