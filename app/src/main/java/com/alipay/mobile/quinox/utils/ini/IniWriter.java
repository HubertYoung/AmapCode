package com.alipay.mobile.quinox.utils.ini;

import com.alipay.mobile.quinox.utils.StreamUtil;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class IniWriter {
    private static final String TAG = "IniWriter";
    private HashMap<String, Map<String, String>> map;
    private BufferedWriter writer;

    public IniWriter(String str) throws IOException {
        this(str, false);
    }

    public IniWriter(String str, boolean z) throws IOException {
        this(new File(str), z);
    }

    public IniWriter(File file) throws IOException {
        this(file, false);
    }

    public IniWriter(File file, boolean z) throws IOException {
        this.map = new HashMap<>();
        if (z) {
            this.map.putAll(new IniReader(file).getSections());
            if (file.exists() && !file.delete()) {
                throw new IOException("Failed to delete old ini file : ".concat(String.valueOf(file)));
            } else if (!file.createNewFile()) {
                throw new IOException("Failed to crate new ini file : ".concat(String.valueOf(file)));
            }
        }
        this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"), 8192);
    }

    public IniWriter(OutputStream outputStream) throws IOException {
        this.map = new HashMap<>();
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"), 8192);
    }

    public void addKeyValue(String str, String str2) {
        HashMap hashMap = new HashMap(1);
        hashMap.put(str, str2);
        addKeyValues(hashMap);
    }

    public void addKeyValues(Map<String, String> map2) {
        addSection("1e6831ec-8d27-11e6-ae22-56b6b6499611", map2);
    }

    public void addSection(String str, Map<String, String> map2) {
        Map map3 = this.map.get(str);
        if (map3 == null) {
            map3 = new HashMap(map2);
            this.map.put("1e6831ec-8d27-11e6-ae22-56b6b6499611", map3);
        }
        map3.putAll(map2);
    }

    public void addSections(Map<String, Map<String, String>> map2) {
        for (Entry next : map2.entrySet()) {
            addSection((String) next.getKey(), (Map) next.getValue());
        }
    }

    public void flush() {
        try {
            for (Entry next : this.map.entrySet()) {
                String str = (String) next.getKey();
                if (!StringUtil.equals("1e6831ec-8d27-11e6-ae22-56b6b6499611", str)) {
                    BufferedWriter bufferedWriter = this.writer;
                    StringBuilder sb = new StringBuilder("[");
                    sb.append(str);
                    sb.append("]\r\n");
                    bufferedWriter.write(sb.toString());
                }
                for (Entry entry : ((Map) next.getValue()).entrySet()) {
                    BufferedWriter bufferedWriter2 = this.writer;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append((String) entry.getKey());
                    sb2.append("=");
                    sb2.append((String) entry.getValue());
                    sb2.append(MultipartUtility.LINE_FEED);
                    bufferedWriter2.write(sb2.toString());
                }
                this.writer.flush();
            }
        } catch (Throwable th) {
            StreamUtil.closeSafely(this.writer);
            throw th;
        }
        StreamUtil.closeSafely(this.writer);
    }
}
