package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;

public class SetExtReq extends AbstractHttpReq {
    private Map<String, Map<String, String>> ext = new HashMap();
    private String fileId;

    public static class ExifFieldKey {
        public static String digitizedTime = "digitizedTime";
        public static String exploreBias = "exploreBias";
        public static String exploreTime = "exploreTime";
        public static String fNumber = "fNumber";
        public static String height = "height";
        public static String isoEquivalent = "isoEquivalent";
        public static String make = "make";
        public static String model = Constants.KEY_MODEL;
        public static String orientation = CaptureParam.ORIENTATION_MODE;
        public static String originalTime = "originalTime";
        public static String software = "software";
        public static String version = "version";
        public static String width = "width";
        public static String xResolution = "xResolution";
        public static String yResolution = "yResolution";
    }

    public static class PrivilegeKey {
        public static String public_key = "public";
        public static String public_value_false = "0";
        public static String public_value_true = "1";
    }

    public SetExtReq(String fileId2) {
        this.fileId = fileId2;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId2) {
        this.fileId = fileId2;
    }

    public Map<String, Map<String, String>> getExt() {
        return this.ext;
    }

    public void setExt(Map<String, Map<String, String>> ext2) {
        this.ext = ext2;
    }
}
