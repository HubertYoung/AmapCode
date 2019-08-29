package com.alipay.mobile.quinox.log;

public abstract class Logger {
    public static final int ASSERT = 7;
    public static final String D = "d";
    public static final int DEBUG = 3;
    public static final String E = "e";
    public static final int ERROR = 6;
    public static final String I = "i";
    public static final int INFO = 4;
    public static final String[] LEVEL_TO_LVL = {"v", "d", "i", "w", "e"};
    public static final String V = "v";
    public static final int VERBOSE = 2;
    public static final String W = "w";
    public static final int WARN = 5;
    private int LOG_LEVEL = 2;

    public abstract int debug(String str, String str2);

    public abstract int error(String str, String str2);

    public abstract String getStackTraceString(Throwable th);

    public abstract int info(String str, String str2);

    public abstract int verbose(String str, String str2);

    public abstract int warn(String str, String str2);

    public void setLogLevel(int i) {
        if (i < 2 || i > 7) {
            throw new RuntimeException("level should between [2 , 7]");
        }
        this.LOG_LEVEL = i;
    }

    public int v(String str, String str2) {
        if (this.LOG_LEVEL <= 2) {
            return verbose(str, str2);
        }
        return -1;
    }

    public int v(String str, String str2, Throwable th) {
        if (this.LOG_LEVEL > 2) {
            return -1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(10);
        sb.append(getStackTraceString(th));
        return verbose(str, sb.toString());
    }

    public int v(String str, Throwable th) {
        if (this.LOG_LEVEL <= 2) {
            return verbose(str, getStackTraceString(th));
        }
        return -1;
    }

    public int d(String str, String str2) {
        if (this.LOG_LEVEL <= 3) {
            return debug(str, str2);
        }
        return -1;
    }

    public int d(String str, String str2, Throwable th) {
        if (this.LOG_LEVEL > 3) {
            return -1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(10);
        sb.append(getStackTraceString(th));
        return debug(str, sb.toString());
    }

    public int d(String str, Throwable th) {
        if (this.LOG_LEVEL <= 3) {
            return debug(str, getStackTraceString(th));
        }
        return -1;
    }

    public int i(String str, String str2) {
        if (this.LOG_LEVEL <= 4) {
            return info(str, str2);
        }
        return -1;
    }

    public int i(String str, String str2, Throwable th) {
        if (this.LOG_LEVEL > 4) {
            return -1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(10);
        sb.append(getStackTraceString(th));
        return info(str, sb.toString());
    }

    public int i(String str, Throwable th) {
        if (this.LOG_LEVEL <= 4) {
            return info(str, getStackTraceString(th));
        }
        return -1;
    }

    public int w(String str, String str2) {
        if (this.LOG_LEVEL <= 5) {
            return warn(str, str2);
        }
        return -1;
    }

    public int w(String str, String str2, Throwable th) {
        if (this.LOG_LEVEL > 5) {
            return -1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(10);
        sb.append(getStackTraceString(th));
        return warn(str, sb.toString());
    }

    public int w(String str, Throwable th) {
        if (this.LOG_LEVEL <= 5) {
            return warn(str, getStackTraceString(th));
        }
        return -1;
    }

    public int e(String str, String str2) {
        if (this.LOG_LEVEL <= 6) {
            return error(str, str2);
        }
        return -1;
    }

    public int e(String str, String str2, Throwable th) {
        if (this.LOG_LEVEL > 6) {
            return -1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(10);
        sb.append(getStackTraceString(th));
        return error(str, sb.toString());
    }

    public int e(String str, Throwable th) {
        if (this.LOG_LEVEL <= 6) {
            return error(str, getStackTraceString(th));
        }
        return -1;
    }
}
