package com.alipay.mobile.common.logging;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.alipay.mobile.common.logging.util.LoggingUtil;

public class DevicePropertyImpl implements DeviceProperty {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private Context g;

    public DevicePropertyImpl(Context context) {
        this.g = context;
    }

    public boolean isXiaomiDevice() {
        if (this.a != null || (!"xiaomi".equals(getBrandName()) && !"xiaomi".equals(getManufacturer()))) {
            return "xiaomi".equals(this.a);
        }
        this.a = "xiaomi";
        return true;
    }

    public boolean isVivoDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_VIVO.equals(getBrandName()) && !DeviceProperty.ALIAS_VIVO.equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_VIVO.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_VIVO;
        return true;
    }

    public boolean isOppoDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_OPPO.equals(getBrandName()) && !DeviceProperty.ALIAS_OPPO.equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_OPPO.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_OPPO;
        return true;
    }

    public boolean isHuaweiDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_HUAWEI.equals(getBrandName()) && !DeviceProperty.ALIAS_HUAWEI.equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_HUAWEI.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_HUAWEI;
        return true;
    }

    public boolean isLeEcoDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_LEECO.equals(getBrandName()) && !"lemobile".equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_LEECO.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_LEECO;
        return true;
    }

    public boolean isQikuDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_QIKU.equals(getBrandName()) && !DeviceProperty.ALIAS_QIKU.equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_QIKU.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_QIKU;
        return true;
    }

    public boolean isZteDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_ZTE.equals(getBrandName()) && !DeviceProperty.ALIAS_ZTE.equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_ZTE.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_ZTE;
        return true;
    }

    public boolean isOnePlusDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_ONEPLUS.equals(getBrandName()) && !DeviceProperty.ALIAS_ONEPLUS.equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_ONEPLUS.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_ONEPLUS;
        return true;
    }

    public boolean isNubiaDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_NUBIA.equals(getBrandName()) && !DeviceProperty.ALIAS_NUBIA.equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_NUBIA.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_NUBIA;
        return true;
    }

    public boolean isCoolpadDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_COOLPAD.equals(getBrandName()) && !"yulong".equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_COOLPAD.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_COOLPAD;
        return true;
    }

    public boolean isLenovoDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_LENOVO.equals(getBrandName()) && !DeviceProperty.ALIAS_LENOVO.equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_LENOVO.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_LENOVO;
        return true;
    }

    public boolean isMeizuDevice() {
        if (this.a != null || (!DeviceProperty.ALIAS_MEIZU.equals(getBrandName()) && !DeviceProperty.ALIAS_MEIZU.equals(getManufacturer()))) {
            return DeviceProperty.ALIAS_MEIZU.equals(this.a);
        }
        this.a = DeviceProperty.ALIAS_MEIZU;
        return true;
    }

    public boolean isSamsungDevice() {
        if (this.a != null || (!"samsung".equals(getBrandName()) && !"samsung".equals(getManufacturer()))) {
            return "samsung".equals(this.a);
        }
        this.a = "samsung";
        return true;
    }

    public String getDeviceAlias() {
        if (this.a == null && !isXiaomiDevice() && !isVivoDevice() && !isOppoDevice() && !isHuaweiDevice() && !isLeEcoDevice() && !isQikuDevice() && !isZteDevice() && !isOnePlusDevice() && !isNubiaDevice() && !isCoolpadDevice() && !isLenovoDevice() && !isMeizuDevice() && !isSamsungDevice()) {
            this.a = "unknown";
        }
        return this.a;
    }

    public String getRomVersion() {
        if (this.b == null) {
            if (isXiaomiDevice()) {
                this.b = LoggingUtil.getSystemProperty("ro.build.version.incremental", "");
            } else if (isVivoDevice()) {
                this.b = LoggingUtil.getSystemProperty("ro.vivo.os.build.display.id", "");
            } else if (isOppoDevice()) {
                this.b = LoggingUtil.getSystemProperty("ro.build.version.opporom", "");
            } else if (isHuaweiDevice()) {
                this.b = LoggingUtil.getSystemProperty("ro.build.version.emui", "");
            } else if (isLeEcoDevice()) {
                this.b = LoggingUtil.getSystemProperty("ro.letv.release.version", "");
            } else if (isQikuDevice()) {
                this.b = LoggingUtil.getSystemProperty("ro.build.uiversion", "");
            } else if (isZteDevice()) {
                this.b = LoggingUtil.getSystemProperty("ro.build.MiFavor_version", "");
            } else if (isOnePlusDevice()) {
                this.b = LoggingUtil.getSystemProperty("ro.rom.version", "");
            } else if (isNubiaDevice()) {
                this.b = LoggingUtil.getSystemProperty("ro.build.rom.id", "");
            } else if (!isCoolpadDevice() && !isLenovoDevice() && !isMeizuDevice()) {
                isSamsungDevice();
            }
            if (TextUtils.isEmpty(this.b)) {
                this.b = getDisplayID();
                if (TextUtils.isEmpty(this.b)) {
                    this.b = "unknown";
                }
            }
            this.b = this.b.toLowerCase();
        }
        return this.b;
    }

    public String getManufacturer() {
        if (this.c == null) {
            try {
                this.c = Build.MANUFACTURER.toLowerCase();
            } catch (Throwable th) {
            }
            if (TextUtils.isEmpty(this.c)) {
                this.c = "unknown";
            }
        }
        return this.c;
    }

    public String getBrandName() {
        if (this.d == null) {
            try {
                this.d = Build.BRAND.toLowerCase();
            } catch (Throwable th) {
            }
            if (TextUtils.isEmpty(this.d)) {
                this.d = "unknown";
            }
        }
        return this.d;
    }

    public String getDisplayID() {
        if (this.e == null) {
            try {
                this.e = Build.DISPLAY.toLowerCase();
            } catch (Throwable th) {
            }
            if (TextUtils.isEmpty(this.e)) {
                this.e = "unknown";
            }
        }
        return this.e;
    }

    public String getFingerPrint() {
        if (this.f == null) {
            try {
                this.f = Build.FINGERPRINT.toLowerCase();
            } catch (Throwable th) {
            }
            if (TextUtils.isEmpty(this.f)) {
                this.f = "unknown";
            }
        }
        return this.f;
    }
}
