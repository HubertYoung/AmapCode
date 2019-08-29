package com.autonavi.minimap.route.bus.busline.overlay;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline;
import com.autonavi.minimap.route.bus.realtimebus.model.TripInfo;
import com.autonavi.minimap.route.bus.realtimebus.model.stTrip;
import com.autonavi.minimap.widget.AmapTextView;

public class RealTimeBusOverlay extends PointOverlay<dus> {
    private Context mContext;
    private Bus mCurBus;
    private BusLinePointOverlay mPointOverlay;
    private RouteBusPointOverlay mRealTimeBusIconOverlay;
    private String mRealTimeBusIconTag;
    private RealTimeBusline mRealTimeBusline = null;

    public RealTimeBusOverlay(bty bty, BusLinePointOverlay busLinePointOverlay, RouteBusPointOverlay routeBusPointOverlay) {
        super(bty);
        setOverlayOnTop(true);
        setBubbleAnimator(2);
        this.mContext = bty.d();
        this.mPointOverlay = busLinePointOverlay;
        this.mRealTimeBusIconOverlay = routeBusPointOverlay;
        setMinDisplayLevel(15);
    }

    public void addRealTimeBuses(Bus bus, RealTimeBusline realTimeBusline, boolean z) {
        addRealTimeBuses(bus, realTimeBusline);
    }

    public void addRealTimeBuses(Bus bus, RealTimeBusline realTimeBusline) {
        if (bus != null && realTimeBusline != null && realTimeBusline.tripInfoMap != null) {
            this.mRealTimeBusline = realTimeBusline;
            this.mCurBus = bus;
            for (int i = 0; i < realTimeBusline.tripInfoMap.entrySet().size(); i++) {
                TripInfo tripInfo = realTimeBusline.tripInfoMap.get(Integer.valueOf(i));
                if (tripInfo != null) {
                    Integer valueOf = Integer.valueOf(i);
                    dus dus = new dus(tripInfo.gpoi, valueOf.intValue());
                    View realTimeBusTipView = getRealTimeBusTipView(valueOf.intValue());
                    if (realTimeBusTipView != null) {
                        dus.mDefaultMarker = createMarker(valueOf.intValue(), realTimeBusTipView, 5, 0.0f, 0.0f, false);
                        duy.a("RealTimeBusOverlay---", "addItem(realtimeBusInfoOverlayItem)");
                        addItem(dus);
                    }
                    dus dus2 = new dus(tripInfo.gpoi, valueOf.intValue());
                    if (TextUtils.isEmpty(this.mRealTimeBusIconTag) || !this.mRealTimeBusIconTag.startsWith(this.mContext.getString(R.string.busline_have_passed_by))) {
                        dus2.mDefaultMarker = createMarker(R.drawable.real_bus_icon, 4);
                    } else {
                        dus2.mDefaultMarker = createMarker(R.drawable.real_bus_icon_arrived, 4);
                    }
                    if (this.mRealTimeBusIconOverlay != null) {
                        this.mRealTimeBusIconOverlay.addItem(dus2);
                    }
                }
            }
        }
    }

    private View getRealTimeBusTipView(int i) {
        String str = null;
        if (this.mRealTimeBusline == null || this.mRealTimeBusline.tripInfoMap == null) {
            return null;
        }
        int lastFocusedIndex = this.mPointOverlay.getLastFocusedIndex();
        if (this.mRealTimeBusline.tripInfoMap.get(Integer.valueOf(i)) == null) {
            return null;
        }
        if (lastFocusedIndex >= 0 && lastFocusedIndex <= this.mCurBus.stations.length) {
            String str2 = this.mCurBus.stationIds[lastFocusedIndex];
            if (!(this.mRealTimeBusline.stationMap == null || str2 == null)) {
                stTrip sttrip = this.mRealTimeBusline.stationMap.get(str2);
                if (sttrip == null || sttrip.tripinfomap == null || sttrip.tripinfomap.size() == 0) {
                    str = this.mContext.getString(R.string.busline_have_passed_by);
                } else {
                    TripInfo tripInfo = sttrip.tripinfomap.get(Integer.valueOf(i));
                    if (tripInfo == null) {
                        str = this.mContext.getString(R.string.busline_have_passed_by);
                    } else {
                        try {
                            str = ebj.a(this.mContext, 4, TextUtils.isEmpty(tripInfo.sitetime) ? 0 : ahh.b(tripInfo.sitetime), TextUtils.isEmpty(tripInfo.stationleft) ? 0 : ahh.b(tripInfo.stationleft));
                        } catch (NumberFormatException unused) {
                            str = ebj.a(this.mContext, 4, 0, 0);
                        }
                    }
                }
            }
        }
        if (str != null) {
            this.mRealTimeBusIconTag = str;
        }
        return getMarkerFromView(this.mContext, str, lastFocusedIndex);
    }

    private View getMarkerFromView(Context context, String str, int i) {
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.tip_realtimebus, null);
        inflate.setTag(Boolean.FALSE);
        if (i < 0) {
            inflate.findViewById(R.id.businfo_layout).setVisibility(8);
        } else if (str != null && !str.trim().equals("")) {
            if (str.startsWith(this.mContext.getString(R.string.busline_have_passed_by))) {
                inflate.findViewById(R.id.businfo_layout).setVisibility(8);
                inflate.setTag(Boolean.TRUE);
            } else {
                AmapTextView amapTextView = (AmapTextView) inflate.findViewById(R.id.staion_time_textview);
                amapTextView.setVisibility(0);
                amapTextView.setText(str);
            }
        }
        duy.a("RealTimeBusOverlay---", "text1 = ".concat(String.valueOf(str)));
        duy.a("RealTimeBusOverlay---", "stationindex = ".concat(String.valueOf(i)));
        return inflate;
    }

    private String getRestTime(Context context, int i) {
        String str;
        if (i <= 60) {
            return context.getString(R.string.route_arriving_station);
        }
        int i2 = i / 60;
        if (i2 >= 60) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2 / 60);
            sb.append(context.getString(R.string.busline_hour));
            String sb2 = sb.toString();
            int i3 = i2 % 60;
            if (i3 > 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(i3);
                sb3.append(context.getString(R.string.busline_minute));
                str = sb3.toString();
            } else {
                str = sb2;
            }
        } else if (i2 == 0) {
            StringBuilder sb4 = new StringBuilder("1");
            sb4.append(context.getString(R.string.route_minutes));
            str = sb4.toString();
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(i2);
            sb5.append(context.getString(R.string.route_minutes));
            str = sb5.toString();
        }
        return str;
    }
}
