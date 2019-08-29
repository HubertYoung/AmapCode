package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.map.core.MapManager;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* renamed from: oa reason: default package */
/* compiled from: AbstractScreenShoter */
public abstract class oa implements ob {
    protected MapManager a;
    protected bty b;
    protected View c;
    public final a d = new a(this);

    /* renamed from: oa$a */
    /* compiled from: AbstractScreenShoter */
    public static class a extends Handler {
        public WeakReference<oa> a;

        public a(oa oaVar) {
            this.a = new WeakReference<>(oaVar);
        }

        public final void handleMessage(Message message) {
            final Bitmap bitmap;
            if (message.what == 1001) {
                final oa oaVar = (oa) this.a.get();
                if (oaVar != null) {
                    try {
                        final Bitmap bitmap2 = (Bitmap) message.obj;
                        View view = oaVar.c;
                        final Bitmap createBitmap = Bitmap.createBitmap(message.arg1, message.arg2, Config.ARGB_8888);
                        view.draw(new Canvas(createBitmap));
                        if (bitmap2 == null) {
                            bitmap = null;
                        } else {
                            int width = bitmap2.getWidth();
                            int height = bitmap2.getHeight();
                            int width2 = createBitmap.getWidth();
                            int height2 = createBitmap.getHeight();
                            Bitmap createBitmap2 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                            Canvas canvas = new Canvas(createBitmap2);
                            canvas.drawBitmap(bitmap2, 0.0f, 0.0f, null);
                            canvas.drawBitmap(createBitmap, (float) ((width - width2) / 2), (float) ((height - height2) / 2), null);
                            canvas.save();
                            canvas.restore();
                            bitmap = createBitmap2;
                        }
                        AnonymousClass1 r1 = new Runnable() {
                            public final void run() {
                                final String str;
                                try {
                                    str = FileUtil.saveBitmap(bitmap, 10);
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                    str = null;
                                }
                                oaVar.d.post(new Runnable() {
                                    public final void run() {
                                        a.this.a(bitmap, str);
                                        if (bitmap2 != null && !bitmap2.isRecycled()) {
                                            bitmap2.recycle();
                                        }
                                        if (createBitmap != null && !createBitmap.isRecycled()) {
                                            createBitmap.recycle();
                                        }
                                    }
                                });
                            }
                        };
                        new Thread(r1).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        a(null, "");
                    } catch (OutOfMemoryError e2) {
                        e2.printStackTrace();
                        a(null, "");
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public void a(Bitmap bitmap, String str) {
            ob obVar = (ob) this.a.get();
            if (obVar != null) {
                obVar.a(str);
            }
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    public void a(String str) {
    }

    public oa(View view, MapManager mapManager) {
        if (view == null || mapManager == null) {
            throw new IllegalArgumentException("we need all this params");
        }
        this.c = view;
        this.a = mapManager;
        this.b = mapManager.getMapView();
    }

    public void a(final int i, final int i2) {
        this.b.a(this.b.al(), this.b.am(), (defpackage.bty.a) new defpackage.bty.a() {
            public final void onCallBack(Bitmap bitmap) {
                oa.this.d.sendMessage(oa.this.d.obtainMessage(1001, i, i2, bitmap));
            }
        });
    }
}
