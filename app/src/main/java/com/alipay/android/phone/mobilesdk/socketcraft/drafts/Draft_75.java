package com.alipay.android.phone.mobilesdk.socketcraft.drafts;

import com.alipay.android.phone.mobilesdk.socketcraft.drafts.Draft.CloseHandshakeType;
import com.alipay.android.phone.mobilesdk.socketcraft.drafts.Draft.HandshakeState;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.InvalidDataException;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.InvalidFrameException;
import com.alipay.android.phone.mobilesdk.socketcraft.exceptions.NotSendableException;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.FrameBuilder;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata.Opcode;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.FramedataImpl1;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ClientHandshake;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ClientHandshakeBuilder;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.HandshakeBuilder;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ServerHandshake;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ServerHandshakeBuilder;
import com.alipay.android.phone.mobilesdk.socketcraft.util.Charsetfunctions;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Draft_75 extends Draft {
    public static final byte CR = 13;
    public static final byte END_OF_FRAME = -1;
    public static final byte LF = 10;
    public static final byte START_OF_FRAME = 0;
    protected ByteBuffer currentFrame;
    protected boolean readingState = false;
    protected List<Framedata> readyframes = new LinkedList();
    private final Random reuseableRandom = new Random();

    public HandshakeState acceptHandshakeAsClient(ClientHandshake request, ServerHandshake response) {
        return (!request.getFieldValue("WebSocket-Origin").equals(response.getFieldValue("Origin")) || !basicAccept(response)) ? HandshakeState.NOT_MATCHED : HandshakeState.MATCHED;
    }

    public HandshakeState acceptHandshakeAsServer(ClientHandshake handshakedata) {
        if (!handshakedata.hasFieldValue("Origin") || !basicAccept(handshakedata)) {
            return HandshakeState.NOT_MATCHED;
        }
        return HandshakeState.MATCHED;
    }

    public ByteBuffer createBinaryFrame(Framedata framedata) {
        if (framedata.getOpcode() != Opcode.TEXT) {
            throw new RuntimeException("only text frames supported");
        }
        ByteBuffer pay = framedata.getPayloadData();
        ByteBuffer b = ByteBuffer.allocate(pay.remaining() + 2);
        b.put(0);
        pay.mark();
        b.put(pay);
        pay.reset();
        b.put(-1);
        b.flip();
        return b;
    }

    public List<Framedata> createFrames(ByteBuffer binary, boolean mask) {
        throw new RuntimeException("not yet implemented");
    }

    public List<Framedata> createFrames(String text, boolean mask) {
        FrameBuilder frame = new FramedataImpl1();
        try {
            frame.setPayload(ByteBuffer.wrap(Charsetfunctions.utf8Bytes(text)));
            frame.setFin(true);
            frame.setOptcode(Opcode.TEXT);
            frame.setTransferemasked(mask);
            return Collections.singletonList(frame);
        } catch (InvalidDataException e) {
            throw new NotSendableException((Throwable) e);
        }
    }

    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder request) {
        request.put("Upgrade", "WebSocket");
        request.put(H5AppHttpRequest.HEADER_CONNECTION, "Upgrade");
        if (!request.hasFieldValue("Origin")) {
            request.put("Origin", "random" + this.reuseableRandom.nextInt());
        }
        return request;
    }

    public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake request, ServerHandshakeBuilder response) {
        response.setHttpStatusMessage("Web Socket Protocol Handshake");
        response.put("Upgrade", "WebSocket");
        response.put(H5AppHttpRequest.HEADER_CONNECTION, request.getFieldValue(H5AppHttpRequest.HEADER_CONNECTION));
        response.put("WebSocket-Origin", request.getFieldValue("Origin"));
        response.put("WebSocket-Location", "ws://" + request.getFieldValue("Host") + request.getResourceDescriptor());
        return response;
    }

    /* access modifiers changed from: protected */
    public List<Framedata> translateRegularFrame(ByteBuffer buffer) {
        while (buffer.hasRemaining()) {
            byte newestByte = buffer.get();
            if (newestByte == 0) {
                if (this.readingState) {
                    throw new InvalidFrameException((String) "unexpected START_OF_FRAME");
                }
                this.readingState = true;
            } else if (newestByte == -1) {
                if (!this.readingState) {
                    throw new InvalidFrameException((String) "unexpected END_OF_FRAME");
                }
                if (this.currentFrame != null) {
                    this.currentFrame.flip();
                    FramedataImpl1 curframe = new FramedataImpl1();
                    curframe.setPayload(this.currentFrame);
                    curframe.setFin(true);
                    curframe.setOptcode(Opcode.TEXT);
                    this.readyframes.add(curframe);
                    this.currentFrame = null;
                    buffer.mark();
                }
                this.readingState = false;
            } else if (!this.readingState) {
                return null;
            } else {
                if (this.currentFrame == null) {
                    this.currentFrame = createBuffer();
                } else if (!this.currentFrame.hasRemaining()) {
                    this.currentFrame = increaseBuffer(this.currentFrame);
                }
                this.currentFrame.put(newestByte);
            }
        }
        List frames = this.readyframes;
        this.readyframes = new LinkedList();
        return frames;
    }

    public List<Framedata> translateFrame(ByteBuffer buffer) {
        List frames = translateRegularFrame(buffer);
        if (frames != null) {
            return frames;
        }
        throw new InvalidDataException(1002);
    }

    public void reset() {
        this.readingState = false;
        this.currentFrame = null;
    }

    public CloseHandshakeType getCloseHandshakeType() {
        return CloseHandshakeType.NONE;
    }

    public ByteBuffer createBuffer() {
        return ByteBuffer.allocate(INITIAL_FAMESIZE);
    }

    public ByteBuffer increaseBuffer(ByteBuffer full) {
        full.flip();
        ByteBuffer newbuffer = ByteBuffer.allocate(checkAlloc(full.capacity() * 2));
        newbuffer.put(full);
        return newbuffer;
    }

    public Draft copyInstance() {
        return new Draft_75();
    }
}
