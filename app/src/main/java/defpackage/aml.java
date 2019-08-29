package defpackage;

import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;

/* renamed from: aml reason: default package */
/* compiled from: MapOverlayListener */
public interface aml {
    boolean onBlankClick(int i);

    void onLineOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits);

    boolean onNoBlankClick(int i);

    void onNoFeatureClick(int i);

    void onPointOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits);
}
