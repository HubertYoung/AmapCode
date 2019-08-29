package com.autonavi.minimap.ajx3.muparser;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ParserException extends Exception {
    public ParserException(String str) {
        super(str);
    }
}
