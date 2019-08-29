package com.autonavi.jni.ae.gmap.glyph;

public class FontStyle {
    public static final int STYLE_ITALIC = 1;
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_OBLIQUE = 2;
    public static final int VARIANT_NORAL = 8;
    public static final int VARIANT_SMALL_CAPS = 16;
    public static final int WEIGHT_BLACK = 900;
    public static final int WEIGHT_BOLD = 700;
    public static final int WEIGHT_EXTRA_BLACK = 1000;
    public static final int WEIGHT_EXTRA_BOLD = 800;
    public static final int WEIGHT_EXTRA_LIGHT = 200;
    public static final int WEIGHT_INVISIBLE = 0;
    public static final int WEIGHT_LIGHT = 300;
    public static final int WEIGHT_MEDIUM = 500;
    public static final int WEIGHT_NORMAL = 400;
    public static final int WEIGHT_SEMI_BOLD = 600;
    public static final int WEIGHT_THIN = 100;
    public String family = "";
    public boolean isStroke = false;
    public float lineWidth = 0.0f;
    public float size = 10.0f;
    public int style = 0;
    public int variant = 8;
    public int weight = 400;
}
