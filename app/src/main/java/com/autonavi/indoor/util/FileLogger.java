package com.autonavi.indoor.util;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.indoor.entity.ScanData;
import com.autonavi.indoor.entity.ScanPair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileLogger {
    FileOutputStream a;
    private File b = null;
    public boolean isLogging = false;

    public boolean start(String str) {
        if (!new File(str).isDirectory()) {
            if (L.isLogging) {
                L.d("File Log not enabled because of input dir is not exist".concat(String.valueOf(str)));
            }
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date()));
        sb.append("java.txt");
        this.b = new File(str, sb.toString());
        if (L.isLogging) {
            StringBuilder sb2 = new StringBuilder("Log:");
            sb2.append(this.b.getName());
            L.d(sb2.toString());
        }
        try {
            this.a = new FileOutputStream(this.b, true);
            this.isLogging = true;
            return true;
        } catch (FileNotFoundException e) {
            if (L.isLogging) {
                StringBuilder sb3 = new StringBuilder("Failed to start fileLog");
                sb3.append(e.toString());
                L.d(sb3.toString());
            }
            return false;
        }
    }

    public void stop() {
        if (this.isLogging) {
            try {
                this.a.close();
                this.b = null;
                this.isLogging = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void d(ScanData scanData) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder("ble:");
        sb.append(scanData.time_);
        sb.append(",");
        stringBuffer.append(sb.toString());
        for (ScanPair next : scanData.scans_) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(next.mID);
            sb2.append(MetaRecord.LOG_SEPARATOR);
            sb2.append(next.mRSSI);
            sb2.append("$");
            stringBuffer.append(sb2.toString());
        }
        d(stringBuffer.toString());
    }

    public void d(String str) {
        if (this.isLogging && !TextUtils.isEmpty(str)) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("\n");
                this.a.write(sb.toString().getBytes());
                this.a.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
