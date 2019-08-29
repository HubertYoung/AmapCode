package com.alipay.android.phone.mobilesdk.socketcraft.framing;

import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.InvalidDataException;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.InvalidFrameException;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata.Opcode;
import com.alipay.android.phone.mobilesdk.socketcraft.util.Charsetfunctions;
import java.nio.ByteBuffer;

public class CloseFrameBuilder extends FramedataImpl1 implements CloseFrame {
    static final ByteBuffer emptybytebuffer = ByteBuffer.allocate(0);
    private int code;
    private String reason;

    public CloseFrameBuilder() {
        super(Opcode.CLOSING);
        setFin(true);
    }

    public CloseFrameBuilder(int code2) {
        super(Opcode.CLOSING);
        setFin(true);
        setCodeAndMessage(code2, "");
    }

    public CloseFrameBuilder(int code2, String m) {
        super(Opcode.CLOSING);
        setFin(true);
        setCodeAndMessage(code2, m);
    }

    private void setCodeAndMessage(int code2, String m) {
        if (m == null) {
            m = "";
        }
        if (code2 == 1015) {
            code2 = 1005;
            m = "";
        }
        if (code2 != 1005) {
            byte[] by = Charsetfunctions.utf8Bytes(m);
            ByteBuffer buf = ByteBuffer.allocate(4);
            buf.putInt(code2);
            buf.position(2);
            ByteBuffer pay = ByteBuffer.allocate(by.length + 2);
            pay.put(buf);
            pay.put(by);
            pay.rewind();
            setPayload(pay);
        } else if (m.length() > 0) {
            throw new InvalidDataException(1002, (String) "A close frame must have a closecode if it has a reason");
        }
    }

    private void initCloseCode() {
        this.code = 1005;
        ByteBuffer payload = super.getPayloadData();
        payload.mark();
        if (payload.remaining() >= 2) {
            ByteBuffer bb = ByteBuffer.allocate(4);
            bb.position(2);
            bb.putShort(payload.getShort());
            bb.position(0);
            this.code = bb.getInt();
            if (this.code == 1006 || this.code == 1015 || this.code == 1005 || this.code > 4999 || this.code < 1000 || this.code == 1004) {
                throw new InvalidFrameException("closecode must not be sent over the wire: " + this.code);
            }
        }
        payload.reset();
    }

    public int getCloseCode() {
        return this.code;
    }

    private void initMessage() {
        if (this.code == 1005) {
            this.reason = Charsetfunctions.stringUtf8(super.getPayloadData());
            return;
        }
        ByteBuffer b = super.getPayloadData();
        int mark = b.position();
        try {
            b.position(b.position() + 2);
            this.reason = Charsetfunctions.stringUtf8(b);
            b.position(mark);
        } catch (IllegalArgumentException e) {
            throw new InvalidFrameException((Throwable) e);
        } catch (Throwable th) {
            b.position(mark);
            throw th;
        }
    }

    public String getMessage() {
        return this.reason;
    }

    public String toString() {
        return super.toString() + "code: " + this.code;
    }

    public void setPayload(ByteBuffer payload) {
        super.setPayload(payload);
        initCloseCode();
        initMessage();
    }

    public ByteBuffer getPayloadData() {
        if (this.code == 1005) {
            return emptybytebuffer;
        }
        return super.getPayloadData();
    }
}
