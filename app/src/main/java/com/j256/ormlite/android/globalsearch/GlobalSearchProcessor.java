package com.j256.ormlite.android.globalsearch;

import java.util.List;

public class GlobalSearchProcessor {
    private static GlobalSearchAgent globalSearchAgent;

    public static GlobalSearchAgent getGlobalSearchAgent() {
        return globalSearchAgent;
    }

    public static void setGlobalSearchAgent(GlobalSearchAgent gsa) {
        globalSearchAgent = gsa;
    }

    public static boolean addDB(String dbPath, String dbName, int dbConnection) {
        if (globalSearchAgent != null) {
            return globalSearchAgent.addDB(dbPath, dbName, dbConnection);
        }
        return false;
    }

    public static boolean addDB(String dbPath, String dbName, int dbConnection, String password, boolean useWAL) {
        if (globalSearchAgent != null) {
            return globalSearchAgent.addDB(dbPath, dbName, dbConnection, password, useWAL);
        }
        return false;
    }

    public static boolean addIndexForTable(String indexName, String dbName, String tableName, List<String> fields, int maxRows, String whereCondition) {
        if (globalSearchAgent != null) {
            return addIndexForTable(indexName, dbName, tableName, fields, maxRows, whereCondition);
        }
        return false;
    }

    public static boolean addIndexForTable(String indexName, String dbName, String tableName, List<String> fields, int maxRows, String whereCondition, List<String> pinyinField) {
        if (globalSearchAgent != null) {
            return addIndexForTable(indexName, dbName, tableName, fields, maxRows, whereCondition, pinyinField);
        }
        return false;
    }

    public static boolean addIndexForTable(String indexName, String dbName, String tableName, List<String> fields, int maxRows, String whereCondition, List<String> pinyinField, List<String> profileField, List<String> notokenField) {
        if (globalSearchAgent != null) {
            return addIndexForTable(indexName, dbName, tableName, fields, maxRows, whereCondition, pinyinField, profileField, notokenField);
        }
        return false;
    }
}
