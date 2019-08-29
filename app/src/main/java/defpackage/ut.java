package defpackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.R;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

/* renamed from: ut reason: default package */
/* compiled from: EnvMapShotPathCallbackImpl */
public final class ut implements us {
    int a;
    private int b;
    private String c;
    private WeakReference<Activity> d;

    public ut(Activity activity, int i, String str, int i2) {
        this.b = i;
        this.c = str;
        this.d = new WeakReference<>(activity);
        this.a = i2;
    }

    private Activity a() {
        if (this.d != null) {
            return (Activity) this.d.get();
        }
        return null;
    }

    public final void a(String str) {
        Bitmap bitmap;
        if (a() != null) {
            if (str.contains("file://")) {
                str = str.replace("file://", "");
            }
            View view = null;
            if (TextUtils.isEmpty(str)) {
                bitmap = null;
            } else {
                bitmap = BitmapFactory.decodeFile(str);
            }
            Activity a2 = a();
            int i = this.b;
            View decorView = a2.getWindow().getDecorView();
            decorView.setDrawingCacheEnabled(true);
            decorView.buildDrawingCache();
            Bitmap drawingCache = decorView.getDrawingCache();
            a2.getWindowManager().getDefaultDisplay().getSize(new Point());
            int width = decorView.getWidth();
            int height = decorView.getHeight();
            Rect rect = new Rect();
            a2.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int a3 = rect.top + agn.a((Context) a2, (float) (i / 2));
            Bitmap createBitmap = Bitmap.createBitmap(drawingCache, 0, a3, width, height - a3);
            decorView.destroyDrawingCache();
            String str2 = this.c;
            if (a() != null) {
                view = a().getLayoutInflater().inflate(R.layout.share_env_img_view, null);
                ((ImageView) view.findViewById(R.id.map_view)).setImageBitmap(bitmap);
                ((ImageView) view.findViewById(R.id.card_view)).setImageBitmap(createBitmap);
                ((TextView) view.findViewById(R.id.txt_view)).setText(str2);
            }
            if (view != null) {
                up a4 = a(view);
                dct dct = new dct(0);
                dct.d = true;
                dct.e = true;
                dct.h = true;
                Bitmap bitmap2 = a4.a;
                String str3 = a4.b;
                AnonymousClass1 r2 = new ur() {
                    public final void a(int i) {
                        String str;
                        if (i != 7) {
                            Entry[] entryArr = new Entry[1];
                            switch (ut.this.a) {
                                case 600000:
                                    str = "air";
                                    break;
                                case 600001:
                                    str = "water";
                                    break;
                                case 600002:
                                    str = "soil";
                                    break;
                                case 600003:
                                    str = "pollution";
                                    break;
                                default:
                                    str = "";
                                    break;
                            }
                            entryArr[0] = new SimpleEntry("from", str);
                            LogManager.actionLogV25("P00377", "B001", entryArr);
                        }
                    }
                };
                dcb dcb = (dcb) a.a.a(dcb.class);
                if (dcb != null) {
                    dcb.a(dct, (dcd) new a(bitmap2, str3, r2));
                }
            }
        }
    }

    @NonNull
    private static up a(@NonNull View view) {
        up upVar = new up();
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        upVar.a = view.getDrawingCache();
        String str = "";
        try {
            str = FileUtil.saveBitmap(upVar.a);
        } catch (IOException unused) {
        }
        upVar.b = str;
        return upVar;
    }
}
