package tv.danmaku.ijk.media.gles;

import android.graphics.Point;
import android.graphics.RectF;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ParamChecker;
import com.alipay.multimedia.gles.GlUtil;
import com.iflytek.tts.TtsService.Tts;

public class GLRoundedGeometry {
    public static final int BUBBLE_TYPE_LEFT = 1;
    public static final int BUBBLE_TYPE_NONE = 0;
    public static final int BUBBLE_TYPE_RIGHT = 2;
    private static final float HEIGHT_RATION = 2.28f;
    private static final float TRIANGLE_VER_EDGE_LENGTH = 0.08f;
    private float TRIANGLE_OFFSET = 0.057f;
    private float TRIANGLE_OFFSET_Y = 0.0f;
    private float[] mBottomLeft = new float[2];
    private float[] mBottomLeftRadius = new float[2];
    private float[] mBottomRight = new float[2];
    private float[] mBottomRightRadius = new float[2];
    private int mBubbleType = 0;
    private float[] mInnerBottomLeft = new float[2];
    private float[] mInnerBottomRight = new float[2];
    private float[] mInnerTopLeft = new float[2];
    private float[] mInnerTopRight = new float[2];
    private float[] mJiaojiao1 = new float[2];
    private float[] mJiaojiao2 = new float[2];
    private float[] mJiaojiao3 = new float[2];
    private float[] mLeftBottom = new float[2];
    private float[] mLeftTop = new float[2];
    private float[] mRightBottom = new float[2];
    private float[] mRightTop = new float[2];
    private float[] mTopLeft = new float[2];
    private float[] mTopLeftRadius = new float[2];
    private float[] mTopRight = new float[2];
    private float[] mTopRightRadius = new float[2];
    private int mTriangleOffetY = GlUtil.getDefaltTriangleOffsetY(AppUtils.getApplicationContext());
    private float mTriangleVEL = TRIANGLE_VER_EDGE_LENGTH;

    public static class GeometryArrays {
        public int indicesOffset = 0;
        public short[] triangleIndices;
        public float[] triangleVertices;
        public int verticesOffset = 0;

        public GeometryArrays(float[] vertices, short[] indices) {
            this.triangleVertices = vertices;
            this.triangleIndices = indices;
        }
    }

    public GeometryArrays generateVertexData(RectF radii, RectF viewPortGLBounds, Point viewPortPxSize) {
        return generateVertexData(radii, viewPortGLBounds, viewPortPxSize, 0.0f);
    }

