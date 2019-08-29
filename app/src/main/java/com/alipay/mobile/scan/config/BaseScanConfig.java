package com.alipay.mobile.scan.config;

import java.util.Map;

public class BaseScanConfig {
    public static final String CODE_TIPS = "codeTips";
    public static final String EXT_HELP_MESSAGE = "ext_help_message";
    public static final String EXT_HELP_SCHEME = "ext_help_scheme";
    public static final String MY_QR_SCHEME = "myQrCodeScheme";
    public static final String MY_QR_TEXT = "myQrCodeText";
    public static final String SHOW_MORE = "showMore";
    public static final String SHOW_PICTURE = "showPicture";
    public static final String SHOW_TORCH = "showTorch";

    public String getAppSchemeProtocol() {
        return null;
    }

    public String getUiConfigJson() {
        return null;
    }

    public String getOtherConfigs() {
        return null;
    }

    public String getMaPlatformProduct() {
        return null;
    }

    public String getReportScheme() {
        return null;
    }

    public Map<String, String> getExtentionConfigs() {
        return null;
    }

    public String getBirdNestEngineVersion() {
        return null;
    }
}
