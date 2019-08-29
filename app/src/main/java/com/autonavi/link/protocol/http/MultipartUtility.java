package com.autonavi.link.protocol.http;

import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MultipartUtility {
    public static final String LINE_FEED = "\r\n";
    private final String a;
    private HttpURLConnection b;
    private String c;
    private OutputStream d;
    private PrintWriter e;

    public MultipartUtility(String str, Proxy proxy, String str2) throws IOException {
        this.c = str2;
        StringBuilder sb = new StringBuilder("===");
        sb.append(System.currentTimeMillis());
        sb.append("===");
        this.a = sb.toString();
        URL url = new URL(str);
        if (proxy == null) {
            this.b = (HttpURLConnection) url.openConnection();
        } else {
            this.b = (HttpURLConnection) url.openConnection(proxy);
        }
        this.b.setUseCaches(false);
        this.b.setDoOutput(true);
        this.b.setDoInput(true);
        HttpURLConnection httpURLConnection = this.b;
        StringBuilder sb2 = new StringBuilder("multipart/form-data; boundary=");
        sb2.append(this.a);
        httpURLConnection.setRequestProperty("Content-Type", sb2.toString());
        this.b.setRequestProperty(H5AppHttpRequest.HEADER_UA, "CodeJava Agent");
        this.b.setRequestProperty("Test", "Bonjour");
        this.d = this.b.getOutputStream();
        this.e = new PrintWriter(new OutputStreamWriter(this.d, str2), true);
    }

    public MultipartUtility(String str, String str2) throws IOException {
        this.c = str2;
        StringBuilder sb = new StringBuilder("===");
        sb.append(System.currentTimeMillis());
        sb.append("===");
        this.a = sb.toString();
        this.b = (HttpURLConnection) new URL(str).openConnection();
        this.b.setUseCaches(false);
        this.b.setDoOutput(true);
        this.b.setDoInput(true);
        HttpURLConnection httpURLConnection = this.b;
        StringBuilder sb2 = new StringBuilder("multipart/form-data; boundary=");
        sb2.append(this.a);
        httpURLConnection.setRequestProperty("Content-Type", sb2.toString());
        this.b.setRequestProperty(H5AppHttpRequest.HEADER_UA, "CodeJava Agent");
        this.b.setRequestProperty("Test", "Bonjour");
        this.d = this.b.getOutputStream();
        this.e = new PrintWriter(new OutputStreamWriter(this.d, str2), true);
    }

    public void addFormField(String str, String str2) {
        PrintWriter printWriter = this.e;
        StringBuilder sb = new StringBuilder("--");
        sb.append(this.a);
        printWriter.append(sb.toString()).append(LINE_FEED);
        PrintWriter printWriter2 = this.e;
        StringBuilder sb2 = new StringBuilder("Content-Disposition: form-data; name=\"");
        sb2.append(str);
        sb2.append("\"");
        printWriter2.append(sb2.toString()).append(LINE_FEED);
        PrintWriter printWriter3 = this.e;
        StringBuilder sb3 = new StringBuilder("Content-Type: text/plain; charset=");
        sb3.append(this.c);
        printWriter3.append(sb3.toString()).append(LINE_FEED);
        this.e.append(LINE_FEED);
        this.e.append(str2).append(LINE_FEED);
        this.e.flush();
    }

    public void addFilePart(String str, File file, Progresser progresser) throws IOException {
        String name = file.getName();
        PrintWriter printWriter = this.e;
        StringBuilder sb = new StringBuilder("--");
        sb.append(this.a);
        printWriter.append(sb.toString()).append(LINE_FEED);
        PrintWriter printWriter2 = this.e;
        StringBuilder sb2 = new StringBuilder("Content-Disposition: form-data; name=\"");
        sb2.append(str);
        sb2.append("\"; filename=\"");
        sb2.append(name);
        sb2.append("\"");
        printWriter2.append(sb2.toString()).append(LINE_FEED);
        PrintWriter printWriter3 = this.e;
        StringBuilder sb3 = new StringBuilder("Content-Type: ");
        sb3.append(URLConnection.guessContentTypeFromName(name));
        printWriter3.append(sb3.toString()).append(LINE_FEED);
        this.e.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        this.e.append(LINE_FEED);
        this.e.flush();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read != -1) {
                this.d.write(bArr, 0, read);
            } else {
                this.d.flush();
                fileInputStream.close();
                this.e.append(LINE_FEED);
                this.e.flush();
                return;
            }
        }
    }

    public MultipartUtility(String str) throws IOException {
        this.c = str;
        StringBuilder sb = new StringBuilder("===");
        sb.append(System.currentTimeMillis());
        sb.append("===");
        this.a = sb.toString();
    }

    public List<String> addFilePart(String str, Proxy proxy, String str2, File file, Progresser progresser) throws IOException {
        return addFilePart(str, proxy, str2, null, file, 0, progresser);
    }

    public List<String> addFilePart(String str, Proxy proxy, String str2, String str3, File file, long j, Progresser progresser) throws IOException {
        String str4;
        DataOutputStream dataOutputStream;
        FileInputStream fileInputStream;
        MultipartUtility multipartUtility = this;
        Proxy proxy2 = proxy;
        long j2 = j;
        Progresser progresser2 = progresser;
        String encode = Uri.encode(TextUtils.isEmpty(str3) ? file.getName() : str3);
        StringBuilder sb = new StringBuilder("--");
        sb.append(multipartUtility.a);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder("Content-Disposition: form-data; name=\"");
        sb3.append(str2);
        sb3.append("\"; filename=\"");
        sb3.append(encode);
        sb3.append("\"");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder("Content-Type: ");
        sb5.append(URLConnection.guessContentTypeFromName(encode));
        String sb6 = sb5.toString();
        StringBuilder sb7 = new StringBuilder("--");
        sb7.append(multipartUtility.a);
        sb7.append("--");
        String sb8 = sb7.toString();
        FileInputStream fileInputStream2 = new FileInputStream(file);
        byte[] bArr = new byte[4096];
        int length = (int) ((((long) (((((sb2.length() + sb4.length()) + sb6.length()) + "Content-Transfer-Encoding: binary".length()) + sb8.length()) + 14)) + fileInputStream2.getChannel().size()) - j2);
        URL url = new URL(str);
        if (proxy2 == null) {
            multipartUtility.b = (HttpURLConnection) url.openConnection();
        } else {
            multipartUtility.b = (HttpURLConnection) url.openConnection(proxy2);
        }
        multipartUtility.b.setUseCaches(false);
        multipartUtility.b.setDoOutput(true);
        multipartUtility.b.setDoInput(true);
        HttpURLConnection httpURLConnection = multipartUtility.b;
        StringBuilder sb9 = new StringBuilder("multipart/form-data; boundary=");
        sb9.append(multipartUtility.a);
        httpURLConnection.setRequestProperty("Content-Type", sb9.toString());
        multipartUtility.b.setRequestProperty(H5AppHttpRequest.HEADER_UA, "CodeJava Agent");
        multipartUtility.b.setRequestProperty("Test", "Bonjour");
        if (VERSION.SDK_INT >= 19) {
            multipartUtility.b.setFixedLengthStreamingMode(length);
            str4 = sb8;
        } else {
            str4 = sb8;
            multipartUtility.b.setRequestProperty("Content-Length", String.format("%d", new Object[]{Integer.valueOf(length)}));
        }
        multipartUtility.d = multipartUtility.b.getOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(multipartUtility.d);
        dataOutputStream2.writeBytes(sb2);
        dataOutputStream2.writeBytes(LINE_FEED);
        dataOutputStream2.writeBytes(sb4);
        dataOutputStream2.writeBytes(LINE_FEED);
        dataOutputStream2.writeBytes(sb6);
        dataOutputStream2.writeBytes(LINE_FEED);
        dataOutputStream2.writeBytes("Content-Transfer-Encoding: binary");
        dataOutputStream2.writeBytes(LINE_FEED);
        dataOutputStream2.writeBytes(LINE_FEED);
        dataOutputStream2.flush();
        if (j2 > 0) {
            multipartUtility.a(fileInputStream2, j2);
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j3 = currentTimeMillis;
        int i = 0;
        int i2 = 0;
        while (true) {
            int read = fileInputStream2.read(bArr);
            if (read == -1) {
                break;
            }
            multipartUtility.d.write(bArr, 0, read);
            i += read;
            if (progresser2 != null) {
                long currentTimeMillis2 = System.currentTimeMillis() - j3;
                i2 += read;
                if (currentTimeMillis2 > 1000) {
                    long currentTimeMillis3 = System.currentTimeMillis();
                    fileInputStream = fileInputStream2;
                    dataOutputStream = dataOutputStream2;
                    String format = String.format("%.1f%%", new Object[]{Double.valueOf((((double) i) * 100.0d) / ((double) length))});
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append((((double) i2) / (((double) currentTimeMillis2) * 1024.0d)) * 1000.0d);
                    progresser2.onProgress(format, sb10.toString());
                    j3 = currentTimeMillis3;
                    i2 = 0;
                } else {
                    dataOutputStream = dataOutputStream2;
                    fileInputStream = fileInputStream2;
                }
                fileInputStream2 = fileInputStream;
                dataOutputStream2 = dataOutputStream;
                multipartUtility = this;
            } else {
                DataOutputStream dataOutputStream3 = dataOutputStream2;
            }
        }
        DataOutputStream dataOutputStream4 = dataOutputStream2;
        FileInputStream fileInputStream3 = fileInputStream2;
        if (progresser2 != null && length > 0) {
            StringBuilder sb11 = new StringBuilder();
            sb11.append((((double) length) / (((double) (System.currentTimeMillis() - currentTimeMillis)) * 1024.0d)) * 1000.0d);
            progresser2.onProgress("100", sb11.toString());
        }
        this.d.flush();
        fileInputStream3.close();
        DataOutputStream dataOutputStream5 = dataOutputStream4;
        dataOutputStream5.writeBytes(LINE_FEED);
        dataOutputStream5.flush();
        ArrayList arrayList = new ArrayList();
        dataOutputStream5.flush();
        dataOutputStream5.writeBytes(str4);
        dataOutputStream5.writeBytes(LINE_FEED);
        dataOutputStream5.close();
        int responseCode = this.b.getResponseCode();
        if (responseCode == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.b.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    arrayList.add(readLine);
                } else {
                    bufferedReader.close();
                    this.b.disconnect();
                    return arrayList;
                }
            }
        } else {
            throw new IOException("Server returned non-OK status: ".concat(String.valueOf(responseCode)));
        }
    }

    public List<String> addFilePart(String str, Proxy proxy, Map<String[], Long> map, Progresser progresser) throws IOException {
        char c2;
        char c3;
        Iterator<Entry<String[], Long>> it;
        String str2;
        Iterator<Entry<String[], Long>> it2;
        int i;
        Proxy proxy2 = proxy;
        Progresser progresser2 = progresser;
        Iterator<Entry<String[], Long>> it3 = map.entrySet().iterator();
        long j = 0;
        while (true) {
            c2 = 1;
            c3 = 0;
            if (!it3.hasNext()) {
                break;
            }
            Entry next = it3.next();
            String[] strArr = (String[]) next.getKey();
            if (strArr != null && strArr.length > 1) {
                String str3 = strArr[0];
                File file = new File(strArr[1]);
                String name = file.getName();
                long longValue = ((Long) next.getValue()).longValue();
                StringBuilder sb = new StringBuilder("--");
                sb.append(this.a);
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder("Content-Disposition: form-data; name=\"");
                sb3.append(str3);
                sb3.append("\"; filename=\"");
                sb3.append(name);
                sb3.append("\"");
                String sb4 = sb3.toString();
                StringBuilder sb5 = new StringBuilder("Content-Type: ");
                sb5.append(URLConnection.guessContentTypeFromName(name));
                j += (((long) ((((sb2.length() + sb4.length()) + sb5.toString().length()) + "Content-Transfer-Encoding: binary".length()) + 12)) + file.length()) - longValue;
            }
        }
        StringBuilder sb6 = new StringBuilder("--");
        sb6.append(this.a);
        sb6.append("--");
        String sb7 = sb6.toString();
        long length = j + ((long) (sb7.length() + 4));
        URL url = new URL(str);
        if (proxy2 == null) {
            this.b = (HttpURLConnection) url.openConnection();
        } else {
            this.b = (HttpURLConnection) url.openConnection(proxy2);
        }
        this.b.setUseCaches(false);
        this.b.setDoOutput(true);
        this.b.setDoInput(true);
        HttpURLConnection httpURLConnection = this.b;
        StringBuilder sb8 = new StringBuilder("multipart/form-data; boundary=");
        sb8.append(this.a);
        httpURLConnection.setRequestProperty("Content-Type", sb8.toString());
        this.b.setRequestProperty(H5AppHttpRequest.HEADER_UA, "CodeJava Agent");
        this.b.setRequestProperty("Test", "Bonjour");
        if (VERSION.SDK_INT >= 19) {
            this.b.setFixedLengthStreamingMode(length);
        } else {
            this.b.setRequestProperty("Content-Length", String.format("%d", new Object[]{Long.valueOf(length)}));
        }
        this.d = this.b.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(this.d);
        Iterator<Entry<String[], Long>> it4 = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry next2 = it.next();
            String[] strArr2 = (String[]) next2.getKey();
            String str4 = strArr2[c3];
            File file2 = new File(strArr2[c2]);
            String name2 = file2.getName();
            long longValue2 = ((Long) next2.getValue()).longValue();
            StringBuilder sb9 = new StringBuilder("--");
            sb9.append(this.a);
            String sb10 = sb9.toString();
            StringBuilder sb11 = new StringBuilder("Content-Disposition: form-data; name=\"");
            sb11.append(str4);
            sb11.append("\"; filename=\"");
            sb11.append(name2);
            sb11.append("\"");
            String sb12 = sb11.toString();
            StringBuilder sb13 = new StringBuilder("Content-Type: ");
            sb13.append(URLConnection.guessContentTypeFromName(name2));
            String sb14 = sb13.toString();
            FileInputStream fileInputStream = new FileInputStream(file2);
            byte[] bArr = new byte[4096];
            dataOutputStream.writeBytes(sb10);
            dataOutputStream.writeBytes(LINE_FEED);
            dataOutputStream.writeBytes(sb12);
            dataOutputStream.writeBytes(LINE_FEED);
            dataOutputStream.writeBytes(sb14);
            dataOutputStream.writeBytes(LINE_FEED);
            dataOutputStream.writeBytes("Content-Transfer-Encoding: binary");
            dataOutputStream.writeBytes(LINE_FEED);
            dataOutputStream.writeBytes(LINE_FEED);
            dataOutputStream.flush();
            if (longValue2 > 0) {
                a(fileInputStream, longValue2);
            }
            long currentTimeMillis = System.currentTimeMillis();
            long j2 = currentTimeMillis;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                int read = fileInputStream.read(bArr);
                it2 = it;
                if (read == -1) {
                    break;
                }
                String str5 = str2;
                this.d.write(bArr, 0, read);
                i2 += read;
                if (progresser2 != null) {
                    FileInputStream fileInputStream2 = fileInputStream;
                    byte[] bArr2 = bArr;
                    long currentTimeMillis2 = System.currentTimeMillis() - j2;
                    i3 += read;
                    if (currentTimeMillis2 > 1000) {
                        long currentTimeMillis3 = System.currentTimeMillis();
                        i = i2;
                        String format = String.format("%.1f%%", new Object[]{Double.valueOf((((double) i2) * 100.0d) / ((double) length))});
                        StringBuilder sb15 = new StringBuilder();
                        sb15.append((((double) i3) / (((double) currentTimeMillis2) * 1024.0d)) * 1000.0d);
                        progresser2.onProgress(format, sb15.toString());
                        j2 = currentTimeMillis3;
                        i3 = 0;
                    } else {
                        i = i2;
                    }
                    it = it2;
                    str2 = str5;
                    fileInputStream = fileInputStream2;
                    bArr = bArr2;
                    i2 = i;
                } else {
                    int i4 = i2;
                    it = it2;
                    str2 = str5;
                }
            }
            String str6 = str2;
            FileInputStream fileInputStream3 = fileInputStream;
            if (progresser2 != null) {
                if (length > 0) {
                    StringBuilder sb16 = new StringBuilder();
                    sb16.append((((double) length) / (((double) (System.currentTimeMillis() - currentTimeMillis)) * 1024.0d)) * 1000.0d);
                    progresser2.onProgress("100", sb16.toString());
                }
            }
            this.d.flush();
            fileInputStream3.close();
            dataOutputStream.writeBytes(LINE_FEED);
            dataOutputStream.flush();
            it4 = it2;
            sb7 = str6;
            c2 = 1;
            c3 = 0;
        }
        ArrayList arrayList = new ArrayList();
        dataOutputStream.writeBytes(LINE_FEED);
        dataOutputStream.flush();
        dataOutputStream.writeBytes(str2);
        dataOutputStream.writeBytes(LINE_FEED);
        dataOutputStream.close();
        int responseCode = this.b.getResponseCode();
        if (responseCode == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.b.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    arrayList.add(readLine);
                } else {
                    bufferedReader.close();
                    this.b.disconnect();
                    return arrayList;
                }
            }
        } else {
            throw new IOException("Server returned non-OK status: ".concat(String.valueOf(responseCode)));
        }
    }

    private long a(InputStream inputStream, long j) {
        byte[] bArr = new byte[2048];
        if (j <= 0) {
            return 0;
        }
        long j2 = j;
        int i = 0;
        while (j2 > 0) {
            try {
                i = inputStream.read(bArr, 0, (int) Math.min(2048, j2));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            if (i < 0) {
                break;
            }
            j2 -= (long) i;
        }
        return j - j2;
    }

    public void addHeaderField(String str, String str2) {
        PrintWriter printWriter = this.e;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(str2);
        printWriter.append(sb.toString()).append(LINE_FEED);
        this.e.flush();
    }

    public List<String> finish() throws IOException {
        ArrayList arrayList = new ArrayList();
        this.e.append(LINE_FEED).flush();
        PrintWriter printWriter = this.e;
        StringBuilder sb = new StringBuilder("--");
        sb.append(this.a);
        sb.append("--");
        printWriter.append(sb.toString()).append(LINE_FEED);
        this.e.close();
        int responseCode = this.b.getResponseCode();
        if (responseCode == 200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.b.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    arrayList.add(readLine);
                } else {
                    bufferedReader.close();
                    this.b.disconnect();
                    return arrayList;
                }
            }
        } else {
            throw new IOException("Server returned non-OK status: ".concat(String.valueOf(responseCode)));
        }
    }
}
