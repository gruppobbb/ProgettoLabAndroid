package com.progettolab.game3D.entities;

import android.opengl.Matrix;

import model.Coordinate;
import model.mobs.Mob;
import model.movement.Moveable;

/**
 * Modello di un {@link Mob} contenente le informazioni per la rappresentazione di un Nodo
 * in una determinata posizione e con una determinata trasformazione.
 * @author Max
 * @author Jan
 */
public class Mob3D extends Mob implements Moveable{

    private float[] mModelMatrix = new float[16];
    private boolean needNewMatrix;

    /**
     * @param coordinate Coordinate iniziali del mob
     * @param shiftAmount coefficiente di spostamento
     */
    public Mob3D(Coordinate coordinate, float shiftAmount) {
        super(coordinate, shiftAmount);

        needNewMatrix = true;
    }

    /**
     * Restituisce la model matrix della ship
     * @return model matrix
     */
    public float[] getModelMatrix() {
        if(needNewMatrix){
            Coordinate coord = getCoordinate();
            Matrix.setIdentityM(mModelMatrix, 0);
            Matrix.translateM(mModelMatrix, 0, coord.getX(), coord.getY(), coord.getZ());
            needNewMatrix = false;
        }
        return mModelMatrix;
    }

    /**
     * @see Moveable
     */
    @Override
    public void move() {
        Coordinate coord = getCoordinate();
        coord.setZ(coord.getZ() + getShiftAmount());
        needNewMatrix = true;
    }
}
