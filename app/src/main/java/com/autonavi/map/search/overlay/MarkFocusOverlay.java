package com.autonavi.map.search.overlay;

import com.autonavi.minimap.base.overlay.PointOverlay;

import defpackage.bty;
import defpackage.bzw;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"SE_NO_SERIALVERSIONID"})
public class MarkFocusOverlay extends PointOverlay< bzw > {
    public MarkFocusOverlay( bty bty) {
        super(bty);
    }
}
