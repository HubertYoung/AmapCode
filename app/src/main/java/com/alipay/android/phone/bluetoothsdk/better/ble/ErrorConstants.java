package com.alipay.android.phone.bluetoothsdk.better.ble;

import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;

public class ErrorConstants {
    public static final String[] BLUETOOTH_STATE_STR = {"蓝牙未打开", "与系统服务的链接暂时丢失", "未授权支付宝使用蓝牙功能", "未知错误"};
    public static final String[] ERROR_BEACON_ALREADY_DISCOVERING = {"11004", "已经开始搜索"};
    public static final String[] ERROR_BEACON_BLUETOOTH_UNAVAILABLE = {"11001", "蓝牙服务不可用"};
    public static final String[] ERROR_BEACON_INVALID_UUID = {"11006", "UUID格式错误"};
    public static final String[] ERROR_BEACON_LACK_PARAMS = {"11005", "缺少参数"};
    public static final String[] ERROR_BEACON_LOCATION_FORBIDDEN = {"11003", "位置权限禁止"};
    public static final String[] ERROR_BEACON_LOCATION_UNAVAILABLE = {"11002", "位置服务不可用"};
    public static final String[] ERROR_BEACON_SYSTEM_ERROR = {"11007", "系统错误"};
    public static final String[] ERROR_BEACON_UNSUPPORT = {"11000", "系统或设备不支持"};
    public static final String[] ERROR_BEACON_UUID_EMPTY = {"11008", "UUID为空"};
    public static final String[] ERROR_BLUETOOTHADAPTER_NOT_INITIALIZED = {H5AppPrepareData.PREPARE_FAIL, "未初始化蓝牙适配器"};
    public static final String[] ERROR_BLUETOOTH_UNAVAILABLE = {"10001", "当前蓝牙适配器不可用"};
    public static final String[] ERROR_CHARACTERISTICID_INVALID = {"10013", "特征 id 不可用"};
    public static final String[] ERROR_CHARACTERISTIC_NOT_FOUND = {"10005", "没有找到指定特征值"};
    public static final String[] ERROR_CHARACTERISTIC_OPERATION_NOT_SUPPORT = {"10007", "当前特征值不支持此操作"};
    public static final String ERROR_CODE_12 = "12";
    public static final String ERROR_CODE_13 = "13";
    public static final String ERROR_CODE_14 = "14";
    public static final String ERROR_CODE_15 = "15";
    public static final String[] ERROR_CODE_ARRAY = {"12", "13", "13", "14", "15"};
    public static final String[] ERROR_CONNECT_FAIL = {H5AppPrepareData.PREPARE_UNZIP_FAIL, "连接失败"};
    public static final String[] ERROR_DESCRIPTOR_NOT_FOUND = {H5AppPrepareData.PREPARE_TIMEOUT, "没有找到指定描述符"};
    public static final String[] ERROR_DEVICEID_INVALID = {H5AppPrepareData.PREPARE_FALLBACK_FAIL, "设备 id 不可用"};
    public static final String[] ERROR_DEVICE_NOT_FOUND = {"10002", "没有找到指定设备"};
    public static final String[] ERROR_HEX_DATA_ERROR = {"10014", "发送的数据为空或格式错误"};
    public static final String[] ERROR_NO_CONNECTION = {"10006", "当前连接已断开"};
    public static final String[] ERROR_PARAM_LACK = {"10016", "缺少参数"};
    public static final String[] ERROR_READ_CHARACTERISTIC_FAIL = {"10018", "读取特征值失败"};
    public static final String[] ERROR_SCAN_INVALID_UUID = {"10020", "UUID格式错误"};
    public static final String[] ERROR_SCAN_LOCATION_UNAVAILABLE = {"10019", "位置权限未开启"};
    public static final String[] ERROR_SERVICEID_INVALID = {"10012", "服务 id 不可用"};
    public static final String[] ERROR_SERVICE_NOT_FOUND = {H5AppPrepareData.PREPARE_RPC_FAIL, "没有找到指定服务"};
    public static final String[] ERROR_SYSTEM_ERROR = {"10008", "系统异常"};
    public static final String[] ERROR_TIMEOUT = {"10015", "操作超时"};
    public static final String[] ERROR_UNSUPPORT_BLE = {H5AppPrepareData.PREPARE_DOWNLOAD_FAIL, "不支持蓝牙4.0"};
    public static final String[] ERROR_WRITE_CHARACTERISTIC_FAIL = {"10017", "写入特征值失败"};
}
