package com.alipay.mobile.common.transportext.biz.iprank;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.iprank.mng.speedtest.BaseSpeedTest;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.diagnose.network.SpeedTestLinkData;
import com.alipay.mobile.common.transportext.biz.diagnose.network.SpeedTestManager;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;

public class SpeeTestImpl implements BaseSpeedTest {
    private static final int FAKE_WIFI = -1000;
    private static final String TAG = "IPR_SpeeTestImpl";

    public int speedTest(String ip, int port) {
        try {
            SpeedTestLinkData linkData = SpeedTestManager.instance().diagnoseByLink(ip, false);
            if (TextUtils.equals(linkData.result, DictionaryKeys.CTRLXY_Y)) {
                return (int) linkData.connTime;
            }
            int errCode = linkData.errCode;
            if (errCode == -1 || errCode == -2) {
                LogCatUtil.error((String) TAG, "speedTest fake wifi,ip: " + ip);
                return -1000;
            }
            LogCatUtil.debug(TAG, "speedTest fail,ip: " + ip);
            return -1;
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "SpeeTestImpl exception", ex);
            return -1;
        }
    }

    public boolean isActivate() {
        return true;
    }

    public int getPriority() {
        return 100;
    }
}
