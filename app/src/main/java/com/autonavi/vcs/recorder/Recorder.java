package com.autonavi.vcs.recorder;

import android.media.AudioRecord;
import android.os.Looper;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.vcs.Constants.VUIStatus;
import com.autonavi.vcs.NativeVcsManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public final class Recorder {
    public int a;
    public AtomicBoolean b = new AtomicBoolean(false);
    public AtomicBoolean c = new AtomicBoolean(false);
    public b d;
    eqi e;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecordStatusConstraint {
    }

    class a extends FutureTask<Integer> {
        /* access modifiers changed from: private */
        public c b;

        public a(Callable<Integer> callable) {
            super(callable);
            this.b = (c) callable;
        }

        /* access modifiers changed from: protected */
        public final void done() {
            try {
                int intValue = ((Integer) get()).intValue();
                StringBuilder sb = new StringBuilder(">>> LooperObserver.done/mStatus: ");
                sb.append(Recorder.this.a);
                bfh.a("Recorder-Recorder", sb.toString());
                aho.a(new Runnable(intValue) {
                    public final void run() {
                        Recorder.this.d = null;
                        aho.a(new Runnable(3, r2 != 0) {
                            public final void run() {
                                if (Recorder.this.e != null) {
                                    if (1 == r2) {
                                        Recorder.this.a = 2;
                                        Recorder.this.e.a(r3);
                                    } else if (2 == r2) {
                                        Recorder.this.a = 4;
                                        Recorder.this.e.b(r3);
                                    } else {
                                        if (3 == r2) {
                                            Recorder.this.a = 6;
                                            Recorder.this.e.c(r3);
                                        }
                                    }
                                }
                            }
                        });
                    }
                });
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder("CancellationException/msg: ");
                sb2.append(e.toString());
                bfh.a("Recorder-Recorder", sb2.toString());
                e.printStackTrace();
                StringBuilder sb3 = new StringBuilder(">>> LooperObserver.done/mStatus: ");
                sb3.append(Recorder.this.a);
                bfh.a("Recorder-Recorder", sb3.toString());
                aho.a(new Runnable(2) {
                    public final void run() {
                        Recorder.this.d = null;
                        aho.a(new Runnable(3, r2 != 0) {
                            public final void run() {
                                if (Recorder.this.e != null) {
                                    if (1 == r2) {
                                        Recorder.this.a = 2;
                                        Recorder.this.e.a(r3);
                                    } else if (2 == r2) {
                                        Recorder.this.a = 4;
                                        Recorder.this.e.b(r3);
                                    } else {
                                        if (3 == r2) {
                                            Recorder.this.a = 6;
                                            Recorder.this.e.c(r3);
                                        }
                                    }
                                }
                            }
                        });
                    }
                });
            } catch (Throwable th) {
                StringBuilder sb4 = new StringBuilder(">>> LooperObserver.done/mStatus: ");
                sb4.append(Recorder.this.a);
                bfh.a("Recorder-Recorder", sb4.toString());
                aho.a(new Runnable(0) {
                    public final void run() {
                        Recorder.this.d = null;
                        aho.a(new Runnable(3, r2 != 0) {
                            public final void run() {
                                if (Recorder.this.e != null) {
                                    if (1 == r2) {
                                        Recorder.this.a = 2;
                                        Recorder.this.e.a(r3);
                                    } else if (2 == r2) {
                                        Recorder.this.a = 4;
                                        Recorder.this.e.b(r3);
                                    } else {
                                        if (3 == r2) {
                                            Recorder.this.a = 6;
                                            Recorder.this.e.c(r3);
                                        }
                                    }
                                }
                            }
                        });
                    }
                });
                throw th;
            }
        }
    }

    public class b {
        public ExecutorService a = Executors.newCachedThreadPool();
        public a b;

        public b() {
            this.b = new a(new c(Recorder.this, 0));
        }

        public final void a() {
            this.a.shutdownNow();
        }
    }

    class c implements Callable<Integer> {
        /* access modifiers changed from: private */
        public volatile int b;
        private AtomicBoolean c;
        private AudioRecord d;

        /* synthetic */ c(Recorder recorder, byte b2) {
            this();
        }

        private c() {
            this.c = new AtomicBoolean(false);
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public Integer call() {
            boolean z = false;
            while (!Recorder.this.c.get()) {
                boolean z2 = Recorder.this.b.get();
                if (z2 && !z && NativeVcsManager.getInstance().isRecorderApplied()) {
                    if (b() && this.d != null) {
                        this.d.startRecording();
                        if (this.d.getRecordingState() != 3) {
                            this.d.stop();
                            z = false;
                        } else {
                            z = true;
                        }
                        NativeVcsManager.getInstance().setRecorderApplied(z);
                        StringBuilder sb = new StringBuilder("isRecorderApplied:");
                        sb.append(NativeVcsManager.getInstance().isRecorderApplied());
                        bfh.a("Recorder-Recorder", sb.toString());
                    }
                    aho.a(new Runnable(1, z) {
                        public final void run() {
                            if (Recorder.this.e != null) {
                                if (1 == r2) {
                                    Recorder.this.a = 2;
                                    Recorder.this.e.a(r3);
                                } else if (2 == r2) {
                                    Recorder.this.a = 4;
                                    Recorder.this.e.b(r3);
                                } else {
                                    if (3 == r2) {
                                        Recorder.this.a = 6;
                                        Recorder.this.e.c(r3);
                                    }
                                }
                            }
                        }
                    });
                } else if (!z2 && z) {
                    if (this.d != null) {
                        this.d.stop();
                        z = false;
                    }
                    aho.a(new Runnable(2, true) {
                        public final void run() {
                            if (Recorder.this.e != null) {
                                if (1 == r2) {
                                    Recorder.this.a = 2;
                                    Recorder.this.e.a(r3);
                                } else if (2 == r2) {
                                    Recorder.this.a = 4;
                                    Recorder.this.e.b(r3);
                                } else {
                                    if (3 == r2) {
                                        Recorder.this.a = 6;
                                        Recorder.this.e.c(r3);
                                    }
                                }
                            }
                        }
                    });
                }
                if (z) {
                    if (this.d != null) {
                        byte[] bArr = new byte[1024];
                        int read = this.d.read(bArr, 0, 1024);
                        if (NativeVcsManager.getInstance().isInit()) {
                            NativeVcsManager.getInstance().pushAudioData(bArr, read);
                        }
                        a(bArr, read);
                    }
                } else if (!Thread.interrupted()) {
                    try {
                        Thread.sleep(StatisticConfig.MIN_UPLOAD_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            bfh.a("Recorder-Recorder", "AR thread exit loop");
            if (this.d != null) {
                this.d.release();
            }
            AMapLog.e("Recorder-Recorder", "RecordLooper AR thread finish");
            return Integer.valueOf(1);
        }

        private boolean b() {
            if (this.d != null) {
                return true;
            }
            int minBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2);
            AMapLog.e("Recorder-Recorder", "creatAudioRecorder min buffer size ".concat(String.valueOf(minBufferSize)));
            int i = minBufferSize * 4;
            if (i < 4096) {
                i = 4096;
            }
            try {
                AudioRecord audioRecord = new AudioRecord(5, 16000, 16, 2, i);
                this.d = audioRecord;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (this.d == null) {
                    AudioRecord audioRecord2 = new AudioRecord(1, 16000, 16, 2, i);
                    this.d = audioRecord2;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.d != null && this.d.getState() == 1) {
                return true;
            }
            if (this.d != null) {
                StringBuilder sb = new StringBuilder(" doStart recorder create failed state = ");
                sb.append(this.d.getState());
                bfh.a("Recorder-Recorder", sb.toString());
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject.put("event_name", VUIStatus.VUIStatus_Error.toString());
                    jSONObject2.put("errMessage", "AudioRecorder create failed");
                    jSONObject.putOpt("param", jSONObject2);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                NativeVcsManager.getInstance().onVCSStatusChange(VUIStatus.VUIStatus_Error.ordinal(), jSONObject.toString());
                if (!this.c.get()) {
                    this.c.set(true);
                    bfq bfq = d.a;
                    StringBuilder sb2 = new StringBuilder("failed state=");
                    sb2.append(this.d.getState());
                    bfp.a(bfq, 2, sb2.toString());
                }
            }
            if (this.d != null) {
                this.d.release();
                this.d = null;
            }
            return false;
        }

        private void a(byte[] bArr, int i) {
            int i2 = 0;
            long j = 0;
            for (int i3 = 0; i3 < 1024; i3++) {
                j += (long) (bArr[i3] * bArr[i3]);
            }
            double log10 = Math.log10(((double) j) / ((double) i)) * 10.0d;
            if (log10 >= 34.0d) {
                i2 = 3;
            } else if (log10 >= 27.0d) {
                i2 = 2;
            } else if (log10 >= 20.0d) {
                i2 = 1;
            }
            this.b = i2;
        }
    }

    public final int a() {
        try {
            return this.d.b.b.b;
        } catch (Exception unused) {
            return -2;
        }
    }

    public static boolean b() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
