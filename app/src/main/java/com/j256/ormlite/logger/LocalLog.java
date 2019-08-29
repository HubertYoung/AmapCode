package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log.Level;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class LocalLog implements Log {
    private static final Level DEFAULT_LEVEL = Level.DEBUG;
    public static final String LOCAL_LOG_FILE_PROPERTY = "com.j256.ormlite.logger.file";
    public static final String LOCAL_LOG_LEVEL_PROPERTY = "com.j256.ormlite.logger.level";
    public static final String LOCAL_LOG_PROPERTIES_FILE = "/ormliteLocalLog.properties";
    private static final List<PatternLevel> classLevels = readLevelResourceFile(LocalLog.class.getResourceAsStream(LOCAL_LOG_PROPERTIES_FILE));
    private static final ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<DateFormat>() {
        /* access modifiers changed from: protected */
        public final DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        }
    };
    private static PrintStream printStream;
    private final String className;
    private final Level level;

    class PatternLevel {
        Level level;
        Pattern pattern;

        public PatternLevel(Pattern pattern2, Level level2) {
            this.pattern = pattern2;
            this.level = level2;
        }
    }

    static {
        openLogFile(System.getProperty(LOCAL_LOG_FILE_PROPERTY));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003a, code lost:
        r1 = r4.level;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LocalLog(java.lang.String r9) {
        /*
            r8 = this;
            r8.<init>()
            java.lang.String r5 = com.j256.ormlite.logger.LoggerFactory.getSimpleClassName(r9)
            r8.className = r5
            r1 = 0
            java.util.List<com.j256.ormlite.logger.LocalLog$PatternLevel> r5 = classLevels
            if (r5 == 0) goto L_0x003d
            java.util.List<com.j256.ormlite.logger.LocalLog$PatternLevel> r5 = classLevels
            java.util.Iterator r5 = r5.iterator()
        L_0x0014:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x003d
            java.lang.Object r4 = r5.next()
            com.j256.ormlite.logger.LocalLog$PatternLevel r4 = (com.j256.ormlite.logger.LocalLog.PatternLevel) r4
            java.util.regex.Pattern r6 = r4.pattern
            java.util.regex.Matcher r6 = r6.matcher(r9)
            boolean r6 = r6.matches()
            if (r6 == 0) goto L_0x0014
            if (r1 == 0) goto L_0x003a
            com.j256.ormlite.logger.Log$Level r6 = r4.level
            int r6 = r6.ordinal()
            int r7 = r1.ordinal()
            if (r6 >= r7) goto L_0x0014
        L_0x003a:
            com.j256.ormlite.logger.Log$Level r1 = r4.level
            goto L_0x0014
        L_0x003d:
            if (r1 != 0) goto L_0x0049
            java.lang.String r5 = "com.j256.ormlite.logger.level"
            java.lang.String r2 = java.lang.System.getProperty(r5)
            if (r2 != 0) goto L_0x004c
            com.j256.ormlite.logger.Log$Level r1 = DEFAULT_LEVEL
        L_0x0049:
            r8.level = r1
            return
        L_0x004c:
            java.lang.String r5 = r2.toUpperCase()     // Catch:{ IllegalArgumentException -> 0x0056 }
            com.j256.ormlite.logger.Log$Level r3 = com.j256.ormlite.logger.Log.Level.valueOf(r5)     // Catch:{ IllegalArgumentException -> 0x0056 }
            r1 = r3
            goto L_0x0049
        L_0x0056:
            r0 = move-exception
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Level '"
            r6.<init>(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r7 = "' was not found"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6, r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.logger.LocalLog.<init>(java.lang.String):void");
    }

    public static void openLogFile(String logPath) {
        if (logPath == null) {
            printStream = System.out;
            return;
        }
        try {
            printStream = new PrintStream(new File(logPath));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Log file " + logPath + " was not found", e);
        }
    }

    public boolean isLevelEnabled(Level level2) {
        return this.level.isEnabled(level2);
    }

    public void log(Level level2, String msg) {
        printMessage(level2, msg, null);
    }

    public void log(Level level2, String msg, Throwable throwable) {
        printMessage(level2, msg, throwable);
    }

    /* access modifiers changed from: 0000 */
    public void flush() {
        printStream.flush();
    }

    static List<PatternLevel> readLevelResourceFile(InputStream stream) {
        List levels = null;
        if (stream != null) {
            try {
                levels = configureClassLevels(stream);
            } catch (IOException e) {
                System.err.println("IO exception reading the log properties file '/ormliteLocalLog.properties': " + e);
            } finally {
                try {
                    stream.close();
                } catch (IOException e2) {
                }
            }
        }
        return levels;
    }

    private static List<PatternLevel> configureClassLevels(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List list = new ArrayList();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                return list;
            }
            if (!(line.length() == 0 || line.charAt(0) == '#')) {
                String[] parts = line.split("=");
                if (parts.length != 2) {
                    System.err.println("Line is not in the format of 'pattern = level': " + line);
                } else {
                    try {
                        list.add(new PatternLevel(Pattern.compile(parts[0].trim()), Level.valueOf(parts[1].trim())));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Level '" + parts[1] + "' was not found");
                    }
                }
            }
        }
    }

    private void printMessage(Level level2, String message, Throwable throwable) {
        if (isLevelEnabled(level2)) {
            StringBuilder sb = new StringBuilder(128);
            sb.append(dateFormatThreadLocal.get().format(new Date()));
            sb.append(" [").append(level2.name()).append("] ");
            sb.append(this.className).append(' ');
            sb.append(message);
            printStream.println(sb.toString());
            if (throwable != null) {
                throwable.printStackTrace(printStream);
            }
        }
    }
}
