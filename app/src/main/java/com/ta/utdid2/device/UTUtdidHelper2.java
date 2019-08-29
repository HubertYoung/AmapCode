package com.ta.utdid2.device;

import com.ta.utdid2.android.utils.AESUtils;
import com.ta.utdid2.android.utils.Base64;
import com.ta.utdid2.android.utils.StringUtils;

public class UTUtdidHelper2 {
    public String dePack(String str) {
        return AESUtils.decrypt(str);
    }

    public String dePackWithBase64(String str) {
        String decrypt = AESUtils.decrypt(str);
        if (StringUtils.isEmpty(decrypt)) {
            return null;
        }
        try {
            return new String(Base64.decode(decrypt, 0));
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
