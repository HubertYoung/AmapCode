package com.autonavi.jni.route.health;

import java.util.HashMap;
import java.util.Map;

public enum VoiceType {
    VT_DEFAULT(0),
    VT_LUO_YONG_HAO(1),
    VT_TF_BOY_WANGYUAN(2),
    VT_LIN_ZHI_LING(3),
    VT_GUO_DE_GANG(4),
    VT_TF_BOY_WANGJUNKAI(5),
    VT_TF_BOY_YIYANGQIANXI(6),
    VT_HUANGXIAOMING_NUANXIN(7),
    VT_HUANGXIAOMING_GENGZHI(8);
    
    private static Map<Integer, VoiceType> map;
    private int value;

    static {
        int i;
        VoiceType[] values;
        map = new HashMap();
        for (VoiceType voiceType : values()) {
            map.put(Integer.valueOf(voiceType.value), voiceType);
        }
    }

    private VoiceType(int i) {
        this.value = i;
    }

    public static VoiceType setValue(int i) {
        return map.get(Integer.valueOf(i));
    }

    public final int getValue() {
        return this.value;
    }
}
