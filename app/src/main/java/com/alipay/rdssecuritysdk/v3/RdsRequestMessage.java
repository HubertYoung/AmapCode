package com.alipay.rdssecuritysdk.v3;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class RdsRequestMessage extends Message {
    public static final String DEFAULT_EXTRA1 = "";
    public static final String DEFAULT_EXTRA2 = "";
    public static final String DEFAULT_EXTRA3 = "";
    public static final String DEFAULT_EXTRA4 = "";
    public static final String DEFAULT_EXTRA5 = "";
    public static final int TAG_EXTRA1 = 4;
    public static final int TAG_EXTRA2 = 5;
    public static final int TAG_EXTRA3 = 6;
    public static final int TAG_EXTRA4 = 7;
    public static final int TAG_EXTRA5 = 8;
    public static final int TAG_NATIVE = 1;
    public static final int TAG_SDK = 2;
    public static final int TAG_TAOBAO = 3;
    @ProtoField(tag = 1)
    public Native _native;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String extra1;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String extra2;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String extra3;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String extra4;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String extra5;
    @ProtoField(tag = 2)
    public Sdk sdk;
    @ProtoField(tag = 3)
    public Taobao taobao;

    public static final class Native extends Message {
        public static final int TAG_ENV = 1;
        @ProtoField(tag = 1)
        public Env env;

        public static final class Env extends Message {
            public static final String DEFAULT_BINARYHASH = "";
            public static final Boolean DEFAULT_EM = Boolean.FALSE;
            public static final Boolean DEFAULT_INS = Boolean.FALSE;
            public static final Boolean DEFAULT_INSEX = Boolean.FALSE;
            public static final List<String> DEFAULT_MAL = Collections.emptyList();
            public static final Boolean DEFAULT_REP = Boolean.FALSE;
            public static final List<String> DEFAULT_REPIE = Collections.emptyList();
            public static final String DEFAULT_REPIEHASH = "";
            public static final Boolean DEFAULT_ROOT = Boolean.FALSE;
            public static final String DEFAULT_SAFE = "";
            public static final String DEFAULT_SIGN = "";
            public static final String DEFAULT_SIGNHASH = "";
            public static final int TAG_BINARYHASH = 7;
            public static final int TAG_EM = 1;
            public static final int TAG_INS = 2;
            public static final int TAG_INSEX = 3;
            public static final int TAG_MAL = 4;
            public static final int TAG_REP = 5;
            public static final int TAG_REPIE = 12;
            public static final int TAG_REPIEHASH = 8;
            public static final int TAG_ROOT = 6;
            public static final int TAG_SAFE = 9;
            public static final int TAG_SIGN = 10;
            public static final int TAG_SIGNHASH = 11;
            @ProtoField(tag = 7, type = Datatype.STRING)
            public String binaryhash;
            @ProtoField(tag = 1, type = Datatype.BOOL)
            public Boolean em;
            @ProtoField(tag = 2, type = Datatype.BOOL)
            public Boolean ins;
            @ProtoField(tag = 3, type = Datatype.BOOL)
            public Boolean insEx;
            @ProtoField(label = Label.REPEATED, tag = 4, type = Datatype.STRING)
            public List<String> mal;
            @ProtoField(tag = 5, type = Datatype.BOOL)
            public Boolean rep;
            @ProtoField(label = Label.REPEATED, tag = 12, type = Datatype.STRING)
            public List<String> repie;
            @ProtoField(tag = 8, type = Datatype.STRING)
            public String repiehash;
            @ProtoField(tag = 6, type = Datatype.BOOL)
            public Boolean root;
            @ProtoField(tag = 9, type = Datatype.STRING)
            public String safe;
            @ProtoField(tag = 10, type = Datatype.STRING)
            public String sign;
            @ProtoField(tag = 11, type = Datatype.STRING)
            public String signhash;

            public Env(Env env) {
                super(env);
                if (env != null) {
                    this.em = env.em;
                    this.ins = env.ins;
                    this.insEx = env.insEx;
                    this.mal = copyOf(env.mal);
                    this.rep = env.rep;
                    this.root = env.root;
                    this.binaryhash = env.binaryhash;
                    this.repiehash = env.repiehash;
                    this.safe = env.safe;
                    this.sign = env.sign;
                    this.signhash = env.signhash;
                    this.repie = copyOf(env.repie);
                }
            }

            public Env() {
            }

            public final Env fillTagValue(int i, Object obj) {
                switch (i) {
                    case 1:
                        this.em = (Boolean) obj;
                        break;
                    case 2:
                        this.ins = (Boolean) obj;
                        break;
                    case 3:
                        this.insEx = (Boolean) obj;
                        break;
                    case 4:
                        this.mal = immutableCopyOf((List) obj);
                        break;
                    case 5:
                        this.rep = (Boolean) obj;
                        break;
                    case 6:
                        this.root = (Boolean) obj;
                        break;
                    case 7:
                        this.binaryhash = (String) obj;
                        break;
                    case 8:
                        this.repiehash = (String) obj;
                        break;
                    case 9:
                        this.safe = (String) obj;
                        break;
                    case 10:
                        this.sign = (String) obj;
                        break;
                    case 11:
                        this.signhash = (String) obj;
                        break;
                    case 12:
                        this.repie = immutableCopyOf((List) obj);
                        break;
                }
                return this;
            }

            public final boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Env)) {
                    return false;
                }
                Env env = (Env) obj;
                return equals((Object) this.em, (Object) env.em) && equals((Object) this.ins, (Object) env.ins) && equals((Object) this.insEx, (Object) env.insEx) && equals(this.mal, env.mal) && equals((Object) this.rep, (Object) env.rep) && equals((Object) this.root, (Object) env.root) && equals((Object) this.binaryhash, (Object) env.binaryhash) && equals((Object) this.repiehash, (Object) env.repiehash) && equals((Object) this.safe, (Object) env.safe) && equals((Object) this.sign, (Object) env.sign) && equals((Object) this.signhash, (Object) env.signhash) && equals(this.repie, env.repie);
            }

            public final int hashCode() {
                int i = this.hashCode;
                if (i != 0) {
                    return i;
                }
                int i2 = 0;
                int i3 = 1;
                int hashCode = (((((((((((((((((((this.em != null ? this.em.hashCode() : 0) * 37) + (this.ins != null ? this.ins.hashCode() : 0)) * 37) + (this.insEx != null ? this.insEx.hashCode() : 0)) * 37) + (this.mal != null ? this.mal.hashCode() : 1)) * 37) + (this.rep != null ? this.rep.hashCode() : 0)) * 37) + (this.root != null ? this.root.hashCode() : 0)) * 37) + (this.binaryhash != null ? this.binaryhash.hashCode() : 0)) * 37) + (this.repiehash != null ? this.repiehash.hashCode() : 0)) * 37) + (this.safe != null ? this.safe.hashCode() : 0)) * 37) + (this.sign != null ? this.sign.hashCode() : 0)) * 37;
                if (this.signhash != null) {
                    i2 = this.signhash.hashCode();
                }
                int i4 = (hashCode + i2) * 37;
                if (this.repie != null) {
                    i3 = this.repie.hashCode();
                }
                int i5 = i4 + i3;
                this.hashCode = i5;
                return i5;
            }
        }

        public Native(Native nativeR) {
            super(nativeR);
            if (nativeR != null) {
                this.env = nativeR.env;
            }
        }

        public Native() {
        }

        public final Native fillTagValue(int i, Object obj) {
            if (i == 1) {
                this.env = (Env) obj;
            }
            return this;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Native)) {
                return false;
            }
            return equals((Object) this.env, (Object) ((Native) obj).env);
        }

        public final int hashCode() {
            int i = this.hashCode;
            if (i != 0) {
                return i;
            }
            int hashCode = this.env != null ? this.env.hashCode() : 0;
            this.hashCode = hashCode;
            return hashCode;
        }
    }

    public static final class Sdk extends Message {
        public static final int TAG_DEV = 1;
        public static final int TAG_ENV = 2;
        public static final int TAG_LOC = 3;
        public static final int TAG_USR = 4;
        @ProtoField(tag = 1)
        public Dev dev;
        @ProtoField(tag = 2)
        public Env env;
        @ProtoField(tag = 3)
        public Loc loc;
        @ProtoField(tag = 4)
        public Usr usr;

        public static final class Dev extends Message {
            public static final String DEFAULT_APDID = "";
            public static final String DEFAULT_GSS = "";
            public static final String DEFAULT_GSS2 = "";
            public static final String DEFAULT_H = "";
            public static final String DEFAULT_IDFA = "";
            public static final String DEFAULT_IMEI = "";
            public static final String DEFAULT_IMSI = "";
            public static final String DEFAULT_MAC = "";
            public static final String DEFAULT_PX = "";
            public static final String DEFAULT_TID = "";
            public static final String DEFAULT_UMID = "";
            public static final String DEFAULT_USB = "";
            public static final String DEFAULT_UTDID = "";
            public static final String DEFAULT_W = "";
            public static final String DEFAULT_WI = "";
            public static final int TAG_APDID = 1;
            public static final int TAG_GSS = 2;
            public static final int TAG_GSS2 = 3;
            public static final int TAG_H = 4;
            public static final int TAG_IDFA = 5;
            public static final int TAG_IMEI = 6;
            public static final int TAG_IMSI = 7;
            public static final int TAG_MAC = 8;
            public static final int TAG_PX = 9;
            public static final int TAG_SENSOR = 10;
            public static final int TAG_TID = 11;
            public static final int TAG_UMID = 12;
            public static final int TAG_USB = 13;
            public static final int TAG_UTDID = 14;
            public static final int TAG_W = 15;
            public static final int TAG_WI = 16;
            @ProtoField(tag = 1, type = Datatype.STRING)
            public String apdid;
            @ProtoField(tag = 2, type = Datatype.STRING)
            public String gss;
            @ProtoField(tag = 3, type = Datatype.STRING)
            public String gss2;
            @ProtoField(tag = 4, type = Datatype.STRING)
            public String h;
            @ProtoField(tag = 5, type = Datatype.STRING)
            public String idfa;
            @ProtoField(tag = 6, type = Datatype.STRING)
            public String imei;
            @ProtoField(tag = 7, type = Datatype.STRING)
            public String imsi;
            @ProtoField(tag = 8, type = Datatype.STRING)
            public String mac;
            @ProtoField(tag = 9, type = Datatype.STRING)
            public String px;
            @ProtoField(tag = 10)
            public Sensor sensor;
            @ProtoField(tag = 11, type = Datatype.STRING)
            public String tid;
            @ProtoField(tag = 12, type = Datatype.STRING)
            public String umid;
            @ProtoField(tag = 13, type = Datatype.STRING)
            public String usb;
            @ProtoField(tag = 14, type = Datatype.STRING)
            public String utdid;
            @ProtoField(tag = 15, type = Datatype.STRING)
            public String w;
            @ProtoField(tag = 16, type = Datatype.STRING)
            public String wi;

            public static final class Sensor extends Message {
                public static final Long DEFAULT_T = Long.valueOf(0);
                public static final int TAG_DATA = 1;
                public static final int TAG_T = 2;
                @ProtoField(tag = 1)
                public Data data;
                @ProtoField(tag = 2, type = Datatype.INT64)
                public Long t;

                public static final class Data extends Message {
                    public static final List<String> DEFAULT_ACCELEROMETER = Collections.emptyList();
                    public static final List<String> DEFAULT_GRAVITY = Collections.emptyList();
                    public static final List<String> DEFAULT_GYROSCOPE = Collections.emptyList();
                    public static final List<String> DEFAULT_MAGNETOMETER = Collections.emptyList();
                    public static final int TAG_ACCELEROMETER = 1;
                    public static final int TAG_GRAVITY = 2;
                    public static final int TAG_GYROSCOPE = 3;
                    public static final int TAG_MAGNETOMETER = 4;
                    @ProtoField(label = Label.REPEATED, tag = 1, type = Datatype.STRING)
                    public List<String> Accelerometer;
                    @ProtoField(label = Label.REPEATED, tag = 2, type = Datatype.STRING)
                    public List<String> Gravity;
                    @ProtoField(label = Label.REPEATED, tag = 3, type = Datatype.STRING)
                    public List<String> Gyroscope;
                    @ProtoField(label = Label.REPEATED, tag = 4, type = Datatype.STRING)
                    public List<String> Magnetometer;

                    public Data(Data data) {
                        super(data);
                        if (data != null) {
                            this.Accelerometer = copyOf(data.Accelerometer);
                            this.Gravity = copyOf(data.Gravity);
                            this.Gyroscope = copyOf(data.Gyroscope);
                            this.Magnetometer = copyOf(data.Magnetometer);
                        }
                    }

                    public Data() {
                    }

                    public final Data fillTagValue(int i, Object obj) {
                        switch (i) {
                            case 1:
                                this.Accelerometer = immutableCopyOf((List) obj);
                                break;
                            case 2:
                                this.Gravity = immutableCopyOf((List) obj);
                                break;
                            case 3:
                                this.Gyroscope = immutableCopyOf((List) obj);
                                break;
                            case 4:
                                this.Magnetometer = immutableCopyOf((List) obj);
                                break;
                        }
                        return this;
                    }

                    public final boolean equals(Object obj) {
                        if (obj == this) {
                            return true;
                        }
                        if (!(obj instanceof Data)) {
                            return false;
                        }
                        Data data = (Data) obj;
                        return equals(this.Accelerometer, data.Accelerometer) && equals(this.Gravity, data.Gravity) && equals(this.Gyroscope, data.Gyroscope) && equals(this.Magnetometer, data.Magnetometer);
                    }

                    public final int hashCode() {
                        int i = this.hashCode;
                        if (i != 0) {
                            return i;
                        }
                        int i2 = 1;
                        int hashCode = (((((this.Accelerometer != null ? this.Accelerometer.hashCode() : 1) * 37) + (this.Gravity != null ? this.Gravity.hashCode() : 1)) * 37) + (this.Gyroscope != null ? this.Gyroscope.hashCode() : 1)) * 37;
                        if (this.Magnetometer != null) {
                            i2 = this.Magnetometer.hashCode();
                        }
                        int i3 = hashCode + i2;
                        this.hashCode = i3;
                        return i3;
                    }
                }

                public Sensor(Sensor sensor) {
                    super(sensor);
                    if (sensor != null) {
                        this.data = sensor.data;
                        this.t = sensor.t;
                    }
                }

                public Sensor() {
                }

                public final Sensor fillTagValue(int i, Object obj) {
                    switch (i) {
                        case 1:
                            this.data = (Data) obj;
                            break;
                        case 2:
                            this.t = (Long) obj;
                            break;
                    }
                    return this;
                }

                public final boolean equals(Object obj) {
                    if (obj == this) {
                        return true;
                    }
                    if (!(obj instanceof Sensor)) {
                        return false;
                    }
                    Sensor sensor = (Sensor) obj;
                    return equals((Object) this.data, (Object) sensor.data) && equals((Object) this.t, (Object) sensor.t);
                }

                public final int hashCode() {
                    int i = this.hashCode;
                    if (i != 0) {
                        return i;
                    }
                    int i2 = 0;
                    int hashCode = (this.data != null ? this.data.hashCode() : 0) * 37;
                    if (this.t != null) {
                        i2 = this.t.hashCode();
                    }
                    int i3 = hashCode + i2;
                    this.hashCode = i3;
                    return i3;
                }
            }

            public Dev(Dev dev) {
                super(dev);
                if (dev != null) {
                    this.apdid = dev.apdid;
                    this.gss = dev.gss;
                    this.gss2 = dev.gss2;
                    this.h = dev.h;
                    this.idfa = dev.idfa;
                    this.imei = dev.imei;
                    this.imsi = dev.imsi;
                    this.mac = dev.mac;
                    this.px = dev.px;
                    this.sensor = dev.sensor;
                    this.tid = dev.tid;
                    this.umid = dev.umid;
                    this.usb = dev.usb;
                    this.utdid = dev.utdid;
                    this.w = dev.w;
                    this.wi = dev.wi;
                }
            }

            public Dev() {
            }

            public final Dev fillTagValue(int i, Object obj) {
                switch (i) {
                    case 1:
                        this.apdid = (String) obj;
                        break;
                    case 2:
                        this.gss = (String) obj;
                        break;
                    case 3:
                        this.gss2 = (String) obj;
                        break;
                    case 4:
                        this.h = (String) obj;
                        break;
                    case 5:
                        this.idfa = (String) obj;
                        break;
                    case 6:
                        this.imei = (String) obj;
                        break;
                    case 7:
                        this.imsi = (String) obj;
                        break;
                    case 8:
                        this.mac = (String) obj;
                        break;
                    case 9:
                        this.px = (String) obj;
                        break;
                    case 10:
                        this.sensor = (Sensor) obj;
                        break;
                    case 11:
                        this.tid = (String) obj;
                        break;
                    case 12:
                        this.umid = (String) obj;
                        break;
                    case 13:
                        this.usb = (String) obj;
                        break;
                    case 14:
                        this.utdid = (String) obj;
                        break;
                    case 15:
                        this.w = (String) obj;
                        break;
                    case 16:
                        this.wi = (String) obj;
                        break;
                }
                return this;
            }

            public final boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Dev)) {
                    return false;
                }
                Dev dev = (Dev) obj;
                return equals((Object) this.apdid, (Object) dev.apdid) && equals((Object) this.gss, (Object) dev.gss) && equals((Object) this.gss2, (Object) dev.gss2) && equals((Object) this.h, (Object) dev.h) && equals((Object) this.idfa, (Object) dev.idfa) && equals((Object) this.imei, (Object) dev.imei) && equals((Object) this.imsi, (Object) dev.imsi) && equals((Object) this.mac, (Object) dev.mac) && equals((Object) this.px, (Object) dev.px) && equals((Object) this.sensor, (Object) dev.sensor) && equals((Object) this.tid, (Object) dev.tid) && equals((Object) this.umid, (Object) dev.umid) && equals((Object) this.usb, (Object) dev.usb) && equals((Object) this.utdid, (Object) dev.utdid) && equals((Object) this.w, (Object) dev.w) && equals((Object) this.wi, (Object) dev.wi);
            }

            public final int hashCode() {
                int i = this.hashCode;
                if (i != 0) {
                    return i;
                }
                int i2 = 0;
                int hashCode = (((((((((((((((((((((((((((((this.apdid != null ? this.apdid.hashCode() : 0) * 37) + (this.gss != null ? this.gss.hashCode() : 0)) * 37) + (this.gss2 != null ? this.gss2.hashCode() : 0)) * 37) + (this.h != null ? this.h.hashCode() : 0)) * 37) + (this.idfa != null ? this.idfa.hashCode() : 0)) * 37) + (this.imei != null ? this.imei.hashCode() : 0)) * 37) + (this.imsi != null ? this.imsi.hashCode() : 0)) * 37) + (this.mac != null ? this.mac.hashCode() : 0)) * 37) + (this.px != null ? this.px.hashCode() : 0)) * 37) + (this.sensor != null ? this.sensor.hashCode() : 0)) * 37) + (this.tid != null ? this.tid.hashCode() : 0)) * 37) + (this.umid != null ? this.umid.hashCode() : 0)) * 37) + (this.usb != null ? this.usb.hashCode() : 0)) * 37) + (this.utdid != null ? this.utdid.hashCode() : 0)) * 37) + (this.w != null ? this.w.hashCode() : 0)) * 37;
                if (this.wi != null) {
                    i2 = this.wi.hashCode();
                }
                int i3 = hashCode + i2;
                this.hashCode = i3;
                return i3;
            }
        }

        public static final class Env extends Message {
            public static final String DEFAULT_ASDK = "";
            public static final String DEFAULT_BOARD = "";
            public static final String DEFAULT_BRAND = "";
            public static final String DEFAULT_DEVICE = "";
            public static final String DEFAULT_DISPLAYID = "";
            public static final Boolean DEFAULT_EM = Boolean.FALSE;
            public static final String DEFAULT_INCREMENTAL = "";
            public static final String DEFAULT_KERVER = "";
            public static final String DEFAULT_MANUFACTURER = "";
            public static final String DEFAULT_MODEL = "";
            public static final String DEFAULT_NAME = "";
            public static final String DEFAULT_OS = "";
            public static final String DEFAULT_OSRELEASE = "";
            public static final String DEFAULT_PF = "";
            public static final String DEFAULT_PM = "";
            public static final String DEFAULT_PN = "";
            public static final String DEFAULT_PROCESSOR = "";
            public static final String DEFAULT_QEMU = "";
            public static final Boolean DEFAULT_ROOT = Boolean.FALSE;
            public static final String DEFAULT_TAGS = "";
            public static final int TAG_ASDK = 1;
            public static final int TAG_BOARD = 2;
            public static final int TAG_BRAND = 3;
            public static final int TAG_DEVICE = 20;
            public static final int TAG_DISPLAYID = 4;
            public static final int TAG_EM = 5;
            public static final int TAG_INCREMENTAL = 6;
            public static final int TAG_KERVER = 7;
            public static final int TAG_MANUFACTURER = 8;
            public static final int TAG_MODEL = 9;
            public static final int TAG_NAME = 10;
            public static final int TAG_OS = 11;
            public static final int TAG_OSRELEASE = 17;
            public static final int TAG_PF = 12;
            public static final int TAG_PM = 13;
            public static final int TAG_PN = 14;
            public static final int TAG_PROCESSOR = 15;
            public static final int TAG_QEMU = 16;
            public static final int TAG_ROOT = 18;
            public static final int TAG_TAGS = 19;
            @ProtoField(tag = 1, type = Datatype.STRING)
            public String asdk;
            @ProtoField(tag = 2, type = Datatype.STRING)
            public String board;
            @ProtoField(tag = 3, type = Datatype.STRING)
            public String brand;
            @ProtoField(tag = 20, type = Datatype.STRING)
            public String device;
            @ProtoField(tag = 4, type = Datatype.STRING)
            public String displayid;
            @ProtoField(tag = 5, type = Datatype.BOOL)
            public Boolean em;
            @ProtoField(tag = 6, type = Datatype.STRING)
            public String incremental;
            @ProtoField(tag = 7, type = Datatype.STRING)
            public String kerver;
            @ProtoField(tag = 8, type = Datatype.STRING)
            public String manufacturer;
            @ProtoField(tag = 9, type = Datatype.STRING)
            public String model;
            @ProtoField(tag = 10, type = Datatype.STRING)
            public String name;
            @ProtoField(tag = 11, type = Datatype.STRING)
            public String os;
            @ProtoField(tag = 17, type = Datatype.STRING)
            public String osRelease;
            @ProtoField(tag = 12, type = Datatype.STRING)
            public String pf;
            @ProtoField(tag = 13, type = Datatype.STRING)
            public String pm;
            @ProtoField(tag = 14, type = Datatype.STRING)
            public String pn;
            @ProtoField(tag = 15, type = Datatype.STRING)
            public String processor;
            @ProtoField(tag = 16, type = Datatype.STRING)
            public String qemu;
            @ProtoField(tag = 18, type = Datatype.BOOL)
            public Boolean root;
            @ProtoField(tag = 19, type = Datatype.STRING)
            public String tags;

            public Env(Env env) {
                super(env);
                if (env != null) {
                    this.asdk = env.asdk;
                    this.board = env.board;
                    this.brand = env.brand;
                    this.displayid = env.displayid;
                    this.em = env.em;
                    this.incremental = env.incremental;
                    this.kerver = env.kerver;
                    this.manufacturer = env.manufacturer;
                    this.model = env.model;
                    this.name = env.name;
                    this.os = env.os;
                    this.pf = env.pf;
                    this.pm = env.pm;
                    this.pn = env.pn;
                    this.processor = env.processor;
                    this.qemu = env.qemu;
                    this.osRelease = env.osRelease;
                    this.root = env.root;
                    this.tags = env.tags;
                    this.device = env.device;
                }
            }

            public Env() {
            }

            public final Env fillTagValue(int i, Object obj) {
                switch (i) {
                    case 1:
                        this.asdk = (String) obj;
                        break;
                    case 2:
                        this.board = (String) obj;
                        break;
                    case 3:
                        this.brand = (String) obj;
                        break;
                    case 4:
                        this.displayid = (String) obj;
                        break;
                    case 5:
                        this.em = (Boolean) obj;
                        break;
                    case 6:
                        this.incremental = (String) obj;
                        break;
                    case 7:
                        this.kerver = (String) obj;
                        break;
                    case 8:
                        this.manufacturer = (String) obj;
                        break;
                    case 9:
                        this.model = (String) obj;
                        break;
                    case 10:
                        this.name = (String) obj;
                        break;
                    case 11:
                        this.os = (String) obj;
                        break;
                    case 12:
                        this.pf = (String) obj;
                        break;
                    case 13:
                        this.pm = (String) obj;
                        break;
                    case 14:
                        this.pn = (String) obj;
                        break;
                    case 15:
                        this.processor = (String) obj;
                        break;
                    case 16:
                        this.qemu = (String) obj;
                        break;
                    case 17:
                        this.osRelease = (String) obj;
                        break;
                    case 18:
                        this.root = (Boolean) obj;
                        break;
                    case 19:
                        this.tags = (String) obj;
                        break;
                    case 20:
                        this.device = (String) obj;
                        break;
                }
                return this;
            }

            public final boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Env)) {
                    return false;
                }
                Env env = (Env) obj;
                return equals((Object) this.asdk, (Object) env.asdk) && equals((Object) this.board, (Object) env.board) && equals((Object) this.brand, (Object) env.brand) && equals((Object) this.displayid, (Object) env.displayid) && equals((Object) this.em, (Object) env.em) && equals((Object) this.incremental, (Object) env.incremental) && equals((Object) this.kerver, (Object) env.kerver) && equals((Object) this.manufacturer, (Object) env.manufacturer) && equals((Object) this.model, (Object) env.model) && equals((Object) this.name, (Object) env.name) && equals((Object) this.os, (Object) env.os) && equals((Object) this.pf, (Object) env.pf) && equals((Object) this.pm, (Object) env.pm) && equals((Object) this.pn, (Object) env.pn) && equals((Object) this.processor, (Object) env.processor) && equals((Object) this.qemu, (Object) env.qemu) && equals((Object) this.osRelease, (Object) env.osRelease) && equals((Object) this.root, (Object) env.root) && equals((Object) this.tags, (Object) env.tags) && equals((Object) this.device, (Object) env.device);
            }

            public final int hashCode() {
                int i = this.hashCode;
                if (i != 0) {
                    return i;
                }
                int i2 = 0;
                int hashCode = (((((((((((((((((((((((((((((((((((((this.asdk != null ? this.asdk.hashCode() : 0) * 37) + (this.board != null ? this.board.hashCode() : 0)) * 37) + (this.brand != null ? this.brand.hashCode() : 0)) * 37) + (this.displayid != null ? this.displayid.hashCode() : 0)) * 37) + (this.em != null ? this.em.hashCode() : 0)) * 37) + (this.incremental != null ? this.incremental.hashCode() : 0)) * 37) + (this.kerver != null ? this.kerver.hashCode() : 0)) * 37) + (this.manufacturer != null ? this.manufacturer.hashCode() : 0)) * 37) + (this.model != null ? this.model.hashCode() : 0)) * 37) + (this.name != null ? this.name.hashCode() : 0)) * 37) + (this.os != null ? this.os.hashCode() : 0)) * 37) + (this.pf != null ? this.pf.hashCode() : 0)) * 37) + (this.pm != null ? this.pm.hashCode() : 0)) * 37) + (this.pn != null ? this.pn.hashCode() : 0)) * 37) + (this.processor != null ? this.processor.hashCode() : 0)) * 37) + (this.qemu != null ? this.qemu.hashCode() : 0)) * 37) + (this.osRelease != null ? this.osRelease.hashCode() : 0)) * 37) + (this.root != null ? this.root.hashCode() : 0)) * 37) + (this.tags != null ? this.tags.hashCode() : 0)) * 37;
                if (this.device != null) {
                    i2 = this.device.hashCode();
                }
                int i3 = hashCode + i2;
                this.hashCode = i3;
                return i3;
            }
        }

        public static final class Loc extends Message {
            public static final String DEFAULT_ACC = "";
            public static final Boolean DEFAULT_ACTIVE = Boolean.FALSE;
            public static final String DEFAULT_BSSID = "";
            public static final String DEFAULT_CARRIER = "";
            public static final String DEFAULT_CID = "";
            public static final String DEFAULT_LA = "";
            public static final String DEFAULT_LAC = "";
            public static final String DEFAULT_LO = "";
            public static final String DEFAULT_MCC = "";
            public static final String DEFAULT_MNC = "";
            public static final String DEFAULT_NETTPYE = "";
            public static final List<String> DEFAULT_OMAC = Collections.emptyList();
            public static final String DEFAULT_SSID = "";
            public static final String DEFAULT_STRENGTH = "";
            public static final Long DEFAULT_T = Long.valueOf(0);
            public static final int TAG_ACC = 1;
            public static final int TAG_ACTIVE = 2;
            public static final int TAG_BSSID = 3;
            public static final int TAG_CARRIER = 4;
            public static final int TAG_CID = 5;
            public static final int TAG_LA = 6;
            public static final int TAG_LAC = 7;
            public static final int TAG_LO = 8;
            public static final int TAG_MCC = 9;
            public static final int TAG_MNC = 10;
            public static final int TAG_NETTPYE = 11;
            public static final int TAG_OMAC = 12;
            public static final int TAG_SSID = 13;
            public static final int TAG_STRENGTH = 14;
            public static final int TAG_T = 15;
            @ProtoField(tag = 1, type = Datatype.STRING)
            public String acc;
            @ProtoField(tag = 2, type = Datatype.BOOL)
            public Boolean active;
            @ProtoField(tag = 3, type = Datatype.STRING)
            public String bssid;
            @ProtoField(tag = 4, type = Datatype.STRING)
            public String carrier;
            @ProtoField(tag = 5, type = Datatype.STRING)
            public String cid;
            @ProtoField(tag = 6, type = Datatype.STRING)
            public String la;
            @ProtoField(tag = 7, type = Datatype.STRING)
            public String lac;
            @ProtoField(tag = 8, type = Datatype.STRING)
            public String lo;
            @ProtoField(tag = 9, type = Datatype.STRING)
            public String mcc;
            @ProtoField(tag = 10, type = Datatype.STRING)
            public String mnc;
            @ProtoField(tag = 11, type = Datatype.STRING)
            public String nettpye;
            @ProtoField(label = Label.REPEATED, tag = 12, type = Datatype.STRING)
            public List<String> omac;
            @ProtoField(tag = 13, type = Datatype.STRING)
            public String ssid;
            @ProtoField(tag = 14, type = Datatype.STRING)
            public String strength;
            @ProtoField(tag = 15, type = Datatype.INT64)
            public Long t;

            public Loc(Loc loc) {
                super(loc);
                if (loc != null) {
                    this.acc = loc.acc;
                    this.active = loc.active;
                    this.bssid = loc.bssid;
                    this.carrier = loc.carrier;
                    this.cid = loc.cid;
                    this.la = loc.la;
                    this.lac = loc.lac;
                    this.lo = loc.lo;
                    this.mcc = loc.mcc;
                    this.mnc = loc.mnc;
                    this.nettpye = loc.nettpye;
                    this.omac = copyOf(loc.omac);
                    this.ssid = loc.ssid;
                    this.strength = loc.strength;
                    this.t = loc.t;
                }
            }

            public Loc() {
            }

            public final Loc fillTagValue(int i, Object obj) {
                switch (i) {
                    case 1:
                        this.acc = (String) obj;
                        break;
                    case 2:
                        this.active = (Boolean) obj;
                        break;
                    case 3:
                        this.bssid = (String) obj;
                        break;
                    case 4:
                        this.carrier = (String) obj;
                        break;
                    case 5:
                        this.cid = (String) obj;
                        break;
                    case 6:
                        this.la = (String) obj;
                        break;
                    case 7:
                        this.lac = (String) obj;
                        break;
                    case 8:
                        this.lo = (String) obj;
                        break;
                    case 9:
                        this.mcc = (String) obj;
                        break;
                    case 10:
                        this.mnc = (String) obj;
                        break;
                    case 11:
                        this.nettpye = (String) obj;
                        break;
                    case 12:
                        this.omac = immutableCopyOf((List) obj);
                        break;
                    case 13:
                        this.ssid = (String) obj;
                        break;
                    case 14:
                        this.strength = (String) obj;
                        break;
                    case 15:
                        this.t = (Long) obj;
                        break;
                }
                return this;
            }

            public final boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Loc)) {
                    return false;
                }
                Loc loc = (Loc) obj;
                return equals((Object) this.acc, (Object) loc.acc) && equals((Object) this.active, (Object) loc.active) && equals((Object) this.bssid, (Object) loc.bssid) && equals((Object) this.carrier, (Object) loc.carrier) && equals((Object) this.cid, (Object) loc.cid) && equals((Object) this.la, (Object) loc.la) && equals((Object) this.lac, (Object) loc.lac) && equals((Object) this.lo, (Object) loc.lo) && equals((Object) this.mcc, (Object) loc.mcc) && equals((Object) this.mnc, (Object) loc.mnc) && equals((Object) this.nettpye, (Object) loc.nettpye) && equals(this.omac, loc.omac) && equals((Object) this.ssid, (Object) loc.ssid) && equals((Object) this.strength, (Object) loc.strength) && equals((Object) this.t, (Object) loc.t);
            }

            public final int hashCode() {
                int i = this.hashCode;
                if (i != 0) {
                    return i;
                }
                int i2 = 0;
                int hashCode = (((((((((((((((((((((((((((this.acc != null ? this.acc.hashCode() : 0) * 37) + (this.active != null ? this.active.hashCode() : 0)) * 37) + (this.bssid != null ? this.bssid.hashCode() : 0)) * 37) + (this.carrier != null ? this.carrier.hashCode() : 0)) * 37) + (this.cid != null ? this.cid.hashCode() : 0)) * 37) + (this.la != null ? this.la.hashCode() : 0)) * 37) + (this.lac != null ? this.lac.hashCode() : 0)) * 37) + (this.lo != null ? this.lo.hashCode() : 0)) * 37) + (this.mcc != null ? this.mcc.hashCode() : 0)) * 37) + (this.mnc != null ? this.mnc.hashCode() : 0)) * 37) + (this.nettpye != null ? this.nettpye.hashCode() : 0)) * 37) + (this.omac != null ? this.omac.hashCode() : 1)) * 37) + (this.ssid != null ? this.ssid.hashCode() : 0)) * 37) + (this.strength != null ? this.strength.hashCode() : 0)) * 37;
                if (this.t != null) {
                    i2 = this.t.hashCode();
                }
                int i3 = hashCode + i2;
                this.hashCode = i3;
                return i3;
            }
        }

        public static final class Usr extends Message {
            public static final String DEFAULT_APPKEY = "";
            public static final String DEFAULT_APPNAME = "";
            public static final String DEFAULT_APPVER = "";
            public static final String DEFAULT_PUBKEY = "";
            public static final String DEFAULT_SDKNAME = "";
            public static final String DEFAULT_SDKVER = "";
            public static final String DEFAULT_USER = "";
            public static final int TAG_APPKEY = 1;
            public static final int TAG_APPNAME = 2;
            public static final int TAG_APPVER = 3;
            public static final int TAG_PUBKEY = 4;
            public static final int TAG_SDKNAME = 5;
            public static final int TAG_SDKVER = 6;
            public static final int TAG_UA = 7;
            public static final int TAG_USER = 8;
            @ProtoField(tag = 1, type = Datatype.STRING)
            public String appkey;
            @ProtoField(tag = 2, type = Datatype.STRING)
            public String appname;
            @ProtoField(tag = 3, type = Datatype.STRING)
            public String appver;
            @ProtoField(tag = 4, type = Datatype.STRING)
            public String pubkey;
            @ProtoField(tag = 5, type = Datatype.STRING)
            public String sdkname;
            @ProtoField(tag = 6, type = Datatype.STRING)
            public String sdkver;
            @ProtoField(tag = 7)
            public Ua ua;
            @ProtoField(tag = 8, type = Datatype.STRING)
            public String user;

            public static final class AD extends Message {
                public static final Boolean DEFAULT_F = Boolean.FALSE;
                public static final String DEFAULT_KEY = "";
                public static final String DEFAULT_PR = "";
                public static final String DEFAULT_R = "";
                public static final String DEFAULT_T = "";
                public static final String DEFAULT_X = "";
                public static final String DEFAULT_Y = "";
                public static final int TAG_F = 4;
                public static final int TAG_KEY = 5;
                public static final int TAG_PR = 1;
                public static final int TAG_R = 3;
                public static final int TAG_T = 2;
                public static final int TAG_X = 6;
                public static final int TAG_Y = 7;
                @ProtoField(tag = 4, type = Datatype.BOOL)
                public Boolean f;
                @ProtoField(tag = 5, type = Datatype.STRING)
                public String key;
                @ProtoField(tag = 1, type = Datatype.STRING)
                public String pr;
                @ProtoField(tag = 3, type = Datatype.STRING)
                public String r;
                @ProtoField(tag = 2, type = Datatype.STRING)
                public String t;
                @ProtoField(tag = 6, type = Datatype.STRING)
                public String x;
                @ProtoField(tag = 7, type = Datatype.STRING)
                public String y;

                public AD(AD ad) {
                    super(ad);
                    if (ad != null) {
                        this.pr = ad.pr;
                        this.t = ad.t;
                        this.r = ad.r;
                        this.f = ad.f;
                        this.key = ad.key;
                        this.x = ad.x;
                        this.y = ad.y;
                    }
                }

                public AD() {
                }

                public final AD fillTagValue(int i, Object obj) {
                    switch (i) {
                        case 1:
                            this.pr = (String) obj;
                            break;
                        case 2:
                            this.t = (String) obj;
                            break;
                        case 3:
                            this.r = (String) obj;
                            break;
                        case 4:
                            this.f = (Boolean) obj;
                            break;
                        case 5:
                            this.key = (String) obj;
                            break;
                        case 6:
                            this.x = (String) obj;
                            break;
                        case 7:
                            this.y = (String) obj;
                            break;
                    }
                    return this;
                }

                public final boolean equals(Object obj) {
                    if (obj == this) {
                        return true;
                    }
                    if (!(obj instanceof AD)) {
                        return false;
                    }
                    AD ad = (AD) obj;
                    return equals((Object) this.pr, (Object) ad.pr) && equals((Object) this.t, (Object) ad.t) && equals((Object) this.r, (Object) ad.r) && equals((Object) this.f, (Object) ad.f) && equals((Object) this.key, (Object) ad.key) && equals((Object) this.x, (Object) ad.x) && equals((Object) this.y, (Object) ad.y);
                }

                public final int hashCode() {
                    int i = this.hashCode;
                    if (i != 0) {
                        return i;
                    }
                    int i2 = 0;
                    int hashCode = (((((((((((this.pr != null ? this.pr.hashCode() : 0) * 37) + (this.t != null ? this.t.hashCode() : 0)) * 37) + (this.r != null ? this.r.hashCode() : 0)) * 37) + (this.f != null ? this.f.hashCode() : 0)) * 37) + (this.key != null ? this.key.hashCode() : 0)) * 37) + (this.x != null ? this.x.hashCode() : 0)) * 37;
                    if (this.y != null) {
                        i2 = this.y.hashCode();
                    }
                    int i3 = hashCode + i2;
                    this.hashCode = i3;
                    return i3;
                }
            }

            public static final class Action extends Message {
                public static final List<AD> DEFAULT_AD = Collections.emptyList();
                public static final String DEFAULT_CN = "";
                public static final String DEFAULT_ET = "";
                public static final String DEFAULT_NUM = "";
                public static final String DEFAULT_PN = "";
                public static final String DEFAULT_SEQ = "";
                public static final String DEFAULT_T = "";
                public static final String DEFAULT_TYPE = "";
                public static final int TAG_AD = 1;
                public static final int TAG_CN = 2;
                public static final int TAG_ET = 3;
                public static final int TAG_NUM = 4;
                public static final int TAG_PN = 5;
                public static final int TAG_SEQ = 6;
                public static final int TAG_T = 8;
                public static final int TAG_TYPE = 7;
                @ProtoField(label = Label.REPEATED, tag = 1)
                public List<AD> ad;
                @ProtoField(tag = 2, type = Datatype.STRING)
                public String cn;
                @ProtoField(tag = 3, type = Datatype.STRING)
                public String et;
                @ProtoField(tag = 4, type = Datatype.STRING)
                public String num;
                @ProtoField(tag = 5, type = Datatype.STRING)
                public String pn;
                @ProtoField(tag = 6, type = Datatype.STRING)
                public String seq;
                @ProtoField(tag = 8, type = Datatype.STRING)
                public String t;
                @ProtoField(tag = 7, type = Datatype.STRING)
                public String type;

                public Action(Action action) {
                    super(action);
                    if (action != null) {
                        this.ad = copyOf(action.ad);
                        this.cn = action.cn;
                        this.et = action.et;
                        this.num = action.num;
                        this.pn = action.pn;
                        this.seq = action.seq;
                        this.type = action.type;
                        this.t = action.t;
                    }
                }

                public Action() {
                }

                public final Action fillTagValue(int i, Object obj) {
                    switch (i) {
                        case 1:
                            this.ad = immutableCopyOf((List) obj);
                            break;
                        case 2:
                            this.cn = (String) obj;
                            break;
                        case 3:
                            this.et = (String) obj;
                            break;
                        case 4:
                            this.num = (String) obj;
                            break;
                        case 5:
                            this.pn = (String) obj;
                            break;
                        case 6:
                            this.seq = (String) obj;
                            break;
                        case 7:
                            this.type = (String) obj;
                            break;
                        case 8:
                            this.t = (String) obj;
                            break;
                    }
                    return this;
                }

                public final boolean equals(Object obj) {
                    if (obj == this) {
                        return true;
                    }
                    if (!(obj instanceof Action)) {
                        return false;
                    }
                    Action action = (Action) obj;
                    return equals(this.ad, action.ad) && equals((Object) this.cn, (Object) action.cn) && equals((Object) this.et, (Object) action.et) && equals((Object) this.num, (Object) action.num) && equals((Object) this.pn, (Object) action.pn) && equals((Object) this.seq, (Object) action.seq) && equals((Object) this.type, (Object) action.type) && equals((Object) this.t, (Object) action.t);
                }

                public final int hashCode() {
                    int i = this.hashCode;
                    if (i != 0) {
                        return i;
                    }
                    int i2 = 0;
                    int hashCode = (((((((((((((this.ad != null ? this.ad.hashCode() : 1) * 37) + (this.cn != null ? this.cn.hashCode() : 0)) * 37) + (this.et != null ? this.et.hashCode() : 0)) * 37) + (this.num != null ? this.num.hashCode() : 0)) * 37) + (this.pn != null ? this.pn.hashCode() : 0)) * 37) + (this.seq != null ? this.seq.hashCode() : 0)) * 37) + (this.type != null ? this.type.hashCode() : 0)) * 37;
                    if (this.t != null) {
                        i2 = this.t.hashCode();
                    }
                    int i3 = hashCode + i2;
                    this.hashCode = i3;
                    return i3;
                }
            }

            public static final class Ua extends Message {
                public static final List<Action> DEFAULT_ACTION = Collections.emptyList();
                public static final String DEFAULT_NUM = "";
                public static final String DEFAULT_T = "";
                public static final int TAG_ACTION = 1;
                public static final int TAG_NUM = 2;
                public static final int TAG_T = 3;
                @ProtoField(label = Label.REPEATED, tag = 1)
                public List<Action> action;
                @ProtoField(tag = 2, type = Datatype.STRING)
                public String num;
                @ProtoField(tag = 3, type = Datatype.STRING)
                public String t;

                public Ua(Ua ua) {
                    super(ua);
                    if (ua != null) {
                        this.action = copyOf(ua.action);
                        this.num = ua.num;
                        this.t = ua.t;
                    }
                }

                public Ua() {
                }

                public final Ua fillTagValue(int i, Object obj) {
                    switch (i) {
                        case 1:
                            this.action = immutableCopyOf((List) obj);
                            break;
                        case 2:
                            this.num = (String) obj;
                            break;
                        case 3:
                            this.t = (String) obj;
                            break;
                    }
                    return this;
                }

                public final boolean equals(Object obj) {
                    if (obj == this) {
                        return true;
                    }
                    if (!(obj instanceof Ua)) {
                        return false;
                    }
                    Ua ua = (Ua) obj;
                    return equals(this.action, ua.action) && equals((Object) this.num, (Object) ua.num) && equals((Object) this.t, (Object) ua.t);
                }

                public final int hashCode() {
                    int i = this.hashCode;
                    if (i != 0) {
                        return i;
                    }
                    int i2 = 0;
                    int hashCode = (((this.action != null ? this.action.hashCode() : 1) * 37) + (this.num != null ? this.num.hashCode() : 0)) * 37;
                    if (this.t != null) {
                        i2 = this.t.hashCode();
                    }
                    int i3 = hashCode + i2;
                    this.hashCode = i3;
                    return i3;
                }
            }

            public Usr(Usr usr) {
                super(usr);
                if (usr != null) {
                    this.appkey = usr.appkey;
                    this.appname = usr.appname;
                    this.appver = usr.appver;
                    this.pubkey = usr.pubkey;
                    this.sdkname = usr.sdkname;
                    this.sdkver = usr.sdkver;
                    this.ua = usr.ua;
                    this.user = usr.user;
                }
            }

            public Usr() {
            }

            public final Usr fillTagValue(int i, Object obj) {
                switch (i) {
                    case 1:
                        this.appkey = (String) obj;
                        break;
                    case 2:
                        this.appname = (String) obj;
                        break;
                    case 3:
                        this.appver = (String) obj;
                        break;
                    case 4:
                        this.pubkey = (String) obj;
                        break;
                    case 5:
                        this.sdkname = (String) obj;
                        break;
                    case 6:
                        this.sdkver = (String) obj;
                        break;
                    case 7:
                        this.ua = (Ua) obj;
                        break;
                    case 8:
                        this.user = (String) obj;
                        break;
                }
                return this;
            }

            public final boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Usr)) {
                    return false;
                }
                Usr usr = (Usr) obj;
                return equals((Object) this.appkey, (Object) usr.appkey) && equals((Object) this.appname, (Object) usr.appname) && equals((Object) this.appver, (Object) usr.appver) && equals((Object) this.pubkey, (Object) usr.pubkey) && equals((Object) this.sdkname, (Object) usr.sdkname) && equals((Object) this.sdkver, (Object) usr.sdkver) && equals((Object) this.ua, (Object) usr.ua) && equals((Object) this.user, (Object) usr.user);
            }

            public final int hashCode() {
                int i = this.hashCode;
                if (i != 0) {
                    return i;
                }
                int i2 = 0;
                int hashCode = (((((((((((((this.appkey != null ? this.appkey.hashCode() : 0) * 37) + (this.appname != null ? this.appname.hashCode() : 0)) * 37) + (this.appver != null ? this.appver.hashCode() : 0)) * 37) + (this.pubkey != null ? this.pubkey.hashCode() : 0)) * 37) + (this.sdkname != null ? this.sdkname.hashCode() : 0)) * 37) + (this.sdkver != null ? this.sdkver.hashCode() : 0)) * 37) + (this.ua != null ? this.ua.hashCode() : 0)) * 37;
                if (this.user != null) {
                    i2 = this.user.hashCode();
                }
                int i3 = hashCode + i2;
                this.hashCode = i3;
                return i3;
            }
        }

        public Sdk(Sdk sdk) {
            super(sdk);
            if (sdk != null) {
                this.dev = sdk.dev;
                this.env = sdk.env;
                this.loc = sdk.loc;
                this.usr = sdk.usr;
            }
        }

        public Sdk() {
        }

        public final Sdk fillTagValue(int i, Object obj) {
            switch (i) {
                case 1:
                    this.dev = (Dev) obj;
                    break;
                case 2:
                    this.env = (Env) obj;
                    break;
                case 3:
                    this.loc = (Loc) obj;
                    break;
                case 4:
                    this.usr = (Usr) obj;
                    break;
            }
            return this;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Sdk)) {
                return false;
            }
            Sdk sdk = (Sdk) obj;
            return equals((Object) this.dev, (Object) sdk.dev) && equals((Object) this.env, (Object) sdk.env) && equals((Object) this.loc, (Object) sdk.loc) && equals((Object) this.usr, (Object) sdk.usr);
        }

        public final int hashCode() {
            int i = this.hashCode;
            if (i != 0) {
                return i;
            }
            int i2 = 0;
            int hashCode = (((((this.dev != null ? this.dev.hashCode() : 0) * 37) + (this.env != null ? this.env.hashCode() : 0)) * 37) + (this.loc != null ? this.loc.hashCode() : 0)) * 37;
            if (this.usr != null) {
                i2 = this.usr.hashCode();
            }
            int i3 = hashCode + i2;
            this.hashCode = i3;
            return i3;
        }
    }

    public static final class Taobao extends Message {
        public static final String DEFAULT_APPKEY = "";
        public static final String DEFAULT_T = "";
        public static final String DEFAULT_VERSION = "";
        public static final String DEFAULT_WUA = "";
        public static final int TAG_APPKEY = 1;
        public static final int TAG_T = 2;
        public static final int TAG_VERSION = 3;
        public static final int TAG_WUA = 4;
        @ProtoField(tag = 1, type = Datatype.STRING)
        public String appKey;
        @ProtoField(tag = 2, type = Datatype.STRING)
        public String t;
        @ProtoField(tag = 3, type = Datatype.STRING)
        public String version;
        @ProtoField(tag = 4, type = Datatype.STRING)
        public String wua;

        public Taobao(Taobao taobao) {
            super(taobao);
            if (taobao != null) {
                this.appKey = taobao.appKey;
                this.t = taobao.t;
                this.version = taobao.version;
                this.wua = taobao.wua;
            }
        }

        public Taobao() {
        }

        public final Taobao fillTagValue(int i, Object obj) {
            switch (i) {
                case 1:
                    this.appKey = (String) obj;
                    break;
                case 2:
                    this.t = (String) obj;
                    break;
                case 3:
                    this.version = (String) obj;
                    break;
                case 4:
                    this.wua = (String) obj;
                    break;
            }
            return this;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Taobao)) {
                return false;
            }
            Taobao taobao = (Taobao) obj;
            return equals((Object) this.appKey, (Object) taobao.appKey) && equals((Object) this.t, (Object) taobao.t) && equals((Object) this.version, (Object) taobao.version) && equals((Object) this.wua, (Object) taobao.wua);
        }

        public final int hashCode() {
            int i = this.hashCode;
            if (i != 0) {
                return i;
            }
            int i2 = 0;
            int hashCode = (((((this.appKey != null ? this.appKey.hashCode() : 0) * 37) + (this.t != null ? this.t.hashCode() : 0)) * 37) + (this.version != null ? this.version.hashCode() : 0)) * 37;
            if (this.wua != null) {
                i2 = this.wua.hashCode();
            }
            int i3 = hashCode + i2;
            this.hashCode = i3;
            return i3;
        }
    }

    public RdsRequestMessage(RdsRequestMessage rdsRequestMessage) {
        super(rdsRequestMessage);
        if (rdsRequestMessage != null) {
            this._native = rdsRequestMessage._native;
            this.sdk = rdsRequestMessage.sdk;
            this.taobao = rdsRequestMessage.taobao;
            this.extra1 = rdsRequestMessage.extra1;
            this.extra2 = rdsRequestMessage.extra2;
            this.extra3 = rdsRequestMessage.extra3;
            this.extra4 = rdsRequestMessage.extra4;
            this.extra5 = rdsRequestMessage.extra5;
        }
    }

    public RdsRequestMessage() {
    }

    public final RdsRequestMessage fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this._native = (Native) obj;
                break;
            case 2:
                this.sdk = (Sdk) obj;
                break;
            case 3:
                this.taobao = (Taobao) obj;
                break;
            case 4:
                this.extra1 = (String) obj;
                break;
            case 5:
                this.extra2 = (String) obj;
                break;
            case 6:
                this.extra3 = (String) obj;
                break;
            case 7:
                this.extra4 = (String) obj;
                break;
            case 8:
                this.extra5 = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RdsRequestMessage)) {
            return false;
        }
        RdsRequestMessage rdsRequestMessage = (RdsRequestMessage) obj;
        return equals((Object) this._native, (Object) rdsRequestMessage._native) && equals((Object) this.sdk, (Object) rdsRequestMessage.sdk) && equals((Object) this.taobao, (Object) rdsRequestMessage.taobao) && equals((Object) this.extra1, (Object) rdsRequestMessage.extra1) && equals((Object) this.extra2, (Object) rdsRequestMessage.extra2) && equals((Object) this.extra3, (Object) rdsRequestMessage.extra3) && equals((Object) this.extra4, (Object) rdsRequestMessage.extra4) && equals((Object) this.extra5, (Object) rdsRequestMessage.extra5);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int hashCode = (((((((((((((this._native != null ? this._native.hashCode() : 0) * 37) + (this.sdk != null ? this.sdk.hashCode() : 0)) * 37) + (this.taobao != null ? this.taobao.hashCode() : 0)) * 37) + (this.extra1 != null ? this.extra1.hashCode() : 0)) * 37) + (this.extra2 != null ? this.extra2.hashCode() : 0)) * 37) + (this.extra3 != null ? this.extra3.hashCode() : 0)) * 37) + (this.extra4 != null ? this.extra4.hashCode() : 0)) * 37;
        if (this.extra5 != null) {
            i2 = this.extra5.hashCode();
        }
        int i3 = hashCode + i2;
        this.hashCode = i3;
        return i3;
    }
}
