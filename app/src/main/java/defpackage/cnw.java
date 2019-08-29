package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.auidebugger.qrcode.DownloadPage;
import com.autonavi.minimap.auidebugger.qrcode.QRCodePage;
import com.autonavi.minimap.qrcode.QRCodeScanActivityHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

/* renamed from: cnw reason: default package */
/* compiled from: QRCodePresenter */
public final class cnw extends AbstractBasePresenter<QRCodePage> implements dtz {
    private QRCodeScanActivityHandler a;
    private dtt b;
    private Map<DecodeHintType, ?> c;
    private Vector<BarcodeFormat> d;
    private String e;
    private boolean f;
    private boolean g;
    private MediaPlayer h;
    private Handler i = new Handler();
    private final OnCompletionListener j = new OnCompletionListener() {
        public final void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public final void a(Intent intent) {
    }

    public final void b(Intent intent) {
    }

    public cnw(QRCodePage qRCodePage) {
        super(qRCodePage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        QRCodePage qRCodePage = (QRCodePage) this.mPage;
        qRCodePage.a = (dty) qRCodePage.findViewById(R.id.viewfinder_view);
        qRCodePage.c = qRCodePage.findViewById(R.id.progressLayout);
        qRCodePage.d = (ProgressBar) qRCodePage.findViewById(R.id.progressbar);
        qRCodePage.e = (TextView) qRCodePage.findViewById(R.id.msg);
        ((TextView) qRCodePage.findViewById(R.id.debug_title_back)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                QRCodePage.this.finish();
            }
        });
        qRCodePage.b = false;
    }

    public final void onStart() {
        super.onStart();
        ((QRCodePage) this.mPage).requestScreenOrientation(1);
        this.a = null;
        this.d = null;
        this.e = null;
        if (this.b == null) {
            this.b = new dtt(((QRCodePage) this.mPage).getContext());
            ((QRCodePage) this.mPage).a.setCameraManager(this.b);
        }
        QRCodePage qRCodePage = (QRCodePage) this.mPage;
        SurfaceHolder holder = ((SurfaceView) qRCodePage.findViewById(R.id.preview_view)).getHolder();
        if (qRCodePage.b) {
            ((cnw) qRCodePage.mPresenter).a(holder);
        } else {
            holder.addCallback(qRCodePage);
        }
        if (DoNotUseTool.getMapView() instanceof brw) {
            ((brw) DoNotUseTool.getMapView()).d(8);
        }
        this.d = null;
        this.e = null;
        this.g = true;
        if (((AudioManager) ((QRCodePage) this.mPage).getActivity().getSystemService("audio")).getRingerMode() != 2) {
            this.g = false;
        }
        if (this.g && this.h == null) {
            ((QRCodePage) this.mPage).getActivity().setVolumeControlStream(3);
            this.h = new MediaPlayer();
            this.h.setAudioStreamType(3);
            this.h.setOnCompletionListener(this.j);
            AssetFileDescriptor openRawResourceFd = ((QRCodePage) this.mPage).getResources().openRawResourceFd(R.raw.qr_beep);
            try {
                this.h.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
                openRawResourceFd.close();
                this.h.setVolume(0.1f, 0.1f);
                this.h.prepare();
            } catch (IOException unused) {
                this.h = null;
            }
        }
        this.f = true;
    }

    public final void onPause() {
        super.onPause();
        f();
    }

    public final void onDestroy() {
        if (DoNotUseTool.getMapView() instanceof brw) {
            ((brw) DoNotUseTool.getMapView()).d(0);
        }
        QRCodePage qRCodePage = (QRCodePage) this.mPage;
        SurfaceHolder holder = ((SurfaceView) qRCodePage.findViewById(R.id.preview_view)).getHolder();
        if (holder != null) {
            holder.removeCallback(qRCodePage);
        }
        super.onDestroy();
    }

    private void f() {
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
        if (this.b != null) {
            this.b.b();
        }
    }

    public final void a(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        } else if (this.b == null) {
            throw new IllegalStateException("CameraManager is null");
        } else {
            try {
                this.b.a(surfaceHolder);
                if (this.a == null) {
                    QRCodeScanActivityHandler qRCodeScanActivityHandler = new QRCodeScanActivityHandler(this, this.d, this.c, this.e, this.b);
                    this.a = qRCodeScanActivityHandler;
                }
            } catch (IOException unused) {
            } catch (RuntimeException unused2) {
                ToastHelper.showToast("请开启摄像头权限后重试");
            }
        }
    }

    private void g() {
        try {
            Message.obtain(this.a, b().getResources().getIdentifier("restart_preview", "id", "com.autonavi.minimap")).sendToTarget();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void h() {
        if (this.g && this.h != null) {
            this.h.start();
        }
        if (this.f) {
            ((Vibrator) ((QRCodePage) this.mPage).getActivity().getSystemService("vibrator")).vibrate(200);
        }
    }

    public final dty a() {
        return ((QRCodePage) this.mPage).a;
    }

    public final Context b() {
        return ((QRCodePage) this.mPage).getContext();
    }

    public final Handler c() {
        return this.a;
    }

    public final dtt e() {
        return this.b;
    }

    public final void a(Result result) {
        if (result == null) {
            g();
            return;
        }
        h();
        String text = result.getText();
        if (TextUtils.isEmpty(text)) {
            g();
            return;
        }
        String scheme = Uri.parse(text).getScheme();
        if (TextUtils.isEmpty(scheme) || (!scheme.equals("http") && !scheme.equals("https") && !scheme.equals("androidamap") && !scheme.equals("amaplink"))) {
            ToastHelper.showLongToast(text);
            return;
        }
        h();
        if (text.equals("")) {
            ToastHelper.showToast("Scan failed!");
            return;
        }
        if (text.endsWith(".js") || text.endsWith(".ajx")) {
            f();
            cny.a(((QRCodePage) this.mPage).getContext(), text);
            ((QRCodePage) this.mPage).finish();
            ((QRCodePage) this.mPage).startPage(DownloadPage.class, (PageBundle) null);
            AMapLog.d("ajx_down_load", "QRCodePresent#handleDecode:".concat(String.valueOf(text)));
        }
        ToastHelper.showToast(text);
    }

    public final void d() {
        ((QRCodePage) this.mPage).a.drawViewfinder();
    }
}
