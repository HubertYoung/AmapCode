package com.alipay.mobile.common.nbnet.biz.constants;

import com.alipay.mobile.common.nbnet.api.NBNetConfigHelper;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;

public class DefaultNBNetConfigHelper implements NBNetConfigHelper {
    private static DefaultNBNetConfigHelper a;

    public static final DefaultNBNetConfigHelper a() {
        if (a != null) {
            return a;
        }
        synchronized (DefaultNBNetConfigHelper.class) {
            try {
                if (a != null) {
                    DefaultNBNetConfigHelper defaultNBNetConfigHelper = a;
                    return defaultNBNetConfigHelper;
                }
                a = new DefaultNBNetConfigHelper();
                return a;
            }
        }
    }

    private DefaultNBNetConfigHelper() {
    }

    public void enableMockUploadServerLimitedMode() {
        TransportConfigureManager.getInstance().setValue(NBNetConfigureItem.MOCK_UPLOAD_SERVER_LIMITED_SWITCH, "T");
    }

    public void enableMockDownloadServerLimitedMode() {
        TransportConfigureManager.getInstance().setValue(NBNetConfigureItem.MOCK_DOWNLOAD_SERVER_LIMITED_SWITCH, "T");
    }

    public void disableMockUploadServerLimitedMode() {
        TransportConfigureManager.getInstance().setValue(NBNetConfigureItem.MOCK_UPLOAD_SERVER_LIMITED_SWITCH, "F");
    }

    public void disableMockDownloadServerLimitedMode() {
        TransportConfigureManager.getInstance().setValue(NBNetConfigureItem.MOCK_DOWNLOAD_SERVER_LIMITED_SWITCH, "F");
    }

    public boolean isMockingUploadServerLimitedMode() {
        return TransportConfigureManager.getInstance().equalsString(NBNetConfigureItem.MOCK_UPLOAD_SERVER_LIMITED_SWITCH, "T");
    }

    public boolean isMockingDownloadServerLimitedMode() {
        return TransportConfigureManager.getInstance().equalsString(NBNetConfigureItem.MOCK_DOWNLOAD_SERVER_LIMITED_SWITCH, "T");
    }
}
