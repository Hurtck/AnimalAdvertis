package animaladvertis.com.animaladvertis.util;

import android.graphics.Matrix;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

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
            0.0f, 2f, 0.0f,
            -2f, -2f, -2f,
            2f, -2f, -2f,
            0.0f, -2f, 2f
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

    private final float[] mat_ambient = { 0.2f, 0.3f, 0.8f, 1.0f };
    private FloatBuffer mat_ambient_buf;
    private final float[] mat_diffuse = { 0.4f, 0.6f, 0.8f, 1.0f };
    private FloatBuffer mat_diffuse_buf;
    private final float[] mat_specular = { 0.2f * 0.4f, 0.2f * 0.6f, 0.2f * 0.8f, 1.0f };
    private FloatBuffer mat_specular_buf;

    private Sphere mSphere = new Sphere();

    public volatile float mLightX = 10f;
    public volatile float mLightY = 10f;
    public volatile float mLightZ = 10f;

    FloatBuffer taperVerticesBuffer;
    IntBuffer taperColorsBuffer;
    ByteBuffer taperFacetsBuffer;
    FloatBuffer cubeVerticesBuffer;
    ByteBuffer cubeFacetsBuffer;



    public float rotate=0.0f;
    public float xTranslate=0.0f;
    public float yTranslate=0.0f;
    public float[] mvp = new float[2];
    public static final float offset = 3;


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
        //---------------------绘制第一个3d图形-----------------------
        gl.glLoadIdentity();
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        // 材质
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambient_buf);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_diffuse_buf);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_specular_buf);
        // 镜面指数 0~128 越小越粗糙
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 96.0f);

        //光源位置
        float[] light_position = {mLightX, mLightY, mLightZ, 0.0f};
        ByteBuffer mpbb = ByteBuffer.allocateDirect(light_position.length*4);
        mpbb.order(ByteOrder.nativeOrder());
        FloatBuffer mat_posiBuf = mpbb.asFloatBuffer();
        mat_posiBuf.put(light_position);
        mat_posiBuf.position(0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, mat_posiBuf);
        gl.glTranslatef(mvp[0],mvp[1],-5);
        mSphere.draw(gl);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //gl.glDisable(GL10.GL_DIFFUSE);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
        gl.glClearColor(0,0,0,0);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        initBuffers();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0,0,width,height);
        float ratio = (float)width/height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        GLU.gluPerspective(gl, 90.0f, (float) width / height, 0.1f, 50.0f);

        // 选择模型观察矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // 重置模型观察矩阵
        gl.glLoadIdentity();
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

    private void initBuffers() {
        ByteBuffer bufTemp = ByteBuffer.allocateDirect(mat_ambient.length * 4);
        bufTemp.order(ByteOrder.nativeOrder());
        mat_ambient_buf = bufTemp.asFloatBuffer();
        mat_ambient_buf.put(mat_ambient);
        mat_ambient_buf.position(0);

        bufTemp = ByteBuffer.allocateDirect(mat_diffuse.length * 4);
        bufTemp.order(ByteOrder.nativeOrder());
        mat_diffuse_buf = bufTemp.asFloatBuffer();
        mat_diffuse_buf.put(mat_diffuse);
        mat_diffuse_buf.position(0);

        bufTemp = ByteBuffer.allocateDirect(mat_specular.length * 4);
        bufTemp.order(ByteOrder.nativeOrder());
        mat_specular_buf = bufTemp.asFloatBuffer();
        mat_specular_buf.put(mat_specular);
        mat_specular_buf.position(0);
    }


}
