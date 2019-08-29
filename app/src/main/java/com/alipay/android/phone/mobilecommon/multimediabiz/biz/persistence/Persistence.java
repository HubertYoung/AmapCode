package com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import java.util.List;

public interface Persistence<T> {
    T add(T t);

    List<T> add(List<T> list);

    T[] add(T... tArr);

    long countOf();

    T delete(Class<T> cls, String str);

    T delete(T t);

    List<T> delete(List<T> list);

    T[] delete(T... tArr);

    DeleteBuilder<T, String> deleteBuilder();

    void deleteById(Object obj);

    String getTableName();

    T query(Class<T> cls, String str);

    List<T> queryAll(Class<T> cls);

    QueryBuilder<T, String> queryBuilder();

    List<T> queryForEq(Class<T> cls, String str, String str2);

    T save(T t);

    List<T> save(List<T> list);

    T[] save(T... tArr);

    T update(T t, String... strArr);

    List<T> update(List<T> list, String... strArr);

    UpdateBuilder<T, String> updateBuilder();
}
