package com.alipay.zoloz.toyger.extservice.record;

import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.service.BioService;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.ZimRecordService;
import java.util.HashMap;
import java.util.Map;

public class ToygerRecordService extends BioService {
    public static final String ALERT_APPEAR = "AlertAppear";
    public static final String ALERT_CHOOSE = "AlertChoose";
    public static final String ALGORITHM = "Algorithm";
    public static final String BIG_PIC_SLICE = "bigPicSlice";
    public static final String CALLBACK_VERIFY_SYSTEM = "callbackVerifySystem";
    public static final String CLICK_BACK = "clickback";
    public static final String CLICK_START_CAPTURE = "clickStartCapture";
    public static final String DARK_PAGE_START = "dark_page_start";
    public static final String DETECT_COND_END = "detectCondEnd";
    public static final String DETECT_COND_START = "detectCondStart";
    public static final String DEVICE_ERR = "deviceErr";
    public static final String DEV_TECH_SEED = "fromH5";
    public static final String ENTER_DETECTION_END = "EnterDetectionEnd";
    public static final String ENTER_DETECTION_START = "EnterDetectionStart";
    public static final String ENTER_GUIDE_PAGE = "enterGuidePage";
    public static final String ENTRY_SDK = "entrySDK";
    public static final String EXIT_GUIDE_PAGE = "exitGuidePage";
    public static final String EXIT_SDK = "exitSDK";
    public static final String EYE_SLICE = "eyeSlice";
    public static final String FACE_SLICE = "faceSlice";
    public static final String IMAGE_CAPTURE_END = "imageCaptureEnd";
    public static final String IMAGE_CAPTURE_START = "imageCaptureStart";
    public static final String LIVEBODY_END = "livebodyEnd";
    public static final String LIVEBODY_START = "livebodyStart";
    public static final String LOAD_ALGORITHM_ERR = "loadAlgorithmErr";
    public static final String NOTICE_EXIT_SDK = "noticeExitSDK";
    public static final String POSE_CHECK_END = "poseCheckEnd";
    public static final String POSE_END = "poseEnd";
    public static final String POSE_START = "poseStart";
    public static final String PROMPT_COPY_POINT = "promptCopyPoint";
    public static final String SENSOR_SLICE = "sensorSlice";
    public static final String UPLOAD_AVARRIABLE = "uploadAvarriable";
    public static final String UPLOAD_END = "uploadEnd";
    public static final String UPLOAD_START = "uploadStart";
    protected HashMap<String, MetaRecord> mRecordMap;
    private ZimRecordService mZimRecordService;

