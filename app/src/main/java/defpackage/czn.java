package defpackage;

/* renamed from: czn reason: default package */
/* compiled from: IVoiceOperationService */
public interface czn extends czi {
    void moveMapView(int i, int i2);

    void setVoiceOperationServiceDelegate(czn czn);

    boolean voiceCloseTraffic();

    float[] voiceMixMaxZoom();

    boolean voiceOpenTraffic();

    int voiceZoomInDiffMainMap(float f);

    int voiceZoomInMainMap();

    int voiceZoomOutDiffMainMap(float f);

    int voiceZoomOutMainMap();
}
