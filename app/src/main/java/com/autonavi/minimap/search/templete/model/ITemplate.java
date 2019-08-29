package com.autonavi.minimap.search.templete.model;

import java.util.List;
import java.util.Map;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public interface ITemplate<T> {
    List<T> getTemplateData();

    Map<Integer, T> getTemplateDataMap();

    void setTemplateData(List<T> list);

    void setTemplateDataMap(Map<Integer, T> map);
}
