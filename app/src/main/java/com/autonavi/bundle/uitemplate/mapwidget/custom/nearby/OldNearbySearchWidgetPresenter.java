package com.autonavi.bundle.uitemplate.mapwidget.custom.nearby;

import com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.parse.WeatherRestrict;

public class OldNearbySearchWidgetPresenter extends NearBySearchWidgetPresenter {
    public void freshState(WeatherRestrict weatherRestrict) {
        this.mContextDes = new StringBuffer("探索附近");
        if (weatherRestrict == null || !isWidgetNotNull()) {
            ((NearBySearchMapWidget) this.mBindWidget).setWeatherRestrictVisible(8);
            ((NearBySearchMapWidget) this.mBindWidget).getContentView().setContentDescription(this.mContextDes.toString());
            return;
        }
        ((NearBySearchMapWidget) this.mBindWidget).setWeatherRestrictVisible(0);
        ((NearBySearchMapWidget) this.mBindWidget).setCutLineVisible(8);
        boolean isHasWeatherState = weatherRestrict.isHasWeatherState();
        if (isHasWeatherState) {
            setWeatherTemperature(weatherRestrict.image_url, weatherRestrict.temperature);
            StringBuffer stringBuffer = this.mContextDes;
            stringBuffer.append(weatherRestrict.temperature);
            stringBuffer.append("度");
        } else {
            ((NearBySearchMapWidget) this.mBindWidget).setWeatherContainerVisibility(8);
        }
        if (weatherRestrict.isHasTrafficRestrict()) {
            ((NearBySearchMapWidget) this.mBindWidget).setRestrictVisible(0);
            StringBuilder sb = new StringBuilder("今日");
            String str = null;
            if (weatherRestrict.isTodayNotRestrict()) {
                sb.append("不限行");
                this.mContextDes.append(sb.toString());
            } else {
                str = weatherRestrict.plate_no.replace(",", "");
                sb.append(str);
                sb.append("限行");
                StringBuffer stringBuffer2 = this.mContextDes;
                stringBuffer2.append("今日");
                stringBuffer2.append(weatherRestrict.plate_no);
                stringBuffer2.append("限行");
            }
            dealWithPlateNo(sb.toString(), str, 1.1f);
            if (((OldNearbySearchMapWidget) this.mBindWidget).isInSafeRegion()) {
                if (isHasWeatherState) {
                    ((NearBySearchMapWidget) this.mBindWidget).setCutLineVisible(0);
                }
                ((NearBySearchMapWidget) this.mBindWidget).getContentView().setContentDescription(this.mContextDes.toString());
                return;
            }
        } else {
            ((NearBySearchMapWidget) this.mBindWidget).setRestrictVisible(8);
            if (isHasWeatherState) {
                ((NearBySearchMapWidget) this.mBindWidget).getContentView().setContentDescription(this.mContextDes.toString());
                return;
            }
        }
        ((NearBySearchMapWidget) this.mBindWidget).setWeatherRestrictVisible(8);
    }
}
