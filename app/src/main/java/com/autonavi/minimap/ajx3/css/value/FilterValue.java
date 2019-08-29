package com.autonavi.minimap.ajx3.css.value;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class FilterValue {
    public int blur;
    public float brightness;
    public float saturate;
    public int shadowBlur;
    public int shadowColor;
    public int shadowX;
    public int shadowY;

    public String toString() {
        return String.format("@css value filter: {_blur:%s, _brightness:%s, _saturate:%s, _shadow_x:%s, _shadow_y:%s, _shadow_blur:%s, _shadow_color:%s}", new Object[]{Integer.valueOf(this.blur), Float.valueOf(this.brightness), Float.valueOf(this.saturate), Integer.valueOf(this.shadowX), Integer.valueOf(this.shadowY), Integer.valueOf(this.shadowBlur), Integer.valueOf(this.shadowColor)});
    }
}
