package com.alipay.mobile.quinox.utils.ini;

import com.alipay.mobile.quinox.log.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IniReader {
    static final String DEFAULT_SECTION = "1e6831ec-8d27-11e6-ae22-56b6b6499611";
    private static final String TAG = "IniReader";
    private HashMap<String, Map<String, String>> map;

    public IniReader(String str) throws IOException {
        this(new File(str));
    }

    public IniReader(File file) throws IOException {
        this((InputStream) new FileInputStream(file));
    }

    public IniReader(InputStream inputStream) throws IOException {
        this(new BufferedReader(new InputStreamReader(inputStream)));
    }

    public IniReader(BufferedReader bufferedReader) throws IOException {
        Map map2;
        this.map = new HashMap<>();
        String str = null;
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                String trim = readLine.trim();
                if (trim.length() != 0 && !trim.matches("^\\#.*$")) {
                    if (trim.matches("^\\[\\S+\\]$")) {
                        String replaceFirst = trim.replaceFirst("^\\[(\\S+)\\]$", "$1");
                        if (!this.map.containsKey(replaceFirst)) {
                            this.map.put(replaceFirst, new HashMap());
                            str = replaceFirst;
                        }
                    } else if (trim.matches("^\\S+=.*$")) {
                        int indexOf = trim.indexOf("=");
                        String trim2 = trim.substring(0, indexOf).trim();
                        String trim3 = trim.substring(indexOf + 1).trim();
                        if (str == null) {
                            str = DEFAULT_SECTION;
                            map2 = new HashMap();
                            this.map.put(str, map2);
                        } else {
                            map2 = this.map.get(str);
                        }
                        map2.put(trim2, trim3);
                    } else {
                        Log.w((String) TAG, "unknown line:".concat(String.valueOf(trim)));
                    }
                }
            } else {
                return;
            }
        }
    }

    public String get(String str, String str2) {
        if (str == null || str.trim().length() == 0) {
            str = DEFAULT_SECTION;
        }
        return getSection(str).get(str2);
    }

    public String get(String str) {
        return getSection(DEFAULT_SECTION).get(str);
    }

    public Map<String, String> get() {
        return getSection(DEFAULT_SECTION);
    }

    public Map<String, String> getSection(String str) {
        if (str == null || str.trim().length() == 0) {
            str = DEFAULT_SECTION;
        }
        return this.map.get(str);
    }

    public Map<String, Map<String, String>> getSections() {
        return this.map;
    }

    public Set<String> getSectionNames() {
        return this.map.keySet();
    }
}
