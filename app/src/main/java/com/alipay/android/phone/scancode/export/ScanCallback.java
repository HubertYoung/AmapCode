package com.alipay.android.phone.scancode.export;

import android.content.Intent;

public interface ScanCallback {
    void onScanResult(boolean z, Intent intent);
}
