package com.alipay.android.phone.mobilesdk.socketcraft.handshake;

import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public class HandshakedataImpl1 implements HandshakeBuilder {
    private byte[] content;
    private TreeMap<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public Iterator<String> iterateHttpFields() {
        return Collections.unmodifiableSet(this.map.keySet()).iterator();
    }

    public String getFieldValue(String name) {
        String s = this.map.get(name);
        if (s == null) {
            return "";
        }
        return s;
    }

    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content2) {
        this.content = content2;
    }

    public void put(String name, String value) {
        this.map.put(name, value);
    }

    public boolean hasFieldValue(String name) {
        return this.map.containsKey(name);
    }
}
