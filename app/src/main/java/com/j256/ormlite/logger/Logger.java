package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log.Level;
import java.util.Arrays;

public class Logger {
    private static final String ARG_STRING = "{}";
    private static final int ARG_STRING_LENGTH = 2;
    private static final Object UNKNOWN_ARG = new Object();
    private final Log log;

    public Logger(Log log2) {
        this.log = log2;
    }

    public boolean isLevelEnabled(Level level) {
        return this.log.isLevelEnabled(level);
    }

    public void trace(String msg) {
        innerLog(Level.TRACE, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(String msg, Object arg0) {
        innerLog(Level.TRACE, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(String msg, Object arg0, Object arg1) {
        innerLog(Level.TRACE, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void trace(String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.TRACE, null, msg, arg0, arg1, arg2, null);
    }

    public void trace(String msg, Object[] argArray) {
        innerLog(Level.TRACE, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void trace(Throwable throwable, String msg) {
        innerLog(Level.TRACE, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(Throwable throwable, String msg, Object arg0) {
        innerLog(Level.TRACE, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void trace(Throwable throwable, String msg, Object arg0, Object arg1) {
        innerLog(Level.TRACE, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void trace(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.TRACE, throwable, msg, arg0, arg1, arg2, null);
    }

    public void trace(Throwable throwable, String msg, Object[] argArray) {
        innerLog(Level.TRACE, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void debug(String msg) {
        innerLog(Level.DEBUG, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(String msg, Object arg0) {
        innerLog(Level.DEBUG, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(String msg, Object arg0, Object arg1) {
        innerLog(Level.DEBUG, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void debug(String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.DEBUG, null, msg, arg0, arg1, arg2, null);
    }

    public void debug(String msg, Object[] argArray) {
        innerLog(Level.DEBUG, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void debug(Throwable throwable, String msg) {
        innerLog(Level.DEBUG, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(Throwable throwable, String msg, Object arg0) {
        innerLog(Level.DEBUG, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void debug(Throwable throwable, String msg, Object arg0, Object arg1) {
        innerLog(Level.DEBUG, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void debug(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.DEBUG, throwable, msg, arg0, arg1, arg2, null);
    }

    public void debug(Throwable throwable, String msg, Object[] argArray) {
        innerLog(Level.DEBUG, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void info(String msg) {
        innerLog(Level.INFO, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(String msg, Object arg0) {
        innerLog(Level.INFO, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(String msg, Object arg0, Object arg1) {
        innerLog(Level.INFO, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void info(String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.INFO, null, msg, arg0, arg1, arg2, null);
    }

    public void info(String msg, Object[] argArray) {
        innerLog(Level.INFO, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void info(Throwable throwable, String msg) {
        innerLog(Level.INFO, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(Throwable throwable, String msg, Object arg0) {
        innerLog(Level.INFO, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void info(Throwable throwable, String msg, Object arg0, Object arg1) {
        innerLog(Level.INFO, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void info(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.INFO, throwable, msg, arg0, arg1, arg2, null);
    }

    public void info(Throwable throwable, String msg, Object[] argArray) {
        innerLog(Level.INFO, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void warn(String msg) {
        innerLog(Level.WARNING, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(String msg, Object arg0) {
        innerLog(Level.WARNING, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(String msg, Object arg0, Object arg1) {
        innerLog(Level.WARNING, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void warn(String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.WARNING, null, msg, arg0, arg1, arg2, null);
    }

    public void warn(String msg, Object[] argArray) {
        innerLog(Level.WARNING, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void warn(Throwable throwable, String msg) {
        innerLog(Level.WARNING, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(Throwable throwable, String msg, Object arg0) {
        innerLog(Level.WARNING, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void warn(Throwable throwable, String msg, Object arg0, Object arg1) {
        innerLog(Level.WARNING, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void warn(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.WARNING, throwable, msg, arg0, arg1, arg2, null);
    }

    public void warn(Throwable throwable, String msg, Object[] argArray) {
        innerLog(Level.WARNING, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void error(String msg) {
        innerLog(Level.ERROR, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(String msg, Object arg0) {
        innerLog(Level.ERROR, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(String msg, Object arg0, Object arg1) {
        innerLog(Level.ERROR, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void error(String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.ERROR, null, msg, arg0, arg1, arg2, null);
    }

    public void error(String msg, Object[] argArray) {
        innerLog(Level.ERROR, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void error(Throwable throwable, String msg) {
        innerLog(Level.ERROR, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(Throwable throwable, String msg, Object arg0) {
        innerLog(Level.ERROR, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void error(Throwable throwable, String msg, Object arg0, Object arg1) {
        innerLog(Level.ERROR, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void error(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.ERROR, throwable, msg, arg0, arg1, arg2, null);
    }

    public void error(Throwable throwable, String msg, Object[] argArray) {
        innerLog(Level.ERROR, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void fatal(String msg) {
        innerLog(Level.FATAL, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(String msg, Object arg0) {
        innerLog(Level.FATAL, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(String msg, Object arg0, Object arg1) {
        innerLog(Level.FATAL, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void fatal(String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.FATAL, null, msg, arg0, arg1, arg2, null);
    }

    public void fatal(String msg, Object[] argArray) {
        innerLog(Level.FATAL, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void fatal(Throwable throwable, String msg) {
        innerLog(Level.FATAL, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(Throwable throwable, String msg, Object arg0) {
        innerLog(Level.FATAL, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void fatal(Throwable throwable, String msg, Object arg0, Object arg1) {
        innerLog(Level.FATAL, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void fatal(Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(Level.FATAL, throwable, msg, arg0, arg1, arg2, null);
    }

    public void fatal(Throwable throwable, String msg, Object[] argArray) {
        innerLog(Level.FATAL, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void log(Level level, String msg) {
        innerLog(level, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Level level, String msg, Object arg0) {
        innerLog(level, null, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Level level, String msg, Object arg0, Object arg1) {
        innerLog(level, null, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void log(Level level, String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(level, null, msg, arg0, arg1, arg2, null);
    }

    public void log(Level level, String msg, Object[] argArray) {
        innerLog(level, null, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    public void log(Level level, Throwable throwable, String msg) {
        innerLog(level, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Level level, Throwable throwable, String msg, Object arg0) {
        innerLog(level, throwable, msg, arg0, UNKNOWN_ARG, UNKNOWN_ARG, null);
    }

    public void log(Level level, Throwable throwable, String msg, Object arg0, Object arg1) {
        innerLog(level, throwable, msg, arg0, arg1, UNKNOWN_ARG, null);
    }

    public void log(Level level, Throwable throwable, String msg, Object arg0, Object arg1, Object arg2) {
        innerLog(level, throwable, msg, arg0, arg1, arg2, null);
    }

    public void log(Level level, Throwable throwable, String msg, Object[] argArray) {
        innerLog(level, throwable, msg, UNKNOWN_ARG, UNKNOWN_ARG, UNKNOWN_ARG, argArray);
    }

    private void innerLog(Level level, Throwable throwable, String msg, Object arg0, Object arg1, Object arg2, Object[] argArray) {
        if (this.log.isLevelEnabled(level)) {
            String fullMsg = buildFullMessage(msg, arg0, arg1, arg2, argArray);
            if (throwable == null) {
                this.log.log(level, fullMsg);
            } else {
                this.log.log(level, fullMsg, throwable);
            }
        }
    }

    private String buildFullMessage(String msg, Object arg0, Object arg1, Object arg2, Object[] argArray) {
        StringBuilder sb = null;
        int lastIndex = 0;
        int argC = 0;
        while (true) {
            int argIndex = msg.indexOf("{}", lastIndex);
            if (argIndex == -1) {
                break;
            }
            if (sb == null) {
                sb = new StringBuilder(128);
            }
            sb.append(msg, lastIndex, argIndex);
            lastIndex = argIndex + ARG_STRING_LENGTH;
            if (argArray == null) {
                if (argC == 0) {
                    appendArg(sb, arg0);
                } else if (argC == 1) {
                    appendArg(sb, arg1);
                } else if (argC == 2) {
                    appendArg(sb, arg2);
                }
            } else if (argC < argArray.length) {
                appendArg(sb, argArray[argC]);
            }
            argC++;
        }
        if (sb == null) {
            return msg;
        }
        sb.append(msg, lastIndex, msg.length());
        return sb.toString();
    }

    private void appendArg(StringBuilder sb, Object arg) {
        if (arg == UNKNOWN_ARG) {
            return;
        }
        if (arg == null) {
            sb.append("null");
        } else if (arg.getClass().isArray()) {
            sb.append(Arrays.toString((Object[]) arg));
        } else {
            sb.append(arg);
        }
    }
}
