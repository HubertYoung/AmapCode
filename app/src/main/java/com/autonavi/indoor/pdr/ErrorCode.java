package com.autonavi.indoor.pdr;

public class ErrorCode extends Error {
    public static final int SENSOR_ACC_TIMESTAMP_ERROR = 1024;
    public static final int SENSOR_ACC_UN_AVAILABLE = 1;
    public static final int SENSOR_ACC_UPDATE_ERROR = 32;
    public static final int SENSOR_GRAVITY_TIMESTAMP_ERROR = 2048;
    public static final int SENSOR_GRAVITY_UN_AVAILABLE = 2;
    public static final int SENSOR_GRAVITY_UPDATE_ERROR = 64;
    public static final int SENSOR_GYRO_TIMESTAMP_ERROR = 4096;
    public static final int SENSOR_GYRO_UN_AVAILABLE = 4;
    public static final int SENSOR_GYRO_UPDATE_ERROR = 128;
    public static final int SENSOR_MAGNETIC_TIMESTAMP_ERROR = 8192;
    public static final int SENSOR_MAGNETIC_UN_AVAILABLE = 8;
    public static final int SENSOR_MAGNETIC_UPDATE_ERROR = 256;
    public static final int SENSOR_PRESS_TIMESTAMP_ERROR = 16384;
    public static final int SENSOR_PRESS_UN_AVAILABLE = 16;
    public static final int SENSOR_PRESS_UPDATE_ERROR = 512;

    public ErrorCode(String str) {
        super(str);
    }

    public static String getErrorString(long j) {
        String str = "";
        int i = ((1 & j) > 0 ? 1 : ((1 & j) == 0 ? 0 : -1));
        if (i != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("加速度计不可用,");
            str = sb.toString();
        }
        int i2 = ((2 & j) > 0 ? 1 : ((2 & j) == 0 ? 0 : -1));
        if (i2 != 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("重力计不可用,");
            str = sb2.toString();
        }
        if (!(i == 0 || i2 == 0)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(" 定位方向会不准确");
            str = sb3.toString();
        }
        int i3 = ((4 & j) > 0 ? 1 : ((4 & j) == 0 ? 0 : -1));
        if (i3 != 0) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append("陀螺仪不可用,");
            str = sb4.toString();
        }
        int i4 = ((8 & j) > 0 ? 1 : ((8 & j) == 0 ? 0 : -1));
        if (i4 != 0) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append("地磁计不可用,");
            str = sb5.toString();
        }
        if (!(i3 == 0 || i4 == 0)) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(str);
            sb6.append("定位结果可能会存在跳点现象");
            str = sb6.toString();
        }
        if ((16 & j) != 0) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append(str);
            sb7.append("气压计不可用，楼层判断可能不准确。");
            str = sb7.toString();
        }
        int i5 = ((32 & j) > 0 ? 1 : ((32 & j) == 0 ? 0 : -1));
        if (i5 != 0) {
            StringBuilder sb8 = new StringBuilder();
            sb8.append(str);
            sb8.append("加速度计更新错误，");
            sb8.append("监测到了此传感器，但是一段时间内没有接收到回调数据.");
            str = sb8.toString();
        }
        int i6 = ((64 & j) > 0 ? 1 : ((64 & j) == 0 ? 0 : -1));
        if (i6 != 0) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append(str);
            sb9.append("重力计更新错误");
            sb9.append("监测到了此传感器，但是一段时间内没有接收到回调数据.");
            str = sb9.toString();
        }
        if (!(i5 == 0 || i6 == 0)) {
            StringBuilder sb10 = new StringBuilder();
            sb10.append(str);
            sb10.append(" 定位方向会不准确");
            str = sb10.toString();
        }
        int i7 = ((128 & j) > 0 ? 1 : ((128 & j) == 0 ? 0 : -1));
        if (i7 != 0) {
            StringBuilder sb11 = new StringBuilder();
            sb11.append(str);
            sb11.append("陀螺仪更新错误");
            sb11.append("监测到了此传感器，但是一段时间内没有接收到回调数据.");
            str = sb11.toString();
        }
        int i8 = ((256 & j) > 0 ? 1 : ((256 & j) == 0 ? 0 : -1));
        if (i8 != 0) {
            StringBuilder sb12 = new StringBuilder();
            sb12.append(str);
            sb12.append("地磁更新错误");
            sb12.append("监测到了此传感器，但是一段时间内没有接收到回调数据.");
            str = sb12.toString();
        }
        if (!(i7 == 0 || i8 == 0)) {
            StringBuilder sb13 = new StringBuilder();
            sb13.append(str);
            sb13.append("定位结果可能会存在跳点现象");
            str = sb13.toString();
        }
        if ((512 & j) != 0) {
            StringBuilder sb14 = new StringBuilder();
            sb14.append(str);
            sb14.append("气压计更新错误");
            sb14.append("监测到了此传感器，但是一段时间内没有接收到回调数据.");
            sb14.append("，楼层判断可能不准确。");
            str = sb14.toString();
        }
        if ((1024 & j) != 0) {
            StringBuilder sb15 = new StringBuilder();
            sb15.append(str);
            sb15.append("时间戳错误，传感器");
            sb15.append("陀螺仪或磁传感器的时间戳不在同一维度，将用系统时间代替，最后角度精度可能下降");
            str = sb15.toString();
        }
        if ((2048 & j) != 0) {
            StringBuilder sb16 = new StringBuilder();
            sb16.append(str);
            sb16.append("时间戳错误");
            sb16.append("陀螺仪或磁传感器的时间戳不在同一维度，将用系统时间代替，最后角度精度可能下降");
            str = sb16.toString();
        }
        if ((4096 & j) != 0) {
            StringBuilder sb17 = new StringBuilder();
            sb17.append(str);
            sb17.append("时间戳错误");
            str = sb17.toString();
        }
        if ((8192 & j) != 0) {
            StringBuilder sb18 = new StringBuilder();
            sb18.append(str);
            sb18.append("时间戳错误");
            str = sb18.toString();
        }
        if ((j & 16384) == 0) {
            return str;
        }
        StringBuilder sb19 = new StringBuilder();
        sb19.append(str);
        sb19.append("时间戳错误");
        return sb19.toString();
    }
}
