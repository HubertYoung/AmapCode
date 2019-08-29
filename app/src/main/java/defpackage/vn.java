package defpackage;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;

@BundleInterface(vr.class)
/* renamed from: vn reason: default package */
/* compiled from: HeadunitViewImpl */
public class vn implements vr {
    private ImageView a;
    private a b;

    /* renamed from: vn$a */
    /* compiled from: HeadunitViewImpl */
    static class a extends Handler {
        private final WeakReference<vr> a;

        public a(vr vrVar) {
            this.a = new WeakReference<>(vrVar);
        }

        public final void handleMessage(Message message) {
            vr vrVar = (vr) this.a.get();
            if (vrVar != null && message.what == 0) {
                ImageView imageView = (ImageView) vrVar.b();
                if (imageView != null) {
                    imageView.setClickable(true);
                    imageView.setAlpha(1.0f);
                }
            }
        }
    }

    public final void a() {
        if (this.a != null) {
            this.a.setClickable(false);
            this.a.setAlpha(0.6f);
            if (this.b != null) {
                this.b.sendEmptyMessageDelayed(0, 5000);
            }
        }
    }

    public final /* synthetic */ Object b() {
        if (this.a == null) {
            this.a = new ImageView(AMapAppGlobal.getApplication());
            this.a.setBackgroundResource(R.drawable.icon_c_bg_single);
            this.a.setImageResource(R.drawable.icon_c21_selector);
            this.a.setScaleType(ScaleType.FIT_CENTER);
            this.b = new a(this);
        }
        return this.a;
    }
}
