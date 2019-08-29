package com.amap.bundle.drive.ajx.inter;

import com.autonavi.minimap.bundle.share.entity.ShareParam;

public interface ShareStatusListener {
    ShareParam getShareDataByType(int i);

    void onFinish(int i, int i2);
}
