package com.autonavi.minimap.strictmode;

import android.content.Context;
import com.autonavi.common.CC;
import dalvik.system.BlockGuard.Policy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class MyCustomAndroidBlockGuardPolicy implements Policy {
    public static boolean a = false;
    private final int b = 1;
    private Context c = CC.getApplication().getApplicationContext();
    private final List<String> d = new ArrayList();

    enum TypeEnum {
        onWriteToDisk,
        onReadFromDisk,
        onNetwork
    }

    public MyCustomAndroidBlockGuardPolicy() {
        a();
    }

    private void a() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.c.getResources().getAssets().open("strictmode_configer")));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    this.d.add(readLine);
                } else {
                    return;
                }
            }
        } catch (Throwable unused) {
        }
    }
}
