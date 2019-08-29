package com.j256.ormlite.android.apptools;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.SqliteAndroidDatabaseType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrmLiteConfigUtil {
    protected static final String RAW_DIR_NAME = "raw";
    protected static final String RESOURCE_DIR_NAME = "res";
    private static final DatabaseType databaseType = new SqliteAndroidDatabaseType();
    protected static int maxFindSourceLevel = 20;

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Main can take a single file-name argument.");
        }
        writeConfigFile(args[0]);
    }

    public static void writeConfigFile(String fileName) {
        List classList = new ArrayList();
        findAnnotatedClasses(classList, new File("."), 0);
        writeConfigFile(fileName, (Class<?>[]) (Class[]) classList.toArray(new Class[classList.size()]));
    }

    public static void writeConfigFile(String fileName, Class<?>[] classes) {
        File rawDir = findRawDir(new File("."));
        if (rawDir == null) {
            System.err.println("Could not find raw directory which is typically in the res directory");
        } else {
            writeConfigFile(new File(rawDir, fileName), classes);
        }
    }

    public static void writeConfigFile(File configFile) {
        writeConfigFile(configFile, new File("."));
    }

    public static void writeConfigFile(File configFile, File searchDir) {
        List classList = new ArrayList();
        findAnnotatedClasses(classList, searchDir, 0);
        writeConfigFile(configFile, (Class<?>[]) (Class[]) classList.toArray(new Class[classList.size()]));
    }

    public static void writeConfigFile(File configFile, Class<?>[] classes) {
        System.out.println("Writing configurations to " + configFile.getAbsolutePath());
        writeConfigFile((OutputStream) new FileOutputStream(configFile), classes);
    }

    public static void writeConfigFile(OutputStream outputStream, File searchDir) {
        List classList = new ArrayList();
        findAnnotatedClasses(classList, searchDir, 0);
        writeConfigFile(outputStream, (Class<?>[]) (Class[]) classList.toArray(new Class[classList.size()]));
    }

    public static void writeConfigFile(OutputStream outputStream, Class<?>[] classes) {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream), 4096);
        try {
            writeHeader(writer);
            for (Class clazz : classes) {
                writeConfigForTable(writer, clazz);
            }
            System.out.println("Done.");
        } finally {
            writer.close();
        }
    }

    protected static File findRawDir(File dir) {
        int i = 0;
        while (dir != null && i < 20) {
            File rawDir = findResRawDir(dir);
            if (rawDir != null) {
                return rawDir;
            }
            dir = dir.getParentFile();
            i++;
        }
        return null;
    }

    private static void writeHeader(BufferedWriter writer) {
        writer.append('#');
        writer.newLine();
        writer.append("# generated on ").append(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()));
        writer.newLine();
        writer.append('#');
        writer.newLine();
    }

    private static void findAnnotatedClasses(List<Class<?>> classList, File dir, int level) {
        File[] listFiles;
        Class[] declaredClasses;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                if (level < maxFindSourceLevel) {
                    findAnnotatedClasses(classList, file, level + 1);
                }
            } else if (file.getName().endsWith(".java")) {
                String packageName = getPackageOfClass(file);
                if (packageName == null) {
                    System.err.println("Could not find package name for: " + file);
                } else {
                    String name = file.getName();
                    try {
                        Class clazz = Class.forName(packageName + "." + name.substring(0, name.length() - 5));
                        if (classHasAnnotations(clazz)) {
                            classList.add(clazz);
                        }
                        try {
                            for (Class innerClazz : clazz.getDeclaredClasses()) {
                                if (classHasAnnotations(innerClazz)) {
                                    classList.add(innerClazz);
                                }
                            }
                        } catch (Throwable t) {
                            System.err.println("Could not load inner classes for: " + clazz);
                            System.err.println("     " + t);
                        }
                    } catch (Throwable t2) {
                        System.err.println("Could not load class file for: " + file);
                        System.err.println("     " + t2);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r12v0, types: [java.lang.Class<?>, java.lang.Class, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void writeConfigForTable(java.io.BufferedWriter r11, java.lang.Class r12) {
        /*
            java.lang.String r5 = com.j256.ormlite.table.DatabaseTableConfig.extractTableName(r12)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r6 = r12
        L_0x000a:
            if (r6 == 0) goto L_0x004d
            java.lang.reflect.Field[] r8 = r6.getDeclaredFields()     // Catch:{ Error -> 0x0029 }
            int r9 = r8.length     // Catch:{ Error -> 0x0029 }
            r7 = 0
        L_0x0012:
            if (r7 >= r9) goto L_0x0024
            r1 = r8[r7]     // Catch:{ Error -> 0x0029 }
            com.j256.ormlite.db.DatabaseType r10 = databaseType     // Catch:{ Error -> 0x0029 }
            com.j256.ormlite.field.DatabaseFieldConfig r2 = com.j256.ormlite.field.DatabaseFieldConfig.fromField(r10, r5, r1)     // Catch:{ Error -> 0x0029 }
            if (r2 == 0) goto L_0x0021
            r3.add(r2)     // Catch:{ Error -> 0x0029 }
        L_0x0021:
            int r7 = r7 + 1
            goto L_0x0012
        L_0x0024:
            java.lang.Class r6 = r6.getSuperclass()     // Catch:{ Error -> 0x0029 }
            goto L_0x000a
        L_0x0029:
            r0 = move-exception
            java.io.PrintStream r7 = java.lang.System.err
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Skipping "
            r8.<init>(r9)
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r9 = " because we got an error finding its definition: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r0.getMessage()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.println(r8)
        L_0x004c:
            return
        L_0x004d:
            boolean r7 = r3.isEmpty()
            if (r7 == 0) goto L_0x006e
            java.io.PrintStream r7 = java.lang.System.out
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Skipping "
            r8.<init>(r9)
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r9 = " because no annotated fields found"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.println(r8)
            goto L_0x004c
        L_0x006e:
            com.j256.ormlite.table.DatabaseTableConfig r4 = new com.j256.ormlite.table.DatabaseTableConfig
            r4.<init>(r12, r5, r3)
            com.j256.ormlite.table.DatabaseTableConfigLoader.write(r11, r4)
            java.lang.String r7 = "#################################"
            r11.append(r7)
            r11.newLine()
            java.io.PrintStream r7 = java.lang.System.out
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Wrote config for "
            r8.<init>(r9)
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r8 = r8.toString()
            r7.println(r8)
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.android.apptools.OrmLiteConfigUtil.writeConfigForTable(java.io.BufferedWriter, java.lang.Class):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.Class<?>, code=java.lang.Class, for r8v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean classHasAnnotations(java.lang.Class r8) {
        /*
            r3 = 1
            r4 = 0
        L_0x0002:
            if (r8 == 0) goto L_0x0085
            java.lang.Class<com.j256.ormlite.table.DatabaseTable> r5 = com.j256.ormlite.table.DatabaseTable.class
            java.lang.annotation.Annotation r5 = r8.getAnnotation(r5)
            if (r5 == 0) goto L_0x000d
        L_0x000c:
            return r3
        L_0x000d:
            java.lang.reflect.Field[] r1 = r8.getDeclaredFields()     // Catch:{ Throwable -> 0x002a }
            int r6 = r1.length
            r5 = r4
        L_0x0013:
            if (r5 >= r6) goto L_0x0055
            r0 = r1[r5]
            java.lang.Class<com.j256.ormlite.field.DatabaseField> r7 = com.j256.ormlite.field.DatabaseField.class
            java.lang.annotation.Annotation r7 = r0.getAnnotation(r7)
            if (r7 != 0) goto L_0x000c
            java.lang.Class<com.j256.ormlite.field.ForeignCollectionField> r7 = com.j256.ormlite.field.ForeignCollectionField.class
            java.lang.annotation.Annotation r7 = r0.getAnnotation(r7)
            if (r7 != 0) goto L_0x000c
            int r5 = r5 + 1
            goto L_0x0013
        L_0x002a:
            r2 = move-exception
            java.io.PrintStream r3 = java.lang.System.err
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Could not load get delcared fields from: "
            r5.<init>(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            r3.println(r5)
            java.io.PrintStream r3 = java.lang.System.err
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "     "
            r5.<init>(r6)
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r5 = r5.toString()
            r3.println(r5)
            r3 = r4
            goto L_0x000c
        L_0x0055:
            java.lang.Class r8 = r8.getSuperclass()     // Catch:{ Throwable -> 0x005a }
            goto L_0x0002
        L_0x005a:
            r2 = move-exception
            java.io.PrintStream r3 = java.lang.System.err
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Could not get super class for: "
            r5.<init>(r6)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            r3.println(r5)
            java.io.PrintStream r3 = java.lang.System.err
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "     "
            r5.<init>(r6)
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r5 = r5.toString()
            r3.println(r5)
            r3 = r4
            goto L_0x000c
        L_0x0085:
            r3 = r4
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.j256.ormlite.android.apptools.OrmLiteConfigUtil.classHasAnnotations(java.lang.Class):boolean");
    }

    private static String getPackageOfClass(File file) {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null) {
                    reader.close();
                    return null;
                } else if (line.contains("package")) {
                    String[] parts = line.split("[ \t;]");
                    if (parts.length > 1 && parts[0].equals("package")) {
                        return parts[1];
                    }
                }
            } finally {
                reader.close();
            }
        }
    }

    private static File findResRawDir(File dir) {
        File[] listFiles;
        for (File file : dir.listFiles()) {
            if (file.getName().equals("res") && file.isDirectory()) {
                File[] rawFiles = file.listFiles(new FileFilter() {
                    public final boolean accept(File file) {
                        return file.getName().equals("raw") && file.isDirectory();
                    }
                });
                if (rawFiles.length == 1) {
                    return rawFiles[0];
                }
            }
        }
        return null;
    }
}
