package com.alipay.multimedia.gles;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GlTexture {
    private IntBuffer a = IntBuffer.allocate(1);

    public GlTexture(int type, int width, int height) {
        GLES20.glGenTextures(1, this.a);
        GlUtil.checkGlError("glGenTextures");
        GLES20.glBindTexture(type, this.a.get(0));
        GlUtil.checkGlError("glBindTexture");
        if (type == 3553) {
            GLES20.glTexImage2D(type, 0, 6408, width, height, 0, 6408, 5121, null);
            GLES20.glTexParameteri(type, 10241, 9729);
            GLES20.glTexParameteri(type, 10240, 9729);
            GLES20.glTexParameteri(type, 10242, 33071);
            GLES20.glTexParameteri(type, 10243, 33071);
        }
    }

    public GlTexture(int type) {
        GLES20.glGenTextures(1, this.a);
        GlUtil.checkGlError("glGenTextures");
        GLES20.glBindTexture(type, this.a.get(0));
        GlUtil.checkGlError("glBindTexture");
        GLES20.glTexParameteri(type, 10241, 9729);
        GLES20.glTexParameteri(type, 10240, 9729);
        GLES20.glTexParameteri(type, 10242, 33071);
        GLES20.glTexParameteri(type, 10243, 33071);
    }

    public GlTexture(FloatBuffer texture, int width, int height) {
        GLES20.glActiveTexture(33984);
        GLES20.glPixelStorei(3317, 4);
        GLES20.glGenTextures(1, this.a);
        GLES20.glBindTexture(3553, this.a.get(0));
        GLES20.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5121, texture);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
    }

    public GlTexture(Bitmap bitmap) {
        GLES20.glGenTextures(1, this.a);
        GlUtil.checkGlError("glGenTextures");
        GLES20.glBindTexture(3553, this.a.get(0));
        GlUtil.checkGlError("glBindTexture");
        GLUtils.texImage2D(3553, 0, 6408, bitmap, 5121, 0);
        GlUtil.checkGlError("texImage2D");
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
    }

    public GlTexture(byte[] bytearray, int width, int height) {
        GLES20.glPixelStorei(3317, 1);
        GLES20.glGenTextures(1, this.a);
        GlUtil.checkGlError("glGenTextures");
        GLES20.glBindTexture(3553, this.a.get(0));
        GlUtil.checkGlError("glBindTexture");
        GLES20.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5121, GlUtil.createByteBuffer(bytearray));
        GlUtil.checkGlError("texImage2D");
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
    }

    public int getID() {
        return this.a.get(0);
    }

    public void release() {
        if (this.a != null) {
            GLES20.glDeleteTextures(1, this.a);
            this.a = null;
        }
    }
}
