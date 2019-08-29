package com.autonavi.minimap.map.overlayholder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface OverlayPage {

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OvProperty {
        boolean clickable() default false;

        boolean moveToFocus() default false;

        UvOverlay overlay() default UvOverlay.None;

        boolean visible() default false;

        VisiblePolicy visiblePolicy() default VisiblePolicy.CareConfig;
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface OverlayPageProperty {
        OvProperty[] overlays() default {};
    }

    public enum UvOverlay {
        None,
        GpsOverlay,
        MapPointOverlay,
        TrafficOverlay,
        LocalReportOverlay,
        SaveOverlay,
        GeoCodeOverlay
    }

    public enum VisiblePolicy {
        CareConfig,
        IgnoreConfig
    }
}
