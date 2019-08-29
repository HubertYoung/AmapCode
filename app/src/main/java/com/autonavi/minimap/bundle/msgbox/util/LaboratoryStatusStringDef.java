package com.autonavi.minimap.bundle.msgbox.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
public @interface LaboratoryStatusStringDef {
    public static final String SP_KEY_LABORATORY_RED_SHOW_FLAG = "laboratory_red_show_flag";
    public static final String SP_KEY_MAIN_HEADER_RED_FLAG = "main_header_red_flag";
}
