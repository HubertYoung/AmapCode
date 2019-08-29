package com.autonavi.minimap.ackor.ackoramap;

public interface IAmapService {
    IAmapHttpRequest createAmapHttpRequest();

    void destroyAmapHttpRequest(IAmapHttpRequest iAmapHttpRequest);

    IBehavior getBehavior();
}
