package defpackage;

import android.media.AudioRecord;
import com.amap.bundle.logs.AMapLog;

/* renamed from: djj reason: default package */
/* compiled from: DrivePermissionUtil */
public final class djj {
    public static boolean a() {
        int minBufferSize = AudioRecord.getMinBufferSize(44100, 16, 2);
        if (minBufferSize != -2) {
            char c = 65535;
            if (minBufferSize != -1) {
                AudioRecord audioRecord = new AudioRecord(1, 44100, 16, 2, minBufferSize);
                if (audioRecord.getState() == 0) {
                    AMapLog.e("DrivePermissionUtil", "AudioRecord initialize fail !");
                    return false;
                }
                try {
                    audioRecord.startRecording();
                    byte[] bArr = new byte[20480];
                    char c2 = 0;
                    for (int i = 0; i < 3; i++) {
                        if (audioRecord.read(bArr, 0, 5120) <= 0) {
                            c2 = 1;
                        }
                    }
                    audioRecord.stop();
                    audioRecord.release();
                    c = c2;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    c = 1;
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                }
                if (c == 0) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
