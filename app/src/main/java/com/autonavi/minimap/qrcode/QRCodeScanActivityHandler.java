package com.autonavi.minimap.qrcode;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.autonavi.minimap.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import java.util.Collection;
import java.util.Map;

public final class QRCodeScanActivityHandler extends Handler {
    private final dtz a;
    private final dtx b;
    private State c = State.SUCCESS;
    private final dtt d;

    enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public QRCodeScanActivityHandler(dtz dtz, Collection<BarcodeFormat> collection, Map<DecodeHintType, ?> map, String str, dtt dtt) {
        this.a = dtz;
        dtx dtx = new dtx(dtz, collection, map, str, new dud(dtz.a()));
        this.b = dtx;
        this.b.start();
        this.d = dtt;
        dtt.c();
        b();
    }

    public final void handleMessage(Message message) {
        if (message.what == R.id.auto_focus) {
            if (this.c == State.PREVIEW) {
                this.d.b(this, R.id.auto_focus);
            }
        } else if (message.what == R.id.restart_preview) {
            b();
        } else {
            String str = null;
            if (message.what == R.id.decode_succeeded) {
                this.c = State.SUCCESS;
                Bundle data = message.getData();
                if (data != null) {
                    byte[] byteArray = data.getByteArray("barcode_bitmap");
                    if (byteArray != null) {
                        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, null).copy(Config.ARGB_8888, true);
                    }
                    data.getFloat("barcode_scaled_factor");
                }
                if (this.a != null) {
                    this.a.a((Result) message.obj);
                }
            } else if (message.what == R.id.decode_failed) {
                this.c = State.PREVIEW;
                this.d.a(this.b.a(), R.id.decode);
            } else if (message.what == R.id.return_scan_result) {
                if (this.a != null) {
                    this.a.a((Intent) message.obj);
                }
            } else if (message.what == R.id.launch_product_query && this.a != null) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(524288);
                intent.setData(Uri.parse((String) message.obj));
                ResolveInfo resolveActivity = this.a.b().getPackageManager().resolveActivity(intent, 65536);
                if (!(resolveActivity == null || resolveActivity.activityInfo == null)) {
                    str = resolveActivity.activityInfo.packageName;
                }
                if ("com.android.browser".equals(str) || "com.android.chrome".equals(str)) {
                    intent.setPackage(str);
                    intent.addFlags(268435456);
                    intent.putExtra("com.android.browser.application_id", str);
                }
                try {
                    this.a.b(intent);
                } catch (ActivityNotFoundException unused) {
                }
            }
        }
    }

    public final void a() {
        this.c = State.DONE;
        this.d.d();
        Message.obtain(this.b.a(), R.id.quit).sendToTarget();
        try {
            this.b.join(500);
        } catch (InterruptedException unused) {
        }
        removeMessages(R.id.auto_focus);
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }

    private void b() {
        if (this.c == State.SUCCESS) {
            this.c = State.PREVIEW;
            this.d.a(this.b.a(), R.id.decode);
            this.d.b(this, R.id.auto_focus);
            if (this.a != null) {
                this.a.d();
            }
        }
    }
}
