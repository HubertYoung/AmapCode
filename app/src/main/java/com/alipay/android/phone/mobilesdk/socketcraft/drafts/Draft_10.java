package com.alipay.android.phone.mobilesdk.socketcraft.drafts;

import android.support.v4.media.TransportMediator;
import com.alipay.android.phone.mobilesdk.socketcraft.WebSocket.Role;
import com.alipay.android.phone.mobilesdk.socketcraft.drafts.Draft.CloseHandshakeType;
import com.alipay.android.phone.mobilesdk.socketcraft.drafts.Draft.HandshakeState;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.InvalidDataException;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.InvalidFrameException;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.InvalidHandshakeException;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.LimitExedeedException;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.NotSendableException;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.CloseFrameBuilder;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.FrameBuilder;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata.Opcode;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.FramedataImpl1;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ClientHandshake;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ClientHandshakeBuilder;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.HandshakeBuilder;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.Handshakedata;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ServerHandshake;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ServerHandshakeBuilder;
import com.alipay.android.phone.mobilesdk.socketcraft.util.Base64;
import com.alipay.android.phone.mobilesdk.socketcraft.util.Charsetfunctions;
import com.alipay.android.phone.mobilesdk.socketcraft.util.WsMessageConstants;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Draft_10 extends Draft {
    static final /* synthetic */ boolean $assertionsDisabled = (!Draft_10.class.desiredAssertionStatus());
    private ByteBuffer incompleteframe;
    private final Random reuseableRandom = new Random();

    class IncompleteException extends Throwable {
        private static final long serialVersionUID = 7330519489840500997L;
        private int preferedsize;

        public IncompleteException(int preferedsize2) {
            this.preferedsize = preferedsize2;
        }

        public int getPreferedSize() {
            return this.preferedsize;
        }
    }

    public static int readVersion(Handshakedata handshakedata) {
        int i = -1;
        String vers = handshakedata.getFieldValue("Sec-WebSocket-Version");
        if (vers.length() <= 0) {
            return i;
        }
        try {
            return new Integer(vers.trim()).intValue();
        } catch (NumberFormatException e) {
            return i;
        }
    }

    public HandshakeState acceptHandshakeAsClient(ClientHandshake request, ServerHandshake response) {
        if (!request.hasFieldValue("Sec-WebSocket-Key") || !response.hasFieldValue("Sec-WebSocket-Accept")) {
            return HandshakeState.NOT_MATCHED;
        }
        if (generateFinalKey(request.getFieldValue("Sec-WebSocket-Key")).equals(response.getFieldValue("Sec-WebSocket-Accept"))) {
            return HandshakeState.MATCHED;
        }
        return HandshakeState.NOT_MATCHED;
    }

    public HandshakeState acceptHandshakeAsServer(ClientHandshake handshakedata) {
        int v = readVersion(handshakedata);
        if (v == 7 || v == 8) {
            return basicAccept(handshakedata) ? HandshakeState.MATCHED : HandshakeState.NOT_MATCHED;
        }
        return HandshakeState.NOT_MATCHED;
    }

    public ByteBuffer createBinaryFrame(Framedata framedata) {
        int i;
        ByteBuffer mes = framedata.getPayloadData();
        boolean mask = this.role == Role.CLIENT;
        int sizebytes = mes.remaining() <= 125 ? 1 : mes.remaining() <= 65535 ? 2 : 8;
        if (sizebytes > 1) {
            i = sizebytes + 1;
        } else {
            i = sizebytes;
        }
        ByteBuffer buf = ByteBuffer.allocate((mask ? 4 : 0) + i + 1 + mes.remaining());
        buf.put((byte) (((byte) (framedata.isFin() ? -128 : 0)) | fromOpcode(framedata.getOpcode())));
        byte[] payloadlengthbytes = toByteArray((long) mes.remaining(), sizebytes);
        if ($assertionsDisabled || payloadlengthbytes.length == sizebytes) {
            if (sizebytes == 1) {
                buf.put((byte) ((mask ? Byte.MIN_VALUE : 0) | payloadlengthbytes[0]));
            } else if (sizebytes == 2) {
                buf.put((byte) ((mask ? -128 : 0) | TransportMediator.KEYCODE_MEDIA_PLAY));
                buf.put(payloadlengthbytes);
            } else if (sizebytes == 8) {
                buf.put((byte) ((mask ? -128 : 0) | 127));
                buf.put(payloadlengthbytes);
            } else {
                throw new RuntimeException("Size representation not supported/specified");
            }
            if (mask) {
                ByteBuffer maskkey = ByteBuffer.allocate(4);
                maskkey.putInt(this.reuseableRandom.nextInt());
                buf.put(maskkey.array());
                int i2 = 0;
                while (mes.hasRemaining()) {
                    buf.put((byte) (mes.get() ^ maskkey.get(i2 % 4)));
                    i2++;
                }
            } else {
                buf.put(mes);
            }
            if ($assertionsDisabled || buf.remaining() == 0) {
                buf.flip();
                return buf;
            }
            throw new AssertionError(buf.remaining());
        }
        throw new AssertionError();
    }

    public List<Framedata> createFrames(ByteBuffer binary, boolean mask) {
        FrameBuilder curframe = new FramedataImpl1();
        try {
            curframe.setPayload(binary);
            curframe.setFin(true);
            curframe.setOptcode(Opcode.BINARY);
            curframe.setTransferemasked(mask);
            return Collections.singletonList(curframe);
        } catch (InvalidDataException e) {
            throw new NotSendableException((Throwable) e);
        }
    }

    public List<Framedata> createFrames(String text, boolean mask) {
        FrameBuilder curframe = new FramedataImpl1();
        try {
            curframe.setPayload(ByteBuffer.wrap(Charsetfunctions.utf8Bytes(text)));
            curframe.setFin(true);
            curframe.setOptcode(Opcode.TEXT);
            curframe.setTransferemasked(mask);
            return Collections.singletonList(curframe);
        } catch (InvalidDataException e) {
            throw new NotSendableException((Throwable) e);
        }
    }

    private byte fromOpcode(Opcode opcode) {
        if (opcode == Opcode.CONTINUOUS) {
            return 0;
        }
        if (opcode == Opcode.TEXT) {
            return 1;
        }
        if (opcode == Opcode.BINARY) {
            return 2;
        }
        if (opcode == Opcode.CLOSING) {
            return 8;
        }
        if (opcode == Opcode.PING) {
            return 9;
        }
        if (opcode == Opcode.PONG) {
            return 10;
        }
        throw new RuntimeException("Don't know how to handle " + opcode.toString());
    }

    private String generateFinalKey(String in) {
        String acc = in.trim() + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        try {
            return Base64.encodeBytes(MessageDigest.getInstance("SHA1").digest(acc.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder request) {
        request.put("Upgrade", "websocket");
        request.put(H5AppHttpRequest.HEADER_CONNECTION, "Upgrade");
        request.put("Sec-WebSocket-Version", "8");
        byte[] random = new byte[16];
        this.reuseableRandom.nextBytes(random);
        request.put("Sec-WebSocket-Key", Base64.encodeBytes(random));
        return request;
    }

    public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake request, ServerHandshakeBuilder response) {
        response.put("Upgrade", "websocket");
        response.put(H5AppHttpRequest.HEADER_CONNECTION, request.getFieldValue(H5AppHttpRequest.HEADER_CONNECTION));
        response.setHttpStatusMessage("Switching Protocols");
        String seckey = request.getFieldValue("Sec-WebSocket-Key");
        if (seckey == null) {
            throw new InvalidHandshakeException((String) "missing Sec-WebSocket-Key");
        }
        response.put("Sec-WebSocket-Accept", generateFinalKey(seckey));
        return response;
    }

    private byte[] toByteArray(long val, int bytecount) {
        byte[] buffer = new byte[bytecount];
        int highest = (bytecount * 8) - 8;
        for (int i = 0; i < bytecount; i++) {
            buffer[i] = (byte) ((int) (val >>> (highest - (i * 8))));
        }
        return buffer;
    }

    private Opcode toOpcode(byte opcode) {
        switch (opcode) {
            case 0:
                return Opcode.CONTINUOUS;
            case 1:
                return Opcode.TEXT;
            case 2:
                return Opcode.BINARY;
            case 8:
                return Opcode.CLOSING;
            case 9:
                return Opcode.PING;
            case 10:
                return Opcode.PONG;
            default:
                throw new InvalidFrameException("unknow optcode " + ((short) opcode));
        }
    }

    public List<Framedata> translateFrame(ByteBuffer buffer) {
        List frames = new LinkedList();
        if (this.incompleteframe != null) {
            try {
                buffer.mark();
                int available_next_byte_count = buffer.remaining();
                int expected_next_byte_count = this.incompleteframe.remaining();
                if (expected_next_byte_count > available_next_byte_count) {
                    this.incompleteframe.put(buffer.array(), buffer.position(), available_next_byte_count);
                    buffer.position(buffer.position() + available_next_byte_count);
                    return Collections.emptyList();
                }
                this.incompleteframe.put(buffer.array(), buffer.position(), expected_next_byte_count);
                buffer.position(buffer.position() + expected_next_byte_count);
                frames.add(translateSingleFrame((ByteBuffer) this.incompleteframe.duplicate().position(0)));
                this.incompleteframe = null;
            } catch (IncompleteException e) {
                this.incompleteframe.limit();
                ByteBuffer extendedframe = ByteBuffer.allocate(checkAlloc(e.getPreferedSize()));
                if ($assertionsDisabled || extendedframe.limit() > this.incompleteframe.limit()) {
                    this.incompleteframe.rewind();
                    extendedframe.put(this.incompleteframe);
                    this.incompleteframe = extendedframe;
                    return translateFrame(buffer);
                }
                throw new AssertionError();
            }
        }
        while (buffer.hasRemaining()) {
            buffer.mark();
            try {
                frames.add(translateSingleFrame(buffer));
            } catch (IncompleteException e2) {
                buffer.reset();
                this.incompleteframe = ByteBuffer.allocate(checkAlloc(e2.getPreferedSize()));
                this.incompleteframe.put(buffer);
                return frames;
            }
        }
        return frames;
    }

    public Framedata translateSingleFrame(ByteBuffer buffer) {
        FrameBuilder frame;
        int maxpacketsize = buffer.remaining();
        int realpacketsize = 2;
        if (maxpacketsize < 2) {
            IncompleteException incompleteException = new IncompleteException(2);
            throw incompleteException;
        }
        byte b1 = buffer.get();
        boolean FIN = (b1 >> 8) != 0;
        byte rsv = (byte) ((b1 & Byte.MAX_VALUE) >> 4);
        if (rsv != 0) {
            throw new InvalidFrameException("bad rsv " + rsv);
        }
        byte b2 = buffer.get();
        boolean MASK = (b2 & Byte.MIN_VALUE) != 0;
        int payloadlength = (byte) (b2 & Byte.MAX_VALUE);
        Opcode optcode = toOpcode((byte) (b1 & 15));
        if (FIN || !(optcode == Opcode.PING || optcode == Opcode.PONG || optcode == Opcode.CLOSING)) {
            if (payloadlength < 0 || payloadlength > 125) {
                if (optcode == Opcode.PING || optcode == Opcode.PONG || optcode == Opcode.CLOSING) {
                    throw new InvalidFrameException((String) "more than 125 octets");
                } else if (payloadlength == 126) {
                    realpacketsize = 2 + 2;
                    if (maxpacketsize < 4) {
                        IncompleteException incompleteException2 = new IncompleteException(4);
                        throw incompleteException2;
                    }
                    byte[] sizebytes = new byte[3];
                    sizebytes[1] = buffer.get();
                    sizebytes[2] = buffer.get();
                    BigInteger bigInteger = new BigInteger(sizebytes);
                    payloadlength = bigInteger.intValue();
                } else {
                    realpacketsize = 2 + 8;
                    if (maxpacketsize < 10) {
                        IncompleteException incompleteException3 = new IncompleteException(10);
                        throw incompleteException3;
                    }
                    byte[] bytes = new byte[8];
                    for (int i = 0; i < 8; i++) {
                        bytes[i] = buffer.get();
                    }
                    BigInteger bigInteger2 = new BigInteger(bytes);
                    long length = bigInteger2.longValue();
                    if (length > 2147483647L) {
                        throw new LimitExedeedException(WsMessageConstants.MSG_PAYLOAD_SIZE_BIG);
                    }
                    payloadlength = (int) length;
                }
            }
            int realpacketsize2 = (MASK ? 4 : 0) + realpacketsize + payloadlength;
            if (maxpacketsize < realpacketsize2) {
                IncompleteException incompleteException4 = new IncompleteException(realpacketsize2);
                throw incompleteException4;
            }
            ByteBuffer payload = ByteBuffer.allocate(checkAlloc(payloadlength));
            if (MASK) {
                byte[] maskskey = new byte[4];
                buffer.get(maskskey);
                for (int i2 = 0; i2 < payloadlength; i2++) {
                    payload.put((byte) (buffer.get() ^ maskskey[i2 % 4]));
                }
            } else {
                payload.put(buffer.array(), buffer.position(), payload.limit());
                buffer.position(buffer.position() + payload.limit());
            }
            if (optcode == Opcode.CLOSING) {
                frame = new CloseFrameBuilder();
            } else {
                frame = new FramedataImpl1();
                frame.setFin(FIN);
                frame.setOptcode(optcode);
            }
            payload.flip();
            frame.setPayload(payload);
            return frame;
        }
        throw new InvalidFrameException((String) "control frames may no be fragmented");
    }

    public void reset() {
        this.incompleteframe = null;
    }

    public Draft copyInstance() {
        return new Draft_10();
    }

    public CloseHandshakeType getCloseHandshakeType() {
        return CloseHandshakeType.TWOWAY;
    }
}
