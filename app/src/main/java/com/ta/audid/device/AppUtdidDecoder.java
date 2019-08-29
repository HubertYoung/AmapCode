package com.ta.audid.device;

import com.ta.audid.utils.ByteUtils;
import com.ta.utdid2.android.utils.Base64;
import com.ta.utdid2.android.utils.IntUtils;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.device.UTUtdid;
import java.util.Arrays;

public class AppUtdidDecoder {
    public static UtdidObj decode(String str) {
        UtdidObj utdidObj = new UtdidObj();
        if (str == null || str.length() != 24) {
            utdidObj.setValid(false);
        } else {
            try {
                byte[] decode = Base64.decode(str, 2);
                if (decode.length == 18) {
                    byte[] bArr = new byte[14];
                    byte[] bArr2 = new byte[4];
                    byte[] bArr3 = new byte[4];
                    System.arraycopy(decode, 0, bArr, 0, 14);
                    System.arraycopy(decode, 0, bArr2, 0, 4);
                    byte b = decode[8];
                    System.arraycopy(decode, 14, bArr3, 0, 4);
                    try {
                        if (Arrays.equals(bArr3, IntUtils.getBytes(StringUtils.hashCode(UTUtdid.calcHmac(bArr))))) {
                            utdidObj.setValid(true);
                            utdidObj.setTimestamp(ByteUtils.getLongByByte4(bArr2));
                            utdidObj.setVersion(b);
                            return utdidObj;
                        }
                    } catch (Exception unused) {
                    }
                }
            } catch (Exception unused2) {
                utdidObj.setValid(false);
                return utdidObj;
            }
        }
        return utdidObj;
    }
}
