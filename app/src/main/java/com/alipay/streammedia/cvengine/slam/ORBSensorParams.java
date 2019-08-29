package com.alipay.streammedia.cvengine.slam;

public class ORBSensorParams {
    private long timeStamp;
    private SensorType type;
    private float x;
    private float y;
    private float z;

    public enum SensorType {
        GYROSCOPE(0, "陀螺仪"),
        GRAVIMETER(1, "重力计"),
        ACCELEROMETER(2, "加速计"),
        ROTATION(3, "偏转角度");
        
        private int index;
        private String name;

        private SensorType(int index2, String name2) {
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
            SensorType[] values;
            for (SensorType t : values()) {
                if (t.getIndex() == index2) {
                    return t.getName();
                }
            }
            return "Unknown Type";
        }
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp2) {
        this.timeStamp = timeStamp2;
    }

    public int getType() {
        return this.type.getIndex();
    }

    public void setType(SensorType type2) {
        this.type = type2;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x2) {
        this.x = x2;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y2) {
        this.y = y2;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z2) {
        this.z = z2;
    }
}
