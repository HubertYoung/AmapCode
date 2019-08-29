package defpackage;

import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import com.autonavi.minimap.map.ITrafficOverlayItem;
import com.autonavi.minimap.map.ITrafficTopic;
import com.autonavi.minimap.map.TrafficOverlayItem;
import java.util.ArrayList;

/* renamed from: csg reason: default package */
/* compiled from: TrafficOverlayItemImpl */
public class csg implements ITrafficOverlayItem {
    public TrafficTopic a;
    public String b = "";
    private long c = 0;
    private boolean d = false;
    private boolean e;
    private POIOverlayItem f;

    public void onPrepareAddItem(PointOverlay pointOverlay) {
        if (this.a != null) {
            int layerTag = this.a.getLayerTag();
            if (layerTag != 11040) {
                if (layerTag != 11100) {
                    switch (layerTag) {
                        case TrafficTopic.ACCIDENT_VEHICLE /*11010*/:
                            if (!this.e) {
                                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_accident_fault, 4);
                                this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_accident_fault, 4);
                                break;
                            } else {
                                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_accident_fault_local, 5);
                                break;
                            }
                        case TrafficTopic.ACCIDENT_CRASH /*11011*/:
                            if (!this.e) {
                                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_accident_accident, 4);
                                this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_accident_accident, 4);
                                break;
                            } else {
                                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_accident_accident_local, 5);
                                break;
                            }
                        case TrafficTopic.ACCIDENT_BARRIER /*11012*/:
                            this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_accident_obstacle, 4);
                            this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_accident_obstacle, 4);
                            break;
                        default:
                            switch (layerTag) {
                                case TrafficTopic.JAM_SLOW /*11020*/:
                                    this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_road_slow, 4);
                                    this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_road_slow, 4);
                                    break;
                                case TrafficTopic.JAM_CROWDED /*11021*/:
                                    if (!this.e) {
                                        this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_road_jam, 4);
                                        this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_road_jam, 4);
                                        break;
                                    } else {
                                        this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_road_jam_local, 5);
                                        break;
                                    }
                                case TrafficTopic.JAM_STILL /*11022*/:
                                    this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_road_block, 4);
                                    this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_road_block_hl, 4);
                                    break;
                                case TrafficTopic.JAM_UNBLOCKED /*11023*/:
                                    this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_road_unimpeded, 4);
                                    this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_road_unimpeded, 4);
                                    break;
                                default:
                                    switch (layerTag) {
                                        case TrafficTopic.POLICE_CONTROL /*11030*/:
                                            this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_police_control, 4);
                                            this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_police_control_hl, 4);
                                            break;
                                        case TrafficTopic.CONTROL_CONTROL /*11031*/:
                                            this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_control_control, 4);
                                            this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_control_control, 4);
                                            break;
                                        case TrafficTopic.POLICE_DRUNK /*11032*/:
                                            this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_police_drunk, 4);
                                            this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_police_drunk_hl, 4);
                                            break;
                                        case TrafficTopic.POLICE_LAW_ENFORCE /*11033*/:
                                            if (!this.e) {
                                                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_police_law_enforce, 4);
                                                this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_police_law_enforce, 4);
                                                break;
                                            } else {
                                                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_police_law_enforce_local, 5);
                                                break;
                                            }
                                        default:
                                            switch (layerTag) {
                                                case TrafficTopic.CONTROL_CLOSE /*11050*/:
                                                case TrafficTopic.CONTROL_CLOSE_ROAD /*11051*/:
                                                case TrafficTopic.CONTROL_CLOSE_EXIT /*11052*/:
                                                case TrafficTopic.CONTROL_CLOSE_ENTRY /*11053*/:
                                                case TrafficTopic.CONTROL_CLOSE_ORDINARY_ACCIDENT /*11054*/:
                                                case TrafficTopic.CONTROL_CLOSE_MAJOR_ACCIDENT /*11055*/:
                                                case TrafficTopic.CONTROL_CLOSE_CONSTRUCTION /*11056*/:
                                                case TrafficTopic.CONTROL_CLOSE_FOG /*11057*/:
                                                case TrafficTopic.CONTROL_CLOSE_RAIN /*11058*/:
                                                case TrafficTopic.CONTROL_CLOSE_SNOW /*11059*/:
                                                case TrafficTopic.CONTROL_CLOSE_HAIL /*11061*/:
                                                case TrafficTopic.CONTROL_CLOSE_PONDING /*11062*/:
                                                case TrafficTopic.CONTROL_CLOSE_SNOWS /*11063*/:
                                                case TrafficTopic.CONTROL_CLOSE_ICE /*11064*/:
                                                case TrafficTopic.CONTROL_CLOSE_SUBSIDENCE /*11065*/:
                                                    if (!this.e) {
                                                        this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_control_close, 4);
                                                        this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_control_close, 4);
                                                        break;
                                                    } else {
                                                        this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_control_close_local, 5);
                                                        break;
                                                    }
                                                case TrafficTopic.DANGER_CHILD /*11060*/:
                                                    this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_urgency, 4);
                                                    this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_urgency, 4);
                                                    break;
                                                default:
                                                    switch (layerTag) {
                                                        case TrafficTopic.ANNOUNCEMENT /*11070*/:
                                                            this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_announcement, 4);
                                                            this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_announcement, 4);
                                                            break;
                                                        case TrafficTopic.CAMERA_TAG /*11071*/:
                                                            this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_live_action, 4);
                                                            this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_live_action, 4);
                                                            break;
                                                        case TrafficTopic.EMERGENCY_EVENT_TAG /*11072*/:
                                                            this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_urgency, 4);
                                                            this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_urgency, 4);
                                                            break;
                                                        default:
                                                            if (!this.e) {
                                                                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_control_close, 4);
                                                                this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_control_close, 4);
                                                                break;
                                                            } else {
                                                                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_control_close_local, 5);
                                                                break;
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
                } else if (this.e) {
                    this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_ponding_local, 5);
                } else {
                    this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_ponding, 4);
                    this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_ponding, 4);
                }
            } else if (this.e) {
                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_construction_local, 5);
            } else {
                this.f.mDefaultMarker = pointOverlay.createMarker(R.drawable.traffic_construction, 4);
                this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_construction, 4);
            }
            ArrayList<String> uids = this.a.getUids();
            if (uids != null && uids.size() > 0 && TrafficOverlayItem.pondingContains(uids)) {
                this.f.mFocusMarker = pointOverlay.createMarker(R.drawable.traffic_ponding, 4);
            }
        }
    }

    public void init(ITrafficTopic iTrafficTopic, String str, boolean z, POIOverlayItem pOIOverlayItem) {
        this.a = (TrafficTopic) iTrafficTopic;
        this.b = str;
        this.e = z;
        this.f = pOIOverlayItem;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof TrafficOverlayItem)) {
            return false;
        }
        TrafficOverlayItem trafficOverlayItem = (TrafficOverlayItem) obj;
        if (trafficOverlayItem.getTopic() == null || this.a == null || trafficOverlayItem.getTopic().getId() != this.a.getId()) {
            return false;
        }
        return true;
    }

    public void setGeneratedTime(long j) {
        this.c = j;
    }

    public long getGeneratedTime() {
        return this.c;
    }

    public void setIsLocalReport(boolean z) {
        this.d = z;
    }

    public boolean isLocaReport() {
        return this.d;
    }

    public /* bridge */ /* synthetic */ ITrafficTopic getTopic() {
        return this.a;
    }
}
