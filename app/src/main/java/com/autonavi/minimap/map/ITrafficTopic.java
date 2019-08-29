package com.autonavi.minimap.map;

import java.util.ArrayList;

public interface ITrafficTopic {
    int getFilterKey();

    int getId();

    ArrayList<ITrafficTopic> getSubinfo();

    void setId(int i);
}
