package model3D;

import android.opengl.Matrix;

import model.Coordinate;
import model.mobs.Mob;
import model.movement.Moveable;

/**
 * Modello di mob rappresentato in uno spazio tridimensionale.
 * @author Max
 * @author Jan
 */
public class Mob3D extends Mob implements Moveable{

    private float[] modelMatrix = new float[16];
    private boolean needUpdate;

    public Mob3D(Coordinate coordinate, float shiftAmount) {
        super(coordinate, shiftAmount);
        needUpdate = true;
    }

    public float[] getModelMatrix() {
        if(needUpdate) {
            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0,
                    getCoordinate().getX(),
                    getCoordinate().getY(),
                    getCoordinate().getZ());
            needUpdate = false;
        }
        return modelMatrix;
    }

    @Override
    public void move() {
        Coordinate coord = getCoordinate();
        coord.setZ(coord.getZ() + getShiftAmount());
        needUpdate = true;
    }
}
