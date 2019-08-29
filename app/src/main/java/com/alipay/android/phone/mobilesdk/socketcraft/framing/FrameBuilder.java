package com.alipay.android.phone.mobilesdk.socketcraft.framing;

import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata.Opcode;
import java.nio.ByteBuffer;

public interface FrameBuilder extends Framedata {
    void setFin(boolean z);

    void setOptcode(Opcode opcode);

    void setPayload(ByteBuffer byteBuffer);

    void setTransferemasked(boolean z);
}
