package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.navi.navidata.TmcColor;
import com.autonavi.jni.ae.route.RouteService;
import com.autonavi.jni.ae.route.model.LightBarItem;
import com.autonavi.jni.ae.route.model.TmcRoutePath;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: dji reason: default package */
/* compiled from: AsyncTrafficBarLoader */
public final class dji {
    final int a;
    final int b;

    /* renamed from: dji$a */
    /* compiled from: AsyncTrafficBarLoader */
    public class a extends AsyncTask<NavigationPath, Void, Bitmap> {
        private final WeakReference<ImageView> b;
        private final NavigationPath c;
        private ICarRouteResult d;
        private final ArrayList<Bitmap> e = new ArrayList<>();
        private int f = 0;
        private final ReentrantLock g = new ReentrantLock();

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object doInBackground(Object[] objArr) {
            return a();
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            super.onPostExecute(bitmap);
            try {
                a aVar = null;
                if (isCancelled()) {
                    bitmap = null;
                }
                if (!(bitmap == null || this.b == null)) {
                    ImageView imageView = (ImageView) this.b.get();
                    if (imageView != null) {
                        Drawable drawable = imageView.getDrawable();
                        if (drawable instanceof b) {
                            aVar = (a) ((b) drawable).a.get();
                        }
                    }
                    if (this == aVar) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }

        public a(ImageView imageView, ICarRouteResult iCarRouteResult) {
            this.b = new WeakReference<>(imageView);
            this.d = iCarRouteResult;
            this.c = iCarRouteResult.getFocusNavigationPath();
        }

        private Bitmap a() {
            Bitmap bitmap;
            TmcColor tmcColor;
            try {
                this.g.lock();
                if (this.d != null) {
                    int i = this.c.mPathlength;
                    LightBarItem[] decodeRouteTmcBar = RouteService.decodeRouteTmcBar(this.d.getBackUpTbtData(), new TmcRoutePath());
                    if (decodeRouteTmcBar != null && i > 0) {
                        ArrayList arrayList = new ArrayList();
                        if (decodeRouteTmcBar == null) {
                            bitmap = null;
                        } else {
                            int length = decodeRouteTmcBar.length - 1;
                            int i2 = 0;
                            for (int i3 = 0; i3 <= length; i3++) {
                                int i4 = decodeRouteTmcBar[i3].length;
                                i2 += i4;
                                int i5 = (i4 * dji.this.a) / i;
                                if (i5 <= 0) {
                                    i5 = 1;
                                }
                                switch (decodeRouteTmcBar[i3].status) {
                                    case 0:
                                        tmcColor = TmcColor.UNKNOWN;
                                        break;
                                    case 1:
                                        tmcColor = TmcColor.UNBLOCK;
                                        break;
                                    case 2:
                                        tmcColor = TmcColor.SLOW;
                                        break;
                                    case 3:
                                        tmcColor = TmcColor.BLOCK;
                                        break;
                                    case 4:
                                        tmcColor = TmcColor.GRIDLOCKED;
                                        break;
                                    default:
                                        tmcColor = TmcColor.UNKNOWN;
                                        break;
                                }
                                arrayList.add(a(i5, dji.this.b, tmcColor));
                            }
                            if (i2 < i) {
                                int i6 = ((i - i2) * dji.this.a) / i;
                                if (i6 <= 0) {
                                    i6 = 1;
                                }
                                arrayList.add(a(i6, dji.this.b, TmcColor.UNBLOCK));
                            }
                            if (this.f < dji.this.a) {
                                int i7 = dji.this.a - this.f;
                                if (i7 <= 0) {
                                    i7 = 1;
                                }
                                arrayList.add(a(i7, dji.this.b, TmcColor.UNBLOCK));
                            }
                            bitmap = a(arrayList);
                        }
                        if (bitmap != null) {
                            this.g.unlock();
                            return bitmap;
                        }
                    }
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            } catch (Throwable th) {
                this.g.unlock();
                throw th;
            }
            this.g.unlock();
            return null;
        }

        private Bitmap a(ArrayList<Bitmap> arrayList) {
            Bitmap createBitmap = Bitmap.createBitmap(dji.this.a, arrayList.get(0).getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            int size = arrayList.size() - 1;
            int i = 0;
            for (int i2 = 0; i2 <= size; i2++) {
                Bitmap bitmap = arrayList.get(i2);
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, (float) i, 0.0f, null);
                    i += bitmap.getWidth();
                    bitmap.recycle();
                }
            }
            return i > dji.this.a ? Bitmap.createScaledBitmap(createBitmap, (int) (((double) createBitmap.getWidth()) * 0.9d), createBitmap.getHeight(), true) : createBitmap;
        }

        private Bitmap a(int i, int i2, TmcColor tmcColor) {
            this.f += i;
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
            new Canvas(createBitmap).drawARGB(255, tmcColor.R(), tmcColor.G(), tmcColor.B());
            return createBitmap;
        }
    }

    /* renamed from: dji$b */
    /* compiled from: AsyncTrafficBarLoader */
    public static class b extends BitmapDrawable {
        final WeakReference<a> a;

        public b(a aVar) {
            super(null);
            this.a = new WeakReference<>(aVar);
        }
    }

    public dji(Context context) {
        this.a = agn.a(context, 280.0f);
        this.b = agn.a(context, 6.0f);
    }
}
