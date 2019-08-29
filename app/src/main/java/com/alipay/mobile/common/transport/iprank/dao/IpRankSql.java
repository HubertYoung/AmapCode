package com.alipay.mobile.common.transport.iprank.dao;

public class IpRankSql {
    public static final String CLEAR_IPRANK = "delete from ip_rank";
    public static final String CREATE_IP_RANK = "CREATE TABLE ip_rank (_id integer primary key autoincrement,lbs_id integer,domain varchar(32),ip varchar(50),time bigint,ttl bigint,netType integer,rtt integer,successCount integer,failCount integer,feedbackSuccCount integer,feedbackSuccTime bigint,lastSuccTime bigint,grade float)";
    public static final String CREATE_LBS_TABLE = "create table lbs(_id integer primary key autoincrement,latlng varchar(32))";
    public static final String CREATE_LOCATION = "create table location(_id integer primary key autoincrement,bssid varchar(20),cellbs varchar(20))";
    public static final String DELETE_FINALLY = "delete from ip_rank where _id in (select _id from ip_rank order by failCount desc limit 0,?)";
    public static final String DELETE_FROM_IPRANK = "delete from ip_rank where (failCount+0.1)/(successCount+0.1) > ?";
    public static final String DELETE_SECOND = "delete from ip_rank where _id in (select _id from ip_rank where _id in (select _id from ip_rank  where _id in (select _id from ip_rank order by feedbackSuccTime asc limit 0,40) order by feedbackSuccCount asc limit 0,30) order by failCount desc limit 0,20)";
    public static final String GET_ALL_IPRANK_MODELS = "select domain,ip,time,ttl,rtt,successCount,failCount,feedbackSuccCount,feedbackSuccTime,lastSuccTime,grade from ip_rank where lbs_id = ? and netType = ?";
    public static final String GET_IPRANK_MODEL = "select ip,time,ttl,rtt,successCount,failCount,feedbackSuccCount,feedbackSuccTime,lastSuccTime,grade from ip_rank where domain = ?  and ip = ? and lbs_id = ? and netType = ?";
    public static final String GET_IPRANK_MODELS = "select ip,time,ttl,rtt,successCount,failCount,feedbackSuccCount,feedbackSuccTime,lastSuccTime,grade from ip_rank where domain = ?  and lbs_id = ? and netType = ?";
    public static final String GET_LBSMODELS_FROM_TABLE = "select _id,latlng from lbs";
    public static final String GET_LBS_ID = "select _id from lbs where latlng = ?";
    public static final String GET_LBS_TABLE_SIZE = "select count(*) from lbs";
    public static final String GET_TABLE_SIZE = "select count(*) from ip_rank";
    public static final String Get_GiVEN_NUM_IPRANK_MODELS = "select domain,ip,time,ttl,rtt,successCount,failCount,feedbackSuccCount,feedbackSuccTime,lastSuccTime,grade from ip_rank  where lbs_id = ? and netType = ? order by lastSuccTime desc limit 0,?";
    public static final String IP_RANK_TABLE = "ip_rank";
    public static final String LBS_TABLE = "lbs";
    public static final String REMOVEIPSFROMDB = "delete from ip_rank where domain = ?";
    public static final String REMOVEIPS_NOTIN_LOCALDNS = "delete from ip_rank where domain = ? and lbs_id = ? and netType = ? and ip not in ";
    public static final String REMOVE_IPMODEL_FROMDB = "delete from ip_rank where domain = ? and ip = ? and netType = ? and lbs_id = ?";
    public static final String REMOVE_SINGLE_IP_FROMDB = "delete from ip_rank where domain = ? and ip = ?";
    public static final String isIpInDB = "select ip from ip_rank where domain = ? and ip = ? and netType = ? and lbs_id = ?";
}
