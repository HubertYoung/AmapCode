package defpackage;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import com.autonavi.drivelicense.LicenseRecogJni;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;
import com.iflytek.tts.TtsService.Tts;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/* renamed from: cqo reason: default package */
/* compiled from: DecodePageHandler */
public final class cqo extends Handler {
    private static final String b = "cqo";
    public int a = 0;
    private Timer c;
    /* access modifiers changed from: private */
    public final WeakReference<CarLicenseScanPage> d;
    private int e = 0;
    /* access modifiers changed from: private */
    public boolean f = false;

    cqo(CarLicenseScanPage carLicenseScanPage) {
        this.d = new WeakReference<>(carLicenseScanPage);
        this.c = new Timer("DecodeHandler.Timer");
        this.c.schedule(new TimerTask() {
            public final void run() {
                if (!cqo.this.f) {
                    CarLicenseScanPage carLicenseScanPage = (CarLicenseScanPage) cqo.this.d.get();
                    if (!(carLicenseScanPage == null || !carLicenseScanPage.isAlive() || carLicenseScanPage.b == null)) {
                        Message.obtain(carLicenseScanPage.b, 263).sendToTarget();
                    }
                }
            }
        }, 2000);
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        if (i != 256) {
            switch (i) {
                case 261:
                    this.e = 0;
                    cqm.a().a((Handler) this);
                    break;
                case 262:
                    if (this.c != null) {
                        this.c.cancel();
                        this.c = null;
                    }
                    Looper.myLooper().quit();
                    return;
            }
            return;
        }
        this.f = true;
        byte[] bArr = (byte[]) message.obj;
        int i2 = message.arg1;
        int i3 = message.arg2;
        if (bArr != null && bArr.length != 0) {
            System.currentTimeMillis();
            byte[] a2 = a(bArr, i2, i3);
            System.currentTimeMillis();
            int i4 = -1;
            if (a2 != null && a2.length > 0) {
                System.currentTimeMillis();
                i4 = LicenseRecogJni.license_verify(a2, a2.length);
                System.currentTimeMillis();
            }
            this.e++;
            this.a++;
            CarLicenseScanPage carLicenseScanPage = (CarLicenseScanPage) this.d.get();
            if (carLicenseScanPage != null && carLicenseScanPage.isAlive()) {
                if (i4 == 1) {
                    Message obtain = Message.obtain(carLicenseScanPage.b, Tts.TTS_STATE_INVALID_DATA);
                    obtain.obj = Base64.encodeToString(a2, 0);
                    obtain.sendToTarget();
                    return;
                }
                if (this.e > 10 && this.e <= 15) {
                    Message.obtain(carLicenseScanPage.b, Tts.TTS_STATE_CREATED).sendToTarget();
                } else if (this.e > 15) {
                    Message.obtain(carLicenseScanPage.b, Tts.TTS_STATE_DESTROY).sendToTarget();
                }
                if (this.e <= 15) {
                    cqm.a().a((Handler) this);
                }
            }
        }
    }

    private static byte[] a(byte[] bArr, int i, int i2) {
        int i3;
        if (bArr == null || bArr.length == 0 || i <= 0 || i2 <= 0) {
            return null;
        }
        if (bArr == null) {
            i3 = -1;
        } else {
            try {
                i3 = LicenseRecogJni.license_rotate_yuv_90(bArr, bArr.length, i, i2);
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        byte[] bArr2 = i3 == 0 ? bArr : null;
        if (bArr2 == null) {
            return null;
        }
        YuvImage yuvImage = new YuvImage(bArr2, 17, i2, i, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, i2, i), 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return byteArray;
    }
}
