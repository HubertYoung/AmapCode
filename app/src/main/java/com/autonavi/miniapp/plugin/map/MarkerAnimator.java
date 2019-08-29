package com.autonavi.miniapp.plugin.map;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.OverlayAnimationListener;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPointOverlay;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPointOverlayItem;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.MarkerAnimItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkerAnimator {
    private static final String TAG = "MarkerAnimator";
    private static Map<Integer, Integer> animationTypeMap;
    private MiniAppPointOverlay mOverlay;

    static {
        HashMap hashMap = new HashMap();
        animationTypeMap = hashMap;
        hashMap.put(Integer.valueOf(0), Integer.valueOf(7));
    }

    public MarkerAnimator(MiniAppPointOverlay miniAppPointOverlay) {
        this.mOverlay = miniAppPointOverlay;
    }

    public void processAnimItem(MarkerAnimItem markerAnimItem, OverlayAnimationListener overlayAnimationListener) {
        if (!isValidAnimItem(markerAnimItem)) {
            StringBuilder sb = new StringBuilder("invalid animation command. markerId: ");
            sb.append(markerAnimItem.markerId);
            AMapLog.warning("infoservice.miniapp", TAG, sb.toString());
        } else if (this.mOverlay == null) {
            AMapLog.error("infoservice.miniapp", TAG, "MiniAppPointOverlay is null.");
        } else {
            GLPointOverlay gLPointOverlay = (GLPointOverlay) this.mOverlay.getGLOverlay();
            if (gLPointOverlay == null) {
                AMapLog.error("infoservice.miniapp", TAG, "GLPointOverlay is null.");
                return;
            }
            MiniAppPointOverlayItem findMarker = findMarker(markerAnimItem.markerId);
            if (findMarker != null) {
                Integer num = animationTypeMap.get(Integer.valueOf(markerAnimItem.type));
                if (num != null) {
                    if (!(findOverlayItem(findMarker.mItemId) == null || overlayAnimationListener == null)) {
                        gLPointOverlay.setAnimationListener(overlayAnimationListener);
                    }
                    gLPointOverlay.ModifyAnimationPointItem(findMarker.mItemId, num.intValue());
                    return;
                }
                StringBuilder sb2 = new StringBuilder("unknown animation type ");
                sb2.append(markerAnimItem.type);
                AMapLog.warning("infoservice.miniapp", TAG, sb2.toString());
                return;
            }
            StringBuilder sb3 = new StringBuilder("find no markerId: ");
            sb3.append(markerAnimItem.markerId);
            AMapLog.warning("infoservice.miniapp", TAG, sb3.toString());
        }
    }

    private boolean isValidAnimItem(MarkerAnimItem markerAnimItem) {
        return (markerAnimItem.type == -1 || markerAnimItem.markerId == null) ? false : true;
    }

    public MiniAppPointOverlayItem findMarker(Object obj) {
        for (MiniAppPointOverlayItem miniAppPointOverlayItem : this.mOverlay.getItems()) {
            if (obj != null && obj.equals(miniAppPointOverlayItem.mMiniAppMarker.id)) {
                return miniAppPointOverlayItem;
            }
        }
        return null;
    }

    public ame findOverlayItem(long j) {
        if (this.mOverlay == null) {
            return null;
        }
        GLPointOverlay gLPointOverlay = (GLPointOverlay) this.mOverlay.getGLOverlay();
        if (gLPointOverlay == null) {
            return null;
        }
        List<ame> itemList = gLPointOverlay.getItemList();
        if (itemList == null) {
            return null;
        }
        for (ame next : itemList) {
            if (next != null && j == next.e) {
                return next;
            }
        }
        return null;
    }
}
