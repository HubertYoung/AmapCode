package com.huawei.android.pushselfshow.richpush.html.a;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.richpush.html.api.b;
import org.json.JSONException;
import org.json.JSONObject;

public class f implements OnCompletionListener, OnErrorListener, OnPreparedListener, h {
    public String a = null;
    Handler b = new Handler();
    Runnable c = null;
    boolean d = true;
    /* access modifiers changed from: private */
    public a e = a.MEDIA_NONE;
    /* access modifiers changed from: private */
    public String f = null;
    /* access modifiers changed from: private */
    public int g = 1000;
    private MediaPlayer h = null;
    private int i = 0;
    /* access modifiers changed from: private */
    public NativeToJsMessageQueue j;

    public enum a {
        MEDIA_NONE,
        MEDIA_STARTING,
        MEDIA_RUNNING,
        MEDIA_PAUSED,
        MEDIA_STOPPED
    }

    public f(Context context) {
        c.e("PushSelfShowLog", "init AudioPlayer");
    }

    private void a(a aVar) {
        this.e = aVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007a, code lost:
        if (r0 != null) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008b, code lost:
        if (r0 != null) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ac, code lost:
        if (r0 != null) goto L_0x008d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x009f A[Catch:{ all -> 0x007d }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b9 A[SYNTHETIC, Splitter:B:47:0x00b9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean j() {
        /*
            r4 = this;
            com.huawei.android.pushselfshow.richpush.html.a.f$a r0 = r4.e
            int r0 = r0.ordinal()
            com.huawei.android.pushselfshow.richpush.html.a.f$a r1 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_NONE
            int r1 = r1.ordinal()
            r2 = 0
            if (r0 != r1) goto L_0x00c5
            android.media.MediaPlayer r0 = r4.h
            if (r0 != 0) goto L_0x0029
            android.media.MediaPlayer r0 = new android.media.MediaPlayer
            r0.<init>()
            r4.h = r0
            android.media.MediaPlayer r0 = r4.h
            r0.setOnErrorListener(r4)
            android.media.MediaPlayer r0 = r4.h
            r0.setOnPreparedListener(r4)
            android.media.MediaPlayer r0 = r4.h
            r0.setOnCompletionListener(r4)
        L_0x0029:
            r0 = 0
            java.lang.String r1 = r4.f     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            boolean r1 = com.huawei.android.pushselfshow.richpush.html.api.b.a(r1)     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            if (r1 == 0) goto L_0x004a
            android.media.MediaPlayer r1 = r4.h     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            java.lang.String r3 = r4.f     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            r1.setDataSource(r3)     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            android.media.MediaPlayer r1 = r4.h     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            r3 = 3
            r1.setAudioStreamType(r3)     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            com.huawei.android.pushselfshow.richpush.html.a.f$a r1 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_STARTING     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            r4.a(r1)     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            android.media.MediaPlayer r1 = r4.h     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            r1.prepareAsync()     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            goto L_0x007a
        L_0x004a:
            java.io.File r1 = new java.io.File     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            java.lang.String r3 = r4.f     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            r1.<init>(r3)     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            boolean r3 = r1.exists()     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            if (r3 == 0) goto L_0x007a
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            r3.<init>(r1)     // Catch:{ RuntimeException -> 0x00a0, FileNotFoundException -> 0x0091, IOException -> 0x007f }
            android.media.MediaPlayer r0 = r4.h     // Catch:{ RuntimeException -> 0x0078, FileNotFoundException -> 0x0076, IOException -> 0x0074, all -> 0x0071 }
            java.io.FileDescriptor r1 = r3.getFD()     // Catch:{ RuntimeException -> 0x0078, FileNotFoundException -> 0x0076, IOException -> 0x0074, all -> 0x0071 }
            r0.setDataSource(r1)     // Catch:{ RuntimeException -> 0x0078, FileNotFoundException -> 0x0076, IOException -> 0x0074, all -> 0x0071 }
            com.huawei.android.pushselfshow.richpush.html.a.f$a r0 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_STARTING     // Catch:{ RuntimeException -> 0x0078, FileNotFoundException -> 0x0076, IOException -> 0x0074, all -> 0x0071 }
            r4.a(r0)     // Catch:{ RuntimeException -> 0x0078, FileNotFoundException -> 0x0076, IOException -> 0x0074, all -> 0x0071 }
            android.media.MediaPlayer r0 = r4.h     // Catch:{ RuntimeException -> 0x0078, FileNotFoundException -> 0x0076, IOException -> 0x0074, all -> 0x0071 }
            r0.prepare()     // Catch:{ RuntimeException -> 0x0078, FileNotFoundException -> 0x0076, IOException -> 0x0074, all -> 0x0071 }
            r0 = r3
            goto L_0x007a
        L_0x0071:
            r1 = move-exception
            r0 = r3
            goto L_0x00b7
        L_0x0074:
            r0 = r3
            goto L_0x007f
        L_0x0076:
            r0 = r3
            goto L_0x0091
        L_0x0078:
            r0 = r3
            goto L_0x00a0
        L_0x007a:
            if (r0 == 0) goto L_0x00b6
            goto L_0x00ae
        L_0x007d:
            r1 = move-exception
            goto L_0x00b7
        L_0x007f:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r3 = "prepareAsync/prepare error"
            com.huawei.android.pushagent.a.a.c.e(r1, r3)     // Catch:{ all -> 0x007d }
            com.huawei.android.pushselfshow.richpush.html.a.f$a r1 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_NONE     // Catch:{ all -> 0x007d }
            r4.a(r1)     // Catch:{ all -> 0x007d }
            if (r0 == 0) goto L_0x00b6
        L_0x008d:
            r0.close()     // Catch:{ Exception -> 0x00af }
            return r2
        L_0x0091:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r3 = "prepareAsync/prepare error"
            com.huawei.android.pushagent.a.a.c.e(r1, r3)     // Catch:{ all -> 0x007d }
            com.huawei.android.pushselfshow.richpush.html.a.f$a r1 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_NONE     // Catch:{ all -> 0x007d }
            r4.a(r1)     // Catch:{ all -> 0x007d }
            if (r0 == 0) goto L_0x00b6
            goto L_0x00ae
        L_0x00a0:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r3 = "prepareAsync/prepare error"
            com.huawei.android.pushagent.a.a.c.e(r1, r3)     // Catch:{ all -> 0x007d }
            com.huawei.android.pushselfshow.richpush.html.a.f$a r1 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_NONE     // Catch:{ all -> 0x007d }
            r4.a(r1)     // Catch:{ all -> 0x007d }
            if (r0 == 0) goto L_0x00b6
        L_0x00ae:
            goto L_0x008d
        L_0x00af:
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r1 = "close fileInputStream error"
            com.huawei.android.pushagent.a.a.c.e(r0, r1)
        L_0x00b6:
            return r2
        L_0x00b7:
            if (r0 == 0) goto L_0x00c4
            r0.close()     // Catch:{ Exception -> 0x00bd }
            goto L_0x00c4
        L_0x00bd:
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r2 = "close fileInputStream error"
            com.huawei.android.pushagent.a.a.c.e(r0, r2)
        L_0x00c4:
            throw r1
        L_0x00c5:
            com.huawei.android.pushselfshow.richpush.html.a.f$a r1 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_STARTING
            int r1 = r1.ordinal()
            if (r0 != r1) goto L_0x00ce
            return r2
        L_0x00ce:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.html.a.f.j():boolean");
    }

    /* access modifiers changed from: private */
    public float k() {
        try {
            return ((float) this.h.getDuration()) / 1000.0f;
        } catch (Exception unused) {
            c.e("PushSelfShowLog", "getDurationInSeconds error ");
            return -1.0f;
        }
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public void a() {
        if (j() && this.h != null) {
            h();
        }
    }

    public void a(int i2) {
        try {
            if (j()) {
                this.h.seekTo(i2);
                c.a("PushSelfShowLog", "Send a onStatus update for the new seek");
                return;
            }
            this.i = i2;
        } catch (Exception | IllegalStateException unused) {
            c.a("PushSelfShowLog", "seekToPlaying failed");
        }
    }

    public void a(int i2, int i3, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            c.a("PushSelfShowLog", "jsMessageQueue is null while run into Audio Player exec");
            return;
        }
        this.j = nativeToJsMessageQueue;
        if ("preparePlaying".equals(str)) {
            d();
            if (str2 != null) {
                this.a = str2;
                a(jSONObject);
                return;
            }
            c.a("PushSelfShowLog", "Audio exec callback is null ");
        } else if ("startPlaying".equals(str)) {
            a();
        } else if ("seekToPlaying".equals(str)) {
            if (jSONObject != null) {
                try {
                    if (jSONObject.has("milliseconds")) {
                        a(jSONObject.getInt("milliseconds"));
                    }
                } catch (JSONException unused) {
                    c.a("PushSelfShowLog", "seekto error");
                }
            }
        } else if ("pausePlaying".equals(str)) {
            e();
        } else if ("stopPlaying".equals(str)) {
            f();
        } else if ("getPlayingStatus".equals(str)) {
            if (jSONObject != null) {
                try {
                    if (jSONObject.has("frequently")) {
                        int i2 = jSONObject.getInt("frequently");
                        if (i2 > this.g) {
                            this.g = i2;
                        }
                    }
                } catch (JSONException unused2) {
                    c.a("PushSelfShowLog", "seekto error");
                }
            }
            StringBuilder sb = new StringBuilder("this.frequently is ");
            sb.append(this.g);
            c.e("PushSelfShowLog", sb.toString());
            g();
        } else {
            nativeToJsMessageQueue.a(str2, com.huawei.android.pushselfshow.richpush.html.api.d.a.METHOD_NOT_FOUND_EXCEPTION, "error", null);
        }
    }

    public void a(JSONObject jSONObject) {
        NativeToJsMessageQueue nativeToJsMessageQueue;
        String str;
        com.huawei.android.pushselfshow.richpush.html.api.d.a aVar;
        String str2;
        c.e("PushSelfShowLog", " run into Audio player createAudio");
        if (jSONObject != null && jSONObject.has("url")) {
            try {
                String string = jSONObject.getString("url");
                String a2 = b.a(this.j.a(), string);
                if (a2 == null || a2.length() <= 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(string);
                    sb.append("File not exist");
                    c.e("PushSelfShowLog", sb.toString());
                    nativeToJsMessageQueue = this.j;
                    str = this.a;
                    aVar = com.huawei.android.pushselfshow.richpush.html.api.d.a.AUDIO_ONLY_SUPPORT_HTTP;
                    str2 = "error";
                } else {
                    this.f = a2;
                    nativeToJsMessageQueue = this.j;
                    str = this.a;
                    aVar = com.huawei.android.pushselfshow.richpush.html.api.d.a.OK;
                    str2 = "success";
                }
                nativeToJsMessageQueue.a(str, aVar, str2, null);
                if (jSONObject.has("pauseOnActivityPause")) {
                    this.d = jSONObject.getBoolean("pauseOnActivityPause");
                }
            } catch (Exception e2) {
                c.d("PushSelfShowLog", "startPlaying failed ", e2);
            }
            StringBuilder sb2 = new StringBuilder(" this.audioFile = ");
            sb2.append(this.f);
            c.e("PushSelfShowLog", sb2.toString());
        }
        this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION, "error", null);
        StringBuilder sb22 = new StringBuilder(" this.audioFile = ");
        sb22.append(this.f);
        c.e("PushSelfShowLog", sb22.toString());
    }

    public void b() {
        c.e("PushSelfShowLog", "Audio onResume");
    }

    public void c() {
        c.b((String) "PushSelfShowLog", (String) "Audio onPause and pauseOnActivityPause is %s  this.player is %s", Boolean.valueOf(this.d), this.h);
        d();
    }

    public void d() {
        c.e("PushSelfShowLog", "Audio reset/Destory");
        try {
            this.d = true;
            if (this.h != null) {
                if (this.e == a.MEDIA_RUNNING || this.e == a.MEDIA_PAUSED) {
                    this.h.stop();
                }
                this.h.release();
                this.h = null;
            }
            this.f = null;
            a(a.MEDIA_NONE);
            this.g = 1000;
            this.i = 0;
            if (this.c != null) {
                this.b.removeCallbacks(this.c);
            }
            this.c = null;
        } catch (Exception | IllegalStateException unused) {
            c.a("PushSelfShowLog", "reset music error");
        }
    }

    public void e() {
        if (this.e != a.MEDIA_RUNNING || this.h == null) {
            StringBuilder sb = new StringBuilder("AudioPlayer Error: pausePlaying() called during invalid state: ");
            sb.append(this.e.ordinal());
            c.a("PushSelfShowLog", sb.toString());
            return;
        }
        this.h.pause();
        a(a.MEDIA_PAUSED);
    }

    public void f() {
        if (this.e == a.MEDIA_RUNNING || this.e == a.MEDIA_PAUSED) {
            this.h.pause();
            this.h.seekTo(0);
            c.a("PushSelfShowLog", "stopPlaying is calling stopped");
            a(a.MEDIA_STOPPED);
            return;
        }
        StringBuilder sb = new StringBuilder("AudioPlayer Error: stopPlaying() called during invalid state: ");
        sb.append(this.e.ordinal());
        c.a("PushSelfShowLog", sb.toString());
    }

    public void g() {
        StringBuilder sb = new StringBuilder("getPlayingStatusRb is ");
        sb.append(this.c);
        c.e("PushSelfShowLog", sb.toString());
        if (this.c == null) {
            this.c = new g(this);
        } else {
            try {
                this.b.removeCallbacks(this.c);
            } catch (Exception unused) {
                c.e("PushSelfShowLog", "getPlayingStatus error,handler.removeCallbacks");
            }
        }
        this.b.postDelayed(this.c, (long) this.g);
        StringBuilder sb2 = new StringBuilder("handler.postDelayed ");
        sb2.append(this.g);
        c.e("PushSelfShowLog", sb2.toString());
    }

    public void h() {
        try {
            this.h.start();
            a(a.MEDIA_RUNNING);
            this.i = 0;
        } catch (Exception e2) {
            c.d("PushSelfShowLog", "play() error ", e2);
        }
    }

    public long i() {
        if (this.e == a.MEDIA_RUNNING || this.e == a.MEDIA_PAUSED) {
            return (long) (this.h.getCurrentPosition() / 1000);
        }
        return -1;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        c.a("PushSelfShowLog", "on completion is calling stopped");
        a(a.MEDIA_STOPPED);
    }

    public boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
        StringBuilder sb = new StringBuilder("AudioPlayer.onError(");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        sb.append(")");
        c.a("PushSelfShowLog", sb.toString());
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.f);
            this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.AUDIO_PLAY_ERROR, "error", jSONObject);
        } catch (JSONException unused) {
            c.e("PushSelfShowLog", "onError error");
        }
        d();
        return false;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        a(this.i);
        h();
    }
}
