package com.alipay.mobile.nebula.appcenter.res;

import android.content.res.Resources;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class H5ResourceManager {
    public static final String TAG = "H5ResourceManager";
    private static final Map<Integer, String> resMap = new HashMap();

    public static synchronized String getRaw(int resId) {
        String str;
        synchronized (H5ResourceManager.class) {
            try {
                if (!resMap.containsKey(Integer.valueOf(resId))) {
                    resMap.put(Integer.valueOf(resId), readRaw(resId));
                }
                str = resMap.get(Integer.valueOf(resId));
            }
        }
        return str;
    }

    public static String readRawFromResource(int resId, Resources resources) {
        String text = null;
        InputStream ips = null;
        long time = System.currentTimeMillis();
        try {
            InputStream ips2 = resources.openRawResource(resId);
            BufferedReader br = new BufferedReader(new InputStreamReader(ips2));
            StringBuilder builder = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                builder.append(line);
                builder.append(10);
            }
            text = builder.toString();
            if (ips2 != null) {
                try {
                    ips2.close();
                } catch (Throwable t) {
                    H5Log.e(TAG, "exception detail", t);
                }
            }
        } catch (Throwable t2) {
            H5Log.e(TAG, "exception detail", t2);
        }
        H5Log.d(TAG, "readRaw elapse " + (System.currentTimeMillis() - time));
        return text;
    }

    public static String readRaw(int resId) {
        return readRawFromResource(resId, H5Utils.getNebulaCoreResources());
    }
}
