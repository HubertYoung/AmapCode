package com.autonavi.bundle.uitemplate.api;

public interface IAmapPageHost {
    void onPageCreated();

    void onPageDestroy();

    void onPagePause();

    void onPageResume();

    void onPageStart();

    void onPageStop();
}
