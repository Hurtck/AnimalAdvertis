package animaladvertis.com.animaladvertis.util;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.view.Surface;

/**
 * Created by 47321 on 2017/4/18 0018.
 */

public class SensorUtils {
    private static float[] mTmp = new float[16];
    private static float[] oTmp = new float[16];

    //by default, the coordinate is remapped to landscape
    public static void sensorRotationVectorToMatrix(SensorEvent event, int deviceRotation, float[] output) {
        float[] values = event.values;
        switch (deviceRotation){
            case Surface.ROTATION_0:
                SensorManager.getRotationMatrixFromVector(output, values);
                break;
            default:
                SensorManager.getRotationMatrixFromVector(mTmp, values);
                SensorManager.remapCoordinateSystem(mTmp, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, output);
        }
        Matrix.rotateM(output, 0, 90.0F, 1.0F, 0.0F, 0.0F);
    }

    public static void getOrientation(SensorEvent event,float[] output){
        //sensorRotationVectorToMatrix(event,oTmp);
        SensorManager.getRotationMatrixFromVector(oTmp, event.values);
        SensorManager.getOrientation(oTmp,output);
    }

    public static void getOrientationFromRotationMatrix(float[] rotationMatrix,float[] output){
        SensorManager.getOrientation(rotationMatrix,output);
    }
}
