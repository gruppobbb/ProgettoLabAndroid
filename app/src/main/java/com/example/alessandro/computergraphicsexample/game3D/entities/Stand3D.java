package com.example.alessandro.computergraphicsexample.game3D.entities;

import android.opengl.Matrix;

import model.Coordinate;

/**
 * @author Jancarlos.
 */
public class Stand3D {

    private Coordinate coord;
    private float[] angles;
    private float[] mModelMatrix;
    private boolean needNewMatrix;

    public Stand3D(Coordinate coord) {
        this.coord = coord;
        angles = new float[3];
        angles[0] = 0.0f;
        angles[1] = 0.0f;
        angles[2] = 0.0f;
        needNewMatrix = true;
        mModelMatrix = new float[16];
    }

    /**
     * Restituisce la model matrix dello Stand.
     * @return model matrix
     */
    public float[] getModelMatrix() {
        if(needNewMatrix){

            Matrix.setIdentityM(mModelMatrix, 0);
            Matrix.translateM(mModelMatrix, 0, coord.getX(), coord.getY(), coord.getZ());

            Matrix.rotateM(mModelMatrix, 0, angles[0], 1.0f, 0.0f, 0.0f);
            Matrix.rotateM(mModelMatrix, 0, angles[1], 0.0f, 1.0f, 0.0f);
            Matrix.rotateM(mModelMatrix, 0, angles[2], 0.0f, 0.0f, 1.0f);

            needNewMatrix = false;
        }
        return mModelMatrix;
    }

    /**
     * Modifica l'inclinazione dello Stand rispetto ai tre assi.
     * @param shiftAngleX Incremento dell'inclinazione lungo l'asse X
     * @param shiftAngleY Incremento dell'inclinazione lungo l'asse Y
     * @param shiftAngleZ Incremento dell'inclinazione lungo l'asse Z
     */
    public void shiftAngle(float shiftAngleX, float shiftAngleY, float shiftAngleZ) {
        angles[0] = angles[0] + shiftAngleX;
        angles[1] = angles[1] + shiftAngleY;
        angles[2] = angles[2] + shiftAngleZ;
        needNewMatrix = true;
    }

}
