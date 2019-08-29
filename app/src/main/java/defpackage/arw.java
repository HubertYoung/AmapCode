package defpackage;

import com.uc.webview.export.internal.SDKFactory;

/* renamed from: arw reason: default package */
/* compiled from: VoiceOperationServiceImpl */
public final class arw implements czn {
    private czn a;

    public final boolean voiceOpenTraffic() {
        if (this.a != null) {
            return this.a.voiceOpenTraffic();
        }
        return false;
    }

    public final boolean voiceCloseTraffic() {
        if (this.a != null) {
            return this.a.voiceCloseTraffic();
        }
        return false;
    }

    public final int voiceZoomInMainMap() {
        return this.a != null ? this.a.voiceZoomInMainMap() : SDKFactory.getCoreType;
    }

    public final int voiceZoomOutMainMap() {
        return this.a != null ? this.a.voiceZoomOutMainMap() : SDKFactory.getCoreType;
    }

    public final void setVoiceOperationServiceDelegate(czn czn) {
        this.a = czn;
    }

    public final int voiceZoomInDiffMainMap(float f) {
        return this.a != null ? this.a.voiceZoomInDiffMainMap(f) : SDKFactory.getCoreType;
    }

    public final void moveMapView(int i, int i2) {
        if (this.a != null) {
            this.a.moveMapView(i, i2);
        }
    }

    public final int voiceZoomOutDiffMainMap(float f) {
        return this.a != null ? this.a.voiceZoomOutDiffMainMap(f) : SDKFactory.getCoreType;
    }

    public final float[] voiceMixMaxZoom() {
        if (this.a != null) {
            return this.a.voiceMixMaxZoom();
        }
        return new float[3];
    }
}
