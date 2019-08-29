package com.alipay.mobile.security.bio.utils;

import android.util.Base64;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class RSASign {
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static boolean doCheck(byte[] bArr, byte[] bArr2, InputStream inputStream) {
        try {
            RSAPublicKey a = a(inputStream);
            Signature instance = Signature.getInstance("SHA1WithRSA");
            instance.initVerify(a);
            instance.update(bArr);
            return instance.verify(bArr2);
        } catch (SignatureException e) {
            BioLog.e(e.toString());
        } catch (Exception e2) {
            BioLog.e(e2.toString());
        }
        return false;
    }

    private static RSAPublicKey a(InputStream inputStream) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return a(sb.toString());
                }
                if (readLine.charAt(0) != '-') {
                    sb.append(readLine);
                    sb.append(13);
                }
            }
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e2) {
            throw new Exception("公钥输入流为空");
        }
    }

    private static RSAPublicKey a(String str) {
        try {
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e2) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e3) {
            throw new Exception("公钥数据为空");
        }
    }
}
