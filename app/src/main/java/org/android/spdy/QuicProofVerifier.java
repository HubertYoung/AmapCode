package org.android.spdy;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Set;

public class QuicProofVerifier {
    private static final int DECODE_C = 1;
    private static final int DECODE_EQ = 3;
    private static final int DECODE_N = 2;
    private static final int DECODE_RIGHT = 4;
    private static String kProofSignatureLabel = "QUIC CHLO and server config signature\u0000";
    private static String kProofSignatureLabelOld = "QUIC server config signature\u0000";

    public static int VerifyProof(String str, int i, String str2, int i2, String str3, String[] strArr, String str4, String str5) {
        CertificateFactory certificateFactory = AndroidTrustAnchors.a().d;
        if (certificateFactory == null) {
            return 0;
        }
        X509Certificate x509Certificate = null;
        try {
            x509Certificate = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(strArr[0].getBytes("ISO-8859-1")));
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (verifySignature(str2, i2, str3, x509Certificate, str5) == 0) {
            return 0;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(x509Certificate);
        Set<TrustAnchor> LoadFromAndroidSystem = LoadFromAndroidSystem(certificateFactory);
        if (LoadFromAndroidSystem == null) {
            return 0;
        }
        int i3 = 1;
        while (i3 < strArr.length) {
            try {
                arrayList.add((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(strArr[1].getBytes("ISO-8859-1"))));
                i3++;
            } catch (UnsupportedEncodingException e4) {
                e4.printStackTrace();
            } catch (CertificateException e5) {
                e5.printStackTrace();
            } catch (InvalidAlgorithmParameterException e6) {
                e6.printStackTrace();
            } catch (NoSuchAlgorithmException e7) {
                e7.printStackTrace();
            } catch (CertPathValidatorException e8) {
                e8.printStackTrace();
            }
        }
        CertPath generateCertPath = certificateFactory.generateCertPath(arrayList);
        PKIXParameters pKIXParameters = new PKIXParameters(LoadFromAndroidSystem);
        pKIXParameters.setRevocationEnabled(false);
        CertPathValidator.getInstance("PKIX").validate(generateCertPath, pKIXParameters);
        String[] split = x509Certificate.getSubjectDN().toString().split(",");
        StringBuilder sb = new StringBuilder();
        int length = split.length;
        int i4 = 0;
        while (i4 < length && !getCNfromSubject(split[i4], sb)) {
            i4++;
        }
        String sb2 = sb.toString();
        if (str.equals(sb2) || (sb2.startsWith("*") && str.endsWith(sb2.substring(2)))) {
            return 1;
        }
        return 0;
    }

    private static boolean getCNfromSubject(String str, StringBuilder sb) {
        char[] charArray = str.toCharArray();
        sb.setLength(0);
        char c = 1;
        boolean z = false;
        for (char c2 : charArray) {
            switch (c) {
                case 1:
                    if (c2 != 'C') {
                        break;
                    } else {
                        c = 2;
                        break;
                    }
                case 2:
                    c = 3;
                    break;
                case 3:
                    if (c2 != ' ') {
                        if (c2 == '=') {
                            c = 4;
                            z = true;
                            break;
                        } else {
                            c = 1;
                            break;
                        }
                    } else {
                        break;
                    }
                case 4:
                    if (c2 == ' ') {
                        break;
                    } else {
                        sb.append(c2);
                        break;
                    }
            }
        }
        return z;
    }

    private static Set<TrustAnchor> LoadFromAndroidSystem(CertificateFactory certificateFactory) {
        return AndroidTrustAnchors.a().c;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0040, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
        r1.printStackTrace();
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0046, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0047, code lost:
        r9 = r1;
        r1 = null;
        r0 = r9;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0040 A[ExcHandler: InvalidAlgorithmParameterException (r1v4 'e' java.security.InvalidAlgorithmParameterException A[CUSTOM_DECLARE]), Splitter:B:5:0x000b] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x004f A[SYNTHETIC, Splitter:B:20:0x004f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int verifySignature(java.lang.String r10, int r11, java.lang.String r12, java.security.cert.X509Certificate r13, java.lang.String r14) {
        /*
            r11 = 0
            if (r13 == 0) goto L_0x00a3
            r13.checkValidity()     // Catch:{ CertificateExpiredException -> 0x009e, CertificateNotYetValidException -> 0x0099 }
            java.security.PublicKey r13 = r13.getPublicKey()
            r0 = 0
            java.lang.String r1 = r13.getAlgorithm()     // Catch:{ NoSuchAlgorithmException -> 0x0046, InvalidAlgorithmParameterException -> 0x0040 }
            java.lang.String r2 = "RSA"
            boolean r1 = r1.contains(r2)     // Catch:{ NoSuchAlgorithmException -> 0x0046, InvalidAlgorithmParameterException -> 0x0040 }
            if (r1 == 0) goto L_0x0037
            java.lang.String r1 = "SHA256withRSA/PSS"
            java.security.Signature r1 = java.security.Signature.getInstance(r1)     // Catch:{ NoSuchAlgorithmException -> 0x0046, InvalidAlgorithmParameterException -> 0x0040 }
            java.security.spec.PSSParameterSpec r8 = new java.security.spec.PSSParameterSpec     // Catch:{ NoSuchAlgorithmException -> 0x0035, InvalidAlgorithmParameterException -> 0x0040 }
            java.lang.String r3 = "SHA-256"
            java.lang.String r4 = "MGF1"
            java.security.spec.MGF1ParameterSpec r5 = new java.security.spec.MGF1ParameterSpec     // Catch:{ NoSuchAlgorithmException -> 0x0035, InvalidAlgorithmParameterException -> 0x0040 }
            java.lang.String r2 = "SHA-256"
            r5.<init>(r2)     // Catch:{ NoSuchAlgorithmException -> 0x0035, InvalidAlgorithmParameterException -> 0x0040 }
            r6 = 32
            r7 = 1
            r2 = r8
            r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ NoSuchAlgorithmException -> 0x0035, InvalidAlgorithmParameterException -> 0x0040 }
            r1.setParameter(r8)     // Catch:{ NoSuchAlgorithmException -> 0x0035, InvalidAlgorithmParameterException -> 0x0040 }
            goto L_0x004d
        L_0x0035:
            r0 = move-exception
            goto L_0x004a
        L_0x0037:
            java.lang.String r1 = r13.getAlgorithm()     // Catch:{ NoSuchAlgorithmException -> 0x0046, InvalidAlgorithmParameterException -> 0x0040 }
            java.security.Signature r1 = java.security.Signature.getInstance(r1)     // Catch:{ NoSuchAlgorithmException -> 0x0046, InvalidAlgorithmParameterException -> 0x0040 }
            goto L_0x004d
        L_0x0040:
            r1 = move-exception
            r1.printStackTrace()
            r1 = r0
            goto L_0x004d
        L_0x0046:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
        L_0x004a:
            r0.printStackTrace()
        L_0x004d:
            if (r1 == 0) goto L_0x00a3
            r1.initVerify(r13)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            java.lang.String r13 = kProofSignatureLabel     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            java.lang.String r0 = "ISO-8859-1"
            byte[] r13 = r13.getBytes(r0)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            r1.update(r13)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            r13 = 4
            byte[] r13 = new byte[r13]     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            int r0 = r12.length()     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            byte r0 = (byte) r0     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            r13[r11] = r0     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            r1.update(r13)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            java.lang.String r13 = "ISO-8859-1"
            byte[] r12 = r12.getBytes(r13)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            r1.update(r12)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            java.lang.String r12 = "ISO-8859-1"
            byte[] r10 = r10.getBytes(r12)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            r1.update(r10)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            java.lang.String r10 = "ISO-8859-1"
            byte[] r10 = r14.getBytes(r10)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            boolean r10 = r1.verify(r10)     // Catch:{ InvalidKeyException -> 0x0094, SignatureException -> 0x008f, UnsupportedEncodingException -> 0x008a }
            if (r10 == 0) goto L_0x00a3
            r10 = 1
            return r10
        L_0x008a:
            r10 = move-exception
            r10.printStackTrace()
            goto L_0x00a3
        L_0x008f:
            r10 = move-exception
            r10.printStackTrace()
            goto L_0x00a3
        L_0x0094:
            r10 = move-exception
            r10.printStackTrace()
            goto L_0x00a3
        L_0x0099:
            r10 = move-exception
            r10.printStackTrace()
            return r11
        L_0x009e:
            r10 = move-exception
            r10.printStackTrace()
            return r11
        L_0x00a3:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.spdy.QuicProofVerifier.verifySignature(java.lang.String, int, java.lang.String, java.security.cert.X509Certificate, java.lang.String):int");
    }
}
