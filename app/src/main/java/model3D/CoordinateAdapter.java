package model3D;

import model.Coordinate;

/**
 * Adapter per ottenere le coordinate come un vettore di float.
 * @author Max
 */
public class CoordinateAdapter {

    public static float[] getCoordinates(Coordinate coordinate) {
        return new float[]{coordinate.getX(), coordinate.getY(), coordinate.getZ()};
    }
}
