package com.alipay.streammedia.cvengine.slam;

public enum ORBRunMode {
    VISION(0, "视觉模式"),
    IMU(1, "IMU模式"),
    FUSE(2, "融合模式（视觉+IMU）");
    
    private int index;
    private String name;

    private ORBRunMode(int index2, String name2) {
        this.index = index2;
        this.name = name2;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index2) {
        this.index = index2;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String name2) {
        this.name = name2;
    }

    public static String getNameByIndex(int index2) {
        ORBRunMode[] values;
        for (ORBRunMode t : values()) {
            if (t.getIndex() == index2) {
                return t.getName();
            }
        }
        return "Unknown Error";
    }
}
