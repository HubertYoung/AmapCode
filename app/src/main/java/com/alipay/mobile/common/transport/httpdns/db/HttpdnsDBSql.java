package com.alipay.mobile.common.transport.httpdns.db;

public class HttpdnsDBSql {
    public static final String CREATE_HTTPDNS_ORIGINAL = "create table httpdns_original (_id integer primary key autoincrement,domain varchar(32),ip varchar(50),port integer,time bigint,ttl bigint,netType integer,gr varchar(20),cname varchar(64),ttd integer)";
    public static final String HttpdnsOriginal_tableName = "httpdns_original";
    public static final String ISHOSTINDB_INGORE_NETTYPE = "select ttl from httpdns_original where domain = ?";
    public static final String REMOVEIPINFOFROMDB_INGORE_NETTYPE = "delete from httpdns_original where domain = ?";
    public static final String clearHttpdnsOriginal = "delete from httpdns_original";
    public static final String dropTable = "drop table httpdns_original";
    public static final String getAllIPFromDB = "select domain,ip,port,time,ttl,gr,cname,ttd from httpdns_original where netType = ?";
    public static final String isHostInDB = "select ttl from httpdns_original where domain = ? and netType = ? ";
    public static final String queryIpInfoFromDB = "select ip,port,time,ttl,gr,cname,ttd from httpdns_original where domain = ? and netType = ?";
    public static final String removeIpInfoFromDB = "delete from httpdns_original where domain = ? and netType = ? ";
    public static final String removeSingleIpInfoFromDB = "delete from httpdns_original where domain = ? and ip = ? and netType = ? ";
}
