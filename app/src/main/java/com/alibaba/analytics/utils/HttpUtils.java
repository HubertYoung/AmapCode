package com.alibaba.analytics.utils;

public final class HttpUtils {
    public static final int HTTP_REQ_TYPE_GET = 1;
    public static final int HTTP_REQ_TYPE_POST_FORM_DATA = 2;
    public static final int HTTP_REQ_TYPE_POST_URL_ENCODED = 3;
    private static final int MAX_CONNECTION_TIME_OUT = 10000;
    public static final int MAX_READ_CONNECTION_STREAM_TIME_OUT = 60000;
    private static final String POST_Field_BOTTOM = "--GJircTeP--\r\n";
    private static final String POST_Field_TOP = "--GJircTeP\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\nContent-Type: application/octet-stream \r\n\r\n";

    public static class HttpResponse {
        public byte[] data = null;
        public int httpResponseCode = -1;
        public long rt = 0;
    }

    static {
        System.setProperty("http.keepAlive", "true");
    }

    /* JADX WARNING: Removed duplicated region for block: B:116:0x01e0 A[Catch:{ IOException -> 0x01f9 }, LOOP:1: B:114:0x01d9->B:116:0x01e0, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x01e4 A[EDGE_INSN: B:117:0x01e4->B:118:? ?: BREAK  
    EDGE_INSN: B:117:0x01e4->B:118:? ?: BREAK  , SYNTHETIC, Splitter:B:117:0x01e4] */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x01f2  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0200 A[SYNTHETIC, Splitter:B:129:0x0200] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x020d A[SYNTHETIC, Splitter:B:137:0x020d] */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x0237 A[SYNTHETIC, Splitter:B:157:0x0237] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alibaba.analytics.utils.HttpUtils.HttpResponse sendRequest(int r18, java.lang.String r19, java.util.Map<java.lang.String, java.lang.Object> r20, boolean r21) {
        /*
            r1 = r18
            r2 = r20
            com.alibaba.analytics.utils.HttpUtils$HttpResponse r4 = new com.alibaba.analytics.utils.HttpUtils$HttpResponse
            r4.<init>()
            boolean r5 = android.text.TextUtils.isEmpty(r19)
            if (r5 == 0) goto L_0x0010
            return r4
        L_0x0010:
            java.net.URL r5 = new java.net.URL     // Catch:{ MalformedURLException -> 0x024d, IOException -> 0x0247 }
            r6 = r19
            r5.<init>(r6)     // Catch:{ MalformedURLException -> 0x024d, IOException -> 0x0247 }
            java.net.URLConnection r5 = r5.openConnection()     // Catch:{ MalformedURLException -> 0x024d, IOException -> 0x0247 }
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ MalformedURLException -> 0x024d, IOException -> 0x0247 }
            if (r5 == 0) goto L_0x0246
            r6 = 1
            r7 = 3
            r8 = 2
            if (r1 == r8) goto L_0x0026
            if (r1 != r7) goto L_0x0029
        L_0x0026:
            r5.setDoOutput(r6)
        L_0x0029:
            r5.setDoInput(r6)
            if (r1 == r8) goto L_0x0037
            if (r1 != r7) goto L_0x0031
            goto L_0x0037
        L_0x0031:
            java.lang.String r9 = "GET"
            r5.setRequestMethod(r9)     // Catch:{ ProtocolException -> 0x0240 }
            goto L_0x003c
        L_0x0037:
            java.lang.String r9 = "POST"
            r5.setRequestMethod(r9)     // Catch:{ ProtocolException -> 0x0240 }
        L_0x003c:
            r9 = 0
            r5.setUseCaches(r9)
            r10 = 10000(0x2710, float:1.4013E-41)
            r5.setConnectTimeout(r10)
            r10 = 60000(0xea60, float:8.4078E-41)
            r5.setReadTimeout(r10)
            java.lang.String r10 = "Connection"
            java.lang.String r11 = "close"
            r5.setRequestProperty(r10, r11)
            if (r21 == 0) goto L_0x005b
            java.lang.String r10 = "Accept-Encoding"
            java.lang.String r11 = "gzip,deflate"
            r5.setRequestProperty(r10, r11)
        L_0x005b:
            r5.setInstanceFollowRedirects(r6)
            if (r1 == r8) goto L_0x0066
            if (r1 != r7) goto L_0x0063
            goto L_0x0066
        L_0x0063:
            r10 = 0
            goto L_0x015e
        L_0x0066:
            if (r1 != r8) goto L_0x0070
            java.lang.String r11 = "Content-Type"
            java.lang.String r12 = "multipart/form-data; boundary=GJircTeP"
            r5.setRequestProperty(r11, r12)
            goto L_0x0079
        L_0x0070:
            if (r1 != r7) goto L_0x0079
            java.lang.String r11 = "Content-Type"
            java.lang.String r12 = "application/x-www-form-urlencoded"
            r5.setRequestProperty(r11, r12)
        L_0x0079:
            if (r2 == 0) goto L_0x014f
            int r11 = r20.size()
            if (r11 <= 0) goto L_0x014f
            java.io.ByteArrayOutputStream r11 = new java.io.ByteArrayOutputStream
            r11.<init>()
            java.util.Set r12 = r20.keySet()
            int r13 = r12.size()
            java.lang.String[] r13 = new java.lang.String[r13]
            r12.toArray(r13)
            com.alibaba.analytics.utils.KeyArraySorter r12 = com.alibaba.analytics.utils.KeyArraySorter.getInstance()
            java.lang.String[] r12 = r12.sortResourcesList(r13, r6)
            int r13 = r12.length
            r14 = 0
        L_0x009d:
            if (r14 >= r13) goto L_0x0138
            r15 = r12[r14]
            if (r1 != r8) goto L_0x00dc
            java.lang.Object r16 = r2.get(r15)
            r10 = r16
            byte[] r10 = (byte[]) r10
            if (r10 == 0) goto L_0x00d9
            java.lang.String r7 = "--GJircTeP\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\nContent-Type: application/octet-stream \r\n\r\n"
            java.lang.Object[] r6 = new java.lang.Object[r8]     // Catch:{ IOException -> 0x00d1 }
            r6[r9] = r15     // Catch:{ IOException -> 0x00d1 }
            r16 = 1
            r6[r16] = r15     // Catch:{ IOException -> 0x00cf }
            java.lang.String r6 = java.lang.String.format(r7, r6)     // Catch:{ IOException -> 0x00cf }
            byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x00cf }
            r11.write(r6)     // Catch:{ IOException -> 0x00cf }
            r11.write(r10)     // Catch:{ IOException -> 0x00cf }
            java.lang.String r6 = "\r\n"
            byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x00cf }
            r11.write(r6)     // Catch:{ IOException -> 0x00cf }
            goto L_0x0132
        L_0x00cf:
            r0 = move-exception
            goto L_0x00d4
        L_0x00d1:
            r0 = move-exception
            r16 = 1
        L_0x00d4:
            r6 = r0
            r6.printStackTrace()
            goto L_0x0132
        L_0x00d9:
            r16 = 1
            goto L_0x0132
        L_0x00dc:
            r6 = 3
            r16 = 1
            if (r1 != r6) goto L_0x0132
            java.lang.Object r6 = r2.get(r15)
            java.lang.String r6 = (java.lang.String) r6
            int r7 = r11.size()
            if (r7 <= 0) goto L_0x0111
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x010b }
            java.lang.String r10 = "&"
            r7.<init>(r10)     // Catch:{ IOException -> 0x010b }
            r7.append(r15)     // Catch:{ IOException -> 0x010b }
            java.lang.String r10 = "="
            r7.append(r10)     // Catch:{ IOException -> 0x010b }
            r7.append(r6)     // Catch:{ IOException -> 0x010b }
            java.lang.String r6 = r7.toString()     // Catch:{ IOException -> 0x010b }
            byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x010b }
            r11.write(r6)     // Catch:{ IOException -> 0x010b }
            goto L_0x0132
        L_0x010b:
            r0 = move-exception
            r6 = r0
            r6.printStackTrace()
            goto L_0x0132
        L_0x0111:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x012d }
            r7.<init>()     // Catch:{ IOException -> 0x012d }
            r7.append(r15)     // Catch:{ IOException -> 0x012d }
            java.lang.String r10 = "="
            r7.append(r10)     // Catch:{ IOException -> 0x012d }
            r7.append(r6)     // Catch:{ IOException -> 0x012d }
            java.lang.String r6 = r7.toString()     // Catch:{ IOException -> 0x012d }
            byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x012d }
            r11.write(r6)     // Catch:{ IOException -> 0x012d }
            goto L_0x0132
        L_0x012d:
            r0 = move-exception
            r6 = r0
            r6.printStackTrace()
        L_0x0132:
            int r14 = r14 + 1
            r6 = 1
            r7 = 3
            goto L_0x009d
        L_0x0138:
            if (r1 != r8) goto L_0x0149
            java.lang.String r2 = "--GJircTeP--\r\n"
            byte[] r2 = r2.getBytes()     // Catch:{ IOException -> 0x0144 }
            r11.write(r2)     // Catch:{ IOException -> 0x0144 }
            goto L_0x0149
        L_0x0144:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x0149:
            byte[] r2 = r11.toByteArray()
            r10 = r2
            goto L_0x0150
        L_0x014f:
            r10 = 0
        L_0x0150:
            if (r10 == 0) goto L_0x0154
            int r2 = r10.length
            goto L_0x0155
        L_0x0154:
            r2 = 0
        L_0x0155:
            java.lang.String r6 = "Content-Length"
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r5.setRequestProperty(r6, r2)
        L_0x015e:
            long r6 = java.lang.System.currentTimeMillis()
            r5.connect()     // Catch:{ Exception -> 0x021a, all -> 0x0216 }
            if (r1 == r8) goto L_0x016a
            r2 = 3
            if (r1 != r2) goto L_0x0188
        L_0x016a:
            if (r10 == 0) goto L_0x0188
            int r1 = r10.length     // Catch:{ Exception -> 0x021a, all -> 0x0216 }
            if (r1 <= 0) goto L_0x0188
            java.io.DataOutputStream r1 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x021a, all -> 0x0216 }
            java.io.OutputStream r2 = r5.getOutputStream()     // Catch:{ Exception -> 0x021a, all -> 0x0216 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x021a, all -> 0x0216 }
            r1.write(r10)     // Catch:{ Exception -> 0x0183, all -> 0x017f }
            r1.flush()     // Catch:{ Exception -> 0x0183, all -> 0x017f }
            goto L_0x0189
        L_0x017f:
            r0 = move-exception
            r10 = r1
            goto L_0x0234
        L_0x0183:
            r0 = move-exception
            r10 = r1
            r1 = r0
            goto L_0x021d
        L_0x0188:
            r1 = 0
        L_0x0189:
            if (r1 == 0) goto L_0x0193
            r1.close()     // Catch:{ IOException -> 0x018f }
            goto L_0x0193
        L_0x018f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0193:
            int r1 = r5.getResponseCode()     // Catch:{ IOException -> 0x019a }
            r4.httpResponseCode = r1     // Catch:{ IOException -> 0x019a }
            goto L_0x019f
        L_0x019a:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L_0x019f:
            long r1 = java.lang.System.currentTimeMillis()
            long r1 = r1 - r6
            r4.rt = r1
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
            if (r21 == 0) goto L_0x01cb
            java.lang.String r2 = "gzip"
            java.lang.String r3 = r5.getContentEncoding()     // Catch:{ IOException -> 0x01c7, all -> 0x01c3 }
            boolean r2 = r2.equals(r3)     // Catch:{ IOException -> 0x01c7, all -> 0x01c3 }
            if (r2 == 0) goto L_0x01cb
            java.util.zip.GZIPInputStream r2 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x01c7, all -> 0x01c3 }
            java.io.InputStream r3 = r5.getInputStream()     // Catch:{ IOException -> 0x01c7, all -> 0x01c3 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x01c7, all -> 0x01c3 }
            goto L_0x01d4
        L_0x01c3:
            r0 = move-exception
            r1 = r0
            r10 = 0
            goto L_0x020b
        L_0x01c7:
            r0 = move-exception
            r1 = r0
            r10 = 0
            goto L_0x01fb
        L_0x01cb:
            java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ IOException -> 0x01c7, all -> 0x01c3 }
            java.io.InputStream r3 = r5.getInputStream()     // Catch:{ IOException -> 0x01c7, all -> 0x01c3 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x01c7, all -> 0x01c3 }
        L_0x01d4:
            r10 = r2
            r2 = 2048(0x800, float:2.87E-42)
            byte[] r3 = new byte[r2]     // Catch:{ IOException -> 0x01f9 }
        L_0x01d9:
            int r5 = r10.read(r3, r9, r2)     // Catch:{ IOException -> 0x01f9 }
            r6 = -1
            if (r5 == r6) goto L_0x01e4
            r1.write(r3, r9, r5)     // Catch:{ IOException -> 0x01f9 }
            goto L_0x01d9
        L_0x01e4:
            r10.close()     // Catch:{ Exception -> 0x01e8 }
            goto L_0x01ec
        L_0x01e8:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01ec:
            int r2 = r1.size()
            if (r2 <= 0) goto L_0x0246
            byte[] r1 = r1.toByteArray()
            r4.data = r1
            goto L_0x0246
        L_0x01f9:
            r0 = move-exception
            r1 = r0
        L_0x01fb:
            r1.printStackTrace()     // Catch:{ all -> 0x0209 }
            if (r10 == 0) goto L_0x0208
            r10.close()     // Catch:{ Exception -> 0x0204 }
            goto L_0x0208
        L_0x0204:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0208:
            return r4
        L_0x0209:
            r0 = move-exception
            r1 = r0
        L_0x020b:
            if (r10 == 0) goto L_0x0215
            r10.close()     // Catch:{ Exception -> 0x0211 }
            goto L_0x0215
        L_0x0211:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0215:
            throw r1
        L_0x0216:
            r0 = move-exception
            r1 = r0
            r10 = 0
            goto L_0x0235
        L_0x021a:
            r0 = move-exception
            r1 = r0
            r10 = 0
        L_0x021d:
            r1.printStackTrace()     // Catch:{ all -> 0x0233 }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0233 }
            r3 = 0
            long r1 = r1 - r6
            r4.rt = r1     // Catch:{ all -> 0x0233 }
            if (r10 == 0) goto L_0x0232
            r10.close()     // Catch:{ IOException -> 0x022e }
            goto L_0x0232
        L_0x022e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0232:
            return r4
        L_0x0233:
            r0 = move-exception
        L_0x0234:
            r1 = r0
        L_0x0235:
            if (r10 == 0) goto L_0x023f
            r10.close()     // Catch:{ IOException -> 0x023b }
            goto L_0x023f
        L_0x023b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x023f:
            throw r1
        L_0x0240:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            return r4
        L_0x0246:
            return r4
        L_0x0247:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            return r4
        L_0x024d:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.utils.HttpUtils.sendRequest(int, java.lang.String, java.util.Map, boolean):com.alibaba.analytics.utils.HttpUtils$HttpResponse");
    }
}
