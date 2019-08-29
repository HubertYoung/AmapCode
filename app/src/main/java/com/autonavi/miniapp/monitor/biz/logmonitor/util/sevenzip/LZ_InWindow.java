package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

import java.io.IOException;
import java.io.InputStream;

public class LZ_InWindow {
    public int _blockSize;
    public byte[] _bufferBase;
    public int _bufferOffset;
    int _keepSizeAfter;
    int _keepSizeBefore;
    int _pointerToLastSafePosition;
    public int _pos;
    int _posLimit;
    InputStream _stream;
    boolean _streamEndWasReached;
    public int _streamPos;

    public void MoveBlock() {
        int i = (this._bufferOffset + this._pos) - this._keepSizeBefore;
        if (i > 0) {
            i--;
        }
        int i2 = (this._bufferOffset + this._streamPos) - i;
        for (int i3 = 0; i3 < i2; i3++) {
            byte[] bArr = this._bufferBase;
            bArr[i3] = bArr[i + i3];
        }
        this._bufferOffset -= i;
    }

    public void ReadBlock() throws IOException {
        if (!this._streamEndWasReached) {
            while (true) {
                int i = ((0 - this._bufferOffset) + this._blockSize) - this._streamPos;
                if (i != 0) {
                    int read = this._stream.read(this._bufferBase, this._bufferOffset + this._streamPos, i);
                    if (read == -1) {
                        this._posLimit = this._streamPos;
                        if (this._bufferOffset + this._posLimit > this._pointerToLastSafePosition) {
                            this._posLimit = this._pointerToLastSafePosition - this._bufferOffset;
                        }
                        this._streamEndWasReached = true;
                        return;
                    }
                    this._streamPos += read;
                    if (this._streamPos >= this._pos + this._keepSizeAfter) {
                        this._posLimit = this._streamPos - this._keepSizeAfter;
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void Free() {
        this._bufferBase = null;
    }

    public void Create(int i, int i2, int i3) {
        this._keepSizeBefore = i;
        this._keepSizeAfter = i2;
        int i4 = i + i2 + i3;
        if (this._bufferBase == null || this._blockSize != i4) {
            Free();
            this._blockSize = i4;
            this._bufferBase = new byte[this._blockSize];
        }
        this._pointerToLastSafePosition = this._blockSize - i2;
    }

    public void SetStream(InputStream inputStream) {
        this._stream = inputStream;
    }

    public void ReleaseStream() {
        this._stream = null;
    }

    public void Init() throws IOException {
        this._bufferOffset = 0;
        this._pos = 0;
        this._streamPos = 0;
        this._streamEndWasReached = false;
        ReadBlock();
    }

    public void MovePos() throws IOException {
        this._pos++;
        if (this._pos > this._posLimit) {
            if (this._bufferOffset + this._pos > this._pointerToLastSafePosition) {
                MoveBlock();
            }
            ReadBlock();
        }
    }

    public byte GetIndexByte(int i) {
        return this._bufferBase[this._bufferOffset + this._pos + i];
    }

    public int GetMatchLen(int i, int i2, int i3) {
        if (this._streamEndWasReached && this._pos + i + i3 > this._streamPos) {
            i3 = this._streamPos - (this._pos + i);
        }
        int i4 = i2 + 1;
        int i5 = this._bufferOffset + this._pos + i;
        int i6 = 0;
        while (i6 < i3) {
            int i7 = i5 + i6;
            if (this._bufferBase[i7] != this._bufferBase[i7 - i4]) {
                break;
            }
            i6++;
        }
        return i6;
    }

    public int GetNumAvailableBytes() {
        return this._streamPos - this._pos;
    }

    public void ReduceOffsets(int i) {
        this._bufferOffset += i;
        this._posLimit -= i;
        this._pos -= i;
        this._streamPos -= i;
    }
}
