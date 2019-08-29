package com.alipay.mobile.common.transport.config.db;

public class SqlConstants {
    public static final String COUNT_BY_KEY_SQL = "SELECT id  FROM nw_conf_mng_table WHERE switch_key = ? ";
    public static final String CREATE_TABLE_SQL = "CREATE TABLE `nw_conf_mng_table` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  `switch_key` VARCHAR(500) NOT NULL UNIQUE ,  `value` TEXT ,  `gmt_modified` bigint NOT NULL ,  `gmt_created` bigint NOT NULL );";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS nw_conf_mng_table";
    public static final String QUERY_BY_KEY_SQL = "SELECT *  FROM nw_conf_mng_table WHERE switch_key = ? ";
}
