package com.autonavi.minimap.auidebugger.boommenu.Types;

public enum StateType {
    CLOSED(0),
    OPENING(1),
    OPEN(2),
    CLOSING(3);
    
    int a;

    private StateType(int i) {
        this.a = i;
    }
}
