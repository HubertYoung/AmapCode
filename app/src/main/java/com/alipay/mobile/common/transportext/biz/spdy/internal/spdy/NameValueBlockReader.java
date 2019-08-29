package com.alipay.mobile.common.transportext.biz.spdy.internal.spdy;

import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

class NameValueBlockReader implements Closeable {
    /* access modifiers changed from: private */
    public int compressedLimit;
    private final FillableInflaterInputStream fillableInflaterInputStream;
    private final DataInputStream nameValueBlockIn = new DataInputStream(this.fillableInflaterInputStream);

    class FillableInflaterInputStream extends InflaterInputStream {
        public FillableInflaterInputStream(InputStream in, Inflater inf) {
            super(in, inf);
        }

        public void fill() {
            super.fill();
        }
    }

    NameValueBlockReader(final InputStream in) {
        this.fillableInflaterInputStream = new FillableInflaterInputStream(new InputStream() {
            public int read() {
                return Util.readSingleByte(this);
            }

            public int read(byte[] buffer, int offset, int byteCount) {
                int consumed = in.read(buffer, offset, Math.min(byteCount, NameValueBlockReader.this.compressedLimit));
                NameValueBlockReader.this.compressedLimit = NameValueBlockReader.this.compressedLimit - consumed;
                return consumed;
            }

            public void close() {
                in.close();
            }
        }, new Inflater() {
            public int inflate(byte[] buffer, int offset, int count) {
                int result = super.inflate(buffer, offset, count);
                if (result != 0 || !needsDictionary()) {
                    return result;
                }
                setDictionary(Spdy3.DICTIONARY);
                return super.inflate(buffer, offset, count);
            }
        });
    }

    public List<String> readNameValueBlock(int length) {
        this.compressedLimit += length;
        try {
            int numberOfPairs = this.nameValueBlockIn.readInt();
            if (numberOfPairs < 0) {
                throw new IOException("numberOfPairs < 0: " + numberOfPairs);
            } else if (numberOfPairs > 1024) {
                throw new IOException("numberOfPairs > 1024: " + numberOfPairs);
            } else {
                List entries = new ArrayList(numberOfPairs * 2);
                for (int i = 0; i < numberOfPairs; i++) {
                    String name = readString();
                    String values = readString();
                    if (name.length() == 0) {
                        throw new IOException("name.length == 0");
                    }
                    entries.add(name);
                    entries.add(values);
                }
                doneReading();
                return entries;
            }
        } catch (DataFormatException e) {
            throw new IOException(e.getMessage());
        }
    }

    private void doneReading() {
        if (this.compressedLimit != 0) {
            this.fillableInflaterInputStream.fill();
            if (this.compressedLimit != 0) {
                throw new IOException("compressedLimit > 0: " + this.compressedLimit);
            }
        }
    }

    private String readString() {
        int length = this.nameValueBlockIn.readInt();
        byte[] bytes = new byte[length];
        Util.readFully(this.nameValueBlockIn, bytes);
        return new String(bytes, 0, length, "UTF-8");
    }

    public void close() {
        this.nameValueBlockIn.close();
    }
}
