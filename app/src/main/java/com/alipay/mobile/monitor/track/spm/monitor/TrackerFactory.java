package com.alipay.mobile.monitor.track.spm.monitor;

import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.BaseTracker;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.ClickTracker;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.ExposeTracker;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.SlideTracker;

public class TrackerFactory {
    public static BaseTracker createTracker(String behavorId, Builder builder) {
        char c = 65535;
        switch (behavorId.hashCode()) {
            case -1926005497:
                if (behavorId.equals(BehavorID.EXPOSURE)) {
                    c = 1;
                    break;
                }
                break;
            case -899647277:
                if (behavorId.equals(BehavorID.SLIDE)) {
                    c = 2;
                    break;
                }
                break;
            case 860524583:
                if (behavorId.equals("clicked")) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return new ClickTracker(builder);
            case 1:
                return new ExposeTracker(builder);
            case 2:
                return new SlideTracker(builder);
            default:
                return null;
        }
    }
}
