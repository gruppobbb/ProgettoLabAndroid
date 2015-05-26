package com.example.alessandro.computergraphicsexample.game3D.entities;

import android.opengl.Matrix;

import model.Coordinate;
import model.ships.Ship;

/**
 * Modello di un Mob contenente le informazioni per la rappresentazione di un Nodo e del suo modello
 * in una determinata posizione e con una determinata trasformazione.
 * @author Max
 * @author Jan
 */
public class Ship3D extends Ship{

    private float[] mModelMatrix = new float[16];
    private float scale;
    private float angle[] = new float[3];
    private boolean needNewMatrix;


    public Ship3D(Coordinate coordinate) {
        super(coordinate);

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
            //TODO: controllare con questo ordine : scale * rotation * translation.

            Matrix.setIdentityM(mModelMatrix, 0);
            Matrix.translateM(mModelMatrix, 0, coord.getX(), coord.getY(), coord.getZ());

            Matrix.rotateM(mModelMatrix, 0, angle[0], 1.0f, 0.0f, 0.0f);
            Matrix.rotateM(mModelMatrix, 0, angle[1], 0.0f, 1.0f, 0.0f);
            Matrix.rotateM(mModelMatrix, 0, angle[2], 0.0f, 0.0f, 1.0f);

            Matrix.scaleM(mModelMatrix, 0, scale, scale, scale);

            needNewMatrix = false;

        }

        return mModelMatrix;

    }

    public void shiftTraslation(float shiftX, float shiftY, float shiftZ){

        Coordinate coord = getCoordinate();
        coord.setX(coord.getX() + shiftX );
        coord.setY(coord.getY() + shiftY );
        coord.setZ(coord.getZ() + shiftZ );

        needNewMatrix = true;
    }

    public void setTranslation(float x, float y, float z){
        Coordinate coord = getCoordinate();
        coord.setX(x);
        coord.setY(y);
        coord.setZ(z);
        needNewMatrix = true;
    }

    public void setAngle(float angleX, float angleY, float angleZ) {
        angle[0] = angleX;
        angle[1] = angleY;
        angle[2] = angleZ;
        needNewMatrix = true;
    }

    public void shiftAngle(float shiftAngleX, float shiftAngleY, float shiftAngleZ) {
        angle[0] = angle[0] + shiftAngleX;
        angle[1] = angle[1] + shiftAngleY;
        angle[2] = angle[2] + shiftAngleZ;
        needNewMatrix = true;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void shiftScale(float shiftScale) {
        if(scale + shiftScale > 0){
            scale = scale + shiftScale;
            needNewMatrix = true;
        }
    }

}
