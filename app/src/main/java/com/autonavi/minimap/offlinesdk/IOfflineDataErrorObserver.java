package com.autonavi.minimap.offlinesdk;

public interface IOfflineDataErrorObserver {
    boolean onDataEngineNotMachted(int i, int i2);

    boolean onDataOpenError(int i, int i2);

    boolean onDataParseError(int i, int i2);

    boolean onDataVersionNotMatched(int i, int i2);
}
