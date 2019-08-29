package defpackage;

import android.media.AudioRecord;
import java.lang.ref.WeakReference;

/* renamed from: bgq reason: default package */
/* compiled from: MicChecker */
public final class bgq {
    public final a a = new a(this, 0);

    /* renamed from: bgq$a */
    /* compiled from: MicChecker */
    public class a extends defpackage.ahl.a {
        public WeakReference<b> a;

        private a() {
        }

        /* synthetic */ a(bgq bgq, byte b2) {
            this();
        }

        public final void onFinished(Object obj) {
            super.onFinished(obj);
            boolean booleanValue = obj != null ? ((Boolean) obj).booleanValue() : false;
            if (!(this.a == null || this.a.get() == null)) {
                ((b) this.a.get()).a(booleanValue);
            }
            this.a = null;
        }

        public final void onCancelled() {
            super.onCancelled();
            if (!(this.a == null || this.a.get() == null)) {
                ((b) this.a.get()).a(false);
            }
            this.a = null;
        }

        public final void onError(Throwable th) {
            super.onError(th);
            if (!(this.a == null || this.a.get() == null)) {
                ((b) this.a.get()).a(false);
            }
            this.a = null;
        }

        public final Object doBackground() {
            return Boolean.valueOf(bgq.a());
        }
    }

    /* renamed from: bgq$b */
    /* compiled from: MicChecker */
    public interface b {
        void a(boolean z);
    }

    static boolean a() {
        Boolean bool = Boolean.TRUE;
        int minBufferSize = AudioRecord.getMinBufferSize(16000, 16, 2) * 4;
        if (minBufferSize < 4096) {
            minBufferSize = 4096;
        }
        AudioRecord audioRecord = null;
        try {
            AudioRecord audioRecord2 = new AudioRecord(5, 16000, 16, 2, minBufferSize);
            audioRecord = audioRecord2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (audioRecord == null) {
            try {
                AudioRecord audioRecord3 = new AudioRecord(1, 16000, 16, 2, minBufferSize);
                audioRecord = audioRecord3;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (audioRecord != null) {
            try {
                if (audioRecord.getRecordingState() != 1) {
                    bool = Boolean.FALSE;
                }
                audioRecord.startRecording();
                if (audioRecord.getRecordingState() != 3) {
                    audioRecord.stop();
                    bool = Boolean.FALSE;
                }
            } catch (Throwable th) {
                if (audioRecord != null) {
                    audioRecord.stop();
                    audioRecord.release();
                }
                throw th;
            }
        }
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
        }
        return bool.booleanValue();
    }
}
