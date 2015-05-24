package com.example.alessandro.computergraphicsexample.alternative.entities;

import android.opengl.Matrix;

import model.Coordinate;
import model.mobs.Mob;
import model.movement.Moveable;

/**
 * Modello di un Mob contenente le informazioni per la rappresentazione di un Nodo e del suo modello
 * in una determinata posizione e con una determinata trasformazione.
 * @author Max
 * @author Jan
 */
public class AlternativeMob3D extends Mob implements Moveable{

    private float[] mModelMatrix = new float[16];
    private float scale;
    private float angle[] = new float[3];
    private boolean needNewMatrix;

    /**
     * Costruzione di una "istanza" del mob nelle coordinate {@link Coordinate}, con uno spostamento
     * pari a shiftAmount.
     * @param coordinate
     * @param shiftAmount
     */
    public AlternativeMob3D(Coordinate coordinate, float shiftAmount) {
        super(coordinate, shiftAmount);

        //default
        scale = 1.0f;
        angle[0] = 0.0f;    //TODO: Su blender la scimmia è fatta al contrario, bisogna ruotare il modello.
        angle[1] = (float)Math.PI;
        angle[2] = 0.0f;

        needNewMatrix = true;
    }

    public float[] getModelMatrix() {

        if(needNewMatrix){

            Coordinate coord = getCoordinate();

            Matrix.setIdentityM(mModelMatrix, 0);
            Matrix.translateM(mModelMatrix, 0, coord.getX(), coord.getY(), coord.getZ());

            needNewMatrix = false;

        }

        return mModelMatrix;

    }

    @Override
    public void move() {
        Coordinate coord = getCoordinate();
        coord.setZ(coord.getZ() + getShiftAmount());
        needNewMatrix = true;
    }
}
