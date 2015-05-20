package model3D;

import android.opengl.Matrix;

import model.Coordinate;
import model.mobs.Mob;
import model.movement.Moveable;
import shadow.math.SFMatrix3f;

/**
 * Modello di un Mob contenente le informazioni per la rappresentazione di un Nodo e del suo modello
 * in una determinata posizione e con una determinata trasformazione.
 * @author Max
 * @author Jan
 */
public class Mob3D extends Mob implements Moveable{

    private SFMatrix3f matrix3f;
    private float scale;
    private float angle[] = new float[3];
    private boolean needUpdate;

    /**
     * Costruzione di una "istanza" del mob nelle coordinate {@link Coordinate}, con uno spostamento
     * pari a shiftAmount.
     * @param coordinate
     * @param shiftAmount
     */
    public Mob3D(Coordinate coordinate, float shiftAmount) {
        super(coordinate, shiftAmount);

        //default
        scale = 1.0f;
        angle[0] = 0.0f;    //TODO: Su blender la scimmia è fatta al contrario, bisogna ruotare il modello.
        angle[1] = (float)Math.PI;
        angle[2] = 0.0f;

        needUpdate = true;
    }


    public SFMatrix3f getModelMatrix() {
        if(needUpdate) {
            //TODO: Con questo risolviamo il "POP-UP" del mob, appena possibile vi spiego come.
            //Creazione della matrice di scala.
            SFMatrix3f scaleMatrix=SFMatrix3f.getScale(scale, scale, scale);

            //creazione della matrice di rotazione
            SFMatrix3f rotationMatrixX = SFMatrix3f.getRotationX(angle[0]);
            SFMatrix3f rotationMatrixY = SFMatrix3f.getRotationY(angle[1]);
            SFMatrix3f rotationMatrixZ = SFMatrix3f.getRotationZ(angle[2]);

            SFMatrix3f totalRotation = rotationMatrixX.MultMatrix(rotationMatrixY);
            totalRotation = totalRotation.MultMatrix(rotationMatrixZ);

            //Salvataggio della matrice risultante dal prodotto delle due precedenti.
            matrix3f = scaleMatrix.MultMatrix(totalRotation);

            needUpdate = false;
        }
        return matrix3f;
    }



    @Override
    public void move() {
        Coordinate coord = getCoordinate();
        coord.setZ(coord.getZ() + getShiftAmount());
        needUpdate = true;
    }
}
