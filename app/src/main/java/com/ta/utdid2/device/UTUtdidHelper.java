package com.ta.utdid2.device;

import com.ta.utdid2.android.utils.AESUtils;
import com.ta.utdid2.android.utils.Base64;

public class UTUtdidHelper {
    public String pack(byte[] bArr) {
        return AESUtils.encrypt(Base64.encodeToString(bArr, 2));
    }

    public String packUtdidStr(String str) {
        return AESUtils.encrypt(str);
    }

    public String dePack(String str) {
        return AESUtils.decrypt(str);
    }
}