    public GeometryArrays generateVertexData(RectF radii, RectF viewPortGLBounds, Point viewPortPxSize, float z) {
        float x0 = viewPortGLBounds.left;
        float x1 = viewPortGLBounds.right;
        float y0 = viewPortGLBounds.bottom;
        float y1 = viewPortGLBounds.top;
        float leftTopRadius = radii.left;
        float rightTopRadius = radii.top;
        float rightBottomRadius = radii.right;
        float leftBottomRadius = radii.bottom;
        this.mTopLeftRadius[0] = (leftTopRadius / ((float) viewPortPxSize.x)) * viewPortGLBounds.width();
        this.mTopLeftRadius[1] = (leftTopRadius / ((float) viewPortPxSize.y)) * (-viewPortGLBounds.height());
        this.mTopRightRadius[0] = (rightTopRadius / ((float) viewPortPxSize.x)) * viewPortGLBounds.width();
        this.mTopRightRadius[1] = (rightTopRadius / ((float) viewPortPxSize.y)) * (-viewPortGLBounds.height());
        this.mBottomRightRadius[0] = (rightBottomRadius / ((float) viewPortPxSize.x)) * viewPortGLBounds.width();
        this.mBottomRightRadius[1] = (rightBottomRadius / ((float) viewPortPxSize.y)) * (-viewPortGLBounds.height());
        this.mBottomLeftRadius[0] = (leftBottomRadius / ((float) viewPortPxSize.x)) * viewPortGLBounds.width();
        this.mBottomLeftRadius[1] = (leftBottomRadius / ((float) viewPortPxSize.y)) * (-viewPortGLBounds.height());
        this.TRIANGLE_OFFSET_Y = (((float) this.mTriangleOffetY) / ((float) viewPortPxSize.y)) * (-viewPortGLBounds.height());
        if (viewPortPxSize.x > viewPortPxSize.y) {
            this.mTriangleVEL = 0.18239999f;
        } else {
            this.mTriangleVEL = TRIANGLE_VER_EDGE_LENGTH;
        }
        if (this.mBubbleType == 1 && this.TRIANGLE_OFFSET > 0.0f) {
            buildLeftTriangleLoc(x0, y0, x1, y1);
        }
        buildLeftRectLoc(x0, y0, x1, y1);
        buildTopRectLoc(x0, y0, x1, y1);
        buildRightRectLoc(x0, y0, x1, y1);
        buildBottomRectLoc(x0, y0, x1, y1);
        buildInnerRectLoc(x0, y0, x1, y1);
        if (this.mBubbleType == 2 && this.TRIANGLE_OFFSET > 0.0f) {
            buildRightTriangleLoc(x0, y0, x1, y1);
        }
        int verticesSize = Tts.TTS_STATE_DESTROY;
        int indicesSize = 102;
        if ((this.mBubbleType == 1 || this.mBubbleType == 2) && this.TRIANGLE_OFFSET > 0.0f) {
            verticesSize = Tts.TTS_STATE_DESTROY + 15;
            indicesSize = 102 + 3;
        }
        float[] vertices = new float[verticesSize];
        short[] indices = new short[indicesSize];
        GeometryArrays geoArrays = new GeometryArrays(vertices, indices);
        addRect(geoArrays, new float[][]{this.mInnerTopLeft, this.mInnerTopRight, this.mInnerBottomLeft, this.mInnerBottomRight}, viewPortGLBounds, z);
        geoArrays.verticesOffset += 20;
        geoArrays.indicesOffset += 6;
        addRect(geoArrays, new float[][]{this.mLeftTop, this.mInnerTopLeft, this.mLeftBottom, this.mInnerBottomLeft}, viewPortGLBounds, z);
        geoArrays.verticesOffset += 20;
        geoArrays.indicesOffset += 6;
        addRect(geoArrays, new float[][]{this.mInnerTopRight, this.mRightTop, this.mInnerBottomRight, this.mRightBottom}, viewPortGLBounds, z);
        geoArrays.verticesOffset += 20;
        geoArrays.indicesOffset += 6;
        addRect(geoArrays, new float[][]{this.mTopLeft, this.mInnerTopLeft, this.mTopRight, this.mInnerTopRight}, viewPortGLBounds, z);
        geoArrays.verticesOffset += 20;
        geoArrays.indicesOffset += 6;
        addRect(geoArrays, new float[][]{this.mInnerBottomLeft, this.mBottomLeft, this.mInnerBottomRight, this.mBottomRight}, viewPortGLBounds, z);
        geoArrays.verticesOffset += 20;
        geoArrays.indicesOffset += 6;
        addRoundedCorner(geoArrays, this.mInnerTopLeft, this.mTopLeftRadius, 3.1415927f, 1.5707964f, 6, viewPortGLBounds, z);
        geoArrays.verticesOffset += 40;
        geoArrays.indicesOffset += 18;
        addRoundedCorner(geoArrays, this.mInnerTopRight, this.mTopRightRadius, 1.5707964f, 0.0f, 6, viewPortGLBounds, z);
        geoArrays.verticesOffset += 40;
        geoArrays.indicesOffset += 18;
        addRoundedCorner(geoArrays, this.mInnerBottomRight, this.mBottomRightRadius, 4.712389f, 6.2831855f, 6, viewPortGLBounds, z);
        geoArrays.verticesOffset += 40;
        geoArrays.indicesOffset += 18;
        addRoundedCorner(geoArrays, this.mInnerBottomLeft, this.mBottomLeftRadius, 3.1415927f, 4.712389f, 6, viewPortGLBounds, z);
        geoArrays.verticesOffset += 40;
        geoArrays.indicesOffset += 18;
        if ((this.mBubbleType == 1 || this.mBubbleType == 2) && this.TRIANGLE_OFFSET > 0.0f) {
            addTriangle(geoArrays, new float[][]{this.mJiaojiao1, this.mJiaojiao2, this.mJiaojiao3}, viewPortGLBounds, z);
        }
        return new GeometryArrays(vertices, indices);
    }

