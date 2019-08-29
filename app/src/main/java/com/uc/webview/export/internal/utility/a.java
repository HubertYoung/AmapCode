package com.uc.webview.export.internal.utility;

import android.support.v4.view.InputDeviceCompat;
import com.iflytek.tts.TtsService.Tts;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.security.DigestException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: ProGuard */
public final class a {

    /* renamed from: com.uc.webview.export.internal.utility.a$a reason: collision with other inner class name */
    /* compiled from: ProGuard */
    public static class C0072a extends Exception {
        public C0072a(String str) {
            super(str);
        }

        public C0072a(String str, Throwable th) {
            super(str, th);
        }
    }

    /* compiled from: ProGuard */
    static class b extends c {
        private byte[] a;

        public b(X509Certificate x509Certificate, byte[] bArr) {
            super(x509Certificate);
            this.a = bArr;
        }

        public final byte[] getEncoded() throws CertificateEncodingException {
            return this.a;
        }
    }

    /* compiled from: ProGuard */
    static class c extends X509Certificate {
        private final X509Certificate a;

        public c(X509Certificate x509Certificate) {
            this.a = x509Certificate;
        }

        public Set<String> getCriticalExtensionOIDs() {
            return this.a.getCriticalExtensionOIDs();
        }

        public byte[] getExtensionValue(String str) {
            return this.a.getExtensionValue(str);
        }

        public Set<String> getNonCriticalExtensionOIDs() {
            return this.a.getNonCriticalExtensionOIDs();
        }

        public boolean hasUnsupportedCriticalExtension() {
            return this.a.hasUnsupportedCriticalExtension();
        }

        public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
            this.a.checkValidity();
        }

