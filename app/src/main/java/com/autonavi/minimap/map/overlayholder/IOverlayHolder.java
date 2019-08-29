package com.autonavi.minimap.map.overlayholder;

public interface IOverlayHolder {
    void clearAndRemove();

    void restore();

    void save();
}
