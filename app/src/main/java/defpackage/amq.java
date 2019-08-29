package defpackage;

import android.util.Log;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: amq reason: default package */
/* compiled from: MapOverlayListenerAdapter */
public final class amq implements aml {
    private String a = "MapOverlayListenerAdapter";
    private ArrayList<aml> b = new ArrayList<>();
    private ReentrantLock c = new ReentrantLock();

    public final void onPointOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
        this.c.lock();
        try {
            int size = this.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                aml aml = this.b.get(i2);
                if (aml != null) {
                    aml.onPointOverlayClick(i, gLAmapFocusHits);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.c.unlock();
            throw th;
        }
        this.c.unlock();
    }

    public final void onLineOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
        this.c.lock();
        try {
            int size = this.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                aml aml = this.b.get(i2);
                if (aml != null) {
                    aml.onLineOverlayClick(i, gLAmapFocusHits);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.c.unlock();
            throw th;
        }
        this.c.unlock();
    }

    public final void onNoFeatureClick(int i) {
        this.c.lock();
        try {
            int size = this.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                aml aml = this.b.get(i2);
                if (aml != null) {
                    aml.onNoFeatureClick(i);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.c.unlock();
            throw th;
        }
        this.c.unlock();
    }

    public final boolean onBlankClick(int i) {
        boolean z;
        this.c.lock();
        try {
            int size = this.b.size();
            int i2 = 0;
            z = false;
            while (i2 < size) {
                try {
                    aml aml = this.b.get(i2);
                    if (aml != null) {
                        z = z || aml.onBlankClick(i);
                    }
                    i2++;
                } catch (Exception e) {
                    e = e;
                    try {
                        Log.getStackTraceString(e);
                        this.c.unlock();
                        return z;
                    } catch (Throwable th) {
                        this.c.unlock();
                        throw th;
                    }
                }
            }
        } catch (Exception e2) {
            e = e2;
            z = false;
            Log.getStackTraceString(e);
            this.c.unlock();
            return z;
        }
        this.c.unlock();
        return z;
    }

    public final boolean onNoBlankClick(int i) {
        boolean z;
        this.c.lock();
        try {
            int size = this.b.size();
            int i2 = 0;
            z = false;
            while (i2 < size) {
                try {
                    aml aml = this.b.get(i2);
                    if (aml != null) {
                        z = z || aml.onNoBlankClick(i);
                    }
                    i2++;
                } catch (Exception e) {
                    e = e;
                    try {
                        Log.getStackTraceString(e);
                        this.c.unlock();
                        return z;
                    } catch (Throwable th) {
                        this.c.unlock();
                        throw th;
                    }
                }
            }
        } catch (Exception e2) {
            e = e2;
            z = false;
            Log.getStackTraceString(e);
            this.c.unlock();
            return z;
        }
        this.c.unlock();
        return z;
    }

    public final void a(aml aml) {
        this.c.lock();
        try {
            this.b.add(aml);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.c.unlock();
            throw th;
        }
        this.c.unlock();
    }

    public final void b(aml aml) {
        this.c.lock();
        try {
            this.b.remove(aml);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.c.unlock();
            throw th;
        }
        this.c.unlock();
    }
}
