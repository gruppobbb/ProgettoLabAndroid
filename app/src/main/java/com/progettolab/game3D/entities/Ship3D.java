package com.progettolab.game3D.entities;

import android.opengl.Matrix;

import model.Coordinate;
import model.ships.Ship;

/**
 * Modello di una {@link Ship} contenente le informazioni per la rappresentazione di un Nodo
 * in una determinata posizione e con una determinata trasformazione.
 * @author Max
 * @author Jan
 */
public class Ship3D extends Ship{

    private float[] mModelMatrix = new float[16];
    private float angle[] = new float[3];
    private boolean needNewMatrix;

    /**
     * Non imposta il raggio di collisione, che deve pertanto essere impostato manualmente.
     * In alternativa utilizzare l'altro costruttore.
     * @param coordinate Coordinate iniziali della ship
     */
    public Ship3D(Coordinate coordinate) {
        super(coordinate);
        //default
        angle[0] = 0.0f;    //TODO: Su blender la scimmia è fatta al contrario, bisogna ruotare il modello.
        angle[1] = (float)Math.PI;
        angle[2] = 0.0f;

        needNewMatrix = true;
    }

    /**
     * @param coordinate Coordinate iniziali della ship
     * @param collisionRay Raggio di collisione della ship
     */
    public Ship3D(Coordinate coordinate, double collisionRay) {
        this(coordinate);
        setCollisionRay(collisionRay);
    }

    /**
     * Restituisce la model matrix della ship
     * @return model matrix
     */
    public float[] getModelMatrix() {
        if(needNewMatrix){

            Coordinate coord = getCoordinate();
            //TODO: controllare con questo ordine : scale * rotation * translation.

            Matrix.setIdentityM(mModelMatrix, 0);
            Matrix.translateM(mModelMatrix, 0, coord.getX(), coord.getY(), coord.getZ());

            Matrix.rotateM(mModelMatrix, 0, angle[0], 1.0f, 0.0f, 0.0f);
            Matrix.rotateM(mModelMatrix, 0, angle[1], 0.0f, 1.0f, 0.0f);
            Matrix.rotateM(mModelMatrix, 0, angle[2], 0.0f, 0.0f, 1.0f);

            needNewMatrix = false;
        }
        return mModelMatrix;
    }


    /**
     * Riposizionamento del centro del modello della ship in un nuovo punto.
     * @param newX
     * @param newY
     * @param newZ
     */
    public void setTraslation(float newX, float newY, float newZ){

        Coordinate coord = getCoordinate();
        coord.setX( newX );
        coord.setY( newY);
        coord.setZ( newZ );
        needNewMatrix = true;

    }

    /**
     * Trasla la ship lungo i tre assi.
     * @param shiftX traslazione lungo X
     * @param shiftY traslazione lungo Y
     * @param shiftZ traslazione lungo Z
     */
    public void shiftTraslation(float shiftX, float shiftY, float shiftZ){

        Coordinate coord = getCoordinate();
        coord.setX(coord.getX() + shiftX );
        coord.setY(coord.getY() + shiftY );
        coord.setZ(coord.getZ() + shiftZ );
        needNewMatrix = true;
    }

    /**
     * Imposta l'inclinazione della ship rispetto ai tre assi.
     * @param angleX Inclinazione rispetto a X
     * @param angleY Inclinazione rispetto a Y
     * @param angleZ Inclinazione rispetto a Z
     */
    public void setAngle(float angleX, float angleY, float angleZ) {
        angle[0] = angleX;
        angle[1] = angleY;
        angle[2] = angleZ;
        needNewMatrix = true;
    }

    /**
     * Modifica l'inclinazione della ship rispetto ai tre assi.
     * @param shiftAngleX Incremento dell'inclinazione lungo l'asse X
     * @param shiftAngleY Incremento dell'inclinazione lungo l'asse Y
     * @param shiftAngleZ Incremento dell'inclinazione lungo l'asse Z
     */
    public void shiftAngle(float shiftAngleX, float shiftAngleY, float shiftAngleZ) {
        angle[0] = angle[0] + shiftAngleX;
        angle[1] = angle[1] + shiftAngleY;
        angle[2] = angle[2] + shiftAngleZ;
        needNewMatrix = true;
    }

}
