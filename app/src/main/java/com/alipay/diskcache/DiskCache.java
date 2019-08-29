package com.alipay.diskcache;

import android.graphics.Bitmap;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.diskcache.model.StatisticInfo;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public interface DiskCache {

    public interface QueryFilter {
        FileCacheModel parse(List<FileCacheModel> list);
    }

    boolean appendAliasKey(String str, String str2);

    void clear();

    void close();

    String genPathByKey(String str);

    FileCacheModel get(String str);

    FileCacheModel get(String str, QueryFilter queryFilter);

    List<FileCacheModel> getAlias(int i);

    List<FileCacheModel> getMultiAlias(int i);

    String getPath(String str);

    List<FileCacheModel> getRecent(long j, int i);

    long getTotalSize(int i);

    long getTotalSize(String str);

    List<String> queryAllBusiness();

    List<FileCacheModel> queryExpiredRecords(int i, boolean z);

    List<FileCacheModel> queryForStatistic(String str, int i, boolean z, long j);

    List<FileCacheModel> queryForStatistic(String str, int i, boolean z, long j, boolean z2);

    List<FileCacheModel> queryNonWhiteListRecords(Set<String> set, int i, boolean z);

    boolean remove(String str);

    boolean remove(List<FileCacheModel> list);

    boolean save(String str, int i, int i2, String str2, long j);

    boolean save(String str, Bitmap bitmap, String str2);

    boolean save(String str, InputStream inputStream, String str2);

    boolean save(String str, String str2, int i, int i2, String str3, String str4, long j);

    boolean save(String str, byte[] bArr, String str2);

    void setupExpiredWhiteList(Set<String> set);

    List<StatisticInfo> statisticByGroup(String str, int i, boolean z, long j);

    List<StatisticInfo> statisticByGroup(String str, int i, boolean z, long j, boolean z2);

    void trim();

    boolean update(FileCacheModel fileCacheModel);

    boolean update(String str, int i);

    boolean update(String str, String str2);

    boolean update(String str, String str2, int i);

    void updateConfig(StrategyConfig strategyConfig);
}
