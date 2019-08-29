package com.alipay.mobile.beehive.util;

public class GradientColor {
    public static final GradientColor BLUE = new GradientColor(BLUE_START_COLOR, BLUE_END_COLOR);
    private static final int BLUE_END_COLOR = -12821079;
    private static final int BLUE_START_COLOR = -11500353;
    public static final GradientColor GREEN = new GradientColor(GREEN_START_COLOR, GREEN_END_COLOR);
    private static final int GREEN_END_COLOR = -16345206;
    private static final int GREEN_START_COLOR = -15159660;
    public static final GradientColor RED = new GradientColor(RED_START_COLOR, RED_END_COLOR);
    private static final int RED_END_COLOR = -2466684;
    private static final int RED_START_COLOR = -1218706;
    private int endColor;
    private int startColor;

    public GradientColor(int startColor2, int endColor2) {
        this.startColor = startColor2;
        this.endColor = endColor2;
    }

    public int[] getStartEndColors() {
        return new int[]{this.startColor, this.endColor};
    }
}
