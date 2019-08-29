package com.alibaba.analytics.core.store;

import com.alibaba.analytics.core.model.Log;
import java.util.List;

public interface ILogStore {
    void clear();

    int clearOldLogByCount(int i);

    int clearOldLogByField(String str, String str2);

    int count();

    int delete(List<Log> list);

    List<Log> get(int i);

    double getDbFileSize();

    boolean insert(List<Log> list);

    void update(List<Log> list);

    void updateLogPriority(List<Log> list);
}
