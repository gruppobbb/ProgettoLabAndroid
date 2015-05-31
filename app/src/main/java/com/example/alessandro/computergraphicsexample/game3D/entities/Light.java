package com.example.alessandro.computergraphicsexample.game3D.entities;

import model.Coordinate;

/**
 * Classe per la rappresentazione e gestione di una sorgente di luce.
 * @author Jancarlos.
 */
public class Light {

    private float[] v3fColor;

    private Coordinate position;

    /**
     * @param position Posizione della luce
     */
    public Light(Coordinate position) {
        this.position = position;
        this.v3fColor = new float[3];
        this.v3fColor[0] = 1.0f;
        this.v3fColor[1] = 1.0f;
        this.v3fColor[2] = 1.0f;
    }

    /**
     * @param position Posizione della luce
     * @param v3fColor Colore della luce
     */
    public Light(Coordinate position, float[] v3fColor) {
        this.position = position;
        this.v3fColor = v3fColor;
    }

    /**
     * Restituisce la posizione della luce.
     * @return Posizione della luce
     */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * Restituisce la posizione della luce, sotto forma di vettore tridimensionale.
     * @return Posizione della luce
     */
    public float[] getPositionVector(){
        float[] positionVector = { position.getX(), position.getY(), position.getZ() };
        return positionVector;
    }

    /**
     * Imposta la posizione della luce, fornendo un oggetto {@link Coordinate}
     * @param position Posizione
     */
    public void setPosition(Coordinate position) {
        this.position = position;
    }

    /**
     * Restituisce il colore della luce, sotto forma di vettore tridimensionale.
     * @return Colore della luce
     */
    public float[] getColor() {
        return v3fColor;
    }

    /**
     * Imposta il colore della luce.
     * @param v3fColor Colore della luce, in RGB
     */
    public void setColor(float[] v3fColor) {
        this.v3fColor = v3fColor;
    }

}