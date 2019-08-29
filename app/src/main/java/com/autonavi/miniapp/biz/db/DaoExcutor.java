package com.autonavi.miniapp.biz.db;

public interface DaoExcutor<T> {
    T excute(AppDbHelper appDbHelper) throws Exception;
}
