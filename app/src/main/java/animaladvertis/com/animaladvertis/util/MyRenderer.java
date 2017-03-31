package animaladvertis.com.animaladvertis.util;

import android.graphics.Matrix;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 47321 on 2017/3/27 0027.
 */

public class MyRenderer implements GLSurfaceView.Renderer {
    float[] taperVertices = new float[]{
            0.0f, 0.3f, 0.0f,
            -0.3f, -0.3f, -0.1f,
            0.3f, -0.3f, -0.1f,
            0.0f, -0.1f, 0.1f
    };

    int[] taperColors = new int[]{
            65535, 0, 0, 65535,
            0, 65535, 0, 65535,
            0, 0, 65535, 65535,
            65535, 65535, 0, 65535
    };

    private byte[] taperFacets = new byte[]{
            0, 1, 2,
            0, 1, 3,
            1, 2, 3,
            0, 2, 3
    };

    float[] cubeVertices = new float[]{
            0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f
    };

    private byte[] cubeFacets = new byte[]{
            0, 1, 2,
            0, 2, 3,
            2, 3, 7,
            2, 6, 7,
            0, 3, 7,
            0, 4, 7,
            4, 5, 6,
            4, 6, 7,
            0, 1, 4,
            1, 4, 5,
            1, 2, 6,
            1, 5, 6
    };

//	float[] triangleData = new float[]{
//			.1f, .6f, .0f,  //上顶点
//			-.3f, .0f, .0f, //左顶点
//			.3f, .1f, .0f   //右顶点
//	};
//	int[] triangleColor = new int[]{
//			65535, 0, 0, 0, //上顶点红色
//			0, 65535, 0, 0, //左顶点绿色
//			0, 0, 65535, 0  //右顶点蓝色
//	};
//	float[] rectData = new float[]{
//			.4f, .4f, .0f,    //右上顶点
//			.4f, -.4f, .0f,   //右下顶点
//			-.4f, .4f, .0f,   //左上顶点
//			-.4f, -.4f, .0f   //左下顶点
//	};
//	int[] rectColor = new int[]{
//			0, 65535, 0, 0,		//右上顶点绿色
//			0, 0, 65535, 0,		//右下顶点蓝色
//			65535, 0, 0, 0,		//左上顶点红色
//			65535, 65535, 0, 0	//左下顶点黄色
//	};
//	float[] rectData2 = new float[]{
//			-.4f, .4f, .0f,	//左上顶点
//			.4f, .4f, .0f,	//右上顶点
//			.4f, -.4f, .0f,	//右下顶点
//			-.4f, -.4f, .0f	//左下顶点
//	};
//	float[] pentacle = new float[]{
//		.4f, .4f, .0f,
//		-.2f, .3f, .0f,
//		.5f, .0f, .0f,
//		-.4f, .0f, .0f,
//		-.1f, -.3f, .0f
//	};
//	FloatBuffer triangleDataBuffer;
//	IntBuffer triangleColorBuffer;
//	FloatBuffer rectDataBuffer;
//	IntBuffer rectColorBuffer;
//	FloatBuffer rectDataBuffer2;
//	FloatBuffer pentacleBuffer;
//	//控制旋转的角度
//	private float rotate;

    FloatBuffer taperVerticesBuffer;
    IntBuffer taperColorsBuffer;
    ByteBuffer taperFacetsBuffer;
    FloatBuffer cubeVerticesBuffer;
    ByteBuffer cubeFacetsBuffer;



    public float rotate=0.0f;
    public float xTranslate=0.0f;
    public float yTranslate=0.0f;


    public MyRenderer() {
        //将定点位置数据转换为FloatBuffer
        taperVerticesBuffer = floatBufferUtil(taperVertices);
        taperFacetsBuffer = ByteBuffer.wrap(taperFacets);
        taperColorsBuffer = intBufferUtil(taperColors);
        cubeVerticesBuffer = floatBufferUtil(cubeVertices);
        cubeFacetsBuffer = ByteBuffer.wrap(cubeFacets);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清除屏幕缓存和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        //启用顶点坐标数据
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //启用顶点颜色数据
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        //设置当前矩阵堆栈为模型堆栈
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //---------------------绘制第一个3d图形-----------------------
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -1.5f);
        //gl.glTranslatef(-0.6f, 0.0f, -1.5f);
        gl.glRotatef(rotate, 0f, 100.0f, 0f);
        gl.glTranslatef(xTranslate,yTranslate,0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, taperVerticesBuffer);
        //设置顶点的颜色数据
        gl.glColorPointer(4, GL10.GL_FIXED, 0, taperColorsBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, taperFacetsBuffer.remaining(), GL10.GL_UNSIGNED_BYTE, taperFacetsBuffer);
        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DIFFUSE);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
        gl.glClearColor(0,0,0,0);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0,0,width,height);
        float ratio = (float)width/height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio,ratio,-1,1,1,10);
    }

    //定义一个工具方法，将int[]数组转换为OpenGl ES所需的IntBuffer
    private IntBuffer intBufferUtil(int[] arr)
    {
        IntBuffer mBuffer;
        //初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }
    //定义一个工具方法，将float[]数组转换为OpenGL ES所需的FloatBuffer
    private FloatBuffer floatBufferUtil(float[] arr)
    {
        FloatBuffer mBuffer;
        //初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }
}
