package com.j256.ormlite.dao;

public interface RawRowMapper<T> {
    T mapRow(String[] strArr, String[] strArr2);
}