    public void onCreate(BioServiceManager bioServiceManager) {
        super.onCreate(bioServiceManager);
        this.mRecordMap = new HashMap<>();
        this.mRecordMap.put(ENTRY_SDK, new MetaRecord((String) "UC-YWRLSB-161114-01", (String) "event", (String) "20000189", (String) ENTRY_SDK, 1));
        this.mRecordMap.put(ENTER_GUIDE_PAGE, new MetaRecord((String) "UC-YWRLSB-161114-02", (String) "event", (String) "20000189", (String) ENTER_GUIDE_PAGE, 1));
        this.mRecordMap.put(EXIT_GUIDE_PAGE, new MetaRecord((String) "UC-YWRLSB-161114-03", (String) "event", (String) "20000189", (String) EXIT_GUIDE_PAGE, 1));
        this.mRecordMap.put(DEV_TECH_SEED, new MetaRecord((String) "UC-YWRLSB-161114-03", (String) "event", (String) "20000189", (String) DEV_TECH_SEED, 1));
        this.mRecordMap.put(CLICK_START_CAPTURE, new MetaRecord((String) "UC-YWRLSB-161114-04", (String) "event", (String) "20000189", (String) CLICK_START_CAPTURE, 1));
        this.mRecordMap.put(DEVICE_ERR, new MetaRecord((String) "UC-YWRLSB-161114-05", (String) "event", (String) "20000189", (String) DEVICE_ERR, 1));
        this.mRecordMap.put(LOAD_ALGORITHM_ERR, new MetaRecord((String) "UC-YWRLSB-161114-06", (String) "event", (String) "20000189", (String) LOAD_ALGORITHM_ERR, 1));
        this.mRecordMap.put(ENTER_DETECTION_START, new MetaRecord((String) "UC-YWRLSB-161114-07", (String) BehavorID.OPENPAGE, (String) "20000189", (String) "enterDetectionStart", 1));
        this.mRecordMap.put(ENTER_DETECTION_END, new MetaRecord((String) "UC-YWRLSB-161114-08", (String) "event", (String) "20000189", (String) "enterDetectionEnd", 1));
        this.mRecordMap.put(DETECT_COND_START, new MetaRecord((String) "UC-YWRLSB-161114-09", (String) "event", (String) "20000189", (String) DETECT_COND_START, 1));
        this.mRecordMap.put(DETECT_COND_END, new MetaRecord((String) "UC-YWRLSB-161114-10", (String) "event", (String) "20000189", (String) DETECT_COND_END, 1));
        this.mRecordMap.put(POSE_START, new MetaRecord((String) "UC-YWRLSB-161114-11", (String) "event", (String) "20000189", (String) POSE_START, 1));
        this.mRecordMap.put(POSE_END, new MetaRecord((String) "UC-YWRLSB-161114-12", (String) "event", (String) "20000189", (String) POSE_END, 1));
        this.mRecordMap.put(LIVEBODY_START, new MetaRecord((String) "UC-YWRLSB-161114-13", (String) "event", (String) "20000189", (String) LIVEBODY_START, 1));
        this.mRecordMap.put(LIVEBODY_END, new MetaRecord((String) "UC-YWRLSB-161114-14", (String) "event", (String) "20000189", (String) LIVEBODY_END, 1));
        this.mRecordMap.put(UPLOAD_START, new MetaRecord((String) "UC-YWRLSB-161114-15", (String) "event", (String) "20000189", (String) "imageUploadStart", 1));
        this.mRecordMap.put(UPLOAD_END, new MetaRecord((String) "UC-YWRLSB-161114-16", (String) "event", (String) "20000189", (String) "imageUploadEnd", 1));
        this.mRecordMap.put(PROMPT_COPY_POINT, new MetaRecord((String) "UC-YWRLSB-161114-17", (String) "event", (String) "20000189", (String) "actionPrompt", 3, (String) "2"));
        this.mRecordMap.put(FACE_SLICE, new MetaRecord((String) "UC-YWRLSB-161114-18", (String) "event", (String) "20000189", (String) FACE_SLICE, 1, (String) "2"));
        this.mRecordMap.put(EYE_SLICE, new MetaRecord((String) "UC-YWRLSB-161114-19", (String) "event", (String) "20000189", (String) EYE_SLICE, 1));
        this.mRecordMap.put(SENSOR_SLICE, new MetaRecord((String) "UC-YWRLSB-161114-20", (String) "event", (String) "20000189", (String) SENSOR_SLICE, 1, (String) "2"));
        this.mRecordMap.put(ALERT_APPEAR, new MetaRecord((String) "UC-YWRLSB-161114-21", (String) "event", (String) "20000189", (String) "alertAppear", 1));
        this.mRecordMap.put(ALERT_CHOOSE, new MetaRecord((String) "UC-YWRLSB-161114-22", (String) "event", (String) "20000189", (String) "alertChoose", 1));
        this.mRecordMap.put(CALLBACK_VERIFY_SYSTEM, new MetaRecord((String) "UC-YWRLSB-161114-23", (String) "event", (String) "20000189", (String) CALLBACK_VERIFY_SYSTEM, 1));
        this.mRecordMap.put(NOTICE_EXIT_SDK, new MetaRecord((String) "UC-YWRLSB-161114-26", (String) "event", (String) "20000189", (String) NOTICE_EXIT_SDK, 1));
        this.mRecordMap.put(EXIT_SDK, new MetaRecord((String) "UC-YWRLSB-161114-24", (String) "event", (String) "20000189", (String) EXIT_SDK, 1));
        this.mRecordMap.put(UPLOAD_AVARRIABLE, new MetaRecord((String) "UC-YWRLSB-161114-25", (String) "event", (String) "20000189", (String) "uploadAvailable", 1));
        this.mRecordMap.put(BIG_PIC_SLICE, new MetaRecord((String) "UC-YWRLSB-161114-26", (String) "event", (String) "20000189", (String) BIG_PIC_SLICE, 1));
        this.mRecordMap.put(ALGORITHM, new MetaRecord((String) "UC-YWRLSB-161114-27", (String) "event", (String) "20000189", (String) ALGORITHM, 3));
        this.mRecordMap.put(POSE_CHECK_END, new MetaRecord((String) "UC-YWRLSB-161114-28", (String) "event", (String) "20000189", (String) POSE_CHECK_END, 1));
        this.mRecordMap.put(DARK_PAGE_START, new MetaRecord((String) "UC-YWRLSB-161114-29", (String) "event", (String) "20000189", (String) "darkPageStart", 1));
        this.mRecordMap.put(IMAGE_CAPTURE_START, new MetaRecord((String) "UC-YWRLSB-161114-30", (String) "event", (String) "20000189", (String) IMAGE_CAPTURE_START, 1));
        this.mRecordMap.put(IMAGE_CAPTURE_END, new MetaRecord((String) "UC-YWRLSB-161114-31", (String) "event", (String) "20000189", (String) IMAGE_CAPTURE_END, 1));
        this.mRecordMap.put(CLICK_BACK, new MetaRecord((String) "UC-YWRLSB-161114-32", (String) "event", (String) "20000189", (String) CLICK_BACK, 1));
        this.mZimRecordService = (ZimRecordService) bioServiceManager.getBioService(ZimRecordService.class);
    }

    public void write(String str) {
        write(str, null);
    }

    public void addExtProperties(Map<String, String> map) {
        this.mZimRecordService.addExtProperties(map);
    }

    public void write(String str, Map<String, String> map) {
        this.mZimRecordService.write(this.mRecordMap.get(str), map);
    }

    public void retry() {
        this.mZimRecordService.retry();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
