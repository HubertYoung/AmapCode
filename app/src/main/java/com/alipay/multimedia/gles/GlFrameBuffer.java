package com.alipay.multimedia.gles;

import android.opengl.GLES20;
import com.alipay.alipaylogger.Log;
import java.nio.IntBuffer;

public class GlFrameBuffer {
    private static String a = "GlFrameBuffer";
    private IntBuffer b = IntBuffer.allocate(1);

    public GlFrameBuffer(int textureID) {
        GLES20.glGenFramebuffers(1, this.b);
        GlUtil.checkGlError("glGenFramebuffers");
        GLES20.glBindFramebuffer(36160, this.b.get(0));
        GlUtil.checkGlError("glBindFramebuffer");
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, textureID, 0);
        GlUtil.checkGlError("glFramebufferTexture2D");
        if (GLES20.glCheckFramebufferStatus(36160) != 36053) {
            Log.e(a, "GlFrameBuffer complete failed");
        }
    }

    public void release() {
        if (this.b != null) {
            GLES20.glDeleteFramebuffers(1, this.b);
            this.b = null;
        }
    }

    public int getID() {
        return this.b.get(0);
    }
}
