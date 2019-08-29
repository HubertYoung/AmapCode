package defpackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import com.autonavi.minimap.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.util.Hashtable;
import java.util.Vector;

/* renamed from: dtw reason: default package */
/* compiled from: DecodeLocalImageThread */
public final class dtw extends Thread {
    private Context a;
    private Handler b;
    private MultiFormatReader c = new MultiFormatReader();
    private Intent d;

    public dtw(Context context, Handler handler, Intent intent) {
        this.a = context;
        this.b = handler;
        this.d = intent;
        Hashtable hashtable = new Hashtable(3);
        new Vector();
        Vector vector = new Vector();
        dtx.a(vector);
        hashtable.put(DecodeHintType.POSSIBLE_FORMATS, vector);
        hashtable.put(DecodeHintType.CHARACTER_SET, "UTF8");
        this.c.setHints(hashtable);
    }

    public final void run() {
        Bitmap a2 = a();
        if (a2 != null) {
            int width = a2.getWidth();
            int height = a2.getHeight();
            int[] iArr = new int[(width * height)];
            a2.getPixels(iArr, 0, width, 0, 0, width, height);
            Object obj = null;
            try {
                Result decodeWithState = this.c.decodeWithState(new BinaryBitmap(new HybridBinarizer(new duc(width, height, iArr))));
                this.c.reset();
                a2.recycle();
                obj = decodeWithState;
            } catch (ReaderException unused) {
                this.c.reset();
                a2.recycle();
            } catch (Throwable th) {
                this.c.reset();
                a2.recycle();
                throw th;
            }
            if (obj != null) {
                Message obtain = Message.obtain(this.b, R.id.return_scan_result);
                obtain.obj = obj;
                this.b.sendMessage(obtain);
                return;
            }
            this.b.sendMessage(Message.obtain(this.b, R.id.decode_failed));
            return;
        }
        this.b.sendMessage(Message.obtain(this.b, R.id.decode_failed));
    }

    private Bitmap a() {
        Bitmap bitmap;
        if (this.d.getExtras() != null) {
            bitmap = (Bitmap) this.d.getParcelableExtra("data");
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeFile(this.d.getStringExtra("INTENT_PHOTO_CUT_PATH"));
            }
            if (bitmap != null) {
                return bitmap;
            }
        } else {
            bitmap = null;
        }
        Uri data = this.d.getData();
        if (data != null) {
            try {
                bitmap = BitmapFactory.decodeStream(this.a.getContentResolver().openInputStream(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