    private void buildLeftTriangleLoc(float x0, float y0, float x1, float y1) {
        ParamChecker.pmdCheck(Float.valueOf(y0), Float.valueOf(x1));
        if (this.mBubbleType == 1) {
            this.mJiaojiao1[0] = x0;
            this.mJiaojiao1[1] = y1 - this.TRIANGLE_OFFSET_Y;
            this.mJiaojiao2[0] = this.mJiaojiao1[0] + this.TRIANGLE_OFFSET;
            this.mJiaojiao2[1] = this.mJiaojiao1[1] + (this.mTriangleVEL / 2.0f);
            this.mJiaojiao3[0] = this.mJiaojiao1[0] + this.TRIANGLE_OFFSET;
            this.mJiaojiao3[1] = this.mJiaojiao1[1] - (this.mTriangleVEL / 2.0f);
        }
    }

    private void buildLeftRectLoc(float x0, float y0, float x1, float y1) {
        ParamChecker.pmdCheck(Float.valueOf(x1));
        if (this.mBubbleType == 1) {
            this.mLeftTop[0] = this.TRIANGLE_OFFSET + x0;
            this.mLeftTop[1] = y1 - this.mTopLeftRadius[1];
            this.mLeftBottom[0] = this.TRIANGLE_OFFSET + x0;
            this.mLeftBottom[1] = this.mBottomLeftRadius[1] + y0;
            return;
        }
        this.mLeftTop[0] = x0;
        this.mLeftTop[1] = y1 - this.mTopLeftRadius[1];
        this.mLeftBottom[0] = x0;
        this.mLeftBottom[1] = this.mBottomLeftRadius[1] + y0;
    }

    private void buildRightRectLoc(float x0, float y0, float x1, float y1) {
        ParamChecker.pmdCheck(Float.valueOf(x0));
        if (this.mBubbleType == 2) {
            this.mRightTop[0] = x1 - this.TRIANGLE_OFFSET;
            this.mRightTop[1] = y1 - this.mTopRightRadius[1];
            this.mRightBottom[0] = x1 - this.TRIANGLE_OFFSET;
            this.mRightBottom[1] = this.mBottomRightRadius[1] + y0;
            return;
        }
        this.mRightTop[0] = x1;
        this.mRightTop[1] = y1 - this.mTopRightRadius[1];
        this.mRightBottom[0] = x1;
        this.mRightBottom[1] = this.mBottomRightRadius[1] + y0;
    }

    private void buildTopRectLoc(float x0, float y0, float x1, float y1) {
        ParamChecker.pmdCheck(Float.valueOf(y0));
        if (this.mBubbleType == 1) {
            this.mTopLeft[0] = this.mTopLeftRadius[0] + x0 + this.TRIANGLE_OFFSET;
            this.mTopLeft[1] = y1;
            this.mTopRight[0] = x1 - this.mTopRightRadius[0];
            this.mTopRight[1] = y1;
        } else if (this.mBubbleType == 2) {
            this.mTopLeft[0] = this.mTopLeftRadius[0] + x0;
            this.mTopLeft[1] = y1;
            this.mTopRight[0] = (x1 - this.mTopRightRadius[0]) - this.TRIANGLE_OFFSET;
            this.mTopRight[1] = y1;
        } else {
            this.mTopLeft[0] = this.mTopLeftRadius[0] + x0;
            this.mTopLeft[1] = y1;
            this.mTopRight[0] = x1 - this.mTopRightRadius[0];
            this.mTopRight[1] = y1;
        }
    }

