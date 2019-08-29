package com.alipay.android.phone.inside.offlinecode.engine;

public enum JSEngineType {
    WEBKIT("webkit", "com.alipay.android.phone.inside.offlinecode.engine.webkit.WebkitJSEngine");
    
    String clazz;
    String name;

    private JSEngineType(String str, String str2) {
        this.name = str;
        this.clazz = str2;
    }
}
