package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.autonavi.ae.gmap.AMapSurface;
import com.autonavi.map.core.IOverlayManager;

/* renamed from: bro reason: default package */
/* compiled from: IMapManager */
public interface bro extends defpackage.ami.a, amj, amk, aml, amn {

    /* renamed from: bro$a */
    /* compiled from: IMapManager */
    public interface a {
        void a();
    }

    void addIndoorBuildingListener(defpackage.ami.a aVar);

    void addMapModeChangeListener(a aVar);

    boolean checkMutex();

    void doMutex();

    void fadeCompassWidget(int i);

    int getMapMode();

    bty getMapView();

    bty getMapView(int i);

    brx getMapViewManager();

    @NonNull
    IOverlayManager getOverlayManager();

    void indoorBuildingActivity(int i, ami ami);

    void init(Context context, akq akq, AMapSurface aMapSurface, ImageView imageView);

    boolean isMapSurfaceCreated();

    void notifyMapModeChange(int i);

    void paintCompass(int i);

    void refreshScaleLineView(int i);

    void release();

    void removeIndoorBuidingListener(defpackage.ami.a aVar);

    void removeMapModeChangeListener(a aVar);

    void renderPauseDelay();

    void resetMapView(Bitmap bitmap);

    void restoreMapStateWithouMapMode();

    void saveMapState();

    void setCameraDegree(int i);

    void setEyrieMapGestureListener(amj amj, aky aky);

    void setFrontViewVisibility(int i, boolean z);

    void setIRealtimeBusStateListener(ccz ccz);

    void setIndoorBuildingListener(defpackage.ami.a aVar);

    void setMapSurfaceCreated(boolean z);

    void setMapWidgetListener(amn amn);

    void setScaleColor(int i, int i2, int i3);

    void updateLockMapAngleState(float f);

    void updateLockMapAngleState(bty bty, float f);
}
