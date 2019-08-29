package com.j256.ormlite.android.globalsearch;

import java.util.List;

public interface GlobalSearchAgent {
    boolean addDB(String str, String str2, int i);

    boolean addDB(String str, String str2, int i, String str3, boolean z);

    boolean addIndexForTable(String str, String str2, String str3, List<String> list, int i, String str4);

    boolean addIndexForTable(String str, String str2, String str3, List<String> list, int i, String str4, List<String> list2);

    boolean addIndexForTable(String str, String str2, String str3, List<String> list, int i, String str4, List<String> list2, List<String> list3, List<String> list4);
}
