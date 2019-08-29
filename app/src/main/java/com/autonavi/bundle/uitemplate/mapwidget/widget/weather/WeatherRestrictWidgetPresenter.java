package com.autonavi.bundle.uitemplate.mapwidget.widget.weather;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.parse.WeatherRestrict;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.sdk.log.util.LogConstant;

public class WeatherRestrictWidgetPresenter extends BaseMapWidgetPresenter<WeatherRestrictMapWidget> {
    private final String RESTRICT_NUM_BACKGROUND_COLOR = "#93A1BB";
    private final String SP_KEY_USER_INDIVIDUALITY_TYPE = "userIndividualityType";
    private final String SP_NAME_BASEMAP = "basemap";
    private final String USER_TYPE_BUS = "2";
    private final String USER_TYPE_DRIVER = "1";
    private String jumpSchema;
    protected StringBuffer mContextDes;
    private String mNearbyDataCache = "";

    public void internalClickListener(View view) {
        if (this.jumpSchema != null) {
            LogManager.actionLogV2("P00001", LogConstant.WEATHER_RESTRICT_CLICK);
            DoNotUseTool.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(this.jumpSchema)));
        }
    }

    public void responseData(String str) {
        if (str != null || "".equals(this.mNearbyDataCache)) {
            this.mNearbyDataCache = str;
        } else {
            str = this.mNearbyDataCache;
        }
        if (this.mBindWidget != null) {
            freshState(WeatherRestrict.createInstance(str));
        }
    }

    private String getUserType() {
        return new MapSharePreference((String) "basemap").getStringValue("userIndividualityType", "");
    }

    public void freshState(WeatherRestrict weatherRestrict) {
        this.jumpSchema = "";
        if (this.mBindWidget != null) {
            if (weatherRestrict == null) {
                ((WeatherRestrictMapWidget) this.mBindWidget).setWidgetContainerVisible(8);
                return;
            }
            this.jumpSchema = weatherRestrict.jumpSchema;
            ((WeatherRestrictMapWidget) this.mBindWidget).setWidgetContainerVisible(0);
            this.mContextDes = new StringBuffer();
            String userType = getUserType();
            boolean z = true;
            bez.a(((WeatherRestrictMapWidget) this.mBindWidget).getClass().getSimpleName(), "fresh weather info ", new bew("userType", userType));
            if ((!TextUtils.equals("1", userType) && TextUtils.equals("2", userType)) || !weatherRestrict.isHasTrafficRestrict()) {
                z = false;
            }
            if (weatherRestrict.isHasWeatherState()) {
                ((WeatherRestrictMapWidget) this.mBindWidget).setWeatherContainerVisibility(0);
                ((WeatherRestrictMapWidget) this.mBindWidget).setWeatherIcon(weatherRestrict.image_url);
                StringBuilder sb = new StringBuilder(weatherRestrict.temperature);
                sb.append("°");
                String sb2 = sb.toString();
                ((WeatherRestrictMapWidget) this.mBindWidget).setWeatherLabel(sb2);
                this.mContextDes.append(sb2);
                if (!z) {
                    ((WeatherRestrictMapWidget) this.mBindWidget).setCutLineVisible(8);
                    ((WeatherRestrictMapWidget) this.mBindWidget).setRestrictContainerVisible(8);
                    return;
                }
                ((WeatherRestrictMapWidget) this.mBindWidget).setCutLineVisible(0);
            } else {
                ((WeatherRestrictMapWidget) this.mBindWidget).setWeatherContainerVisibility(8);
                ((WeatherRestrictMapWidget) this.mBindWidget).setCutLineVisible(8);
                if (!z) {
                    ((WeatherRestrictMapWidget) this.mBindWidget).setWidgetContainerVisible(8);
                    return;
                }
            }
            ((WeatherRestrictMapWidget) this.mBindWidget).setRestrictContainerVisible(0);
            StringBuilder sb3 = new StringBuilder();
            String str = null;
            if (weatherRestrict.isTodayNotRestrict()) {
                sb3.append("今日不限行");
            } else {
                String replace = weatherRestrict.plate_no.replace(",", "");
                if (replace != null && replace.length() > 3) {
                    replace = replace.substring(0, 3);
                }
                str = replace;
                sb3.append("限行");
                this.mContextDes.append(str);
            }
            this.mContextDes.append(sb3);
            boolean isNum = isNum(str);
            dealWithPlateNo(sb3.toString(), str, isNum ? 1.08f : 0.83f, isNum);
            ((WeatherRestrictMapWidget) this.mBindWidget).getContentView().setContentDescription(this.mContextDes.toString());
        }
    }

    private boolean isNum(String str) {
        char[] charArray = TextUtils.isEmpty(str) ? null : str.toCharArray();
        if (charArray != null && charArray.length > 0) {
            for (char c : charArray) {
                if (c < '0' || c > '9') {
                    return false;
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void dealWithPlateNo(String str, String str2, float f, boolean z) {
        if (!TextUtils.isEmpty(str2)) {
            ((WeatherRestrictMapWidget) this.mBindWidget).setRestrictLabel(doPlateNo(Token.SEPARATOR.concat(String.valueOf(str2)), 1, str2.length(), f, z), str);
            return;
        }
        ((WeatherRestrictMapWidget) this.mBindWidget).setRestrictLabel("", str);
    }

    private SpannableStringBuilder doPlateNo(CharSequence charSequence, int i, int i2, float f, boolean z) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = i + i3;
            int i5 = i4 + 1;
            spannableStringBuilder.setSpan(new RelativeSizeSpan(f), i4, i5, 17);
            spannableStringBuilder.setSpan(new bex(Color.parseColor("#93A1BB"), z), i4, i5, 17);
        }
        return spannableStringBuilder;
    }
}
