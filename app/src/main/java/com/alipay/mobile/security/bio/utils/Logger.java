package com.alipay.mobile.security.bio.utils;

public abstract class Logger {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private int a = 2;

    public abstract int debug(String str, String str2);

    public abstract int error(String str, String str2);

    public abstract String getStackTraceString(Throwable th);

    public abstract int info(String str, String str2);

    public abstract int verbose(String str, String str2);

    public abstract int warn(String str, String str2);

    public void setLogLevel(int i) {
        if (i < 2 || i > 7) {
            throw new RuntimeException("The level value should between [2 , 7]");
        }
        this.a = i;
    }

    public int v(String str, String str2) {
        if (this.a <= 2) {
            return verbose(str, str2);
        }
        return -1;
    }

    public int v(String str, String str2, Throwable th) {
        if (this.a <= 2) {
            return verbose(str, str2 + 10 + getStackTraceString(th));
        }
        return -1;
    }

    public int v(String str, Throwable th) {
        if (this.a <= 2) {
            return verbose(str, getStackTraceString(th));
        }
        return -1;
    }

    public int d(String str, String str2) {
        if (this.a <= 3) {
            return debug(str, str2);
        }
        return -1;
    }

    public int d(String str, String str2, Throwable th) {
        if (this.a <= 3) {
            return debug(str, str2 + 10 + getStackTraceString(th));
        }
        return -1;
    }

    public int d(String str, Throwable th) {
        if (this.a <= 3) {
            return debug(str, getStackTraceString(th));
        }
        return -1;
    }

    public int i(String str, String str2) {
        if (this.a <= 4) {
            return info(str, str2);
        }
        return -1;
    }

    public int i(String str, String str2, Throwable th) {
        if (this.a <= 4) {
            return info(str, str2 + 10 + getStackTraceString(th));
        }
        return -1;
    }

    public int i(String str, Throwable th) {
        if (this.a <= 4) {
            return info(str, getStackTraceString(th));
        }
        return -1;
    }

    public int w(String str, String str2) {
        if (this.a <= 5) {
            return warn(str, str2);
        }
        return -1;
    }

    public int w(String str, String str2, Throwable th) {
        if (this.a <= 5) {
            return warn(str, str2 + 10 + getStackTraceString(th));
        }
        return -1;
    }

    public int w(String str, Throwable th) {
        if (this.a <= 5) {
            return warn(str, getStackTraceString(th));
        }
        return -1;
    }

    public int e(String str, String str2) {
        if (this.a <= 6) {
            return error(str, str2);
        }
        return -1;
    }

    public int e(String str, String str2, Throwable th) {
        if (this.a <= 6) {
            return error(str, str2 + 10 + getStackTraceString(th));
        }
        return -1;
    }

    public int e(String str, Throwable th) {
        if (this.a <= 6) {
            return error(str, getStackTraceString(th));
        }
        return -1;
    }
}