        public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
            this.a.checkValidity(date);
        }

        public int getVersion() {
            return this.a.getVersion();
        }

        public BigInteger getSerialNumber() {
            return this.a.getSerialNumber();
        }

        public Principal getIssuerDN() {
            return this.a.getIssuerDN();
        }

        public Principal getSubjectDN() {
            return this.a.getSubjectDN();
        }

        public Date getNotBefore() {
            return this.a.getNotBefore();
        }

        public Date getNotAfter() {
            return this.a.getNotAfter();
        }

        public byte[] getTBSCertificate() throws CertificateEncodingException {
            return this.a.getTBSCertificate();
        }

        public byte[] getSignature() {
            return this.a.getSignature();
        }

        public String getSigAlgName() {
            return this.a.getSigAlgName();
        }

        public String getSigAlgOID() {
            return this.a.getSigAlgOID();
        }

        public byte[] getSigAlgParams() {
            return this.a.getSigAlgParams();
        }

        public boolean[] getIssuerUniqueID() {
            return this.a.getIssuerUniqueID();
        }

        public boolean[] getSubjectUniqueID() {
            return this.a.getSubjectUniqueID();
        }

        public boolean[] getKeyUsage() {
            return this.a.getKeyUsage();
        }

        public int getBasicConstraints() {
            return this.a.getBasicConstraints();
        }

        public byte[] getEncoded() throws CertificateEncodingException {
            return this.a.getEncoded();
        }

        public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            this.a.verify(publicKey);
        }

        public void verify(PublicKey publicKey, String str) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            this.a.verify(publicKey, str);
        }

        public String toString() {
            return this.a.toString();
        }

        public PublicKey getPublicKey() {
            return this.a.getPublicKey();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0065  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r10) throws java.io.IOException {
        /*
            r0 = 0
            r1 = 0
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ a -> 0x0063, all -> 0x005b }
            java.lang.String r3 = "r"
            r2.<init>(r10, r3)     // Catch:{ a -> 0x0063, all -> 0x005b }
            long r8 = r2.length()     // Catch:{ a -> 0x0059, all -> 0x0057 }
            r3 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r10 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r10 <= 0) goto L_0x0018
            r2.close()
            return r0
        L_0x0018:
            java.nio.channels.FileChannel r4 = r2.getChannel()     // Catch:{ IOException -> 0x0042 }
            java.nio.channels.FileChannel$MapMode r5 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ IOException -> 0x0042 }
            r6 = 0
            java.nio.MappedByteBuffer r10 = r4.map(r5, r6, r8)     // Catch:{ IOException -> 0x0042 }
            java.nio.ByteOrder r1 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ a -> 0x0059, all -> 0x0057 }
            r10.order(r1)     // Catch:{ a -> 0x0059, all -> 0x0057 }
            int r1 = b(r10)     // Catch:{ a -> 0x0059, all -> 0x0057 }
            long r3 = a(r10, r1)     // Catch:{ a -> 0x0059, all -> 0x0057 }
            int r1 = (int) r3     // Catch:{ a -> 0x0059, all -> 0x0057 }
            int r3 = c(r10, r1)     // Catch:{ a -> 0x0059, all -> 0x0057 }
            java.nio.ByteBuffer r10 = a(r10, r3, r1)     // Catch:{ a -> 0x0059, all -> 0x0057 }
            e(r10)     // Catch:{ a -> 0x0059, all -> 0x0057 }
            r2.close()
            r10 = 1
            return r10
        L_0x0042:
            r10 = move-exception
            java.lang.Throwable r1 = r10.getCause()     // Catch:{ a -> 0x0059, all -> 0x0057 }
            boolean r1 = r1 instanceof java.lang.OutOfMemoryError     // Catch:{ a -> 0x0059, all -> 0x0057 }
            if (r1 == 0) goto L_0x004f
            r2.close()
            return r0
        L_0x004f:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ a -> 0x0059, all -> 0x0057 }
            java.lang.String r3 = "Failed to memory-map APK"
            r1.<init>(r3, r10)     // Catch:{ a -> 0x0059, all -> 0x0057 }
            throw r1     // Catch:{ a -> 0x0059, all -> 0x0057 }
        L_0x0057:
            r10 = move-exception
            goto L_0x005d
        L_0x0059:
            r1 = r2
            goto L_0x0063
        L_0x005b:
            r10 = move-exception
            r2 = r1
        L_0x005d:
            if (r2 == 0) goto L_0x0062
            r2.close()
        L_0x0062:
            throw r10
        L_0x0063:
            if (r1 == 0) goto L_0x0068
            r1.close()
        L_0x0068:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.a.a(java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0016  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.security.cert.X509Certificate[][] b(java.lang.String r3) throws com.uc.webview.export.internal.utility.a.C0072a, java.lang.SecurityException, java.io.IOException {
        /*
            r0 = 0
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ all -> 0x0013 }
            java.lang.String r2 = "r"
            r1.<init>(r3, r2)     // Catch:{ all -> 0x0013 }
            java.security.cert.X509Certificate[][] r3 = a(r1)     // Catch:{ all -> 0x0010 }
            r1.close()
            return r3
        L_0x0010:
            r3 = move-exception
            r0 = r1
            goto L_0x0014
        L_0x0013:
            r3 = move-exception
        L_0x0014:
            if (r0 == 0) goto L_0x0019
            r0.close()
        L_0x0019:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.a.b(java.lang.String):java.security.cert.X509Certificate[][]");
    }

    private static X509Certificate[][] a(RandomAccessFile randomAccessFile) throws C0072a, SecurityException, IOException {
        long length = randomAccessFile.length();
        if (length > 2147483647L) {
            StringBuilder sb = new StringBuilder("File too large: ");
            sb.append(randomAccessFile.length());
            sb.append(" bytes");
            throw new IOException(sb.toString());
        }
        try {
            MappedByteBuffer map = randomAccessFile.getChannel().map(MapMode.READ_ONLY, 0, length);
            map.load();
            return a((ByteBuffer) map);
        } catch (IOException e) {
            if (e.getCause() instanceof OutOfMemoryError) {
                throw new C0072a("Failed to memory-map APK", e);
            }
            throw new IOException("Failed to memory-map APK", e);
        }
    }

    public static X509Certificate[][] a(ByteBuffer byteBuffer) throws C0072a, SecurityException {
        ByteBuffer slice = byteBuffer.slice();
        slice.order(ByteOrder.LITTLE_ENDIAN);
        int b2 = b(slice);
        int a = (int) a(slice, b2);
        int c2 = c(slice, a);
        return a(slice, e(a(slice, c2, a)), c2, a, b2);
    }

    private static X509Certificate[][] a(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i, int i2, int i3) throws SecurityException {
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509");
            try {
                ByteBuffer c2 = c(byteBuffer2);
                int i4 = 0;
                while (c2.hasRemaining()) {
                    i4++;
                    try {
                        arrayList.add(a(c(c2), (Map<Integer, byte[]>) hashMap, instance));
                    } catch (IOException | SecurityException | BufferUnderflowException e) {
                        StringBuilder sb = new StringBuilder("Failed to parse/verify signer #");
                        sb.append(i4);
                        sb.append(" block");
                        throw new SecurityException(sb.toString(), e);
                    }
                }
                if (i4 <= 0) {
                    throw new SecurityException("No signers found");
                } else if (hashMap.isEmpty()) {
                    throw new SecurityException("No content digests found");
                } else {
                    a((Map<Integer, byte[]>) hashMap, byteBuffer, i, i2, i3);
                    return (X509Certificate[][]) arrayList.toArray(new X509Certificate[arrayList.size()][]);
                }
            } catch (IOException e2) {
                throw new SecurityException("Failed to read list of signers", e2);
            }
        } catch (CertificateException e3) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e3);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0233, code lost:
        r2.add(new com.uc.webview.export.internal.utility.a.b((java.security.cert.X509Certificate) r22.generateCertificate(new java.io.ByteArrayInputStream(r5)), r5));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x023c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x024d, code lost:
        throw new java.lang.SecurityException("Failed to decode certificate #".concat(java.lang.String.valueOf(r4)), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0252, code lost:
        if (r2.isEmpty() == false) goto L_0x025c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x025b, code lost:
        throw new java.lang.SecurityException("No certificates listed");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x026e, code lost:
        if (java.util.Arrays.equals(r3, ((java.security.cert.X509Certificate) r2.get(0)).getPublicKey().getEncoded()) != false) goto L_0x0278;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0277, code lost:
        throw new java.lang.SecurityException("Public key mismatch between certificate and signature record");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0284, code lost:
        return (java.security.cert.X509Certificate[]) r2.toArray(new java.security.cert.X509Certificate[r2.size()]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0285, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0286, code lost:
        r3 = new java.lang.StringBuilder("Failed to verify ");
        r3.append(r8);
        r3.append(" signature");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x029f, code lost:
        throw new java.lang.SecurityException(r3.toString(), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0073, code lost:
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0116, code lost:
        r5 = android.util.Pair.create(r5, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0140, code lost:
        r5 = android.util.Pair.create(r5, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0144, code lost:
        r8 = (java.lang.String) r5.first;
        r5 = (java.security.spec.AlgorithmParameterSpec) r5.second;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        r2 = java.security.KeyFactory.getInstance(r2).generatePublic(new java.security.spec.X509EncodedKeySpec(r3));
        r11 = java.security.Signature.getInstance(r8);
        r11.initVerify(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0160, code lost:
        if (r5 == null) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0162, code lost:
        r11.setParameter(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0165, code lost:
        r11.update(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x016c, code lost:
        if (r11.verify(r10) != false) goto L_0x0185;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x016e, code lost:
        r2 = new java.lang.StringBuilder();
        r2.append(r8);
        r2.append(" signature did not verify");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0184, code lost:
        throw new java.lang.SecurityException(r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0185, code lost:
        r1.clear();
        r2 = c(r1);
        r5 = new java.util.ArrayList();
        r8 = null;
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0197, code lost:
        if (r2.hasRemaining() == false) goto L_0x01d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0199, code lost:
        r7 = r7 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        r10 = c(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01a2, code lost:
        if (r10.remaining() >= 8) goto L_0x01ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01ab, code lost:
        throw new java.io.IOException("Record too short");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01ac, code lost:
        r11 = r10.getInt();
        r5.add(java.lang.Integer.valueOf(r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01b7, code lost:
        if (r11 != r9) goto L_0x0193;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01b9, code lost:
        r8 = d(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01be, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01cf, code lost:
        throw new java.io.IOException("Failed to parse digest record #".concat(java.lang.String.valueOf(r7)), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01d4, code lost:
        if (r4.equals(r5) != false) goto L_0x01de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01dd, code lost:
        throw new java.lang.SecurityException("Signature algorithms don't match between digests and signatures records");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01de, code lost:
        r2 = a(r9);
        r4 = r21.put(java.lang.Integer.valueOf(r2), r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01ee, code lost:
        if (r4 == null) goto L_0x0211;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01f4, code lost:
        if (java.security.MessageDigest.isEqual(r4, r8) != false) goto L_0x0211;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01f6, code lost:
        r3 = new java.lang.StringBuilder();
        r3.append(b(r2));
        r3.append(" contents digest does not match the digest specified by a preceding signer");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0210, code lost:
        throw new java.lang.SecurityException(r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0211, code lost:
        r1 = c(r1);
        r2 = new java.util.ArrayList();
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x021f, code lost:
        if (r1.hasRemaining() == false) goto L_0x024e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0221, code lost:
        r4 = r4 + 1;
        r5 = d(r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.security.cert.X509Certificate[] a(java.nio.ByteBuffer r20, java.util.Map<java.lang.Integer, byte[]> r21, java.security.cert.CertificateFactory r22) throws java.lang.SecurityException, java.io.IOException {
        /*
            java.nio.ByteBuffer r1 = c(r20)
            java.nio.ByteBuffer r2 = c(r20)
            byte[] r3 = d(r20)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r5 = -1
            r6 = 0
            r7 = 0
            r10 = r7
            r8 = 0
            r9 = -1
        L_0x0017:
            boolean r11 = r2.hasRemaining()
            r12 = 8
            r13 = 1
            if (r11 == 0) goto L_0x00ad
            int r8 = r8 + 1
            java.nio.ByteBuffer r11 = c(r2)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            int r14 = r11.remaining()     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            if (r14 >= r12) goto L_0x0034
            java.lang.SecurityException r1 = new java.lang.SecurityException     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            java.lang.String r2 = "Signature record too short"
            r1.<init>(r2)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            throw r1     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
        L_0x0034:
            int r12 = r11.getInt()     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r12)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            r4.add(r14)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            switch(r12) {
                case 257: goto L_0x0044;
                case 258: goto L_0x0044;
                case 259: goto L_0x0044;
                case 260: goto L_0x0044;
                case 513: goto L_0x0044;
                case 514: goto L_0x0044;
                case 769: goto L_0x0044;
                case 770: goto L_0x0044;
                default: goto L_0x0042;
            }     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
        L_0x0042:
            r14 = 0
            goto L_0x0045
        L_0x0044:
            r14 = 1
        L_0x0045:
            if (r14 != 0) goto L_0x0048
            goto L_0x0017
        L_0x0048:
            if (r9 == r5) goto L_0x0093
            int r14 = a(r12)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            int r15 = a(r9)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            switch(r14) {
                case 1: goto L_0x006b;
                case 2: goto L_0x0058;
                default: goto L_0x0055;
            }     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
        L_0x0055:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            goto L_0x0085
        L_0x0058:
            switch(r15) {
                case 1: goto L_0x0074;
                case 2: goto L_0x0073;
                default: goto L_0x005b;
            }     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
        L_0x005b:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            java.lang.String r2 = "Unknown digestAlgorithm2: "
            java.lang.String r3 = java.lang.String.valueOf(r15)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            r1.<init>(r2)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            throw r1     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
        L_0x006b:
            switch(r15) {
                case 1: goto L_0x0073;
                case 2: goto L_0x0071;
                default: goto L_0x006e;
            }     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
        L_0x006e:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            goto L_0x0077
        L_0x0071:
            r13 = -1
            goto L_0x0074
        L_0x0073:
            r13 = 0
        L_0x0074:
            if (r13 <= 0) goto L_0x0017
            goto L_0x0093
        L_0x0077:
            java.lang.String r2 = "Unknown digestAlgorithm2: "
            java.lang.String r3 = java.lang.String.valueOf(r15)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            r1.<init>(r2)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            throw r1     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
        L_0x0085:
            java.lang.String r2 = "Unknown digestAlgorithm1: "
            java.lang.String r3 = java.lang.String.valueOf(r14)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            r1.<init>(r2)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            throw r1     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
        L_0x0093:
            byte[] r9 = d(r11)     // Catch:{ IOException | BufferUnderflowException -> 0x009b }
            r10 = r9
            r9 = r12
            goto L_0x0017
        L_0x009b:
            r0 = move-exception
            r1 = r0
            java.lang.SecurityException r2 = new java.lang.SecurityException
            java.lang.String r3 = "Failed to parse signature record #"
            java.lang.String r4 = java.lang.String.valueOf(r8)
            java.lang.String r3 = r3.concat(r4)
            r2.<init>(r3, r1)
            throw r2
        L_0x00ad:
            if (r9 != r5) goto L_0x00c1
            if (r8 != 0) goto L_0x00b9
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.String r2 = "No signatures found"
            r1.<init>(r2)
            throw r1
        L_0x00b9:
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.String r2 = "No supported signatures found"
            r1.<init>(r2)
            throw r1
        L_0x00c1:
            switch(r9) {
                case 257: goto L_0x00e5;
                case 258: goto L_0x00e5;
                case 259: goto L_0x00e5;
                case 260: goto L_0x00e5;
                case 513: goto L_0x00e2;
                case 514: goto L_0x00e2;
                case 769: goto L_0x00df;
                case 770: goto L_0x00df;
                default: goto L_0x00c4;
            }
        L_0x00c4:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Unknown signature algorithm: 0x"
            r2.<init>(r3)
            r3 = r9 & -1
            long r3 = (long) r3
            java.lang.String r3 = java.lang.Long.toHexString(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x00df:
            java.lang.String r2 = "DSA"
            goto L_0x00e7
        L_0x00e2:
            java.lang.String r2 = "EC"
            goto L_0x00e7
        L_0x00e5:
            java.lang.String r2 = "RSA"
        L_0x00e7:
            switch(r9) {
                case 257: goto L_0x012e;
                case 258: goto L_0x011b;
                case 259: goto L_0x0114;
                case 260: goto L_0x0111;
                case 513: goto L_0x010e;
                case 514: goto L_0x010b;
                case 769: goto L_0x0108;
                case 770: goto L_0x0105;
                default: goto L_0x00ea;
            }
        L_0x00ea:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Unknown signature algorithm: 0x"
            r2.<init>(r3)
            r3 = r9 & -1
            long r3 = (long) r3
            java.lang.String r3 = java.lang.Long.toHexString(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0105:
            java.lang.String r5 = "SHA512withDSA"
            goto L_0x0116
        L_0x0108:
            java.lang.String r5 = "SHA256withDSA"
            goto L_0x0116
        L_0x010b:
            java.lang.String r5 = "SHA512withECDSA"
            goto L_0x0116
        L_0x010e:
            java.lang.String r5 = "SHA256withECDSA"
            goto L_0x0116
        L_0x0111:
            java.lang.String r5 = "SHA512withRSA"
            goto L_0x0116
        L_0x0114:
            java.lang.String r5 = "SHA256withRSA"
        L_0x0116:
            android.util.Pair r5 = android.util.Pair.create(r5, r7)
            goto L_0x0144
        L_0x011b:
            java.lang.String r5 = "SHA512withRSA/PSS"
            java.security.spec.PSSParameterSpec r8 = new java.security.spec.PSSParameterSpec
            java.lang.String r15 = "SHA-512"
            java.lang.String r16 = "MGF1"
            java.security.spec.MGF1ParameterSpec r17 = java.security.spec.MGF1ParameterSpec.SHA512
            r18 = 64
            r19 = 1
            r14 = r8
            r14.<init>(r15, r16, r17, r18, r19)
            goto L_0x0140
        L_0x012e:
            java.lang.String r5 = "SHA256withRSA/PSS"
            java.security.spec.PSSParameterSpec r8 = new java.security.spec.PSSParameterSpec
            java.lang.String r15 = "SHA-256"
            java.lang.String r16 = "MGF1"
            java.security.spec.MGF1ParameterSpec r17 = java.security.spec.MGF1ParameterSpec.SHA256
            r18 = 32
            r19 = 1
            r14 = r8
            r14.<init>(r15, r16, r17, r18, r19)
        L_0x0140:
            android.util.Pair r5 = android.util.Pair.create(r5, r8)
        L_0x0144:
            java.lang.Object r8 = r5.first
            java.lang.String r8 = (java.lang.String) r8
            java.lang.Object r5 = r5.second
            java.security.spec.AlgorithmParameterSpec r5 = (java.security.spec.AlgorithmParameterSpec) r5
            java.security.KeyFactory r2 = java.security.KeyFactory.getInstance(r2)     // Catch:{ InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException -> 0x0285 }
            java.security.spec.X509EncodedKeySpec r11 = new java.security.spec.X509EncodedKeySpec     // Catch:{ InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException -> 0x0285 }
            r11.<init>(r3)     // Catch:{ InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException -> 0x0285 }
            java.security.PublicKey r2 = r2.generatePublic(r11)     // Catch:{ InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException -> 0x0285 }
            java.security.Signature r11 = java.security.Signature.getInstance(r8)     // Catch:{ InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException -> 0x0285 }
            r11.initVerify(r2)     // Catch:{ InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException -> 0x0285 }
            if (r5 == 0) goto L_0x0165
            r11.setParameter(r5)     // Catch:{ InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException -> 0x0285 }
        L_0x0165:
            r11.update(r1)     // Catch:{ InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException -> 0x0285 }
            boolean r2 = r11.verify(r10)     // Catch:{ InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException -> 0x0285 }
            if (r2 != 0) goto L_0x0185
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r8)
            java.lang.String r3 = " signature did not verify"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0185:
            r1.clear()
            java.nio.ByteBuffer r2 = c(r1)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r8 = r7
            r7 = 0
        L_0x0193:
            boolean r10 = r2.hasRemaining()
            if (r10 == 0) goto L_0x01d0
            int r7 = r7 + r13
            java.nio.ByteBuffer r10 = c(r2)     // Catch:{ IOException | BufferUnderflowException -> 0x01be }
            int r11 = r10.remaining()     // Catch:{ IOException | BufferUnderflowException -> 0x01be }
            if (r11 >= r12) goto L_0x01ac
            java.io.IOException r1 = new java.io.IOException     // Catch:{ IOException | BufferUnderflowException -> 0x01be }
            java.lang.String r2 = "Record too short"
            r1.<init>(r2)     // Catch:{ IOException | BufferUnderflowException -> 0x01be }
            throw r1     // Catch:{ IOException | BufferUnderflowException -> 0x01be }
        L_0x01ac:
            int r11 = r10.getInt()     // Catch:{ IOException | BufferUnderflowException -> 0x01be }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r11)     // Catch:{ IOException | BufferUnderflowException -> 0x01be }
            r5.add(r14)     // Catch:{ IOException | BufferUnderflowException -> 0x01be }
            if (r11 != r9) goto L_0x0193
            byte[] r8 = d(r10)     // Catch:{ IOException | BufferUnderflowException -> 0x01be }
            goto L_0x0193
        L_0x01be:
            r0 = move-exception
            r1 = r0
            java.io.IOException r2 = new java.io.IOException
            java.lang.String r3 = "Failed to parse digest record #"
            java.lang.String r4 = java.lang.String.valueOf(r7)
            java.lang.String r3 = r3.concat(r4)
            r2.<init>(r3, r1)
            throw r2
        L_0x01d0:
            boolean r2 = r4.equals(r5)
            if (r2 != 0) goto L_0x01de
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.String r2 = "Signature algorithms don't match between digests and signatures records"
            r1.<init>(r2)
            throw r1
        L_0x01de:
            int r2 = a(r9)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r2)
            r5 = r21
            java.lang.Object r4 = r5.put(r4, r8)
            byte[] r4 = (byte[]) r4
            if (r4 == 0) goto L_0x0211
            boolean r4 = java.security.MessageDigest.isEqual(r4, r8)
            if (r4 != 0) goto L_0x0211
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r2 = b(r2)
            r3.append(r2)
            java.lang.String r2 = " contents digest does not match the digest specified by a preceding signer"
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            r1.<init>(r2)
            throw r1
        L_0x0211:
            java.nio.ByteBuffer r1 = c(r1)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r4 = 0
        L_0x021b:
            boolean r5 = r1.hasRemaining()
            if (r5 == 0) goto L_0x024e
            int r4 = r4 + r13
            byte[] r5 = d(r1)
            java.io.ByteArrayInputStream r7 = new java.io.ByteArrayInputStream     // Catch:{ CertificateException -> 0x023c }
            r7.<init>(r5)     // Catch:{ CertificateException -> 0x023c }
            r8 = r22
            java.security.cert.Certificate r7 = r8.generateCertificate(r7)     // Catch:{ CertificateException -> 0x023c }
            java.security.cert.X509Certificate r7 = (java.security.cert.X509Certificate) r7     // Catch:{ CertificateException -> 0x023c }
            com.uc.webview.export.internal.utility.a$b r9 = new com.uc.webview.export.internal.utility.a$b
            r9.<init>(r7, r5)
            r2.add(r9)
            goto L_0x021b
        L_0x023c:
            r0 = move-exception
            r1 = r0
            java.lang.SecurityException r2 = new java.lang.SecurityException
            java.lang.String r3 = "Failed to decode certificate #"
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r3 = r3.concat(r4)
            r2.<init>(r3, r1)
            throw r2
        L_0x024e:
            boolean r1 = r2.isEmpty()
            if (r1 == 0) goto L_0x025c
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.String r2 = "No certificates listed"
            r1.<init>(r2)
            throw r1
        L_0x025c:
            java.lang.Object r1 = r2.get(r6)
            java.security.cert.X509Certificate r1 = (java.security.cert.X509Certificate) r1
            java.security.PublicKey r1 = r1.getPublicKey()
            byte[] r1 = r1.getEncoded()
            boolean r1 = java.util.Arrays.equals(r3, r1)
            if (r1 != 0) goto L_0x0278
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.String r2 = "Public key mismatch between certificate and signature record"
            r1.<init>(r2)
            throw r1
        L_0x0278:
            int r1 = r2.size()
            java.security.cert.X509Certificate[] r1 = new java.security.cert.X509Certificate[r1]
            java.lang.Object[] r1 = r2.toArray(r1)
            java.security.cert.X509Certificate[] r1 = (java.security.cert.X509Certificate[]) r1
            return r1
        L_0x0285:
            r0 = move-exception
            r1 = r0
            java.lang.SecurityException r2 = new java.lang.SecurityException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Failed to verify "
            r3.<init>(r4)
            r3.append(r8)
            java.lang.String r4 = " signature"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.a.a(java.nio.ByteBuffer, java.util.Map, java.security.cert.CertificateFactory):java.security.cert.X509Certificate[]");
    }

    private static void a(Map<Integer, byte[]> map, ByteBuffer byteBuffer, int i, int i2, int i3) throws SecurityException {
        if (map.isEmpty()) {
            throw new SecurityException("No digests provided");
        }
        ByteBuffer a = a(byteBuffer, 0, i);
        ByteBuffer a2 = a(byteBuffer, i2, i3);
        byte[] bArr = new byte[(byteBuffer.capacity() - i3)];
        byteBuffer.position(i3);
        byteBuffer.get(bArr);
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(byteBuffer.order());
        long j = (long) i;
        n.a(wrap);
        int position = wrap.position() + 16;
        if (j < 0 || j > 4294967295L) {
            throw new IllegalArgumentException("uint32 value of out range: ".concat(String.valueOf(j)));
        }
        wrap.putInt(wrap.position() + position, (int) j);
        int[] iArr = new int[map.size()];
        int i4 = 0;
        for (Integer intValue : map.keySet()) {
            iArr[i4] = intValue.intValue();
            i4++;
        }
        try {
            Map<Integer, byte[]> a3 = a(iArr, new ByteBuffer[]{a, a2, wrap});
            for (Entry next : map.entrySet()) {
                int intValue2 = ((Integer) next.getKey()).intValue();
                if (!MessageDigest.isEqual((byte[]) next.getValue(), a3.get(Integer.valueOf(intValue2)))) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(b(intValue2));
                    sb.append(" digest of contents did not verify");
                    throw new SecurityException(sb.toString());
                }
            }
        } catch (DigestException e) {
            throw new SecurityException("Failed to compute digest(s) of contents", e);
        }
    }

    private static Map<Integer, byte[]> a(int[] iArr, ByteBuffer[] byteBufferArr) throws DigestException {
        int i;
        int i2;
        int[] iArr2 = iArr;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            i = 3;
            i2 = 1048576;
            if (i3 >= 3) {
                break;
            }
            i4 += ((byteBufferArr[i3].remaining() + 1048576) - 1) / 1048576;
            i3++;
        }
        HashMap hashMap = new HashMap(i4);
        for (int i5 : iArr2) {
            byte[] bArr = new byte[((c(i5) * i4) + 5)];
            bArr[0] = 90;
            a(i4, bArr);
            hashMap.put(Integer.valueOf(i5), bArr);
        }
        byte[] bArr2 = new byte[5];
        bArr2[0] = -91;
        int i6 = 0;
        int i7 = 0;
        while (i6 < i) {
            ByteBuffer byteBuffer = byteBufferArr[i6];
            while (byteBuffer.hasRemaining()) {
                ByteBuffer b2 = b(byteBuffer, Math.min(byteBuffer.remaining(), i2));
                int length = iArr2.length;
                int i8 = 0;
                while (i8 < length) {
                    int i9 = iArr2[i8];
                    String b3 = b(i9);
                    try {
                        MessageDigest instance = MessageDigest.getInstance(b3);
                        b2.clear();
                        a(b2.remaining(), bArr2);
                        instance.update(bArr2);
                        instance.update(b2);
                        int c2 = c(i9);
                        int digest = instance.digest((byte[]) hashMap.get(Integer.valueOf(i9)), (i7 * c2) + 5, c2);
                        if (digest != c2) {
                            StringBuilder sb = new StringBuilder("Unexpected output size of ");
                            sb.append(instance.getAlgorithm());
                            sb.append(" digest: ");
                            sb.append(digest);
                            throw new RuntimeException(sb.toString());
                        }
                        i8++;
                    } catch (NoSuchAlgorithmException e) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(b3);
                        sb2.append(" digest not supported");
                        throw new RuntimeException(sb2.toString(), e);
                    }
                }
                i7++;
                i2 = 1048576;
            }
            i6++;
            i = 3;
            i2 = 1048576;
        }
        HashMap hashMap2 = new HashMap(iArr2.length);
        for (Entry entry : hashMap.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            byte[] bArr3 = (byte[]) entry.getValue();
            String b4 = b(intValue);
            try {
                hashMap2.put(Integer.valueOf(intValue), MessageDigest.getInstance(b4).digest(bArr3));
            } catch (NoSuchAlgorithmException e2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(b4);
                sb3.append(" digest not supported");
                throw new RuntimeException(sb3.toString(), e2);
            }
        }
        return hashMap2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003d A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int b(java.nio.ByteBuffer r8) throws com.uc.webview.export.internal.utility.a.C0072a {
        /*
            com.uc.webview.export.internal.utility.n.a(r8)
            int r0 = r8.capacity()
            r1 = -1
            r2 = 22
            if (r0 >= r2) goto L_0x000f
            java.io.PrintStream r8 = java.lang.System.out
            goto L_0x0032
        L_0x000f:
            int r0 = r0 - r2
            r2 = 65535(0xffff, float:9.1834E-41)
            int r3 = java.lang.Math.min(r0, r2)
            r4 = 0
        L_0x0018:
            if (r4 >= r3) goto L_0x0032
            int r5 = r0 - r4
            int r6 = r8.getInt(r5)
            r7 = 101010256(0x6054b50, float:2.506985E-35)
            if (r6 != r7) goto L_0x002f
            int r6 = r5 + 20
            short r6 = r8.getShort(r6)
            r6 = r6 & r2
            if (r6 != r4) goto L_0x002f
            goto L_0x0033
        L_0x002f:
            int r4 = r4 + 1
            goto L_0x0018
        L_0x0032:
            r5 = -1
        L_0x0033:
            if (r5 != r1) goto L_0x003d
            com.uc.webview.export.internal.utility.a$a r8 = new com.uc.webview.export.internal.utility.a$a
            java.lang.String r0 = "Not an APK file: ZIP End of Central Directory record not found"
            r8.<init>(r0)
            throw r8
        L_0x003d:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.a.b(java.nio.ByteBuffer):int");
    }

    private static long a(ByteBuffer byteBuffer, int i) throws C0072a {
        n.a(byteBuffer);
        int i2 = i - 20;
        if (i2 >= 0 && byteBuffer.getInt(i2) == 117853008) {
            throw new C0072a("ZIP64 APK not supported");
        }
        ByteBuffer a = a(byteBuffer, i, byteBuffer.capacity());
        n.a(a);
        long a2 = n.a(a, a.position() + 16);
        long j = (long) i;
        if (a2 >= j) {
            StringBuilder sb = new StringBuilder("ZIP Central Directory offset out of range: ");
            sb.append(a2);
            sb.append(". ZIP End of Central Directory offset: ");
            sb.append(i);
            throw new C0072a(sb.toString());
        }
        n.a(a);
        if (n.a(a, a.position() + 12) + a2 == j) {
            return a2;
        }
        throw new C0072a("ZIP Central Directory is not immediately followed by End of Central Directory");
    }

    private static int a(int i) {
        switch (i) {
            case 257:
            case Tts.TTS_STATE_CREATED /*259*/:
            case InputDeviceCompat.SOURCE_DPAD /*513*/:
            case 769:
                return 1;
            case Tts.TTS_STATE_INVALID_DATA /*258*/:
            case Tts.TTS_STATE_DESTROY /*260*/:
            case 514:
            case 770:
                return 2;
            default:
                StringBuilder sb = new StringBuilder("Unknown signature algorithm: 0x");
                sb.append(Long.toHexString((long) (i & -1)));
                throw new IllegalArgumentException(sb.toString());
        }
    }

    private static String b(int i) {
        switch (i) {
            case 1:
                return "SHA-256";
            case 2:
                return "SHA-512";
            default:
                throw new IllegalArgumentException("Unknown content digest algorthm: ".concat(String.valueOf(i)));
        }
    }

    private static int c(int i) {
        switch (i) {
            case 1:
                return 32;
            case 2:
                return 64;
            default:
                throw new IllegalArgumentException("Unknown content digest algorthm: ".concat(String.valueOf(i)));
        }
    }

    private static ByteBuffer a(ByteBuffer byteBuffer, int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("start: ".concat(String.valueOf(i)));
        } else if (i2 < i) {
            StringBuilder sb = new StringBuilder("end < start: ");
            sb.append(i2);
            sb.append(" < ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else {
            int capacity = byteBuffer.capacity();
            if (i2 > byteBuffer.capacity()) {
                StringBuilder sb2 = new StringBuilder("end > capacity: ");
                sb2.append(i2);
                sb2.append(" > ");
                sb2.append(capacity);
                throw new IllegalArgumentException(sb2.toString());
            }
            int limit = byteBuffer.limit();
            int position = byteBuffer.position();
            try {
                byteBuffer.position(0);
                byteBuffer.limit(i2);
                byteBuffer.position(i);
                ByteBuffer slice = byteBuffer.slice();
                slice.order(byteBuffer.order());
                return slice;
            } finally {
                byteBuffer.position(0);
                byteBuffer.limit(limit);
                byteBuffer.position(position);
            }
        }
    }

    private static ByteBuffer b(ByteBuffer byteBuffer, int i) throws BufferUnderflowException {
        if (i < 0) {
            throw new IllegalArgumentException("size: ".concat(String.valueOf(i)));
        }
        int limit = byteBuffer.limit();
        int position = byteBuffer.position();
        int i2 = i + position;
        if (i2 < position || i2 > limit) {
            throw new BufferUnderflowException();
        }
        byteBuffer.limit(i2);
        try {
            ByteBuffer slice = byteBuffer.slice();
            slice.order(byteBuffer.order());
            byteBuffer.position(i2);
            return slice;
        } finally {
            byteBuffer.limit(limit);
        }
    }

    private static ByteBuffer c(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.remaining() < 4) {
            StringBuilder sb = new StringBuilder("Remaining buffer too short to contain length of length-prefixed field. Remaining: ");
            sb.append(byteBuffer.remaining());
            throw new IOException(sb.toString());
        }
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new IllegalArgumentException("Negative length");
        } else if (i <= byteBuffer.remaining()) {
            return b(byteBuffer, i);
        } else {
            StringBuilder sb2 = new StringBuilder("Length-prefixed field longer than remaining buffer. Field length: ");
            sb2.append(i);
            sb2.append(", remaining: ");
            sb2.append(byteBuffer.remaining());
            throw new IOException(sb2.toString());
        }
    }

    private static byte[] d(ByteBuffer byteBuffer) throws IOException {
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new IOException("Negative length");
        } else if (i > byteBuffer.remaining()) {
            StringBuilder sb = new StringBuilder("Underflow while reading length-prefixed value. Length: ");
            sb.append(i);
            sb.append(", available: ");
            sb.append(byteBuffer.remaining());
            throw new IOException(sb.toString());
        } else {
            byte[] bArr = new byte[i];
            byteBuffer.get(bArr);
            return bArr;
        }
    }

    private static void a(int i, byte[] bArr) {
        bArr[1] = (byte) (i & 255);
        bArr[2] = (byte) ((i >>> 8) & 255);
        bArr[3] = (byte) ((i >>> 16) & 255);
        bArr[4] = (byte) ((i >>> 24) & 255);
    }

    private static int c(ByteBuffer byteBuffer, int i) throws C0072a {
        f(byteBuffer);
        if (i < 32) {
            throw new C0072a("APK too small for APK Signing Block. ZIP Central Directory offset: ".concat(String.valueOf(i)));
        } else if (byteBuffer.getLong(i - 16) == 2334950737559900225L && byteBuffer.getLong(i - 8) == 3617552046287187010L) {
            long j = byteBuffer.getLong(i - 24);
            if (j < 24 || j > 2147483639) {
                throw new C0072a("APK Signing Block size out of range: ".concat(String.valueOf(j)));
            }
            int i2 = (int) j;
            int i3 = i - (i2 + 8);
            if (i3 < 0) {
                throw new C0072a("APK Signing Block offset out of range: ".concat(String.valueOf(i3)));
            }
            long j2 = byteBuffer.getLong(i3);
            if (j2 == ((long) i2)) {
                return i3;
            }
            StringBuilder sb = new StringBuilder("APK Signing Block sizes in header and footer do not match: ");
            sb.append(j2);
            sb.append(" vs ");
            sb.append(i2);
            throw new C0072a(sb.toString());
        } else {
            throw new C0072a("No APK Signing Block before ZIP Central Directory");
        }
    }

    private static ByteBuffer e(ByteBuffer byteBuffer) throws C0072a {
        f(byteBuffer);
        ByteBuffer a = a(byteBuffer, 8, byteBuffer.capacity() - 24);
        int i = 0;
        while (a.hasRemaining()) {
            i++;
            if (a.remaining() < 8) {
                throw new C0072a("Insufficient data to read size of APK Signing Block entry #".concat(String.valueOf(i)));
            }
            long j = a.getLong();
            if (j < 4 || j > 2147483647L) {
                StringBuilder sb = new StringBuilder("APK Signing Block entry #");
                sb.append(i);
                sb.append(" size out of range: ");
                sb.append(j);
                throw new C0072a(sb.toString());
            }
            int i2 = (int) j;
            int position = a.position() + i2;
            if (i2 > a.remaining()) {
                StringBuilder sb2 = new StringBuilder("APK Signing Block entry #");
                sb2.append(i);
                sb2.append(" size out of range: ");
                sb2.append(i2);
                sb2.append(", available: ");
                sb2.append(a.remaining());
                throw new C0072a(sb2.toString());
            } else if (a.getInt() == 1896449818) {
                return b(a, i2 - 4);
            } else {
                a.position(position);
            }
        }
        throw new C0072a("No APK Signature Scheme v2 block in APK Signing Block");
    }

    private static void f(ByteBuffer byteBuffer) {
        if (byteBuffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }
}
