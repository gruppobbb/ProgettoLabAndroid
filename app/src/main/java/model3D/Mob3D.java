package model3D;

import android.mtp.MtpObjectInfo;
import android.opengl.Matrix;

import model.Coordinate;
import model.mobs.Mob;

/**
 * Created by Max on 19/05/2015.
 */
public class Mob3D extends Mob {

    private float[] modelMatrix = new float[16];
    private boolean needUpdate;


    public Mob3D(Coordinate coordinate, float shiftAmount) {
        super(coordinate, shiftAmount);
        needUpdate = true;
    }

    public float[] getModelMatrix() {
        if(needUpdate) {
            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0, getCoordinate().getX(), getCoordinate().getY(), getCoordinate().getZ());
        }
        return modelMatrix;
    }

}
