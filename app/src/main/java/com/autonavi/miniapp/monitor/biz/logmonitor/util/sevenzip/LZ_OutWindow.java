package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

import java.io.IOException;
import java.io.OutputStream;

public class LZ_OutWindow {
    byte[] _buffer;
    int _pos;
    OutputStream _stream;
    int _streamPos;
    int _windowSize = 0;

    public void Create(int i) {
        if (this._buffer == null || this._windowSize != i) {
            this._buffer = new byte[i];
        }
        this._windowSize = i;
        this._pos = 0;
        this._streamPos = 0;
    }

    public void SetStream(OutputStream outputStream) throws IOException {
        ReleaseStream();
        this._stream = outputStream;
    }

    public void ReleaseStream() throws IOException {
        Flush();
        this._stream = null;
    }

    public void Init(boolean z) {
        if (!z) {
            this._streamPos = 0;
            this._pos = 0;
        }
    }

    public void Flush() throws IOException {
        int i = this._pos - this._streamPos;
        if (i != 0) {
            this._stream.write(this._buffer, this._streamPos, i);
            if (this._pos >= this._windowSize) {
                this._pos = 0;
            }
            this._streamPos = this._pos;
        }
    }

    public void CopyBlock(int i, int i2) throws IOException {
        int i3 = (this._pos - i) - 1;
        if (i3 < 0) {
            i3 += this._windowSize;
        }
        while (i2 != 0) {
            if (i3 >= this._windowSize) {
                i3 = 0;
            }
            byte[] bArr = this._buffer;
            int i4 = this._pos;
            this._pos = i4 + 1;
            int i5 = i3 + 1;
            bArr[i4] = this._buffer[i3];
            if (this._pos >= this._windowSize) {
                Flush();
            }
            i2--;
            i3 = i5;
        }
    }

    public void PutByte(byte b) throws IOException {
        byte[] bArr = this._buffer;
        int i = this._pos;
        this._pos = i + 1;
        bArr[i] = b;
        if (this._pos >= this._windowSize) {
            Flush();
        }
    }

    public byte GetByte(int i) {
        int i2 = (this._pos - i) - 1;
        if (i2 < 0) {
            i2 += this._windowSize;
        }
        return this._buffer[i2];
    }
}
