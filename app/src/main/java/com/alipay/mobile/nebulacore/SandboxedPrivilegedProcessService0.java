package com.alipay.mobile.nebulacore;

import android.content.Intent;
import android.os.IBinder;
import com.alipay.mobile.nebula.util.H5Log;
import com.uc.sandboxExport.SandboxedProcessService;

public class SandboxedPrivilegedProcessService0 extends SandboxedProcessService {
    public void onCreate() {
        H5Log.d("SandboxedPrivilegedProcessService0", "onCreate");
        super.onCreate();
    }

    public IBinder onBind(Intent intent) {
        H5Log.d("SandboxedPrivilegedProcessService0", "onBind");
        return super.onBind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
