package com.alipay.mobile.beehive.capture.magicscan;

import android.opengl.GLES20;
import com.alipay.mobile.beehive.capture.magicscan.utils.MatrixHelper;
import com.alipay.mobile.beehive.capture.magicscan.utils.ShaderUtil;
import com.alipay.mobile.beehive.capture.utils.Logger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class SimpleRender {
    private static final float INIT_POSITION = 0.0f;
    private static final short[] Indices = {0, 1, 2, 2, 3, 0};
    private static final double ONCE_DURATION = 2000.0d;
    private static final String TAG = "[EdgeDetector]SimpleRender";
    private static final String fragment = "#extension GL_OES_EGL_image_external : require\nprecision highp float;\nuniform samplerExternalOES sTexture;\nuniform sampler2D sMeshData;\n\nvarying highp vec2 coordinate;\nvarying float aPos;\nvarying vec3 edgeColor;\nvarying float edgeDetect; \n\nvoid main()\n{\n    if (edgeDetect < 0.0) { \n       gl_FragColor = texture2D(sTexture, coordinate);\n       return; \n    }\n\n    float block = 1200.0;\n    float delta = 1.0/block;\n\n    // do sobel filter\n    float sobel_x[9];\n    sobel_x[0] = -1.0;sobel_x[1] = 0.0;sobel_x[2] = 1.0;\n    sobel_x[3] = -2.0;sobel_x[4] = 0.0;sobel_x[5] = 2.0;\n    sobel_x[6] = -1.0;sobel_x[7] = 0.0;sobel_x[8] = 1.0;\n\n    float sobel_y[9];\n    sobel_y[0] = 1.0;sobel_y[1] = 2.0;sobel_y[2] = 1.0;\n    sobel_y[3] = 0.0;sobel_y[4] = 0.0;sobel_y[5] = 0.0;\n    sobel_y[6] = -1.0;sobel_y[7] = -2.0;sobel_y[8] = -1.0;\n\n    float sum_x = 0.0;\n    float sum_y = 0.0;\n    for (int i = -1; i <=1; i++) {\n        for (int j = -1; j <= 1; j++) {\n            float x = max(0.0, coordinate.x + float(i) * delta);\n            float y = max(0.0, coordinate.y + float(i) * delta);\n\n            vec4 val = texture2D(sTexture, vec2(x, y));\n            float gray = val.r * 0.299 + val.g * 0.587 + val.b * 0.114;\n\n            sum_x += gray * sobel_x[(i + 1) * 3 + (j + 1)];\n            sum_y += gray * sobel_y[(i + 1) * 3 + (j + 1)];\n        }\n    }\n    float edge = sqrt(sum_x * sum_x + sum_y * sum_y);\n\n    if (edge > 0.235) {\n        edge = 1.0;\n    } else {\n        edge = 0.0;\n    }\n\n    float distance = aPos - coordinate.x;\n    float bandwidth = 0.8;\n    float line = coordinate.x - aPos;\n    if (line < 0.001 && line > -0.001)\n    {\n        gl_FragColor = vec4(edgeColor, 1.0);\n    }\n    else\n    {\n        if (distance > 0.0  && distance < bandwidth && coordinate.y > 0.002 && coordinate.y < 0.997 && coordinate.x > 0.002 && coordinate.x < 0.997)\n        {\n            if (edge > 0.02) //  edge line\n            {\n                float alpha = distance / bandwidth;\n                alpha = 1.0 - alpha;\n                alpha = alpha * alpha * alpha;\n                vec4 origin = texture2D(sTexture, coordinate);\n                vec4 edges = vec4(edgeColor, edge);\n\n                gl_FragColor = edges * alpha + origin * (1.0 - alpha);\n            }\n            else // mesh line\n            {\n                float meshheight = 0.3;\n                if (distance <= meshheight)\n                {\n                    vec4 origin = texture2D(sTexture, coordinate);\n                    float x = 1.0 - distance / meshheight;\n                    float y = coordinate.y;\n                    vec4 mesh = texture2D(sMeshData, vec2(y, x));\n\n                    float alpha = mesh.a;\n                    gl_FragColor = mesh * alpha + origin * (1.0 - alpha);\n                }\n                else\n                {\n                    gl_FragColor = texture2D(sTexture, coordinate);\n                }\n            }\n        }\n        else\n        {\n            gl_FragColor = texture2D(sTexture, coordinate);\n        }\n    }\n}\n";
    private static String vertex = "attribute vec4 position;\nattribute mediump vec4 textureCoordinate;\nuniform mat4 transformMatrix;\nuniform mat4 stMatrix;\nuniform float alphaPos;\nuniform vec3 colorEdge;\nuniform float doDetect;\n\nvarying mediump vec2 coordinate;\nvarying float aPos;\nvarying mediump vec3 edgeColor;\nvarying float edgeDetect; \n\nvoid main()\n{\n    gl_Position = transformMatrix * position;\n    coordinate = (stMatrix * textureCoordinate).xy;\n    aPos = alphaPos;\n    edgeColor = colorEdge;\n    edgeDetect = doDetect; \n}\n";
    private float _B = 1.0f;
    private float _G = 1.0f;
    private float _R = 0.0f;
    private int _alphaPos;
    private int _colorEdge;
    private int _doEdgeDetect;
    private int _frameBuffer;
    private int _inputTexSlot;
    private byte[] _meshData;
    private int _meshHeight;
    private int _meshSlot;
    private int _meshTexName = -1;
    private int _meshWidth;
    private int _positionSlot;
    private int _program;
    private int _stMatrix;
    private int _texCoordSlot;
    private int _transformMatrix;
    private ShortBuffer indicesBuffer = null;
    private volatile double mAnimPosition = 0.0d;
    private long mLastDrawTime = 0;
    private MatrixHelper mMatrixHelper = new MatrixHelper();
    private FloatBuffer texCoordBuffer = null;
    private FloatBuffer verticesBuffer = null;

    public void prepareRender(int inputTex, int outputTex) {
        Logger.d(TAG, "prepareRender");
        prepareIndices();
        prepareBuffers();
        prepareShaders();
        setupMeshTexture();
        this.mMatrixHelper.setInitStack();
        this.mMatrixHelper.setOrthoM(-1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 3.0f);
        this.mMatrixHelper.setCamera(0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    public void destroyRender() {
        Logger.debug(TAG, "destroyRender");
        if (this._meshTexName != 0) {
            GLES20.glDeleteTextures(1, new int[]{this._meshTexName}, 0);
            this._meshTexName = 0;
        }
        this._meshData = null;
        if (this._frameBuffer != 0) {
            GLES20.glDeleteFramebuffers(1, new int[]{this._frameBuffer}, 0);
            this._frameBuffer = 0;
        }
    }

    public void setMeshData(byte[] rgba, int width, int height) {
        Logger.d(TAG, "setMeshData, width=" + width + ", height=" + height);
        this._meshData = rgba;
        this._meshWidth = width;
        this._meshHeight = height;
    }

    public void setEdgeColor(float r, float g, float b) {
        this._R = r;
        this._G = g;
        this._B = b;
    }

    private void setupMeshTexture() {
        if (this._meshData != null && this._meshWidth > 0 && this._meshHeight > 0) {
            int[] textures = new int[1];
            GLES20.glGenTextures(1, textures, 0);
            this._meshTexName = textures[0];
            GLES20.glBindTexture(3553, this._meshTexName);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            ByteBuffer byteBuffer = ByteBuffer.allocate(this._meshData.length);
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.put(this._meshData);
            byteBuffer.position(0);
            GLES20.glTexImage2D(3553, 0, 6408, this._meshWidth, this._meshHeight, 0, 6408, 5121, byteBuffer);
            ShaderUtil.checkGlError("setupMeshTexture, glTexImage2D");
        }
    }

    public void draw(int inputTex, int outputTex, float[] stmatrix, boolean useDetect) {
        long start = System.currentTimeMillis();
        if (this.mLastDrawTime == 0) {
            this.mLastDrawTime = System.currentTimeMillis();
        }
        this.mAnimPosition = Math.pow(((double) ((float) (System.currentTimeMillis() - this.mLastDrawTime))) / ONCE_DURATION, 2.0d);
        if (this.mAnimPosition > 2.5d) {
            this.mLastDrawTime = 0;
            this.mAnimPosition = 0.0d;
        }
        if (this._frameBuffer == 0) {
            int[] temp = new int[1];
            GLES20.glGenFramebuffers(1, temp, 0);
            this._frameBuffer = temp[0];
            GLES20.glBindTexture(3553, outputTex);
            GLES20.glBindFramebuffer(36160, this._frameBuffer);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, outputTex, 0);
            ShaderUtil.checkGlError("prepareRender, glFramebufferTexture2D, _frameBuffer=" + this._frameBuffer);
            Logger.debug(TAG, "prepareRender, glFramebufferTexture2D, _frameBuffer=" + this._frameBuffer);
            if (GLES20.glCheckFramebufferStatus(36160) != 36053) {
                Logger.error((String) TAG, (String) "Error in glCheckFramebufferStatus");
            }
            GLES20.glBindTexture(3553, 0);
            GLES20.glBindFramebuffer(36160, 0);
        }
        GLES20.glBindFramebuffer(36160, this._frameBuffer);
        GLES20.glBindTexture(3553, outputTex);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, outputTex, 0);
        int statusCode = GLES20.glCheckFramebufferStatus(36160);
        if (statusCode == 36053) {
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(770, 771);
            GLES20.glUseProgram(this._program);
            this.mMatrixHelper.setSTMatrix(stmatrix);
            GLES20.glUniformMatrix4fv(this._transformMatrix, 1, false, this.mMatrixHelper.getFinalMatrix(), 0);
            GLES20.glUniformMatrix4fv(this._stMatrix, 1, false, this.mMatrixHelper.getSTMatrix(), 0);
            ShaderUtil.checkGlError("draw, glUniformMatrix4v");
            GLES20.glEnableVertexAttribArray(this._positionSlot);
            GLES20.glVertexAttribPointer(this._positionSlot, 3, 5126, false, 12, this.verticesBuffer);
            GLES20.glEnableVertexAttribArray(this._texCoordSlot);
            GLES20.glVertexAttribPointer(this._texCoordSlot, 2, 5126, false, 8, this.texCoordBuffer);
            ShaderUtil.checkGlError("draw, glVertexAttribPointer");
            if (inputTex != -1) {
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(36197, inputTex);
                ShaderUtil.checkGlError("draw, glBindTexture inputTex");
                GLES20.glUniform1i(this._inputTexSlot, 0);
                ShaderUtil.checkGlError("draw, glUniform1i inputTex");
            }
            if (this._meshTexName != -1) {
                GLES20.glActiveTexture(33985);
                GLES20.glBindTexture(3553, this._meshTexName);
                GLES20.glUniform1i(this._meshSlot, 1);
                ShaderUtil.checkGlError("draw, glUniform1i, _meshTexName");
            }
            GLES20.glUniform1f(this._alphaPos, (float) this.mAnimPosition);
            GLES20.glUniform3f(this._colorEdge, this._R, this._G, this._B);
            if (useDetect) {
                GLES20.glUniform1f(this._doEdgeDetect, 1.0f);
            } else {
                GLES20.glUniform1f(this._doEdgeDetect, -1.0f);
            }
            ShaderUtil.checkGlError("draw, glUniform3f");
            GLES20.glDrawElements(4, Indices.length, 5123, this.indicesBuffer);
            ShaderUtil.checkGlError("draw, glDrawElements");
            GLES20.glDisableVertexAttribArray(this._positionSlot);
            GLES20.glDisableVertexAttribArray(this._texCoordSlot);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(36197, 0);
        } else {
            Logger.error((String) TAG, "glCheckFramebufferStatus error, code=" + statusCode);
        }
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glUseProgram(0);
        long used = System.currentTimeMillis() - start;
        if (used >= 10) {
            Logger.debug(TAG, "draw end, takes " + used + " ms");
        }
    }

    private void prepareIndices() {
        ByteBuffer ibb = ByteBuffer.allocateDirect(Indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        this.indicesBuffer = ibb.asShortBuffer();
        this.indicesBuffer.put(Indices);
        this.indicesBuffer.position(0);
    }

    private void prepareShaders() {
        this._program = ShaderUtil.createProgram(vertex, fragment);
        ShaderUtil.checkGlError("prepareShaders, createProgram");
        GLES20.glUseProgram(this._program);
        ShaderUtil.checkGlError("prepareShaders, glUseProgram");
        this._positionSlot = GLES20.glGetAttribLocation(this._program, "position");
        this._texCoordSlot = GLES20.glGetAttribLocation(this._program, "textureCoordinate");
        this._transformMatrix = GLES20.glGetUniformLocation(this._program, "transformMatrix");
        this._stMatrix = GLES20.glGetUniformLocation(this._program, "stMatrix");
        this._alphaPos = GLES20.glGetUniformLocation(this._program, "alphaPos");
        this._colorEdge = GLES20.glGetUniformLocation(this._program, "colorEdge");
        this._doEdgeDetect = GLES20.glGetUniformLocation(this._program, "doDetect");
        this._meshSlot = GLES20.glGetUniformLocation(this._program, "sMeshData");
        this._inputTexSlot = GLES20.glGetUniformLocation(this._program, "sTexture");
        ShaderUtil.checkGlError("prepareShaders, glGetUniformLocations");
        GLES20.glEnableVertexAttribArray(this._positionSlot);
        GLES20.glEnableVertexAttribArray(this._texCoordSlot);
        ShaderUtil.checkGlError("prepareShaders, glEnableVertexAttribArray");
    }

    private void prepareBuffers() {
        ByteBuffer vbb = ByteBuffer.allocateDirect(48);
        vbb.order(ByteOrder.nativeOrder());
        this.verticesBuffer = vbb.asFloatBuffer();
        this.verticesBuffer.put(new float[]{1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f});
        this.verticesBuffer.position(0);
        ByteBuffer vbb2 = ByteBuffer.allocateDirect(32);
        vbb2.order(ByteOrder.nativeOrder());
        this.texCoordBuffer = vbb2.asFloatBuffer();
        this.texCoordBuffer.put(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f});
        this.texCoordBuffer.position(0);
    }
}
