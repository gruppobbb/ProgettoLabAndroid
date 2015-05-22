package model3D;



import library.shadow.math.SFMatrix3f;
import model.Coordinate;
import model.ships.Ship;

/**
 * Author Jan.
 */
public class Ship3D extends Ship {

    private SFMatrix3f modelMatrix;
    private boolean needUpdate;

    private float[] angle = new float[3];
    private Coordinate coord;

    public Ship3D(float x, float y, float z) {

        super( new Coordinate(x, y, z) );
        needUpdate = true;

    }


    public void updateModelMatrix(){

        //creazione della matrice di rotazione
        SFMatrix3f rotationMatrixX = SFMatrix3f.getRotationX(angle[0]);
        SFMatrix3f rotationMatrixY = SFMatrix3f.getRotationY(angle[1]);
        SFMatrix3f rotationMatrixZ = SFMatrix3f.getRotationZ(angle[2]);

        SFMatrix3f totalRotation = rotationMatrixX.MultMatrix(rotationMatrixY);
        modelMatrix = totalRotation.MultMatrix(rotationMatrixZ);

    }

    public void shiftShip(float x, float y){
        coord = getCoordinate();
        coord.setX(coord.getX() + x);
        coord.setY(coord.getY() + y);
    }

    public float[] getAngle() {
        return angle;
    }

    public void setAngle(float angleX, float angleY, float angleZ) {
        angle[0] = angleX;
        angle[1] = angleY;
        angle[2] = angleZ;
        needUpdate = true;
    }

    public void shiftAngle(float shiftAngleX, float shiftAngleY, float shiftAngleZ) {
        angle[0] = angle[0] + shiftAngleX;
        angle[1] = angle[1] + shiftAngleY;
        angle[2] = angle[2] + shiftAngleZ;
        needUpdate = true;
    }

    public SFMatrix3f getModelMatrix() {

        if(needUpdate){

            updateModelMatrix();
            needUpdate = false;

        }
        return modelMatrix;
    }
}
