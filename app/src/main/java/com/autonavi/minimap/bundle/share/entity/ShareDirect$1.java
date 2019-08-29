package com.autonavi.minimap.bundle.share.entity;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.util.ShareFinishCallback;

public class ShareDirect$1 implements ShareFinishCallback {
    public final void a(int i) {
        if (i == 0) {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.pubok));
            return;
        }
        if (i == -1) {
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.puberr));
        }
    }
}
