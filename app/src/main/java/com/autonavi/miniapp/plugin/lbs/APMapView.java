package com.autonavi.miniapp.plugin.lbs;

import android.graphics.Bitmap;
import android.os.Bundle;
import java.util.List;

public interface APMapView {
    public static final String HIDE_BADGE = "0";
    public static final String HIDE_GOTOTAXI = "1";
    public static final int SEARCH_MODE_BUS = 1001;
    public static final int SEARCH_MODE_DRIVE = 1002;
    public static final int SEARCH_MODE_WALK = 1003;

    public interface OnCloudSearchListener {
        void onCloudSearched(List<CloudItem> list);
    }

    public interface OnMapClickListener {
        void onMapClick(LatLonPoint latLonPoint);
    }

    public interface OnPoiActionListener {
        void onPoiClick(LatLonPointEx latLonPointEx);

        void onPoiDrag(LatLonPointEx latLonPointEx);

        void onPoiDragEnd(LatLonPointEx latLonPointEx);

        void onPoiDragStart(LatLonPointEx latLonPointEx);
    }

    public interface OnSearchListener {
        void onPoiSearched(List<PoiItem> list);
    }

    void clear();

    void drawingCircle(Double d, Double d2, Double d3);

    void drawingPolygonOptions(List<String> list);

    boolean getInfoWindowClickEnabled();

    void locate();

    void moveToLatLng(LatLonPoint latLonPoint, int i);

    void onCreateView(Bundle bundle);

    void onDestroyView();

    void onPauseView();

    void onResumeView();

    void onSaveInstance(Bundle bundle);

    void search(String str, String str2);

    void searchRoute(LatLonPoint latLonPoint, LatLonPoint latLonPoint2, String str, int i);

    void searchRoute(LatLonPointEx latLonPointEx, LatLonPointEx latLonPointEx2, String str, int i);

    void searchViaCloud(String str, String str2, double d, double d2);

    void searchViaCloud(String str, String str2, LatLonPoint latLonPoint);

    void setHiddenInfo(String str);

    void setInfoWindowClickEnabled(boolean z);

    void setMyLocationEnabled(boolean z);

    void setMyLocationIcon(Bitmap bitmap);

    void setOnCloudSearchListener(OnCloudSearchListener onCloudSearchListener);

    void setOnLocateListener(OnLocateListener onLocateListener);

    void setOnMapClickListener(OnMapClickListener onMapClickListener);

    void setOnPoiActionListener(OnPoiActionListener onPoiActionListener);

    void setOnSearchListener(OnSearchListener onSearchListener);

    void setSearchRouteMode(int i);

    void setShowMarkerInfoWindowEnable(boolean z);

    void setZoomControlsEnabled(boolean z);

    void showPoint(double d, double d2);

    void showPoint(double d, double d2, int i);

    void showPoint(LatLonPoint latLonPoint);

    void showPoint(LatLonPoint latLonPoint, int i);

    void showPointEx(LatLonPointEx latLonPointEx);

    void showPointEx(LatLonPointEx latLonPointEx, int i);

    void showPoints(List<LatLonPoint> list);

    void showPointsEx(List<LatLonPointEx> list);

    void startNavigate(Double d, Double d2, String str);
}
