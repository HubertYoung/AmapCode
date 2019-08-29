package com.alipay.android.phone.inside.offlinecode.engine;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.android.phone.a.a.a;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.offlinecode.gen.YctCodeProtocol;
import com.alipay.android.phone.inside.offlinecode.plugin.utils.Utils;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import com.alipay.android.phone.inside.offlinecode.utils.MD5;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.security.bio.utils.DESCoder;
import com.alipay.offlinepay.usersslwrapper.CryptoJNI;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.taobao.accs.common.Constants;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

public class ScriptContext {
    private static Context mContext;

    public static void setContext(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    public static String $getCryptoResult(String str, String str2) throws Exception {
        JSONObject jSONObject = new JSONObject(str);
        if ("1".equals(jSONObject.getString("resultCode"))) {
            return jSONObject.getString("result");
        }
        Behavior a = LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusScriptGetCryptoResultFail");
        a.g = str2;
        a.a(str);
        StringBuilder sb = new StringBuilder("crypto method:");
        sb.append(str2);
        sb.append(" occurs error.");
        throw new Exception(sb.toString());
    }

    public static byte[] $enc(String str, String str2, byte[] bArr, byte[] bArr2) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        SecretKey generateSecret = SecretKeyFactory.getInstance(str).generateSecret(new SecretKeySpec(bArr, str));
        Cipher instance = Cipher.getInstance(str2);
        instance.init(1, generateSecret, secureRandom);
        return instance.doFinal(bArr2);
    }

    public static String getEnvInfo(JSONObject jSONObject) throws Exception {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("platform", a.a);
        jSONObject2.put(Constants.KEY_MODEL, Build.MODEL);
        jSONObject2.put("version", VERSION.RELEASE);
        return jSONObject2.toString();
    }

    public static String getUserInfo(JSONObject jSONObject) throws Exception {
        return Utils.getLoginId();
    }

    public static String hasApi(JSONObject jSONObject) throws Exception {
        String string = jSONObject.getString("name");
        for (Method name : ScriptContext.class.getMethods()) {
            if (TextUtils.equals(name.getName(), string)) {
                return "true";
            }
        }
        return "false";
    }

    public static String spm(JSONObject jSONObject) throws Exception {
        String string = jSONObject.getString("spmId");
        LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, string).a(jSONObject.optString(ProcessInfo.ALIAS_EXT));
        return "";
    }

    public static String log(JSONObject jSONObject) throws Exception {
        String optString = jSONObject.optString("tag");
        String string = jSONObject.getString("message");
        if (TextUtils.isEmpty(optString)) {
            optString = "buscode-script";
        }
        LoggerFactory.f().b(optString, string);
        return "";
    }

    public static String utcTime(JSONObject jSONObject) throws Exception {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String sm2(JSONObject jSONObject) throws Exception {
        String str;
        String string = jSONObject.getString("hexData");
        String string2 = jSONObject.getString("privateKey");
        String string3 = jSONObject.getString(Constants.KEY_MODE);
        String optString = jSONObject.optString("uidHex");
        if (TextUtils.isEmpty(optString)) {
            str = CryptoJNI.sm2Sign(string, string2, Integer.parseInt(string3));
        } else {
            str = CryptoJNI.sm2SignWithUid(string, string2, Integer.parseInt(string3), optString);
        }
        return $getCryptoResult(str, "SM2");
    }

    public static String sm2Verify(JSONObject jSONObject) throws Exception {
        String str;
        String string = jSONObject.getString("hexData");
        String string2 = jSONObject.getString("hexSignData");
        String string3 = jSONObject.getString("publicKey");
        String string4 = jSONObject.getString(Constants.KEY_MODE);
        String optString = jSONObject.optString("uidHex");
        if (TextUtils.isEmpty(optString)) {
            str = CryptoJNI.sm2Verify(string, string2, string3, Integer.parseInt(string4));
        } else {
            str = CryptoJNI.sm2VerifyWithUid(string, string2, string3, Integer.parseInt(string4), optString);
        }
        return String.valueOf("1".equals(new JSONObject(str).getString("resultCode")));
    }

    public static String sm3(JSONObject jSONObject) throws Exception {
        return $getCryptoResult(CryptoJNI.sm3Digest(jSONObject.getString("hexData")), "SM3");
    }

    public static String ecdsa(JSONObject jSONObject) throws Exception {
        return $getCryptoResult(CryptoJNI.ecdsaSign(jSONObject.getString("hexData"), jSONObject.getString("privateKey"), jSONObject.getString("eccName")), "ECDSA");
    }

    public static String md5(JSONObject jSONObject) throws Exception {
        return MD5.getHexDigest(jSONObject.getString("hexData"));
    }

    public static String sha1(JSONObject jSONObject) throws Exception {
        return $getCryptoResult(CryptoJNI.sha1Digest(jSONObject.getString("hexData")), "SHA1");
    }

    public static String sha256(JSONObject jSONObject) throws Exception {
        return $getCryptoResult(CryptoJNI.sha256Digest(jSONObject.getString("hexData")), "SHA256");
    }

    public static String des(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("DES", "DES", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String des_cbc_nopadding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("DES", "DES/CBC/NoPadding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String des_cbc_pkcs5padding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("DES", "DES/CBC/PKCS5Padding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String des_ecb_nopadding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("DES", "DES/ECB/NoPadding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String des_ecb_pkcs5padding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("DES", "DES/ECB/PKCS5Padding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String aes(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("AES", "AES", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String aes_cbc_nopadding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("AES", "AES/CBC/NoPadding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String aes_cbc_pkcs5padding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("AES", "AES/CBC/PKCS5Padding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String aes_ecb_nopadding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("AES", "AES/ECB/NoPadding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String aes_ecb_pkcs5padding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc("AES", "AES/ECB/PKCS5Padding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String tri_des(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc(DESCoder.ALGORITHM, DESCoder.ALGORITHM, HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String tri_des_cbc_nopadding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc(DESCoder.ALGORITHM, "DESede/CBC/NoPadding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String tri_des_cbc_pkcs5padding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc(DESCoder.ALGORITHM, "DESede/CBC/PKCS5Padding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String tri_des_ecb_nopadding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc(DESCoder.ALGORITHM, "DESede/ECB/NoPadding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String tri_des_ecb_pkcs5padding(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString($enc(DESCoder.ALGORITHM, "DESede/ECB/PKCS5Padding", HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }

    public static String base64(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString(Base64.encode(HexUtils.parse(jSONObject.getString("hexData")), 2));
    }

    public static String hmac(JSONObject jSONObject) throws Exception {
        String string = jSONObject.getString("hexData");
        SecretKeySpec secretKeySpec = new SecretKeySpec(HexUtils.parse(jSONObject.getString("privateKey")), "HmacMD5");
        Mac instance = Mac.getInstance(secretKeySpec.getAlgorithm());
        instance.init(secretKeySpec);
        return HexUtils.toHexString(instance.doFinal(HexUtils.parse(string)));
    }

    public static String pboc_3des_mac(JSONObject jSONObject) throws Exception {
        return HexUtils.toHexString(YctCodeProtocol.calculatePboc3DesMAC(HexUtils.parse(jSONObject.getString("privateKey")), HexUtils.parse(jSONObject.getString("hexData"))));
    }
}