    private void buildBottomRectLoc(float x0, float y0, float x1, float y1) {
        ParamChecker.pmdCheck(Float.valueOf(y1));
        if (this.mBubbleType == 1) {
            this.mBottomLeft[0] = this.mBottomLeftRadius[0] + x0 + this.TRIANGLE_OFFSET;
            this.mBottomLeft[1] = y0;
            this.mBottomRight[0] = x1 - this.mBottomRightRadius[0];
            this.mBottomRight[1] = y0;
        } else if (this.mBubbleType == 2) {
            this.mBottomLeft[0] = this.mBottomLeftRadius[0] + x0;
            this.mBottomLeft[1] = y0;
            this.mBottomRight[0] = (x1 - this.mBottomRightRadius[0]) - this.TRIANGLE_OFFSET;
            this.mBottomRight[1] = y0;
        } else {
            this.mBottomLeft[0] = this.mBottomLeftRadius[0] + x0;
            this.mBottomLeft[1] = y0;
            this.mBottomRight[0] = x1 - this.mBottomRightRadius[0];
            this.mBottomRight[1] = y0;
        }
    }

    private void buildInnerRectLoc(float x0, float y0, float x1, float y1) {
        ParamChecker.pmdCheck(Float.valueOf(x0), Float.valueOf(y0), Float.valueOf(x1), Float.valueOf(y1));
        this.mInnerTopLeft[0] = this.mTopLeft[0];
        this.mInnerTopLeft[1] = this.mLeftTop[1];
        this.mInnerTopRight[0] = this.mTopRight[0];
        this.mInnerTopRight[1] = this.mRightTop[1];
        this.mInnerBottomLeft[0] = this.mBottomLeft[0];
        this.mInnerBottomLeft[1] = this.mLeftBottom[1];
        this.mInnerBottomRight[0] = this.mBottomRight[0];
        this.mInnerBottomRight[1] = this.mRightBottom[1];
    }

    private void buildRightTriangleLoc(float x0, float y0, float x1, float y1) {
        ParamChecker.pmdCheck(Float.valueOf(x0), Float.valueOf(y0), Float.valueOf(x1));
        if (this.mBubbleType == 2) {
            this.mJiaojiao1[0] = this.mRightTop[0] + this.TRIANGLE_OFFSET;
            this.mJiaojiao1[1] = y1 - this.TRIANGLE_OFFSET_Y;
            this.mJiaojiao2[0] = this.mRightTop[0];
            this.mJiaojiao2[1] = this.mJiaojiao1[1] - (this.mTriangleVEL / 2.0f);
            this.mJiaojiao3[0] = this.mRightTop[0];
            this.mJiaojiao3[1] = this.mJiaojiao1[1] + (this.mTriangleVEL / 2.0f);
        }
    }

    public void setBubbleType(int bubbleType) {
        this.mBubbleType = bubbleType;
    }

    public void setTriangleOffsetY(int offset) {
        this.mTriangleOffetY = offset;
        if (offset < 0) {
            this.TRIANGLE_OFFSET = 0.0f;
        }
    }

    private void addRect(GeometryArrays geoArrays, float[][] rectPoints, RectF viewPort, float z) {
        float[] vertices = geoArrays.triangleVertices;
        short[] indices = geoArrays.triangleIndices;
        int indicesOffset = geoArrays.indicesOffset;
        int verticesOffset = geoArrays.verticesOffset;
        int rectPointIdx = 0;
        for (float[] rectPoint : rectPoints) {
            int currentVertexOffset = verticesOffset + (rectPointIdx * 5);
            vertices[currentVertexOffset + 0] = rectPoint[0];
            vertices[currentVertexOffset + 1] = rectPoint[1];
            vertices[currentVertexOffset + 2] = z;
            vertices[currentVertexOffset + 3] = (rectPoint[0] - viewPort.left) / viewPort.width();
            vertices[currentVertexOffset + 4] = (rectPoint[1] - viewPort.bottom) / (-viewPort.height());
            rectPointIdx++;
        }
        int initialIdx = verticesOffset / 5;
        indices[indicesOffset + 0] = (short) initialIdx;
        indices[indicesOffset + 1] = (short) (initialIdx + 1);
        indices[indicesOffset + 2] = (short) (initialIdx + 2);
        indices[indicesOffset + 3] = (short) (initialIdx + 1);
        indices[indicesOffset + 4] = (short) (initialIdx + 2);
        indices[indicesOffset + 5] = (short) (initialIdx + 3);
    }

