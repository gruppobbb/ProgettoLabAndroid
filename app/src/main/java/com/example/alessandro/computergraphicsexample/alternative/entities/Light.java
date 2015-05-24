package com.example.alessandro.computergraphicsexample.alternative.entities;

import model.Coordinate;

/**
 * Created by Jancarlos.
 */
public class Light {

    private float[] v3fColor;

    private Coordinate position;

    public Light(Coordinate position) {
        this.position = position;
        this.v3fColor = new float[3];
        this.v3fColor[0] = 1.0f;
        this.v3fColor[1] = 1.0f;
        this.v3fColor[2] = 1.0f;
    }

    public Light(Coordinate position, float[] v3fColor) {
        this.position = position;
        this.v3fColor = v3fColor;
    }

    public Coordinate getPosition() {
        return position;
    }

    public float[] getPositionVector(){
        float[] positionVector = { position.getX(), position.getY(), position.getZ() };
        return positionVector;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public float[] getColor() {
        return v3fColor;
    }

    public void setColor(float[] v3fColor) {
        this.v3fColor = v3fColor;
    }

}