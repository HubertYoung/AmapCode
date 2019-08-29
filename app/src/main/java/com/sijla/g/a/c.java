package com.sijla.g.a;

import android.text.TextUtils;
import com.sijla.g.b;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class c {
    public static void a(File file) {
        if (file != null) {
            try {
                if (file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null) {
                        if (listFiles.length > 0) {
                            for (File a : listFiles) {
                                a(a);
                            }
                            return;
                        }
                        file.delete();
                    }
                    return;
                }
                file.delete();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public static void a(List<File> list) {
        if (list != null && list.size() > 0) {
            for (File next : list) {
                if (next.exists() && next.isFile()) {
                    next.delete();
                }
            }
        }
    }

    public static boolean b(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists() && parentFile.mkdirs()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String c(File file) {
        if (file.exists()) {
            BufferedReader bufferedReader = null;
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                while (true) {
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine != null) {
                            sb.append(readLine);
                            sb.append("\n");
                        } else {
                            String sb2 = sb.toString();
                            b.a(bufferedReader2);
                            return sb2;
                        }
                    } catch (IOException e) {
                        e = e;
                        bufferedReader = bufferedReader2;
                        try {
                            e.printStackTrace();
                            b.a(bufferedReader);
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            b.a(bufferedReader);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader = bufferedReader2;
                        b.a(bufferedReader);
                        throw th;
                    }
                }
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                b.a(bufferedReader);
                return "";
            }
        }
        return "";
    }

    public static boolean a(String str, String str2, boolean z) {
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        if (!str.endsWith("\n")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("\n");
            str = sb.toString();
        }
        BufferedWriter bufferedWriter2 = null;
        try {
            File file = new File(str2);
            b(file);
            bufferedReader = new BufferedReader(new StringReader(str));
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(file, z));
            } catch (Exception e) {
                e = e;
                try {
                    e.printStackTrace();
                    b.a(bufferedWriter2, bufferedReader);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    b.a(bufferedWriter2, bufferedReader);
                    throw th;
                }
            }
            try {
                char[] cArr = new char[1024];
                while (true) {
                    int read = bufferedReader.read(cArr);
                    if (read != -1) {
                        bufferedWriter.write(cArr, 0, read);
                    } else {
                        bufferedWriter.flush();
                        b.a(bufferedWriter, bufferedReader);
                        return true;
                    }
                }
            } catch (Exception e2) {
                e = e2;
                bufferedWriter2 = bufferedWriter;
                e.printStackTrace();
                b.a(bufferedWriter2, bufferedReader);
                return false;
            } catch (Throwable th2) {
                th = th2;
                bufferedWriter2 = bufferedWriter;
                b.a(bufferedWriter2, bufferedReader);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            bufferedReader = null;
            e.printStackTrace();
            b.a(bufferedWriter2, bufferedReader);
            return false;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            b.a(bufferedWriter2, bufferedReader);
            throw th;
        }
    }

    public static File a(String str, String str2) {
        return a(str, b.g(str2));
    }

    public static File a(String str, byte[] bArr) {
        Closeable[] closeableArr;
        FileOutputStream fileOutputStream = null;
        if (b.a(str) || bArr == null || a.i() < 1) {
            return null;
        }
        File file = new File(str);
        b(file);
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                fileOutputStream2.write(bArr);
                closeableArr = new Closeable[]{fileOutputStream2};
            } catch (Exception e) {
                e = e;
                fileOutputStream = fileOutputStream2;
                try {
                    e.printStackTrace();
                    closeableArr = new Closeable[]{fileOutputStream};
                    b.a(closeableArr);
                    return file;
                } catch (Throwable th) {
                    th = th;
                    b.a(fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                b.a(fileOutputStream);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            closeableArr = new Closeable[]{fileOutputStream};
            b.a(closeableArr);
            return file;
        }
        b.a(closeableArr);
        return file;
    }

    public static File[] a(String str) {
        return a(str, (FileFilter) null);
    }

    public static File[] a(String str, FileFilter fileFilter) {
        File[] fileArr = new File[0];
        if (TextUtils.isEmpty(str)) {
            return fileArr;
        }
        File file = new File(str);
        return (!file.exists() || !file.isDirectory()) ? fileArr : file.listFiles(fileFilter);
    }
}
