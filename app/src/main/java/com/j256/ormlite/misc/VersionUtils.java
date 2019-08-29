package com.j256.ormlite.misc;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

public class VersionUtils {
    private static final String CORE_VERSION = "VERSION__4.48__";
    private static String coreVersion = CORE_VERSION;
    private static Logger logger;
    private static boolean thrownOnErrors = false;

    private VersionUtils() {
    }

    public static final void checkCoreVersusJdbcVersions(String jdbcVersion) {
        logVersionWarnings("core", coreVersion, "jdbc", jdbcVersion);
    }

    public static final void checkCoreVersusAndroidVersions(String androidVersion) {
        logVersionWarnings("core", coreVersion, "android", androidVersion);
    }

    public static String getCoreVersion() {
        return coreVersion;
    }

    static void setThrownOnErrors(boolean thrownOnErrors2) {
        thrownOnErrors = thrownOnErrors2;
    }

    private static void logVersionWarnings(String label1, String version1, String label2, String version2) {
        if (version1 == null) {
            if (version2 != null) {
                warning(null, "Unknown version", " for {}, version for {} is '{}'", new Object[]{label1, label2, version2});
            }
        } else if (version2 == null) {
            warning(null, "Unknown version", " for {}, version for {} is '{}'", new Object[]{label2, label1, version1});
        } else if (!version1.equals(version2)) {
            warning(null, "Mismatched versions", ": {} is '{}', while {} is '{}'", new Object[]{label1, version1, label2, version2});
        }
    }

    private static void warning(Throwable th, String msg, String format, Object[] args) {
        getLogger().warn(th, msg + format, args);
        if (thrownOnErrors) {
            throw new IllegalStateException("See error log for details:" + msg);
        }
    }

    private static Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(VersionUtils.class);
        }
        return logger;
    }
}