    private void addRoundedCorner(GeometryArrays geoArrays, float[] center, float[] radius, float rads0, float rads1, int triangles, RectF viewPort, float z) {
        int triangleEdge2Offset;
        float[] vertices = geoArrays.triangleVertices;
        short[] indices = geoArrays.triangleIndices;
        int verticesOffset = geoArrays.verticesOffset;
        int indicesOffset = geoArrays.indicesOffset;
        int i = 0;
        while (i < triangles) {
            int currentOffset = verticesOffset + (i * 5) + (i > 0 ? 10 : 0);
            float rads = rads0 + ((rads1 - rads0) * (((float) i) / ((float) triangles)));
            float radsNext = rads0 + ((rads1 - rads0) * (((float) (i + 1)) / ((float) triangles)));
            if (i == 0) {
                vertices[currentOffset + 0] = center[0];
                vertices[currentOffset + 1] = center[1];
                vertices[currentOffset + 2] = z;
                vertices[currentOffset + 3] = (vertices[currentOffset + 0] - viewPort.left) / viewPort.width();
                vertices[currentOffset + 4] = (vertices[currentOffset + 1] - viewPort.bottom) / (-viewPort.height());
                vertices[currentOffset + 5] = center[0] + (radius[0] * ((float) Math.cos((double) rads)));
                vertices[currentOffset + 6] = center[1] + (radius[1] * ((float) Math.sin((double) rads)));
                vertices[currentOffset + 7] = z;
                vertices[currentOffset + 8] = (vertices[currentOffset + 5] - viewPort.left) / viewPort.width();
                vertices[currentOffset + 9] = (vertices[currentOffset + 6] - viewPort.bottom) / (-viewPort.height());
                triangleEdge2Offset = 10;
            } else {
                triangleEdge2Offset = 0;
            }
            int edge2Offset = currentOffset + triangleEdge2Offset;
            vertices[edge2Offset + 0] = center[0] + (radius[0] * ((float) Math.cos((double) radsNext)));
            vertices[edge2Offset + 1] = center[1] + (radius[1] * ((float) Math.sin((double) radsNext)));
            vertices[edge2Offset + 2] = z;
            vertices[edge2Offset + 3] = (vertices[edge2Offset + 0] - viewPort.left) / viewPort.width();
            vertices[edge2Offset + 4] = (vertices[edge2Offset + 1] - viewPort.bottom) / (-viewPort.height());
            int initialIdx = verticesOffset / 5;
            indices[(i * 3) + indicesOffset + 0] = (short) initialIdx;
            indices[(i * 3) + indicesOffset + 1] = (short) (initialIdx + i + 1);
            indices[(i * 3) + indicesOffset + 2] = (short) (initialIdx + i + 2);
            i++;
        }
    }

    private void addTriangle(GeometryArrays geoArrays, float[][] trianglesPoints, RectF viewPort, float z) {
        float[] vertices = geoArrays.triangleVertices;
        short[] indices = geoArrays.triangleIndices;
        int indicesOffset = geoArrays.indicesOffset;
        int verticesOffset = geoArrays.verticesOffset;
        int rectPointIdx = 0;
        for (float[] rectPoint : trianglesPoints) {
            int currentVertexOffset = verticesOffset + (rectPointIdx * 5);
            vertices[currentVertexOffset + 0] = rectPoint[0];
            vertices[currentVertexOffset + 1] = rectPoint[1];
            vertices[currentVertexOffset + 2] = z;
            vertices[currentVertexOffset + 3] = (rectPoint[0] - viewPort.left) / viewPort.width();
            vertices[currentVertexOffset + 4] = (rectPoint[1] - viewPort.bottom) / (-viewPort.height());
            rectPointIdx++;
        }
        int initialIdx = verticesOffset / 5;
        indices[indicesOffset + 0] = (short) initialIdx;
        indices[indicesOffset + 1] = (short) (initialIdx + 1);
        indices[indicesOffset + 2] = (short) (initialIdx + 2);
    }
}
