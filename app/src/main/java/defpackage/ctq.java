package defpackage;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.bundle.activities.jsaction.MediaPlayHelper$5;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/* renamed from: ctq reason: default package */
/* compiled from: MediaPlayHelper */
public final class ctq {
    private static ctq h;
    /* access modifiers changed from: 0000 */
    public MediaPlayer a;
    OnPreparedListener b;
    OnCompletionListener c;
    OnErrorListener d;
    Uri e;
    Context f;
    /* access modifiers changed from: 0000 */
    public SeekBar g;
    private OnSeekBarChangeListener i;
    /* access modifiers changed from: private */
    public int j;
    private WeakReference<SoundPool> k = null;
    /* access modifiers changed from: private */
    public Handler l = new Handler() {
        public final void handleMessage(Message message) {
            if (message.what == 1 && ctq.this.a != null) {
                if (ctq.this.a.getCurrentPosition() != 0) {
                    ctq.this.j = ctq.this.a.getCurrentPosition();
                }
                ctq.this.l.sendEmptyMessage(1);
                if (ctq.this.g != null) {
                    ctq.this.g.setProgress(ctq.this.j);
                }
            }
            super.handleMessage(message);
        }
    };

    /* renamed from: ctq$a */
    /* compiled from: MediaPlayHelper */
    class a implements bjf {
        final String a;
        bjg b;
        boolean c;
        boolean d = false;
        String e;

        public final void onProgressUpdate(long j, long j2) {
        }

        public final void onStart(long j, Map<String, List<String>> map, int i) {
        }

        a(String str) {
            this.a = str;
            this.e = ctq.a(str);
        }

        public final void onFinish(bpk bpk) {
            File file = new File(this.e);
            if (!this.c && file.exists()) {
                try {
                    SoundPool a2 = ctq.this.a();
                    a2.load(file.getAbsolutePath(), 0);
                    a2.setOnLoadCompleteListener(new OnLoadCompleteListener() {
                        public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                            if (a.this.d) {
                                soundPool.play(i, 2.0f, 2.0f, 0, -1, 1.0f);
                            } else {
                                soundPool.play(i, 2.0f, 2.0f, 0, 0, 1.0f);
                            }
                            if (DebugLog.isDebug()) {
                                AMapLog.i("========", "FileCallback play :".concat(String.valueOf(i)));
                            }
                        }
                    });
                } catch (Exception e2) {
                    kf.a((Throwable) e2);
                }
                this.c = true;
            }
        }

        public final void onError(int i, int i2) {
            StringBuilder sb = new StringBuilder("file download error, url: ");
            sb.append(this.a);
            sb.append(", errorCode: ");
            sb.append(i);
            sb.append(", statusCode: ");
            sb.append(i2);
            AMapLog.e("MediaPlayHelper", sb.toString());
        }
    }

    private ctq(Context context) {
        this.f = context;
        this.b = new OnPreparedListener() {
            public final void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    ctq.this.a.start();
                    if (ctq.this.g != null) {
                        ctq.this.l.sendEmptyMessage(1);
                        ctq.this.g.setMax(ctq.this.a.getDuration());
                    }
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            }
        };
        this.c = new OnCompletionListener() {
            public final void onCompletion(MediaPlayer mediaPlayer) {
                try {
                    ctq.this.a.release();
                    ctq.this.a = null;
                    if (ctq.this.g != null) {
                        ctq.this.g.setVisibility(8);
                    }
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            }
        };
        this.d = new OnErrorListener() {
            public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                if (DebugLog.isDebug()) {
                    ctq.a(i, i2);
                }
                try {
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    ctq.this.a = null;
                    if (ctq.this.g != null) {
                        ctq.this.g.setVisibility(8);
                    }
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                return true;
            }
        };
        this.i = new MediaPlayHelper$5(this);
        this.k = new WeakReference<>(new SoundPool(20, 1, 5));
    }

    public static synchronized ctq a(Context context) {
        ctq ctq;
        synchronized (ctq.class) {
            try {
                if (h == null) {
                    h = new ctq(context);
                }
                ctq = h;
            }
        }
        return ctq;
    }

    public final SoundPool a() {
        SoundPool soundPool = (SoundPool) this.k.get();
        if (soundPool != null) {
            return soundPool;
        }
        this.k.clear();
        this.k = null;
        SoundPool soundPool2 = new SoundPool(20, 1, 5);
        this.k = new WeakReference<>(soundPool2);
        return soundPool2;
    }

    /* access modifiers changed from: 0000 */
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            Application application = AMapAppGlobal.getApplication();
            File externalCacheDir = VERSION.SDK_INT >= 8 ? application.getExternalCacheDir() : null;
            if (externalCacheDir == null) {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                StringBuilder sb = new StringBuilder("/Android/data/");
                sb.append(application.getPackageName());
                sb.append("/cache/");
                externalCacheDir = new File(externalStorageDirectory, sb.toString());
            }
            File file = new File(externalCacheDir, "uio_http_cache");
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }
            return new File(file, new agh().a(str)).getAbsolutePath();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    static /* synthetic */ void a(int i2, int i3) {
        StringBuilder sb = new StringBuilder("OnError - Error code: ");
        sb.append(i2);
        sb.append(" Extra code: ");
        sb.append(i3);
        AMapLog.d("MediaPlayHelper", sb.toString());
        if (i2 == -1010) {
            AMapLog.d("MediaPlayHelper", "MEDIA_ERROR_UNSUPPORTED");
        } else if (i2 == -1007) {
            AMapLog.d("MediaPlayHelper", "MEDIA_ERROR_MALFORMED");
        } else if (i2 == -1004) {
            AMapLog.d("MediaPlayHelper", "MEDIA_ERROR_IO");
        } else if (i2 == -110) {
            AMapLog.d("MediaPlayHelper", "MEDIA_ERROR_TIMED_OUT");
        } else if (i2 == 1) {
            AMapLog.d("MediaPlayHelper", "MEDIA_ERROR_UNKNOWN");
        } else if (i2 == 100) {
            AMapLog.d("MediaPlayHelper", "MEDIA_ERROR_SERVER_DIED");
        } else if (i2 == 200) {
            AMapLog.d("MediaPlayHelper", "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
        }
        if (i3 == 1) {
            AMapLog.d("MediaPlayHelper", "MEDIA_INFO_UNKNOWN");
        } else if (i3 != 3) {
            switch (i3) {
                case 700:
                    AMapLog.d("MediaPlayHelper", "MEDIA_INFO_VIDEO_TRACK_LAGGING");
                    break;
                case 701:
                    AMapLog.d("MediaPlayHelper", "MEDIA_INFO_METADATA_UPDATE");
                    return;
                case 702:
                    AMapLog.d("MediaPlayHelper", "MEDIA_INFO_BUFFERING_END");
                    return;
                default:
                    switch (i3) {
                        case 800:
                            AMapLog.d("MediaPlayHelper", "MEDIA_INFO_BAD_INTERLEAVING");
                            return;
                        case 801:
                            AMapLog.d("MediaPlayHelper", "MEDIA_INFO_NOT_SEEKABLE");
                            return;
                        case 802:
                            AMapLog.d("MediaPlayHelper", "MEDIA_INFO_METADATA_UPDATE");
                            return;
                    }
            }
        } else {
            AMapLog.d("MediaPlayHelper", "MEDIA_INFO_VIDEO_RENDERING_START");
        }
    }
}
